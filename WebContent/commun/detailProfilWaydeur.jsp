<%@page import="website.metier.AvisBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="website.metier.ProfilBean"%>
<%@page import="website.metier.Outils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="website.metier.AuthentificationSite"%>
<%@page import="website.html.Etoile"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>>Détail professionnel</title>


<meta name="viewport" content="width=device-width, initial-scale=1">

<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<link
	href="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">
<script
	src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<!-- <script src="src/bootstrap-rating-input.js"></script> -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-rating-input/0.4.0/bootstrap-rating-input.js"></script>

<script src="js/moment.js"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css"
	rel="stylesheet" type="text/css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>

<link href="/wayd/css/style.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
	var lastIndex = 0;
</script>
<style>
.vcenter {
	display: inline-block;
	vertical-align: middle;
	float: none;
}

.vertical-align {
	display: flex;
	align-items: center;
}
</style>
</head>
<body>


	<%
		AuthentificationSite authentification = new AuthentificationSite(
			request, response);
			if (!authentification.isAuthentifie())
		return;

			ProfilBean profil = (ProfilBean) request.getAttribute("profil");
			ArrayList<AvisBean> listAvis = (ArrayList<AvisBean>) request
			.getAttribute("listAvis");
	%>

	<%
		if (authentification.isPro()) {
	%>

	<%@ include file="/pro/menu.jsp"%>
	<%
		} else {
	%>

	<%@ include file="/waydeur/menuWaydeur.jsp"%>
	<%
		}
	%>

	<div class="container">
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">

					<div class="panel-title">Profil </div>

				</div>

				<div style="padding-top: 30px" class="panel-body">

					<div class="form-group">
						<div class="btn-group">
							<a class="btn btn-danger" href="SignalerProfil?idProfil=<%=profil.getId()%>" role="button">Signaler</a>

						</div>
					</div>

					<div class="form-group">

						<div class="row vertical-align">
							<div class='col-sm-4'>

								<img height="300" width="300"
									src=<%out.println(Outils.getUrlPhoto(profil.getPhotostr()));%>
									class="img-thumbnail  " class="text-center" />

							</div>

							<div class='col-sm-6' class="text-center">

								<h4 style="padding-left: 15px"><%=profil.getPseudo()%></h4>

								<%
									if (!profil.isAfficheAge()) {
								%>
								<h5 style="padding-left: 15px"><%=profil.getAge()%></h5>
								<%
									}
								%>

								<%
									if (!profil.isAfficeSexe()) {
								%>
								<h5 style="padding-left: 15px"><%=profil.getSexe()%></h5>
								<%
									}
								%>
								<h5 style="padding-left: 15px">
										<%=Etoile.getNbrEtoiles(profil.getNote())%>
								</h5>
							</div>

						</div>

					</div>
					<div class="form-group">
						<label for="description">Description:</label>
						<textarea disabled class="form-control" rows="5" id="description"
							name="description"><%=profil.getCommentaireStr()%></textarea>
					</div>

					<div class="form-group">
						<div class="row">
							<div class='col-sm-12'>

								<div class="table-responsive" id="listAvis">
									<table class="table table-striped" id="list">
										<thead>
											<tr>
												<th>Avis</th>
											</tr>
										</thead>
										<tbody>
											<%
												for (AvisBean avisBean : listAvis) {
											%>

												<tr>
												<td><%=Etoile.getNbrEtoiles(avisBean.getNote())%> 
												<strong><%=avisBean.getPrenomnotateur()%></strong>
												</td>
												<td><%=avisBean.getLibelle()%></td>

											</tr>

										</script>				
											</tr>

											<%
												}
											%>
										</tbody>
									</table>

								</div>
								<div class="form-group">
									<div class="btn-group">
										<a class="btn btn-info" id="plusavis" role="button">+</a>
									</div>
								</div>
							</div>

						</div>

					</div>
				</div>
			</div>
		</div>

	</div>
	<script>
	$(document).on("click", "#plusavis", function() { // When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...
		$.get("PlusAvis?lastIndex=" + lastIndex, function(responseJson) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...

			//       $select.find("tr").remove();    
			//  	alert("klk");                      // Find all child elements with tag name "option" and remove them (just to prevent duplicate options when button is pressed again).

			ajouteLigneTable(responseJson);
		});
	});

	function ajouteLigneTable(responseJson) {

		$.each(responseJson, function(index, avis) { // Iterate over the JSON array.

			var table = document.getElementById('list');
			var newRow = table.insertRow(-1);
			var rowNumber = table.childNodes.length;

			var note = newRow.insertCell(-1);
			var ligne = getNbrEtoile(avis.note) + '<strong >'
					+ avis.prenomnotateur + '</strong>';
			note.innerHTML = ligne;

			var commentaire = newRow.insertCell(-1);
			var pseudotext = '<p>' + avis.libelle + '</p>';
			commentaire.innerHTML = pseudotext;

			lastIndex = avis.idnoter;

		});
	}

	function getNbrEtoile(nbr) {

		nbr = Math.ceil(nbr);

		if (nbr == 0) {

			return '<p><span  style="color: #FF0000;" class="glyphicon glyphicon-thumbs-down"></span></p> ';

		}

		var retour = '<p>';
		for (var iter = 0; iter < nbr; iter++) {

			retour = retour
					+ '<span class="glyphicon glyphicon-thumbs-up"></span> ';

		}

		retour = retour + '</p>';
		return retour;

	}
	</script>

</body>
</html>