package servlet.commun;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import website.dao.PersonneDAO;
import website.metier.AuthentificationSite;
import website.metier.AvisBean;
import website.metier.ProfilBean;

/**
 * Servlet implementation class DetailProfilSite
 */
public class DetailProfilSite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailProfilSite() {
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
		if (!authentification.isAuthentifie())
			return;
		
		int idprofil = Integer.parseInt(request.getParameter("idprofil"));
		ProfilBean profil =  PersonneDAO.getFullProfil(idprofil);
		ArrayList<AvisBean> listAvis= PersonneDAO.getListAvis(profil.getId());
		
		request.setAttribute("profil", profil);
		request.setAttribute("listAvis", listAvis);
		
		

		switch (profil.getTypeuser()) {
		case ProfilBean.PRO:
			
			request.getRequestDispatcher("/commun/detailProfilPro.jsp").forward(request, response);
			
			break;

		case ProfilBean.WAYDEUR:

		
			request.getRequestDispatcher("/commun/detailProfilWaydeur.jsp").forward(request, response);
			
			break;

		default:

			break;
		}

	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
