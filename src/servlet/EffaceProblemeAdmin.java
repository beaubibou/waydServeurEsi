package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import website.dao.ProblemeDAO;
import website.metier.AuthentificationSite;

/**
 * Servlet implementation class EffaceProblemeAdmin
 */
public class EffaceProblemeAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(EffaceProblemeAdmin.class);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EffaceProblemeAdmin() {
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
	
		if (request.getParameter("idProbleme")!=null)
			idSuggestion=Integer.parseInt((String)request.getParameter("idProbleme"));
		
		boolean retour=ProblemeDAO.supprime(idSuggestion);
		
		response.sendRedirect("ListProbleme");
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	doGet(request, response);
	}

}
