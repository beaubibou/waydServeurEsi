package servlet.commun;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import wayd.ws.WBservices;
import website.coordination.Coordination;
import website.dao.ActiviteDAO;
import website.enumeration.AlertJsp;
import website.html.AlertInfoJsp;
import website.metier.ActiviteBean;
import website.metier.AuthentificationSite;
import website.metier.FiltreRecherche;
import website.metier.PhotoActiviteBean;
import website.metier.ProfilBean;

/**
 * Servlet implementation class DetailActiviteSite
 */
public class DetailActiviteSite extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(DetailActiviteSite.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DetailActiviteSite() {
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

		FiltreRecherche filtre = authentification.getFiltre();
		
		if (filtre == null) {
			new AlertInfoJsp("Le filtre est null", AlertJsp.Alert,
					"MesActivitesWaydeur").send(request, response);
					return;
		}

		int idActivite = Integer.parseInt(request.getParameter("idactivite"));
	
		ActiviteBean activite = new Coordination().getActivite(idActivite);
		ArrayList<PhotoActiviteBean>listPhotoActivite=ActiviteDAO.getListPhotoActivite(idActivite);
		
		if (activite==null){
			
			request.setAttribute("message", "L'activité n'existe plus");
			request.getRequestDispatcher("/commun/erreurConnection.jsp")
			.forward(request, response);

			return;
		}
		
		activite.setPositionRecherche(filtre.getLatitude(),
				filtre.getLongitude());
		
		
		ActiviteDAO.addNbrVu(authentification.getId(), idActivite,activite.getIdorganisateur());
		
		request.setAttribute("activite", activite);
		request.setAttribute("listPhoto", listPhotoActivite);
	
		// recuepre les coordonnes du filtre pour le calcul de la distance
		
		switch (activite.getTypeUser()) {
		case ProfilBean.PRO:
			
			request.getRequestDispatcher("/commun/detailActivitePro.jsp")
					.forward(request, response);

			break;

		case ProfilBean.WAYDEUR:
			
			request.getRequestDispatcher("/commun/detailActiviteWaydeur.jsp")
					.forward(request, response);

			break;

		default:

			break;
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
