<%@page import="website.html.AlertInfoJsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
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
	

		AlertInfoJsp alerte = (AlertInfoJsp) request.getAttribute("alerte");
	%>

</br>
</br>
</br>
</br>


<div class="container">
<div class="row">
<div class="col-sm-4 col-sm-offset-4 ">
		<%=alerte.getHtml()%>
	
	</div>
</div>
</div>


	<script type="text/javascript">
		$(document).ready(function() {
			$("#myAlert").bind('closed.bs.alert', function() {
	<%=alerte.getRedirectionHtml()%>
		});
		});
	</script>
</body>
</html>