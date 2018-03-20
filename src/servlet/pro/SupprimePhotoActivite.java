package servlet.pro;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wayde.bean.MessageServeur;
import website.dao.ActiviteDAO;
import website.metier.AuthentificationSite;

/**
 * Servlet implementation class SupprimePhotoActivite
 */
public class SupprimePhotoActivite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupprimePhotoActivite() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
	
		
		if (!authentification.isAuthentifiePro())
			return;
	
		int idActivite =0;
		int id=0;
		if (request.getParameter("idActivite")!=null){
			idActivite = Integer.parseInt(request.getParameter("idActivite"));
				
		}
		
		
		if (request.getParameter("id")!=null){
			id = Integer.parseInt(request.getParameter("id"));
				
		}
	
		MessageServeur messageServeur=ActiviteDAO.supprimePhotoActivite(id, idActivite);
		response.sendRedirect("ModifierActivite?idactivite="+idActivite);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
		
	}

}
