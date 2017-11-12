package servlet.pro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
import wayde.bean.MessageServeur;
import website.enumeration.AlertJsp;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;

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
		// TODO Auto-generated method stub
		System.out.println("doget crres user");
		response.sendRedirect("auth/CreationCompteCaptcha.jsp");
		
//		System.out.println(request.getParameter("pwd"));
//		System.out.println("***********test creation");
//		
	}
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String pwd=request.getParameter("pwd");
		String email=request.getParameter("email");
			
		
		String reponseCaptcha = request.getParameter("g-recaptcha-response");
		try {
		if (isCaptcha(reponseCaptcha)){
			
		MessageServeur messageServeur=	creerUtilisateur(email,pwd);
	
		if (messageServeur.isReponse())
		{
			request.setAttribute("messageAlert", messageServeur.getMessage());
			request.setAttribute("typeMessage", AlertJsp.Sucess);
			request.getRequestDispatcher("auth/login.jsp").forward(request, response);
		}
		else
		{
			request.setAttribute("messageAlert", messageServeur.getMessage());
			request.setAttribute("typeMessage", AlertJsp.warning);
			request.getRequestDispatcher("auth/login.jsp").forward(request, response);
		}
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private MessageServeur creerUtilisateur(String email, String pwd) {
		// TODO Auto-generated method stub
		if (FirebaseApp.getApps().isEmpty())
			FirebaseApp.initializeApp(WBservices.optionFireBase);

		CreateRequest nouveauUser = new CreateRequest()
				.setEmail(email).setEmailVerified(false)
				.setPassword(pwd)
				.setDisabled(false);
			//	.setPhoneNumber("+11234567890")
				//.setDisplayName("John Doe")
			//	.setPhotoUrl("http://www.example.com/12345678/photo.png")
			//	.setDisabled(false);
				

		try {
			UserRecord userRecord = FirebaseAuth.getInstance()
					.createUserAsync(nouveauUser).get();
			
			return new MessageServeur(true, "Vous allez recevoir un mail");

		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			
			
		//	e.printStackTrace();
			
			String erreur="Erreur inconnue";
			System.out.println(e.getMessage());
			if (e.getMessage().toString().contains("EMAIL_EXISTS"))
			 erreur="Mail existe déja";
			 
			return new MessageServeur(false, erreur);
			
		}
		
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
	//Return false
		return true;
	}

}
