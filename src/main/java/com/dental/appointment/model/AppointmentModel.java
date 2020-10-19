package com.dental.appointment.model;

import com.dental.appointment.entity.Appointment;
import com.dental.appointment.entity.Status;
import com.dental.doctor.entity.Doctor;
import com.dental.treatement.entity.Treatment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AppointmentModel {

    private Doctor doctor;
    private Treatment treatment;
    private LocalDateTime dateTime;
    private Status status;

    public static Function<Appointment, AppointmentModel> entityToModelMapper() {
        return appointment -> AppointmentModel.builder()
                .doctor(appointment.getDoctor())
                .treatment(appointment.getTreatment())
                .dateTime(appointment.getDateTime())
                .status(appointment.getStatus())
                .build();
    }
}
