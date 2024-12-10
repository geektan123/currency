package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ScraperController {

    @Autowired
    private ScraperService scraperService;

    // A list of predefined currency pairs and periods
    private static final String[] CURRENCY_PAIRS = {"GBP-INR", "AED-INR"};
    private static final String[] PERIODS = {"1W", "1M", "3M", "6M", "1Y"};

    @GetMapping("/scrape")
    public List<HistoricalData> scrapeData(
            @RequestParam String quote,
            @RequestParam String period) throws IOException {

        // Validate input parameters
        if (!isValidCurrencyPair(quote)) {
            throw new IllegalArgumentException("Invalid currency pair. Use one of: " + String.join(", ", CURRENCY_PAIRS));
        }
        if (!isValidPeriod(period)) {
            throw new IllegalArgumentException("Invalid period. Use one of: " + String.join(", ", PERIODS));
        }

        // Call the ScraperService with the quote and period (e.g., "1W", "1M", "3M", "6M", "1Y")
        return scraperService.scrapeData(quote, period);
    }

    // Validates that the currency pair is valid
    private boolean isValidCurrencyPair(String quote) {
        for (String validPair : CURRENCY_PAIRS) {
            if (validPair.equals(quote)) {
                return true;
            }
        }
        return false;
    }

    // Validates that the period is valid
    private boolean isValidPeriod(String period) {
        for (String validPeriod : PERIODS) {
            if (validPeriod.equals(period)) {
                return true;
            }
        }
        return false;
    }
}
