package com.rest.Main.Monitor;

import com.google.gson.Gson;
import com.rest.Main.Monitor.HighPressure.HighPressureRecord;
import com.rest.Main.domain.Practitioner;
import com.rest.Main.domain.Patient;
import com.google.gson.reflect.TypeToken;
import com.rest.Main.service.PatientCholesterolService;
import com.rest.Main.service.PatientDetailsService;
import com.rest.Main.service.PatientPressureService;
import com.rest.Main.service.PatientService;
import com.rest.Main.service.PatientServiceImpl;
import com.rest.Main.service.PatientSmokerService;

import org.json.JSONException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes({"practitioner", "monitorList","patients","monitorCheck","monitorPatientList","patientCheck",
                     "patientSelection","timer","threshold","pressureThreshold","pressureList","records","record",
                     "summary","patientToFilter","monitorJson"})

public class MonitorController {

    @Autowired
    private PatientServiceImpl service;
    
    @Autowired
    private PatientService[] services = new PatientService[4];


    @GetMapping("/loadMonitor")
    public String loadMonitor(Model model,@ModelAttribute ("monitorList")  ArrayList<Patient> monitorList){
    	
        if (monitorList.size() > 1) {
            ArrayList<Float> cholVals = new ArrayList<Float>();
            for (Patient patient : monitorList) {
                Float cholesterolVal = patient.getCholesterol();
                if (cholesterolVal != 0.0){
                    cholVals.add(cholesterolVal);
                }
            }
            float sum = (float) 0;
            for (Float value : cholVals) {
                sum += value;
            }
            double averageCholesterol = sum / (double) cholVals.size();
            model.addAttribute("averageChol", averageCholesterol);
        }

        model.addAttribute("patientSelection", new Patient());
        model.addAttribute("timer", new RefreshTimer());
        model.addAttribute("threshold", new CholesterolThreshold());
        model.addAttribute("pressureThreshold", new PressureThreshold());
        model.addAttribute("patientToFilter", new Patient());
        model.addAttribute("summary", new Statistics());



        return "redirect:/monitor";
    }

   @PostMapping("/toMonitor")
    public String addToMonitor(Model model, @ModelAttribute ("patient") Patient patient,
                               @ModelAttribute ("monitorCheck") HashMap<String, Patient> monitorCheck,
                               @ModelAttribute ("patientCheck")HashMap<String, Patient> patientCheck,
                               @ModelAttribute ("monitorPatientList") ArrayList<Patient> monitorPatientList,
                               @ModelAttribute ("monitorList")  ArrayList<Patient> monitorList){

       if (!(monitorCheck.containsKey(patient.getId()))) {
           monitorList.add(patient);
           monitorCheck.put(patient.getId(), patient);
           patientCheck.remove(patient.getId());
           monitorPatientList = new ArrayList<>(patientCheck.values());

           Gson gson = new Gson();
           String monitorJson = gson.toJson(monitorCheck);
           model.addAttribute("monitorJson",monitorJson);

           model.addAttribute("monitorPatientList",monitorPatientList);

       }else if (patientCheck.containsKey(patient.getId())){
           monitorPatientList.add(patient);
           patientCheck.put(patient.getId(), patient);
       }


       return "redirect:/monitor";
   }
   
   
   @GetMapping("/toBarGraph")
   public String prepareBarGraph(Model model, @ModelAttribute ("monitorList") ArrayList<Patient> monitorList) {
	   
	   String[] patientNames = new String[monitorList.size()];
	   float[] patientValues = new float[monitorList.size()];
	   
	   for (int i = 0; i < monitorList.size(); i++) {
		   patientNames[i] = monitorList.get(i).getName() +" "+ monitorList.get(i).getSurname();
		   patientValues[i] = monitorList.get(i).getCholesterol();
	   }
	   
	   model.addAttribute("cholesterolNames", patientNames);
	   model.addAttribute("cholesterolValues", patientValues);
	   
	   return "bar-graph";
   }

   @GetMapping("/monitor")
   public String toPatientsGetRequest(Model model, @ModelAttribute ("monitorList")  ArrayList<Patient> monitorList,
                                      @ModelAttribute ("pressureThreshold") PressureThreshold threshold,
                                      @ModelAttribute ("summary") Statistics summary){

	   //Statistics summary = new Statistics();
	   
	   
	   int numberChol = 0;
	   int numberSystolic = 0;
       int numberDiastolic = 0;
       int numberSmokers = 0;
	   
       if (monitorList.size() > 1) {
           ArrayList<Float> cholVals = new ArrayList<>();
           for (Patient eachPatient : monitorList) {
               Float cholesterolVal = eachPatient.getCholesterol();
               if (cholesterolVal != 0.0){
                   cholVals.add(cholesterolVal);
               }
           }

           float sum = (float) 0;
           for (Float value : cholVals) {
               sum += value;
           }
           double averageCholesterol = sum / (double) cholVals.size();
           
           for (Patient eachPatient : monitorList) {
               Float cholesterolVal = eachPatient.getCholesterol();
               if(cholesterolVal> averageCholesterol ){
                   eachPatient.setHighest();
                   numberChol = numberChol + 1;
               }

           }
           model.addAttribute("averageChol", averageCholesterol);
       }

       if (threshold.getDiastolic() > 0) {
           for (Patient patient : monitorList) {
               if (threshold.getDiastolic() <= patient.getDiastolicPressure()) {
                   numberDiastolic = numberDiastolic + 1;
               }
           }
       }

       if (threshold.getSystolic() > 0) {
    	   ArrayList<Patient> pressureList = new ArrayList<>();
           for (Patient patient : monitorList) {
        	   if (threshold.getSystolic() <= patient.getSystolicPressure()) {
        		   pressureList.add(patient);
        		   numberSystolic = numberSystolic + 1;
               }
                   

                
           }
           model.addAttribute("pressureList",pressureList);
       }
       
       String nonSmoker = "Never smoker";
       
       for (Patient eachPatient : monitorList) {
    	   //if (eachPatient.getSmoker() != null) {
	    	   if (eachPatient.getSmoker().equals(nonSmoker)) {
	    		   numberSmokers += 1;
	    	   }
    	   //}
	    	   
	    	   //summary.setName(eachPatient.getSmoker());
       }
                  
       summary.setHighDiastolic(numberDiastolic);
       summary.setHighSystolic(numberSystolic);
       summary.setAboveAverageChol(numberChol);
       summary.setNonSmokers(numberSmokers);
       model.addAttribute("summary", summary);
           
       return "cholesterol-monitor";
   }

    @PostMapping("/toPatients")
    public String removeFromMonitor(Model model, @ModelAttribute ("patient") Patient patient,
                                    @ModelAttribute ("monitorCheck") HashMap<String, Patient> monitorCheck,
                                    @ModelAttribute ("patientCheck")HashMap<String, Patient> patientCheck,
                                    @ModelAttribute ("monitorPatientList") ArrayList<Patient> monitorPatientList,
                                    @ModelAttribute ("monitorList")  ArrayList<Patient> monitorList
                                    ){

        if ((monitorCheck.containsKey(patient.getId()))) {
            monitorCheck.remove(patient.getId());
            monitorList = new ArrayList<>(monitorCheck.values());
            model.addAttribute("monitorList",monitorList);
            monitorPatientList.add(patient);
            patientCheck.put(patient.getId(), patient);


            Gson gson = new Gson();
            String monitorJson = gson.toJson(monitorCheck);
            model.addAttribute("monitorJson",monitorJson);

        } else if (!(patientCheck.containsKey(patient.getId()))){

            patientCheck.remove(patient.getId());
            monitorPatientList = new ArrayList<>(patientCheck.values());
            model.addAttribute("monitorPatientList",monitorPatientList);
        }




        return "redirect:/monitor";
    }

    @PostMapping("/setTimer")
    public String setTimer(@RequestParam("action") String action , Model model,
                           @ModelAttribute ("timer") RefreshTimer timer)  {
        if (action.equals("Stop")){
            timer.setSeconds(0);
        }

        if (timer.getSeconds() != 0) {

            model.addAttribute(timer);

        }

        return "redirect:/refresh";
    }

    @PostMapping("/setCholesterol")
    public String setCholesterolThreshold(Model model, @ModelAttribute ("threshold") CholesterolThreshold threshold){
        model.addAttribute("threshold",threshold);
        return "redirect:/monitor";
    }

    @PostMapping("/setPressure")
    public String setPressureThreshold(Model model,
                                       @ModelAttribute("monitorList") ArrayList<Patient> monitorList, @ModelAttribute ("pressureThreshold") PressureThreshold threshold){

        model.addAttribute("pressureThreshold",threshold);
        model.addAttribute("pressureList", new ArrayList<Patient>());



        return "redirect:/monitor";
    }

    @GetMapping("/refresh")
    public String refresh(Model model, @ModelAttribute ("timer") RefreshTimer timer,
                           @ModelAttribute Practitioner practitioner,
                           @ModelAttribute ("monitorCheck") HashMap<String, Patient> monitorCheck,
                           @ModelAttribute ("patientCheck")HashMap<String, Patient> patientCheck) throws JSONException {

        if (timer.getSeconds() != 0) {
            ArrayList<Patient> monitorList = new ArrayList<>();
            ArrayList<Patient> monitorPatientList = new ArrayList<>();
            model.addAttribute(timer);
            ArrayList<Patient> patients = service.getPatientList(practitioner);
            
            services[0] = new PatientDetailsService();
            services[1] = new PatientCholesterolService();
            services[2] = new PatientPressureService();
            services[3] = new PatientSmokerService();
            
            
            for (Patient patient : patients) {
            	
            	for (PatientService service : services){
            		service.addToPatient(patient);
            	}
            	
                if (monitorCheck.containsKey(patient.getId())) {
                    monitorList.add(patient);
                    model.addAttribute(monitorList);
                }
                if (patientCheck.containsKey(patient.getId())) {
                    monitorPatientList.add(patient);
                    model.addAttribute(monitorPatientList);
                }

            }
        }

        return "redirect:/monitor";
    }
    @PostMapping("/cholesterol-switch")
    public String cholesterolSwitch(Model model, @ModelAttribute ("patientToFilter") Patient patientToFilter,
                                    @ModelAttribute("monitorJson") String monitorJson){

        patientToFilter.setCholesterolOn(!patientToFilter.isCholesterolOn());

        Map<String, Patient> mapObj = new Gson().fromJson(
                monitorJson, new TypeToken<HashMap<String, Patient>>() {}.getType()
        );

        mapObj.replace(patientToFilter.getId(),patientToFilter);

        monitorJson = new Gson().toJson(mapObj);
        model.addAttribute("monitorJson",monitorJson);

        ArrayList<Patient> monitorList = new ArrayList<>(mapObj.values());
        model.addAttribute("monitorList", monitorList);
        return "redirect:/monitor";
    }

    @PostMapping("/pressure-switch")
    public String pressureSwitch(Model model, @ModelAttribute ("patientToFilter") Patient patientToFilter,
                                 @ModelAttribute("monitorJson") String monitorJson){

        patientToFilter.setPressureOn(!patientToFilter.isPressureOn());
        Map<String, Patient> mapObj = new Gson().fromJson(
                monitorJson, new TypeToken<HashMap<String, Patient>>() {}.getType()
        );

        mapObj.replace(patientToFilter.getId(),patientToFilter);

        monitorJson = new Gson().toJson(mapObj);
        model.addAttribute("monitorJson",monitorJson);

        ArrayList<Patient> monitorList = new ArrayList<>(mapObj.values());
        model.addAttribute("monitorList", monitorList);
        return "redirect:/monitor";
    }

    @GetMapping("/load-high-bp")
    public String loadHighBp(Model model, @ModelAttribute ("pressureList")  ArrayList<Patient> pressureList){

        ArrayList<HighPressureRecord> allHighPressureRecords = new ArrayList<>();
         for (Patient patient: pressureList){
             HighPressureRecord highPressureRecord = new HighPressureRecord();
             String pressureValueString = patient.getSystolicPressureObservations();
             String pressureDatesString = patient.getSystolicPressureObservationDates();

             highPressureRecord.setName(patient.getName() + " " + patient.getSurname());
             highPressureRecord.setPressureValues(pressureValueString.split(","));
             highPressureRecord.setPressureDates(pressureDatesString.split(","));
             highPressureRecord.setPressureValuesString(pressureValueString);
             highPressureRecord.setPressureDatesString(pressureDatesString);
             allHighPressureRecords.add(highPressureRecord);
         }

        model.addAttribute("records", allHighPressureRecords);
         model.addAttribute("record",new HighPressureRecord());
        return "high-pressure";
    }


}





























