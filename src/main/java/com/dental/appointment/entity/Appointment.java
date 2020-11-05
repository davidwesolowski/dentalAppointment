package com.dental.appointment.entity;

import com.dental.treatement.entity.Treatment;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
@Entity
@Table(name = "appointments")
public class Appointment implements Serializable {

    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "treatment")
    private Treatment treatment;
    private Status status;
}
