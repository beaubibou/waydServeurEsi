package servlet.commun;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import wayd.ws.WBservices;
import website.coordination.Coordination;
import website.dao.ActiviteDAO;
import website.metier.ActiviteBean;
import website.metier.ProfilBean;

/**
 * Servlet implementation class DetailActiviteSite
 */
public class DetailActiviteSite extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(WBservices.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DetailActiviteSite() {
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

		HttpSession session = request.getSession();
		ProfilBean profil = (ProfilBean) session.getAttribute("profil");

		
		if (profil == null) {
			response.sendRedirect("auth/login.jsp");
			return;
		}

		if (profil.getTypeuser() != ProfilBean.WAYDEUR
				|| profil.isPremiereconnexion()) {
			response.sendRedirect("auth/login.jsp");
			return;
		}
		
		int idActivite = Integer.parseInt(request.getParameter("idactivite"));
		ActiviteBean activite =  new Coordination().getActivite(idActivite);
		request.setAttribute("activite", activite);
		
		LOG.info(activite.getPseudo());
				
		LOG.info("DetailActiviteSite");

		switch (activite.getTypeUser()) {
		case ProfilBean.PRO:
			LOG.info("DetailActivitePro");
			System.out.println("ativite dans deteal"+activite);
		
			request.getRequestDispatcher("/commun/detailActivitePro.jsp").forward(request, response);
			
			break;

		case ProfilBean.WAYDEUR:

			LOG.info("DetailActiviteWaydeur");
			System.out.println("ativite dans deteal"+activite);
			request.getRequestDispatcher("/commun/detailActiviteWaydeur.jsp").forward(request, response);
			
			break;

		default:

			break;
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
