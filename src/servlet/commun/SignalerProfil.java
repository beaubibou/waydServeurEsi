package servlet.commun;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wayde.bean.MessageServeur;
import website.coordination.Coordination;
import website.dao.ActiviteDAO;
import website.dao.PersonneDAO;
import website.dao.SignalementDAO;
import website.enumeration.AlertJsp;
import website.html.AlertInfoJsp;
import website.metier.ActiviteBean;
import website.metier.AuthentificationSite;
import website.metier.ProfilBean;

/**
 * Servlet implementation class SignalerProfil
 */
public class SignalerProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		// TODO Auto-generated method stub

		AuthentificationSite authentification = new AuthentificationSite(
				request, response);

		if (!authentification.isAuthentifie())
			return;

		int idProfil = Integer.parseInt(request.getParameter("idProfil"));

		ProfilBean profilAsignaler = PersonneDAO.getFullProfil(idProfil);

		request.setAttribute("profil", profilAsignaler);
		if (request.getParameter("idmotif") == null) {
			// Si pas de motif issu d'un detail activité redirection vers le
			// choix du motif

			request.getRequestDispatcher("/commun/SignalerProfil.jsp").forward(
					request, response);
System.out.println("id motif== null");
		}

		if (request.getParameter("idmotif") != null) {
			// Mise à jour du signalement
			int idMotif = Integer.parseInt(request.getParameter("idmotif"));

			String complement = request.getParameter("complement");
			System.out.println("id motif!= null");
			MessageServeur messageServeur = SignalementDAO.addSignalement(
					authentification.getProfil().getId(), idProfil, idMotif,
					complement);

			if (messageServeur.isReponse()) {
				System.out.println("message servuer ok");
				switch (authentification.getProfil().getTypeuser()) {

				case ProfilBean.WAYDEUR:
					System.out.println("Profil waydeur");
					
					new AlertInfoJsp("Profil signalé", AlertJsp.Sucess,
							"MesActivitesWaydeur").send(request, response);
					return;

				case ProfilBean.PRO:
					System.out.println("Profil pro");
					
					new AlertInfoJsp("Profil signalé", AlertJsp.Sucess,
							"MesActivites").send(request, response);
					return;
				}

			}
			if (!messageServeur.isReponse()) {
				{
					System.out.println("message servuer nok");

					if (authentification.isPro()) {
						new AlertInfoJsp(messageServeur.getMessage(),
								AlertJsp.Alert, "MesActivites").send(request,response);
						System.out.println("not reponse pro");
						return;
					}
					
					
					if (authentification.isWaydeur())
						new AlertInfoJsp(messageServeur.getMessage(),
								AlertJsp.Alert, "MesActivitesWaydeur").send(
								request, response);
					

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
