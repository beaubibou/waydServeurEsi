<!DOCTYPE html>

<%@page import="website.enumeration.AlertJsp"%>
<%@page import="website.html.*"%>
<html lang="en">
<head>
<title>Login</title>
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
				alt="Cinque Terre" width="100" height="100">Connectez vous</h1>
		</div>
		<p>blablal......</p>
		
	
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

					<div class="panel-title">Connectez vous</div>
					<div
						style="float: right; font-size: 80%; position: relative; top: -10px">
						<a href="form_forget_password.html">Mot de passe oublié?</a>
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
								value="pmestivier@club.fr" placeholder="username or email">
						</div>

						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-lock"></i></span> <input id="login-password"
								type="password" class="form-control" name="pwd" value="azerty"
								placeholder="password">
						</div>

<!-- 
						<div class="input-group">
							<div class="checkbox">
								<label> <input id="login-remember" type="checkbox"
									name="remember" value="1"> Remember me
								</label>
							</div>
						</div>

 -->
						<div style="margin-top: 10px" class="form-group">
							<!-- Button -->

					 	<div class="col-sm-12 controls">
				
					<!--			<a id="btn-fblogin" onclick="popup()" class="btn btn-primary">Login
									with Google</a> 
					 -->			<div class="btn-group">		
									<a id="btn-password" onclick="signPassword()"
									class="btn btn-primary">Se connecter</a>
								 <a href="/wayd/Home"  class="btn btn-info"><span
								  class="glyphicon glyphicon-home" ></span> Accueil</a>
									</div>

							</div>
						</div>

						<div class="form-group">
						
							<div class="col-md-12 control">
								<div
									style="border-top: 1px solid #888; padding-top: 15px; font-size: 85%">
									Pas de compte! <a href="/wayd/CreerUserPro">
										Inscrivez vous!! </a>
								</div>
							
							</div>
												
						</div>
						
						
					</form>



				</div>
			</div>
		</div>
		<div id="signupbox" style="display: none; margin-top: 50px"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">Sign Up</div>
					<div
						style="float: right; font-size: 85%; position: relative; top: -10px">
						<a id="signinlink" href="#"
							onclick="$('#signupbox').hide(); $('#loginbox').show()">Sign
							In</a>
					</div>
				</div>
				<div class="panel-body">
					<form id="signupform" class="form-horizontal" role="form">

						<div id="signupalert" style="display: none"
							class="alert alert-danger">
							<p>Error:</p>
							<span></span>
						</div>

						<div class="form-group">
							<label for="email" class="col-md-3 control-label">Email</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="email"
									placeholder="Email Address">
							</div>
						</div>

						<div class="form-group">
							<label for="firstname" class="col-md-3 control-label">First
								Name</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="firstname"
									placeholder="First Name">
							</div>
						</div>
						<div class="form-group">
							<label for="lastname" class="col-md-3 control-label">Last
								Name</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="lastname"
									placeholder="Last Name">
							</div>
						</div>
						<div class="form-group">
							<label for="password" class="col-md-3 control-label">Password</label>
							<div class="col-md-9">
								<input type="password" class="form-control" name="passwd"
									placeholder="Password">
							</div>
						</div>

						<div class="form-group">
							<label for="icode" class="col-md-3 control-label">Invitation
								Code</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="icode"
									placeholder="">
							</div>
						</div>

						<div class="form-group">
							<!-- Button -->
							<div class="col-md-offset-3 col-md-9">
								<button id="btn-signup" type="button" class="btn btn-info">
									<i class="icon-hand-right"></i> &nbsp Sign Up
								</button>
								
								<span style="margin-left: 8px;">or</span>
							</div>
						</div>

						<div style="border-top: 1px solid #999; padding-top: 20px"
							class="form-group">

							<div class="col-md-offset-3 col-md-9">
								<button id="btn-fbsignup" type="button" class="btn btn-primary">
									<i class="icon-facebook"></i> Â  Sign Up with Facebook
								</button>
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
			  
			  BootstrapDialog.alert(errorMessage);
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
		  BootstrapDialog.alert(errorMessage);
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
		  BootstrapDialog.alert(errorMessage);
		  // ...
		});
	
}
</script>

</body>
</html>
