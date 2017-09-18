package servlet.pro;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import website.dao.ActiviteDAO;
import website.dao.StatDAO;
import website.metier.IndicateurWayd;
import website.metier.ProfilBean;

/**
 * Servlet implementation class AcceuilPro
 */
public class AcceuilPro extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AcceuilPro() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		
		//*********  Regle d'authentification*********************
		
		ProfilBean profil=(ProfilBean)session.getAttribute("profil");
		
		System.out.println("Profil dans acceuil pro"+profil);
		if (profil==null){
			response.sendRedirect("/wayd/auth/login.jsp");
		    return;
		}
						
		if (profil.getTypeuser()!=ProfilBean.PRO ||profil.isPremiereconnexion())
		{
		response.sendRedirect("/wayd/auth/login.jsp");
		return;
		}
		//*********************************************************
		request.getRequestDispatcher("MesActivites").forward(request, response);
		
		
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
