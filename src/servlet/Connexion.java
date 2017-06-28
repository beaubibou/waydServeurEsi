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

		System.out.println("token " + request.getParameter("token"));
		
		testToken(request.getParameter("token"),request,response);
		// response.sendRedirect("login.jsp");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		String pwd = request.getParameter("pwd");
		System.out.println("Do post Connexion");
		HttpSession session = request.getSession();

		session.setAttribute("profil",new ProfilBean());

		if (true)
			response.sendRedirect("Acceuil");

	}

	public void testToken(String idtoken, HttpServletRequest request, HttpServletResponse response) {

	
		HttpSession session=request.getSession();
		
		FirebaseAuth.getInstance().verifyIdToken(idtoken)
				.addOnSuccessListener(new OnSuccessListener<FirebaseToken>() {
					@Override
					public void onSuccess(FirebaseToken decodedToken) {
						
						String uid = decodedToken.getUid();
						ProfilBean profil=PersonneDAO.getFullProfilByUid(uid);
						System.out.println("admin"+profil.isAdmin());	
						if (profil!=null)
							if (profil.isAdmin()){
						
						System.out.println(profil.getPseudo());
						session.setAttribute("profil", profil);
						
						try {
							response.sendRedirect("Acceuil");
							
						
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						}
							else{
							
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
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
