<!DOCTYPE html>

<%@page import="texthtml.pro.ChangemetMotDePasse"%>
<%@page import="texthtml.pro.Erreur_HTML"%>

<%@page import="website.enumeration.AlertJsp"%>
<%@page import="website.html.*"%>
<%@page import="website.metier.AuthentificationSite"%>
<html lang="en">
<head>
<title><%=ChangemetMotDePasse.TITRE_ONGLET%></title>
<meta charset="utf-8">

<script src="https://www.gstatic.com/firebasejs/4.1.2/firebase.js"></script>
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
<script src="/wayd/js/initfirebase.js"></script>

</head>
<body>
	<%
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);

		if (!authentification.isAuthentifie())
			return;

		String email = authentification.getProfil().getEmail();
		
	%>

	<div class="container">
		<div class="page-header">

			<h1>
				<img src="/wayd/img/waydLogoHD.png" style="margin-right: 50px;"
					class="img-rounded" alt="Cinque Terre" width="100" height="100"><%=ChangemetMotDePasse.JUMBO_TITRE%></h1>
		</div>


		<br>
	</div>


	<form id="formmasque" action="/wayd/Connexion" method="post">
		<input id="token" type="hidden" class="form-control" name="token">
		<input id="pwd" type="hidden" class="form-control" name="pwd">
	</form>

	<div class="container">
		<div id="loginbox"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">

					<div class="panel-title"><%=ChangemetMotDePasse.HINT_MOT_DE_PASSE%></div>

				</div>

				<div style="padding-top: 30px" class="panel-body">

					<div style="display: none" id="login-alert"
						class="alert alert-danger col-sm-12"></div>

					<form method="post" action="klk" id="loginform"
						class="form-horizontal" role="form">

						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-user"></i></span> <input id="ancienMdp"
								type="password" class="form-control" name="ancienMdp"
								value="azerty"
								placeholder="<%=ChangemetMotDePasse.HINT_ANCIEN_MOT_DE_PASSE%>">
						</div>

						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-user"></i></span> <input id="motDePasse"
								type="password" class="form-control" name="motDePasse"
								value="azerty"
								placeholder="<%=ChangemetMotDePasse.HINT_MOT_DE_PASSE%>">
						</div>



						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-lock"></i></span> <input id="motDePasseBis"
								type="password" class="form-control" name="motDePasseBis"
								value="azerty"
								placeholder="<%=ChangemetMotDePasse.HINT_MOT_DE_PASSE_BIS%>">
						</div>

						<div style="margin-top: 10px" class="form-group">
							<!-- Button -->

							<div class="col-sm-12 controls">

								<a id="btn-password" onclick="envoiDemande()"
									class="btn btnwayd ">Envoyer la demande</a> <a
									href="/wayd/ComptePro" class="btn btn-info"><span
									class="glyphicon glyphicon-arrow-left"></span> </a>
							</div>


						</div>

					</form>
				</div>
			</div>
		</div>
	</div>
	<script>


function envoiDemande(){

	var email= "<%=email%>";
	var ancienmdp= document.getElementById("ancienMdp").value;
	var mdp= document.getElementById("motDePasse").value;
	var mdpBis= document.getElementById("motDePasseBis").value;

	
	if ( mdp!=mdpBis){
	
		BootstrapDialog.show({
			message:"<%=Erreur_HTML.MOT_DE_PASSEBIS_NOK%>"
					});

		return false;

}

	firebase.auth().signInWithEmailAndPassword(email, ancienmdp).then(function(firebaseUser) {

		 
		 firebase.auth().currentUser.updatePassword(mdp).then(function() {


				BootstrapDialog.alert("<%=Erreur_HTML.MOT_DE_PASSE_CHANGE%>", function(){
					document.location.href="/wayd/ComptePro";   
				});
				
			  // Update successful.
			}).catch(function(error) {
			  // An error happened.
				var errorMessage = error.message;
				 
				BootstrapDialog.show({
					message:errorMessage
							});
			});



		
	  
		  
	}).catch(function(error) {
		  // Handle Errors here.
		  var errorCode = error.code;
	
		if (errorCode=='auth/wrong-password')

				BootstrapDialog.show({
				message:"<%=Erreur_HTML.MOT_DE_PASSE_ANCIEN_NOK%>"
						});

		  // ...
		});
	

	
	
	
}
</script>



</body>
</html>
