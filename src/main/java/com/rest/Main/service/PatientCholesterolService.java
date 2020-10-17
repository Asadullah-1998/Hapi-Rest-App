package com.rest.Main.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.rest.Main.domain.Patient;

@Service
public class PatientCholesterolService extends PatientService {

    
    
    public PatientCholesterolService() {
    	
    	setCode("2093-3");
    	setCondition("&_sort=-date&_count=1");
    	
    }
	
    @Override
    public void addToPatient(Patient patient) {
    	
         try {
        	 
             String patientVariables = restTemplate.getForObject(root_url+ patient_url +patient.getId() + get_variable + getCode() + getCondition(),String.class);

             JSONObject patientObject = new JSONObject(patientVariables);
             JSONArray patientEntry = patientObject.getJSONArray("entry");

             for (int j = 0; j < patientEntry.length(); j++ ){
                 JSONObject patientValues = patientEntry.getJSONObject(j);

                 JSONObject patientResource = (JSONObject) patientValues.get("resource");

                 try {
                     JSONObject patientValueQuantity = (JSONObject) patientResource.get("valueQuantity");

                     String JsonCholesterol = patientValueQuantity.getString("value");

                     Float cholesterolLevel = Float.parseFloat(JsonCholesterol);

                     String time = patientResource.getString("effectiveDateTime");

                     patient.setCholesterol(cholesterolLevel);
                     patient.setCholesterolDate(time);

                 } catch (Exception ignored){}


             }

         } catch (Exception ignored){}
    	
    	
    }
	
	
	

}
