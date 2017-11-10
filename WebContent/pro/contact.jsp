<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="website.html.*"%>
    <%@page import="website.metier.AuthentificationSite"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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

<link href="/wayd/css/styleWayd.css" rel="stylesheet" type="text/css">
<link href="/wayd/css/nbrcaractere.css" rel="stylesheet" media="all" type="text/css"> 

</head>
<body  style="background-color: #FFD54F;"> 
<!-- Contact Section -->
</br>
<div id="contact" class="text-center">
  
  	<%
  	AuthentificationSite authentification=	new AuthentificationSite(request, response);
	if (!authentification.isAuthentifiePro())
	return;
  	
  	MenuEnum etatMenu = MenuEnum.contact;
	%>
   <%@ include file="menu.jsp"%>

  <div class="container" style="background-color: #FFD54F;margin-top: 100px;">
    <div class="section-title center">
      
      <h2>Contactez-nous</h2>
      <hr>
      <h4>Une question, un problème ? Vous pouvez nous contacter via le formulaire ci-dessous ou à l'adresse contact@wayd.fr</h4>
    </div>
    </br>
    <div class="col-md-8 col-md-offset-2">
      <form action="/wayd/ContactMessage" method="post">
        <div class="row">
         
          <div class="col-md-6">
            <div class="form-group">
              <input type="email" id="email" name="mail" class="form-control" placeholder="Adresse mail" maxlength="<%=ParametreHtmlPro.TAILLE_MAIL_CONTACT_MAX %>"  required="required">
              <p class="help-block text-danger"></p>
            </div>
          </div>
        </div>
        <div class="form-group">
          <textarea name="message" name="message" id="message" class="form-control" rows="4" placeholder="<%=ParametreHtmlPro.getHintDescriptionMessageContact() %>" maxlength="<%=ParametreHtmlPro.TAILLE_MESSAGE_CONTACT_MAX %>" required></textarea>
          <p class="help-block text-danger"></p>
        </div>
        <div id="success"></div>
        <button type="submit" class="btn btn-info btn-lg">Envoyer le message</button>
      </form>
      <div class="social">
        <ul>
          <li><a href="https://www.facebook.com/WaydCommunity/"><i class="fa fa-facebook"></i></a></li>
          <li><a href="https://twitter.com/WaydCommunity"><i class="fa fa-twitter"></i></a></li>
                 
        </ul>
      </div>
    </div>
  </div>
</body>
</html>