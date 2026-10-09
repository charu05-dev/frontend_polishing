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
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Product;

@WebServlet("/products")
public class ProductListServlet extends HttpServlet {


private static final long serialVersionUID = 1L;

private ProductDAO productDAO;

@Override
public void init() throws ServletException {
    productDAO = new ProductDAOImpl();
}

@Override
protected void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws ServletException, IOException {

    try {

        String keyword =
                request.getParameter("keyword");

        String category =
                request.getParameter("category");

        String color =
                request.getParameter("color");

        String brand =
                request.getParameter("brand");

        String size =
                request.getParameter("size");

        String minPriceParameter =
                request.getParameter("minPrice");

        String maxPriceParameter =
                request.getParameter("maxPrice");

        String inStockParameter =
                request.getParameter("inStock");

        String sortBy =
                request.getParameter("sortBy");

        String sortOrder =
                request.getParameter("sortOrder");


        Integer categoryId = null;

        if (category != null
                && !category.trim().isEmpty()) {

            categoryId =
                    Integer.parseInt(category);
        }


        Double minPrice = null;

        if (minPriceParameter != null
                && !minPriceParameter.trim().isEmpty()) {

            minPrice =
                    Double.parseDouble(
                            minPriceParameter
                    );
        }


        Double maxPrice = null;

        if (maxPriceParameter != null
                && !maxPriceParameter.trim().isEmpty()) {

            maxPrice =
                    Double.parseDouble(
                            maxPriceParameter
                    );
        }


        Boolean inStock = null;

        if (inStockParameter != null
                && !inStockParameter.trim().isEmpty()) {

            inStock =
                    Boolean.parseBoolean(
                            inStockParameter
                    );
        }


        boolean hasKeyword =
                keyword != null
                && !keyword.trim().isEmpty();

        boolean hasCategory =
                categoryId != null;

        boolean hasColor =
                color != null
                && !color.trim().isEmpty();

        boolean hasBrand =
                brand != null
                && !brand.trim().isEmpty();

        boolean hasSize =
                size != null
                && !size.trim().isEmpty();

        boolean hasMinPrice =
                minPrice != null;

        boolean hasMaxPrice =
                maxPrice != null;

        boolean hasStockFilter =
                inStock != null;

        boolean hasSorting =
                sortBy != null
                && !sortBy.trim().isEmpty();


        List<Product> products;


        /*
         * If any filter or sorting is selected,
         * use getFilteredProducts().
         */
        if (hasKeyword
                || hasCategory
                || hasColor
                || hasBrand
                || hasSize
                || hasMinPrice
                || hasMaxPrice
                || hasStockFilter
                || hasSorting) {

            products =
                    productDAO.getFilteredProducts(

                            hasKeyword
                                ? keyword.trim()
                                : null,

                            categoryId,

                            hasColor
                                ? color.trim()
                                : null,

                            hasBrand
                                ? brand.trim()
                                : null,

                            hasSize
                                ? size.trim()
                                : null,

                            minPrice,

                            maxPrice,

                            inStock,

                            hasSorting
                                ? sortBy.trim()
                                : null,

                            sortOrder
                    );

        }

        /*
         * No filters or sorting
         */
        else {

            products =
                    productDAO.getAllProducts();
        }


        // Load filter options

        List<String> colors =
                productDAO.getAllColors();

        List<String> brands =
                productDAO.getAllBrands();


        request.setAttribute(
                "products",
                products
        );

        request.setAttribute(
                "keyword",
                keyword
        );

        request.setAttribute(
                "category",
                category
        );

        request.setAttribute(
                "color",
                color
        );

        request.setAttribute(
                "brand",
                brand
        );

        request.setAttribute(
                "size",
                size
        );

        request.setAttribute(
                "minPrice",
                minPriceParameter
        );

        request.setAttribute(
                "maxPrice",
                maxPriceParameter
        );

        request.setAttribute(
                "inStock",
                inStockParameter
        );

        request.setAttribute(
                "sortBy",
                sortBy
        );

        request.setAttribute(
                "sortOrder",
                sortOrder
        );

        request.setAttribute(
                "colors",
                colors
        );

        request.setAttribute(
                "brands",
                brands
        );


        request.getRequestDispatcher(
                "/WEB-INF/views/products.jsp"
        ).forward(request, response);


    } catch (NumberFormatException e) {

        response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Invalid filter value"
        );

    } catch (SQLException e) {

        throw new ServletException(
                "Error retrieving products",
                e
        );
    }
}


}
