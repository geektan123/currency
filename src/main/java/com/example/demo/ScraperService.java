package com.example.demo;

import com.example.demo.HistoricalData;
import com.example.demo.HistoricalData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScraperService {

    public List<HistoricalData> scrapeData(String quote, String fromDate, String toDate) throws IOException {
        String url = "https://finance.yahoo.com/quote/" + quote + "/history/?period1=" + fromDate + "&period2=" + toDate;
        Document doc = Jsoup.connect(url).get();
        Elements rows = doc.select("div.table-container.yf-j5d1ld table tbody tr");
         System.out.println(url);
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

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
