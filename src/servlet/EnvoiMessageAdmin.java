package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import website.dao.MessageDAO;
import website.metier.AuthentificationSite;

/**
 * Servlet implementation class EnvoiMessageAdmin
 */
public class EnvoiMessageAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnvoiMessageAdmin() {
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
	
		int idDestinataire=0;
		int idMessage=0;
		
		if (request.getParameter("idDestinataire")!=null)
			idDestinataire=Integer.parseInt((String)request.getParameter("idDestinataire"));
	
		if (request.getParameter("idMessage")!=null)
			idMessage=Integer.parseInt((String)request.getParameter("idMessage"));
	
		request.setAttribute("idDestinataire", idDestinataire);
		request.setAttribute("idMessage", idMessage);
		
		System.out.println(idDestinataire +"jkj "+idMessage);
		request.getRequestDispatcher("admin/envoiMessage.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		if (!authentification.isAuthentifieAdmin())
			return;
	
		int idDestinataire=0;
		int idEmetteur=authentification.getId();
		int idMessage=0;
		boolean clore=false;
		String message="";
		
		if (request.getParameter("idDestinataire")!=null)
			idDestinataire=Integer.parseInt((String)request.getParameter("idDestinataire"));
	
		if (request.getParameter("idMessage")!=null)
			 idMessage=Integer.parseInt((String)request.getParameter("idMessage"));
	
		if (request.getParameter("clore")!=null)
			clore=true;
		message=request.getParameter("message");
			
		MessageDAO.ajouteMessage(message, idDestinataire, idEmetteur);
			
		
	}

}
