package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import website.dao.ActiviteDAO;
import website.dao.PersonneDAO;
import website.metier.AuthentificationSite;
import website.metier.admin.FitreAdminProfils;
import website.pager.PagerProfilBean;

/**
 * Servlet implementation class ListProfil
 */
public class ListProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ListProfil.class);
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListProfil() {
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
		
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		if (!authentification.isAuthentifieAdmin())
			return;

		HttpSession session = request.getSession();

		FitreAdminProfils filtreProfil = (FitreAdminProfils) session
				.getAttribute("filtreProfil");

		metAjourFiltre(request, response, filtreProfil);
		
		String action=request.getParameter("action");
		
		doAction(action,request);

		int page = 0;

		if (request.getParameter("page") != null)
			page = Integer.parseInt(request.getParameter("page"));

		
		PagerProfilBean pager = new PagerProfilBean(filtreProfil, page);

		request.setAttribute("pager", pager);

		request.getRequestDispatcher("admin/listProfil.jsp").forward(request,
				response);

		return;

	}

	private void doAction(String action,HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		int idPersonne = 0;
	
		if (action==null)return;
		
		switch (action){
		
		case "desactive":
			
		
			if (request.getParameter("idPersonne") != null) {
				idPersonne = Integer.parseInt(request.getParameter("idPersonne"));
			}

			 PersonneDAO.activerProfilEtActivite(idPersonne, false);
			
			 
			break;	
		
			case "active":
			
		
			if (request.getParameter("idPersonne") != null) {
				idPersonne = Integer.parseInt(request.getParameter("idPersonne"));
			}

			PersonneDAO.activerProfilEtActivite(idPersonne, true);
	
			break;
			
			case "validecompte":
				
				if (request.getParameter("idPersonne") != null) {
					idPersonne = Integer.parseInt(request.getParameter("idPersonne"));
				}

				PersonneDAO.valideCompte(idPersonne, true);
				
				
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

	}

	private void metAjourFiltre(HttpServletRequest request,
			HttpServletResponse response, FitreAdminProfils filtre) {
		// TODO Auto-generated method stub

		if (request.getParameter("typeUser") != null) {
			int typeUser = Integer.parseInt(request.getParameter("typeUser"));
			filtre.setTypeUser(typeUser);
		}
		
		
		
		
		if (request.getParameter("etatProfil") != null) {
			int etatUser = Integer.parseInt(request.getParameter("etatProfil"));
			filtre.setEtatProfil(etatUser);
		}
		
		
		if (request.getParameter("etatProfilValide") != null) {
			int etatProfilValide = Integer.parseInt(request.getParameter("etatProfilValide"));
			filtre.setEtatValide(etatProfilValide);
		}
	
		
		
		
		if (request.getParameter("pseudo") != null) {
			filtre.setPseudo(request.getParameter("pseudo"));
		}
		
		if (request.getParameter("email") != null) {
			filtre.setEmail(request.getParameter("email"));
		}
	
		if (request.getParameter("typeSignalement") != null) {
			int typeSignalement = Integer.parseInt(request
					.getParameter("typeSignalement"));
			filtre.setTypeSignalement(typeSignalement);
		}


	}
}
