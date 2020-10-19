package com.dental.treatement.model;

import com.dental.treatement.entity.Treatment;
import com.dental.treatement.service.TreatmentService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

@FacesConverter(forClass = TreatmentModel.class, managed = true)
public class TreatmentModelConverter implements Converter<TreatmentModel> {

    private final TreatmentService treatmentService;

    @Inject
    public TreatmentModelConverter(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @Override
    public TreatmentModel getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value == null || value.isBlank()) return null;
        String id = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        Optional<Treatment> treatment = treatmentService.find(UUID.fromString(value));
        return treatment.isEmpty() ? null : TreatmentModel.entityToModelMapper().apply(treatment.get());
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, TreatmentModel value) {
        return value == null ? "" : value.getName();
    }
}
