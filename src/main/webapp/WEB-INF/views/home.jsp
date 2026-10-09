<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.Product" %>
<%@ page import="com.fashionstore.model.Category" %>
<%@ page import="com.fashionstore.dao.impl.ProductDAOImpl" %>
<%@ page import="com.fashionstore.dao.impl.CategoryDAOImpl" %>
<%
    List<Product> featuredProducts = null;
    List<Category> categories = null;
    try {
        featuredProducts = new ProductDAOImpl().getAllProducts();
        categories = new CategoryDAOImpl().getAllCategories();
    } catch (Exception ignored) {
        featuredProducts = null;
        categories = null;
    }

    String heroImage = request.getContextPath() + "/images/hero.jpg";
    if (featuredProducts != null && !featuredProducts.isEmpty()
            && featuredProducts.get(0).getImage() != null) {
        heroImage = request.getContextPath() + "/images/products/"
                + featuredProducts.get(0).getImage();
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FashionStore - Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<%@ include file="partials/navbar.jsp" %>

<main>
    <section class="hero">
        <div class="container hero-content">
            <div class="hero-text">
                <p class="hero-eyebrow">New season edit</p>
                <h1 class="hero-title">Wear the mood.<br>Own the moment.</h1>
                <p class="hero-description">
                    Discover bold silhouettes, everyday essentials, and statement pieces
                    made for a modern wardrobe.
                </p>
                <a href="${pageContext.request.contextPath}/products" class="btn btn-outline">
                    Shop the collection
                </a>
            </div>
            <div class="hero-image">
                <img src="<%= heroImage %>" alt="FashionStore featured look">
            </div>
        </div>
    </section>

    <section class="section section-lilac">
        <div class="container">
            <div class="section-header">
                <h2>Shop by category</h2>
                <p>Jump into the looks you actually wear.</p>
            </div>
            <div class="category-rail">
                <% if (categories != null) {
                    for (Category category : categories) { %>
                    <a class="category-pill"
                       href="${pageContext.request.contextPath}/products?category=<%= category.getCategoryId() %>">
                        <%= category.getCategoryName() %>
                    </a>
                <%  }
                   } %>
            </div>
        </div>
    </section>

    <section class="section section-white">
        <div class="container">
            <div class="section-header">
                <h2>Featured finds</h2>
                <p>Large-format pieces from the latest drop.</p>
            </div>
            <div class="product-grid">
                <%
                    int shown = 0;
                    if (featuredProducts != null) {
                        for (Product product : featuredProducts) {
                            if (shown >= 8) {
                                break;
                            }
                            shown++;
                %>
                <article class="product-card">
                    <a href="${pageContext.request.contextPath}/product?id=<%= product.getProductId() %>">
                        <div class="product-image">
                            <img src="${pageContext.request.contextPath}/images/products/<%= product.getImage() %>"
                                 alt="<%= product.getProductName() %>">
                        </div>
                    </a>
                    <div class="product-info">
                        <p class="product-brand"><%= product.getBrand() %> &middot; <%= product.getColor() %></p>
                        <h3 class="product-name"><%= product.getProductName() %></h3>
                        <p class="product-price">&#8377;<%= String.format("%.0f", product.getPrice()) %></p>
                        <a class="btn btn-primary"
                           href="${pageContext.request.contextPath}/product?id=<%= product.getProductId() %>">
                            View details
                        </a>
                    </div>
                </article>
                <%      }
                    }
                %>
            </div>
        </div>
    </section>

    <section class="section section-lilac">
        <div class="container">
            <div class="promo-banner">
                <div>
                    <h2>Your next favorite outfit is one click away.</h2>
                    <p>Filter by color, size, brand, and price &mdash; then add it to your cart.</p>
                </div>
                <a href="${pageContext.request.contextPath}/products" class="btn btn-outline">Browse all styles</a>
            </div>
        </div>
    </section>

    <section class="section section-sky">
        <div class="container">
            <div class="section-header center">
                <h2>Why FashionStore</h2>
            </div>
            <div class="why-grid">
                <article class="why-card">
                    <h3>Easy shopping</h3>
                    <p>Search, filter, and sort the catalog until the look feels right.</p>
                </article>
                <article class="why-card">
                    <h3>Multiple styles</h3>
                    <p>Men, women, kids, footwear, and accessories in one store.</p>
                </article>
                <article class="why-card">
                    <h3>Secure checkout</h3>
                    <p>Sign in to save your details and place orders from your cart.</p>
                </article>
                <article class="why-card">
                    <h3>Order tracking</h3>
                    <p>Follow every order from placement through your order history.</p>
                </article>
            </div>
        </div>
    </section>
</main>

<%@ include file="partials/footer.jsp" %>
</body>
</html>
