<%@page import="texthtml.pro.CreationCompteProText"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="website.metier.TypeUser"%>
<%@page import="website.metier.TypeAccess"%>
<%@page import="website.html.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="website.metier.AuthentificationSite"%>
<%@page import="website.dao.CacheValueDAO"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Renseignements</title>

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
<script>
	var latitude = 0;
	var longitude = 0;
</script>
<script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>

	<%
		MessageAlertDialog alerte = (MessageAlertDialog) request
				.getAttribute("alerte");

		String pseudo = OutilsHtml.convertRequeteToString(request
				.getAttribute("pseudo"));
		String adresse = OutilsHtml.convertRequeteToString(request
				.getAttribute("adresse"));
		String telephone = OutilsHtml.convertRequeteToString(request
				.getAttribute("telephone"));
		String email = OutilsHtml.convertRequeteToString(request
				.getAttribute("email"));
		String pwd1 = OutilsHtml.convertRequeteToString(request
				.getAttribute("pwd1"));
		String pwd = OutilsHtml.convertRequeteToString(request
				.getAttribute("pwd"));
		String siret = OutilsHtml.convertRequeteToString(request
				.getAttribute("siret"));
		String commentaire = OutilsHtml.convertRequeteToString(request
				.getAttribute("commentaire"));
		double latitude = OutilsHtml.convertRequeteToDouble(request
				.getAttribute("latitude"));
		double longitude = OutilsHtml.convertRequeteToDouble(request
				.getAttribute("longitude"));
	%>
	<script type="text/javascript">
	<%=AlertDialog.getAlert(alerte)%>

	</script>


	<div class="container">
		<div class="page-header">

			<h1>
				<img src="/wayd/img/waydLogoHD.png" style="margin-right: 50px;"
					class="img-rounded" alt="Cinque Terre" width="100" height="100"><%=CreationCompteProText.TITRE_JUMBO %>
			</h1>
		</div>
	

	</div>

	<div class="container">
		<div id="loginbox" class="mainbox col-md-8 col-md-offset-2 col-sm-8">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">
					<div class="panel-title"><%=CreationCompteProText.TITRE_PANEL%></div>

				</div>

				<div style="padding-top: 30px" class="panel-body">

					<form action="/wayd/CreerUserPro" method="post"
						onsubmit="return valideFormulaire()">
						<div class="form-group">
							<div class="row">
								<div class='col-md-4 col-md-offset-5'>
									<h4>Authentification</h4>
								</div>
							</div>
							<div style="margin-bottom: 25px" class="input-group">
								<span class="input-group-addon"><i
									class="glyphicon glyphicon-user"></i></span> <input
									id="login-username" type="email" class="form-control"
									name="email" required value="<%=email%>"
									placeholder="<%=CreationCompteProText.HINT_EMAIL%>">
							</div>

							<div style="margin-bottom: 25px" class="input-group">
								<span class="input-group-addon"><i
									class="glyphicon glyphicon-lock"></i></span> <input
									id="login-password" type="password" class="form-control"
									name="pwd" value="<%=pwd%>"
									placeholder="<%=CreationCompteProText.HINT_MOT_DE_PASSE%>">
							</div>

							<div style="margin-bottom: 25px" class="input-group">
								<span class="input-group-addon"><i
									class="glyphicon glyphicon-lock"></i></span> <input
									id="login-password-bis" type="password" class="form-control"
									name="pwd1" value="<%=pwd1%>"
									placeholder="<%=CreationCompteProText.HINT_MOT_DE_PASSE_BIS%>">
							</div>
							<div class="row">
								<div class='col-md-4 col-md-offset-5'>
									<h4>Renseignements</h4>
								</div>
							</div>

							<div class="form-group">
								<label for="nom"><%=CreationCompteProText.LABEL_NOM%></label> <input
									type="text"
									maxlength="<%=CreationCompteProText.TAILLE_PSEUDO_MAX%>"
									value="<%=pseudo%>" class="form-control" id="nom"
									placeholder="<%=CreationCompteProText.getHintNomSociete()%>" name="nom" required>
							</div>

							<div class="form-group">
								<label for="nom"><%=CreationCompteProText.LABEL_NUMERO_SIRET%></label>
								<input type="text" class="form-control" id="nom"
									placeholder="<%=CreationCompteProText.HINT_SIRET%>" value="<%=siret%>" name="siret"
									pattern="[0-9]{<%=CreationCompteProText.TAILLE_SIRET_MAX%>}"
									maxlength="<%=CreationCompteProText.TAILLE_SIRET_MAX%>"
									required>
							</div>

							<div class="form-group">
								<label for="nom"><%=CreationCompteProText.LABEL_TELEPHONE%></label>
								<input type="text" class="form-control" id="nom"
									placeholder="<%=CreationCompteProText.HINT_TELEPHONE%>"
									pattern="[0-9]{10}"
									name="telephone" value="<%=telephone%>"
									required
									maxlength="<%=CreationCompteProText.TAILLE_TELEPHONNE_MAX%>">
							</div>

							<input type="hidden" class="form-control" id="typeuser"
								placeholder="typeuser" name="typeuser" required value="1">

							<div class="form-group">
								<label for="adresse"><%=CreationCompteProText.LABEL_ADRESSE%></label>
								<input type="text" class="form-control" id="adresse"
									value="<%=adresse%>" placeholder="Renseigner l'adresse"
									name="adresse" onkeypress="initPosition()"
									required
									maxlength="<%=CreationCompteProText.TAILLE_ADRESSE_MAX%>">
							</div>

							<div class="form-group">
								<label for="commentaire"><%=CreationCompteProText.LABEL_DESCRIPTION_PROFIL%></label>
								<textarea class="form-control" rows="5" id="description"
									placeholder="<%=CreationCompteProText.getHintDescriptionProfil()%>"
									maxlength="<%=CreationCompteProText.TAILLE_DESCRIPTION_PROFIL_MAX%>"
									value="<%=commentaire%>" name="commentaire"></textarea>
							</div>

							<h5 class="nbrcaracteremax" id="nbr">
								0 Caractére sur <%=CreationCompteProText.TAILLE_DESCRIPTION_PROFIL_MAX%>
							</h5>
							<div class="form-group">

								<input type="hidden" class="form-control" id="latitude"
									name="latitude" value=0>
							</div>
							<div class="form-group">

								<input type="hidden" class="form-control" id="longitude"
									name="longitude" value=0>
							</div>
							<div class="form-group">
								<div class="g-recaptcha"
									data-sitekey="6Ld6TzgUAAAAAMx76Q_NXm3xEJ1vPa799RLMeYLn"></div>
							</div>

							
								<button type="submit" class="btn btnwayd">Soumettre</button>
								<a href="/wayd/Home" class="btn btnwayd" role="button"><span
									class="glyphicon glyphicon-home"></span> Accueil</a>


							
						</div>

					</form>


				</div>
			</div>
		</div>
	</div>

	<script>

	latitude=<%=latitude%>;
	longitude=<%=longitude%>
	
			var placeSearch, autocomplete;
			var componentForm = {
				street_number : 'short_name',
				route : 'long_name',
				locality : 'long_name',
				administrative_area_level_1 : 'short_name',
				country : 'long_name',
				postal_code : 'short_name'
			};
			function initAutocomplete() {
				// Create the autocomplete object, restricting the search to geographical
				// location types.
				autocomplete = new google.maps.places.Autocomplete(
				/** @type {!HTMLInputElement} */
				(document.getElementById('adresse')), {
					types : [ 'geocode' ]
				});
				// When the user selects an address from the dropdown, populate the address
				// fields in the form.
				autocomplete.addListener('place_changed', fillInAddress);
			}
			function fillInAddress() {
				// Get the place details from the autocomplete object.

				var place = autocomplete.getPlace();
				document.getElementById("latitude").value = autocomplete
						.getPlace().geometry.location.lat();
				document.getElementById("longitude").value = autocomplete
						.getPlace().geometry.location.lng();
				latitude = autocomplete.getPlace().geometry.location.lat();

				longitude = autocomplete.getPlace().geometry.location.lng();

			}
			// Bias the autocomplete object to the user's geographical location,
			// as supplied by the browser's 'navigator.geolocation' object.
			function geolocate() {
				if (navigator.geolocation) {
					navigator.geolocation
							.getCurrentPosition(function(position) {
								var geolocation = {
									lat : position.coords.latitude,
									lng : position.coords.longitude
								};
								var circle = new google.maps.Circle({
									center : geolocation,
									radius : position.coords.accuracy
								});
								autocomplete.setBounds(circle.getBounds());
							});
				}
			}
		</script>
	<script
		src="https://maps.googleapis.com/maps/api/js?key=<%=Outils.getCleMap()%>&libraries=places&callback=initAutocomplete"
		async defer></script>

	<script>
			function valideFormulaire() {

				

					if ((latitude == 0 || longitude == 0)) {
					BootstrapDialog
							.alert("<%=CreationCompteProText.ALERT_GPS_NO_POSITION%>");
					return false;
				}
		
		return true;	
			
			}

			function initPosition() {
				latitude = 0;
				longitude = 0;

			}
		</script>
	<script>
		$(document).ready(function(e) {

			$('#description').keyup(function() {

				var nombreCaractere = $(this).val().length;
				//alert(nombreCaractere);

				var msg = nombreCaractere + ' Caractere(s) / <%=CreationCompteProText.TAILLE_DESCRIPTION_PROFIL_MAX%>';

												$('#nbr').text(msg);
												// Le script qui devra calculer et afficher le nombre de mots et de caractères

											})

						});

		// Init le nombre de caraterces	
		var nombreCaractere = $('#description').val().length;
		var msg = nombreCaractere + " / "
				+
	<%=CreationCompteProText.TAILLE_DESCRIPTION_PROFIL_MAX%>
		;
		$('#nbr').text(msg);
	</script>
</body>
</html>