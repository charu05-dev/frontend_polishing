<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.Product" %>
<%@ page import="com.fashionstore.model.ProductSize" %>
<%
    Product product = (Product) request.getAttribute("product");
    List<ProductSize> sizes = (List<ProductSize>) request.getAttribute("sizes");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= product.getProductName() %> - FashionStore</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<%@ include file="partials/navbar.jsp" %>

<main class="page-shell">
    <div class="container details-layout">
        <div class="details-gallery">
            <img src="<%= request.getContextPath() %>/images/products/<%= product.getImage() %>"
                 alt="<%= product.getProductName() %>">
        </div>

        <div>
            <p class="details-meta"><%= product.getBrand() %> &middot; <%= product.getColor() %></p>
            <h1 class="details-title"><%= product.getProductName() %></h1>
            <p class="details-price">&#8377;<%= String.format("%.0f", product.getPrice()) %></p>
            <p class="details-copy"><%= product.getDescription() %></p>

            <form action="<%= request.getContextPath() %>/add-to-cart" method="post">
                <input type="hidden" name="productId" value="<%= product.getProductId() %>">

                <p class="size-title">Select size</p>
                <div class="sizes">
                    <% if (sizes != null && !sizes.isEmpty()) {
                        for (ProductSize size : sizes) { %>
                        <label class="size">
                            <input type="radio"
                                   name="productSizeId"
                                   value="<%= size.getProductSizeId() %>"
                                   required>
                            <%= size.getSize() %>
                        </label>
                    <%  }
                       } else { %>
                        <p>No sizes available.</p>
                    <% } %>
                </div>

                <div class="quantity">
                    <label for="quantity"><strong>Quantity</strong></label>
                    <input id="quantity"
                           type="number"
                           name="quantity"
                           value="1"
                           min="1"
                           required>
                </div>

                <button type="submit" class="btn btn-primary btn-large">Add to Cart</button>
            </form>
        </div>
    </div>
</main>

<%@ include file="partials/footer.jsp" %>
</body>
</html>
