package servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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
import website.dao.CacheValueDAO;
import website.dao.PersonneDAO;
import website.enumeration.TypePhoto;
import website.metier.AuthentificationSite;
import website.metier.Outils;
import website.metier.ProfilBean;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.tasks.OnSuccessListener;

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
		FirebaseOptions options;
		if (FirebaseApp.getApps().isEmpty()) {

			try {

				options = new FirebaseOptions.Builder()
						// .setServiceAccount(new
						// FileInputStream("/usr/lib/jvm/java-8-openjdk-amd64/jre/cle/cle.json"))

						.setServiceAccount(new FileInputStream("d:/cle.json"))
						.setDatabaseUrl("https://wayd-c0414.firebaseio.com")
						.build();
				FirebaseApp.initializeApp(options);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (FirebaseApp.getApps().isEmpty()) {

			try {
				options = new FirebaseOptions.Builder()
						.setServiceAccount(
								new FileInputStream(
										"/usr/lib/jvm/java-8-openjdk-amd64/jre/cle/cle.json"))

						.setDatabaseUrl("https://wayd-c0414.firebaseio.com")
						.build();
				FirebaseApp.initializeApp(options);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		BufferedImage img = null;
		try {
		   img = ImageIO.read(new File(getServletContext().getRealPath("/") + "img/inconnu.jpg"));
		    String photoStr=Outils.encodeToString(img, "jpg");
		    CacheValueDAO.addPhotoCache(TypePhoto.Inconnu,photoStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		LOG.info("Do post Connexion");
		success=false;
		
		String pwd = (String) request.getParameter("pwd");
		testToken(request.getParameter("token"), request, response, pwd);

		try {
			int temps = 0;
			while (temps < 500) {
				Thread.sleep(30);
				LOG.info("wait"+temps);
				temps++;
				if (success) {

					temps = 1001;
				}

			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LOG.info("************Sortie");

	}

	
	
	public void testToken(final String idtoken, HttpServletRequest request,
			final HttpServletResponse response, String pwd) {

		final HttpSession session = request.getSession();
		LOG.info("Test token");
		FirebaseAuth.getInstance().verifyIdToken(idtoken)
				.addOnSuccessListener(new OnSuccessListener<FirebaseToken>() {
					@Override
					public void onSuccess(FirebaseToken decodedToken) {
						LOG.info("Feed back onsuccess");
						String uid = decodedToken.getUid();

					
						
						// if (!decodedToken.isEmailVerified() && pwd!=null ){
						//
						// try {
						// response.sendRedirect("/wayd/erreur/emailNonValide.jsp");
						// } catch (IOException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// }
						// return;
						// }

						ProfilBean profil = PersonneDAO.getFullProfilByUid(uid);

						if (profil == null) {
							Connection connexion = null;

							try {
								LOG.info("Creation du compte" + profil);
								connexion = CxoPool.getConnection();
								connexion.setAutoCommit(false);
								wayde.dao.PersonneDAO personnedao = new wayde.dao.PersonneDAO(
										connexion);
								personnedao.addCompteGenerique(uid, idtoken,
										"", "", "");
								connexion.commit();

								profil = PersonneDAO.getFullProfilByUid(uid);
								LOG.info("User cr�e" + profil);
								session.setAttribute("profil", profil);
								response.sendRedirect("/wayd/auth/inscriptionPro.jsp");
								success = true;
								return ;
							} catch (SQLException | NamingException
									| IOException e) {
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

							}

						}

						
						if (profil != null) {

							session.setAttribute("profil", profil);

							if (profil.isPremiereconnexion()) {
								try {
									response.sendRedirect("/wayd/auth/inscriptionPro.jsp");
									success = true;
									return;
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}

							if (profil.isAdmin()) {

								try {
									response.sendRedirect("Acceuil");
									success = true;
									return;
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							switch (profil.getTypeuser()) {

							case ProfilBean.PRO:
								session.setAttribute("profil", profil);
								try {

									response.sendRedirect("AcceuilPro");
									success = true;
									return;
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								break;

							case ProfilBean.WAYDEUR:

								//session.setAttribute("profil", profil);
								try {
									response.sendRedirect("/wayd/auth/pageNoWaydeurSite.jsp");
									success = true;
									return;
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								break;

							

							}

						} else {

							try {

								response.sendRedirect("Error.jsp");

							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

					}

				});

	}

}
