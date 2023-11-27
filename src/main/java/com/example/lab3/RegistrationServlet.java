package com.example.lab3;

import java.io.*;
import java.util.Map;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "RegistrationServlet", value = "/registration")
public class RegistrationServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Отправка формы для ввода данных (если это необходимо)
        response.getWriter().println("<html><body>");
        response.getWriter().println("<form action=\"registration\" method=\"post\">");
        response.getWriter().println("Username: <input type=\"text\" name=\"username\"><br>");
        response.getWriter().println("Password: <input type=\"password\" name=\"password\"><br>");
        response.getWriter().println("Email: <input type=\"text\" name=\"email\"><br>");
        response.getWriter().println("<input type=\"submit\" value=\"Register\">");
        response.getWriter().println("</form>");
        response.getWriter().println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Cache<Integer, User> idUserCache;
        Cache<String, Integer> idNameCache;
        try {
            idUserCache = (Cache<Integer, User>) getServletContext().getAttribute("idUserCache");
            idNameCache = (Cache<String, Integer>) getServletContext().getAttribute("idNameCache");
        }catch (RuntimeException e){
            response.getWriter().println("UserCache Exception");
            return;
        }
        // Получение параметров из POST-запроса
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Map<Integer,User> idUserMap=idUserCache.asMap();
        int id = 0;
        while (idUserMap.containsKey(id)){
            ++id;
        }
        User newUser = new User();
        newUser.setId(id);
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        idUserCache.put(id,newUser);
        idNameCache.put(username,id);
        response.setStatus(HttpServletResponse.SC_CREATED);
        CookieUtils.saveObjectToCookie(newUser,24*60*60,"currentUser",response);
        response.sendRedirect(request.getContextPath() + "/users/" + id);
    }

}