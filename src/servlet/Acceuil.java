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
			IndicateurWayd 	indicateur=ActiviteDAO.getIndicateurs();
			indicateur.setNbrMessageByActDuJour(StatDAO.getNbrMessageByActDuJour());
			indicateur.setNbrMessageDuJour(StatDAO.getNbrMessageDuJour());
			indicateur.setNbrActiviteDuJour(StatDAO.getNbrActiviteDuJour());
			indicateur.setNbrParticipationDuJour(StatDAO.getNbrParticipationDuJour());
			request.setAttribute("indicateur", indicateur);
			request.getRequestDispatcher("acceuil.jsp").forward(request, response);
		
		}
		else{
			
			response.sendRedirect("login.jsp");
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
