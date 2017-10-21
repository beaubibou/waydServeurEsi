package servlet.commun;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.sun.mail.handlers.message_rfc822;

import wayd.ws.WBservices;
import wayde.bean.MessageServeur;
import website.coordination.Coordination;
import website.dao.ActiviteDAO;
import website.enumeration.AlertJsp;
import website.html.AlertInfoJsp;
import website.metier.ActiviteBean;
import website.metier.AuthentificationSite;
import website.metier.ProfilBean;

/**
 * Servlet implementation class SignalerActivite
 */
public class SignalerActivite extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(SignalerActivite.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignalerActivite() {
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

		AuthentificationSite authentification = new AuthentificationSite(
				request, response);

		if (!authentification.isAuthentifie())
			return;

		int idActivite = Integer.parseInt(request.getParameter("idActivite"));
		ActiviteBean activite = new Coordination().getActivite(idActivite);
		request.setAttribute("activite", activite);

		if (request.getParameter("idmotif") == null) {
			// Si pas de motif issu d'un detail activité redirection vers le
			// choix du motif
			request.getRequestDispatcher("/commun/SignalerActivite.jsp")
					.forward(request, response);

		}

		if (request.getParameter("idmotif") != null) {
			// Mise à jour du signalement
			int idMotif = Integer.parseInt(request.getParameter("idmotif"));

			MessageServeur message = new ActiviteDAO().signalerActivite(
					authentification.getProfil().getId(), activite.getId(),
					idMotif, "", activite.getTitre(), activite.getLibelle());

			if (message.isReponse()) {

				switch (authentification.getProfil().getTypeuser()) {

				case ProfilBean.WAYDEUR:

					new AlertInfoJsp("Profil signalé", AlertJsp.Sucess,
							"MesActivitesWaydeur").send(request, response);
					return;
					
				case ProfilBean.PRO:
					new AlertInfoJsp("Profil signalé", AlertJsp.Sucess,
							"MesActivites").send(request, response);
					return;

				}

				return;
			}
			
			if (!message.isReponse()){
						
				if (authentification.isPro()) {
					new AlertInfoJsp(message.getMessage(),
							AlertJsp.Alert, "MesActivites").send(request,response);
				
					return;
				}
								
				if (authentification.isWaydeur()){
					new AlertInfoJsp(message.getMessage(),
							AlertJsp.Alert, "MesActivitesWaydeur").send(
							request, response);
					return;
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
		// TODO Auto-generated method stub
	}

}
