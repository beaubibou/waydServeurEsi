package servlet.commun;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import website.dao.ActiviteDAO;
import website.metier.ActiviteAjax;
import website.metier.AuthentificationSite;

import com.google.gson.Gson;

/**
 * Servlet implementation class ListMapActivite
 */
public class ListMapActivite extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ListMapActivite.class); 
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
		
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		
		if (!authentification.isAuthentifieAdmin())
			return;
		
		
		double malongitude = Double.parseDouble(request.getParameter("longitude"));
		double malatitude = Double.parseDouble(request.getParameter("latitude"));
		double latitudeNE = Double.parseDouble(request.getParameter("boundNElat"));
		double longitueNE = Double.parseDouble(request.getParameter("boundNElon"));
		double latitudeSW = Double.parseDouble(request.getParameter("boundSWlat"));
		double longitueSW = Double.parseDouble(request.getParameter("boundSWlon"));
	
		
		try {
	
			List<ActiviteAjax>	listActiviteAjax= new ActiviteDAO().getListActiviteAjaxMap(malatitude, malongitude,latitudeNE, longitueNE, latitudeSW, longitueSW);
		// mettre la requete
		
			String json = new Gson().toJson(listActiviteAjax);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			
		
		
		
		} catch (Exception e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
		}
		
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		}

}
