package servlet.pro;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import wayde.bean.MessageServeur;
import website.enumeration.AlertJsp;
import website.html.AlertDialog;
import website.html.AlertInfoJsp;
import website.html.MessageAlertDialog;
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
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);

		if (!authentification.isAuthentifiePro())
			return;
		
		response.sendRedirect("pro/comptePro.jsp");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
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
		String siret = request.getParameter("siret");

		MessageServeur messageServeur=testParametreRequete(nom,adresse,commentaire,latitude,longitude,telephone,siteWeb,siret);
		
		if (!messageServeur.isReponse()){
			
			authentification.setAlertMessageDialog( new MessageAlertDialog("Message Information",messageServeur.getMessage(),null,AlertJsp.warning));
			response.sendRedirect("MesActivites");
			return;
				
		}
		
		
		ProfilBean profil = authentification.getProfil();
		FiltreRecherche filtreRecherche = authentification.getFiltre();

		website.dao.PersonneDAO personneDAO = new website.dao.PersonneDAO();

		if (personneDAO.updateProfilProFull(nom, adresse, latitude, longitude,
				commentaire, profil.getId(), siteWeb, telephone,siret)) {

			profil.setTelephone(telephone);
			profil.setSiteWeb(siteWeb);
			profil.setSiret(siret);
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
			request.setAttribute(AlertDialog.ALERT_DIALOG, new MessageAlertDialog("Message Information","Compte mis � jour",null));
			request.getRequestDispatcher("MesActivites").forward(request, response);
	
		//	new AlertInfoJsp("Compte mis � jour", AlertJsp.Sucess, "AcceuilPro")
			//	.send(request, response);
		} else {
			new AlertInfoJsp("Un probleme est survenue", AlertJsp.Alert,
					"AcceuilPro").send(request, response);
		}

	}

	private MessageServeur testParametreRequete(String nom, String adresse,
			String commentaire, double latitude, double longitude,
			String telephone, String siteWeb, String siret) {
		// TODO Auto-generated method stub
	
		
		if (!testFormatTelephone(telephone)){
			
			return new MessageServeur(false,"Numero de téléphonne non conforme");
		
		}
	
	return  new MessageServeur(true,"Ok");
	}

	private boolean testFormatTelephone(String telephone) {
		// TODO Auto-generated method stub
		
		if (telephone==null)
			return true;
		
		if (telephone.length()==0)return true;
		
		if (telephone.length()!=14)return false;
		
		
		return true;
	}

	

}
