package com.dental.appointment.view;

import com.dental.appointment.entity.Status;
import com.dental.appointment.model.AppointmentCreateModel;
import com.dental.appointment.service.AppointmentService;
import com.dental.treatement.model.TreatmentModel;
import com.dental.treatement.service.TreatmentService;
import com.dental.utils.Utils;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
@Named
@NoArgsConstructor
public class AppointmentCreate implements Serializable {

    private AppointmentService appointmentService;
    private TreatmentService treatmentService;

    @Getter
    private AppointmentCreateModel appointment;

    @Getter
    private List<TreatmentModel> treatments;

    @Getter
    private List<Status> statuses;

    @Inject
    public AppointmentCreate(AppointmentService appointmentService, TreatmentService treatmentService) {
        this.appointmentService = appointmentService;
        this.treatmentService = treatmentService;
        statuses = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        treatments = treatmentService.findAll()
                            .stream()
                            .map(TreatmentModel.entityToModelMapper())
                            .collect(Collectors.toList());
        appointment = AppointmentCreateModel.builder().build();
        statuses.add(Status.CANCELED);
        statuses.add(Status.HELD);
        statuses.add(Status.REGISTERED);
    }

    public String createAppointment() {
        if (Utils.validateDateTime(appointment.getDateTime()) && appointment.getStatus() != null) {
            appointmentService.create(AppointmentCreateModel.modelToEntityMapper(
                    treatment -> treatmentService.findByName(treatment).orElseThrow()
            ).apply(appointment));
            return "/treatment/treatment_list?faces-redirect=true";
        }
        return null;
    }

    public String cancelCreatingAppointment() {
        return "/treatment/treatment_list?faces-redirect=true";
    }
}
