package servlet.waydeur;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import wayde.bean.CxoPool;
import website.metier.AuthentificationSite;

/**
 * Servlet implementation class AproposWaydeur
 */
public class AproposWaydeur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(AproposWaydeur.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AproposWaydeur() {
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
		response.sendRedirect("waydeur/apropos.jsp");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
