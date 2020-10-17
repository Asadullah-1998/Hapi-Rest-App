package com.rest.Main;

import com.rest.Main.domain.Patient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes({"monitorList","patients","monitorCheck","monitorPatientList","patientCheck","patientSelection"})
@Controller
public class patientDetailsController {

    @PostMapping("/patientDetails")
    public String showDetailsPost(Model model, @ModelAttribute ("patientSelection") Patient patient)  {

        model.addAttribute("patientSelection",patient);


        return "redirect:/details";
    }

    @GetMapping("/details")

    public String showDetailGet(Model model, @ModelAttribute ("patientSelection") Patient patient)  {

        return "patient-details";
    }
}
