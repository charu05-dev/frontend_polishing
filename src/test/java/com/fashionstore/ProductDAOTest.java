package com.fashionstore;

import java.sql.SQLException;
import java.util.List;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Product;

public class ProductDAOTest {

    public static void main(String[] args) {

        ProductDAO productDAO = new ProductDAOImpl();

        try {

            List<Product> products = productDAO.getAllProducts();

            System.out.println("Total Products: " + products.size());
            System.out.println();

            for (Product product : products) {

                System.out.println("Product ID: " + product.getProductId());
                System.out.println("Product Name: " + product.getProductName());
                System.out.println("Price: " + product.getPrice());
                System.out.println("Color: " + product.getColor());
                System.out.println("Brand: " + product.getBrand());
                System.out.println("--------------------------------");
            }

        } catch (SQLException e) {

            System.out.println("Error while fetching products!");
            e.printStackTrace();
        }
    }
}