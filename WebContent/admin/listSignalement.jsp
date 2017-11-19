<%@page import="website.metier.SignalementCount"%>
<%@page import="website.metier.SuggestionBean"%>
<%@page import="website.metier.ProblemeBean"%>
<%@page import="website.metier.ActiviteBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>List signalement</title>
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
		<h2>Liste signalement</h2>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>Pseudo</th>
					<th>Nombre</th>
		
				</tr>
			</thead>
			<tbody>
				<%
					ArrayList<SignalementCount> listSignalementCount = (ArrayList<SignalementCount>) request
															.getAttribute("listSignalementCount");
									if (listSignalementCount!=null)			
				for (SignalementCount signaleCount : listSignalementCount) {
														String lienDetailOrganisateur = "DetailParticipant?idparticipant="
																+ signaleCount.getIdpersonne();
											
				%>

				<tr>

					<td><a href=<%out.println(lienDetailOrganisateur);%>> <%=signaleCount.getPseudo()%>
					</a></td>

					<td>
						<%=signaleCount.getNbrSignalement()	%>

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