package servlet.pro;

import gcmnotification.AcquitAllNotificationGcm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import website.dao.ActiviteDAO;
import website.metier.ActiviteAjax;
import website.metier.AuthentificationSite;

/**
 * Servlet implementation class MapPro
 */
public class MapPro extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(MapPro.class);
	  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MapPro() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		
		
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);

		if (!authentification.isAuthentifiePro())
			return;

					
		request.getRequestDispatcher("/pro/mapfullscreen.jsp").forward(request, response);
	
	}

}
