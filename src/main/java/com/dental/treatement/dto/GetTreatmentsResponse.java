package com.dental.treatement.dto;

import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GetTreatmentsResponse {

    @Getter
    @Setter
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Treatment {
        private UUID id;
        private String name;
        private float cost;
    }

    @Singular
    List<Treatment> treatments;

    public static Function<Collection<com.dental.treatement.entity.Treatment>, GetTreatmentsResponse> entityToDtoMapper() {
        return treatments -> {
            GetTreatmentsResponse.GetTreatmentsResponseBuilder resp = GetTreatmentsResponse.builder();
            treatments.stream()
                    .map(treatment -> Treatment.builder()
                        .id(treatment.getId())
                        .name(treatment.getName())
                        .cost(treatment.getCost())
                        .build())
                    .forEach(resp::treatment);
            return resp.build();
        };
    }

}
