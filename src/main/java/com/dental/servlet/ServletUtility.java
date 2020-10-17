package com.dental.servlet;

import javax.servlet.http.HttpServletRequest;

public class ServletUtility {
    public static String parseRequestPath(HttpServletRequest req) {
        String path = req.getPathInfo();
        return path != null ? path : "";
    }
}
