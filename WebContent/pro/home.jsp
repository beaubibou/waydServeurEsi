<%@page import="texthtml.pro.AccueilProText"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="website.enumeration.MenuEnum"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title><%=AccueilProText.TITRE_ONGLET%></title>
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

<link href="/wayd/css/styleWayd.css" rel="stylesheet" type="text/css" />

<link href="/wayd/css/nbrcaractere.css" rel="stylesheet" media="all"
	type="text/css" />

<style>
body,html {
	height: 100%;
	margin: 0;
}

.hero-image {
	background-image: url("/wayd/img/waydfond.jpg");
	height: 100%;
	background-position: center;
	background-repeat: no-repeat;
	background-size: cover;
	position: relative;
}

.hero-text {
	text-align: center;
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	color: white;
}

.hero-text button {
	border: none;
	outline: 0;
	display: inline-block;
	padding: 10px 25px;
	color: black;
	background-color: #ddd;
	text-align: center;
	cursor: pointer;
}

.hero-text button:hover {
	background-color: #555;
	color: white;
}

#jumbo {
	/* IE8 and below */
	background: rgb(255, 173, 64);
	/* all other browsers */
	background: rgba(255, 173, 64, 0.5);
}
</style>

</head>
<body>

	<%
		MenuEnum etatMenu = MenuEnum.home;
	%>

	<div class="hero-image">
		<%@ include file="menu.jsp"%>
	 <a href="/wayd/MesActivites">
		<div class="hero-text">
			<div class="container">
				<div class="jumbotron" id="jumbo">
					<img src="/wayd/img/waydLogoHD.png" alt="Cinque Terre"
						style="width: 30%">
					<h1>Wayd professionel</h1>
					<p>Diffuser, communiquer</p>
				</div>
			
			</div>
		</div>
		</a>
	</div>

</body>
</html>