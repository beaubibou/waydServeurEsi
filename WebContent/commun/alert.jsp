<%@page import="website.html.AlertInfoJsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>
<%@page import="website.metier.AuthentificationSite"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link href="/wayd/css/style.css" rel="stylesheet" type="text/css">

<title>Message information</title>
</head>
<body>

	
	
	<%
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		if (!authentification.isAuthentifie())
			return;
		MenuEnum etatMenu=null;
	%>

	<%
		if (authentification.isPro()) {
	%>

	<%@ include file="/pro/menu.jsp"%>
	<%
		} else {
	%>

	<%@ include file="/waydeur/menuWaydeur.jsp"%>
	<%
		}
	%>

</br>
</br>
</br>
</br>
</br>

	<%
	
		AlertInfoJsp alerte = (AlertInfoJsp) request.getAttribute("alerte");
	%>


	<%=alerte.getHtml()%>


	<script type="text/javascript">
		$(document).ready(function() {
			$("#myAlert").bind('closed.bs.alert', function() {
	<%=alerte.getRedirectionHtml()%>
		});
		});
	</script>
</body>
</html>