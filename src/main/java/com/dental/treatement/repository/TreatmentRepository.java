package com.dental.treatement.repository;

import com.dental.repository.Repository;
import com.dental.treatement.entity.Treatment;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class TreatmentRepository implements Repository<Treatment, UUID> {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Treatment> find(UUID id) {
        return Optional.ofNullable(entityManager.find(Treatment.class, id));
    }

    @Override
    public List<Treatment> findAll() {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        return entityManager.createQuery("SELECT t FROM Treatment t", Treatment.class).getResultList();
    }

    @Override
    public void update(Treatment treatment) {
        delete(treatment.getId());
        create(treatment);
    }
    
    @Override
    public void create(Treatment treatment) {
        entityManager.persist(treatment);
    }

    @Override
    public void delete(UUID id) {
        Query query = entityManager.createQuery("DELETE FROM Treatment WHERE id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public Optional<Treatment> findByName(String name) {
        try {
            Query query = entityManager.createQuery("SELECT t FROM Treatment t WHERE t.name=:name", Treatment.class);
            query.setParameter("name", name);
            Treatment treatment = (Treatment) query.getSingleResult();
            return Optional.of(treatment);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
