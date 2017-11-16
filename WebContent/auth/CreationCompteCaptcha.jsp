<%@page import="website.html.AlertDialog"%>
<%@page import="website.html.MessageAlertDialog"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Création</title>
<meta charset="utf-8">

<script src='https://www.google.com/recaptcha/api.js'></script>


<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/prettify/r298/run_prettify.min.js"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.34.9/css/bootstrap-dialog.min.css"
	rel="stylesheet" type="text/css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.34.9/js/bootstrap-dialog.min.js"></script>
<link href="/wayd/css/styleWayd.css" rel="stylesheet" type="text/css">

</head>
<body>
<div class="container">
	<div class="page-header">
			
			<h1 ><img src="/wayd/img/waydLogoHD.png" style="margin-right:50px;" class="img-rounded"
				alt="Cinque Terre" width="100" height="100">Créer votre compte</h1>
		</div>
		<p>blablal......</p>
		
	<%MessageAlertDialog alerte=(MessageAlertDialog)request.getAttribute("alerte");%>
	<script type="text/javascript">
	<%=AlertDialog.getAlert(alerte) %>

	</script>

	
	
	</div>
	<div class="container">
		<div id="loginbox"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">

					<div class="panel-title">Inscription</div>

				</div>

				<div style="padding-top: 30px" class="panel-body">

					<div style="display: none" id="login-alert"
						class="alert alert-danger col-sm-12"></div>

					<form class="form-horizontal" onsubmit="return valideFormulaire()" action="/wayd/CreerUserPro"
						method="POST" >

						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-user"></i></span> <input id="login-username"
								type="text" class="form-control" name="email" required
								value="pmestivier@club.fr" placeholder="username or email">
						</div>

						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-lock"></i></span> <input id="login-password"
								type="password" class="form-control" name="pwd" value=""
								placeholder="Mot de passe">
						</div>

						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-lock"></i></span> <input
								id="login-password-bis" type="password" class="form-control"
								name="pwd2" value="" placeholder="Réssaisir le mot de passe">
						</div>

						<div style="margin-top: 10px" class="form-group">
							<!-- Button -->

							<div class="col-sm-12 controls">

								<button type="submit" >Soumettre</button>

							</div>
						</div>
						<div class="g-recaptcha"
							data-sitekey="6Ld6TzgUAAAAAMx76Q_NXm3xEJ1vPa799RLMeYLn"></div>

					</form>
				</div>
			</div>
		</div>
	</div>


<script type="text/javascript">

function valideFormulaire(){

	var pwd=$('#login-password').val();
	var pwd1=$('#login-password-bis').val();

	if (pwd!=pwd1){
	    BootstrapDialog.alert('Les mots de passe sont différents');
		return false;
		}

}


</script>
</body>
</html>