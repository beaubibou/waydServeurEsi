<%@page import="website.metier.ActiviteBean"%>
<%@page import="website.metier.ProfilBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
    <title>Bootstrap 3 with Google Map</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
 
    <!-- Bootstrap core CSS -->
    <link href="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.1.1/css/bootstrap.css" rel="stylesheet" media="screen">
 
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="http://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7/html5shiv.js"></script>
      <script src="http://cdnjs.cloudflare.com/ajax/libs/respond.js/1.3.0/respond.js"></script>
    <![endif]-->
 
    <style>
      #map-container { height: 100vh }
    </style>
</head>
<body>
<%@ include file="menu.jsp"%>
	
	
<%

ProfilBean profil = (ProfilBean) session.getAttribute("profil");

ArrayList<ActiviteBean> listMesActivite = (ArrayList<ActiviteBean>) request.getAttribute("listMesActivite");

%>


    <div id="map-container" class="col-sm-5 col-md-6 col-lg-12" style="background-color:white;">
     
    </div>
   
     <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.1.1/js/bootstrap.min.js"></script>
    <script src="http://maps.google.com/maps/api/js?sensor=false"></script>
    <script>	
 
      function init_map() {
	//	var var_location = new google.maps.LatLng(45.430817,12.331516);
        var var_location = new google.maps.LatLng(<%=profil.getLatitude()%>,<%=profil.getLongitude()%>);
       
         var var_mapoptions = {
          center: var_location,
          zoom: 12
        };
        
        var var_map = new google.maps.Map(document.getElementById("map-container"),
            var_mapoptions);
 
      
     <%
     for (ActiviteBean activite : listMesActivite) {
			%>
        
        positionItem = new google.maps.LatLng(<%=activite.getLatitude()%>,<%=activite.getLongitude()%>);
       
		 var_marker = new google.maps.Marker({
			position: positionItem,
			map: var_map,
			title:"Venice"});
	      var_marker.setMap(var_map);	
	      var info=getInfoWindow("<%=activite.getTitre()%>","<%=activite.getLibelle()%>","lien","cree");
			
			//	  var info = new google.maps.InfoWindow({
			//content: contentString
		//});
		
		var_marker.addListener('click', function() {
	    info.open(var_map, var_marker);
		});
	      
	     
	      <%}%>
	       	
	
      }
 
      google.maps.event.addDomListener(window, 'load', init_map);
 
    </script>
      <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD2TO9-HtrUmagi0JTZn6YSN0QLbsoVkTg&callback=initMap">
    </script>
    
    
     <script>
		function getInfoWindow( titre,contenu,lien,cree){
	
		var contentString = '<div id="content">'+
      '<div id="siteNotice">'+
      '</div>'+
      '<h1 id="firstHeading" class="firstHeading">'+titre+'</h1>'+
      '<div id="bodyContent">'+
      '<p>'+contenu+'</p>'+
      '<p><a href='+lien+'>'+
      'Consulter</a> '+
      '(crée le '+cree+' ).</p>'+
      '</div>'+
      '</div>';
	
			
		var infowindow = new google.maps.InfoWindow({
		content: contentString
		
		
		});
		
		return infowindow;
		}
		
		
    
    </script>
</body>
</html>
