<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="website.enumeration.MenuEnum"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Wayd community</title>
<meta name="description" content="">
<meta name="author" content="">

<!-- Favicons
    ================================================== -->
<link rel="shortcut icon" href="/wayd/commun/img/wayd32.png"
	type="image/x-icon">
<link rel="apple-touch-icon" href="/wayd/commun/img/wayd32.png">
<link rel="apple-touch-icon" sizes="32x32"
	href="/wayd/commun/img/wayd32.png">


<!-- Bootstrap -->
<link rel="stylesheet" type="text/css"
	href="/wayd/commun/css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="/wayd/commun/fonts/font-awesome/css/font-awesome.css">

<!-- Stylesheet
    ================================================== -->
<link rel="stylesheet" type="text/css" href="commun/css/style.css">
<link
	href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900"
	rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body style="background-color: #FFD54F;">
	<div id="services" class="text-center">
		<%
			MenuEnum etatMenu = MenuEnum.apropos;
		%>
		<%@ include file="menu.jsp"%>
		</br>
		<div class="container" style="background-color: #FFD54F;">
			<div class="col-md-10 col-md-offset-1 section-title">
				</br>
				<h1>Besoin de visibilit� ?</h1>
				<hr>
				<p>Wayd vous offre un moyen gratuit, simple et efficace pour
					valoriser votre activit�.</p>
			</div>
			<div class="row">
				<div class="col-xs-6 col-md-3">
					<i class="fa fa-home"></i>
					<h4>Gratuit�</h4>
					<p>Pour valoriser votre activit�, vous organisez des activit�s
						gratuites ? Wayd vous permet, gratuitement, de communiquer dessus.</p>
				</div>
				<div class="col-xs-6 col-md-3">
					<i class="fa fa-umbrella"></i>
					<h4>Simplicit�</h4>
					<p>L'interface pro de Wayd vous permet de publier simplement
						eten moins de 2 minutes vos activit�s afin d'offrir du contenu aux
						membres de Wayd.</p>
				</div>
				<div class="col-xs-6 col-md-3">
					<i class="fa fa-gears"></i>
					<h4>Efficacit�</h4>
					<p>Un concept efficace qui permet d'offrir aux Waydeurs �
						proximit�, et qui cherche quelque-chose � faire, une vision de ce
						que proposez.</p>
				</div>
				<div class="col-xs-6 col-md-3">
					<i class="fa fa-language"></i>
					<h4>Evolutivit�</h4>
					<p>Une plateforme qui �voluera en fonction des besoins des
						Waydeurs et de vos besoins de communications.</p>
				</div>
			</div>
		</div>

	</div>


</body>
</html>