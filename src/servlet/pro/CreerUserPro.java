package servlet.pro;

import gcmnotification.AcquitAllNotificationGcm;

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

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import texthtml.pro.Erreur_HTML;
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

/**
 * Servlet implementation class CreerUserPro
 */
public class CreerUserPro extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(CreerUserPro.class);
	
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
	
		response.sendRedirect("auth/CreationComptePro.jsp");
	
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
		String siteweb=request.getParameter("siteweb");
	
		double latitude = 0;
		double longitude = 0;
		
	
		if (request.getParameter("latitude") != null)
		
		 latitude = Double.parseDouble(request.getParameter("latitude"));

		if (request.getParameter("longitude") != null)
			 longitude =Double.parseDouble(request.getParameter("longitude"));
 
		//*******************************************************
	
		MessageServeur messageServeurTestRequete=testParametreRequete(pwd,pwd1,email,pseudo,siret,
				telephone,adresse,commentaire,latitude,longitude);
			
		if (!messageServeurTestRequete.isReponse()){
					
			redirige( pwd, pwd1, email, pseudo, siret, telephone, adresse, commentaire, latitude, longitude,
					request,response, messageServeurTestRequete.getMessage());
				
			return;	
		}
		
		
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
			LOG.debug( ExceptionUtils.getStackTrace(e));
		
			redirige( pwd, pwd1, email, pseudo, siret, telephone, adresse, commentaire, latitude, longitude,
					request,response, "Le captcha n'est pas valide");
				
			return;	
		}
		
			
		
			
		MessageServeur messageServeurFireBase=	creerUtilisateurFireBase(email,pwd,pseudo);
		
		if (messageServeurFireBase.isReponse())
		{
			String uuid=messageServeurFireBase.getMessage();
			MessageServeur messageCreerUserDAO=creerUtilisateurDAO(uuid, pwd, pwd1, email, pseudo, siret, telephone, adresse, commentaire,siteweb, latitude, longitude);
			
			if (messageCreerUserDAO.isReponse()){
				

				
				envoiMailConfirmation(request,response, email, pwd);
				
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



	private void envoiMailConfirmation(HttpServletRequest request,
			HttpServletResponse response,String email,String pwd) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("email", email);
		request.setAttribute("pwd", pwd);
		request.getRequestDispatcher("auth/sendEmail.jsp").forward(request, response);
//
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
			request.getRequestDispatcher("auth/CreationComptePro.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
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
			
			
			 String s = ExceptionUtils.getStackTrace(e);
			String erreur="Erreur inconnue";
		
			if (s.contains("EMAIL_EXISTS"))
				erreur=Erreur_HTML.MAIL_EXISTE_DEJA;
			 
			return new MessageServeur(false, erreur);
			
		}
		
	}
	
	private MessageServeur creerUtilisateurDAO(String uuid,String pwd, String pwd1,
			String email, String pseudo, String siret, String telephone,
			String adresse, String commentaire,String siteweb, double latitude,
			double longitude) {
		// TODO Auto-generated method stub
	
		

			Connection connexion = null;

			try {
				connexion = CxoPool.getConnection();
				connexion.setAutoCommit(false);
				wayde.dao.PersonneDAO personnedao = new wayde.dao.PersonneDAO(
						connexion);
				personnedao.addComptePro(uuid, pseudo, email, adresse, siret, telephone,commentaire,siteweb, latitude, longitude);
				connexion.commit();
				return new MessageServeur(true,Erreur_HTML.CREATION_COMPTE);
		
			
			} catch (SQLException | NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LOG.error( ExceptionUtils.getStackTrace(e));
				CxoPool.rollBack(connexion);
				return new MessageServeur(false,e.getMessage());
						
			} // ...
			finally {
				CxoPool.closeConnection(connexion);
				
				//
			}

		
	}
	
private MessageServeur testParametreRequete(String pwd, String pwd1,
		String email, String pseudo, String siret, String telephone,
		String adresse, String commentaire, double latitude,
		double longitude) {
	// TODO Auto-generated method stub

	LOG.info("Test par "+pseudo);
	
	
	
	if (PersonneDAO.isPseudoExist(pseudo.trim()))
		return new MessageServeur(false,Erreur_HTML.PSEUDO_EXISTE);
	
	if (PersonneDAO.isSiretExist(siret))
		return new MessageServeur(false,Erreur_HTML.SIRET_EXISTE);
	
	
	if (PersonneDAO.isTelephoneExist(telephone))
		return new MessageServeur(false,Erreur_HTML.TELEPHONNE_EXIST_DEJA);
	
	if (pwd==null)return new MessageServeur(false,Erreur_HTML.MOT_DE_PASSE_VIDE);
	if (pwd.length()<6)return new MessageServeur(false,Erreur_HTML.MOT_DE_PASSE_6_CARACTERE);
	
	if (pwd1==null)return new MessageServeur(false,Erreur_HTML.MOT_DE_PASSE_BIS_NOK);
	
	
	if (!pwd.equals(pwd1)){
		return new MessageServeur(false,Erreur_HTML.MOT_DE_PASSE_BIS_NOK);			
	}
	
	
	return new MessageServeur(true,"ok");
	
}

	private boolean isCaptcha(String reponseCaptcha) throws Exception {


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
	
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);

		}
		
		if (result.toString().contains("\"success\": true"))
			{
			LOG.info("Captcha Ok");
			return true;
			}
			
		LOG.info("Captcha NOk");
		return false;
	
		
	}

}
