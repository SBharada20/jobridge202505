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
import com.example.chatapp.model.ChatRoom;
import com.example.chatapp.model.User;

@WebServlet("/chat")
public class ChatServlet extends HttpServlet {

    private ChatMessageDao messageDao;
    private UserDao userDao;
    private ChatRoomDao roomDao;

    @Override
    public void init() {
        messageDao = new ChatMessageDao();
        userDao = new UserDao(); // ← ヌルポ対策のため必ず初期化
        roomDao = new ChatRoomDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roomIdStr = req.getParameter("roomId");
        if (roomIdStr == null) {
            resp.sendRedirect("rooms.jsp");
            return;
        }

        long roomId;
        try {
            roomId = Integer.parseInt(roomIdStr);
        } catch (NumberFormatException e) {
            resp.sendRedirect("rooms.jsp");
            return;
        }

        // チャットルーム情報取得
        ChatRoom room = roomDao.findById(roomId);
        if (room == null) {
            resp.sendRedirect("rooms.jsp");
            return;
        }

        // メッセージ取得と表示名セット
        List<ChatMessage> messages = messageDao.findByRoomId((long) roomId);
        for (ChatMessage msg : messages) {
            User user = userDao.getUserById(msg.getUserId());
            if (user != null) {
                msg.setDisplayName(user.getDisplayName());
            }
        }

        // データをJSPへ渡す
        req.setAttribute("roomId", roomId);
        req.setAttribute("room", room);
        req.setAttribute("messages", messages);
        req.getRequestDispatcher("chat.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String content = req.getParameter("content");
        String roomIdStr = req.getParameter("roomId");

        HttpSession session = req.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);

        if (user == null || content == null || content.trim().isEmpty() || roomIdStr == null) {
            resp.sendRedirect("rooms.jsp");
            return;
        }

        long roomId;
        try {
            roomId = Integer.parseInt(roomIdStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            resp.sendRedirect("rooms.jsp");
            return;
        }

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRoomId(roomId);
        chatMessage.setUserId(user.getId());
        chatMessage.setContent(content.trim());
        chatMessage.setTimestamp(LocalDateTime.now());

        try {
            messageDao.save(chatMessage);
//            System.out.println("Message saved: " + chatMessage.getContent());
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "メッセージの保存中にエラーが発生しました。");
            req.getRequestDispatcher("chat.jsp").forward(req, resp);
            return;
        }

        // 成功時、チャット画面を再表示
        resp.sendRedirect("chat?roomId=" + roomId);
    }

}
