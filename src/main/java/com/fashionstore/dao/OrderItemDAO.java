package com.fashionstore.dao;

import java.sql.SQLException;
import java.util.List;

import com.fashionstore.model.OrderItem;

public interface OrderItemDAO {

    // Create
    boolean addOrderItem(OrderItem orderItem)
            throws SQLException;

    // Read
    OrderItem getOrderItemById(int orderItemId)
            throws SQLException;

    List<OrderItem> getOrderItemsByOrderId(int orderId)
            throws SQLException;

    // Update
    boolean updateQuantity(
            int orderItemId,
            int quantity)
            throws SQLException;

    // Delete
    boolean deleteOrderItem(int orderItemId)
            throws SQLException;

    boolean deleteOrderItemsByOrderId(int orderId)
            throws SQLException;
}