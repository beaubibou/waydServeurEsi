package servlet.pro;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import website.dao.ActiviteDAO;
import website.metier.ActiviteBean;
import website.metier.ProfilBean;

/**
 * Servlet implementation class ConfirmDialog
 */
public class ConfirmDialog extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmDialog() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		ProfilBean profil = (ProfilBean) session.getAttribute("profil");

		if (profil == null) {
			response.sendRedirect("auth/login.jsp");
			return;
		}

		if (profil.getTypeuser() != ProfilBean.PRO
				|| profil.isPremiereconnexion()) {
			response.sendRedirect("auth/login.jsp");
			return;
		}
		
		String action=request.getParameter("action");
		String from=request.getParameter("from");
		
		
		switch (action){
		
		case "effaceActivite":
			int idActivite=Integer.parseInt(request.getParameter("idactivite"));
			request.setAttribute("lienAction", "/wayd/SupprimeActivite?idactivite=" + idActivite);
			request.setAttribute("lienRetour", from);
			request.setAttribute("message", "Effacer l'activité");
			request.getRequestDispatcher("/pro/confirmdialog.jsp").forward(request, response);
			
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
