package servlet;

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
import website.metier.ActiviteBean;
import website.metier.AuthentificationSite;
import website.metier.Outils;
import website.metier.Pagination;
import website.metier.admin.FiltreJSP;
import website.metier.admin.FitreAdminActivites;
import website.metier.admin.FitreAdminProbleme;
import website.pager.PagerActiviteBean;

/**
 * Servlet implementation class ListActivite
 */
public class ListActivite extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ListActivite.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListActivite() {
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
		if (!authentification.isAuthentifieAdmin())
			return;

		HttpSession session = request.getSession();

		FitreAdminActivites filtreActivite = (FitreAdminActivites) session
				.getAttribute("filtreActivite");

		metAjourFiltre(request, response, filtreActivite);
		
		int page = 0;
		
		if (request.getParameter("page") != null)
			page = Integer.parseInt(request.getParameter("page"));

		System.out.println(filtreActivite);
		
		PagerActiviteBean pager = new PagerActiviteBean(filtreActivite,
				page);

		request.setAttribute("pager", pager);
	
		request.getRequestDispatcher("admin/listActivite.jsp").forward(request,
				response);

		return;

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
	}

	private void metAjourFiltre(HttpServletRequest request,
			HttpServletResponse response, FitreAdminActivites filtreActivite) {
		// TODO Auto-generated method stub
		if (request.getParameter("rayon") != null) {
			int rayon = Integer.parseInt(request.getParameter("rayon"));
			filtreActivite.setRayon(rayon);
		}
		
		if (request.getParameter("typeUser") != null) {
			int typeUser = Integer.parseInt(request
					.getParameter("typeUser"));
			filtreActivite.setTypeUser(typeUser);
		}
		
		if (request.getParameter("typeSignalement") != null) {
			int typeSignalement = Integer.parseInt(request
					.getParameter("typeSignalement"));
			filtreActivite.setTypeSignalement(typeSignalement);
		}
		
		if (request.getParameter("typeactivite") != null) {
			int typeactivite = Integer.parseInt(request
					.getParameter("typeactivite"));
			filtreActivite.setTypeactivite(typeactivite);
		}

		if (request.getParameter("latitude") != null) {
			double latitude = Double.parseDouble(request
					.getParameter("latitude"));
			filtreActivite.setLatitude(latitude);
		}

		if (request.getParameter("longitude") != null) {
			double longitude = Double.parseDouble(request
					.getParameter("longitude"));
			filtreActivite.setLongitude(longitude);
		}

		if (request.getParameter("autocomplete") != null) {
			String autocomplete = request.getParameter("autocomplete");
			filtreActivite.setVille(autocomplete);
		}

	}

}
