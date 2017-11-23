package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import website.dao.MessageDAO;
import website.dao.SuggestionDAO;
import website.metier.AuthentificationSite;

/**
 * Servlet implementation class EffaceSuggestionAdmin
 */
public class EffaceSuggestionAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EffaceSuggestionAdmin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		
		if (!authentification.isAuthentifieAdmin())
			return;
	
		int idSuggestion=0;
	
		if (request.getParameter("idSuggestion")!=null)
			idSuggestion=Integer.parseInt((String)request.getParameter("idSuggestion"));
		
		boolean retour=SuggestionDAO.supprime(idSuggestion);
		
		response.sendRedirect("ListSuggestion");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		doGet(request, response);
	
	}

}
