package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import website.dao.SignalementDAO;
import website.metier.SignalementCount;

/**
 * Servlet implementation class ListSignalement
 */
public class ListSignalement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListSignalement() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		if (session.getAttribute("profil") != null) {

			ArrayList<SignalementCount> listSignalementCount = new ArrayList<SignalementCount>();
			listSignalementCount = SignalementDAO.getCountSignalementBy();
			request.setAttribute("listSignalementCount", listSignalementCount);
			request.getRequestDispatcher("listSignalement.jsp").forward(request,
					response);

		}else{
			response.sendRedirect("login.jsp");
		}

		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		ArrayList<SignalementCount> listSignalementCount = new ArrayList<SignalementCount>();
		listSignalementCount = SignalementDAO.getCountSignalementBy();
		request.setAttribute("listSignalementCount", listSignalementCount);
		request.getRequestDispatcher("listSignalement.jsp").forward(request,
				response);
	}

}
