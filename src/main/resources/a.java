```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.fashionstore.model.Product" %>

<%
    Product product = (Product) request.getAttribute("product");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= product.getProductName() %></title>
</head>

<body>

    <h1><%= product.getProductName() %></h1>

    <p>
        <strong>Brand:</strong>
        <%= product.getBrand() %>
    </p>

    <p>
        <strong>Price:</strong>
        ₹<%= product.getPrice() %>
    </p>

    <p>
        <strong>Color:</strong>
        <%= product.getColor() %>
    </p>

    <p>
        <strong>Description:</strong>
        <%= product.getDescription() %>
    </p>

    <p>
        <strong>Product ID:</strong>
        <%= product.getProductId() %>
    </p>

    <img src="<%= request.getContextPath() + "/" + product.getImage() %>"
         alt="<%= product.getProductName() %>"
         width="300">

</body>
</html>
```
