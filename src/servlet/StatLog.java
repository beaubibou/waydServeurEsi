package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import website.metier.AuthentificationSite;
import website.metier.admin.FitreAdminLogs;
import website.pager.PagerLogsBean;

/**
 * Servlet implementation class StatLog
 */
public class StatLog extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(StatLog.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StatLog() {
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

	
		String action = request.getParameter("action");

		if (action != null) {

			switch (action) {
			case "detailStat":

				java.util.Date dateDebut = null;
				String dateMessage = request.getParameter("datemessage");
				String log_level = request.getParameter("log_level");
			
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
				int pageAfficher = 0;
			
				try {
					
				
					if (request.getParameter("page") != null)
						pageAfficher = Integer.parseInt(request.getParameter("page"));
					
					dateDebut = simpleDateFormat.parse(dateMessage);
			
					HttpSession session = request.getSession();

					FitreAdminLogs filtreLogs = (FitreAdminLogs) session
							.getAttribute("filtreLogs");
					
					
					filtreLogs.setLogLevel(log_level);
					
					filtreLogs.setDateCreationDebut(new DateTime(dateDebut.getTime()));
					filtreLogs.setDateCreationFin(new DateTime(dateDebut.getTime()));
					
					PagerLogsBean pagerLogsBean = new PagerLogsBean(filtreLogs,
							pageAfficher);

					request.setAttribute("pager", pagerLogsBean);

					request.getRequestDispatcher("admin/listLogs.jsp").forward(request,
							response);
			
				} catch (ParseException e) {
				
					LOG.error( ExceptionUtils.getStackTrace(e));
				
				}
				
			
		
				
			
				
//				ArrayList<LogBean> listLogs = LogDAO.getListLogDetail(dateMessage,
//						log_level);
//
//				request.setAttribute("listlogs", listLogs);
//				request.getRequestDispatcher("admin/listLogsDetail.jsp").forward(
//						request, response);
//				
//				LOG.info(dateMessage);
//				LOG.info(log_level);
//				return;

			}

		} else {

			request.getRequestDispatcher("admin/statLog.jsp").forward(request,
					response);

		}

	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		if (!authentification.isAuthentifieAdmin())
			return;

		request.getRequestDispatcher("admin/statLog.jsp").forward(request,
				response);

	
	}

}
