package com.fashionstore.dao;

import java.sql.SQLException;
import java.util.List;

import com.fashionstore.model.ProductSize;

public interface ProductSizeDAO {

    // Create
    boolean addProductSize(ProductSize productSize)
            throws SQLException;

    // Read
    ProductSize getProductSizeById(int productSizeId)
            throws SQLException;

    List<ProductSize> getSizesByProductId(int productId)
            throws SQLException;

    List<ProductSize> getAvailableSizesByProductId(int productId)
            throws SQLException;

    ProductSize getProductSize(int productId, String size)
            throws SQLException;

    // Stock operations
    boolean updateStock(int productSizeId, int stock)
            throws SQLException;

    boolean increaseStock(int productSizeId, int quantity)
            throws SQLException;

    boolean decreaseStock(int productSizeId, int quantity)
            throws SQLException;

    boolean hasEnoughStock(int productSizeId, int quantity)
            throws SQLException;

    // Update
    boolean updateProductSize(ProductSize productSize)
            throws SQLException;

    // Delete
    boolean deleteProductSize(int productSizeId)
            throws SQLException;

    boolean deleteSizesByProductId(int productId)
            throws SQLException;
}