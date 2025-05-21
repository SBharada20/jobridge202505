<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ログイン</title>
    <style>
        body { font-family: sans-serif; padding: 20px; }
        form { width: 300px; margin: auto; }
        .error { color: red; margin-bottom: 10px; }
    </style>
</head>
<body>
    <h2>ログイン</h2>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <form action="login" method="post">
        <div>
            <label for="username">ユーザー名:</label><br>
            <input type="text" id="username" name="username" required />
        </div>
        <div>
            <label for="password">パスワード:</label><br>
            <input type="password" id="password" name="password" required />
        </div>
        <div style="margin-top: 10px;">
            <button type="submit">ログイン</button>
        </div>
    </form>

    <p><a href="register">新規登録はこちら</a></p>
    
    <p><a href="chat.jsp">chatはこちら</a></p> 
    <p><a href="createRoom.jsp">新規ROOMはこちら</a></p>
</body>
</html>

