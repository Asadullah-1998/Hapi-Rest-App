package com.rest.Main.Monitor.HighPressure;

public class HighPressureRecord {


    private String name;
    private String[] pressureValues;
    private String[] pressureDates;

    private String pressureValuesString;
    private String pressureDatesString;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getPressureValues() {
        return pressureValues;
    }

    public void setPressureValues(String[] pressureValues) {
        this.pressureValues = pressureValues;
    }

    public String[] getPressureDates() {
        return pressureDates;
    }

    public void setPressureDates(String[] pressureDates) {
        this.pressureDates = pressureDates;
    }
    public String getPressureValuesString() {
        return pressureValuesString;
    }

    public void setPressureValuesString(String pressureValuesString) {
        this.pressureValuesString = pressureValuesString;
    }

    public String getPressureDatesString() {
        return pressureDatesString;
    }

    public void setPressureDatesString(String pressureDatesString) {
        this.pressureDatesString = pressureDatesString;
    }

}
