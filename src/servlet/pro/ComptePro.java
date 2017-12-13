package servlet.pro;

import gcmnotification.AcquitAllNotificationGcm;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.sun.mail.handlers.text_html;

import texthtml.pro.CommunText;
import texthtml.pro.Erreur_HTML;
import wayde.bean.MessageServeur;
import website.dao.PersonneDAO;
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
	private static final Logger LOG = Logger.getLogger(ComptePro.class);

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

		String action = request.getParameter("action");
	
		if (action==null)
		action="noAction";
	
		
		switch (action) {
	
		case "supprimerPhoto":
			
		PersonneDAO.updatePhoto(null, authentification.getId());
		authentification.getProfil().setPhotostr(null);
		break;

		
		}

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
		int idpersonne = authentification.getId();
		MessageServeur messageServeur = testParametreRequete(nom, adresse,
				commentaire, latitude, longitude, telephone, siteWeb, siret,
				idpersonne);
		if (!messageServeur.isReponse()) {

			authentification.setAlertMessageDialog(new MessageAlertDialog(
					"Message Information", messageServeur.getMessage(), null,
					AlertJsp.warning));
			response.sendRedirect("MesActivites");
			return;

		}

		ProfilBean profil = authentification.getProfil();
		FiltreRecherche filtreRecherche = authentification.getFiltre();

		website.dao.PersonneDAO personneDAO = new website.dao.PersonneDAO();

		if (personneDAO.updateProfilProFull(nom, adresse, latitude, longitude,
				commentaire, profil.getId(), siteWeb, telephone, siret)) {

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
			request.setAttribute(AlertDialog.ALERT_DIALOG,
					new MessageAlertDialog("Message Information",
							Erreur_HTML.COMPTE_MIS_A_JOUR, null));
			request.getRequestDispatcher("MesActivites").forward(request,
					response);

		} else {
			new AlertInfoJsp("Un probleme est survenue", AlertJsp.Alert,
					"AcceuilPro").send(request, response);
		}

	}

	private MessageServeur testParametreRequete(String nom, String adresse,
			String commentaire, double latitude, double longitude,
			String telephone, String siteWeb, String siret, int idPersonne) {
		// TODO Auto-generated method stub

		nom = nom.trim();
	
		if (nom == null || nom.isEmpty()) {
			return new MessageServeur(false, Erreur_HTML.NOM_VIDE_INTERDIT);

		}

		if (nom.length() > CommunText.TAILLE_PSEUDO_MAX)
			return new MessageServeur(false,
					CommunText.PSEUDO_LIMITE_A_CARATERE());

		MessageServeur telephonneFormat = testFormatTelephone(telephone,
				idPersonne);

		if (!telephonneFormat.isReponse())
			return telephonneFormat;

		MessageServeur testFormatSiret = testFormatSiret(siret, idPersonne);

		if (!testFormatSiret.isReponse())
			return testFormatSiret;

		if (commentaire != null) {
			if (commentaire.length() > CommunText.TAILLE_DESCRIPTION_PROFIL_MAX)
				return new MessageServeur(false,
						CommunText.DESCRIPTION_PROFIL_LIMITE_A_CARATERE());

		}

		return new MessageServeur(true, "Ok");
	}

	private MessageServeur testFormatTelephone(String telephone, int idPersonne) {
		// TODO Auto-generated method stub

		if (telephone == null)
			return new MessageServeur(false, Erreur_HTML.NUMERO_TELEPHONE_VIDE);

		if (telephone.isEmpty())
			return new MessageServeur(false, Erreur_HTML.NUMERO_TELEPHONE_VIDE);

		if (telephone.length() != CommunText.TAILLE_TELEPHONNE_MAX)
			return new MessageServeur(false,
					Erreur_HTML.MAUVAISE_TAILLE_NUMERO_TELEPHONE);

		if (PersonneDAO.isTelephoneExistPersonne(telephone, idPersonne))
			return new MessageServeur(false, Erreur_HTML.TELEPHONNE_EXIST_DEJA);

		return new MessageServeur(true, "ok");
	}

	private MessageServeur testFormatSiret(String siret, int idPersonne) {
		// TODO Auto-generated method stub

		if (siret == null || siret.isEmpty())
			return new MessageServeur(false, Erreur_HTML.SIRET_VIDE);

	
		if (siret.length() != CommunText.TAILLE_SIRET_MAX)
			return new MessageServeur(false, Erreur_HTML.SIRET_MAUVAISE_TAILLE);

		if (PersonneDAO.isSiretExistPersonne(siret, idPersonne))
			return new MessageServeur(false, Erreur_HTML.SIRET_EXISTE);

		return new MessageServeur(true, "ok");
	}
}
