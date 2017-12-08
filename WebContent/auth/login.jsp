<!DOCTYPE html>

<%@page import="texthtml.pro.LoginTxt"%>
<%@page import="website.enumeration.AlertJsp"%>
<%@page import="website.html.*"%>
<html lang="en">
<head>
<title><%=LoginTxt.TITRE_ONGLET %></title>
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
				alt="Cinque Terre" width="100" height="100"><%=LoginTxt.JUMBO_TITRE %></h1>
		</div>
	
	
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

					<div class="panel-title"><%=LoginTxt.TITRE_PANEL %></div>
					<div
						style="float: right; font-size: 80%; position: relative; top: -10px">
						<a href="motdepasseoublie.jsp">Mot de passe oublié?</a>
					</div>
				</div>

				<div style="padding-top: 30px" class="panel-body">

					<div style="display: none" id="login-alert"
						class="alert alert-danger col-sm-12"></div>

					<form id="loginform" class="form-horizontal" role="form">

						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-user"></i></span> <input id="login-username"
								type="email" class="form-control" name="email"
								 placeholder="<%=LoginTxt.HINT_EMAIL %>">
						</div>

						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-lock"></i></span> <input id="login-password"
								type="password" class="form-control" name="pwd"
								placeholder="<%=LoginTxt.HINT_MOT_DE_PASSE%>">
						</div>


						<div style="margin-top: 10px" class="form-group">
							<!-- Button -->

					 	<div class="col-sm-12 controls">
				
					<!--			<a id="btn-fblogin" onclick="popup()" class="btn btn-primary">Login
									with Google</a> 
					 -->		
									<a id="btn-password" onclick="signPassword()"
									class="btn  btnwayd">Se connecter</a>
							
									<a id="btn-primary" href="/wayd/auth/redemandeConfirmationMail.jsp"
									class="btn  btnwayd">Mail de confirmation non recu?</a>

							
						</div>

						
						<br>
						<div class="form-group">
							<div class="col-md-12 control">
							<br>
								<div
									style="border-top: 1px solid #888; padding-top: 15px;padding-left:15px; font-size: 85%">
									Pas de compte? <a href="/wayd/CreerUserPro">
										Inscrivez-vous. </a>
								</div>
							
							</div>
												
						</div>
						
						
					</form>

</div>

				</div>
			</div>
		</div>
		
	<script>


function popup(){
	
	var provider = new firebase.auth.GoogleAuthProvider();
	firebase.auth().signInWithPopup(provider).then(function(result) {
		  // This gives you a Google Access Token. You can use it to access the Google API.
		  var token = result.credential.accessToken;
		  // The signed-in user info.
		  var user = result.user;
		  // ...
		  firebase.auth().currentUser.getToken(/* forceRefresh */ true).then(function(idToken) {
			  // Send token to your backend via HTTPS
			  // ...
			//  document.getElementById("demo").innerHTML ="opo";
			//   document.location.href="/wayd/Connexion?token="+idToken;
			  document.getElementById("token").value =idToken;
			  document.getElementById("formmasque").submit();
			
		 
		  }).catch(function(error) {
			  var errorMessage = error.message;

			  var errorCode = error.code;
			  
			  BootstrapDialog.alert(errorCode);
			  // Handle error
			});
	  
		  
	}).catch(function(error) {
		  // Handle Errors here.
		  var errorCode = error.code;
		  var errorMessage = error.message;
		  // The email of the user's account used.
		  var email = error.email;
		  // The firebase.auth.AuthCredential type that was used.
		  var credential = error.credential;
		  BootstrapDialog.alert(errorCode);
		  // ...
		});
	
	
}


function signPassword(){

	var email= document.getElementById("login-username").value;
	var password= document.getElementById("login-password").value;
	
	
	firebase.auth().signInWithEmailAndPassword(email, password).then(function(firebaseUser) {
		 
		  firebase.auth().currentUser.getToken(/* forceRefresh */ true).then(function(idToken) {
			  // Send token to your backend via HTTPS
			
			//  document.getElementById("login-username").innerHTML ="opo";
			 //  document.location.href="/wayd/Connexion?token="+idToken+"&pwd=1";
			    document.getElementById("token").value =idToken;
			    document.getElementById("pwd").value ="1";
				document.getElementById("formmasque").submit();
		  
		  }).catch(function(error) {
			  // Handle error
			});
	  
		  
	}).catch(function(error) {
		  // Handle Errors here.
		  var errorCode = error.code;
		  var errorMessage = error.message;
		  // The email of the user's account used.
		  var email = error.email;
		  // The firebase.auth.AuthCredential type that was used.
		  var credential = error.credential;

		  if (errorCode=="auth/wrong-password")
			  BootstrapDialog.alert("Mauvais mot de passe ou login incorrect.");
		  else
		  if (errorCode=="auth/user-not-found")
			  BootstrapDialog.alert("Le compte n'existe pas.");

		  else
			  if (errorCode=="auth/user-disabled")
				  BootstrapDialog.alert("Le compte est désactivé.");
			  else
			  BootstrapDialog.alert(errorMessage);
		 
			 
		  // ...
		});
	
}
</script>

</body>
</html>
