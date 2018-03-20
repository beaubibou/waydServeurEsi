package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import website.dao.ActiviteDAO;
import website.metier.AuthentificationSite;

/**
 * Servlet implementation class ModifieActivite
 */
public class ModifieActivite extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ModifieActivite.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifieActivite() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		if (!authentification.isAuthentifieAdmin())
			return;

		String action = request.getParameter("action");

		switch (action) {

		case "setgratuit":

			setGratuit(request, response);
	
			break;

		case "setcategorie":

			setCategorie(request, response);
			break;

		case "setactif":

			setActif(request, response);

			break;

		default:
			break;
		}

	}

	private void setGratuit(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			int gratuit = Integer.parseInt(request.getParameter("gratuit"));
			int idactivite = Integer.parseInt(request
					.getParameter("idactivite"));
			ActiviteDAO.setGratuite(idactivite, gratuit);

		}

		catch (Exception e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
		}
	}

	private void setCategorie(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			int typeactivite = Integer.parseInt(request
					.getParameter("categorie"));
			int idactivite = Integer.parseInt(request
					.getParameter("idactivite"));
			ActiviteDAO.setTypeActivite(idactivite, typeactivite);
			LOG.info(typeactivite);
		} catch (Exception e) {
			LOG.error(ExceptionUtils.getStackTrace(e));

		}
	}

	private void setActif(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			boolean actif = Boolean.parseBoolean(request.getParameter("actif"));
			int idactivite = Integer.parseInt(request
					.getParameter("idactivite"));
			ActiviteDAO.setActif(idactivite, actif);
			LOG.info("setActif");
		}

		catch (Exception e) {

			LOG.error(ExceptionUtils.getStackTrace(e));

		}
	}

}
