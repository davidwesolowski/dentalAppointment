package com.dental.appointment.dto;

import com.dental.appointment.entity.Appointment;
import com.dental.appointment.entity.Status;
import com.dental.treatement.entity.Treatment;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GetAppointmentResponse {

    private Treatment treatment;
    private Status status;
    private String dateTime;

    public static Function<Appointment, GetAppointmentResponse> entityToDtoMapper() {
        return appointment -> {
            GetAppointmentResponse.GetAppointmentResponseBuilder resp = GetAppointmentResponse.builder();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            resp.treatment(appointment.getTreatment());
            resp.dateTime(appointment.getDateTime().format(formatter));
            resp.status(appointment.getStatus());
            return resp.build();
        };
    }

}
