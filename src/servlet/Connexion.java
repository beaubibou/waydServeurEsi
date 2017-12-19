package servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import texthtml.pro.Erreur_HTML;
import wayd.ws.WBservices;
import website.dao.CacheValueDAO;
import website.dao.PersonneDAO;
import website.enumeration.TypePhoto;
import website.metier.Outils;
import website.metier.ProfilBean;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

/**
 * Servlet implementation class Connexion
 */
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(Connexion.class);
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub

		super.init(config);

	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	if (testEsi(request, response))
return;

		String pwd = (String) request.getParameter("pwd");
		testToken(request.getParameter("token"), request, response, pwd);

	}

	public boolean testEsi(HttpServletRequest request,
			HttpServletResponse response) {

	
	
		
		final HttpSession session = request.getSession();
		ProfilBean profil = PersonneDAO.getFullProfilByUid("papa");
		session.setAttribute("profil", profil);
		
		try {
			response.sendRedirect("Acceuil");
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
		}

		return true;

	}

	public void testToken(final String idtoken, HttpServletRequest request,
			final HttpServletResponse response, String pwd) throws IOException,
			ServletException {

		final HttpSession session = request.getSession();
	

		try {
			FirebaseToken token = FirebaseAuth.getInstance()
					.verifyIdTokenAsync(idtoken).get();
			String uid = token.getUid();
			
//			if (!token.isEmailVerified()){
//				request.setAttribute("message","Votre adresse mail n'est pas vérifiée");
//				request.getRequestDispatcher("commun/erreurConnection.jsp")
//						.forward(request, response);	
//				return;
//				
//			}
//			
			
			ProfilBean profil = PersonneDAO.getFullProfilByUid(uid);

			if (profil == null) {

				
				request.setAttribute("message",
						Erreur_HTML.CREATION_COMPTE_NOK);
				request.getRequestDispatcher("commun/erreurConnection.jsp")
						.forward(request, response);
				return;

			}

			if (profil != null) {

				if (!profil.isActif()) {
					request.setAttribute("message",Erreur_HTML.ERR_COMPTE_DESACTIVTE);
					request.getRequestDispatcher("commun/erreurConnection.jsp")
							.forward(request, response);
					return;
				}

				session.setAttribute("profil", profil);

			

				if (profil.isAdmin()) {

					response.sendRedirect("Acceuil");
				
					return;

				}

				switch (profil.getTypeuser()) {

				case ProfilBean.PRO:
					session.setAttribute("profil", profil);

					response.sendRedirect("AcceuilPro");
					
					return;

				case ProfilBean.WAYDEUR:

					session.invalidate();
					
					request.setAttribute("message", Erreur_HTML.APPLICATION_NON_DISPO_WAYDEUR);	
					request.getRequestDispatcher("pro/messageInfo.jsp").forward(request, response);
			
					return;

				}

			}

		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("commun/erreurConnection.jsp")
					.forward(request, response);
			return;
		}

	}



}
