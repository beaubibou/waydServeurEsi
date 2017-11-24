<%@page import="website.metier.admin.FiltreJSP"%>
<%@page import="website.metier.TypeActiviteBean"%>
<%@page import="website.metier.ActiviteBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Liste activités</title>
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
		<h2>Liste activites</h2>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>Titre</th>
					<th>Organisateur</th>
					<th>Date fin</th>
					<th>Active</th>
				</tr>
			</thead>
			<tbody>
				<%
					ArrayList<ActiviteBean> listActivite = (ArrayList<ActiviteBean>) request
															.getAttribute("listActivite");
								
						if (listActivite!=null)
							for (ActiviteBean activite : listActivite) {
									String lien = "DetailActivite?idactivite=" + activite.getId()+"&from=listActivite.jsp";
				%>

				<tr>
					<td><a href=<%out.println(lien);%>> <%=activite.getTitre()%></a></td>
					<td><%=activite.getPseudo()%></td>
					<td><%=activite.getDatefinStr()%></td>
					<td><%=activite.isActiveStr()%></td>

				</tr>

				<%
					}
				%>

			</tbody>
		</table>
	</div>

	


</body>
</html>