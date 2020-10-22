package com.dental.appointment.model;

import com.dental.appointment.entity.Appointment;
import com.dental.appointment.entity.Status;
import com.dental.treatement.entity.Treatment;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AppointmentEditModel {

    private String treatment;
    private String dateTime;
    private Status status;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static Function<Appointment, AppointmentEditModel> entityToModelMapper(
            Function<Treatment, String> treatmentFunction
    ) {
        return appointment -> AppointmentEditModel.builder()
                .treatment(treatmentFunction.apply(appointment.getTreatment()))
                .dateTime(appointment.getDateTime().format(formatter))
                .status(appointment.getStatus())
                .build();
    }

    public static BiFunction<Appointment, AppointmentEditModel, Appointment> modelToEntityMapper(
            Function<String, Treatment> treatmentFunction) {
        return (appointment, appointmentEditModel) -> {
            appointment.setTreatment(treatmentFunction.apply(appointmentEditModel.getTreatment()));
            appointment.setDateTime(LocalDateTime.parse(appointmentEditModel.getDateTime(), formatter));
            appointment.setStatus(appointmentEditModel.getStatus());
            return appointment;
        };

    }
}
