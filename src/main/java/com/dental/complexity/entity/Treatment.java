package com.dental.complexity.entity;

import com.dental.complexity.entity.Complexity;
import lombok.*;

import java.time.LocalTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Treatment {
    private UUID id;
    private String name;
    private float cost;
    private Complexity complexity;
    private LocalTime duration;
}
