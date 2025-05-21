<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.ChatMessage, model.ChatRoom, model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    // ログインチェック
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login");
        return;
    }
    ChatRoom room = (ChatRoom) request.getAttribute("room");
    List<ChatMessage> messages = (List<ChatMessage>) request.getAttribute("messages");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>チャットルーム：<%= room.getName() %></title>
    <style>
        .chat-container { width: 600px; margin: 2em auto; }
        .messages { border: 1px solid #ccc; padding: 1em; height: 300px; overflow-y: scroll; }
        .message { margin-bottom: 0.5em; }
        .message .author { font-weight: bold; }
        .message .time { font-size: 0.8em; color: #666; margin-left: 0.5em; }
        form textarea { width: 100%; height: 60px; }
        form button { margin-top: 0.5em; }
    </style>
</head>
<body>
<div class="chat-container">
    <h2>ルーム: <%= room.getName() %></h2>
    <div class="messages">
        <c:forEach var="msg" items="${messages}">
            <div class="message">
                <span class="author">${msg.displayName}</span>
                <span class="time">[<fmt:formatDate value="${msg.timestamp}" pattern="HH:mm:ss"/>]</span><br/>
                <span class="content">${msg.content}</span>
            </div>
        </c:forEach>
        <c:if test="${empty messages}">
            <p>まだメッセージがありません。</p>
        </c:if>
    </div>

    <form action="chat" method="post">
        <input type="hidden" name="roomId" value="${room.id}" />
        <textarea name="content" placeholder="メッセージを入力..." required></textarea><br/>
        <button type="submit">送信</button>
    </form>

    <p><a href="rooms">← ルーム一覧へ戻る</a></p>
    <p><a href="logout">ログアウト</a></p>
</div>
</body>
</html>

