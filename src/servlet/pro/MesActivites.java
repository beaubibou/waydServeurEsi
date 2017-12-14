package servlet.pro;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import website.dao.ActiviteDAO;
import website.html.JumbotronJsp;
import website.metier.ActiviteBean;
import website.metier.AuthentificationSite;
import website.metier.FiltreRecherche;
import website.metier.TypeEtatActivite;

/**
 * Servlet implementation class MesActivites
 */
public class MesActivites extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(MesActivites.class);
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MesActivites() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// ********* Regle d'authentification*********************
		doPost(request, response);
	}

	private void afficheJumbotron(int nbrActivite, HttpServletRequest request,
			int typeEtatActivite) {

		JumbotronJsp jumbotron = null;
		if (nbrActivite == 0) {

			switch (typeEtatActivite) {

			case TypeEtatActivite.ENCOURS:
				jumbotron = new JumbotronJsp("Informations",
						"Vous n'avez aucune activité en cours."
						+ " N'hésiter à proposer des activités via le menu"
						+ " <a href='/wayd/AjouteActivitePro'>Proposer. </a>", "Proposez des activites");
				request.setAttribute("jumbotron", jumbotron);
				break;

			case TypeEtatActivite.PLANIFIEE:
				jumbotron = new JumbotronJsp("Informations",
						"Vous n'avez aucune activité planifiée. N'hésiter à proposer"
						+ " des activités via le menu <a href='/wayd/AjouteActivitePlanifiee'>Proposer. </a>", 
						"Proposez des activites");
				request.setAttribute("jumbotron", jumbotron);
				break;

			case TypeEtatActivite.TOUTES:
				jumbotron = new JumbotronJsp("Informations",
						"Vous n'avez aucune activité en cours."
						+ " N'hésiter à proposer des activités via le menu"
						+ " <a href='/wayd/AjouteActivitePro'>Proposer. </a>", "Proposez des activites");
				request.setAttribute("jumbotron", jumbotron);
				break;

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

		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		if (!authentification.isAuthentifiePro())
			return;
		
		
		FiltreRecherche filtre = authentification.getFiltre();
		
		if (request.getParameter("etatActivite") != null) {
			int etatActivite = Integer.parseInt(request
					.getParameter("etatActivite"));
			filtre.setTypeEtatActivite(etatActivite);

		}

		ArrayList<ActiviteBean> listMesActivite = ActiviteDAO.getMesActivite(
				authentification.getProfil().getId(),
				filtre.getTypeEtatActivite());

		afficheJumbotron(listMesActivite.size(), request,
				filtre.getTypeEtatActivite());

		request.setAttribute("listMesActivite", listMesActivite);
		request.getRequestDispatcher("/pro/mesActivite.jsp").forward(request,
				response);

	}
}
