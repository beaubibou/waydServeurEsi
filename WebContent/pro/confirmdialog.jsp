<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Confirmer</title>
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <link href="/wayd/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>

<% 
String  action = (String) request.getAttribute("lienAction");
String  retour = (String) request.getAttribute("lienAction");
String  message = (String) request.getAttribute("message");
%>


<div class="container">
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-3 col-md-offset-3 col-sm-8">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">
					<div class="panel-title"><%out.println(message);%></div>
				</div>
				<div style="padding-top: 30px" class="panel-body">


 <a href=<%out.println(retour);%> class="btn btn-info" role="button">  non  </a>
 <a href=<%out.println(action);%> class="btn btn-info" role="button">  oui  </a>
 

				</div>
			</div>
		</div>
			</div>
</body>
</html>