package com.fashionstore.model;
import java.sql.Timestamp;

public class Order {

    private int orderId;
    private int userId;
    private double totalAmount;
    private String orderStatus;
    private Timestamp orderDate;


    public Order() {
    }

    public Order(int orderId, int userId, double totalAmount,
                 String orderStatus, Timestamp orderDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }
}