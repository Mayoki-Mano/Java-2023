package com.example.lab3.filters;

public class FilterUtils {
    public static boolean isPathAllowed(String servletPath, String[] allowedPaths) {
        for (String allowedPath : allowedPaths) {
            if (servletPath.equals(allowedPath)) {
                return true;
            }
        }
        return false;
    }
}
