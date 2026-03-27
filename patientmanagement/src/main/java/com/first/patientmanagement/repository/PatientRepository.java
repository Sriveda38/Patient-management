package com.first.patientmanagement.repository;

import com.first.patientmanagement.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Patient findByPatientName(String patientName);


}
