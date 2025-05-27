package com.example.chatapp.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.chatapp.dao.ChatRoomDao;
import com.example.chatapp.model.ChatRoom;

@WebServlet("/createRoom")
public class CreateRoomServlet extends HttpServlet {

    private final ChatRoomDao chatRoomDao = new ChatRoomDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // フォームを表示
        req.getRequestDispatcher("/WEB-INF/views/createRoom.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name = req.getParameter("name");

        if (name == null || name.trim().isEmpty()) {
            // 名前が空の場合
            req.setAttribute("errorMessage", "ルーム名を入力してください。");
            req.getRequestDispatcher("/WEB-INF/views/createRoom.jsp").forward(req, resp);
            return;
        }

        // 空白文字をトリム
        name = name.trim();

        // 名前の重複チェック
        if (chatRoomDao.existsByName(name)) {
            // 重複する名前の場合
            req.setAttribute("errorMessage", "そのルーム名は既に使用されています。別の名前を入力してください。");
            req.setAttribute("inputName", name); // 入力値を保持
            req.getRequestDispatcher("/WEB-INF/views/createRoom.jsp").forward(req, resp);
            return;
        }

        // 重複しない場合は保存
        ChatRoom room = new ChatRoom();
        room.setName(name);
        chatRoomDao.save(room);

        // ルーム一覧へリダイレクト
        resp.sendRedirect(req.getContextPath() + "/rooms");
    }
}