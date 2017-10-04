package servlet.waydeur;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import website.metier.ProfilBean;

/**
 * Servlet implementation class AcceuilWaydeur
 */
public class AcceuilWaydeur extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AcceuilWaydeur() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		//*********  Regle d'authentification*********************
		
		ProfilBean profil=(ProfilBean)session.getAttribute("profil");
				System.out.println("Profil dans acceuil waydeur"+profil);
		if (profil==null){
			response.sendRedirect("auth/login.jsp");
		    return;
		}
						
		if (profil.getTypeuser()!=ProfilBean.WAYDEUR ||profil.isPremiereconnexion())
		{
		response.sendRedirect("auth/login.jsp");
		return;
		}
		//*********************************************************
			
			
		request.getRequestDispatcher("MesActivitesWaydeur").forward(request, response);
		
		
		// TODO Auto-generated method stub
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
