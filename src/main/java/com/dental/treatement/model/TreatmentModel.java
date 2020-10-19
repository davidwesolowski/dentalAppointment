package com.dental.treatement.model;

import com.dental.treatement.entity.Complexity;
import com.dental.treatement.entity.Treatment;
import lombok.*;

import java.time.LocalTime;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class TreatmentModel {

    private String name;
    private Complexity complexity;
    private float cost;
    private LocalTime duration;

    public static Function<Treatment, TreatmentModel> entityToModelMapper() {
        return treatment -> TreatmentModel.builder()
                .name(treatment.getName())
                .complexity(treatment.getComplexity())
                .cost(treatment.getCost())
                .duration(treatment.getDuration())
                .build();
    }
}

