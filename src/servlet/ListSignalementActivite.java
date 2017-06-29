package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import website.dao.ActiviteDAO;
import website.dao.PersonneDAO;
import website.dao.SignalementDAO;
import website.metier.ActiviteBean;
import website.metier.ProfilBean;
import website.metier.SignalementCount;

/**
 * Servlet implementation class ListSignalementActivite
 */
public class ListSignalementActivite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListSignalementActivite() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();

		if (session.getAttribute("profil") != null )
		{
			ArrayList<ActiviteBean> 	listActivite= new ArrayList<ActiviteBean>();
			listActivite=ActiviteDAO.getListActiviteSignale();
			request.setAttribute("listActivite", listActivite);
			request.getRequestDispatcher("listActiviteSignale.jsp").forward(request, response);
		
		}
	else{
		response.sendRedirect("login.jsp");
	}

	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
