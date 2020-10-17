package com.rest.Main.Monitor;

public class Statistics {
	
	private int aboveAverageChol;
	
	private int highSystolic;
	
	private int highDiastolic;
	
	private int nonSmokers;
	
	
	public Statistics() {
		setAboveAverageChol(0);
		setHighSystolic(0);
		setHighDiastolic(0);
		setNonSmokers(0);
	}
	
	
	public int getAboveAverageChol() {
		return aboveAverageChol;	
	}
	
	public void setAboveAverageChol(int value) {
		aboveAverageChol = value;
	}
	
	public int getHighSystolic() {
		return highSystolic;	
	}
	
	public void setHighSystolic(int value) {
		highSystolic = value;
	}
	
	public int getHighDiastolic() {
		return highDiastolic;	
	}
	
	public void setHighDiastolic(int value) {
		highDiastolic = value;
	}
	
	public int getNonSmokers() {
		return nonSmokers;
	}
	
	public void setNonSmokers(int value) {
		nonSmokers = value;
	}	
	
	
}
