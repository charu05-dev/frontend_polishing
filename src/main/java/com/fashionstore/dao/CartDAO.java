package com.fashionstore.dao;

import java.sql.SQLException;

import com.fashionstore.model.Cart;

public interface CartDAO {

    // Create
    boolean createCart(Cart cart)
            throws SQLException;

    // Read
    Cart getCartById(int cartId)
            throws SQLException;

    Cart getCartByUserId(int userId)
            throws SQLException;

    // Check
    boolean cartExistsForUser(int userId)
            throws SQLException;

    // Get existing cart or create a new one
    Cart getOrCreateCart(int userId)
            throws SQLException;

    // Delete
    boolean deleteCart(int cartId)
            throws SQLException;
}