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

import com.fashionstore.dao.OrderDAO;
import com.fashionstore.dao.OrderItemDAO;
import com.fashionstore.dao.impl.OrderDAOImpl;
import com.fashionstore.dao.impl.OrderItemDAOImpl;
import com.fashionstore.model.Order;
import com.fashionstore.model.OrderItem;
import com.fashionstore.model.User;

@WebServlet("/order-details")
public class OrderDetailsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;

    @Override
    public void init() throws ServletException {

        orderDAO = new OrderDAOImpl();
        orderItemDAO = new OrderItemDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        // Check login
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

            // Get order ID
            int orderId =
                    Integer.parseInt(
                        request.getParameter("orderId")
                    );

            // Get order
            Order order =
                    orderDAO.getOrderById(orderId);

            // Check whether order exists
            if (order == null) {

                response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Order not found"
                );

                return;
            }

            // Make sure the order belongs to logged-in user
            if (order.getUserId() != userId) {

                response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "You are not allowed to view this order"
                );

                return;
            }

            // Get order items
            List<OrderItem> orderItems =
                    orderItemDAO.getOrderItemsByOrderId(
                        orderId
                    );

            request.setAttribute(
                "order",
                order
            );

            request.setAttribute(
                "orderItems",
                orderItems
            );

            request.getRequestDispatcher(
                "/WEB-INF/views/order-details.jsp"
            ).forward(request, response);

        } catch (NumberFormatException e) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Invalid order ID"
            );

        } catch (SQLException e) {

            throw new ServletException(
                "Error retrieving order details",
                e
            );
        }
    }
}