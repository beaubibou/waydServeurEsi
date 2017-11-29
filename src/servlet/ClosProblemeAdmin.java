package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import wayde.bean.MessageServeur;
import website.dao.ProblemeDAO;
import website.metier.AuthentificationSite;

/**
 * Servlet implementation class ClosProblemeAdmin
 */
public class ClosProblemeAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ClosProblemeAdmin.class);
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ClosProblemeAdmin() {
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

		int idMessage = 0;

		if (request.getParameter("idmessage") != null) {
			idMessage = Integer.parseInt(request.getParameter("idmessage"));

		}

		MessageServeur messageServeur = ProblemeDAO.lireProbleme(idMessage);

		response.sendRedirect("ListProbleme");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		doPost(request, response);

	}

}
