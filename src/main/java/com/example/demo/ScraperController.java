package com.example.demo;


import com.example.demo.HistoricalData;
import com.example.demo.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ScraperController {

    @Autowired
    private ScraperService scraperService;

    @GetMapping("/scrape")
    public List<HistoricalData> scrapeData(
            @RequestParam String quote,
            @RequestParam String fromDate,
            @RequestParam String toDate) throws IOException {
        return scraperService.scrapeData(quote, fromDate, toDate);
    }
}
