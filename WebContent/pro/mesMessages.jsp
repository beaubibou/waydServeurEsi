
<%@page import="website.metier.TypeEtatMessage"%>
<%@page import="java.nio.channels.SeekableByteChannel"%>
<%@page import="website.enumeration.MenuEnum"%>
<%@page import="website.enumeration.AlertJsp"%>
<%@page import="website.metier.FiltreJSP"%>
<%@page import="website.metier.TypeEtatMessage"%>
<%@page import="website.metier.MessageBean"%>
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
<title>Mes Messages</title>
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

<link href="/wayd/css/styleWayd.css" rel="stylesheet" type="text/css">>

</head>
<body>

	<%
		AuthentificationSite authentification=	new AuthentificationSite(request, response);
		if (!authentification.isAuthentifiePro())
			return;
		
		FiltreRecherche filtre=authentification.getFiltre();
			ArrayList<TypeEtatMessage> listEtatMessage = CacheValueDAO.getListEtatMessage();
			MenuEnum etatMenu=MenuEnum.mesmessages;
	%>

	<%@ include file="menu.jsp"%>

	
	<script type="text/javascript">
		
	<%=new AlertDialog(authentification).getMessage()%>
	
	
	</script>
	
	<div class="container" style="margin-top: 100px">

		<div class="panel panel-primary">
			<div class="panel-heading">
				<div class="row">
					<div class="col-sm-2">
						<form method="post" action="MesMessages" id="formulaire"
							class="form-inline">
							<div class="form-group">
								<select class="form-control" id="idFiltreMessage"
									name="etatMessage">

									<%
										for (TypeEtatMessage etatMessage:listEtatMessage) {
									%>
									<option value="<%=etatMessage.getId()%>"
										<%=Outils.jspAdapterListSelected(etatMessage.getId(), filtre.getTypeMessage())%>>
										<%=etatMessage.getLibelle()%></option>
									<%
										}
									%>

								</select>

							</div>

						</form>
					</div>

					<div class="col-sm-2 col-sm-offset-8 ">
						<button  name="supprimerMessages" class="btn btn-default">Effacez</button>
					</div>

				</div>


			</div>

		</div>
		<table class="table table-responsive"  id="matable">
			<thead style="background-color: #2196F3;" align="center">
				<tr>
					<th  style="width:10%;"  class="text-center">Date</th>
					<th class="text-center">Message</th>
					
					<th   style="width:10%;"  class="text-center"><input type="checkbox" id="ckAll">
						<th   style="width:10%;" class="text-center">Action</th>
				
					
					<th style="width:10%;"  class="text-center">Lu</th>
				
				</tr>
			</thead>
			<tbody
				style="background-color: #FFFFFF; text-align: center; vertical-align: middle;">

				<%
					ArrayList<MessageBean> listMessage =
					(ArrayList<MessageBean>) request.getAttribute("listMesMessages");
																																																																			    
							    if (listMessage!=null)
							for (MessageBean messageBean : listMessage) {
							String lienEfface = "/wayd/SupprimeMessage?idmessage=" + messageBean.getId();
							String lienLecture = "/wayd/LireMessage?idmessage=" + messageBean.getId();
						
				%>


				<tr>
				<td style="vertical-align: middle;"><%=messageBean.getDateCreationHtml()%></td>
					
					<td class="idMessage" id=<%=messageBean.getId()%>
						style="vertical-align: middle;"><%=messageBean.getMessage()%></td>
					
					<td><%=messageBean.getCheckHtml()%></td>
					<td><button id='<%=lienEfface%>' name='supprimer'
							type='button' class='btn btn-danger btn-sm'>
							<span class='glyphicon glyphicon-trash'></span>
						</button></td>
					<td>
					<%=messageBean.getLuHtml(lienLecture) %>	
					</td>
				
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
				if (action == 'supprimerMessages')
					effaceActivites();
				if (action == 'lireMessage')
					lireMessage(lien);

			});

			$("#ckAll").click(function() {

				litTable();
			});
		});

		$(function() {

			$('#idFiltreMessage').on('change', function() {
				var selected = $(this).val();
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

		function DialogEffaceMessages() {

			BootstrapDialog.show({
				title : 'Efface messages',
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
						document.getElementById("form_listMessages").submit();
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
						effaceMessage(lien);
						dialog.close();
					}
				}

				]
			});

		}

		function effaceMessage(lien) {
			location.href = lien;
		}
		

		
	</script>


	<form id="form_listMessages" action="SupprimeMessages" method="post">
		<input id="idListMessages" type="hidden" name="listMessage"></input>
	</form>
	<script type="text/javascript">
		function effaceActivites() {

			var listMessage = "";
			var nbrLigne = 0;
			$('#matable tr').each(function() {
				var checkBox = $(this).find('input:checkbox'); //L'index 0 permet de récupérer le contenu de la première cellule de la ligne
				var id = $(this).find('input:checkbox');

				if (checkBox.is(":checked")) {

					var id = $(this).find('.idMessage').attr('id');
					if (id != null) {
						listMessage = listMessage + id + ";";
						nbrLigne++;
					}
				}

			});
			if (nbrLigne > 0) {

				document.getElementById("idListMessages").value = listMessage;
				DialogEffaceMessages();

			}

		}

		function lireMessage(lien) {
			location.href = lien;
		}
	</script>

</body>
</html>
