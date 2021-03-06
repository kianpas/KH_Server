<%@page import="member.model.service.MemberService"%>
<%@page import="member.model.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
//String msg = (String)request.getAttribute("msg");    
String msg = (String) session.getAttribute("msg");

//메시지를 세션처리하면 계속 남아있음 그러므로 1회용처리 해줘야함
if (msg != null)
	session.removeAttribute("msg");
String loc = (String) request.getAttribute("loc");
Member loginMember = (Member) session.getAttribute("loginMember");

//사용자 쿠키처리
String saveId = null;
Cookie[] cookies = request.getCookies();
if (cookies != null) {
	for (Cookie c : cookies) {
		String name = c.getName();
		String value = c.getValue();
		//System.out.println(name + " : " + value); //JSESSIONID : 71206E4ECB96F29207D9B0C76BA1B709
		if ("saveId".equals(name))
	saveId = value;
	}
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hello MVC</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css" />
<script src="<%=request.getContextPath()%>/js/jquery-3.6.0.js"></script>
<script>
/**
 * 문자열이므로 쌍따옴표로 감싸줘야 한다
 */
 <%if (msg != null) {%>
alert("<%=msg%>");
<%}%>

/*
 * 오류시 인덱스 페이지로 돌아감
 
 <%if (loc != null) {%>
 	location.href = "<%=loc%>
	";
<%}%>
*/
	/**
	 * 로그인 폼 유효성 검사
	 */
	$(function() {
		$("#loginFrm").submit(function() {
			var $memberId = $(memberId);
			var $password = $(password);
			if (/^.{4,}/.test($memberId.val()) == false) {
				alert("유효한 아이디를 입력하세요.");
				$memberId.select();
				return false;
			}

			if (/^.{4,}/.test($password.val()) == false) {
				alert("유효한 비밀번호를 입력하세요.");
				$password.select();
				return false;
			}
		});
	});
</script>
</head>
<body>
	<div id="container">

		<header>
			<h1>Hello MVC</h1>
			<!-- 로그인폼 시작 -->
			<div class="login-container">
				<%
				if (loginMember == null) {
				%>
				<form id="loginFrm"
					action="<%=request.getContextPath()%>/member/login" method="post">
					<table>
						<tr>
							<td><input type="text" name="memberId" id="memberId"
								placeholder="아이디" tabindex="1"
								value="<%=saveId != null ? saveId : ""%>"></td>
							<td><input type="submit" value="로그인" tabindex="3"></td>
						</tr>
						<tr>
							<td><input type="password" name="password" id="password"
								placeholder="비밀번호" tabindex="2"></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="2"><input type="checkbox" name="saveId"
								id="saveId" value="<%=saveId != null ? "checked" : ""%>" /> <label
								for="saveId">아이디저장</label>&nbsp;&nbsp; <input type="button"
								value="회원가입"
								onclick="location.href='<%=request.getContextPath()%>/member/memberEnroll';">
							</td>
						</tr>
					</table>
				</form>
				<!-- 로그인폼 끝-->
				<%
				} else {
				%>
				<%-- 로그인 성공시 --%>
				<table id="loggin">
					<tr>
						<td><%=loginMember.getMemberName()%> 님, 안녕하세요.</td>
					</tr>
					<tr>
						<td><input type="button" value="내정보보기"
							onclick="location.href='<%=request.getContextPath()%>/member/memberView';" />
							<input type="button" value="로그아웃"
							onclick="location.href='<%=request.getContextPath()%>/member/logout';" />
						</td>
					</tr>
				</table>
				<%
				}
				%>
			</div>
			<!-- 메인메뉴 시작 -->
			<nav>
				<ul class="main-nav">
					<li class="home"><a href="<%=request.getContextPath()%>">Home</a></li>
					<li class="notice"><a href="#">공지사항</a></li>
					<li class="board"><a href="<%=request.getContextPath()%>/board/boardList">게시판</a></li>
					<%-- 관리자 계정시 보여주기, 로그인전에 바로 검사하면 nullpoint에러남 --%>
					<%
					if (loginMember != null && MemberService.ADMIN_ROLE.equals(loginMember.getMemberRole())) {
					%>
					<li class="members"><a
						href="<%=request.getContextPath()%>/admin/memberList">회원 관리</a></li>
					<%
					}
					%>
				</ul>
			</nav>
			<!-- 메인메뉴 끝-->
		</header>

		<section id="content">