package servlet.pro;

import gcmnotification.AddActiviteGcm;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import threadpool.PoolThreadGCM;
import wayd.ws.WBservices;
import website.dao.CacheValueDAO;
import website.enumeration.AlertJsp;
import website.html.AlertDialog;
import website.html.AlertInfoJsp;
import website.html.MessageAlertDialog;
import website.metier.AuthentificationSite;
import website.metier.DureeBean;
import website.metier.Outils;
import website.metier.ProfilBean;
import website.metier.QuantiteWaydeurBean;
import website.metier.TypeAccess;
import website.metier.TypeActiviteBean;

/**
 * Servlet implementation class AjouteActivite
 */
public class AjouteActivitePro extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(AjouteActivitePro.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AjouteActivitePro() {
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

		request.getRequestDispatcher("pro/form_creationactivite.jsp").forward(
				request, response);

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
		String titre = request.getParameter("titre");
		String adresse = request.getParameter("adresse");
		String description = request.getParameter("description");
		double latitude = Double.parseDouble(request.getParameter("latitude"));
		double longitude = Double
				.parseDouble(request.getParameter("longitude"));
		// int typeaccess =
		// Integer.parseInt(request.getParameter("typeaccess"));
		int typeactivite = Integer.parseInt(request
				.getParameter("typeactivite"));

		String datedebut = request.getParameter("debut");
		String datefin = request.getParameter("fin");

		Date dateDebut = null;
		Date dateFin = null;

		try {
			dateDebut = Outils.getDateFromString(datedebut);
			dateFin = Outils.getDateFromString(datefin);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		website.dao.ActiviteDAO activiteDAO = new website.dao.ActiviteDAO();
		int idActivite = activiteDAO.addActivitePro(authentification.getId(),
				titre, description, dateDebut, dateFin, adresse, latitude,
				longitude, typeactivite, ProfilBean.PRO, 2);
		LOG.debug("idactivite ajot�e from pro" + idActivite);

		if (idActivite != 0) {
			PoolThreadGCM.poolThread.execute(new AddActiviteGcm(idActivite));
			
			System.out.println("******************ajoteu acitivte prokkkkkkkkkkkkkkkkkkk");
			
			authentification.setAlertMessageDialog( new MessageAlertDialog("Message Information","Activit� ajout�",null));
			response.sendRedirect("MesActivites");
			//new AlertInfoJsp("Activite ajout�e", AlertJsp.Sucess, "AcceuilPro")
			//		.send(request, response);
			
			//request.getRequestDispatcher("MesActivites").forward(request, response);
			
			return;
		}

		new AlertInfoJsp("Une erreur est survenue", AlertJsp.Alert,
				"AcceuilPro").send(request, response);

		return;

	}

}
