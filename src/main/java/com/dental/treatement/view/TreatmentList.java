package com.dental.treatement.view;

import com.dental.treatement.model.TreatmentsModel;
import com.dental.treatement.service.TreatmentService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@RequestScoped
@Named
public class TreatmentList implements Serializable {

    private final TreatmentService treatmentService;
    private TreatmentsModel treatments;

    @Inject
    public TreatmentList(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    public TreatmentsModel getTreatments() {
        if (treatments == null)
            treatments = TreatmentsModel.entityToModelMapper().apply(treatmentService.findAll());
        return treatments;
    }

    public String deleteTreatment(TreatmentsModel.Treatment treatment) {
        treatmentService.delete(treatment.getId());
        return "treatment_list?faces-redirect=true";
    }
}
