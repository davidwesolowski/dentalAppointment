package com.dental.appointment.model;

import com.dental.appointment.entity.Appointment;
import com.dental.doctor.entity.Doctor;
import com.dental.treatement.entity.Treatment;
import com.dental.treatement.model.TreatmentsModel;
import lombok.*;

import java.io.Serializable;
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
        private Doctor doctor;
        private Treatment treatment;

    }

    @Singular
    private List<Appointment> appointments;

    public static Function<Collection<com.dental.appointment.entity.Appointment>, AppointmentsModel> entityToModelMapper() {
        return appointments -> {
            AppointmentsModel.AppointmentsModelBuilder modelBuilder = AppointmentsModel.builder();
            appointments.stream()
                    .map(appointment -> Appointment.builder()
                            .id(appointment.getId())
                            .doctor(appointment.getDoctor())
                            .treatment(appointment.getTreatment())
                            .build())
                    .forEach(modelBuilder::appointment);
            return modelBuilder.build();
        };
    }

}
