<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.chatapp.model.User" %>
<%@ page import="com.example.chatapp.model.ChatRoom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    // セッションチェック
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>チャットルーム一覧</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 1em; background: #f9f9f9; }
        .container { max-width: 600px; margin: 2em auto; background: #fff; padding: 1.5em; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        h2 { margin-top: 0; }
        ul { list-style: none; padding: 0; }
        li { margin: 0.5em 0; }
        a.room-link { text-decoration: none; color: #2c3e50; font-size: 1.1em; }
        a.room-link:hover { text-decoration: underline; }
        .actions { margin-top: 1.5em; }
        .actions a, .actions form { display: inline-block; margin-right: 1em; }
        .btn { background: #3498db; color: #fff; padding: 0.5em 1em; border: none; border-radius: 4px; cursor: pointer; text-decoration: none; }
        .btn:hover { background: #2980b9; }
    </style>
</head>
<body>
    <div class="container">
        <h2>ようこそ、<%= user.getDisplayName() %> さん</h2>

        <h3>チャットルーム一覧</h3>
        <ul>
            <c:forEach var="room" items="${rooms}">
                <li>
                    <a class="room-link" href="chat?roomId=${room.id}">${room.name}</a>
                </li>
            </c:forEach>
        </ul>
        <c:if test="${empty rooms}">
            <p>現在、参加可能なルームはありません。</p>
        </c:if>

        <div class="actions">
            <a class="btn" href="createRoom">新しいルームを作成</a>
            <form action="logout" method="get" style="display:inline;">
                <button type="submit" class="btn">ログアウト</button>
            </form>
        </div>
    </div>
</body>
</html>
