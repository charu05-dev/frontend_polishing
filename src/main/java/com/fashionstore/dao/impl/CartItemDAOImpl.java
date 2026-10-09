package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.model.CartItem;
import com.fashionstore.util.DBConnection;

public class CartItemDAOImpl implements CartItemDAO {

    @Override
    public boolean addCartItem(CartItem cartItem)
            throws SQLException {

        String sql = "INSERT INTO cart_items "
                   + "(cart_id, product_id, product_size_id, quantity) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, cartItem.getCartId());
            ps.setInt(2, cartItem.getProductId());
            ps.setInt(3, cartItem.getProductSizeId());
            ps.setInt(4, cartItem.getQuantity());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public CartItem getCartItemById(int cartItemId)
            throws SQLException {

        String sql = "SELECT * FROM cart_items "
                   + "WHERE cart_item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, cartItemId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapCartItem(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<CartItem> getCartItemsByCartId(int cartId)
            throws SQLException {

        List<CartItem> items = new ArrayList<>();

        String sql =
                "SELECT ci.cart_item_id, "
              + "ci.cart_id, "
              + "ci.product_id, "
              + "ci.product_size_id, "
              + "ci.quantity, "
              + "p.product_name, "
              + "p.price, "
              + "ps.size "
              + "FROM cart_items ci "
              + "JOIN products p "
              + "ON ci.product_id = p.product_id "
              + "JOIN product_sizes ps "
              + "ON ci.product_size_id = ps.product_size_id "
              + "WHERE ci.cart_id = ? "
              + "ORDER BY ci.cart_item_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, cartId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    CartItem item = new CartItem();

                    item.setCartItemId(
                            rs.getInt("cart_item_id"));

                    item.setCartId(
                            rs.getInt("cart_id"));

                    item.setProductId(
                            rs.getInt("product_id"));

                    item.setProductSizeId(
                            rs.getInt("product_size_id"));

                    item.setQuantity(
                            rs.getInt("quantity"));

                    item.setProductName(
                            rs.getString("product_name"));

                    item.setPrice(
                            rs.getDouble("price"));

                    item.setSize(
                            rs.getString("size"));

                    items.add(item);
                }
            }
        }

        return items;
    }

    @Override
    public CartItem getCartItemByProductSize(
            int cartId, int productSizeId)
            throws SQLException {

        String sql = "SELECT * FROM cart_items "
                   + "WHERE cart_id = ? "
                   + "AND product_size_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            ps.setInt(2, productSizeId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapCartItem(rs);
                }
            }
        }

        return null;
    }

    @Override
    public boolean updateQuantity(
            int cartItemId, int quantity)
            throws SQLException {

        String sql = "UPDATE cart_items "
                   + "SET quantity = ? "
                   + "WHERE cart_item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean increaseQuantity(
            int cartItemId, int quantity)
            throws SQLException {

        String sql = "UPDATE cart_items "
                   + "SET quantity = quantity + ? "
                   + "WHERE cart_item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean decreaseQuantity(
            int cartItemId, int quantity)
            throws SQLException {

        String sql = "UPDATE cart_items "
                   + "SET quantity = quantity - ? "
                   + "WHERE cart_item_id = ? "
                   + "AND quantity >= ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);
            ps.setInt(3, quantity);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean removeCartItem(int cartItemId)
            throws SQLException {

        String sql = "DELETE FROM cart_items "
                   + "WHERE cart_item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, cartItemId);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean removeCartItemByProductSize(
            int cartId, int productSizeId)
            throws SQLException {

        String sql = "DELETE FROM cart_items "
                   + "WHERE cart_id = ? "
                   + "AND product_size_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            ps.setInt(2, productSizeId);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean clearCart(int cartId)
            throws SQLException {

        String sql = "DELETE FROM cart_items "
                   + "WHERE cart_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, cartId);

            return ps.executeUpdate() > 0;
        }
    }

    private CartItem mapCartItem(ResultSet rs)
            throws SQLException {

        CartItem cartItem = new CartItem();

        cartItem.setCartItemId(
                rs.getInt("cart_item_id"));

        cartItem.setCartId(
                rs.getInt("cart_id"));

        cartItem.setProductId(
                rs.getInt("product_id"));

        cartItem.setProductSizeId(
                rs.getInt("product_size_id"));

        cartItem.setQuantity(
                rs.getInt("quantity"));

        return cartItem;
    }
}