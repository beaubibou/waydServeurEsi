<%@page import="website.metier.PhotoActiviteBean"%>
<%@page import="website.html.OutilsHtml"%>
<%@page import="texthtml.pro.Erreur_HTML"%>
<%@page import="texthtml.pro.ModifierActiviteText"%>
<%@page import="website.html.DateHtlm"%>
<%@page import="website.metier.ActiviteBean"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="website.metier.ProfilBean"%>
<%@page import="website.metier.TypeActiviteBean"%>
<%@page import="website.metier.TypeAccess"%>
<%@page import="website.metier.Outils"%>
<%@page import="website.metier.AuthentificationSite"%>
<%@page import="website.dao.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="texthtml.pro.CompteProText"%>
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


<script src="js/moment.js"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css"
	rel="stylesheet" type="text/css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<link href="/wayd/css/style.css" rel="stylesheet" type="text/css">
<link href="/wayd/css/nbrcaractere.css" rel="stylesheet" media="all"
	type="text/css">
<link href="/wayd/css/styleWayd.css" rel="stylesheet" type="text/css" />


</head>
<body>

	<%
		AuthentificationSite authentification=	new AuthentificationSite(request, response);
			if (!authentification.isAuthentifiePro())
		return;
			
		ArrayList<TypeActiviteBean> listTypeActivite=CacheValueDAO.getListTypeActivitePro();
		ActiviteBean activite=(ActiviteBean)request.getAttribute("activite");
		ArrayList<PhotoActiviteBean> listPhoto=(ArrayList<PhotoActiviteBean>)request.getAttribute("listPhoto");
		MenuEnum etatMenu=null;
		//ArrayList<TypeAccess> listTypeAccess=CacheValueDAO.getListTypeAccess();
	%>
	<%@ include file="menu.jsp"%>

	<div class="container">
		<div id="loginbox"
			class="mainbox col-md-8 col-md-offset-2 col-sm-8 margedebut">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">
					<div class="panel-title">Modifier votre activité</div>
				</div>

				<div style="padding-top: 30px" class="panel-body">
					<div class="form-group" style="border-bottom: 1px solid #888;">

						<p class="text-tuto"><%=ModifierActiviteText.MESSAGE_JUMBO_LIGNE1%></p>
						<p class="text-tuto"><%=ModifierActiviteText.MESSAGE_JUMBO_LIGNE2%></p>

					</div>
					<div class="form-group">

						<form
							action="/wayd/AjoutePhotoActivite?idActivite=<%=activite.getId()%>"
							method="post" enctype="multipart/form-data"
							onsubmit="return valideFichier()">
							<input type="file" name="file" size="50" id="file" /> <br>
							<input type="submit" value="Envoyer la photo"
								class="btn btnwayd btn-sm" />

						</form>
					</div>



					<form action="/wayd/UpdateActivitePro"
						onsubmit="return valideFormulaire()" method="post">

						<br>

						<div class="form-group">
							<label for="titre"><%=ModifierActiviteText.LABEL_TITRE%></label>
							<input type="text" maxlength="50" class="form-control" id="titre"
								required placeholder="<%=ModifierActiviteText.HINT_TITRE%>"
								name="titre" value=<%=activite.getTitre()%>>
						</div>


						<div class="form-group">
							<div class="row">

								<div class='col-sm-4'>
									<div class="form-group">
										<label for="iddatedebut"><%=ModifierActiviteText.LABEL_DATE_DEBUT%></label>
										<div class='input-group date' id='datedebut'>
											<input readonly type='text' class="form-control" id="iddatedebut"
												name="debut" /> <span class="input-group-addon"> <span
												class="glyphicon glyphicon-calendar"></span>
											</span>
										</div>
									</div>
								</div>

								<div class='col-sm-4'>
									<div class="form-group">
										<label for="iddatefin"><%=ModifierActiviteText.LABEL_DATE_FIN%></label>
										<div class='input-group date' id="datefin">
											<input readonly type='text' class="form-control" id="iddatefin"
												name="fin" /> <span class="input-group-addon"> <span
												class="glyphicon glyphicon-calendar"></span>
											</span>
										</div>
									</div>
								</div>
								<div class='col-sm-4'>
									<label for="typeactivite"><%=ModifierActiviteText.LABEL_TYPE_ACTIVITE%></label>
									<select class="form-control" id="type" name="typeactivite">
										<%
											for (TypeActiviteBean typeactivite:listTypeActivite) {
										%>

										<option value="<%=typeactivite.id%>"
											<%=Outils.jspAdapterListSelected(typeactivite.getId(), activite.getTypeactivite())%>><%=typeactivite.getLibelle()%></option>


										<%
											}
										%>
									</select>

								</div>
							</div>

						</div>

						<div class="form-group">
							<label for="adresse"><%=ModifierActiviteText.LABEL_ADRESSE%></label>
							<input type="text" class="form-control" id="adresse" required
								value="<%=activite.getAdresse()%>" name="adresse"
								onkeypress="initPosition()">
						</div>

						<div class="form-group">
							<label for="description"><%=ModifierActiviteText.LABEL_DESCRIPTION_ACTIVITE%></label>
							<textarea
								maxlength="<%=ModifierActiviteText.TAILLE_DESCRIPTION_ACTIVITE_MAX%>"
								class="form-control" rows="5" id="description"
								name="description"
								placeholder="<%=ModifierActiviteText.getHintDescriptionActivite()%>"><%=OutilsHtml.convertStringHtml(activite.getLibelle())%></textarea>
						</div>
						<h5 class="nbrcaracteremax" id="nbr">

							<%=ModifierActiviteText.initNbrCaracteres()%></h5>


						<button type="submit" class="btn btnwayd">Enregistrer</button>

						<div class="form-group">

							<input type="hidden" class="form-control" id="latitude"
								placeholder="latitude" name="latitude"
								value=<%=activite.getLatitude()%>>
						</div>
						<div class="form-group">

							<input type="hidden" class="form-control" id="longitude"
								placeholder="longitude" name="longitude"
								value=<%=activite.getLongitude()%>>
						</div>
						<input type="hidden" name="idActivite"
							value="<%=activite.getId()%>">

						<div class="form-group"></div>

					</form>



				</div>
				<%
					if (listPhoto!=null && !listPhoto.isEmpty())
				    {
				%>

				<h2>Vos photos</h2>
				<div id="myCarousel" class="carousel slide" data-ride="carousel">
					
    <ol class="carousel-indicators">
      <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
     
     <%if (listPhoto.size()>0)
    	 for (int f=1;f<listPhoto.size();f++){
    	 %>
     			
      <li data-target="#myCarousel" data-slide-to="<%=f%>"></li>
    <%} %>
    </ol>
 
					<!-- Wrapper for slides -->
					<div class="carousel-inner">


						<div class="item active">

							<img src=<%=Outils.getUrlPhoto(listPhoto.get(0).getPhoto())%>
								style="width: 100%;">

							<div align="center">
								<a href='<%=listPhoto.get(0).getLienSuppression()%>'
									class="btn btn-alert "> <span
									class="glyphicon glyphicon-trash">Supprimer</span>
								</a>

							</div>

						</div>

						<%
							for (int f=1;f<listPhoto.size();f++) {
						%>
						<div class="item">
							<img src=<%=Outils.getUrlPhoto(listPhoto.get(f).getPhoto())%>
								style="width: 100%;" >
						
						<div align="center">
								<a href='<%=listPhoto.get(f).getLienSuppression()%>'
									class="btn btn-alert "> <span
									class="glyphicon glyphicon-trash">Supprimer</span>
								</a>

							</div>

						</div>

						<%
							}
						%>


						<!-- Left and right controls -->
						<a class="left carousel-control" href="#myCarousel"
							data-slide="prev"> <span
							class="glyphicon glyphicon-chevron-left"></span> <span
							class="sr-only">Previous</span>
						</a> <a class="right carousel-control" href="#myCarousel"
							data-slide="next"> <span
							class="glyphicon glyphicon-chevron-right"></span> <span
							class="sr-only">Next</span>
						</a>
					</div>
				</div>
				<%
					}
				%>
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

			var dateDebut =
	<%=DateHtlm.getDateHtml(activite.getDatedebut())%>
		;
			var dateFin =
	<%=DateHtlm.getDateHtml(activite.getDatefin())%>
		;

			$('#datedebut').datetimepicker({
				defaultDate : dateDebut,
				format : 'DD/MM/YYYY HH:mm'

			});

			$('#datefin').datetimepicker({
				defaultDate : dateFin,
				format : 'DD/MM/YYYY HH:mm'

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
				alert();
				BootstrapDialog
						.alert("<%=ModifierActiviteText.ALERT_GPS_NO_POSITION%>");
				return false;
			}

			if (datedebut > datefin) {
				alert("<%=Erreur_HTML.DATEDEBUT_SUP_DATEFIN%>");
				return false;
			}
			if (datefin < new Date()) {
				alert("<%=Erreur_HTML.DATEFIN_INF_NOW%>");
				return false;
			}

			diffHeure = heureDiff(new Date(datedebut).getTime(), new Date(
					datefin).getTime());
			// Condition Ã  rajouter pour le nbr d'heure max de l'activitÃ©

			if (diffHeure > 8) {
				alert("<%=Erreur_HTML.DUREE_PAS_SUPERIEUR_A%>");
				return false;
			}

			if (diffHeure < 1) {
				alert("<%=Erreur_HTML.DUREE_PAS_INFERIEURE_A%>");
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
		
		function valideFichier(){
			
			var monfichier;
			var nbrActivite;
			nbrActivite=<%=ActiviteDAO.getNbrPhoto(activite.getId())%>;
			monfichier=latitude = document.getElementById("file").value;
			
		if (monfichier==''){
			BootstrapDialog
			.alert("<%=ModifierActiviteText.AUCUN_FICHIER_SELECTIONNE%>");
		return false;
		}
				
		
		if (nbrActivite==3){
			
			BootstrapDialog
			.alert("<%=ModifierActiviteText.NBR_MESSAGE_IMAGE_MAX%>");
		return false;
			
		}
		return true;
		}
	</script>

		<script>
	$(document).ready(function(e) {

		$('#description').keyup(function() {

			var nombreCaractere = $(this).val().length;
			//alert(nombreCaractere);

			var msg = nombreCaractere + '<%=ModifierActiviteText.getNbrCarateresDescription()%>';

			$('#nbr').text(msg);
			// Le script qui devra calculer et afficher le nombre de mots et de caractères

		})

	});

	// Init le nombre de caraterces	
	var nombreCaractere = $('#description').val().length;
	var msg = nombreCaractere +   '<%=ModifierActiviteText.getNbrCarateresDescription()%>';
			$('#nbr').text(msg);
		</script>
</body>
</html>
