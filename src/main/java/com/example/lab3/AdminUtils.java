package com.example.lab3;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AdminUtils {
    static boolean isAdmin(HttpServletRequest request) throws JsonProcessingException {
        boolean isAdmin = false;
        User user = CookieUtils.getObjectFromCookie(request, "currentUser", User.class);
        if (user != null) {
            if (user.getUsername().equals("admin")) {
                isAdmin = true;
            }
        }
        return isAdmin;
    }

}
