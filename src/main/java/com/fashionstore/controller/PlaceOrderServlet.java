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
import com.fashionstore.dao.OrderDAO;
import com.fashionstore.dao.OrderItemDAO;
import com.fashionstore.dao.impl.CartDAOImpl;
import com.fashionstore.dao.impl.CartItemDAOImpl;
import com.fashionstore.dao.impl.OrderDAOImpl;
import com.fashionstore.dao.impl.OrderItemDAOImpl;
import com.fashionstore.model.Cart;
import com.fashionstore.model.CartItem;
import com.fashionstore.model.Order;
import com.fashionstore.model.OrderItem;
import com.fashionstore.model.User;

@WebServlet("/place-order")
public class PlaceOrderServlet extends HttpServlet {

private static final long serialVersionUID = 1L;

private CartDAO cartDAO;
private CartItemDAO cartItemDAO;
private OrderDAO orderDAO;
private OrderItemDAO orderItemDAO;

@Override
public void init() throws ServletException {

    cartDAO = new CartDAOImpl();
    cartItemDAO = new CartItemDAOImpl();
    orderDAO = new OrderDAOImpl();
    orderItemDAO = new OrderItemDAOImpl();
}

@Override
protected void doPost(HttpServletRequest request,
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

        if (cartItems == null || cartItems.isEmpty()) {

            response.sendRedirect(
                request.getContextPath() + "/cart"
            );

            return;
        }

        // Calculate total
        double totalAmount = 0;

        for (CartItem item : cartItems) {

            totalAmount +=
                item.getPrice() * item.getQuantity();
        }

        // Create order
        Order order = new Order();

        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setOrderStatus("PLACED");

        int orderId =
                orderDAO.createOrder(order);

        if (orderId == -1) {

            throw new ServletException(
                "Unable to create order"
            );
        }

        // Create order items
        for (CartItem cartItem : cartItems) {

            OrderItem orderItem =
                    new OrderItem();

            orderItem.setOrderId(orderId);

            orderItem.setProductId(
                cartItem.getProductId()
            );

            orderItem.setProductSizeId(
                cartItem.getProductSizeId()
            );

            orderItem.setQuantity(
                cartItem.getQuantity()
            );

            orderItem.setPrice(
                cartItem.getPrice()
            );

            orderItemDAO.addOrderItem(
                orderItem
            );
        }

        // Clear cart
        cartItemDAO.clearCart(
            cart.getCartId()
        );

        // Send order ID to confirmation page
        request.setAttribute(
            "orderId",
            orderId
        );

        request.setAttribute(
            "totalAmount",
            totalAmount
        );

        request.getRequestDispatcher(
            "/WEB-INF/views/order-success.jsp"
        ).forward(request, response);

    } catch (SQLException e) {

        throw new ServletException(
            "Error placing order",
            e
        );
    }
}


}
