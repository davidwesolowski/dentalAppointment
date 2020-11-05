package com.dental.treatement.entity;

import com.dental.appointment.entity.Appointment;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
@Entity
@Table(name = "treatments")
public class Treatment implements Serializable {

    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();
    private String name;
    private float cost;
    private Complexity complexity;
    private LocalTime duration;


    @OneToMany(mappedBy = "treatment", cascade = CascadeType.REMOVE)
    List<Appointment> appointments;
}
