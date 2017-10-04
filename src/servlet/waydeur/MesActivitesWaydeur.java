package servlet.waydeur;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import wayd.ws.WBservices;
import website.dao.ActiviteDAO;
import website.dao.CacheValueDAO;
import website.dao.TypeActiviteDAO;
import website.metier.ActiviteBean;
import website.metier.AuthentificationSite;
import website.metier.FiltreRecherche;
import website.metier.ProfilBean;
import website.metier.TypeActiviteBean;
import website.metier.TypeEtatActivite;

/**
 * Servlet implementation class MesActivitesWaydeur
 */
public class MesActivitesWaydeur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(WBservices.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MesActivitesWaydeur() {
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
		if (!authentification.isAuthentifieWaydeur())
			return;

		FiltreRecherche filtre =authentification.getFiltre();

		ArrayList<ActiviteBean> listMesActivite = ActiviteDAO.getMesActivite(
				authentification.getProfil().getId(), filtre.getTypeEtatActivite());

		if (listMesActivite.size() == 0) {

			request.setAttribute("titre", "Conseil");
			request.setAttribute("message", "il faut ajouter une activité");

			request.getRequestDispatcher("/waydeur/MessageInfo.jsp").forward(
					request, response);

		} else {
			request.setAttribute("listMesActivite", listMesActivite);
			request.getRequestDispatcher("/waydeur/mesActiviteWaydeur.jsp")
					.forward(request, response);

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
		if (!authentification.isAuthentifieWaydeur())
			return;

		FiltreRecherche filtre =authentification.getFiltre();

		
		int etatActivite = Integer.parseInt(request
				.getParameter("etatActivite"));

		filtre.setTypeEtatActivite(etatActivite);

		ArrayList<ActiviteBean> listMesActivite = ActiviteDAO.getMesActivite(
				authentification.getProfil().getId(), filtre.getTypeEtatActivite());

			if (listMesActivite.size() == 0) {

			request.setAttribute("titre", "Conseil");
			request.setAttribute("message", "il faut ajouter une activité");

			request.getRequestDispatcher("/waydeur/MessageInfo.jsp").forward(
					request, response);

		} else {
			
			request.setAttribute("listMesActivite", listMesActivite);
			request.getRequestDispatcher("/waydeur/mesActiviteWaydeur.jsp")
					.forward(request, response);

		}

	}

}
