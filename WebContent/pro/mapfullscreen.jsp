<%@page import="website.metier.ActiviteAjax"%>
<%@page import="website.metier.ProfilBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<title>Bootstrap 3 with Google Map</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Bootstrap core CSS -->
<link
	href="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.1.1/css/bootstrap.css"
	rel="stylesheet" media="screen">

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="http://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7/html5shiv.js"></script>
      <script src="http://cdnjs.cloudflare.com/ajax/libs/respond.js/1.3.0/respond.js"></script>
    <![endif]-->

<style>
#map-container {
	height: 100vh
}
</style>
</head>
<body>
	<%@ include file="menu.jsp"%>

	<%
		ProfilBean profil = (ProfilBean) session.getAttribute("profil");
		//	ArrayList<ActiviteAjax> listMesActivite = (ArrayList<ActiviteAjax>) request.getAttribute("listMesActivite");
		//	System.out.println("nbr actié"+listMesActivite.size());
	%>


	<div id="map-container" class="col-sm-5 col-md-6 col-lg-12"
		style="background-color: white;"></div>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script
		src="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.1.1/js/bootstrap.min.js"></script>
	<script src="http://maps.google.com/maps/api/js?sensor=false"></script>
	<script>
		var encours = 0;
		var var_map;
		var markersMap = [];

		function init_map() {
			//	var var_location = new google.maps.LatLng(45.430817,12.331516);
			var var_location = new google.maps.LatLng(
	<%=profil.getLatitude()%>
		,
	<%=profil.getLongitude()%>
		);

			var var_mapoptions = {

				zoom : 5
			};

			var_map = new google.maps.Map(document
					.getElementById("map-container"), var_mapoptions);
			var_map.addListener('center_changed', function() {
				// 3 seconds after the center of the map has changed, pan back to the
				// marker.

				if (encours == 0) {
					encours = 1;
					window.setTimeout(function() {

						var centre = var_map.getCenter();
						lat = centre.lat();
						lon = centre.lng();
						var bounds = var_map.getBounds();
						latNE = bounds.getNorthEast().lat();
						lonNE = bounds.getNorthEast().lng();
						latSW = bounds.getSouthWest().lat();
						lonSW = bounds.getSouthWest().lng();

						$.get("ListMapActivite?latitude=" + lat + "&longitude="
								+ lon + "&boundNElat=" + latNE + "&boundNElon="
								+ lonNE + "&boundSWlat=" + latSW
								+ "&boundSWlon=" + lonSW,
								function(responseJson) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
									clearMap();
									encours = 0;
									afficheMarker(responseJson);
								});
					}, 1000);

				}
			});

			// 		On  à poser un listener sur le changement de centrer - CE changement le déclence
			var_map.setCenter(var_location);

		}

		function afficheMarkerPosition(latitude, longitude) {

			positionItem = new google.maps.LatLng(latitude, longitude);
			var var_marker = new google.maps.Marker({
				position : positionItem,
				map : var_map
			//	,title : "Venice"
			});
			var_marker.setMap(var_map);
			markersMap.push(var_marker);

			//	var info = getInfoWindow("titre", "contenu", "lien", "cree");

			var contentString = getContent("titre", "contenu", "lien", "cree");
			var info = new google.maps.InfoWindow({
				content : contentString
			});

			var_marker.addListener('click', function() {
				info.open(var_map, var_marker);
			});

		}
		function afficheMarker(reponseJson) {

			$.each(reponseJson, function(index, activite) { // Iterate over the JSON array.

				positionItem = new google.maps.LatLng(activite.latitude,
						activite.longitude);
				var var_marker = new google.maps.Marker({
					position : positionItem,
					map : var_map
					,title : activite.titre
				});
				var_marker.setMap(var_map);
				markersMap.push(var_marker);

				//	var info = getInfoWindow("titre", "contenu", "lien", "");
				var lien="DetailActiviteSite?idactivite="+activite.id;
				var contentString = getContent(activite.titre,
						activite.libelle, lien, "");
				var info = new google.maps.InfoWindow({
					content : contentString
				});

				var_marker.addListener('click', function() {
					info.open(var_map, var_marker);
				});

			});
		}

		function clearMap() {

			$.each(markersMap, function() {
				this.setMap(null);
			});

		}
		google.maps.event.addDomListener(window, 'load', init_map);
	</script>
	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD2TO9-HtrUmagi0JTZn6YSN0QLbsoVkTg&callback=initMap">
		
	</script>

	<script>
		function getContent(titre, contenu, lien, cree) {

			var contentString = '<div id="content">' + '<div id="siteNotice">'
					+ '</div>' + '<h1 id="firstHeading" class="firstHeading">'
					+ titre + '</h1>' + '<div id="bodyContent">' + '<p>'
					+ contenu + '</p>' + '<p><a href='+lien+'>'
					+ 'Consulter</a> ' + '' + cree + '</p>'
					+ '</div>' + '</div>';

			return contentString;

		}
	</script>
</body>
</html>
