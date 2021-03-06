package servlet.commun;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import texthtml.pro.Erreur_HTML;
import website.coordination.Coordination;
import website.dao.ActiviteDAO;
import website.enumeration.AlertJsp;
import website.html.AlertInfoJsp;
import website.metier.ActiviteBean;
import website.metier.AuthentificationSite;
import website.metier.PhotoActiviteBean;
import website.metier.ProfilBean;

/**
 * Servlet implementation class ModifierActivite
 */
public class ModifierActivite extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ModifierActivite.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifierActivite() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
	
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);

		if (!authentification.isAuthentifie())
			return;

		int idActivite = Integer.parseInt(request.getParameter("idactivite"));

		ActiviteBean activiteBean = new Coordination().getActivite(idActivite);
		ArrayList<PhotoActiviteBean>listPhotoActivite=ActiviteDAO.getListPhotoActivite(idActivite);
			
	
		if (activiteBean==null){
			
		switch(authentification.getProfil().getTypeuser()){
		
		case ProfilBean.PRO:
			
				new AlertInfoJsp(Erreur_HTML.ACTIVITE_EXISTE_PLUS, AlertJsp.Alert,
						"MesActivites").send(request, response);
				
				return;
			
		case ProfilBean.WAYDEUR:
	
				break;
		
		}
		}
		
		
		switch (activiteBean.getTypeUser()) {

		case ProfilBean.PRO:

			request.setAttribute("activite", activiteBean);
			request.setAttribute("listPhoto", listPhotoActivite);
			
			request.getRequestDispatcher("/pro/modifierActivitePro.jsp")
					.forward(request, response);

			break;

		case ProfilBean.WAYDEUR:

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
