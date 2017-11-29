<%@page import="website.metier.ParticipantBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="website.metier.ActiviteBean"%>
<%@page import="website.metier.IndicateurWayd"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Détail Activite</title>
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
</head>
<body>

	<%@ include file="menu.jsp"%>

		<%
			ActiviteBean activite = (ActiviteBean) request
					.getAttribute("activite");
			ArrayList<ParticipantBean> listParticipant = activite
					.getListParticipant();
			String lienDetailOrganisateur = "DetailParticipant?idPersonne="
					+ activite.getIdorganisateur();
		%>
		<h2 align="center">	<%=activite.getEtatHtml() %> - <%=activite.getTitre()%></h2>
	
	<div class="container">
		<a href=<%=lienDetailOrganisateur%>><h4><%=activite.getTypeUserHTML()%> - <%=activite.getPseudo()%></h4></a>
		<div class="row">

			<div class="col-sm-2">

				<img height="300" width="200" src=<%=activite.getUrlPhoto()%>
					class="img-thumbnail" />
			</div>
			<div class="col-sm-2">

			
				<h6><%=activite.getHoraireLeA() %></h6>
			</div>
			<div class="col-sm-8">
				
				<textarea disabled="disabled" class="form-control" rows="5"
					id="comment"><%=activite.getLibelle()%>
					</textarea>

			</div>
		</div>
	</br>
	
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-body" style="background: #99ccff;">
	
			<%
				if (activite.isActive()) {
			%>

			<a
			<a
				href="DetailActivite?action=terminerActivite&idactivite=<%=activite.getId()%>"
				class="btn btn-info" role="button">Terminer</a>
				href="DetailActivite?action=effacerActivite&idactivite=<%=activite.getId()%>"
				class="btn btn-danger" role="button">Supprimer</a> 

			<%
				}
			%>
	</div>
	</div>
	</div>
	
	</div>

	<div class="container">
		<h4>Liste participants</h4>

		<table class="table table-striped">
			<thead>
				<tr>

					<th>Pseudo</th>
					<th>age</th>
					<th>sexe</th>

				</tr>
			</thead>
			<tbody>
				<%
					for (ParticipantBean participantBean : listParticipant) {
						String lien = "DetailParticipant?idPersonne="
								+ participantBean.getId();
				%>

				<tr>
					<td><a href=<%=lien%>> <%=participantBean.getPseudo()%>
					</a></td>
					<td>
						<%
							out.println(participantBean.getAge());
						%>
					</td>
					<td>
						<%
							out.println(participantBean.getSexe());
						%>
					</td>
				</tr>

				<%
					}
				%>

			</tbody>
		</table>
	</div>
</body>
</html>