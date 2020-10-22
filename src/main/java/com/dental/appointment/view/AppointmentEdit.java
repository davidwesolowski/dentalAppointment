package com.dental.appointment.view;

import com.dental.appointment.entity.Appointment;
import com.dental.appointment.entity.Status;
import com.dental.appointment.model.AppointmentEditModel;
import com.dental.appointment.service.AppointmentService;
import com.dental.treatement.entity.Treatment;
import com.dental.treatement.model.TreatmentModel;
import com.dental.treatement.service.TreatmentService;
import com.dental.utils.Utils;
import lombok.Getter;
import lombok.Setter;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ViewScoped
@Named
public class AppointmentEdit implements Serializable {

    private final AppointmentService appointmentService;
    private final TreatmentService treatmentService;

    @Getter
    @Setter
    private String id;

    @Getter
    private AppointmentEditModel appointment;

    @Getter
    private List<TreatmentModel> treatments;

    @Getter
    private List<Status> statuses;

    @Inject
    public AppointmentEdit(AppointmentService appointmentService, TreatmentService treatmentService) {
        this.appointmentService = appointmentService;
        this.treatmentService = treatmentService;
        statuses = new ArrayList<>();
    }

    public void init() throws IOException {
        if (Utils.validateId(id)) {
            Optional<Appointment> appointment = appointmentService.find(UUID.fromString(id));
            appointment.ifPresentOrElse(
                    original -> this.appointment = AppointmentEditModel.entityToModelMapper(
                            Treatment::getName)
                            .apply(original),
                    () -> {
                        try {
                            FacesContext.getCurrentInstance()
                                    .getExternalContext()
                                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Appointment not found");
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    }
            );

            treatments = treatmentService.findAll()
                    .stream()
                    .filter(this::isTreatmentNotExistInAppointment)
                    .map(TreatmentModel.entityToModelMapper())
                    .collect(Collectors.toList());
            statuses.add(Status.CANCELED);
            statuses.add(Status.HELD);
            statuses.add(Status.REGISTERED);
        }
    }

    private boolean isTreatmentNotExistInAppointment(Treatment treatment) {
        return !treatment.getName().equals(this.appointment.getTreatment());
    }

    public String editAppointment() {
        if (Utils.validateDateTime(appointment.getDateTime()))
        {
            appointmentService.update(AppointmentEditModel.modelToEntityMapper(
                    treatment -> treatmentService.findByName(treatment).orElseThrow()
            ).apply(appointmentService.find(UUID.fromString(id)).orElseThrow(), appointment));

            return "/treatment/treatment_list?faces-redirect=true";
        }
        return "/appointment/appointment_edit.xhtml?faces-redirect=true&includeViewParams=true";
    }

    public String cancelEditingAppointment() {
        return "/treatment/treatment_list?faces-redirect=true";
    }
}
