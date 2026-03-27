package com.first.patientmanagement.service;

import com.first.patientmanagement.entity.Patient;
import com.first.patientmanagement.model.PatientPrinciple;
import com.first.patientmanagement.repository.PatientRepository;
import com.sun.security.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService  implements UserDetailsService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients () {
        return patientRepository.findAll();
    }

    public Patient getPatientById (int id) {
        return patientRepository.getById(id);
    }

    public String deletePatientById (int id) {
         patientRepository.deleteById(id);
         return "Deleted successfully";
    }

    public String updatePatientById(@RequestBody Patient patient, int id) {
        Patient updatedPatient = new Patient();
        updatedPatient.setId(patient.getId());
        updatedPatient.setPatientName(patient.getPatientName());
        updatedPatient.setEmail(patient.getEmail());
        patientRepository.save(updatedPatient);
        return "Updated patient successfully";
    }

    @Override
    public UserDetails loadUserByUsername(String patientName) throws UsernameNotFoundException {

        Patient patient = patientRepository.findByPatientName(patientName);

        if(patient == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        return new PatientPrinciple(patient);
    }

}
