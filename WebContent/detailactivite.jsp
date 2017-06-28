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
</head>
<body>

	<%@ include file="menu.jsp"%>

	<h2 align="center">Detail Activité</h2>
	<div class="container">

		<%
			ActiviteBean activite = (ActiviteBean) request
					.getAttribute("activite");
			ArrayList<ParticipantBean> listParticipant = activite
					.getListParticipant();
			String lienDetailOrganisateur = "DetailParticipant?idparticipant="
					+ activite.getIdorganisateur();
		%>

		<a href=<%out.println(lienDetailOrganisateur);%>><h2>
				<%
					out.println(activite.getPseudo());
				%>
			</h2></a>
		<div class="row">

			<div class="col-sm-2"">
				<img height="80" width="80"
					src=<%out.println(activite.getUrlPhoto());%> class="img-circle" />
			</div>
			<div class="col-sm-4"">
				<div class="span2">

					<h6>
						<%
							out.println(activite.getTitre());
						%>
					</h6>
					<h6>
						De
						<%
						out.println(activite.getDatedebutStr() + " à "
								+ activite.getDatefinStr());
						;
					%>
					</h6>
					
					<label for="comment">Comment:</label>
					<textarea  disabled="disabled" class="form-control" rows="3" id="comment"><%=activite.getLibelle()%>
					</textarea>
					
					<h6>

						<%
							out.println(activite.isActiveStr());
							;
						%>
					</h6>


				</div>
			</div>
			<div class="col-sm-6"">
				<%
					if (activite.isActive()) {
				%>

				<a href="DetailActivite?action=effacerActivite&idactivite=<%=activite.getId() %>" class="btn btn-info" role="button">Supprimer</a> <a
				 
				   href="DetailActivite?action=terminerActivite&idactivite=<%=activite.getId() %>" class="btn btn-info" role="button">Terminer</a>

				<%
					}
				%>
			</div>
		</div>

		<div class="container">
			<h2>Liste des participants</h2>

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
							String lien = "DetailParticipant?idparticipant="
									+ participantBean.getId();
					%>

					<tr>
						<td><a href=<%out.println(lien);%>> <%
 	out.println(participantBean.getPseudo());
 %>
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