package servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import website.dao.ActiviteDAO;
import website.metier.AuthentificationSite;

/**
 * Servlet implementation class EffaceActiviteCarpediem
 */
public class EffaceActiviteCarpediem extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public final static String RAZ_CARPEDIEM = "RAZ_CARPEDIEM";
	public final static String RAZ_CARPEDIEM_BEFORE_NOW = "RAZ_CARPEDIEM_BEFORE_NOW";

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public EffaceActiviteCarpediem() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);

		if (!authentification.isAuthentifieAdmin())
			return;

		String action = request.getParameter("action");

		switch (action) {

		case RAZ_CARPEDIEM:

			ActiviteDAO.effaceTouteCarpeDiem();

			break;
		case RAZ_CARPEDIEM_BEFORE_NOW:

			ActiviteDAO.effaceTouteCarpeDiem(new Date());
	
			break;

		}
	}

}
