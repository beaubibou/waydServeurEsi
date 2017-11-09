
<%@page import="java.nio.channels.SeekableByteChannel"%>
<%@page import="website.enumeration.MenuEnum"%>
<%@page import="website.enumeration.AlertJsp"%>
<%@page import="website.metier.FiltreJSP"%>
<%@page import="website.metier.TypeActiviteBean"%>
<%@page import="website.metier.ActiviteBean"%>
<%@page import="website.metier.Pagination"%>
<%@page import="website.metier.TypeEtatActivite"%>
<%@page import="website.metier.FiltreRecherche"%>
<%@page import="website.metier.AuthentificationSite"%>
<%@page import="website.metier.Outils"%>
<%@page import="website.dao.CacheValueDAO"%>
<%@page import="website.html.*"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
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

<script src="js/alertdialog.js"></script>

<link href="/wayd/css/style.css" rel="stylesheet" type="text/css">

</head>
<body>


	<%
		AuthentificationSite authentification=	new AuthentificationSite(request, response);
		if (!authentification.isAuthentifiePro())
			return;
		
		FiltreRecherche filtre=authentification.getFiltre();
			ArrayList<TypeEtatActivite> listEtatActivite = CacheValueDAO.getListEtatActivite();
			MenuEnum etatMenu=MenuEnum.mesactivites;
	%>




	<%@ include file="menu.jsp"%>
	<script type="text/javascript">
	<%=new AlertDialog(authentification,AlertJsp.Sucess).getMessage()%>
		
	</script>
	
	<div class="container">
		</br>
		<h2>Mes activités</h2>
		<p>Gérez vos activités</p>
		<form action="MesActivites" method="post">

			<div class="row">

				<div class='col-sm-2'>
					<div class="form-group">
						<select class="form-control" id="idtypeaccess" name="etatActivite">

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
		<table class="table table-responsive" border="3";>
			<thead style="background-color: #2196F3;" align="center">
				<tr>
					<th class="text-center">Titre</th>

					<th class="text-center">Vus</th>
					<th class="text-center">Etat</th>
					<th class="text-center">Date</th>
					<th class="text-center">Action</th>
				</tr>
			</thead>
			<tbody
				style="background-color: #FFFFFF; text-align: center; vertical-align: middle;">

				<%
					ArrayList<ActiviteBean> listMesActivite =
												(ArrayList<ActiviteBean>) request.getAttribute("listMesActivite");
																																																																    
												    if (listMesActivite!=null)
												for (ActiviteBean activite : listMesActivite) {
													String lienEfface = "/wayd/SupprimeActivite?idactivite=" + activite.getId();
													String lienConfirmDialog="/wayd/ConfirmDialog?idactivite=" + activite.getId()+"&action=effaceActivite&from=MesActivites";
												String lienDetail = "/wayd/DetailActiviteSite?idactivite=" + activite.getId()+"&from=listActivite.jsp";
												String lienEdit = "/wayd/ModifierActivite?idactivite=" + activite.getId()+"&from=listActivite.jsp";
				%>


				<tr>
					<td style="vertical-align: middle;"><%=activite.getTitre()%></td>
					<td style="vertical-align: middle;"><span class="badge"><%=activite.getNbrVu()%></span></td>

					<%=activite.getEtatHtml()%>

					<td style="vertical-align: middle;"><%=activite.getHoraireLeA()%></td>

					<td style="vertical-align: middle;"><a href="<%=lienDetail%>"
						class="btn btn-info btn-sm"> <span
							class="glyphicon glyphicon-search"></span>
					</a> <!-- Affiche le bouton effacer si pas terminée --> <%
 	if (!activite.isTerminee()){
 %>

						<a href="<%=lienEdit%>" class="btn btn-info btn-sm"> <span
							class="glyphicon glyphicon-edit"></span>
					</a>

						<button id=<%out.println(lienEfface);%> name="supprimer"
							type="button" class="btn btn-danger btn-sm">
							<span class="glyphicon glyphicon-trash"></span>
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
