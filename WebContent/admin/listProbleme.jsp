<%@page import="website.metier.ProblemeBean"%>
<%@page import="website.metier.ActiviteBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="website.metier.AuthentificationSite"%>
<%@page import="website.metier.admin.*"%>
<%@page import="website.metier.Outils"%>
<%@page import="website.dao.CacheValueDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Liste problèmes</title>
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
	<%	ArrayList<EtatProbleme> listEtatProbleme = CacheValueDAO.getListEtatProbleme();
	FitreAdminProbleme filtre=(FitreAdminProbleme)session.getAttribute("filtreProbleme");
	
	%>
	<div class="container" style="margin-top: 100px">

		<div class="panel panel-primary"">
			<div class="panel-heading">
				<div class="row">
					<div class="col-sm-2">
						<form method="post" action="ListProbleme" id="formulaire"
							class="form-inline">
							<div class="form-group">
								<select class="form-control" id="idEtatProbleme"
									name="etatProbleme">

									<%
										for (EtatProbleme etatProbleme:listEtatProbleme) {
									%>
									<option value="<%=etatProbleme.getId()%>"
										<%=Outils.jspAdapterListSelected(etatProbleme.getId(), filtre.getEtatProbleme())%>>
										<%=etatProbleme.getLibelle()%></option>
									<%
										}
									%>

								</select>

							</div>

						</form>
					</div>

					<div class="col-sm-2 col-sm-offset-8 ">
						<button href="#" name="supprimerActivites" class="btn btn-default">Effacez</button>
					</div>


				</div>


			</div>

		</div>	

	<div class="container">
		<h2>Liste problemes</h2>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>Date</th>
					<th>Problème</th>
					<th>Email</th>
					<th>Action</th>
				
				</tr>
			</thead>
			<tbody>
				<%
					ArrayList<ProblemeBean> listProbleme = (ArrayList<ProblemeBean>) request
									.getAttribute("listProbleme");
							for (ProblemeBean prb : listProbleme) {
								String lienEfface ="/wayd/EffaceProblemeAdmin?idProbleme="+prb.getId();
										
					%>

				<tr>
				<td>
						<%
							out.println(prb.getDateCreationStr());
						%>
					</td>
				
					<td> <%out.println(prb.getProbleme());%>
					</td>
					
					<td>
						<%
							out.println(prb.getEmail());
						%>
					</td>
					
					<td>
						</button>
							<button id='<%=lienEfface%>' name='supprime' type='button'
							class='btn btn-primary btn-sm'>
							<span class='glyphicon glyphicon-trash'></span>
						</button>
					</td>
					

				</tr>

				<%
					}
				%>

			</tbody>
		</table>
	</div>

		<script>
		$(function() {

			$('button').click(function() {

				var lien = $(this).attr('id');
				var action = $(this).attr('name')
	
				if (action == 'supprime')
					location.href = lien;

			});

		});
		$(function() {

			$('#idEtatProbleme').on('change', function() {

				document.getElementById("formulaire").submit();
			});

		});
		
	</script>


</body>
</html>