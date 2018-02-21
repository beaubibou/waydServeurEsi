<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
	<%@page import="website.html.*"%>
	<%@page import="website.html.MessageAlertDialog"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src='https://www.google.com/recaptcha/api.js'></script>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Wayd community</title>
<meta name="description" content="">
<meta name="author" content="">

<!-- Favicons
    ================================================== -->
<link rel="shortcut icon" href="/wayd/commun/img/wayd32.png"
	type="image/x-icon">
<link rel="apple-touch-icon" href="/wayd/commun/img/wayd32.png">
<link rel="apple-touch-icon" sizes="32x32"
	href="/wayd/commun/img/wayd32.png">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


<!-- Stylesheet
    ================================================== -->
<link rel="stylesheet" type="text/css" href="/wayd/commun/css/style.css">
<link
	href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900"
	rel="stylesheet">
	
	<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.34.9/css/bootstrap-dialog.min.css"
	rel="stylesheet" type="text/css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.34.9/js/bootstrap-dialog.min.js"></script>

<script src="js/alertdialog.js"></script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body id="page-top" data-spy="scroll" data-target=".navbar-fixed-top">
	<!-- Navigation
    ==================r========================-->
	
	<nav id="menu" class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"> </span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand page-scroll" href="#page-top"><img
				src="/wayd/commun/img/wayd32.png"></img></a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="#about" class="page-scroll">Découvrir</a></li>
				<li><a href="#services" class="page-scroll">Professionels</a></li>
				<li><a href="#download" class="page-scroll">Téléchargement</a></li>
				<li><a href="#contact" class="page-scroll">Contact</a></li>
				
				<li><a href="/wayd/pro/home.jsp" class="page-scroll">Site
						Wayd Pro</a></li>

			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid --> </nav>
	<!-- Header -->
	<header id="header">
	<div class="intro">
		<div class="overlay">
			<div class="container">
				<div class="row">
					<div class="intro-text">
						<h1>Wayd Community</h1>
						<p>Une communauté pour partager, se divertir et s'entraider !</p>
						<a href="#about" class="btn btn-custom btn-lg page-scroll">Découvrir</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	</header>
	<!-- About Section -->
	<div id="about">
		<div class="container" >
    <div class="section-title text-center center">
      <h2>Découvrir Wayd</h2>
      <hr>
    </div>
    <div class="row">
      <div class="col-xs-12 col-md-6"> <img src="/wayd/commun/img/about.jpg" class="img-responsive thumbnail" alt=""> </div>
      <div class="col-xs-12 col-md-6">
        <div class="about-text">
          <h3>Avec Wayd Community, fini l'ennui !</h3>
          <p>Vous arrive-t-il de ne pas savoir quoi faire ? </p>
          <p>Née d'une attente d'une heure à ne rien faire dans une gare en attendant un train, Wayd Community vous propose des activités à faire, réelement à proximitées, immédiatement et en fonction de vos envies. Alors juste pour tailler le bout de gras avec un waydeur, faire un petit footing avec une waydeuse ou encore pour obtenir de l'aide : <br> <br> Bienvenue sur Wayd !</p>
          <div class="list-style">
            <div class="col-lg-6 col-sm-6 col-xs-12">
              <ul>
			   <li>Spontanéïté</li>
				<li>Proximité</li>
				<li>Personalisation de vos centres interêts</li>
				<li>Une communauté pour partager, se divertir et s'entraider</li>
                      
              </ul>
            </div>
            <div class="col-lg-6 col-sm-6 col-xs-12">
              <ul>
                <li>Pas de publicité</li>
                <li>Pas de donnée collectée a des fins commerciales</li>
				<li>Pas de notification non désirée</li>
                <li>Des avis pour conserver des membres en phase avec la philisophie</li>
                
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

<!-- Services Section -->
<div id="services" class="text-center">
  <div class="container">
    <div class="col-md-10 col-md-offset-1 section-title">
      <h2>Besoin de visibilité ?</h2>
      <hr>
      <p>Wayd vous offre un moyen gratuit, simple et efficace pour valoriser votre activité.</p>
    </div>
    <div class="row">
      <div class="col-xs-6 col-md-3"> <i class="fa fa-home"></i>
        <h4>Gratuité</h4>
        <p>Pour valoriser votre activité, vous organisez des activités gratuites ? Wayd vous permet, gratuitement, de communiquer dessus.</p>
      </div>
      <div class="col-xs-6 col-md-3"> <i class="fa fa-umbrella"></i>
        <h4>Simplicité</h4>
        <p>L'interface pro de Wayd vous permet de publier simplement et en  moins de 2 minutes vos activités afin d'offrir du contenu aux membres de Wayd.</p>
      </div>
      <div class="col-xs-6 col-md-3"> <i class="fa fa-gears"></i>
        <h4>Efficacité</h4>
        <p>Un concept efficace qui permet d'offrir aux Waydeurs à proximité, et qui cherche quelque-chose à faire, une vision de ce que proposez. </p>
      </div>
      <div class="col-xs-6 col-md-3"> <i class="fa fa-language"></i>
        <h4>Evolutivité</h4>
        <p>Une plateforme qui évoluera en fonction des besoins des Waydeurs et de vos besoins de communications.</p>
      </div>
    </div>
  </div>
</div>

<!-- Download Section -->
<div id="download" class="text-center">
  <div class="container">
    <div class="col-md-10 col-md-offset-1 section-title">
      <h2>Télécharger Wayd</h2>
      <hr>
      <p>Vous pouvez retrouver Wayd sur :</p>
	  <p> </p>
	  
	  <br>
	  <p> </p>
      <p><a href="https://play.google.com/store/apps/details?id=com.application.wayd&hl=fr"> <img src="/wayd/commun/img/googleplay.png"></img></a></p>
          
    </div>
  </div>
</div>

<!-- Contact Section -->
<div id="contact" class="text-center">
  <div class="container">
    <div class="section-title center">
      <h2>Contactez-nous</h2>
      <hr>
      <p>Une question, un problème ? Vous pouvez nous contacter via le formulaire ci-dessous ou à l'adresse contact@wayd.fr</p>
    </div>
    <div class="col-md-8 col-md-offset-2">
      <form name="sentMessage" id="contactForm" action="/wayd/ContactMessageCaptcha" method="post" >
        <div class="row">
          <div class="col-md-6">
            <div class="form-group">
              <input name="pseudo" type="text" id="name" class="form-control" placeholder="Nom" required="required">
              <p class="help-block text-danger"></p>
            </div>
          </div>
          <div class="col-md-6">
            <div class="form-group">
              <input name="email" type="email" id="email" class="form-control" placeholder="Adresse email" required="required">
              <p class="help-block text-danger"></p>
            </div>
          </div>
        </div>
        <div class="form-group">
          <textarea name="message" id="message" class="form-control" rows="4" placeholder="Message" maxlength="250" required></textarea>
          <p class="help-block text-danger"></p>
        </div>
        <div id="success"></div>
        	<div class="g-recaptcha"
							data-sitekey="6Ld6TzgUAAAAAMx76Q_NXm3xEJ1vPa799RLMeYLn"></div>
    
        <button type="submit" class="btn btn-custom btn-lg">Envoyer le message</button>
    	
      </form>
      <div class="social">
        <ul>
          <li><a href="https://www.facebook.com/WaydCommunity/"><i class="fa fa-facebook"></i></a></li>
          <li><a href="https://twitter.com/WaydCommunity"><i class="fa fa-twitter"></i></a></li>
                 
        </ul>
      </div>
    </div>
  </div>
</div>
<div id="footer">
  <div class="container text-center">
    <div class="fnav">
      <p>Copyright &copy; 2017 Wayd Inc | contact@wayd.fr</p>
    </div>
  </div>
</div>
<script type="text/javascript" src="js/jquery.1.11.1.js"></script> 
<script type="text/javascript" src="js/bootstrap.js"></script> 
<script type="text/javascript" src="js/SmoothScroll.js"></script> 
<script type="text/javascript" src="js/jqBootstrapValidation.js"></script> 
<script type="text/javascript" src="js/contact_me.js"></script> 
<script type="text/javascript" src="js/main.js"></script>
</body>
</html>