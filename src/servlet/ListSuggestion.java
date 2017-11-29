package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import website.dao.SuggestionDAO;
import website.metier.AuthentificationSite;
import website.metier.SuggestionBean;
import website.metier.admin.FitreAdminSuggestions;

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
		// TODO Auto-generated method stub
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

				ArrayList<SuggestionBean> listSuggestion = new ArrayList<SuggestionBean>();
			listSuggestion = SuggestionDAO.getListSuggestion(
					filtreSuggestion.getEtatSuggestion(),
					filtreSuggestion.getDateDebutCreation(),
					filtreSuggestion.getDateFinCreation());
			request.setAttribute("listSuggestion", listSuggestion);
			request.getRequestDispatcher("admin/listSuggestion.jsp").forward(request,
					response);

		
		

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
	
	public DateTime getDateFromString(String datestr) throws ParseException {

		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime dt = formatter.parseDateTime(datestr);
		return dt;
	}

}
