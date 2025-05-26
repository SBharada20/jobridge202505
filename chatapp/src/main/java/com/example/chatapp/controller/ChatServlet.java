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
import com.example.chatapp.dao.UserDao;
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
    private UserDao userDao;
    private ChatRoomDao roomDao;

    @Override
    public void init() throws ServletException {
        try {
            messageDao = new ChatMessageDao();
            userDao = new UserDao();
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
   
            // セッションから成功メッセージを取得して表示
            HttpSession session1 = req.getSession(false);
            if (session1 != null) {
                String success = (String) session1.getAttribute("success");
                if (success != null) {
                    req.setAttribute("success", success);
                    session1.removeAttribute("success"); // 一度表示したら削除
                }
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
        String action = req.getParameter("action");
        
        // 削除アクションの処理
        if ("delete".equals(action)) {
            handleDeleteMessage(req, resp);
            return;
        }

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
     * メッセージ削除処理
     */
    private void handleDeleteMessage(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String messageIdStr = req.getParameter("messageId");
        String roomIdStr = req.getParameter("roomId");
        
        // セッションとユーザーの検証
        HttpSession session = req.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);
        
        if (user == null) {
            LOGGER.warning("Unauthorized delete attempt");
            resp.sendRedirect("login.jsp");
            return;
        }
        
        // パラメータの検証
        if (messageIdStr == null || messageIdStr.trim().isEmpty() ||
            roomIdStr == null || roomIdStr.trim().isEmpty()) {
            LOGGER.warning("Missing parameters for delete operation");
            redirectWithError(req, resp, roomIdStr, "削除に必要な情報が不足しています。");
            return;
        }
        
        long messageId;
        long roomId;
        try {
            messageId = Long.parseLong(messageIdStr.trim());
            roomId = Long.parseLong(roomIdStr.trim());
            if (messageId <= 0 || roomId <= 0) {
                throw new NumberFormatException("IDs must be positive");
            }
        } catch (NumberFormatException e) {
            LOGGER.warning("Invalid ID parameters for delete: messageId=" + messageIdStr + ", roomId=" + roomIdStr);
            redirectWithError(req, resp, roomIdStr, "無効なパラメータです。");
            return;
        }
        
        // メッセージ削除実行
        try {
            boolean deleted = messageDao.deleteMessage(messageId, user.getId());
            
            if (deleted) {
                LOGGER.info("Message deleted successfully by user: " + user.getId() + ", messageId: " + messageId);
                // 成功時はチャットルームにリダイレクト（成功メッセージ付き）
                req.getSession().setAttribute("success", "メッセージが削除されました。");
                resp.sendRedirect("chat?roomId=" + roomId);
            } else {
                LOGGER.warning("Failed to delete message - not found or not owned: messageId=" + messageId + ", userId=" + user.getId());
                redirectWithError(req, resp, roomIdStr, "メッセージの削除に失敗しました。自分のメッセージのみ削除できます。");
            }
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during message deletion", e);
            redirectWithError(req, resp, roomIdStr, "メッセージの削除中にエラーが発生しました。");
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