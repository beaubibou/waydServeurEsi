package servlet.waydeur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import website.dao.ActiviteDAO;
import website.metier.ActiviteAjax;
import website.metier.ActiviteBean;
import website.metier.AuthentificationSite;
import website.metier.ProfilBean;

/**
 * Servlet implementation class MapWaydeur
 */
public class MapWaydeur extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MapWaydeur() {
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

		if (!authentification.isAuthentifieWaydeur())
			return;

					
		request.getRequestDispatcher("/waydeur/mapfullscreen.jsp").forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
