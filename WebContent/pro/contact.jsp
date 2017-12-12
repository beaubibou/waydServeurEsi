<%@page import="texthtml.pro.ContactProText"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="website.html.*"%>
<%@page import="website.metier.AuthentificationSite"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

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
<link href="/wayd/css/nbrcaractere.css" rel="stylesheet" media="all"
	type="text/css">

</head>
<body>
	<!-- Contact Section -->

	<div id="contact" class="text-center">

		<%
			AuthentificationSite authentification = new AuthentificationSite(
					request, response);
			if (!authentification.isAuthentifiePro())
				return;

			MenuEnum etatMenu = MenuEnum.contact;
		%>
		<%@ include file="menu.jsp"%>

		<div class="container" style="margin-top: 100px;">
			<div class="section-title center">

				<h2><%=ContactProText.TITRE_JUMBO%></h2>
				<hr>
				<h4><%=ContactProText.MESSAGE_JUMBO_LIGNE1%></h4>
			</div>
			<br>
			<br>
			<div class="col-md-8 col-md-offset-2">
			<br>
			<br>
				<form action="/wayd/ContactMessage" method="post">
					<div class="row"></div>
					<div class="form-group">
						<textarea name="message" id="message" class="form-control"
							rows="7"
							placeholder="<%=ContactProText.getHintDescriptionMessage()%>"
							maxlength="<%=ContactProText.TAILLE_MAX_MESSAGE%>" required></textarea>
						<h5 class="nbrcaracteremax" id="nbr"><%=ContactProText.initNbrCaracteres()%></h5>
					</div>

					<div id="success"></div>
					<button type="submit" class="btnwayd btn-lg "><%=ContactProText.LABEL_BUTTON_ENVOYER%></button>
				</form>
			</div>

		</div>
	</div>
	
	<script>
		$(document).ready(function(e) {

			$('#message').keyup(function() {

				var nombreCaractere = $(this).val().length;
				//alert(nombreCaractere);

				var msg = nombreCaractere + '<%=ContactProText.getNbrCarateresDescriptionMessage()%>';

				$('#nbr').text(msg);
				// Le script qui devra calculer et afficher le nombre de mots et de caractères

			})

		});

		// Init le nombre de caraterces	
		var nombreCaractere = $('#message').val().length;
		var msg = nombreCaractere +   '<%=ContactProText.getNbrCarateresDescriptionMessage()%>';
		$('#nbr').text(msg);
	</script>
</body>
</html>