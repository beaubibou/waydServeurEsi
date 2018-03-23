package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;

import website.dao.ActiviteDAO;
import website.metier.ActiviteCarpeDiem;
import website.metier.AuthentificationSite;
import carpediem.ImportCarpe;

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

		String ville_ = "montpelleier";

		String startDate = "2018-03-22T19:45:00+01:00";
		String endDate = "2018-03-23T22:30:00+01:00";
		String image = "http://lyon.carpediem.cd/data/afisha/bp/0b/b8/0bb8796831.jpg";
		String description = "Revivez la trilogie du Seigneur des Anneaux en version longue, et en Dolby CinÃ©ma! Vendredi 09 Mars Ã  19h45: La";
		String url = "http://lyon.carpediem.cd/events6020360-trilogie-du-seigneur-des-anneaux-at-path-vaise";
		String name = "Trilogie du Seigneur des Anneaux";
		String address = "43, rue des Docks, 69009 Lyon";
		String nomLieu = "PathÃ© Vaise";

		double lat = 34.3;
		double lng = 4.3;
		int id = 503030;

		// ActiviteCarpeDiem activite = new ActiviteCarpeDiem(startDate,
		// endDate,
		// image, description, url, name, address, nomLieu, ville_, lat,
		// lng, id);
		//
		// website.dao.ActiviteDAO.ajouteActiviteCarpeDiem(activite);

		ArrayList<String> villes = new ArrayList<String>();

		villes.add("lyon");
		// villes.add("paris");
		// villes.add("bordeaux");
		// villes.add("lyon");
		// villes.add("marseille");
		// villes.add("nantes");
		// villes.add("nice");
		// villes.add("strasbourg");
		// villes.add("toulouse");

		ImportCarpe importCarpe = new ImportCarpe();
		StringBuilder totalLog = new StringBuilder();

		for (String ville : villes) {

			DateTime date = new DateTime();

			for (int nbrJours = 0; nbrJours < 1; nbrJours++) {

				DateTime date1 = date.plusDays(nbrJours);
				String dateEventStr = getFormatDate(date1);
				try {
					importCarpe.importActivitesByPage(dateEventStr, ville);
				} catch (JSONException e) {

					LOG.error(ExceptionUtils.getStackTrace(e));
				}

			}
			

			DateTime dateInit = new DateTime();
			DateTime dateDujourEffacer = new DateTime(dateInit.getYear(),
					dateInit.getMonthOfYear(), dateInit.getDayOfMonth(), 0, 0,
					0);

			ActiviteDAO.effaceTouteCarpeDiem(dateDujourEffacer.toDate());
			ActiviteDAO.updateDateCarpeDiem(new Date());

		}

	}

	private String getFormatDate(DateTime dt) {

		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd.MM.yyyy");
		return fmt.print(dt);

	}
}
