<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link type="text/css" rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Roboto:300,400,500">
<p id="lat">popo</p>

<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places"></script>

<script>
	var autocomplete;
	function initialize() {
		autocomplete = new google.maps.places.Autocomplete(
		/** @type {HTMLInputElement} */
		(document.getElementById('autocomplete')), {
			types : [ 'geocode' ]
		});

		google.maps.event.addListener(autocomplete, 'place_changed',
				function() {

					document.getElementById('lat').innerHTML = autocomplete
							.getPlace().geometry.location.lat();

				});
	}
</script>
</head>
<body onload="initialize()">
	<div id="locationField">
		<input id="autocomplete" placeholder="Enter your address"
			onFocus="geolocate()" type="text"></input>
	</div>
</body>
</html>
