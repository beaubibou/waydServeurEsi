package servlet.pro;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import wayde.bean.MessageServeur;
import website.dao.MessageDAO;
import website.enumeration.AlertJsp;
import website.html.MessageAlertDialog;
import website.metier.AuthentificationSite;

/**
 * Servlet implementation class SupprimeMessage
 */
public class SupprimeMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(SupprimeMessage.class);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupprimeMessage() {
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
	
		
		if (!authentification.isAuthentifiePro())
			return;
	
		int idMessage =0;
	
		if (request.getParameter("idmessage")!=null){
			 idMessage = Integer.parseInt(request.getParameter("idmessage"));
				
		}
	
		MessageServeur messageServeur=MessageDAO.effaceMessage(idMessage);
			
		if (messageServeur.isReponse()){
			
			authentification.setAlertMessageDialog(new MessageAlertDialog(
					"Message Information", "Message supprimé", null,AlertJsp.Sucess));
			response.sendRedirect("MesMessages");
			return;
			
		}
		else
		{
			authentification.setAlertMessageDialog(new MessageAlertDialog(
					"Message Information", "Une erreur est survenue", null,AlertJsp.warning));
			response.sendRedirect("MesMessages");
			return;
			
		}
				
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
	
		
		if (!authentification.isAuthentifiePro())
			return;
		
		response.sendRedirect("MesActivites");
		
		
	}

}
