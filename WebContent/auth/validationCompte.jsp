<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
String mode=(String)request.getParameter("mode") ;
String oobCode=(String)request.getParameter("oobCode") ;
String apiKey=(String)request.getParameter("apiKey") ;

%>


<!-- https://wayd-c0414.firebaseapp.com/__/auth/action?mode=<action>&oobCode=<code>-->

</body>
<script type="text/javascript">
document.location.href="https://wayd-c0414.firebaseapp.com/__/auth/action?mode=<%=mode%>&oobCode=<%=oobCode%>&apiKey=<%=apiKey%>";
</script>

</html>