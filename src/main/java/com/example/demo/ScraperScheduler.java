package com.example.demo;

import com.google.api.core.ApiFuture;
import com.google.firebase.database.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    public ScraperScheduler(FirebaseDatabase firebaseDatabase) {
        this.databaseReference = firebaseDatabase.getReference("historicalData");
    }

    @Scheduled(cron = "0 0 0 * * ?") // Every day at midnight (00:00)
    public void scrape7DaysData() {
        executeScheduledTask(7, "7Days");
    }

    @Scheduled(cron = "0 0 0 * * ?") // Every day at midnight (00:00)
    public void scrape1MonthData() {
        executeScheduledTask(30, "1Month");
    }

    @Scheduled(cron = "0 0 0 * * ?") // Every day at midnight (00:00)
    public void scrape3MonthsData() {
        executeScheduledTask(90, "3Months");
    }

    @Scheduled(cron = "0 0 0 * * ?") // Every day at midnight (00:00)
    public void scrape6MonthsData() {
        executeScheduledTask(180, "6Months");
    }

    @Scheduled(cron = "0 0 0 * * ?") // Every day at midnight (00:00)
    public void scrape1YearData() {
        executeScheduledTask(365, "1Year");
    }

    private void executeScheduledTask(int daysAgo, String range) {
        long now = Instant.now().getEpochSecond();
        long fromDate = calculatePastTimestamp(daysAgo);

        processData(quote, fromDate, now, range);
        processData(quote1, fromDate, now, range);
    }

    private void processData(String quote, long fromDate, long toDate, String range) {
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
        ApiFuture<Void> future = databaseReference.child(path).setValueAsync(dataList);

        // Handle success or failure
        try {
            // Block until the operation completes or times out
            future.get(); // You can use future.get(timeout, unit) for timeout
            logger.info("Data successfully updated in Firebase at path '{}'", path);
        } catch (Exception e) {
            logger.error("Failed to update data in Firebase at path '{}': {}", path, e.getMessage(), e);
        }
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

        // Wait for the asynchronous operation to complete with a timeout
        return futureData.get(30, TimeUnit.SECONDS); // Timeout after 30 seconds
    }
}
