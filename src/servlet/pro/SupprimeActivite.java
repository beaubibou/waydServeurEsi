package servlet.pro;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import website.coordination.Coordination;
import website.dao.ActiviteDAO;
import website.metier.ActiviteBean;
import website.metier.ProfilBean;

/**
 * Servlet implementation class SupprimeActivite
 */
public class SupprimeActivite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupprimeActivite() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
	System.out.println("efface");
	
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
	
	int idActivite=Integer.parseInt(request.getParameter("idactivite"));

	ActiviteBean activite= new Coordination().getActivite(idActivite);
	System.out.println("activite à efface "+idActivite);
	
	if (activite.getIdorganisateur()==profil.getId()){
		 new Coordination().effaceActivite(idActivite);
		System.out.println("activite  effacée "+idActivite);
		response.sendRedirect("MesActivites");
	}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
	
	
	}

}
