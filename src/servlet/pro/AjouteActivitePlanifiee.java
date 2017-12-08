package servlet.pro;

import gcmnotification.AcquitAllNotificationGcm;
import gcmnotification.AddActiviteGcm;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import texthtml.pro.CommunText;
import threadpool.PoolThreadGCM;
import wayde.bean.MessageServeur;
import website.dao.ActiviteDAO;
import website.enumeration.AlertJsp;
import website.html.MessageAlertDialog;
import website.metier.AuthentificationSite;
import website.metier.ProfilBean;

/**
 * Servlet implementation class AjouteActivitePlanifiee
 */
public class AjouteActivitePlanifiee extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger
			.getLogger(AjouteActivitePlanifiee.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AjouteActivitePlanifiee() {
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

		request.getRequestDispatcher("pro/creation_activite_planifiee.jsp")
				.forward(request, response);

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

		HashMap<Integer, String> joursVoulus = new HashMap<Integer, String>();

		String titre = request.getParameter("titre");
		String adresse = request.getParameter("adresse");
		String description = request.getParameter("description");

		double latitude = Double.parseDouble(request.getParameter("latitude"));
		double longitude = Double
				.parseDouble(request.getParameter("longitude"));
		int typeactivite = Integer.parseInt(request
				.getParameter("typeactivite"));

		int duree = Integer.parseInt(request.getParameter("duree"));

		String datedebut = request.getParameter("debut");
		String datefin = request.getParameter("fin");

		Date dateDebut = null;
		Date dateFin = null;

		if (request.getParameter("lundi") != null) {

			joursVoulus.put(2, "lundi");

		}

		if (request.getParameter("mardi") != null)
			joursVoulus.put(3, "mardi");

		if (request.getParameter("mercredi") != null)
			joursVoulus.put(4, "mercredi");

		if (request.getParameter("jeudi") != null)
			joursVoulus.put(5, "jeudi");

		if (request.getParameter("vendredi") != null)
			joursVoulus.put(6, "vendredi");

		if (request.getParameter("samedi") != null)
			joursVoulus.put(7, "samedi");

		if (request.getParameter("dimanche") != null)
			joursVoulus.put(1, "dimanche");

		String heuredebut = request.getParameter("heuredebut");
		// String heurefin = request.getParameter("heurefin");
		// String duree = request.getParameter("duree");
		try {
			dateDebut = getDateFromString(datedebut, heuredebut);
			dateFin = getDateFromString(datefin, heuredebut);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			authentification.setAlertMessageDialog(new MessageAlertDialog(
					"Parse Date non conformes", e.getMessage(), null,
					AlertJsp.warning));
			response.sendRedirect("MesActivites");
			return;

		}

		MessageServeur messageServeur = testParametreReque(titre, adresse,
				description, latitude, longitude, typeactivite, dateDebut,
				dateFin, duree);

		if (!messageServeur.isReponse()) {
			authentification.setAlertMessageDialog(new MessageAlertDialog(
					"Message erreur", messageServeur.getMessage(), null,
					AlertJsp.warning));
			response.sendRedirect("MesActivites");
			return;

		}
		
		if (ActiviteDAO.getNbrActiviteProposeEnCours(authentification.getId())>=CommunText.NBR_ACTIVITE_MAX) {

			authentification.setAlertMessageDialog(new MessageAlertDialog(
					"Message Information",
					"Vous avez dépassé le nombre d'activités maximum",
					null, AlertJsp.warning));
			response.sendRedirect("MesActivites");
			return;
		}
		

		int nbrActiviteCree = ajouteActivites(authentification.getId(), titre,
				description, adresse, latitude, longitude, typeactivite,
				dateDebut, dateFin, joursVoulus, duree);

		if (nbrActiviteCree == 0) {

			authentification.setAlertMessageDialog(new MessageAlertDialog(
					"Message Information",
					"Vous n'avez crée aucune activité. Vérifier vos dates",
					null, AlertJsp.warning));
			response.sendRedirect("MesActivites");
			return;
		}

		if (nbrActiviteCree > 0) {
			if (nbrActiviteCree < joursVoulus.size()) {
				authentification.setAlertMessageDialog(new MessageAlertDialog(
						"Message Information",
						"Vous avez crée " + nbrActiviteCree+" sur les "+joursVoulus.size() + " demandés. Vous ne pouver pas dépasser "
								+ CommunText.NBR_ACTIVITE_MAX+ " activités." , null,
						AlertJsp.Sucess));
				response.sendRedirect("MesActivites");
				return;
			}
			else{
				authentification.setAlertMessageDialog(new MessageAlertDialog(
						"Message Information",
						"Vous avez crée " + nbrActiviteCree+" activités" , null,
						AlertJsp.Sucess));
				response.sendRedirect("MesActivites");
				return;
				
				
			}
		}
	}

	private int ajouteActivites(int idPersonne, String titre,
			String description, String adresse, double latitude,
			double longitude, int typeactivite, Date dateDebut, Date dateFin,
			HashMap<Integer, String> joursVoulus, int duree) {
		// TODO Auto-generated method stub
		int nbrAjout = 0;
		long nbrJours = (dateFin.getTime() - dateDebut.getTime()) / 1000 / 3600
				/ 24 + 1;

		for (int f = 0; f <= nbrJours; f++) {

			Calendar datetmp = Calendar.getInstance();
			datetmp.setTime(dateDebut);
			datetmp.add(Calendar.DAY_OF_MONTH, f);
			if (ActiviteDAO.getNbrActiviteProposeEnCours(idPersonne) < CommunText.NBR_ACTIVITE_MAX)
				if (joursVoulus.containsKey(datetmp.get(Calendar.DAY_OF_WEEK))) {
					if (ajouteActiviteDAO(idPersonne, titre, description,
							adresse, latitude, longitude, typeactivite,
							datetmp, duree))
						nbrAjout++;
				}

		}

		return nbrAjout;

	}

	private boolean ajouteActiviteDAO(int idPersonne, String titre,
			String description, String adresse, double latitude,
			double longitude, int typeactivite, Calendar dateDebut, int duree) {
		// TODO Auto-generated method stub

		Calendar calFin = Calendar.getInstance();
		calFin.setTime(dateDebut.getTime());
		calFin.add(Calendar.MINUTE, duree);

		website.dao.ActiviteDAO activiteDAO = new website.dao.ActiviteDAO();
		//
		int idActivite = activiteDAO.addActivitePro(idPersonne, titre,
				description, dateDebut.getTime(), calFin.getTime(), adresse,
				latitude, longitude, typeactivite, ProfilBean.PRO, 2);

		if (idActivite != 0) {
			PoolThreadGCM.poolThread.execute(new AddActiviteGcm(idActivite));
			return true;
		}
		return false;
	}

	public Date getDateFromString(String datestr, String heurestr)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date d = sdf.parse(datestr);
		Calendar caldate = Calendar.getInstance();
		caldate.setTime(d);
		SimpleDateFormat heureformat = new SimpleDateFormat("HH:mm");
		Date dateheure = heureformat.parse(heurestr);
		Calendar calHeure = Calendar.getInstance();
		calHeure.setTime(dateheure);
		caldate.set(Calendar.HOUR_OF_DAY, calHeure.get(Calendar.HOUR_OF_DAY));
		caldate.set(Calendar.MINUTE, calHeure.get(Calendar.MINUTE));
		return caldate.getTime();
	}

	private MessageServeur testParametreReque(String titre, String adresse,
			String description, double latitude, double longitude,
			int typeactivite, Date dateDebut, Date dateFin, int duree) {
		// TODO Auto-generated method stub

		if (duree > 8 * 60)

			return new MessageServeur(false, "Durée trop longue");

		// titre = titre.trim();
		// adresse = adresse.trim();
		// description = description.trim();

		return new MessageServeur(true, "paas ocn");

	}
}
