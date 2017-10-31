package servlet.commun;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import website.enumeration.AlertJsp;
import website.html.AlertInfoJsp;
import website.metier.ProfilBean;

/**
 * Servlet implementation class Form_PremierProfil
 */
public class Form_PremierProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(Form_PremierProfil.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Form_PremierProfil() {
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
		HttpSession session = request.getSession();

		String nom = request.getParameter("nom");
		String adresse = request.getParameter("adresse");
		String commentaire = request.getParameter("commentaire");
		int typeuser = Integer.parseInt(request.getParameter("typeuser"));
		double latitude = 0;
		double longitude = 0;
	
		if (request.getParameter("latitude") != null)
		
		 latitude = Double.parseDouble(request.getParameter("latitude"));

		if (request.getParameter("longitude") != null)
			 longitude =Double.parseDouble(request.getParameter("longitude"));

	
		ProfilBean profil = (ProfilBean) session.getAttribute("profil");

		if (profil != null) {
			website.dao.PersonneDAO personneDAO = new website.dao.PersonneDAO();

			switch (typeuser) {

			case ProfilBean.PRO:
			
				if (profil.isPremiereconnexion()==false){
					
					new AlertInfoJsp("Votre compte est déjà crée", AlertJsp.Alert,
							"AcceuilPro").send(request, response);
					return;
					
				}
				
				String siret = request.getParameter("siret");
				String telephone = request.getParameter("telephone");
			
				personneDAO.updateProfilPro(nom, adresse, latitude, longitude,
						 commentaire,   siret, telephone,profil.getId());
				profil.setPseudo(nom);
				profil.setAdresse(adresse);
				profil.setCommentaire(commentaire);
				profil.setTypeuser(typeuser);
				profil.setLatitude(latitude);
				profil.setLongitude(longitude);
				profil.setLatitudeFixe(latitude);
				profil.setLongitudeFixe(longitude);
				profil.setSiret(siret);
				profil.setTelephone(telephone);
				profil.setPremiereconnexion(false);
				response.sendRedirect("AcceuilPro");

				break;

			

			case ProfilBean.WAYDEUR:
			
				
				int sexe = Integer.parseInt(request.getParameter("sexe"));
				personneDAO.updateProfilWaydeur(nom, sexe, commentaire,
						profil.getId());
				profil.setPseudo(nom);
				profil.setCommentaire(commentaire);
				profil.setTypeuser(typeuser);
				profil.setPremiereconnexion(false);
				profil.setSexe(sexe);
				response.sendRedirect("AcceuilWaydeur");
			

				break;

			default:

				response.sendRedirect("auth/login.jsp");
			}

		} else {

			response.sendRedirect("auth/login.jsp");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
