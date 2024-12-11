# Scraper API

This project provides a RESTful API for fetching historical financial data using Yahoo Finance as the data source. The API is built with Spring Boot and uses JSoup for web scraping.

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
GET https://currency-production-e62e.up.railway.app/api/scrape?quote=GBPAED=X&fromDate=2020-01-01&toDate=2022-11-30
```

#### Example Response:
```json
[
    {
        "date": "Nov 30, 2022",
        "open": 4.3991,
        "high": 4.417,
        "low": 4.3917,
        "close": 4.3889,
        "adjClose": 4.3889,
        "volume": 0
    },
    {
        "date": "Nov 29, 2022",
        "open": 4.403,
        "high": 4.4303,
        "low": 4.3958,
        "close": 4.392,
        "adjClose": 4.392,
        "volume": 0
    },
    {
        "date": "Nov 28, 2022",
        "open": 4.42,
        "high": 4.4496,
        "low": 4.4197,
        "close": 4.4328,
        "adjClose": 4.4328,
        "volume": 0
    },
    {
        "date": "Nov 25, 2022",
        "open": 4.4441,
        "high": 4.4522,
        "low": 4.4295,
        "close": 4.4483,
        "adjClose": 4.4483,
        "volume": 0
    },
    {
        "date": "Nov 24, 2022",
        "open": 4.4388,
        "high": 4.4603,
        "low": 4.4295,
        "close": 4.4264,
        "adjClose": 4.4264,
        "volume": 0
    },
    {
        "date": "Nov 23, 2022",
        "open": 4.3642,
        "high": 4.4324,
        "low": 4.3612,
        "close": 4.3653,
        "adjClose": 4.3653,
        "volume": 0
    },
    {
        "date": "Nov 22, 2022",
        "open": 4.3523,
        "high": 4.3705,
        "low": 4.3442,
        "close": 4.341,
        "adjClose": 4.341,
        "volume": 0
    },
    {
        "date": "Nov 21, 2022",
        "open": 4.3538,
        "high": 4.3538,
        "low": 4.3275,
        "close": 4.3702,
        "adjClose": 4.3702,
        "volume": 0
    },
    {
        "date": "Nov 18, 2022",
        "open": 4.3662,
        "high": 4.3888,
        "low": 4.3643,
        "close": 4.3471,
        "adjClose": 4.3471,
        "volume": 0
    },
    {
        "date": "Nov 17, 2022",
        "open": 4.3658,
        "high": 4.3909,
        "low": 4.3205,
        "close": 4.3755,
        "adjClose": 4.3755,
        "volume": 0
    },
    {
        "date": "Nov 16, 2022",
        "open": 4.3488,
        "high": 4.3856,
        "low": 4.3461,
        "close": 4.3566,
        "adjClose": 4.3566,
        "volume": 0
    },
    {
        "date": "Nov 15, 2022",
        "open": 4.3136,
        "high": 4.4101,
        "low": 4.3136,
        "close": 4.316,
        "adjClose": 4.316,
        "volume": 0
    },
    {
        "date": "Nov 14, 2022",
        "open": 4.3288,
        "high": 4.3434,
        "low": 4.302,
        "close": 4.3318,
        "adjClose": 4.3318,
        "volume": 0
    },
    {
        "date": "Nov 11, 2022",
        "open": 4.2793,
        "high": 4.3313,
        "low": 4.2793,
        "close": 4.2951,
        "adjClose": 4.2951,
        "volume": 0
    },
    {
        "date": "Nov 10, 2022",
        "open": 4.1814,
        "high": 4.2893,
        "low": 4.1734,
        "close": 4.1712,
        "adjClose": 4.1712,
        "volume": 0
    },
    {
        "date": "Nov 9, 2022",
        "open": 4.2388,
        "high": 4.2473,
        "low": 4.1896,
        "close": 4.2392,
        "adjClose": 4.2392,
        "volume": 0
    },
    {
        "date": "Nov 8, 2022",
        "open": 4.2283,
        "high": 4.2487,
        "low": 4.1986,
        "close": 4.227,
        "adjClose": 4.227,
        "volume": 0
    },
    {
        "date": "Nov 7, 2022",
        "open": 4.1594,
        "high": 4.2148,
        "low": 4.1469,
        "close": 4.1536,
        "adjClose": 4.1536,
        "volume": 0
    },
    {
        "date": "Nov 4, 2022",
        "open": 4.1123,
        "high": 4.1468,
        "low": 4.0996,
        "close": 4.0997,
        "adjClose": 4.0997,
        "volume": 0
    },
    {
        "date": "Nov 3, 2022",
        "open": 4.1915,
        "high": 4.1915,
        "low": 4.098,
        "close": 4.1814,
        "adjClose": 4.1814,
        "volume": 0
    },
    {
        "date": "Nov 2, 2022",
        "open": 4.2253,
        "high": 4.2312,
        "low": 4.2148,
        "close": 4.2157,
        "adjClose": 4.2157,
        "volume": 0
    },
    {
        "date": "Nov 1, 2022",
        "open": 4.2201,
        "high": 4.2473,
        "low": 4.2106,
        "close": 4.2118,
        "adjClose": 4.2118,
        "volume": 0
    }
]
```

## Getting Started

### Prerequisites
- Java 17+
- Maven

### Setup
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd <repository-folder>
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
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

## License
This project is licensed under the MIT License. See the LICENSE file for details.

## Contributions
Contributions are welcome! Feel free to submit issues or pull requests to improve the project.

## Contact
For questions or support, please contact:
- **Developer:** [Your Name]
- **Email:** [your.email@example.com]

