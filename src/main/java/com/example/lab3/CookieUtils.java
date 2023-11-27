package com.example.lab3;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.*;
import org.apache.commons.codec.binary.Base64;

public interface CookieUtils {
    static void saveObjectToCookie(Object obj, int maxAgeInSeconds, String cookieName, HttpServletResponse response) throws JsonProcessingException {
        String encodedJson = Base64.encodeBase64URLSafeString(DAO.objectToJson(obj).getBytes());
        Cookie cookie = new Cookie(cookieName, encodedJson);
        cookie.setMaxAge(maxAgeInSeconds); // Время жизни куки в секундах
        response.addCookie(cookie);
    }

    static <T> T getObjectFromCookie(HttpServletRequest request, String cookieName, Class<T> valueType) throws JsonProcessingException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    try {
                        // Декодирование значения куки из Base64 и преобразование в объект
                        String decodedJson = new String(Base64.decodeBase64(cookie.getValue()));
                        return DAO.jsonToObject(decodedJson, valueType);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    static boolean checkAuthentication(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("currentUser".equals(cookie.getName())) {

                    return true;
                }
            }
        }
        return false;
    }
    static void deleteUserCookies(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
    }

}
