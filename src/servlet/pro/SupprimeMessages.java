package servlet.pro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import website.dao.MessageDAO;
import website.metier.AuthentificationSite;

/**
 * Servlet implementation class SupprimeMessages
 */
public class SupprimeMessages extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupprimeMessages() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		
		if (!authentification.isAuthentifiePro())
			return;

			List<Integer> param=new ArrayList<>();
		int nbrLigneSupprime =0;
		String listMessage=request.getParameter("listMessage");
		
		for (String activiteStr:listMessage.split(";"))
		
		{
	
		
		int idMessage = Integer.parseInt(activiteStr);
		param.add(idMessage);
		System.out.println("a effacer "+idMessage);
		
		}
	
		MessageDAO.effaceMessages(param);
	}

}
