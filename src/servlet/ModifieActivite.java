package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import website.dao.ActiviteDAO;
import website.metier.AuthentificationSite;

/**
 * Servlet implementation class ModifieActivite
 */
public class ModifieActivite extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifieActivite() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
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
		
		String action = request.getParameter("action");

		switch (action) {

		case "setgratuit":

			try{
			int gratuit=Integer.parseInt(request.getParameter("gratuit"));
			int idactivite=Integer.parseInt(request.getParameter("idactivite"));
			ActiviteDAO.setGratuite(idactivite, gratuit);
		
			}
			
			catch(Exception e){
			e.printStackTrace();	
		
			}
			break;

		default:
			break;
		}

	}

}
