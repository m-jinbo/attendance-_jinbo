package com.example.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.UserFindDAO;
import model.entity.Employees;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String userIdParam = request.getParameter("userid");
        String password = request.getParameter("password");

        boolean hasError = false;
        Integer userId = null;

        // Validate user ID
        if (userIdParam == null || userIdParam.isEmpty()) {
            request.setAttribute("userIdError", "社員IDを入力してください。");
            hasError = true;
        } else {
            try {
                userId = Integer.parseInt(userIdParam);
            } catch (NumberFormatException e) {
                request.setAttribute("userIdError", "社員IDは数字でなければなりません。");
                hasError = true;
            }
        }

        // Validate password
        if (password == null || password.isEmpty()) {
            request.setAttribute("passwordError", "パスワードを入力してください。");
            hasError = true;
        }

        if (hasError) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("top.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            UserFindDAO userFindDAO = new UserFindDAO();
            Employees employee = new Employees();
            employee.setUserId(userId);
            employee.setPassword(password);

            Employees authenticatedEmployee = userFindDAO.findAccount(employee);

            if (authenticatedEmployee != null) {
                HttpSession session = request.getSession();
                session.setAttribute("userId", authenticatedEmployee.getUserId());
                session.setAttribute("name", authenticatedEmployee.getName());

                response.sendRedirect("confirmForm.jsp");
            } else {
                request.setAttribute("error", "社員IDまたはパスワードが正しくありません。");
                RequestDispatcher dispatcher = request.getRequestDispatcher("top.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "データベースエラーが発生しました。", e);
            request.setAttribute("error", "データベースエラーが発生しました。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("top.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "システムエラーが発生しました。", e);
            request.setAttribute("error", "システムエラーが発生しました。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("top.jsp");
            dispatcher.forward(request, response);
        }
    }
}
