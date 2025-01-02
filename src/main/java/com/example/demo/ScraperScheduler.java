package com.example.demo;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

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
}
