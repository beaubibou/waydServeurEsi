package wayd.ws;

import fcm.ServeurMethodes;
import gcmnotification.AcquitAllNotificationGcm;
import gcmnotification.AcquitMessageByActGcm;
import gcmnotification.AcquitMessageDiscussionByActGcm;
import gcmnotification.AcquitMessageDiscussionGcm;
import gcmnotification.AcquitMessageGcm;
import gcmnotification.AcquitNotificationGcm;
import gcmnotification.AddActiviteGcm;
import gcmnotification.AddAvisGcm;
import gcmnotification.AddMessageByActGcm;
import gcmnotification.AddMessageGcm;
import gcmnotification.AddParticipationGcm;
import gcmnotification.EffaceActiviteGcm;
import gcmnotification.EffaceAmiGcm;
import gcmnotification.EffaceDiscussionGcm;
import gcmnotification.EffaceMessageEmisByActGcm;
import gcmnotification.EffaceMessageEmisGcm;
import gcmnotification.EffaceMessageGcm;
import gcmnotification.EffaceMessageRecuByActGcm;
import gcmnotification.EffaceMessageRecuGcm;
import gcmnotification.EffaceNotificationRecuGcm;
import gcmnotification.EffaceParticipationGcm;
import gcmnotification.UpdateActiviteGcm;
import gcmnotification.UpdateNotificationGcm;
import gcmnotification.UpdatePositionGcm;
import gcmnotification.UpdatePreferenceGcm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import javax.naming.NamingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import reponsevaleur.Erreur;
import reponsevaleur.ListActivitesRV;
import servlet.ListActivite;
import threadpool.PoolThreadGCM;
import wayde.bean.Activite;
import wayde.bean.Ami;
import wayde.bean.Avis;
import wayde.bean.CxoPool;
import wayde.bean.Discussion;
import wayde.bean.Droit;
import wayde.bean.IndicateurWayd;
import wayde.bean.Message;
import wayde.bean.MessageServeur;
import wayde.bean.Notification;
import wayde.bean.Participant;
import wayde.bean.Participation;
import wayde.bean.Personne;
import wayde.bean.PhotoActivite;
import wayde.bean.PhotoWaydeur;
import wayde.bean.Preference;
import wayde.bean.Profil;
import wayde.bean.ProfilNotation;
import wayde.bean.ProfilPro;
import wayde.bean.RetourMessage;
import wayde.bean.TableauBord;
import wayde.bean.TypeActivite;
import wayde.bean.Version;
import wayde.beandatabase.TypeActiviteDb;
import wayde.dao.ActiviteDAO;
import wayde.dao.AmiDAO;
import wayde.dao.AvisDAO;
import wayde.dao.DiscussionDAO;
import wayde.dao.MessageDAO;
import wayde.dao.NotificationDAO;
import wayde.dao.ParticipantDAO;
import wayde.dao.ParticipationDAO;
import wayde.dao.PersonneDAO;
import wayde.dao.PreferenceDAO;
import wayde.dao.SignalementDAO;
import wayde.dao.SuggestionDAO;
import wayde.dao.TypeActiviteDAO;
import website.dao.LogDAO;
import website.metier.ProfilBean;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import comparator.DiscussionDateComparator;

public class WBservices {
	public final static int NB_MAX_ACTIVITE = 1;
	public static SimpleDateFormat formatDate = new SimpleDateFormat(
			"dd-MM HH:mm:ss");
	private static final Logger LOG = Logger.getLogger(WBservices.class);
	public static FirebaseOptions optionFireBase;
	public final static String cheminUnixBoulotCle = "/home/devel/perso/cle.json";
	public final static String cheminWindowsCle = "d:/cle.json";
	public final static String cheminProdCle = "/usr/lib/jvm/java-8-openjdk-amd64/jre/cle/cle.json";

	static {
		boolean chargement = false;
		if (optionFireBase == null) {

			try {

				File f = new File(cheminUnixBoulotCle);

				if (f.exists()) {

					FileInputStream serviceAccount = new FileInputStream(
							cheminUnixBoulotCle);

					optionFireBase = new FirebaseOptions.Builder()
							.setCredentials(
									GoogleCredentials
											.fromStream(serviceAccount))
							.setDatabaseUrl("https://wayd-c0414.firebaseio.com")
							.build();
					chargement = true;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LOG.error(ExceptionUtils.getStackTrace(e));
			}
		}

		if (optionFireBase == null) {

			try {
				File f = new File(cheminWindowsCle);
				if (f.exists()) {
					FileInputStream serviceAccount = new FileInputStream(
							cheminWindowsCle);

					optionFireBase = new FirebaseOptions.Builder()
							.setCredentials(
									GoogleCredentials
											.fromStream(serviceAccount))
							.setDatabaseUrl("https://wayd-c0414.firebaseio.com")
							.build();
					chargement = true;

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LOG.error(ExceptionUtils.getStackTrace(e));
			}
		}

		if (optionFireBase == null) {

			try {
				File f = new File(cheminProdCle);
				if (f.exists()) {

					FileInputStream serviceAccount = new FileInputStream(
							cheminProdCle);

					optionFireBase = new FirebaseOptions.Builder()
							.setCredentials(
									GoogleCredentials
											.fromStream(serviceAccount))
							.setDatabaseUrl("https://wayd-c0414.firebaseio.com")
							.build();
					chargement = true;
				}
			} catch (IOException e) {

				e.printStackTrace();
				LOG.error(ExceptionUtils.getStackTrace(e));
			}
		}

		if (chargement == false) {

			LOG.error("Le fichier cle.json n a pas pu etre chargé.");
		}
	}

	public MessageServeur supprimerCompte(int idPersonne, String jeton) {
		long debut = System.currentTimeMillis();

		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);

			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					idPersonne, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			boolean retour = website.dao.PersonneDAO
					.supprimePersonne(idPersonne);

			LogDAO.LOG_DUREE("supprimerCompte", debut);

			if (retour)
				return new MessageServeur(true, "ok");
			else
				return new MessageServeur(false, "Une erreur est survenue");

		} catch (Exception e) {

			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			return null;
		} finally {

			CxoPool.closeConnection(connexion);
		}

	}

	public Avis getAvis(int idnoter, int idactivite, int idnotateur,
			int idpersonnenotee, int idDemandeur, String jeton) {

		// Retour l'avis si idnoter est !=0 on cherche l'avis par son id
		// dans la table noter, sinon par les 3 autres param�tres
		long debut = System.currentTimeMillis();

		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			if (!personneDAO.isAutorise(idDemandeur, jeton))
				return null;

			AvisDAO avisdao = new AvisDAO(connexion);

			Avis avis = null;

			if (idnoter != 0)//
				avis = avisdao.getAvisById(idnoter);

			else

				avis = avisdao.getDetailAvis(idactivite, idnotateur,
						idpersonnenotee);

			LogDAO.LOG_DUREE("getAvis", debut);

			return avis;

		} catch (Exception e) {

			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			return null;
		} finally {

			CxoPool.closeConnection(connexion);
		}
	}

	public MessageServeur updatePreference(int idpersonne, int rayon,
			int idtypeactivite[], Boolean[] active, String jeton) {

		long debut = System.currentTimeMillis();
		Connection connexion = null;
		try {

			connexion = CxoPool.getConnection();
			PreferenceDAO preferencedao = new PreferenceDAO(connexion);

			// SECURITE*************************************

			PersonneDAO personnedao = new PersonneDAO(connexion);
			MessageServeur autorise = personnedao.isAutoriseMessageServeur(
					idpersonne, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			// ******************************************************

			connexion.setAutoCommit(false);
			preferencedao.addPreference(idpersonne, idtypeactivite, active);
			personnedao.updateRayon(idpersonne, rayon);
			connexion.commit();
			// new UpdatePreferenceGcm(idpersonne).start();
			PoolThreadGCM.poolThread
					.execute(new UpdatePreferenceGcm(idpersonne));

		} catch (Exception e) {

			CxoPool.rollBack(connexion);
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));

		} finally {
			CxoPool.closeConnection(connexion);
		}

		LogDAO.LOG_DUREE("updatePreference", debut);

		return new MessageServeur(true, TextWebService.PREFERENCE_SAUVEGARDEES);

	}

	public Ami[] getListAmi(int iddemandeur, int idpersonne, String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		ArrayList<Ami> retour = new ArrayList<Ami>();

		try {
			connexion = CxoPool.getConnection();

			// *****************Securite*****************

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			if (!personneDAO.isAutorise(iddemandeur, jeton))
				return null;

			// ************************************

			AmiDAO amidao = new AmiDAO(connexion);
			retour = amidao.getListAmi(idpersonne);

			LogDAO.LOG_DUREE("getListAmi", debut);

			return (Ami[]) retour.toArray(new Ami[retour.size()]);

		} catch (SQLException | NamingException e) {

			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			return null;
		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public Participant[] getListParticipant(int iddemandeur, int idactivite,
			String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		ArrayList<Participant> retour = new ArrayList<Participant>();

		try {
			connexion = CxoPool.getConnection();
			// *****************Securite*****************

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			if (!personneDAO.isAutorise(iddemandeur, jeton))
				return null;

			// ************************************

			ParticipantDAO participantdao = new ParticipantDAO(connexion);
			retour = participantdao.getListPaticipant(idactivite);

			LogDAO.LOG_DUREE("getListParticipant", debut);

		} catch (SQLException | NamingException e) {

			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
		} finally {
			CxoPool.closeConnection(connexion);
		}

		return (Participant[]) retour.toArray(new Participant[retour.size()]);

	}

	public Preference[] getListPreferences(int iddemandeur, int idpersonne,
			String jeton) {

		Connection connexion = null;
		ArrayList<Preference> retour = new ArrayList<Preference>();
		long debut = System.currentTimeMillis();

		try {
			connexion = CxoPool.getConnection();
			// *****************Securite*****************

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			if (!personneDAO.isAutorise(iddemandeur, jeton))
				return null;

			// ************************************
			PreferenceDAO preferencedao = new PreferenceDAO(connexion);

			retour = preferencedao.getLisPreferences(idpersonne);

		} catch (SQLException | NamingException e) {

			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));

		} finally {
			CxoPool.closeConnection(connexion);
		}

		LogDAO.LOG_DUREE("getListPreferences", debut);

		return (Preference[]) retour.toArray(new Preference[retour.size()]);

	}

	public PhotoWaydeur[] getListPhotoWaydeur(int iddemandeur, String jeton,
			int idpersonne[]) {
		Connection connexion = null;
		long debut = System.currentTimeMillis();
		ArrayList<PhotoWaydeur> listPhotoWaydeur = new ArrayList<PhotoWaydeur>();

		try {

			connexion = CxoPool.getConnection();
			PersonneDAO personneDao = new PersonneDAO(connexion);
			listPhotoWaydeur = personneDao.getListPhotoWaydeur(idpersonne);

		} catch (SQLException | NamingException e) {
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));

		} finally {
			CxoPool.closeConnection(connexion);
		}

		LogDAO.LOG_DUREE("getListPhotoWaydeur", debut);

		return (PhotoWaydeur[]) listPhotoWaydeur
				.toArray(new PhotoWaydeur[listPhotoWaydeur.size()]);

	}

	public PhotoWaydeur[] getListPhotoWaydeurByAct(int iddemandeur,
			int idactivite, String jeton) {

		Connection connexion = null;
		long debut = System.currentTimeMillis();
		ArrayList<PhotoWaydeur> listPhotoWaydeur = new ArrayList<PhotoWaydeur>();

		try {

			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			if (!personneDAO.isAutorise(iddemandeur, jeton))
				return null;

			ParticipantDAO participantDAO = new ParticipantDAO(connexion);
			ArrayList<Participant> listParticipants = participantDAO
					.getListPaticipant(idactivite);

			for (Participant participant : listParticipants) {
				listPhotoWaydeur.add(new PhotoWaydeur(participant.getId(),
						participant.getPhotostr()));
			}

		} catch (SQLException | NamingException e) {

			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));

		} finally {
			CxoPool.closeConnection(connexion);
		}

		LogDAO.LOG_DUREE("getListPhotoWaydeurByAct", debut);

		return (PhotoWaydeur[]) listPhotoWaydeur
				.toArray(new PhotoWaydeur[listPhotoWaydeur.size()]);

	}

	public PhotoWaydeur getPhotoWaydeur(int iddemandeur, String jeton,
			int idpersonne) {
		Connection connexion = null;
		long debut = System.currentTimeMillis();

		try {

			connexion = CxoPool.getConnection();
			PersonneDAO personneDao = new PersonneDAO(connexion);

			if (!personneDao.isAutorise(iddemandeur, jeton))
				return null;

			LogDAO.LOG_DUREE("getPhotoWaydeur", debut);

			return personneDao.getPhotoWaydeur(idpersonne);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			return null;

		} finally {

			CxoPool.closeConnection(connexion);
		}

	}

	public Message[] getDiscussion(int iddestinataire, int idemetteur,
			String jeton) {
		Connection connexion = null;
		long debut = System.currentTimeMillis();
		ArrayList<Message> listmessage = new ArrayList<Message>();

		try {

			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			if (!personneDAO.isAutorise(iddestinataire, jeton))
				return null;

			MessageDAO messagedao = new MessageDAO(connexion);
			listmessage = messagedao.getDiscussion(iddestinataire, idemetteur);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));

		} finally {
			CxoPool.closeConnection(connexion);
		}

		LogDAO.LOG_DUREE("getDiscussion", debut);

		return (Message[]) listmessage.toArray(new Message[listmessage.size()]);

	}

	public Message[] getDiscussionByAct(int iddestinataire, int idactivite,
			String jeton) {

		Connection connexion = null;
		long debut = System.currentTimeMillis();
		ArrayList<Message> listmessage = new ArrayList<Message>();

		try {

			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			if (!personneDAO.isAutorise(iddestinataire, jeton))
				return null;

			MessageDAO messagedao = new MessageDAO(connexion);
			listmessage = messagedao.getDiscussionByAct(iddestinataire,
					idactivite);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));

		} finally {
			CxoPool.closeConnection(connexion);
		}

		LogDAO.LOG_DUREE("getDiscussionByAct", debut);

		return (Message[]) listmessage.toArray(new Message[listmessage.size()]);

	}

	public Discussion[] getListDiscussion(int iddemandeur, int idpersonne,
			String jeton) {
		Connection connexion = null;
		long debut = System.currentTimeMillis();
		ArrayList<Discussion> retour = new ArrayList<Discussion>();

		try {
			connexion = CxoPool.getConnection();

			// *****************Securite*****************
			PersonneDAO personneDAO = new PersonneDAO(connexion);
			if (!personneDAO.isAutorise(iddemandeur, jeton))
				return null;

			// ************************************

			MessageDAO messagedao = new MessageDAO(connexion);
			ArrayList<Discussion> listdiscussion = null;

			// RECUPERE LES MESSAGE DES ACTIVITES//

			for (Discussion discussion : new DiscussionDAO(connexion)
					.getArrayDiscussionByAct(idpersonne))
				if (discussion != null)
					retour.add(discussion);

			// RECUPERE LES MESSAGE DES AMIS//
			listdiscussion = messagedao.getListDiscussion(idpersonne);

			for (Discussion discussion : listdiscussion)
				retour.add(discussion);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
		}

		finally {
			CxoPool.closeConnection(connexion);
		}

		Collections.sort(retour, new DiscussionDateComparator());

		LogDAO.LOG_DUREE("getListDiscussion", debut);

		return (Discussion[]) retour.toArray(new Discussion[retour.size()]);

	}

	public Notification[] getListNotification(int iddemandeur, int idpersonne,
			String jeton) {
		ArrayList<Notification> retour = new ArrayList<Notification>();
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();

			// ************************************Securite
			PersonneDAO personneDAO = new PersonneDAO(connexion);
			if (!personneDAO.isAutorise(iddemandeur, jeton))
				return null;
			// ************************************

			NotificationDAO notificationdao = new NotificationDAO(connexion);
			retour = notificationdao.getListNotification(idpersonne);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));

		} finally {
			CxoPool.closeConnection(connexion);
		}

		LogDAO.LOG_DUREE("getListNotification", debut);

		return (Notification[]) retour.toArray(new Notification[retour.size()]);

	}

	// Permet de lire les messages apr�s le message idxmessage
	public Message[] getListMessageAfter(int idpersonne, int idxmessage,
			String jeton) {
		long debut = System.currentTimeMillis();
		ArrayList<Message> listmessage = new ArrayList<Message>();
		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();
			// *****************Securite*****************

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			if (!personneDAO.isAutorise(idpersonne, jeton))
				return null;

			listmessage = new MessageDAO(connexion).getListMessageAfter(
					idpersonne, idxmessage);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));

		} finally {
			CxoPool.closeConnection(connexion);
		}

		LogDAO.LOG_DUREE("getListMessageAfter", debut);

		return (Message[]) listmessage.toArray(new Message[listmessage.size()]);

	}

	public Notification[] getListNotificationAfter(int idpersonne,
			int idxmessage, String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		ArrayList<Notification> retour = new ArrayList<Notification>();

		try {
			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			if (!personneDAO.isAutorise(idpersonne, jeton))
				return null;

			retour = new NotificationDAO(connexion).getListNotificationAfter(
					idpersonne, idxmessage);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));

		} finally {
			CxoPool.closeConnection(connexion);
		}

		LogDAO.LOG_DUREE("getListNotificationAfter", debut);

		return (Notification[]) retour.toArray(new Notification[retour.size()]);

	}

	public Message[] getListMessageAfterByAct(int idpersonne, int idxmessage,
			int idactivite, String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		ArrayList<Message> listmessage = new ArrayList<Message>();

		try {
			connexion = CxoPool.getConnection();
			PersonneDAO personneDAO = new PersonneDAO(connexion);
			if (!personneDAO.isAutorise(idpersonne, jeton))
				return null;

			listmessage = new MessageDAO(connexion).getListMessageAfterByAct(
					idpersonne, idxmessage, idactivite);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
		}

		finally {
			CxoPool.closeConnection(connexion);
		}

		LogDAO.LOG_DUREE("getListMessageAfterByAct", debut);

		return (Message[]) listmessage.toArray(new Message[listmessage.size()]);

	}

	public Activite getActivite(int idpersonne, int idactivite, String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();

			// *****************Securite*****************

			PersonneDAO personneDAO = new PersonneDAO(connexion);

			if (!personneDAO.isAutorise(idpersonne, jeton))
				return null;

			// ************************************
			ActiviteDAO activitedao = new ActiviteDAO(connexion);
			Activite activite = activitedao.getActivite(idactivite);

			if (activite != null) {
				activite.defineOrganisateur(idpersonne);//
				activitedao.isInscrit(activite, idpersonne);
				activite.setInteret(activitedao.isDejaInteret(idpersonne,
						idactivite));
				website.dao.ActiviteDAO.addNbrVu(idpersonne, idactivite,
						activite.getIdorganisateur());

			}

			// Ajoute le nbr de vu pour chaque vu de l'activit�

			LogDAO.LOG_DUREE("getActivite", debut);

			return activite;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			return null;
		} finally {

			CxoPool.closeConnection(connexion);

		}

	}

	public MessageServeur isDejaInteret(int idpersonne, int idactivite,
			int typeInteret, String jeton) {
		long debut = System.currentTimeMillis();

		Connection connexion = null;

		MessageServeur retour;
		try {

			typeInteret = 0;

			connexion = CxoPool.getConnection();

			// *****************Securite*****************

			PersonneDAO personneDAO = new PersonneDAO(connexion);

			if (!personneDAO.isAutorise(idpersonne, jeton))
				return new MessageServeur(false,
						TextWebService.PROFIL_NON_RECONNU);

			// ************************************

			boolean isdejaInteret = website.dao.ActiviteDAO
					.isInteretDejaSignale(idpersonne, idactivite);
			// Ajoute le nbr de vu pour chaque vu de l'activit�

			LogDAO.LOG_DUREE("isDejaInteret", debut);

			return new MessageServeur(isdejaInteret, "ok");

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));

		} finally {

			CxoPool.closeConnection(connexion);

		}

		return new MessageServeur(false, TextWebService.ERREUR_INCONNUE);

	}

	public MessageServeur addInteretActivite(int idpersonne, int idactivite,
			int typeInteret, String jeton) {
		long debut = System.currentTimeMillis();

		Connection connexion = null;

		MessageServeur retour;
		try {
			// connexion = CxoPool.getConnection();

			typeInteret = 0;

			connexion = CxoPool.getConnection();

			// *****************Securite*****************

			PersonneDAO personneDAO = new PersonneDAO(connexion);

			if (!personneDAO.isAutorise(idpersonne, jeton))
				return new MessageServeur(false,
						TextWebService.PROFIL_NON_RECONNU);

			// ************************************

			retour = website.dao.ActiviteDAO.addInteretActivite(idpersonne,
					idactivite, typeInteret);

			// Ajoute le nbr de vu pour chaque vu de l'activit�

			LogDAO.LOG_DUREE("addInteretActivite", debut);

			return retour;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));

		} finally {

			CxoPool.closeConnection(connexion);

		}

		return new MessageServeur(false, TextWebService.ERREUR_INCONNUE);
	}

	public TableauBord getTableauBord(int idpersonne, String jeton) {
		Connection connexion = null;
		TableauBord tableaubord;

		if (idpersonne == 0)
			return null;

		try {

			long debut = System.currentTimeMillis();
			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);

			if (!personneDAO.isAutorise(idpersonne, jeton))
				return null;

			ActiviteDAO activitedao = new ActiviteDAO(connexion);

			tableaubord = activitedao.getTableauBord(idpersonne);

			LogDAO.LOG_DUREE("getTableauBord", debut);
			CxoPool.closeConnection(connexion);
			return tableaubord;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			return null;
		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public IndicateurWayd getIndicateurs(int idpersonne, String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		IndicateurWayd indicateurs;

		try {
			connexion = CxoPool.getConnection();
			ActiviteDAO activitedao = new ActiviteDAO(connexion);
			PersonneDAO personneDAO = new PersonneDAO(connexion);
			if (!personneDAO.isAutorise(idpersonne, jeton))
				return null;

			indicateurs = activitedao.getIndicateurs();

			LogDAO.LOG_DUREE("getIndicateurs", debut);

			return indicateurs;

		} catch (SQLException | NamingException e) {
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			return null;

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public Avis[] getListAvis(int iddemandeur, int idpersonnenotee, String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		ArrayList<Avis> listavis = new ArrayList<Avis>();

		try {
			connexion = CxoPool.getConnection();

			// *****************Securite*****************

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			if (!personneDAO.isAutorise(iddemandeur, jeton))
				return null;

			// ************************************

			AvisDAO avisdao = new AvisDAO(connexion);
			listavis = avisdao.getListAvis(idpersonnenotee);

		} catch (Exception e) {

			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));

		} finally {

			CxoPool.closeConnection(connexion);
		}

		LogDAO.LOG_DUREE("getListAvis", debut);

		return (Avis[]) listavis.toArray(new Avis[listavis.size()]);

	}

	public Activite[] getListActivitePref(int iddemandeur, int idpersonne,
			String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		ArrayList<Activite> listActivite = new ArrayList<Activite>();

		try {
			connexion = CxoPool.getConnection();
			// *****************Securite*****************

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			if (!personneDAO.isAutorise(iddemandeur, jeton))
				return null;

			// ************************************
			ActiviteDAO activitedao = new ActiviteDAO(connexion);
			listActivite = activitedao.getListActivitePref(idpersonne);

			for (Activite activite : listActivite) {
				activite.setOrganisateur(false);
			}

		} catch (Exception e) {

			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));

		} finally {
			CxoPool.closeConnection(connexion);
		}

		LogDAO.LOG_DUREE("getListActivitePref", debut);

		return (Activite[]) listActivite.toArray(new Activite[listActivite
				.size()]);

	}

	public PhotoActivite[] getListPhotoActivite(int iddemandeur,
			int idactivite, String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		ArrayList<PhotoActivite> listPhoto = new ArrayList<PhotoActivite>();

		try {
			connexion = CxoPool.getConnection();
			// *****************Securite*****************

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			if (!personneDAO.isAutorise(iddemandeur, jeton))
				return null;

			// ************************************
			ActiviteDAO activitedao = new ActiviteDAO(connexion);
			listPhoto = activitedao.getListPhotoActivite(idactivite);

		} catch (Exception e) {

			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));

		} finally {
			CxoPool.closeConnection(connexion);
		}

		LogDAO.LOG_DUREE("getListPhotoActivite", debut);

		return (PhotoActivite[]) listPhoto.toArray(new PhotoActivite[listPhoto
				.size()]);

	}

	public Activite[] getListActivites(int idpersonne, String latitudestr,
			String longitudestr, int rayon, int idtypeactivite, String motcle,
			long debutActivite, long finActivite, int typeUser,
			int accessActivite, int commenceDans, String jeton) {

		// commenceDans en minutes
		// accessActivite payante/gratuite
		// typeUser pro/asso/waydeur

		long debut = System.currentTimeMillis();
		Connection connexion = null;

		ArrayList<Activite> listActivite = new ArrayList<Activite>();

		try {
			connexion = CxoPool.getConnection();

			// *****************Securite*****************

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			if (!personneDAO.isAutorise(idpersonne, jeton))
				return null;

			// ********************************************************

			ActiviteDAO activitedao = new ActiviteDAO(connexion);
			listActivite = activitedao.getListActivites(
					Double.valueOf(latitudestr), Double.valueOf(longitudestr),
					rayon, idtypeactivite, motcle, debutActivite, finActivite,
					typeUser, accessActivite, commenceDans);

			for (Activite activite : listActivite) {
				activite.defineOrganisateur(idpersonne);
			}

		} catch (NumberFormatException | SQLException | NamingException e1) {
			e1.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e1));

		} finally {
			CxoPool.closeConnection(connexion);
		}

		LogDAO.LOG_DUREE("getListActivites", debut);

		return (Activite[]) listActivite.toArray(new Activite[listActivite
				.size()]);

	}

	public ListActivitesRV getListActivitesRV(int idpersonne, String latitudestr,
			String longitudestr, int rayon, int idtypeactivite, String motcle,
			long debutActivite, long finActivite, int typeUser,
			int accessActivite, int commenceDans, String jeton) {

		// commenceDans en minutes
		// accessActivite payante/gratuite
		// typeUser pro/asso/waydeur

		
		long debut = System.currentTimeMillis();
		
		ListActivitesRV retour=new ListActivitesRV();
	
		Connection connexion = null;

		ArrayList<Activite> listActivite = new ArrayList<Activite>();
		ArrayList<Erreur> listErreurs = new ArrayList<Erreur>();
	
		try {
			connexion = CxoPool.getConnection();

			// *****************Securite*****************

			PersonneDAO personneDAO = new PersonneDAO(connexion);
		
			if (!personneDAO.isAutorise(idpersonne, jeton)){
				listErreurs.add(new Erreur(1, "Pas authentifié"));
				retour.initErreurs(listErreurs);
				return retour;
			}
			// ********************************************************

			ActiviteDAO activitedao = new ActiviteDAO(connexion);
			listActivite = activitedao.getListActivites(
					Double.valueOf(latitudestr), Double.valueOf(longitudestr),
					rayon, idtypeactivite, motcle, debutActivite, finActivite,
					typeUser, accessActivite, commenceDans);

			for (Activite activite : listActivite) {
				activite.defineOrganisateur(idpersonne);
			}

		} catch (NumberFormatException | SQLException | NamingException e1) {
			e1.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e1));
			listErreurs.add(new Erreur(2, e1.getMessage()));

		} finally {
			CxoPool.closeConnection(connexion);
		}
		LogDAO.LOG_DUREE("getListActivites", debut);
		retour.initActivite(listActivite);
		retour.initErreurs(listErreurs);

		return  retour;
		

	}

	public Activite[] getListActiviteAvenir(int idpersonne, String latitudestr,
			String longitudestr, int rayon, int idtypeactivite, String motcle,
			int commencedans, String jeton) {

		long debut = System.currentTimeMillis();
		Connection connexion = null;

		ArrayList<Activite> listActivite = new ArrayList<Activite>();

		try {
			connexion = CxoPool.getConnection();

			// *****************Securite*****************

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			if (!personneDAO.isAutorise(idpersonne, jeton))
				return null;

			// ********************************************************

			ActiviteDAO activitedao = new ActiviteDAO(connexion);
			listActivite = activitedao.getListActiviteAvenir(
					Double.valueOf(latitudestr), Double.valueOf(longitudestr),
					rayon, idtypeactivite, motcle, commencedans);

			for (Activite activite : listActivite) {
				activite.defineOrganisateur(idpersonne);
			}

		} catch (NumberFormatException | SQLException | NamingException e1) {

			e1.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e1));
		} finally {
			CxoPool.closeConnection(connexion);
		}

		LogDAO.LOG_DUREE("getListActiviteAvenir", debut);

		return (Activite[]) listActivite.toArray(new Activite[listActivite
				.size()]);

	}

	public Activite[] getListActiviteAvenirNocritere(int idpersonne,
			String latitudestr, String longitudestr, int rayon, String motcle,
			int commencedans, String jeton) {

		Connection connexion = null;
		long debut = System.currentTimeMillis();

		ArrayList<Activite> listActivite = new ArrayList<Activite>();

		// ************************************Securite

		try {

			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			if (!personneDAO.isAutorise(idpersonne, jeton))
				return null;

			ActiviteDAO activitedao = new ActiviteDAO(connexion);

			listActivite = activitedao.getListActiviteAvenirNocritere(
					Double.valueOf(latitudestr), Double.valueOf(longitudestr),
					rayon, motcle, commencedans);

			for (Activite activite : listActivite) {
				activite.defineOrganisateur(idpersonne);
			}

		} catch (NumberFormatException | SQLException | NamingException e1) {

			e1.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e1));
		} finally {
			CxoPool.closeConnection(connexion);
		}

		LogDAO.LOG_DUREE("getListActiviteAvenirNocritere", debut);

		return (Activite[]) listActivite.toArray(new Activite[listActivite
				.size()]);

	}

	public Activite[] getMesActiviteEncours(int iddemandeur, int idpersonne,
			String jeton) {
		long debut = System.currentTimeMillis();

		Connection connexion = null;
		ArrayList<Activite> listActivite = new ArrayList<Activite>();

		try {
			connexion = CxoPool.getConnection();
			// ************************************Securite
			PersonneDAO personneDAO = new PersonneDAO(connexion);
			if (!personneDAO.isAutorise(iddemandeur, jeton))
				return null;
			// ************************************

			ActiviteDAO activitedao = new ActiviteDAO(connexion);

			listActivite = activitedao.getMesActiviteEncours(idpersonne);

			for (Activite activite : listActivite)
				activite.defineOrganisateur();

			LogDAO.LOG_DUREE("getMesActiviteEncours", debut);

		} catch (SQLException | NamingException e) {
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));

		} finally {
			CxoPool.closeConnection(connexion);
		}

		return (Activite[]) listActivite.toArray(new Activite[listActivite
				.size()]);
	}

	public Activite[] getMesActiviteArchive(int iddemandeur, int idpersonne,
			String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		ArrayList<Activite> listActivite = new ArrayList<Activite>();
		try {
			connexion = CxoPool.getConnection();
			// ************************************Securite
			PersonneDAO personneDAO = new PersonneDAO(connexion);
			if (!personneDAO.isAutorise(iddemandeur, jeton))
				return null;
			// ************************************

			ActiviteDAO activitedao = new ActiviteDAO(connexion);
			listActivite = activitedao.getMesActiviteArchive(idpersonne);

			for (Activite activite : listActivite) {
				if (activite.role == 0) {
					activite.setDejainscrit(true);
				}

				if (activite.role == 1) {
					activite.setOrganisateur(true);

				}

			}

		} catch (SQLException | NamingException e) {

			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));

		} finally {
			CxoPool.closeConnection(connexion);
		}

		LogDAO.LOG_DUREE("getMesActiviteArchive", debut);

		return (Activite[]) listActivite.toArray(new Activite[listActivite
				.size()]);

	}

	public TypeActivite[] getListTypeActivite(int idpersonne, String jeton) {

		Connection connexion = null;
		long debut = System.currentTimeMillis();
		ArrayList<TypeActivite> retour = new ArrayList<TypeActivite>();
		ArrayList<TypeActiviteDb> listtypeactivitedb;

		try {
			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			if (!personneDAO.isAutorise(idpersonne, jeton))
				return null;

			TypeActiviteDAO typeactivitedao = new TypeActiviteDAO(connexion);
			listtypeactivitedb = typeactivitedao.getListTypeActivite();

			for (TypeActiviteDb typeactivitedb : listtypeactivitedb)
				retour.add(new TypeActivite(typeactivitedb));

		} catch (SQLException | NamingException e) {

			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
		} finally {
			CxoPool.closeConnection(connexion);
		}

		LogDAO.LOG_DUREE("getListTypeActivite", debut);

		return (TypeActivite[]) retour.toArray(new TypeActivite[retour.size()]);

	}

	public Version getVersion(int idpersonne, String jeton) {
		long debut = System.currentTimeMillis();

		Connection connexion = null;

		Version retour = new Version(0, 0, 0);

		Statement stmt = null;
		ResultSet rs = null;
		try {
			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);

			if (!personneDAO.isAutorise(idpersonne, jeton))
				return null;

			stmt = connexion.createStatement();
			rs = stmt.executeQuery("SELECT version,majeur,mineur from version");

			while (rs.next()) {
				int version = rs.getInt("version");
				int majeur = rs.getInt("majeur");
				int mineur = rs.getInt("mineur");
				retour = new Version(version, majeur, mineur);

			}
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));

		} finally {

			CxoPool.close(connexion, stmt, rs);

		}

		LogDAO.LOG_DUREE("getVersion", debut);

		return retour;

	}

	public MessageServeur addActivite(String titre, String libelle,
			int idorganisateur, int dureebalise, int idtypeactivite,
			String latitudestr, String longitudestr, String adresse,
			int nbmaxwaydeur, int dureeactivite, String jeton)
			throws ParseException {

		long debut = System.currentTimeMillis();

		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					idorganisateur, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			connexion.setAutoCommit(false);
			ActiviteDAO activitedao = new ActiviteDAO(connexion);

			double latitude = Double.parseDouble(latitudestr);
			double longitude = Double.parseDouble(longitudestr);
			Date datedebut, datebalise, datefinActivite;
			datedebut = new Date();
			// Calendar calBalise = Calendar.getInstance();
			// calBalise.setTime(datedebut);
			// calBalise.add(Calendar.MINUTE, dureebalise);
			// datebalise = calBalise.getTime();

			Calendar calFinActivite = Calendar.getInstance();
			calFinActivite.setTime(datedebut);
			calFinActivite.add(Calendar.MINUTE, dureeactivite);
			datefinActivite = calFinActivite.getTime();

			if (activitedao.getNbrActiviteProposeEnCours(idorganisateur) == WBservices.NB_MAX_ACTIVITE) {
				return new MessageServeur(false,
						TextWebService.QUOTA_ACTIVITE_DEPASSE);
			}

			Activite activite = new Activite(titre, libelle, idorganisateur,
					datedebut, idtypeactivite, latitude, longitude, adresse,
					true, nbmaxwaydeur, datefinActivite, ProfilBean.WAYDEUR);

			// ****************Ajoute l'activite*****************************

			activitedao.addActivite(activite);
			connexion.commit();

			// new AddActiviteGcm(activite, idorganisateur).start();

			PoolThreadGCM.poolThread.execute(new AddActiviteGcm(activite,
					idorganisateur));

			LogDAO.LOG_DUREE("addActivite", debut);
			return new MessageServeur(true, Integer.toString(activite.getId()));

		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());

		} finally {

			CxoPool.closeConnection(connexion);
		}

	}

	public MessageServeur addActivitePro(String titre, String libelle,
			int idorganisateur, int idtypeactivite, String latitudestr,
			String longitudestr, String adresse, Long dateDebut, Long DateFin,
			String jeton) throws ParseException {

		long debut = System.currentTimeMillis();

		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					idorganisateur, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			connexion.setAutoCommit(false);
			ActiviteDAO activitedao = new ActiviteDAO(connexion);

			double latitude = Double.parseDouble(latitudestr);
			double longitude = Double.parseDouble(longitudestr);
			Date datedebut, datefinActivite;
			datedebut = new Date(dateDebut);
			datefinActivite = new Date(DateFin);

			if (activitedao.getNbrActiviteProposeEnCours(idorganisateur) == WBservices.NB_MAX_ACTIVITE) {
				return new MessageServeur(false,
						TextWebService.QUOTA_ACTIVITE_DEPASSE);
			}

			Activite activite = new Activite(titre, libelle, idorganisateur,
					datedebut, idtypeactivite, latitude, longitude, adresse,
					true, 0, datefinActivite, ProfilBean.PRO);

			// ****************Ajoute l'activite*****************************

			activitedao.addActivite(activite);
			connexion.commit();

			// new AddActiviteGcm(activite, idorganisateur).start();

			PoolThreadGCM.poolThread.execute(new AddActiviteGcm(activite,
					idorganisateur));

			LogDAO.LOG_DUREE("addActivitePro", debut);

			return new MessageServeur(true, Integer.toString(activite.getId()));

		} catch (Exception e) {

			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);

			return new MessageServeur(false, e.getMessage());

		} finally {

			CxoPool.closeConnection(connexion);
		}

	}

	public MessageServeur addSuggestion(String suggestion, int idpersonne,
			String jeton) throws ParseException {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		try {
			connexion = CxoPool.getConnection();
			PersonneDAO personneDAO = new PersonneDAO(connexion);
			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					idpersonne, jeton);

			if (!autorise.isReponse()) {
				return autorise;
			}
			connexion.setAutoCommit(false);
			SuggestionDAO suggestiondao = new SuggestionDAO(connexion);
			suggestiondao.addSuggestion(idpersonne, suggestion);
			connexion.commit();

			LogDAO.LOG_DUREE("addSuggestion", debut);

			return new MessageServeur(true, TextWebService.AJOUTE_SUGGESTION);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);

			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}
	}

	public MessageServeur addPrbConnexion(String probleme, String email)
			throws ParseException {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			SuggestionDAO suggestiondao = new SuggestionDAO(connexion);
			suggestiondao.addPrbConnexion(probleme, email);
			connexion.commit();
			LogDAO.LOG_DUREE("addPrbConnexion", debut);
			return new MessageServeur(true, TextWebService.AJOUTE_SUGGESTION);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public RetourMessage addMessage(int idemetteur, String corps,
			int iddestinataire, String jeton) {
		long debut = System.currentTimeMillis();

		if (corps.length() == 0)
			return null;
		// return new MessageServeur(false, "Chaine vide non autoris�e");

		Connection connexion = null;
		Message message = new Message(idemetteur, corps, 0, 0);

		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);

			AmiDAO amidao = new AmiDAO(connexion);
			PersonneDAO personneDAO = new PersonneDAO(connexion);

			// Droit droit = new PersonneDAO(connexion)
			// .getDroit(idemetteur, jeton);

			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					idemetteur, jeton);

			if (!autorise.isReponse()) {
				return new RetourMessage(new Date().getTime(),
						RetourMessage.NON_AUTORISE, idemetteur);
			}

			// Plutot que d'informer le destinataire du changement du nombre de
			// message,
			// on calcule le nouveau nbr de message du destinataire et on lui
			// envoi le nbr par gcm.
			// Evite les aller retours.

			if (!amidao.isAmiFrom(iddestinataire, idemetteur))
				return new RetourMessage(new Date().getTime(),
						RetourMessage.PLUS_SON_AMI, idemetteur);

			MessageDAO messagedao = new MessageDAO(connexion);
			int idmessage = messagedao.addMessage(message, iddestinataire);
			connexion.commit();

			PoolThreadGCM.poolThread.execute(new AddMessageGcm(iddestinataire));

			LogDAO.LOG_DUREE("addMessage", debut);

			return new RetourMessage(new Date().getTime(), idmessage,
					idemetteur);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return null;
		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public RetourMessage addMessageByAct(int idemetteur, String corps,
			int idactivite, String jeton) {
		long debut = System.currentTimeMillis();

		if (corps.length() == 0)
			return null;
		// return new MessageServeur(false, "Chaine vide non autoris�e");

		Connection connexion = null;

		Message message = new Message(idemetteur, corps, idactivite, 0);

		try {

			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);

			ParticipationDAO participationdao = new ParticipationDAO(connexion);

			PersonneDAO personneDAO = new PersonneDAO(connexion);

			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					idemetteur, jeton);

			if (!autorise.isReponse()) {
				return new RetourMessage(new Date().getTime(),
						RetourMessage.NON_AUTORISE, idemetteur);
			}
			// Droit droit = new PersonneDAO(connexion)
			// .getDroit(idemetteur, jeton);

			// if (droit == null)
			// return new RetourMessage(new Date().getTime(),
			// RetourMessage.NON_AUTORISE, idemetteur);
			// // return new MessageServeur(false, "Tu n'es pas reconnu");
			//
			// MessageServeur autorisation = droit.isDefautAccess();
			//
			// if (!autorisation.isReponse()) {
			// return new RetourMessage(new Date().getTime(),
			// RetourMessage.NON_AUTORISE, idemetteur);
			// // return autorisation;
			// }

			ArrayList<Personne> listparticipant = participationdao
					.getListPartipantActivite(idactivite);

			if (!participationdao.isInListParticipant(listparticipant,
					idemetteur)) {
				return new RetourMessage(new Date().getTime(),
						RetourMessage.PLUS_INSCRIT, idemetteur);

			}

			MessageDAO messagedao = new MessageDAO(connexion);
			int idmessage = messagedao
					.addMessageByAct(message, listparticipant);
			connexion.commit();

			// new AddMessageByActGcm(idactivite).start();
			PoolThreadGCM.poolThread
					.execute(new AddMessageByActGcm(idactivite));

			LogDAO.LOG_DUREE("addMessageByAct", debut);

			return new RetourMessage(new Date().getTime(), idmessage,
					idemetteur);
			// return new MessageServeur(true, "" + idmessage);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return null;

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public MessageServeur effaceParticipation(int idDemandeur, int idAeffacer,
			int idactivite, String jeton) {

		long debut = System.currentTimeMillis();

		Connection connexion = null;

		try {

			connexion = CxoPool.getConnection();

			ParticipationDAO participationdao = new ParticipationDAO(connexion);
			DiscussionDAO discussiondao = new DiscussionDAO(connexion);
			MessageDAO messagedao = new MessageDAO(connexion);
			NotificationDAO notificationDAO = new NotificationDAO(connexion);
			ActiviteDAO activiteDAO = new ActiviteDAO(connexion);
			// ******************GESTION
			// SECURITE*************************************

			Activite activite = activiteDAO.getActivite(idactivite);

			if (activite == null)
				return new MessageServeur(false, TextWebService.activiteFinie);
			if (activite.isTerminee())
				return new MessageServeur(false,
						TextWebService.ACTIVITE_TERMINEE);

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					idDemandeur, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			// ******************************************************

			connexion.setAutoCommit(false);
			ArrayList<Personne> listepersonne = participationdao
					.getListPartipantActivite(idactivite);
			discussiondao.effaceDiscussionActivite(idactivite, idAeffacer);
			// Efface les messages emis et recu par le demadeur
			// vidage des messages
			Personne personne = new PersonneDAO(connexion)
					.getPersonneId(idAeffacer);
			Message message;
			if (idDemandeur == activite.getIdorganisateur()) {

				message = new Message(idAeffacer,
						"L'organisateur a désinscrit " + personne.getPrenom(),
						idactivite, 0);
				notificationDAO.addNotification(idAeffacer,
						Notification.MESSAGE_TEXT, idactivite, idDemandeur);
				activiteDAO.addRefus(idAeffacer, idactivite);
			} else {
				message = new Message(idAeffacer, personne.getPrenom()
						+ " ne souhaite plus participer", idactivite, 0);

			}

			messagedao.addMessageByAct(message, listepersonne);
			participationdao.RemoveParticipation(idAeffacer, idactivite,
					activite.getIdorganisateur());
			// Efface la particiaption attention a l'orrde.
			new ActiviteDAO(connexion).updateChampCalcule(idactivite);
			connexion.commit();

			// new EffaceParticipationGcm(listepersonne, idactivite, idAeffacer)
			// .start();
			PoolThreadGCM.poolThread.execute(new EffaceParticipationGcm(
					listepersonne, idactivite, idAeffacer));

			LogDAO.LOG_DUREE("effaceParticipation", debut);

			return new MessageServeur(true,
					TextWebService.suppressionParicipation);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);

			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public MessageServeur effaceActivite(int idorganisateur, int idactivite,
			String jeton) {

		long debut = System.currentTimeMillis();

		Connection connexion = null;

		try {

			connexion = CxoPool.getConnection();

			ActiviteDAO activitedao = new ActiviteDAO(connexion);
			NotificationDAO notificationdao = new NotificationDAO(connexion);
			ParticipationDAO participationdao = new ParticipationDAO(connexion);
			DiscussionDAO discussiondao = new DiscussionDAO(connexion);

			Activite activite = activitedao.getActivite(idactivite);

			if (activite == null)
				return new MessageServeur(false, TextWebService.activiteFinie);

			if (activite.isTerminee())
				return new MessageServeur(false,
						TextWebService.ACTIVITE_TERMINEE);

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					idorganisateur, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			// Recuepre les personnes interesse par cette activit�e
			connexion.setAutoCommit(false);
			final ArrayList<Personne> personneinteresse = activitedao
					.getListPersonneInterresse(activitedao
							.getActivite(idactivite));
			ArrayList<Personne> participants = participationdao
					.getListPartipantActivite(idactivite);

			activitedao.RemoveActivite(idactivite);

			notificationdao.addNotification(participants,
					Notification.Supprime_Activite, 0, idorganisateur);

			LOG.info("efaf acrtiii");

			discussiondao.effaceDiscussionTouteActivite(idactivite);

			connexion.commit();
			connexion.close();
			// new EffaceActiviteGcm(personneinteresse, participants,
			// idactivite)
			// .start();
			PoolThreadGCM.poolThread.execute(new EffaceActiviteGcm(
					personneinteresse, participants, idactivite));

			LogDAO.LOG_DUREE("effaceActivite", debut);

			return new MessageServeur(true, TextWebService.suppressionActivite);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public MessageServeur effaceMessage(int idpersonne, int listmessage[],
			String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					idpersonne, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			connexion.setAutoCommit(false);
			new MessageDAO(connexion).RemoveMessage(idpersonne, listmessage);
			connexion.commit();

			// new EffaceMessageGcm(idpersonne).start();
			PoolThreadGCM.poolThread.execute(new EffaceMessageGcm(idpersonne));

			LogDAO.LOG_DUREE("effaceMessage", debut);

			return new MessageServeur(true, "Suppressin ok");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}
	}

	public MessageServeur effaceMessageRecu(int idpersonne, int idmessage,
			String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {

			// ******************GESTION
			// SECURITE*************************************
			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					idpersonne, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			connexion.setAutoCommit(false);

			// ******************************************************

			new MessageDAO(connexion).effaceMessageRecu(idpersonne, idmessage);
			connexion.commit();
			// new EffaceMessageRecuGcm(idpersonne).start();

			PoolThreadGCM.poolThread.execute(new EffaceMessageRecuGcm(
					idpersonne));

			LogDAO.LOG_DUREE("effaceMessageRecu", debut);

			return new MessageServeur(true, TextWebService.suppressionMessage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public MessageServeur effaceMessageRecuByAct(int idpersonne, int idmessage,
			String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {

			// ******************GESTION
			// SECURITE*************************************
			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);

			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					idpersonne, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			connexion.setAutoCommit(false);
			new MessageDAO(connexion).effaceMessageRecuByAct(idpersonne,
					idmessage);
			connexion.commit();

			// new EffaceMessageRecuByActGcm(idpersonne).start();
			PoolThreadGCM.poolThread.execute(new EffaceMessageRecuByActGcm(
					idpersonne));

			LogDAO.LOG_DUREE("effaceMessageRecuByAct", debut);

			return new MessageServeur(true, TextWebService.suppressionMessage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public MessageServeur effaceNotificationRecu(int iddestinataire,
			int idnotification, String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {
			// ******************GESTION
			// SECURITE*************************************
			connexion = CxoPool.getConnection();
			PersonneDAO personneDAO = new PersonneDAO(connexion);
			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					iddestinataire, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			connexion.setAutoCommit(false);

			// ******************************************************

			new NotificationDAO(connexion).effaceNotification(iddestinataire,
					idnotification);
			connexion.commit();
			// new EffaceNotificationRecuGcm(iddestinataire).start();
			PoolThreadGCM.poolThread.execute(new EffaceNotificationRecuGcm(
					iddestinataire));

			LogDAO.LOG_DUREE("effaceNotificationRecu", debut);

			return new MessageServeur(true,
					TextWebService.suppressionNotifiaction);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public MessageServeur effaceAmi(int idpersonne, int idami, String jeton) {

		Connection connexion = null;
		long debut = System.currentTimeMillis();

		try {
			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					idpersonne, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			connexion.setAutoCommit(false);

			new AmiDAO(connexion).effaceAmi(idpersonne, idami);
			new NotificationDAO(connexion).addNotificationSupAmi(idpersonne,
					idami);
			connexion.commit();

			// new EffaceAmiGcm(idami, idpersonne).start();

			PoolThreadGCM.poolThread
					.execute(new EffaceAmiGcm(idami, idpersonne));

			LogDAO.LOG_DUREE("effaceAmi", debut);

			return new MessageServeur(true, TextWebService.suppressionAmi);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());

		} finally {

			CxoPool.closeConnection(connexion);
		}

	}

	public MessageServeur effaceMessageEmisByAct(int idpersonne, int idmessage,
			String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {

			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					idpersonne, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			connexion.setAutoCommit(false);

			new MessageDAO(connexion).effaceMessageEmisByAct(idpersonne,
					idmessage);
			connexion.commit();
			// new EffaceMessageEmisByActGcm(idpersonne).start();
			PoolThreadGCM.poolThread.execute(new EffaceMessageEmisByActGcm(
					idpersonne));
			LogDAO.LOG_DUREE("effaceMessageEmisByAct", debut);

			return new MessageServeur(true, TextWebService.suppressionMessage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public MessageServeur effaceMessageEmis(int idpersonne, int idmessage,
			String jeton) {
		long debut = System.currentTimeMillis();

		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					idpersonne, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			connexion.setAutoCommit(false);
			new MessageDAO(connexion).effaceMessageEmis(idpersonne, idmessage);
			connexion.commit();

			// new EffaceMessageEmisGcm(idpersonne).start();
			PoolThreadGCM.poolThread.execute(new EffaceMessageEmisGcm(
					idpersonne));

			LogDAO.LOG_DUREE("effaceMessageEmis", debut);

			return new MessageServeur(true, TextWebService.suppressionMessage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public MessageServeur effaceDiscussion(int iddestinataire, int idemetteur,
			String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();

			PersonneDAO personnedao = new PersonneDAO(connexion);
			MessageServeur autorise = personnedao.isAutoriseMessageServeur(
					iddestinataire, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			connexion.setAutoCommit(false);
			new MessageDAO(connexion).effaceDiscussion(iddestinataire,
					idemetteur);
			connexion.commit();
			// new EffaceDiscussionGcm(iddestinataire).start();
			PoolThreadGCM.poolThread.execute(new EffaceDiscussionGcm(
					iddestinataire));

			LogDAO.LOG_DUREE("effaceDiscussion", debut);

			return new MessageServeur(true,
					TextWebService.suppressionDiscussion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public MessageServeur acquitMessageDiscussion(int idpersonne,
			int idemetteur, String jeton) {
		// lit les message d'une discussion en bloc pour un emetteur et un
		// destinataire apres la fermeture de la liste des messages.

		Connection connexion = null;
		long debut = System.currentTimeMillis();

		try {
			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					idpersonne, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			connexion.setAutoCommit(false);
			MessageDAO messagedao = new MessageDAO(connexion);
			messagedao.LitMessageDiscussion(idpersonne, idemetteur);
			connexion.commit();

			// new AcquitMessageDiscussionGcm(idpersonne).start();
			PoolThreadGCM.poolThread.execute(new AcquitMessageDiscussionGcm(
					idpersonne));
			LogDAO.LOG_DUREE("acquitMessageDiscussion", debut);

			return new MessageServeur(true,
					TextWebService.acquittementMessageDiscussion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public MessageServeur acquitMessageDiscussionByAct(int iddestinataire,
			int idactivite, String jeton) {
		// lit les message d'une discussion en bloc pour un emetteur et un
		// destinataire apres la fermeture de la liste des messages.
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {

			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					iddestinataire, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			connexion.setAutoCommit(false);
			MessageDAO messagedao = new MessageDAO(connexion);
			messagedao.LitMessageDiscussionByAct(iddestinataire, idactivite);
			connexion.commit();

			// new AcquitMessageDiscussionByActGcm(iddestinataire).start();
			PoolThreadGCM.poolThread
					.execute(new AcquitMessageDiscussionByActGcm(iddestinataire));

			LogDAO.LOG_DUREE("acquitMessageDiscussionByAct", debut);

			return new MessageServeur(true,
					TextWebService.acquittementMessageDiscussion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public MessageServeur acquitAllNotification(int idpersonne, String jeton) {
		// lit les message d'une discussion en bloc pour un emetteur et un
		// destinataire
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					idpersonne, jeton);
			if (!autorise.isReponse()) {

				return autorise;
			}

			connexion.setAutoCommit(false);
			NotificationDAO notificationdao = new NotificationDAO(connexion);
			notificationdao.litNotification(idpersonne);
			connexion.commit();

			// new AcquitAllNotificationGcm(idpersonne).start();
			PoolThreadGCM.poolThread.execute(new AcquitAllNotificationGcm(
					idpersonne));

			LogDAO.LOG_DUREE("acquitAllNotification", debut);

			return new MessageServeur(true,
					TextWebService.acquittementMessageDiscussion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());

		} finally {

			CxoPool.closeConnection(connexion);

		}

	}

	public MessageServeur acquitMessage(int idpersonne, int idmessage,
			String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		try {
			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					idpersonne, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			MessageDAO messagedao = new MessageDAO(connexion);

			connexion.setAutoCommit(false);
			messagedao.LitMessage(idpersonne, idmessage);
			connexion.commit();

			// new AcquitMessageGcm(idpersonne).start();

			PoolThreadGCM.poolThread.execute(new AcquitMessageGcm(idpersonne));

			LogDAO.LOG_DUREE("acquitMessage", debut);

			return new MessageServeur(true,
					TextWebService.acquittementMessageDiscussion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public MessageServeur acquitMessageByAct(int idpersonne, int idmessage,
			String jeton) {
		long debut = System.currentTimeMillis();

		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					idpersonne, jeton);

			if (!autorise.isReponse()) {
				return autorise;
			}

			connexion.setAutoCommit(false);
			MessageDAO messagedao = new MessageDAO(connexion);
			messagedao.LitMessageByAct(idpersonne, idmessage);
			connexion.commit();

			// new AcquitMessageByActGcm(idpersonne).start();

			PoolThreadGCM.poolThread.execute(new AcquitMessageByActGcm(
					idpersonne));

			LogDAO.LOG_DUREE("acquitMessageByAct", debut);

			return new MessageServeur(true,
					TextWebService.acquittementMessageDiscussion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public MessageServeur acquitNotification(int idpersonne, int idmessage,
			String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		try {
			connexion = CxoPool.getConnection();

			PersonneDAO personnedao = new PersonneDAO(connexion);

			MessageServeur autorise = personnedao.isAutoriseMessageServeur(
					idpersonne, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			connexion.setAutoCommit(false);

			NotificationDAO notificationdao = new NotificationDAO(connexion);
			notificationdao.LitNotification(idpersonne, idmessage);
			connexion.commit();
			// new AcquitNotificationGcm(idpersonne).start();
			PoolThreadGCM.poolThread.execute(new AcquitNotificationGcm(
					idpersonne));

			LogDAO.LOG_DUREE("acquitNotification", debut);

			return new MessageServeur(true,
					TextWebService.acquittementMessageDiscussion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);

			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);

		}

	}

	public MessageServeur addParticipation(int iddemandeur, int idorganisateur,
			int idactivite, String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		try {

			if (iddemandeur == idorganisateur)
				return new MessageServeur(false,
						TextWebService.infoParticpationActivite);

			connexion = CxoPool.getConnection();

			// Limite le nombre de participation a 2
			// ParticipationDAO participationDAO=new
			// ParticipationDAO(connexion);
			// if (participationDAO.getNbrParticipation(iddemandeur)==2)
			// {
			// LOG.info("activite pleine");
			// return new MessageServeur(false, LibelleMessage.activiteFinie);
			// }
			//
			//

			ActiviteDAO activitedao = new ActiviteDAO(connexion);
			Activite activite = activitedao.getActivite(idactivite);

			PersonneDAO personneDAO = new PersonneDAO(connexion);

			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					iddemandeur, jeton);

			if (!autorise.isReponse()) {
				return autorise;
			}

			if (activite == null)
				return new MessageServeur(false, TextWebService.activiteFinie);

			if (activite.isTerminee())
				return new MessageServeur(false,
						TextWebService.ACTIVITE_TERMINEE);

			if (activite.isComplete())
				return new MessageServeur(false,
						TextWebService.activiteComplete);

			if (activitedao.isInscrit(activite, iddemandeur)) {
				return new MessageServeur(false,
						TextWebService.activiteDejaInscrit);
			}

			connexion.setAutoCommit(false);
			Participation participation = new Participation(iddemandeur,
					idorganisateur, idactivite);
			ParticipationDAO participationdao = new ParticipationDAO(connexion);
			participationdao.addParticipation(participation);
			activitedao.updateChampCalcule(idactivite);
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

			PoolThreadGCM.poolThread.execute(new AddParticipationGcm(
					listparticipant, idactivite));

			LogDAO.LOG_DUREE("addParticipation", debut);

			return new MessageServeur(true, TextWebService.activiteInscription);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public MessageServeur addAvis(int idpersonne, int idpersonnenotee,
			int idactivite, String titre, String libelle, String notestr,
			boolean demandeami, String jeton) {
		long debut = System.currentTimeMillis();

		Connection connexion = null;
		double note = Double.parseDouble(notestr);

		try {

			connexion = CxoPool.getConnection();
			PersonneDAO personneDAO = new PersonneDAO(connexion);
			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					idpersonne, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			connexion.setAutoCommit(false);
			AvisDAO avisdao = new AvisDAO(connexion);
			NotificationDAO notificationdao = new NotificationDAO(connexion);

			avisdao.addAvis(idpersonnenotee, idpersonne, idactivite, titre,
					libelle, note);// Ajoute

			new PersonneDAO(connexion)
					.updateChampCalculePersonne(idpersonnenotee);
			avisdao.updateDemande(idpersonne, idpersonnenotee, idactivite,
					demandeami); //

			boolean ajoutami = avisdao.gestionAmi(idpersonne, idpersonnenotee,
					idactivite);

			notificationdao.removeNotificationAnoter(idpersonne,
					idpersonnenotee, idactivite);

			// Envoi la notification si les 2 ont notés

			if (avisdao.isDoubleAvis(idpersonne, idpersonnenotee, idactivite)) {

				notificationdao.addNotification(idpersonnenotee,
						Notification.RecoitAvis, idactivite, idpersonne); //

				notificationdao.addNotification(idpersonne,
						Notification.RecoitAvis, idactivite, idpersonnenotee); //
			}

			if (ajoutami) // Envoi au 2 personne le
				// fait quelles soient amies
				notificationdao.addNotificationAjoutAmi(idpersonnenotee,
						idactivite, idpersonne);

			connexion.commit();

			// new AddAvisGcm(idpersonnenotee, idpersonne).start();

			PoolThreadGCM.poolThread.execute(new AddAvisGcm(idpersonnenotee,
					idpersonne));

			LogDAO.LOG_DUREE("addAvis", debut);

			return new MessageServeur(true, TextWebService.notationValidee);

		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public MessageServeur updateProfilPro(String photostr, String pseudo,
			String commentaire, int idpersonne, String tel, String siret,
			String siteweb, String jeton) {

		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {

			connexion = CxoPool.getConnection();
			// SECURITE*************************************
			PersonneDAO personnedao = new PersonneDAO(connexion);
			MessageServeur autorise = personnedao.isAutoriseMessageServeur(
					idpersonne, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			// ******************************************************

			connexion.setAutoCommit(false);
			personnedao.updateProfilWaydPro(photostr, pseudo, commentaire,
					idpersonne, tel, siret, siteweb);
			connexion.commit();

			LogDAO.LOG_DUREE("updateProfilPro", debut);

			return new MessageServeur(true, TextWebService.profilMisAjour);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());
		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public MessageServeur updateProfilWayd(String photostr, String nom,
			String prenom, String datenaissancestr, int sexe,
			String commentaire, int idpersonne, boolean afficheage,
			boolean affichesexe, String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {

			connexion = CxoPool.getConnection();
			// SECURITE*************************************
			PersonneDAO personnedao = new PersonneDAO(connexion);
			MessageServeur autorise = personnedao.isAutoriseMessageServeur(
					idpersonne, jeton);

			if (!autorise.isReponse()) {
				return autorise;
			}

			// ******************************************************

			connexion.setAutoCommit(false);

			personnedao.updateProfilWayd(idpersonne, photostr, nom, prenom,
					datenaissancestr, sexe, commentaire, afficheage,
					affichesexe);
			connexion.commit();

			LogDAO.LOG_DUREE("updateProfilWayd", debut);

			return new MessageServeur(true, TextWebService.profilMisAjour);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());
		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public MessageServeur updatePseudo(String pseudo, Long datenaissance,
			int sexe, int idpersonne, String jeton) {
		long debut = System.currentTimeMillis();

		Connection connexion = null;
		String lowerpseudo = pseudo.toLowerCase();

		try {

			// SECURITE*************************************
			connexion = CxoPool.getConnection();

			PersonneDAO personnedao = new PersonneDAO(connexion);

			MessageServeur autorise = personnedao.isAutoriseMessageServeur(
					idpersonne, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			if (personnedao.isPseudoExist(pseudo))
				return new MessageServeur(false, TextWebService.pseudoExist);

			connexion.setAutoCommit(false);
			personnedao.updatePseudo(lowerpseudo, datenaissance, sexe, jeton,
					idpersonne);
			connexion.commit();

			LogDAO.LOG_DUREE("updatePseudo", debut);

			return new MessageServeur(true, TextWebService.profilMisAjour);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);

			return new MessageServeur(false, e.getMessage());
		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	// ************************Permet de avoir si des notifications de notation
	// sont � pousser
	// *************************En effet les notification de notations
	// n'apparaissent qu'a la fin de l'activite
	// ************** Il n' y pas de thread sur le serveur d'appli c'est le
	// client qui sollicite toutes les 5 minutes

	public MessageServeur updateNotification(int idpersonne, String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		try {

			connexion = CxoPool.getConnection();

			// **************Securit�
			PersonneDAO personneDAO = new PersonneDAO(connexion);
			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					idpersonne, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}
			// ****************************************
			connexion.setAutoCommit(false);
			NotificationDAO notificationdao = new NotificationDAO(connexion);
			notificationdao.addNotificationFromAvis(idpersonne);

			LogDAO.LOG_DUREE("updateNotification", debut);

			connexion.commit();

			// new UpdateNotificationGcm(idpersonne).start();
			PoolThreadGCM.poolThread.execute(new UpdateNotificationGcm(
					idpersonne));
			return new MessageServeur(true, TextWebService.preferenceMisAjour);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());
		} finally {
			CxoPool.closeConnection(connexion);
		}
	}

	public MessageServeur updateNotificationPref(int idpersonne, String jeton,
			boolean notification) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		try {

			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					idpersonne, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			connexion.setAutoCommit(false);
			personneDAO.updateNotificationPref(idpersonne, notification);
			connexion.commit();

			LogDAO.LOG_DUREE("updateNotificationPref", debut);

			return new MessageServeur(true, TextWebService.preferenceMisAjour);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());
		} finally {
			CxoPool.closeConnection(connexion);
		}
	}

	public MessageServeur updatePosition(int idpersonne, String latitudestr,
			String longitudestr, String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		try {

			connexion = CxoPool.getConnection();

			PersonneDAO personneDAO = new PersonneDAO(connexion);
			MessageServeur autorise = personneDAO.isAutoriseMessageServeur(
					idpersonne, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			connexion.setAutoCommit(false);
			personneDAO.updatePosition(idpersonne, Double.valueOf(latitudestr),
					Double.valueOf(longitudestr));
			connexion.commit();

			// new UpdatePositionGcm(idpersonne).start();
			PoolThreadGCM.poolThread.execute(new UpdatePositionGcm(idpersonne));

			LogDAO.LOG_DUREE("updatePosition", debut);

			return new MessageServeur(true, TextWebService.preferenceMisAjour);
		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);

			return new MessageServeur(false, e.getMessage());
		} finally {
			CxoPool.closeConnection(connexion);
		}
	}

	public MessageServeur updateActivite(int idpersonne, int idactivite,
			String titre, String libelle, int nbrmax, String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();

			// SECURITE*************************************
			PersonneDAO personnedao = new PersonneDAO(connexion);
			ActiviteDAO activitedao = new ActiviteDAO(connexion);

			MessageServeur autorise = personnedao.isAutoriseMessageServeur(
					idpersonne, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			connexion.setAutoCommit(false);
			activitedao.updateActivite(idpersonne, libelle, titre, idactivite,
					nbrmax);
			connexion.commit();

			// new UpdateActiviteGcm(idactivite, idpersonne).start();

			PoolThreadGCM.poolThread.execute(new UpdateActiviteGcm(idactivite,
					idpersonne));

			LogDAO.LOG_DUREE("updateActivite", debut);

			return new MessageServeur(true, TextWebService.activiteModifiee);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());
		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public MessageServeur updateGCM(int idpersonne, String gcm, String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();
			// SECURITE*************************************
			PersonneDAO personnedao = new PersonneDAO(connexion);
			MessageServeur autorise = personnedao.isAutoriseMessageServeur(
					idpersonne, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			// ******************************************************

			connexion.setAutoCommit(false);
			personnedao.updateGCM(idpersonne, gcm);

			connexion.commit();

			long duree = System.currentTimeMillis() - debut;
			LogDAO.LOG_DUREE("updateGCM", duree);

			return new MessageServeur(true, TextWebService.profilMisAjour);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());
		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public Profil getFullProfil(int iddemandeur, int idpersonne, String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();

			// ************************************Securite
			PersonneDAO personneDAO = new PersonneDAO(connexion);
			if (!personneDAO.isAutorise(iddemandeur, jeton))
				return null;

			// ************************************

			Profil profil = personneDAO.getFullProfil(idpersonne);

			LogDAO.LOG_DUREE("getFullProfil", debut);

			return profil;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			return null;
		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public ProfilPro getFullProfilPro(int iddemandeur, int idpersonne,
			String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();

			// ************************************Securite
			PersonneDAO personneDAO = new PersonneDAO(connexion);
			if (!personneDAO.isAutorise(iddemandeur, jeton))
				return null;

			// ************************************

			ProfilPro profilpro = personneDAO.getFullProfilPro(idpersonne);

			LogDAO.LOG_DUREE("getFullProfilPro", debut);

			return profilpro;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			return null;
		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public ProfilNotation getProfilNotation(int iddemandeur, String jeton,
			int notateur, int idpersonne, int idactivite) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();
			PersonneDAO personnedao = new PersonneDAO(connexion);

			if (!personnedao.isAutorise(iddemandeur, jeton))
				return null;

			ProfilNotation profil = personnedao.getProfilNotation(notateur,
					idpersonne, idactivite);

			LogDAO.LOG_DUREE("getProfilNotation", debut);

			return profil;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			return null;
		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public Personne getPersonne(final String idtoken, final String photostr,
			final String nom, final String gcmToken) {
		Connection connexion = null;
		long debut = System.currentTimeMillis();

		if (FirebaseApp.getApps().isEmpty()) {

			FirebaseApp.initializeApp(WBservices.optionFireBase);

		}
		PreparedStatement preparedStatement = null;
		FirebaseToken token;
		try {

			token = FirebaseAuth.getInstance().verifyIdTokenAsync(idtoken)
					.get();
			String uid = token.getUid();
			String email = token.getEmail();

			// *******************Cree ou met à jour le profil
			PersonneDAO
					.gestionUid(uid, idtoken, photostr, nom, gcmToken, email);
			// ***************************************
			connexion = CxoPool.getConnection();

			PersonneDAO personnedao = new PersonneDAO(connexion);

			Personne personne = personnedao.getPersonneByUID(uid);// Recherche

			if (personne == null) {
				return null;
			}

			personnedao.updateChampCalculePersonne(personne.getId());// calcule
																		// les
																		// champs
			personne.setMessage("Ok");

			Droit droit = new PersonneDAO(connexion).getDroit(personne.getId());

			if (droit == null) {
				personne.setMessage(TextWebService.PERSONNE_INEXISTANTE);
				personne.setId(0);// echec connexion
				return personne;
			}
			
			if (!droit.isJetonOk(idtoken)) {
				personne.setMessage(TextWebService.JETON_NON_VALIDE);
				personne.setId(0);// echec connexion
				return personne;
			}

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				personne.setId(0);// echec connexion
				personne.setMessage(TextWebService.COMPTE_INACTIF);
				return personne;
			}

			NotificationDAO notificationdao = new NotificationDAO(connexion);
			notificationdao.addNotificationFromAvis(personne.getId());

			// ******** Met les notifications existantes � pas lu **//
			String requete = "UPDATE  notification set lu=false "
					+ " WHERE iddestinataire=? and idtype=1";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, personne.getId());
			preparedStatement.execute();

			LogDAO.LOG_DUREE("getPersonne", debut);

			return personne;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			return null;
		}

		finally {

			CxoPool.close(connexion, preparedStatement);
		}
	}

	public MessageServeur signalerActivite(int idpersonne, int idactivite,
			int idmotif, String motif, String titre, String libelle,
			String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {

			connexion = CxoPool.getConnection();

			PersonneDAO personnedao = new PersonneDAO(connexion);
			MessageServeur autorise = personnedao.isAutoriseMessageServeur(
					idpersonne, jeton);
			if (!autorise.isReponse()) {
				return autorise;
			}

			SignalementDAO signalementdao = new SignalementDAO(connexion);

			// Verfiie que le signalement est unique
			if (signalementdao.isSignalerActvite(idpersonne, idactivite))
				return new MessageServeur(false,
						TextWebService.activiteDejaSignale);

			connexion.setAutoCommit(false);
			signalementdao.signalerActivite(idpersonne, idactivite, idmotif,
					motif, titre, libelle);
			connexion.commit();

			LogDAO.LOG_DUREE("signalerActivite", debut);

			return new MessageServeur(true, TextWebService.activiteSignale);

		} catch (Exception e) {

			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public MessageServeur signalerProfil(int idpersonne, int idsignalement,
			int idmotif, String motif, String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {

			connexion = CxoPool.getConnection();

			SignalementDAO signalementdao = new SignalementDAO(connexion);

			PersonneDAO personnedao = new PersonneDAO(connexion);
			MessageServeur autorise = personnedao.isAutoriseMessageServeur(
					idpersonne, jeton);
			if (!autorise.isReponse()) {
				return new MessageServeur(false,
						TextWebService.PROFIL_NON_RECONNU);

			}
			// Verfiie que le signalement est unique
			if (signalementdao.isSignalerProfil(idpersonne, idsignalement))
				return new MessageServeur(false,
						TextWebService.PROFIL_DEJA_SIGNALE);

			connexion.setAutoCommit(false);
			signalementdao.signalerProfil(idpersonne, idsignalement, idmotif,
					motif);
			connexion.commit();

			LogDAO.LOG_DUREE("signalerProfil", debut);

			return new MessageServeur(true, TextWebService.profilSignale);

		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public Activite[] getActivites(int idPersonne, String latitudestr,
			String longitudestr, int rayonmetre, int typeactivite,
			String motcle, int typeUser, int commenceDans, String jeton) {
		double malatitude = Double.valueOf(latitudestr);
		double malongitude = Double.valueOf(longitudestr);

		long debut = System.currentTimeMillis();

		Connection connexion = null;
		ArrayList<Activite> listActivite = new ArrayList<Activite>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();

			PersonneDAO personnedao = new PersonneDAO(connexion);
			MessageServeur autorise = personnedao.isAutoriseMessageServeur(
					idPersonne, jeton);
			if (!autorise.isReponse()) {
				return null;

			}

			double coef = rayonmetre * 0.007 / 700;
			double latMin = malatitude - coef;
			double latMax = malatitude + coef;
			double longMin = malongitude - coef;
			double longMax = malongitude + coef;
			Activite activite = null;

			Calendar calendrierDebut = Calendar.getInstance();

			Date dateRechercheDebut = calendrierDebut.getTime();

			Calendar calendrierFin = Calendar.getInstance();

			calendrierFin.add(Calendar.MINUTE, commenceDans);
			Date dateRechercheFin = calendrierFin.getTime();
			// on remonte les activités dont le debut est comprise entre
			// l'heure
			// actuelle + commenceDans et l'heure actuelle + commenceDans+1
			// heure

			String requete = null;

			// Renvoi les imm�diates
			if (commenceDans == 0) {

				requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
						+ " activite.longitude,    personne.prenom,    personne.sexe,    personne.nom,    personne.idpersonne,personne.datenaissance,    "
						+ "personne.note,personne.nbravis as totalavis,personne.photo,personne.affichesexe,personne.afficheage,activite.typeuser,"
						+ "activite.nbrwaydeur as nbrparticipant,1 as role,"
						+ "activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite,activite.nbmaxwayd  FROM personne,"
						+ "activite  WHERE personne.idpersonne = activite.idpersonne  "
						+ "and (? between datedebut and  datefin )"
						+ " and activite.latitude between ? and ?"
						+ " and activite.longitude between ? and ?";
			}

			// recheche les activite dont la date de debut est comprise entre
			// datefinde rechere et maintenant
			else {
				// Requete N�2
				requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
						+ " activite.longitude,    personne.prenom,    personne.sexe,    personne.nom,    personne.idpersonne,personne.datenaissance,    "
						+ "personne.note,personne.nbravis as totalavis,personne.photo,personne.affichesexe,personne.afficheage,activite.typeuser,"
						+ "activite.nbrwaydeur as nbrparticipant,1 as role,"
						+ "activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite,activite.nbmaxwayd  FROM personne,"
						+ "activite  WHERE personne.idpersonne = activite.idpersonne  "
						+ "and datedebut>? "
						+ " and activite.latitude between ? and ?"
						+ " and activite.longitude between ? and ? and datedebut<?";

			}

			if (typeactivite != -1) {
				requete = requete + " and activite.idtypeactivite=?";
			}

			if (!motcle.isEmpty()) {

				requete = requete
						+ " and ( UPPER(libelle) like UPPER(?) or UPPER(titre) like UPPER(?)) ";

			}

			if (typeUser != 0) {

				requete = requete + " and activite.typeuser=?";

			}

			requete = requete + " ORDER BY datedebut asc;";

			preparedStatement = connexion.prepareStatement(requete);

			preparedStatement.setTimestamp(1, new java.sql.Timestamp(
					dateRechercheDebut.getTime()));

			preparedStatement.setDouble(2, latMin);
			preparedStatement.setDouble(3, latMax);
			preparedStatement.setDouble(4, longMin);
			preparedStatement.setDouble(5, longMax);

			int index = 5;

			if (commenceDans != 0) {
				// ajoute � la requete n�2 la date de fin
				index++;
				preparedStatement.setTimestamp(index, new java.sql.Timestamp(
						dateRechercheFin.getTime()));
			}

			if (typeactivite != -1) {
				index++;
				preparedStatement.setInt(index, typeactivite);

			}

			if (!motcle.isEmpty()) {
				index++;
				String test = "%" + motcle + "%";
				preparedStatement.setString(index, test);
				index++;
				preparedStatement.setString(index, test);

			}

			if (typeUser != 0) {

				index++;
				preparedStatement.setInt(index, typeUser);

			}

			//

			rs = preparedStatement.executeQuery();
			while (rs.next()) {

				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				double distance = ServeurMethodes.getDistance(malatitude,
						latitude, malongitude, longitude);
				if (distance >= rayonmetre)
					continue;

				int id = rs.getInt("idactivite");
				String libelle = rs.getString("libelle");
				String titre = rs.getString("titre");
				int idorganisateur = rs.getInt("idpersonne");
				int idtypeactivite = rs.getInt("idtypeactivite");
				int sexe = rs.getInt("sexe");
				int nbmaxwayd = rs.getInt("nbmaxwayd");
				int nbrparticipant = rs.getInt("nbrparticipant");
				Date datedebut = rs.getTimestamp("datedebut");
				Date datefin = rs.getTimestamp("datefin");
				String adresse = rs.getString("adresse");
				double note = rs.getDouble("note");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				boolean afficheage = rs.getBoolean("afficheage");
				boolean affichesexe = rs.getBoolean("affichesexe");

				// Date datefinactivite = rs.getTimestamp("d_finactivite");

				if (prenom == null)
					prenom = "";
				String photo = rs.getString("photo");
				int role = rs.getInt("role");
				Date datenaissance = rs.getTimestamp("datenaissance");
				boolean archive = false;
				int totalavis = rs.getInt("totalavis");
				int typeuser = rs.getInt("typeuser");
				activite = new Activite(id, titre, libelle, idorganisateur,
						datedebut, datefin, idtypeactivite, latitude,
						longitude, adresse, nom, prenom, photo, note, role,
						archive, totalavis, datenaissance, sexe,
						nbrparticipant, afficheage, affichesexe, nbmaxwayd,
						typeuser, 0);

				listActivite.add(activite);

			}

			rs.close();
			preparedStatement.close();

			LogDAO.LOG_DUREE("getActivites", debut);

			return (Activite[]) listActivite.toArray(new Activite[listActivite
					.size()]);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			return (Activite[]) listActivite.toArray(new Activite[listActivite
					.size()]);
		} finally {
			CxoPool.close(connexion, preparedStatement, rs);
		}

	}

}
