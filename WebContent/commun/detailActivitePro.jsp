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
<title>Détail activité</title>


<meta name="viewport" content="width=device-width, initial-scale=1">

<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<link
	href="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">
<script
	src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<!-- <script src="src/bootstrap-rating-input.js"></script> -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-rating-input/0.4.0/bootstrap-rating-input.js"></script>


<script src="js/moment.js"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css"
	rel="stylesheet" type="text/css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-rating-input/0.4.0/bootstrap-rating-input.js"></script>

<link href="/wayd/css/styleWayd.css" rel="stylesheet" type="text/css">


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

	<%
		if (authentification.isPro()) {
	%>

	<%@ include file="/pro/menu.jsp"%>
	<%
		} else {
	%>

	<%@ include file="/waydeur/menuWaydeur.jsp"%>
	<%
		}
	%>
	
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