package com.fashionstore.dao;

import java.sql.SQLException;
import java.util.List;

import com.fashionstore.model.Product;

public interface ProductDAO {

    // Create
    boolean addProduct(Product product) throws SQLException;

    // Basic read operations
    Product getProductById(int productId) throws SQLException;

    List<Product> getAllProducts() throws SQLException;

    List<Product> getProductsByCategory(int categoryId)
            throws SQLException;

    // Search
    List<Product> searchProducts(String keyword)
            throws SQLException;

    // Individual filters
    List<Product> getProductsByColor(String color)
            throws SQLException;

    List<Product> getProductsByBrand(String brand)
            throws SQLException;

    List<Product> getProductsByPriceRange(
            double minPrice,
            double maxPrice)
            throws SQLException;

    List<Product> getProductsBySize(String size)
            throws SQLException;

    // Sorting
    List<Product> getProductsSortedByPrice(boolean ascending)
            throws SQLException;

    List<Product> getProductsSortedByName(boolean ascending)
            throws SQLException;

    // Combined filtering
    List<Product> getFilteredProducts(
            String keyword,
            Integer categoryId,
            String color,
            String brand,
            String size,
            Double minPrice,
            Double maxPrice,
            Boolean inStock,
            String sortBy,
            String sortOrder)
            throws SQLException;

    // Filter options
    List<String> getAllColors()
            throws SQLException;

    List<String> getAllBrands()
            throws SQLException;

    // Update
    boolean updateProduct(Product product)
            throws SQLException;

    // Delete
    boolean deleteProduct(int productId)
            throws SQLException;
}