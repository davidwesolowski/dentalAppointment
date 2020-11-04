package com.dental.datastore;

import com.dental.appointment.entity.Appointment;
import com.dental.treatement.entity.Treatment;
import com.dental.doctor.entity.Doctor;
import com.dental.serialization.CloningUtility;
import lombok.extern.java.Log;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.stream.Collectors;

@Log
@ApplicationScoped
public class DataStore {
    private Set<Doctor> doctors = new HashSet<>();
    private Set<Treatment> treatments = new HashSet<>();
    private Set<Appointment> appointments = new HashSet<>();

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
                    throw new IllegalArgumentException("Doctor with this id: " + doctor.getId() + "does not exists!");
                }
        );
    }

    public synchronized  List<Treatment> findAllTreatments() { return new ArrayList<>(treatments); }

    public synchronized Optional<Treatment> findTreatment(UUID id) {
        return treatments.stream()
                .filter(treatment -> treatment.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized Optional<Treatment> findTreatmentByName(String name) {
        return treatments.stream()
                .filter(treatment -> treatment.getName().equals(name))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized void createTreatment(Treatment treatment) {
        treatments.add(treatment);
    }

    public synchronized void updateTreatment(Treatment treatment) {
        findTreatment(treatment.getId()).ifPresent(
                original -> {
                    treatments.remove(original);
                    treatments.add(treatment);
                }
        );
    }

    public synchronized void deleteTreatment(UUID id) {
        findTreatment(id).ifPresent(original -> {
            appointments = appointments.stream()
                            .filter(appointment -> !appointment.getTreatment().equals(original))
                            .collect(Collectors.toSet());
            treatments.remove(original);
        });
    }

    public synchronized List<Appointment> findAllAppointments() { return new ArrayList<>(appointments); }

    public synchronized Optional<Appointment> findAppointment(UUID id) {
        return appointments.stream()
                .filter(appointment -> appointment.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized void createAppointment(Appointment appointment)  {
        appointments.add(appointment);
    }

    public synchronized void updateAppointment(Appointment appointment) {
        findAppointment(appointment.getId()).ifPresent(
            original -> {
                appointments.remove(original);
                appointments.add(appointment);
        });
    }

    public synchronized void deleteAppointment(UUID id) {
        findAppointment(id).ifPresent(original -> appointments.remove(original));
    }

    public synchronized List<Appointment> findAppointmentsByTreatment(UUID id) {
        return appointments.stream()
                .filter(appointment -> appointment.getTreatment().getId().equals(id))
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized Optional<Appointment> findAppointmentByTreatment (UUID treatmentId, UUID appointmentId) {
        return findAppointmentsByTreatment(treatmentId).stream()
                .filter(appointment -> appointment.getId().equals(appointmentId))
                .findFirst()
                .map(CloningUtility::clone);
    }

}
