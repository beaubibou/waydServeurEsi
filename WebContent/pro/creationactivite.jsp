<%@page import="texthtml.pro.CreationActivitePlanifieeText"%>
<%@page import="texthtml.pro.CreationActiviteText"%>
<%@page import="texthtml.pro.Erreur_HTML"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="website.metier.ProfilBean"%>
<%@page import="website.metier.TypeActiviteBean"%>
<%@page import="website.metier.TypeAccess"%>
<%@page import="website.dao.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="website.metier.AuthentificationSite"%>
<%@page import="website.metier.Outils"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>><%=CreationActiviteText.TITRE_ONGLET%></title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

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

<script src="js/moment.js"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css"
	rel="stylesheet" type="text/css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<link href="/wayd/css/styleWayd.css" rel="stylesheet" type="text/css">
<link href="/wayd/css/nbrcaractere.css" rel="stylesheet" media="all"
	type="text/css">
</head>
<body>

	<%
		AuthentificationSite authentification=	new AuthentificationSite(request, response);
			if (!authentification.isAuthentifiePro())
			return;
			
			ProfilBean profil = authentification.getProfil();
			ArrayList<TypeActiviteBean> listTypeActivite=CacheValueDAO.getListTypeActivitePro();
			// Defini le li a rendre actif
		MenuEnum etatMenu=null;
	%>

	<%@ include file="menu.jsp"%>


	<div class="container" >
		<div id="loginbox"
			class="mainbox col-md-8 col-md-offset-2 col-sm-8 margedebut">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">
					<div class="panel-title"><%=CreationActiviteText.TITRE_PANEL%></div>
				</div>

				<div style="padding-top: 30px" class="panel-body">
				
				
					<form action="/wayd/AjouteActivitePro"
						onsubmit="return valideFormulaire()" method="post">

						<div class="form-group"   style="border-bottom: 1px solid #888;">

							<p class="text-tuto"><%=CreationActiviteText.MESSAGE_JUMBO_LIGNE1%></p>
							<p class="text-tuto"><%=CreationActiviteText.MESSAGE_JUMBO_LIGNE2%></p>
								<p class="text-tuto"><%=CreationActiviteText.MESSAGE_JUMBO_LIGNE3%></p>
						
						</div>
					<br>	

						<div class="form-group">
							<label for="titre"><%=CreationActiviteText.LABEL_TITRE%></label>
							<input type="text" class="form-control" id="titre" required
								placeholder="<%=CreationActiviteText.getHintTitreActivite()%>"
								maxLength="<%=CreationActiviteText.TAILLE_TITRE_ACTIVITE_MAX%>"
								name="titre" required>
						</div>


						<div class="form-group">
							<div class="row">

								<div class='col-sm-4'>
									<div class="form-group">
										<label for="iddatedebut"><%=CreationActiviteText.LABEL_DATE_DEBUT%></label>
										<div class='input-group date' id='datedebut'>
											<input  s style="background-color:white;"  readonly type='text' class="form-control" id="iddatedebut"
												name="debut" /> <span class="input-group-addon"> <span
												class="glyphicon glyphicon-calendar"></span>
											</span>
										</div>
									</div>
								</div>

								<div class='col-sm-4'>
									<div class="form-group">
										<label for="iddatefin"><%=CreationActiviteText.LABEL_DATE_FIN%></label>
										<div class='input-group date' id="datefin">
											<input readonly style="background-color:white;" type='text' class="form-control" id="iddatefin"
												name="fin" /> <span class="input-group-addon"> <span
												class="glyphicon glyphicon-calendar"></span>
											</span>
										</div>
									</div>
								</div>
								<div class='col-sm-4'>
									<label for="typeactivite"><%=CreationActiviteText.LABEL_TYPE_ACTIVITE%></label>
									<select class="form-control" id="type" name="typeactivite">
										<%
											for (TypeActiviteBean typeactivite:listTypeActivite) {
										%>
										<option value="<%=typeactivite.getId()%>"><%=typeactivite.getLibelle()%></option>
										<%
											}
										%>
									</select>

								</div>
							</div>
						</div>

						<div class="form-group">
							<label for="adresse"><%=CreationActiviteText.LABEL_ADRESSE%></label>
							<input type="text" class="form-control" id="adresse" required
								value="<%=profil.getAdresse()%>" name="adresse"
								onkeypress="initPosition()"
								maxlength="<%=CreationActiviteText.TAILLE_ADRESSE_MAX%>">
						</div>

						<div class="form-group">
							<label for="description"><%=CreationActiviteText.LABEL_DESCRIPTION_ACTIVITE%></label>
							<textarea
								placeholder="<%=CreationActiviteText.getHintDescriptionActivite()%>"
								maxlength="<%=CreationActiviteText.TAILLE_DESCRIPTION_ACTIVITE_MAX%>"
								class="form-control" rows="5" id="description"
								name="description"></textarea>
						</div>
						<h5 class="nbrcaracteremax" id="nbr">

							<%=CreationActiviteText.initNbrCaracteres()%></h5>

						<button type="submit" class="btnwayd btn-lg">Proposer</button>

						<div class="form-group">

							<input type="hidden" class="form-control" id="latitude"
								placeholder="latitude" name="latitude"
								value=<%=profil.getLatitudeFixe()%>>
						</div>
						<div class="form-group">

							<input type="hidden" class="form-control" id="longitude"
								placeholder="longitude" name="longitude"
								value=<%=profil.getLongitudeFixe()%>>
						</div>
						
					</form>
					
						
				</div>
			</div>
		</div>


	</div>

	<script>
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
			document.getElementById("latitude").value = autocomplete.getPlace().geometry.location
					.lat();
			document.getElementById("longitude").value = autocomplete
					.getPlace().geometry.location.lng();
		}
		// Bias the autocomplete object to the user's geographical location,
		// as supplied by the browser's 'navigator.geolocation' object.
		function geolocate() {
			if (navigator.geolocation) {
				navigator.geolocation.getCurrentPosition(function(position) {
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
		$(function() {

			$('#datedebut').datetimepicker({
				defaultDate : new Date,
				format : 'DD/MM/YYYY HH:mm',
				focusOnShow: false,
				  ignoreReadonly: true

			});

			var heure = new Date().getHours() + 3;

			$('#datefin').datetimepicker(
					{
						defaultDate : moment(new Date()).hours(heure)
								.minutes(0).seconds(0).milliseconds(0),
						format : 'DD/MM/YYYY HH:mm',
						focusOnShow: false,
						  ignoreReadonly: true

					});

		});
	</script>
	<script>
		function dateDiff(date1, date2) {
			var diff = {} // Initialisation du retour
			var tmp = date2 - date1;

			tmp = Math.floor(tmp / 1000); // Nombre de secondes entre les 2 dates
			diff.sec = tmp % 60; // Extraction du nombre de secondes

			tmp = Math.floor((tmp - diff.sec) / 60); // Nombre de minutes (partie entiÃ¨re)
			diff.min = tmp % 60; // Extraction du nombre de minutes

			tmp = Math.floor((tmp - diff.min) / 60); // Nombre d'heures (entiÃ¨res)
			diff.hour = tmp % 24; // Extraction du nombre d'heures

			tmp = Math.floor((tmp - diff.hour) / 24); // Nombre de jours restants
			diff.day = tmp;

			return diff;
		}

		function heureDiff(date1, date2) {

			var tmp = date2 - date1;

			tmp = Math.floor(tmp / 1000) / 3600;

			return tmp;

		}

		function valideFormulaire() {
			
			var datedebut = $('#datedebut').data('DateTimePicker').date();
			var datefin = $('#datefin').data('DateTimePicker').date();

			// Verifie les positions
			latitude = document.getElementById("latitude").value;
			longitude = document.getElementById("longitude").value;

			if (latitude == 0 || longitude == 0) {
			
				BootstrapDialog.show({
					message:"<%=CreationActivitePlanifieeText.ALERT_GPS_NO_POSITION%>"
							});

				return false;
			}

			if (datedebut > datefin) {
				BootstrapDialog.show({
					message:"<%=Erreur_HTML.DATEDEBUT_SUP_DATEFIN%>"
							});
				return false;
			}
			if (datefin < new Date()) {
				BootstrapDialog.show({
					message:"<%=Erreur_HTML.DATEFIN_INF_NOW%>"
							});
				return false;
			}

			diffHeure = heureDiff(new Date(datedebut).getTime(), new Date(
					datefin).getTime());
			// Condition Ã  rajouter pour le nbr d'heure max de l'activitÃ©

			if (diffHeure > 8) {
				BootstrapDialog.show({
					message:"<%=Erreur_HTML.DUREE_PAS_SUPERIEUR_A%>"
							});
				return false;
			}

			if (diffHeure <1) {
				BootstrapDialog.show({
					message:"<%=Erreur_HTML.DUREE_PAS_INFERIEURE_A%>"
							});
				return false;
			}
			return true;
		}

		function initPosition() {
			latitude = 0;
			longitude = 0;
			document.getElementById("latitude").value = 0;
			longitude = document.getElementById("longitude").value = 0;

		}
	</script>

	<script>
		$(document).ready(function(e) {

			$('#description').keyup(function() {

				var nombreCaractere = $(this).val().length;
				//alert(nombreCaractere);

				var msg = nombreCaractere + '<%=CreationActiviteText.getNbrCarateresDescription()%>';

				$('#nbr').text(msg);
				// Le script qui devra calculer et afficher le nombre de mots et de caractères

			})

		});

		// Init le nombre de caraterces	
		var nombreCaractere = $('#description').val().length;
		var msg = nombreCaractere +   '<%=CreationActiviteText.getNbrCarateresDescription()%>';
		$('#nbr').text(msg);
	</script>
</body>
</html>
