<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="website.metier.TypeUser"%>
<%@page import="website.metier.ProfilBean"%>

<%@page import="java.util.ArrayList"%>

<%@page import="website.dao.CacheValueDAO"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Renseignements</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/prettify/r298/run_prettify.min.js"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.34.9/css/bootstrap-dialog.min.css"
	rel="stylesheet" type="text/css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.34.9/js/bootstrap-dialog.min.js"></script>
<link href="/wayd/css/style.css" rel="stylesheet" type="text/css">

<script>
	var latitude = 0;
	var longitude = 0;
</script>

</head>
<body>

	<%
	ProfilBean profil = (ProfilBean) session.getAttribute("profil");
	if (profil==null)
	 response.sendRedirect(	"commun/acceuil.html");
	%>

	<div class="container">
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">
					<div class="panel-title">Inscription</div>

				</div>

				<div style="padding-top: 30px" class="panel-body">

					<h2>Vous �tes?</h2>
					<a href="inscriptionPro.jsp" type="button" class="btn btn-success"
						role="button">Professionel</a> <br> <br> 
						
						<a
						href="inscriptionWaydeur.jsp" type="button"
						class="btn btn-success" role="button">Particulier</a>




				</div>
			</div>
		</div>
</body>
</html>