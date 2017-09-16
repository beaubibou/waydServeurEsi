<!DOCTYPE html>
<html lang="en">
<head>
  <title>Mes activit�s</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <link href="../css/style.css" rel="stylesheet" type="text/css">

</head>
<body>
<%@ include file="menu.jsp"%>
<div class="container">
		<div id="loginbox" style="margin-top: 100px;"
			class="mainbox col-md-12  col-sm-8">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">
							<div class="panel-title">Liste de vos activit�s</div>
				</div>
				<div style="padding-top: 30px" class="panel-body">
 
  <div class="table-responsive">
  <table class="table table-condensed" >
    <thead>
      <tr>
        <th>Nom</th>
        <th>Description</th>
        <th>Vues</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>John</td>
        <td> <textarea  class="form-control" disabled="true" rows="1" id="comment">"ppppppp"</textarea></td>
     <td> <span class="badge">10</span></td>
        <td><a href="#" class="btn btn-success btn-sm">
		
		<span class="glyphicon glyphicon-search"></span> 
		</a>
		<a href="#" class="btn btn-info btn-sm">
		<span class="glyphicon glyphicon-edit"></span> 
		</a>
		<a href="#" class="btn btn-danger btn-sm">
		<span class="glyphicon glyphicon-remove"></span> 
		</a>
		</td>
		
      </tr>
     
    </tbody>
  </table>
  
</div>

				</div>
			</div>
		</div>
</body>
</html>
