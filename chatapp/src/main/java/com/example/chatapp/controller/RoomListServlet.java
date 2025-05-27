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

        req.getRequestDispatcher("/WEB-INF/views/rooms.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        // 認証済みユーザーの確認
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String action = req.getParameter("action");
        
        if ("delete".equals(action)) {
            // 削除処理
            String roomIdStr = req.getParameter("roomId");
            
            if (roomIdStr != null && !roomIdStr.trim().isEmpty()) {
                try {
                    long roomId = Long.parseLong(roomIdStr);
                    
                    // チャットルームを削除
                    boolean deleted = chatRoomDao.deleteById(roomId);
                    
                    if (deleted) {
                        // 削除成功時のメッセージ
                        session.setAttribute("successMessage", "チャットルームを削除しました。");
                    } else {
                        // 削除失敗時のメッセージ
                        session.setAttribute("errorMessage", "チャットルームの削除に失敗しました。");
                    }
                    
                } catch (NumberFormatException e) {
                    // 無効なID形式の場合
                    session.setAttribute("errorMessage", "無効なルームIDです。");
                }
            } else {
                // ルームIDが指定されなかった場合
                session.setAttribute("errorMessage", "ルームIDが指定されていません。");
            }
        }
        
        // ルーム一覧へリダイレクト
        resp.sendRedirect(req.getContextPath() + "/rooms");
    }
}