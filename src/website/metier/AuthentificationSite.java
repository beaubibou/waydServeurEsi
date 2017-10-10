package website.metier;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthentificationSite {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private ProfilBean profil;

	public AuthentificationSite(HttpServletRequest request,
			HttpServletResponse response) {

		super();
		this.request = request;
		this.response = response;
		HttpSession session = request.getSession();
		profil = (ProfilBean) session.getAttribute("profil");

	}

	public ProfilBean getProfil() {
		return profil;
	}

	public boolean isPro(){
		
		return profil.isPro();
	}
	
public boolean isWaydeur(){
		
		return profil.isWaydeur();
	}
	public void setProfil(ProfilBean profil) {
		this.profil = profil;
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

	public boolean isAuthentifiePro() throws IOException {

		HttpSession session = request.getSession();

		ProfilBean profil = (ProfilBean) session.getAttribute("profil");

		if (profil == null) {
			response.sendRedirect("auth/login.jsp");
			return false;
		}

		if (profil.getTypeuser() != ProfilBean.PRO
				|| profil.isPremiereconnexion()) {
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
	public FiltreRecherche getFiltre(){
		
		return profil.getFiltreRecherche();
	}

}
