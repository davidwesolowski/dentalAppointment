package com.dental.treatement.dto;

import com.dental.treatement.entity.Complexity;
import com.dental.treatement.entity.Treatment;
import lombok.*;

import java.time.LocalTime;
import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UpdateTreatmentRequest {

    private String name;
    private float cost;
    private Complexity complexity;
    private LocalTime duration;

    public static BiFunction<Treatment, UpdateTreatmentRequest, Treatment> dtoToEntityMapper() {
        return (treatment, updateTreatmentRequest) ->  {
            treatment.setName(updateTreatmentRequest.getName());
            treatment.setComplexity(updateTreatmentRequest.getComplexity());
            treatment.setCost(updateTreatmentRequest.getCost());
            treatment.setDuration(updateTreatmentRequest.getDuration());
            return treatment;
        };
    }
}
