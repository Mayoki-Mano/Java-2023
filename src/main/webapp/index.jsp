<%@ page import="java.util.Date" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
<p><a href="users">Users Servlet</a></p>
<br/>
</body>
</html>