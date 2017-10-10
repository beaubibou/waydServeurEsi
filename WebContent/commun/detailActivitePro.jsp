<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@page import="website.metier.ParticipantBean"%>
<%@page import="website.metier.Outils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="website.metier.ActiviteBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>>Détail activité pro</title>
<meta charset="utf-8">



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

<link href="/wayd/css/style.css" rel="stylesheet" type="text/css">


<style>
.vcenter {
	display: inline-block;
	vertical-align: middle;
	float: none;
}

.vertical-align {
	display: flex;
	align-items: center;
}
</style>
</head>
<body>

	<%@ include file="/pro/menu.jsp"%>
<%
		ActiviteBean activite = (ActiviteBean) request
				.getAttribute("activite");
		ArrayList<ParticipantBean> listParticipant = activite
				.getListParticipant();
	%>

	<div class="container">
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">

					<div class="panel-title">Détail activité</div>

				</div>

				<div style="padding-top: 30px" class="panel-body">


					<div class="form-group">

						<div class="row">
							<div class='col-sm-9'>
							<a class="btn btn-danger"
								href="SignalerActivite?idActivite=<%=activite.getId()%>"
								role="button">Signaler</a> 
							</div>

							<div class='col-sm-2  ' class="text-center">
								<a class="btn btn-info" href="SiteWeb" role="button">Site
									Web</a>

							</div>

						</div>

					</div>
					<div class="form-group">

						<div class="row vertical-align" style="border: 1px solid">
							<div class='col-sm-3'>

								<img height="80" width="80" src="image.jpeg" class="img-circle"
									class="text-center" />
							</div>

							<div class='col-sm-6' class="text-center">

								<a
									href="DetailProfilSite?idprofil=<%=activite.getIdorganisateur()%>">
									<h3 style="padding-left: 15px; color: blue;"><%=activite.getPseudo()%></h3>
								</a>

								<h4 style="padding-left: 15px">Titre:</h4>

								<h5 style="padding-left: 15px">Adresse:</h5>


							</div>

						</div>


						<div class="form-group">
							</br>
							<h4>Type d'activité: Sport</h4>
							<h4>De 8h00 à 10H00 à 1km</h4>

						</div>


						<div class="form-group">
							<label for="description">Description:</label>
							<textarea class="form-control" rows="5" id="description"
								name="description"></textarea>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>





</body>
</html>