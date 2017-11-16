<%@page import="website.metier.ActiviteBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@page import="website.metier.AuthentificationSite"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>>Signaler activité</title>
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


</head>
<body>
	<%
	AuthentificationSite authentification = new AuthentificationSite(
			request, response);
	if (!authentification.isAuthentifie())
		return;
	
	ActiviteBean activite = (ActiviteBean) request
				.getAttribute("activite");
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
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">

					<div class="panel-title">Signaler</div>

				</div>
				<div class="form-group">

					<div class="row">

						<div class='col-sm-5 col-sm-offset-1 '>
							
							<h3>Que se passe-t-il ?</h3>

						</div>
					</div>
				</div>

				<div style="padding-top: 30px" class="panel-body">

					<div class="form-group">

						<div class="row">

							<div class='col-sm-10 col-md-offset-1 '>

								<a style="width:100%" class="btn btn-info"
									href="SignalerActivite?idActivite=<%=activite.getId()%>&idmotif=0"
									role="button">Cette activité est suspecte</a>

							</div>

						</div>

					</div>

					<div class="form-group">

						<div class="row">

							<div class='col-sm-10 col-md-offset-1 '>

								<a style="width:100%"  class="btn btn-info"
									href="SignalerActivite?idActivite=<%=activite.getId()%>&idmotif=1"
									role="button">Elle est dangereuse</a>

							</div>

						</div>
					</div>
					<div class="form-group">

						<div class="row">

							<div class='col-sm-10 col-md-offset-1 '>
							
								<a style="width:100%" class="btn btn-info"
									href="SignalerActivite?idActivite=<%=activite.getId()%>&idmotif=2"
									role="button">Elle est illicite</a>

							</div>

						</div>

					</div>


					<div class="form-group">

						<div class="row">

							<div class='col-sm-10 col-md-offset-1 '>

								<a style="width:100%"  class="btn btn-info"
									href="SignalerActivite?idActivite=<%=activite.getId()%>&idmotif=3"
									role="button">Elle est indiquée gratuite mais est payante</a>

							</div>

						</div>

					</div>
					
					<div class="form-group">

						<div class="row">

								<div class='col-sm-10 col-md-offset-1 '>

								<a style="width:100%"  class="btn btn-info"
									href="SignalerActivite?idActivite=<%=activite.getId()%>&idmotif=4"
									role="button">Autres raisons</a>

							</div>

						</div>

					</div>

					</br>
					<div class="form-group">
						<div class="row">



							<div class='col-sm-12'>

								<label for="description">Description:</label>
								<textarea class="form-control" rows="5" id="description"
									name="description"></textarea>
							</div>

						</div>
					</div>

				</div>

			</div>

		</div>

	</div>
</body>
</html>