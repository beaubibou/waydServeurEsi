
<%@page import="website.metier.TypeActiveActivite"%>
<%@page import="website.metier.TypeGratuitActivite"%>
<%@page import="website.metier.TypeSignalement"%>
<%@page import="website.metier.TypeEtatActivite"%>
<%@page import="website.pager.PagerActiviteBean"%>
<%@page import="website.dao.CacheValueDAO"%>
<%@page import="website.metier.admin.FitreAdminActivites"%>
<%@page import="website.metier.admin.FiltreJSP"%>
<%@page import="website.metier.TypeActiviteBean"%>
<%@page import="website.metier.ActiviteBean"%>
<%@page import="website.metier.Outils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="website.metier.TypeUser"%>
<%@page import="website.metier.AuthentificationSite"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
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
<link href="/wayd/css/styleWaydAdmin.css" rel="stylesheet"
	type="text/css">
	<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="js/moment.js"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css"
	rel="stylesheet" type="text/css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
	<link href="/wayd/css/styleWaydAdmin.css" rel="stylesheet"
	type="text/css">
</head>

<body>

	<%@ include file="menu.jsp"%>

	<%
		AuthentificationSite authentification = new AuthentificationSite(
		request, response);
		if (!authentification.isAuthentifieAdmin())
			return;
			FitreAdminActivites filtre=(FitreAdminActivites)session.getAttribute("filtreActivite");
			ArrayList<TypeActiviteBean> listTypeActiviteBean=CacheValueDAO.getListTypeActiviteToutes();
			ArrayList<TypeActiviteBean> listTypeActiviteCritere=CacheValueDAO.getListTypeActiviteWaydeur();
			ArrayList<TypeUser> listTypeUser=CacheValueDAO.getListTypeUser();
			ArrayList<TypeSignalement> listTypeSignalement=CacheValueDAO.getListTypeSignalementActivite();
			ArrayList<TypeEtatActivite> listEtatActivite=CacheValueDAO.getListEtatActivite();
			ArrayList<TypeGratuitActivite> listGratuitActivite=CacheValueDAO.getListGratuitActivite();
			ArrayList<TypeGratuitActivite> listGratuitActiviteRequete=CacheValueDAO.getListGratuitActiviteRequete();
			ArrayList<TypeActiveActivite> listActiveActivites=CacheValueDAO.getListActivteActivite();
			
			PagerActiviteBean pager=(PagerActiviteBean) request
			.getAttribute("pager");
			ArrayList<ActiviteBean> listActivite = pager.getListActivite();
	%>



	<div class="container" style="width: 90%;">
		<div class="panel panel-primary">
			<div class="panel-body" style="background: #99ccff;">

				<form class="form-inline" id="formulaire" method="post"
					action="ListActivite">

					<div class="form-group">
						<label for="autocomplete">Ville:</label> <input
							placeholder="Enter your address" onFocus="geolocate()"
							type="text" class="form-control" id="autocomplete"
							name="autocomplete" value="<%=filtre.getVille()%>"> <input
							type="hidden" step="any" class="form-control" id="latitude"
							name="latitude" value="<%=filtre.getLatitude()%>"> <input
							type="hidden" step="any" class="form-control" id="longitude"
							name="longitude" value="<%=filtre.getLongitude()%>"> <label
							for="rayon">Rayon:</label> <input type="number"
							class="form-control" id="rayon" name="rayon"
							value="<%=filtre.getRayon()%>">

						<div class="form-group">
							<label for="typeUser">Type</label> <select
								data-style="btn-primary" class="form-control" id="typeUser"
								name="typeUser">

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
								data-style="btn-primary" class="form-control"
								id="typeSignalement" name="typeSignalement">

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
						<div class="form-group">
				<label for="iddaterecherche">Date fin</label>
				<div class='input-group date' id="daterecherche">
					<input readonly style="background-color:white;" type='text' class="form-control" id="iddaterecherche" name="daterecherche" />
					<span class="input-group-addon"> <span
						class="glyphicon glyphicon-calendar"></span>
					</span>
				</div>
			</div>
						


						<div class="form-group">
							<label for="etatActivite">Etat</label> <select
								data-style="btn-primary" class="form-control" id="etatActivite"
								name="etatActivite">

								<%
									for (TypeEtatActivite typeEtatActivite:listEtatActivite) {
								%>
								<option value="<%=typeEtatActivite.getId()%>"
									<%=Outils.jspAdapterListSelected(typeEtatActivite.getId(), filtre.getEtatActivite())%>>
									<%=typeEtatActivite.getLibelle()%></option>
								<%
									}
								%>

							</select>
						</div>
						
						
						<div class="form-group">
						<br>
							<label for="etatActivite">Payant</label> <select
								data-style="btn-primary" class="form-control" id="etatActivite"
								name="gratuit">

								<%
									for (TypeGratuitActivite typeEtatActivite:listGratuitActiviteRequete) {
								%>
								<option value="<%=typeEtatActivite.getId()%>"
									<%=Outils.jspAdapterListSelected(typeEtatActivite.getId(), filtre.getGratuit())%>>
									<%=typeEtatActivite.getLibelle()%></option>
								<%
									}
								%>

							</select>
						</div>

						<input type="hidden" class="form-control" id="latitudeFiltre"
							placeholder="latitudeFiltre" name="latitudeFiltre"
							value=<%=pager.getFiltAdminActivites().getLatitude()%>>

						<div class="form-group">

							<input type="hidden" class="form-control" id="longitudeFiltre"
								placeholder="longitude" name="longitudeFiltre"
								value=<%=pager.getFiltAdminActivites().getLongitude()%>>
						</div>
								<label><input name="actif" type="checkbox"
					<%=Outils.jspAdapterCheked(filtre.isActif())%>> Actif		</label>
					
										<label><input name="masque" type="checkbox"
					
					<%=Outils.jspAdapterCheked(filtre.isMasque())%>> Masqué		</label>
				
					
						
						<button id="go" type="submit" class="btn btn-info"
							name="rechercheactivite">Rechercher</button>
					</div>

				</form>
			</div>
		</div>
	</div>


	<div class="container" style="width: 90%;">

		<table class="table table-striped">
			<thead>
				<tr>
					<th style="width: 10%;" class="text-center">Etat</th>
					<th style="width: 40%;" class="text-center">Description</th>
						<th style="width: 10%;" class="text-center">Type</th>
					<th style="width: 10%;" class="text-center">Payant</th>
					<th style="width: 10%;" class="text-center">User</th>
					<th style="width: 10%;" class="text-center">Le</th>
					<th style="width: 5%;" class="text-center">Vu</th>
					<th style="width: 5%;" class="text-center">Sign.</th>
					<th style="width: 5%;" class="text-center">Actif.</th>
					<th style="width: 5%;" class="text-center">Masqué.</th>
					<th style="width: 5%;" class="text-center">Fbook.</th>
					
					
				
				</tr>
			</thead>
			<tbody
				style="background-color: #FFFFFF; text-align: center; vertical-align: middle;">
				<%
					if (listActivite!=null)
							for (ActiviteBean activite : listActivite) {
							String lien = "DetailActivite?idactivite=" + activite.getId()+"&from=listActivite.jsp"
									+"&latitudeFiltre="+pager.getFiltAdminActivites().getLatitude()+
									"&longitudeFiltre="+pager.getFiltAdminActivites().getLongitude();
							String lienParticipant = "DetailParticipant?idPersonne=" + activite.getIdorganisateur()+"&from=listActivite.jsp";
				%>
 
				<tr>
					<td>

						<div class="clearfix">
							<img height="100" width="100" src=<%=activite.getPhoto()%>
								class="img-thumbnail pull-left ">

							<p>
								<%=activite.getTypeUserLienHTML(lienParticipant)%></p>
							<p><%=activite.getEtatHtml()%></p>
							<p><%=activite.calculDistance()%></p>

						</div>
					</td>

					<td><a href=<%=lien%>>
							<p><%=activite.getFulldescription()%></p>
					</a></td>
					<td class="categorie"><select
								data-style="btn-primary" class="form-control" id="typeactivite"
								name="<%=activite.getId()%>">

								<%
									for (TypeActiviteBean typeActivite:listTypeActiviteCritere) {
								%>
								<option value="<%=typeActivite.getId()%>"
									<%=Outils.jspAdapterListSelected(typeActivite.getId(), activite.getTypeactivite())%>>
									<%=typeActivite.getLibelle()%></option>
								<%
									}
								%>

							</select></td>
					<td class="payant">
						 <select
								data-style="btn-primary" class="form-control" id="etatActivite"
								name="<%=activite.getId()%>">

								<%
									for (TypeGratuitActivite typeGratuitActivite:listGratuitActivite) {
								%>
								<option value="<%=typeGratuitActivite.getId()%>"
									<%=Outils.jspAdapterListSelected(typeGratuitActivite.getId(), activite.getGratuite())%>>
									<%=typeGratuitActivite.getLibelle()%></option>
								<%
									}
								%>

							</select>
						
					</td>
					<td><a href=<%=lienParticipant%>><%=activite.getPseudo()%></a></td>
					<td><%=activite.getHoraireLeA()%></td>
					<td><%=activite.getNbrVu()%></td>
					<td><%=activite.getNbrSignalement()%></td>
					
					<td class="actif">
							
						<label><input name="<%=activite.getId() %>" type="checkbox"
					<%=Outils.jspAdapterCheked(activite.actif)%>> 		</label>
					
					</td>
					
					<td class="masque">
							
						<label><input name="<%=activite.getId() %>" type="checkbox"
					<%=Outils.jspAdapterCheked(activite.isMasque())%>> 		</label>
					
					</td>
					<td><a href=<%=activite.getLienFaceBook() %>>FB</a></td>
				

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
		src="https://maps.googleapis.com/maps/api/js?key=<%=Outils.getCleMap()%>&libraries=places&callback=initAutocomplete"
		async defer></script>


	<script>
	
		
		$(function() {

			$('#daterecherche').datetimepicker({
				defaultDate : new Date(<%=filtre.getDateRecherche().getYear()%>,<%=filtre.getDateRecherche().getMonthOfYear()-1%>,<%=filtre.getDateRecherche().getDayOfMonth()%>),
				format : 'DD/MM/YYYY',
				focusOnShow: false,
				  ignoreReadonly: true

			}).on('dp.change', function (e) {document.getElementById("formulaire").submit(); });
			
			
			
			
			$('form select').change(function() {

				document.getElementById("formulaire").submit();
			});

			$('#rayon').change(function() {

				document.getElementById("formulaire").submit();
			});
			
			$('td.categorie select').change(function() {

				
					$.post("ModifieActivite?action=setcategorie&categorie="+this.value+"&idactivite="+this.name
							,
							function(responseText) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...

								

					
							});	
					
					
				});
			
			
			$('td.payant select').change(function() {

				
			//alert(this.value);
			
				$.post("ModifieActivite?action=setgratuit&gratuit="+this.value+"&idactivite="+this.name
						,
						function(responseText) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...

							
							

						});	
				
				
			});
			
			
			$('td.actif :checkbox').change(function() {
				
			
			
 				$.post("ModifieActivite?action=setactif&actif="+this.checked+"&idactivite="+this.name
						,
						function(responseText) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...

							

				
						});	
				
				
			});
			
			$('td.masque :checkbox').change(function() {
				
				
			
 				$.post("ModifieActivite?action=setmasque&masque="+this.checked+"&idactivite="+this.name
						,
						function(responseText) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...

							

				
						});	
				
				
			});
		});
	</script>
</body>

</html>