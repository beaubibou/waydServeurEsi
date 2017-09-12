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
			<a class="navbar-brand" href="Acceuil">WAYD</a>

		</div>
		<ul class="nav navbar-nav">

			<li><a href="ListActivite">Activités</a></li>
			<li><a href="ListProbleme">Problèmes</a></li>
			<li><a href="ListSuggestion">Suggestions</a></li>
			<li><a href="ListSignalementProfils">Signalements Profils</a></li>
			<li><a href="ListSignalementActivite">Signalements Activités</a></li>
			<li><a href="ListProfil">Profils</a></li>
			<li><a href="Controleur?action=deconnexion">Déconnexion</a></li>

		</ul>



	</div>
</nav>

