package com.dental.configuration;

import com.dental.appointment.entity.Appointment;
import com.dental.appointment.entity.Status;
import com.dental.appointment.service.AppointmentService;
import com.dental.doctor.entity.Doctor;
import com.dental.doctor.entity.Position;
import com.dental.doctor.entity.Specialisation;
import com.dental.doctor.service.DoctorService;
import com.dental.treatement.entity.Complexity;
import com.dental.treatement.entity.Treatment;
import com.dental.treatement.service.TreatmentService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class InitializedData {

    private final DoctorService doctorService;
    private final TreatmentService treatmentService;
    private final AppointmentService appointmentService;

    @Inject
    public InitializedData(DoctorService doctorService, TreatmentService treatmentService, AppointmentService appointmentService) {
        this.doctorService = doctorService;
        this.treatmentService = treatmentService;
        this.appointmentService = appointmentService;
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

        List<Treatment> treatments = new ArrayList<>();
        treatments.add(Treatment.builder()
                .id(UUID.randomUUID())
                .name("Sealing teeth")
                .cost((float) 120)
                .complexity(Complexity.EASY)
                .duration(LocalTime.of(1, 0))
                .build());
        treatments.add(Treatment.builder()
                .id(UUID.randomUUID())
                .name("Root canal treatment")
                .cost((float) 400)
                .complexity(Complexity.HARD)
                .duration(LocalTime.of(2, 30))
                .build());
        treatments.add(Treatment.builder()
                .id(UUID.randomUUID())
                .name("Apex resection")
                .cost((float) 800)
                .complexity(Complexity.DANGEROUS)
                .duration(LocalTime.of(1, 30))
                .build());
        treatments.add(Treatment.builder()
                .id(UUID.randomUUID())
                .name("Tooth of wisdom extraction")
                .cost((float) 1000)
                .complexity(Complexity.DANGEROUS)
                .duration(LocalTime.of(0, 40))
                .build());
        treatments.forEach(treatmentService::create);

        List<Appointment> appointments = new ArrayList<>();
        appointments.add(Appointment.builder()
                .id(UUID.randomUUID())
                .dateTime(LocalDateTime.of(
                        2020, 10, 18,
                        16, 30, 0
                ))
                .treatment(treatments.get(1))
                .status(Status.REGISTERED)
                .build());
        appointments.add(Appointment.builder()
                .id(UUID.randomUUID())
                .dateTime(LocalDateTime.of(
                        2010, 8, 18,
                        16, 30, 0
                ))
                .treatment(treatments.get(1))
                .status(Status.CANCELED)
                .build());
        appointments.add(Appointment.builder()
                .id(UUID.randomUUID())
                .dateTime(LocalDateTime.of(
                        2017, 2, 18,
                        16, 30, 0
                ))
                .treatment(treatments.get(0))
                .status(Status.HELD)
                .build());
        appointments.add(Appointment.builder()
                .id(UUID.randomUUID())
                .dateTime(LocalDateTime.of(
                        2017, 2, 18,
                        16, 30, 0
                ))
                .treatment(treatments.get(3))
                .status(Status.HELD)
                .build());
        appointments.add(Appointment.builder()
                .id(UUID.randomUUID())
                .dateTime(LocalDateTime.of(
                        2017, 2, 18,
                        16, 30, 0
                ))
                .treatment(treatments.get(2))
                .status(Status.HELD)
                .build());
        appointments.forEach(appointmentService::create);
    }
}
