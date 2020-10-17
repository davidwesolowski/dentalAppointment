package com.dental.doctor.service;

import com.dental.doctor.entity.Doctor;
import com.dental.doctor.repository.DoctorRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor
public class DoctorService {

    private DoctorRepository doctorRepository;

    @Inject
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public void create(Doctor doctor) {
        doctorRepository.create(doctor);
    }

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public Optional<Doctor> find(UUID id) {
        return doctorRepository.find(id);
    }

    public void updateAvatar(UUID id, String avatarPath) {
        doctorRepository.find(id).ifPresent(doctor -> {
            doctor.setAvatarPath(avatarPath);
            doctorRepository.update(doctor);
        });

    }
}
