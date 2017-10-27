package servlet.pro;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import wayde.bean.MessageServeur;
import wayde.bean.Personne;
import website.coordination.Coordination;
import website.dao.ActiviteDAO;
import website.dao.MessageBean;
import website.enumeration.AlertJsp;
import website.html.AlertInfoJsp;
import website.metier.ActiviteBean;
import website.metier.AuthentificationSite;
import website.metier.ProfilBean;

/**
 * Servlet implementation class SupprimeActivite
 */
public class SupprimeActivite extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SupprimeActivite() {
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
		if (!authentification.isAuthentifiePro())
			return;

		int idActivite = Integer.parseInt(request.getParameter("idactivite"));

		ActiviteBean activite = new Coordination().getActivite(idActivite);

		if (activite.getIdorganisateur() == authentification.getId()) {
		
			MessageServeur retour = new ActiviteDAO().effaceActivite(
					activite.getIdorganisateur(), activite.getId());
		
			if (retour.isReponse())
				new AlertInfoJsp("Activite supprimmée", AlertJsp.Sucess,
						"MesActivites").send(request, response);
			else
				new AlertInfoJsp("Une erreur est survenue", AlertJsp.Alert,
						"MesActivites").send(request, response);
			return;

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
