package com.dental.doctor.servlet;

import com.dental.doctor.entity.Doctor;
import com.dental.doctor.service.AvatarService;
import com.dental.doctor.service.DoctorService;
import com.dental.servlet.ServletUtility;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@WebServlet(urlPatterns = AvatarServlet.Paths.AVATARS + "/*", initParams = {
        @WebInitParam(name = "avatarPath", value = "D:\\Users\\Dawid\\Desktop\\JEE\\avatars")
})
@MultipartConfig
public class AvatarServlet extends HttpServlet {

    private final DoctorService doctorService;
    private final AvatarService avatarService;
    private String avatarPath;

    @Override
    public void init(ServletConfig config) throws ServletException {
        avatarPath = config.getInitParameter("avatarPath");
        super.init(config);
    }

    @Inject
    public AvatarServlet(DoctorService doctorService, AvatarService avatarService) {
        this.doctorService = doctorService;
        this.avatarService = avatarService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String reqPath = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();
        if (Paths.AVATARS.matches(servletPath)) {
            if (reqPath.matches(Patterns.AVATAR)) {
                getAvatar(req, resp);
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqPath = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (reqPath.matches(Patterns.AVATAR)) {
                postAvatar(req, resp);
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqPath = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (reqPath.matches(Patterns.AVATAR)) {
                putAvatar(req, resp);
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String reqPath = ServletUtility.parseRequestPath(req);
        String servletPath = req.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (reqPath.matches(Patterns.AVATAR)) {
                deleteAvatar(req, resp);
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void getAvatar(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UUID id = UUID.fromString(ServletUtility.parseRequestPath(req).replaceAll("/", ""));
        Optional<Doctor> doctor = doctorService.find(id);
        if (doctor.isPresent()) {
            String avatarPath = doctor.get().getAvatarPath();
            if (avatarPath != null) {
                Optional<byte[]> avatar = avatarService.find(java.nio.file.Paths.get(avatarPath));
                String ext = avatarPath.split("\\.")[1].toLowerCase();
                if (avatar.isPresent()) {
                    if (ext.equals("jpg") || ext.equals("jpeg"))
                        resp.addHeader(HttpHeaders.CONTENT_TYPE, "image/jpeg");
                    else
                        resp.addHeader(HttpHeaders.CONTENT_TYPE, "image/" + ext);
                    resp.setContentLength(avatar.get().length);
                    resp.getOutputStream().write(avatar.get());
                    resp.setStatus(HttpServletResponse.SC_OK);
                }
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void postAvatar(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UUID id = UUID.fromString(ServletUtility.parseRequestPath(req).replaceAll("/",""));
        Optional<Doctor> doctor = doctorService.find(id);
        if (doctor.isPresent()) {
            if (doctor.get().getAvatarPath() == null) {
                Part avatar = req.getPart(Parameters.AVATAR);
                byte[] avatarBytes = avatar.getInputStream().readAllBytes();
                if (avatarBytes.length > 0) {
                    String ext = avatar.getSubmittedFileName().split("\\.")[1].toLowerCase();
                    String avatarPathWithId = avatarPath + "\\" + id.toString() + "." + ext;
                    avatarService.create(avatarBytes, java.nio.file.Paths.get(avatarPathWithId));
                    doctorService.updateAvatar(id, avatarPathWithId);
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                }
                else {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
            else {
                resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            }
        }
        else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void putAvatar(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UUID id = UUID.fromString(ServletUtility.parseRequestPath(req).replaceAll("/", ""));
        Optional<Doctor> doctor = doctorService.find(id);
        if (doctor.isPresent()) {
            Doctor currentDoctor =  doctor.get();
            if (currentDoctor.getAvatarPath() != null) {
                Part avatar = req.getPart(Parameters.AVATAR);
                byte[] avatarBytes = avatar.getInputStream().readAllBytes();
                if (avatarBytes.length > 0) {
                    String ext = avatar.getSubmittedFileName().split("\\.")[1];
                    String avatarPathWithId = avatarPath + "\\" + id.toString() + "." + ext;
                    avatarService.delete(java.nio.file.Paths.get(currentDoctor.getAvatarPath()));
                    avatarService.update(avatarBytes, java.nio.file.Paths.get(avatarPathWithId));
                    doctorService.updateAvatar(id, avatarPathWithId);
                    resp.setStatus(HttpServletResponse.SC_OK);
                }
                else {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
            else {
                resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void deleteAvatar(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UUID id = UUID.fromString(ServletUtility.parseRequestPath(req).replaceAll("/", ""));
        Optional<Doctor> doctor = doctorService.find(id);
        if (doctor.isPresent()) {
            String avatarPath = doctor.get().getAvatarPath();
            if (avatarPath != null) {
                avatarService.delete(java.nio.file.Paths.get(avatarPath));
                doctorService.updateAvatar(id, null);
                resp.setStatus(HttpServletResponse.SC_OK);
            }
            else {
                resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            }
        }
        else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public static class Paths {
        public static final String AVATARS = "/api/avatars";
    }

    public static class Patterns {
        public static final String AVATAR = "^/[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}/?$";
    }

    public static class Parameters {
        public static final String AVATAR = "avatar";
    }
}
