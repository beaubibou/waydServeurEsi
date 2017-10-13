package servlet.commun;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import website.coordination.Coordination;
import website.dao.ActiviteDAO;
import website.metier.ActiviteBean;
import website.metier.ProfilBean;

/**
 * Servlet implementation class ModifierActivite
 */
public class ModifierActivite extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifierActivite() {
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

		int idActivite = 0;// Integer.parseInt(request.getParameter("idActivite"));

		idActivite = 3;
		ActiviteBean activiteBean = new Coordination().getActivite(idActivite);
		activiteBean.setTypeactivite(3);
		Calendar cal=Calendar.getInstance();
		
		cal.set(Calendar.HOUR_OF_DAY, 20);
		
		activiteBean.setDatefin(cal.getTime());
			
		
		if (activiteBean == null) {
			System.out.println("L'activite n'exite plus");
			return;

		}
		switch (activiteBean.getTypeUser()) {

		case ProfilBean.PRO:

			request.setAttribute("activite", activiteBean);
			request.getRequestDispatcher("/pro/modifierActivitePro.jsp")
					.forward(request, response);

			break;

		case ProfilBean.WAYDEUR:

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
