<%@page import="website.metier.ProblemeBean"%>
<%@page import="website.metier.ActiviteBean"%>
<%@page import="java.util.ArrayList"%>
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
		
	%>
	<div class="container">

		<h3>Critères</h3>

		<form class="form-inline" method="post" action="ListActivite">
			<div class="form-group">
				<label for="pseudo">Pseudo:</label> <input type="date"
					class="form-control" id="datedebut" name="datedebut">
				<div class="form-group">
					<button id="go" type="submit" class="btn btn-default">Rechercher</button>
				</div>
			</div>
		</form>
	</div>

	<div class="container">
		<h2>Liste profils</h2>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>photo</th>
					<th>Pseudo</th>
					<th>Date création</th>



				</tr>
			</thead>
			<tbody>
				<%
					ArrayList<ProfilBean> listProfil = (ArrayList<ProfilBean>) request
							.getAttribute("listProfil");
					for (ProfilBean profil : listProfil) {
						String lien = "DetailParticipant?idparticipant="
								+ profil.getId();
				%>

				<tr>
					<td><img height="30" width="30" src=<%=profil.getUrlPhoto()%>
						class="img-circle" /></td>
					<td><a href=<%=lien%>><%=profil.getPseudo()%></a></td>

					<td><%=profil.getDatecreationStr()%></td>

			</tr>



				<%
					}
				%>

			</tbody>
		</table>
	</div>




</body>
</html>