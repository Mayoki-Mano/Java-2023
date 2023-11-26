package com.example.lab3;

import java.io.*;

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
        String email = request.getParameter("password");
        String password = request.getParameter("email");
        int id = idUserCache.asMap().size() + 1;
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        idUserCache.put(id,newUser);
        idNameCache.put(username,id);
        response.setStatus(HttpServletResponse.SC_CREATED);

        // Создание сессии
        HttpSession session = request.getSession(true);
        // Установка атрибута с идентификатором пользователя в сессии
        session.setAttribute("userid", id);

        // Создание cookie с идентификатором пользователя
        Cookie userCookie = new Cookie("userid", String.valueOf(id));
        // Установка времени жизни cookie (например, 1 день)
        userCookie.setMaxAge(24 * 60 * 60);
        // Добавление cookie в ответ
        response.addCookie(userCookie);

        // Редирект на страницу /users/userid
        response.sendRedirect(request.getContextPath() + "/users/" + id);
    }

}