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
<%@ page language="java" contentType="text/html; charset=utf-8"
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
		
			ArrayList<TypeEtatLogs> listEtatLog = CacheValueDAO.getListTypeEtatLogs();
			ArrayList<TypeEtatLogPerf> listEtatLogPerf = CacheValueDAO.getListTypeEtatLogPerf();
		
		
			FitreAdminLogs filtreLogs=(FitreAdminLogs)session.getAttribute("filtreLogs");
		
		PagerLogsBean pager=(PagerLogsBean) request
				.getAttribute("pager");
		
		ArrayList<LogBean> listLogs = pager.getListLogs();
		
	
	
		//LogDAO.LOG_PERF_REQUETE=true;
	%>
	<div class="container" style="width: 90%;">
		<div class="panel panel-primary">
			<div class="panel-body" style="background: #99ccff;">
				<form class="form-inline" action="ListLogs" id="formulaire">
					<div class="form-group">
						<label for="idEtatProbleme">Etat</label> <select
							data-style="btn-primary" class="form-control"
							id="idEtatSuggestion" name="etatLogs">

							<%
								for (TypeEtatLogs etatLogs:listEtatLog) {
							%>
							<option value="<%=etatLogs.getId()%>"
								<%=Outils.jspAdapterListSelected(etatLogs.getId(), filtreLogs.getNiveau_log())%>>
								<%=etatLogs.getLibelle()%></option>
							<%
								}
							%>

						</select>
					</div>
					<div class="form-group">
						<label for="iddatedebut">Date debut</label>
						<div class='input-group date' id='datedebut'>
							<input type='text' class="form-control" id="iddatedebut"
								name="debut" /> <span class="input-group-addon"> <span
								class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>



					<div class="form-group">
						<label for="iddatefin">Date fin</label>
						<div class='input-group date' id="datefin">
							<input type='text' class="form-control" id="iddatefin" name="fin" />
							<span class="input-group-addon"> <span
								class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>


					<div class="form-group">
						<label for="idEtatProbleme">LogPerf</label> <select
							data-style="btn-primary" class="form-control"
							id="idLogPerf" name="logPerf">

							<%
								for (TypeEtatLogPerf etatPerf:listEtatLogPerf) {
							%>
							<option value="<%=etatPerf.getId()%>"
								<%=Outils.jspAdapterListSelected(etatPerf.getId(), LogDAO.getETAT_PERF())%>>
								<%=etatPerf.getLibelle()%></option>
							<%
								}
							%>

						</select>
					</div>
					
					<button type="submit" class="btn btn-info">Recherchez</button>

				</form>
			</div>
		</div>
	</div>

	<div class="container" style="width: 90%;">

		<table class="table table-striped">
			<thead>
				<tr>
					<th style="width: 10%;">Date</th>
					<th style="width: 10%;">Niveau</th>
					<th class="text-center">Message</th>
					<th style="width: 30%;" class="text-center">Location</th>
					<th style="width: 10%;" class="text-center">Durée</th>


				</tr>
			</thead>
			<tbody>
				<%
					for (LogBean monLog : listLogs) {	
						%>

				<tr>

					<td><%=monLog.getDate_log()%></td>

					<td><%=monLog.getNiveau_log()%></td>

					<td><%=monLog.getMessage()%></td>

					<td><%=monLog.getLocation()%></td>

					<td><%=monLog.getDuree()%></td>


					<%
						}
					%>
				
			</tbody>
		</table>
	</div>
	<ul class="pager">

		<li <%=pager.isPreviousHtml()%>><a
			href="<%=pager.getLienPrevioustHtml()%>">Previous</a></li>
		<li>Page N° <%=pager.getPageEnCours()%></li>
		<li <%=pager.isNextHtml()%>><a
			href="<%=pager.getLienNextHtml()%>">Next</a></li>

	</ul>

	<script>
		$(function() {

		

		    $('#log').change(function() {
		    	document.getElementById("formulaire").submit(); 
		    });

			$('button').click(function() {

				var lien = $(this).attr('id');
				var action = $(this).attr('name')

				if (action == 'envoiMessage')
					location.href = lien;

				if (action == 'supprime')
					location.href = lien;

				if (action == 'lireMessage')
					lireMessage(lien);

			});

			$('#datedebut')
					.datetimepicker(
							{
								defaultDate : new Date(
	<%=filtreLogs.getDateCreationDebut().getYear()%>
		,
	<%=filtreLogs.getDateCreationDebut().getMonthOfYear()%>
		- 1,
	<%=filtreLogs.getDateCreationDebut().getDayOfMonth()%>
		),
								format : 'DD/MM/YYYY'

							}).on('dp.change', function(e) {
						document.getElementById("formulaire").submit();
					});

			$('#datefin')
					.datetimepicker(
							{
								defaultDate : new Date(
	<%=filtreLogs.getDateCreationFin().getYear()%>
		,
	<%=filtreLogs.getDateCreationFin().getMonthOfYear()-1%>
		,
	<%=filtreLogs.getDateCreationFin().getDayOfMonth()%>
		),
								format : 'DD/MM/YYYY'

							}).on('dp.change', function(e) {
						document.getElementById("formulaire").submit();
					});

			$('select').on('change', function() {

				document.getElementById("formulaire").submit();
			});

			

		});
		 

		function lireMessage(lien) {
			location.href = lien;
		}
	</script>


</body>




</html>