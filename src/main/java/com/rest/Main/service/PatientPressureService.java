package com.rest.Main.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.rest.Main.domain.Patient;


@Service
public class PatientPressureService extends PatientService{

    
    
    public PatientPressureService() {
    	
    	setCode("55284-4");
    	
    	setCondition("&_sort=-date&_count=5");
    	
    }

    @Override
    public void addToPatient(Patient patient) {
    
    	
    	
        try {
        	
            String patientVariables = restTemplate.getForObject(root_url+ patient_url +patient.getId() + get_variable + getCode() + getCondition() ,String.class);

        	
            JSONObject patientObject = new JSONObject(patientVariables);
            JSONArray patientEntry = patientObject.getJSONArray("entry");

            for (int j = 0; j < patientEntry.length(); j++ ){
                JSONObject patientValues = patientEntry.getJSONObject(j);

                JSONObject patientResource = (JSONObject) patientValues.get("resource");

                String time = patientResource.getString("effectiveDateTime");

                JSONArray resourceComponent =  patientResource.getJSONArray("component");



                for (int i = 0; i < resourceComponent.length();i++){

                    if (i==0) {
                        if (j == 0) {
                            try {

                                JSONObject patientValueQuantityJson = resourceComponent.getJSONObject(i);
                                JSONObject patientValueQuantity = (JSONObject) patientValueQuantityJson.get("valueQuantity");


                                String JsonDiastolic = patientValueQuantity.getString("value");
                                Float diastolicPressure = Float.parseFloat(JsonDiastolic);
                                patient.setDiastolicPressure(diastolicPressure);
                                patient.setDiastolicDate(time);

                            } catch (JSONException ignored) {}
                        }
                    }

                    if(i == 1) {
                        if (j==0){
                            try {

                                JSONObject patientValueQuantityJson = resourceComponent.getJSONObject(i);
                                JSONObject patientValueQuantity = (JSONObject) patientValueQuantityJson.get("valueQuantity");


                                String JsonSystolic = patientValueQuantity.getString("value");
                                Float systolicPressure = Float.parseFloat(JsonSystolic);
                                patient.setSystolicPressure(systolicPressure);
                                patient.setSystolicDate(time);

                            } catch (JSONException ignored) { }
                        }
                            try {

                                JSONObject patientValueQuantityJson = resourceComponent.getJSONObject(i);
                                JSONObject patientValueQuantity = (JSONObject) patientValueQuantityJson.get("valueQuantity");

                                String systolicPressureString = patient.getSystolicPressureObservations();
                                String systolicPressureDateString = patient.getSystolicPressureObservationDates();
                                String JsonSystolic = patientValueQuantity.getString("value");
                                

                                systolicPressureString = systolicPressureString + "," + JsonSystolic;
                                systolicPressureDateString = systolicPressureDateString + "," + time;


                                patient.setSystolicPressureObservations(systolicPressureString);
                                patient.setSystolicPressureObservationDates(systolicPressureDateString);

                            } catch (JSONException ignored) {}
                        }



                }
            }

        } catch (Exception ignored) {}
    	
    }
	
	
	
	

}
