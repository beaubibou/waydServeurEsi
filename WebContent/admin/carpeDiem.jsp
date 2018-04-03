<%@page import="servlet.EffaceActiviteCarpediem"%>
<%@page import="servlet.pro.CreerUserPro"%>
<%@page import="servlet.Connexion"%>
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
		boolean verifieemail = Connexion.verifieEmail;
		boolean clemapadmin = Outils.clemapadmin;

		//boolean desativeCompte = CreerUserPro.compteDesactiveParDefaut;

		//LogDAO.LOG_PERF_REQUETE=true;
	%>
	<div class="container">
		<h2>Paramétres</h2>

		<form action="/wayd/ImportCarpeDiem" method="post">

			<div class="form-group">
				<label for="maxLogs">Date</label> <input name="date" type="text"
					placeholder="jj.mm.yyyy" class="form-control" id="maxlogs">
			</div>
			<div class="form-group">
				<label for="nbrLogSupprimer">Ville</label> <input type="text"
					class="form-control" name="ville">
			</div>

			<input type="text" name="token" placeholder="token fb" ></input>
			<button type="submit" class="btn btn-default">Charge carpeDiem</button>
	
	
		</form>

		<form action="/wayd/EffaceActiviteCarpediem" method="post">

		<input type="hidden" name="action" value="<%=EffaceActiviteCarpediem.RAZ_CARPEDIEM %>">
		<button type="submit" class="btn btn-default">Efface</button>
		</form>
		
		<form action="/wayd/EffaceActiviteCarpediem" method="post">

		<input type="hidden" name="action" value="<%=EffaceActiviteCarpediem.PREPARE_TEST_FIREBASE %>">
		
		<button type="submit" class="btn btn-default">Prepare test firebase</button>
		
		</form>
	</div>

</body>




</html>