package servlet.waydeur;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import wayd.ws.WBservices;
import website.dao.CacheValueDAO;
import website.metier.DureeBean;
import website.metier.Outils;
import website.metier.ProfilBean;
import website.metier.QuantiteWaydeurBean;
import website.metier.SexeBean;
import website.metier.TypeActiviteBean;

/**
 * Servlet implementation class AjouteActiviteWaydeur
 */
public class AjouteActiviteWaydeur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(WBservices.class);


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AjouteActiviteWaydeur() {
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
		HttpSession session = request.getSession();
		ProfilBean profil = (ProfilBean) session.getAttribute("profil");

		LOG.info("doget");
		if (profil == null) {
			response.sendRedirect("auth/login.jsp");
			return;
		}

		if (profil.getTypeuser() != ProfilBean.WAYDEUR
				|| profil.isPremiereconnexion()) {
			response.sendRedirect("auth/login.jsp");
			return;
		}

		
		ArrayList<TypeActiviteBean> listTypeActivite=new CacheValueDAO().getListTypeActiviteWaydeur();
		ArrayList<DureeBean> listDuree=new CacheValueDAO().getListDuree();
		ArrayList<QuantiteWaydeurBean> listQuantiteWaydeur=new CacheValueDAO().getListQuantiteWaydeur();
		
		request.setAttribute("listTypeActivite", listTypeActivite);
		request.setAttribute("listDuree", listDuree);
		
		request.setAttribute("listQuantiteWaydeur", listQuantiteWaydeur);
		
	
		request.getRequestDispatcher("waydeur/form_creationactiviteWaydeur.jsp").forward(request, response);
		
		
	

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		LOG.info("doPost");
		HttpSession session = request.getSession();
		ProfilBean profil = (ProfilBean) session.getAttribute("profil");

		String titre = request.getParameter("titre");
		String adresse = request.getParameter("adresse");
		String description = request.getParameter("description");

		double latitude = Double.parseDouble(request.getParameter("latitude"));
		double longitude = Double
				.parseDouble(request.getParameter("longitude"));
		int maxwaydeur = Integer.parseInt(request.getParameter("maxwaydeur"));
		int typeactivite = Integer.parseInt(request
				.getParameter("typeactivite"));
		int duree = Integer.parseInt(request.getParameter("duree"));
		
		

		if (profil != null) {
			website.dao.ActiviteDAO activiteDAO = new website.dao.ActiviteDAO();

			activiteDAO.addActiviteWaydeur(profil.getId(), titre, description,
					  adresse, latitude, longitude,
					typeactivite,  maxwaydeur,duree,ProfilBean.WAYDEUR);

			response.sendRedirect("AcceuilWaydeur");

		}
	}

}
