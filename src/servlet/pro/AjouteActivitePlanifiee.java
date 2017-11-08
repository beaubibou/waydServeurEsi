package servlet.pro;

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

import website.html.AlertDialog;
import website.html.MessageAlertDialog;
import website.metier.AuthentificationSite;
import website.metier.Outils;
import website.metier.ProfilBean;

/**
 * Servlet implementation class AjouteActivitePlanifiee
 */
public class AjouteActivitePlanifiee extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

		request.getRequestDispatcher("pro/form_creation_activite_planifiee.jsp").forward(
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
		
		HashMap<Integer, String> joursVoulus = new HashMap<Integer, String>();

		String titre = request.getParameter("titre");
		String adresse = request.getParameter("adresse");
		String description = request.getParameter("description");

		System.out.println("aderess" + adresse);
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

		if (request.getParameter("lundi") != null)
		{	
		
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
		String heurefin = request.getParameter("heurefin");

		try {
			dateDebut = getDateFromString(datedebut, heuredebut);
			dateFin = getDateFromString(datefin, heurefin);
			
			ajouteActivite(authentification.getId() , titre, description, adresse,
					 latitude, longitude, typeactivite,dateDebut, dateFin, joursVoulus);
			request.setAttribute(AlertDialog.ALERT_DIALOG, new MessageAlertDialog("Message Information","Vos activités ont été crée",null));
			request.getRequestDispatcher("MesActivites").forward(request, response);
			return;

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute(AlertDialog.ALERT_DIALOG, new MessageAlertDialog("Message erreur",e.getMessage(),null));
			request.getRequestDispatcher("MesActivites").forward(request, response);
		
		}

		

	}

	private void ajouteActivite(int idPersonne,String titre,String description,String adresse,
			double latitude,double longitude,int typeactivite,Date dateDebut, Date dateFin,
			HashMap<Integer, String> joursVoulus) {
		// TODO Auto-generated method stub

		long nbrJours = (dateFin.getTime() - dateDebut.getTime()) / 1000 / 3600
				/ 24 + 1;

		System.out.println("nbr jours" + (int) nbrJours);
		System.out.println("nbr de jours " + joursVoulus.size());

		for (int f = 0; f <= nbrJours; f++) {

			Calendar datetmp = Calendar.getInstance();
			datetmp.setTime(dateDebut);
			datetmp.add(Calendar.DAY_OF_MONTH, f);
			if (joursVoulus.containsKey(datetmp.get(Calendar.DAY_OF_WEEK))){
				
				ajouteActiviteDAO( idPersonne, titre, description, adresse,
						 latitude, longitude, typeactivite,datetmp,dateFin);
			}

		}

	}

	private void ajouteActiviteDAO(int idPersonne,String titre,String description,String adresse,
			double latitude,double longitude,int typeactivite,Calendar dateDebut, Date dateFin) {
		// TODO Auto-generated method stub
		
		Calendar calFin=Calendar.getInstance();
		calFin.setTime(dateFin);
		calFin.set(Calendar.DAY_OF_MONTH, dateDebut.get(Calendar.DAY_OF_MONTH));
		calFin.set(Calendar.MONTH, dateDebut.get(Calendar.MONTH));
		calFin.set(Calendar.YEAR, dateDebut.get(Calendar.YEAR));
		
		System.out.println("Debut"+dateDebut);
		System.out.println("Fin"+calFin);
			
		website.dao.ActiviteDAO activiteDAO = new website.dao.ActiviteDAO();
	
		int idActivite = activiteDAO.addActivitePro(idPersonne,
				titre, description, dateDebut.getTime(), calFin.getTime(), adresse, latitude,
				longitude, typeactivite, ProfilBean.PRO, 2);
	
		
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

}
