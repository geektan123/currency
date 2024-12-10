package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class ScraperController {

    @Autowired
    private ScraperService scraperService;

    /**
     * Endpoint to trigger scraping manually for any currency pair.
     *
     * @param quote    The currency pair, e.g., "GBP-INR".
     * @param fromDate The start date as a Unix timestamp.
     * @param toDate   The end date as a Unix timestamp.
     * @return List of HistoricalData records.
     */
    @GetMapping("/scrape")
    public List<HistoricalData> scrapeData(
            @RequestParam String quote,
            @RequestParam String fromDate,
            @RequestParam String toDate) {
        try {
            return scraperService.scrapeData(quote, fromDate, toDate);
        } catch (IOException e) {
            throw new RuntimeException("Error fetching data for " + quote + " from " + fromDate + " to " + toDate, e);
        }
    }

    /**
     * Helper method to scrape data for a given currency pair and a time range.
     *
     * @param quote    The currency pair, e.g., "GBP-INR".
     * @param timeRange The time range in days (e.g., 7 for last week, 30 for last month).
     * @return List of HistoricalData records.
     */
    private List<HistoricalData> scrapeForTimeRange(String quote, long timeRangeInDays) throws IOException {
        long now = System.currentTimeMillis() / 1000;
        long startTime = now - timeRangeInDays * 24 * 60 * 60;
        return scraperService.scrapeData(quote, String.valueOf(startTime), String.valueOf(now));
    }

    /**
     * Endpoint to scrape data for the last 1 week.
     *
     * @param quote The currency pair, e.g., "GBP-INR".
     * @return List of HistoricalData records.
     */
    @GetMapping("/scrape/1W")
    public List<HistoricalData> scrape1WData(@RequestParam String quote) {
        try {
            return scrapeForTimeRange(quote, 7); // 7 days for 1 week
        } catch (IOException e) {
            throw new RuntimeException("Error fetching data for " + quote + " for the last week", e);
        }
    }

    /**
     * Endpoint to scrape data for the last 1 month.
     *
     * @param quote The currency pair, e.g., "GBP-INR".
     * @return List of HistoricalData records.
     */
    @GetMapping("/scrape/1M")
    public List<HistoricalData> scrape1MData(@RequestParam String quote) {
        try {
            return scrapeForTimeRange(quote, 30); // 30 days for 1 month
        } catch (IOException e) {
            throw new RuntimeException("Error fetching data for " + quote + " for the last 1 month", e);
        }
    }

    /**
     * Endpoint to scrape data for the last 3 months.
     *
     * @param quote The currency pair, e.g., "GBP-INR".
     * @return List of HistoricalData records.
     */
    @GetMapping("/scrape/3M")
    public List<HistoricalData> scrape3MData(@RequestParam String quote) {
        try {
            return scrapeForTimeRange(quote, 90); // 90 days for 3 months
        } catch (IOException e) {
            throw new RuntimeException("Error fetching data for " + quote + " for the last 3 months", e);
        }
    }

    /**
     * Endpoint to scrape data for the last 6 months.
     *
     * @param quote The currency pair, e.g., "GBP-INR".
     * @return List of HistoricalData records.
     */
    @GetMapping("/scrape/6M")
    public List<HistoricalData> scrape6MData(@RequestParam String quote) {
        try {
            return scrapeForTimeRange(quote, 180); // 180 days for 6 months
        } catch (IOException e) {
            throw new RuntimeException("Error fetching data for " + quote + " for the last 6 months", e);
        }
    }

    /**
     * Endpoint to scrape data for the last 1 year.
     *
     * @param quote The currency pair, e.g., "GBP-INR".
     * @return List of HistoricalData records.
     */
    @GetMapping("/scrape/1Y")
    public List<HistoricalData> scrape1YData(@RequestParam String quote) {
        try {
            return scrapeForTimeRange(quote, 365); // 365 days for 1 year
        } catch (IOException e) {
            throw new RuntimeException("Error fetching data for " + quote + " for the last 1 year", e);
        }
    }
}
