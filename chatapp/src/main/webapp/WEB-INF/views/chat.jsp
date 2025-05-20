<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.example.chatapp.model.ChatMessage" %>
<%@ page import="com.example.chatapp.model.ChatRoom" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>チャットルーム</title>
</head>
<body>
    <h2>チャットルーム: <%= ((ChatRoom) request.getAttribute("room")).getName() %></h2>

    <div style="border: 1px solid #ccc; padding: 10px; max-height: 300px; overflow-y: scroll;">
        <%
            List<ChatMessage> messages = (List<ChatMessage>) request.getAttribute("messages");
            for (ChatMessage msg : messages) {
        %>
            <p><strong>[User <%= msg.getUserId() %>]</strong> <%= msg.getContent() %></p>
        <%
            }
        %>
    </div>

    <form method="post" action="chat">
        <input type="hidden" name="roomId" value="<%= ((ChatRoom) request.getAttribute("room")).getId() %>" />
        <textarea name="content" rows="3" cols="40" required></textarea><br/>
        <input type="submit" value="送信" />
    </form>

    <p><a href="rooms.jsp">← ルーム一覧へ戻る</a></p>
</body>
</html>
