package servlet.commun;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import wayd.ws.WBservices;
import wayde.bean.MessageServeur;
import website.coordination.Coordination;
import website.dao.ActiviteDAO;
import website.metier.ActiviteBean;
import website.metier.ProfilBean;

/**
 * Servlet implementation class SignalerActivite
 */
public class SignalerActivite extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(WBservices.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignalerActivite() {
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

		int idActivite = Integer.parseInt(request.getParameter("idActivite"));
		ActiviteBean activite = new Coordination().getActivite(idActivite);
		request.setAttribute("activite", activite);

		if (request.getParameter("idmotif") == null) {
			// Si pas de motif issu d'un detail activité redirection vers le
			// choix du motif
			request.getRequestDispatcher("/commun/SignalerActivite.jsp")
					.forward(request, response);

		} else {
			// Mise à jour du signalement
			int idMotif = Integer.parseInt(request.getParameter("idmotif"));

			MessageServeur message = new ActiviteDAO().signalerActivite(
					profil.getId(), activite.getId(), idMotif, "",
					activite.getTitre(), activite.getLibelle());

			if (message.isReponse()) {

				response.sendRedirect("waydeur/mesActiviteWaydeur.jsp");
				return;
			} else {

				LOG.info(message.getMessage());
				request.setAttribute("message",
						"Tu as deja signalé cette activité");
				request.getRequestDispatcher("waydeur/MessageInfo.jsp").forward(request, response);
				
				return;
			}
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
