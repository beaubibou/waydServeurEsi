
<%@page import="website.enumeration.AlertJsp"%>
<%@page import="website.html.AlertInfoJsp"%>
<%@page import="website.metier.QuandBean"%>
<%@page import="website.metier.FiltreRecherche"%>
<%@page import="website.metier.FiltreJSP"%>
<%@page import="website.metier.TypeActiviteBean"%>
<%@page import="website.metier.TypeAccess"%>
<%@page import="website.metier.TypeUser"%>
<%@page import="website.metier.ActiviteBean"%>
<%@page import="website.metier.ProfilBean"%>
<%@page import="website.metier.RayonBean"%>
<%@page import="website.metier.AuthentificationSite"%>
<%@page import="website.metier.Pagination"%>
<%@page import="java.util.ArrayList"%>
<%@page import="website.metier.Outils"%>
<%@page import="website.dao.CacheValueDAO"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Recherche activités</title>
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


</head>
<body>
	<%@ include file="menuWaydeur.jsp"%>
	<%
		AuthentificationSite authentification=	new AuthentificationSite(request, response);
			if (!authentification.isAuthentifieWaydeur())
		return;
			
			FiltreRecherche filtre=authentification.getFiltre();
		
		ArrayList<TypeActiviteBean> listTypeActivite = CacheValueDAO
			.getListTypeActiviteToutes();
			ArrayList<TypeAccess> listTypeAccess = CacheValueDAO
			.getListTypeAccess();
			ArrayList<TypeUser> listTypeUser = CacheValueDAO.getListTypeUser();
			ArrayList<QuandBean> listQuand = CacheValueDAO.getListQuand();
			ArrayList<RayonBean> listRayon = CacheValueDAO.getListRayon();
	%>


	<div class="container">


		<div class="panel panel-default">
			<div class="panel-heading">


				<form action="RechercheWaydeur" method="post">

					<div class="form-group">
						<label for="adresse">Adresse:</label> <input type="text"
							class="form-control" id="adresse"
							placeholder="Renseigner l'adresse" name="adresse" required
							value="<%=filtre.getAdresse()%>">
					</div>


					<div class="form-group">
						<div class="row">

							<div class='col-sm-2'>
								<div class="form-group">
									<label for="Mot clés">Titre:</label> <input type="text"
										class="form-control" id="motcle" placeholder="motcle"
										name="motcle" value=<%=filtre.getMotCle()%>>
								</div>
							</div>


							<div class='col-sm-2'>
								<div class="form-group">
									<label for="duree">Type:</label> <select class="form-control"
										id="typeactivite" name="typeactivite">

										<%
											for (TypeActiviteBean typeactivite:listTypeActivite){
										%>
										<option value="<%=typeactivite.id%>"
											<%=Outils.jspAdapterListSelected(typeactivite.getId(), filtre.getTypeActivite())%>><%=typeactivite.getLibelle()%></option>
										<%
											}
										%>

									</select>
								</div>
							</div>

							<div class='col-sm-2'>
								<div class="form-group">
									<label for="duree">Propsé par:</label> <select
										class="form-control" id="idtypeuser" name="typeuser">


										<%
											for (TypeUser typeUser:listTypeUser){
										%>
										<option value="<%=typeUser.getId()%>"
											<%=Outils.jspAdapterListSelected(typeUser.getId(), filtre.getTyperUser())%>>
											<%=typeUser.getLibelle()%></option>
										<%
											}
										%>

									</select>
								</div>
							</div>

							<div class='col-sm-2'>
								<div class="form-group">
									<label for="duree">Quand</label> <select class="form-control"
										id="idquand" name="commence">
										<%
											for (QuandBean quand:listQuand){
										%>
										<option value="<%=quand.getValue()%>"
											<%=Outils.jspAdapterListSelected(quand.getValue(), filtre.getQuand())%>>
											<%=quand.getLibelle()%></option>

										<%
											}
										%>



									</select>
								</div>

							</div>

							<div class='col-sm-2'>
								<div class="form-group">
									<label for="duree">Rayon</label> <select class="form-control"
										id="idrayon" name="rayon">
										<%
											for (RayonBean rayon:listRayon){
										%>
										<option value="<%=rayon.getValue()%>"
											<%=Outils.jspAdapterListSelected(rayon.getValue(), filtre.getRayon())%>>
											<%=rayon.getLibelle()%></option>
										<%
											}
										%>
									</select>
								</div>

							</div>


						</div>
					</div>

					<button type="submit" class="btn btn-default">Rechercher</button>

					<div class="form-group">

						<input type="hidden" class="form-control" id="latitude"
							name="latitude" value="<%=filtre.getLatitude()%>">
					</div>
					<div class="form-group">
						<input type="hidden" class="form-control" id="longitude"
							name="longitude" value="<%=filtre.getLongitude()%>">
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
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD2TO9-HtrUmagi0JTZn6YSN0QLbsoVkTg&libraries=places&callback=initAutocomplete"
		async defer></script>







	<div class="container">
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-12  col-sm-8">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">
					<div class="panel-title">Liste de vos activités</div>
				</div>
				<div style="padding-top: 30px" class="panel-body">

					<div class="table-responsive">
						<table class="table table-condensed">
							<thead>
								<tr>
									<th>Titre</th>
									<th>Description</th>
									<th>Vues</th>
									<th>Etat</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>

								<%
									ArrayList<ActiviteBean> listActivite = (ArrayList<ActiviteBean>) request.getAttribute("listActivite");
									for (ActiviteBean activite : listActivite) {
									String lienEfface = "/wayd/SupprimeActiviteWaydeur?idactivite=" + activite.getId();
									String lienConfirmDialog="/wayd/ConfirmDialog?idactivite=" + activite.getId()+"&action=effaceActivite&from=MesActivites";
									String lienDetail = "/wayd/DetailActiviteSite?idactivite=" + activite.getId()+"&from=listActivite.jsp";
								%>

								<tr>
									<td><%=activite.getTitre()%></td>
									<td><textarea class="form-control" disabled rows="2"
											id="comment"><%=activite.getLibelle()%></textarea></td>

									<td><span class="badge">10</span></td>
									<td><%=activite.getEtat()%></td>

									<td><a href=" <%=lienDetail%>>"
										class="btn btn-success btn-sm"> <span
											class="glyphicon glyphicon-search"></span>
									</a></td>

								</tr>
								<%
									}
								%>


							</tbody>
						</table>

					</div>

				</div>
			</div>
		</div>
	</div>

	<script>
		$(function() {

			$('button').click(function() {

				var lien = $(this).attr('id');
				var action = $(this).attr('name')
				if (action == 'supprimer')
					DialogEffaceActivite(lien);

			});
		});
	</script>

	<script>
		function DialogEffaceActivite(lien) {

			BootstrapDialog.show({
				title : 'Efface activité',
				message : 'Confirmez',
				buttons : [ {
					label : 'Oui',
					action : function(dialog) {
						effaceActivite(lien);
						dialog.close();
					}
				}, {
					label : 'Annuler',
					action : function(dialog) {
						dialog.close();
					}
				} ]
			});

		}

		function effaceActivite(lien) {
			location.href = lien;
		}
	</script>


</body>
</html>
