<%@ page import="java.util.Date" %>
<%@ page import="com.example.lab3.User" %>
<%@ page import="com.example.lab3.CookieUtils" %>
<%@ page import="com.example.lab3.AdminUtils" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    boolean isAuthenticated = CookieUtils.checkAuthentication(request);
    if (Objects.equals(request.getParameter("logout"), "true")) {
        CookieUtils.deleteUserCookies(request,response);
        isAuthenticated=false;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Welcome!" %>
</h1>
<p>
    Current time: <%=new Date() %>
</p>
<p><a href="registration">Registration Servlet</a></p>
<p><a href="login">Login Servlet</a></p>
<%
    // Показываем ссылку "Users Servlet" только если у пользователя роль "admin"
    if (isAuthenticated) {
%>
<p><a href="users">Users Servlet</a></p>
<p><a href="?logout=true">Logout</a></p>
<%
    }
%>
<br/>
</body>
</html>