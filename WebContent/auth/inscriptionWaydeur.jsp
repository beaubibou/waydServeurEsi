<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="website.metier.ProfilBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
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

<script>
	var latitude = 0;
	var longitude = 0;
</script>

</head>
<body>

	<%
	
	%>

	<div class="container">
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">
					<div class="panel-title">Inscription</div>

				</div>

				<div style="padding-top: 30px" class="panel-body">



					<form action="../Form_PremierProfil"
						onsubmit="return valideFormulaire()">
						<div class="form-group">
							<label for="nom">Nom*:</label> <input type="text"
								class="form-control"  name="nom"
								required>
						</div>

						<div  class="form-group">
							<label for="sexe">Vous �tes:</label> <select class="form-control"
								id="idsexe" name="sexe">
								<option value="1">Homme</option>
								<option value="2">Femme</option>
							</select>
						</div>
						
							<input type="hidden" class="form-control" 
							name="typeuser" required value="3">
						
						

						<div class="form-group">
							<label for="commentaire">Renseignements:</label>
							<textarea class="form-control" rows="5" id="commentaire"
								name="commentaire"></textarea>
						</div>

						</br>
						<button type="submit" class="btn btn-info">Enregistrer</button>
			
					</form>


				</div>
			</div>
		</div>


	


</body>
</html>