
<%@page import="website.metier.TypeSignalement"%>
<%@page import="website.pager.PagerActiviteBean"%>
<%@page import="website.dao.CacheValueDAO"%>
<%@page import="website.metier.admin.FitreAdminActivites"%>
<%@page import="website.metier.admin.FiltreJSP"%>
<%@page import="website.metier.TypeActiviteBean"%>
<%@page import="website.metier.ActiviteBean"%>
<%@page import="website.metier.Pagination"%>
<%@page import="website.metier.Outils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="website.metier.TypeUser"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Liste activités</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

	<%@ include file="menu.jsp"%>

	<%
		FitreAdminActivites filtre=(FitreAdminActivites)session.getAttribute("filtreActivite");
			ArrayList<TypeActiviteBean> listTypeActiviteBean=CacheValueDAO.getListTypeActiviteToutes();
			ArrayList<TypeUser> listTypeUser=CacheValueDAO.getListTypeUser();
			ArrayList<TypeSignalement> listTypeSignalement=CacheValueDAO.getListTypeSignalementActivite();
			PagerActiviteBean pager=(PagerActiviteBean) request
			.getAttribute("pager");
			ArrayList<ActiviteBean> listActivite = pager.getListActivite();
	%>




	<div class="container">

		<h3>Critéres</h3>


		<form class="form-inline" method="post" action="ListActivite">

			<div class="form-group">
				<label for="autocomplete">Ville:</label> <input
					placeholder="Enter your address" onFocus="geolocate()" type="text"
					class="form-control" id="autocomplete" name="autocomplete"
					value="<%=filtre.getVille()%>"> <input type="hidden"
					step="any" class="form-control" id="latitude" name="latitude"
					value="<%=filtre.getLatitude()%>"> <input type="hidden"
					step="any" class="form-control" id="longitude" name="longitude"
					value="<%=filtre.getLongitude()%>"> <label for="rayon">Rayon:</label>
				<input type="number" class="form-control" id="rayon" name="rayon"
					value="<%=filtre.getRayon()%>">

				<div class="form-group">
					<label for="typeUser">Type</label> <select data-style="btn-primary"
						class="form-control" id="typeUser" name="typeUser">

						<%
							for (TypeUser typeuser:listTypeUser) {
						%>
						<option value="<%=typeuser.getId()%>"
							<%=Outils.jspAdapterListSelected(typeuser.getId(), filtre.getTypeUser())%>>
							<%=typeuser.getLibelle()%></option>
						<%
							}
						%>

					</select>
				</div>


				<div class="form-group">
					<label for="typeactivite">Cat.</label> <select
						data-style="btn-primary" class="form-control" id="typeactivite"
						name="typeactivite">

						<%
							for (TypeActiviteBean typeActivite:listTypeActiviteBean) {
						%>
						<option value="<%=typeActivite.getId()%>"
							<%=Outils.jspAdapterListSelected(typeActivite.getId(), filtre.getTypeactivite())%>>
							<%=typeActivite.getLibelle()%></option>
						<%
							}
						%>

					</select>
				</div>
				
				<div class="form-group">
					<label for="typeSignalement">Signalement</label> <select
						data-style="btn-primary" class="form-control" id="typeSignalement"
						name="typeSignalement">

						<%
							for (TypeSignalement typeSignalement:listTypeSignalement) {
						%>
						<option value="<%=typeSignalement.getId()%>"
							<%=Outils.jspAdapterListSelected(typeSignalement.getId(), filtre.getTypeSignalement())%>>
							<%=typeSignalement.getLibelle()%></option>
						<%
							}
						%>

					</select>
				</div>



				<button id="go" type="submit" class="btn btn-default"
					name="rechercheactivite">Rechercher</button>

			</div>
		</form>

	</div>
	<div class="container">
		<h2>Liste activites</h2>

		<table class="table table-striped">
			<thead>
				<tr>
					<th style="width: 10%;" class="text-center">User</th>
					<th>Titre</th>
					<th>Organisateur</th>
					<th>Date fin</th>
					<th>Active</th>
					<th>Nbr Signalement</th>
				</tr>
			</thead>
			<tbody>
				<%
					if (listActivite!=null)
					for (ActiviteBean activite : listActivite) {
					String lien = "DetailActivite?idactivite=" + activite.getId()+"&from=listActivite.jsp";
				%>

				<tr>
				<td><%=activite.getTypeUserHTML() %>
					<td><a href=<%out.println(lien);%>> <%=activite.getTitre()%></a></td>
					<td><%=activite.getPseudo()%></td>
					<td><%=activite.getDatefinStr()%></td>
					<td><%=activite.isActiveStr()%></td>
					<td><%=activite.getNbrSignalement()%></td>

				</tr>

				<%
					}
				%>

			</tbody>
		</table>
	</div>



	<ul class="pager">

		<li <%=pager.isPreviousHtml()%>><a
			href="<%=pager.getLienPrevioustHtml()%>">Previous</a></li>
		<li>Page N° <%=pager.getPageEnCours()%></li>
		<li <%=pager.isNextHtml()%>><a
			href="<%=pager.getLienNextHtml()%>">Next</a></li>


	</ul>
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
			(document.getElementById('autocomplete')), {
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

</body>

</html>