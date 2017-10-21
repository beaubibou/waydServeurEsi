package servlet.pro;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import website.metier.AuthentificationSite;
import website.metier.FiltreRecherche;
import website.metier.ProfilBean;

/**
 * Servlet implementation class ComptePro
 */
public class ComptePro extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ComptePro() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		// ********* Regle d'authentification*********************
		HttpSession session = request.getSession();
		ProfilBean profil = (ProfilBean) session.getAttribute("profil");

	//	System.out.println("Profil dans acceuil pro" + profil);
		if (profil == null) {
			response.sendRedirect("auth/login.jsp");
			return;
		}

		if (profil.getTypeuser() != ProfilBean.PRO
				|| profil.isPremiereconnexion()) {
			response.sendRedirect("auth/login.jsp");
			return;
		}

		response.sendRedirect("pro/form_fullprofilpro.jsp");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	


		
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		if (!authentification.isAuthentifiePro())
			return;
		
		String nom = request.getParameter("nom");
		String adresse = request.getParameter("adresse");
		String commentaire = request.getParameter("commentaire");
		double latitude = Double.parseDouble(request.getParameter("latitude"));
		double longitude = Double
				.parseDouble(request.getParameter("longitude"));
		String telephone = request.getParameter("telephone");
		String siteWeb = request.getParameter("siteweb");

		System.out.println("ComptePro-method post" + " " + adresse + "typeuse "
				+  " lat:" + latitude + "  " + longitude+ siteWeb+" "+telephone);

		
		ProfilBean profil=authentification.getProfil();
		FiltreRecherche filtreRecherche=authentification.getFiltre();
		
		website.dao.PersonneDAO personneDAO = new website.dao.PersonneDAO();

			personneDAO.updateProfilProFull(nom, adresse, latitude, longitude,
					 commentaire, profil.getId(),siteWeb, telephone);
		
			profil.setTelephone(telephone);
			profil.setSiteWeb(siteWeb);
			profil.setPseudo(nom);
			profil.setAdresse(adresse);
			profil.setCommentaire(commentaire);
			profil.setLatitude(latitude);
			profil.setLongitude(longitude);
			profil.setLatitudeFixe(latitude);
			profil.setLongitudeFixe(longitude);
			profil.setPremiereconnexion(false);
			filtreRecherche.setLatitude(latitude);
			filtreRecherche.setLongitude(longitude);

			response.sendRedirect("AcceuilPro");
			
	
		

	}

}
