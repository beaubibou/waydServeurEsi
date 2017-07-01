<%@page import="website.metier.FiltreJSP"%>
<%@page import="website.metier.TypeActiviteBean"%>
<%@page import="website.metier.ActiviteBean"%>
<%@page import="website.metier.Pagination"%>
<%@page import="java.util.ArrayList"%>
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
		int rayon=10000000;
	   	String ville="";
	   	double latitude=0,longitude=0;
	   
	   	FiltreJSP filtre=(FiltreJSP)session.getAttribute("filtre");
	   	
	   	if (filtre!=null){
	   	 	rayon=filtre.getRayon();
	   		latitude=filtre.getLatitude();
	   		longitude=filtre.getLongitude();
	   		ville=filtre.getVille();
	   	}
	%>


	<div class="container">

		<h3>Critéres</h3>


		<form class="form-inline" method="post" action="ListActivite">

			<div class="form-group">
				<label for="autocomplete">Ville:</label> <input
					placeholder="Enter your address" onFocus="geolocate()" type="text"
					class="form-control" id="autocomplete" name="autocomplete"
					value="<%=ville%>"> <input type="hidden" step="any"
					class="form-control" id="latitude" name="latitude"
					value="<%=latitude%>"> <input type="hidden" step="any"
					class="form-control" id="longitude" name="longitude"
					value="<%=longitude%>"> <label for="rayon">Rayon:</label>
				<input type="number" class="form-control" id="rayon" name="rayon"
					value="<%=rayon%>"> <label for="sel1">Type
					activité</label> <select class="form-control" id="sel1" name="typeactivite">

					<option value="-1">Toutes</option>
					<%
						ArrayList<TypeActiviteBean> listTypeActivite = (ArrayList<TypeActiviteBean>) request
										.getAttribute("listtypeactivite");
												for (TypeActiviteBean typeactivite:listTypeActivite){
					%>
					<option value="<%=typeactivite.getId()%>"
						id="<%=typeactivite.getId()%>">
						<%=typeactivite.getLibelle()%>
					</option>

					<%
						}
					%>
				</select>
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
					<th>Titre</th>
					<th>Organisateur</th>
					<th>Date fin</th>
					<th>Active</th>
				</tr>
			</thead>
			<tbody>
				<%
					ArrayList<ActiviteBean> listActivite = (ArrayList<ActiviteBean>) request
																	.getAttribute("listActivite");
										
								if (listActivite!=null)
									for (ActiviteBean activite : listActivite) {
											String lien = "DetailActivite?idactivite=" + activite.getId()+"&from=listActivite.jsp";
				%>

				<tr>
					<td><a href=<%out.println(lien);%>> <%=activite.getTitre()%></a></td>
					<td><%=activite.getPseudo()%></td>
					<td><%=activite.getDatefinStr()%></td>
					<td><%=activite.isActiveStr()%></td>

				</tr>

				<%
					}
				%>

			</tbody>
		</table>
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
	<%
		if (filtre!=null) {
	%>

	<script type="text/javascript">
document.getElementById("<%=filtre.getTypeactivite()%>").selected = true;
	</script>
	<%
		}
	%>

</body>
<footer class="container-fluid bg-4 text-center">
	<ul class="pagination">

		<%
			Pagination pagination = (Pagination) request
					.getAttribute("pagination");
			if (pagination != null) {
				for (String numpage : pagination.getPagination()) {
		%>
		<li><a href=ListActivite?pageAafficher=<%=numpage%>><%=numpage%></a></li>
		
		<%
			}
			}
		%>

	</ul>
</footer>
</html>