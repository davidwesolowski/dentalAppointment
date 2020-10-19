package com.dental.treatement.repository;

import com.dental.datastore.DataStore;
import com.dental.repository.Repository;
import com.dental.treatement.entity.Treatment;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class TreatmentRepository implements Repository<Treatment, UUID> {

    private final DataStore dataStore;

    @Inject
    public TreatmentRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public Optional<Treatment> find(UUID id) {
        return dataStore.findTreatment(id);
    }

    @Override
    public List<Treatment> findAll() {
        return dataStore.findAllTreatments();
    }

    @Override
    public void update(Treatment treatment) {
        dataStore.updateTreatment(treatment);
    }

    @Override
    public void create(Treatment treatment) {
        dataStore.createTreatment(treatment);
    }

    @Override
    public void delete(UUID id) {
        dataStore.deleteTreatment(id);
    }
}
