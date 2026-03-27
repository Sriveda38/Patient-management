package com.first.patientmanagement.controller;

import com.first.patientmanagement.dto.AuthResponse;
import com.first.patientmanagement.entity.Patient;
import com.first.patientmanagement.service.PatientRegistration;
import com.first.patientmanagement.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;


@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRegistration patientRegistration;

    @CrossOrigin(origins = "*")

    @GetMapping("/hello")
    public String helloWorld () {
        return "Hello World";
    }


    @PostMapping("/admin/add")
    public Patient registerUser(@RequestBody Patient patient) {
        return patientRegistration.registerPatient(patient);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody Patient patient) {
        return patientRegistration.verify(patient);
    }

    @GetMapping("/user/getAllPatients")
    public List<Patient> getAllPatients () {
        return patientService.getAllPatients();
    }

    @GetMapping("/getPatientById/{id}")
    public Patient getPatientById(@PathVariable int id) {
        Patient patient;
        return  patient = patientService.getPatientById(id);
    }


    @PutMapping("/admin/update/{id}")
    public String updatePatientById(@RequestBody Patient patient, @PathVariable int id) {
        String response;
       return  response = patientService.updatePatient(patient, id);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable int id) {
        String response;
        response = patientService.deletePatient(id);
        return ResponseEntity.ok(response);
    }
}
