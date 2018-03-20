package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import website.dao.LogDAO;
import website.metier.AuthentificationSite;
import website.metier.Outils;

/**
 * Servlet implementation class ParametreAdmin
 */
public class ParametreAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ParametreAdmin.class);

	  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ParametreAdmin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);

		if (!authentification.isAuthentifieAdmin())
			return;
				
		int maxLogs = Integer.parseInt(request
				.getParameter("maxLogs"));

		int nbrLogSupprimer = Integer.parseInt(request
				.getParameter("nbrLogSupprimer"));

		int tempsWarning = Integer.parseInt(request
				.getParameter("tempsWarning"));

		int echantillonage = Integer.parseInt(request
				.getParameter("echantillonage"));
		
		int tpscalculperf = Integer.parseInt(request
				.getParameter("tpscalculperf"));
		
		if (request.getParameter("verifieEmail")==null ||request.getParameter("verifieEmail").isEmpty()){
			Connexion.verifieEmail=false;
		}
		else
		{
			Connexion.verifieEmail=true;
		}
		
		if (request.getParameter("clemapadmin")==null ||request.getParameter("clemapadmin").isEmpty()){
			Outils.clemapadmin=false;
		}
		else
		{
			Outils.clemapadmin=true;
		}
		
		
		
		LogDAO.MAX_LOG_SIZE=maxLogs;
		LogDAO.NBR_LOG_A_EFFACER=nbrLogSupprimer;
		LogDAO.TPS_WARNING_REQUETE=tempsWarning;
		LogDAO.TPS_ECHATILLONNAGE=echantillonage;
		LogDAO.TPS_CALCUL_PERFOMENCE=tpscalculperf;
		

		response.sendRedirect("admin/paramAdmin.jsp");
		

}
	
}
