package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import website.dao.ParticipantDAO;
import website.metier.AuthentificationSite;

/**
 * Servlet implementation class EffaceParticipant
 */
public class EffaceParticipant extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(EffaceParticipant.class);
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EffaceParticipant() {
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
		
		int idPersonne=0;
	
		if (request.getParameter("idPersonne") != null)
			idPersonne = Integer.parseInt((String) request
					.getParameter("idPersonne"));

		boolean retour = ParticipantDAO.supprimeParticipant(idPersonne);

		response.sendRedirect("Acceuil");

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
