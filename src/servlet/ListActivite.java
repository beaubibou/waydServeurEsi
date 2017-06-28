package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import website.dao.ActiviteDAO;
import website.dao.TypeActiviteDAO;
import website.metier.ActiviteBean;
import website.metier.FiltreJSP;
import website.metier.TypeActiviteBean;

/**
 * Servlet implementation class ListActivite
 */
public class ListActivite extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListActivite() {
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
			// ArrayList<ActiviteBean> listActivite= new
			// ArrayList<ActiviteBean>();
			ArrayList<TypeActiviteBean> listTypeActivite = TypeActiviteDAO
					.getListTypeActivite();

			// listActivite=ActiviteDAO.getListActiviteTotal();
			// request.setAttribute("listActivite", listActivite);
			request.setAttribute("listtypeactivite", listTypeActivite);
			request.getRequestDispatcher("listActivite.jsp").forward(request,
					response);

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();

		if (session.getAttribute("profil") != null) {

			if (request.getParameter("rechercheactivite") != null) {

				int rayon = Integer.parseInt(request.getParameter("rayon"));
				int idtypeactivite = Integer.parseInt(request
						.getParameter("typeactivite"));
				double latitude = Double.parseDouble(request
						.getParameter("latitude"));
				double longitude = Double.parseDouble(request
						.getParameter("longitude"));
				String ville = (String) request.getParameter("autocomplete");

				FiltreJSP filtreActivite = new FiltreJSP(rayon, idtypeactivite,
						ville, latitude, longitude);

				ArrayList<TypeActiviteBean> listTypeActivite = TypeActiviteDAO
						.getListTypeActivite();

				System.out.println(listTypeActivite.size() + " rayon " + rayon
						+ " type " + idtypeactivite + " long" + longitude
						+ " lat " + latitude);

				request.setAttribute("listtypeactivite", listTypeActivite);
				request.setAttribute("filtre", filtreActivite);

				ArrayList<ActiviteBean> listActivite = new ArrayList<ActiviteBean>();
				listActivite = ActiviteDAO.getListActivite(latitude, longitude,
						rayon, idtypeactivite);
				request.setAttribute("listActivite", listActivite);
				request.getRequestDispatcher("listActivite.jsp").forward(
						request, response);

				return;
			}

		} else {
			response.sendRedirect("login.jsp");
		}
	}

}
