<header class="navbar">
    <div class="navbar-container">
        <a href="${pageContext.request.contextPath}/home" class="logo">
            FashionStore<span>.</span>
        </a>

        <form class="search-box"
              action="${pageContext.request.contextPath}/products"
              method="get">
            <input type="text"
                   name="keyword"
                   placeholder="Search fashion..."
                   aria-label="Search products">
            <button type="submit" aria-label="Search">&#8594;</button>
        </form>

        <button class="menu-toggle"
                type="button"
                aria-expanded="false"
                aria-label="Open menu">
            Menu
        </button>

        <nav class="nav-links">
            <a href="${pageContext.request.contextPath}/products">Products</a>

            <%
                Object loggedInUser = session.getAttribute("loggedInUser");
                if (loggedInUser != null) {
            %>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            <%
                } else {
            %>
                <a href="${pageContext.request.contextPath}/login">Login</a>
            <%
                }
            %>

            <a href="${pageContext.request.contextPath}/cart" class="cart-link">Cart</a>
        </nav>
    </div>
</header>
