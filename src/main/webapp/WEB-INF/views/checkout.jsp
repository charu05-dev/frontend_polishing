<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.User" %>
<%@ page import="com.fashionstore.model.CartItem" %>
<%
    User user = (User) request.getAttribute("user");
    List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");
    Double cartTotal = (Double) request.getAttribute("cartTotal");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout - FashionStore</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<%@ include file="partials/navbar.jsp" %>

<main class="page-shell">
    <div class="container">
        <div class="page-heading">
            <h1>Checkout</h1>
        </div>

        <div class="split-layout">
            <section class="summary-card checkout-details">
                <h2>Delivery details</h2>
                <p><strong>Name:</strong> <%= user.getName() %></p>
                <p><strong>Email:</strong> <%= user.getEmail() %></p>
                <p><strong>Phone:</strong> <%= user.getPhone() %></p>
                <p><strong>Address:</strong> <%= user.getAddress() %></p>
                <p><strong>City:</strong> <%= user.getCity() %></p>
                <p><strong>State:</strong> <%= user.getState() %></p>
                <p><strong>Pincode:</strong> <%= user.getPincode() %></p>
            </section>

            <aside class="summary-card">
                <h2>Order summary</h2>
                <% for (CartItem item : cartItems) {
                    double itemTotal = item.getPrice() * item.getQuantity();
                %>
                    <div class="checkout-item">
                        <h3><%= item.getProductName() %></h3>
                        <p>Size: <%= item.getSize() %></p>
                        <p>Quantity: <%= item.getQuantity() %></p>
                        <p>Price: &#8377;<%= String.format("%.0f", item.getPrice()) %></p>
                        <p>Item total: &#8377;<%= String.format("%.0f", itemTotal) %></p>
                    </div>
                <% } %>

                <p class="summary-total">Total: &#8377;<%= String.format("%.0f", cartTotal) %></p>

                <form action="<%= request.getContextPath() %>/place-order" method="post">
                    <button type="submit" class="btn btn-primary btn-large btn-full">Place order</button>
                </form>

                <div class="line-actions">
                    <a class="btn btn-ghost" href="<%= request.getContextPath() %>/cart">Back to cart</a>
                </div>
            </aside>
        </div>
    </div>
</main>

<%@ include file="partials/footer.jsp" %>
</body>
</html>
