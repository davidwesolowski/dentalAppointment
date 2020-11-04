package com.dental.appointment.controller;

import com.dental.appointment.dto.CreateAppointmentRequest;
import com.dental.appointment.dto.GetAppointmentResponse;
import com.dental.appointment.dto.GetAppointmentsResponse;
import com.dental.appointment.dto.UpdateAppointmentRequest;
import com.dental.appointment.entity.Appointment;
import com.dental.appointment.service.AppointmentService;
import com.dental.treatement.entity.Treatment;
import com.dental.treatement.service.TreatmentService;
import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor
@Path("/treatments/{treatmentId}/appointments")
public class AppointmentController {

    AppointmentService appointmentService;
    TreatmentService treatmentService;

    @Inject
    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Inject
    public void setTreatmentService(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAppointment(@PathParam("treatmentId") UUID treatmentId, CreateAppointmentRequest appointmentRequest) {
        Optional<Treatment> treatmentExists = treatmentService.find(treatmentId);
        if (treatmentExists.isPresent()) {
            appointmentRequest.setTreatment(treatmentExists.get().getName());
            Appointment appointment = CreateAppointmentRequest.dtoToEntityMapper(
                    treatment -> treatmentService.findByName(treatment).orElseThrow()
            ).apply(appointmentRequest);
            appointmentService.create(appointment);
            return Response.created(URI.create("/treatments/" + treatmentId.toString() + "/appointments/" + appointment.getId().toString())).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppointments(@PathParam("treatmentId") UUID treatmentId) {
        if (checkExistingTreatment(treatmentId)) {
            List<Appointment> appointments = appointmentService.findAppointmentsByTreatment(treatmentId);
            return Response.ok(GetAppointmentsResponse.entityToDtoMapper().apply(appointments)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{appointmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppointment(@PathParam("treatmentId") UUID treatmentId, @PathParam("appointmentId") UUID appointmentId) {
        if (checkExistingTreatment(treatmentId) && checkExistingAppointment(appointmentId)) {
            Optional<Appointment> appointment = appointmentService.findAppointmentByTreatment(treatmentId, appointmentId);
            if (appointment.isPresent()) {
                return Response.ok(GetAppointmentResponse.entityToDtoMapper().apply(appointment.get())).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{appointmentId}")
    public Response deleteAppointment(@PathParam("treatmentId") UUID treatmentId,
                                      @PathParam("appointmentId") UUID appointmentId) {
        if (checkExistingTreatment(treatmentId) && checkExistingAppointment(appointmentId)) {
            appointmentService.delete(appointmentId);
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{appointmentId}")
    public Response updateAppointment(@PathParam("treatmentId") UUID treatmentId,
                                      @PathParam("appointmentId") UUID appointmentId,
                                      UpdateAppointmentRequest appointmentRequest) {
        Optional<Treatment> treatmentExists = treatmentService.find(treatmentId);
        if (treatmentExists.isPresent()) {
            Optional<Appointment> appointmentExists = appointmentService.find(appointmentId);
            if (appointmentExists.isPresent()) {
                Appointment appointment = UpdateAppointmentRequest.dtoToEntityMapper().apply(appointmentExists.get(),
                        appointmentRequest);
                appointmentService.update(appointment);
                return Response.ok().build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    private boolean checkExistingTreatment(UUID id) {
        return treatmentService.find(id).isPresent();
    }

    private boolean checkExistingAppointment(UUID id) {
        return appointmentService.find(id).isPresent();
    }
}
