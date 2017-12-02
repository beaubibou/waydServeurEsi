<%@page import="website.metier.SignalementBean"%>
<%@page import="website.dao.CacheValueDAO"%>
<%@page import="website.metier.ProfilBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="website.metier.ParticipantBean"%>
<%@page import="website.metier.Outils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="website.metier.ActiviteBean"%>
<%@page import="website.metier.AuthentificationSite"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>>D�tail activit� pro</title>
<meta charset="utf-8">

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
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-rating-input/0.4.0/bootstrap-rating-input.js"></script>

<link href="/wayd/css/styleWayd.css" rel="stylesheet" type="text/css">

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
		if (!authentification.isAuthentifie())
			return;

		ActiviteBean activite = (ActiviteBean) request
				.getAttribute("activite");
		ArrayList<ParticipantBean> listParticipant = activite
				.getListParticipant();

		ArrayList<SignalementBean> listSignalement = new ArrayList<SignalementBean>();
	%>



	<div class="container">
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">

					<div style="text-align: center;" class="panel-title ">D�tail
						activit� professionelle</div>

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
									�
									<%=activite.calculDistance()%></h5>


							</div>

						</div>


						<div class="form-group">
							</br>
							<h5>
								Type d'activit�:
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
			data-target="#amis">List des amis</button>
		<div id="amis" class="collapse">
		
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Type</th>
				</tr>
			</thead>
			<tbody>
				<%
					for (SignalementBean signalement : listSignalement) {
						
				%>

				<tr>
					<td><%=signalement.getLibelle()%></td>


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
			data-target="#signalement">List Signalement</button>
		<div id="signalement" class="collapse">
		
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