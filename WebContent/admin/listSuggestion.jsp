<%@page import="website.pager.PagerSuggestionBean"%>
<%@page import="website.metier.admin.FitreAdminSuggestions"%>
<%@page import="website.metier.admin.EtatSuggestion"%>
<%@page import="website.metier.SuggestionBean"%>
<%@page import="website.metier.ProblemeBean"%>
<%@page import="website.metier.ActiviteBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="website.dao.CacheValueDAO"%>
<%@page import="website.metier.Outils"%>
<%@page import="website.metier.AuthentificationSite"%>
<%@page import="website.pager.PagerSuggestionBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>List suggestions</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

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

<script src="js/moment.js"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css"
	rel="stylesheet" type="text/css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<link href="/wayd/css/styleWaydAdmin.css" rel="stylesheet" type="text/css">
<link href="/wayd/css/nbrcaractere.css" rel="stylesheet" media="all"
	type="text/css">
</head>

<body>

	<%@ include file="menu.jsp"%>
	<%
	AuthentificationSite authentification = new AuthentificationSite(
			request, response);
			if (!authentification.isAuthentifieAdmin())
		return;
			
		ArrayList<EtatSuggestion> listEtatSuggestion = CacheValueDAO.getListEtatSuggestions();
			FitreAdminSuggestions filtreSugestion=(FitreAdminSuggestions)session.getAttribute("filtreSuggestion");
			
			PagerSuggestionBean pager=(PagerSuggestionBean) request
					.getAttribute("pager");
			System.out.print("parger"+pager);
			
			ArrayList<SuggestionBean> listSuggestion = pager.getListSuggestion();
	%>
		<div class="container" style="width: 90%;">
<div class="panel panel-primary">	
	    <div class="panel-body" style="background: #99ccff;">		
		<form class="form-inline" action="ListSuggestion" id="formulaire">
			<div class="form-group">
				<label for="idEtatProbleme">Etat</label> <select
					data-style="btn-primary" class="form-control" id="idEtatSuggestion"
					name="etatSuggestion">

					<%
						for (EtatSuggestion etatSuggestion:listEtatSuggestion) {
					%>
					<option value="<%=etatSuggestion.getId()%>"
						<%=Outils.jspAdapterListSelected(etatSuggestion.getId(), filtreSugestion.getEtatSuggestion())%>>
						<%=etatSuggestion.getLibelle()%></option>
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
					<th style="width:10%;" >Pseudo</th>
					<th >Suggestion</th>
					<th  style="width:10%;"  class="text-center">Date</th>
					<th  style="width:10%;"  class="text-center">Action</th>
					<th  style="width:10%;"  class="text-center">Cloturé</th>

				</tr>
			</thead>
			<tbody>
				<%
			
						for (SuggestionBean suggestion : listSuggestion) {
					String lienDetailOrganisateur = "DetailParticipant?idPersonne="
					+ suggestion.getIdPersonne();
				String lienMessage =
						"/wayd/EnvoiMessageAdmin?idPersonne=" +suggestion.getIdPersonne()+
						"&idMessage="+suggestion.getId()+"&formInit=listSuggestion";
				String lienEfface ="/wayd/EffaceSuggestionAdmin?idSuggestion="+suggestion.getId()
				+"&formInit=listSuggestion";		

				String lienLecture = "/wayd/ClosSuggestionAdmin?idmessage=" + suggestion.getId();
				%>

				<tr>

					<td><a href=<%=lienDetailOrganisateur%>>
					 <%=suggestion.getPseudo()%>
					</a></td>

					<td>
						
						<textarea disabled=true rows="3" cols="130"><%=suggestion.getSuggestion()%></textarea>
						
					

					</td>

					<td><%=suggestion.getDateCreationStr()%></td>

					<td>

						<button id='<%=lienMessage%>' name='envoiMessage' type='button'
							class='btn btn-primary btn-sm'>
							<span class='glyphicon glyphicon-send'></span>
						</button>
						<button id='<%=lienEfface%>' name='supprime' type='button'
							class='btn btn-primary btn-sm'>
							<span class='glyphicon glyphicon-trash'></span>
						</button>
					</td>
					<td><%=suggestion.getLuHtml(lienLecture)%></td>



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

				if (action == 'envoiMessage')
					location.href = lien;

				if (action == 'supprime')
					location.href = lien;

				if (action == 'lireMessage')
					lireMessage(lien);

			});

			$('#datedebut').datetimepicker({
				defaultDate : new Date(	<%=filtreSugestion.getDateDebutCreation().getYear()%>,<%=filtreSugestion.getDateDebutCreation().getMonthOfYear()%>-1,<%=filtreSugestion.getDateDebutCreation().getDayOfMonth()%>),
				format : 'DD/MM/YYYY'

			}).on('dp.change', function (e) {document.getElementById("formulaire").submit(); });

		
	
			$('#datefin').datetimepicker(
					{
						defaultDate : new Date(<%=filtreSugestion.getDateFinCreation().getYear()%>,<%=filtreSugestion.getDateFinCreation().getMonthOfYear()-1%>,<%=filtreSugestion.getDateFinCreation().getDayOfMonth()%>),
								format : 'DD/MM/YYYY'

							}).on('dp.change', function (e) {document.getElementById("formulaire").submit(); });


			$('select').on('change', function() {

				document.getElementById("formulaire").submit();
			});


			});
			
		

	
	

	
		function lireMessage(lien) {
			location.href = lien;
		}
	</script>


</body>




</html>