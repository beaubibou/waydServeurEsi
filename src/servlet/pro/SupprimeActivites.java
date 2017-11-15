package servlet.pro;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wayde.bean.MessageServeur;
import website.coordination.Coordination;
import website.dao.ActiviteDAO;
import website.enumeration.AlertJsp;
import website.html.AlertDialog;
import website.html.MessageAlertDialog;
import website.metier.ActiviteBean;
import website.metier.AuthentificationSite;

/**
 * Servlet implementation class SupprimeActivites
 */
public class SupprimeActivites extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SupprimeActivites() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		if (!authentification.isAuthentifiePro())
			return;


		int nbrLigneSupprime =0;
		String listActivite=request.getParameter("listActivite");
		
		for (String activiteStr:listActivite.split(";"))
		
		{
	
		
		int idActivite = Integer.parseInt(activiteStr);
		ActiviteBean activite = new Coordination().getActivite(idActivite);
			
		if (activite.isSupprimable(authentification.getId()))
		{
			MessageServeur retour = new ActiviteDAO().effaceActivite(
					activite.getIdorganisateur(), activite.getId());
		
			if (retour.isReponse()) 
			nbrLigneSupprime++;
			
			if (!retour.isReponse()){
				authentification.setAlertMessageDialog(new MessageAlertDialog(
						"Message Information", "Une erreur est survenue", null,AlertJsp.warning));
				response.sendRedirect("MesActivites");
				return;
			}
			
		}
		
				
		}
		
		authentification.setAlertMessageDialog(new MessageAlertDialog(
				"Message Information", "Vous avez supprimé "+ nbrLigneSupprime+ " activités", null,AlertJsp.Sucess));
		response.sendRedirect("MesActivites");
	
		return;

	
	
	}
}
