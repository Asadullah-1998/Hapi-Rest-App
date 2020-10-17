package com.rest.Main.service;

import com.rest.Main.domain.Patient;

import org.springframework.web.client.RestTemplate;



public abstract class PatientService {
	
	private String code;
	
	private String condition;
	
	protected final String root_url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/";

	protected static final String patient_url = "Observation?patient=";

    protected static final String get_variable = "&code=";

    protected RestTemplate restTemplate = new RestTemplate();
	
	
    public abstract void addToPatient(Patient patient);
    
    
    public void setCode(String value) {
    	this.code = value;
    }
    
    public String getCode() {
    	return this.code;
    }
    
    
    public void setCondition(String value) {
    	this.condition = value;
    }
    
    public String getCondition() {
    	return this.condition;
    }

}
