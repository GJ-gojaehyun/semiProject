<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="com.web.user.model.dto.User" %>
<%
	String msg = (String)request.getAttribute("msg");
	String loc = (String)request.getAttribute("loc");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>��å�ϰ� �޼���</title>
</head>
<body>
	<script>
		alert('<%=msg%>');
		location.assign('<%=request.getContextPath() %><%=loc%>');
	</script>
</body>
</html>