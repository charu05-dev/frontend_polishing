package com.fashionstore.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.ProductSizeDAO;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.dao.impl.ProductSizeDAOImpl;
import com.fashionstore.model.Product;
import com.fashionstore.model.ProductSize;

@WebServlet("/product")
public class ProductDetailsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ProductDAO productDAO;
    private ProductSizeDAO productSizeDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAOImpl();
        productSizeDAO = new ProductSizeDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");

        // Check if product ID is provided
        if (id == null || id.trim().isEmpty()) {
            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Product ID is missing"
            );
            return;
        }

        try {

            int productId = Integer.parseInt(id);

            // Get product
            Product product = productDAO.getProductById(productId);

            // Product not found
            if (product == null) {
                response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Product not found"
                );
                return;
            }

            // Get available sizes
            List<ProductSize> sizes =
                    productSizeDAO.getAvailableSizesByProductId(productId);

            // Send data to JSP
            request.setAttribute("product", product);
            request.setAttribute("sizes", sizes);

            // Open product details page
            request.getRequestDispatcher(
                "/WEB-INF/views/product-details.jsp"
            ).forward(request, response);

        } catch (NumberFormatException e) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Invalid product ID"
            );

        } catch (SQLException e) {

            throw new ServletException(
                "Error retrieving product details",
                e
            );
        }
    }
}