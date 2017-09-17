package servlet.pro;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import website.metier.Outils;
import website.metier.ProfilBean;

/**
 * Servlet implementation class AjouteActivite
 */
public class AjouteActivite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjouteActivite() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		ProfilBean profil = (ProfilBean) session.getAttribute("profil");

		if (profil == null) {
			response.sendRedirect("auth/login.jsp");
			return;
		}

		if (profil.getTypeuser() != ProfilBean.PRO
				|| profil.isPremiereconnexion()) {
			response.sendRedirect("auth/login.jsp");
			return;
		}

			
		
		response.sendRedirect("pro/form_creationactivite.jsp");
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.print("ajoute actuvute");
		
		HttpSession session = request.getSession();
		ProfilBean profil = (ProfilBean) session.getAttribute("profil");

		String titre = request.getParameter("titre");
		String adresse = request.getParameter("adresse");
		String description = request.getParameter("description");
		
		
		double latitude = Double.parseDouble(request.getParameter("latitude"));
		double longitude = Double
				.parseDouble(request.getParameter("longitude"));
		int typeaccess = Integer.parseInt(request.getParameter("typeaccess"));
		int typeactivite = Integer.parseInt(request.getParameter("typeactivite"));
		
		String datedebut=request.getParameter("debut");
		String datefin=request.getParameter("fin");
	
		Date dateDebut=null;
		Date dateFin=null;
		
		try {
			dateDebut=Outils.getDateFromString(datedebut);
			dateFin=Outils.getDateFromString(datefin);
		
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
			
		if (profil != null) {
			website.dao.ActiviteDAO activiteDAO = new website.dao.ActiviteDAO();

			activiteDAO.addActivitePro(profil.getId(), titre, description, dateDebut, dateFin, adresse, latitude, 
					longitude, typeactivite, ProfilBean.PRO, typeaccess);
			
			response.sendRedirect("AcceuilPro");
			

		}

	}
	
	}


