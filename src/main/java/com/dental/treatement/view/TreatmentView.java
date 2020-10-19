package com.dental.treatement.view;

import com.dental.treatement.entity.Treatment;
import com.dental.treatement.model.TreatmentModel;
import com.dental.treatement.service.TreatmentService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
@Named
public class TreatmentView implements Serializable {

    private final TreatmentService treatmentService;

    @Getter
    @Setter
    private String id;

    @Getter
    private TreatmentModel treatment;

    @Inject
    public TreatmentView(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    public void init() throws IOException {
        Optional<Treatment> treatment = treatmentService.find(UUID.fromString(id));
        treatment.ifPresentOrElse(
                original -> this.treatment = TreatmentModel.entityToModelMapper().apply(original),
                () -> {
                    try {
                        FacesContext.getCurrentInstance().getExternalContext()
                                .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Treatment not found!");
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
        );
    }

}
