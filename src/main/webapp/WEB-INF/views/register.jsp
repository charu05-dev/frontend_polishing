<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Account - FashionStore</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<%@ include file="partials/navbar.jsp" %>

<main class="auth-page">
    <div class="auth-card wide">
        <h1>Create account</h1>
        <p>Join FashionStore to save your delivery details and place orders.</p>

        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) { %>
            <div class="error"><%= error %></div>
        <% } %>

        <form action="<%= request.getContextPath() %>/register" method="post">
            <div class="form-group">
                <label>Name</label>
                <input type="text" name="name" required>
            </div>
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" required>
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" required>
            </div>
            <div class="form-group">
                <label>Phone</label>
                <input type="text" name="phone" required>
            </div>
            <div class="form-group">
                <label>Address</label>
                <input type="text" name="address" required>
            </div>
            <div class="form-grid">
                <div class="form-group">
                    <label>City</label>
                    <input type="text" name="city" required>
                </div>
                <div class="form-group">
                    <label>State</label>
                    <input type="text" name="state" required>
                </div>
            </div>
            <div class="form-group">
                <label>Pincode</label>
                <input type="text" name="pincode" required>
            </div>
            <button type="submit" class="btn btn-primary btn-large btn-full">Register</button>
        </form>

        <p class="auth-switch">
            Already have an account?
            <a href="<%= request.getContextPath() %>/login">Login</a>
        </p>
    </div>
</main>

<%@ include file="partials/footer.jsp" %>
</body>
</html>
