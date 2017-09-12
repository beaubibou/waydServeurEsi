package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import website.dao.PersonneDAO;
import website.metier.ProfilBean;

/**
 * Servlet implementation class ListProfil
 */
public class ListProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListProfil() {
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
				ArrayList<ProfilBean> 	listProfil= new ArrayList<ProfilBean>();
				listProfil=PersonneDAO.getListProfil();
				request.setAttribute("listProfil", listProfil);
				request.getRequestDispatcher("admin/listProfil.jsp").forward(request, response);
			
			}
		else{
			response.sendRedirect("auth/login.jsp");
		}
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
	
		
		
	}

}
