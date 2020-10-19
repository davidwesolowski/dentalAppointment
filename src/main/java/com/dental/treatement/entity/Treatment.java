package com.dental.treatement.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
public class Treatment implements Serializable {
    private UUID id;
    private String name;
    private float cost;
    private Complexity complexity;
    private LocalTime duration;
}
