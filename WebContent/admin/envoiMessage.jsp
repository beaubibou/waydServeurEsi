
<%@page import="website.metier.FiltreJSP"%>
<%@page import="website.metier.TypeActiviteBean"%>
<%@page import="website.metier.ActiviteBean"%>
<%@page import="website.metier.Pagination"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Envoi message</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

	<%@ include file="menu.jsp"%>

	<%
		int idDestinataire = ((Integer) request.getAttribute("idDestinataire"))
				.intValue();
		int idMessage = ((Integer) request.getAttribute("idMessage"))
				.intValue();
		String  formInit = (String) request.getAttribute("formInit");
				
		
		
	%>





<div class="container">
		<div id="loginbox"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
		<h2>Envoi message</h2>

	<form method="post" action="/wayd/EnvoiMessageAdmin">

		<div class="form-group">
			<label for="comment">Message</label>
			<textarea name="message" class="form-control" rows="5" id="comment"></textarea>
		</div>
	
		<div class="checkbox">
			<label><input name="clore" type="checkbox">Clore le sujet</label>
		</div>
		
		<input type="hidden" name="idMessage" value="<%=idMessage%>">
		 <input	type="hidden" name="idDestinataire" value="<%=idDestinataire%>">
		  <input	type="hidden" name="formInit" value="<%=formInit%>">
		<button type="submit" class="btn btn-default">Submit</button>
	
	</form>

</div>
</div>

</body>

</html>