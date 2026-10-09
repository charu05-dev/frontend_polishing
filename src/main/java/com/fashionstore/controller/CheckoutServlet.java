package com.fashionstore.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.dao.impl.CartDAOImpl;
import com.fashionstore.dao.impl.CartItemDAOImpl;
import com.fashionstore.model.Cart;
import com.fashionstore.model.CartItem;
import com.fashionstore.model.User;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {


private static final long serialVersionUID = 1L;

private CartDAO cartDAO;
private CartItemDAO cartItemDAO;

@Override
public void init() throws ServletException {

    cartDAO = new CartDAOImpl();
    cartItemDAO = new CartItemDAOImpl();
}

@Override
protected void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws ServletException, IOException {

    // Check login
    HttpSession session = request.getSession(false);

    if (session == null
            || session.getAttribute("loggedInUser") == null) {

        response.sendRedirect(
            request.getContextPath() + "/login"
        );

        return;
    }

    try {

        // Get logged-in user
        User user =
                (User) session.getAttribute("loggedInUser");

        int userId =
                user.getUserId();

        // Get user's cart
        Cart cart =
                cartDAO.getCartByUserId(userId);

        if (cart == null) {

            response.sendRedirect(
                request.getContextPath() + "/cart"
            );

            return;
        }

        // Get cart items
        List<CartItem> cartItems =
                cartItemDAO.getCartItemsByCartId(
                    cart.getCartId()
                );

        // Check if cart is empty
        if (cartItems == null || cartItems.isEmpty()) {

            response.sendRedirect(
                request.getContextPath() + "/cart"
            );

            return;
        }

        // Calculate total
        double cartTotal = 0;

        for (CartItem item : cartItems) {

            cartTotal +=
                item.getPrice() * item.getQuantity();
        }

        request.setAttribute(
            "cartItems",
            cartItems
        );

        request.setAttribute(
            "cartTotal",
            cartTotal
        );

        request.setAttribute(
            "user",
            user
        );

        // Open checkout page
        request.getRequestDispatcher(
            "/WEB-INF/views/checkout.jsp"
        ).forward(request, response);

    } catch (SQLException e) {

        throw new ServletException(
            "Error loading checkout",
            e
        );
    }
}


}
