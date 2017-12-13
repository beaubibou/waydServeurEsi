package servlet;

import gcmnotification.EffaceActiviteGcm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import threadpool.PoolThreadGCM;
import website.coordination.Coordination;
import website.dao.ActiviteDAO;
import website.metier.ActiviteBean;
import website.metier.AuthentificationSite;
import website.metier.TypeUser;

/**
 * Servlet implementation class DetailActivite
 */
public class DetailActivite extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(DetailActivite.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DetailActivite() {
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

		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		if (!authentification.isAuthentifieAdmin())
			return;

		HttpSession session = request.getSession();

		if (session.getAttribute("profil") != null) {

			String action = (String) request.getParameter("action");

			if (action == null) {// chargemetn par d�faut
			
				int idActivite = Integer.parseInt(request
						.getParameter("idactivite"));
				ActiviteBean activite = new Coordination()
						.getActivite(idActivite);
				request.setAttribute("activite", activite);

				// Calcle de la distance par rapport au filtre
			
				double latitudeFiltre=Double.parseDouble(request
						.getParameter("latitudeFiltre"));
				double longitudeFiltre=Double.parseDouble(request
						.getParameter("longitudeFiltre"));
				activite.setPositionRecherche(latitudeFiltre, longitudeFiltre);
			
				//*************************
				switch (activite.getTypeUser()) {

				case TypeUser.PRO:
					request.getRequestDispatcher("admin/detailactivitePro.jsp")
							.forward(request, response);
					break;
				case TypeUser.WAYDEUR:
					request.getRequestDispatcher("admin/detailactiviteWaydeur.jsp")
							.forward(request, response);
					break;
				}

			} else

				switch (action) {

				case "terminerActivite":
					int idActivite = Integer.parseInt(request
							.getParameter("idactivite"));

					if (ActiviteDAO.terminerActivite(idActivite))
						response.sendRedirect("ListActivite");

					break;
				case "effacerActivite":

					idActivite = Integer.parseInt(request
							.getParameter("idactivite"));

					// prepare le message GMC à envoyer
					// Oblige de le faire avant la suppression pour connaitre les 
					// participant et interresses
					
					EffaceActiviteGcm effaceGcm=new EffaceActiviteGcm(idActivite);
				
					if (ActiviteDAO.supprimeActivite(idActivite)){
						
						PoolThreadGCM.poolThread.execute(effaceGcm);
						response.sendRedirect("ListActivite");
						
					}
						

					break;

				}

		} else {

			response.sendRedirect("auth/login.jsp");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		if (!authentification.isAuthentifieAdmin())
			return;

		doGet(request, response);

	}

}
