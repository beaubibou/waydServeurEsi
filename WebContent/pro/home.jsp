<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="website.enumeration.MenuEnum"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<style>
body, html {
    height: 100%;
    margin: 0;
}

.hero-image {
  background-image: url("/wayd/img/waydfond.jpg");
  height: 100%;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  position: relative;
}

.hero-text {
  text-align: center;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
}

.hero-text button {
  border: none;
  outline: 0;
  display: inline-block;
  padding: 10px 25px;
  color: black;
  background-color: #ddd;
  text-align: center;
  cursor: pointer;
}

.hero-text button:hover {
  background-color: #555;
  color: white;
}
#jumbo {
    /* IE8 and below */
    background: rgb(00, 54, 60);
    /* all other browsers */
    background: rgba(00, 54, 60, 0.5);
}

</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%MenuEnum etatMenu=MenuEnum.home; %>

<div class="hero-image">
<%@ include file="menu.jsp"%>
  <div class="hero-text">
   <div class="container">
  <div class="jumbotron" id="jumbo">
    <img src="/wayd/img/waydLogoHD.png" class="img-thumbnail" alt="Cinque Terre" style="width:30%"> 
    <h1>Wayd professionel</h1>
    <p>Diffuser, communiquer</p>
  </div>
  <p>This is some text.</p>
  <p>This is another text.</p>

</div>
</div>
</div>

</body>
</html>