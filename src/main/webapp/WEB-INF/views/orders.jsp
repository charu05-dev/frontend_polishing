<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.Order" %>
<%
    List<Order> orders = (List<Order>) request.getAttribute("orders");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Orders - FashionStore</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<%@ include file="partials/navbar.jsp" %>

<main class="page-shell">
    <div class="container">
        <div class="page-heading">
            <h1>My orders</h1>
        </div>

        <% if (orders == null || orders.isEmpty()) { %>
            <div class="empty-state">
                <h2>You have no orders yet.</h2>
                <a class="btn btn-primary" href="<%= request.getContextPath() %>/home">Continue shopping</a>
            </div>
        <% } else { %>
            <div class="order-list">
                <% for (Order order : orders) {
                    String status = order.getOrderStatus() == null ? "" : order.getOrderStatus().toLowerCase();
                    String statusClass = "status-pill";
                    if ("placed".equals(status)) statusClass += " status-placed";
                    else if ("shipped".equals(status)) statusClass += " status-shipped";
                    else if ("delivered".equals(status)) statusClass += " status-delivered";
                    else if ("cancelled".equals(status)) statusClass += " status-cancelled";
                %>
                    <article class="order-card">
                        <div>
                            <h2>Order #<%= order.getOrderId() %></h2>
                            <p><strong>Order date:</strong> <%= order.getOrderDate() %></p>
                            <p><span class="<%= statusClass %>"><%= order.getOrderStatus() %></span></p>
                            <p class="product-price">&#8377;<%= String.format("%.0f", order.getTotalAmount()) %></p>
                        </div>
                        <a class="btn btn-primary"
                           href="<%= request.getContextPath() %>/order-details?orderId=<%= order.getOrderId() %>">
                            View order details
                        </a>
                    </article>
                <% } %>
            </div>
        <% } %>

        <div class="line-actions">
            <a class="btn btn-ghost" href="<%= request.getContextPath() %>/home">Continue shopping</a>
        </div>
    </div>
</main>

<%@ include file="partials/footer.jsp" %>
</body>
</html>
