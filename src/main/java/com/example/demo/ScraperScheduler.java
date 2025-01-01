package com.example.demo;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ScraperScheduler {

    private static final Logger logger = LoggerFactory.getLogger(ScraperScheduler.class);

    @Autowired
    private ScraperService scraperService;

    @Value("${scraper.quote:GBPINR}")
    private String quote;

    @Value("${scraper.quote1:AEDINR}")
    private String quote1;

    /**
     * Scheduled method to scrape data for the last 7 days for quote and quote1 separately
     */
    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public Map<String, List<HistoricalData>> scrape7DaysData() {
        long now = Instant.now().getEpochSecond();
        long fromDate = calculatePastTimestamp(7);  // 7 days ago

        Map<String, List<HistoricalData>> result = new HashMap<>();
        result.put(quote, scrapeAndLog(quote, fromDate, now));
        result.put(quote1, scrapeAndLog(quote1, fromDate, now));

        return result;
    }

    /**
     * Scheduled method to scrape data for the last 1 week for quote and quote1 separately
     */
    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public Map<String, List<HistoricalData>> scrape1WeekData() {
        long now = Instant.now().getEpochSecond();
        long fromDate = calculatePastTimestamp(7);  // 7 days ago

        Map<String, List<HistoricalData>> result = new HashMap<>();
        result.put(quote, scrapeAndLog(quote, fromDate, now));
        result.put(quote1, scrapeAndLog(quote1, fromDate, now));

        return result;
    }

    /**
     * Scheduled method to scrape data for the last 1 month for quote and quote1 separately
     */
    @Scheduled(cron = "0 0 0 1 * ?") // Runs on the 1st day of each month
    public Map<String, List<HistoricalData>> scrape1MonthData() {
        long now = Instant.now().getEpochSecond();
        long fromDate = calculatePastTimestamp(30);  // 1 month ago (approximately 30 days)

        Map<String, List<HistoricalData>> result = new HashMap<>();
        result.put(quote, scrapeAndLog(quote, fromDate, now));
        result.put(quote1, scrapeAndLog(quote1, fromDate, now));

        return result;
    }

    /**
     * Scheduled method to scrape data for the last 6 months for quote and quote1 separately
     */
    @Scheduled(cron = "0 0 0 1 6 ?") // Runs on the 1st day of every 6th month
    public Map<String, List<HistoricalData>> scrape6MonthsData() {
        long now = Instant.now().getEpochSecond();
        long fromDate = calculatePastTimestamp(180);  // 6 months ago (approximately 180 days)

        Map<String, List<HistoricalData>> result = new HashMap<>();
        result.put(quote, scrapeAndLog(quote, fromDate, now));
        result.put(quote1, scrapeAndLog(quote1, fromDate, now));

        return result;
    }

    /**
     * Scheduled method to scrape data for the last 1 year for quote and quote1 separately
     */
    @Scheduled(cron = "0 0 0 1 1 ?") // Runs on the 1st day of every year
    public Map<String, List<HistoricalData>> scrape1YearData() {
        long now = Instant.now().getEpochSecond();
        long fromDate = calculatePastTimestamp(365);  // 1 year ago (approximately 365 days)

        Map<String, List<HistoricalData>> result = new HashMap<>();
        result.put(quote, scrapeAndLog(quote, fromDate, now));
        result.put(quote1, scrapeAndLog(quote1, fromDate, now));

        return result;
    }

    /**
     * Fetches data for a specific date range and logs the result.
     *
     * @param quote    The quote to fetch data for.
     * @param fromDate The starting timestamp of the date range.
     * @param toDate   The ending timestamp of the date range.
     * @return A list of HistoricalData objects fetched.
     */
    private List<HistoricalData> scrapeAndLog(String quote, long fromDate, long toDate) {
        List<HistoricalData> dataList = new ArrayList<>();
        try {
            // Fetch data using the scraper service
            dataList = scraperService.scrapeData(quote, String.valueOf(fromDate), String.valueOf(toDate));

            // Log the number of records fetched
            logger.info("Fetched {} records for '{}' from {} to {}", dataList.size(), quote, fromDate, toDate);
        } catch (IOException e) {
            // Log any errors that occur during data fetching
            logger.error("Error scraping data for '{}' from {} to {}", quote, fromDate, toDate, e);
        }
        return dataList;
    }

    /**
     * Calculates a past timestamp by subtracting the specified number of days from the current time.
     *
     * @param daysAgo The number of days to subtract.
     * @return The calculated timestamp.
     */
    private long calculatePastTimestamp(int daysAgo) {
        return Instant.now().getEpochSecond() - daysAgo * 24L * 60 * 60;
    }
}
