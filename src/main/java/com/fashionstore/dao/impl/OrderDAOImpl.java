package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.OrderDAO;
import com.fashionstore.model.Order;
import com.fashionstore.util.DBConnection;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public int createOrder(Order order)
            throws SQLException {

        String sql = "INSERT INTO orders "
                   + "(user_id, total_amount, order_status) "
                   + "VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getUserId());
            ps.setDouble(2, order.getTotalAmount());
            ps.setString(3, order.getOrderStatus());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return -1;
    }

    @Override
    public Order getOrderById(int orderId)
            throws SQLException {

        String sql = "SELECT * FROM orders WHERE order_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapOrder(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Order> getOrdersByUserId(int userId)
            throws SQLException {

        List<Order> orders = new ArrayList<>();

        String sql = "SELECT * FROM orders "
                   + "WHERE user_id = ? "
                   + "ORDER BY order_date DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    orders.add(mapOrder(rs));
                }
            }
        }

        return orders;
    }

    @Override
    public List<Order> getAllOrders()
            throws SQLException {

        List<Order> orders = new ArrayList<>();

        String sql = "SELECT * FROM orders "
                   + "ORDER BY order_date DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                orders.add(mapOrder(rs));
            }
        }

        return orders;
    }

    @Override
    public boolean updateOrderStatus(
            int orderId, String status)
            throws SQLException {

        String sql = "UPDATE orders "
                   + "SET order_status = ? "
                   + "WHERE order_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, orderId);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateOrder(Order order)
            throws SQLException {

        String sql = "UPDATE orders SET "
                   + "user_id = ?, "
                   + "total_amount = ?, "
                   + "order_status = ? "
                   + "WHERE order_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, order.getUserId());
            ps.setDouble(2, order.getTotalAmount());
            ps.setString(3, order.getOrderStatus());
            ps.setInt(4, order.getOrderId());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteOrder(int orderId)
            throws SQLException {

        String sql = "DELETE FROM orders WHERE order_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            return ps.executeUpdate() > 0;
        }
    }

    private Order mapOrder(ResultSet rs)
            throws SQLException {

        Order order = new Order();

        order.setOrderId(
                rs.getInt("order_id"));

        order.setUserId(
                rs.getInt("user_id"));

        order.setTotalAmount(
                rs.getDouble("total_amount"));

        order.setOrderStatus(
                rs.getString("order_status"));

        order.setOrderDate(
                rs.getTimestamp("order_date"));

        return order;
    }
}