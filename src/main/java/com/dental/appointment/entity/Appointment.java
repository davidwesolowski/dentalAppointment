package com.dental.appointment.entity;

import com.dental.complexity.entity.Treatment;
import com.dental.doctor.entity.Doctor;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Appointment {
    private UUID id;
    private LocalDateTime dateTime;
    private Doctor doctor;
    private Treatment treatment;
    private Status status;
}
