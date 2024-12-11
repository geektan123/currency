# Task 1  -   Yahoo Finance Scraper

This project demonstrates how to scrape historical financial data from Yahoo Finance and store it in an SQLite in-memory database using Java. The data is retrieved for a specific currency pair (EUR/USD in this example) within a defined date range. The program uses the JSoup library to parse the HTML content and extract the relevant financial data, including open, high, low, close, adjusted close, and volume values.

## Features
- Scrapes historical financial data from Yahoo Finance for a specified currency pair (e.g., EUR/USD).
- Stores the scraped data in an SQLite in-memory database.
- Displays the stored data after scraping.
- Handles potential errors such as invalid numeric data.

## Prerequisites

Before running this project, ensure you have the following:
- **Java 8 or later**.
- **JSoup library** for HTML parsing. You can add it to your project dependencies.



## Usage

1. **Set the currency pair and date range:**

   In the `main` method, you can modify the following variables to scrape data for different currency pairs and date ranges:
   ```java
   String quote = "EURUSD=X";  // Currency pair
   String fromDate = "2023-01-01";  // Start date
   String toDate = "2023-12-31";    // End date
   ```

2. **Run the program:**
   Execute the program to scrape the historical data and store it in the SQLite database.

3. **View the data:**
   After scraping, the program will display the stored data in the console.

## Code Explanation

### 1. **URL Construction:**
   The URL for scraping is constructed using the currency pair and the date range:
   ```java
   String url = "https://finance.yahoo.com/quote/" + quote + "/history/?period1=" + fromDate + "&period2=" + toDate;
   ```

### 2. **Database Connection:**
   The program connects to an SQLite in-memory database using JDBC:
   ```java
   try (Connection conn = DriverManager.getConnection("jdbc:sqlite::memory:")) {
   ```

### 3. **Create Table:**
   A table is created in the SQLite database to store the historical data:
   ```java
   String createTableSQL = "CREATE TABLE IF NOT EXISTS HistoricalData (" 
       + "Date TEXT, "
       + "Open REAL, "
       + "High REAL, "
       + "Low REAL, "
       + "Close REAL, "
       + "AdjClose REAL, "
       + "Volume INTEGER)";
   ```

### 4. **Data Scraping:**
   The program scrapes the historical data using JSoup and parses the relevant fields:
   ```java
   Document doc = Jsoup.connect(url).get();
   Elements rows = doc.select("div.table-container.yf-j5d1ld table tbody tr");
   ```

### 5. **Data Insertion:**
   The scraped data is inserted into the SQLite database:
   ```java
   String insertSQL = "INSERT INTO HistoricalData (Date, Open, High, Low, Close, AdjClose, Volume) "
       + "VALUES (?, ?, ?, ?, ?, ?, ?)";
   ```

### 6. **Error Handling:**
   The program validates and handles invalid numeric data gracefully:
   ```java
   private static double parseDouble(String value) { ... }
   private static int parseInt(String value) { ... }
   ```

### 7. **Display Data:**
   The program retrieves and displays the stored data from the SQLite database:
   ```java
   String query = "SELECT * FROM HistoricalData";
   ```

## Example Output

After running the program, the output will look like:
```
URL: https://finance.yahoo.com/quote/EURUSD=X/history/?period1=2023-01-01&period2=2023-12-31
Data scraped and stored in SQLite database.
Historical Data:
2023-01-01 | 1.100 | 1.120 | 1.090 | 1.110 | 1.110 | 50000
2023-01-02 | 1.112 | 1.125 | 1.100 | 1.115 | 1.115 | 45000
...
```
