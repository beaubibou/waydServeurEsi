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
import website.metier.ActiviteBean;
import website.metier.FiltreRecherche;
import website.metier.ProfilBean;
import website.metier.QuandBean;
import website.metier.RayonBean;
import website.metier.TypeAccess;
import website.metier.TypeActiviteBean;
import website.metier.TypeUser;

/**
 * Servlet implementation class RechercheWaydeur
 */
public class RechercheWaydeur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(WBservices.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RechercheWaydeur() {
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
		LOG.info("doGet");
		HttpSession session = request.getSession();

		// Recupere le filtre de la recherche stocké dans la session

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

		ArrayList<TypeActiviteBean> listTypeActivite = CacheValueDAO
				.getListTypeActiviteToutes();
		ArrayList<TypeAccess> listTypeAccess = CacheValueDAO
				.getListTypeAccess();
		ArrayList<TypeUser> listTypeUser = CacheValueDAO.getListTypeUser();
		ArrayList<QuandBean> listQuand = CacheValueDAO.getListQuand();
		ArrayList<RayonBean> listRayon = CacheValueDAO.getListRayon();

		request.setAttribute("listTypeActivite", listTypeActivite);
		request.setAttribute("listTypeAccess", listTypeAccess);
		request.setAttribute("listTypeUser", listTypeUser);
		request.setAttribute("listQuand", listQuand);
		request.setAttribute("listRayon", listRayon);
		request.getRequestDispatcher("waydeur/rechecheWaydeur.jsp").forward(
				request, response);

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

		LOG.info("doPost");
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

		int typeacess = Integer.parseInt(request.getParameter("typeaccess"));
		int typeactivite = Integer.parseInt(request
				.getParameter("typeactivite"));
		int typeuser = Integer.parseInt(request.getParameter("typeuser"));
		int commence = Integer.parseInt(request.getParameter("commence"));
		int rayon = Integer.parseInt(request.getParameter("rayon"));
		String adresse = request.getParameter("adresse");
		double latitude = Double.parseDouble(request.getParameter("latitude"));
		double longitude = Double
				.parseDouble(request.getParameter("longitude"));

		String motcle = (request.getParameter("motcle"));

		// ********** Recharge le filtre**********
		ArrayList<TypeActiviteBean> listTypeActivite = CacheValueDAO
				.getListTypeActiviteToutes();
		ArrayList<TypeAccess> listTypeAccess = CacheValueDAO
				.getListTypeAccess();
		ArrayList<TypeUser> listTypeUser = CacheValueDAO.getListTypeUser();
		ArrayList<QuandBean> listQuand = CacheValueDAO.getListQuand();
		ArrayList<RayonBean> listRayon = CacheValueDAO.getListRayon();

		filtre.setQuand(commence);
		filtre.setTyperUser(typeuser);
		filtre.setTypeActivite(typeactivite);
		filtre.setRayon(rayon);
		filtre.setLatitude(latitude);
		filtre.setLatitude(longitude);
		filtre.setAdresse(adresse);
		request.setAttribute("listTypeActivite", listTypeActivite);
		request.setAttribute("listTypeAccess", listTypeAccess);
		request.setAttribute("listTypeUser", listTypeUser);
		request.setAttribute("listQuand", listQuand);
		request.setAttribute("listRayon", listRayon);

		// ************************************
		LOG.info("type access" + typeacess);
		LOG.info("type activite" + typeactivite);
		LOG.info("type user" + typeuser);
		LOG.info("commence" + commence);
		LOG.info("rayon" + rayon);
		LOG.info("latitude" + latitude);
		LOG.info("longitude" + longitude);
		LOG.info("mot cle" + motcle);

		rayon = rayon * 1000;
		ArrayList<ActiviteBean> listActivite = new ActiviteDAO()
				.getListActivites(latitude, longitude, rayon, typeactivite,
						motcle, typeuser, typeacess, commence);

		LOG.info("Nbr resultat" + listActivite.size());
		request.setAttribute("listActivite", listActivite);

		request.getRequestDispatcher("waydeur/rechecheWaydeur.jsp").forward(
				request, response);

	}

}
