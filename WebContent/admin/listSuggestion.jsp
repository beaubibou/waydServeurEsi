<%@page import="website.metier.admin.FitreAdminSuggestions"%>
<%@page import="website.metier.admin.EtatSuggestion"%>
<%@page import="website.metier.SuggestionBean"%>
<%@page import="website.metier.ProblemeBean"%>
<%@page import="website.metier.ActiviteBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="website.dao.CacheValueDAO"%>
<%@page import="website.metier.Outils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>List suggestions</title>
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
<%	ArrayList<EtatSuggestion> listEtatSuggestion = CacheValueDAO.getListEtatSuggestions();
	FitreAdminSuggestions filtre=(FitreAdminSuggestions)session.getAttribute("filtreSuggestion");
	
	%>
	<div class="container">
	
		<div class="panel panel-primary"">
			<div class="panel-heading">
				<div class="row">
					<div class="col-sm-2">
						<form method="post" action="ListProbleme" id="formulaire"
							class="form-inline">
							<div class="form-group">
								<select class="form-control" id="idEtatProbleme"
									name="etatProbleme">

									<%
										for (EtatSuggestion etatSuggestion:listEtatSuggestion) {
									%>
									<option value="<%=etatSuggestion.getId()%>"
										<%=Outils.jspAdapterListSelected(etatSuggestion.getId(), filtre.getEtatSuggestion())%>>
										<%=etatSuggestion.getLibelle()%></option>
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

	

	<div class="container">
		<h2>Liste de suggestions</h2>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>Pseudo</th>
					<th>Suggestion</th>
					<th>Date</th>
					<th>Action</th>
					<th>Cloturé</th>

				</tr>
			</thead>
			<tbody>
				<%
					ArrayList<SuggestionBean> listSuggestion = (ArrayList<SuggestionBean>) request
																	.getAttribute("listSuggestion");
for (SuggestionBean suggestion : listSuggestion) {
	String lienDetailOrganisateur = "DetailParticipant?idparticipant="
			+ suggestion.getIdPersonne();
String lienMessage =
		"/wayd/EnvoiMessageAdmin?idDestinataire=" +suggestion.getIdPersonne()+
		"&idMessage="+suggestion.getId()+"&formInit=listSuggestion";
String lienEfface ="/wayd/EffaceSuggestionAdmin?idSuggestion="+suggestion.getId()
+"&formInit=listSuggestion";			

%>

				<tr>

					<td><a href=<%out.println(lienDetailOrganisateur);%>> <%
 	out.println(suggestion.getPseudo());
 %>
					</a></td>

					<td>
						<%
							out.println(suggestion.getSuggestion());
						%>

					</td>

					<td>
						<%
							out.println(suggestion.getDateCreationStr());
						%>
					</td>

					<td>

						<button id='<%=lienMessage%>' name='envoiMessage' type='button'
							class='btn btn-primary btn-sm'>
							<span class='glyphicon glyphicon-send'></span>
						</button>
							<button id='<%=lienEfface%>' name='supprime' type='button'
							class='btn btn-primary btn-sm'>
							<span class='glyphicon glyphicon-trash'></span>
						</button>
					</td>
						</td>

					

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

				if (action == 'envoiMessage')
					location.href = lien;

				if (action == 'supprime')
					location.href = lien;

			});

		});

		
	</script>


</body>




</html>