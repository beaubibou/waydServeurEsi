package servlet.commun;

import gcmnotification.AcquitAllNotificationGcm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import website.dao.ActiviteDAO;
import website.dao.PersonneDAO;
import website.metier.ActiviteAjax;
import website.metier.AuthentificationSite;
import website.metier.AvisBean;

import com.google.gson.Gson;

/**
 * Servlet implementation class PlusAvis
 */
public class PlusAvis extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(PlusAvis.class);
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PlusAvis() {
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

//		try {
//
//			AuthentificationSite authentification = new AuthentificationSite(
//					request, response);
//
//			if (!authentification.isAuthentifie())
//				return;
//			int lastIndex = Integer.parseInt(request.getParameter("lastIndex"));
//			ArrayList<AvisBean> listAvis = PersonneDAO.getListAvisAfter(
//					authentification.getProfil().getId(), lastIndex);
//			
//			String json = new Gson().toJson(listAvis);
//			response.setContentType("application/json");
//			response.setCharacterEncoding("UTF-8");
//			response.getWriter().write(json);
//			
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			LOG.error( ExceptionUtils.getStackTrace(e));
//		}
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
