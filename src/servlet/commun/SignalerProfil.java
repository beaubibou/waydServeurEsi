package servlet.commun;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import texthtml.pro.Erreur_HTML;
import wayde.bean.MessageServeur;
import website.dao.PersonneDAO;
import website.dao.SignalementDAO;
import website.enumeration.AlertJsp;
import website.html.AlertInfoJsp;
import website.metier.AuthentificationSite;
import website.metier.ProfilBean;

/**
 * Servlet implementation class SignalerProfil
 */
public class SignalerProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(SignalerProfil.class);
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignalerProfil() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		

		AuthentificationSite authentification = new AuthentificationSite(
				request, response);

		if (!authentification.isAuthentifie())
			return;

		int idProfil = Integer.parseInt(request.getParameter("idProfil"));

		ProfilBean profilAsignaler = PersonneDAO.getFullProfil(idProfil);

		request.setAttribute("profil", profilAsignaler);
		if (request.getParameter("idmotif") == null) {
			// Si pas de motif issu d'un detail activit� redirection vers le
			// choix du motif

			request.getRequestDispatcher("/commun/SignalerProfil.jsp").forward(
					request, response);

		}

		if (request.getParameter("idmotif") != null) {
			// Mise � jour du signalement
			int idMotif = Integer.parseInt(request.getParameter("idmotif"));

			String complement = request.getParameter("complement");
			MessageServeur messageServeur = SignalementDAO.addSignalement(
					authentification.getProfil().getId(), idProfil, idMotif,
					complement);

			if (messageServeur.isReponse()) {
				switch (authentification.getProfil().getTypeuser()) {

				case ProfilBean.WAYDEUR:
					
					new AlertInfoJsp(Erreur_HTML.PROFIL_SIGNALE, AlertJsp.Sucess,
							"MesActivitesWaydeur").send(request, response);
					return;

				case ProfilBean.PRO:
					
					new AlertInfoJsp(Erreur_HTML.PROFIL_SIGNALE, AlertJsp.Sucess,
							"MesActivites").send(request, response);
					return;
				}

			}
			if (!messageServeur.isReponse()) {
				{
			
					if (authentification.isPro()) {
						new AlertInfoJsp(messageServeur.getMessage(),
								AlertJsp.Alert, "MesActivites").send(request,response);
					
						return;
					}
					
					
					if (authentification.isWaydeur()){
						new AlertInfoJsp(messageServeur.getMessage(),
								AlertJsp.Alert, "MesActivitesWaydeur").send(
								request, response);
						return;
					}

				}
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
	}

}
