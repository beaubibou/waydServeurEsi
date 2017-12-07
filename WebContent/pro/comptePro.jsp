<%@page import="texthtml.pro.CompteProText"%>
<%@page import="javax.print.attribute.standard.Compression"%>
<%@page import="website.metier.ProfilBean"%>
<%@page import="website.metier.Outils"%>
<%@page import="website.html.*"%>
<%@page import="website.metier.AuthentificationSite"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title><%=CompteProText.TITRE_ONGLET %></title>

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

<link href="/wayd/css/styleWayd.css" rel="stylesheet" type="text/css"/>

<link href="/wayd/css/nbrcaractere.css" rel="stylesheet" media="all"type="text/css"/>


<script src="js/initGoogleMap.js"></script>

<script>
	var latitude = 0;
	var longitude = 0;
</script>

</head>
<body>

	<%
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		if (!authentification.isAuthentifiePro())
			return;

		ProfilBean profil = authentification.getProfil();
		MenuEnum etatMenu = MenuEnum.moncompte;
	%>
	<%@ include file="menu.jsp"%>

	<div class="container margedebut" >
		<div id="loginbox" class="mainbox col-md-8 col-md-offset-2 col-sm-8">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">
					<div class="panel-title">Mon compte</div>
				</div>
				<div style="padding-top: 30px" class="panel-body">

						<div  style="border-bottom: 1px solid #888;">

							<p class="text-tuto"><%=CompteProText.MESSAGE_JUMBO_L1%></p>
								<p class="text-tuto"><%=CompteProText.MESSAGE_JUMBO_L2%></p>
					
					</div>
						<br>
					<br>
							
					
					<div class="form-group">
						<div class="row">
							<div class="col-sm-4">
								<img height="300" width="200"
									src=<%=Outils.getUrlPhoto(profil.getPhotostr())%>
									class="img-thumbnail" />
							</div>
							<div class="col-sm-8">
								<form action="/wayd/ChargePhotoPro" method="post"
									enctype="multipart/form-data" onsubmit="return valideFichier()">
									<input type="file" name="file" size="50" id="file" /> 
									<br><input
										type="submit" value="Envoyer" class="btn btnwayd btn-sm" />
								</form>
							</div>
						</div>
					</div>


					<form action="/wayd/ComptePro" method="post"
						onsubmit="return valideFormulaire()">
					
					
					
						<div class="form-group">
						
						<div class="row">
								<div class="col-sm-2">
									<div class="form-group">
									<a id="btn-primary" href="/wayd/auth/changementmotdepasse.jsp"
									class="btn  btnwayd">Changement de mot de passe</a>
							</div>
							</div>
					 </div>	
							
							<div class="row">
								<div class="col-sm-8">
									<div class="form-group">
										<label for="nom"><%=CompteProText.LABEL_NOM %></label> <input type="text"
											class="form-control" id="nom"
											placeholder="<%=CompteProText.getHintNomSociete()%>"
											maxlength="<%=CompteProText.TAILLE_PSEUDO_MAX%>"
											name="nom" required
											value="<%=profil.getPseudo()%>">
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label for="typro"><%=CompteProText.TYPE_COMPTE%></label> <input type="text"
											class="form-control" disabled id="typepro"
											value="Professionel">
									</div>
								</div>

							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-sm-8">
									<div class="form-group">
										<label for="siteweb"><%=CompteProText.LABEL_SITE_WEB %></label>
									</div>
									<input type="text" class="form-control" id="siteweb"
										maxlength="<%=CompteProText.TAILLE_SITE_WEB_MAX%>"
										placeholder='<%=CompteProText.HINT_SITEWEB%>' name="siteweb"
										value=<%=OutilsHtml.convertRequeteToString(profil.getSiteWebStr())%>>
								</div>


								<div class="col-sm-4">
									<div class="form-group">
										<label for="tel"><%=CompteProText.LABEL_TELEPHONE %></label>
									</div>
									<input type="text" class="form-control" id="tel"
										placeholder="<%=CompteProText.HINT_TELEPHONE %>" name="telephone"
										pattern="[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}"
										maxlength="<%=CompteProText.TAILLE_TELEPHONNE_MAX%>"
										required
										value=<%=OutilsHtml.convertRequeteToString(profil.getTelephoneStr())%>>
								</div>

							</div>
						</div>

						<div class="form-group">
							<div class="row">
								<div class="col-sm-8">
									<div class="form-group">
										<label for="siret"><%=CompteProText.LABEL_NUMERO_SIRET %></label>
									</div>
									<input type="text" class="form-control" id="siret"
										maxlength='<%=CompteProText.TAILLE_SIRET_MAX%>'
										pattern="[0-9]{<%=CompteProText.TAILLE_SIRET_MAX %>}"
										
										placeholder='<%=CompteProText.HINT_SIRET%>' name="siret"
										value=<%=OutilsHtml.convertRequeteToString(profil.getSiret())%>>
								</div>

							</div>
						</div>


						<div class="form-group">
							<label for="adresse"><%=CompteProText.LABEL_ADRESSE %></label> <input type="text"
								class="form-control" id="adresse"
								placeholder='<%=CompteProText.HINT_ADRESSE %>' name="adresse" required
								onkeypress="initPosition()"
								maxlength="<%=CompteProText.TAILLE_ADRESSE_MAX%>"
								value="<%=OutilsHtml.convertRequeteToString(profil.getAdresse())%>">
						</div>

						<div class="form-group">
							<label for="description"><%=CompteProText.LABEL_DESCRIPTION_PROFIL %></label>
							<textarea class="form-control" rows="5" id="description"
								name="commentaire"
								placeholder="<%=CompteProText.getHintDescriptionProfil()%>"
								maxlength="<%=CompteProText.TAILLE_DESCRIPTION_PROFIL_MAX%>"><%=profil.getCommentaireStr()%></textarea>
						</div>

						<h5 class="nbrcaracteremax" id="nbr">
							0 Caractére  "<%=CompteProText.TAILLE_DESCRIPTION_PROFIL_MAX%>"
						</h5>

						<button type="submit" class="btnwayd btn-lg">Sauvegarder</button>

						<div class="form-group">

							<input type="hidden" class="form-control" id="latitude"
								name="latitude" value=<%=profil.getLatitude()%>>
						</div>
						<div class="form-group">

							<input type="hidden" class="form-control" id="longitude"
								name="longitude" value=<%=profil.getLongitude()%>>
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
			latitude = autocomplete.getPlace().geometry.location.lat();

			longitude = autocomplete.getPlace().geometry.location.lng();

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
	
	function valideFichier(){
		
		var monfichier;
		monfichier=latitude = document.getElementById("file").value;
		
	if (monfichier==''){
		BootstrapDialog
		.alert("<%=CompteProText.AUCUN_FICHIER_SELECTIONNE%>");
	return false;
	}
			
	
	}
	function valideFormulaire() {

			
			latitude = document.getElementById("latitude").value;
			longitude = document.getElementById("longitude").value;
			if (latitude == 0 || longitude == 0) {
				BootstrapDialog
						.alert("<%=CompteProText.ALERT_GPS_NO_POSITION%>");
				return false;
			}
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

				var msg = nombreCaractere + '/ <%=CompteProText.TAILLE_DESCRIPTION_PROFIL_MAX%>';

												$('#nbr').text(msg);
												// Le script qui devra calculer et afficher le nombre de mots et de caractères

											})

						});

		// Init le nombre de caraterces	
		var nombreCaractere = $('#description').val().length;
		var msg = nombreCaractere + "/"
				+
		<%=CompteProText.TAILLE_DESCRIPTION_PROFIL_MAX%>
		;
		$('#nbr').text(msg);
	</script>
</body>
</html>
