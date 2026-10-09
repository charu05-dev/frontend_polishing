package com.fashionstore.model;

public class CartItem {

    private int cartItemId;
    private int cartId;
    private int productId;
    private int productSizeId;
    private int quantity;

    // Product details for displaying cart
    private String productName;
    private double price;
    private String size;

    public CartItem() {

    }

    public CartItem(int cartItemId, int cartId, int productId,
                    int productSizeId, int quantity) {

        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.productId = productId;
        this.productSizeId = productSizeId;
        this.quantity = quantity;
    }

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(int productSizeId) {
        this.productSizeId = productSizeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}