<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>
<%@page import="website.metier.ProfilBean"%>
<%@page import="website.metier.TypeActiviteBean"%>
<%@page import="website.metier.TypeAccess"%>
<%@page import="website.dao.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="website.metier.AuthentificationSite"%>
<%@page import="website.enumeration.MenuEnum"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>>Création activité</title>

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
	MenuEnum etatMenu=MenuEnum.ajouteActivitePlanifiee;
	%>
	
	<%@ include file="menu.jsp"%>
	<div class="container">
		<div class="page-header" style="margin-top: 100px">
			<h1>Proposez vos activités</h1>
		</div>
		<p>Proposez vos activités gratuites à la communauté. Une activité
			ne peut pas exéder 8 heures.</p>
		<p>Vous pouvez planifier jusqu à 5 activités simultanément.</p>
	</div>

	<div id="loginbox" style="margin-top: 50px;"
		class="mainbox col-md-8 col-md-offset-2 col-sm-8">
		<div class="panel panel-default">
			<div class="panel-heading panel-heading-custom">
				<div class="panel-title">
					<span><img src="/wayd/img/waydLogoHD.png" class="img-thumbnail"
						alt="Cinque Terre" style="width: 10%"> </span> Ajoute une activité
				</div>


			</div>

			<div style="padding-top: 30px" class="panel-body">
				<form action="/wayd/AjouteActivitePlanifiee"
					onsubmit="return valideFormulaire()" method="post">

					<div class="form-group">
						<div class="row">
							<div class='col-sm-8'>
								<label for="titre">Titre:</label> <input type="text"
									maxlength="50" class="form-control input" id="titre" required
									placeholder="Nom " name="titre" value="tire">

							</div>
							<div class='col-sm-4'>
								<label for="typeactivite">Type d'activitée:</label> <select
									class="form-control" id="type" name="typeactivite">
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
						<div class="row">

							<div class='col-sm-4'>
								<div class="form-group">
									<label for="iddatedebut">Date debut</label>
									<div class='input-group date' id='datedebut'>
										<input type='text' class="form-control" id="iddatedebut"
											name="debut" /> <span class="input-group-addon"> <span
											class="glyphicon glyphicon-calendar"></span>
										</span>
									</div>
								</div>
							</div>

							<div class='col-sm-4'>
								<div class="form-group">
									<label for="iddatefin">Date fin</label>
									<div class='input-group date' id="datefin">
										<input type='text' class="form-control" id="iddatefin"
											name="fin" /> <span class="input-group-addon"> <span
											class="glyphicon glyphicon-calendar"></span>
										</span>
									</div>
								</div>
							</div>



						</div>

					</div>


					<div class="form-group">
						<div class="row">

							<div class='col-sm-4'>
								<div class="form-group">
									<label for="idheuredebut">Heure debut</label>
									<div class='input-group date' id='heuredebut'>
										<input type='text' class="form-control" id="idheuredebut"
											name="heuredebut" /> <span class="input-group-addon">
											<span class="glyphicon glyphicon-calendar"></span>
										</span>
									</div>
								</div>
							</div>

							<div class='col-sm-4'>
								<div class="form-group">
									<label for="idheurefin">Heure fin</label>
									<div class='input-group date' id="heurefin">
										<input type='text' class="form-control" id="idheurefin"
											name="heurefin" /> <span class="input-group-addon"> <span
											class="glyphicon glyphicon-calendar"></span>
										</span>
									</div>
								</div>
							</div>



						</div>

					</div>


					<div class="container">

						<h4>Cette activité se répéte tous les jours:</h4>
						</br> <label class="radio-inline"> <input type="checkbox"
							name="lundi">Lundi
						</label> <label class="radio-inline"> <input type="checkbox"
							name="mardi">Mardi
						</label> <label class="radio-inline"> <input type="checkbox"
							name="mercredi">Mercredi
						</label> <label class="radio-inline"> <input type="checkbox"
							name="jeudi">Jeudi
						</label> <label class="radio-inline"> <input type="checkbox"
							name="vendredi">Vendredi
						</label> <label class="radio-inline"> <input type="checkbox"
							name="samedi">Samedi
						</label> <label class="radio-inline"> <input type="checkbox"
							name="dimanche">Dimanche
						</label>


					</div>
					<div class="form-group">
						<label for="adresse">Adresse:</label> <input type="text"
							class="form-control" id="adresse" required
							value="<%out.println(profil.getAdresse());%>" name="adresse"
							onkeypress="initPosition()">
					</div>

					<div class="form-group">
						<label for="description">Description:</label>
						<textarea maxlength="200" class="form-control" rows="5"
							id="description" name="description"></textarea>
					</div>
					<h5 class="nbrcaracteremax" id="nbr">0 Caractére sur 200</h5>
					<button type="submit" class="btn btn-info">Enregistrer</button>

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

					<div class="form-group"></div>

				</form>

			</div>
		</div>
	</div>



	<div class="navbar navbar-default navbar-fixed-bottom" id="monfooter">
		<div class="container">
			<p class="navbar-text pull-left">
				© 2014 - Site Built By Mr. M. <a href="http://tinyurl.com/tbvalid"
					target="_blank">HTML 5 Validation</a>
			</p>

			<a href="http://youtu.be/zJahlKPCL9g"
				class="navbar-btn btn-danger btn pull-right"> <span
				class="glyphicon glyphicon-star"></span>  Subscribe on YouTube
			</a>
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
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA_K_75z5BiALmZbNnEHlP7Y7prhXd-vAc&libraries=places&callback=initAutocomplete"
		async defer></script>
	<script>
		$(function() {

			$('#datedebut').datetimepicker(
					{
						defaultDate : moment(new Date()).hours(0).minutes(0)
								.seconds(0).milliseconds(0),
						format : 'DD/MM/YYYY'

					});

			$('#datefin').datetimepicker(
					{
						defaultDate : moment(new Date()).hours(0).minutes(0)
								.seconds(0).milliseconds(0),
						format : 'DD/MM/YYYY'

					});

			$('#heuredebut').datetimepicker({
				defaultDate : new Date,
				format : 'HH:mm'

			});
			$('#heurefin').datetimepicker({
				defaultDate : new Date,
				format : 'HH:mm'

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
			return true;
			var datedebut = $('#datedebut').data('DateTimePicker').date();
			var datefin = $('#datefin').data('DateTimePicker').date();

			// Verifie les positions
			latitude = document.getElementById("latitude").value;
			longitude = document.getElementById("longitude").value;

			if (latitude == 0 || longitude == 0) {
				alert();
				BootstrapDialog
						.alert('La position GPS de votre adresse n\'a pas été trouvée. Veuillez ressaisir votre adresse');
				return false;
			}

			if (datedebut > datefin) {
				alert("date debut>datefin");
				return false;
			}
			if (datefin < new Date()) {
				alert("date fin avant maientnant");
				return false;
			}

			diffHeure = heureDiff(new Date(datedebut).getTime(), new Date(
					datefin).getTime());
			// Condition Ã  rajouter pour le nbr d'heure max de l'activitÃ©

			if (diffHeure > 8) {
				alert("La durée ne peut pas exéder 8 heures");
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

				var msg = nombreCaractere + ' Caractere(s) / 200';

				$('#nbr').text(msg);
				// Le script qui devra calculer et afficher le nombre de mots et de caractères

			})

		});

		// Init le nombre de caraterces	
		var nombreCaractere = $('#description').val().length;
		var msg = nombreCaractere + ' Caractere(s) / 200';
		$('#nbr').text(msg);
	</script>
</body>
</html>
