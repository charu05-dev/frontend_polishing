package com.fashionstore.controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fashionstore.dao.UserDAO;
import com.fashionstore.dao.impl.UserDAOImpl;
import com.fashionstore.model.User;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {


private static final long serialVersionUID = 1L;

private UserDAO userDAO;

@Override
public void init() throws ServletException {
    userDAO = new UserDAOImpl();
}

@Override
protected void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws ServletException, IOException {

    request.getRequestDispatcher(
            "/WEB-INF/views/register.jsp"
    ).forward(request, response);
}

@Override
protected void doPost(HttpServletRequest request,
                       HttpServletResponse response)
        throws ServletException, IOException {

    try {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String pincode = request.getParameter("pincode");


        // Check if email already exists

        if (userDAO.emailExists(email)) {

            request.setAttribute(
                    "error",
                    "Email already registered."
            );

            request.getRequestDispatcher(
                    "/WEB-INF/views/register.jsp"
            ).forward(request, response);

            return;
        }


        // Create User object

        User user = new User();

        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setAddress(address);
        user.setCity(city);
        user.setState(state);
        user.setPincode(pincode);


        // Save user

        boolean registered =
                userDAO.addUser(user);


        if (registered) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login"
            );

        } else {

            request.setAttribute(
                    "error",
                    "Registration failed. Please try again."
            );

            request.getRequestDispatcher(
                    "/WEB-INF/views/register.jsp"
            ).forward(request, response);
        }


    } catch (SQLException e) {

        throw new ServletException(
                "Error registering user",
                e
        );
    }
}


}
