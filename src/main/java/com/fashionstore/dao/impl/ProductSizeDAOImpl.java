package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.ProductSizeDAO;
import com.fashionstore.model.ProductSize;
import com.fashionstore.util.DBConnection;

public class ProductSizeDAOImpl implements ProductSizeDAO {

    @Override
    public boolean addProductSize(ProductSize productSize)
            throws SQLException {

        String sql = "INSERT INTO product_sizes "
                   + "(product_id, size, stock) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, productSize.getProductId());
            ps.setString(2, productSize.getSize());
            ps.setInt(3, productSize.getStock());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public ProductSize getProductSizeById(int productSizeId)
            throws SQLException {

        String sql = "SELECT * FROM product_sizes "
                   + "WHERE product_size_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, productSizeId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapProductSize(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<ProductSize> getSizesByProductId(int productId)
            throws SQLException {

        String sql = "SELECT * FROM product_sizes "
                   + "WHERE product_id = ? "
                   + "ORDER BY product_size_id";

        return getSizes(sql, productId);
    }

    @Override
    public List<ProductSize> getAvailableSizesByProductId(int productId)
            throws SQLException {

        String sql = "SELECT * FROM product_sizes "
                   + "WHERE product_id = ? AND stock > 0 "
                   + "ORDER BY product_size_id";

        return getSizes(sql, productId);
    }

    @Override
    public ProductSize getProductSize(int productId, String size)
            throws SQLException {

        String sql = "SELECT * FROM product_sizes "
                   + "WHERE product_id = ? AND size = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ps.setString(2, size);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapProductSize(rs);
                }
            }
        }

        return null;
    }

    @Override
    public boolean updateStock(int productSizeId, int stock)
            throws SQLException {

        String sql = "UPDATE product_sizes SET stock = ? "
                   + "WHERE product_size_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, stock);
            ps.setInt(2, productSizeId);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean increaseStock(int productSizeId, int quantity)
            throws SQLException {

        String sql = "UPDATE product_sizes "
                   + "SET stock = stock + ? "
                   + "WHERE product_size_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, productSizeId);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean decreaseStock(int productSizeId, int quantity)
            throws SQLException {

        String sql = "UPDATE product_sizes "
                   + "SET stock = stock - ? "
                   + "WHERE product_size_id = ? "
                   + "AND stock >= ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, productSizeId);
            ps.setInt(3, quantity);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean hasEnoughStock(
            int productSizeId, int quantity) throws SQLException {

        String sql = "SELECT stock FROM product_sizes "
                   + "WHERE product_size_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, productSizeId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("stock") >= quantity;
                }
            }
        }

        return false;
    }

    @Override
    public boolean updateProductSize(ProductSize productSize)
            throws SQLException {

        String sql = "UPDATE product_sizes SET "
                   + "product_id = ?, size = ?, stock = ? "
                   + "WHERE product_size_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, productSize.getProductId());
            ps.setString(2, productSize.getSize());
            ps.setInt(3, productSize.getStock());
            ps.setInt(4, productSize.getProductSizeId());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteProductSize(int productSizeId)
            throws SQLException {

        String sql = "DELETE FROM product_sizes "
                   + "WHERE product_size_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, productSizeId);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteSizesByProductId(int productId)
            throws SQLException {

        String sql = "DELETE FROM product_sizes "
                   + "WHERE product_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, productId);

            return ps.executeUpdate() > 0;
        }
    }

    private ProductSize mapProductSize(ResultSet rs)
            throws SQLException {

        ProductSize productSize = new ProductSize();

        productSize.setProductSizeId(
                rs.getInt("product_size_id"));

        productSize.setProductId(
                rs.getInt("product_id"));

        productSize.setSize(
                rs.getString("size"));

        productSize.setStock(
                rs.getInt("stock"));

        return productSize;
    }

    private List<ProductSize> getSizes(
            String sql, int productId) throws SQLException {

        List<ProductSize> sizes = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    sizes.add(mapProductSize(rs));
                }
            }
        }

        return sizes;
    }
}