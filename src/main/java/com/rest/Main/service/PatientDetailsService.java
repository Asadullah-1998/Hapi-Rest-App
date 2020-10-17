package com.rest.Main.service;

import com.rest.Main.domain.Patient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class PatientDetailsService extends PatientService{

	
	
    public void addToPatient(Patient patient) {
    	
	    
	    
    	try {    
    		
    	    String patientVariables = restTemplate.getForObject(root_url+patient.getId(),String.class);

	    	
	    	JSONObject patientObject = new JSONObject(patientVariables);
	
	        String patientGender = patientObject.getString("gender");
	
	        String patientBirthDate = patientObject.getString("birthDate");
	
	        JSONArray patientAddressArray = patientObject.getJSONArray("address");
	
	
	        String patientLine = null;
	        String patientCountry = null;
	        String patientCity = null;
	        String patientState = null;
	
	        for (int i = 0; i < patientAddressArray.length(); i++){
	            try {
	                patientLine = patientAddressArray.getJSONObject(i).getString("line");
	                patientLine = patientLine.replaceAll("[\\[\\]\"]", "");
	                patientCountry = patientAddressArray.getJSONObject(i).getString("country");
	                patientCity = patientAddressArray.getJSONObject(i).getString("city");
	                patientState = patientAddressArray.getJSONObject(i).getString("state");
	            } catch (Exception JSONException) {
	                JSONException.printStackTrace();
	            }
	
	        }
	
	        patient.setAddress(patientLine);
	        patient.setGender(patientGender);
	        patient.setCountry(patientCountry);
	        patient.setState(patientState);
	        patient.setCity(patientCity);
	        patient.setBirthDate(patientBirthDate);
	    } catch (Exception ignored) {
        	
        }
	        
	        
    }
    
	
	
	

}
