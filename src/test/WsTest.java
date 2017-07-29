package test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.naming.NamingException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import wayd.ws.WBservices;
import wayde.bean.Activite;
import wayde.bean.Ami;
import wayde.bean.Avis;
import wayde.bean.CxoPool;
import wayde.bean.Discussion;
import wayde.bean.InfoNotation;
import wayde.bean.Message;
import wayde.bean.MessageServeur;
import wayde.bean.Notification;
import wayde.bean.Participant;
import wayde.bean.Personne;
import wayde.bean.Preference;
import wayde.bean.Profil;
import wayde.bean.RetourMessage;
import wayde.bean.TableauBord;
import wayde.bean.TypeActivite;
import wayde.dao.ActiviteDAO;
import wayde.dao.PersonneDAO;

public class WsTest {

	String uid = "uiduid";
	String uid1 = "uiduid1";
	String token = "token";
	String token1 = "token1";
	String photostr = "photostr";
	String nom = "nom";
	String nom1 = "nom1";
	String gcmToken = "gcmToken";
	String gcmToken1 = "gcmToken1";
	static final String corpMessageByAct = "testMessageByAct";
	static final String corpMessage = "testMessage";
	static final String libelleActivite = "Activite test";
	static Connection connexion;


	@Before
	public void init() throws SQLException {
		connexion = null;
		try {
			connexion = CxoPool.getConnection();
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String requete = "delete from noter";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.execute();
		preparedStatement.close();

		requete = "delete from ami";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.execute();
		preparedStatement.close();

		requete = "delete from notification";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.execute();
		preparedStatement.close();

		requete = "delete from demandeami";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.execute();
		preparedStatement.close();

		requete = "delete from participer";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.execute();
		preparedStatement.close();

		requete = "delete from messagebyact ";
		preparedStatement = connexion.prepareStatement(requete);

		preparedStatement.execute();
		preparedStatement.close();
		
		requete = "delete from message";
		preparedStatement = connexion.prepareStatement(requete);

		preparedStatement.execute();
		preparedStatement.close();

		requete = "delete from activite where libelle=?";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setString(1, libelleActivite);
		preparedStatement.execute();
		preparedStatement.close();

		requete = "delete from personne where personne.login like ?";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setString(1, uid + "%");
		preparedStatement.execute();
		preparedStatement.close();

	}

	@Test
	public void inscriptionAnnulation() throws SQLException, Exception {

		// Test - Creation 2 utilisateurs.
		// Inscription d'un participant et desincription par l'organisateur

		TypeActivite[] listTypeActivite = WBservices.getListTypeActivite();
		assertTrue(listTypeActivite != null);
		assertTrue(listTypeActivite.length != 0);

		PersonneDAO personnedao = new PersonneDAO(connexion);

		int idpersonne = 0;
		idpersonne = personnedao.addCompteGenerique(uid, token, photostr, nom,
				gcmToken);

		// *******************Verifice que la personne est crée

		assertTrue(idpersonne != 0);

		// Recupere la personne créée

		Personne personne = WBservices.getPersonnebyToken(token);

		assertTrue(personne != null);
		// Test les valeurs par défaut
		assertTrue(personne.isPremiereconnexion());
		assertTrue(personne.isNotification());
		assertTrue(personne.isActif());
		assertFalse(personne.isAdmin());

		// ******************** Premiere connexion

		WBservices.updatePseudo("tutu", new Date().getTime(), 0, idpersonne,
				token);

		personne = personnedao.getPersonneId(idpersonne);

		assertTrue(personne != null);
		assertFalse(personne.isPremiereconnexion());
		assertTrue(personne.isNotification());
		assertTrue(personne.isActif());
		assertFalse(personne.isAdmin());

		// recupere ses préférences

		Preference[] preferences = WBservices.getListPreferences(idpersonne,idpersonne,token);

		assertTrue(preferences != null);
		assertTrue(preferences.length == 8);

		// recupere son profil

		Profil profil = WBservices.getFullProfil(idpersonne,idpersonne,token);
		assertTrue(profil != null);
		assertTrue(profil.getId() == idpersonne);

		// Recupere ami

		Ami[] listAmi = WBservices.getListAmi(idpersonne,idpersonne,token);
		assertTrue(listAmi != null);

		// Recupere sa nottaton

		InfoNotation infoNotation = WBservices.getInfoNotation(idpersonne);
		assertTrue(infoNotation != null);

		// Recupere ses avis
		Avis[] listAvis = WBservices.getListAvis(idpersonne,idpersonne,token);
		assertTrue(listAvis != null);
		assertTrue(listAvis.length == 0);

		// Recupere ses discussion
		Discussion[] listDiscussion = WBservices.getListDiscussion(idpersonne,idpersonne,token);
		assertTrue(listDiscussion != null);
		assertTrue(listDiscussion.length == 0);

		// Recupere son TDB

		TableauBord tableauBord = WBservices.getTableauBord(idpersonne);
		assertTrue(tableauBord != null);
		assertTrue(tableauBord.getNbractiviteencours() == 0);

		
		MessageServeur messageServeur =null;

		// Recueper les activité en cours

		Activite[] activiteEnCours = WBservices
				.getMesActiviteEncours(idpersonne,idpersonne,token);
		assertTrue(activiteEnCours != null);
		assertTrue(activiteEnCours.length == 0);

		// met à jour le calcul des notifications

		messageServeur = WBservices.updateNotification(idpersonne, token);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		// Recueper les activité archivées

		Activite[] activiteArchies = WBservices
				.getMesActiviteArchive(idpersonne,idpersonne,token);
		assertTrue(activiteArchies != null);
		assertTrue(activiteArchies.length == 0);

		// Mets à jour les préfrence de diffusion de notification

		messageServeur = WBservices.updateNotificationPref(idpersonne, token,
				false);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		personne = WBservices.getPersonnebyToken(token);
		assertFalse(personne.isNotification());

		// ********************** Ajoute une activité

		messageServeur = WBservices.addActivite("test" + new Date().getTime(),
				libelleActivite, idpersonne, 90, 1, "42", "3", "adresse", 3,
				90, token);

		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());
		int idactivite = Integer.valueOf(messageServeur.getMessage());
		System.out.println("activite créee" + idactivite);

		// Verifie le nbr activite en cours

		activiteEnCours = WBservices.getMesActiviteEncours(idpersonne,idpersonne,token);
		assertTrue(activiteEnCours != null);
		assertTrue(activiteEnCours.length == 1);

		// Récupere le detail activité

		Activite activite = WBservices.getActivite(idpersonne, idactivite,token);

		assertTrue(activite != null);
		assertTrue(activite.getId() == idactivite);
		assertTrue(activite.getIdorganisateur() == idpersonne);

		// Récupere la liste des participants

		Participant[] participants = WBservices.getListParticipant(idpersonne,idactivite,token);
		assertTrue(participants != null);
		assertTrue(participants.length == 1);

		// Modifie l'activité

		messageServeur = WBservices.updateActivite(idpersonne, idactivite,
				"titres", libelleActivite, 5, token);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		// Verifie la mise à jour

		activite = WBservices.getActivite(idpersonne, idactivite,token);
		assertTrue(activite != null);
		assertTrue(activite.getId() == idactivite);
		assertTrue(activite.getIdorganisateur() == idpersonne);
		assertTrue(activite.getTitre().compareTo("titres") == 0);
		assertTrue(activite.getLibelle().compareTo(libelleActivite) == 0);
		assertTrue(activite.getNbmaxwaydeur() == 5);

		// Ajoute un message à l'activité

		RetourMessage retourMessage = WBservices.addMessageByAct(idpersonne,
				corpMessageByAct, idactivite, token);
		assertTrue(retourMessage != null);
		assertTrue(retourMessage.getId() != 0);

		int idmessage = retourMessage.getId();

		// Recupere les discussions de l'activité

		Message[] messages = WBservices.getDiscussionByAct(idpersonne,
				idactivite,token);
		assertTrue(messages != null);
		assertTrue(messages.length == 1);

		// Efface le message
		messageServeur = WBservices.effaceMessageEmisByAct(idpersonne,
				idmessage, token);

		// Verifie son l'effacement du message effacement

		messages = WBservices.getDiscussionByAct(idpersonne, idactivite,token);
		assertTrue(messages != null);
		assertTrue(messages.length == 0);

		// Verifie le nombre d'activité en cours 1 seule

		activiteEnCours = WBservices.getMesActiviteEncours(idpersonne,idpersonne,token);
		assertTrue(activiteEnCours != null);
		assertTrue(activiteEnCours.length == 1);

		// **************************** Creation du participant

		int idparticipant = 0;
		idparticipant = personnedao.addCompteGenerique(uid1, token1, photostr,
				nom1, gcmToken1);

		// *******************Verifice que la personne est crée

		assertTrue(idparticipant != 0);

		// *********************** Recupere la personne créée

		Personne participant = WBservices.getPersonnebyToken(token1);

		assertTrue(personne != null);
		// Test les valeurs par défaut
		assertTrue(participant.isPremiereconnexion());
		assertTrue(participant.isNotification());
		assertTrue(participant.isActif());
		assertFalse(participant.isAdmin());

		// ************************* Premiere connexion

		WBservices.updatePseudo("tutu1", new Date().getTime(), 0,
				idparticipant, token1);

		participant = personnedao.getPersonneId(idparticipant);
		assertTrue(participant != null);
		assertFalse(participant.isPremiereconnexion());
		assertTrue(participant.isNotification());
		assertTrue(participant.isActif());
		assertFalse(participant.isAdmin());

		// ***************AJOUT DU PARTICIPANT A L'ACTIVITE

		messageServeur = WBservices.addParticipation(idparticipant, idpersonne,
				idactivite, token1);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		// Verifie le nombre de participant

		participants = WBservices.getListParticipant(idpersonne,idactivite,token);
		assertTrue(participants != null);
		assertTrue(participants.length == 2);

		// Verifie le nombre de participant de l'activité
		activite = WBservices.getActivite(idpersonne, idactivite,token);

		assertTrue(activite != null);
		assertTrue(activite.getNbrparticipant() == 2);

		// Recupere la list des activite de l'oragisateur

		activiteEnCours = WBservices.getMesActiviteEncours(idpersonne,idpersonne,token);
		assertTrue(activiteEnCours != null);
		assertTrue(activiteEnCours.length == 1);

		// Recupere la list des activites du participant

		activiteEnCours = WBservices.getMesActiviteEncours(idparticipant,idparticipant,token1);
		assertTrue(activiteEnCours != null);
		assertTrue(activiteEnCours.length == 1);

		// Ajoute un message à l'activité

		retourMessage = WBservices.addMessageByAct(idpersonne,
				corpMessageByAct, idactivite, token);
		assertTrue(retourMessage != null);
		assertTrue(retourMessage.getId() != 0);

		idmessage = retourMessage.getId();

		// Le participant récupere la discussion de l'activité.

		messages = WBservices.getDiscussionByAct(idparticipant, idactivite,token1);
		assertTrue(messages != null);

		// acquit le message

		messageServeur = WBservices.acquitMessageByAct(idparticipant,
				idmessage, token1);
		assertTrue(messageServeur != null);

		// acquit tous les messages de tous les participants

		messageServeur = WBservices.acquitMessageDiscussionByAct(idpersonne,
				idactivite, token);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		messageServeur = WBservices.acquitMessageDiscussionByAct(idparticipant,
				idactivite, token1);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		// Verifie que le participant à une discussion
		listDiscussion = WBservices.getListDiscussion(idparticipant,idparticipant,token1);
		assertTrue(listDiscussion != null);
		assertTrue(listDiscussion.length == 1);

		// l'organisateur annule la partcipation du participant

		messageServeur = WBservices.effaceParticipation(idpersonne,
				idparticipant, idactivite, token);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		// Verifie que le participant n'a plus de discussion
		listDiscussion = WBservices.getListDiscussion(idparticipant,idparticipant,token1);
		assertTrue(listDiscussion != null);
		assertTrue(listDiscussion.length == 0);

		try {
			connexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void inscriptionNotation() throws SQLException, Exception {

		// Test - Creation 2 utilisateurs.
		// Inscription d'un participant et desincription par l'organisateur

		TypeActivite[] listTypeActivite = WBservices.getListTypeActivite();
		assertTrue(listTypeActivite != null);
		assertTrue(listTypeActivite.length != 0);

		PersonneDAO personnedao = new PersonneDAO(connexion);

		int idpersonne = 0;
		idpersonne = personnedao.addCompteGenerique(uid, token, photostr, nom,
				gcmToken);

		// *******************Verifice que la personne est crée

		assertTrue(idpersonne != 0);

		// Recupere la personne créée

		Personne personne = WBservices.getPersonnebyToken(token);

		assertTrue(personne != null);
		// Test les valeurs par défaut
		assertTrue(personne.isPremiereconnexion());
		assertTrue(personne.isNotification());
		assertTrue(personne.isActif());
		assertFalse(personne.isAdmin());

		// ******************** Premiere connexion

		WBservices.updatePseudo("tutu", new Date().getTime(), 0, idpersonne,
				token);

		personne = personnedao.getPersonneId(idpersonne);

		assertTrue(personne != null);
		assertFalse(personne.isPremiereconnexion());
		assertTrue(personne.isNotification());
		assertTrue(personne.isActif());
		assertFalse(personne.isAdmin());

		// recupere ses préférences

		Preference[] preferences = WBservices.getListPreferences(idpersonne,idpersonne,token);

		assertTrue(preferences != null);
		assertTrue(preferences.length == 8);

		// recupere son profil

		Profil profil = WBservices.getFullProfil(idpersonne,idpersonne,token);
		assertTrue(profil != null);
		assertTrue(profil.getId() == idpersonne);

		// Recupere ami

		Ami[] listAmi = WBservices.getListAmi(idpersonne,idpersonne,token);
		assertTrue(listAmi != null);

		// Recupere sa nottaton

		InfoNotation infoNotation = WBservices.getInfoNotation(idpersonne);
		assertTrue(infoNotation != null);

		// Recupere ses avis
		Avis[] listAvis = WBservices.getListAvis(idpersonne,idpersonne,token);
		assertTrue(listAvis != null);
		assertTrue(listAvis.length == 0);

		// Recupere ses discussion
		Discussion[] listDiscussion = WBservices.getListDiscussion(idpersonne,idpersonne,token);
		assertTrue(listDiscussion != null);
		assertTrue(listDiscussion.length == 0);

		// Recupere son TDB

		TableauBord tableauBord = WBservices.getTableauBord(idpersonne);
		assertTrue(tableauBord != null);
		assertTrue(tableauBord.getNbractiviteencours() == 0);

		// Test un ami inexistant
		MessageServeur messageServeur = null;

		// Recueper les activité en cours

		Activite[] activiteEnCours = WBservices
				.getMesActiviteEncours(idpersonne,idpersonne,token);
		assertTrue(activiteEnCours != null);
		assertTrue(activiteEnCours.length == 0);

		// met à jour le calcul des notifications

		messageServeur = WBservices.updateNotification(idpersonne, token);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		// Recueper les activité archivées

		Activite[] activiteArchies = WBservices
				.getMesActiviteArchive(idpersonne,idpersonne,token);
		assertTrue(activiteArchies != null);
		assertTrue(activiteArchies.length == 0);

		// Mets à jour les préfrence de diffusion de notification

		messageServeur = WBservices.updateNotificationPref(idpersonne, token,
				false);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		personne = WBservices.getPersonnebyToken(token);
		assertFalse(personne.isNotification());

		// ********************** Ajoute une activité

		messageServeur = WBservices.addActivite("test" + new Date().getTime(),
				libelleActivite, idpersonne, 90, 1, "42", "3", "adresse", 3,
				90, token);

		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());
		int idactivite = Integer.valueOf(messageServeur.getMessage());
		System.out.println("activite créee" + idactivite);

		// Verifie le nbr activite en cours

		activiteEnCours = WBservices.getMesActiviteEncours(idpersonne,idpersonne,token);
		assertTrue(activiteEnCours != null);
		assertTrue(activiteEnCours.length == 1);

		// Récupere le detail activité

		Activite activite = WBservices.getActivite(idpersonne, idactivite,token);

		assertTrue(activite != null);
		assertTrue(activite.getId() == idactivite);
		assertTrue(activite.getIdorganisateur() == idpersonne);

		// Récupere la liste des participants

		Participant[] participants = WBservices.getListParticipant(idpersonne,idactivite,token);
		assertTrue(participants != null);
		assertTrue(participants.length == 1);

		// Modifie l'activité

		messageServeur = WBservices.updateActivite(idpersonne, idactivite,
				"titres", libelleActivite, 5, token);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		// Verifie la mise à jour

		activite = WBservices.getActivite(idpersonne, idactivite,token);
		assertTrue(activite != null);
		assertTrue(activite.getId() == idactivite);
		assertTrue(activite.getIdorganisateur() == idpersonne);
		assertTrue(activite.getTitre().compareTo("titres") == 0);
		assertTrue(activite.getLibelle().compareTo(libelleActivite) == 0);
		assertTrue(activite.getNbmaxwaydeur() == 5);

		// Ajoute un message à l'activité

		RetourMessage retourMessage = WBservices.addMessageByAct(idpersonne,
				corpMessageByAct, idactivite, token);
		assertTrue(retourMessage != null);
		assertTrue(retourMessage.getId() != 0);

		int idmessage = retourMessage.getId();

		// Recupere les discussions de l'activité

		Message[] messages = WBservices.getDiscussionByAct(idpersonne,
				idactivite,token);
		assertTrue(messages != null);
		assertTrue(messages.length == 1);

		// Efface le message
		messageServeur = WBservices.effaceMessageEmisByAct(idpersonne,
				idmessage, token);

		// Verifie son l'effacement du message effacement

		messages = WBservices.getDiscussionByAct(idpersonne, idactivite,token);
		assertTrue(messages != null);
		assertTrue(messages.length == 0);

		// Verifie le nombre d'activité en cours 1 seule

		activiteEnCours = WBservices.getMesActiviteEncours(idpersonne,idpersonne,token);
		assertTrue(activiteEnCours != null);
		assertTrue(activiteEnCours.length == 1);

		// **************************** Creation du participant

		int idparticipant = 0;
		idparticipant = personnedao.addCompteGenerique(uid1, token1, photostr,
				nom1, gcmToken1);

		// *******************Verifice que la personne est crée

		assertTrue(idparticipant != 0);

		// *********************** Recupere la personne créée

		Personne participant = WBservices.getPersonnebyToken(token1);

		assertTrue(personne != null);
		// Test les valeurs par défaut
		assertTrue(participant.isPremiereconnexion());
		assertTrue(participant.isNotification());
		assertTrue(participant.isActif());
		assertFalse(participant.isAdmin());

		// ************************* Premiere connexion

		WBservices.updatePseudo("tutu1", new Date().getTime(), 0,
				idparticipant, token1);

		participant = personnedao.getPersonneId(idparticipant);
		assertTrue(participant != null);
		assertFalse(participant.isPremiereconnexion());
		assertTrue(participant.isNotification());
		assertTrue(participant.isActif());
		assertFalse(participant.isAdmin());

		// ***************AJOUT DU PARTICIPANT A L'ACTIVITE

		messageServeur = WBservices.addParticipation(idparticipant, idpersonne,
				idactivite, token1);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());
		
		// Verifie le nombre d'activite en cours pour chacun doit être égale à un pour les 2
		
		Activite[] listActiviteEncours=WBservices.getMesActiviteEncours(idpersonne,idpersonne,token);
		assertTrue(listActiviteEncours != null);
		assertTrue(listActiviteEncours.length==1);	
		
		listActiviteEncours=WBservices.getMesActiviteEncours(idparticipant,idparticipant,token1);
		assertTrue(listActiviteEncours != null);
		assertTrue(listActiviteEncours.length==1);	

		// Verifie le nombre de participant

		participants = WBservices.getListParticipant(idpersonne,idactivite,token);
		assertTrue(participants != null);
		assertTrue(participants.length == 2);

		// Verifie le nombre de participant de l'activité
		activite = WBservices.getActivite(idpersonne, idactivite,token);

		assertTrue(activite != null);
		assertTrue(activite.getNbrparticipant() == 2);

		// Recupere la list des activite de l'oragisateur

		activiteEnCours = WBservices.getMesActiviteEncours(idpersonne,idpersonne,token);
		assertTrue(activiteEnCours != null);
		assertTrue(activiteEnCours.length == 1);

		// Recupere la list des activites du participant

		activiteEnCours = WBservices.getMesActiviteEncours(idparticipant,idparticipant,token1);
		assertTrue(activiteEnCours != null);
		assertTrue(activiteEnCours.length == 1);

		// Ajoute un message à l'activité

		retourMessage = WBservices.addMessageByAct(idpersonne,
				corpMessageByAct, idactivite, token);
		assertTrue(retourMessage != null);
		assertTrue(retourMessage.getId() != 0);

		idmessage = retourMessage.getId();

		// Le participant récupere la discussion de l'activité.

		messages = WBservices.getDiscussionByAct(idparticipant, idactivite,token1);
		assertTrue(messages != null);

		// acquit le message

		messageServeur = WBservices.acquitMessageByAct(idparticipant,
				idmessage, token1);
		assertTrue(messageServeur != null);

		// acquit tous les messages de tous les participants

		messageServeur = WBservices.acquitMessageDiscussionByAct(idpersonne,
				idactivite, token);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		messageServeur = WBservices.acquitMessageDiscussionByAct(idparticipant,
				idactivite, token1);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		// Verifie que le participant à une discussion
		listDiscussion = WBservices.getListDiscussion(idparticipant,idparticipant,token1);
		assertTrue(listDiscussion != null);
		assertTrue(listDiscussion.length == 1);

		// Terminie l'activite
		 ActiviteDAO.terminerActivite(idactivite);

		// Pause terminer une activite revient à la mettre à la date du jour.
		// On fait une pause avant de lancer la mise à jour des notifications.
		
		// On essaie d'envyer un message - Il ne sont pas ami
			
		retourMessage=WBservices.addMessage(idpersonne, "message", idparticipant, token);
		assertTrue(retourMessage != null);
		assertTrue(retourMessage.getId()==RetourMessage.PLUS_SON_AMI);	
			
		retourMessage=WBservices.addMessage(idparticipant, "message", idpersonne, token1);
		assertTrue(retourMessage != null);
		assertTrue(retourMessage.getId()==RetourMessage.PLUS_SON_AMI);	
	
		// met à jour les notifications (demande de notation)
		messageServeur = WBservices.updateNotification(idpersonne, token);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		// Verifie la creation de la notation pour l'orgainsateur
		Notification[] listNotification = WBservices
				.getListNotification(idpersonne,idpersonne,token);
		assertTrue(listNotification != null);
		assertTrue(listNotification.length == 1);

		// met à jour les notifications (demande de notation) pour le
		// participant

		messageServeur = WBservices.updateNotification(idparticipant, token1);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		// met à jour les notifications (demande de notation) pour le
		// participant
		listNotification = WBservices.getListNotification(idpersonne,idpersonne,token);
		assertTrue(listNotification != null);
		assertTrue(listNotification.length == 1);

		// L'organisateur note le participant

		messageServeur = WBservices.addAvis(idpersonne, idparticipant,
				idactivite, "titre", "cool", "3.2", true, token);

		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		// Le participant note l'organisateur

		messageServeur = WBservices.addAvis(idparticipant, idpersonne,
				idactivite, "titre", "cool", "3.1", true, token1);

		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		// Le participant et l'oranisateur ont maintenant 2 notifications
		listNotification = WBservices.getListNotification(idpersonne,idpersonne,token);
		assertTrue(listNotification != null);
		assertTrue(listNotification.length == 2);

		listNotification = WBservices.getListNotification(idparticipant,idparticipant,token1);
		assertTrue(listNotification != null);
		assertTrue(listNotification.length == 2);

		// acquitement de toutes les notifications de l'organisteur

		messageServeur = WBservices.acquitAllNotification(idpersonne, token);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		// acquitement de toutes les notifications de participant

		messageServeur = WBservices
				.acquitAllNotification(idparticipant, token1);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		// efface de toutes les notifications de l'organisteur

		listNotification = WBservices.getListNotification(idpersonne,idpersonne,token);

		for (Notification notification : listNotification) {

			messageServeur = WBservices.effaceNotificationRecu(idpersonne,
					notification.getIdnotification(), token);
			assertTrue(messageServeur != null);
			assertTrue(messageServeur.isReponse());

		}
		
		// Verifie l'effacement
		listNotification = WBservices.getListNotification(idpersonne,idpersonne,token);
		assertTrue(listNotification != null);
		assertTrue(listNotification.length==0);


		// efface de toutes les notifications du participant

		listNotification = WBservices.getListNotification(idparticipant,idparticipant,token1);

		for (Notification notification : listNotification) {

			messageServeur = WBservices.effaceNotificationRecu(idparticipant,
					notification.getIdnotification(), token1);
			assertTrue(messageServeur != null);
			assertTrue(messageServeur.isReponse());

		}
		
		// Verifie l'effacement
		
		listNotification = WBservices.getListNotification(idparticipant,idparticipant,token1);
		assertTrue(listNotification != null);
		assertTrue(listNotification.length==0);
		
		// Verifie pas de messages au départ.
	
		messages= WBservices.getDiscussion(idpersonne, idparticipant,token);
		assertTrue(messages != null);
		assertTrue(messages.length==0);	
		
		messages= WBservices.getDiscussion( idparticipant,idpersonne,token1);
		assertTrue(messages != null);
		assertTrue(messages.length==0);	
		
		// Envoi de message 
		
		retourMessage=WBservices.addMessage(idpersonne, "message", idparticipant, token);
		assertTrue(retourMessage != null);
		assertTrue(retourMessage.getId()>0);	
			
		
		retourMessage=WBservices.addMessage(idparticipant, "message", idpersonne, token1);
		assertTrue(retourMessage != null);
		assertTrue(retourMessage.getId()>0);	
		
		
		// Verifie le nobmr de message 2 au total (1 envoye 1 recu).
		messages= WBservices.getDiscussion(idpersonne, idparticipant,token);
		assertTrue(messages != null);
		assertTrue(messages.length==2);	
		
		messages= WBservices.getDiscussion( idparticipant,idpersonne,token1);
		assertTrue(messages != null);
		assertTrue(messages.length==2);	
		
	
		messageServeur=WBservices.acquitMessageDiscussion(idpersonne, idparticipant, token);
		messageServeur=WBservices.acquitMessageDiscussion(idparticipant, idpersonne, token1);
			
		
		// Regarde ses archives
		
		Activite[] listActiviteArchives=WBservices.getMesActiviteArchive(idpersonne,idpersonne,token);
		assertTrue(listActiviteArchives != null);
		assertTrue(listActiviteArchives.length==1);	
		
		listActiviteArchives=WBservices.getMesActiviteArchive(idparticipant,idparticipant,token1);
		assertTrue(listActiviteArchives != null);
		assertTrue(listActiviteArchives.length==1);	
		
		// Activite en cours ==0
		
		listActiviteEncours=WBservices.getMesActiviteEncours(idpersonne,idpersonne,token);
		assertTrue(listActiviteEncours != null);
		assertTrue(listActiviteEncours.length==0);	
		
		listActiviteEncours=WBservices.getMesActiviteEncours(idparticipant,idparticipant,token1);
		assertTrue(listActiviteEncours != null);
		assertTrue(listActiviteEncours.length==0);	
		
		
		// Verifie la liste d'ami
		
		listAmi=WBservices.getListAmi(idpersonne,idpersonne,token);
		assertTrue(listAmi != null);
		assertTrue(listAmi.length==1);
		assertTrue(listAmi[0].getId()==idparticipant);	
		
		listAmi=WBservices.getListAmi(idparticipant,idparticipant,token1);
		assertTrue(listAmi != null);
		assertTrue(listAmi.length==1);
		assertTrue(listAmi[0].getId()==idpersonne);	
	
	 
		// l'effacement d'un ami l'efface des 2 cotes
		
		messageServeur=WBservices.effaceAmi(idpersonne, idparticipant, token);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());
		listAmi=WBservices.getListAmi(idpersonne,idpersonne,token);
		assertTrue(listAmi != null);
		assertTrue(listAmi.length==0);
	
		listAmi=WBservices.getListAmi(idparticipant,idparticipant,token1);
		assertTrue(listAmi != null);
		assertTrue(listAmi.length==0);
		
		
		try {
			connexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	@Test
	public void rechercheActivite() throws SQLException, Exception {

		// Test - Creation 2 utilisateurs.
		// recherce et inscritpion

		
		TypeActivite[] listTypeActivite = WBservices.getListTypeActivite();
		assertTrue(listTypeActivite != null);
		assertTrue(listTypeActivite.length != 0);

		PersonneDAO personnedao = new PersonneDAO(connexion);

		int idpersonne = 0;
		idpersonne = personnedao.addCompteGenerique(uid, token, photostr, nom,
				gcmToken);

		// *******************Verifice que la personne est crée

		assertTrue(idpersonne != 0);

		// Recupere la personne créée

		Personne personne = WBservices.getPersonnebyToken(token);

		assertTrue(personne != null);
		// Test les valeurs par défaut
		assertTrue(personne.isPremiereconnexion());
		assertTrue(personne.isNotification());
		assertTrue(personne.isActif());
		assertFalse(personne.isAdmin());

		// ******************** Premiere connexion

		WBservices.updatePseudo("tutu", new Date().getTime(), 0, idpersonne,
				token);

		personne = personnedao.getPersonneId(idpersonne);

		assertTrue(personne != null);
		assertFalse(personne.isPremiereconnexion());
		assertTrue(personne.isNotification());
		assertTrue(personne.isActif());
		assertFalse(personne.isAdmin());

		// recupere ses préférences

		Preference[] preferences = WBservices.getListPreferences(idpersonne,idpersonne,token);

		assertTrue(preferences != null);
		assertTrue(preferences.length == 8);

		// recupere son profil

		Profil profil = WBservices.getFullProfil(idpersonne,idpersonne,token);
		assertTrue(profil != null);
		assertTrue(profil.getId() == idpersonne);

		// Recupere ami

		Ami[] listAmi = WBservices.getListAmi(idpersonne,idpersonne,token);
		assertTrue(listAmi != null);

		// Recupere sa nottaton

		InfoNotation infoNotation = WBservices.getInfoNotation(idpersonne);
		assertTrue(infoNotation != null);

		// Recupere ses avis
		Avis[] listAvis = WBservices.getListAvis(idpersonne,idpersonne,token);
		assertTrue(listAvis != null);
		assertTrue(listAvis.length == 0);

		// Recupere ses discussion
		Discussion[] listDiscussion = WBservices.getListDiscussion(idpersonne,idpersonne,token);
		assertTrue(listDiscussion != null);
		assertTrue(listDiscussion.length == 0);

		// Recupere son TDB

		TableauBord tableauBord = WBservices.getTableauBord(idpersonne);
		assertTrue(tableauBord != null);
		assertTrue(tableauBord.getNbractiviteencours() == 0);

	
		MessageServeur messageServeur =null;

		// Recueper les activité en cours

		Activite[] activiteEnCours = WBservices
				.getMesActiviteEncours(idpersonne,idpersonne,token);
		assertTrue(activiteEnCours != null);
		assertTrue(activiteEnCours.length == 0);

		// met à jour le calcul des notifications

		messageServeur = WBservices.updateNotification(idpersonne, token);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		// Recueper les activité archivées

		Activite[] activiteArchies = WBservices
				.getMesActiviteArchive(idpersonne,idpersonne,token);
		assertTrue(activiteArchies != null);
		assertTrue(activiteArchies.length == 0);

		// Mets à jour les préfrence de diffusion de notification

		messageServeur = WBservices.updateNotificationPref(idpersonne, token,
				false);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		personne = WBservices.getPersonnebyToken(token);
		assertFalse(personne.isNotification());

		// ********************** Ajoute une activité *******

		String longitudeStr="3";
		String latitudeStr="42";
		messageServeur = WBservices.addActivite("test" + new Date().getTime(),
				libelleActivite, idpersonne, 90, 1, latitudeStr, longitudeStr, "adresse", 3,
				90, token);

		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());
		int idactivite = Integer.valueOf(messageServeur.getMessage());
		System.out.println("activite créee" + idactivite);

		// Verifie le nbr activite en cours

		activiteEnCours = WBservices.getMesActiviteEncours(idpersonne,idpersonne,token);
		assertTrue(activiteEnCours != null);
		assertTrue(activiteEnCours.length == 1);

		// Récupere le detail activité

		Activite activite;

		

		// Verifie le nombre d'activité en cours 1 seule

		activiteEnCours = WBservices.getMesActiviteEncours(idpersonne,idpersonne,token);
		assertTrue(activiteEnCours != null);
		assertTrue(activiteEnCours.length == 1);

		// **************************** Creation du participant

		int idparticipant = 0;
		idparticipant = personnedao.addCompteGenerique(uid1, token1, photostr,
				nom1, gcmToken1);

		// *******************Verifice que la personne est crée

		assertTrue(idparticipant != 0);

		// *********************** Recupere la personne créée

		Personne participant = WBservices.getPersonnebyToken(token1);

		assertTrue(personne != null);
		// Test les valeurs par défaut
		assertTrue(participant.isPremiereconnexion());
		assertTrue(participant.isNotification());
		assertTrue(participant.isActif());
		assertFalse(participant.isAdmin());

		// ************************* Premiere connexion

		WBservices.updatePseudo("tutu1", new Date().getTime(), 0,
				idparticipant, token1);

		participant = personnedao.getPersonneId(idparticipant);
		assertTrue(participant != null);
		assertFalse(participant.isPremiereconnexion());
		assertTrue(participant.isNotification());
		assertTrue(participant.isActif());
		assertFalse(participant.isAdmin());

		// ***************Recherche de l'activité
		
		Activite[] listActivite=WBservices.getListActiviteAvenirNocritere(idparticipant,latitudeStr,longitudeStr,1000,"",0);
		assertTrue(listActivite != null);
		assertTrue(listActivite.length==1);
	
		
		// Inscription à l'activité
		
		
		messageServeur = WBservices.addParticipation(idparticipant, idpersonne,
				listActivite[0].getId(), token1);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());
		
		// Verifie le nombre d'activite en cours pour chacun doit être égale à un pour les 2
		
		Activite[] listActiviteEncours=WBservices.getMesActiviteEncours(idpersonne,idpersonne,token);
		assertTrue(listActiviteEncours != null);
		assertTrue(listActiviteEncours.length==1);	
		
		listActiviteEncours=WBservices.getMesActiviteEncours(idparticipant,idparticipant,token1);
		assertTrue(listActiviteEncours != null);
		assertTrue(listActiviteEncours.length==1);	

		// Verifie le nombre de participant

		Participant[]participants = WBservices.getListParticipant(idpersonne,idactivite,token);
		assertTrue(participants != null);
		assertTrue(participants.length == 2);

		// Verifie le nombre de participant de l'activité
		activite = WBservices.getActivite(idpersonne, idactivite,token);

		assertTrue(activite != null);
		assertTrue(activite.getNbrparticipant() == 2);

		// Recupere la list des activite de l'organisateur

		activiteEnCours = WBservices.getMesActiviteEncours(idpersonne,idpersonne,token);
		assertTrue(activiteEnCours != null);
		assertTrue(activiteEnCours.length == 1);

		// Recupere la list des activites du participant

		activiteEnCours = WBservices.getMesActiviteEncours(idparticipant,idparticipant,token1);
		assertTrue(activiteEnCours != null);
		assertTrue(activiteEnCours.length == 1);

		// Ajoute un message à l'activité

		RetourMessage retourMessage = WBservices.addMessageByAct(idpersonne,
				corpMessageByAct, idactivite, token);
		assertTrue(retourMessage != null);
		assertTrue(retourMessage.getId() != 0);

		int idmessage = retourMessage.getId();

		// Le participant récupere la discussion de l'activité.

		Message[] messages = WBservices.getDiscussionByAct(idparticipant, idactivite,token1);
		assertTrue(messages != null);

		// acquit le message

		messageServeur = WBservices.acquitMessageByAct(idparticipant,
				idmessage, token1);
		assertTrue(messageServeur != null);

		// acquit tous les messages de tous les participants

		messageServeur = WBservices.acquitMessageDiscussionByAct(idpersonne,
				idactivite, token);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		messageServeur = WBservices.acquitMessageDiscussionByAct(idparticipant,
				idactivite, token1);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		// Verifie que le participant à une discussion
		listDiscussion = WBservices.getListDiscussion(idparticipant,idparticipant,token1);
		assertTrue(listDiscussion != null);
		assertTrue(listDiscussion.length == 1);

		// Terminie l'activite
		 ActiviteDAO.terminerActivite(idactivite);

		// Pause terminer une activite revient à la mettre à la date du jour.
		// On fait une pause avant de lancer la mise à jour des notifications.
		
		// On essaie d'envyer un message - Il ne sont pas ami
			
		retourMessage=WBservices.addMessage(idpersonne, "message", idparticipant, token);
		assertTrue(retourMessage != null);
		assertTrue(retourMessage.getId()==RetourMessage.PLUS_SON_AMI);	
			
		retourMessage=WBservices.addMessage(idparticipant, "message", idpersonne, token1);
		assertTrue(retourMessage != null);
		assertTrue(retourMessage.getId()==RetourMessage.PLUS_SON_AMI);	
	
		// met à jour les notifications (demande de notation)
		messageServeur = WBservices.updateNotification(idpersonne, token);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		// Verifie la creation de la notation pour l'orgainsateur
		Notification[] listNotification = WBservices
				.getListNotification(idpersonne,idpersonne,token);
		assertTrue(listNotification != null);
		assertTrue(listNotification.length == 1);

		// met à jour les notifications (demande de notation) pour le
		// participant

		messageServeur = WBservices.updateNotification(idparticipant, token1);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		// met à jour les notifications (demande de notation) pour le
		// participant
		listNotification = WBservices.getListNotification(idpersonne,idpersonne,token);
		assertTrue(listNotification != null);
		assertTrue(listNotification.length == 1);

		// L'organisateur note le participant

		messageServeur = WBservices.addAvis(idpersonne, idparticipant,
				idactivite, "titre", "cool", "3.2", true, token);

		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		// Le participant note l'organisateur

		messageServeur = WBservices.addAvis(idparticipant, idpersonne,
				idactivite, "titre", "cool", "3.1", true, token1);

		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		// Le participant et l'oranisateur ont maintenant 2 notifications
		listNotification = WBservices.getListNotification(idpersonne,idpersonne,token);
		assertTrue(listNotification != null);
		assertTrue(listNotification.length == 2);

		listNotification = WBservices.getListNotification(idparticipant,idparticipant,token1);
		assertTrue(listNotification != null);
		assertTrue(listNotification.length == 2);

		// acquitement de toutes les notifications de l'organisteur

		messageServeur = WBservices.acquitAllNotification(idpersonne, token);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		// acquitement de toutes les notifications de participant

		messageServeur = WBservices
				.acquitAllNotification(idparticipant, token1);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());

		// efface de toutes les notifications de l'organisteur

		listNotification = WBservices.getListNotification(idpersonne,idpersonne,token);

		for (Notification notification : listNotification) {

			messageServeur = WBservices.effaceNotificationRecu(idpersonne,
					notification.getIdnotification(), token);
			assertTrue(messageServeur != null);
			assertTrue(messageServeur.isReponse());

		}
		
		// Verifie l'effacement
		listNotification = WBservices.getListNotification(idpersonne,idpersonne,token);
		assertTrue(listNotification != null);
		assertTrue(listNotification.length==0);


		// efface de toutes les notifications du participant

		listNotification = WBservices.getListNotification(idparticipant,idparticipant,token1);

		for (Notification notification : listNotification) {

			messageServeur = WBservices.effaceNotificationRecu(idparticipant,
					notification.getIdnotification(), token1);
			assertTrue(messageServeur != null);
			assertTrue(messageServeur.isReponse());

		}
		
		// Verifie l'effacement
		
		listNotification = WBservices.getListNotification(idparticipant,idparticipant,token1);
		assertTrue(listNotification != null);
		assertTrue(listNotification.length==0);
		
		// Verifie pas de messages au départ.
	
		messages= WBservices.getDiscussion(idpersonne, idparticipant,token);
		assertTrue(messages != null);
		assertTrue(messages.length==0);	
		
		messages= WBservices.getDiscussion( idparticipant,idpersonne,token1);
		assertTrue(messages != null);
		assertTrue(messages.length==0);	
		
		// Envoi de message 
		
		retourMessage=WBservices.addMessage(idpersonne, "message", idparticipant, token);
		assertTrue(retourMessage != null);
		assertTrue(retourMessage.getId()>0);	
			
		
		retourMessage=WBservices.addMessage(idparticipant, "message", idpersonne, token1);
		assertTrue(retourMessage != null);
		assertTrue(retourMessage.getId()>0);	
		
		
		// Verifie le nobmr de message 2 au total (1 envoye 1 recu).
		messages= WBservices.getDiscussion(idpersonne, idparticipant,token);
		assertTrue(messages != null);
		assertTrue(messages.length==2);	
		
		messages= WBservices.getDiscussion( idparticipant,idpersonne,token1);
		assertTrue(messages != null);
		assertTrue(messages.length==2);	
			
		messageServeur=WBservices.acquitMessageDiscussion(idpersonne, idparticipant, token);
		messageServeur=WBservices.acquitMessageDiscussion(idparticipant, idpersonne, token1);
			
		
		// Regarde ses archives
		
		Activite[] listActiviteArchives=WBservices.getMesActiviteArchive(idpersonne,idpersonne,token);
		assertTrue(listActiviteArchives != null);
		assertTrue(listActiviteArchives.length==1);	
		
		listActiviteArchives=WBservices.getMesActiviteArchive(idparticipant,idparticipant,token1);
		assertTrue(listActiviteArchives != null);
		assertTrue(listActiviteArchives.length==1);	
		
		// Activite en cours ==0
		
		listActiviteEncours=WBservices.getMesActiviteEncours(idpersonne,idpersonne,token);
		assertTrue(listActiviteEncours != null);
		assertTrue(listActiviteEncours.length==0);	
		
		listActiviteEncours=WBservices.getMesActiviteEncours(idparticipant,idparticipant,token1);
		assertTrue(listActiviteEncours != null);
		assertTrue(listActiviteEncours.length==0);	
		
		
		// Verifie la liste d'ami
		
		listAmi=WBservices.getListAmi(idpersonne,idpersonne,token);
		assertTrue(listAmi != null);
		assertTrue(listAmi.length==1);
		assertTrue(listAmi[0].getId()==idparticipant);	
		
		listAmi=WBservices.getListAmi(idparticipant,idparticipant,token1);
		assertTrue(listAmi != null);
		assertTrue(listAmi.length==1);
		assertTrue(listAmi[0].getId()==idpersonne);	
	
	 
		// l'effacement d'un ami l'efface des 2 cotes
		
		messageServeur=WBservices.effaceAmi(idpersonne, idparticipant, token);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());
		listAmi=WBservices.getListAmi(idpersonne,idpersonne,token);
		assertTrue(listAmi != null);
		assertTrue(listAmi.length==0);
	
		listAmi=WBservices.getListAmi(idparticipant,idparticipant,token1);
		assertTrue(listAmi != null);
		assertTrue(listAmi.length==0);
		
		


}}
