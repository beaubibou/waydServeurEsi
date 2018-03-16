package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.JodaTimePermission;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import website.metier.ActiviteCarpeDiem;
import carpediem.ImportCarpe;

/**
 * Servlet implementation class ImportCarpeDiem
 */
public class ImportCarpeDiem extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// String date = request.getParameter("date");
		// String ville = request.getParameter("ville");
		//
		// String startDate="2018-03-09T19:45:00+01:00";
		// String endDate="2018-03-22T22:30:00+01:00";
		// String
		// image="http://lyon.carpediem.cd/data/afisha/bp/0b/b8/0bb8796831.jpg";
		// String
		// description="Revivez la trilogie du Seigneur des Anneaux en version longue, et en Dolby CinÃ©ma! Vendredi 09 Mars Ã  19h45: La";
		// String
		// url="http://lyon.carpediem.cd/events6020360-trilogie-du-seigneur-des-anneaux-at-path-vaise";
		// String name="Trilogie du Seigneur des Anneaux";
		// String address="43, rue des Docks, 69009 Lyon";
		// String nomLieu="PathÃ© Vaise";
		//
		// double lat=34.3 ;
		// double lng=4.3 ;
		// int id=503030;
		// ActiviteCarpeDiem activite=new ActiviteCarpeDiem(startDate, endDate,
		// image, description, url, name, address, nomLieu, ville, lat, lng,
		// id);
		// website.dao.ActiviteDAO.ajouteActiviteCarpeDiem(activite);

		ArrayList<String> villes = new ArrayList<String>();

		villes.add("lyon");
		villes.add("montpellier");

		for (String ville : villes) {
			DateTime date = new DateTime();
			for (int nbrJours = 0; nbrJours < 10; nbrJours++) {

				DateTime date1 = date.plusDays(nbrJours);
				String dateEventStr=getFormatDate(date1) ;
				new ImportCarpe(dateEventStr,ville);
			

			}
		}

	}

	private String getFormatDate(DateTime dt) {
		
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd.MM.yyyy");
		return fmt.print(dt);

	}
}
