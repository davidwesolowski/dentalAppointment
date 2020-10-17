package com.dental.doctor.dto;

import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GetDoctorsResponse {

    @Getter
    @Setter
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Doctor {
        private UUID id;
        private String firstName;
        private String lastName;
    }

    @Singular
    List<Doctor> doctors;

    public static Function<Collection<com.dental.doctor.entity.Doctor>, GetDoctorsResponse> entityToDtoMapper() {
        return doctors -> {
            GetDoctorsResponse.GetDoctorsResponseBuilder resp = GetDoctorsResponse.builder();
            doctors.stream()
                    .map(doctor -> Doctor.builder()
                        .id(doctor.getId())
                        .firstName(doctor.getFirstName())
                        .lastName(doctor.getLastName())
                        .build())
                    .forEach(resp::doctor);
            return resp.build();
        };
    }
}
