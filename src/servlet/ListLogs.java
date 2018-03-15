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

import website.dao.LogDAO;
import website.metier.AuthentificationSite;
import website.metier.admin.FitreAdminLogs;
import website.pager.PagerLogsBean;

/**
 * Servlet implementation class ListLogs
 */
public class ListLogs extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ListLogs.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListLogs() {
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

		FitreAdminLogs filtreLogs = (FitreAdminLogs) session
				.getAttribute("filtreLogs");

		int etatLogs = filtreLogs.getNiveau_log();
		DateTime dateDebut = filtreLogs.getDateCreationDebut();
		DateTime dateFin = filtreLogs.getDateCreationFin();
		
		if (request.getParameter("methode") != null) {
			filtreLogs.setMethode(request.getParameter("methode"));
		}
		
		if (request.getParameter("logPerf") != null) 
		{
			LogDAO.setETAT_PERF(Integer.parseInt((String) request
					.getParameter("logPerf")));
		}
		
		
		if (request.getParameter("etatLogs") != null) {
			etatLogs = Integer.parseInt((String) request
					.getParameter("etatLogs"));
			filtreLogs.setNiveau_log(etatLogs);
		
		}

		try {

			if (request.getParameter("debut") != null) {

				String datedebut = request.getParameter("debut");
				dateDebut = getDateFromString(datedebut);
				filtreLogs.setDateCreationDebut(dateDebut);

			}
			if (request.getParameter("fin") != null) {

				String datefin = request.getParameter("fin");
				dateFin = getDateFromString(datefin);
				filtreLogs.setDateCreationFin(dateFin);

			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
		}

		int pageAfficher = 0;
		if (request.getParameter("page") != null)
			pageAfficher = Integer.parseInt(request.getParameter("page"));

		PagerLogsBean pagerLogsBean = new PagerLogsBean(filtreLogs,
				pageAfficher);

		request.setAttribute("pager", pagerLogsBean);

		request.getRequestDispatcher("admin/listLogs.jsp").forward(request,
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
