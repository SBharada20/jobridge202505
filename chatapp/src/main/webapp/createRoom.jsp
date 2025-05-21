<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>チャットルーム作成</title>
</head>
<body>
    <h2>新しいチャットルームを作成</h2>

    <form method="post" action="createRoom.jsp">
        <label>ルーム名:</label><br/>
        <input type="text" name="name" required /><br/><br/>
        <input type="submit" value="作成" />
    </form>

    <p><a href="rooms.jsp">← ルーム一覧へ戻る</a></p>
</body>
</html>
