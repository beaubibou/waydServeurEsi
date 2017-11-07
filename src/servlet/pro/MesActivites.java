package servlet.pro;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import website.dao.ActiviteDAO;
import website.html.AlertDialog;
import website.html.JumbotronJsp;
import website.metier.ActiviteBean;
import website.metier.AuthentificationSite;
import website.metier.FiltreRecherche;
import website.metier.ProfilBean;

/**
 * Servlet implementation class MesActivites
 */
public class MesActivites extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MesActivites() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// ********* Regle d'authentification*********************
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		if (!authentification.isAuthentifiePro())
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
		request.getRequestDispatcher("/pro/mesActivite.jsp")
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

		FiltreRecherche filtre =authentification.getFiltre();

		
		if (request.getParameter("etatActivite")!=null){
		int etatActivite = Integer.parseInt(request
				.getParameter("etatActivite"));
		filtre.setTypeEtatActivite(etatActivite);

		}
		
		ArrayList<ActiviteBean> listMesActivite = ActiviteDAO.getMesActivite(
				authentification.getProfil().getId(), filtre.getTypeEtatActivite());

		if (listMesActivite.size() == 0) {

			JumbotronJsp jumbotron=new JumbotronJsp("sosu titre", "titre", "");
				request.setAttribute("jumbotron", jumbotron);
		

		} 
		
			request.setAttribute("listMesActivite", listMesActivite);
			request.getRequestDispatcher("/pro/mesActivite.jsp")
					.forward(request, response);

		

	}
}
