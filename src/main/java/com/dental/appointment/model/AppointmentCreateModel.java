package com.dental.appointment.model;

import com.dental.appointment.entity.Appointment;
import com.dental.appointment.entity.Status;
import com.dental.treatement.entity.Treatment;
import com.dental.treatement.model.TreatmentModel;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AppointmentCreateModel {

    private TreatmentModel treatment;
    private String dateTime;
    private Status status;

    public static Function<AppointmentCreateModel, Appointment> modelToEntityMapper(
            Function<String, Treatment> treatmentFunction
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return appointmentModel -> Appointment.builder()
                .id(UUID.randomUUID())
                .treatment(treatmentFunction.apply(appointmentModel.getTreatment().getName()))
                .status(appointmentModel.getStatus())
                .dateTime(LocalDateTime.parse(appointmentModel.getDateTime(), formatter))
                .build();
    }
}
