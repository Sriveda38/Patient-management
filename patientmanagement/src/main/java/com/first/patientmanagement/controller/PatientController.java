package com.first.patientmanagement.controller;

import com.first.patientmanagement.entity.Patient;
import com.first.patientmanagement.service.PatientRegistration;
import com.first.patientmanagement.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRegistration patientRegistration;

    @GetMapping("/hello")
    public String helloWorld () {
        return "Hello World";
    }

    @PostMapping("/register")
    public Patient registerUser(@RequestBody Patient patient) {
        return patientRegistration.registerPatient(patient);
    }

    @PostMapping("/login")
    public String login(@RequestBody Patient patient) {
        return patientRegistration.verify(patient);
    }

    @GetMapping("/getAllPatients")
    public List<Patient> getAllPatients () {
        return patientService.getAllPatients();
    }

    @GetMapping("/getPatientById/{id}")
    public Patient getPatientById(@PathVariable int id) {
        Patient patient;
        return  patient = patientService.getPatientById(id);
    }


    @PutMapping("/editPatientById/{id}")
    public String updatePatientById(@RequestBody Patient patient, @PathVariable int id) {
        String response;
       return  response = patientService.updatePatientById(patient, id);
    }

    @DeleteMapping("/deletePatientById/{id}")
    public String deletePatientById(@PathVariable int id) {
        String response;
        return response =  patientService.deletePatientById(id);
    }




}
