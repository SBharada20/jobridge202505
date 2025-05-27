<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.example.chatapp.model.User" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
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
    <title>チャットルーム - ${room.name}</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <script>
        function confirmDelete(messageId, roomId) {
            if (confirm('このメッセージを削除しますか？')) {
                // 削除リクエストを送信
                var form = document.createElement('form');
                form.method = 'POST';
                form.action = 'chat';
                
                var actionInput = document.createElement('input');
                actionInput.type = 'hidden';
                actionInput.name = 'action';
                actionInput.value = 'delete';
                form.appendChild(actionInput);
                
                var messageIdInput = document.createElement('input');
                messageIdInput.type = 'hidden';
                messageIdInput.name = 'messageId';
                messageIdInput.value = messageId;
                form.appendChild(messageIdInput);
                
                var roomIdInput = document.createElement('input');
                roomIdInput.type = 'hidden';
                roomIdInput.name = 'roomId';
                roomIdInput.value = roomId;
                form.appendChild(roomIdInput);
                
                document.body.appendChild(form);
                form.submit();
            }
        }
    </script>
</head>
<body>
    <div class="content-wrapper">
            <!-- 共通ヘッダーロゴをinclude -->
        <jsp:include page="/WEB-INF/views/header-logo.jsp" />
        <h3>チャットルーム: ${room.name}</h3>
        <div class="info">
            <div style="font-size: 14px; opacity: 0.9;">
                ようこそ、<strong><%= user.getDisplayName() %></strong> さん！
            </div>
        </div>
        
        <!-- エラーメッセージ表示 -->
        <c:if test="${not empty error}">
            <div class="alert-error">
                ${error}
            </div>
        </c:if>
        
        <!-- 成功メッセージ表示 -->
        <c:if test="${not empty success}">
            <div class="alert-success">
                ${success}
            </div>
        </c:if>
        
        <div id="chat-messages">
            <c:forEach var="message" items="${messages}">
                <div class="message ${message.displayName == sessionScope.user.displayName ? 'my-message' : 'other-message'}">
                    <div class="message-header">
                        <div class="message-info">
                            <span class="username">
                                <c:choose>
                                    <c:when test="${message.displayName == sessionScope.user.displayName}">
                                        あなた
                                    </c:when>
                                    <c:otherwise>
                                        ${message.displayName}
                                    </c:otherwise>
                                </c:choose>
                            </span>
                            <span class="timestamp">${message.formattedDateString}</span>
                        </div>
                        <c:if test="${message.userId == sessionScope.user.id}">
                            <button class="delete-btn" onclick="confirmDelete(${message.id}, ${roomId})">
                                削除
                            </button>
                        </c:if>
                    </div>
                    <div class="message-content">
                        <c:out value="${message.content}" />
                    </div>
                </div>
            </c:forEach>
        </div>
        <div style="text-align: right; font-size: 14px;">
            <form class="message-form" action="chat" method="post">
                <input type="hidden" name="roomId" value="${roomId}" />
                <input type="text" name="content" placeholder="メッセージを入力..." required />
                <input type="submit" value="送信" />
            </form>
            <p style="margin-top: 20px;"><a href="rooms" class="nav-link">チャットルーム一覧に戻る</a></p>
        </div>
    </div>
</body>
</html>