package com.rest.Main.Monitor.HighPressure;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes({"practitioner","monitorList","patients","monitorCheck","monitorPatientList","patientCheck","record"})
public class HighPressureController {


    @GetMapping("/highPressure")
    public String loadHighPressure(Model model, @ModelAttribute("record") HighPressureRecord record){





        return "high-pressure";
    }

    @PostMapping("/build-graph")
    public String buildGraph(Model model, @ModelAttribute("record") HighPressureRecord record){

        record.setPressureValues(record.getPressureValuesString().split(",", 0));
        record.setPressureDates(record.getPressureDatesString().split(",", 0));
        
        
        Map<String, String> patientMap = new HashMap<String, String>();

        
        Double[] systolicValues = new Double[record.getPressureValues().length - 1];
        String[] systolicDates = new String[record.getPressureDates().length - 1];
        
        
        int k = 0;
        
        for (int j = record.getPressureValues().length -1; j >= 0; j--) {
        	if (!(record.getPressureValues()[j].isBlank() || 
        			record.getPressureDates()[j].isBlank())) {
        		systolicValues[k] = Double.parseDouble(record.getPressureValues()[j]);
        		systolicDates[k] = record.getPressureDates()[j];
        		k++;
        	}
        }
     
        
  
       
        
        patientMap.put("Name", record.getName());

        
        
        
        model.addAttribute("PatientMap", patientMap);
        
        model.addAttribute("SystolicValues", systolicValues);
        model.addAttribute("SystolicDates", systolicDates);

        return "line-graph";
    }




}
