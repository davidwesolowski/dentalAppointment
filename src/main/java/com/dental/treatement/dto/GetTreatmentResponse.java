package com.dental.treatement.dto;

import com.dental.treatement.entity.Complexity;
import com.dental.treatement.entity.Treatment;
import lombok.*;

import java.time.LocalTime;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class GetTreatmentResponse {

    private String name;
    private float cost;
    private Complexity complexity;
    private LocalTime duration;

    public static Function<Treatment, GetTreatmentResponse> entityToDtoMapper() {
        return treatment -> {
            GetTreatmentResponse.GetTreatmentResponseBuilder resp = GetTreatmentResponse.builder();
            resp.name(treatment.getName());
            resp.complexity(treatment.getComplexity());
            resp.cost(treatment.getCost());
            resp.duration(treatment.getDuration());
            return resp.build();
        };
    }
}
