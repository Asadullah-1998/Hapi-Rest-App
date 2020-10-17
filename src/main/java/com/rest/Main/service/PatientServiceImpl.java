package com.rest.Main.service;

import com.rest.Main.domain.Patient;
import com.rest.Main.domain.Practitioner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.stereotype.Service;


import java.util.*;


@Service
public class PatientServiceImpl extends PatientService {

	
	@Override
	public void addToPatient(Patient patient) {
		
	}

    public ArrayList<Patient> getPatientList(Practitioner practitioner) throws JSONException {

        String url = root_url + "Encounter?participant.identifier=" + practitioner.getId() +
                "&_include=Encounter.participant.individual&_include=Encounter.patient";

        String result = restTemplate.getForObject(url, String.class);
        JSONObject root = new JSONObject(result);

        ArrayList<Patient> encounterList = new ArrayList<Patient>();

        JSONArray entry = root.getJSONArray("entry");

        for (int i = 0; i < entry.length(); i++) {
            JSONObject jsonEntry = entry.getJSONObject(i);
            Patient patient = new Patient();

            JSONObject entryResource = (JSONObject) jsonEntry.get("resource");

            JSONObject entrySubject = (JSONObject) entryResource.get("subject");

            String tempID = entrySubject.getString("reference");
            String tempName = entrySubject.getString("display");

            String processedName = tempName.replaceAll("\\d","");
            String[] splitNames = processedName.split("\\s+");

            patient.setId(tempID);
            if (splitNames.length >= 3) {

                patient.setName(splitNames[1]);
                patient.setSurname(splitNames[2]);
            }

            if (splitNames.length == 2){
                patient.setName(splitNames[0]);
                patient.setSurname(splitNames[1]);
            }

            encounterList.add(patient);

        }
        Map<String, Patient> patientMap = new HashMap<>();
        for (int j = 1; j < encounterList.size(); j++) {
            String patientId = encounterList.get(j).getId();


            if ((patientMap.containsKey(patientId))) {

                continue;
            } else {
                patientMap.put(patientId,encounterList.get(j));
            }

        }

        ArrayList<Patient> patientList = new ArrayList<Patient>(patientMap.values());

        for (Patient patient : patientList) {

            patient.setSystolicPressureObservations("");
            patient.setSystolicPressureObservationDates("");

        }

        return patientList;
    }



}
