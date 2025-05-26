package com.example.chatapp.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.example.chatapp.dao.ChatRoomDao;
import com.example.chatapp.model.ChatRoom;

@WebServlet("/rooms")
public class RoomListServlet extends HttpServlet {

	private ChatRoomDao chatRoomDao;

	@Override
	public void init() throws ServletException {
	     chatRoomDao = new ChatRoomDao();
	    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
//    	System.out.println("RoomListServlet#doGet called");

        // 認証済みユーザーの確認
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        List<ChatRoom> rooms = chatRoomDao.findAll();
        req.setAttribute("rooms", rooms);
//        System.out.println("rooms.size() = " + rooms.size());

        req.getRequestDispatcher("rooms.jsp").forward(req, resp);
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
    	doGet(req, resp);
	
	}
}