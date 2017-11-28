<%@page import="website.pager.PagerProblemeBean"%>
<%@page import="website.metier.ProblemeBean"%>
<%@page import="website.metier.ActiviteBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="website.metier.AuthentificationSite"%>
<%@page import="website.metier.admin.*"%>
<%@page import="website.metier.Outils"%>
<%@page import="java.util.*"%>
<%@page import="website.dao.CacheValueDAO"%>
<%@page import="org.joda.time.DateTime"%>
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

<script src="js/moment.js"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css"
	rel="stylesheet" type="text/css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
	<link href="/wayd/css/styleWaydAdmin.css" rel="stylesheet"
	type="text/css">
</head>
<body>

	<%@ include file="menu.jsp"%>
	<%
		ArrayList<EtatProbleme> listEtatProbleme = CacheValueDAO.getListEtatProbleme();
			FitreAdminProbleme filtreProbleme=(FitreAdminProbleme)session.getAttribute("filtreProbleme");
			
			PagerProblemeBean pager=(PagerProblemeBean) request
					.getAttribute("pager");
			ArrayList<ProblemeBean> listProbleme = pager.getListProbleme();
	%>
	
	<div class="container" style="width: 90%;">
  <div class="panel panel-primary">	
	    <div class="panel-body" style="background: #99ccff;">
		<form  class="form-inline" action="ListProbleme" id="formulaire" >
			<div class="form-group">
				<label  for="idEtatProbleme">Etat</label> <select data-style="btn-primary"
					class="form-control" id="idEtatProbleme" name="etatProbleme"  >

					<%
						for (EtatProbleme etatProbleme:listEtatProbleme) {
					%>
					<option value="<%=etatProbleme.getId()%>"
						<%=Outils.jspAdapterListSelected(etatProbleme.getId(), filtreProbleme.getEtatProbleme())%>>
						<%=etatProbleme.getLibelle()%></option>
					<%
						}
					%>

				</select>
			</div>
			<div class="form-group">
				<label for="iddatedebut">Date debut</label>
				<div class='input-group date' id='datedebut'>
					<input type='text' class="form-control" id="iddatedebut"
						name="debut" /> <span class="input-group-addon"> <span
						class="glyphicon glyphicon-calendar"></span>
					</span>
				</div>
			</div>



			<div class="form-group">
				<label for="iddatefin">Date fin</label>
				<div class='input-group date' id="datefin">
					<input type='text' class="form-control" id="iddatefin" name="fin" />
					<span class="input-group-addon"> <span
						class="glyphicon glyphicon-calendar"></span>
					</span>
				</div>
			</div>



			<button type="submit" class="btn btn-info">Recherchez</button>

		</form>
	
	</div>

	</div>
</div>


<div class="container" style="width: 90%;">
	
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Date</th>
					<th>Problème</th>
					<th>Email</th>
					<th>Action</th>
					<th>Clos</th>

				</tr>
			</thead>
			<tbody>
				<%
					
				for (ProblemeBean prb : listProbleme) {
					String lienEfface ="/wayd/ListProbleme?idmessage="+prb.getId()+"&page="+pager.getPageEnCours()+"&action=supprime";
					String lienLecture ="/wayd/ListProbleme?idmessage=" + prb.getId()+"&page="+pager.getPageEnCours()+"&action=clos";
				%>

				<tr>
					<td>
						<%
							out.println(prb.getDateCreationStr());
						%>
					</td>

					<td>
						<%
							out.println(prb.getProbleme());
						%>
					</td>

					<td>
						<%
							out.println(prb.getEmail());
						%>
					</td>

					<td>
						
						<button id='<%=lienEfface%>' name='supprime' type='button'
							class='btn btn-primary btn-sm'>
							<span class='glyphicon glyphicon-trash'></span>
						</button>
					</td>
					<td>
						<%=prb.getLuHtml(lienLecture) %>	
				
					</td>


				</tr>

				<%
					}
				%>

			</tbody>
		</table>
	
</div>
 <ul class="pager">
 
  <li <%=pager.isPreviousHtml()%>> <a  href="<%=pager.getLienPrevioustHtml()%>">Previous</a></li>
 <li>Page N° <%=pager.getPageEnCours()%></li>
  <li  <%=pager.isNextHtml()%>><a href="<%=pager.getLienNextHtml()%>">Next</a></li>

</ul>

	<script>
		$(function() {

			$('button').click(function() {

				var lien = $(this).attr('id');
				var action = $(this).attr('name')

				if (action == 'supprime')
					location.href = lien;
				if (action == 'lireMessage')
					lireMessage(lien);
				

			});

			$('select').change(function() {

				document.getElementById("formulaire").submit();
			});

			
			
			$('#datedebut').datetimepicker({
				defaultDate : new Date('<%=filtreProbleme.getDateDebutCreation().getMonthOfYear()%>,<%=filtreProbleme.getDateDebutCreation().getDayOfMonth()%>,	<%=filtreProbleme.getDateDebutCreation().getYear()%>'),
				format : 'DD/MM/YYYY'

			}).on('dp.change', function (e) {document.getElementById("formulaire").submit(); });

			var d = new Date(99,5,24)
	
			$('#datefin').datetimepicker(
					{
						defaultDate : new Date('<%=filtreProbleme.getDateFinCreation().getMonthOfYear()%>,<%=filtreProbleme.getDateFinCreation().getDayOfMonth()%>,	<%=filtreProbleme.getDateFinCreation().getYear()%>	'),
			format : 'DD/MM/YYYY'

							}).on('dp.change', function (e) {document.getElementById("formulaire").submit(); });

		});
	</script>
	<script>
	


		
		

		

		
		function lireMessage(lien) {
			location.href = lien;
		}
		<script>


	

	
		
	</script>
</body>
</html>