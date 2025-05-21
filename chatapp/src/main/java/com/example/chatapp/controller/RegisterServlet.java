package com.example.chatapp.controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.chatapp.dao.UserDao;
import com.example.chatapp.model.User;



@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserDao userDao;

    @Override
    public void init() {
        userDao = new UserDao(); // DAOはJDBC設定済み前提
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String displayName = req.getParameter("username");

        if (username == null || password == null || displayName == null ||
                username.isEmpty() || password.isEmpty() || displayName.isEmpty()) {
            req.setAttribute("error", "全ての項目を入力してください。");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

        // ユーザー名が既に存在するか確認
        try {
			if (userDao.findByUsername(username) != null) {
			    req.setAttribute("error", "そのユーザー名は既に使用されています。");
			    req.getRequestDispatcher("register.jsp").forward(req, resp);
			    return;
			}
		} catch (SQLException | ServletException | IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

        // ユーザー登録処理
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // セキュリティ上はハッシュ推奨
        user.setDisplayName(displayName);

        boolean success = userDao.insertUser(user);

        if (success) {
        	req.setAttribute("message", "登録が完了しました。ログインしてください。");
            resp.sendRedirect("login.jsp");
        } else {
            req.setAttribute("error", "ユーザー登録に失敗しました。");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }
}

