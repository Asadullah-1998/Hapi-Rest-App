package com.rest.Main.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.rest.Main.domain.Patient;

@Service
public class PatientSmokerService extends PatientService{
	

	
	public PatientSmokerService() {
		
		setCode("72166-2");
		setCondition("&_sort=-date&_count=1");
		
	}
	
	
    @Override
    public void addToPatient(Patient patient) {
    	       	
			        
	    try {
	    	
	    	String url = root_url+ patient_url + patient.getId() + get_variable + getCode() + getCondition();
			String patientVariables = restTemplate.getForObject(url ,String.class);
	        	
	        JSONObject patientObject = new JSONObject(patientVariables);
	        JSONArray patientEntry = patientObject.getJSONArray("entry");
	        	
	        for (int j = 0; j < patientEntry.length(); j++ ){
	        	JSONObject patientValues = patientEntry.getJSONObject(j);
	
	            JSONObject patientResource = (JSONObject) patientValues.get("resource");
	
	            try {
	            	JSONObject patientValueQuantity = (JSONObject) patientResource.get("valueCodeableConcept");
	
	                String JsonSmoker = patientValueQuantity.getString("text");
	
	                    //Float cholesterolLevel = Float.parseFloat(JsonCholesterol);
	
	                    //String time = patientResource.getString("effectiveDateTime");
	
	                patient.setSmoker(JsonSmoker);
	                    //patient.setCholesterolDate(time);
	
	            } catch (Exception ignored){}
	        }
	    } catch (Exception ignored){}
	        	
	}
}
