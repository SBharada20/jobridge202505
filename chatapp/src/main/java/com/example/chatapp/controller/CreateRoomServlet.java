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

        if (name != null && !name.trim().isEmpty()) {
            ChatRoom room = new ChatRoom();
            room.setName(name);
            chatRoomDao.save(room);
        }

        resp.sendRedirect("rooms.jsp");
    }
}

