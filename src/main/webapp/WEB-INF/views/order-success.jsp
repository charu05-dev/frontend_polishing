<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Integer orderId = (Integer) request.getAttribute("orderId");
    Double totalAmount = (Double) request.getAttribute("totalAmount");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmed - FashionStore</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<%@ include file="partials/navbar.jsp" %>

<main class="success-wrap">
    <div class="success-card">
        <div class="success-mark">&#10003;</div>
        <h1>Order Placed Successfully!</h1>
        <p>Thank you for shopping with FashionStore.</p>
        <p><strong>Order ID:</strong> <%= orderId %></p>
        <p><strong>Order Total:</strong> &#8377;<%= String.format("%.0f", totalAmount) %></p>
        <div class="success-actions">
            <a class="btn btn-primary" href="<%= request.getContextPath() %>/home">Continue shopping</a>
            <a class="btn btn-outline" href="<%= request.getContextPath() %>/orders">View my orders</a>
        </div>
    </div>
</main>

<%@ include file="partials/footer.jsp" %>
</body>
</html>
