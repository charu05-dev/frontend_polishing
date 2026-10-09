package com.fashionstore.controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fashionstore.dao.UserDAO;
import com.fashionstore.dao.impl.UserDAOImpl;
import com.fashionstore.model.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {


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
            "/WEB-INF/views/login.jsp"
    ).forward(request, response);
}

@Override
protected void doPost(HttpServletRequest request,
                       HttpServletResponse response)
        throws ServletException, IOException {

    try {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user =
                userDAO.login(email, password);

        if (user != null) {

            HttpSession session =
                    request.getSession();

            session.setAttribute(
                    "loggedInUser",
                    user
            );

            response.sendRedirect(
                    request.getContextPath() + "/home"
            );

        } else {

            request.setAttribute(
                    "error",
                    "Invalid email or password."
            );

            request.getRequestDispatcher(
                    "/WEB-INF/views/login.jsp"
            ).forward(request, response);
        }

    } catch (SQLException e) {

        throw new ServletException(
                "Error during login",
                e
        );
    }
}


}
