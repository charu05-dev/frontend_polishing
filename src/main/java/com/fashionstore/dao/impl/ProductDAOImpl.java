package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.model.Product;
import com.fashionstore.util.DBConnection;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public boolean addProduct(Product product) throws SQLException {

        String sql = "INSERT INTO products "
                   + "(category_id, product_name, description, price, color, image, brand) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, product.getCategoryId());
            ps.setString(2, product.getProductName());
            ps.setString(3, product.getDescription());
            ps.setDouble(4, product.getPrice());
            ps.setString(5, product.getColor());
            ps.setString(6, product.getImage());
            ps.setString(7, product.getBrand());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Product getProductById(int productId) throws SQLException {

        String sql = "SELECT * FROM products WHERE product_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapProduct(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Product> getAllProducts() throws SQLException {

        String sql = "SELECT * FROM products ORDER BY product_id DESC";

        return executeProductQuery(sql);
    }

    @Override
    public List<Product> getProductsByCategory(int categoryId)
            throws SQLException {

        String sql = "SELECT * FROM products "
                   + "WHERE category_id = ? "
                   + "ORDER BY product_id DESC";

        List<Product> products = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, categoryId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    products.add(mapProduct(rs));
                }
            }
        }

        return products;
    }

    @Override
    public List<Product> searchProducts(String keyword)
            throws SQLException {

        String sql = "SELECT * FROM products "
                   + "WHERE product_name LIKE ? "
                   + "OR description LIKE ? "
                   + "OR brand LIKE ? "
                   + "OR color LIKE ? "
                   + "ORDER BY product_id DESC";

        List<Product> products = new ArrayList<>();
        String search = "%" + keyword + "%";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);
            ps.setString(4, search);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    products.add(mapProduct(rs));
                }
            }
        }

        return products;
    }

    @Override
    public List<Product> getProductsByColor(String color)
            throws SQLException {

        String sql = "SELECT * FROM products "
                   + "WHERE color = ? "
                   + "ORDER BY product_id DESC";

        return executeProductQueryWithString(sql, color);
    }

    @Override
    public List<Product> getProductsByBrand(String brand)
            throws SQLException {

        String sql = "SELECT * FROM products "
                   + "WHERE brand = ? "
                   + "ORDER BY product_id DESC";

        return executeProductQueryWithString(sql, brand);
    }

    @Override
    public List<Product> getProductsByPriceRange(
            double minPrice, double maxPrice) throws SQLException {

        String sql = "SELECT * FROM products "
                   + "WHERE price BETWEEN ? AND ? "
                   + "ORDER BY price ASC";

        List<Product> products = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setDouble(1, minPrice);
            ps.setDouble(2, maxPrice);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    products.add(mapProduct(rs));
                }
            }
        }

        return products;
    }

    @Override
    public List<Product> getProductsBySize(String size)
            throws SQLException {

        String sql = "SELECT DISTINCT p.* "
                   + "FROM products p "
                   + "JOIN product_sizes ps "
                   + "ON p.product_id = ps.product_id "
                   + "WHERE ps.size = ? "
                   + "ORDER BY p.product_id DESC";

        return executeProductQueryWithString(sql, size);
    }

    @Override
    public List<Product> getProductsSortedByPrice(boolean ascending)
            throws SQLException {

        String order = ascending ? "ASC" : "DESC";

        String sql = "SELECT * FROM products ORDER BY price " + order;

        return executeProductQuery(sql);
    }

    @Override
    public List<Product> getProductsSortedByName(boolean ascending)
            throws SQLException {

        String order = ascending ? "ASC" : "DESC";

        String sql = "SELECT * FROM products ORDER BY product_name " + order;

        return executeProductQuery(sql);
    }

    @Override
    public List<Product> getFilteredProducts(
            String keyword,
            Integer categoryId,
            String color,
            String brand,
            String size,
            Double minPrice,
            Double maxPrice,
            Boolean inStock,
            String sortBy,
            String sortOrder) throws SQLException {

        StringBuilder sql = new StringBuilder(
                "SELECT DISTINCT p.* FROM products p ");

        List<Object> parameters = new ArrayList<>();

        boolean sizeFilter = size != null && !size.trim().isEmpty();

        if (sizeFilter) {
            sql.append("JOIN product_sizes ps "
                    + "ON p.product_id = ps.product_id ");
        }

        sql.append("WHERE 1=1 ");

        if (keyword != null && !keyword.trim().isEmpty()) {

            sql.append("AND (p.product_name LIKE ? "
                    + "OR p.description LIKE ? "
                    + "OR p.brand LIKE ? "
                    + "OR p.color LIKE ?) ");

            String search = "%" + keyword + "%";

            parameters.add(search);
            parameters.add(search);
            parameters.add(search);
            parameters.add(search);
        }

        if (categoryId != null) {
            sql.append("AND p.category_id = ? ");
            parameters.add(categoryId);
        }

        if (color != null && !color.trim().isEmpty()) {
            sql.append("AND p.color = ? ");
            parameters.add(color);
        }

        if (brand != null && !brand.trim().isEmpty()) {
            sql.append("AND p.brand = ? ");
            parameters.add(brand);
        }

        if (sizeFilter) {
            sql.append("AND ps.size = ? ");
            parameters.add(size);
        }

        if (minPrice != null) {
            sql.append("AND p.price >= ? ");
            parameters.add(minPrice);
        }

        if (maxPrice != null) {
            sql.append("AND p.price <= ? ");
            parameters.add(maxPrice);
        }

        if (inStock != null) {

            if (inStock) {

                sql.append(
                    "AND EXISTS "
                  + "(SELECT 1 FROM product_sizes ps2 "
                  + "WHERE ps2.product_id = p.product_id "
                  + "AND ps2.stock > 0) ");

            } else {

                sql.append(
                    "AND NOT EXISTS "
                  + "(SELECT 1 FROM product_sizes ps2 "
                  + "WHERE ps2.product_id = p.product_id "
                  + "AND ps2.stock > 0) ");
            }
        }

        String orderColumn = "p.product_id";
        String orderDirection = "DESC";

        if (sortBy != null) {

            switch (sortBy.toLowerCase()) {

                case "price":
                    orderColumn = "p.price";
                    break;

                case "name":
                    orderColumn = "p.product_name";
                    break;

                case "newest":
                    orderColumn = "p.product_id";
                    break;

                default:
                    orderColumn = "p.product_id";
            }
        }

        if ("asc".equalsIgnoreCase(sortOrder)) {
            orderDirection = "ASC";
        }

        sql.append("ORDER BY ")
           .append(orderColumn)
           .append(" ")
           .append(orderDirection);

        List<Product> products = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql.toString())) {

            for (int i = 0; i < parameters.size(); i++) {

                ps.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    products.add(mapProduct(rs));
                }
            }
        }

        return products;
    }

    @Override
    public List<String> getAllColors() throws SQLException {

        List<String> colors = new ArrayList<>();

        String sql = "SELECT DISTINCT color FROM products "
                   + "ORDER BY color";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                colors.add(rs.getString("color"));
            }
        }

        return colors;
    }

    @Override
    public List<String> getAllBrands() throws SQLException {

        List<String> brands = new ArrayList<>();

        String sql = "SELECT DISTINCT brand FROM products "
                   + "ORDER BY brand";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                brands.add(rs.getString("brand"));
            }
        }

        return brands;
    }

    @Override
    public boolean updateProduct(Product product) throws SQLException {

        String sql = "UPDATE products SET "
                   + "category_id = ?, "
                   + "product_name = ?, "
                   + "description = ?, "
                   + "price = ?, "
                   + "color = ?, "
                   + "image = ?, "
                   + "brand = ? "
                   + "WHERE product_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, product.getCategoryId());
            ps.setString(2, product.getProductName());
            ps.setString(3, product.getDescription());
            ps.setDouble(4, product.getPrice());
            ps.setString(5, product.getColor());
            ps.setString(6, product.getImage());
            ps.setString(7, product.getBrand());
            ps.setInt(8, product.getProductId());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteProduct(int productId) throws SQLException {

        String sql = "DELETE FROM products WHERE product_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, productId);

            return ps.executeUpdate() > 0;
        }
    }

    private Product mapProduct(ResultSet rs) throws SQLException {

        Product product = new Product();

        product.setProductId(rs.getInt("product_id"));
        product.setCategoryId(rs.getInt("category_id"));
        product.setProductName(rs.getString("product_name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getDouble("price"));
        product.setColor(rs.getString("color"));
        product.setImage(rs.getString("image"));
        product.setBrand(rs.getString("brand"));

        return product;
    }

    private List<Product> executeProductQuery(String sql)
            throws SQLException {

        List<Product> products = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                products.add(mapProduct(rs));
            }
        }

        return products;
    }

    private List<Product> executeProductQueryWithString(
            String sql, String value) throws SQLException {

        List<Product> products = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, value);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    products.add(mapProduct(rs));
                }
            }
        }

        return products;
    }
}