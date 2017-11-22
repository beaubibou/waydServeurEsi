package servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import wayd.ws.WBservices;
import wayde.bean.CxoPool;
import wayde.bean.MessageServeur;
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
	protected boolean success;

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
		if (FirebaseApp.getApps().isEmpty())
			FirebaseApp.initializeApp(WBservices.optionFireBase);
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(getServletContext().getRealPath("/")
					+ "img/inconnu.jpg"));
			String photoStr = Outils.encodeToString(img, "jpg");
			CacheValueDAO.addPhotoCache(TypePhoto.Inconnu, photoStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		LOG.info("Do post Connexion");
		success = false;
		//
		if (testEsi(request, response))
			return;

		String pwd = (String) request.getParameter("pwd");
		testToken(request.getParameter("token"), request, response, pwd);

	}

	public boolean testEsi(HttpServletRequest request,
			HttpServletResponse response) {

		final HttpSession session = request.getSession();
		String pwd = (String) request.getParameter("pwd");
		ProfilBean profil = PersonneDAO.getFullProfilByUid("papa");
		System.out.println(profil.getPseudo());

		session.setAttribute("profil", profil);

		try {
			response.sendRedirect("Acceuil");
		//	response.sendRedirect("AcceuilPro");
			// response.sendRedirect("/wayd/auth/inscriptionPro.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;

	}

	public void testToken(final String idtoken, HttpServletRequest request,
			final HttpServletResponse response, String pwd) throws IOException {

		final HttpSession session = request.getSession();
		LOG.info("Test token");

		try {
			FirebaseToken token = FirebaseAuth.getInstance()
					.verifyIdTokenAsync(idtoken).get();
			String uid = token.getUid();

			ProfilBean profil = PersonneDAO.getFullProfilByUid(uid);

			if (profil == null) {

				MessageServeur messageServeur = creationProfilBdd(uid, idtoken,
						request, response);
				if (messageServeur.isReponse()) {
					response.sendRedirect("/wayd/auth/inscriptionPro.jsp");
					return;
				} else {

					response.sendRedirect("Error.jsp");
					return;

				}
			}

			if (profil != null) {

				session.setAttribute("profil", profil);

				// if (profil.isPremiereconnexion()) {
				// try {
				// LOG.info("premier connexion");
				// response.sendRedirect("/wayd/auth/inscriptionPro.jsp");
				// success = true;
				// return;
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				//
				// }

				if (profil.isAdmin()) {

					response.sendRedirect("Acceuil");
					success = true;
					return;

				}

				switch (profil.getTypeuser()) {

				case ProfilBean.PRO:
					session.setAttribute("profil", profil);

					response.sendRedirect("AcceuilPro");
					success = true;
					return;

				case ProfilBean.WAYDEUR:

					session.invalidate();
					response.sendRedirect("/wayd/auth/pageNoWaydeurSite.jsp");
					success = true;
					return;

				}

			}

		} catch (InterruptedException | ExecutionException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			response.sendRedirect("Error.jsp");
		}

	}

	private MessageServeur creationProfilBdd(String uid, String idtoken,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Connection connexion = null;

		try {
			LOG.info("Creation du compte");
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			wayde.dao.PersonneDAO personnedao = new wayde.dao.PersonneDAO(
					connexion);
			personnedao.addCompteGenerique(uid, idtoken, "", "", "");
			connexion.commit();

			ProfilBean profil = PersonneDAO.getFullProfilByUid(uid);

			if (profil != null) {
				HttpSession session = request.getSession();
				LOG.info("User cr�e" + profil);
				session.setAttribute("profil", profil);

				return new MessageServeur(true, "ok");
			} else {
				return new MessageServeur(false, "ok");

			}

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			try {
				connexion.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} // ...
		finally {
			CxoPool.closeConnection(connexion);
			//
		}
		return new MessageServeur(false, "ok");

	}

}
