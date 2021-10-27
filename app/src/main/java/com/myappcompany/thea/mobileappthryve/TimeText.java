package com.myappcompany.thea.mobileappthryve;

public class TimeText {
    // example: "1:00 PM"
    private String timeDisplay;

    // example: "13:00"
    private String timeData;

    public TimeText(String timeDisplay, String timeData) {
        this.timeDisplay = timeDisplay;
        this.timeData = timeData;
    }

    public String getTimeDisplay() {
        return timeDisplay;
    }

    public String getTimeData() {
        return timeData;
    }

    @Override
    public String toString() {
        return timeDisplay;
    }
}
