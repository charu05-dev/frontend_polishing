package com.fashionstore.dao;

import java.sql.SQLException;
import java.util.List;

import com.fashionstore.model.Category;

public interface CategoryDAO {

    boolean addCategory(Category category) throws SQLException;

    Category getCategoryById(int categoryId) throws SQLException;

    Category getCategoryByName(String categoryName) throws SQLException;

    List<Category> getAllCategories() throws SQLException;

    boolean categoryExists(String categoryName) throws SQLException;

    boolean updateCategory(Category category) throws SQLException;

    boolean deleteCategory(int categoryId) throws SQLException;
}