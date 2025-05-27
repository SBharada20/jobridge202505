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
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <script>
        function confirmDelete(roomName) {
            return confirm("「" + roomName + "」を削除してもよろしいですか？\nこの操作は取り消せません。");
        }
    </script>
</head>
<body>

    <div class="content-wrapper">
            <!-- 共通ヘッダーロゴをinclude -->
            <jsp:include page="/WEB-INF/views/header-logo.jsp" />
       
        <div class="container">
            <!-- メッセージ表示 -->
            <%
                String successMessage = (String) session.getAttribute("successMessage");
                String errorMessage = (String) session.getAttribute("errorMessage");
                if (successMessage != null) {
                    session.removeAttribute("successMessage");
            %>
                <div class="message success-message">
                    <%= successMessage %>
                </div>
            <%
                }
                if (errorMessage != null) {
                    session.removeAttribute("errorMessage");
            %>
                <div class="message error-message">
                    <%= errorMessage %>
                </div>
            <%
                }
            %>

            <h3>チャットルーム一覧</h3>
              <div class="info">
            <div style="font-size: 14px; opacity: 0.9;">
                ようこそ、<strong><%= user.getDisplayName() %></strong> さん！
            </div>
        </div>
            <ul class="room-list">
                <c:forEach var="room" items="${rooms}">
                    <li class="room-item">
                        <a class="room-link" href="${pageContext.request.contextPath}/chat?roomId=${room.id}">
                            ${room.name}
                        </a>
                        <form method="post" action="rooms" style="display:inline;" 
                              onsubmit="return confirmDelete('${room.name}')">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="roomId" value="${room.id}">
                            <button type="submit" class="delete-btn">削除</button>
                        </form>
                    </li>
                </c:forEach>
            </ul>
            <c:if test="${empty rooms}">
                <p>現在、参加可能なルームはありません。</p>
            </c:if>
     
            <div class="actions">
                <div class="form-group">
                    <a class="btn btn-primary" href="createRoom">新しいルームを作成</a>
                </div>
                <div class="form-group">
                    <form action="logout" method="get" style="display:inline;">
                        <button type="submit" class="btn">ログアウト</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>