package servlet.pro;

import gcmnotification.AcquitAllNotificationGcm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import website.metier.AuthentificationSite;

/**
 * Servlet implementation class AcceuilPro
 */
public class AcceuilPro extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(AcceuilPro.class);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AcceuilPro() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doPost(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	//*********  Regle d'authentification*********************
		
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);

		if (!authentification.isAuthentifiePro())
			return;
		//*********************************************************
		request.getRequestDispatcher("MesActivites").forward(request, response);
		
		
		// TODO Auto-generated method stub
	
	}

}
