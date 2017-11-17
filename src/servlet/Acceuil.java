package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import website.dao.ActiviteDAO;
import website.dao.StatDAO;
import website.metier.IndicateurWayd;
import website.metier.admin.FitreAdminActivites;
import website.metier.admin.FitreAdminProbleme;
import website.metier.admin.FitreAdminProfils;
import website.metier.admin.FitreAdminSuggestions;

/**
 * Servlet implementation class Acceuil
 */
public class Acceuil extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		HttpSession session = request.getSession();
	
		
		if (session.getAttribute("profil") != null )
		{
			if (session.getAttribute("filtreProbleme")==null)
				session.setAttribute("filtreProbleme",new FitreAdminProbleme());
			
			
			if (session.getAttribute("filtreSuggestion")==null)
				session.setAttribute("filtreSuggestion",new FitreAdminSuggestions());
			
			if (session.getAttribute("filtreProfils")==null)
				session.setAttribute("filtreProfils",new FitreAdminProfils());
		
			if (session.getAttribute("filtreActivite")==null)
				session.setAttribute("filtreActivite",new FitreAdminActivites());
			
			
			IndicateurWayd 	indicateur=ActiviteDAO.getIndicateurs();
			indicateur.setNbrMessageByActDuJour(StatDAO.getNbrMessageByActDuJour());
			indicateur.setNbrMessageDuJour(StatDAO.getNbrMessageDuJour());
			indicateur.setNbrActiviteDuJour(StatDAO.getNbrActiviteDuJour());
			indicateur.setNbrParticipationDuJour(StatDAO.getNbrParticipationDuJour());
			request.setAttribute("indicateur", indicateur);
			request.getRequestDispatcher("admin/acceuil.jsp").forward(request, response);
		
		}
		else{
			
			response.sendRedirect("auth/login.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
