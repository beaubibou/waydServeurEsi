<%@page import="website.metier.ProfilBean"%>
<!DOCTYPE html>
<html lang="en">
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

		if (profil == null) {

		}
	%>
<%@ include file="menu.jsp"%>

	<div class="container">
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-8 col-md-offset-2 col-sm-8">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">
					<div class="panel-title">Mon compte</div>
				</div>
				<div style="padding-top: 30px" class="panel-body">


					<form action="../ComptePro" method="post"
						onsubmit="return valideFormulaire()">
						<div class="form-group">
							<label for="nom">Nom*:</label>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-sm-8">
									<input type="text" class="form-control" id="nom"
										placeholder="Nom " name="nom" required value=<%out.println(profil.getPseudo());%>>
								</div>
								<div class="col-sm-4">
									<input type="text" class="form-control" disabled="true"
										id="nom" placeholder="Nom " name="nom" value="Professionel">
								</div>

							</div>
						</div>


						<div class="form-group">
							<label for="adresse">Adresse*:</label> <input type="text"
								class="form-control" id="adresse"
								placeholder="Renseigner l'adresse" name="adresse" required
								onkeypress="initPosition()"
								value=<%out.println(profil.getAdresse());%>>
						</div>

						<div class="form-group">
							<label for="commentaire">Renseignements:</label>
							<textarea class="form-control" rows="5" id="commentaire"
								name="commentaire"   ><%out.println(profil.getCommentaireStr());%></textarea>
						</div>


						<div class="form-group">
							<div class="row">
								<div class="col-sm-8">
									<div class="form-group">
										<label for="siteweb">Site web:</label>
									</div>
									<input type="text" class="form-control" id="siteweb"
										placeholder="http://monsite.fr" name="siteweb" value=<%out.println(profil.getSiteWebStr());%>>
								</div>

								<div class="col-sm-4">
									<div class="form-group">
										<label for="tel">TÈlÈphone</label>
									</div>
									<input type="text" class="form-control" id="tel"
										placeholder="06-xx-xx-xx" name="telephone" value=<%out.println(profil.getTelephoneStr());%>>
								</div>

							</div>
						</div>

						<button type="submit" class="btn btn-info">Modifier</button>

						<div class="form-group">

							<input type="text" class="form-control" id="latitude"
								name="latitude" value=<%out.println(profil.getLatitude());%>>
						</div>
						<div class="form-group">

							<input type="text" class="form-control" id="longitude"
								name="longitude" value=<%out.println(profil.getLongitude());%>>
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
				
			latitude=document.getElementById("latitude").value ;
			longitude=document.getElementById("longitude").value ;
				if (latitude == 0 || longitude == 0) {
					BootstrapDialog
							.alert('La position GPS de votre adresse n\'a pas √©t√© trouv√©e. Veuillez ressaisir votre adresse');
					return false;
				}
			}

			function initPosition() {
				latitude = 0;
				longitude = 0;
				document.getElementById("latitude").value=0;
				longitude=document.getElementById("longitude").value=0;
				

			}
		</script>
</body>
</html>
