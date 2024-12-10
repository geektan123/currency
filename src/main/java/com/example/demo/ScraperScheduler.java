package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Component
public class ScraperScheduler {

    @Autowired
    private ScraperService scraperService;

    // Schedule task to run every day at midnight (CRON expression: "0 0 0 * * ?")
    @Scheduled(cron = "0 0 0 * * ?")
    public void triggerScrapingTask() {
        try {
            String quote = "GBPINR";

            // Get current timestamp in seconds
            long now = Instant.now().getEpochSecond();

            // Calculate timestamps for different periods
            long oneWeekAgo = now - 7L * 24 * 60 * 60;
            long oneMonthAgo = now - 30L * 24 * 60 * 60;
            long threeMonthsAgo = now - 90L * 24 * 60 * 60;
            long sixMonthsAgo = now - 180L * 24 * 60 * 60;
            long oneYearAgo = now - 365L * 24 * 60 * 60;

            // Scrape data for each period
            scrapeAndLog(quote, oneWeekAgo, now);
            scrapeAndLog(quote, oneMonthAgo, now);
            scrapeAndLog(quote, threeMonthsAgo, now);
            scrapeAndLog(quote, sixMonthsAgo, now);
            scrapeAndLog(quote, oneYearAgo, now);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to scrape data and log the results
    private void scrapeAndLog(String quote, long fromDate, long toDate) {
        try {
            List<HistoricalData> dataList = scraperService.scrapeData(quote, String.valueOf(fromDate), String.valueOf(toDate));
            System.out.println("Fetched " + dataList.size() + " records for " + quote + " from " + fromDate + " to " + toDate);
        } catch (IOException e) {
            System.err.println("Error scraping data for " + quote + " from " + fromDate + " to " + toDate);
            e.printStackTrace();
        }
    }
}
