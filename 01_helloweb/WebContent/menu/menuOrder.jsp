<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    String main = request.getParameter("main_menu");
    String side = request.getParameter("side_menu");
    String drink = request.getParameter("drink_menu");
    	
    int price =(int)request.getAttribute("price");
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>감사합니다.</h1>
<p><%= main %>. <%= side %> <%= drink %></p>
<p>총 결제 금액은 <%= price %>원 입니다.</p>
</body>
</html>