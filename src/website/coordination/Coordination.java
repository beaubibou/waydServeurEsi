package website.coordination;

import gcmnotification.AddParticipationGcm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import threadpool.PoolThreadGCM;
import wayd.ws.TextWebService;
import wayd.ws.WBservices;
import wayde.bean.CxoPool;
import wayde.bean.LibelleMessage;
import wayde.bean.Message;
import wayde.bean.MessageServeur;
import wayde.bean.Participation;
import wayde.bean.Personne;
import wayde.dao.MessageDAO;
import wayde.dao.ParticipationDAO;
import wayde.dao.PersonneDAO;
import website.dao.ActiviteDAO;
import website.dao.MessageBean;
import website.dao.ParticipantDAO;
import website.metier.ActiviteBean;
import website.metier.ParticipantBean;

public class Coordination {
	private static final Logger LOG = Logger.getLogger(Coordination.class);

	public MessageServeur addParticipation(int iddemandeur, int idorganisateur,
			int idactivite) {
	
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		try {

			if (iddemandeur == idorganisateur)
				return new MessageServeur(false,
						LibelleMessage.infoParticpationActivite);

			connexion = CxoPool.getConnection();

			//Limite le nombre de participation a 2
//			ParticipationDAO participationDAO=new ParticipationDAO(connexion);
//			if (participationDAO.getNbrParticipation(iddemandeur)==2)
//			{
//				LOG.info("activite pleine");
//				return  new MessageServeur(false, LibelleMessage.activiteFinie);
//			}
//			
//				
			ActiviteDAO activiteDAO=new  ActiviteDAO(connexion);
			ActiviteBean activite = activiteDAO.getActivite(idactivite);
				
			
			
			if (activite == null)
				return new MessageServeur(false, LibelleMessage.activiteFinie);

			if (activite.isTerminee())
				return new MessageServeur(false, TextWebService.ACTIVITE_TERMINEE);

			if (activite.isComplete())
				return new MessageServeur(false,
						LibelleMessage.activiteComplete);

			if (activite.isInscrit( iddemandeur)) {
				return new MessageServeur(false,
						LibelleMessage.activiteDejaInscrit);
			}

			connexion.setAutoCommit(false);
			Participation participation = new Participation(iddemandeur,
					idorganisateur, idactivite);
			ParticipationDAO participationdao = new ParticipationDAO(connexion);
			participationdao.addParticipation(participation);
			activiteDAO.updateChampCalcule(idactivite);
			participationdao.addNotation(participation);
			participationdao.addDemandeAmi(participation);
			MessageDAO messagedao = new MessageDAO(connexion);
			ArrayList<Personne> listparticipant = participationdao
					.getListPartipantActivite(idactivite);
			Personne personne = new PersonneDAO(connexion)
					.getPersonneId(iddemandeur);

			Message message = new Message(iddemandeur, personne.getPrenom()
					+ " participe", idactivite, 0);

			messagedao.addMessageByAct(message, listparticipant);

			connexion.commit();

			// new AddParticipationGcm(listparticipant, idactivite).start();

			PoolThreadGCM.poolThread.execute(new AddParticipationGcm(					listparticipant, idactivite));

			String loginfo = "addParticipation - "
					+ (System.currentTimeMillis() - debut) + "ms";
			LOG.info(loginfo);

			return new MessageServeur(true, LibelleMessage.activiteInscription);
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				connexion.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return new MessageServeur(false,
					"ERREUR SURVENUE DANS METHODE addparticipation");

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}
	
	public ActiviteBean getActivite(int idActivite){
		
		Connection connexion =null;
		try {
			 connexion = CxoPool.getConnection();
		
			return new ActiviteDAO(connexion).getActivite(idActivite);
		
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return null;

		} finally {

			try {
				connexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public  MessageBean effaceActivite(int idActivite) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
	

		try {

			connexion = CxoPool.getConnection();

			ActiviteBean activite =  new ActiviteDAO(connexion).getActivite(idActivite);

			if (!activite.isActive()) {

				return new MessageBean(TextWebService.ACTIVITE_DESACTIVEE);
			}
			
			
			
			String requete = "DELETE FROM demandeami where ( idactivite=? );";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idActivite);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM public.participer where ( idactivite=? );";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idActivite);

			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM public.noter where ( idactivite=? );";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idActivite);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM public.activite where ( idactivite=? );";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idActivite);
			preparedStatement.execute();
			preparedStatement.close();

			return new MessageBean("Ok");

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new MessageBean(TextWebService.ERREUR_INCONNUE);
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}

	}
}
