package wayd.ws;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

import javax.naming.NamingException;

import wayde.bean.Activite;
import wayde.bean.Ami;
import wayde.bean.Avis;
import wayde.bean.AvisaDonner;
import wayde.bean.CxoPool;
import wayde.bean.DefinitionPreference;
import wayde.bean.Discussion;
import wayde.bean.Droit;
import wayde.bean.IndicateurWayd;
import wayde.bean.InfoNotation;
import wayde.bean.LibelleMessage;
import wayde.bean.Message;
import wayde.bean.MessageServeur;
import wayde.bean.Notification;
import wayde.bean.Participant;
import wayde.bean.Participation;
import wayde.bean.Personne;
import wayde.bean.Preference;
import wayde.bean.Profil;
import wayde.bean.ProfilDiscussion;
import wayde.bean.ProfilNotation;
import wayde.bean.RetourMessage;
import wayde.bean.TableauBord;
import wayde.bean.TypeActivite;
import wayde.beandatabase.AvisaDonnerDb;
import wayde.beandatabase.DefinitionPreferenceDb;
import wayde.beandatabase.TypeActiviteDb;
import wayde.dao.ActiviteDAO;
import wayde.dao.AmiDAO;
import wayde.dao.AvisDAO;
import wayde.dao.AvisaDonnerDAO;
import wayde.dao.DefinintionPreferenceDAO;
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

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.tasks.OnSuccessListener;

import comparator.DiscussionDateComparator;
import fcm.PushNotifictionHelper;
import fcm.ServeurMethodes;

public class WBservices {
	public final static int NB_MAX_ACTIVITE = 100;
	public static SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM HH:mm:ss");

	public void envoyerMail() {

		// new Outils().EnvoyerMdp("pmestivier@club.fr", "ppp");
	//kljlkj
	}

	
	public boolean testToken(String idtoken, String photostr, String nom,String gcmToken) {
		long debut = System.currentTimeMillis();
		System.out.print("test token");
		FirebaseOptions options;
		if (FirebaseApp.getApps().isEmpty()) {

			try {
				options = new FirebaseOptions.Builder()
						// .setServiceAccount(new
						// FileInputStream("/usr/lib/jvm/java-8-openjdk-amd64/jre/cle/cle.json"))
//jklmjl
						.setServiceAccount(new FileInputStream("d:/cle.json"))
						.setDatabaseUrl("https://wayd-c0414.firebaseio.com/")
						.build();
				FirebaseApp.initializeApp(options);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (FirebaseApp.getApps().isEmpty()) {

			try {
				options = new FirebaseOptions.Builder()
						.setServiceAccount(
								new FileInputStream(
										"/usr/lib/jvm/java-8-openjdk-amd64/jre/cle/cle.json"))

						// .setServiceAccount(new
						// FileInputStream("d:/cle.json"))
						.setDatabaseUrl("https://wayd-c0414.firebaseio.com/")
						.build();
				FirebaseApp.initializeApp(options);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		FirebaseAuth.getInstance().verifyIdToken(idtoken)
				.addOnSuccessListener(new OnSuccessListener<FirebaseToken>() {
					@Override
					public void onSuccess(FirebaseToken decodedToken) {
						String uid = decodedToken.getUid();
						Connection connexion = null;
						try {
							connexion = CxoPool.getConnection();
							PersonneDAO personnedao = new PersonneDAO(connexion);
							if (!personnedao.isLoginExist(uid)) {
								 personnedao
										.addCompteGenerique(uid, idtoken,
												photostr, nom,gcmToken);

							}

							else
								personnedao.updateJeton(uid, idtoken, photostr,
										nom,gcmToken);
						} catch (SQLException | NamingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} // ...
						catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							CxoPool.closeConnection(connexion);
						}

					}
				});

		System.out.println(formatDate.format(new Date())
				+ "-Test jeton -Connexion :"
				+ (System.currentTimeMillis() - debut) + "ms");

		return true;

	}

	public MessageServeur isAmiFrom(int idpersonne, int idami) {
		Connection connexion = null;
		long debut = System.currentTimeMillis();
		boolean retour;
		try {
			connexion = CxoPool.getConnection();
			AmiDAO amiddao = new AmiDAO(connexion);
			retour = amiddao.isAmiFrom(idpersonne, idami);
			System.out.println(formatDate.format(new Date()) + ";isAmiFrom;"
					+ (System.currentTimeMillis() - debut) + "ms");

			return new MessageServeur(retour, LibelleMessage.pasAMI);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			CxoPool.closeConnection(connexion);

		}
		return new MessageServeur(false, LibelleMessage.pasAMI);

	}

	public Avis getAvis(int idnoter, int idactivite, int idnotateur,
			int idpersonnenotee) {

		// Retour l'avis si idnoter est !=0 on cherche l'avis par son id
		// dans la table noter, sinon par les 3 autres paramétres
		long debut = System.currentTimeMillis();

		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();
			AvisDAO avisdao = new AvisDAO(connexion);

			Avis avis = null;

			if (idnoter != 0)//
				avis = avisdao.getAvisById(idnoter);
			else

				avis = avisdao.getDetailAvis(idactivite, idnotateur,
						idpersonnenotee);

			System.out.println(formatDate.format(new Date()) + ";getAvis;"
					+ (System.currentTimeMillis() - debut) + " ms");
			return avis;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// System.out.println("REtuornull");
			return null;
		} finally {

			CxoPool.closeConnection(connexion);
		}
	}

	public DefinitionPreference getDefPref(int idpersonne, int idtypeactivite) {

		Connection connexion = null;

		long debut = System.currentTimeMillis();
		DefinitionPreferenceDb defprefdb;

		try {
			connexion = CxoPool.getConnection();
			DefinintionPreferenceDAO definitionprefdao = new DefinintionPreferenceDAO(
					connexion);

			defprefdb = definitionprefdao
					.getDefPref(idpersonne, idtypeactivite);

			if (defprefdb == null)
				return null;

			DefinitionPreference defpref = new DefinitionPreference(defprefdb);

			System.out.println(formatDate.format(new Date()) + ";getDefPref;"
					+ (System.currentTimeMillis() - debut) + "ms");
			return defpref;

		} catch (SQLException | NamingException e) {

			e.printStackTrace();
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
			PersonneDAO personnedao = new PersonneDAO(connexion);

			// SECURITE*************************************

			Droit droit = new PersonneDAO(connexion)
					.getDroit(idpersonne, jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return autorisation;
			}

			// ******************************************************

			preferencedao.addPreference(idpersonne, idtypeactivite, active);
			personnedao.updateRayon(idpersonne, rayon);

			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Connection connexionGcm = null;
					try {
						connexionGcm = CxoPool.getConnection();
						new ServeurMethodes(connexionGcm)
								.envoiAndroidRefreshTDB(idpersonne);

					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						CxoPool.closeConnection(connexionGcm);
					}

				}
			}).start();

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}
		System.out.println(formatDate.format(new Date()) + ";updatePreference;"
				+ (System.currentTimeMillis() - debut) + "ms");
		return new MessageServeur(true, LibelleMessage.preferenceSauvegardee);

	}

	public InfoNotation getInfoNotation(int idpersonne) {
		// System.out.println("Cherche notation de " + idpersonne);

		long debut = System.currentTimeMillis();
		Connection connexion = null;

		String requete = " SELECT (SELECT round(AVG(note),1) FROM noter where idpersonnenotee=? and fait=true) as moyenne"
				+ " ,	(SELECT COUNT(*) FROM noter where idpersonnenotee=? and fait=true) as total";

		PreparedStatement preparedStatement;
		try {
			connexion = CxoPool.getConnection();
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setInt(2, idpersonne);
			// preparedStatement.executeQuery();
			ResultSet rs = preparedStatement.executeQuery();
			int totalavis = 0;
			double moyenne = 0;
			while (rs.next()) {
				moyenne = rs.getDouble("moyenne");
				totalavis = rs.getInt("total");
			}

			preparedStatement.close();
			rs.close();
			// System.out.println("Trouve notation" + idpersonne);
			// Stats.nbRequete++;
			System.out.println(formatDate.format(new Date())
					+ ";getInfoNotation;"
					+ (System.currentTimeMillis() - debut) + "ms");
			return new InfoNotation(totalavis, moyenne);
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}
		return null;

	}

	public Message getMessage(int idmessage) {

		// AvisDb avisdb = AvisDAO.getAvis(id);

		long debut = System.currentTimeMillis();
		Connection connexion = null;
		try {
			connexion = CxoPool.getConnection();
			MessageDAO messagedao = new MessageDAO(connexion);
			Message message;
			message = messagedao.getMessage(idmessage);
			System.out.println(formatDate.format(new Date()) + ";getMessage;"
					+ (System.currentTimeMillis() - debut) + "ms");
			return message;
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public Message[] getListMessageNonLu(int idpersonne) {

		Connection connexion = null;
		ArrayList<Message> listmessage = new ArrayList<Message>();
		long debut = System.currentTimeMillis();
		try {

			connexion = CxoPool.getConnection();
			MessageDAO messagedao = new MessageDAO(connexion);
			listmessage = messagedao.getListMessageNonLu(idpersonne);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			CxoPool.closeConnection(connexion);
		}
		System.out.println(formatDate.format(new Date())
				+ ";getListMessageNonLu;"
				+ (System.currentTimeMillis() - debut) + "ms");
		return (Message[]) listmessage.toArray(new Message[listmessage.size()]);

	}

	public Ami[] getListAmi(int idpersonne) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		ArrayList<Ami> retour = new ArrayList<Ami>();

		try {
			connexion = CxoPool.getConnection();
			AmiDAO amidao = new AmiDAO(connexion);
			retour = amidao.getListAmi(idpersonne);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}
		System.out.println(formatDate.format(new Date()) + ";getListAmi;"
				+ (System.currentTimeMillis() - debut) + "ms");
		return (Ami[]) retour.toArray(new Ami[retour.size()]);

	}

	public Participant[] getListParticipant(int idactivite) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		ArrayList<Participant> retour = new ArrayList<Participant>();

		// System.out.println("Recupere tous les participants de l'activite "
		// + idactivite);
		try {
			connexion = CxoPool.getConnection();
			ParticipantDAO participantdao = new ParticipantDAO(connexion);
			retour = participantdao.getListPaticipant(idactivite);
			System.out.println(formatDate.format(new Date())
					+ ";getListParticipant;"
					+ (System.currentTimeMillis() - debut) + "ms");

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}

		return (Participant[]) retour.toArray(new Participant[retour.size()]);

	}

	public Preference[] getListPreferences(int idpersonne) {

		Connection connexion = null;
		ArrayList<Preference> retour = new ArrayList<Preference>();
		long debut = System.currentTimeMillis();

		try {
			connexion = CxoPool.getConnection();
			PreferenceDAO preferencedao = new PreferenceDAO(connexion);
			retour = preferencedao.getLisPreferences(idpersonne);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}
		System.out.println(formatDate.format(new Date())
				+ ";getListPreference;" + (System.currentTimeMillis() - debut)
				+ "ms");
		return (Preference[]) retour.toArray(new Preference[retour.size()]);

	}

	public ProfilDiscussion[] getListProfilDiscussion(int idpersonne[]) {
		Connection connexion = null;
		long debut = System.currentTimeMillis();
		ArrayList<ProfilDiscussion> listProfilDiscussion = new ArrayList<ProfilDiscussion>();

		try {

			connexion = CxoPool.getConnection();
			PersonneDAO personneDao = new PersonneDAO(connexion);
			listProfilDiscussion = personneDao
					.getListProfilDiscussion(idpersonne);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}

		System.out.println(formatDate.format(new Date())
				+ ";getListProfilDiscussion;"
				+ (System.currentTimeMillis() - debut) + "ms");
		return (ProfilDiscussion[]) listProfilDiscussion
				.toArray(new ProfilDiscussion[listProfilDiscussion.size()]);

	}

	public ProfilDiscussion getProfilDiscussion(int idpersonne) {
		Connection connexion = null;
		long debut = System.currentTimeMillis();

		try {

			connexion = CxoPool.getConnection();
			PersonneDAO personneDao = new PersonneDAO(connexion);

			System.out.println(formatDate.format(new Date())
					+ ";getProfilDiscussion;"
					+ (System.currentTimeMillis() - debut) + "ms");

			return personneDao.getProfilDiscussion(idpersonne);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {

			CxoPool.closeConnection(connexion);
		}

	}

	public Message[] getDiscussion(int iddestinataire, int idemetteur) {
		Connection connexion = null;
		long debut = System.currentTimeMillis();
		ArrayList<Message> listmessage = new ArrayList<Message>();

		try {

			connexion = CxoPool.getConnection();
			MessageDAO messagedao = new MessageDAO(connexion);
			listmessage = messagedao.getDiscussion(iddestinataire, idemetteur);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}

		System.out.println(formatDate.format(new Date()) + ";getDiscussion;"
				+ (System.currentTimeMillis() - debut) + "ms");

		return (Message[]) listmessage.toArray(new Message[listmessage.size()]);

	}

	public Message[] getDiscussionByAct(int iddestinataire, int idactivite) {

		Connection connexion = null;
		long debut = System.currentTimeMillis();
		ArrayList<Message> listmessage = new ArrayList<Message>();

		try {

			connexion = CxoPool.getConnection();
			MessageDAO messagedao = new MessageDAO(connexion);
			listmessage = messagedao.getDiscussionByAct(iddestinataire,
					idactivite);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}
		System.out.println(formatDate.format(new Date())
				+ ";getDiscussionByAct" + (System.currentTimeMillis() - debut)
				+ "ms");
		return (Message[]) listmessage.toArray(new Message[listmessage.size()]);

	}

	public Discussion[] getListDiscussion(int idpersonne) {
		Connection connexion = null;
		long debut = System.currentTimeMillis();
		ArrayList<Discussion> retour = new ArrayList<Discussion>();

		try {
			connexion = CxoPool.getConnection();
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
		}

		finally {
			CxoPool.closeConnection(connexion);
		}

		Collections.sort(retour, new DiscussionDateComparator());
		System.out.println(formatDate.format(new Date())
				+ ";getListDiscussion;" + (System.currentTimeMillis() - debut)
				+ "ms");
		return (Discussion[]) retour.toArray(new Discussion[retour.size()]);

	}

	public Notification[] getListNotification(int idpersonne) {
		ArrayList<Notification> retour = new ArrayList<Notification>();
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();
			NotificationDAO notificationdao = new NotificationDAO(connexion);
			retour = notificationdao.getListNotification(idpersonne);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}
		System.out.println(formatDate.format(new Date())
				+ ";getListNotification;"
				+ (System.currentTimeMillis() - debut) + "ms");
		return (Notification[]) retour.toArray(new Notification[retour.size()]);

	}

	// Permet de lire les messages aprés le message idxmessage
	public Message[] getListMessageAfter(int idpersonne, int idxmessage) {
		long debut = System.currentTimeMillis();
		ArrayList<Message> listmessage = new ArrayList<Message>();
		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();
			listmessage = new MessageDAO(connexion).getListMessageAfter(
					idpersonne, idxmessage);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}
		System.out.println(formatDate.format(new Date())
				+ ";getListMessageAfter;"
				+ (System.currentTimeMillis() - debut) + "ms");
		return (Message[]) listmessage.toArray(new Message[listmessage.size()]);

	}

	public Message[] getListMessageBefore(int iddestinataire, int idemetteur,
			int idxmessage) {

		long debut = System.currentTimeMillis();
		ArrayList<Message> listmessage = new ArrayList<Message>();

		Connection connexion = null;

		try {

			connexion = CxoPool.getConnection();
			listmessage = new MessageDAO(connexion).getListMessageBefore(
					iddestinataire, idemetteur, idxmessage);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}
		System.out.println(formatDate.format(new Date())
				+ ";getListMessageBefore;"
				+ (System.currentTimeMillis() - debut) + "ms");
		return (Message[]) listmessage.toArray(new Message[listmessage.size()]);

	}

	public Message[] getListMessageBeforeByAct(int iddestinataire,
			int activite, int idxmessage) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		ArrayList<Message> listmessage = new ArrayList<Message>();

		try {

			connexion = CxoPool.getConnection();
			listmessage = new MessageDAO(connexion).getListMessageBeforeByAct(
					iddestinataire, activite, idxmessage);
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}
		System.out.println(formatDate.format(new Date())
				+ ";getListMessageBeforeByAct;"
				+ (System.currentTimeMillis() - debut) + "ms");
		return (Message[]) listmessage.toArray(new Message[listmessage.size()]);

	}

	public Notification[] getListNotificationAfter(int idpersonne,
			int idxmessage) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		ArrayList<Notification> retour = new ArrayList<Notification>();

		try {
			connexion = CxoPool.getConnection();
			retour = new NotificationDAO(connexion).getListNotificationAfter(
					idpersonne, idxmessage);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}
		System.out.println(formatDate.format(new Date())
				+ ";getListNotificationAfter;"
				+ (System.currentTimeMillis() - debut) + "ms");
		return (Notification[]) retour.toArray(new Notification[retour.size()]);

	}

	public Message[] getListMessageAfterByAct(int idpersonne, int idxmessage,
			int idactivite) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		ArrayList<Message> listmessage = new ArrayList<Message>();

		try {
			connexion = CxoPool.getConnection();
			listmessage = new MessageDAO(connexion).getListMessageAfterByAct(
					idpersonne, idxmessage, idactivite);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			CxoPool.closeConnection(connexion);
		}
		System.out.println(formatDate.format(new Date())
				+ ";getListMessageAfterByAct;"
				+ (System.currentTimeMillis() - debut) + "ms");
		return (Message[]) listmessage.toArray(new Message[listmessage.size()]);

	}

	public Message[] getListMessageArchive(int idpersonne) {

		Connection connexion = null;
		ArrayList<Message> listmessage = new ArrayList<Message>();
		long debut = System.currentTimeMillis();
		try {
			connexion = CxoPool.getConnection();
			MessageDAO messagedao = new MessageDAO(connexion);
			listmessage = messagedao.getListMessageNonLu(idpersonne);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}
		System.out.println(formatDate.format(new Date())
				+ ";getListMessageArchive;"
				+ (System.currentTimeMillis() - debut) + "ms");
		return (Message[]) listmessage.toArray(new Message[listmessage.size()]);

	}

	public DefinitionPreference[] getListDefinitionPref(int idpersonne,
			int idtypeactivite) {
		Connection connexion = null;
		long debut = System.currentTimeMillis();
		ArrayList<DefinitionPreference> retour = new ArrayList<DefinitionPreference>();
		// System.out.print("getListDefinitionPref;");

		try {
			connexion = CxoPool.getConnection();
			DefinintionPreferenceDAO definitionpref = new DefinintionPreferenceDAO(
					connexion);

			ArrayList<DefinitionPreferenceDb> listDefPrefdb;
			listDefPrefdb = definitionpref.getListDefinitionPref(idpersonne,
					idtypeactivite);

			for (DefinitionPreferenceDb defPrefdb : listDefPrefdb)
				retour.add(new DefinitionPreference(defPrefdb));

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}

		System.out.println(formatDate.format(new Date())
				+ ";getListDefinitionPref;"
				+ (System.currentTimeMillis() - debut) + "ms");
		return (DefinitionPreference[]) retour
				.toArray(new DefinitionPreference[retour.size()]);

	}

	public static Activite getActivite(int idpersonne, int idactivite) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {
			// connexion = CxoPool.getConnection();
			connexion = CxoPool.getConnection();
			ActiviteDAO activitedao = new ActiviteDAO(connexion);
			Activite activite = activitedao.getActivite(idactivite);

			if (activite != null) {
				activite.defineOrganisateur(idpersonne);//
				activitedao.isInscrit(activite, idpersonne);

			}
			System.out.println(formatDate.format(new Date())
					+ ";getActiviteppp;" + (System.currentTimeMillis() - debut)
					+ "ms");
			return activite;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {

			CxoPool.closeConnection(connexion);

		}

	}

	public TableauBord getTableauBord(int idpersonne) {
		// System.out.println("Get Tableau de bord " + idpersonne);
		Connection connexion = null;
		TableauBord tableaubord;

		if (idpersonne == 0)
			return null;

		try {

			long debut = System.currentTimeMillis();
			connexion = CxoPool.getConnection();
			ActiviteDAO activitedao = new ActiviteDAO(connexion);
			tableaubord = activitedao.getTableauBord(idpersonne);

			System.out.println(formatDate.format(new Date())
					+ ";getTableauBord;" + (System.currentTimeMillis() - debut)
					+ "ms");
			return tableaubord;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public static IndicateurWayd getIndicateurs() {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		IndicateurWayd indicateurs;

		try {
			connexion = CxoPool.getConnection();
			ActiviteDAO activitedao = new ActiviteDAO(connexion);
			indicateurs = activitedao.getIndicateurs();

			System.out.println(formatDate.format(new Date())
					+ ";getIndicateurs;" + (System.currentTimeMillis() - debut)
					+ "ms");
			return indicateurs;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public Avis[] getListAvis(int idpersonnenotee) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		ArrayList<Avis> listavis = new ArrayList<Avis>();

		try {
			connexion = CxoPool.getConnection();
			AvisDAO avisdao = new AvisDAO(connexion);
			listavis = avisdao.getListAvis(idpersonnenotee);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			CxoPool.closeConnection(connexion);
		}
		System.out.println(formatDate.format(new Date()) + ";getListAvis;"
				+ (System.currentTimeMillis() - debut) + "ms");

		return (Avis[]) listavis.toArray(new Avis[listavis.size()]);

	}

	public static Preference[] getLisPreference(int idpersonne) {

		long debut = System.currentTimeMillis();
		ArrayList<Preference> listpreference = new ArrayList<Preference>();

		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();
			PreferenceDAO preferencedao = new PreferenceDAO(connexion);
			listpreference = preferencedao.getLisPreference(idpersonne);

		}

		catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}
		System.out.println(formatDate.format(new Date())
				+ ";getListPreference;" + (System.currentTimeMillis() - debut)
				+ "ms");
		return (Preference[]) listpreference
				.toArray(new Preference[listpreference.size()]);

	}

	public Activite[] getListActivitePref(int idpersonne) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		ArrayList<Activite> listActivite = new ArrayList<Activite>();

		try {
			connexion = CxoPool.getConnection();
			ActiviteDAO activitedao = new ActiviteDAO(connexion);
			listActivite = activitedao.getListActivitePref(idpersonne);

			for (Activite activite : listActivite) {
				activite.setOrganisateur(false);
			}

		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}

		System.out.println(formatDate.format(new Date())
				+ ";getListActivitePref;"
				+ (System.currentTimeMillis() - debut) + "ms");
		return (Activite[]) listActivite.toArray(new Activite[listActivite
				.size()]);

	}

	public Activite[] getListActiviteAvenir(int idpersonne, String latitudestr,
			String longitudestr, int rayon, int idtypeactivite, String motcle,
			int commencedans) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		// System.out.println("Rechercje des activites à venir dans un rayon de "
		// + rayon + "lat" + latitudestr + " lon:" + longitudestr
		// + " qui commence dans " + commencedans);

		ArrayList<Activite> listActivite = new ArrayList<Activite>();

		try {
			connexion = CxoPool.getConnection();
			ActiviteDAO activitedao = new ActiviteDAO(connexion);
			listActivite = activitedao.getListActiviteAvenir(
					Double.valueOf(latitudestr), Double.valueOf(longitudestr),
					rayon, idtypeactivite, motcle, commencedans);

			for (Activite activite : listActivite) {
				activite.defineOrganisateur(idpersonne);

			}

		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException | NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}
		System.out.println(formatDate.format(new Date())
				+ ";getListActiviteAvenir"
				+ (System.currentTimeMillis() - debut) + "ms");
		return (Activite[]) listActivite.toArray(new Activite[listActivite
				.size()]);

	}

	public Activite[] getListActiviteAvenirNocritere(int idpersonne,
			String latitudestr, String longitudestr, int rayon, String motcle,
			int commencedans) {

		Connection connexion = null;
		long debut = System.currentTimeMillis();

		ArrayList<Activite> listActivite = new ArrayList<Activite>();
		try {

			connexion = CxoPool.getConnection();
			ActiviteDAO activitedao = new ActiviteDAO(connexion);

			listActivite = activitedao.getListActiviteAvenirNocritere(
					Double.valueOf(latitudestr), Double.valueOf(longitudestr),
					rayon, motcle, commencedans);

			for (Activite activite : listActivite) {
				activite.defineOrganisateur(idpersonne);

			}

		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException | NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}
		System.out.println(formatDate.format(new Date())
				+ ";getListActiviteAvenirNoCritere;"
				+ (System.currentTimeMillis() - debut) + " ms");

		return (Activite[]) listActivite.toArray(new Activite[listActivite
				.size()]);

	}

	public Activite[] getMesActiviteEncours(int idpersonne) {
		long debut = System.currentTimeMillis();

		Connection connexion = null;
		ArrayList<Activite> listActivite = new ArrayList<Activite>();

		try {
			connexion = CxoPool.getConnection();
			ActiviteDAO activitedao = new ActiviteDAO(connexion);

			listActivite = activitedao.getMesActiviteEncours(idpersonne);

			for (Activite activite : listActivite)
				activite.defineOrganisateur();

			System.out.println(formatDate.format(new Date())
					+ ";getMesActiviteEnCours" + " en "
					+ (System.currentTimeMillis() - debut) + "ms");

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}

		return (Activite[]) listActivite.toArray(new Activite[listActivite
				.size()]);
	}

	public Activite[] getMesActiviteArchive(int idpersonne) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		ArrayList<Activite> listActivite = new ArrayList<Activite>();
		try {
			connexion = CxoPool.getConnection();
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
			// System.out.println("Perf"+Stats.getPerf());

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			CxoPool.closeConnection(connexion);
		}

		System.out.println(formatDate.format(new Date())
				+ ";getMesActiviteArchive"
				+ (System.currentTimeMillis() - debut) + " ms");

		return (Activite[]) listActivite.toArray(new Activite[listActivite
				.size()]);

	}

	public AvisaDonner[] getListAvisaDonner(int idpersonne) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		ArrayList<AvisaDonner> retour = new ArrayList<AvisaDonner>();

		try {
			connexion = CxoPool.getConnection();
			AvisaDonnerDAO avisadonner = new AvisaDonnerDAO(connexion);
			ArrayList<AvisaDonnerDb> listavisadonner;
			listavisadonner = avisadonner.getListAvisaDonner(idpersonne);

			for (AvisaDonnerDb avisadonnerdb : listavisadonner) {
				AvisaDonner avisadonne = new AvisaDonner(avisadonnerdb);
				retour.add(avisadonne);

			}
			System.out.println(formatDate.format(new Date())
					+ ";getListAvisaDonner"
					+ (System.currentTimeMillis() - debut) + "ms");

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}

		return (AvisaDonner[]) retour.toArray(new AvisaDonner[retour.size()]);

	}

	public TypeActivite[] getListTypeActivite() {

		Connection connexion = null;
		long debut = System.currentTimeMillis();
		ArrayList<TypeActivite> retour = new ArrayList<TypeActivite>();
		ArrayList<TypeActiviteDb> listtypeactivitedb;

		try {
			connexion = CxoPool.getConnection();
			TypeActiviteDAO typeactivitedao = new TypeActiviteDAO(connexion);
			listtypeactivitedb = typeactivitedao.getListTypeActivite();

			for (TypeActiviteDb typeactivitedb : listtypeactivitedb)
				retour.add(new TypeActivite(typeactivitedb));

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}
		System.out.println(formatDate.format(new Date())
				+ ";getListTypeActivite;"
				+ (System.currentTimeMillis() - debut) + "ms");

		return (TypeActivite[]) retour.toArray(new TypeActivite[retour.size()]);

	}

	public static MessageServeur addActivite(String titre, String libelle,
			int idorganisateur, int dureebalise, int idtypeactivite,
			String latitudestr, String longitudestr, String adresse,
			int nbmaxwaydeur, int dureeactivite, String jeton)
			throws ParseException {

		long debut = System.currentTimeMillis();

		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();
			ActiviteDAO activitedao = new ActiviteDAO(connexion);

			Droit droit = new PersonneDAO(connexion).getDroit(idorganisateur,
					jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			// Test les autorisation// pour l'ajout
			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return autorisation;
			}

			double latitude = Double.parseDouble(latitudestr);
			double longitude = Double.parseDouble(longitudestr);
			Date datedebut, datebalise, datefinActivite;
			// datedebut_ = Parametres.formatDateWs.parse(datedebutstr);
			datedebut = new Date();
			Calendar calBalise = Calendar.getInstance();
			calBalise.setTime(datedebut);
			calBalise.add(Calendar.MINUTE, dureebalise);
			datebalise = calBalise.getTime();
			Calendar calFinActivite = Calendar.getInstance();
			calFinActivite.setTime(datedebut);
			calFinActivite.add(Calendar.MINUTE, dureeactivite);
			datefinActivite = calFinActivite.getTime();

			// datebalise = Parametres.formatDateWs.parse(datefinstr);
			// datefinActivite =
			// Parametres.formatDateWs.parse(datefinactivitestr);

			if (activitedao.getNbrActiviteProposeEnCours(idorganisateur) == WBservices.NB_MAX_ACTIVITE) {
				return new MessageServeur(false,
						LibelleMessage.activiteOrganisee);
			}

			Activite activite = new Activite(titre, libelle, idorganisateur,
					datedebut, datebalise, idtypeactivite, latitude, longitude,
					adresse, true, nbmaxwaydeur, datefinActivite);
			// ****************Ajoute l'activite*****************************

			activitedao.addActivite(activite);

			final ArrayList<Personne> personneinteresse = activitedao
					.getListPersonneInterresse(activite);

			new Thread(new Runnable() {

				@Override
				public void run() {

					Connection connexiongcm = null;
					try {
						connexiongcm = CxoPool.getConnection();

						ServeurMethodes serveurmethode = new ServeurMethodes(
								connexiongcm);
						serveurmethode.gcmUpdateNbrActivite(idorganisateur);
						serveurmethode.gcmPushInterressByactivite(
								personneinteresse, activite.getId());
						serveurmethode
								.gcmUpdateNbrSuggestion(personneinteresse);
					
						PushNotifictionHelper.sendPushNotificationSuggestionList(personneinteresse,activite);

					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						CxoPool.closeConnection(connexiongcm);
					}

				}
			}).start();

			System.out.println(formatDate.format(new Date()) + ";Addactivite;"
					+ (System.currentTimeMillis() - debut) + "ms");

			return new MessageServeur(true, Integer.toString(activite.getId()));

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

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
			SuggestionDAO suggestiondao = new SuggestionDAO(connexion);

			Droit droit = new PersonneDAO(connexion)
					.getDroit(idpersonne, jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return autorisation;
			}

			suggestiondao.addSuggestion(idpersonne, suggestion);

			System.out.println(formatDate.format(new Date())
					+ ";addSugesstion;" + (System.currentTimeMillis() - debut)
					+ "ms");
			return new MessageServeur(true, LibelleMessage.ajouteSuggestion);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

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
			SuggestionDAO suggestiondao = new SuggestionDAO(connexion);
			suggestiondao.addPrbConnexion(probleme, email);
			System.out.println(formatDate.format(new Date())
					+ ";addPrbConnexion;"
					+ (System.currentTimeMillis() - debut) + "ms");
			return new MessageServeur(true, LibelleMessage.ajouteSuggestion);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		// return new MessageServeur(false, "Chaine vide non autorisée");

		Connection connexion = null;
		Message message = new Message(idemetteur, corps, 0, 0);

		try {
			connexion = CxoPool.getConnection();
			AmiDAO amidao = new AmiDAO(connexion);
			Droit droit = new PersonneDAO(connexion)
					.getDroit(idemetteur, jeton);

			if (droit == null)
				return new RetourMessage(new Date().getTime(),
						RetourMessage.NON_AUTORISE, idemetteur);

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
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

			new Thread(new Runnable() {

				@Override
				public void run() {

					Connection connexionGcm = null;
					try {
						connexionGcm = CxoPool.getConnection();
						new ServeurMethodes(connexionGcm)
								.gcmUpdateNbrMessage(iddestinataire);

					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						CxoPool.closeConnection(connexionGcm);
					}

					// TODO Auto-generated method stub

				}
			}).start();

			System.out.println(formatDate.format(new Date()) + ";addMessage;"
					+ (System.currentTimeMillis() - debut) + "ms");

			return new RetourMessage(new Date().getTime(), idmessage,
					idemetteur);
			// return new MessageServeur(true, "" + idmessage);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// return new MessageServeur(false, e.getMessage());
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
		// return new MessageServeur(false, "Chaine vide non autorisée");

		Connection connexion = null;

		Message message = new Message(idemetteur, corps, idactivite, 0);

		try {
			connexion = CxoPool.getConnection();
			ParticipationDAO participationdao = new ParticipationDAO(connexion);
			Droit droit = new PersonneDAO(connexion)
					.getDroit(idemetteur, jeton);

			if (droit == null)
				return new RetourMessage(new Date().getTime(),
						RetourMessage.NON_AUTORISE, idemetteur);
			// return new MessageServeur(false, "Tu n'es pas reconnu");

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return new RetourMessage(new Date().getTime(),
						RetourMessage.NON_AUTORISE, idemetteur);
				// return autorisation;
			}

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

			new Thread(new Runnable() {

				@Override
				public void run() {

					Connection connexionGcm = null;
					try {
						connexionGcm = CxoPool.getConnection();
						new ServeurMethodes(connexionGcm)
								.gcmUpdateNbrMessageByAct(listparticipant);

					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						CxoPool.closeConnection(connexionGcm);
					}

				}
			}).start();

			System.out.println(formatDate.format(new Date())
					+ ";addMessageByAct;"
					+ (System.currentTimeMillis() - debut) + "ms");
			return new RetourMessage(new Date().getTime(), idmessage,
					idemetteur);
			// return new MessageServeur(true, "" + idmessage);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

			// return new MessageServeur(false, e.getMessage());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			// return new MessageServeur(false, e.getMessage());
		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public MessageServeur effaceParticipation(int idAeffacer, int idactivite,
			int idorganisateur, int idDemandeur, String jeton) {

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
				return new MessageServeur(false, LibelleMessage.activiteFinie);
			if (activite.isTerminee())
				return new MessageServeur(false, "Activité terminéee");
				
			
			Droit droit = new PersonneDAO(connexion).getDroit(idDemandeur,
					jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return autorisation;
			}

			// ******************************************************

			ArrayList<Personne> listepersonne = participationdao
					.getListPartipantActivite(idactivite);

			discussiondao.effaceDiscussionActivite(idactivite, idAeffacer);
			// Efface les messages emis et recu par le demadeur
			// vidage des messages
			Personne personne = new PersonneDAO(connexion)
					.getPersonneId(idAeffacer);
			Message message;
			if (idDemandeur == idorganisateur) {

				message = new Message(idAeffacer,
						"L'organisateur à désinscrit " + personne.getPrenom(),
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
					idorganisateur);// Efface la particiaption attention a
									// l'orrde.
			new ActiviteDAO(connexion).updateChampCalcule(idactivite);

			new Thread(new Runnable() {
				@Override
				public void run() {

					Connection connexionGcm = null;
					try {
						connexionGcm = CxoPool.getConnection();
						new ServeurMethodes(connexionGcm)
								.envoiAndroidRefreshTdbAll(listepersonne); // TODO
																			// Auto-generated
						new ServeurMethodes(connexionGcm)
								.gcmAnnuleParticipation(idAeffacer, idactivite); // method

						new ServeurMethodes(connexionGcm)
								.gcmUpdateNbrMessageByAct(listepersonne);

						new ServeurMethodes(connexionGcm)
								.envoiAndroidUpdateActivite(listepersonne,
										idactivite);
						new ServeurMethodes(connexionGcm)
								.envoiAndroidUpdateNotification(listepersonne);

					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						CxoPool.closeConnection(connexionGcm);
					}
				}
			}).start();

			System.out.println(formatDate.format(new Date())
					+ ";effaceParticipation;"
					+ (System.currentTimeMillis() - debut) + "ms");
			return new MessageServeur(true,
					LibelleMessage.suppressionParicipation);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new MessageServeur(false, e.getMessage());

		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return new MessageServeur(false, e.getMessage());
		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public static MessageServeur effaceActivite(int idorganisateur, int idactivite,
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
				return new MessageServeur(false, LibelleMessage.activiteFinie);

			if (activite.isTerminee())
				return new MessageServeur(false, "L'activite est terminée");
		
			Droit droit = new PersonneDAO(connexion).getDroit(idorganisateur,
					jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageServeur autorisation = droit.isEffaceActivite(
					activitedao.getActivite(idactivite), idorganisateur);
			// System.out.println("test doit");

			if (!autorisation.isReponse()) {
				return autorisation;
			}

			// Recuepre les personnes interesse par cette activitée
			final ArrayList<Personne> personneinteresse = activitedao
					.getListPersonneInterresse(activitedao
							.getActivite(idactivite));

			ArrayList<Personne> listepersonne = participationdao
					.getListPartipantActivite(idactivite);

			activitedao.RemoveActivite(idactivite);
			notificationdao.addNotification(listepersonne,
					Notification.Supprime_Activite, 0, idorganisateur);
			discussiondao.effaceDiscussionTouteActivite(idactivite);

			new Thread(new Runnable() {

				@Override
				public void run() {
					Connection connexionGcm = null;
					try {
						connexionGcm = CxoPool.getConnection();
						ServeurMethodes serveurmethode = new ServeurMethodes(
								connexionGcm);

						ArrayList<Personne> listpersonneTotal = new ArrayList<Personne>();
						listpersonneTotal.addAll(listepersonne);
						listpersonneTotal.addAll(personneinteresse);

						serveurmethode
								.envoiAndroidRefreshTdbAll(listpersonneTotal);
						serveurmethode.gcmAnnuleActivite(listepersonne,
								idactivite);
						serveurmethode.gcmEffaceSuggestion(personneinteresse,
								idactivite);
						new ServeurMethodes(connexionGcm)
								.envoiAndroidUpdateNotification(listepersonne);

					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						CxoPool.closeConnection(connexionGcm);
					}

				}
			}).start();
			System.out.println(formatDate.format(new Date())
					+ ";effaceActivite;" + (System.currentTimeMillis() - debut)
					+ "ms");
			return new MessageServeur(true, LibelleMessage.suppressionActivite);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new MessageServeur(false, e.getMessage());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

			Droit droit = new PersonneDAO(connexion)
					.getDroit(idpersonne, jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return autorisation;
			}

			new MessageDAO(connexion).RemoveMessage(idpersonne, listmessage);

			new Thread(new Runnable() {

				@Override
				public void run() {

					Connection connexionGcm = null;
					try {
						connexionGcm = CxoPool.getConnection();
						new ServeurMethodes(connexionGcm)
								.gcmUpdateNbrMessage(idpersonne);

					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						CxoPool.closeConnection(connexionGcm);
					}

					// TODO Auto-generated method stub

				}
			}).start();
			System.out.println(formatDate.format(new Date())
					+ ";effaceMessage;" + (System.currentTimeMillis() - debut)
					+ "ms");
			return new MessageServeur(true, "Suppressin ok");
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

			Droit droit = new PersonneDAO(connexion)
					.getDroit(idpersonne, jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return autorisation;
			}

			// ******************************************************

			new MessageDAO(connexion).effaceMessageRecu(idpersonne, idmessage);

			new Thread(new Runnable() {

				@Override
				public void run() {
					Connection connexionGcm = null;
					try {
						connexionGcm = CxoPool.getConnection();
						new ServeurMethodes(connexionGcm)
								.gcmUpdateNbrMessage(idpersonne);

					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						CxoPool.closeConnection(connexionGcm);
					}

				}
			}).start();

			System.out.println(formatDate.format(new Date())
					+ ";effaceMessageRecu;"
					+ (System.currentTimeMillis() - debut) + "ms");
			return new MessageServeur(true, LibelleMessage.suppressionMessage);
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			Droit droit = new PersonneDAO(connexion)
					.getDroit(idpersonne, jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return autorisation;
			}

			// ******************************************************

			new MessageDAO(connexion).effaceMessageRecuByAct(idpersonne,
					idmessage);

			new Thread(new Runnable() {

				@Override
				public void run() {
					Connection connexionGcm = null;
					try {
						connexionGcm = CxoPool.getConnection();
						new ServeurMethodes(connexionGcm)
								.gcmUpdateNbrMessage(idpersonne);

					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						CxoPool.closeConnection(connexionGcm);
					}

				}
			}).start();

			System.out.println(formatDate.format(new Date())
					+ ";effaceMessageRecuByAct;"
					+ (System.currentTimeMillis() - debut) + "ms");
			return new MessageServeur(true, LibelleMessage.suppressionMessage);
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

			Droit droit = new PersonneDAO(connexion).getDroit(iddestinataire,
					jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return autorisation;
			}

			// ******************************************************

			new NotificationDAO(connexion).effaceNotification(iddestinataire,
					idnotification);

			new Thread(new Runnable() {

				@Override
				public void run() {
					Connection connexionGcm = null;
					try {
						connexionGcm = CxoPool.getConnection();
						new ServeurMethodes(connexionGcm)
								.gcmUpdateNotification(iddestinataire);

					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						CxoPool.closeConnection(connexionGcm);
					}
				}
			}).start();

			System.out.println(formatDate.format(new Date())
					+ ";effaceNotificationRecu;"
					+ (System.currentTimeMillis() - debut) + "ms");
			return new MessageServeur(true,
					LibelleMessage.suppressionNotifiaction);
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

			Droit droit = new PersonneDAO(connexion)
					.getDroit(idpersonne, jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return autorisation;
			}

			new AmiDAO(connexion).effaceAmi(idpersonne, idami);
			new NotificationDAO(connexion).addNotificationSupAmi(idpersonne,
					idami);

			new Thread(new Runnable() {

				@Override
				public void run() {
					Connection connexionGcm = null;
					try {
						connexionGcm = CxoPool.getConnection();
						ServeurMethodes serveurmethode = new ServeurMethodes(
								connexionGcm);
						serveurmethode.gcmUpdateNotification(idami);
						serveurmethode.gcmUpdateNbrAmi(idpersonne);
						serveurmethode.gcmUpdateNbrAmi(idami);
						new ServeurMethodes(connexionGcm)
								.envoiAndroidUpdateNotification(idpersonne);
						new ServeurMethodes(connexionGcm)
								.envoiAndroidUpdateNotification(idami);

					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						CxoPool.closeConnection(connexionGcm);
					}

				}
			}).start();
			System.out.println(formatDate.format(new Date()) + ";effaceAmi;"
					+ (System.currentTimeMillis() - debut) + "ms");
			return new MessageServeur(true, LibelleMessage.suppressionAmi);
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			Droit droit = new PersonneDAO(connexion)
					.getDroit(idpersonne, jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return autorisation;
			}

			new MessageDAO(connexion).effaceMessageEmisByAct(idpersonne,
					idmessage);

			new Thread(new Runnable() {

				@Override
				public void run() {

					Connection connexionGcm = null;

					try {
						connexionGcm = CxoPool.getConnection();
						new ServeurMethodes(connexionGcm)
								.gcmUpdateNbrMessage(idpersonne);

					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						CxoPool.closeConnection(connexionGcm);
					}

				}
			}).start();
			System.out.println(formatDate.format(new Date())
					+ ";effaceMessageEmisByAct;"
					+ (System.currentTimeMillis() - debut) + "ms");
			return new MessageServeur(true, LibelleMessage.suppressionMessage);
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			new MessageDAO(connexion).effaceMessageEmis(idpersonne, idmessage);

			new Thread(new Runnable() {

				@Override
				public void run() {

					Connection connexionGcm = null;
					try {
						connexionGcm = CxoPool.getConnection();
						new ServeurMethodes(connexionGcm)
								.gcmUpdateNbrMessage(idpersonne);

					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						CxoPool.closeConnection(connexionGcm);
					}

				}
			}).start();
			System.out.println(formatDate.format(new Date())
					+ ";effaceMessageEmis;"
					+ (System.currentTimeMillis() - debut) + "ms");
			return new MessageServeur(true, LibelleMessage.suppressionMessage);
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

			Droit droit = new PersonneDAO(connexion).getDroit(iddestinataire,
					jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return autorisation;
			}

			new MessageDAO(connexion).effaceDiscussion(iddestinataire,
					idemetteur);

			new Thread(new Runnable() {

				@Override
				public void run() {

					Connection connexionGcm = null;
					try {
						connexionGcm = CxoPool.getConnection();
						new ServeurMethodes(connexionGcm)
								.gcmUpdateNbrMessage(iddestinataire);

					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						CxoPool.closeConnection(connexionGcm);
					}
				}
			}).start();

			System.out.println(formatDate.format(new Date())
					+ ";effaceDiscussion;"
					+ (System.currentTimeMillis() - debut) + "ms");
			return new MessageServeur(true,
					LibelleMessage.suppressionDiscussion);
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			Droit droit = new PersonneDAO(connexion)
					.getDroit(idpersonne, jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return autorisation;
			}

			MessageDAO messagedao = new MessageDAO(connexion);
			messagedao.LitMessageDiscussion(idpersonne, idemetteur);

			new Thread(new Runnable() {

				@Override
				public void run() {
					Connection connexiongcm = null;

					try {
						connexiongcm = CxoPool.getConnection();
						new ServeurMethodes(connexiongcm)
								.gcmUpdateNbrMessage(idpersonne);

					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						CxoPool.closeConnection(connexiongcm);
					}

				}
			}).start();
			System.out.println(formatDate.format(new Date())
					+ ";acquitMessageDiscussion;"
					+ (System.currentTimeMillis() - debut) + "ms");
			return new MessageServeur(true,
					LibelleMessage.acquittementMessageDiscussion);
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			Droit droit = new PersonneDAO(connexion).getDroit(iddestinataire,
					jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return autorisation;
			}

			MessageDAO messagedao = new MessageDAO(connexion);
			messagedao.LitMessageDiscussionByAct(iddestinataire, idactivite);

			new Thread(new Runnable() {

				@Override
				public void run() {
					Connection connexiongcm = null;
					try {
						connexiongcm = CxoPool.getConnection();
						new ServeurMethodes(connexiongcm)
								.gcmUpdateNbrMessage(iddestinataire);

					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						CxoPool.closeConnection(connexiongcm);
					}

				}

			}).start();
			System.out.println(formatDate.format(new Date())
					+ ";acquitMessageDiscussionByAct;"
					+ (System.currentTimeMillis() - debut) + "ms");
			return new MessageServeur(true,
					LibelleMessage.acquittementMessageDiscussion);
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

			Droit droit = new PersonneDAO(connexion)
					.getDroit(idpersonne, jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return autorisation;
			}

			NotificationDAO notificationdao = new NotificationDAO(connexion);
			notificationdao.litNotification(idpersonne);

			new Thread(new Runnable() {

				@Override
				public void run() {
					Connection connexiongcm = null;
					try {
						connexiongcm = CxoPool.getConnection();
						new ServeurMethodes(connexiongcm)
								.gcmUpdateNotification(idpersonne);

					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						CxoPool.closeConnection(connexiongcm);
					}

				}
			}).start();

			System.out.println(formatDate.format(new Date())
					+ ";AcquitAllNotifications;"
					+ (System.currentTimeMillis() - debut) + "ms");

			return new MessageServeur(true,
					LibelleMessage.acquittementMessageDiscussion);
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

			Droit droit = new PersonneDAO(connexion)
					.getDroit(idpersonne, jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return autorisation;
			}

			MessageDAO messagedao = new MessageDAO(connexion);
			messagedao.LitMessage(idpersonne, idmessage);

			new Thread(new Runnable() {

				@Override
				public void run() {
					Connection connexiongcm = null;
					try {
						connexiongcm = CxoPool.getConnection();
						new ServeurMethodes(connexiongcm)
								.gcmUpdateNbrMessage(idpersonne);

					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						CxoPool.closeConnection(connexiongcm);
					}

				}
			}).start();

			System.out.println(formatDate.format(new Date())
					+ ";acquitMessage;" + (System.currentTimeMillis() - debut)
					+ "ms");

			return new MessageServeur(true,
					LibelleMessage.acquittementMessageDiscussion);
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			Droit droit = new PersonneDAO(connexion)
					.getDroit(idpersonne, jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageDAO messagedao = new MessageDAO(connexion);
			messagedao.LitMessageByAct(idpersonne, idmessage);

			new Thread(new Runnable() {

				@Override
				public void run() {
					Connection connexiongcm = null;
					try {
						connexiongcm = CxoPool.getConnection();
						new ServeurMethodes(connexiongcm)
								.gcmUpdateNbrMessage(idpersonne);
					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {

						CxoPool.closeConnection(connexiongcm);
					}

				}
			}).start();
			System.out.println(formatDate.format(new Date())
					+ ";acquitMessageByAct;"
					+ (System.currentTimeMillis() - debut) + "ms");

			return new MessageServeur(true,
					LibelleMessage.acquittementMessageDiscussion);
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

			Droit droit = new PersonneDAO(connexion)
					.getDroit(idpersonne, jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return autorisation;
			}

			NotificationDAO notificationdao = new NotificationDAO(connexion);
			notificationdao.LitNotification(idpersonne, idmessage);

			new Thread(new Runnable() {

				@Override
				public void run() {
					Connection connexiongcm = null;
					try {
						connexiongcm = CxoPool.getConnection();
						new ServeurMethodes(connexiongcm)
								.gcmUpdateNotification(idpersonne);

					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						CxoPool.closeConnection(connexiongcm);
					}

				}
			}).start();
			System.out.println(formatDate.format(new Date())
					+ ";acquitNotification;"
					+ (System.currentTimeMillis() - debut) + "ms");
			return new MessageServeur(true,
					LibelleMessage.acquittementMessageDiscussion);
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
						LibelleMessage.infoParticpationActivite);
			
			connexion = CxoPool.getConnection();
			ActiviteDAO activitedao = new ActiviteDAO(connexion);
			Activite activite = activitedao.getActivite(idactivite);

			
				
			Droit droit = new PersonneDAO(connexion).getDroit(iddemandeur,
					jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return autorisation;
			}

			if (activite == null)
				return new MessageServeur(false, LibelleMessage.activiteFinie);
			
			if (activite.isTerminee())
				return new MessageServeur(false, "Activité terminéee");
		
			if (activite.isComplete())
				return new MessageServeur(false,
						LibelleMessage.activiteComplete);

			if (activitedao.isInscrit(activite, iddemandeur)) {
				return new MessageServeur(false,
						LibelleMessage.activiteDejaInscrit);
			}

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

			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					Connection connexionGcm = null;
					try {
						connexionGcm = CxoPool.getConnection();
						new ServeurMethodes(connexionGcm)
								.envoiAndroidRefreshTdbAll(listparticipant);
						new ServeurMethodes(connexionGcm)
								.gcmUpdateNbrMessageByAct(listparticipant);

						new ServeurMethodes(connexionGcm)
								.envoiAndroidUpdateActivite(listparticipant,
										idactivite);

					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						CxoPool.closeConnection(connexionGcm);
					}

				}
			}).start();
			System.out.println(formatDate.format(new Date())
					+ ";AddParticipation;"
					+ (System.currentTimeMillis() - debut) + "ms");

			return new MessageServeur(true, LibelleMessage.activiteInscription);
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}

		return new MessageServeur(false,
				"ERREUR SURVENUE DANS METHODE addparticipation");

	}

	public MessageServeur addAvis(int idpersonne, int idpersonnenotee,
			int idactivite, String titre, String libelle, String notestr,
			boolean demandeami, String jeton) {
		long debut = System.currentTimeMillis();

		Connection connexion = null;
		double note = Double.parseDouble(notestr);

		try {

			connexion = CxoPool.getConnection();
			AvisDAO avisdao = new AvisDAO(connexion);
			NotificationDAO notificationdao = new NotificationDAO(connexion);

			Droit droit = new PersonneDAO(connexion)
					.getDroit(idpersonne, jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return autorisation;
			}

			avisdao.addAvis(idpersonnenotee, idactivite, titre, libelle, note);// Ajoute
			new PersonneDAO(connexion)
					.updateChampCalculePersonne(idpersonnenotee);
			avisdao.updateDemande(idpersonne, idpersonnenotee, idactivite,
					demandeami); //

			boolean ajoutami = avisdao.gestionAmi(idpersonne, idpersonnenotee,
					idactivite);

			notificationdao.removeNotificationAnoter(idpersonne,
					idpersonnenotee, idactivite);

			notificationdao.addNotification(idpersonnenotee,
					Notification.RecoitAvis, idactivite, idpersonne); //

			if (ajoutami) // Envoi au 2 personne le
				// fait quelles soient amies
				notificationdao.addNotificationAjoutAmi(idpersonnenotee,
						idactivite, idpersonne);

			new Thread(new Runnable() {

				@Override
				public void run() {

					Connection connexionGcm = null;
					try {
						connexionGcm = CxoPool.getConnection();
						new ServeurMethodes(connexionGcm)
								.envoiAndroidRefreshTDB(idpersonnenotee);
						;
						new ServeurMethodes(connexionGcm)
								.envoiAndroidRefreshTDB(idpersonne);

						new ServeurMethodes(connexionGcm)
								.envoiAndroidUpdateNotification(idpersonnenotee);

					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						CxoPool.closeConnection(connexionGcm);
					}

				}
			}).start();

			System.out.println(formatDate.format(new Date())
					+ ",Donne un avis en;"
					+ (System.currentTimeMillis() - debut) + " ms");

			return new MessageServeur(true, LibelleMessage.notationValidee);

		} catch (SQLException | NamingException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public static void genereJeu() {

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

			Droit droit = new PersonneDAO(connexion)
					.getDroit(idpersonne, jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return autorisation;
			}

			// ******************************************************

			personnedao.updateProfilWayd(idpersonne, photostr, nom, prenom,
					datenaissancestr, sexe, commentaire, afficheage,
					affichesexe);
			System.out.println(formatDate.format(new Date())
					+ ";updateProfilWayd;"
					+ (System.currentTimeMillis() - debut) + "ms");
			return new MessageServeur(true, LibelleMessage.profilMisAjour);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new MessageServeur(false, e.getMessage());
		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public static MessageServeur updatePseudo(String pseudo, Long datenaissance,
			int sexe, int idpersonne, String jeton) {
		long debut = System.currentTimeMillis();
		
		Connection connexion = null;
		String lowerpseudo = pseudo.toLowerCase();

		try {

			// SECURITE*************************************
			connexion = CxoPool.getConnection();
			PersonneDAO personnedao = new PersonneDAO(connexion);

			Droit droit = new PersonneDAO(connexion)
					.getDroit(idpersonne, jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return autorisation;
			}

			// ******************************************************

			if (personnedao.isPseudoExist(pseudo))
				return new MessageServeur(false, LibelleMessage.pseudoExist);

			personnedao.updatePseudo(lowerpseudo, datenaissance, sexe, jeton,
					idpersonne);
			System.out.println(formatDate.format(new Date()) + ";updatePseudo;"
					+ (System.currentTimeMillis() - debut) + "ms");
			return new MessageServeur(true, LibelleMessage.profilMisAjour);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new MessageServeur(false, e.getMessage());
		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	// ************************Permet de avoir si des notifications de notation
	// sont à pousser
	// *************************En effet les notification de notations
	// n'apparaissent qu'a la fin de l'activite
	// ************** Il n' y pas de thread sur le serveur d'appli c'est le
	// client qui sollicite toutes les 5 minutes

	public MessageServeur updateNotification(int idpersonne, String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		try {

			connexion = CxoPool.getConnection();
			NotificationDAO notificationdao = new NotificationDAO(connexion);
			notificationdao.addNotificationFromAvis(idpersonne);

			System.out.println(formatDate.format(new Date())
					+ ";UpdateNotification;"
					+ (System.currentTimeMillis() - debut) + "ms");
			
			new Thread(new Runnable() {

				@Override
				public void run() {

					Connection connexionGcm = null;
					try {
						connexionGcm = CxoPool.getConnection();
						new ServeurMethodes(connexionGcm)
								.envoiAndroidUpdateNotification(idpersonne);

					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						CxoPool.closeConnection(connexionGcm);
					}

				}
			}).start();
			
			
			return new MessageServeur(true, LibelleMessage.preferenceMisAjour);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new MessageServeur(false, e.getMessage());
		} finally {
			CxoPool.closeConnection(connexion);
		}
	}

	public MessageServeur updateNotificationPref(int idpersonne, String jeton,boolean notification) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		try {

			connexion = CxoPool.getConnection();
			PersonneDAO personneDAO = new PersonneDAO(connexion);
			personneDAO.updateNotificationPref(idpersonne,notification);

			System.out.println(formatDate.format(new Date())
					+ ";UpdateNotification;"
					+ (System.currentTimeMillis() - debut) + "ms");
			
	
			
			return new MessageServeur(true, LibelleMessage.preferenceMisAjour);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new MessageServeur(false, e.getMessage());
		} finally {
			CxoPool.closeConnection(connexion);
		}
	}

	
	public MessageServeur updatePosition(int idpersonne, String latitudestr,
			String longitudestr) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		try {

			connexion = CxoPool.getConnection();
			PersonneDAO personnedao = new PersonneDAO(connexion);
			personnedao.updatePosition(idpersonne, Double.valueOf(latitudestr),
					Double.valueOf(longitudestr));

			new Thread(new Runnable() {

				@Override
				public void run() {
					Connection connexionGcm = null;
					try {
						connexionGcm = CxoPool.getConnection();
						new ServeurMethodes(connexionGcm)
								.envoiAndroidRefreshTDB(idpersonne);

					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						CxoPool.closeConnection(connexionGcm);
					}

				}
			}).start();
			System.out.println(formatDate.format(new Date())
					+ ";updatePosition;" + (System.currentTimeMillis() - debut)
					+ "ms");
			return new MessageServeur(true, LibelleMessage.preferenceMisAjour);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			Droit droit = personnedao.getDroit(idpersonne, jeton);
			ParticipationDAO participationDAO= new ParticipationDAO(connexion);
			
			ArrayList<Personne> listpersonne=participationDAO.getListPartipantActiviteExpect(idactivite, idpersonne);
			
			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return autorisation;
			}

			// ******************************************************
			activitedao.updateActivite(idpersonne, libelle, titre, idactivite,
					nbrmax);

			
			
			new Thread(new Runnable() {
				@Override
				public void run() {

					Connection connexionGcm = null;
					try {
						connexionGcm = CxoPool.getConnection();
					

						new ServeurMethodes(connexionGcm)
								.envoiAndroidUpdateActivite(listpersonne,
										idactivite);// envoi la mise à jour à tous les participants sauf l'organisateur
						// sa mise à jour est en local.
						
					} catch (SQLException | NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						CxoPool.closeConnection(connexionGcm);
					}
				}
			}).start();
			
			
			
			
			System.out.println(formatDate.format(new Date())
					+ ";UpdateActivite;" + (System.currentTimeMillis() - debut)
					+ "ms");
			return new MessageServeur(true, LibelleMessage.activiteModifiee);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

			Droit droit = new PersonneDAO(connexion)
					.getDroit(idpersonne, jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return autorisation;
			}

			// ******************************************************

		
			personnedao.updateGCM(idpersonne, gcm);
			
			
			System.out.println(formatDate.format(new Date()) + ";updateGCM;"
					+ (System.currentTimeMillis() - debut) + "ms");
			return new MessageServeur(true, LibelleMessage.profilMisAjour);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new MessageServeur(false, e.getMessage());
		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public static Profil getFullProfil(int idpersonne) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();
			PersonneDAO personnedao = new PersonneDAO(connexion);
			Profil profil = personnedao.getFullProfil(idpersonne);
			System.out.println(formatDate.format(new Date())
					+ ";getFullProfil;" + (System.currentTimeMillis() - debut)
					+ "ms");

			return profil;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public ProfilNotation getProfilNotation(int notateur, int idpersonne,
			int idactivite) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();
			PersonneDAO personnedao = new PersonneDAO(connexion);
			ProfilNotation profil = personnedao.getProfilNotation(notateur,
					idpersonne, idactivite);

			System.out.println(formatDate.format(new Date())
					+ ";getProfilNotation"
					+ (System.currentTimeMillis() - debut) + "ms");
			return profil;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public Personne getPersonnebyToken(String idtoken) {
		Connection connexion = null;
		long debut = System.currentTimeMillis();
		try {
			connexion = CxoPool.getConnection();
			PersonneDAO personnedao = new PersonneDAO(connexion);

			Personne personne = personnedao.getPersonneJeton(idtoken);// Recherche

			// un

			if (personne == null) {
				return null;
			}

			if (personne != null) {
				personnedao.updateChampCalculePersonne(personne.getId());// calcule
																			// les
																			// champs
				personne.setMessage("Ok");

				Droit droit = new PersonneDAO(connexion).getDroit(
						personne.getId(), idtoken);

				if (droit == null) {
					personne.setMessage("Tu n'es pas reconnu");
					personne.setId(0);// echec connexion
					return personne;
				}

				MessageServeur autorisation = droit.isDefautAccess();

				if (!autorisation.isReponse()) {
					personne.setId(0);// echec connexion
					personne.setMessage("Ton compte n'est plus actif");
					return personne;
				}

				NotificationDAO notificationdao = new NotificationDAO(connexion);
				notificationdao.addNotificationFromAvis(personne.getId());

				// ******** Met les notifications existantes à pas lu **//
				String requete = "UPDATE  notification set lu=false "
						+ " WHERE iddestinataire=? and idtype=1";
				PreparedStatement preparedStatement = connexion
						.prepareStatement(requete);
				preparedStatement.setInt(1, personne.getId());
				preparedStatement.execute();
				preparedStatement.close();

				System.out.println(formatDate.format(new Date())
						+ ";getPersonneByToken;"
						+ (System.currentTimeMillis() - debut) + "ms");
				return personne;

			}

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {

			CxoPool.closeConnection(connexion);
		}

		return null;

	}

	public int test_getNbrActiviteEncours() {

		Connection connexion = null;
		int nbractivite = 0;
		try {
			connexion = CxoPool.getConnection();
			String requete = "Select count(idactivite) as nbractivite  FROM activite where  activite.d_finactivite>? ;";
			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);

			preparedStatement.setTimestamp(1,
					new java.sql.Timestamp(new Date().getTime()));
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				nbractivite = rs.getInt("nbractivite");
			}
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} finally {
			CxoPool.closeConnection(connexion);
		}

		return nbractivite;
	}

	public void tesgcm(String gmcToken){
		
		try {
			PushNotifictionHelper.sendPushNotificationTo(gmcToken);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int test_getPremierId() {

		Connection connexion = null;
		int premierId = 0;
		try {
			connexion = CxoPool.getConnection();
			String requete = "select min (idpersonne) as minid from personne";
			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				premierId = rs.getInt("minid");
			}
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} finally {
			CxoPool.closeConnection(connexion);
		}

		return premierId;
	}

	public int test_getDernierId() {

		Connection connexion = null;
		int dernierId = 0;
		try {
			connexion = CxoPool.getConnection();
			String requete = "select max (idpersonne) as maxid from personne";
			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				dernierId = rs.getInt("maxid");
			}
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} finally {
			CxoPool.closeConnection(connexion);
		}

		return dernierId;
	}

	public Personne test_GetPersonneAlea() {
		long debut = System.currentTimeMillis();

		Connection connexion = null;
		try {

			connexion = CxoPool.getConnection();
			PersonneDAO personnedao = new PersonneDAO(connexion);
			Personne personne = personnedao.test_GetPersonneAle();
			System.out.println(formatDate.format(new Date())
					+ "Cherche Personne alea "
					+ (System.currentTimeMillis() - debut) + "ms");

			return personne;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public String test_GetToken(int idpersonne) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();
			PersonneDAO personnedao = new PersonneDAO(connexion);

			System.out.println(formatDate.format(new Date()) + "Get Token:"
					+ (System.currentTimeMillis() - debut) + "ms");

			return personnedao.test_getToken(idpersonne);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}
		return null;
	}

	public void test_DonneAvis(int idpersonne, String jeton) {
		Connection connexion = null;
		try {
			connexion = CxoPool.getConnection();

			AvisaDonnerDAO avisadonner = new AvisaDonnerDAO(connexion);

			ArrayList<AvisaDonnerDb> listavisAdonner = avisadonner
					.getListAvisaDonner(idpersonne);
			if (listavisAdonner.size() != 0) {

				AvisaDonnerDb avis = listavisAdonner.get(new Random(System
						.currentTimeMillis()).nextInt(listavisAdonner.size()));
				int idpersonnenotee = avis.getIdpersonnenotee();
				int idactivite = avis.getIdactivite();
				addAvis(idpersonne, idpersonnenotee, idactivite, "Titre"
						+ System.currentTimeMillis(),
						"Libelle" + System.currentTimeMillis(), "3.2", true,
						jeton);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public void test_addCompte() {
		Connection connexion = null;

		try {
			// for (int f=0;f<500;f++)
			connexion = CxoPool.getConnection();
			PersonneDAO personnedao = new PersonneDAO(connexion);
			personnedao.TestaddCompteGenerique();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public MessageServeur signalerActivite(int idpersonne, int idactivite,
			int idmotif, String motif, String titre, String libelle,
			String jeton) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {

			connexion = CxoPool.getConnection();
			SignalementDAO signalementdao = new SignalementDAO(connexion);
			Droit droit = new PersonneDAO(connexion)
					.getDroit(idpersonne, jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return autorisation;
			}

			// Verfiie que le signalement est unique
			if (signalementdao.isSignalerActvite(idpersonne, idactivite))
				return new MessageServeur(false,
						LibelleMessage.activiteDejaSignale);

			signalementdao.signalerActivite(idpersonne, idactivite, idmotif,
					motif, titre, libelle);

			System.out.println(formatDate.format(new Date())
					+ "Signaler une activité;"
					+ (System.currentTimeMillis() - debut) + " ms");

			return new MessageServeur(true, LibelleMessage.activiteSignale);

		} catch (SQLException | NamingException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
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
			Droit droit = new PersonneDAO(connexion)
					.getDroit(idpersonne, jeton);

			if (droit == null)
				return new MessageServeur(false, LibelleMessage.pasReconnu);

			MessageServeur autorisation = droit.isDefautAccess();

			if (!autorisation.isReponse()) {
				return autorisation;
			}
			// Verfiie que le signalement est unique
			if (signalementdao.isSignalerProfil(idpersonne, idsignalement))
				return new MessageServeur(false, "Tu as déja signalé ce profil");

			signalementdao.signalerProfil(idpersonne, idsignalement, idmotif,
					motif);

			System.out.println(formatDate.format(new Date())
					+ "Signaler un profil;"
					+ (System.currentTimeMillis() - debut) + " ms");

			return new MessageServeur(true, LibelleMessage.profilSignale);

		} catch (SQLException | NamingException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	
	public Discussion[] getListDiscussionNew(int idpersonne) {
		Connection connexion = null;
		long debut = System.currentTimeMillis();
		ArrayList<Discussion> retour = new ArrayList<Discussion>();

		try {
			connexion = CxoPool.getConnection();
			MessageDAO messagedao = new MessageDAO(connexion);
			ArrayList<Discussion> listdiscussion = null;

			// RECUPERE LES MESSAGE DES ACTIVITES//

			for (Discussion discussion : new DiscussionDAO(connexion)
					.getArrayDiscussionByAct(idpersonne))
				if (discussion != null)
					retour.add(discussion);

			// RECUPERE jhLES MESSAGE DES AMIS//
			listdiscussion = messagedao.getListDiscussion(idpersonne);

			for (Discussion discussion : listdiscussion)
				retour.add(discussion);

		} catch (SQLException | NamingException e) {
			// TODO Auto-    nkljhgenerated catch block
			e.printStackTrace();
		}

		finally {
			CxoPool.closeConnection(connexion);
		}

		Collections.sort(retour, new DiscussionDateComparator());
		System.out.println(formatDate.format(new Date())
				+ ";getListDiscussion;" + (System.currentTimeMillis() - debut)
				+ "ms");
		
		return (Discussion[]) retour.toArray(new Discussion[retour.size()]);

	}

}
