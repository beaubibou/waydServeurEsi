package servlet.pro;

import gcmnotification.AcquitAllNotificationGcm;

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
import website.metier.MessageBean;
import website.metier.TypeEtatActivite;
import website.metier.TypeEtatMessage;

/**
 * Servlet implementation class MesMessages
 */
public class MesMessages extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(MesMessages.class);
	  
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

		
		doPost(request,response);
	
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

		
		afficheJumbotron(listMesMessages.size(), request,
				filtre.getTypeMessage());
		
			request.setAttribute("listMesMessages", listMesMessages);
			request.getRequestDispatcher("/pro/mesMessages.jsp")
					.forward(request, response);

		
	
	}
	
	private void afficheJumbotron(int nbrActivite, HttpServletRequest request,
			int typeEtatActivite) {

		JumbotronJsp jumbotron = null;
		if (nbrActivite == 0) {

			switch (typeEtatActivite) {

			case TypeEtatMessage.LU:
				jumbotron = new JumbotronJsp("Informations",
						"Aucun message à lire","");
				request.setAttribute("jumbotron", jumbotron);
				break;

			case TypeEtatMessage.NONLU:
				jumbotron = new JumbotronJsp("Informations",
						"Aucun message non lus","");
				request.setAttribute("jumbotron", jumbotron);
				break;

			case TypeEtatMessage.TOUS:
				jumbotron = new JumbotronJsp("Informations",
						"Aucun messages en cours",
						"Bla....");
				request.setAttribute("jumbotron", jumbotron);
				break;

			}

		}

	}

}
