<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - FashionStore</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<%@ include file="partials/navbar.jsp" %>

<main class="auth-page">
    <div class="auth-card">
        <h1>Welcome back</h1>
        <p>Sign in to shop, checkout, and track your orders.</p>

        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) { %>
            <div class="error"><%= error %></div>
        <% } %>

        <form action="<%= request.getContextPath() %>/login" method="post">
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" required>
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" required>
            </div>
            <button type="submit" class="btn btn-primary btn-large btn-full">Login</button>
        </form>

        <p class="auth-switch">
            Don't have an account?
            <a href="<%= request.getContextPath() %>/register">Create account</a>
        </p>
    </div>
</main>

<%@ include file="partials/footer.jsp" %>
</body>
</html>
