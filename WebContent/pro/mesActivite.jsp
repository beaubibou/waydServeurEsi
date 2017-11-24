
<%@page import="java.nio.channels.SeekableByteChannel"%>
<%@page import="website.enumeration.MenuEnum"%>
<%@page import="website.enumeration.AlertJsp"%>
<%@page import="website.metier.admin.FiltreJSP"%>
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

<link href="/wayd/css/styleWayd.css" rel="stylesheet" type="text/css">


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
		
	<%=new AlertDialog(authentification).getMessage()%>
		
	</script>

	<div class="container" style="margin-top: 100px">

		<div class="panel panel-primary"">
			<div class="panel-heading">
				<div class="row">
					<div class="col-sm-2">
						<form method="post" action="MesActivites" id="formulaire"
							class="form-inline">
							<div class="form-group">
								<select class="form-control" id="idEtatActivite"
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

						</form>
					</div>

					<div class="col-sm-2 col-sm-offset-8 ">
						<button href="#" name="supprimerActivites" class="btn btn-default">Effacez</button>
					</div>


				</div>


			</div>

		</div>
		<table class="table table-responsive" border="3" id="matable">
			<thead style="background-color: #2196F3;" align="center">
				<tr>
					<th style="width: 10%;" class="text-center">Etat</th>
					<th class="text-center">Titre</th>
					<th style="width: 5%;" class="text-center">Vus</th>
					<th style="width: 20%;" class="text-center">Date</th>
					<th style="width: 15%;" class="text-center">Action</th>
					<th style="width: 10%;" class="text-center"><input
						type="checkbox" id="ckAll">
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
						String lienDetail = "/wayd/DetailActiviteSite?idactivite=" + activite.getId()+"&from=listActivite.jsp";
						String lienEdit = "/wayd/ModifierActivite?idactivite=" + activite.getId()+"&from=listActivite.jsp";
				%>


				<tr>
					<%=activite.getEtatHtml()%>
					<td class="idActivite" id=<%=activite.getId()%>
						style="vertical-align: middle;"><%=activite.getTitre()%></td>
					<td style="vertical-align: middle;"><span class="badge"><%=activite.getNbrVu()%></span></td>
					<td style="vertical-align: middle;"><%=activite.getHoraireLeA()%></td>
					<td style="vertical-align: middle;"><a href="<%=lienDetail%>"
						class="btn btn-info btn-sm"> <span
							class="glyphicon glyphicon-search"></span>
					</a> <!-- Affiche le bouton effacer si pas terminée --> <%
 	if (!activite.isTerminee()){
 %> <a href="<%=lienEdit%>" class="btn btn-info btn-sm"> <span
							class="glyphicon glyphicon-edit"></span>
					</a>

						<button id=<%out.println(lienEfface);%> name="supprimer"
							type="button" class="btn btn-danger btn-sm">
							<span class="glyphicon glyphicon-trash"></span>
						</button> <%
 	}
 %></td>
					<td><%=activite.getCheckHtml()%></td>

				</tr>
				<%
					}
				%>
			</tbody>
		</table>

	</div>
	<%=JumbotronJsp.getJumbotron((JumbotronJsp) request.getAttribute("jumbotron"))%>


	<script>
		$(function() {

			$('button').click(function() {

				var lien = $(this).attr('id');
				var action = $(this).attr('name')
				if (action == 'supprimer')
					DialogEffaceActivite(lien);
				if (action == 'supprimerActivites')
					effaceActivites();

			});

			$("#ckAll").click(function() {

				litTable();
			
			});
		});

		$(function() {

			$('#idEtatActivite').on('change', function() {

				document.getElementById("formulaire").submit();
			});

		});
	</script>

	<script>
		function litTable() {

			$('#matable tr').each(function() {
				var checkBox = $(this).find('input:checkbox'); //L'index 0 permet de récupérer le contenu de la première cellule de la ligne
			
					if ($('#ckAll').is(":checked")) {

					checkBox.prop("checked", true); // it is checked
				
						} else {

					checkBox.prop("checked", false); // it is checked

				}

			});

		
		}

		function DialogEffaceActivites() {

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
						document.getElementById("form_listActivites").submit();
						dialog.close();
					}
				}

				]
			});

		}

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


	<form id="form_listActivites" action="SupprimeActivites" method="post">
		<input id="idListActivites" type="hidden" name="listActivite"></input>
	</form>
	<script type="text/javascript">
		function effaceActivites() {

			var listActivite = "";
			var nbrLigne = 0;
			$('#matable tr').each(function() {
				var checkBox = $(this).find('input:checkbox'); //L'index 0 permet de récupérer le contenu de la première cellule de la ligne
				var id = $(this).find('input:checkbox');

				if (checkBox.is(":checked")) {

					var id = $(this).find('.idActivite').attr('id');
					if (id != null) {
						listActivite = listActivite + id + ";";
						nbrLigne++;
					}
				}

			});
			if (nbrLigne > 0) {

				document.getElementById("idListActivites").value = listActivite;
				DialogEffaceActivites();

			}

		}
	</script>

</body>
</html>
