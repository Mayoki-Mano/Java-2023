package com.example.lab3.utils;

import com.example.lab3.structures.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.benmanes.caffeine.cache.Cache;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

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
    static boolean adminExists(ServletContext context) {
        Cache<String, Integer> idNameCache = (Cache<String, Integer>) context.getAttribute("idNameCache");
        return idNameCache.get("admin", k->-1)!=-1;
    }

}
