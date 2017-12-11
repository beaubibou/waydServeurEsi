package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import texthtml.pro.Erreur_HTML;
import threadpool.PoolThreadGCM;
import wayd.ws.TextWebService;
import wayde.bean.Activite;
import wayde.bean.CxoPool;
import wayde.bean.MessageServeur;
import wayde.bean.Personne;
import wayde.dao.ParticipationDAO;
import wayde.dao.PersonneDAO;
import wayde.dao.SignalementDAO;
import website.html.OutilsHtml;
import website.metier.ActiviteAjax;
import website.metier.ActiviteBean;
import website.metier.IndicateurWayd;
import website.metier.Outils;
import website.metier.ParticipantBean;
import website.metier.ProfilBean;
import website.metier.TypeActiviteBean;
import website.metier.TypeEtatActivite;
import website.metier.TypeEtatMessage;
import website.metier.TypeSignalement;
import website.metier.TypeUser;
import website.metier.admin.FitreAdminActivites;
import fcm.ServeurMethodes;
import gcmnotification.EffaceActiviteGcm;

public class ActiviteDAO {

	private static final Logger LOG = Logger.getLogger(ActiviteDAO.class);
	Connection connexion;

	public ActiviteDAO(Connection connexion) {
		this.connexion = connexion;
	}

	public ActiviteDAO() {

	}

	public static boolean addNbrVu(int idpersonne, int idactivite,
			int idorganisateur) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		if (idorganisateur == idpersonne)
			return false;

		if (isDejaVu(idpersonne, idactivite))
			return false;

		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "INSERT INTO nbrvu("
					+ "   idpersonne, idactivite)" + "	VALUES (?,?)";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setInt(2, idactivite);
			preparedStatement.execute();
			preparedStatement.close();
			requete = "UPDATE activite  SET  nbrvu=nbrvu+1 WHERE idactivite=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idactivite);
			preparedStatement.execute();
			connexion.commit();

			return true;

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {

				if (connexion != null)
					connexion.close();
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return false;

	}

	private static boolean isDejaVu(int idpersonne, int idactivite) {
		// TODO Auto-generated method stub

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();
			String requete = " SELECT idpersonne from nbrvu where idpersonne=? and idactivite=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setInt(2, idactivite);
			rs = preparedStatement.executeQuery();

			if (rs.next())
				return true;

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {

			try {
				if (rs != null)
					rs.close();
				if (preparedStatement != null)
					preparedStatement.close();
				connexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return false;
	}
	
	private static boolean isInteretDejaSignale(int idpersonne, int idactivite) {
		// TODO Auto-generated method stub

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();
			String requete = " SELECT idpersonne from interet where idpersonne=? and idactivite=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setInt(2, idactivite);
			rs = preparedStatement.executeQuery();

			if (rs.next())
				return true;

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {

			try {
				if (rs != null)
					rs.close();
				if (preparedStatement != null)
					preparedStatement.close();
				connexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return false;
	}

	public ArrayList<ActiviteAjax> getListActiviteAjaxMap(double malatitude,
			double malongitude, double NELat, double NELon, double SWLat,
			double SWlon) {
		ArrayList<ActiviteAjax> retour = new ArrayList<ActiviteAjax>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();
			double latMin = SWLat;
			double latMax = NELat;
			double longMin = SWlon;
			double longMax = NELon;
			ActiviteAjax activite = null;

			String requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
					+ " activite.longitude,    personne.prenom,    personne.sexe,    personne.nom,    personne.idpersonne,personne.datenaissance,    "
					+ "personne.note,personne.nbravis as totalavis,personne.photo,activite.nbrwaydeur as nbrparticipant,1 as role,"
					+ "activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite,"
					+ "activite.nbmaxwayd,activite.typeuser   FROM personne,"
					+ "activite  WHERE personne.idpersonne = activite.idpersonne   "
					+ "and  datefin>? "
					+ " and activite.latitude between ? and ?"
					+ " and activite.longitude between ? and ? FETCH FIRST 40 ROWS ONLY;";

			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setTimestamp(1,
					new java.sql.Timestamp(new Date().getTime()));
			preparedStatement.setDouble(2, latMin);
			preparedStatement.setDouble(3, latMax);
			preparedStatement.setDouble(4, longMin);
			preparedStatement.setDouble(5, longMax);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				int id = rs.getInt("idactivite");
				String libelle = rs.getString("libelle");
				String titre = rs.getString("titre");
				int idorganisateur = rs.getInt("idpersonne");
				int idtypeactivite = rs.getInt("idtypeactivite");
				int typeUser = rs.getInt("typeuser");
				String nom = rs.getString("nom");
				String pseudo = rs.getString("prenom");

				if (pseudo == null)
					pseudo = "";
				String photo = rs.getString("photo");

				activite = new ActiviteAjax(id, titre, libelle, idorganisateur,
						latitude, longitude, photo, nom, pseudo, typeUser,
						idtypeactivite);
				retour.add(activite);

			}

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				rs.close();
				preparedStatement.close();
				connexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return retour;
	}

	public ArrayList<ActiviteAjax> getListActiviteEncoursAjax(
			double centreLatitude, double centreLongitude) throws SQLException {

		ActiviteAjax activite = null;
		ArrayList<ActiviteAjax> retour = new ArrayList<ActiviteAjax>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();
			String requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
					+ " activite.longitude,    personne.prenom,  personne.datenaissance,  personne.sexe,    personne.nom,    personne.idpersonne,"
					+ "personne.photo,"
					+ "activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite,activite.typeuser   FROM personne,"
					+ "activite  WHERE personne.idpersonne = activite.idpersonne "
					+ "and  activite.datefin>?  ";

			preparedStatement = connexion.prepareStatement(requete);

			preparedStatement.setTimestamp(1,
					new java.sql.Timestamp(new Date().getTime()));
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("idactivite");
				String libelle = rs.getString("libelle");
				String titre = rs.getString("titre");
				int idorganisateur = rs.getInt("idpersonne");
				int idtypeactivite = rs.getInt("idtypeactivite");
				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				String nom = rs.getString("nom");
				String pseudo = rs.getString("prenom");
				if (pseudo == null)
					pseudo = "";
				String photo = rs.getString("photo");
				int typeUser = rs.getInt("typeuser");
				activite = new ActiviteAjax(id, titre, libelle, idorganisateur,
						latitude, longitude, photo, nom, pseudo, typeUser,
						idtypeactivite);
				retour.add(activite);
			}

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return retour;
		} finally {
			rs.close();
			preparedStatement.close();
			connexion.close();

		}

		return retour;

		// Cherche dans les activite

	}

	public boolean isInscrit(ActiviteBean activite, int idpersonne) {
		String requete = "SELECT  idpersonne FROM public.participer "
				+ "where( idpersonne=? and idactivite=?);";
		PreparedStatement preparedStatement;
		try {

			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setInt(2, activite.getId());
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {

				preparedStatement.close();
				return true;
			} else {
				preparedStatement.close();
				return false;

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

		// TODO Auto-generated method stub

	}

	public static boolean updateActivitePro(String titre, String commentaire,
			Date datedebut, Date datefin, String adresse, double latitude,
			double longitude, int idtypeactivite, int idactivite) {

		Connection connexion = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
	//		LOG.info("Mise a jour activite"+ commentaire.length());
	
			String requete = "UPDATE  activite set titre=?, libelle=?,  datedebut=?, datefin=?,  adresse=?,"
					+ " latitude=?,  longitude=?,  idtypeactivite=?"
					+ " WHERE idactivite=?";
			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);
			preparedStatement.setString(1, titre);
			preparedStatement.setString(2, Outils.getStringStatement(commentaire));
			preparedStatement.setTimestamp(3,
					new java.sql.Timestamp(datedebut.getTime()));
			preparedStatement.setTimestamp(4,
					new java.sql.Timestamp(datefin.getTime()));
			preparedStatement.setString(5, adresse);

			preparedStatement.setDouble(6, latitude);
			preparedStatement.setDouble(7, longitude);
			preparedStatement.setInt(8, idtypeactivite);
			preparedStatement.setInt(9, idactivite);
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();
			return true;

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			try {
				connexion.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();

		} finally {

			try {
				connexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;

	}

	public int addActivitePro(int idpersonne, String titre, String commentaire,
			Date datedebut, Date datefin, String adresse, double latitude,
			double longitude, int idtypeactivite, int typeuser, int typeaccess) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		LOG.info("commmentaire "+commentaire.length());
		
		
		
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "INSERT INTO activite("
					+ "idpersonne, titre, libelle,datedebut,"
					+ " datefin, adresse, latitude, longitude, actif,"
					+ " idtypeactivite,datecreation,typeuser,typeacces,nbrvu)"
					+ "	VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,0)";

			preparedStatement = connexion.prepareStatement(requete,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setString(2, titre);
			preparedStatement.setString(3, Outils.getStringStatement(commentaire));
		//	preparedStatement.setString(3,null);
			preparedStatement.setTimestamp(4,
					new java.sql.Timestamp(datedebut.getTime()));
			preparedStatement.setTimestamp(5,
					new java.sql.Timestamp(datefin.getTime()));
			preparedStatement.setString(6, adresse);
			preparedStatement.setDouble(7, latitude);
			preparedStatement.setDouble(8, longitude);
			preparedStatement.setBoolean(9, true);
			preparedStatement.setInt(10, idtypeactivite);
			preparedStatement.setTimestamp(11, new java.sql.Timestamp(
					new Date().getTime()));
			preparedStatement.setInt(12, typeuser);
			preparedStatement.setInt(13, typeaccess);
			preparedStatement.execute();
			connexion.commit();
			rs = preparedStatement.getGeneratedKeys();
			int cle = 0;
			if (rs.next())
				cle = rs.getInt("idactivite");

			return cle;
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				if (rs != null)
					rs.close();
				if (connexion != null)
					connexion.close();
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		LOG.info("Creation activite user");
		return 0;
	}

	public static IndicateurWayd getIndicateurs() {
		Connection connexion = null;
		try {
			connexion = CxoPool.getConnection();

			int nbrTotalactivite = 0, nbrTotalparticipation = 0, nbrTotalInscrit = 0, nbrTotalMessage = 0, nbrTotalMessageByAct = 0;

			// ************Calcul le nbr total d'activité

			String requete = "Select count(idactivite) as nbractivite  FROM activite";
			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				nbrTotalactivite = rs.getInt("nbractivite");
			}

			// ***********Calcul le nbr de participation

			requete = "Select count(idpersonne) as nbrinscrit  FROM personne;";
			preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				nbrTotalInscrit = rs.getInt("nbrinscrit");
			}

			// **************Calcul le nbr d'inscrit
			requete = "Select count(idpersonne) as nbrparticipation  FROM participer;";
			preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				nbrTotalparticipation = rs.getInt("nbrparticipation");
			}

			// **************Calcul de message non lu en stand alone;

			requete = "select count(idmessage) as nbrmessage from message;";

			preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				nbrTotalMessage = rs.getInt("nbrmessage");
			}
			// ***********Calcul de message non lu en stand talkgroup

			requete = "select  count(idmessage) as nbrmessagebyact from messagebyact;";
			preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				nbrTotalMessageByAct = rs.getInt("nbrmessagebyact");
			}

			return new IndicateurWayd(nbrTotalactivite, nbrTotalparticipation,
					nbrTotalInscrit, nbrTotalMessage, nbrTotalMessageByAct);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		finally {

			CxoPool.closeConnection(connexion);

		}
	}

	

	// Renvoi une activité avec la liste des participants
	//

	public ActiviteBean getActivite(int idActivite) {

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ActiviteBean activite = null;

		String requete = " SELECT activite.datedebut,       activite.adresse,    activite.latitude,"
				+ " activite.longitude,    personne.prenom as pseudo,    personne.sexe,    personne.nom,    personne.idpersonne,"
				+ "personne.affichesexe,personne.afficheage,personne.datenaissance,personne.note,"
				+ "personne.nbravis as totalavis,    personne.photo,activite.idactivite,activite.libelle,"
				+ "activite.titre,activite.nbrwaydeur as nbrparticipant ,activite.nbmaxwayd,activite.nbrvu,   activite.datefin, activite.idtypeactivite,"
				+ "activite.typeuser,activite.typeacces,type_activite.nom as libelleActivite,activite.adresse   FROM personne,"
				+ "activite,type_activite  WHERE activite.idtypeactivite=type_activite.idtypeactivite and personne.idpersonne = activite.idpersonne  and activite.idactivite=?";

		try {
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idActivite);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
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
				double note = rs.getDouble("note");
				String nom = rs.getString("nom");
				String pseudo = rs.getString("pseudo");
				String photo = rs.getString("photo");
				Date datenaissance = rs.getTimestamp("datenaissance");
				int typeUser = rs.getInt("typeuser");
				int typeAcces = rs.getInt("typeacces");
				String adresse = rs.getString("adresse");
				// Date datefinactivite = rs.getTimestamp("d_finactivite");

				int totalavis = rs.getInt("totalavis");
				int nbrVu=rs.getInt("nbrvu");
				String libelleActivite = rs.getString("libelleActivite");
				int nbrSignalement = 0;
				activite = new ActiviteBean(id, titre, libelle, idorganisateur,
						datedebut, datefin, idtypeactivite, latitude,
						longitude, nom, pseudo, photo, note, totalavis,
						datenaissance, sexe, nbrparticipant, nbmaxwayd,
						typeUser, typeAcces, libelleActivite, adresse,
						nbrSignalement);
				activite.setNbrVu(nbrVu);

				ArrayList<ParticipantBean> listParticipant = new ParticipantDAO(
						connexion).getListPaticipant(idActivite);

				activite.setListParticipant(listParticipant);
				return activite;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				preparedStatement.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return activite;

	}

	public static ArrayList<ActiviteBean> getListActivite(int idpersonne) {
		ActiviteBean activite = null;
		ArrayList<ActiviteBean> retour = new ArrayList<ActiviteBean>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		Connection connexion = null;
		try {
			connexion = CxoPool.getConnection();

			String requete = "SELECT activite.datedebut,activite.adresse,activite.latitude,"
					+ "activite.longitude,personne.prenom,personne.sexe,personne.nom,  personne.datenaissance,personne.idpersonne, "
					+ "personne.note,0 as role,"
					+ "personne.nbravis as totalavis,"
					+ "activite.nbrwaydeur as nbrparticipant,    personne.photo,"
					+ "personne.photo,activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite, activite.nbmaxwayd  FROM personne,"
					+ "activite,participer  WHERE (personne.idpersonne=activite.idpersonne and "
					+ "activite.idactivite = participer.idactivite "
					+ " and participer.idpersonne=?  ) ORDER BY datedebut DESC";

			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("idactivite");
				String libelle = rs.getString("libelle");
				String titre = rs.getString("titre");
				int idorganisateur = rs.getInt("idpersonne");
				int idtypeactivite = rs.getInt("idtypeactivite");
				int nbrparticipant = rs.getInt("nbrparticipant");
				Date datedebut = rs.getTimestamp("datedebut");
				Date datefin = rs.getTimestamp("datefin");
				String adresse = rs.getString("adresse");
				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				double note = rs.getDouble("note");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				Date datenaissance = rs.getTimestamp("datenaissance");
				String photo = rs.getString("photo");
				boolean archive = true;
				int totalavis = rs.getInt("totalavis");
				int role = rs.getInt("role");
				int sexe = rs.getInt("sexe");
				int nbmaxwayd = rs.getInt("nbmaxwayd");
				activite = new ActiviteBean(id, titre, libelle, idorganisateur,
						datedebut, datefin, idtypeactivite, latitude,
						longitude, adresse, nom, prenom, photo, note, role,
						archive, totalavis, datenaissance, sexe,
						nbrparticipant, true, true, nbmaxwayd);
				retour.add(activite);

			}

			preparedStatement.close();
			rs.close();
			// Cherche dans les activite

			requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
					+ " activite.longitude,    personne.prenom,personne.datenaissance,    personne.sexe,    personne.nom,    personne.idpersonne,   "
					+ "personne.note,personne.nbravis as totalavis,"
					+ "activite.nbrwaydeur as nbrparticipant"
					+ ",personne.photo,activite.idactivite,  activite.libelle,    activite.titre,   activite.datefin,    activite.idtypeactivite,activite.nbmaxwayd   FROM personne,"
					+ "activite  WHERE personne.idpersonne = activite.idpersonne  and activite.idpersonne=? order by datedebut DESC";

			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("idactivite");
				String libelle = rs.getString("libelle");
				String titre = rs.getString("titre");
				int idorganisateur = rs.getInt("idpersonne");
				int idtypeactivite = rs.getInt("idtypeactivite");
				int nbrparticipant = rs.getInt("nbrparticipant");
				Date datedebut = rs.getTimestamp("datedebut");
				Date datefin = rs.getTimestamp("datefin");
				String adresse = rs.getString("adresse");
				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				double note = rs.getDouble("note");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				Date datenaissance = rs.getTimestamp("datenaissance");
				String photo = rs.getString("photo");
				boolean archive = true;
				int sexe = rs.getInt("sexe");
				int totalavis = rs.getInt("totalavis");
				int nbmaxwayd = rs.getInt("nbmaxwayd");
				// Date datefinactivite = rs.getTimestamp("d_finactivite");

				activite = new ActiviteBean(id, titre, libelle, idorganisateur,
						datedebut, datefin, idtypeactivite, latitude,
						longitude, adresse, nom, prenom, photo, note, 1,
						archive, totalavis, datenaissance, sexe,
						nbrparticipant, true, true, nbmaxwayd);
				retour.add(activite);
			}

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return retour;
		} finally {
			CxoPool.close(connexion, preparedStatement, rs);
		}

		return retour;

	}

	public static ArrayList<ActiviteBean> getMesActivite(int idpersonne,
			int etatActivite) {
		ActiviteBean activite = null;
		ArrayList<ActiviteBean> retour = new ArrayList<ActiviteBean>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		Connection connexion = null;
		try {
			connexion = CxoPool.getConnection();
			String requete = "";
			switch (etatActivite)

			{
			case TypeEtatActivite.ENCOURS:
				requete = "SELECT activite.datedebut,activite.adresse,activite.latitude,"
						+ "activite.longitude,personne.prenom,personne.sexe,personne.nom,  personne.datenaissance,personne.idpersonne, "
						+ "personne.note,0 as role,"
						+ "personne.nbravis as totalavis,"
						+ "activite.nbrwaydeur as nbrparticipant,    personne.photo,"
						+ "personne.photo,activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite, activite.nbmaxwayd  FROM personne,"
						+ "activite,participer  WHERE (personne.idpersonne=activite.idpersonne and "
						+ "activite.idactivite = participer.idactivite "
						+ " and participer.idpersonne=? and ?  between datedebut and datefin) ORDER BY datedebut DESC";

				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idpersonne);
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(
						new Date().getTime()));

				rs = preparedStatement.executeQuery();

				break;
			case TypeEtatActivite.TERMINEE:
				requete = "SELECT activite.datedebut,activite.adresse,activite.latitude,"
						+ "activite.longitude,personne.prenom,personne.sexe,personne.nom,  personne.datenaissance,personne.idpersonne, "
						+ "personne.note,0 as role,"
						+ "personne.nbravis as totalavis,"
						+ "activite.nbrwaydeur as nbrparticipant,personne.photo,"
						+ "personne.photo,activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite, activite.nbmaxwayd  FROM personne,"
						+ "activite,participer  WHERE (personne.idpersonne=activite.idpersonne and "
						+ "activite.idactivite = participer.idactivite "
						+ " and participer.idpersonne=? and datefin<? ) ORDER BY datedebut DESC";

				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idpersonne);
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(
						new Date().getTime()));
				rs = preparedStatement.executeQuery();

				break;

			case TypeEtatActivite.TOUTES:
				requete = "SELECT activite.datedebut,activite.adresse,activite.latitude,"
						+ "activite.longitude,personne.prenom,personne.sexe,personne.nom,  personne.datenaissance,personne.idpersonne, "
						+ "personne.note,0 as role,"
						+ "personne.nbravis as totalavis,"
						+ "activite.nbrwaydeur as nbrparticipant,    personne.photo,"
						+ "personne.photo,activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite, activite.nbmaxwayd  FROM personne,"
						+ "activite,participer  WHERE (personne.idpersonne=activite.idpersonne and "
						+ "activite.idactivite = participer.idactivite "
						+ " and participer.idpersonne=?  ) ORDER BY datedebut DESC";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idpersonne);
				rs = preparedStatement.executeQuery();
				break;

			case TypeEtatActivite.PLANIFIEE:
				requete = "SELECT activite.datedebut,activite.adresse,activite.latitude,"
						+ "activite.longitude,personne.prenom,personne.sexe,personne.nom,  personne.datenaissance,personne.idpersonne, "
						+ "personne.note,0 as role,"
						+ "personne.nbravis as totalavis,"
						+ "activite.nbrwaydeur as nbrparticipant,personne.photo,"
						+ "personne.photo,activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite, activite.nbmaxwayd  FROM personne,"
						+ "activite,participer  WHERE (personne.idpersonne=activite.idpersonne and "
						+ "activite.idactivite = participer.idactivite "
						+ " and participer.idpersonne=? and datedebut>? ) ORDER BY datedebut DESC";

				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idpersonne);
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(
						new Date().getTime()));
				rs = preparedStatement.executeQuery();

				break;

			}

			while (rs.next()) {
				int id = rs.getInt("idactivite");
				String libelle = rs.getString("libelle");
				String titre = rs.getString("titre");
				int idorganisateur = rs.getInt("idpersonne");
				int idtypeactivite = rs.getInt("idtypeactivite");
				int nbrparticipant = rs.getInt("nbrparticipant");
				Date datedebut = rs.getTimestamp("datedebut");
				Date datefin = rs.getTimestamp("datefin");
				String adresse = rs.getString("adresse");
				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				double note = rs.getDouble("note");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				Date datenaissance = rs.getTimestamp("datenaissance");
				String photo = rs.getString("photo");
				boolean archive = true;
				int totalavis = rs.getInt("totalavis");
				int role = rs.getInt("role");
				int sexe = rs.getInt("sexe");
				int nbmaxwayd = rs.getInt("nbmaxwayd");

				// Date datefinactivite = rs.getTimestamp("d_finactivite");

				activite = new ActiviteBean(id, titre, libelle, idorganisateur,
						datedebut, datefin, idtypeactivite, latitude,
						longitude, adresse, nom, prenom, photo, note, role,
						archive, totalavis, datenaissance, sexe,
						nbrparticipant, true, true, nbmaxwayd);

				retour.add(activite);

			}

			preparedStatement.close();
			rs.close();
			// Cherche dans les activite

			switch (etatActivite)

			{
			case TypeEtatActivite.ENCOURS:
				requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
						+ " activite.longitude,    personne.prenom,personne.datenaissance,    personne.sexe,    personne.nom,    personne.idpersonne,   "
						+ "personne.note,personne.nbravis as totalavis,"
						+ "activite.nbrwaydeur as nbrparticipant, nbrvu,nbr_interet"
						+ ",personne.photo,activite.idactivite,  activite.libelle,    activite.titre,   activite.datefin,    activite.idtypeactivite,activite.nbmaxwayd"
						+ "   FROM personne,activite"
						+ "  WHERE personne.idpersonne = activite.idpersonne  and activite.idpersonne=? and ? between datedebut and datefin order by datedebut DESC";

				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idpersonne);
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(
						new Date().getTime()));
				rs = preparedStatement.executeQuery();

				break;
			case TypeEtatActivite.TERMINEE:
				requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
						+ " activite.longitude,    personne.prenom,personne.datenaissance,    personne.sexe,    personne.nom,    personne.idpersonne,   "
						+ "personne.note,personne.nbravis as totalavis, nbrvu,nbr_interet,"
						+ "activite.nbrwaydeur as nbrparticipant"
						+ ",personne.photo,activite.idactivite,  activite.libelle,    activite.titre,   activite.datefin,    activite.idtypeactivite,activite.nbmaxwayd"
						+ " FROM personne,activite "
						+ " WHERE personne.idpersonne = activite.idpersonne  and activite.idpersonne=? and datefin<? order by datedebut DESC";

				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idpersonne);
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(
						new Date().getTime()));
				rs = preparedStatement.executeQuery();

				break;

			case TypeEtatActivite.TOUTES:
				requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
						+ " activite.longitude,    personne.prenom,personne.datenaissance,    personne.sexe,    personne.nom,    personne.idpersonne,   "
						+ "personne.note,personne.nbravis as totalavis, nbrvu,nbr_interet,"
						+ "activite.nbrwaydeur as nbrparticipant,personne.photo,activite.idactivite,  activite.libelle,    activite.titre,   activite.datefin,    activite.idtypeactivite,activite.nbmaxwayd"
						+ " FROM personne,"
						+ "activite  WHERE personne.idpersonne = activite.idpersonne  and activite.idpersonne=? order by datedebut DESC";

				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idpersonne);
				rs = preparedStatement.executeQuery();

				break;

			case TypeEtatActivite.PLANIFIEE:
				requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
						+ " activite.longitude,    personne.prenom,personne.datenaissance,    personne.sexe,    personne.nom,    personne.idpersonne,   "
						+ "personne.note,personne.nbravis as totalavis, nbrvu,nbr_interet,"
						+ "activite.nbrwaydeur as nbrparticipant"
						+ ",personne.photo,activite.idactivite,  activite.libelle,    activite.titre,   activite.datefin,    activite.idtypeactivite,activite.nbmaxwayd"
						+ " FROM personne,activite "
						+ " WHERE personne.idpersonne = activite.idpersonne  and activite.idpersonne=? and datedebut>? order by datedebut DESC";

				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idpersonne);
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(
						new Date().getTime()));
				rs = preparedStatement.executeQuery();

			}

			while (rs.next()) {
				int id = rs.getInt("idactivite");
				String libelle = rs.getString("libelle");
				String titre = rs.getString("titre");
				int idorganisateur = rs.getInt("idpersonne");
				int idtypeactivite = rs.getInt("idtypeactivite");
				int nbrparticipant = rs.getInt("nbrparticipant");
				Date datedebut = rs.getTimestamp("datedebut");
				Date datefin = rs.getTimestamp("datefin");
				String adresse = rs.getString("adresse");
				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				double note = rs.getDouble("note");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				Date datenaissance = rs.getTimestamp("datenaissance");
				String photo = rs.getString("photo");
				boolean archive = true;
				int sexe = rs.getInt("sexe");
				int totalavis = rs.getInt("totalavis");
				int nbmaxwayd = rs.getInt("nbmaxwayd");
				int nbrvu = rs.getInt("nbrvu");
				int nbr_interet = rs.getInt("nbr_interet");
				
				// Date datefinactivite = rs.getTimestamp("d_finactivite");

				activite = new ActiviteBean(id, titre, libelle, idorganisateur,
						datedebut, datefin, idtypeactivite, latitude,
						longitude, adresse, nom, prenom, photo, note, 1,
						archive, totalavis, datenaissance, sexe,
						nbrparticipant, true, true, nbmaxwayd, nbrvu,nbr_interet);

				retour.add(activite);
			}

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return retour;
		} finally {
			CxoPool.close(connexion, preparedStatement, rs);
		}

		Collections.sort(retour, new Comparator<ActiviteBean>() {

			@Override
			public int compare(ActiviteBean o1, ActiviteBean o2) {
				// TODO Auto-generated method stub

				return o2.datedebut.compareTo(o1.datedebut);
			}
		});
		return retour;

	}

	public static ArrayList<website.metier.MessageBean> getMesMessages(
			int idpersonne, int etatFiltreMessage) {
		website.metier.MessageBean activite = null;
		ArrayList<website.metier.MessageBean> retour = new ArrayList<website.metier.MessageBean>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		Connection connexion = null;
		try {
			connexion = CxoPool.getConnection();
			String requete = "";
			switch (etatFiltreMessage)

			{
			case TypeEtatMessage.LU:

				requete = "SELECT personne.prenom as pseudo,sujet,corps,message.idpersonne,message.datecreation,idmessage,"
						+ "iddestinataire,lu,emis,iddiscussion"
						+ " from message,personne where personne.idpersonne=message.idpersonne and message.iddestinataire=? and lu=true and emis=false";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idpersonne);
				rs = preparedStatement.executeQuery();
				break;

			case TypeEtatMessage.TOUS:

				requete = "SELECT personne.prenom as pseudo,sujet,corps,message.idpersonne,message.datecreation,idmessage,"
						+ "iddestinataire,lu,emis,iddiscussion"
						+ " from message,personne where personne.idpersonne=message.idpersonne and message.iddestinataire=? and emis=false";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idpersonne);
				rs = preparedStatement.executeQuery();
				break;

			case TypeEtatMessage.NONLU:

				requete = "SELECT personne.prenom as pseudo,sujet,corps,message.idpersonne,message.datecreation,idmessage,iddestinataire,lu,emis,iddiscussion"
						+ " from message,personne where personne.idpersonne=message.idpersonne and message.iddestinataire=? and lu=false and emis=false";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idpersonne);
				rs = preparedStatement.executeQuery();
				break;

			}

			while (rs.next()) {
				int id = rs.getInt("idmessage");
				String message = rs.getString("corps");
				String nomEmetteur = rs.getString("pseudo");
				Date dateCreation = rs.getTimestamp("datecreation");
				boolean emis = rs.getBoolean("emis");
				boolean lu = rs.getBoolean("lu");

				// Date datefinactivite = rs.getTimestamp("d_finactivite");

				activite = new website.metier.MessageBean(id, nomEmetteur,
						dateCreation, message, lu, emis);

				retour.add(activite);

			}

			preparedStatement.close();
			rs.close();
			// Cherche dans les activite

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return retour;
		} finally {
			CxoPool.close(connexion, preparedStatement, rs);
		}

		Collections.sort(retour, new Comparator<website.metier.MessageBean>() {

			@Override
			public int compare(website.metier.MessageBean o1,
					website.metier.MessageBean o2) {
				// TODO Auto-generated method stub

				return o2.getDateCreation().compareTo(o1.getDateCreation());
			}
		});
		return retour;

	}

	public static ArrayList<ActiviteBean> getListActivite(
			FitreAdminActivites filtre, int page, int maxResult) {

		int offset = (maxResult) * page;

		int typeactivite = filtre.getTypeactivite();
		int typeUser_ = filtre.getTypeUser();
		int rayonmetre = filtre.getRayon();
		int typeSignalement = filtre.getTypeSignalement();
		int etatActivite=filtre.getEtatActivite();
		double malatitude = filtre.getLatitude();
		double malongitude = filtre.getLongitude();

		System.out.println("lat "+malatitude +" lon "+malongitude);
		
		double coef = rayonmetre * 0.007 / 700;
		double latMin = malatitude - coef;
		double latMax = malatitude + coef;
		double longMin = malongitude - coef;
		double longMax = malongitude + coef;

		ActiviteBean activite = null;

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<ActiviteBean> retour = new ArrayList<ActiviteBean>();
		Connection connexion = null;

		try {

			connexion = CxoPool.getConnection();

			String requete = "SELECT 	personne.prenom,    personne.sexe,    personne.nom,    personne.idpersonne,personne.datenaissance,"
					+ " personne.note,personne.nbravis as totalavis,personne.photo,activite.idactivite,    activite.titre,    activite.libelle,    activite.adresse,"
					+ "activite.latitude,    activite.longitude,    activite.actif,    activite.idtypeactivite,    activite.datefin,    activite.datedebut,"
					+ "activite.idpersonne,    activite.datecreation,    activite.nbrwaydeur as nbrparticipant,    activite.nbmaxwayd,    activite.d_finactivite,"
					+ "activite.typeacces,    activite.typeuser,activite.nbrvu,    activite.nbrvu, COALESCE(tablesignalement.nbrsignalement, 0::bigint) AS nbrsignalement, "
					+ "type_activite.nom as libelleActivite "
					+ " FROM activite"
					+ " LEFT JOIN ( SELECT count(*) AS nbrsignalement, signaler_activite.idactivite "
					+ " FROM signaler_activite  GROUP BY signaler_activite.idactivite) tablesignalement ON activite.idactivite = tablesignalement.idactivite"
					+ " LEFT JOIN type_activite ON type_activite.idtypeactivite = activite.idtypeactivite "
					+ " left join personne on personne.idpersonne = activite.idpersonne "
					+ " WHERE activite.latitude between ? and ? and activite.longitude between ? and ?  ";

			// tablesignalement.nbrsignalement = 1
			if (typeactivite != TypeActiviteBean.TOUS) {// on trie sur
														// l'activité

				requete = requete + " and activite.idtypeactivite=? ";

			}

			if (typeUser_ != TypeUser.TOUS) {// on trie sur l'activité

				requete = requete + " and activite.typeuser=? ";

			}
			
			
			
			switch(etatActivite){
			
			case TypeEtatActivite.ENCOURS:
			
				requete=requete+" and current_timestamp between datedebut and datefin ";
				
			break;
			
			 case TypeEtatActivite.PLANIFIEE:
				
					requete=requete+" and datedebut>current_timestamp ";
					
				break;
				
				
			case TypeEtatActivite.TERMINEE:
			
				requete=requete+" and datefin<current_timestamp ";
				break;
			
			
			}
			

			switch (typeSignalement) {

			case TypeSignalement.AUMOINSUNE:
				requete = requete + " and nbrsignalement>0 ";

				break;

			case TypeSignalement.MOINSDE10:
				requete = requete + " and nbrsignalement<10  or nbrsignalement is null ";

				break;
			case TypeSignalement.PLUSDE10:
				requete = requete + " and nbrsignalement>=10 ";
				break;

			case TypeSignalement.TOUS:

				break;
			}

			requete = requete
					+ " order by activite.datecreation desc limit ?  offset ?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setDouble(1, latMin);
			preparedStatement.setDouble(2, latMax);
			preparedStatement.setDouble(3, longMin);
			preparedStatement.setDouble(4, longMax);

			int index = 5;

			if (typeactivite != TypeActiviteBean.TOUS) {// on trie sur
														// l'activité

				preparedStatement.setInt(index, typeactivite);
				index++;
			}

			if (typeUser_ != TypeUser.TOUS) {// on trie sur l'activité

				preparedStatement.setInt(index, typeUser_);
				index++;

			}

			preparedStatement.setInt(index, maxResult);
			index++;
			preparedStatement.setInt(index, offset);

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
				double note = rs.getDouble("note");
				String nom = rs.getString("nom");
				String pseudo = rs.getString("prenom");
				// Date datefinactivite = rs.getTimestamp("d_finactivite");
				String photo = rs.getString("photo");
				Date datenaissance = rs.getTimestamp("datenaissance");
				int totalavis = rs.getInt("totalavis");
				int typeUser = rs.getInt("typeuser");
				int typeAcces = rs.getInt("typeacces");
				// Date datefinactivite = rs.getTimestamp("d_finactivite");

				String libelleActivite = rs.getString("libelleActivite");
				String adresse = rs.getString("adresse");
				int nbrSignalement = rs.getInt("nbrsignalement");
				int nbrVu=rs.getInt("nbrvu");
				
				activite = new ActiviteBean(id, titre, libelle, idorganisateur,
						datedebut, datefin, idtypeactivite, latitude,
						longitude, nom, pseudo, photo, note, totalavis,
						datenaissance, sexe, nbrparticipant, nbmaxwayd,
						typeUser, typeAcces, libelleActivite, adresse,
						nbrSignalement);
			
				activite.setPositionRecherche(filtre.getLatitude(), filtre.getLongitude());
				activite.setNbrVu(nbrVu);
			
				retour.add(activite);

			}

			return retour;
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return retour;
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}

	}

	
	public static boolean terminerActivite(int idActivite) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = CxoPool.getConnection();
			String requete = "UPDATE public.activite   SET     datefin=current_timestamp,datefin=current_timestamp WHERE idactivite=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idActivite);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			return true;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return false;

		} finally {

			CxoPool.closeConnection(connexion);
		}

	}

	public static boolean supprimeActivite(int idactivite) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = CxoPool.getConnection();

			String requete = "DELETE FROM demandeami where ( idactivite=? );";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idactivite);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM public.participer where ( idactivite=? );";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idactivite);

			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM public.noter where ( idactivite=? );";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idactivite);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM public.activite where ( idactivite=? );";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idactivite);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "Delete from messagebyact where idactivite=? ;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idactivite);
			preparedStatement.execute();

			preparedStatement.close();

			return true;
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return false;

		} finally {

			CxoPool.closeConnection(connexion);
		}
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

	}

	public MessageServeur effaceActivite(int idorganisateur, int idactivite) {

		long debut = System.currentTimeMillis();

		Connection connexion = null;

		try {

			connexion = CxoPool.getConnection();

			wayde.dao.ActiviteDAO activitedao = new wayde.dao.ActiviteDAO(
					connexion);
			ParticipationDAO participationdao = new ParticipationDAO(connexion);
			Activite activite = activitedao.getActivite(idactivite);

			if (activite == null)
				return new MessageServeur(false,  Erreur_HTML.ACTIVITE_EXISTE_PLUS);

			if (activite.isTerminee())
				return new MessageServeur(false, Erreur_HTML.ACTIVITE_SUPPRIMEE);

			// Recuepre les personnes interesse par cette activit�e
			connexion.setAutoCommit(false);
			final ArrayList<Personne> personneinteresse = activitedao
					.getListPersonneInterresse(activitedao
							.getActivite(idactivite));
			ArrayList<Personne> participants = participationdao
					.getListPartipantActivite(idactivite);

			activitedao.RemoveOnlyActivite(idactivite);
			connexion.commit();

			// ************ Si l'activité est en cours je brodact via
			// GCM*******************
			if (activite.isEnCours())
				PoolThreadGCM.poolThread.execute(new EffaceActiviteGcm(
						personneinteresse, participants, idactivite));
			// ******************************************************************************

			String loginfo = "effaceActivite - "
					+ (System.currentTimeMillis() - debut) + "ms";
			LOG.info(loginfo);
			return new MessageServeur(true, TextWebService.suppressionActivite);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			try {
				connexion.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return new MessageServeur(false, e1.getMessage());

			}
			return new MessageServeur(false, e.getMessage());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	

	public void addActiviteWaydeur(int idpersonne, String titre,
			String commentaire, String adresse, double latitude,
			double longitude, int idtypeactivite, int maxwaydeur, int duree,
			int compteWaydeur) {
		// TODO Auto-generated method stub

		Connection connexion = null;
		Date dateDebut = new Date();
		Calendar calFinActivite = Calendar.getInstance();
		calFinActivite.setTime(dateDebut);
		calFinActivite.add(Calendar.MINUTE, duree);
		Date datefinActivite = calFinActivite.getTime();

		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "INSERT INTO activite("
					+ "idpersonne, titre, libelle,datedebut,"
					+ " datefin, adresse, latitude, longitude, actif,"
					+ " idtypeactivite,datecreation,typeuser,typeacces,nbmaxwayd,nbrvu )"
					+ "	VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,0)";

			PreparedStatement preparedStatement = connexion.prepareStatement(
					requete, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setString(2, titre);
			preparedStatement.setString(3, commentaire);
			preparedStatement.setTimestamp(4,
					new java.sql.Timestamp(dateDebut.getTime()));
			preparedStatement.setTimestamp(5, new java.sql.Timestamp(
					datefinActivite.getTime()));
			preparedStatement.setString(6, adresse);
			preparedStatement.setDouble(7, latitude);
			preparedStatement.setDouble(8, longitude);
			preparedStatement.setBoolean(9, true);
			preparedStatement.setInt(10, idtypeactivite);
			preparedStatement.setTimestamp(11, new java.sql.Timestamp(
					new Date().getTime()));
			preparedStatement.setInt(12, ProfilBean.WAYDEUR);
			preparedStatement.setInt(13, ActiviteBean.GRATUIT);
			preparedStatement.setInt(14, maxwaydeur);
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<ActiviteBean> getListActivites(Double malatitude,
			Double malongitude, int rayonmetre, int idtypeactivite_,
			String motcle, int typeUser, int commenceDans) {

		int TOUTES = -1;
		Connection connexion = null;
		ArrayList<ActiviteBean> retour = new ArrayList<ActiviteBean>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		LOG.info("commence" + commenceDans);
		try {
			connexion = CxoPool.getConnection();
			double coef = rayonmetre * 0.007 / 700;
			double latMin = malatitude - coef;
			double latMax = malatitude + coef;
			double longMin = malongitude - coef;
			double longMax = malongitude + coef;
			ActiviteBean activite = null;

			Calendar calendrierDebut = Calendar.getInstance();

			if (commenceDans != TOUTES)
				calendrierDebut.add(Calendar.MINUTE, commenceDans * 60);

			Date dateRechercheDebut = calendrierDebut.getTime();

			Calendar calendrierFin = Calendar.getInstance();
			int finiDans = (commenceDans) * 60 + 60;
			calendrierFin.add(Calendar.MINUTE, finiDans);
			Date dateRechercheFin = calendrierFin.getTime();
			// on remonte les activités dont le debut est comprise entre
			// l'heure
			// actuelle + commenceDans et l'heure actuelle + commenceDans+1
			// heure

			String requete;

			if (commenceDans != TOUTES) {

				requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
						+ " activite.longitude,    personne.prenom,    personne.sexe,    personne.nom,    personne.idpersonne,personne.datenaissance,    "
						+ "personne.note,personne.nbravis as totalavis,personne.photo,"
						+ "activite.nbrwaydeur as nbrparticipant,1 as role,"
						+ "activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite,activite.nbmaxwayd  FROM personne,"
						+ "activite  WHERE personne.idpersonne = activite.idpersonne  "
						+ "and (? between datedebut and  datefin )"
						+ " and activite.latitude between ? and ?"
						+ " and activite.longitude between ? and ?";
			} else {
				requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
						+ " activite.longitude,    personne.prenom,    personne.sexe,    personne.nom,    personne.idpersonne,personne.datenaissance,    "
						+ "personne.note,personne.nbravis as totalavis,personne.photo,"
						+ "activite.nbrwaydeur as nbrparticipant,1 as role,"
						+ "activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite,activite.nbmaxwayd  FROM personne,"
						+ "activite  WHERE personne.idpersonne = activite.idpersonne  "
						+ "and (datefin>? )"
						+ " and activite.latitude between ? and ?"
						+ " and activite.longitude between ? and ?";

			}

			if (idtypeactivite_ != 0) {
				requete = requete + " and activite.idtypeactivite=?";
			}

			if (motcle != null) {

				if (!motcle.equals(""))
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

			if (idtypeactivite_ != 0) {
				index++;
				preparedStatement.setInt(index, idtypeactivite_);

			}

			if (motcle != null) {
				if (!motcle.equals("")) {
					index++;
					String test = "%" + motcle + "%";
					preparedStatement.setString(index, test);
					index++;
					preparedStatement.setString(index, test);
				}
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
				// Date datefinactivite = rs.getTimestamp("d_finactivite");

				if (prenom == null)
					prenom = "";
				String photo = rs.getString("photo");
				int role = rs.getInt("role");
				Date datenaissance = rs.getTimestamp("datenaissance");
				boolean archive = false;
				int totalavis = rs.getInt("totalavis");
				activite = new ActiviteBean(id, titre, libelle, idorganisateur,
						datedebut, datefin, idtypeactivite, latitude,
						longitude, adresse, nom, prenom, photo, note, role,
						archive, totalavis, datenaissance, sexe,
						nbrparticipant, true, true, nbmaxwayd);
				retour.add(activite);

			}

			rs.close();
			preparedStatement.close();
			return retour;

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return retour;
		} finally {
			try {
				if (connexion != null)
					connexion.close();
				if (rs != null)
					rs.close();
				if (preparedStatement != null)
					rs.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public ArrayList<ActiviteBean> getListActivitesold(Double malatitude,
			Double malongitude, int rayonmetre, int idtypeactivite_,
			String motcle, int typeUser, int commenceDans) {

		Connection connexion = null;
		ArrayList<ActiviteBean> retour = new ArrayList<ActiviteBean>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		LOG.info("commence" + commenceDans);
		try {
			connexion = CxoPool.getConnection();
			double coef = rayonmetre * 0.007 / 700;
			double latMin = malatitude - coef;
			double latMax = malatitude + coef;
			double longMin = malongitude - coef;
			double longMax = malongitude + coef;
			ActiviteBean activite = null;

			Calendar calendrierDebut = Calendar.getInstance();
			commenceDans = (commenceDans) * 60;
			calendrierDebut.add(Calendar.MINUTE, commenceDans);
			Date dateRechercheDebut = calendrierDebut.getTime();

			Calendar calendrierFin = Calendar.getInstance();
			int finiDans = (commenceDans) * 60 + 60;
			calendrierFin.add(Calendar.MINUTE, finiDans);
			Date dateRechercheFin = calendrierFin.getTime();
			// on remonte les activités dont le debut est comprise entre
			// l'heure
			// actuelle + commenceDans et l'heure actuelle + commenceDans+1
			// heure

			// String requete =
			// " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
			// +
			// " activite.longitude,    personne.prenom,    personne.sexe,    personne.nom,    personne.idpersonne,personne.datenaissance,    "
			// + "personne.note,personne.nbravis as totalavis,personne.photo,"
			// + "activite.nbrwaydeur as nbrparticipant,1 as role,"
			// +
			// "activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite,activite.nbmaxwayd  FROM personne,"
			// + "activite  WHERE personne.idpersonne = activite.idpersonne  "
			// +
			// "and (datedebut between ? and ? or datefin between ? and ? or (datedebut<=? and datefin>=?))"
			// + " and activite.latitude between ? and ?"
			// + " and activite.longitude between ? and ?";

			String requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
					+ " activite.longitude,    personne.prenom,    personne.sexe,    personne.nom,    personne.idpersonne,personne.datenaissance,    "
					+ "personne.note,personne.nbravis as totalavis,personne.photo,"
					+ "activite.nbrwaydeur as nbrparticipant,1 as role,"
					+ "activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite,activite.nbmaxwayd  FROM personne,"
					+ "activite  WHERE personne.idpersonne = activite.idpersonne  "
					+ "and (datedebut between ? and ? or datefin between ? and ? or (datedebut<=? and datefin>=?))"
					+ " and activite.latitude between ? and ?"
					+ " and activite.longitude between ? and ?";

			if (idtypeactivite_ != 0) {

				requete = requete + " and activite.idtypeactivite=?";

			}

			if (motcle != null) {

				if (!motcle.equals(""))
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
			preparedStatement.setTimestamp(2, new java.sql.Timestamp(
					dateRechercheFin.getTime()));
			preparedStatement.setTimestamp(3, new java.sql.Timestamp(
					dateRechercheDebut.getTime()));
			preparedStatement.setTimestamp(4, new java.sql.Timestamp(
					dateRechercheFin.getTime()));
			preparedStatement.setTimestamp(5, new java.sql.Timestamp(
					dateRechercheDebut.getTime()));
			preparedStatement.setTimestamp(6, new java.sql.Timestamp(
					dateRechercheFin.getTime()));

			preparedStatement.setDouble(7, latMin);
			preparedStatement.setDouble(8, latMax);
			preparedStatement.setDouble(9, longMin);
			preparedStatement.setDouble(10, longMax);

			int index = 10;

			if (idtypeactivite_ != 0) {
				index++;
				preparedStatement.setInt(index, idtypeactivite_);

			}

			if (motcle != null) {
				if (!motcle.equals("")) {
					index++;
					String test = "%" + motcle + "%";
					preparedStatement.setString(index, test);
					index++;
					preparedStatement.setString(index, test);
				}
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
				// Date datefinactivite = rs.getTimestamp("d_finactivite");

				if (prenom == null)
					prenom = "";
				String photo = rs.getString("photo");
				int role = rs.getInt("role");
				Date datenaissance = rs.getTimestamp("datenaissance");
				boolean archive = false;
				int totalavis = rs.getInt("totalavis");
				activite = new ActiviteBean(id, titre, libelle, idorganisateur,
						datedebut, datefin, idtypeactivite, latitude,
						longitude, adresse, nom, prenom, photo, note, role,
						archive, totalavis, datenaissance, sexe,
						nbrparticipant, true, true, nbmaxwayd);
				retour.add(activite);

			}

			rs.close();
			preparedStatement.close();
			return retour;

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return retour;
		} finally {
			try {
				if (connexion != null)
					connexion.close();
				if (rs != null)
					rs.close();
				if (preparedStatement != null)
					rs.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public MessageServeur signalerActivite(int idpersonne, int idactivite,
			int idmotif, String motif, String titre, String libelle) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;

		try {

			connexion = CxoPool.getConnection();

			PersonneDAO personnedao = new PersonneDAO(connexion);

			SignalementDAO signalementdao = new SignalementDAO(connexion);

			// Verfiie que le signalement est unique
			if (signalementdao.isSignalerActvite(idpersonne, idactivite))
				return new MessageServeur(false,
						TextWebService.activiteDejaSignale);

			connexion.setAutoCommit(false);
			signalementdao.signalerActivite(idpersonne, idactivite, idmotif,
					motif, titre, libelle);
			connexion.commit();

			String loginfo = "signalerActivite - "
					+ (System.currentTimeMillis() - debut) + "ms";
			LOG.info(loginfo);

			return new MessageServeur(true, TextWebService.activiteSignale);

		} catch (SQLException | NamingException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				connexion.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public void updateChampCalcule(int idactivite) throws SQLException {
		// Met aj our le nbr participant dans activite

		String requete = "UPDATE activite SET nbrwaydeur=(select  count(idpersonne)+1 "
				+ " from participer where  idactivite=?) WHERE idactivite=?";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idactivite);
		preparedStatement.setInt(2, idactivite);
		preparedStatement.execute();

	}

	
	public static int getNbrActiviteProposeEnCours(int idpersonne)  {
		int nbractivite = 0;
		Connection connexion = null;
		PreparedStatement preparedStatement=null;
		ResultSet rs=null;
		try {
			connexion = CxoPool.getConnection();
	
			String requete = "Select count(idactivite) as nbractivite"
					+ "  FROM activite where ( idpersonne=? and  activite.datefin>?) ;";
			 preparedStatement = connexion
					.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setTimestamp(2,
					new java.sql.Timestamp(new Date().getTime()));
			 rs = preparedStatement.executeQuery();

			if (rs.next()) {
				nbractivite = rs.getInt("nbractivite");
			}
		
			
			return nbractivite ;
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return 0;
		}
		finally{
			
			CxoPool.close(connexion, preparedStatement, rs);
		
		}

		
		//	System.out.println("Calcul nombre d'activite en cours");
	

	}

	public static MessageServeur addInteretActivite(int idpersonne, int idactivite,
			int niveauInteret) {
		// TODO Auto-generated method stub
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		

		if (isInteretDejaSignale(idpersonne, idactivite))
			return new MessageServeur(false, Erreur_HTML.INTERET_DEJA_SIGNALEE);

		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "INSERT INTO interet("
					+ "   idpersonne, idactivite,niveau_interet)" + "	VALUES (?,?,?)";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setInt(2, idactivite);
			preparedStatement.setInt(3, niveauInteret);
			preparedStatement.execute();
			preparedStatement.close();
			requete = "UPDATE activite  SET  nbr_interet=nbr_interet+1 WHERE idactivite=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idactivite);
			preparedStatement.execute();
			connexion.commit();

		    return new MessageServeur(true, Erreur_HTML.INTERET_SIGNALEE);


		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {

				if (connexion != null)
					connexion.close();
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return new MessageServeur(false, Erreur_HTML.ERREUR_INCONNUE);

	
	
	}


}
