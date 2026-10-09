package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.OrderItemDAO;
import com.fashionstore.model.OrderItem;
import com.fashionstore.util.DBConnection;

public class OrderItemDAOImpl implements OrderItemDAO {

    @Override
    public boolean addOrderItem(OrderItem orderItem)
            throws SQLException {

        String sql = "INSERT INTO order_items "
                   + "(order_id, product_id, product_size_id, quantity, price) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderItem.getOrderId());
            ps.setInt(2, orderItem.getProductId());
            ps.setInt(3, orderItem.getProductSizeId());
            ps.setInt(4, orderItem.getQuantity());
            ps.setDouble(5, orderItem.getPrice());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public OrderItem getOrderItemById(int orderItemId)
            throws SQLException {

        String sql = "SELECT * FROM order_items "
                   + "WHERE order_item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderItemId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapOrderItem(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(int orderId)
            throws SQLException {

        List<OrderItem> items = new ArrayList<>();

        String sql = "SELECT * FROM order_items "
                   + "WHERE order_id = ? "
                   + "ORDER BY order_item_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    items.add(mapOrderItem(rs));
                }
            }
        }

        return items;
    }

    @Override
    public boolean updateQuantity(
            int orderItemId, int quantity)
            throws SQLException {

        String sql = "UPDATE order_items "
                   + "SET quantity = ? "
                   + "WHERE order_item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, orderItemId);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteOrderItem(int orderItemId)
            throws SQLException {

        String sql = "DELETE FROM order_items "
                   + "WHERE order_item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderItemId);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteOrderItemsByOrderId(int orderId)
            throws SQLException {

        String sql = "DELETE FROM order_items "
                   + "WHERE order_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            return ps.executeUpdate() > 0;
        }
    }

    private OrderItem mapOrderItem(ResultSet rs)
            throws SQLException {

        OrderItem orderItem = new OrderItem();

        orderItem.setOrderItemId(
                rs.getInt("order_item_id"));

        orderItem.setOrderId(
                rs.getInt("order_id"));

        orderItem.setProductId(
                rs.getInt("product_id"));

        orderItem.setProductSizeId(
                rs.getInt("product_size_id"));

        orderItem.setQuantity(
                rs.getInt("quantity"));

        orderItem.setPrice(
                rs.getDouble("price"));

        return orderItem;
    }
}