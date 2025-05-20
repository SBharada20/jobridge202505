<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.example.chatapp.model.ChatRoom" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>チャットルーム一覧</title>
</head>
<body>
    <h2>チャットルーム一覧</h2>

    <ul>
        <%
            List<ChatRoom> rooms = (List<ChatRoom>) request.getAttribute("rooms");
            for (ChatRoom room : rooms) {
        %>
            <li>
                <a href="chat?roomId=<%= room.getId() %>">
                    <%= room.getName() %>
                </a>
            </li>
        <%
            }
        %>
    </ul>

    <p><a href="logout">ログアウト</a></p>
</body>
</html>
