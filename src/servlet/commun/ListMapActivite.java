package servlet.commun;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import com.google.gson.Gson;

import website.dao.ActiviteDAO;
import website.metier.ActiviteAjax;
import website.metier.AuthentificationSite;

/**
 * Servlet implementation class ListMapActivite
 */
public class ListMapActivite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListMapActivite() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		
		if (!authentification.isAuthentifie())
			return;
		
		
		double malongitude = Double.parseDouble(request.getParameter("longitude"));
		double malatitude = Double.parseDouble(request.getParameter("latitude"));
		double latitudeNE = Double.parseDouble(request.getParameter("boundNElat"));
		double longitueNE = Double.parseDouble(request.getParameter("boundNElon"));
		double latitudeSW = Double.parseDouble(request.getParameter("boundSWlat"));
		double longitueSW = Double.parseDouble(request.getParameter("boundSWlon"));
	
		System.out.println(latitudeNE+" "+longitueNE+" "+latitudeSW+" "+longitueSW);
		
		try {
	
			List<ActiviteAjax>	listActiviteAjax= new ActiviteDAO().getListActiviteAjaxMap(malatitude, malongitude,latitudeNE, longitueNE, latitudeSW, longitueSW);
		// mettre la requete
		
			String json = new Gson().toJson(listActiviteAjax);
			for (ActiviteAjax activiteAjax:listActiviteAjax)
				System.out.println(activiteAjax);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			
		//	System.out.println(listActiviteAjax.size());
		
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
