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
import com.fashionstore.dao.impl.OrderDAOImpl;
import com.fashionstore.model.Order;
import com.fashionstore.model.User;

@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {


private static final long serialVersionUID = 1L;

private OrderDAO orderDAO;

@Override
public void init() throws ServletException {
    orderDAO = new OrderDAOImpl();
}

@Override
protected void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws ServletException, IOException {

    HttpSession session = request.getSession(false);

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

        // Get user's orders
        List<Order> orders =
                orderDAO.getOrdersByUserId(userId);

        request.setAttribute(
            "orders",
            orders
        );

        request.getRequestDispatcher(
            "/WEB-INF/views/orders.jsp"
        ).forward(request, response);

    } catch (SQLException e) {

        throw new ServletException(
            "Error retrieving orders",
            e
        );
    }
}


}
