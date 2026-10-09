package com.fashionstore.model;

public class ProductSize {

    private int productSizeId;
    private int productId;
    private String size;
    private int stock;

    public ProductSize() {
    }

    public ProductSize(int productSizeId, int productId,
                       String size, int stock) {
        this.productSizeId = productSizeId;
        this.productId = productId;
        this.size = size;
        this.stock = stock;
    }

    public int getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(int productSizeId) {
        this.productSizeId = productSizeId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}