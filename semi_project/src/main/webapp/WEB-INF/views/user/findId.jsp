<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<form  action="<%=request.getContextPath() %>/user/loginuser.do" method="post">
	 <input type="text" name="searchemail" placeholder="�̸��� �Է�" required>
	 <input type="text" name="searchname" placeholder="�̸� �Է�" required>
</form>