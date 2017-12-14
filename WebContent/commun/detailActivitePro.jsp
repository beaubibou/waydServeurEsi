<%@page import="website.html.OutilsHtml"%>
<%@page import="website.dao.CacheValueDAO"%>
<%@page import="website.metier.ProfilBean"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="website.metier.ParticipantBean"%>
<%@page import="website.metier.Outils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="website.metier.ActiviteBean"%>
<%@page import="website.metier.AuthentificationSite"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
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

<link href="/wayd/css/styleWayd.css" rel="stylesheet" type="text/css" />

<link href="/wayd/css/nbrcaractere.css" rel="stylesheet" media="all"
	type="text/css" />


</head>
<body>

	<%
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		if (!authentification.isAuthentifie())
			return;

		ActiviteBean activite = (ActiviteBean) request
				.getAttribute("activite");
		ArrayList<ParticipantBean> listParticipant = activite
				.getListParticipant();
		MenuEnum etatMenu=null;
	%>

	
	<%@ include file="/pro/menu.jsp"%>
	
	
	<div class="container">
		<div id="loginbox"	class="mainbox col-md-8 col-md-offset-2 col-sm-8 margedebut">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">

					<div  class="panel-title ">Détail
						activité professionelle</div>

				</div>


				<div style="padding-top: 30px" class="panel-body">


					<div class="form-group">

						<div class="row">

							<div class='col-sm-1  ' class="text-center">
								<img height="30" width="30"
									src=<%out.println(Outils.getUrlPhoto(CacheValueDAO
					.getPhotoTypeActivite(activite.getTypeactivite())));%>
									class="img-circle" class="text-center" />

							</div>
				

					</div>

				</div>
				<div class="form-group">

					<div class="row vertical-align">
						<div class='col-sm-4'>

							<img height="300" width="300"
								src=<%out.println(Outils.getUrlPhoto(activite.getPhoto()));%>
								class="img-thumbnail" class="text-center" />

						</div>

						<div class='col-sm-6' class="text-center">

							<a
								href="DetailProfilSite?idprofil=<%=activite.getIdorganisateur()%>">
								<h3 style="padding-left: 15px; color: blue;"><%=activite.getPseudo()%></h3>
							</a>

							<h4 style="padding-left: 15px"><%=activite.getTitre()%></h4>
							<h5 style="padding-left: 15px"><%=OutilsHtml.convertStringHtml(activite.getAdresse())%></h5>
							<h5 style="padding-left: 15px">
								à
								<%=activite.calculDistance()%></h5>


						</div>

					</div>


					<div class="form-group">
						</br>
						<h5>
							Type d'activité:
							<%=CacheValueDAO.geLibelleTypeActivite(activite.getTypeactivite())%></h5>


						<h5><%=activite.getHoraireLeAHorizontal()%></h5>

					</div>


					<div class="form-group">
						<label for="description">Description:</label>
						<textarea disabled class="form-control" rows="5" id="description"
							name="description"><%=OutilsHtml.convertStringHtml(activite.getLibelle())%></textarea>
					</div>

				</div>
			</div>
		</div>
	</div>


</div>



</body>
</html>