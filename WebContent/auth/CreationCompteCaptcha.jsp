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
<link href="/wayd/css/style.css" rel="stylesheet" type="text/css">

</head>
<body>
	<div class="container">

		<div class="page-header">

			<img src="/wayd/img/waydLogoLR.png" class="img-rounded"
				alt="Cinque Terre" width="30" height="30">

			<h1>Inscrivez vous, pour entrer dans la communauté</h1>
		</div>
		<p>Blabla, blabla</p>

	</div>
	<div class="container">
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">

					<div class="panel-title">Inscription</div>

				</div>

				<div style="padding-top: 30px" class="panel-body">

					<div style="display: none" id="login-alert"
						class="alert alert-danger col-sm-12"></div>

					<form class="form-horizontal" action="/wayd/CreerUserPro"
						method="POST">

						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-user"></i></span> <input id="login-username"
								type="text" class="form-control" name="email"
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

								<button type="submit" method="post" action=""kl">Soumettre</button>

							</div>
						</div>
						<div class="g-recaptcha"
							data-sitekey="6Ld6TzgUAAAAAMx76Q_NXm3xEJ1vPa799RLMeYLn"></div>

					</form>
				</div>
			</div>
		</div>
	</div>



</body>
</html>