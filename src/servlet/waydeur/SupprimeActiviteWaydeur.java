package servlet.waydeur;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import texthtml.pro.Erreur_HTML;
import wayd.ws.WBservices;
import website.coordination.Coordination;
import website.enumeration.AlertJsp;
import website.html.AlertInfoJsp;
import website.metier.ActiviteBean;
import website.metier.AuthentificationSite;
import website.metier.ProfilBean;

/**
 * Servlet implementation class SupprimeActiviteWaydeur
 */
public class SupprimeActiviteWaydeur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(SupprimeActiviteWaydeur.class);
     
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
		
	AuthentificationSite authentification = new AuthentificationSite(
			request, response);
	if (!authentification.isAuthentifieWaydeur())
		return;

	int idActivite = Integer.parseInt(request.getParameter("idactivite"));

	ActiviteBean activite = new Coordination().getActivite(idActivite);
	
	
	

	if (activite.getIdorganisateur() == authentification.getId()) {
		new Coordination().effaceActivite(idActivite);
		new AlertInfoJsp(Erreur_HTML.ACTIVITE_SUPPRIMEE, AlertJsp.Sucess,"MesActivitesWaydeur").send(request, response);
		return;
		
	}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
