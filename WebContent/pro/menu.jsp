
<%@page import="website.html.MenuHtml"%>
<%@page import="website.enumeration.*"%>
<nav class="navbar navbar-inverse navbar-fixed-top" >
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar" >
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      
        <img src="/wayd/img/waydLogoLR.png" class="img-rounded" alt="Cinque Terre" width="30" height="30">
     
    </div>
   
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        
         <li <%=MenuHtml.getActiviteClass(etatMenu,MenuEnum.moncompte) %>><a href="/wayd/ComptePro">Mon compte</a></li>
         <li <%=MenuHtml.getActiviteClass(etatMenu,MenuEnum.mesactivites) %>><a href="/wayd/MesActivites">Gérer </a></li>
         <li <%=MenuHtml.getActiviteClass(etatMenu,MenuEnum.ajouteactivite) %>><a href="/wayd/AjouteActivitePro">Proposer</a></li>
         <li <%=MenuHtml.getActiviteClass(etatMenu,MenuEnum.carte) %>><a href="/wayd/MapPro">Carte</a></li>
         <li  <%=MenuHtml.getActiviteClass(etatMenu,MenuEnum.apropos) %>><a href="/wayd/Apropos">A propos</a></li>
           <li><a href="/wayd/Contact">Contact</a></li>
      
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="/wayd/Deconnexion"><span class="glyphicon glyphicon-log-out"></span> Log out</a></li>
      </ul>
    </div>
  </div>
</nav>
 <div class="container" style="margin-top:30px"> 
