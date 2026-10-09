<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.Order" %>
<%@ page import="com.fashionstore.model.OrderItem" %>
<%@ page import="com.fashionstore.model.Product" %>
<%@ page import="com.fashionstore.model.ProductSize" %>
<%@ page import="com.fashionstore.dao.impl.ProductDAOImpl" %>
<%@ page import="com.fashionstore.dao.impl.ProductSizeDAOImpl" %>
<%
    Order order = (Order) request.getAttribute("order");
    List<OrderItem> orderItems = (List<OrderItem>) request.getAttribute("orderItems");
    ProductDAOImpl productLookup = new ProductDAOImpl();
    ProductSizeDAOImpl sizeLookup = new ProductSizeDAOImpl();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Details - FashionStore</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<%@ include file="partials/navbar.jsp" %>

<main class="page-shell">
    <div class="container">
        <div class="page-heading">
            <h1>Order details</h1>
        </div>

        <section class="summary-card">
            <h2>Order #<%= order.getOrderId() %></h2>
            <p><strong>Status:</strong> <%= order.getOrderStatus() %></p>
            <p><strong>Order date:</strong> <%= order.getOrderDate() %></p>
        </section>

        <h2 style="margin: 28px 0 16px;">Order items</h2>

        <% if (orderItems == null || orderItems.isEmpty()) { %>
            <p>No items found for this order.</p>
        <% } else { %>
            <div class="stack-card">
                <% for (OrderItem item : orderItems) {
                    double itemTotal = item.getPrice() * item.getQuantity();
                    Product orderedProduct = null;
                    ProductSize orderedSize = null;
                    try {
                        orderedProduct = productLookup.getProductById(item.getProductId());
                        orderedSize = sizeLookup.getProductSizeById(item.getProductSizeId());
                    } catch (Exception ignored) {
                    }
                %>
                    <article class="cart-item">
                        <% if (orderedProduct != null && orderedProduct.getImage() != null) { %>
                            <img class="cart-thumb"
                                 src="<%= request.getContextPath() %>/images/products/<%= orderedProduct.getImage() %>"
                                 alt="<%= orderedProduct.getProductName() %>">
                        <% } else { %>
                            <div class="cart-thumb"></div>
                        <% } %>
                        <div>
                            <h3>
                                <%= orderedProduct != null ? orderedProduct.getProductName() : ("Product #" + item.getProductId()) %>
                            </h3>
                            <p><strong>Size:</strong>
                                <%= orderedSize != null ? orderedSize.getSize() : item.getProductSizeId() %>
                            </p>
                            <p><strong>Price:</strong> &#8377;<%= String.format("%.0f", item.getPrice()) %></p>
                            <p><strong>Quantity:</strong> <%= item.getQuantity() %></p>
                            <p><strong>Item total:</strong> &#8377;<%= String.format("%.0f", itemTotal) %></p>
                        </div>
                    </article>
                <% } %>
            </div>
        <% } %>

        <p class="summary-total">Order total: &#8377;<%= String.format("%.0f", order.getTotalAmount()) %></p>

        <div class="line-actions">
            <a class="btn btn-primary" href="<%= request.getContextPath() %>/orders">Back to my orders</a>
            <a class="btn btn-outline" href="<%= request.getContextPath() %>/home">Continue shopping</a>
        </div>
    </div>
</main>

<%@ include file="partials/footer.jsp" %>
</body>
</html>
