<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.Product" %>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    String keyword = (String) request.getAttribute("keyword");
    String category = (String) request.getAttribute("category");
    String color = (String) request.getAttribute("color");
    String brand = (String) request.getAttribute("brand");
    String size = (String) request.getAttribute("size");
    String minPrice = (String) request.getAttribute("minPrice");
    String maxPrice = (String) request.getAttribute("maxPrice");
    String sortBy = (String) request.getAttribute("sortBy");
    String sortOrder = (String) request.getAttribute("sortOrder");
    List<String> colors = (List<String>) request.getAttribute("colors");
    List<String> brands = (List<String>) request.getAttribute("brands");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Products - FashionStore</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<%@ include file="partials/navbar.jsp" %>

<main class="page-shell">
    <div class="container">
        <div class="page-heading">
            <h1>Discover your next look</h1>
            <p>Large fashion cards on a clean shopping canvas. Filter until it feels like you.</p>
        </div>

        <form class="filters-panel"
              action="<%= request.getContextPath() %>/products"
              method="get">

            <div class="filter-grid">
                <div class="field">
                    <label for="keyword">Search</label>
                    <input id="keyword" type="text" name="keyword"
                           placeholder="Search products..."
                           value="<%= keyword != null ? keyword : "" %>">
                </div>

                <div class="field">
                    <label for="category">Category</label>
                    <select id="category" name="category">
                        <option value="">All Categories</option>
                        <option value="1" <%= "1".equals(category) ? "selected" : "" %>>Men</option>
                        <option value="2" <%= "2".equals(category) ? "selected" : "" %>>Women</option>
                        <option value="3" <%= "3".equals(category) ? "selected" : "" %>>Kids</option>
                        <option value="4" <%= "4".equals(category) ? "selected" : "" %>>Footwear</option>
                        <option value="5" <%= "5".equals(category) ? "selected" : "" %>>Accessories</option>
                    </select>
                </div>

                <div class="field">
                    <label for="color">Color</label>
                    <select id="color" name="color">
                        <option value="">All Colors</option>
                        <% if (colors != null) {
                            for (String availableColor : colors) { %>
                            <option value="<%= availableColor %>"
                                <%= availableColor.equals(color) ? "selected" : "" %>>
                                <%= availableColor %>
                            </option>
                        <%  }
                           } %>
                    </select>
                </div>

                <div class="field">
                    <label for="brand">Brand</label>
                    <select id="brand" name="brand">
                        <option value="">All Brands</option>
                        <% if (brands != null) {
                            for (String availableBrand : brands) { %>
                            <option value="<%= availableBrand %>"
                                <%= availableBrand.equals(brand) ? "selected" : "" %>>
                                <%= availableBrand %>
                            </option>
                        <%  }
                           } %>
                    </select>
                </div>

                <div class="field">
                    <label for="size">Size</label>
                    <select id="size" name="size">
                        <option value="">All Sizes</option>
                        <%
                            String[] sizeOptions = {
                                "XS", "S", "M", "L", "XL", "XXL",
                                "28", "30", "32", "34", "36",
                                "6", "7", "8", "9", "10",
                                "One Size"
                            };
                            for (String sizeOption : sizeOptions) {
                        %>
                            <option value="<%= sizeOption %>"
                                <%= sizeOption.equals(size) ? "selected" : "" %>>
                                <%= sizeOption %>
                            </option>
                        <% } %>
                    </select>
                </div>
            </div>

            <div class="filter-row">
                <div class="field">
                    <label for="minPrice">Min price</label>
                    <input id="minPrice" type="number" name="minPrice"
                           placeholder="Min Price" min="0" step="0.01"
                           value="<%= minPrice != null ? minPrice : "" %>">
                </div>
                <div class="field">
                    <label for="maxPrice">Max price</label>
                    <input id="maxPrice" type="number" name="maxPrice"
                           placeholder="Max Price" min="0" step="0.01"
                           value="<%= maxPrice != null ? maxPrice : "" %>">
                </div>
                <div class="field">
                    <label for="sortBy">Sort by</label>
                    <select id="sortBy" name="sortBy">
                        <option value="">Sort By</option>
                        <option value="price" <%= "price".equals(sortBy) ? "selected" : "" %>>Price</option>
                        <option value="name" <%= "name".equals(sortBy) ? "selected" : "" %>>Name</option>
                    </select>
                </div>
                <div class="field">
                    <label for="sortOrder">Order</label>
                    <select id="sortOrder" name="sortOrder">
                        <option value="asc" <%= "asc".equals(sortOrder) ? "selected" : "" %>>Low to High / A to Z</option>
                        <option value="desc" <%= "desc".equals(sortOrder) ? "selected" : "" %>>High to Low / Z to A</option>
                    </select>
                </div>
            </div>

            <div class="filter-actions">
                <button type="submit" class="btn btn-primary">Apply filters</button>
                <a class="btn btn-ghost" href="<%= request.getContextPath() %>/products">Clear all</a>
            </div>
        </form>

        <% if (keyword != null && !keyword.trim().isEmpty()) { %>
            <p class="search-note">Search results for: "<%= keyword %>"</p>
        <% } %>

        <% if (products == null || products.isEmpty()) { %>
            <div class="empty-state">
                <h2>No products found.</h2>
                <p>Try a different filter combination.</p>
                <a class="btn btn-primary" href="<%= request.getContextPath() %>/products">View all products</a>
            </div>
        <% } else { %>
            <div class="product-grid">
                <% for (Product product : products) { %>
                    <article class="product-card">
                        <a href="<%= request.getContextPath() %>/product?id=<%= product.getProductId() %>">
                            <div class="product-image">
                                <img src="<%= request.getContextPath() %>/images/products/<%= product.getImage() %>"
                                     alt="<%= product.getProductName() %>">
                            </div>
                        </a>
                        <div class="product-info">
                            <p class="product-brand"><%= product.getBrand() %> &middot; <%= product.getColor() %></p>
                            <h3 class="product-name"><%= product.getProductName() %></h3>
                            <p class="product-price">&#8377;<%= String.format("%.0f", product.getPrice()) %></p>
                            <a class="btn btn-primary"
                               href="<%= request.getContextPath() %>/product?id=<%= product.getProductId() %>">
                                View details
                            </a>
                        </div>
                    </article>
                <% } %>
            </div>
        <% } %>
    </div>
</main>

<%@ include file="partials/footer.jsp" %>
</body>
</html>
