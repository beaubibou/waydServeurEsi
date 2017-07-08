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
import wayde.bean.Participant;
import wayde.bean.Personne;
import wayde.bean.Preference;
import wayde.bean.Profil;
import wayde.bean.RetourMessage;
import wayde.bean.TableauBord;
import wayde.bean.TypeActivite;
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
	static final String corpMessageByAct="testMessageByAct";
	static final String corpMessage="testMessage";
	static final String libelleActivite="Activite test";
	static Connection connexion;
	static {

	}

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
		
		 requete = "delete from demandeami";
		 preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.execute();
		preparedStatement.close();
		

		 requete = "delete from participer";
		 preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.execute();
		preparedStatement.close();
		
		 requete = "delete from messagebyact where corps=?";
		 preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setString(1, corpMessageByAct);
		preparedStatement.execute();
		preparedStatement.close();
		
		 requete = "delete from activite where libelle=?";
		 preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setString(1, libelleActivite);
		preparedStatement.execute();
		preparedStatement.close();
	
		requete = "delete from personne where personne.login like ?";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setString(1, uid+"%");
		preparedStatement.execute();
		preparedStatement.close();

	}

	@Test
	public void testComplet() throws SQLException, Exception {
		
		TypeActivite[] listTypeActivite= WBservices.getListTypeActivite();
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
	
		//  	recupere ses préférences
		
		Preference[] preferences=WBservices.getLisPreference(idpersonne);
		
		assertTrue(preferences!=null);
		assertTrue(preferences.length==0);
		
		
		//  recupere son profil
		
		Profil profil= WBservices.getFullProfil(idpersonne);
		assertTrue(profil!=null);
		assertTrue(profil.getId()==idpersonne);
		
		// Recupere ami
		
		Ami[] listAmi=WBservices.getListAmi(idpersonne);
		assertTrue(listAmi!=null);
		
		// Recupere sa nottaton
		
		InfoNotation infoNotation=WBservices.getInfoNotation(idpersonne);
		assertTrue(infoNotation!=null);
		
		// Recupere ses avis
		Avis[] listAvis=WBservices.getListAvis(idpersonne);
		assertTrue(listAvis!=null);
		assertTrue(listAvis.length==0);
		
		//Recupere ses discussion
		Discussion[] listDiscussion=WBservices.getListDiscussion(idpersonne);
		assertTrue(listDiscussion!=null);
		assertTrue(listDiscussion.length==0);
		
		// Recupere son TDB
		
		TableauBord tableauBord=WBservices.getTableauBord(idpersonne);
		assertTrue(tableauBord!=null);
		assertTrue(tableauBord.getNbractiviteencours()==0);
		
		// Test un ami inexistant
		MessageServeur messageServeur=WBservices.isAmiFrom(idpersonne, 10);
		assertTrue(messageServeur!=null);
		assertFalse(messageServeur.isReponse());
		
		
		// Recueper les activité en cours
		
		Activite[] activiteEnCours= WBservices.getMesActiviteEncours(idpersonne);
		assertTrue(activiteEnCours!=null);
		assertTrue(activiteEnCours.length==0);
		
		// met à jour le calcul des notifications
		
		messageServeur=WBservices.updateNotification(idpersonne, token);
		assertTrue(messageServeur!=null);
		assertTrue(messageServeur.isReponse());
		
		// Recueper les activité archivées
		
		Activite[] activiteArchies= WBservices.getMesActiviteArchive(idpersonne);
		assertTrue(activiteArchies!=null);
		assertTrue(activiteArchies.length==0);

				
		// Mets à jour les préfrence de diffusion de notification
		
		messageServeur=WBservices.updateNotificationPref( idpersonne,  token, false);
		assertTrue(messageServeur!=null);
		assertTrue(messageServeur.isReponse());
		
		personne = WBservices.getPersonnebyToken(token);
		assertFalse(personne.isNotification());
		
		
		
		//********************** Ajoute une activité

		messageServeur = WBservices.addActivite("test"
				+ new Date().getTime(), libelleActivite, idpersonne, 90, 1,
				"42", "3", "adresse", 3, 90, token);

		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());
		int idactivite = Integer.valueOf(messageServeur.getMessage());
		System.out.println("activite créee" + idactivite);

		// Verifie le nbr activite en cours
		
		activiteEnCours= WBservices.getMesActiviteEncours(idpersonne);
		assertTrue(activiteEnCours!=null);
		assertTrue(activiteEnCours.length==1);
		
		// Récupere le detail activité
		
		Activite activite = WBservices.getActivite(idpersonne, idactivite);

		assertTrue(activite != null);
		assertTrue(activite.getId() == idactivite);
		assertTrue(activite.getIdorganisateur() == idpersonne);
	
		// Récupere la liste des participants
		
		Participant[] participants=WBservices.getListParticipant(idactivite);
		assertTrue(participants != null);
		assertTrue(participants.length==1);
		
		// Modifie l'activité
		
		messageServeur=WBservices.updateActivite( idpersonne,  idactivite,
				 "titres", libelleActivite,  5,  token);
		assertTrue(messageServeur!=null);
		assertTrue(messageServeur.isReponse());
		
		// Verifie la mise à jour
		
		activite = WBservices.getActivite(idpersonne, idactivite);
		assertTrue(activite != null);
		assertTrue(activite.getId() == idactivite);
		assertTrue(activite.getIdorganisateur() == idpersonne);
		assertTrue(activite.getTitre().compareTo("titres")==0);
		assertTrue(activite.getLibelle().compareTo(libelleActivite)==0);
		assertTrue(activite.getNbmaxwaydeur()==5);
	
		// Ajoute un message à l'activité
		
		RetourMessage retourMessage=WBservices.addMessageByAct(idpersonne, corpMessageByAct, idactivite, token);
		assertTrue(retourMessage != null);
		assertTrue(retourMessage.getId()!=0);
	
		int idmessage=retourMessage.getId();
		
		// Recupere les discussions de l'activité
		
		Message[] messages=WBservices.getDiscussionByAct(idpersonne,  idactivite);
		assertTrue(messages != null);
		assertTrue(messages.length==1);
		
		
		// Efface le message
		messageServeur=WBservices.effaceMessageEmisByAct(idpersonne,idmessage,token);
		
		// Verifie son l'effacement du message effacement
		
		messages=WBservices.getDiscussionByAct(idpersonne,  idactivite);
		assertTrue(messages != null);
		assertTrue(messages.length==0);
	
		// Verifie le nombre d'activité en cours 1 seule
		
		activiteEnCours= WBservices.getMesActiviteEncours(idpersonne);
		assertTrue(activiteEnCours!=null);
		assertTrue(activiteEnCours.length==1);
	
		
		//**************************** Creation du participant
		
		
		int idparticipant = 0;
		idparticipant = personnedao.addCompteGenerique(uid1, token1, photostr, nom1,
				gcmToken1);

		// *******************Verifice que la personne est crée

		assertTrue(idparticipant != 0);

		//*********************** Recupere la personne créée

		Personne participant = WBservices.getPersonnebyToken(token1);

		assertTrue(personne != null);
		// Test les valeurs par défaut
		assertTrue(participant.isPremiereconnexion());
		assertTrue(participant.isNotification());
		assertTrue(participant.isActif());
		assertFalse(participant.isAdmin());

		// ************************* Premiere connexion

		WBservices.updatePseudo("tutu1", new Date().getTime(), 0, idparticipant,
				token1);

		participant = personnedao.getPersonneId(idparticipant);
		assertTrue(participant != null);
		assertFalse(participant.isPremiereconnexion());
		assertTrue(participant.isNotification());
		assertTrue(participant.isActif());
		assertFalse(participant.isAdmin());
	
		
		
		// ***************AJOUT DU PARTICIPANT A L'ACTIVITE
		
		messageServeur=WBservices.addParticipation( idparticipant, idpersonne,
				 idactivite,  token1);
		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());
		
		// Verifie le nombre de participant
		
		 participants=WBservices.getListParticipant(idactivite);
			assertTrue(participants != null);
			assertTrue(participants.length==2);
			
		// Verifie le nombre de participant de l'activité
			 activite = WBservices.getActivite(idpersonne, idactivite);

			assertTrue(activite != null);
			assertTrue(activite.getNbrparticipant() == 2);
			
			
			activiteEnCours= WBservices.getMesActiviteEncours(idpersonne);
			assertTrue(activiteEnCours!=null);
			assertTrue(activiteEnCours.length==1);
			
			activiteEnCours= WBservices.getMesActiviteEncours(idparticipant);
			assertTrue(activiteEnCours!=null);
			assertTrue(activiteEnCours.length==1);
		
			
			
		
		
		try {
			connexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
