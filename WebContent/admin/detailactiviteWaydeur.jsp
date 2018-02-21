<%@page import="website.dao.SignalementDAO"%>
<%@page import="website.metier.SignalementActiviteBean"%>
<%@page import="website.dao.CacheValueDAO"%>
<%@page import="website.metier.ProfilBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>
<%@page import="website.metier.ParticipantBean"%>
<%@page import="website.metier.Outils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="website.metier.ActiviteBean"%>
<%@page import="website.metier.AuthentificationSite"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>>Détail activité waydeur</title>
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

<link href="/wayd/css/styleWaydAdmin.css" rel="stylesheet" type="text/css" />

<link href="/wayd/css/nbrcaractere.css" rel="stylesheet" media="all"
	type="text/css" />

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
	<%@ include file="menu.jsp"%>


	<%
	AuthentificationSite authentification = new AuthentificationSite(
			request, response);
			if (!authentification.isAuthentifieAdmin())
		return;

		ActiviteBean activite = (ActiviteBean) request
				.getAttribute("activite");
		ArrayList<ParticipantBean> listParticipant = activite
				.getListParticipant();

		ArrayList<SignalementActiviteBean> listSignalement = SignalementDAO
				.getListSignalementActivite(activite.getId());
	%>

	<div class="container">
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">

					<div style="text-align: center;" class="panel-title ">Détail
						activité Waydeur</div>

				</div>


				<div style="padding-top: 30px" class="panel-body">

					<div class="form-group">
						<div class="row">

							<div class='col-sm-2  ' class="text-center">
								<%
									if (activite.isActive()) {
								%>

								<a
									href="DetailActivite?action=terminerActivite&idactivite=<%=activite.getId()%>"
									class="btn btn-info" role="button">Terminer</a>


								<%
									}
								%>
							</div>


							<div class='col-sm-2  ' class="text-center">
								<a
									href="DetailActivite?action=effacerActivite&idactivite=<%=activite.getId()%>"
									class="btn btn-danger" role="button">Supprimer</a>

							</div>




						</div>
					</div>


					<div class="form-group">

						<div class="row">

							<div class='col-sm-1  ' class="text-center">
								<img height="30" width="30"
									src=<%out.println(Outils.getUrlPhoto(CacheValueDAO
					.getPhotoTypeActivite(activite.getTypeactivite())));%>
									class="img-circle" class="text-center" />

							</div>


						</div>

					</div>
					<div class="form-group">

						<div class="row vertical-align">
							<div class='col-sm-4'>

								<img height="300" width="300"
									src=<%out.println(Outils.getUrlPhoto(activite.getPhoto()));%>
									class="img-thumbnail" class="text-center" />

							</div>

							<div class='col-sm-6' class="text-center">

								<a href="#">
									<h3 style="padding-left: 15px; color: blue;"><%=activite.getPseudo()%></h3>
								</a>

								<h4 style="padding-left: 15px"><%=activite.getTitre()%></h4>
								<h5 style="padding-left: 15px"><%=activite.getAdresse()%></h5>
								<h5 style="padding-left: 15px">
									à
									<%=activite.calculDistance()%></h5>


							</div>

						</div>


						<div class="form-group">
							<br>
							<h5>
								Type d'activité:
								<%=CacheValueDAO.geLibelleTypeActivite(activite
					.getTypeactivite())%></h5>


							<h5><%=activite.getHoraireLeAHorizontal()%></h5>

						</div>


						<div class="form-group">
							<label for="description">Description:</label>
							<textarea disabled class="form-control" rows="5" id="description"
								name="description"><%=activite.getLibelle()%></textarea>
						</div>

					</div>
				</div>
			</div>
		</div>


	</div>

	<div class="container">

		<button type="button" class="btn btn-info" data-toggle="collapse"
			data-target="#signalement">
			Signalement (<%=listSignalement.size()%>)
		</button>
		<div id="signalement" class="collapse">

			<table class="table table-striped">
				<thead>
					<tr>
						<th>Signalé par</th>
						<th>Date</th>
						<th>Motif</th>
						<th>Commentaires</th>
					</tr>
				</thead>
				<tbody>
					<%
						for (SignalementActiviteBean signalement : listSignalement) {
							String lienDetailOrganisateur = "DetailParticipant?idPersonne="
									+ signalement.getIdpersonneInformateur();
					%>

					<tr>
						<td><a href=<%=lienDetailOrganisateur%>> <%=signalement.getPseudoInformateur()%>
						</a></td>
						<td><%=signalement.getDateCreationStr()%></td>
						<td><%=signalement.getLibelle()%></td>
						<td><%=signalement.getMotif()%></td>
					</tr>

					<%
						}
					%>

				</tbody>
			</table>
		</div>

	</div>


	<div class="container">

		<button type="button" class="btn btn-info" data-toggle="collapse"
			data-target="#participants">
			Participants (<%=listParticipant.size()%>)
		</button>
		<div id="participants" class="collapse">

			<table class="table table-striped">
				<thead>
					<tr>
						<th>User</th>
					<th>Pseudo</th>
					<th>age</th>
					</tr>
				</thead>
				<tbody>
						<%
					for (ParticipantBean participantBean : listParticipant) {
						String lien = "DetailParticipant?idPersonne="
								+ participantBean.getId();
				%>

				<tr>
					<td><%=participantBean.getTypeUserHTML()%></td>

					<td><a href=<%=lien%>> <%=participantBean.getPseudo()%>
					</a></td>

					<td><%=participantBean.getAge()%></td>

				</tr>

				<%
					}
				%>


				</tbody>
			</table>
		</div>

	</div>



</body>
</html>