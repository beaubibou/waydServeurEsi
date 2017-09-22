
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
<title>Mes activités</title>
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
		
		<div class="container">

  <button type="button" class="btn btn-info" data-toggle="collapse" data-target="#demo">Critéres</button>
  <div id="demo" class="collapse">
   
<div class="container">
     <form action="/action_page.php" onsubmit="return valideFormulaire()">
  
    <div class="form-group">
      <label for="adresse">Adresse:</label>
      <input type="text" class="form-control" id="adresse" placeholder="Renseigner l'adresse" name="adresse" required>
    </div>
    
   <div class="form-group">
    <div class="row">
		 
		   <div class='col-sm-2'>
            <div class="form-group">
		 <label for="Mot clés">Titre:</label>
      <input type="text" class="form-control" id="motcle" placeholder="motcle" name="motcle" required>
            </div>
        </div>
		  <div class='col-sm-2'>
            <div class="form-group">
			 <label for="acces">Acces</label>
    
      <select class="form-control" id="maxwaydeur" name="maxwaydeur">
        <option value="1">Gratuit</option>
        <option value="2">Payant</option>
        <option value="2">Tous</option>
              
        </select>
            </div>
        </div>
    
         <div class='col-sm-2'>
		<div class="form-group">
		<label for="duree">Type:</label>
    	<select class="form-control" id="typeactivite" name="typeactivite">
        <option value="1">Sport</option>
        <option value="2">Tennis</option>
        <option value="2">Tous</option>
      	</select>       
        </div>
		</div>
		
		 <div class='col-sm-2'>
		<div class="form-group">
		<label for="duree">Propsé par:</label>
    	<select class="form-control" id="typeactivite" name="typeactivite">
        <option value="1">Professionnel</option>
        <option value="2">Associatio</option>
        <option value="3">Waydeur</option>
        <option value="4">Tous</option>
      	</select>       
        </div>
		</div>
		
		 <div class='col-sm-2'>
		<div class="form-group">
		<label for="duree">Quand</label>
    	<select class="form-control" id="typeactivite" name="typeactivite">
        <option value="0">Maitenant</option>
        <option value="1">1heure</option>
        <option value="2">2Heurs</option>
        <option value="3">Tous</option>
      	</select>       
        </div>
        
		</div>
		
		 <div class='col-sm-2'>
		<div class="form-group">
		<label for="duree">Rayon</label>
    	<select class="form-control" id="typeactivite" name="typeactivite">
        <option value="0">1Km</option>
        <option value="1">2Km</option>
        <option value="2">3km</option>
        <option value="3">4km</option>
      	</select>       
        </div>
        
		</div>
     
     
   </div>
</div>
    
    <button type="submit" class="btn btn-default">Enregistrer</button>
    
     <div class="form-group">
   
      <input type="hidden" class="form-control" id="latitude" placeholder="Renseigner l'adresse" name="latitude">
    </div>
     <div class="form-group">

      <input type="hidden" class="form-control" id="longitude" placeholder="Renseigner l'adresse" name="longitude">
    </div>
    
   
  </form>
</div>
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
									ArrayList<ActiviteBean> listMesActivite = (ArrayList<ActiviteBean>) request.getAttribute("listMesActivite");
								    
								     if (listMesActivite!=null)
									for (ActiviteBean activite : listMesActivite) {
										String lienEfface = "/wayd/SupprimeActiviteWaydeur?idactivite=" + activite.getId();
										String lienConfirmDialog="/wayd/ConfirmDialog?idactivite=" + activite.getId()+"&action=effaceActivite&from=MesActivites";
										String lienDetail = "DetailActivite?idactivite=" + activite.getId()+"&from=listActivite.jsp";
								%>

								<tr>
									<td>John</td>
									<td><textarea class="form-control" disabled rows="2"
											id="comment"><%=activite.getLibelle()%></textarea></td>

									<td><span class="badge">10</span></td>
									<td><%=activite.getEtat()%></td>

									<td><a href="#" class="btn btn-success btn-sm"> <span
											class="glyphicon glyphicon-search"></span>
									</a> 
									
									<a href="#" class="btn btn-info btn-sm"> <span
											class="glyphicon glyphicon-edit"></span>
									</a> 
									
									<button  id=<%out.println(lienEfface);%> name="supprimer" type="button" class="btn btn-primary btn-sm">
									<span class="glyphicon glyphicon-remove"></span> 
									</button>
		
									
									
									
									
									
									</td>

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
			location.href=lien;
		}
	</script>


</body>
</html>
