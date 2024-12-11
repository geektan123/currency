# Task 1
This project provides a RESTful API for fetching historical financial data using Yahoo Finance as the data source. The API is built with Spring Boot and uses JSoup for web scraping.
##### `Sub-task-1`
### GET `/api/scrape`
This endpoint takes in a few query parameters as follows:
from: This will be the from currency code (e.g., GBP, AED).
to: This will be the to currency code (e.g., INR).
period: This will be the timeframe for which you want to query data (e.g., 1M, 3M - 1M indicates you are querying exchange data from the last one month).

## Features
- Fetch historical financial data for a given stock symbol or currency pair.
- Specify a date range using `fromDate` and `toDate` parameters.
- Dates are automatically converted to Unix timestamps for accurate queries.

## Endpoints

### `/api/scrape`
**Method:** GET  
Fetch historical data for the specified stock symbol or currency pair.

#### Query Parameters:
- `quote` (String): The stock symbol or currency pair (e.g., `AAPL`, `GBPAED=X`).
- `fromDate` (String): The start date in `yyyy-MM-dd` format.
- `toDate` (String): The end date in `yyyy-MM-dd` format.

#### Example Request:
```http
GET https://skillful-determination-production.up.railway.app/api/scrape?quote=EURUSD=X&fromDate=2023-11-17&toDate=2023-11-22
```

#### Example Response:
```
[
    {
        "date": "Nov 22, 2023",
        "open": 1.0918,
        "high": 1.0923,
        "low": 1.0855,
        "close": 1.0918,
        "adjClose": 1.0918,
        "volume": 0
    },
    {
        "date": "Nov 21, 2023",
        "open": 1.0946,
        "high": 1.0967,
        "low": 1.0923,
        "close": 1.0946,
        "adjClose": 1.0946,
        "volume": 0
    },
    {
        "date": "Nov 20, 2023",
        "open": 1.0907,
        "high": 1.0945,
        "low": 1.0898,
        "close": 1.0907,
        "adjClose": 1.0907,
        "volume": 0
    },
    {
        "date": "Nov 17, 2023",
        "open": 1.0854,
        "high": 1.0892,
        "low": 1.0825,
        "close": 1.0854,
        "adjClose": 1.0854,
        "volume": 0
    }
]
```

## Getting Started

### Prerequisites
- Java 17+
- Spring boot

### Setup
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd <repository-folder>
   ```



4. Access the application locally:
   ```
   http://localhost:8080
   ```

## Deployment
The application is deployed on [Railway](https://railway.app) and accessible via:
```plaintext
https://currency-production-e62e.up.railway.app
```

## Testing

### Using a Web Browser
Access the `/api/scrape` endpoint directly in your browser:
```plaintext
https://currency-production-e62e.up.railway.app/api/scrape?quote=GBPAED=X&fromDate=2020-01-01&toDate=2022-11-30
```

### Using Postman
1. Open Postman.
2. Create a new **GET request**.
3. Set the URL to:
   ```
   https://currency-production-e62e.up.railway.app/api/scrape
   ```
4. Add Query Parameters:
   - `quote`: `GBPAED=X`
   - `fromDate`: `2020-01-01`
   - `toDate`: `2022-11-30`
5. Send the request.

### Using Curl
Run the following command:
```bash
curl -X GET "https://currency-production-e62e.up.railway.app/api/scrape?quote=GBPAED=X&fromDate=2020-01-01&toDate=2022-11-30"
```

## Code Overview

### ScraperController
Defines the `/api/scrape` endpoint and delegates processing to the `ScraperService`.

### ScraperService
Handles the business logic:
- Constructs the Yahoo Finance URL with Unix timestamps.
- Scrapes data using JSoup.
- Parses and returns the data as a list of `HistoricalData` objects.

### HistoricalData
A model class representing historical financial data with fields like:
- `date`
- `open`
- `high`
- `low`
- `close`
- `adjClose`
- `volume`


## Contributions
Contributions are welcome! Feel free to submit issues or pull requests to improve the project.

## Contact
For questions or support, please contact:
- **Developer:** [Tanay Jha]
- **Email:** [tanayjha416@gmail.com]

