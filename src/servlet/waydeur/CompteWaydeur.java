package servlet.waydeur;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import website.metier.AuthentificationSite;
import website.metier.Outils;
import website.metier.ProfilBean;

/**
 * Servlet implementation class CompteWaydeur
 */
public class CompteWaydeur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(CompteWaydeur.class);

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompteWaydeur() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		// ********* Regle d'authentification*********************
		if (true)return;
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		if (!authentification.isAuthentifieWaydeur())
			return;
		
		
		request.getRequestDispatcher("waydeur/form_fullprofilWayd.jsp").forward(request, response);
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		if (true)return;
		
		HttpSession session = request.getSession();
		
		ProfilBean profil = (ProfilBean) session.getAttribute("profil");
		
		
			if (profil == null) {
			response.sendRedirect("auth/login.jsp");
			return;
		}

		if (profil.getTypeuser() != ProfilBean.WAYDEUR
				|| profil.isPremiereconnexion()) {
			response.sendRedirect("auth/login.jsp");
			return;
		}
		
		
		String nom = request.getParameter("nom");
		String adresse = request.getParameter("adresse");
		String commentaire = request.getParameter("commentaire");
		Date datenaissance;
		try {
			datenaissance = Outils.getDateFromString( request.getParameter("datenaissance"));
		} catch (ParseException e) {
		
			datenaissance=new Date();
		
			LOG.error( ExceptionUtils.getStackTrace(e));
		}
		
		int sexe=Integer.parseInt(request.getParameter("sexe"));
	
		
		boolean afficheSexe=Outils.getBooleanValueOf(request.getParameter("afficheSexe"));
		boolean afficheAge=Outils.getBooleanValueOf(request.getParameter("afficheAge"));;
	
		if (profil != null) {
			website.dao.PersonneDAO personneDAO = new website.dao.PersonneDAO();

			personneDAO.updateProfilProFullWaydeur(nom, adresse,
					 commentaire, sexe,afficheSexe,afficheAge,datenaissance,profil.getId() );
		
		
			profil.setPseudo(nom);
			profil.setAdresse(adresse);
			profil.setCommentaire(commentaire);
			profil.setDateNaissance( datenaissance);
			profil.setSexe(sexe);
			profil.setPremiereconnexion(false);
			profil.setAfficeSexe(afficheSexe);
			profil.setAfficheAge(afficheAge);
			response.sendRedirect("AcceuilWaydeur");
			

		}
	
	
	}

}
