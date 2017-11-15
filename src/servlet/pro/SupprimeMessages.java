package servlet.pro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wayde.bean.MessageServeur;
import website.dao.MessageDAO;
import website.enumeration.AlertJsp;
import website.html.MessageAlertDialog;
import website.metier.AuthentificationSite;

/**
 * Servlet implementation class SupprimeMessages
 */
public class SupprimeMessages extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SupprimeMessages() {
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

		List<Integer> param = new ArrayList<>();
		int nbrLigneSupprime = 0;
		String listMessage = request.getParameter("listMessage");
		int nbrLigneEfface = 0;
		for (String activiteStr : listMessage.split(";"))

		{

			int idMessage = Integer.parseInt(activiteStr);

			MessageServeur messageServeur = MessageDAO.effaceMessage(idMessage);

			if (messageServeur.isReponse())
				nbrLigneEfface++;

		}

		if (nbrLigneEfface > 0) {

			authentification.setAlertMessageDialog(new MessageAlertDialog(
					"Message Information", "Vous avez supprimé "+nbrLigneEfface +" messages", null,
					AlertJsp.Sucess));
			response.sendRedirect("MesMessages");
			return;

		}
		else{
			authentification.setAlertMessageDialog(new MessageAlertDialog(
					"Message Information", "Aucun message supprimé", null,
					AlertJsp.Info));
			response.sendRedirect("MesMessages");
			return;

			
		}

	}

}
