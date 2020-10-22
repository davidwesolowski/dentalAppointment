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

    public synchronized void createTreatment(Treatment treatment) throws IllegalArgumentException {
        findTreatment(treatment.getId()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException("Treatment with this id: " + treatment.getId() + " exists!");
                },
                () -> treatments.add(treatment)
        );
    }

    public synchronized void updateTreatment(Treatment treatment) throws IllegalArgumentException {
        findTreatment(treatment.getId()).ifPresentOrElse(
                original -> {
                    treatments.remove(original);
                    treatments.add(treatment);
                },
                () -> {
                    throw new IllegalArgumentException("Treatment with this id: " + treatment.getId() + " does not exists!");
                }
        );
    }

    public synchronized void deleteTreatment(UUID id) throws IllegalArgumentException {
        findTreatment(id).ifPresentOrElse(
                original -> {
                    appointments = appointments.stream()
                            .filter(appointment -> !appointment.getTreatment().equals(original))
                            .collect(Collectors.toSet());
                    treatments.remove(original);
                },
                () -> {
                    throw new IllegalArgumentException("Treatment with this id: " + id.toString() + " does not exists!");
                }
        );
    }

    public synchronized List<Appointment> findAllAppointments() { return new ArrayList<>(appointments); }

    public synchronized Optional<Appointment> findAppointment(UUID id) {
        return appointments.stream()
                .filter(appointment -> appointment.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized void createAppointment(Appointment appointment) throws IllegalArgumentException {
        findAppointment(appointment.getId()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException("Appointment with this id: " + appointment.getId() + " exists!");
                },
                () -> appointments.add(appointment)
        );
    }

    public synchronized void updateAppointment(Appointment appointment) throws IllegalArgumentException {
        findAppointment(appointment.getId()).ifPresentOrElse(
                original -> {
                    appointments.remove(original);
                    appointments.add(appointment);
                },
                () -> {
                    throw new IllegalArgumentException("Appointment with this id: " + appointment.getId() + " doest not exists!");
                }
        );
    }

    public synchronized void deleteAppointment(UUID id) throws IllegalArgumentException {
        findAppointment(id).ifPresentOrElse(
                original -> appointments.remove(original),
                () -> {
                    throw new IllegalArgumentException("Appointment with this id: " + id.toString() + " doest not exists!");
                }
        );
    }

    public synchronized List<Appointment> findAppointmentsByTreatment(UUID id) {
        return appointments.stream()
                .filter(appointment -> appointment.getTreatment().getId().equals(id))
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }


}
