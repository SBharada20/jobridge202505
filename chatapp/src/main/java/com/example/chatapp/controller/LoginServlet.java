package com.example.chatapp.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
import com.example.chatapp.dao.UserDao;
import com.example.chatapp.model.User;



@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private UserDao userDao = new UserDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		if (username == null || username.isEmpty() ||
		          password == null || password.isEmpty()) {
		            req.setAttribute("error", "ユーザー名とパスワードを入力してください。");
		            req.getRequestDispatcher("login.jsp")
		               .forward(req, resp);
		            return;
		        }

//		User user = userDao.findByUsernameAndPassword(username, password);
		
		User user;
		user = userDao.findByUsernameAndPassword(username, password);
		if (user != null) {
			HttpSession session = req.getSession();
			session.setAttribute("user", user);
			resp.sendRedirect(req.getContextPath() + "/rooms");
//				RequestDispatcher dispatcher = req.getRequestDispatcher("rooms.jsp");;
//				dispatcher.forward(req, resp);		
			
			
		} else {
			req.setAttribute("error", "ユーザー名またはパスワードが間違っています。");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}

	}
}