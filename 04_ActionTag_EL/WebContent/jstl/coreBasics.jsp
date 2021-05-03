<%@page import="java.util.Arrays"%>
<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="no1" value="${param.num1}" scope="page" />
<c:set var="no2" value="${param.num2}" scope="page" />
<%--<%
Object no2 = 200;
pageContext.setAttribute("no2", no2);
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Core Basics</title>
</head>
<body>
	<h1>JSTL</h1>
	<p>Jsp Standard Tag Library</p>
	<h1>Core Basics</h1>
	<p>
		<c:out value="${no1}" />
	</p>
	<p>
		<c:out value="${no2}" />
	</p>
	<p>
		<c:out value="${no1 + no2}" />
		<!-- 산술연산에서 null을 0으로 치환 -->
	</p>

	<h2>조건식</h2>
	<%-- <c:if test="${Integer.parseInt(no1)> Integer.parseInt(no2)}">
		${no1}>${no2}
	</c:if>
	<c:if test="${Integer.parseInt(no1)< Integer.parseInt(no2)}">
		${no1}<${no2}
	</c:if>
	<c:if test="${no1 eq no2}">
		${no1} eq ${no2}
	</c:if>
	--%>
	<c:set var="rnd" value="<%=new Random().nextInt(100)%>" />
	<p>
		<c:choose>
			<c:when test="${rnd%5==0}">인형을 뽑았습니다.</c:when>
			<c:when test="${rnd%5 == 1}">권총을 뽑았습니다.</c:when>
			<c:otherwise>꽝입니다. 다음기회에..</c:otherwise>
		</c:choose>
	</p>
	<script>
	console.log(${rnd});
	</script>

	<h2>반복문</h2>
	<c:forEach var="i" begin="1" end="6" step="1">
		<h${i}>Hello World${i}</h${i}>
	</c:forEach>
	<c:forEach var="i" begin="1" end="6" step="1">
		<h${7-i}>Hello World${7-i}</h${7-i}>
	</c:forEach>

	<c:set var="list" value='<%=Arrays.asList("홍길동", "신사임당", "이순신")%>' />
	<c:forEach items="${list}" var="name" varStatus="vs">
		<p>${vs.index}${vs.count}${name}</p>
	</c:forEach>


	<table>
		<tr>
			<th>No</th>
			<th>아이디</th>
			<th>이름</th>
			<th>성별</th>
			<th>나이</th>
			<th>결혼여부</th>
		</tr>
		<c:forEach items="${personList}" var="person" varStatus="vs">
			<tr>
				<td>${vs.count}</td>
				<td>${person.id}</td>
				<td>${person.name}</td>
				<td>${person.gender}</td>
				<td>${person.age}</td>
				<td><input type="checkbox" ${person.married ? 'checked' : ''}
					onclick="return false;" /></td>
			</tr>
		</c:forEach>
	</table>
	<table>
		<c:forEach items="${map}" var="item">
			<tr>
				<th>${item.key}</th>
				<td>${item.value}</td>
			</tr>

		</c:forEach>
	</table>
	<p>
	<c:forEach items="${list}" var="name" varStatus="vs">
		<c:choose>
			<c:when test="${vs.index<2}">${name},</c:when>
			<c:otherwise>${name}</c:otherwise>
		</c:choose>
	</c:forEach>
	</p>
	<p>
	<c:forEach items="${list}" var="name" varStatus="vs">
	${name}${vs.count!=list.size()?"," :""}
	</c:forEach>
	</p>
	<p>
	<c:forEach items="${list}" var="name" varStatus="vs">
		<c:choose>
			<c:when test="${vs.last eq false}">${name},</c:when>
			<c:otherwise>${name}</c:otherwise>
		</c:choose>
	</c:forEach>
	</p>
	
	<h2>url</h2>
	<img src="${pageContext.request.contextPath}/images/6648026.jpeg" alt="이미지" />
	<img src='<c:url value="/images/6648026.jpeg"/>' alt="" />
</body>
</html>