package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scraper")
public class ScraperController {

    @Autowired
    private ScraperScheduler scraperScheduler;

    /**
     * Endpoint to manually trigger scraping for the last 7 days.
     *
     * @return ResponseEntity with a success message.
     */
    @GetMapping("/scrape7DaysData")
    public ResponseEntity<String> scrape7DaysData() {
        scraperScheduler.scrape7DaysData();
        return ResponseEntity.ok("7 days data scraping triggered successfully.");
    }

    /**
     * Endpoint to manually trigger scraping for the last 1 week.
     *
     * @return ResponseEntity with a success message.
     */

    /**
     * Endpoint to manually trigger scraping for the last 1 month.
     *
     * @return ResponseEntity with a success message.
     */
    @GetMapping("/scrape1MonthData")
    public ResponseEntity<String> scrape1MonthData() {
        scraperScheduler.scrape1MonthData();
        return ResponseEntity.ok("1 month data scraping triggered successfully.");
    }

    /**
     * Endpoint to manually trigger scraping for the last 6 months.
     *
     * @return ResponseEntity with a success message.
     */
    @GetMapping("/scrape6MonthsData")
    public ResponseEntity<String> scrape6MonthsData() {
        scraperScheduler.scrape6MonthsData();
        return ResponseEntity.ok("6 months data scraping triggered successfully.");
    }

    /**
     * Endpoint to manually trigger scraping for the last 1 year.
     *
     * @return ResponseEntity with a success message.
     */
    @GetMapping("/scrape1YearData")
    public ResponseEntity<String> scrape1YearData() {
        scraperScheduler.scrape1YearData();
        return ResponseEntity.ok("1 year data scraping triggered successfully.");
    }
}