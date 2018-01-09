<%@page import="website.metier.Outils"%>
<%@page import="website.metier.AuthentificationSite"%>

<!DOCTYPE html>
<html>
  <head>
 
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>Simple Map</title>
    <meta name="viewport" content="initial-scale=1.0">
    <meta charset="utf-8">
    
    
    <style>
      /* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
      #map {
        height: 100%;
      }
      /* Optional: Makes the sample page fill the window. */
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
    </style>
  </head>
  <body>
  
  <%@ include file="menu.jsp"%>

	<%
	AuthentificationSite authentification=	new AuthentificationSite(request, response);
	if (!authentification.isAuthentifieAdmin())
	return;
	ProfilBean profil=authentification.getProfil();
	
	%>
    <div id="map"></div>
   <script>
		var encours = 0;
		var var_map;
		var markersMap = [];

		function init_map() {
			//	var var_location = new google.maps.LatLng(45.430817,12.331516);
			var var_location = new google.maps.LatLng(<%=profil.getLatitudeFixe()%>,<%=profil.getLongitudeFixe()%>);
		
			var var_mapoptions = {zoom : 5};

			
			
			var_map = new google.maps.Map(document.getElementById('map'), {
		          center: {lat: -34.397, lng: 150.644},
		          zoom: 5
		        });
			
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
					map : var_map,
					title : activite.titre
				});
				var_marker.setMap(var_map);
				markersMap.push(var_marker);

				//	var info = getInfoWindow("titre", "contenu", "lien", "");
				var lien = "DetailActivite?idactivite=" + activite.id;
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
	<!--	google.maps.event.addDomListener(window, 'load', init_map);-->
	</script>
    <script src="https://maps.googleapis.com/maps/api/js?key=<%=Outils.getCleMap()%>&callback=init_map"
    async defer></script>
    
    	<script>
		function getContent(titre, contenu, lien, cree) {

			var contentString = '<div id="content">' + '<div id="siteNotice">'
					+ '</div>' + '<h1 id="firstHeading" class="firstHeading">'
					+ titre + '</h1>' + '<div id="bodyContent">' + '<p>'
					+ contenu + '</p>' + '<p><a href='+lien+'>'
					+ 'Consulter</a> ' + '' + cree + '</p>' + '</div>'
					+ '</div>';

			return contentString;

		}
	</script>
  </body>
</html>