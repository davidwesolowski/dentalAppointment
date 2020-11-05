package com.dental.treatement.service;

import com.dental.treatement.entity.Treatment;
import com.dental.treatement.repository.TreatmentRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor
public class TreatmentService {

    private TreatmentRepository treatmentRepository;

    @Inject
    public TreatmentService(TreatmentRepository treatmentRepository) {
        this.treatmentRepository = treatmentRepository;
    }

    public Optional<Treatment> find(UUID id) {
        return treatmentRepository.find(id);
    }

    public List<Treatment> findAll() {
        return treatmentRepository.findAll();
    }

    @Transactional
    public void create(Treatment treatment) {
        treatmentRepository.create(treatment);
    }

    @Transactional
    public void update(Treatment treatment) {
        treatmentRepository.update(treatment);
    }

    @Transactional
    public void delete(UUID id) {
        treatmentRepository.delete(id);
    }

    @Transactional
    public Optional<Treatment> findByName(String name) { return treatmentRepository.findByName(name); }
}
