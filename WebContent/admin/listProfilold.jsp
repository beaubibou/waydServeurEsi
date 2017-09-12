<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Liste profils</title>
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

	 <%@ include file="menu.jsp" %>

	<div class="container">

		<h3>Critères</h3>
		<form class="form-inline" method="post" action="ListActivite">
			<div class="form-group">
				<label for="email">Email:</label> <input type="email"
					class="form-control" id="email" placeholder="kkemail" name="email">
			</div>
			<div class="form-group">
				<label for="pwd">Password:</label> <input type="password"
					class="form-control" id="pwd" placeholder="password" name="pjjwd">
			</div>


			<div class="container">
				<h2>Form control: select</h2>
				<p>The form below contains two dropdown menus (select lists):</p>

				<div class="form-group">
					<label for="sel1">Type activité</label> <select
						class="form-control" id="sel1" name="typeactivite">
						<option value="1">toto</option>
						<option value="2">tata</option>
						<option>titi</option>
						<option>zozou</option>
					</select> <br> <label for="sel2">Mutiple select list (hold
						shift to select more than one):</label>
				</div>

			</div>
			<button type="submit" class="btn btn-default">Rechercher</button>

		</form>
	</div>
	<div class="container">
		<h3>Profils</h3>
		
	</div>

</body>
</html>