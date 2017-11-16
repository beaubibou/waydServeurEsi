<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="website.metier.ProfilBean"%>
    <%@page import="website.html.*"%>
	<%@page import="website.dao.CacheValueDAO"%>
	<%@page import="java.util.ArrayList"%>
	<%@page import="website.metier.SexeBean"%>
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
<link href="/wayd/css/nbrcaractere.css" rel="stylesheet" media="all" type="text/css"> 

<script>
	var latitude = 0;
	var longitude = 0;
</script>

</head>
<body>

	<%
	ArrayList<SexeBean> listSexe= CacheValueDAO.getListSexe();
	%>

<div class="container">
	<div class="page-header">
			
			<h1 ><img src="/wayd/img/waydLogoHD.png" style="margin-right:50px;" class="img-rounded"
				alt="Cinque Terre" width="100" height="100">Connectez vous</h1>
		</div>
		<p>blablal......</p>
		
	
	</div>
	<div class="container">
		<div id="loginbox" 
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">
					<div class="panel-title">Inscription</div>

				</div>

				<div style="padding-top: 30px" class="panel-body">



					<form action="../Form_PremierProfil"
						onsubmit="return valideFormulaire()">
						<div class="form-group">
							<label for="nom">Pseudo*:</label> <input type="text"
								class="form-control"  name="nom" placeholder="<%=ParametreHtmlPro.getHintPseudoWaydeur() %>"  maxlength="<%=ParametreHtmlPro.TAILLE_PSEUDO_MAX %>"
								
								required>
						</div>

						<div  class="form-group">
							<label for="sexe">Vous êtes:</label> 	<select class="form-control" id="genre" name="sexe">

									<%
										for (SexeBean sexebean:listSexe){
									%>
									<option value=<%=sexebean.getId()%>
										>
										<%=sexebean.getLibelle()%></option>

									<%
										};
									%>
								</select>
						</div>
						
							<input type="hidden" class="form-control" 
							name="typeuser" required value="3">
			
						<div class="form-group">
							<label for="commentaire">Renseignements:</label>
							<textarea class="form-control" rows="5" id="description"
								name="commentaire" placeholder="<%=ParametreHtmlPro.getHintDescriptionProfilWaydeur() %>" maxlength="<%=ParametreHtmlPro.TAILLE_DESCRIPTION_PROFIL_MAX %>"></textarea>
						</div>
	<h5 class="nbrcaracteremax" id="nbr">0 Caractére sur "<%=ParametreHtmlPro.TAILLE_DESCRIPTION_ACTIVITE_MAX %>"</h5>
					
						</br>
						<button type="submit" class="btn btn-info">Enregistrer</button>
			
					</form>


				</div>
			</div>
		</div>
<script>
		$(document).ready(function(e) {

			$('#description').keyup(function() {

				var nombreCaractere = $(this).val().length;
				//alert(nombreCaractere);

				var msg = nombreCaractere + ' Caractere(s) / <%=ParametreHtmlPro.TAILLE_DESCRIPTION_ACTIVITE_MAX %>';

				$('#nbr').text(msg);
				// Le script qui devra calculer et afficher le nombre de mots et de caractères

			})

		});

		// Init le nombre de caraterces	
		var nombreCaractere = $('#description').val().length;
		var msg = nombreCaractere + ' Caractere(s) / <%=ParametreHtmlPro.TAILLE_DESCRIPTION_ACTIVITE_MAX %>';
		$('#nbr').text(msg);
	</script>

	


</body>
</html>