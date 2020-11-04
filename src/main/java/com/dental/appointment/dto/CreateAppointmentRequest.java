package com.dental.appointment.dto;

import com.dental.appointment.entity.Appointment;
import com.dental.appointment.entity.Status;
import com.dental.treatement.entity.Treatment;
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
public class CreateAppointmentRequest {

    private String treatment;
    private String dateTime;
    private Status status;

    public static Function<CreateAppointmentRequest, Appointment> dtoToEntityMapper(
            Function<String, Treatment> treatmentFunction
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return req -> Appointment.builder()
                .id(UUID.randomUUID())
                .treatment(treatmentFunction.apply(req.getTreatment()))
                .status(req.getStatus())
                .dateTime(LocalDateTime.parse(req.getDateTime(), formatter))
                .build();
    }
}
