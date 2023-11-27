package com.example.lab3;

import com.github.benmanes.caffeine.cache.Cache;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
@WebServlet(name = "UserDetailsServlet", urlPatterns = {"/users/*"})
public class UserDetailsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Получение пути из URL после "/users/"
        String pathInfo = request.getPathInfo();

        if (pathInfo != null && pathInfo.length() > 1) {
            // Извлечение числа из пути (за исключением первого символа '/')
            String numberString = pathInfo.substring(1);

            try {
                // Преобразование числа из строки
                int number = Integer.parseInt(numberString);
                Cache<Integer, User> idUserCache = (Cache<Integer, User>) getServletContext().getAttribute("idUserCache");
                Map<Integer, User> users = idUserCache.asMap();
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<html><body>");
                out.println("<h1>USER №"+number+":</h1>");
                User user = users.get(number);
                out.println("<p>User ID: " + number + ", Name: " + user.getUsername()+"<p>");
                out.println("<p>Email: "+user.getEmail()+"<p>");
                out.println("<p><a href=\""+request.getContextPath()+"/updateUser/"+user.getId()+"\">Update person</a></p>");
                out.println("<p><a href=\""+request.getContextPath()+"\">Home page</a></p>");
                out.println("</body></html>");
            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid number format");
            }
        } else {
            response.getWriter().println("Missing user number in the URL");
        }
    }
}
