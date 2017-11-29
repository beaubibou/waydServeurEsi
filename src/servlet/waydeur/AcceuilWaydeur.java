package servlet.waydeur;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import wayde.bean.CxoPool;
import website.metier.AuthentificationSite;
import website.metier.ProfilBean;

/**
 * Servlet implementation class AcceuilWaydeur
 */
public class AcceuilWaydeur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(AcceuilWaydeur.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AcceuilWaydeur() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	
		// ********* Regle d'authentification*********************

		AuthentificationSite authentification = new AuthentificationSite(
				request, response);

		if (!authentification.isAuthentifieWaydeur())
			return;

		request.getRequestDispatcher("MesActivitesWaydeur").forward(request,
				response);

	

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
