package com.first.patientmanagement.model;

import com.first.patientmanagement.entity.Patient;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class PatientPrinciple implements UserDetails {

    private Patient patient;

    public PatientPrinciple(Patient patient) {
        this.patient = patient;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public @Nullable String getPassword() {
        return patient.getPassword();
    }

    @Override
    public String getUsername() {
        return patient.getPatientName();
    }
}
