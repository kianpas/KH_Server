<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/header.jsp"%>
<h1>Content</h1>
<p><%=name%>님 반갑습니다
</p>
<a href="/web/jsp/another.jsp">another.jsp</a>
<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Iste
	esse architecto incidunt voluptas deserunt voluptatibus doloremque modi
	quae recusandae voluptatum in voluptates voluptatem rem facilis
	voluptate perferendis culpa mollitia consequuntur.</p>
<script>
	$(function() {
		$("h1").css("color", "deeppink");
	});
</script>
<%@ include file="/jsp/footer.jsp"%>