package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import carpediem.ImportFaceBook;
import website.metier.AuthentificationSite;

/**
 * Servlet implementation class ImportCarpeDiem
 */
public class ImportCarpeDiem extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ImportCarpeDiem.class);
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImportCarpeDiem() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		if (!authentification.isAuthentifieAdmin())
			return;

//		 String ville_ = "montpelleier";
//		
//		 String startDate = "2018-03-22T19:45:00+01:00";
//		 String endDate = "2018-03-23T22:30:00+01:00";
//		 String image =
//		 "http://lyon.carpediem.cd/data/afisha/bp/0b/b8/0bb8796831.jpg";
//		 String description =
//		 "Revivez la trilogie du Seigneur des Anneaux en version longue, et en Dolby CinÃ©ma! Vendredi 09 Mars Ã  19h45: La";
//		 String url =
//		 "http://lyon.carpediem.cd/events6020360-trilogie-du-seigneur-des-anneaux-at-path-vaise";
//		 String name = "Trilogie du Seigneur des Anneaux";
//		 String address = "43, rue des Docks, 69009 Lyon";
//		 String nomLieu = "PathÃ© Vaise";
//		
//		 double lat = 34.3;
//		 double lng = 4.3;
//		 int id = 503030;
//
//		 ActiviteCarpeDiem activite = new ActiviteCarpeDiem(startDate,
//		 endDate,
//		 image, description, url, name, address, nomLieu, ville_, lat,
//		 lng, id);
//		
//		 website.dao.ActiviteDAO.ajouteActiviteCarpeDiem(activite);
//
//		ArrayList<String> villes = new ArrayList<>();
//
//		villes.add("paris");

		String jeton=request.getParameter("token");
		String listevents=request.getParameter("listevents");
		
		System.out.println(listevents);
		
		String[] listEvent=listevents.split(" ");
		for (int f=0; f<listEvent.length;f++){
			
			LOG.info(listEvent[f]);
			
		}
		//executer(jeton);
	ExecutorService executor = Executors.newFixedThreadPool(1);
	executor.execute(new ImportFaceBook(listEvent[0], jeton));
	executor.shutdown();
	//		
//		for (int f=0;f<10;f++){
//			
//			executor.execute(new Import(f));
//			
//		}
	}

	public static String getFormatDate(DateTime dt) {

		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd.MM.yyyy");
		return fmt.print(dt);

	}

	public static void executer(String jeton) throws IOException {

				
		ArrayList<String> villes = new ArrayList<>();
		villes.add("lyon");
		villes.add("paris");
		villes.add("bordeaux");
		villes.add("marseille");
		villes.add("nantes");
		villes.add("nice");
		villes.add("strasbourg");
		villes.add("toulouse");
		
		for (String ville : villes) {
		
			new Thread(new ImportRunnable(1, ville, jeton)).start();;
		
		}

		
		
		//		ImportCarpe importCarpe = new ImportCarpe();
//
//		for (String ville : villes) {
//
//			DateTime date = new DateTime();
//
//			for (int nbrJours = 0; nbrJours < 1; nbrJours++) {
//
//				DateTime date1 = date.plusDays(nbrJours);
//				String dateEventStr = getFormatDate(date1);
//				importCarpe.importActivitesByPageNew(dateEventStr, ville,jeton);
//
//			}

			//DateTime dateInit = new DateTime();
			//DateTime dateDujourEffacer = new DateTime(dateInit.getYear(),
			//		dateInit.getMonthOfYear(), dateInit.getDayOfMonth(), 0, 0,
			//		0);

		//	ActiviteDAO.effaceTouteCarpeDiem(dateDujourEffacer.toDate());
			// ActiviteDAO.updateDateCarpeDiem(new Date());

		}

	
}
