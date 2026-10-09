package com.fashionstore.dao;

import java.sql.SQLException;
import java.util.List;

import com.fashionstore.model.CartItem;

public interface CartItemDAO {

    // Create
    boolean addCartItem(CartItem cartItem)
            throws SQLException;

    // Read
    CartItem getCartItemById(int cartItemId)
            throws SQLException;

    List<CartItem> getCartItemsByCartId(int cartId)
            throws SQLException;

    CartItem getCartItemByProductSize(
            int cartId,
            int productSizeId)
            throws SQLException;

    // Update quantity
    boolean updateQuantity(
            int cartItemId,
            int quantity)
            throws SQLException;

    boolean increaseQuantity(
            int cartItemId,
            int quantity)
            throws SQLException;

    boolean decreaseQuantity(
            int cartItemId,
            int quantity)
            throws SQLException;

    // Delete
    boolean removeCartItem(int cartItemId)
            throws SQLException;

    boolean removeCartItemByProductSize(
            int cartId,
            int productSizeId)
            throws SQLException;

    // Empty cart
    boolean clearCart(int cartId)
            throws SQLException;
}