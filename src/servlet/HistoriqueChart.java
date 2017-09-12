package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import website.dao.StatDAO;
import website.metier.Outils;

/**
 * Servlet implementation class HistoriqueChart
 */
public class HistoriqueChart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HistoriqueChart() {
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

		String requete = null;
		String titre = null;
		String action = request.getParameter("action");
		switch (action) {

		case "histoNbrMessage":

			requete = Outils.convertStatistiqueBeanToString(StatDAO
					.getHistoriqueMessage());
			titre = "Nombre de messages";

			break;

		case "histoNbrMessageByAct":

			requete = Outils.convertStatistiqueBeanToString(StatDAO
					.getHistoriqueMessageByAct());
			titre = "Messages des activit�s";

			break;
		case "histoNbrActivite":

			requete = Outils.convertStatistiqueBeanToString(StatDAO
					.getHistoriqueActivite());
			titre = "Nombre Activit�s";
			break;

		case "histoNbrParticipation":

			requete = Outils.convertStatistiqueBeanToString(StatDAO
					.getHistoriqueParticipation());
			titre = "Nombre de participation";
			break;

		default:
			break;
		}

		request.setAttribute("titre", titre);
		request.setAttribute("requete", requete);
		request.getRequestDispatcher("admin/chartLine.jsp")
				.forward(request, response);

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
