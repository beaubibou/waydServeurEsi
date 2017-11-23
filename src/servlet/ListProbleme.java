package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import wayde.bean.MessageServeur;
import website.dao.ActiviteDAO;
import website.dao.ProblemeDAO;
import website.enumeration.AlertJsp;
import website.html.MessageAlertDialog;
import website.metier.AuthentificationSite;
import website.metier.ProblemeBean;
import website.metier.admin.FitreAdminProbleme;
import website.pager.PagerProblemeBean;

/**
 * Servlet implementation class ListProbleme
 */
public class ListProbleme extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ActiviteDAO.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListProbleme() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession();

		AuthentificationSite authentification = new AuthentificationSite(
				request, response);

		if (!authentification.isAuthentifieAdmin())
			return;

		FitreAdminProbleme filtreProbleme = (FitreAdminProbleme) session
				.getAttribute("filtreProbleme");
		int etatProbleme = filtreProbleme.getEtatProbleme();
		DateTime dateDebut = filtreProbleme.getDateDebutCreation();
		DateTime dateFin = filtreProbleme.getDateFinCreation();

		String action=(String) request.getParameter("action");
		
		doAction(action,request);
		
		
		if (request.getParameter("etatProbleme") != null) {
			etatProbleme = Integer.parseInt((String) request
					.getParameter("etatProbleme"));
			filtreProbleme.setEtatProbleme(etatProbleme);
		}

		try {

			if (request.getParameter("debut") != null) {

				String datedebut = request.getParameter("debut");
				dateDebut = getDateFromString(datedebut);
				filtreProbleme.setDateDebutCreation(dateDebut);

			}
			if (request.getParameter("fin") != null) {

				String datefin = request.getParameter("fin");
				dateFin = getDateFromString(datefin);
				filtreProbleme.setDateFinCreation(dateFin);

			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int pageAfficher = 0;
		if (request.getParameter("page") != null)
			pageAfficher = Integer.parseInt(request.getParameter("page"));

		PagerProblemeBean pagerProblemeBean = new PagerProblemeBean(
				filtreProbleme.getEtatProbleme(),
				filtreProbleme.getDateDebutCreation(),
				filtreProbleme.getDateFinCreation(), pageAfficher);
		
		System.out.println("*****************");
		System.out.println("paga à affiche " + pageAfficher);
		// ArrayList<ProblemeBean> listProblemes
		// =pagerProblemeBean.getListProbleme();

		// ArrayList<ProblemeBean> listProblemes = new
		// ArrayList<ProblemeBean>();
		// listProblemes = ProblemeDAO.getListProbleme(
		// filtreProbleme.getEtatProbleme(),
		// filtreProbleme.getDateDebutCreation(),
		// filtreProbleme.getDateFinCreation());
		//
		request.setAttribute("listProbleme",
				pagerProblemeBean.getListProbleme());
		request.setAttribute("pager", pagerProblemeBean);
		request.getRequestDispatcher("admin/listProbleme.jsp").forward(request,
				response);

	}

	private void doAction(String action,HttpServletRequest request) {
		// TODO Auto-generated method stub
	
	
		
		if (action==null)return;
		
		switch (action){
		
		case "clos":
			
			int idMessage = 0;

			if (request.getParameter("idmessage") != null) {
				idMessage = Integer.parseInt(request.getParameter("idmessage"));
			}

			MessageServeur messageServeur = ProblemeDAO.lireProbleme(idMessage);
	
			break;	
		
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		doGet(request, response);
	}

	public DateTime getDateFromString(String datestr) throws ParseException {

		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime dt = formatter.parseDateTime(datestr);
		return dt;
	}

}
