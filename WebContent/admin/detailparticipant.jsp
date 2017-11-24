<%@page import="website.metier.SignalementBean"%>
<%@page import="website.metier.AmiBean"%>
<%@page import="website.metier.ProfilBean"%>
<%@page import="website.metier.ParticipantBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="website.metier.ActiviteBean"%>
<%@page import="website.metier.IndicateurWayd"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Détail participant</title>
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

	<h2 align="center">Detail Participant</h2>


	<div class="container">


		<%
			ProfilBean profil = (ProfilBean) request.getAttribute("profil");
			ArrayList<AmiBean> listAmi = profil.getListAmi();
			ArrayList<ActiviteBean> listActivite = profil.getListActivite();
			ArrayList<SignalementBean> listSignalement = profil
					.getListSignalement();
			String idParticipant = Integer.toString(profil.getId());

			String libelleBoutonActif;
			String lienBoutonActif;

			if (profil.isActif()) {
				libelleBoutonActif = "Désactiver";
				lienBoutonActif = "DetailParticipant?inactif=true&idPersonne="
						+ idParticipant;

			}

			else {
				libelleBoutonActif = "Activer";
				lienBoutonActif = "DetailParticipant?actif=true&idPersonne="
						+ idParticipant;

			}
		%>

		<h2>
			<%
				out.println(profil.getPseudo());
			%>
		</h2>
		<div class="row">

			<div class="col-sm-2"">
				<img height="80" width="80"
					src=<%out.println(profil.getUrlPhoto());%> class="img-circle" />
			</div>
			<div class="col-sm-4"">
				<div class="span2">

					<h6>
						<%
							out.println(profil.getAge());
						%>
					</h6>
					<h6>

						<%
							out.println(profil.getNote());
						%>/5
					</h6>
					<h6>
						Nbr ami:
						<%
						out.println(profil.getNbrami());
					%>
						<h6>
							Profil :
							<%
							out.println(profil.isActifStr());
						%>
						</h6>
				</div>
			</div>
			<div class="col-sm-6"">
				<a href=<%out.println(lienBoutonActif);%> class="btn btn-info"
					role="button"> <%
 				out.println(libelleBoutonActif);
 %></a>
			</div>
		</div>



	</div>
	</br>
	<div class="container">

		<form method="post" action="/wayd/EnvoiMessageAdmin">

			<div class="form-group">
			<label for="comment">Message</label>
			<textarea name="message" class="form-control" rows="5" id="comment"></textarea>
			</div>
			<input type="hidden" name="idPersonne" value="<%=idParticipant%>">
			<input type="hidden" name="formInit" value="detailParticipant">
			<button type="submit" class="btn btn-primary">Envoyer un message</button>

		</form>
	</div>
	<div class="container">

		<button type="button" class="btn btn-info" data-toggle="collapse"
			data-target="#amis">List des amis</button>
		<div id="amis" class="collapse">

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
						for (AmiBean amiBean : listAmi) {
							String lien = "DetailParticipant?idparticipant="
									+ amiBean.getId();
					%>

					<tr>
						<td><a href=<%out.println(lien);%>> <%
 	out.println(amiBean.getPrenom());
 %>
						</a></td>
						<td>
							<%
								out.println(amiBean.getNote());
							%>
						</td>
						<td>
							<%
								out.println(amiBean.getDepuisle());
							%>
						</td>
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
			data-target="#activite">List des activités</button>
		<div id="activite" class="collapse">

			<table class="table table-striped">
				<thead>
					<tr>
						<th>Titre</th>
						<th>Organisateur</th>
						<th>Date fin</th>
					</tr>
				</thead>
				<tbody>
					<%
						for (ActiviteBean activite : listActivite) {
							String lien = "DetailActivite?idactivite=" + activite.getId();
					%>

					<tr>
						<td><a href=<%out.println(lien);%>> <%
 						out.println(activite.getTitre());
 %>
						</a></td>
						<td>
							<%
								out.println(activite.getPseudo());
							%>
						</td>
						<td>
							<%
								out.println(activite.getDatefinStr());
							%>
						</td>
					</tr>

					<%
						}
					%>

				</tbody>
			</table>
		</div>

	</div>


	<div class="container">
		<button type="button" class="btn btn-danger" data-toggle="collapse"
			data-target="#signalement">Signalement</button>
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
						for (SignalementBean signalement : listSignalement) {
							String lienDetailOrganisateur = "DetailParticipant?idparticipant="
									+ signalement.getIdpersonneInformateur();
					%>

					<tr>
						<td><a href=<%out.println(lienDetailOrganisateur);%>> <%
 	out.println(signalement.getPseudoInformateur());
 %>
						</a></td>

						<td>
							<%
								out.println(signalement.getDateCreationStr());
							%>
						</td>
						<td>
							<%
								out.println(signalement.getLibelle());
							%>
						</td>
						<td>
							<%
								out.println(signalement.getMotif());
							%>
						</td>


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