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

@WebServlet("/update-cart")
public class UpdateCartServlet extends HttpServlet {

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

        // 1. Check whether user is logged in
        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("loggedInUser") == null) {

            response.sendRedirect(
                request.getContextPath() + "/login"
            );

            return;
        }

        try {

            // 2. Get logged-in user
            User user =
                    (User) session.getAttribute("loggedInUser");

            int userId =
                    user.getUserId();

            // 3. Get cart item ID and action
            int cartItemId =
                    Integer.parseInt(
                        request.getParameter("cartItemId")
                    );

            String action =
                    request.getParameter("action");

            // 4. Get the logged-in user's cart
            Cart cart =
                    cartDAO.getCartByUserId(userId);

            if (cart == null) {

                response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Cart not found"
                );

                return;
            }

            // 5. Get requested cart item
            CartItem cartItem =
                    cartItemDAO.getCartItemById(cartItemId);

            if (cartItem == null) {

                response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Cart item not found"
                );

                return;
            }

            // 6. Check cart ownership
            if (cartItem.getCartId() != cart.getCartId()) {

                response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "You are not allowed to modify this cart item"
                );

                return;
            }

            // 7. Perform requested action
            if ("increase".equals(action)) {

                cartItemDAO.increaseQuantity(
                    cartItemId,
                    1
                );

            } else if ("decrease".equals(action)) {

                cartItemDAO.decreaseQuantity(
                    cartItemId,
                    1
                );

            } else if ("remove".equals(action)) {

                cartItemDAO.removeCartItem(
                    cartItemId
                );

            } else {

                response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid cart action"
                );

                return;
            }

            // 8. Return to cart
            response.sendRedirect(
                request.getContextPath() + "/cart"
            );

        } catch (NumberFormatException e) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Invalid cart item ID"
            );

        } catch (SQLException e) {

            throw new ServletException(
                "Error updating cart",
                e
            );
        }
    }
}