package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import website.dao.ActiviteDAO;
import website.metier.ActiviteBean;

/**
 * Servlet implementation class DetailActivite
 */
public class DetailActivite extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DetailActivite() {
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

		if (session.getAttribute("profil") != null) {

			String action = (String) request.getParameter("action");
			System.out.println("ACTION=" + action);

			if (action == null) {// chargemetn par d�faut
				int idActivite = Integer.parseInt(request
						.getParameter("idactivite"));
				System.out.println("Detail activite id=" + idActivite);
				ActiviteBean activite = ActiviteDAO.getActivite(idActivite);
				request.setAttribute("activite", activite);
				request.getRequestDispatcher("detailactivite.jsp").forward(
						request, response);

			} else

				switch (action) {

				case "terminerActivite":
					int idActivite = Integer.parseInt(request
							.getParameter("idactivite"));

					if (ActiviteDAO.terminerActivite(idActivite))
						response.sendRedirect("ListActivite");

					break;
				case "effacerActivite":

					idActivite = Integer.parseInt(request
							.getParameter("idactivite"));

					if (ActiviteDAO.supprimeActivite(idActivite))
						response.sendRedirect("ListActivite");

					break;

				}

		} else {

			response.sendRedirect("login.jsp");
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
