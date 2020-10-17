package com.rest.Main;

import com.google.gson.Gson;
import com.rest.Main.domain.Patient;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.ArrayList;


@Controller
@SessionAttributes({"monitorList","patients","monitorCheck","patientCheck","monitorPatientList","monitorJson"})
public class PatientListController {

    @PostMapping("/addPatient")
    public String addToMonitor(Model model, @ModelAttribute ("patient") Patient patient,
                               @ModelAttribute ("monitorList")  ArrayList<Patient> monitorList,
                               @ModelAttribute ("monitorCheck")HashMap<String, Patient> monitorCheck,
                               @ModelAttribute ("monitorPatientList") ArrayList<Patient> monitorPatientList,
                               @ModelAttribute ("patientCheck")HashMap<String, Patient> patientCheck)  {
        if (!(monitorCheck.containsKey(patient.getId()))){
            monitorList.add(patient);
            monitorCheck.put(patient.getId(), patient);
            patientCheck.remove(patient.getId());
            monitorPatientList = new ArrayList<>(patientCheck.values());
            model.addAttribute("monitorPatientList",monitorPatientList);
            Gson gson = new Gson();
            String monitorJson = gson.toJson(monitorCheck);
           model.addAttribute("monitorJson",monitorJson);

        } else if (patientCheck.containsKey(patient.getId())){
            monitorPatientList.add(patient);
            patientCheck.put(patient.getId(), patient);
        }
        return "redirect:/patients";
    }

    @PostMapping("/removePatient")
    public String removeFromMonitor(Model model, @ModelAttribute ("patient") Patient patient,
                                    @ModelAttribute ("monitorCheck")HashMap<String, Patient> monitorCheck,
                                    @ModelAttribute ("patientCheck")HashMap<String, Patient> patientCheck,
                                    @ModelAttribute ("monitorPatientList") ArrayList<Patient> monitorPatientList
                                    )  {
        if ((monitorCheck.containsKey(patient.getId()))) {

                monitorCheck.remove(patient.getId());
                ArrayList<Patient> monitorList = new ArrayList<>(monitorCheck.values());
                model.addAttribute("monitorList", monitorList);

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
        return "redirect:/patients";
    }

    @GetMapping("/patients")
    public String patientListGet(Model model, @ModelAttribute ("monitorList")  ArrayList<Patient> monitorList) {


        return "patient-list";
    }
}
