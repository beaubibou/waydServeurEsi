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

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import texthtml.pro.CommunText;
import threadpool.PoolThreadGCM;
import wayd.ws.WBservices;
import wayde.bean.MessageServeur;
import website.dao.ActiviteDAO;
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

		request.getRequestDispatcher("pro/creationactivite.jsp").forward(
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
			LOG.error( ExceptionUtils.getStackTrace(e));
			authentification.setAlertMessageDialog( new MessageAlertDialog("Message Information","Date non conforme",null,AlertJsp.warning));
			response.sendRedirect("MesActivites");
				
			return;
		}

		MessageServeur messageServeur=testParametreReque(titre,adresse,description,latitude,longitude,typeactivite,dateDebut,dateFin);
		
		if (!messageServeur.isReponse())
		{
			authentification.setAlertMessageDialog( new MessageAlertDialog("Message Information",messageServeur.getMessage(),null,AlertJsp.warning));
			response.sendRedirect("MesActivites");
			 return;
			
		}
		
		website.dao.ActiviteDAO activiteDAO = new website.dao.ActiviteDAO();
		
		
		if (ActiviteDAO.getNbrActiviteProposeEnCours(authentification.getId())>=CommunText.NBR_ACTIVITE_MAX){
		
			authentification.setAlertMessageDialog(
					new MessageAlertDialog("Message Information","Vous avez dépassé votre quota",null,AlertJsp.warning));
			response.sendRedirect("MesActivites");
			return;
			
			
		}
		
		int idActivite = activiteDAO.addActivitePro(authentification.getId(),
				titre, description, dateDebut, dateFin, adresse, latitude,
				longitude, typeactivite, ProfilBean.PRO, 2);
	
		if (idActivite != 0) {
	
			PoolThreadGCM.poolThread.execute(new AddActiviteGcm(idActivite));
			
			authentification.setAlertMessageDialog( new MessageAlertDialog("Message Information","L'activité est ajoutée",null,AlertJsp.Sucess));
			response.sendRedirect("MesActivites");
			
			
			return;
		}

		authentification.setAlertMessageDialog( new MessageAlertDialog("Message Information","Une erreur est survenue",null,AlertJsp.warning));
		response.sendRedirect("MesActivites");
	
		return;

	}

	private MessageServeur testParametreReque(String titre, String adresse,
			String description, double latitude, double longitude,
			int typeactivite, Date dateDebut, Date dateFin) {
		// TODO Auto-generated method stub
	

		
		return new MessageServeur(true,"ok");
		
	}

}
