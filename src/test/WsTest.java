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
	String token = "token";
	String photostr = "photostr";
	String nom = "jj";
	String gcmToken = "gcmToken";
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

		String requete = "delete from messagebyact where corps=?";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setString(1, "testunit");
		preparedStatement.execute();
		preparedStatement.close();
		
		 requete = "delete from activite where libelle=?";
		 preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setString(1, "activite test");
		preparedStatement.execute();
		preparedStatement.close();
	
		requete = "delete from personne where personne.login=?";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setString(1, uid);
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

		// *******************Verifice que la personne est cr�e

		assertTrue(idpersonne != 0);

		// Recupere la personne cr��e

		Personne personne = WBservices.getPersonnebyToken(token);

		assertTrue(personne != null);
		// Test les valeurs par d�faut
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
	
		//  	recupere ses pr�f�rences
		
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
		
		//Recupere discussion
		Discussion[] listDiscussion=WBservices.getListDiscussion(idpersonne);
		assertTrue(listDiscussion!=null);
		assertTrue(listDiscussion.length==0);
		
		// Recupere le TDB
		
		TableauBord tableauBord=WBservices.getTableauBord(idpersonne);
		assertTrue(tableauBord!=null);
		assertTrue(tableauBord.getNbractiviteencours()==0);
		
		// Test l'ami
		MessageServeur messageServeur=WBservices.isAmiFrom(idpersonne, 10);
		assertTrue(messageServeur!=null);
		assertFalse(messageServeur.isReponse());
		
		
		// Recueper les activit� en cours
		
		Activite[] activiteEnCours= WBservices.getMesActiviteEncours(idpersonne);
		assertTrue(activiteEnCours!=null);
		assertTrue(activiteEnCours.length==0);
		
		// met � jour le calcul des notifications
		
		messageServeur=WBservices.updateNotification(idpersonne, token);
		assertTrue(messageServeur!=null);
		assertTrue(messageServeur.isReponse());
		
		
		// Mets � jour les pr�frence de diffusion de notification
		
		messageServeur=WBservices.updateNotificationPref( idpersonne,  token, false);
		assertTrue(messageServeur!=null);
		assertTrue(messageServeur.isReponse());
		
		personne = WBservices.getPersonnebyToken(token);
		assertFalse(personne.isNotification());
		
		
		
		// Recueper les activit� archiv�es
		
				Activite[] activiteArchies= WBservices.getMesActiviteArchive(idpersonne);
				assertTrue(activiteArchies!=null);
				assertTrue(activiteArchies.length==0);
		
		
		
		//********************** Ajoute une activit�

		messageServeur = WBservices.addActivite("test"
				+ new Date().getTime(), "activite test", idpersonne, 90, 1,
				"42", "3", "adresse", 3, 90, token);

		assertTrue(messageServeur != null);
		assertTrue(messageServeur.isReponse());
		int idactivite = Integer.valueOf(messageServeur.getMessage());
		System.out.println("activite cr�ee" + idactivite);

		// Verifie le nbr activite en cours
		
		activiteEnCours= WBservices.getMesActiviteEncours(idpersonne);
		assertTrue(activiteEnCours!=null);
		assertTrue(activiteEnCours.length==1);
		
		// R�cupere le detail activit�
		
		Activite activite = WBservices.getActivite(idpersonne, idactivite);

		assertTrue(activite != null);
		assertTrue(activite.getId() == idactivite);
		assertTrue(activite.getIdorganisateur() == idpersonne);
	
		// R�cupere la liste des participants
		
		Participant[] participants=WBservices.getListParticipant(idactivite);
		assertTrue(participants != null);
		assertTrue(participants.length==1);
		
		// Modifie l'activit�
		
		messageServeur=WBservices.updateActivite( idpersonne,  idactivite,
				 "titres", "activite test",  5,  token);
		assertTrue(messageServeur!=null);
		assertTrue(messageServeur.isReponse());
		
		// Verifie la mise � jour
		
		activite = WBservices.getActivite(idpersonne, idactivite);
		assertTrue(activite != null);
		assertTrue(activite.getId() == idactivite);
		assertTrue(activite.getIdorganisateur() == idpersonne);
		assertTrue(activite.getTitre().compareTo("titres")==0);
		assertTrue(activite.getLibelle().compareTo("activite test")==0);
		assertTrue(activite.getNbmaxwaydeur()==5);
	
		// Ajoute un message � l'activit�
		
		RetourMessage retourMessage=WBservices.addMessageByAct(idpersonne, "testunit", idactivite, token);
		assertTrue(retourMessage != null);
		assertTrue(retourMessage.getId()!=0);
	
		int idmessage=retourMessage.getId();
		
		// Recupere les discussions de l'activit�
		
		Message[] messages=WBservices.getDiscussionByAct(idpersonne,  idactivite);
		assertTrue(messages != null);
		assertTrue(messages.length==1);
		
		
		// Efface le message
		messageServeur=WBservices.effaceMessageEmisByAct(idpersonne,idmessage,token);
		
		// Verifie son effacement
		
		messages=WBservices.getDiscussionByAct(idpersonne,  idactivite);
		assertTrue(messages != null);
		assertTrue(messages.length==0);
		
		
		
		
		
		try {
			connexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
