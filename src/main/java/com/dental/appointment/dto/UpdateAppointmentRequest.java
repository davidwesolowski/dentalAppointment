package com.dental.appointment.dto;

import com.dental.appointment.entity.Appointment;
import com.dental.appointment.entity.Status;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UpdateAppointmentRequest {

    private String dateTime;
    private Status status;

    public static BiFunction<Appointment, UpdateAppointmentRequest, Appointment> dtoToEntityMapper() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return (appointment, req) -> {
            appointment.setStatus(req.getStatus());
            appointment.setDateTime(LocalDateTime.parse(req.getDateTime(), formatter));
            return  appointment;
        };
    }

}
