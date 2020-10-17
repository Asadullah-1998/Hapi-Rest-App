package com.rest.Main.domain;


public class Patient {
    private String id;
    private String name;
    private String surname;

    private Float cholesterol;
    private String cholesterolDate;

    private Float diastolicPressure;
    private String diastolicDate;

    private Float systolicPressure;
    private String systolicDate;

    private String systolicPressureObservations;
    private String systolicPressureObservationDates;

    private String birthDate;
    private String gender;
    private String city;
    private String state;
    private String country;
    private String address;

   
    private boolean cholesterolOn = true;
    private boolean pressureOn = true;

    private boolean isHighest = false;
    
    private String smoker;




    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setHighest() {
        this.isHighest = true;
    }
    public String getSurname() {
        return surname;
    }
    public Boolean getisHighest() {
        return isHighest;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Float getCholesterol() {
        return cholesterol;
    }

    public Float getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(Float systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public String getSystolicDate() {
        return systolicDate;
    }

    public void setSystolicDate(String systolicDate) {
        this.systolicDate = systolicDate;
    }
    public void setCholesterol(Float cholesterol) {

        if(cholesterol == null){
            this.cholesterol = (float) 0;
        } else {
            this.cholesterol = cholesterol;
        }
    }

    public String getCholesterolDate() {
        return cholesterolDate;
    }

    public void setCholesterolDate(String cholesterolDate) {
        this.cholesterolDate = cholesterolDate;
    }


    public String getCity(){return this.city;}

    public void setCity(String city){this.city = city;}

    public String getCountry(){return this.country;}

    public void setCountry(String country){this.country = country;}

    public String getState(){return this.state;}

    public void setState(String state){this.state = state;}

    public String getBirthDate(){return this.birthDate;}

    public void setBirthDate(String birthDate){this.birthDate = birthDate;}

    public String getGender(){return this.gender;}

    public void setGender(String gender){this.gender = gender;}

    public String getAddress(){return this.address;}

    public void setAddress(String address) {this.address = address;}

    public Float getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(Float diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public String getDiastolicDate() {
        return diastolicDate;
    }

    public void setDiastolicDate(String diastolicDate) {
        this.diastolicDate = diastolicDate;
    }

    public String getSystolicPressureObservations() {
        return systolicPressureObservations;
    }

    public void setSystolicPressureObservations(String systolicPressureObservations) {
        this.systolicPressureObservations = systolicPressureObservations;
    }

    public String getSystolicPressureObservationDates() {
        return systolicPressureObservationDates;
    }

    public void setSystolicPressureObservationDates(String systolicPressureObservationDates) {
        this.systolicPressureObservationDates = systolicPressureObservationDates;
    }

    public boolean isHighest() {
        return isHighest;
    }

    public void setHighest(boolean highest) {
        isHighest = highest;
    }



    public boolean isCholesterolOn() {
        return cholesterolOn;
    }

    public void setCholesterolOn(boolean cholesterolOn) {
        this.cholesterolOn = cholesterolOn;
    }

    public boolean isPressureOn() {
        return pressureOn;
    }

    public void setPressureOn(boolean pressureOn) {
        this.pressureOn = pressureOn;
    }


    
    public String getSmoker() {
    	return smoker;
    }
    
    public void setSmoker(String value) {
    	this.smoker = value;
    }
    
    

}



