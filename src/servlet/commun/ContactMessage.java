package servlet.commun;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import texthtml.pro.Erreur_HTML;
import wayd.ws.WBservices;
import website.dao.SuggestionDAO;
import website.enumeration.AlertJsp;
import website.html.AlertInfoJsp;
import website.html.MessageAlertDialog;
import website.metier.AuthentificationSite;
import website.metier.ProfilBean;
import website.metier.SuggestionBean;

/**
 * Servlet implementation class ContactMessage
 */
public class ContactMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ContactMessage.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ContactMessage() {
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

		if (!authentification.isAuthentifie())
			return;

		String mail = request.getParameter("mail");
		String message = request.getParameter("message");

		if (SuggestionDAO.addSuggestion(authentification.getProfil().getId(),
				mail, message)) 
		{

			switch (authentification.getProfil().getTypeuser()) {

			case ProfilBean.PRO:

				authentification.setAlertMessageDialog( new MessageAlertDialog
						("Message Information","Votre message a été pris en compte",null,AlertJsp.Sucess));
				response.sendRedirect("MesActivites");
			
				break;

			case ProfilBean.WAYDEUR:
				new AlertInfoJsp(Erreur_HTML.MESSAGE_ENVOYE, AlertJsp.Sucess,
						"MesActivitesWaydeur").send(request, response);
				
				break;

			}

			return;
		}

		else {
			
			
			switch (authentification.getProfil().getTypeuser()) {

			case ProfilBean.PRO:

				authentification.setAlertMessageDialog( new MessageAlertDialog
						("Message Information","Une erreur est survenue",null,AlertJsp.danger));
				response.sendRedirect("MesActivites");
			
				break;

			case ProfilBean.WAYDEUR:
				authentification.setAlertMessageDialog( new MessageAlertDialog
						("Message Information","Une erreur est survenue",null,AlertJsp.danger));
				response.sendRedirect("MesActivites");
			
				
				break;

			}
			return;

		}

	}

}
