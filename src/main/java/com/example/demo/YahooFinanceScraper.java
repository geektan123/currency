package com.example.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.sql.*;

public class YahooFinanceScraper {

    public static void main(String[] args) {
        // Define the quote (currency pair), start date, and end date
        String quote = "EURUSD=X";  // EUR to USD, for example
        String fromDate = "2023-01-01";  // Start Date
        String toDate = "2023-12-31";    // End Date

        // Construct the Yahoo Finance URL
        String url = "https://finance.yahoo.com/quote/" + quote + "/history/?period1=" + fromDate + "&period2=" + toDate;
        System.out.println("URL: " + url);  // For debugging or printing the URL

        // Connect to SQLite in-memory database
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite::memory:")) {
            // Create table to store the historical data
            createTable(conn);

            // Scrape the data
            scrapeAndStoreData(url, conn);
            System.out.println("Data scraped and stored in SQLite database.");

            // Optionally, display the data from the database
            displayData(conn);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    // Create the table in SQLite to store the scraped data
    private static void createTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS HistoricalData ("
                + "Date TEXT, "
                + "Open REAL, "
                + "High REAL, "
                + "Low REAL, "
                + "Close REAL, "
                + "AdjClose REAL, "
                + "Volume INTEGER)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        }
    }

    // Scrape the historical data from Yahoo Finance and store it in the SQLite database
    private static void scrapeAndStoreData(String url, Connection conn) throws IOException, SQLException {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements rows = doc.select("div.table-container.yf-j5d1ld table tbody tr");

            for (Element row : rows) {
                Elements columns = row.select("td");
                if (columns.size() == 7) {
                    // Trim and clean data
                    String date = columns.get(0).text().trim();
                    String open = columns.get(1).text().trim();
                    String high = columns.get(2).text().trim();
                    String low = columns.get(3).text().trim();
                    String close = columns.get(4).text().trim();
                    String adjClose = columns.get(5).text().trim();
                    String volume = columns.get(6).text().replace(",", "").trim();

                    // Log raw data for debugging
                    System.out.printf("Raw Parsed: Date=%s, Open=%s, High=%s, Low=%s, Close=%s, AdjClose=%s, Volume=%s%n",
                            date, open, high, low, close, adjClose, volume);

                    // Validate numeric fields
                    try {
                        insertData(conn, date,
                                parseDouble(open),
                                parseDouble(high),
                                parseDouble(low),
                                parseDouble(close),
                                parseDouble(adjClose),
                                parseInt(volume));
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping row due to invalid number format: " + row.text());
                    }
                } else {
                    System.err.println("Unexpected row format. Skipping: " + row.html());
                }
            }
        } catch (IOException e) {
            System.err.println("Error fetching data: " + e.getMessage());
            throw e;
        }
    }

    // Helper methods to safely parse doubles and integers
    private static double parseDouble(String value) {
        try {
            return value.isEmpty() ? 0.0 : Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private static int parseInt(String value) {
        try {
            return value.isEmpty() ? 0 : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Helper method to insert the data into the SQLite database
    private static void insertData(Connection conn, String date, double open, double high, double low, double close,
                                   double adjClose, int volume) throws SQLException {
        String insertSQL = "INSERT INTO HistoricalData (Date, Open, High, Low, Close, AdjClose, Volume) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, date);
            pstmt.setDouble(2, open);
            pstmt.setDouble(3, high);
            pstmt.setDouble(4, low);
            pstmt.setDouble(5, close);
            pstmt.setDouble(6, adjClose);
            pstmt.setInt(7, volume);
            pstmt.executeUpdate();
        }
    }

    // Display data from the SQLite database
    private static void displayData(Connection conn) throws SQLException {
        String query = "SELECT * FROM HistoricalData";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("Historical Data:");
            while (rs.next()) {
                String date = rs.getString("Date");
                double open = rs.getDouble("Open");
                double high = rs.getDouble("High");
                double low = rs.getDouble("Low");
                double close = rs.getDouble("Close");
                double adjClose = rs.getDouble("AdjClose");
                int volume = rs.getInt("Volume");
                System.out.printf("%s | %.2f | %.2f | %.2f | %.2f | %.2f | %d%n",
                        date, open, high, low, close, adjClose, volume);
            }
        }
    }
}
