<%@page import="website.metier.ProfilBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>
 
 <meta charset="utf-8">
 <meta name="viewport" content="width=device-width, initial-scale=1">

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

			<li><a href="ListActivite">Mes activités</a></li>
			<li><a href="ListProbleme">Ajouter</a></li>
			<li><a href="ListSuggestion">Contact</a></li>
			<li><a href="ListSuggestion">Cartographie</a></li>
			<li><a href="ComptePro">Mon compte</a></li>
			<li><a href="Controleur?action=deconnexion">Déconnexion</a></li>

		</ul>



	</div>
</nav>

