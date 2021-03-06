package servlet;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import website.metier.AuthentificationSite;
import website.metier.admin.FitreAdminSuggestions;
import website.pager.PagerSuggestionBean;

/**
 * Servlet implementation class ListSuggestion
 */
public class ListSuggestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ListSuggestion.class);
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListSuggestion() {
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
		if (!authentification.isAuthentifieAdmin())
			return;
		
		HttpSession session = request.getSession();

		FitreAdminSuggestions filtreSuggestion = (FitreAdminSuggestions) session
				.getAttribute("filtreSuggestion");
		
		int etatSuggestion = filtreSuggestion.getEtatSuggestion();
		DateTime dateDebut = filtreSuggestion.getDateDebutCreation();
		DateTime dateFin = filtreSuggestion.getDateFinCreation();

		if (request.getParameter("etatSuggestion") != null) {
			etatSuggestion = Integer.parseInt((String) request
					.getParameter("etatSuggestion"));
			filtreSuggestion.setEtatSuggestion(etatSuggestion);
		}

		try {

			if (request.getParameter("debut") != null) {

				String datedebut = request.getParameter("debut");
				dateDebut = getDateFromString(datedebut);
				filtreSuggestion.setDateDebutCreation(dateDebut);

			}
			if (request.getParameter("fin") != null) {

				String datefin = request.getParameter("fin");
				dateFin = getDateFromString(datefin);
				filtreSuggestion.setDateFinCreation(dateFin);

			}

		} catch (ParseException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
		}

		int pageAfficher = 0;
		if (request.getParameter("page") != null)
			pageAfficher = Integer.parseInt(request.getParameter("page"));
		
		PagerSuggestionBean pagerProblemeBean = new PagerSuggestionBean(
				filtreSuggestion, pageAfficher);
	
		request.setAttribute("pager", pagerProblemeBean);
		
			
			
		request.getRequestDispatcher("admin/listSuggestion.jsp").forward(request,
					response);

		
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
	doGet(request, response);
	}
	
	public DateTime getDateFromString(String datestr) throws ParseException {

		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime dt = formatter.parseDateTime(datestr);
		return dt;
	}

}
