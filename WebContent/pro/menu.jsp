
<%@page import="website.html.MenuHtml"%>
<%@page import="website.enumeration.*"%>

<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#myNavbar">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>

			<img src="/wayd/img/waydLogoLR.png" class="img-rounded"
				alt="Cinque Terre" width="30" height="30">

		</div>

		<div class="collapse navbar-collapse" id="myNavbar">
			<ul class="nav navbar-nav">
				<li <%=MenuHtml.getActiviteClass(etatMenu, MenuEnum.home)%>><a
					href="/wayd/Home">Acceuil</a></li>
				<li <%=MenuHtml.getActiviteClass(etatMenu, MenuEnum.moncompte)%>><a
					href="/wayd/ComptePro">Mon compte</a></li>
				<li
					<%=MenuHtml
					.getActiviteClass(etatMenu, MenuEnum.mesactivites)%>><a
					href="/wayd/MesActivites">G�rer </a></li>

				<li
					<%=MenuHtml.getActiviteClass(etatMenu,
					MenuEnum.ajouteactivite)%>
					class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">Proposez <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="/wayd/AjouteActivitePro">Proposez une
								activit�</a></li>
						<li><a href="/wayd/AjouteActivitePlanifiee">Planifiez
								plusieurs activit�s</a></li>

					</ul></li>



				<li <%=MenuHtml.getActiviteClass(etatMenu, MenuEnum.carte)%>><a
					href="/wayd/MapPro">Carte</a></li>
				<li <%=MenuHtml.getActiviteClass(etatMenu, MenuEnum.apropos)%>><a
					href="/wayd/Apropos">A propos</a></li>
				<li><a href="/wayd/Contact">Contact</a></li>

			</ul>
			<ul class="nav navbar-nav navbar-right">
				<%if (request.getSession().getAttribute("profil")!=null) {%>
				<li><a href="/wayd/Deconnexion"><span
						class="glyphicon glyphicon-log-out"></span> Log out</a></li>
				<%} else
						{ %>

				<li><a href="/wayd/Deconnexion"><span
						class="glyphicon glyphicon-log-in"></span> Log in</a></li>
				<%} %>

				
			</ul>
		</div>
	</div>
</nav>
