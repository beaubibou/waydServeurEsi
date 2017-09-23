<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="website.metier.TypeUser"%>
<%@page import="website.metier.TypeAccess"%>

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
		ArrayList<TypeUser>  listTypeUser =new CacheValueDAO().getListTypeUser();
	%>

	<div class="container">
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">
					<div class="panel-title">Inscription</div>

				</div>

				<div style="padding-top: 30px" class="panel-body">



					<form action="../Form_PremierProfil"
						onsubmit="return valideFormulaire()">
						<div class="form-group">
							<label for="nom">Nom*:</label> <input type="text"
								class="form-control" id="nom" placeholder="Nom " name="nom"
								required>
						</div>

						<div class="form-group">
							<label for="typeuser">Vous êtes:</label> <select
								class="form-control" id="typeuser" name="typeuser">
								<%
									for (TypeUser typeuser:listTypeUser) {
								%>
								<option value="<%=typeuser.getId()%>"><%=typeuser.getLibelle()%></option>
								<%
									}
								%>
							</select>

						</div>

						<div id="divSexe" class="form-group">
							<label for="sexe">Vous êtes:</label> <select class="form-control"
								id="idsexe" name="sexe">
								<option value="1">Homme</option>
								<option value="2">Femme</option>
							</select>
						</div>
						<div class="form-group">
							<label for="adresse">Adresse*:</label> <input type="text"
								class="form-control" id="adresse"
								placeholder="Renseigner l'adresse" name="adresse"
								onkeypress="initPosition()">
						</div>

						<div class="form-group">
							<label for="commentaire">Renseignements:</label>
							<textarea class="form-control" rows="5" id="commentaire"
								name="commentaire"></textarea>
						</div>

						</br>
						<button type="submit" class="btn btn-info">Enregistrer</button>

						<div class="form-group">

							<input type="text" class="form-control" id="latitude"
								name="latitude" value=0>
						</div>
						<div class="form-group">

							<input type="text" class="form-control" id="longitude"
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
				var typeuser = $("#typeuser").val();
				if (typeuser==3)return true;
				if ((latitude == 0 || longitude == 0)) {
					BootstrapDialog
							.alert('La position GPS de votre adresse n\'a pas été trouvée. Veuillez ressaisir votre adresse');
					return false;
				}
			}

			function initPosition() {
				latitude = 0;
				longitude = 0;

			}
		</script>

		<script>
			$(function() {
				$("#divSexe").hide();

				$('#typeuser').on('change', function() {

					var selected = $(this).find("option:selected").val();

					if (selected == 3) {

						$("#adresse").hide();
						$("#labeladresse").hide();
						$("#divSexe").show();

					}

					else {

						$("#adresse").show();
						$("#labeladresse").show();
						$("#divSexe").hide();
					}

				});

				// initialisation des champs
				var typeuser = $("#typeuser").val();

				if (typeuser == 3) {
					$("#adresse").hide();
					$("#labeladresse").hide();
					$("#divSexe").show();

				} else {

					$("#adresse").show();
					$("#labeladresse").show();
					$("#divSexe").hide();

				}

			});
		</script>
</body>
</html>