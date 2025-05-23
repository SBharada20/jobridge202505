package com.example.chatapp.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.example.chatapp.dao.ChatMessageDao;
import com.example.chatapp.dao.ChatRoomDao;
import com.example.chatapp.dao.UserDao3;
import com.example.chatapp.model.ChatMessage;
import com.example.chatapp.model.ChatRoom;
import com.example.chatapp.model.User;

@WebServlet("/chat")
public class ChatServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ChatServlet.class.getName());
    private static final String ROOMS_JSP = "rooms.jsp";
    private static final String CHAT_JSP = "chat.jsp";
    private static final String UTF_8 = "UTF-8";

    private ChatMessageDao messageDao;
    private UserDao3 userDao;
    private ChatRoomDao roomDao;

    @Override
    public void init() throws ServletException {
        try {
            messageDao = new ChatMessageDao();
            userDao = new UserDao3();
            roomDao = new ChatRoomDao();
            LOGGER.info("ChatServlet initialized successfully");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize ChatServlet", e);
            throw new ServletException("Initialization failed", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String roomIdStr = req.getParameter("roomId");
        if (roomIdStr == null || roomIdStr.trim().isEmpty()) {
            LOGGER.warning("Missing roomId parameter in GET request");
            resp.sendRedirect(ROOMS_JSP);
            return;
        }

        long roomId;
        try {
            roomId = Long.parseLong(roomIdStr.trim());
            if (roomId <= 0) {
                throw new NumberFormatException("Room ID must be positive");
            }
        } catch (NumberFormatException e) {
            LOGGER.warning("Invalid roomId parameter: " + roomIdStr);
            resp.sendRedirect(ROOMS_JSP);
            return;
        }
        
        try {
            // Verify user session
            HttpSession session = req.getSession(false);
            User currentUser = (User) (session != null ? session.getAttribute("user") : null);
            if (currentUser == null) {
                LOGGER.warning("Unauthorized access attempt to chat room: " + roomId);
                resp.sendRedirect("login.jsp");
                return;
            }

            // Get chat room information
            ChatRoom room = roomDao.findById(roomId);
            if (room == null) {
                LOGGER.warning("Chat room not found: " + roomId);
                req.setAttribute("error", "指定されたチャットルームが見つかりません。");
                req.getRequestDispatcher(ROOMS_JSP).forward(req, resp);
                return;
            }
           
            // Get messages and set display names
            List<ChatMessage> messages = messageDao.findByRoomId(roomId);
            enrichMessagesWithUserData(messages);

            // Pass data to JSP
            req.setAttribute("roomId", roomId);
            req.setAttribute("room", room);
            req.setAttribute("messages", messages);
            req.setAttribute("currentUser", currentUser);
            req.getRequestDispatcher(CHAT_JSP).forward(req, resp);
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing GET request for room: " + roomId, e);
            req.setAttribute("error", "チャットルームの読み込み中にエラーが発生しました。");
            req.getRequestDispatcher(ROOMS_JSP).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        req.setCharacterEncoding(UTF_8);

        String content = req.getParameter("content");
        String roomIdStr = req.getParameter("roomId");

        // Validate session and user
        HttpSession session = req.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);
        
        if (user == null) {
            LOGGER.warning("Unauthorized message post attempt");
            resp.sendRedirect("login.jsp");
            return;
        }

        // Validate input parameters
        if (content == null || content.trim().isEmpty()) {
            LOGGER.warning("Empty message content from user: " + user.getId());
            redirectWithError(req, resp, roomIdStr, "メッセージが空です。");
            return;
        }

        if (roomIdStr == null || roomIdStr.trim().isEmpty()) {
            LOGGER.warning("Missing roomId in POST request from user: " + user.getId());
            resp.sendRedirect(ROOMS_JSP);
            return;
        }

        long roomId;
        try {
            roomId = Long.parseLong(roomIdStr.trim());
            if (roomId <= 0) {
                throw new NumberFormatException("Room ID must be positive");
            }
        } catch (NumberFormatException e) {
            LOGGER.warning("Invalid roomId in POST request: " + roomIdStr);
            resp.sendRedirect(ROOMS_JSP);
            return;
        }

        // Validate room exists
        try {
            ChatRoom room = roomDao.findById(roomId);
            if (room == null) {
                LOGGER.warning("Attempt to post to non-existent room: " + roomId);
                redirectWithError(req, resp, roomIdStr, "指定されたチャットルームが見つかりません。");
                return;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error validating room existence: " + roomId, e);
            redirectWithError(req, resp, roomIdStr, "チャットルームの確認中にエラーが発生しました。");
            return;
        }

        // Trim and validate content length
        String trimmedContent = content.trim();
        if (trimmedContent.length() > 1000) { // Example limit
            LOGGER.warning("Message too long from user: " + user.getId());
            redirectWithError(req, resp, roomIdStr, "メッセージが長すぎます（最大1000文字）。");
            return;
        }

        // Create and save message
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRoomId(roomId);
        chatMessage.setUserId(user.getId());
        chatMessage.setContent(trimmedContent);
        chatMessage.setTimestamp(LocalDateTime.now());

        try {
            messageDao.save(chatMessage);
            LOGGER.info("Message saved successfully from user: " + user.getId() + " in room: " + roomId);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to save message from user: " + user.getId(), e);
            redirectWithError(req, resp, roomIdStr, "メッセージの保存中にエラーが発生しました。");
            return;
        }

        // Redirect to chat room on success
        resp.sendRedirect("chat?roomId=" + roomId);
    }

    /**
     * Enriches messages with user display names and formatted dates
     */
    private void enrichMessagesWithUserData(List<ChatMessage> messages) {
        for (ChatMessage msg : messages) {
            try {
                User user = userDao.getUserById(msg.getUserId());
                if (user != null) {
                    msg.setDisplayName(user.getDisplayName());
                } else {
                    msg.setDisplayName("Unknown User");
                    LOGGER.warning("User not found for message: " + msg.getUserId());
                }
                
                // LocalDateTimeを文字列に変換してJSPで使いやすくする
                LocalDateTime timestamp = msg.getTimestamp();
                if (timestamp != null) {
                    // Option 1: 文字列形式で時刻を設定
                    java.time.format.DateTimeFormatter formatter = 
                        java.time.format.DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    msg.setFormattedDateString(timestamp.format(formatter));
                    
                    // Option 2: java.util.Dateも必要な場合
                    try {
                        Date utilDate = Date.from(timestamp.atZone(ZoneId.systemDefault()).toInstant());
                        msg.setFormattedDate(utilDate);
                    } catch (Exception dateConversionError) {
                        LOGGER.log(Level.WARNING, "Failed to convert LocalDateTime to Date", dateConversionError);
                        // フォールバック: 現在時刻を使用
                        msg.setFormattedDate(new Date());
                    }
                }
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Error enriching message data for message ID: " + msg.getId(), e);
                msg.setDisplayName("Unknown User");
                // エラー時のフォールバック
                if (msg.getFormattedDate() == null) {
                    msg.setFormattedDate(new Date());
                }
            }
        }
    }

    /**
     * Helper method to redirect with error message
     */
    private void redirectWithError(HttpServletRequest req, HttpServletResponse resp, 
                                 String roomIdStr, String errorMessage) 
            throws ServletException, IOException {
        
        if (roomIdStr != null && !roomIdStr.trim().isEmpty()) {
            try {
                long roomId = Long.parseLong(roomIdStr.trim());
                req.setAttribute("error", errorMessage);
                req.setAttribute("roomId", roomId);
                req.getRequestDispatcher(CHAT_JSP).forward(req, resp);
                return;
            } catch (NumberFormatException e) {
                // Fall through to redirect to rooms
            }
        }
        
        req.setAttribute("error", errorMessage);
        req.getRequestDispatcher(ROOMS_JSP).forward(req, resp);
    }

    @Override
    public void destroy() {
        LOGGER.info("ChatServlet destroyed");
        super.destroy();
    }
}