
package servlet.commun;

import gcmnotification.AcquitAllNotificationGcm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

import wayd.ws.WBservices;
import wayde.bean.MessageServeur;
import website.dao.MessageDAO;
import website.enumeration.AlertJsp;
import website.html.AlertInfoJsp;

import com.google.firebase.FirebaseApp;

/**
 * Servlet implementation class ContactMessageCaptcha
 */
public class ContactMessageCaptcha extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ContactMessageCaptcha.class);
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ContactMessageCaptcha() {
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
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		if (FirebaseApp.getApps().isEmpty())
			FirebaseApp.initializeApp(WBservices.optionFireBase);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String message = request.getParameter("message");
		String email = request.getParameter("email");
		String pseudo = request.getParameter("pseudo");
		String reponseCaptcha = request.getParameter("g-recaptcha-response");

		try {
			
			MessageServeur messageServeur = testRequete(message, email, pseudo,
					reponseCaptcha);
		
			if (messageServeur.isReponse()){
				
				if (MessageDAO.ajouteMessageProbleme(message, email, pseudo)){
				AlertInfoJsp alert=new AlertInfoJsp("Message Envoyé", AlertJsp.Sucess, "commun/acceuil.html");
				request.setAttribute("alerte", alert);
				request.getRequestDispatcher("/commun/alertNoAuth.jsp")
				.forward(request, response);
				return;
				}
			}
			
				
			
			AlertInfoJsp alert=new AlertInfoJsp(messageServeur.getMessage(), AlertJsp.warning, "commun/acceuil.html");
				request.setAttribute("alerte", alert);
				request.getRequestDispatcher("/commun/alertNoAuth.jsp")
				.forward(request, response);
				return;
			


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
		}

	
	}

	private MessageServeur testRequete(String message, String email,
			String pseudo, String reponseCaptcha) throws Exception {
		// TODO Auto-generated method stub

		
		if (message == null)
			return new MessageServeur(false, "Le message est incomplet");

		message = message.trim();

		if (message.isEmpty())
			return new MessageServeur(false, "Le message est incomplet");

		if (email == null)
			return new MessageServeur(false, "Le mail est incomplet");

		email = email.trim();

		
		if (email.isEmpty())
			return new MessageServeur(false, "Le mail est incomplet");

		if (isCaptcha(reponseCaptcha)) {

			return new MessageServeur(true, "Le mail est incomplet");
		} 
		else 
		{
			return new MessageServeur(false, "Cochez je ne suis pas un robot");

		}


	}

	private boolean isCaptcha(String reponseCaptcha) throws Exception {

		 String url = "https://www.google.com/recaptcha/api/siteverify";
		
		 HttpClient client = new DefaultHttpClient();
		 HttpPost post = new HttpPost(url);
		
		 // add header
		
		 List<BasicNameValuePair> urlParameters = new
		 ArrayList<BasicNameValuePair>();
		
		 urlParameters.add(new BasicNameValuePair("secret",
		 "6Ld6TzgUAAAAAFZnSygMYDyAM83ZuReVIT7O068z"));
		 urlParameters.add(new BasicNameValuePair("response",
		 reponseCaptcha));
		
		 post.setEntity(new UrlEncodedFormEntity(urlParameters));
		
		 HttpResponse response = client.execute(post);
			
		
		
		 BufferedReader rd = new BufferedReader(new InputStreamReader(response
		 .getEntity().getContent()));
		
		 StringBuffer result = new StringBuffer();
		 String line = "";
		 while ((line = rd.readLine()) != null) {
		 result.append(line);
		
		 }
		
		 if (result.toString().contains("\"success\": true")){
			 LOG.debug("Captch OK");
			 return true;
		 }
	
		 LOG.debug("Captch NOK");
		 return false;

	
	}

}
