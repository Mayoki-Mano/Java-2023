package com.example.lab3;

import java.io.*;
import java.util.Objects;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        // Отображение страницы входа
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Login</title>");
        out.println("<style>");
        out.println("body { font-family: 'Arial', sans-serif; text-align: center; margin: 50px; }");
        out.println("h2 { color: #333; }");
        out.println("form { display: inline-block; text-align: left; }");
        out.println("input { margin-bottom: 10px; }");
        out.println("p.error { color: red; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Login Page</h2>");

// Check for the presence of an error parameter
        String errorParam = request.getParameter("error");
        if (errorParam != null && errorParam.equals("true")) {
            out.println("<p class='error'>Invalid username or password. Please try again.</p>");
        }
        out.println("<form action='login' method='post'>");
        out.println("<label for='username'>Username:</label>");
        out.println("<input type='text' id='username' name='username' required><br>");
        out.println("<label for='password'>Password:</label>");
        out.println("<input type='password' id='password' name='password' required><br>");
        out.println("<input type='submit' value='Login'>");
        out.println("</form>");

        out.println("</body></html>");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Получение данных из формы ввода
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Проверка учетных данных
        if (isValidCredentials(username, password)) {
            // Авторизация успешна, перенаправление на защищенную страницу
            Cache<String, Integer> idNameCache= (Cache<String, Integer>) getServletContext().getAttribute("idNameCache");
            Cache<Integer, User> idUserCache=(Cache<Integer, User>) getServletContext().getAttribute("idUserCache");
            int userId=idNameCache.get(username,k-> -1);
            User user= idUserCache.get(userId, k-> null);
            CookieUtils.deleteUserCookies(request,response);
            CookieUtils.saveObjectToCookie(user,24*60*60,"currentUser",response);
            response.sendRedirect(request.getContextPath() +"/users/"+userId);
        } else {
            response.sendRedirect(request.getContextPath() +"/login?error=true");
        }
    }


    private boolean isValidCredentials(String username, String password) {
        Cache<Integer, User> idUserCache=(Cache<Integer, User>) getServletContext().getAttribute("idUserCache");
        if (idUserCache.asMap().isEmpty()){
            return false;
        }
        Cache<String, Integer> idNameCache= (Cache<String, Integer>) getServletContext().getAttribute("idNameCache");
        int userId = idNameCache.get(username,k-> -1);
        if (userId==-1){
            return false;
        }
        User user= idUserCache.get(userId, k-> null);
        return Objects.equals(user.getPassword(), password);
    }
}
