package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import website.dao.ActiviteDAO;
import website.dao.AmiDAO;
import website.dao.PersonneDAO;
import website.dao.SignalementDAO;
import website.metier.ActiviteBean;
import website.metier.AmiBean;
import website.metier.ProfilBean;
import website.metier.SignalementBean;

/**
 * Servlet implementation class DetailParticipant
 */
public class DetailParticipant extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DetailParticipant() {
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
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();

		if (session.getAttribute("profil") != null ) {

			if (request.getParameter("actif") != null) {// active le profil
				int idParticipant = Integer.parseInt(request
						.getParameter("idparticipant"));
				System.out.println("Activite" + idParticipant);

				PersonneDAO.activerProfil(idParticipant, true);
				afficheParticipant(request, response);
				
			}

			if (request.getParameter("inactif") != null) {// desactive le profil
			
				int idParticipant = Integer.parseInt(request
						.getParameter("idparticipant"));
				System.out.println("Desactivite" + idParticipant);

				PersonneDAO.activerProfil(idParticipant, false);
				afficheParticipant(request, response);
			
			}

			if (request.getParameter("inactif") == null
					&& request.getParameter("actif") == null
					&& request.getParameter("idparticipant") != null) {// Cas
																		// nominal

				afficheParticipant(request, response);

			}

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	private void afficheParticipant(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession session = request.getSession();
		if (session.getAttribute("profil") != null )
		{
		
		int idParticipant = Integer.parseInt(request
				.getParameter("idparticipant"));
		
		// Creation de l'objet profil complet ami+activité

		ProfilBean profilBean = PersonneDAO.getFullProfil(idParticipant);
		ArrayList<AmiBean> listAmi = AmiDAO.getListAmi(idParticipant);
		ArrayList<ActiviteBean> listActivite = ActiviteDAO
				.getListActivite(idParticipant);
		ArrayList<SignalementBean> listSignalement = SignalementDAO.getListSignalement(idParticipant);
		System.out.println("listetettet"+listSignalement.size());		
		
		profilBean.setListAmi(listAmi);
		profilBean.setListActivite(listActivite);
		profilBean.setListSignalement(listSignalement);

		// Tri des activités par dates

		java.util.Collections.sort(listActivite,
				new Comparator<ActiviteBean>() {

					@Override
					public int compare(ActiviteBean o1, ActiviteBean o2) {
						// TODO Auto-generated method stub
						return o2.getDatedebut().compareTo(o1.getDatefin());
					}
				});

		// Direction vers la page
		request.setAttribute("profil", profilBean);
		request.getRequestDispatcher("admin/detailparticipant.jsp").forward(request,
					response);
		

	}
		else{
			
			response.sendRedirect("auth/login.jsp");
		}
		
	}
	
	

}
