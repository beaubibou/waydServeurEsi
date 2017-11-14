package servlet.pro;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wayde.bean.MessageServeur;
import website.coordination.Coordination;
import website.dao.ActiviteDAO;
import website.dao.MessageDAO;
import website.enumeration.AlertJsp;
import website.html.AlertDialog;
import website.html.MessageAlertDialog;
import website.metier.ActiviteBean;
import website.metier.AuthentificationSite;

/**
 * Servlet implementation class SupprimeMessage
 */
public class SupprimeMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
	
		
		if (!authentification.isAuthentifiePro())
			return;
	
		int idMessage =0;
	
		if (request.getParameter("idMessage")!=null){
			 idMessage = Integer.parseInt(request.getParameter("idMessage"));
				
		}
	
		MessageServeur messageServeur=MessageDAO.effaceMessage(idMessage);
			
		if (messageServeur.isReponse()){
			
			
		}
		else
		{
			
			
		}
				
		
		
		
		return;	// TODO Auto-generated method stub
	}

}
