package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/scraper")
public class ScraperController {

    @Autowired
    private ScraperScheduler scraperScheduler;

    /**
     * Endpoint to get the last 7 days of historical data for the quotes.
     *
     * @return A map containing the historical data for both quotes.
     */
    @GetMapping("/7days")
    public Map<String, List<HistoricalData>> get7DaysData() {
        return scraperScheduler.scrape7DaysData();
    }

    /**
     * Endpoint to get the last 1 week of historical data for the quotes.
     *
     * @return A map containing the historical data for both quotes.
     */
    @GetMapping("/1week")
    public Map<String, List<HistoricalData>> get1WeekData() {
        return scraperScheduler.scrape1WeekData();
    }

    /**
     * Endpoint to get the last 1 month of historical data for the quotes.
     *
     * @return A map containing the historical data for both quotes.
     */
    @GetMapping("/1month")
    public Map<String, List<HistoricalData>> get1MonthData() {
        return scraperScheduler.scrape1MonthData();
    }

    /**
     * Endpoint to get the last 6 months of historical data for the quotes.
     *
     * @return A map containing the historical data for both quotes.
     */
    @GetMapping("/6months")
    public Map<String, List<HistoricalData>> get6MonthsData() {
        return scraperScheduler.scrape6MonthsData();
    }

    /**
     * Endpoint to get the last 1 year of historical data for the quotes.
     *
     * @return A map containing the historical data for both quotes.
     */
    @GetMapping("/1year")
    public Map<String, List<HistoricalData>> get1YearData() {
        return scraperScheduler.scrape1YearData();
    }
}
