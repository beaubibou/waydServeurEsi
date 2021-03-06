
<%@page import="website.metier.admin.FiltreJSP"%>
<%@page import="website.metier.TypeActiviteBean"%>
<%@page import="website.metier.ActiviteBean"%>

<%@page import="website.metier.TypeEtatActivite"%>
<%@page import="website.metier.FiltreRecherche"%>
<%@page import="website.metier.AuthentificationSite"%>
<%@page import="website.metier.Outils"%>
<%@page import="website.dao.CacheValueDAO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
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


	<%
	
	AuthentificationSite authentification=	new AuthentificationSite(request, response);
			if (!authentification.isAuthentifieWaydeur())
		return;
			
			FiltreRecherche filtre=authentification.getFiltre();
		ArrayList<TypeEtatActivite> listEtatActivite = CacheValueDAO.getListEtatActivite();
		return;
	%>


	<div class="container">
		<div class="page-header">
			<h1>Gerez vos activités</h1>
		</div>
		<p>Supprimez, modifiez vos activités...</p>

	</div>

	<div class="container">
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-12  col-sm-8">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">
					<div class="panel-title" style="text-align: center;">Liste de
						vos activités</div>
					<form action="MesActivitesWaydeur" method="post">

						<div class="row">

							<div class='col-sm-2'>
								<div class="form-group">
									<select class="form-control" id="idtypeaccess"
										name="etatActivite">

										<%
											for (TypeEtatActivite etatActivite:listEtatActivite) {
										%>
										<option value="<%=etatActivite.getId()%>"
											<%=Outils.jspAdapterListSelected(etatActivite.getId(), filtre.getTypeEtatActivite())%>>
											<%=etatActivite.getLibelle()%></option>
										<%
											}
										%>

									</select>
								</div>
							</div>
							<div class='col-sm-2'>

								<button type="submit" class="btn btn-info">Cherchez</button>
							</div>
						</div>

					</form>
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
									<th>Horaire</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>

								<%
									ArrayList<ActiviteBean> listMesActivite =
	(ArrayList<ActiviteBean>) request.getAttribute("listMesActivite");
																														    
    if (listMesActivite!=null)
for (ActiviteBean activite : listMesActivite) {
	String lienEfface = "/wayd/SupprimeActiviteWaydeur?idactivite=" + activite.getId();
String lienDetail = "/wayd/DetailActiviteSite?idactivite=" + activite.getId()+"&from=listActivite.jsp";
String lienEdit = "/wayd/ModifierActivite?idactivite=" + activite.getId()+"&from=listActivite.jsp";
								%>

								<tr>
									<td><%=activite.getTitre()%></td>
									<td><textarea class="form-control" disabled rows="2"
											id="comment"><%=activite.getLibelle()%></textarea></td>

									<td><span class="badge">10</span></td>

									<%
										if (!activite.isTerminee()){
									%>
									<td><span class="label label-pill label-success"> </span></td>

									<%
										}
																							 	else{
									%>

									<td><span class="label label-pill label-danger"> </span></td>
									<%
										}
									%>
									<td><%=activite.getHoraireLeA()%></td>

									<td><a href="<%=lienDetail%>" class="btn btn-info btn-sm">
											<span class="glyphicon glyphicon-search"></span>
									</a> <!-- Affiche le bouton effacer si pas terminée --> <%
 	if (!activite.isTerminee()){
 %> <a href="<%=lienEdit%>" class="btn btn-info btn-sm"> <span
											class="glyphicon glyphicon-edit"></span>
									</a>

										<button id=<%out.println(lienEfface);%> name="supprimer"
											type="button" class="btn btn-danger btn-sm">
											<span class="glyphicon glyphicon-remove"></span>
										</button> <%
 	}
 %></td>

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
				buttons : [

				{
					label : 'Annuler',
					action : function(dialog) {
						dialog.close();
					}
				},

				{
					label : 'Oui',
					action : function(dialog) {
						effaceActivite(lien);
						dialog.close();
					}
				}

				]
			});

		}

		function effaceActivite(lien) {
			location.href = lien;
		}
	</script>


</body>
</html>
