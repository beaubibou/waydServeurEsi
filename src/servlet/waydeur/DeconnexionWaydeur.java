package servlet.waydeur;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import website.metier.ProfilBean;

/**
 * Servlet implementation class DeconnexinWaydeur
 */
public class DeconnexionWaydeur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(DeconnexionWaydeur.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeconnexionWaydeur() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (true)return;
		HttpSession session = request.getSession();
		ProfilBean profil = (ProfilBean) session.getAttribute("profil");

		if (profil == null) {
			session.invalidate();
			response.sendRedirect("auth/login.jsp");
			return;
		}

		if (profil.getTypeuser() != ProfilBean.WAYDEUR
				|| profil.isPremiereconnexion()) {
			session.invalidate();
			response.sendRedirect("auth/login.jsp");
			return;
		}
		
		session.invalidate();
		response.sendRedirect("auth/login.jsp");
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				if (true)return;
	}

}
