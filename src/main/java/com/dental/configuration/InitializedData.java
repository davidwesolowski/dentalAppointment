package com.dental.configuration;

import com.dental.doctor.entity.Doctor;
import com.dental.doctor.entity.Position;
import com.dental.doctor.entity.Specialisation;
import com.dental.doctor.service.DoctorService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class InitializedData {

    private final DoctorService doctorService;

    @Inject
    public InitializedData(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) { init(); }

    private synchronized void init() {
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(Doctor.builder()
            .id(UUID.randomUUID())
            .firstName("Antoni")
            .lastName("Mackowski")
            .dateOfBirth(LocalDate.of(1996, 10, 12))
            .login("antoni.mac@wp.pl")
            .position(Position.OWNER)
            .specialisation(Specialisation.ORAL_SURGEON)
            .build());
        doctors.add(Doctor.builder()
            .id(UUID.randomUUID())
            .firstName("Jacek")
            .lastName("Gorski")
            .dateOfBirth(LocalDate.of(2001, 7, 7))
            .login("jacek.gorski@wp.pl")
            .position(Position.WORKER)
            .specialisation(Specialisation.DENTIST)
            .build());
        doctors.add(Doctor.builder()
                .id(UUID.randomUUID())
                .firstName("Natalia")
                .lastName("Wloska")
                .dateOfBirth(LocalDate.of(1998, 1, 19))
                .login("natalia.wloska@wp.pl")
                .position(Position.WORKER)
                .specialisation(Specialisation.ORTHODONTIST)
                .build());
        doctors.add(Doctor.builder()
                .id(UUID.randomUUID())
                .firstName("Pawel")
                .lastName("Lipka")
                .dateOfBirth(LocalDate.of(1992, 5, 24))
                .login("pawel.lipka@wp.pl")
                .position(Position.WORKER)
                .specialisation(Specialisation.PROSTHODONTIST)
                .build());
        doctors.forEach(doctorService::create);
    }
}
