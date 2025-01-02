package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/scraper")
public class ScraperController {

    @Autowired
    private ScraperScheduler scraperScheduler;

    /**
     * Fetch data from Firebase based on the specified duration.
     *
     * @param duration The time duration for data retrieval (1W, 1M, 3M, 6M, 1Y).
     * @return ResponseEntity with the fetched data or error message.
     */
    @PostMapping("/scrapeData")
    public ResponseEntity<Object> fetchData(@RequestParam String duration) {
        String rangePath;

        // Map duration to Firebase range path
        switch (duration.toUpperCase()) {
            case "1W":

                rangePath = "7Days";
                break;
            case "1M":

                rangePath = "1Month";
                break;
            case "3M":

                rangePath = "3Month";
                break;
            case "6M":

                rangePath = "6Months";
                break;
            case "1Y":

                rangePath = "1Year";
                break;
            default:
                return ResponseEntity.badRequest().body("Invalid duration parameter. Use one of: 1W, 1M, 3M, 6M, 1Y.");
        }

        // Fetch data from Firebase
        try {
            List<HistoricalData> data = scraperScheduler.fetchDataFromFirebase(rangePath);
            if (data.isEmpty()) {
                return ResponseEntity.ok("No data found for the requested duration: " + duration);
            }
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching data from Firebase: " + e.getMessage());
        }
    }
}
