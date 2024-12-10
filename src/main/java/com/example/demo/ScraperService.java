package com.example.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableScheduling
public class ScraperService {

    // Assuming HistoricalDataRepository is used to store data (if needed)
    @Autowired
    private HistoricalData dataRepository;

    // Scrapes data for specific currency pairs and periods every hour
    @Scheduled(cron = "0 0 * * * *") // Runs every hour
    public void triggerPeriodicScraping() throws IOException {
        String[] currencyPairs = {"GBP-INR", "AED-INR"};
        String[] periods = {"1W", "1M", "3M", "6M", "1Y"};

        // Loop through currency pairs and periods, and scrape data
        for (String pair : currencyPairs) {
            for (String period : periods) {
                // Scrape data for each pair and period
                List<HistoricalData> data = scrapeData(pair, period);

                // Save the data to an in-memory store or a database
                // For now, just print the data to the console
                System.out.println("Scraped data for " + pair + " with period " + period);

                // Optionally save to a repository
                // dataRepository.saveAll(data); // Uncomment if you have a repository
            }
        }
    }

    // Scrapes data from Yahoo Finance for a given currency pair and period
    public List<HistoricalData> scrapeData(String quote, String period) throws IOException {
        // Calculate date range
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = calculateStartDate(period, currentDate);

        // Convert LocalDate to Unix time (seconds since the Unix epoch)
        long fromUnixTime = startDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC);
        long toUnixTime = currentDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC);

        // Construct the URL with Unix timestamps
        String url = "https://finance.yahoo.com/quote/" + quote + "/history?p=" + quote
                + "&period1=" + fromUnixTime + "&period2=" + toUnixTime;

        System.out.println("Generated URL: " + url);
        Document doc = Jsoup.connect(url).get();
        Elements rows = doc.select("div.table-container.yf-j5d1ld table tbody tr");

        List<HistoricalData> dataList = new ArrayList<>();
        for (Element row : rows) {
            Elements columns = row.select("td");
            if (columns.size() == 7) {
                try {
                    HistoricalData data = new HistoricalData();
                    data.setDate(columns.get(0).text().trim());
                    data.setOpen(parseDouble(columns.get(1).text().trim()));
                    data.setHigh(parseDouble(columns.get(2).text().trim()));
                    data.setLow(parseDouble(columns.get(3).text().trim()));
                    data.setClose(parseDouble(columns.get(4).text().trim()));
                    data.setAdjClose(parseDouble(columns.get(5).text().trim()));
                    data.setVolume(parseInt(columns.get(6).text().replace(",", "").trim()));

                    dataList.add(data);
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return dataList;
    }

    // Calculate start date based on the selected period
    private LocalDate calculateStartDate(String period, LocalDate currentDate) {
        switch (period) {
            case "1W":
                return currentDate.minus(1, ChronoUnit.WEEKS);
            case "1M":
                return currentDate.minus(1, ChronoUnit.MONTHS);
            case "3M":
                return currentDate.minus(3, ChronoUnit.MONTHS);
            case "6M":
                return currentDate.minus(6, ChronoUnit.MONTHS);
            case "1Y":
                return currentDate.minus(1, ChronoUnit.YEARS);
            default:
                throw new IllegalArgumentException("Invalid period. Use one of: 1W, 1M, 3M, 6M, 1Y.");
        }
    }

    // Helper method to parse doubles safely
    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    // Helper method to parse integers safely
    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
