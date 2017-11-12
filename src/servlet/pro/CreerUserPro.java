package servlet.pro;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wayd.ws.WBservices;
import website.dao.CacheValueDAO;
import website.enumeration.TypePhoto;
import website.metier.Outils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.auth.UserRecord.UpdateRequest;

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
		System.out.println(request.getParameter("pwd"));
		System.out.println("***********test creation");
		if (FirebaseApp.getApps().isEmpty()) 
			FirebaseApp.initializeApp(WBservices.optionFireBase);
		
		CreateRequest nouveauUser = new CreateRequest()
				.setEmail("user@wayd.fr").setEmailVerified(false)
				.setPassword("secretPassword").setPhoneNumber("+11234567890")
				.setDisplayName("John Doe")
				.setPhotoUrl("http://www.example.com/12345678/photo.png")
				.setDisabled(false);
		
		   try {
			UserRecord userRecord = FirebaseAuth.getInstance().createUserAsync(nouveauUser).get();
			 
		   } catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
		}
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		System.out.println(request.getParameter("pwd"));
		System.out.println("captcha= " + request.getParameter("g-recaptcha-response"));
		
	}

}
