package com.example.lab3.servlets;

import com.example.lab3.utils.CookieUtils;
import com.example.lab3.structures.User;
import com.github.benmanes.caffeine.cache.Cache;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "RegistrationServlet", value = "/registration")
public class RegistrationServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
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
        String username = request.getParameter("username");
        if (username.equals("admin") && idNameCache.get("admin",k->null)!=null){
            response.getWriter().println("Admin already exists");
            return;
        }
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