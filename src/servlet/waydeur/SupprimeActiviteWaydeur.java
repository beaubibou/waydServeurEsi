package servlet.waydeur;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import wayd.ws.WBservices;
import website.dao.ActiviteDAO;
import website.metier.ActiviteBean;
import website.metier.ProfilBean;

/**
 * Servlet implementation class SupprimeActiviteWaydeur
 */
public class SupprimeActiviteWaydeur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(WBservices.class);
     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupprimeActiviteWaydeur() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	LOG.info("doGet-SupprimeActiviteWaydeur");
		HttpSession session = request.getSession();
		ProfilBean profil = (ProfilBean) session.getAttribute("profil");

		if (profil == null) {
			response.sendRedirect("auth/login.jsp");
			return;
		}

		if (profil.getTypeuser() != ProfilBean.WAYDEUR
				|| profil.isPremiereconnexion()) {
			response.sendRedirect("auth/login.jsp");
			return;
		}
		
		int idActivite=Integer.parseInt(request.getParameter("idactivite"));

		ActiviteBean activite=ActiviteDAO.getActivite(idActivite);
		System.out.println("activite à efface "+idActivite);
		
		if (activite.getIdorganisateur()==profil.getId()){
			ActiviteDAO.effaceActivite(idActivite);
			System.out.println("activite  effacée "+idActivite);
			response.sendRedirect("MesActivitesWaydeur");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
