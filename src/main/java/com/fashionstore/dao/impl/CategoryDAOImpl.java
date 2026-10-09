package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.CategoryDAO;
import com.fashionstore.model.Category;
import com.fashionstore.util.DBConnection;

public class CategoryDAOImpl implements CategoryDAO {

    @Override
    public boolean addCategory(Category category) throws SQLException {

        String sql = "INSERT INTO categories (category_name) VALUES (?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, category.getCategoryName());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Category getCategoryById(int categoryId) throws SQLException {

        String sql = "SELECT * FROM categories WHERE category_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, categoryId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapCategory(rs);
                }
            }
        }

        return null;
    }

    @Override
    public Category getCategoryByName(String categoryName) throws SQLException {

        String sql = "SELECT * FROM categories WHERE category_name = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, categoryName);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapCategory(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Category> getAllCategories() throws SQLException {

        List<Category> categories = new ArrayList<>();

        String sql = "SELECT * FROM categories ORDER BY category_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                categories.add(mapCategory(rs));
            }
        }

        return categories;
    }

    @Override
    public boolean categoryExists(String categoryName) throws SQLException {

        String sql = "SELECT COUNT(*) FROM categories WHERE category_name = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, categoryName);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    @Override
    public boolean updateCategory(Category category) throws SQLException {

        String sql = "UPDATE categories SET category_name = ? "
                   + "WHERE category_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, category.getCategoryName());
            ps.setInt(2, category.getCategoryId());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteCategory(int categoryId) throws SQLException {

        String sql = "DELETE FROM categories WHERE category_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, categoryId);

            return ps.executeUpdate() > 0;
        }
    }

    private Category mapCategory(ResultSet rs) throws SQLException {

        Category category = new Category();

        category.setCategoryId(rs.getInt("category_id"));
        category.setCategoryName(rs.getString("category_name"));

        return category;
    }
}