package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.UserDAO;
import com.fashionstore.model.User;
import com.fashionstore.util.DBConnection;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean addUser(User user) throws SQLException {

        String sql = "INSERT INTO users "
                   + "(name, email, password, phone, address, city, state, pincode) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getCity());
            ps.setString(7, user.getState());
            ps.setString(8, user.getPincode());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public User getUserById(int userId) throws SQLException {

        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        }

        return null;
    }

    @Override
    public User getUserByEmail(String email) throws SQLException {

        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {

        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users ORDER BY user_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(mapUser(rs));
            }
        }

        return users;
    }

    @Override
    public User login(String email, String password) throws SQLException {

        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        }

        return null;
    }

    @Override
    public boolean emailExists(String email) throws SQLException {

        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {

        String sql = "UPDATE users SET name = ?, email = ?, password = ?, "
                   + "phone = ?, address = ?, city = ?, state = ?, pincode = ? "
                   + "WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getCity());
            ps.setString(7, user.getState());
            ps.setString(8, user.getPincode());
            ps.setInt(9, user.getUserId());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteUser(int userId) throws SQLException {

        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            return ps.executeUpdate() > 0;
        }
    }

    private User mapUser(ResultSet rs) throws SQLException {

        User user = new User();

        user.setUserId(rs.getInt("user_id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setPhone(rs.getString("phone"));
        user.setAddress(rs.getString("address"));
        user.setCity(rs.getString("city"));
        user.setState(rs.getString("state"));
        user.setPincode(rs.getString("pincode"));
        user.setCreatedAt(rs.getTimestamp("created_at"));

        return user;
    }
}