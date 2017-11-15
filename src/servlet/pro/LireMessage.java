package servlet.pro;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wayde.bean.MessageServeur;
import website.dao.MessageDAO;
import website.enumeration.AlertJsp;
import website.html.MessageAlertDialog;
import website.metier.AuthentificationSite;

/**
 * Servlet implementation class LireMessage
 */
public class LireMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LireMessage() {
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
	
		MessageServeur messageServeur=MessageDAO.lireMessage(idMessage);
			
		response.sendRedirect("MesMessages");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
