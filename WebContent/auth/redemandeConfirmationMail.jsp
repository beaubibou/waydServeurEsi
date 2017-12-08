<!DOCTYPE html>

<%@page import="texthtml.pro.RedemandeConfirmationMail"%>
<%@page import="website.enumeration.AlertJsp"%>
<%@page import="website.html.*"%>
<html lang="en">
<head>
<title><%=RedemandeConfirmationMail.TITRE_ONGLET %></title>
<meta charset="utf-8">

<script src="https://www.gstatic.com/firebasejs/4.1.2/firebase.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/prettify/r298/run_prettify.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.34.9/css/bootstrap-dialog.min.css" rel="stylesheet" type="text/css" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.34.9/js/bootstrap-dialog.min.js"></script>
	
<link href="/wayd/css/styleWayd.css" rel="stylesheet" type="text/css">
<script src="/wayd/js/initfirebase.js"></script>

</head>
<body>

<div class="container" >
	<div class="page-header">
			
			<h1 ><img src="/wayd/img/waydLogoHD.png" style="margin-right:50px;" class="img-rounded"
				alt="Cinque Terre" width="100" height="100"><%=RedemandeConfirmationMail.JUMBO_TITRE %></h1>
		</div>
		<p><%=RedemandeConfirmationMail.JUMBO_LIGNE1 %></p>
		<p> <%=RedemandeConfirmationMail.JUMBO_LIGNE2 %> </p>
		
	<br>
	</div>
	<script type="text/javascript">
	<%=new AlertDialog((String)request.getAttribute("messageAlert"),(AlertJsp)request.getAttribute("typeMessage")).getMessage()%>
	</script>
		
	<form id="formmasque" action="/wayd/Connexion" method="post">
		<input id="token" type="hidden" class="form-control" name="token">
		<input id="pwd" type="hidden" class="form-control" name="pwd">
	</form>

	<div class="container">
		<div id="loginbox"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-default" >
				<div class="panel-heading panel-heading-custom">

					<div class="panel-title"><%=RedemandeConfirmationMail.TITRE_PANEL %></div>
			
				</div>

				<div style="padding-top: 30px" class="panel-body">

					<div style="display: none" id="login-alert"
						class="alert alert-danger col-sm-12"></div>

					<form id="loginform" class="form-horizontal" role="form">

						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-user"></i></span> <input id="login-username"
								type="email" class="form-control" name="email"
								 placeholder="<%=RedemandeConfirmationMail.HINT_EMAIL %>">
						</div>

						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-lock"></i></span> <input id="login-password"
								type="password" class="form-control" name="pwd" 
								placeholder="<%=RedemandeConfirmationMail.HINT_MOT_DE_PASSE %>">
						</div>

						<div style="margin-top: 10px" class="form-group">
							<!-- Button -->

					 	<div class="col-sm-12 controls">
				
						
									<a id="btn-password" onclick="envoiDemande()"
									class="btn btnwayd ">Envoyer la demande</a>
								 <a href="/wayd/Home"  class="btn btnwayd"><span
								  class="glyphicon glyphicon-home" ></span> Accueil</a>
									</div>

						
						</div>

					
						
						
					</form>



				</div>
			</div>
		</div>
	</div>

	<script>


function envoiDemande(){

	var email= document.getElementById("login-username").value;
	var password= document.getElementById("login-password").value;

	firebase.auth().signInWithEmailAndPassword(email, password).then(function(firebaseUser) {
		 
		firebase.auth().currentUser.sendEmailVerification().then(function() {
			  // Email sent.

			BootstrapDialog.alert('Bienvenue un email de confirmation vous a été envoyé', function(){
				document.location.href="/wayd/auth/login.jsp";
			});
			
		}).catch(function(error) {
			  // An error happened.
		  var errorMessage = error.message;
			 
			BootstrapDialog.alert(errorMessage, function(){
				document.location.href="/wayd/auth/login.jsp";   
			});

				});
	  
		  
	}).catch(function(error) {
		  // Handle Errors here.
		  var errorCode = error.code;
		  var errorMessage = error.message;
		  // The email of the user's account used.
		  var email = error.email;
		  // The firebase.auth.AuthCredential type that was used.
		  var credential = error.credential;
		  BootstrapDialog.alert(errorMessage, function(){
				document.location.href="/wayd/auth/login.jsp";
			});

		  // ...
		});
	

	

	
}
</script>

</body>
</html>
