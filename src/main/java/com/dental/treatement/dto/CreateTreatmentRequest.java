package com.dental.treatement.dto;

import com.dental.treatement.entity.Complexity;
import com.dental.treatement.entity.Treatment;
import lombok.*;

import java.time.LocalTime;
import java.util.UUID;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CreateTreatmentRequest {

    private String name;
    private float cost;
    private Complexity complexity;
    private LocalTime duration;

    public static Function<CreateTreatmentRequest, Treatment> dtoToEntityMapper() {
        return req -> Treatment.builder()
                .id(UUID.randomUUID())
                .name(req.getName())
                .complexity(req.getComplexity())
                .cost(req.getCost())
                .duration(req.getDuration())
                .build();
    }
}
