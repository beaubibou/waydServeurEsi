
<%@page import="texthtml.pro.MenuProText"%>
<%@page import="website.metier.ProfilBean"%>
<%@page import="website.html.MenuHtml"%>
<%@page import="website.enumeration.*"%>
<%@page import="website.dao.MessageDAO"%>
<%@page import="website.metier.AuthentificationSite"%>
<nav class="navbar navbar-inverse navbar-fixed-top" id="menupro">
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
					href="/wayd/Home"><%=MenuProText.ACCUEIL %></a></li>
				<li <%=MenuHtml.getActiviteClass(etatMenu, MenuEnum.moncompte)%>><a
					href="/wayd/ComptePro"><%=MenuProText.MON_COMPTE %></a></li>
				<li
					<%=MenuHtml
					.getActiviteClass(etatMenu, MenuEnum.mesactivites)%>><a
					href="/wayd/MesActivites"><%=MenuProText.GERER %> </a></li>
				<%
					if (request.getSession().getAttribute("profil") != null) {
				%>
				<!--Affiche les message si connecté -->
				<li
					<%=MenuHtml.getActiviteClass(etatMenu,
						MenuEnum.mesmessages)%>><a
					href="/wayd/MesMessages"><span
						class="glyphicon glyphicon-envelope"> </span> <span class="badge">
						<%=MessageDAO.getNbrMessageNonLu(((ProfilBean) request
						.getSession().getAttribute("profil")).getId())%></span></a></li>

				<%
					}
				%>


				<li
					<%=MenuHtml.getActiviteClass(etatMenu,
					MenuEnum.ajouteactivite)%>
					class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown"><%=MenuProText.PROPOSER %> <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a style='color:black;background-color:#FFFFFF;' href="/wayd/AjouteActivitePro"><%=MenuProText.CREER_ACTIVITE %></a></li>
						<li ><a style='color:black;background-color:#FFFFFF;' href="/wayd/AjouteActivitePlanifiee"><%=MenuProText.PLANIFIER_ACTIVITE %></a></li>

					</ul></li>


				<li <%=MenuHtml.getActiviteClass(etatMenu, MenuEnum.carte)%>><a
					href="/wayd/MapPro"><%=MenuProText.CARTE %></a></li>
				<li <%=MenuHtml.getActiviteClass(etatMenu, MenuEnum.apropos)%>><a
					href="/wayd/Apropos"><%=MenuProText.A_PROPOS %></a></li>
				<li <%=MenuHtml.getActiviteClass(etatMenu, MenuEnum.contact)%>><a
					href="/wayd/Contact">Contact</a></li>

			</ul>
			<ul class="nav navbar-nav navbar-right">
				<%
				ProfilBean profiltmp=(ProfilBean)request.getSession().getAttribute("profil") ;
					if (profiltmp!= null) {
				%>
				<li><a style="color:black;"><span
						class="glyphicon glyphicon-user"></span> <%=profiltmp.getPseudo() %></a></li>
			
				<li><a href="/wayd/Deconnexion"><span
						class="glyphicon glyphicon-log-out"></span> Log out</a></li>
			
			
				<%
					} else {
				%>

				<li><a href="/wayd/Deconnexion"><span
						class="glyphicon glyphicon-log-in"></span> Log in</a></li>
				<%
					}
				%>


			</ul>
		</div>
	</div>
</nav>
