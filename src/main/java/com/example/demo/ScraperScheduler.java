package com.example.demo;

import com.google.firebase.database.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class ScraperScheduler {

    private static final Logger logger = LoggerFactory.getLogger(ScraperScheduler.class);

    @Autowired
    private ScraperService scraperService;

    @Value("${scraper.quote:GBPINR}")
    private String quote;

    @Value("${scraper.quote1:AEDINR}")
    private String quote1;

    private final DatabaseReference databaseReference;

    // Constructor to inject FirebaseDatabase reference
    @Autowired
    public ScraperScheduler(FirebaseDatabase firebaseDatabase) {
        this.databaseReference = firebaseDatabase.getReference("historicalData");
    }

    // Scheduled to run daily at midnight (UTC)
    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void scrape7DaysData() {
        scrapeDataForRange(7, "7Days");
    }

    // Scheduled to run on the 1st day of each month (UTC)
    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void scrape1MonthData() {
        scrapeDataForRange(30, "1Month");
    }
    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void scrape3MonthsData() {
            scrapeDataForRange(90, "3Month");

        }
    // Scheduled to run on the 1st day of every 6th month (UTC)
    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void scrape6MonthsData() {
        scrapeDataForRange(180, "6Months");
    }

    // Scheduled to run on the 1st day of every year (UTC)
    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void scrape1YearData() {
        scrapeDataForRange(365, "1Year");
    }

    private void scrapeDataForRange(int daysAgo, String range) {
        long now = Instant.now().getEpochSecond();
        long fromDate = calculatePastTimestamp(daysAgo);

        scrapeAndSave(quote, fromDate, now, range);
        scrapeAndSave(quote1, fromDate, now, range);
    }

    private void scrapeAndSave(String quote, long fromDate, long toDate, String range) {
        try {
            List<HistoricalData> dataList = scraperService.scrapeData(quote, String.valueOf(fromDate), String.valueOf(toDate));

            if (dataList.isEmpty()) {
                logger.warn("No data fetched for '{}' from {} to {}", quote, fromDate, toDate);
            } else {
                String path = quote + "/" + range;
                saveToFirebase(path, dataList);
                logger.info("Saved {} records for '{}' in range '{}' from {} to {}", dataList.size(), quote, range, fromDate, toDate);
            }
        } catch (IOException e) {
            logger.error("Error scraping data for '{}' from {} to {}", quote, fromDate, toDate, e);
        }
    }

    private void saveToFirebase(String path, List<HistoricalData> dataList) {
        databaseReference.child(path).setValueAsync(dataList);
    }

    private long calculatePastTimestamp(int daysAgo) {
        return Instant.now().getEpochSecond() - daysAgo * 24L * 60 * 60;
    }


    public List<HistoricalData> fetchDataFromFirebase(String range) throws Exception {
        String path = quote + "/" + range; // Adjust if needed for quote1
        CompletableFuture<List<HistoricalData>> futureData = new CompletableFuture<>();

        databaseReference.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<HistoricalData> dataList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HistoricalData data = snapshot.getValue(HistoricalData.class);
                    if (data != null) {
                        dataList.add(data);
                    }
                }
                futureData.complete(dataList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                futureData.completeExceptionally(new Exception("Error fetching data from Firebase: " + databaseError.getMessage()));
            }
        });

        // Wait for the asynchronous operation to complete
        return futureData.get(); // Use timeout or default handling as needed
    }
}
