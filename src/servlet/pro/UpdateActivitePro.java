package servlet.pro;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.SendResult;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.sun.org.apache.xerces.internal.dom.DeepNodeListImpl;

import texthtml.pro.Erreur_HTML;
import website.dao.ActiviteDAO;
import website.enumeration.AlertJsp;
import website.html.AlertDialog;
import website.html.AlertInfoJsp;
import website.html.MessageAlertDialog;
import website.metier.AuthentificationSite;
import website.metier.Outils;

/**
 * Servlet implementation class UpdateActivite
 */
public class UpdateActivitePro extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(UpdateActivitePro.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateActivitePro() {
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

		response.sendRedirect("MesActivites");
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

		int typeactivite = Integer.parseInt(request
				.getParameter("typeactivite"));
		int idactivite = Integer.parseInt(request.getParameter("idActivite"));

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
			return;
		}

		if (ActiviteDAO
				.updateActivitePro(titre, description, dateDebut, dateFin,
						adresse, latitude, longitude, typeactivite, idactivite)) {

			request.setAttribute(AlertDialog.ALERT_DIALOG,
					new MessageAlertDialog("Message Information",
							Erreur_HTML.ACTIVITE_MODIFIEE, null));
			request.getRequestDispatcher("MesActivites").forward(request,
					response);

			return;
		}
		;
	}

}
