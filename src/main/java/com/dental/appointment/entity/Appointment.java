package com.dental.appointment.entity;

import com.dental.treatement.entity.Treatment;
import com.dental.doctor.entity.Doctor;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
public class Appointment implements Serializable {
    private UUID id;
    private LocalDateTime dateTime;
    private Doctor doctor;
    private Treatment treatment;
    private Status status;
}
