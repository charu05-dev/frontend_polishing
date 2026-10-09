package com.fashionstore.dao;

import java.sql.SQLException;
import java.util.List;

import com.fashionstore.model.Order;

public interface OrderDAO {

    // Create
    // Returns generated order ID
    int createOrder(Order order)
            throws SQLException;

    // Read
    Order getOrderById(int orderId)
            throws SQLException;

    List<Order> getOrdersByUserId(int userId)
            throws SQLException;

    List<Order> getAllOrders()
            throws SQLException;

    // Status
    boolean updateOrderStatus(
            int orderId,
            String status)
            throws SQLException;

    // Update
    boolean updateOrder(Order order)
            throws SQLException;

    // Delete
    boolean deleteOrder(int orderId)
            throws SQLException;
}