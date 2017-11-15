package servlet.pro;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import website.dao.ActiviteDAO;
import website.html.JumbotronJsp;
import website.metier.ActiviteBean;
import website.metier.AuthentificationSite;
import website.metier.FiltreRecherche;
import website.metier.MessageBean;
import website.metier.TypeEtatMessage;

/**
 * Servlet implementation class MesMessages
 */
public class MesMessages extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MesMessages() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		if (!authentification.isAuthentifiePro())
			return;

		FiltreRecherche filtre =authentification.getFiltre();

		ArrayList<MessageBean> listMesMessages = ActiviteDAO.getMesMessages(
			authentification.getProfil().getId(), filtre.getTypeMessage());
	
	
	//	listMesActivite = new ArrayList<ActiviteBean>();
		if (listMesMessages.size() == 0) {

			JumbotronJsp jumbotron=new JumbotronJsp("sosu titre", "titre", "");
				request.setAttribute("jumbotron", jumbotron);
		
		} 
		
		request.setAttribute("listMesMessages", listMesMessages);
		request.getRequestDispatcher("/pro/mesMessages.jsp")
				.forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
	
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		if (!authentification.isAuthentifiePro())
			return;

		FiltreRecherche filtre =authentification.getFiltre();

		if (request.getParameter("etatMessage")!=null){
		int typeMessage = Integer.parseInt(request
				.getParameter("etatMessage"));
		filtre.setTypeMessage(typeMessage);

		}
		
		ArrayList<MessageBean> listMesMessages = ActiviteDAO.getMesMessages(
				authentification.getProfil().getId(), filtre.getTypeMessage());

		
	
			request.setAttribute("listMesMessages", listMesMessages);
			request.getRequestDispatcher("/pro/mesMessages.jsp")
					.forward(request, response);

		
	
	}

}
