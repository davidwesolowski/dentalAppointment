package com.dental.treatement.controller;

import com.dental.treatement.dto.CreateTreatmentRequest;
import com.dental.treatement.dto.GetTreatmentResponse;
import com.dental.treatement.dto.GetTreatmentsResponse;
import com.dental.treatement.dto.UpdateTreatmentRequest;
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
@Path("/treatments")
public class TreatmentController {

    private TreatmentService treatmentService;

    @Inject
    public void setTreatmentService(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTreatments() {
        List<Treatment> treatments = treatmentService.findAll();
        return Response.ok(GetTreatmentsResponse.entityToDtoMapper().apply(treatments)).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTreatment(@PathParam("id") UUID id) {
        Optional<Treatment> treatment = treatmentService.find(id);
        if (treatment.isPresent()) {
            return Response.ok(GetTreatmentResponse.entityToDtoMapper().apply(treatment.get())).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTreatment(CreateTreatmentRequest treatmentRequest) {
        if (checkExistingByName(treatmentRequest.getName())) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        Treatment treatment = CreateTreatmentRequest.dtoToEntityMapper().apply(treatmentRequest);
        treatmentService.create(treatment);
        return Response.created(URI.create("/treatments/"+treatment.getId().toString())).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteTreatment(@PathParam("id") UUID id) {
        Optional<Treatment> treatmentExists = treatmentService.find(id);
        if (treatmentExists.isPresent()) {
            treatmentService.delete(id);
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTreatment(@PathParam("id") UUID id, UpdateTreatmentRequest updateRequest) {
        Optional<Treatment> treatmentAbleToUpdate = treatmentService.findByName(updateRequest.getName());
        if (treatmentAbleToUpdate.isPresent() && !treatmentAbleToUpdate.get().getId().equals(id)) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        Optional<Treatment> treatmentExists = treatmentService.find(id);
        if (treatmentExists.isPresent()) {
            Treatment treatment = UpdateTreatmentRequest.dtoToEntityMapper().apply(treatmentExists.get(),
                    updateRequest);
            treatmentService.update(treatment);
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    private boolean checkExistingByName(String name) {
        return treatmentService.findByName(name).isPresent();
    }
}
