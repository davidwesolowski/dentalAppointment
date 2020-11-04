package com.dental.appointment.dto;

import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GetAppointmentsResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    public static class Appointment {

        private UUID id;
        private String dateTime;

    }

    @Singular
    List<Appointment> appointments;

    public static Function<Collection<com.dental.appointment.entity.Appointment>, GetAppointmentsResponse> entityToDtoMapper() {
        return appointments -> {
            GetAppointmentsResponse.GetAppointmentsResponseBuilder resp = GetAppointmentsResponse.builder();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            appointments.stream()
                    .map(appointment -> Appointment.builder()
                            .id(appointment.getId())
                            .dateTime(appointment.getDateTime().format(formatter))
                            .build())
                    .forEach(resp::appointment);
            return resp.build();
        };
    }

}
