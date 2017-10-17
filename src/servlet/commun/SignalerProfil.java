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
		
		ProfilBean profilAsignaler =PersonneDAO.getFullProfil(idProfil);

		if (request.getParameter("idmotif") == null) {
			// Si pas de motif issu d'un detail activité redirection vers le
			// choix du motif
		
		request.getRequestDispatcher("/commun/SignalerProfil.jsp").forward(
					request, response);

		} else {
			// Mise à jour du signalement
			int idMotif = Integer.parseInt(request.getParameter("idmotif"));
		
			String complement = request.getParameter("complement");
			boolean retour=SignalementDAO.addSignalement(authentification.getProfil().getId(),
					idProfil, idMotif, complement);

			if (retour) {

				switch (authentification.getProfil().getTypeuser()) {

				case ProfilBean.WAYDEUR:
					new AlertInfoJsp("Profil signalé", AlertJsp.Sucess, "MesActivitesWaydeur").send(request, response);
					response.sendRedirect("MesActivitesWaydeur");
					break;
				case ProfilBean.PRO:
					new AlertInfoJsp("Profil signalé", AlertJsp.Sucess, "MesActivites").send(request, response);
					
				
					break;

				}

				return;
			} else
			{

				if (authentification.isAuthentifiePro())
				new AlertInfoJsp("Une erreur est survenue", AlertJsp.Alert, "MesActivites").send(request, response);
				
				if (authentification.isAuthentifieWaydeur())
					new AlertInfoJsp("Une erreur est survenue", AlertJsp.Alert, "MesActivitesWaydeur").send(request, response);
				
				
				return;
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
