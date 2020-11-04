package com.dental.appointment.service;

import com.dental.appointment.entity.Appointment;
import com.dental.appointment.repository.AppointmentRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor
public class AppointmentService {

    private AppointmentRepository appointmentRepository;

    @Inject
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Optional<Appointment> find(UUID id) {
        return appointmentRepository.find(id);
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public void create(Appointment appointment) {
        appointmentRepository.create(appointment);
    }

    public void update(Appointment appointment) {
        appointmentRepository.update(appointment);
    }

    public void delete(UUID id) {
        appointmentRepository.delete(id);
    }

    public List<Appointment> findAppointmentsByTreatment(UUID id) {
        return appointmentRepository.findAppointmentsByTreatment(id);
    }

    public Optional<Appointment> findAppointmentByTreatment(UUID treatmentId, UUID appointmentId) {
        return appointmentRepository.findAppointmentByTreatment(treatmentId, appointmentId);
    }
}
