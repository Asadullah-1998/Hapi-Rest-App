package com.rest.Main;

import com.rest.Main.domain.Patient;
import com.rest.Main.domain.Practitioner;
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

import java.util.HashMap;
import java.util.ArrayList;


@Controller
@SessionAttributes({"practitioner","monitorList","patients","monitorCheck","monitorPatientList","patientCheck"})
public class IndexController {

    @Autowired
    private PatientServiceImpl service;
    
    @Autowired
    private PatientService[] services = new PatientService[4];

            

//Add
    @GetMapping({"/", "/index"})
    public String practitionerForm(Model model)  {
        
        model.addAttribute("practitioner", new Practitioner());
        model.addAttribute("patientCheck" ,new HashMap<String,Patient>());
        return "index";
    }

    @PostMapping("/returnPatients")
    public String practitionerSubmit(Model model,@ModelAttribute ("practitioner")Practitioner practitioner,
                                     @ModelAttribute ("patientCheck")HashMap<String, Patient> patientCheck) throws JSONException {
    	
        model.addAttribute("patient", new Patient());
        model.addAttribute("monitorList",new ArrayList<Patient>());
        model.addAttribute("monitorCheck" ,new HashMap<String,Patient>());

        
        services[0] = new PatientDetailsService();
        services[1] = new PatientCholesterolService();
        services[2] = new PatientPressureService();
        services[3] = new PatientSmokerService();



        
        ArrayList<Patient> monitorPatientList = new ArrayList<Patient>();
        ArrayList<Patient> patientList = new ArrayList<Patient>();
        patientList = service.getPatientList(practitioner);

        for (Patient patient : patientList) {
        	
        	for (PatientService service : services){
        		service.addToPatient(patient);
        	}
        	
        	patientCheck.put(patient.getId(), patient);
            monitorPatientList.add(patient);
        }


        model.addAttribute("monitorPatientList",monitorPatientList);
        model.addAttribute("patients",patientList);
        model.addAttribute("practitioner",practitioner);
        return "patient-list";
    }




}
