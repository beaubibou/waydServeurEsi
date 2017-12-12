<%@page import="website.metier.ProfilBean"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
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
			<a class="navbar-brand" href="Acceuil">WAYD</a>

		</div>
		<ul class="nav navbar-nav">

			<li><a href="ListActivite">Activités</a></li>
			<li><a href="ListProbleme">Problèmes</a></li>
			<li><a href="ListSuggestion">Suggestions</a></li>
			<li><a href="ListProfil">Profils</a></li>
			<li><a href="MesActivites">Site pro</a></li>
			<li><a href="MapAdmin">Carte</a></li>
			<li><a href="ListLogs">Logs</a></li>
			<li><a href="DeconnexionAdmin">Déconnexion</a></li>

		</ul>

		<ul class="nav navbar-nav navbar-right">
			<li><a href="#"><span style='color: green;' class="glyphicon glyphicon-king"></span>
					Mode Admin</a></li>

		</ul>

	</div>
</nav>

