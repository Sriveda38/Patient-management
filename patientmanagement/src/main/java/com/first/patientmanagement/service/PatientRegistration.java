package com.first.patientmanagement.service;

import com.first.patientmanagement.entity.Patient;
import com.first.patientmanagement.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class PatientRegistration {

    @Autowired
    private PatientRepository repo;

    @Autowired
    private  AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Patient registerPatient(@RequestBody Patient patient) {

        Patient savedPatient = new Patient();
        savedPatient.setId(null);
        savedPatient.setPatientName(patient.getPatientName());
        savedPatient.setEmail(patient.getEmail());
        savedPatient.setPassword(encoder.encode(patient.getPassword()));
        repo.save(savedPatient);
        return savedPatient;
    }

    public String verify(Patient patient) {
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(patient.getPatientName(),patient.getPassword()));
        if(authentication.isAuthenticated())
            return jwtService.generateToken(patient.getPatientName());
        return "Fail";
    }
}
