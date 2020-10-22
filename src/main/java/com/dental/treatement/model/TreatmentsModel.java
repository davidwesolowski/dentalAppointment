package com.dental.treatement.model;

import lombok.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class TreatmentsModel implements Serializable {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    public static class Treatment {

        private UUID id;
        private String name;

    }

    @Singular
    private List<Treatment> treatments;

    public static Function<Collection<com.dental.treatement.entity.Treatment>, TreatmentsModel> entityToModelMapper() {
        return treatments -> {
            TreatmentsModel.TreatmentsModelBuilder modelBuilder = TreatmentsModel.builder();
            treatments.stream()
                    .map(treatment -> Treatment.builder()
                            .id(treatment.getId())
                            .name(treatment.getName())
                            .build())
                    .forEach(modelBuilder::treatment);
            return modelBuilder.build();
        };
    }
}
