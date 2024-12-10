package com.example.demo;

import java.util.Objects;

public class HistoricalData {
    private String date;
    private double open;
    private double high;
    private double low;
    private double close;
    private double adjClose;
    private int volume;

    // Default constructor
    public HistoricalData() {}

    // Constructor with all fields for easier object creation
    public HistoricalData(String date, double open, double high, double low, double close, double adjClose, int volume) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.adjClose = adjClose;
        this.volume = volume;
    }

    // Getters and setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getAdjClose() {
        return adjClose;
    }

    public void setAdjClose(double adjClose) {
        this.adjClose = adjClose;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    // Override toString() for better logging and debugging
    @Override
    public String toString() {
        return "HistoricalData{" +
                "date='" + date + '\'' +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", adjClose=" + adjClose +
                ", volume=" + volume +
                '}';
    }

    // Override equals() and hashCode() to compare instances
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoricalData that = (HistoricalData) o;
        return Double.compare(that.open, open) == 0 &&
                Double.compare(that.high, high) == 0 &&
                Double.compare(that.low, low) == 0 &&
                Double.compare(that.close, close) == 0 &&
                Double.compare(that.adjClose, adjClose) == 0 &&
                volume == that.volume &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, open, high, low, close, adjClose, volume);
    }

    // Optional: Validate that the data is reasonable (e.g., non-negative values for price and volume)
    public boolean isValid() {
        return open >= 0 && high >= 0 && low >= 0 && close >= 0 && adjClose >= 0 && volume >= 0;
    }
}
