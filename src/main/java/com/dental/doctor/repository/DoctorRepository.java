package com.dental.doctor.repository;

import com.dental.datastore.DataStore;
import com.dental.doctor.entity.Doctor;
import com.dental.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class DoctorRepository implements Repository<Doctor, UUID> {

    private final DataStore dataStore;

    @Inject
    public DoctorRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public Optional<Doctor> find(UUID id) {
        return dataStore.findDoctor(id);
    }

    @Override
    public List<Doctor> findAll() {
        return dataStore.findAllDoctors();
    }

    @Override
    public void create(Doctor entity) {
        dataStore.createDoctor(entity);
    }

    @Override
    public void update(Doctor entity) {
        dataStore.updateDoctor(entity);
    }

    @Override
    public void delete(UUID id) {
        throw new UnsupportedOperationException("Not implemented.");
    }

}
