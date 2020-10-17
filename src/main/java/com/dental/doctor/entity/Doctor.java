package com.dental.doctor.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class Doctor implements Serializable {
    private UUID id;
    private String firstName;
    private String lastName;
    private String login;
    private Specialisation specialisation;
    private Position position;
    private LocalDate dateOfBirth;
    private String avatarPath;
}
