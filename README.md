

# Currency Scraping & Data Syncing

This repository contains a script designed to scrape currency data from Yahoo Finance and periodically sync it with an in-memory database via a hosted API. The scraping function is triggered at regular intervals for the specified currency codes and time periods.

## Overview

The goal is to create a system that periodically scrapes data for the following currency codes from Yahoo Finance:

- **GBPINR**
- **AEDINR**

The script will trigger scraping requests for these currencies in the following time intervals:

- 1 Week (`1W`)
- 1 Month (`1M`)
- 3 Months (`3M`)
- 6 Months (`6M`)
- 1 Year (`1Y`)

The data is scraped via the following API endpoint:

#### Example Request:
```http
GET https://currency-production-97a5.up.railway.app/scrape/1W?quote=GBPINR
```

Where:
- `{period}` can be one of `1W`, `1M`, `3M`, `6M`, or `1Y`.
- `{currency_code}` can be `GBPINR` or `AEDINR`.

This project is built using Spring Boot in Java.

## Example Data

Here is an example of the data that can be returned by the API for the `GBPINR` currency:

```json
[
    {
        "date": "Dec 11, 2024",
        "open": 108.416,
        "high": 108.4411,
        "low": 107.8198,
        "close": 108.0552,
        "adjClose": 108.0552,
        "volume": 0
    },
    {
        "date": "Dec 10, 2024",
        "open": 108.1683,
        "high": 108.3214,
        "low": 107.987,
        "close": 108.1502,
        "adjClose": 108.1502,
        "volume": 0
    },
    {
        "date": "Dec 9, 2024",
        "open": 107.8198,
        "high": 108.5956,
        "low": 107.7455,
        "close": 107.8198,
        "adjClose": 107.8198,
        "volume": 0
    },
    {
        "date": "Dec 6, 2024",
        "open": 108.0502,
        "high": 108.3915,
        "low": 107.6357,
        "close": 108.056,
        "adjClose": 108.056,
        "volume": 0
    },
    {
        "date": "Dec 5, 2024",
        "open": 107.5876,
        "high": 108.1226,
        "low": 107.5231,
        "close": 107.5856,
        "adjClose": 107.5856,
        "volume": 0
    },
    {
        "date": "Dec 4, 2024",
        "open": 107.317,
        "high": 107.725,
        "low": 107.038,
        "close": 107.314,
        "adjClose": 107.314,
        "volume": 0
    }
]
```

## Requirements

1. **Java 11 or higher** – The script is written in Java and requires at least version 11 to run.
2. **Cron Job Support** – The script needs to be scheduled using a CRON job for periodic execution.
3. **In-memory Database** – The scraped data should be stored in an in-memory database for fast access and syncing.

## Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/yourusername/currency-scraping-sync.git
cd currency-scraping-sync
```



### 3. Configure the Cron Job

To trigger the scraping function periodically, you need to set up a CRON job. Here’s an example of how to configure it for the desired periods. The script will update the data every midnight.


#### Example CRON jobs:

- For 1 Week (`1W`):
  ```bash
  0 0 * * 0 java -jar currency-scraping-sync.jar 1W GBPINR
  ```
- For 1 Month (`1M`):
  ```bash
  0 0 1 * * java -jar currency-scraping-sync.jar 1M GBPINR
  ```
- For 3 Months (`3M`):
  ```bash
  0 0 1 */3 * java -jar currency-scraping-sync.jar 3M GBPINR
  ```
- For 6 Months (`6M`):
  ```bash
  0 0 1 */6 * java -jar currency-scraping-sync.jar 6M GBPINR
  ```
- For 1 Year (`1Y`):
  ```bash
  0 0 1 1 * java -jar currency-scraping-sync.jar 1Y GBPINR
  ```

**Note:** The CRON job setup mentioned here assumes that the environment where this is deployed has a working CRON scheduler. I have not used a paid server for the CRON jobs, but they will work smoothly when set up locally or on a personal server with proper CRON support.

### 4. API Integration

The scraping function is triggered using an HTTP request to the hosted API:

```bash
https://currency-production-97a5.up.railway.app/scrape/{period}?quote={currency_code}
```

Ensure that the API is running and accessible to the cron jobs for smooth execution.

## Example Usage

To manually trigger a scraping request, use the following:

```bash
java -jar currency-scraping-sync.jar 1W GBPINR
```

This will scrape the data for `GBPINR` for the past week.

## Scheduled Execution

Once the cron jobs are set up, the scraping will automatically occur at the configured intervals. The data will be stored in the in-memory database and can be accessed via the API.

---

This version includes the note about CRON jobs not being set up on a paid server but working smoothly locally.
