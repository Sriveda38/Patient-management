package com.first.patientmanagement.service;

import com.first.patientmanagement.dto.AuthResponse;
import com.first.patientmanagement.entity.Patient;
import com.first.patientmanagement.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Patient registerPatient(@RequestBody Patient patient) {

        Patient savedPatient = new Patient();
        savedPatient.setId(null);
        savedPatient.setPatientName(patient.getPatientName());
        savedPatient.setEmail(patient.getEmail());
        savedPatient.setPassword(passwordEncoder.encode(patient.getPassword()));
        savedPatient.setRole(patient.getRole());
        repo.save(savedPatient);
        return savedPatient;
    }

    public AuthResponse verify(Patient patient) {
        try {
            Authentication authentication =
                    authManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    patient.getEmail(),
                                    patient.getPassword()
                            )
                    );

            if (authentication.isAuthenticated()) {
                Patient dbPatient = repo.findByEmail(patient.getEmail());
                String token = jwtService.generateToken(patient.getEmail());
                String role = dbPatient.getRole();
                return new AuthResponse(token, role);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
