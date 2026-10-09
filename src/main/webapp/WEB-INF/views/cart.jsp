<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.CartItem" %>
<%@ page import="com.fashionstore.model.Product" %>
<%@ page import="com.fashionstore.dao.impl.ProductDAOImpl" %>
<%
    List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");
    double cartTotal = 0;
    ProductDAOImpl productLookup = new ProductDAOImpl();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopping Cart - FashionStore</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<%@ include file="partials/navbar.jsp" %>

<main class="page-shell">
    <div class="container">
        <div class="page-heading">
            <h1>Your cart</h1>
        </div>

        <% if (cartItems == null || cartItems.isEmpty()) { %>
            <div class="empty-state">
                <h2>Your cart is empty.</h2>
                <a class="btn btn-primary" href="<%= request.getContextPath() %>/home">Continue shopping</a>
            </div>
        <% } else { %>
            <div class="split-layout">
                <div class="stack-card">
                    <% for (CartItem item : cartItems) {
                        double itemTotal = item.getPrice() * item.getQuantity();
                        cartTotal += itemTotal;
                        Product cartProduct = null;
                        try {
                            cartProduct = productLookup.getProductById(item.getProductId());
                        } catch (Exception ignored) {
                            cartProduct = null;
                        }
                    %>
                        <article class="cart-item">
                            <% if (cartProduct != null && cartProduct.getImage() != null) { %>
                                <img class="cart-thumb"
                                     src="<%= request.getContextPath() %>/images/products/<%= cartProduct.getImage() %>"
                                     alt="<%= item.getProductName() %>">
                            <% } else { %>
                                <div class="cart-thumb"></div>
                            <% } %>

                            <div>
                                <h2 class="product-name"><%= item.getProductName() %></h2>
                                <p class="product-brand">Size: <%= item.getSize() %></p>
                                <p>Price: &#8377;<%= String.format("%.0f", item.getPrice()) %></p>
                                <p>Item total: &#8377;<%= String.format("%.0f", itemTotal) %></p>

                                <div class="qty-controls">
                                    <form action="<%= request.getContextPath() %>/update-cart" method="post">
                                        <input type="hidden" name="cartItemId" value="<%= item.getCartItemId() %>">
                                        <input type="hidden" name="action" value="decrease">
                                        <button type="submit">&#8722;</button>
                                    </form>
                                    <strong><%= item.getQuantity() %></strong>
                                    <form action="<%= request.getContextPath() %>/update-cart" method="post">
                                        <input type="hidden" name="cartItemId" value="<%= item.getCartItemId() %>">
                                        <input type="hidden" name="action" value="increase">
                                        <button type="submit">+</button>
                                    </form>
                                </div>
                            </div>

                            <form action="<%= request.getContextPath() %>/update-cart" method="post">
                                <input type="hidden" name="cartItemId" value="<%= item.getCartItemId() %>">
                                <input type="hidden" name="action" value="remove">
                                <button type="submit" class="btn-danger">Remove</button>
                            </form>
                        </article>
                    <% } %>
                </div>

                <aside class="summary-card">
                    <h2>Order summary</h2>
                    <div class="summary-row">
                        <span>Subtotal</span>
                        <strong>&#8377;<%= String.format("%.0f", cartTotal) %></strong>
                    </div>
                    <p class="summary-total">Cart total: &#8377;<%= String.format("%.0f", cartTotal) %></p>
                    <a class="btn btn-primary btn-large btn-full" href="<%= request.getContextPath() %>/checkout">
                        Proceed to checkout
                    </a>
                    <div class="line-actions">
                        <a class="btn btn-ghost" href="<%= request.getContextPath() %>/home">Continue shopping</a>
                    </div>
                </aside>
            </div>
        <% } %>
    </div>
</main>

<%@ include file="partials/footer.jsp" %>
</body>
</html>
