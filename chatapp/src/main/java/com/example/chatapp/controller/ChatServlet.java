package com.example.chatapp.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.example.chatapp.dao.ChatMessageDao;
import com.example.chatapp.dao.ChatRoomDao;
import com.example.chatapp.dao.UserDao;
import com.example.chatapp.model.ChatMessage;
import com.example.chatapp.model.User;


@WebServlet("/chat")
public class ChatServlet extends HttpServlet {

    private ChatMessageDao messageDao;
    private UserDao userDao;
    private ChatRoomDao roomDao;
    
    
    @Override
    public void init() {
        messageDao = new ChatMessageDao();
        roomDao = new ChatRoomDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roomIdStr = req.getParameter("roomId");
        if (roomIdStr == null) {
            resp.sendRedirect("rooms.jsp");
            return;
        }

        Long roomId;
        try {
            roomId = (long) Integer.parseInt(roomIdStr);
        } catch (NumberFormatException e) {
            resp.sendRedirect("rooms.jsp");
            return;
        }

        // メッセージ一覧を取得
        List<ChatMessage> messages = messageDao.findByRoomId(roomId);
        // 各メッセージに表示名をセット（JOIN代用）
        for (ChatMessage msg : messages) {
            User user = userDao.getUserById(msg.getUserId());
            if (user != null) {
                msg.setDisplayName(user.getDisplayName());
            }
        }

        req.setAttribute("roomId", roomId);
        req.setAttribute("messages", messages);
        req.getRequestDispatcher("chat.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String content = req.getParameter("content");
        String roomIdStr = req.getParameter("roomId");
        
        User user = (User) req.getSession().getAttribute("user");

        if (roomIdStr == null || content == null || content.trim().isEmpty()) {
            resp.sendRedirect("rooms.jsp");
            return;
        }

        int roomId;
        try {
            roomId = Integer.parseInt(roomIdStr);
        } catch (NumberFormatException e) {
            resp.sendRedirect("rooms.jsp");
            return;
        }

        HttpSession session = req.getSession(false);
        

        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRoomId(roomId);
        chatMessage.setUserId(user.getId());
        chatMessage.setContent(content);
        chatMessage.setTimestamp(LocalDateTime.now());

        messageDao.save(chatMessage);

        // 再表示
        resp.sendRedirect("chat?roomId=" + roomId);
    }
}
