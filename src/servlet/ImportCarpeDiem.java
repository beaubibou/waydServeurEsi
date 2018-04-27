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
import carpediem.ImportFaceBookUser;
import carpediem.ImportMapadoEvent;
import carpediem.ImportMapodoEvents;
import carpediem.ImportOpenAgendaAgenda;
import carpediem.ImportOpenAgendaEvents;
import website.metier.AuthentificationSite;

/**
 * Servlet implementation class ImportCarpeDiem
 */
public class ImportCarpeDiem extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ImportCarpeDiem.class);
	public final static String ACTION_CHARGE_CARPEDIEM = "ACTION_CHARGE_CARPEDIEM";
	public final static String ACTION_CHARGE_EVENT_FACEBOOK = "ACTION_CHARGE_EVENT_FACEBOOK";
	private static final int THREAD_SIMULTANEE = 5;

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

		// String ville_ = "montpelleier";
		//
		// String startDate = "2018-03-22T19:45:00+01:00";
		// String endDate = "2018-03-23T22:30:00+01:00";
		// String image =
		// "http://lyon.carpediem.cd/data/afisha/bp/0b/b8/0bb8796831.jpg";
		// String description =
		// "Revivez la trilogie du Seigneur des Anneaux en version longue, et en Dolby CinÃ©ma! Vendredi 09 Mars Ã  19h45: La";
		// String url =
		// "http://lyon.carpediem.cd/events6020360-trilogie-du-seigneur-des-anneaux-at-path-vaise";
		// String name = "Trilogie du Seigneur des Anneaux";
		// String address = "43, rue des Docks, 69009 Lyon";
		// String nomLieu = "PathÃ© Vaise";
		//
		// double lat = 34.3;
		// double lng = 4.3;
		// int id = 503030;
		//
		// ActiviteCarpeDiem activite = new ActiviteCarpeDiem(startDate,
		// endDate,
		// image, description, url, name, address, nomLieu, ville_, lat,
		// lng, id);
		//
		// website.dao.ActiviteDAO.ajouteActiviteCarpeDiem(activite);
		//
		// ArrayList<String> villes = new ArrayList<>();
		//
		// villes.add("paris");

		String jeton = request.getParameter("token");

		String action = request.getParameter("action");

		switch (action) {

		case ACTION_CHARGE_CARPEDIEM:
			LOG.info("CHARGE CARPEDIEM ");
			executer(jeton);

			break;

		case ACTION_CHARGE_EVENT_FACEBOOK:

			LOG.info("CHARGE EVNETS FB");

			// String listevents = request.getParameter("listevents");
			ExecutorService executor = Executors
					.newFixedThreadPool(1);
			// jeton="EAACEdEose0cBAC5nW0ZBMwcnm2EoZBrVahw76clbe72jIWb0t4C5I4g7ZChETfhf1Om49DwTx6jfGdcepS4dyV9pvhSjRtzn1YaPAdZCQW5jxOfHIysBZClJeMFXiOcsrKllhdFgT94mSKZAnxC7X2mmwEuQENN98a31iD2x2dgkw0701D0YZCxMpRZAc0YcDdsZD";
			// String[] listEvent = listevents.split(" ");
			// for (int courant = 0; courant < listEvent.length; courant++) {
			//
			// LOG.info("Charge THREAD:"+courant+"/"+listevents.length());
			//
			// executor.execute(new ImportFaceBook(listEvent[courant],
			// jeton,listEvent.length,courant));
			// }

			// executor.execute(new ImportFaceBookUser( jeton,"paris"));
			// executor.execute(new
			// ImportOpenAgendaEvent("261c569041f74c8180088cda3e47b375",
			// "19133512"));
			// new ImportOpenAgendaAgenda("261c569041f74c8180088cda3e47b375",
			// "50522407").start();

			// new ImportOpenAgendaEvents("261c569041f74c8180088cda3e47b375",
			// "01/10/2018-01/01/2019").start();
			while (true) {

				
				executor.execute(new ImportMapodoEvents("paris", "today"));
				executor.execute( new ImportMapodoEvents("paris", "tomorrow"));
				executor.execute(new ImportMapodoEvents("paris", "soon"));
//				//
//				//
				 for (int f = 0; f < 200; f++)
				 executor.execute(new ImportOpenAgendaEvents(	 "261c569041f74c8180088cda3e47b375", "19133512",				 1000 * f, (f + 1) * 1000));

				while (!executor.isTerminated()) {

				}
			}
			//

			// new
			// ImportMapadoEvent("ODJjZTY5ODQzZTExNmYwNzVlZDVjYTY4MjM1MTIwOWNhNTkyNGU0ZmQ2NmQ3YzA5NTU5MzQ3NzI2Njg4YzcwMA",
			// "33a3044f-347b-41d1-b099-6af03c214bfe").start();
			// break;

		}
		request.getRequestDispatcher("Acceuil").forward(request, response);

		// executer(jeton);
		//
		// for (int f=0;f<10;f++){
		//
		// executor.execute(new Import(f));
		//
		// }
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

			new Thread(new ImportRunnable(1, ville, jeton)).start();

		}

	}

}
