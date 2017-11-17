package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import wayd.ws.WBservices;
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
	private static final Logger LOG = Logger.getLogger(DetailParticipant.class);

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

		System.out.println("cocouououo");

		if (request.getParameter("actif") != null) {// active le profil
			int idParticipant = Integer.parseInt(request
					.getParameter("idparticipant"));

			PersonneDAO.activerProfil(idParticipant, true);

		}

		if (request.getParameter("inactif") != null) {// desactive le profil

			int idParticipant = Integer.parseInt(request
					.getParameter("idparticipant"));
			PersonneDAO.activerProfil(idParticipant, false);

		}

		afficheParticipant(request, response);

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

	private void afficheParticipant(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		// l'appel de la servelt peut venir d'une servlet ou d'une page

		int idParticipant = 0;
		if (request.getParameter("idparticipant") != null)
			idParticipant = Integer.parseInt((String) request
					.getParameter("idparticipant"));

		if (request.getAttribute("idparticipant") != null)
			idParticipant = Integer.parseInt((String) request
					.getAttribute("idparticipant"));

		// Creation de l'objet profil complet ami+activit�

		ProfilBean profilBean = PersonneDAO.getFullProfil(idParticipant);
		ArrayList<AmiBean> listAmi = AmiDAO.getListAmi(idParticipant);
		ArrayList<ActiviteBean> listActivite = ActiviteDAO
				.getListActivite(idParticipant);
		ArrayList<SignalementBean> listSignalement = SignalementDAO
				.getListSignalement(idParticipant);

		profilBean.setListAmi(listAmi);
		profilBean.setListActivite(listActivite);
		profilBean.setListSignalement(listSignalement);

		// Tri des activit�s par dates

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
		request.getRequestDispatcher("admin/detailparticipant.jsp").forward(
				request, response);

	}

}
