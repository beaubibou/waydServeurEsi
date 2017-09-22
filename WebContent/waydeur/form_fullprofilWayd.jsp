<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>
<%@page import="website.metier.ProfilBean"%>
<%@page import="website.metier.SexeBean"%>
<%@page import="website.metier.Outils"%>
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html lang="fr">
<head>
<title>Mon profil</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

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


<script src="js/moment.js"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css"
	rel="stylesheet" type="text/css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<link href="/wayd/css/style.css" rel="stylesheet" type="text/css">

<script>
	var latitude = 0;
	var longitude = 0;
</script>

</head>
<body>

	<%
		ProfilBean profil = (ProfilBean) session.getAttribute("profil");
			ArrayList<SexeBean> listSexe = (ArrayList<SexeBean>) request.getAttribute("listSexe");
		  
		
			if (profil == null) {

			}
	%>
	<%@ include file="menuWaydeur.jsp"%>

	<div class="container">
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-8 col-md-offset-3 col-sm-8">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">
					<div class="panel-title">Mon profil</div>
				</div>
				<div style="padding-top: 30px" class="panel-body">


					<form action="/wayd/CompteWaydeur" method="post"
						onsubmit="return valideFormulaire()">


						<div class="row">
							<div class="col-sm-2">
								<label for="nom">Pseudo*:</label>
							</div>
							<div class="col-sm-4">
								<input type="text" class="form-control" id="nom"
									placeholder="Nom " name="nom" required
									value="<%out.println(profil.getPseudo());%>">
							</div>
							<div class="col-sm-1">
								<label for="genre">Genre:</label>
							</div>
							<div class="col-sm-3">
								<select class="form-control" id="genre" name="sexe">

									<%
										for (SexeBean sexebean:listSexe){
									%>
									<option value=<%=sexebean.getId() %> 
									<%=Outils.jspAdapterListSelected(profil.getSexe(), sexebean.getId()) %> >
									<%=sexebean.getLibelle() %></option>
									
									<%
										};
									%>
								</select>
							</div>
							<div class="col-sm-2">
								<div class="checkbox">
									<label><input name="afficheSexe" type="checkbox">Afficher</label>
								</div>
							</div>
						</div>

						<div class="row">

							<div class='col-sm-2'>
								<label for="iddateanniversaire">Anniversaire:</label>
							</div>
							<div class='col-sm-4'>
								<div class="form-group">
									<div class='input-group date' id='iddateanniversaire'>

										<input type='text' class="form-control"
											id="iddateanniversaire" name="datenaissance" /> <span
											class="input-group-addon"> <span
											class="glyphicon glyphicon-calendar"></span>
										</span>
									</div>
								</div>
							</div>

							<div class="col-sm-2">
								<div class="checkbox">
									<label><input type="checkbox" name="afficheAge">Afficher</label>
								</div>
							</div>
						</div>



						<div class="form-group">
							<label for="renseignement">Renseignements:</label>
							<textarea class="form-control" rows="5" id="commentaire"
								name="commentaire">	<%=profil.getCommentaireStr().trim()%>				
							</textarea>

						</div>



						<div class="form-group">

							<button type="submit" class="btn btn-info">Modifier</button>
						</div>


					</form>


				</div>
			</div>
		</div>




		<script>
			function valideFormulaire() {
				return true;

			}
		</script>

		<script>
			$(function() {

				$('#iddateanniversaire').datetimepicker({
					defaultDate : new Date,
					format : 'DD/MM/YYYY HH:mm'

				});

			});
		</script>
</body>
</html>
