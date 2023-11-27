package com.example.lab3.servlets;

import com.example.lab3.utils.AdminUtils;
import com.example.lab3.utils.CookieUtils;
import com.example.lab3.structures.User;
import com.github.benmanes.caffeine.cache.Cache;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@WebServlet(value = "/updateUser/*")
public class UpdateUserServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            String numberString = pathInfo.substring(1);
            try {
                int userId = Integer.parseInt(numberString);
                Cache<Integer, User> idUserCache = (Cache<Integer, User>) getServletContext().getAttribute("idUserCache");
                User user = idUserCache.get(userId, k -> null);
                if (user != null) {
                    User cookieUser = CookieUtils.getObjectFromCookie(request, "currentUser", User.class);
                    if (Objects.equals(user, cookieUser) || Objects.equals(Objects.requireNonNull(cookieUser).getUsername(), "admin")) {
                        displayUser(response.getWriter(), user, request);
                    } else {
                        response.getWriter().println("No permissions");
                    }
                } else {
                    response.getWriter().println("No such person in cache");
                }
            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid number format");
            }
        } else {
            response.getWriter().println("Missing user number in the URL");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            String numberString = pathInfo.substring(1);
            try {
                int userId = Integer.parseInt(numberString);
                Cache<Integer, User> idUserCache = (Cache<Integer, User>) getServletContext().getAttribute("idUserCache");
                Cache<String, Integer> idNameCache = (Cache<String, Integer>) getServletContext().getAttribute("idNameCache");
                User user = idUserCache.get(userId, k -> null);
                boolean changedUserWasAdmin = user.getUsername().equals("admin");
                if (user != null) {
                    String newUsername = request.getParameter("username");
                    if (Objects.equals(newUsername, "admin") && AdminUtils.adminExists(getServletContext()) && !changedUserWasAdmin) {
                        response.getWriter().println("Admin already exists");
                        return;
                    }
                    String newPassword = request.getParameter("password");
                    String newEmail = request.getParameter("email");
                    idNameCache.invalidate(user.getUsername());
                    if (newUsername != null && !newUsername.isEmpty()) {
                        user.setUsername(newUsername);
                    }
                    if (newPassword != null && !newPassword.isEmpty()) {
                        user.setPassword(newPassword);
                    }
                    if (newEmail != null && !newEmail.isEmpty()) {
                        user.setEmail(newEmail);
                    }
                    idUserCache.put(userId, user);
                    idNameCache.put(user.getUsername(), userId);
                    if (!AdminUtils.isAdmin(request) || changedUserWasAdmin) {
                        CookieUtils.deleteUserCookies(request, response);
                        CookieUtils.saveObjectToCookie(user, 24 * 60 * 60, "currentUser", response);
                    }
                    response.sendRedirect(request.getContextPath() + "/users?update=true");
                } else {
                    response.getWriter().println("No such person in cache");
                }
            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid number format");
            }
        } else {
            response.getWriter().println("Missing user number in the URL");
        }
    }

    private void displayUser(PrintWriter writer, User user, HttpServletRequest request) {
        writer.println("<html><head><title>User Information</title></head><body>");
        writer.println("<h2>User Information</h2>");
        writer.println("<p>ID: " + user.getId() + "</p>");
        writer.println("<p>Username: " + user.getUsername() + "</p>");
        writer.println("<p>Email: " + user.getEmail() + "</p>");
        writer.println("<form action='" + request.getContextPath() + "/updateUser/" + user.getId() + "' method='post'>");
        writer.println("Update Username: <input type='text' name='username'><br>");
        writer.println("Update Password: <input type='password' name='password'><br>");
        writer.println("Update Email: <input type='text' name='email'><br>");
        writer.println("<input type='submit' value='Update'>");
        writer.println("</form>");
        writer.println("</body></html>");
    }

}
