package com.dental.appointment.repository;

import com.dental.appointment.entity.Appointment;
import com.dental.datastore.DataStore;
import com.dental.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class AppointmentRepository implements Repository<Appointment, UUID> {

    private final DataStore dataStore;

    @Inject
    public AppointmentRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public Optional<Appointment> find(UUID id) {
        return dataStore.findAppointment(id);
    }

    @Override
    public List<Appointment> findAll() {
        return dataStore.findAllAppointments();
    }

    @Override
    public void update(Appointment appointment) {
        dataStore.updateAppointment(appointment);
    }

    @Override
    public void create(Appointment appointment) {
        dataStore.createAppointment(appointment);
    }

    @Override
    public void delete(UUID id) {
        dataStore.deleteAppointment(id);
    }

    public List<Appointment> findAppointmentsByTreatment(UUID id) {
        return dataStore.findAppointmentsByTreatment(id);
    }
}
