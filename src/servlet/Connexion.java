package servlet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import wayde.bean.CxoPool;
import website.dao.PersonneDAO;
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
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
		System.out.println("Do post Connexion");

		String pwd = (String) request.getParameter("pwd");

		testToken(request.getParameter("token"), request, response, pwd);

	}

	public void testToken(String idtoken, HttpServletRequest request,
			HttpServletResponse response, String pwd) {

		HttpSession session = request.getSession();

		FirebaseAuth.getInstance().verifyIdToken(idtoken)
				.addOnSuccessListener(new OnSuccessListener<FirebaseToken>() {
					@Override
					public void onSuccess(FirebaseToken decodedToken) {

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
						System.out.println("admin" + profil);

						if (profil == null) {
							Connection connexion = null;

							try {
								System.out.println("Creation du compte"
										+ profil);
								connexion = CxoPool.getConnection();
								connexion.setAutoCommit(false);
								wayde.dao.PersonneDAO personnedao = new wayde.dao.PersonneDAO(
										connexion);
								personnedao.addCompteGenerique(uid, idtoken,
										"", "", "");
								connexion.commit();

								profil = PersonneDAO.getFullProfilByUid(uid);
								System.out.println("user cree" + profil);

								session.setAttribute("profil", profil);
								response.sendRedirect("/wayd/auth/form_PremierProfil.jsp");

								return;
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
									response.sendRedirect("/wayd/auth/form_PremierProfil.jsp");
									return;
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}

							if (profil.isAdmin()) {

								try {
									response.sendRedirect("Acceuil");
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
									System.out
											.println("Bacule vers acueeil pro");
									response.sendRedirect("AcceuilPro");
									return;
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								break;

							case ProfilBean.WAYDEUR:
								
								session.setAttribute("profil", profil);
								try {
									System.out.println("Bacule vers acueeil waydeur");
									response.sendRedirect("AcceuilWaydeur");
									return;
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}


								break;

							case ProfilBean.ASSOCIATION:

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

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
