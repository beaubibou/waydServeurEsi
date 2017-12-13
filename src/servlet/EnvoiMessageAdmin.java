package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import website.dao.MessageDAO;
import website.metier.AuthentificationSite;

/**
 * Servlet implementation class EnvoiMessageAdmin
 */
public class EnvoiMessageAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(EnvoiMessageAdmin.class);
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EnvoiMessageAdmin() {
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

		int idDestinataire = 0;
		int idMessage = 0;

		if (request.getParameter("idPersonne") != null)
			idDestinataire = Integer.parseInt((String) request
					.getParameter("idPersonne"));

		if (request.getParameter("idMessage") != null)
			idMessage = Integer.parseInt((String) request
					.getParameter("idMessage"));

		String formInit = request.getParameter("formInit");
		
		request.setAttribute("idDestinataire", idDestinataire);
		request.setAttribute("idMessage", idMessage);
		request.setAttribute("formInit", formInit);

		request.getRequestDispatcher("admin/envoiMessage.jsp").forward(request,
				response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		if (!authentification.isAuthentifieAdmin())
			return;

		int idDestinataire = 0;
		int idEmetteur = authentification.getId();
		int idMessage = 0;
		boolean clore = false;
		String message = "";

		if (request.getParameter("idPersonne") != null)
			idDestinataire = Integer.parseInt((String) request
					.getParameter("idPersonne"));

		if (request.getParameter("idMessage") != null)
			idMessage = Integer.parseInt((String) request
					.getParameter("idMessage"));

		if (request.getParameter("clore") != null)
			clore = true;
		message = request.getParameter("message");

		String formInit = request.getParameter("formInit");
		MessageDAO.ajouteMessage(message, idDestinataire, idEmetteur);

		switch (formInit) {

		case "listProfil":

			response.sendRedirect("ListProfil");
			break;

		case "listSuggestion":

			response.sendRedirect("ListSuggestion");
			break;

		case "detailParticipant":

			request.setAttribute("idPersonne", Integer.toString(idDestinataire));
			request.getRequestDispatcher("DetailParticipant").forward(request,
					response);
			break;

		

		}
		
		return;

	}

}
