package com.example.lab3;

import com.github.benmanes.caffeine.cache.Cache;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Objects;

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
                out.println("<h1>USER №" + number + ":</h1>");
                User user = users.get(number);
                User cookieUser = CookieUtils.getObjectFromCookie(request, "currentUser", User.class);
                out.println("<p>User ID: " + number + ", Name: " + user.getUsername() + "<p>");
                out.println("<p>Email: " + user.getEmail() + "<p>");
                if (user.equals(cookieUser) || Objects.requireNonNull(cookieUser).getUsername().equals("admin")) {
                    out.println("<p><a href=\"" + request.getContextPath() + "/updateUser/" + user.getId() + "\">Update person</a></p>");
                    out.println("<form action='"+number+"' method='post'>");
                    out.println("<input type='submit' value='Delete person'>");
                    out.println("</form>");
                }
                out.println("<p><a href=\"" + request.getContextPath() + "\">Home page</a></p>");
                out.println("</body></html>");
            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid number format");
            }
        } else {
            response.getWriter().println("Missing user number in the URL");
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Получение пути из URL после "/users/"
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            // Извлечение числа из пути (за исключением первого символа '/')
            String numberString = pathInfo.substring(1);

            try {
                // Преобразование числа из строки
                int number = Integer.parseInt(numberString);
                Cache<Integer, User> idUserCache = (Cache<Integer, User>) getServletContext().getAttribute("idUserCache");
                Cache<String, Integer> idNameCache = (Cache<String, Integer>) getServletContext().getAttribute("idNameCache");
                User cookieUser = CookieUtils.getObjectFromCookie(request, "currentUser", User.class);
                User user = idUserCache.get(number,k->null);
                if (AdminUtils.isAdmin(request)) {
                    if (user.getUsername().equals("admin")){
                        CookieUtils.deleteUserCookies(request, response);
                    }
                    idNameCache.invalidate(user.getUsername());
                    idUserCache.invalidate(user.getId());
                }else{
                    if (user.getUsername().equals(cookieUser.getUsername())){
                        CookieUtils.deleteUserCookies(request, response);
                        idNameCache.invalidate(user.getUsername());
                        idUserCache.invalidate(user.getId());
                    }
                }
                response.sendRedirect(request.getContextPath()+"/users");
            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid number format");
            }
        } else {
            response.getWriter().println("Missing user number in the URL");
        }
    }

}
