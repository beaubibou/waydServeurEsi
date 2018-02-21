<%@page import="website.metier.ProfilBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>

<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">

			<%
				if (session.getAttribute("profil") != null) {

					ProfilBean profil = (ProfilBean) session.getAttribute("profil");
					out.println("<a class=\"navbar-brand\">" + "Bienvenue "
							+ profil.getPseudo() + "</a>");
				}
			%>
			<a class="navbar-brand" href="/wayd/Acceuil">WAYD</a>

		</div>
		<ul class="nav navbar-nav">

			<li><a href="/wayd/ListActivite">Activités</a></li>
			<li><a href="/wayd/ListProbleme">Problèmes</a></li>
			<li><a href="/wayd/ListSuggestion">Suggestions</a></li>
			<li><a href="/wayd/ListProfil">Profils</a></li>
			<li><a href="/wayd/MesActivites">Site pro</a></li>
		
		 <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Cartes
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
			<li><a href="/wayd/MapAdmin">Carte Activites</a></li>
			<li><a href="/wayd/MapUser">Carte User</a></li>
		    </ul>
      </li>	
			
			<li><a href="/wayd/ListLogs">Logs</a></li>
			<li><a href="/wayd/StatLog">StatLogs</a></li>
			<li><a href="/wayd/admin/AVGtmpRequeteParjour.jsp">Performance</a></li>
			<li><a href="/wayd/admin/paramAdmin.jsp">Paramétres</a></li>
			<li><a href="/wayd/DeconnexionAdmin">Déconnexion</a></li>

		</ul>

		<ul class="nav navbar-nav navbar-right">
			<li><a href="#"><span style='color: green;' class="glyphicon glyphicon-king"></span>
					Mode Admin</a></li>

		</ul>

	</div>
</nav>

