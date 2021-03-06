package website.metier;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import texthtml.pro.Erreur_HTML;
import website.dao.PersonneDAO;
import website.html.MessageAlertDialog;

public class AuthentificationSite {
	private static final Logger LOG = Logger.getLogger(AuthentificationSite.class);
	   
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ProfilBean profil;
	private MessageAlertDialog messageAlertDialog;

	public AuthentificationSite(HttpServletRequest request,
			HttpServletResponse response) {

		super();
		this.request = request;
		this.response = response;
		HttpSession session = request.getSession();
		profil = (ProfilBean) session.getAttribute("profil");

	}

	public void setAlertMessageDialog(MessageAlertDialog messageAlertDialog) {
		profil.setMessageAlertDialog(messageAlertDialog);

	}

	public MessageAlertDialog getMessageAlertDialog() {
		return profil.getMessageAlertDialog();
	}

	public void setMessageAlertDialog(MessageAlertDialog messageAlertDialog) {
		this.messageAlertDialog = messageAlertDialog;
	}

	public ProfilBean getProfil() {
		return profil;
	}

	public boolean isPro() {

		return profil.isPro();
	}

	public boolean isWaydeur() {

		return profil.isWaydeur();
	}

	public void setProfil(ProfilBean profil) {
		this.profil = profil;
	}

	public boolean isSessionConnect() {

		if (profil == null)
			return false;
		return true;

	}

	public boolean isAuthentifieWaydeur() throws IOException {

		if (profil == null) {
			response.sendRedirect("auth/login.jsp");
			return false;
		}

		if (profil.getTypeuser() != ProfilBean.WAYDEUR
				|| profil.isPremiereconnexion()) {
			response.sendRedirect("auth/login.jsp");
			return false;

		}

		return true;
	}

	public boolean isAuthentifiePro() throws IOException, ServletException {

		HttpSession session = request.getSession();

		ProfilBean profil = (ProfilBean) session.getAttribute("profil");

		if (profil == null) {
			response.sendRedirect("auth/login.jsp");
			return false;
		}

		if (!PersonneDAO.isProfilActif(profil.getId())){
		request.setAttribute("message", Erreur_HTML.ERR_COMPTE_DESACTIVTE);	
		
		request.getRequestDispatcher("pro/messageInfo.jsp").forward(request, response);
		
		return false;	
			
		}
		
		if (!profil.isActif()){
			response.sendRedirect("auth/login.jsp");
		return false;
		}
		// if (profil.getTypeuser() != ProfilBean.PRO
		// || profil.isPremiereconnexion()) {
		// response.sendRedirect("auth/login.jsp");
		// return false;
		// }

		if (profil.getTypeuser() != ProfilBean.PRO) {
			response.sendRedirect("auth/login.jsp");
			return false;
		}

		return true;

	}

	public boolean isAuthentifieAdmin() throws IOException {

		HttpSession session = request.getSession();

		ProfilBean profil = (ProfilBean) session.getAttribute("profil");

		if (profil == null) {
			response.sendRedirect("auth/login.jsp");
			return false;
		}

		if (!profil.isAdmin()) {
			response.sendRedirect("auth/login.jsp");
			return false;
		}

		return true;

	}

	public boolean isAuthentifie() throws IOException {

		if (profil == null) {
			response.sendRedirect("auth/login.jsp");
			return false;
		}

		return true;

	}

	public FiltreRecherche getFiltre() {

		return profil.getFiltreRecherche();
	}

	public int getId() {
		
		return profil.getId();
	}

	public String getNbrMessageNonLu() {

		return new Integer(10).toString();
	}

}
