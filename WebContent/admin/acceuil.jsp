<%@page import="texthtml.pro.AccueilProText"%>
<%@page import="website.metier.IndicateurWayd"%>
<%@page import="website.metier.ProfilBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<title><%=AccueilProText.TITRE_ONGLET %></title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<link href="/wayd/css/styleWaydAdmin.css" rel="stylesheet"
	type="text/css">
</head>
<body>
<%@ include file="menu.jsp"%>
		<%
		IndicateurWayd indicateurBean=(IndicateurWayd)request.getAttribute("indicateur");
		ProfilBean profil=(ProfilBean)session.getAttribute("profil");
		%>
	
	<h1 align="center">
		Bienvenue <%=profil.getPseudo()%>
	</h1>
	

	
	</div>
	<div class="container">
		<h3>Statistiques</h3>
		<ul class="list-group">
			<li class="list-group-item">Nombre activités total <span
				class="badge"><%=indicateurBean.getNbrTotalActivite() %></span></li>
			
			<li class="list-group-item">Nombre de participation totale <span
				class="badge"><%=indicateurBean.getNbrTotalParticipation() %></span></li>
		
			<li class="list-group-item">Nombre d'inscrit
		  <span class="badge"><%=indicateurBean.getNbrTotalInscrit()%></span></li>
		
		  	<li class="list-group-item"><a href="HistoriqueChart?action=histoNbrMessage">Nombre de message du jour</a>
			  <span class="badge" ><%=indicateurBean.getNbrMessageDuJour()%></span></li>
		
		   	<li class="list-group-item"><a href="HistoriqueChart?action=histoNbrMessageByAct">Nombre de message du jour par activité</a>
		  <span class="badge"><%=indicateurBean.getNbrMessageByActDuJour() %></span></li>
		
		   	<li class="list-group-item"><a href="HistoriqueChart?action=histoNbrParticipation">Nombre de participations du jour</a>
		  <span class="badge"><%=indicateurBean.getNbrParticipationDuJour() %></span></li>
		
		   	<li class="list-group-item"><a href="HistoriqueChart?action=histoNbrActivite">Nombre d'activités du jour</a>
		  <span class="badge" ><%=indicateurBean.getNbrActiviteDuJour()%></span></li>
	
		</ul>
	</div>

</body>
</html>