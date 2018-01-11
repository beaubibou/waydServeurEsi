package servlet.waydeur;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import website.dao.ActiviteDAO;
import website.metier.ActiviteBean;
import website.metier.AuthentificationSite;
import website.metier.FiltreRecherche;

/**
 * Servlet implementation class RechercheWaydeur
 */
public class RechercheWaydeur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(RechercheWaydeur.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RechercheWaydeur() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if (true)return;
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		if (!authentification.isAuthentifieWaydeur())
			return;

		FiltreRecherche filtre=authentification.getFiltre();
			
		ArrayList<ActiviteBean> listActivite = new ActiviteDAO()
				.getListActivites(filtre.getLatitude(), filtre.getLongitude(), filtre.getRayon()*1000, filtre.getTypeActivite(),
						filtre.getMotCle(), filtre.getTyperUser(),  filtre.getQuand());
		
		
		request.setAttribute("listActivite", listActivite);
		request.getRequestDispatcher("waydeur/rechecheWaydeur.jsp").forward(
				request, response);
	

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	
		// **********************************ATUHENTIFICIATION
		if (true)return;
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		if (!authentification.isAuthentifieWaydeur())
			return;
		
		FiltreRecherche filtre =authentification.getFiltre();
		// ************************RECUERATION DES DONNES***********************
		
		int typeactivite = Integer.parseInt(request
				.getParameter("typeactivite"));
		int typeuser = Integer.parseInt(request.getParameter("typeuser"));
		int commence = Integer.parseInt(request.getParameter("commence"));
		int rayon = Integer.parseInt(request.getParameter("rayon"));
		String adresse = request.getParameter("adresse");
		double latitude = Double.parseDouble(request.getParameter("latitude"));
		double longitude = Double
				.parseDouble(request.getParameter("longitude"));

		String motcle = (request.getParameter("motcle"));

		// ********** Mise a jour du filtre**********
		filtre.setQuand(commence);
		filtre.setTyperUser(typeuser);
		filtre.setTypeActivite(typeactivite);
		filtre.setRayon(rayon);
		filtre.setLatitude(latitude);
		filtre.setLongitude(longitude);
		filtre.setAdresse(adresse);
		filtre.setMotCle(motcle);
		filtre.setAdresse(adresse);// ************************************


		rayon = rayon * 1000;
		
		ArrayList<ActiviteBean> listActivite = new ActiviteDAO()
				.getListActivites(latitude, longitude, rayon, typeactivite,
						motcle, typeuser, commence);

		request.setAttribute("listActivite", listActivite);
		request.getRequestDispatcher("waydeur/rechecheWaydeur.jsp").forward(
				request, response);

	}

}
