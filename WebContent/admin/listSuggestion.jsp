<%@page import="website.metier.SuggestionBean"%>
<%@page import="website.metier.ProblemeBean"%>
<%@page import="website.metier.ActiviteBean"%>
<%@page import="java.util.ArrayList"%>
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

	<div class="container">

		<h3>Critères</h3>

		<form class="form-inline" method="post" action="ListActivite">

			<div class="form-group">
				<label for="latitude">Date début:</label> <input type="date"
					class="form-control" id="datedebut" name="datedebut"> <label
					for="longitude">Date fin:</label> <input type="date"
					class="form-control" id="datefin" name="datefin">


				<div class="form-group">
					<button id="go" type="submit" class="btn btn-default">Rechercher</button>

				</div>
			</div>
		</form>
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
		"&idMessage="+suggestion.getId();
String lienEfface ="/wayd/EffaceSuggestionAdmin?idSuggestion="+suggestion.getId();			

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

				if (action == 'envoiMessage')
					location.href = lien;

				if (action == 'supprime')
					location.href = lien;

			});

		});

		
	</script>


</body>




</html>