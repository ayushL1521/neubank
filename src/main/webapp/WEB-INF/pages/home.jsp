<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<c:url value="/resources/style.css" />" />
        <title><c:out value="${page_title}" /></title>
    </head>
    <body>
        <header class="header">
            <h2 class="logo">NEUBANK</h2>
        </header>
        <div class="content">
            <h2>Welcome to NeuBank Online Banking Dashboard</h2>
            <p>Enter your username & password to login</p><br />
            <form action="<%= request.getContextPath() %>/login" method="post" class="loginForm">
                <label for="username">Username:</label>
                <input type="text" name="username" placeholder="Enter username" value="saran"><br>
                <label for="password">Password:</label>
                <input type="password" name="password" placeholder="Enter password" value="123456"><br>
                <button type="submit" value="Login">Login</button>
            </form>
            <p>${message}</p>
        </div>
    </body>
</html>
