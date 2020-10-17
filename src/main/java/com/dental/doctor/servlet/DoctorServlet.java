package com.dental.doctor.servlet;

import com.dental.doctor.dto.GetDoctorResponse;
import com.dental.doctor.dto.GetDoctorsResponse;
import com.dental.doctor.entity.Doctor;
import com.dental.doctor.service.DoctorService;
import com.dental.servlet.ServletUtility;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@WebServlet(urlPatterns = DoctorServlet.Paths.DOCTORS + "/*")
public class DoctorServlet extends HttpServlet {

    private final DoctorService doctorService;
    private final Jsonb jsonb = JsonbBuilder.create();

    @Inject
    public DoctorServlet(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String reqPath = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();
        if (Paths.DOCTORS.equals(servletPath)) {
            if (reqPath.matches(Patterns.DOCTORS)) {
                getDoctors(req, resp);
                return;
            }
            else if (reqPath.matches((Patterns.DOCTOR))) {
                getDoctor(req, resp);
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void getDoctor(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UUID id = UUID.fromString(ServletUtility.parseRequestPath(req).replaceAll("/", ""));
        Optional<Doctor> doctor = doctorService.find(id);
        if (doctor.isPresent()) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            resp.getWriter().write(jsonb.toJson(GetDoctorResponse.entityToDtoMapper().apply(doctor.get())));
        }
        else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getDoctors(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Doctor> doctors = doctorService.findAll();
        if (doctors.size() > 0) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            resp.getWriter().write(jsonb.toJson(GetDoctorsResponse.entityToDtoMapper().apply(doctors)));
        }
        else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    public static class Paths {
        public static final String DOCTORS = "/api/doctors";
    }

    public static class Patterns {
        public static final String DOCTOR = "^/[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}/?$";
        public static final String DOCTORS = "^/?$";
    }
}
