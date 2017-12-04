<%@page import="website.metier.SignalementProfilBean"%>
<%@page import="website.metier.SignalementActiviteBean"%>
<%@page import="website.html.Etoile"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="website.metier.AuthentificationSite"%>
<%@page import="website.metier.ProfilBean"%>
<%@page import="website.metier.AvisBean"%>
<%@page import="website.metier.Outils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="website.html.OutilsHtml"%>
<%@page import="website.metier.ActiviteBean"%>

<%@page import="website.metier.AmiBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>>Détail Waydeur</title>
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
<script src="src/bootstrap-rating-input.js"></script>
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
<link href="/wayd/css/styleWaydAdmin.css" rel="stylesheet"
	type="text/css">

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
	<%@ include file="menu.jsp"%>

	<%
	AuthentificationSite authentification = new AuthentificationSite(
			request, response);
			if (!authentification.isAuthentifieAdmin())
		return;

			ProfilBean profil = (ProfilBean) request.getAttribute("profil");
			ArrayList<ActiviteBean> listActivite = profil.getListActivite();
			ArrayList<SignalementProfilBean> listSignalement = profil
			.getListSignalement();
			ArrayList<AmiBean> listAmi = profil.getListAmi();
			String idParticipant = Integer.toString(profil.getId());

			String libelleBoutonActif;
			String lienBoutonActif;

			String lienSupprimer;

			lienSupprimer = "EffaceParticipant?idPersonne=" + idParticipant;

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

	<div class="container">
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">

					<div class="panel-title">Détail Waydeur</div>

				</div>

				<div style="padding-top: 30px" class="panel-body">

					<div class="form-group">
						<div class="row">

							<div class='col-sm-2  ' class="text-center">
								<a class="btn btn-info" href="<%=profil.getSiteWebStr()%>"
									role="button">Site Web</a>
							</div>


							<div class='col-sm-2  ' class="text-center">
								<a href=<%=lienBoutonActif%> class="btn btn-info" role="button">
									<%=libelleBoutonActif%></a>


							</div>

							<div class='col-sm-2  ' class="text-center">
								<a href=<%=lienSupprimer%> class="btn btn-danger" role="button">
									Supprimer</a>
							</div>



						</div>
					</div>
					<div class="form-group">

						<div class="row vertical-align">
							<div class='col-sm-4'>

								<img height="300" width="300"
									src=<%out.println(Outils.getUrlPhoto(profil.getPhotostr()));%>
									class="img-thumbnail" class="text-center" />

							</div>

							<div class='col-sm-6' class="text-center">

								<h3 style="padding-left: 15px">
									<%=profil.getActifHtml()%>-<%=profil.getPseudo()%></h3>
								<h5>
									Sexe:<%=profil.getSexeStr()%>
								</h5>

								<h5>
									Age:<%=profil.getAge()%>
								</h5>

								<h5>

									Note:<%=profil.getNote()%>/5
								</h5>

								<h5>
									Nbr ami:
									<%=profil.getNbrami()%>
								</h5>
								
									<h5 style="color: blue">
									Mail :
									<%=OutilsHtml.convertStringHtml(profil.getEmail())%>
								</h5>

							</div>

						</div>

					</div>
					<div class="form-group">
						<label for="description">Description:</label>
						<textarea disabled class="form-control" rows="5" id="description"
							name="description"></textarea>
					</div>
					<form method="post" action="/wayd/EnvoiMessageAdmin">

						<div class="form-group">

							<div class="row vertical-align">
								<div class='col-sm-9'>

									<div class="form-group">
										<label for="description">Envoyer un message:</label>
										<textarea class="form-control" rows="3" id="message"
											placeholder="Envoie un message à l'utlisateur" name="message"></textarea>
									</div>
								</div>
								<div class='col-sm-2'>
									<div class="form-group">
										<button type="submit" class="btn btn-primary">Envoyer
										</button>
									</div>
								</div>
							</div>

						</div>
						<input type="hidden" name="idPersonne" value="<%=idParticipant%>">
						<input type="hidden" name="formInit" value="detailParticipant">

					</form>


				</div>
			</div>
		</div>

	</div>

	<div class="container">
		<button type="button" class="btn btn-info" data-toggle="collapse"
			data-target="#activite">List des activités (<%=listActivite.size() %>)</button>
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
						<td><a href=<%out.println(lien);%>> <%=activite.getTitre()%>
						</a></td>
						<td><%=activite.getPseudo()%></td>
						<td><%=activite.getDatefinStr()%></td>
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
			data-target="#signalement">Signalement (<%=listSignalement.size() %>)</button>
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
						for (SignalementProfilBean signalement : listSignalement) {
													String lienDetailOrganisateur = "DetailParticipant?idparticipant="
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
			data-target="#amis">List des amis (<%=listAmi.size()%>)</button>
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
						<td><a href=<%=lien%>> <%=amiBean.getPrenom()%>
						</a></td>
						<td><%=amiBean.getNote()%></td>
						<td><%=amiBean.getDepuisle()%></td>
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