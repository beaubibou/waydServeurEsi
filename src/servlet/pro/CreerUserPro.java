package servlet.pro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import wayd.ws.WBservices;
import wayde.bean.CxoPool;
import wayde.bean.MessageServeur;
import website.dao.PersonneDAO;
import website.enumeration.AlertJsp;
import website.html.MessageAlertDialog;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;

import fcm.ServeurMethodes;

/**
 * Servlet implementation class CreerUserPro
 */
public class CreerUserPro extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		if (FirebaseApp.getApps().isEmpty())
			FirebaseApp.initializeApp(WBservices.optionFireBase);

	}

	public CreerUserPro() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	
		response.sendRedirect("auth/CreationTotalCompteCaptcha.jsp");
	
	}
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		
		
		// **************************RECUEPRETAION DES PARAMETRES 
		
		String pwd=request.getParameter("pwd");
		String pwd1=request.getParameter("pwd1");
		String email=request.getParameter("email");
		String pseudo=request.getParameter("nom");
		String siret=request.getParameter("siret");
		String telephone=request.getParameter("telephone");
		String adresse=request.getParameter("adresse");
		String commentaire=request.getParameter("commentaire");
	
		double latitude = 0;
		double longitude = 0;
			
		if (request.getParameter("latitude") != null)
		
		 latitude = Double.parseDouble(request.getParameter("latitude"));

		if (request.getParameter("longitude") != null)
			 longitude =Double.parseDouble(request.getParameter("longitude"));
 
		//*******************************************************
	
		String reponseCaptcha = request.getParameter("g-recaptcha-response");
		try {
			
			if (!isCaptcha(reponseCaptcha)){
				
				redirige( pwd, pwd1, email, pseudo, siret, telephone, adresse, commentaire, latitude, longitude,
						request,response, "Le captcha n'est pas valide");
					
				return;	
			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			redirige( pwd, pwd1, email, pseudo, siret, telephone, adresse, commentaire, latitude, longitude,
					request,response, "Le captcha n'est pas valide");
				
			return;	
		}
		
		
		
		MessageServeur messageServeurTestRequete=testParametreRequete(pwd,pwd1,email,pseudo,siret,
				telephone,adresse,commentaire,latitude,longitude);
			
		if (!messageServeurTestRequete.isReponse()){
					
			redirige( pwd, pwd1, email, pseudo, siret, telephone, adresse, commentaire, latitude, longitude,
					request,response, messageServeurTestRequete.getMessage());
				
			return;	
		}
			
		MessageServeur messageServeurFireBase=	creerUtilisateurFireBase(email,pwd,pseudo);
		
		if (messageServeurFireBase.isReponse())
		{
			String uuid=messageServeurFireBase.getMessage();
			MessageServeur messageCreerUserDAO=creerUtilisateurDAO(uuid, pwd, pwd1, email, pseudo, siret, telephone, adresse, commentaire, latitude, longitude);
			
			if (messageCreerUserDAO.isReponse()){
				
				request.setAttribute("messageAlert", messageServeurFireBase.getMessage());
				request.setAttribute("typeMessage", AlertJsp.Sucess);
				request.getRequestDispatcher("auth/login.jsp").forward(request, response);
				return;
			}
			else
			{
			
				redirige( pwd, pwd1, email, pseudo, siret, telephone, adresse, commentaire, latitude, longitude,
						request,response, messageCreerUserDAO.getMessage());
					
				return;	
			
				
			}

		}
		else
		{
		
			redirige( pwd, pwd1, email, pseudo, siret, telephone, adresse, commentaire, latitude, longitude,
					request,response, messageServeurFireBase.getMessage());
				
			return;	
				
			
		}	
		
		
	}



	private void redirige(String pwd, String pwd1,
			String email, String pseudo, String siret, String telephone,
			String adresse, String commentaire, double latitude,
			double longitude,HttpServletRequest request,HttpServletResponse response,String message){
		request.setAttribute("alerte", new MessageAlertDialog("Message Information",message,null,AlertJsp.warning) );
		request.setAttribute("pwd",pwd);
		request.setAttribute("pwd1",pwd1);
		request.setAttribute("email",email);
		request.setAttribute("pseudo",pseudo);
		request.setAttribute("siret",siret);
		request.setAttribute("telephone",telephone);
		request.setAttribute("adresse",adresse);
		request.setAttribute("commentaire",commentaire);
		request.setAttribute("latitude",latitude);
		request.setAttribute("longitude",longitude);
		try {
			request.getRequestDispatcher("auth/CreationTotalCompteCaptcha.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}
	

	private MessageServeur creerUtilisateurFireBase(String email, String pwd,String pseudo) {
		// TODO Auto-generated method stub
		
//		if (true)
//			
//			return new MessageServeur(true,"ok");
//		
//		
		if (FirebaseApp.getApps().isEmpty())
			FirebaseApp.initializeApp(WBservices.optionFireBase);

		CreateRequest nouveauUser = new CreateRequest()
				.setEmail(email).setEmailVerified(false)
				.setPassword(pwd)
				.setDisabled(false)
				.setDisplayName(pseudo);
				

		try {
			
			UserRecord userRecord = FirebaseAuth.getInstance()
					.createUserAsync(nouveauUser).get();
				
			return new MessageServeur(true, userRecord.getUid());

		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			
			
		//	e.printStackTrace();
		
			String erreur="Erreur inconnue";
			System.out.println(e.getMessage());
		
			if (e.getMessage().toString().contains("EMAIL_EXISTS"))
				erreur="Mail existe d�ja";
			 
			return new MessageServeur(false, erreur);
			
		}
		
	}
	
	private MessageServeur creerUtilisateurDAO(String uuid,String pwd, String pwd1,
			String email, String pseudo, String siret, String telephone,
			String adresse, String commentaire, double latitude,
			double longitude) {
		// TODO Auto-generated method stub
	
		

			Connection connexion = null;

			try {
				connexion = CxoPool.getConnection();
				connexion.setAutoCommit(false);
				wayde.dao.PersonneDAO personnedao = new wayde.dao.PersonneDAO(
						connexion);
				personnedao.addComptePro(uuid, pseudo, email, adresse, siret, telephone, latitude, longitude);
				connexion.commit();
				return new MessageServeur(true,"ok");
		
			
			} catch (SQLException | NamingException e) {
				// TODO Auto-generated catch block
				try {
					connexion.rollback();
					return new MessageServeur(false,e.getMessage());
					
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

			return new MessageServeur(false,"Erreur pendant la créatio en base de donnée");
		

		
	}
	
private MessageServeur testParametreRequete(String pwd, String pwd1,
		String email, String pseudo, String siret, String telephone,
		String adresse, String commentaire, double latitude,
		double longitude) {
	// TODO Auto-generated method stub

	
	if (pwd==null)return new MessageServeur(false,"Mot de passe ne peut pas être vide");
	if (pwd.length()<6)return new MessageServeur(false,"Le mot de passe doit faire 6 caractéres");
	
	if (pwd1==null)return new MessageServeur(false,"Le mot de passe de confirmation n'est pas valide");
	
	
	if (!pwd.equals(pwd1)){
		return new MessageServeur(false,"Le mot de passe de confirmation n'est pas valide");			
	}
	
//	if (commentaire==null)return new MessageServeur(false,"ororororrok");
//	if (commentaire.isEmpty())return new MessageServeur(false,"ororororrok");
//	
	return new MessageServeur(true,"ok");
	
}

	private boolean isCaptcha(String reponseCaptcha) throws Exception {

//	if (true)
//			return true;
	
	
	String url = "https://www.google.com/recaptcha/api/siteverify";

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);

		// add header

		List<BasicNameValuePair> urlParameters = new ArrayList<BasicNameValuePair>();

		urlParameters.add(new BasicNameValuePair("secret",
				"6Ld6TzgUAAAAAFZnSygMYDyAM83ZuReVIT7O068z"));
		urlParameters.add(new BasicNameValuePair("response", reponseCaptcha));

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		HttpResponse response = client.execute(post);
		System.out.println("\nSending 'POST' request to URL : " + url);
		// System.out.println("Post parameters : " + post.getEntity());
		System.out.println("Response Code : "
				+ response.getStatusLine().getStatusCode());

		// System.out.println(oj.toString());

		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);

		}
		
		if (result.toString().contains("\"success\": true"))
			return true;
	return false;
	
		
	}

}
