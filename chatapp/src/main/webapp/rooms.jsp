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
<!--    <style>-->
<!--        body { font-family: Arial, sans-serif; padding: 1em; background: #f9f9f9; }-->
<!--        .container { max-width: 600px; margin: 2em auto; background: #fff; padding: 1.5em; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }-->
<!--        h2 { margin-top: 0; }-->
<!--        ul { list-style: none; padding: 0; }-->
<!--        li { margin: 0.5em 0; }-->
<!--        a.room-link { text-decoration: none; color: #2c3e50; font-size: 1.1em; }-->
<!--        a.room-link:hover { text-decoration: underline; }-->
<!--        .actions { margin-top: 1.5em; }-->
<!--        .actions a, .actions form { display: inline-block; margin-right: 1em; }-->
<!--        .btn { background: #3498db; color: #fff; padding: 0.5em 1em; border: none; border-radius: 4px; cursor: pointer; text-decoration: none; }-->
<!--        .btn:hover { background: #2980b9; }-->
<!--    </style>-->
</head>
<body>
<div class="content-wrapper">
            <div class="page-header">
                <div class="logo">
                    <svg viewBox="0 0 800 200" xmlns="http://www.w3.org/2000/svg">
                      <!-- Background circles -->
                      <circle cx="50" cy="100" r="15" fill="#FF8B20" opacity="0.7"/>
                      <circle cx="750" cy="60" r="12" fill="#333" opacity="0.8"/>
                      <circle cx="720" cy="140" r="8" fill="#FF8B20" opacity="0.5"/>
                      
                      <!-- Main JB icon container -->
                      <g transform="translate(20, 30)">
                        <!-- Outer frame -->
                        <path d="M0 20 L0 0 L40 0 L60 20 L60 80 L80 100 L80 140 L40 140 L20 120 L20 100 L0 80 Z" 
                              fill="none" stroke="#FF8B20" stroke-width="8" stroke-linejoin="round"/>
                        
                        <!-- Inner frame -->
                        <path d="M15 35 L15 20 L35 20 L50 35 L50 75 L65 90 L65 125 L35 125 L20 110 L20 90 L15 85 Z" 
                              fill="#FF8B20" opacity="0.2"/>
                        
                        <!-- J letter -->
                        <path d="M25 40 L25 85 Q25 100 35 100 Q45 100 45 85 L45 75" 
                              fill="none" stroke="white" stroke-width="6" stroke-linecap="round"/>
                        
                        <!-- B letter -->
                        <path d="M55 40 L55 110 M55 40 L70 40 Q75 40 75 50 Q75 60 70 60 L55 60 M55 60 L72 60 Q77 60 77 70 Q77 80 72 80 L55 80" 
                              fill="none" stroke="white" stroke-width="5" stroke-linecap="round"/>
                        
                        <!-- Decorative arc -->
                        <path d="M10 110 Q40 130 70 110" fill="none" stroke="#B8860B" stroke-width="6" opacity="0.8"/>
                      </g>
                      
                      <!-- jobridge text -->
                      <g transform="translate(140, 100)" fill="#333" font-family="Arial, sans-serif" font-weight="bold">
                        <!-- jo -->
                        <text x="0" y="20" font-size="48" fill="#333">jo</text>
                        <!-- bridge with stylized 'r' -->
                        <text x="60" y="20" font-size="48" fill="#FF8B20">b</text>
                        <text x="95" y="20" font-size="48" fill="#FF8B20">r</text>
                        <text x="125" y="20" font-size="48" fill="#333">idge</text>
                      </g>
                      
                      <!-- chat text -->
                      <g transform="translate(400, 100)" fill="#333" font-family="Arial, sans-serif" font-weight="bold">
                        <!-- ch -->
                        <text x="0" y="20" font-size="48" fill="#333">ch</text>
                        <!-- at with stylized 'a' -->
                        <text x="80" y="20" font-size="48" fill="#FF8B20">a</text>
                        <text x="115" y="20" font-size="48" fill="#333">t</text>
                      </g>
                      
                      <!-- Chat bubble decorations -->
                      <g transform="translate(580, 40)">
                        <!-- Large bubble -->
                        <ellipse cx="30" cy="30" rx="35" ry="25" fill="#FF8B20" opacity="0.3"/>
                        <ellipse cx="30" cy="30" rx="35" ry="25" fill="none" stroke="#FF8B20" stroke-width="3"/>
                        
                        <!-- Small bubble dots -->
                        <circle cx="10" cy="50" r="4" fill="#FF8B20"/>
                        <circle cx="20" cy="55" r="3" fill="#FF8B20" opacity="0.7"/>
                        <circle cx="5" cy="60" r="2" fill="#FF8B20" opacity="0.5"/>
                      </g>
                      
                      <!-- Connection lines -->
                      <g stroke="#FF8B20" stroke-width="2" opacity="0.4">
                        <path d="M120 80 Q200 60 280 80" fill="none"/>
                        <path d="M380 120 Q450 140 520 120" fill="none"/>
                      </g>
                      
                      <!-- Additional decorative elements -->
                      <g transform="translate(680, 100)">
                        <!-- Network nodes -->
                        <circle cx="0" cy="0" r="3" fill="#FF8B20"/>
                        <circle cx="15" cy="-10" r="3" fill="#333"/>
                        <circle cx="15" cy="10" r="3" fill="#FF8B20" opacity="0.7"/>
                        
                        <!-- Connection lines -->
                        <line x1="0" y1="0" x2="15" y2="-10" stroke="#FF8B20" stroke-width="1" opacity="0.6"/>
                        <line x1="0" y1="0" x2="15" y2="10" stroke="#FF8B20" stroke-width="1" opacity="0.6"/>
                        <line x1="15" y1="-10" x2="15" y2="10" stroke="#333" stroke-width="1" opacity="0.4"/>
                      </g>
                    </svg>
                </div>
    <div class="container">
        <h2 class="page-title">ようこそ、<%= user.getDisplayName() %> さん</h2>

        <h3>チャットルーム一覧</h3>
        <ul class="room-list">
            <c:forEach var="room" items="${rooms}">
                <li>
<!--                    <a class="room-link" href="chat?roomId=${room.id}">${room.name}</a>-->
 						<a class="room-link" href="${pageContext.request.contextPath}/chat?roomId=${room.id}">
                ${room.name}
            </a>
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
</body>
</html>
