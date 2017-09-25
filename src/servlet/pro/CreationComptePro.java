package servlet.pro;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import website.metier.ProfilBean;

/**
 * Servlet implementation class CreationComptePro
 */
public class CreationComptePro extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreationComptePro() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();

		String nom = request.getParameter("nom");
		String adresse = request.getParameter("adresse");
		String commentaire = request.getParameter("commentaire");
		String siret=request.getParameter("siret");
		String telephonne=request.getParameter("telephone");
		
		//int typeuser = Integer.parseInt(request.getParameter("typeuser"));
		double latitude = 0;
		double longitude = 0;
		//int sexe = 1;

		if (request.getParameter("latitude") != null)
		
		 latitude = Double.parseDouble(request.getParameter("latitude"));

		if (request.getParameter("longitude") != null)
			 longitude =Double.parseDouble(request.getParameter("longitude"));

		
		ProfilBean profil = (ProfilBean) session.getAttribute("profil");

		if (profil != null) {
			if (!profil.isPremiereconnexion()){
				response.sendRedirect("aut/login.jsp");
				return;
			}
			website.dao.PersonneDAO personneDAO = new website.dao.PersonneDAO();

				personneDAO.updateProfilPro(nom, adresse, latitude, longitude,
						 commentaire,siret,telephonne, profil.getId());
				profil.setPseudo(nom);
				profil.setAdresse(adresse);
				profil.setCommentaire(commentaire);
				profil.setTypeuser(ProfilBean.PRO);
				profil.setLatitude(latitude);
				profil.setLongitude(longitude);
				profil.setLatitudeFixe(latitude);
				profil.setLongitudeFixe(longitude);
				profil.setTelephone(telephonne);
				profil.setSiret(siret);
				profil.setPremiereconnexion(false);
				response.sendRedirect("AcceuilPro");


		}

			
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
