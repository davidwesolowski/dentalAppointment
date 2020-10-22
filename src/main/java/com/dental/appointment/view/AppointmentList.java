package com.dental.appointment.view;

import com.dental.appointment.model.AppointmentsModel;
import com.dental.appointment.service.AppointmentService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.UUID;

@RequestScoped
@Named
public class AppointmentList implements Serializable {

    private final AppointmentService appointmentService;
    private AppointmentsModel appointments;

    @Inject
    public AppointmentList(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    public AppointmentsModel getAppointments(String id) {
        if (appointments == null)
            appointments = AppointmentsModel.entityToModelMapper()
                    .apply(appointmentService.findAppointmentsByTreatment(UUID.fromString(id)));
        return appointments;
    }

    public String deleteAppointment(AppointmentsModel.Appointment appointment) {
        appointmentService.delete(appointment.getId());
        return "treatment_view?faces-redirect=true&includeViewParams=true";
    }
}
