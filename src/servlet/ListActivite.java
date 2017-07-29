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
import website.metier.FiltreJSP;
import website.metier.Outils;
import website.metier.Pagination;

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

			if (request.getParameter("pageAafficher") != null) {

				// Si on rentre par page à afficher c'est que le filtre est stocké dans la session
				// Suite à une  recherche. (submit du form listactivite).
				FiltreJSP filtre = (FiltreJSP) session.getAttribute("filtre");
			
				int nbrTotalLigne = ActiviteDAO.getCountListActivite(
						filtre.getLatitude(), filtre.getLongitude(),
						filtre.getRayon(), filtre.getTypeactivite());
				
				System.out.println("page afiicher"
						+ request.getParameter("pageAafficher"));
				System.out.println(filtre.toString());

				int pageAafficher = Integer.parseInt(request
						.getParameter("pageAafficher"));

			Pagination pagination = new website.metier.Pagination(
						nbrTotalLigne, pageAafficher, Outils.nbrLigneParPage,
						Outils.nbrMaxPagination, 1);
			
				System.out.println(pagination);
				ArrayList<ActiviteBean> listActivite = ActiviteDAO
						.getListActivite(filtre.getLatitude(),
								filtre.getLongitude(), filtre.getRayon(),
								filtre.getTypeactivite(),
								Outils.nbrLigneParPage, pagination.getDebut());

				request.setAttribute("pagination", pagination);
				request.setAttribute("listActivite", listActivite);
				request.setAttribute("nbrTotalLigne",Integer.toString(nbrTotalLigne) );
				request.setAttribute("pageAafficher",Integer.toString(pageAafficher));
				request.getRequestDispatcher("listActivite.jsp").forward(
						request, response);

			} else {
			
				request.setAttribute("nbrTotalLigne", Integer.toString(0));
				request.setAttribute("pageAafficher", Integer.toString(0));
				request.getRequestDispatcher("listActivite.jsp").forward(
						request, response);

			}

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

			// Si la rechereche vient du submit

			if (request.getParameter("rechercheactivite") != null) {

				System.out.println("rechercher acti!=null");
				int rayon = Integer.parseInt(request.getParameter("rayon"));
				int idtypeactivite = Integer.parseInt(request
						.getParameter("typeactivite"));
				double latitude = Double.parseDouble(request
						.getParameter("latitude"));
				double longitude = Double.parseDouble(request
						.getParameter("longitude"));
				String ville = (String) request.getParameter("autocomplete");

				Pagination pagination = null;

				FiltreJSP filtreActivite = new FiltreJSP(rayon, idtypeactivite,
						ville, latitude, longitude);

				
				session.setAttribute("filtre", filtreActivite);

				ArrayList<ActiviteBean> listActivite = new ArrayList<ActiviteBean>();

				int nbrTotalLigne = ActiviteDAO.getCountListActivite(latitude,
						longitude, rayon, idtypeactivite);

				pagination = new website.metier.Pagination(nbrTotalLigne,
							1, Outils.nbrLigneParPage, Outils.nbrMaxPagination,
							1);
					System.out.println(pagination.toString());

					listActivite = ActiviteDAO.getListActivite(latitude,
							longitude, rayon, idtypeactivite,
							Outils.nbrLigneParPage, pagination.getDebut());
				
					request.setAttribute("nbrTotalLigne",Integer.toString(1) );
					request.setAttribute("pageAafficher",Integer.toString(1));
				
				request.setAttribute("pagination", pagination);
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
