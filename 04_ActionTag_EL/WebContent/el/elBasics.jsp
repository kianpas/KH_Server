<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
	String life = "life is short";
	pageContext.setAttribute("life", life);
		
	%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>El Basics</title>
</head>
<body>
	<h1>El Basics</h1>
	<%--앞의 스코프 생략시, pageScope - requestScope - sessionScope - applicationScope --%>
	<p>${life}</p>
	<p>${requestScope.coffee}</p>
	<p>${requestScope.serverTime}</p>
	<p>${requestScope.honggd}</p>
	<p>${requestScope.honggd.id}</p>
	<p>${requestScope.honggd.name}</p>
	<p>${requestScope.honggd.gender}</p>
	<p>${requestScope.honggd.age}</p>
	<p>${requestScope.honggd.married}</p>
	<p>${book}</p>
	<p>${movie}</p>
	<p>${sessionScope.book}</p>
	<p>${applicationScope.movie}</p>
	<%--el은 값이 없는 경우에 null값 출력되지 않는다, 공백처리 - NullPointerException을 유발하지 않는다. --%>
	<p>${cow}</p>
	
	<h2>list</h2>
	<p>${list}</p>
	<p>${list[0]}</p>
	<p>${list[1]}</p>
	<p>${list[2]}</p>
	<p>${list[3]}</p>
	
	<h2>map</h2>
	<p>${map}</p>
	<p>${map.language}</p>
	<p>${map.Dr.zang}</p>
	<p>${map['Dr.zang']}</p>
	<p>${map['Dr.zang'].name}</p>
	<p>${map['Dr.zang']["name"]}</p>
	
	<h1>Param</h1>
	<p>${param.pname}</p>
	<p>${param.pcount}</p>
	<p>${paramValues.option[0]}</p>
	<p>${paramValues.option[1]}</p>
	
	<h1>cookie</h1>
	<p>${cookie.JSESSIONID}</p>
	<p>${cookie.JSESSIONID.value}</p>
	
	<h1>header</h1>
	<p>${header.Accept}</p>
	<p>${header['user-agent']}</p>
	
	<h1>pageContext</h1>
	<!-- 
		getPage()
		getRequest()
			getMethod():GET|POST
		getResponse()
		getSession()
		getServletContext()
		getErrorData()
	 -->
	<p>${pageContext.request.method}</p>
	<p>${pageContext.request.contextPath}</p>
	<p>${pageContext.request.session}</p>
</body>


</html>