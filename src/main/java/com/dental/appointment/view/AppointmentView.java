package com.dental.appointment.view;

import com.dental.appointment.entity.Appointment;
import com.dental.appointment.entity.Status;
import com.dental.appointment.model.AppointmentModel;
import com.dental.appointment.service.AppointmentService;
import com.dental.doctor.entity.Doctor;
import com.dental.treatement.entity.Treatment;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
@Named
public class AppointmentView {

    private final AppointmentService appointmentService;

    @Getter
    @Setter
    private String id;

    @Getter
    private AppointmentModel appointment;

    @Inject
    public AppointmentView(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    public void init() {
        Optional<Appointment> appointment = appointmentService.find(UUID.fromString(id));
        appointment.ifPresentOrElse(
                original -> this.appointment = AppointmentModel.entityToModelMapper().apply(original),
                () -> {
                    try {
                        FacesContext.getCurrentInstance().getExternalContext()
                                .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Appointment not found!");
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
        );
    }

}
