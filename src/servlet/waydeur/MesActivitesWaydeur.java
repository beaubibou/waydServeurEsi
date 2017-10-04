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
		LOG.info("doGet");
		HttpSession session = request.getSession();
		ProfilBean profil = (ProfilBean) session.getAttribute("profil");

		if (profil == null) {
			response.sendRedirect("auth/login.jsp");
			return;
		}

		if (profil.getTypeuser() != ProfilBean.WAYDEUR
				|| profil.isPremiereconnexion()) {
			response.sendRedirect("auth/login.jsp");
			return;
		}

		FiltreRecherche filtre = (FiltreRecherche) session
				.getAttribute("filtreRecherche");

		if (filtre == null) {
			filtre = new FiltreRecherche();
			session.setAttribute("filtreRecherche", filtre);
		}

		ArrayList<TypeActiviteBean> listTypeActivite = TypeActiviteDAO
				.getListTypeActivite();
		request.setAttribute("listTypeActivite", listTypeActivite);
		ArrayList<TypeEtatActivite> listEtatActivite = CacheValueDAO
				.getListEtatActivite();

		request.setAttribute("listEtatActivite", listEtatActivite);
		System.out.println("lst etat" + listEtatActivite.size());
		LOG.info("taille" + listTypeActivite.size());

		ArrayList<ActiviteBean> listMesActivite = ActiviteDAO.getMesActivite(
				profil.getId(), filtre.getTypeEtatActivite());

		if (listMesActivite.size() == 0) {

			request.setAttribute("titre", "Conseil");
			request.setAttribute("message", "il faut ajouter une activit�");

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

		HttpSession session = request.getSession();
		ProfilBean profil = (ProfilBean) session.getAttribute("profil");

		if (profil == null) {
			response.sendRedirect("auth/login.jsp");
			return;
		}

		if (profil.getTypeuser() != ProfilBean.WAYDEUR
				|| profil.isPremiereconnexion()) {
			response.sendRedirect("auth/login.jsp");
			return;
		}

		FiltreRecherche filtre = (FiltreRecherche) session
				.getAttribute("filtreRecherche");

		if (filtre == null) {
			filtre = new FiltreRecherche();
			session.setAttribute("filtreRecherche", filtre);
		}

		int etatActivite = Integer.parseInt(request
				.getParameter("etatActivite"));

		filtre.setTypeEtatActivite(etatActivite);

		ArrayList<ActiviteBean> listMesActivite = ActiviteDAO.getMesActivite(
				profil.getId(), filtre.getTypeEtatActivite());

		ArrayList<TypeEtatActivite> listEtatActivite = CacheValueDAO
				.getListEtatActivite();

		request.setAttribute("listEtatActivite", listEtatActivite);

		if (listMesActivite.size() == 0) {

			request.setAttribute("titre", "Conseil");
			request.setAttribute("message", "il faut ajouter une activit�");

			request.getRequestDispatcher("/waydeur/MessageInfo.jsp").forward(
					request, response);

		} else {
			request.setAttribute("listMesActivite", listMesActivite);
			request.getRequestDispatcher("/waydeur/mesActiviteWaydeur.jsp")
					.forward(request, response);

		}

	}

}
