package servlet.waydeur;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import website.dao.ActiviteDAO;
import website.html.JumbotronJsp;
import website.metier.ActiviteBean;
import website.metier.AuthentificationSite;
import website.metier.FiltreRecherche;

/**
 * Servlet implementation class MesActivitesWaydeur
 */
public class MesActivitesWaydeur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(MesActivitesWaydeur.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MesActivitesWaydeur() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		// ********* Regle d'authentification*********************
		if (true)return;
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		if (!authentification.isAuthentifieWaydeur())
			return;

		FiltreRecherche filtre =authentification.getFiltre();

		ArrayList<ActiviteBean> listMesActivite = ActiviteDAO.getMesActivite(
				authentification.getProfil().getId(), filtre.getTypeEtatActivite());

	//	listMesActivite = new ArrayList<ActiviteBean>();
		if (listMesActivite.size() == 0) {

			JumbotronJsp jumbotron=new JumbotronJsp("sosu titre", "titre", "");
				request.setAttribute("jumbotron", jumbotron);
		

		} 
		
		request.setAttribute("listMesActivite", listMesActivite);
		request.getRequestDispatcher("/waydeur/mesActiviteWaydeur.jsp")
				.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		

		if (true)return;
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		if (!authentification.isAuthentifieWaydeur())
			return;

		FiltreRecherche filtre =authentification.getFiltre();

		
		int etatActivite = Integer.parseInt(request
				.getParameter("etatActivite"));

		filtre.setTypeEtatActivite(etatActivite);

		ArrayList<ActiviteBean> listMesActivite = ActiviteDAO.getMesActivite(
				authentification.getProfil().getId(), filtre.getTypeEtatActivite());

		if (listMesActivite.size() == 0) {

			JumbotronJsp jumbotron=new JumbotronJsp("sosu titre", "titre", "");
				request.setAttribute("jumbotron", jumbotron);
		

		} 
			request.setAttribute("listMesActivite", listMesActivite);
			request.getRequestDispatcher("/waydeur/mesActiviteWaydeur.jsp")
					.forward(request, response);

		

	}

}
