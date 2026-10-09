package com.fashionstore.controller;

import java.io.IOException;
import java.sql.SQLException;

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

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {


private static final long serialVersionUID = 1L;

private CartDAO cartDAO;
private CartItemDAO cartItemDAO;

@Override
public void init() throws ServletException {
    cartDAO = new CartDAOImpl();
    cartItemDAO = new CartItemDAOImpl();
}

@Override
protected void doPost(HttpServletRequest request,
                      HttpServletResponse response)
        throws ServletException, IOException {

    // Check if user is logged in
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

        int productId =
                Integer.parseInt(
                    request.getParameter("productId")
                );

        int productSizeId =
                Integer.parseInt(
                    request.getParameter("productSizeId")
                );

        int quantity =
                Integer.parseInt(
                    request.getParameter("quantity")
                );

        // Get or create cart for logged-in user
        Cart cart =
                cartDAO.getOrCreateCart(userId);

        // Check if this product size is already in cart
        CartItem existingItem =
                cartItemDAO.getCartItemByProductSize(
                    cart.getCartId(),
                    productSizeId
                );

        if (existingItem != null) {

            // Increase quantity
            cartItemDAO.increaseQuantity(
                existingItem.getCartItemId(),
                quantity
            );

        } else {

            // Create new cart item
            CartItem cartItem =
                    new CartItem();

            cartItem.setCartId(
                cart.getCartId()
            );

            cartItem.setProductId(
                productId
            );

            cartItem.setProductSizeId(
                productSizeId
            );

            cartItem.setQuantity(
                quantity
            );

            cartItemDAO.addCartItem(
                cartItem
            );
        }

        // Go to cart
        response.sendRedirect(
            request.getContextPath() + "/cart"
        );

    } catch (NumberFormatException e) {

        response.sendError(
            HttpServletResponse.SC_BAD_REQUEST,
            "Invalid product or size"
        );

    } catch (SQLException e) {

        throw new ServletException(
            "Error adding product to cart",
            e
        );
    }
}


}
