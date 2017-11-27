<%@page import="website.metier.TypeEtatProfil"%>
<%@page import="website.pager.PagerProfilBean"%>
<%@page import="website.metier.TypeUser"%>
<%@page import="website.metier.admin.FitreAdminProfils"%>
<%@page import="website.metier.ProblemeBean"%>
<%@page import="website.metier.ActiviteBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="website.dao.CacheValueDAO"%>
<%@page import="website.metier.Outils"%>
<%@page import="website.metier.TypeSignalement"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Liste profils</title>
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
	<%
		FitreAdminProfils filtre=(FitreAdminProfils)session.getAttribute("filtreProfil");
			
			ArrayList<TypeUser> listTypeUser=CacheValueDAO.getListTypeUser();
			ArrayList<TypeEtatProfil> listEtatProfil=CacheValueDAO.getListEtatProfil();
			ArrayList<TypeSignalement> listTypeSignalementProfil=CacheValueDAO.getListTypeSignalementProfil();
			
		PagerProfilBean pager=(PagerProfilBean) request
		.getAttribute("pager");
		ArrayList<ProfilBean> listProfil = pager.getListProfils();
	%>

	<div class="container">

		<h3>Critéres</h3>


		<form class="form-inline" method="post" action="ListProfil">

			<div class="form-group">
				<label for="pseudo">Pseudo</label> <input type="text"
					class="form-control" id="pseudo" name="pseudo"
					value="<%=filtre.getPseudo()%>">


				<div class="form-group">
					<label for="typeUser">Type</label> <select data-style="btn-primary"
						class="form-control" id="typeUser" name="typeUser">

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
					<label for="typeEtat">Etat</label> <select data-style="btn-primary"
						class="form-control" id="typeEtat" name="etatProfil">

						<%
							for (TypeEtatProfil typeEtatProfil:listEtatProfil) {
						%>
						<option value="<%=typeEtatProfil.getId()%>"
							<%=Outils.jspAdapterListSelected(typeEtatProfil.getId(), filtre.getEtatProfil())%>>
							<%=typeEtatProfil.getLibelle()%></option>
						<%
							}
						%>

					</select>
				</div>
				
				<div class="form-group">
					<label for="typeSignalement">Signalement</label> <select
						data-style="btn-primary" class="form-control" id="typeSignalement"
						name="typeSignalement">

						<%
							for (TypeSignalement typeSignalement:listTypeSignalementProfil) {
						%>
						<option value="<%=typeSignalement.getId()%>"
							<%=Outils.jspAdapterListSelected(typeSignalement.getId(), filtre.getTypeSignalement())%>>
							<%=typeSignalement.getLibelle()%></option>
						<%
							}
						%>

					</select>
				</div>
				<button id="go" type="submit" class="btn btn-default"
					name="rechercheactivite">Rechercher</button>

			</div>
		</form>

	</div>
	<div class="container">
		<h2>Liste profils</h2>

		<table class="table table-striped">
			<thead>
				<tr>
					<th style="width: 10%;" class="text-center">Etat</th>
					<th>photo</th>
					<th>Pseudo</th>
					<th>Date création</th>
					<th>Action</th>

				</tr>
			</thead>
			<tbody>
				<%
					for (ProfilBean profil : listProfil) {
																		String lien = "DetailParticipant?idPersonne="
																				+ profil.getId();
																		String lienMessage =
																				"/wayd/EnvoiMessageAdmin?idPersonne=" +profil.getId()
																				+"&formInit=listProfil";
																				
																		String lienActivation =profil.getLienActive();
				%>

				<tr>
					<td><a href='<%=lienActivation%>'> <%=profil.getActifHtml()%></a></td>

					<td><img height="30" width="30" src=<%=profil.getUrlPhoto()%>
						class="img-circle" /></td>
					<td><a href=<%=lien%>><%=profil.getPseudo()%></a></td>

					<td><%=profil.getDatecreationStr()%></td>
					<td>
						<button id='<%=lienMessage%>' name='envoiMessage' type='button'
							class='btn btn-primary btn-sm'>
							<span class='glyphicon glyphicon-send'></span>
						</button>


					</td>

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
		$(function() {

			$('button').click(function() {

				var lien = $(this).attr('id');
				var action = $(this).attr('name')

				if (action == 'envoiMessage')
					location.href = lien;

				if (action == 'supprime')
					location.href = lien;

			});

		});
	</script>


</body>
</html>