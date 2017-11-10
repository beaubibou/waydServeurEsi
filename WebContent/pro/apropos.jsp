<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="website.enumeration.MenuEnum"%>
<%@page import="website.metier.AuthentificationSite"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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

<link href="/wayd/css/styleWayd.css" rel="stylesheet" type="text/css">
<link href="/wayd/css/nbrcaractere.css" rel="stylesheet" media="all" type="text/css"> 


</head>
<body style="background-color: #FFD54F;">
	<div id="services" class="text-center">
		<%
		AuthentificationSite authentification=	new AuthentificationSite(request, response);
		if (!authentification.isAuthentifiePro())
		return;
		
		MenuEnum etatMenu = MenuEnum.apropos;
		%>
		<%@ include file="menu.jsp"%>
		</br>
		<div class="container" style="background-color: #FFD54F;">
			<div class="col-md-10 col-md-offset-1 section-title">
				</br>
				<h1>Besoin de visibilité ?</h1>
				<hr>
				<p>Wayd vous offre un moyen gratuit, simple et efficace pour
					valoriser votre activité.</p>
			</div>
			<div class="row">
				<div class="col-xs-6 col-md-3">
					<i class="fa fa-home"></i>
					<h4>Gratuité</h4>
					<p>Pour valoriser votre activité, vous organisez des activités
						gratuites ? Wayd vous permet, gratuitement, de communiquer dessus.</p>
				</div>
				<div class="col-xs-6 col-md-3">
					<i class="fa fa-umbrella"></i>
					<h4>Simplicité</h4>
					<p>L'interface pro de Wayd vous permet de publier simplement
						eten moins de 2 minutes vos activités afin d'offrir du contenu aux
						membres de Wayd.</p>
				</div>
				<div class="col-xs-6 col-md-3">
					<i class="fa fa-gears"></i>
					<h4>Efficacité</h4>
					<p>Un concept efficace qui permet d'offrir aux Waydeurs à
						proximité, et qui cherche quelque-chose à faire, une vision de ce
						que proposez.</p>
				</div>
				<div class="col-xs-6 col-md-3">
					<i class="fa fa-language"></i>
					<h4>Evolutivité</h4>
					<p>Une plateforme qui évoluera en fonction des besoins des
						Waydeurs et de vos besoins de communications.</p>
				</div>
			</div>
		</div>

	</div>


</body>
</html>