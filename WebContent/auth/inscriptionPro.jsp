<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="website.metier.TypeUser"%>
<%@page import="website.metier.TypeAccess"%>
<%@page import="website.html.*"%>
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
<link href="/wayd/css/nbrcaractere.css" rel="stylesheet" media="all" type="text/css"> 
<script>
	var latitude = 0;
	var longitude = 0;
</script>

</head>
<body>


	<div class="container">
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">
					<div class="panel-title">Inscription Professionel</div>

				</div>

				<div style="padding-top: 30px" class="panel-body">

					<form action="../Form_PremierProfil"
						onsubmit="return valideFormulaire()">
						<div class="form-group">
							<label for="nom">Nom*:</label> <input type="text"
								 maxlength="<%=ParametreHtmlPro.TAILLE_PSEUDO_MAX %>" class="form-control" id="nom" placeholder="Nom " name="nom"
								required>
						</div>

						<div class="form-group">
							<label for="nom">Numéro SIRET*:</label> <input type="text"
								class="form-control" id="nom" placeholder="numero siret "
								name="siret"  maxlength="<%=ParametreHtmlPro.TAILLE_SIRET_MAX %>" required>
						</div>

						<div class="form-group">
							<label for="nom">Téléphone:</label> <input type="text"
								class="form-control" id="nom" placeholder="téléphone"
								name="telephone" required maxlength="<%=ParametreHtmlPro.TAILLE_TELEPHONNE_MAX%>"
								>
						</div>

						<input type="hidden" class="form-control" id="typeuser"
							placeholder="typeuser" name="typeuser" required value="1">


						<div class="form-group">
							<label for="adresse">Adresse*:</label> <input type="text"
								class="form-control" id="adresse"
								placeholder="Renseigner l'adresse" name="adresse"
								onkeypress="initPosition()" maxlength="<%=ParametreHtmlPro.TAILLE_ADRESSE_MAX %>">
						</div>

						<div class="form-group">
							<label for="commentaire">Renseignements:</label>
							<textarea class="form-control" rows="5" id="description"
							placeholder="<%=ParametreHtmlPro.getHintDescriptionProfil() %>" maxlength="<%=ParametreHtmlPro.TAILLE_DESCRIPTION_PROFIL_MAX %>"
								name="commentaire"></textarea>
						</div>
						
						<h5 class="nbrcaracteremax" id="nbr">0 Caractére sur <%=ParametreHtmlPro.TAILLE_DESCRIPTION_ACTIVITE_MAX %></h5>
						
						</br>
						<button type="submit" class="btn btn-info">Enregistrer</button>

						<div class="form-group">

							<input type="hidden" class="form-control" id="latitude"
								name="latitude" value=0>
						</div>
						<div class="form-group">

							<input type="hidden" class="form-control" id="longitude"
								name="longitude" value=0>
						</div>

					</form>


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
			src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA_K_75z5BiALmZbNnEHlP7Y7prhXd-vAc&libraries=places&callback=initAutocomplete"
			async defer></script>

		<script>
			function valideFormulaire() {
				if ((latitude == 0 || longitude == 0)) {
					BootstrapDialog
							.alert('La position GPS de votre adresse n\'a pas ï¿½tï¿½ trouvï¿½e. Veuillez ressaisir votre adresse');
					return false;
				}
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

				var msg = nombreCaractere + ' Caractere(s) / <%=ParametreHtmlPro.TAILLE_DESCRIPTION_ACTIVITE_MAX %>';

				$('#nbr').text(msg);
				// Le script qui devra calculer et afficher le nombre de mots et de caractères

			})

		});

		// Init le nombre de caraterces	
		var nombreCaractere = $('#description').val().length;
		var msg = nombreCaractere + ' Caractere(s) / <%=ParametreHtmlPro.TAILLE_DESCRIPTION_ACTIVITE_MAX %>';
		$('#nbr').text(msg);
	</script>
</body>
</html>