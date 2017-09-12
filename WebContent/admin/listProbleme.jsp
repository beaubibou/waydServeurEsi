<%@page import="website.metier.ProblemeBean"%>
<%@page import="website.metier.ActiviteBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Liste problèmes</title>
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
<% %>
	<div class="container">

		<h3>Critères</h3>
	
	
		<form class="form-inline" method="post" action="ListActivite">

			<div class="form-group">
				<label for="datedebut">Date début:</label> <input type="date"
					class="form-control" id="datedebut" name="datedebut">

				<label for="datefin">Date fin:</label> <input type="date"
					class="form-control" id="datefin" name="datefin" >

				<div class="form-group">
					<button id="go" type="submit" class="btn btn-default">Rechercher</button>

				</div>
			</div>
		</form>
	</div>

	<div class="container">
		<h2>Liste problemes</h2>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>Date</th>
					<th>Problème</th>
					<th>Email</th>
				
				</tr>
			</thead>
			<tbody>
				<%
					ArrayList<ProblemeBean> listProbleme = (ArrayList<ProblemeBean>) request
									.getAttribute("listProbleme");
							for (ProblemeBean prb : listProbleme) {
							//	String lien = "DetailActivite?idactivite=" + prb.getId()+"&from=listActivite.jsp";
					
				%>

				<tr>
				<td>
						<%
							out.println(prb.getDateCreationStr());
						%>
					</td>
				
					<td> <%out.println(prb.getProbleme());%>
					</td>
					
					<td>
						<%
							out.println(prb.getEmail());
						%>
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