<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ログイン</title>
</head>
<body>
    <h2>ログイン</h2>

    <form method="post" action="login">
        <label>ユーザー名：</label><input type="text" name="username" required /><br/>
        <label>パスワード：</label><input type="password" name="password" required /><br/>
        <input type="submit" value="ログイン" />
    </form>

    <div style="color: red;">
        ${error != null ? error : ""}
    </div>

    <p><a href="views/register.jsp">新規登録はこちら</a></p>
    <p><a href="views/chat.jsp">chatはこちら</a></p>
    <p><a href="views/createRoom.jsp">新規ROOMはこちら</a></p>
</body>
</html>
