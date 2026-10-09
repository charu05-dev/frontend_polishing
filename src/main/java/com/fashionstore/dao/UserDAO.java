package com.fashionstore.dao;

import java.sql.SQLException;
import java.util.List;

import com.fashionstore.model.User;

public interface UserDAO {

    boolean addUser(User user) throws SQLException;

    User getUserById(int userId) throws SQLException;

    User getUserByEmail(String email) throws SQLException;

    List<User> getAllUsers() throws SQLException;

    User login(String email, String password) throws SQLException;

    boolean emailExists(String email) throws SQLException;

    boolean updateUser(User user) throws SQLException;

    boolean deleteUser(int userId) throws SQLException;
}