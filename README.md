# Task 2
##### `Sub-task-2`
Apologies for the confusion earlier. Let's create a **complete and detailed README** with all the necessary information, including Firebase integration and other setup details.

---

# Currency Exchange Scraper

This project is a Spring Boot application that allows users to scrape currency exchange data (or other services, depending on the configuration) via an API. The application uses a cron job to schedule periodic scraping, but the cron job server is a paid service. However, you can set up the scraper locally and manage the periodic scraping process by configuring cron jobs on your machine.

Additionally, the application integrates with **Firebase** to store and manage configuration files (like `service.json`) and scraped data.

## Prerequisites

Before setting up and running the application, ensure that you have the following installed:

- **Java 11 or above** installed
- **Gradle** (or use Gradle Wrapper)
- **Firebase account** (for configuration management)
- **Cron job service** (to schedule scraping if running locally)

You can check the installation of Java and Gradle by running:

```bash
java -version
gradle -v
```

---

## Installation

### 1. Clone the Repository

Clone the repository to your local machine:

```bash
git clone <repository-url>
cd <project-directory>
```

### 2. Install Dependencies

To install the necessary dependencies, run the following command:

```bash
./gradlew build
```

This will download and set up all the required dependencies for the project.

### 3. Firebase Setup

The scraper application relies on Firebase for configuration and data storage. You will need to set up Firebase and integrate it with the project.

#### Steps for Firebase Setup:

1. **Create a Firebase project** by going to the Firebase Console: [https://console.firebase.google.com/](https://console.firebase.google.com/).
   
2. **Create a Firebase Realtime Database**:
    - In your Firebase Console, navigate to **Database** and select **Realtime Database**.
    - Set the rules as required (e.g., for read/write access).

3. **Create a Firebase Service Account**:
    - In the Firebase Console, go to **Project Settings > Service Accounts**.
    - Click **Generate New Private Key** and download the JSON key.
    - Rename the downloaded JSON key to `firebase-service-account.json`.

4. **Add Firebase SDK to the project**:
    - Add your Firebase service account file (`firebase-service-account.json`) to the `src/main/resources` directory of your project.

    ```bash
    <project-directory>
    ├── src
    │   └── main
    │       └── resources
    │           └── firebase-service-account.json
    ```

### 4. Firebase Configuration in Application

In your `application.properties` or `application.yml`, configure Firebase as follows:

```properties
# Firebase Configuration
firebase.config.path=src/main/resources/firebase-service-account.json
```

This configuration allows the app to connect with Firebase for managing the `service.json` file and other data.

### 5. Create and Configure the `service.json` File

The `service.json` file contains important configuration details, such as API URLs and keys. The scraper requires this file to fetch data and perform its tasks.

#### Example `service.json` File:

```json
{
  "apiUrl": "https://example.com/api/v1",
  "apiKey": "YOUR_API_KEY",
  "scraperSettings": {
    "currencyPairs": ["USD/EUR", "GBP/USD", "EUR/JPY"],
    "timeFrame": "1W"
  }
}
```

**Key Parameters**:
- **apiUrl**: The base URL for the external API to fetch data.
- **apiKey**: The API key for authentication (if required).
- **scraperSettings**: Scraper settings, such as which currency pairs to scrape and the time frame.

### 6. Place the `service.json` in Firebase

Upload the `service.json` file to Firebase Realtime Database so that the scraper can fetch it dynamically.

1. Go to the Firebase Console and navigate to your **Realtime Database**.
2. Upload the `service.json` file to the database, under the path you defined in your code.

For example, store it as:

```
/configs/service.json
```

You can read this file dynamically in your Spring Boot application.

---

## Running the Application Locally

### 1. Build and Run the Application

To build and run the application locally, use the following command:

```bash
./gradlew bootRun
```

This will start the Spring Boot application on **port 8080** by default.


Here’s how you can structure the information you provided in a README file:

---

# API Endpoints

## 2. Fetch Historical Data from Firebase
**POST** `/api/scraper/scrapeData`

This endpoint fetches historical data for a specific currency pair from Firebase based on the specified time duration.

### Request Parameters:
- **duration** (string): The time duration for data retrieval. Must be one of the following:
  - `1W`: Last 7 days.
  - `1M`: Last 1 month.
  - `3M`: Last 3 months.
  - `6M`: Last 6 months.
  - `1Y`: Last 1 year.

### Example Request:
```bash
POST http://localhost:8080/api/scraper/scrapeData?duration=1M
```

### Example Response (for AEDINR - 1 Month):
```json
{
  "historicalData": {
    "AEDINR": {
      "1Month": [
        {
          "adjClose": 23.295,
          "close": 23.295,
          "date": "Jan 2, 2025",
          "high": 23.306,
          "low": 23.274,
          "open": 23.29,
          "volume": 0
        },
        {
          "adjClose": 23.3496,
          "close": 23.3496,
          "date": "Dec 31, 2024",
          "high": 23.327,
          "low": 23.2829,
          "open": 23.2829,
          "volume": 0
        },
        {
          "adjClose": 23.2403,
          "close": 23.2403,
          "date": "Dec 30, 2024",
          "high": 23.2963,
          "low": 23.2427,
          "open": 23.2427,
          "volume": 0
        }
        ...
      ]
    }
  }
}
```

### Success Response:
- **Status**: 200 OK
- **Body**: A JSON object containing the historical data for the specified duration.

### Error Response:
- **Status**: 400 Bad Request
- **Body**: Error message indicating that the duration parameter is invalid.
```json
{
  "message": "Invalid duration parameter. Use one of: 1W, 1M, 3M, 6M, 1Y."
}
```

---

# How to Run the Application

## 1. Clone the repository
```bash
git clone https://github.com/your-username/your-repository.git
cd your-repository
```

## 2. Install Dependencies
Ensure you have **Java 11** or higher installed on your system. The project uses Spring Boot and does not rely on dependency management tools like Gradle or Maven.

## 3. Run the Application
You can run the Spring Boot application directly by navigating to the project directory and running:

```bash
java -jar target/your-app.jar
```

## 4. Firebase Configuration
Ensure that you have a Firebase project set up. Configure the Firebase Admin SDK key in the `application.properties` or `application.yml` file, or initialize it programmatically in your project.

## 5. Testing the API
You can use **Postman** or any API testing tool to test the `/api/scraper/scrapeData` endpoint.

---

This structure provides clarity and ensures easy navigation for developers using the API. Let me know if you need further refinements!
### 3. Scheduling the Scraper with Cron Jobs

The cron job service for periodic scraping is **paid** and not hosted here. However, you can set up the scraper to run periodically by using a **local cron job** or a task scheduler on your machine.

To set up a cron job on a Linux machine, you can use:

```bash
0 * * * * curl http://localhost:8080/api/scraper/scrapeData?duration=1W
```

This cron job will run the scraper every hour. Adjust the timing according to your needs.

---

## How It Works

### Scraper API

The scraper fetches data from external APIs, such as currency exchange data or other services, depending on the configuration in `service.json`. The `scrapeData` endpoint is used to trigger the scraping process for a specified duration.The @Scheduler at every mid night scrapes the data and stores it in firebase and then call hitting the api it fetches the data.

### Firebase Integration

Firebase is used for storing and managing the `service.json` configuration file. The application reads this file from Firebase dynamically during each scrape. This ensures that configuration changes can be made without requiring code updates or redeployments.

The application reads the `service.json` configuration file from Firebase Realtime Database, ensuring the latest configuration is always used.

---

## Troubleshooting

- **Issue: Application fails to start due to missing `firebase-service-account.json`**
  - Solution: Ensure that the `firebase-service-account.json` file is placed in the `src/main/resources` directory, and the correct path is set in the `application.properties` file.

- **Issue: Unable to fetch configuration from Firebase**
  - Solution: Ensure that your Firebase service account has the correct permissions to read and write data to the Realtime Database.

- **Issue: Cron job not working as expected**
  - Solution: Check your cron job configuration. Ensure that the URL is correct and the server is accessible on the specified port.

---

## Contributing

If you would like to contribute to this project, please fork the repository and create a pull request. Ensure that your code follows the existing coding conventions, and write tests for any new functionality.

---


---

This is the complete and updated README file with all necessary steps for setup, including Firebase configuration, local setup, cron job scheduling, and API usage.

