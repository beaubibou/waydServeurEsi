<%@page import="website.dao.CacheValueDAO"%>
<%@page import="website.metier.TypeActiviteBean"%>
<%@page import="website.metier.ProfilBean"%>
<%@page import="website.metier.Outils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="website.metier.AuthentificationSite"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Renseignements</title>
<meta charset="utf-8">
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
<link href="/wayd/css/style.css" rel="stylesheet" type="text/css">




</head>
<body>

	<%
	AuthentificationSite authentification = new AuthentificationSite(
			request, response);
			if (!authentification.isAuthentifieAdmin())
		return;
	ArrayList<TypeActiviteBean> listPhotoTypeActivite=(ArrayList<TypeActiviteBean>)request.getAttribute("listPhotoTypeActivite");
	%>
	
	<div class="container">
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-8 col-md-offset-2 col-sm-8">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">
					<div class="panel-title">Mon compte</div>
				</div>
				<div style="padding-top: 30px" class="panel-body">

<%	for (TypeActiviteBean typeactivite:listPhotoTypeActivite){ %>
					<div class="form-group">
						<div class="row">
							<div class="col-sm-8">
								<img height="80" width="80"
									class="img-circle" />
							</div>
						</div>
						<p><%=typeactivite.getLibelle() %>
						
									<img height="80" width="80" src=<%out.println(Outils.getUrlPhoto(typeactivite.getPhoto()));%> class="img-circle"
									class="text-center" />
						
						<form action="/wayd/ChargePhotoActivite?idTypeActivite=<%=typeactivite.getId() %>&libelle=<%=typeactivite.getLibelle() %>" method="post"
							enctype="multipart/form-data">
							<input type="file" name="file" size="50" /> <br /> <input
								type="submit" value="Upload File" />
						</form>
<%} %>
					</div>
				</div>
			</div>
		</div>
	</div>



</body>
</html>
