package servlet.waydeur;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import wayde.bean.CxoPool;
import website.metier.ProfilBean;

/**
 * Servlet implementation class CreationCompteWaydeur
 */
public class CreationCompteWaydeur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(CreationCompteWaydeur.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreationCompteWaydeur() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (true)return;
	
		HttpSession session = request.getSession();

		String nom = request.getParameter("nom");
		String commentaire = request.getParameter("commentaire");
		int sexe = 1;
	
			if (request.getParameter("sexe") != null)
				sexe = Integer.parseInt(request.getParameter("sexe"));

		ProfilBean profil = (ProfilBean) session.getAttribute("profil");

		if (profil != null) {
			if (!profil.isPremiereconnexion()){
				response.sendRedirect("auth/login.jsp");
				return;
			}
	
			website.dao.PersonneDAO personneDAO = new website.dao.PersonneDAO();
				personneDAO.updateProfilWaydeur(nom, sexe, commentaire,
						profil.getId());
				profil.setPseudo(nom);
				profil.setCommentaire(commentaire);
				profil.setTypeuser(ProfilBean.WAYDEUR);
				profil.setPremiereconnexion(false);
				response.sendRedirect("AcceuilWaydeur");
				

		} 
		else {

			response.sendRedirect("auth/login.jsp");
			return;
		}

	
	
	
	
	
	
	
	
	
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (true)return;
	}

}
