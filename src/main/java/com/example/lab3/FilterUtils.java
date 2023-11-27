package com.example.lab3;

public class FilterUtils {
    public static boolean isPathAllowed(String servletPath, String[] allowedPaths) {
        for (String allowedPath : allowedPaths) {
            if (servletPath.equals(allowedPath)) {
                return true; // Путь найден в списке допустимых
            }
        }
        return false; // Путь не найден в списке допустимых
    }
}
