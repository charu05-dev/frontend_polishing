package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.model.Cart;
import com.fashionstore.util.DBConnection;

public class CartDAOImpl implements CartDAO {

    @Override
    public boolean createCart(Cart cart) throws SQLException {

        String sql = "INSERT INTO cart (user_id) VALUES (?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, cart.getUserId());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Cart getCartById(int cartId) throws SQLException {

        String sql = "SELECT * FROM cart WHERE cart_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, cartId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapCart(rs);
                }
            }
        }

        return null;
    }

    @Override
    public Cart getCartByUserId(int userId) throws SQLException {

        String sql = "SELECT * FROM cart WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapCart(rs);
                }
            }
        }

        return null;
    }

    @Override
    public boolean cartExistsForUser(int userId)
            throws SQLException {

        String sql = "SELECT COUNT(*) FROM cart WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    @Override
    public Cart getOrCreateCart(int userId)
            throws SQLException {

        Cart cart = getCartByUserId(userId);

        if (cart != null) {
            return cart;
        }

        String sql = "INSERT INTO cart (user_id) VALUES (?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     sql,
                     java.sql.Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, userId);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {

                if (rs.next()) {

                    int cartId = rs.getInt(1);

                    return getCartById(cartId);
                }
            }
        }

        return null;
    }

    @Override
    public boolean deleteCart(int cartId)
            throws SQLException {

        String sql = "DELETE FROM cart WHERE cart_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, cartId);

            return ps.executeUpdate() > 0;
        }
    }

    private Cart mapCart(ResultSet rs)
            throws SQLException {

        Cart cart = new Cart();

        cart.setCartId(rs.getInt("cart_id"));
        cart.setUserId(rs.getInt("user_id"));
        cart.setCreatedAt(rs.getTimestamp("created_at"));

        return cart;
    }
}