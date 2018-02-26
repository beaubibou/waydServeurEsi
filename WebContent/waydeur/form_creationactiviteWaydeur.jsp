<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="website.metier.ProfilBean"%>
<%@page import="website.metier.TypeActiviteBean"%>
<%@page import="website.metier.QuantiteWaydeurBean"%>
<%@page import="website.metier.DureeBean"%>
<%@page import="website.dao.CacheValueDAO"%>
<%@page import="website.metier.Outils"%>
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html>

<html lang="fr">
<head>
<title>>Création activité</title>
<meta charset="utf-8">
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


<script src="/wayd/waydeur/js/moment.js"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css"
	rel="stylesheet" type="text/css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<link href="/wayd/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@ include file="menuWaydeur.jsp"%>


	<%
	
	ArrayList<TypeActiviteBean> listTypeActivite=new CacheValueDAO().getListTypeActiviteWaydeur();
			ArrayList<DureeBean> listDuree=new CacheValueDAO().getListDuree();
			ArrayList<QuantiteWaydeurBean> listQuantiteWaydeur=new CacheValueDAO().getListQuantiteWaydeur();
			
			return; 
	%>
<div class="container">
  <div class="page-header">
    <h1>Créez vos activités </h1>      
  </div>
  <p>Proposez une nouvelle activité à la communauté. 
  Votre activité débute au moment ou vous la validé pour la durée que vous souhaitez.</p>      
    
</div>
	<div class="container">
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">

					<div class="panel-title">Création activité</div>

				</div>

				<div style="padding-top: 30px" class="panel-body">

					<form action="/wayd/AjouteActiviteWaydeur" method="post"
						onsubmit="return valideFormulaire()">
						<div class="form-group">
							<label for="titre">Titre:</label> <input type="text"
								class="form-control" id="titre"
								placeholder="Titre de l'activité " name="titre" required>
						</div>
						<div class="form-group">
							<label for="adresse">Adresse:</label> <input type="text"
								class="form-control" id="adresse"
								placeholder="Renseigner l'adresse" name="adresse"
								onkeypress="initPosition()" required>
						</div>

						<div class="form-group">
							<label for="description">Description:</label>
							<textarea class="form-control" rows="5" id="description"
								name="description"></textarea>
						</div>

						<div class="form-group">
							<label for="typeactivite">Type d'activité:</label> <select
								class="form-control" id="typeactivite" name="typeactivite">
								<%
									for (TypeActiviteBean typeactivite:listTypeActivite) {
								%>
								<option value="<%=typeactivite.getId()%>"><%=typeactivite.getLibelle()%></option>
								<%
									}
								%>

							</select>
						</div>



						<div class="form-group">
							<div class="row">
								<div class='col-sm-6'>
									<div class="form-group">
										<label for="acces">Nbr max de waydeur</label> <select
											class="form-control" id="maxwaydeur" name="maxwaydeur">
											<%
												for (QuantiteWaydeurBean quantitewaydeur:listQuantiteWaydeur) {
											%>
											<option value="<%=quantitewaydeur.getValue()%>"><%=quantitewaydeur.getLibelle()%></option>
											<%
												}
											%>
										</select>
									</div>
								</div>

								<div class='col-sm-6'>
									<div class="form-group">

										<label for="duree">Durée:</label> <select class="form-control"
											id="typeactivite" name="duree">
											<%
												for (DureeBean duree:listDuree) {
											%>
											<option value="<%=duree.getValue()%>"><%=duree.getLibelle()%></option>
											<%
												}
											%>
										</select>
									</div>
								</div>

							</div>
						</div>



						<button type="submit" class="btn btn-default">Enregistrer</button>

						<div class="form-group">

							<input type="hidden" class="form-control" id="latitude"
								placeholder="Renseigner l'adresse" name="latitude" value="0">
						</div>
						<div class="form-group">

							<input type="hidden" class="form-control" id="longitude"
								placeholder="Renseigner l'adresse" name="longitude" value="0">
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
				format : 'D/MM/YYYY HH:mm'

			});

			$('#datefin').datetimepicker({
				defaultDate : new Date,
				format : 'D/MM/YYYY HH:mm'

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

			var latitude = document.getElementById("latitude").value;
			var longitude = document.getElementById("longitude").value;

			if ((latitude == 0 || longitude == 0)) {
				BootstrapDialog
						.alert('La position n\'est pas correcte. Veuillez ressaisir votre adresse');
				return false;
			}

			diffHeure = heureDiff(new Date(datedebut).getTime(), new Date(
					datefin).getTime());
			// Condition Ã  rajouter pour le nbr d'heure max de l'activitÃ©

			return true;
		}

		function initPosition() {
			document.getElementById("latitude").value = 0;
			document.getElementById("longitude").value = 0;

		}
	</script>
</body>
</html>
