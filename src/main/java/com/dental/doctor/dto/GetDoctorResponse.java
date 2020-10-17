package com.dental.doctor.dto;

import com.dental.doctor.entity.Doctor;
import com.dental.doctor.entity.Specialisation;
import lombok.*;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GetDoctorResponse {

    private String firstName;
    private String lastName;
    private Specialisation specialisation;
    private LocalDate dateOfBirth;

    public static Function<Doctor, GetDoctorResponse> entityToDtoMapper() {
        return doctor -> GetDoctorResponse.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .specialisation(doctor.getSpecialisation())
                .dateOfBirth(doctor.getDateOfBirth())
                .build();
    }
}
