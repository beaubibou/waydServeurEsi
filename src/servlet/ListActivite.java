package servlet;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import website.metier.AuthentificationSite;
import website.metier.admin.FitreAdminActivites;
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
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

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
		doGet(request, response);
		
	}

	private void metAjourFiltre(HttpServletRequest request,
			HttpServletResponse response, FitreAdminActivites filtreActivite) {
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
		
		if (request.getParameter("etatActivite") != null) {
			int etatActivite = Integer.parseInt(request
					.getParameter("etatActivite"));
			filtreActivite.setEtatActivite(etatActivite);
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
		
		if (request.getParameter("gratuit") != null) {
			int gratuit = Integer.parseInt(request.getParameter("gratuit"));
			filtreActivite.setGratuit(gratuit);
		}
		
		if (request.getParameter("daterecherche") != null) {
			
				DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
				DateTime dateRecherche = formatter.parseDateTime(request.getParameter("daterecherche") );
				filtreActivite.setDateRecherche(dateRecherche);
			
		}
		
		
		
		if (request.getParameter("actif") != null)
			filtreActivite.setActif(true);
		else
			filtreActivite.setActif(false);
		
		if (request.getParameter("masque") != null)
			filtreActivite.setMasque(true);
		else
			filtreActivite.setMasque(false);
		
		
		

	}

}
