package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import website.dao.ActiviteDAO;
import website.dao.LogDAO;
import website.dao.StatDAO;
import website.metier.AuthentificationSite;
import website.metier.IndicateurWayd;
import website.metier.admin.FitreAdminActivites;
import website.metier.admin.FitreAdminLogs;
import website.metier.admin.FitreAdminProbleme;
import website.metier.admin.FitreAdminProfils;
import website.metier.admin.FitreAdminSuggestions;

/**
 * Servlet implementation class Acceuil
 */
public class Acceuil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(Acceuil.class);
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Acceuil() {
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

		if (session.getAttribute("filtreProbleme") == null)
			session.setAttribute("filtreProbleme", new FitreAdminProbleme());

		if (session.getAttribute("filtreSuggestion") == null)
			session.setAttribute("filtreSuggestion",
					new FitreAdminSuggestions());

		if (session.getAttribute("filtreProfil") == null)
			session.setAttribute("filtreProfil", new FitreAdminProfils());

		if (session.getAttribute("filtreActivite") == null)
			session.setAttribute("filtreActivite", new FitreAdminActivites());
		
		if (session.getAttribute("filtreLogs") == null)
			session.setAttribute("filtreLogs", new FitreAdminLogs());

		LogDAO.prepareStatPerf();
		IndicateurWayd indicateur = ActiviteDAO.getIndicateurs();
		indicateur.setNbrMessageByActDuJour(StatDAO.getNbrMessageByActDuJour());
		indicateur.setNbrMessageDuJour(StatDAO.getNbrMessageDuJour());
		indicateur.setNbrActiviteDuJour(StatDAO.getNbrActiviteDuJour());
		indicateur.setNbrParticipationDuJour(StatDAO
				.getNbrParticipationDuJour());
		request.setAttribute("indicateur", indicateur);
		request.getRequestDispatcher("admin/acceuil.jsp").forward(request,
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

}
