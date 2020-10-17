package com.dental.datastore;

import com.dental.doctor.entity.Doctor;
import com.dental.serialization.CloningUtility;
import lombok.extern.java.Log;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;

@Log
@ApplicationScoped
public class DataStore {
    private Set<Doctor> doctors = new HashSet<>();

    public synchronized List<Doctor> findAllDoctors() {
        return new ArrayList<>(doctors);
    }

    public synchronized Optional<Doctor> findDoctor(UUID id) {
        return doctors.stream()
                .filter(doctor -> doctor.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized void createDoctor(Doctor doctor) throws IllegalArgumentException {
        findDoctor(doctor.getId()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException("Doctor with this id: " + doctor.getId() + " exists!");
                },
                () -> doctors.add(doctor));
    }

    public synchronized void updateDoctor(Doctor doctor) throws IllegalArgumentException {
        findDoctor(doctor.getId()).ifPresentOrElse(
                original -> {
                    doctors.remove(original);
                    doctors.add(doctor);
                },
                () -> {
                    throw new IllegalArgumentException("Doctor with this id: " + doctor.getId() + " exists!");
                }
        );
    }
}
