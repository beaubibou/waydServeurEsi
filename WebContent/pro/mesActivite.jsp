
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
<title>Mes activités</title>
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


</head>
<body>
	<%@ include file="menu.jsp"%>
	<div class="container">
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-12  col-sm-8">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-custom">
					<div class="panel-title">Liste de vos activités</div>
				</div>
				<div style="padding-top: 30px" class="panel-body">

					<div class="table-responsive">
						<table class="table table-condensed">
							<thead>
								<tr>
									<th>Titre</th>
									<th>Description</th>
									<th>Vues</th>
									<th>Etat</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>

								<%
									ArrayList<ActiviteBean> listMesActivite = (ArrayList<ActiviteBean>) request.getAttribute("listMesActivite");
								    
								     if (listMesActivite!=null)
									for (ActiviteBean activite : listMesActivite) {
										String lienEfface = "/wayd/SupprimeActivite?idactivite=" + activite.getId();
										String lienConfirmDialog="/wayd/ConfirmDialog?idactivite=" + activite.getId()+"&action=effaceActivite&from=MesActivites";
										String lienDetail = "DetailActivite?idactivite=" + activite.getId()+"&from=listActivite.jsp";
								%>

								<tr>
									<td>John</td>
									<td><textarea class="form-control" disabled rows="2"
											id="comment"><%=activite.getLibelle()%></textarea></td>

									<td><span class="badge">10</span></td>
									<td><%=activite.getEtat()%></td>

									<td><a href="#" class="btn btn-success btn-sm"> <span
											class="glyphicon glyphicon-search"></span>
									</a> 
									
									<a href="#" class="btn btn-info btn-sm"> <span
											class="glyphicon glyphicon-edit"></span>
									</a> 
									
									<button  id=<%out.println(lienEfface);%> name="supprimer" type="button" class="btn btn-primary btn-sm">
									<span class="glyphicon glyphicon-remove"></span> 
									</button>
		
									
									
									
									
									
									</td>

								</tr>
								<%
									}
								%>


							</tbody>
						</table>

					</div>

				</div>
			</div>
		</div>
	</div>

	<script>
		$(function() {

			$('button').click(function() {
			
				var lien = $(this).attr('id');
				var action = $(this).attr('name')
				if (action == 'supprimer')
					DialogEffaceActivite(lien);

			});
		});
	</script>

	<script>
		function DialogEffaceActivite(lien) {

			BootstrapDialog.show({
				title : 'Efface activité',
				message : 'Confirmez',
				buttons : [ {
					label : 'Oui',
					action : function(dialog) {
						effaceActivite(lien);
						dialog.close();
					}
				}, {
					label : 'Annuler',
					action : function(dialog) {
						dialog.close();
					}
				} ]
			});

		}

		function effaceActivite(lien) {
			location.href=lien;
		}
	</script>


</body>
</html>
