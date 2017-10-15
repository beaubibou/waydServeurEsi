package servlet.commun;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import wayd.ws.WBservices;
import website.dao.SuggestionDAO;
import website.enumeration.AlertJsp;
import website.html.AlertInfoJsp;
import website.metier.AuthentificationSite;
import website.metier.SuggestionBean;

/**
 * Servlet implementation class ContactMessage
 */
public class ContactMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(WBservices.class);

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		LOG.info("doGet");
	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		LOG.info("doGet");

		AuthentificationSite authentification = new AuthentificationSite(
				request, response);

		if (!authentification.isAuthentifie())
			return;
		
	
		String mail = request.getParameter("mail");
		String message = request.getParameter("message");
	
		if (SuggestionDAO.addSuggestion(authentification.getProfil().getId(), mail, message)){
		new	AlertInfoJsp("Message envoyé",AlertJsp.Sucess,"MesActivites").send(request, response);;
		return;	
		}
		
		else
		{
			new	AlertInfoJsp("Echec. Une erreur est survenue",AlertJsp.Alert,"MesActivites").send(request, response);;
			return;	
			
			
		}
		
		
	}

}
