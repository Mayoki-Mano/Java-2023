package com.example.lab3;

import com.github.benmanes.caffeine.cache.Cache;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "usersServlet", value = "/users")
public class UsersServlet extends HttpServlet implements UserDAO {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Cache<Integer, User> idUserCache = (Cache<Integer, User>) getServletContext().getAttribute("idUserCache");
        Map<Integer, User> users = idUserCache.asMap();
        response.setContentType("text/html");;
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>USERS:</h1>");
        for (Map.Entry<Integer, User> entry : users.entrySet()) {
            Integer userId = entry.getKey();
            User user = entry.getValue();
            response.getWriter().println("<p>User ID: " + userId + ", Name: " + user.getUsername()+"<p>");
        }
        out.println("</body></html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


    }
}
