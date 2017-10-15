<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="website.html.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Wayd community</title>
<meta name="description" content="">
<meta name="author" content="">

<!-- Favicons
    ================================================== -->
<link rel="shortcut icon" href="/wayd/commun/img/wayd32.png" type="image/x-icon">
<link rel="apple-touch-icon" href="/wayd/commun/img/wayd32.png">
<link rel="apple-touch-icon" sizes="32x32" href="/wayd/commun/img/wayd32.png">


<!-- Bootstrap -->
<link rel="stylesheet" type="text/css"  href="/wayd/commun/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="/wayd/commun/fonts/font-awesome/css/font-awesome.css">

<!-- Stylesheet
    ================================================== -->
<link rel="stylesheet" type="text/css"  href="commun/css/style.css">
<link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body  style="background-color: #FFD54F;"> 
<!-- Contact Section -->
</br>
<div id="contact" class="text-center">
  
   <%@ include file="menu.jsp"%>

  <div class="container" style="background-color: #FFD54F;">
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