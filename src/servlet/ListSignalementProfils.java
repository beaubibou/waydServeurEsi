package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import website.dao.SignalementDAO;
import website.metier.AuthentificationSite;
import website.metier.SignalementCount;

/**
 * Servlet implementation class ListSignalement
 */
public class ListSignalementProfils extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListSignalementProfils() {
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
	
			ArrayList<SignalementCount> listSignalementCount = new ArrayList<SignalementCount>();
			listSignalementCount = SignalementDAO.getCountSignalementBy();
			request.setAttribute("listSignalementCount", listSignalementCount);
			request.getRequestDispatcher("admin/listSignalement.jsp").forward(request,
					response);

	

		// TODO Auto-generated method stub
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
		
		ArrayList<SignalementCount> listSignalementCount = new ArrayList<SignalementCount>();
		listSignalementCount = SignalementDAO.getCountSignalementBy();
		request.setAttribute("listSignalementCount", listSignalementCount);
		request.getRequestDispatcher("admin/listSignalement.jsp").forward(request,
				response);
	}

}
