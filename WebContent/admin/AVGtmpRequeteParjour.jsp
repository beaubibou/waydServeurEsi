<%@page import="website.metier.CountLogInfo"%>
<%@page import="website.metier.TypeEtatLogPerf"%>
<%@page import="website.dao.LogDAO"%>
<%@page import="website.metier.LogBean"%>
<%@page import="website.pager.PagerLogsBean"%>
<%@page import="website.metier.admin.FitreAdminLogs"%>
<%@page import="website.metier.TypeEtatLogs"%>
<%@page import="website.pager.PagerSuggestionBean"%>
<%@page import="website.metier.admin.FitreAdminSuggestions"%>
<%@page import="website.metier.admin.EtatSuggestion"%>
<%@page import="website.metier.SuggestionBean"%>
<%@page import="website.metier.ProblemeBean"%>
<%@page import="website.metier.ActiviteBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="website.dao.CacheValueDAO"%>
<%@page import="website.metier.Outils"%>
<%@page import="website.metier.AuthentificationSite"%>
<%@page import="website.pager.PagerSuggestionBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Logs</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

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

<script src="js/moment.js"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css"
	rel="stylesheet" type="text/css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<link href="/wayd/css/styleWaydAdmin.css" rel="stylesheet"
	type="text/css">
<link href="/wayd/css/nbrcaractere.css" rel="stylesheet" media="all"
	type="text/css">
</head>

<body>

	<%@ include file="menu.jsp"%>
	<%
		AuthentificationSite authentification = new AuthentificationSite(
			request, response);
			if (!authentification.isAuthentifieAdmin())
		return;
		
		ArrayList<CountLogInfo> listCountInfo = LogDAO.avgTpsRequeteJour();
		
			//LogDAO.LOG_PERF_REQUETE=true;
	%>


	<div class="container" style="width: 90%;">

		<table class="table table-striped">
			<thead>
				<tr>
					<th style="width: 10%;">Date</th>
					<th style="width: 10%;">Tps moyen</th>
				


				</tr>
			</thead>
			<tbody>
				<%
					for (CountLogInfo countLogInfo : listCountInfo) {
				%>

				<tr >

					<td ><%=countLogInfo.getDate()%></td>
					<td><%=countLogInfo.getLog_level() %> ms</td>
				</tr>
					<%
						}
					%>
				
			</tbody>
		</table>
	</div>


	


</body>




</html>