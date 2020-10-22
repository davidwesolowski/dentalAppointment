package com.dental.appointment.model;

import lombok.*;

import java.io.Serializable;
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
public class AppointmentsModel implements Serializable {

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
    private List<Appointment> appointments;

    public static Function<Collection<com.dental.appointment.entity.Appointment>, AppointmentsModel> entityToModelMapper() {
        return appointments -> {
            AppointmentsModel.AppointmentsModelBuilder modelBuilder = AppointmentsModel.builder();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            appointments.stream()
                    .map(appointment -> Appointment.builder()
                            .id(appointment.getId())
                            .dateTime(appointment.getDateTime().format(formatter))
                            .build())
                    .forEach(modelBuilder::appointment);
            return modelBuilder.build();
        };
    }

}
