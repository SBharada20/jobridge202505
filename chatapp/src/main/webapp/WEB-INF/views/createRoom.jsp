<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>チャットルーム作成</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
    <div class="content-wrapper">
        <!-- 共通ヘッダーロゴをinclude -->
        <jsp:include page="/WEB-INF/views/header-logo.jsp" />
        <h3>新しいチャットルームを作成</h3>
        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
        <% if (errorMessage != null) { %>
            <div class="error-message">
                <%= errorMessage %>
            </div>
        <% } %>
        <div class="form-container">
            <form method="post" action="createRoom">
                <label>ルーム名:</label><br/>
                <input type="text" name="name" 
                       value="<%= request.getAttribute("inputName") != null ? request.getAttribute("inputName") : "" %>" 
                       required /><br/><br/>
                <button type="submit" class="btn btn-primary">作成</button>
            </form>
        </div>
        <p style="margin-top: 20px;"><a href="rooms">← ルーム一覧へ戻る</a></p>
    </div>
</body>
</html>
