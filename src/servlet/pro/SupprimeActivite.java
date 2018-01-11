package servlet.pro;

import gcmnotification.EffaceActiviteGcm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import texthtml.pro.Erreur_HTML;
import threadpool.PoolThreadGCM;
import wayde.bean.MessageServeur;
import website.coordination.Coordination;
import website.dao.ActiviteDAO;
import website.enumeration.AlertJsp;
import website.html.AlertDialog;
import website.html.MessageAlertDialog;
import website.metier.ActiviteBean;
import website.metier.AuthentificationSite;

/**
 * Servlet implementation class SupprimeActivite
 */
public class SupprimeActivite extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(SupprimeActivite.class);
	
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

			EffaceActiviteGcm effaceActiviteGcm=new EffaceActiviteGcm(idActivite);
			
			MessageServeur retour = new ActiviteDAO().effaceActivite(
					activite.getIdorganisateur(), activite.getId());

			if (retour.isReponse()) {

				PoolThreadGCM.poolThread.execute(effaceActiviteGcm);
				authentification.setAlertMessageDialog(new MessageAlertDialog(
						"Message Information",Erreur_HTML.ACTIVITE_SUPPRIMEE, null,AlertJsp.Sucess));
				response.sendRedirect("MesActivites");
				return;
			}

			else {

				request.setAttribute(AlertDialog.ALERT_DIALOG,
						new MessageAlertDialog("Message Erreur",
								Erreur_HTML.ERREUR_INCONNUE, null,AlertJsp.warning));
				response.sendRedirect("MesActivites");
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
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
	
		
		if (!authentification.isAuthentifiePro())
			return;
		
		response.sendRedirect("MesActivites");
		
	}

}
