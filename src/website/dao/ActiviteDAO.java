package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.naming.NamingException;

import fcm.ServeurMethodes;
import wayde.bean.Activite;
import wayde.bean.CxoPool;
import wayde.bean.Personne;
import website.metier.ActiviteBean;
import website.metier.IndicateurWayd;
import website.metier.ParticipantBean;
import website.metier.ProfilBean;

public class ActiviteDAO {

	public void addActivitePro(int idpersonne, String titre,
			String commentaire, Date datedebut, Date datefin, String adresse,
			double latitude, double longitude, int idtypeactivite,
			int typeuser, int typeaccess) {
		Connection connexion = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "INSERT INTO activite("
					+ "idpersonne, titre, libelle,datedebut,"
					+ " datefin, adresse, latitude, longitude, actif,"
					+ " idtypeactivite,datecreation,typeuser,typeacces)"
					+ "	VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement preparedStatement = connexion.prepareStatement(
					requete, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setString(2, titre);
			preparedStatement.setString(3, commentaire);
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
			preparedStatement.close();
			connexion.commit();

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Creation activite user");

	}

	public static MessageBean effaceActivite(int idActivite) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		ActiviteBean activite = ActiviteDAO.getActivite(idActivite);

		if (!activite.isActive()) {

			return new MessageBean("L'activité n'est plus active");
		}

		try {

			connexion = CxoPool.getConnection();

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
			return new MessageBean("erreur inconnue");
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}

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

	public static ArrayList<ActiviteBean> getListActiviteTotal() {

		ActiviteBean activite = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<ActiviteBean> retour = new ArrayList<ActiviteBean>();
		Connection connexion = null;
		try {

			connexion = CxoPool.getConnection();

			String requete = " SELECT activite.datedebut,      activite.adresse,    activite.latitude,"
					+ " activite.longitude,    personne.prenom as pseudo,    personne.sexe,    personne.nom,"
					+ "personne.datenaissance  ,    personne.idpersonne, "
					+ " personne.note,personne.nbravis as totalavis  ,personne.photo,"
					+ "activite.nbrwaydeur as nbrparticipant,"
					+ "activite.idactivite,    activite.libelle, activite.titre,"
					+ " activite.datefin,activite.idtypeactivite ,activite.nbmaxwayd  FROM personne,"
					+ "activite  WHERE personne.idpersonne = activite.idpersonne  "
					+ " ORDER BY datedebut desc";

			preparedStatement = connexion.prepareStatement(requete);
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
				// Date datefinactivite = rs.getTimestamp("d_finactivite");

				int totalavis = rs.getInt("totalavis");
				activite = new ActiviteBean(id, titre, libelle, idorganisateur,
						datedebut, datefin, idtypeactivite, latitude,
						longitude, nom, pseudo, photo, note, totalavis,
						datenaissance, sexe, nbrparticipant, nbmaxwayd);

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

	// Renvoi une activité avec la liste des participants
	//
	public static ActiviteBean getActivite(int idActivite) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ActiviteBean activite = null;

		try {
			connexion = CxoPool.getConnection();
			String requete = " SELECT activite.datedebut,       activite.adresse,    activite.latitude,"
					+ " activite.longitude,    personne.prenom as pseudo,    personne.sexe,    personne.nom,    personne.idpersonne,"
					+ "personne.affichesexe,personne.afficheage,personne.datenaissance,personne.note,"
					+ "personne.nbravis as totalavis,    personne.photo,activite.idactivite,activite.libelle,"
					+ "activite.titre,activite.nbrwaydeur as nbrparticipant ,activite.nbmaxwayd,   activite.datefin, activite.idtypeactivite   FROM personne,"
					+ "activite  WHERE personne.idpersonne = activite.idpersonne  and activite.idactivite=?";

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
				// Date datefinactivite = rs.getTimestamp("d_finactivite");

				int totalavis = rs.getInt("totalavis");
				activite = new ActiviteBean(id, titre, libelle, idorganisateur,
						datedebut, datefin, idtypeactivite, latitude,
						longitude, nom, pseudo, photo, note, totalavis,
						datenaissance, sexe, nbrparticipant, nbmaxwayd);

				ArrayList<ParticipantBean> listParticipant = ParticipantDAO
						.getListPaticipant(idActivite);
				activite.setListParticipant(listParticipant);

			}

			return activite;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return null;

		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}

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
				// Date datefinactivite = rs.getTimestamp("d_finactivite");
				// System.out.println(datefinactivite);
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

	public static ArrayList<ActiviteBean> getListActivite(double malatitude,
			double malongitude, int rayonmetre, int typeactivite,
			int nbxmaxEnregistrement, int indexDebutResultat) {

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
			if (typeactivite != -1) {// on trie sur l'activité
				System.out.println("tire sur l'activite");
				String requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
						+ " activite.longitude,    personne.prenom,    personne.sexe,    personne.nom,    personne.idpersonne,personne.datenaissance,    "
						+ "personne.note,personne.nbravis as totalavis,personne.photo,"
						+ "activite.nbrwaydeur as nbrparticipant,1 as role,"
						+ "activite.idactivite,activite.libelle,activite.titre,activite.d_finactivite,activite.datefin,activite.idtypeactivite,activite.nbmaxwayd "
						+ " FROM personne,"
						+ " activite  WHERE personne.idpersonne = activite.idpersonne  and activite.idtypeactivite=?  "
						+ " and activite.latitude between ? and ?"
						+ " and activite.longitude between ? and ?"
						+ " ORDER BY datedebut  desc limit ?  offset ?";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, typeactivite);
				preparedStatement.setDouble(2, latMin);
				preparedStatement.setDouble(3, latMax);
				preparedStatement.setDouble(4, longMin);
				preparedStatement.setDouble(5, longMax);
				preparedStatement.setInt(6, nbxmaxEnregistrement);
				preparedStatement.setInt(7, indexDebutResultat);
				rs = preparedStatement.executeQuery();

			}

			else { // On renvou toutes
				System.out.println("tire toutes");
				String requete = " SELECT activite.datedebut,activite.adresse,activite.latitude,activite.longitude,personne.prenom,"
						+ "personne.sexe,personne.nom,personne.idpersonne,personne.datenaissance,personne.note,personne.nbravis as totalavis,personne.photo,"
						+ "activite.nbrwaydeur as nbrparticipant,activite.idactivite,activite.libelle,activite.titre,"
						+ "activite.datefin,activite.idtypeactivite,activite.nbmaxwayd "
						+ "FROM personne, activite "
						+ " WHERE personne.idpersonne = activite.idpersonne    "
						+ " and activite.latitude between ? and ?"
						+ " and activite.longitude between ? and ?"
						+ " ORDER BY datedebut desc limit ?  offset ?";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setDouble(1, latMin);
				preparedStatement.setDouble(2, latMax);
				preparedStatement.setDouble(3, longMin);
				preparedStatement.setDouble(4, longMax);
				preparedStatement.setInt(5, nbxmaxEnregistrement);
				preparedStatement.setInt(6, indexDebutResultat);
				rs = preparedStatement.executeQuery();

			}
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
				activite = new ActiviteBean(id, titre, libelle, idorganisateur,
						datedebut, datefin, idtypeactivite, latitude,
						longitude, nom, pseudo, photo, note, totalavis,
						datenaissance, sexe, nbrparticipant, nbmaxwayd);
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

	public static int getCountListActivite(double malatitude,
			double malongitude, int rayonmetre, int typeactivite) {

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

			if (typeactivite != -1) {// on trie sur l'activité
				System.out.println("tire sur l'activite");
				String requete = " SELECT count(idactivite) as nbr "
						+ " FROM personne, activite"
						+ "  WHERE personne.idpersonne = activite.idpersonne  and activite.idtypeactivite=?  "
						+ " and activite.latitude between ? and ?"
						+ " and activite.longitude between ? and ?";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, typeactivite);
				preparedStatement.setDouble(2, latMin);
				preparedStatement.setDouble(3, latMax);
				preparedStatement.setDouble(4, longMin);
				preparedStatement.setDouble(5, longMax);
				rs = preparedStatement.executeQuery();

			}

			else { // On renvou toutes
				System.out.println("tire toutes");
				String requete = " SELECT count(idactivite) as nbr "
						+ "FROM personne, activite "
						+ " WHERE personne.idpersonne = activite.idpersonne    "
						+ " and activite.latitude between ? and ?"
						+ " and activite.longitude between ? and ?";

				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setDouble(1, latMin);
				preparedStatement.setDouble(2, latMax);
				preparedStatement.setDouble(3, longMin);
				preparedStatement.setDouble(4, longMax);
				rs = preparedStatement.executeQuery();

			}
			if (rs.next()) {

				int nbr = rs.getInt("nbr");
				return nbr;

			}

			return 0;
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
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

		// System.out.println("Effacement d'une activite");
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

	public static ArrayList<ActiviteBean> getListActiviteSignale() {
		// TODO Auto-generated method stub
		ActiviteBean activite = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<ActiviteBean> retour = new ArrayList<ActiviteBean>();
		Connection connexion = null;
		try {

			connexion = CxoPool.getConnection();
			String requete = " SELECT activite.datedebut,      activite.adresse,    activite.latitude,"
					+ " activite.longitude,  personne.prenom as pseudo,    personne.sexe,    personne.nom,"
					+ "personne.datenaissance  ,    personne.idpersonne, "
					+ " personne.note,personne.nbravis as totalavis  ,personne.photo,"
					+ "activite.nbrwaydeur as nbrparticipant,"
					+ "activite.idactivite,  activite.libelle, activite.titre,"
					+ " activite.datefin,activite.idtypeactivite ,activite.nbmaxwayd "
					+ " FROM personne,activite,signaler_activite  "
					+ "  WHERE personne.idpersonne = activite.idpersonne and signaler_activite.idactivite=activite.idactivite"
					+ " ORDER BY datedebut desc";

			preparedStatement = connexion.prepareStatement(requete);
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
				// Date datefinactivite = rs.getTimestamp("d_finactivite");

				int totalavis = rs.getInt("totalavis");
				activite = new ActiviteBean(id, titre, libelle, idorganisateur,
						datedebut, datefin, idtypeactivite, latitude,
						longitude, nom, pseudo, photo, note, totalavis,
						datenaissance, sexe, nbrparticipant, nbmaxwayd);

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

	public void addActiviteWaydeur(int idpersonne, String titre,
			String commentaire, String adresse,
			double latitude, double longitude, int idtypeactivite,
			int maxwaydeur, int duree, int compteWaydeur) {
		// TODO Auto-generated method stub

		Connection connexion = null;
		Date dateDebut=new Date();
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
					+ " idtypeactivite,datecreation,typeuser,typeacces,nbmaxwayd )"
					+ "	VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

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
		System.out.println("Creation activite user");

	}
	
	public ArrayList<Activite> getListActivites(Double malatitude,
			Double malongitude, int rayonmetre, int idtypeactivite_,
			String motcle, long debutActivite, long finActivite, int typeUser,
			int typeacces, int commenceDans){

		Connection connexion = null;
		ArrayList<Activite> retour = new ArrayList<Activite>();
		PreparedStatement preparedStatement=null;
		ResultSet rs = null;
		
		try {
			connexion = CxoPool.getConnection();
			double coef = rayonmetre * 0.007 / 700;
			double latMin = malatitude - coef;
			double latMax = malatitude + coef;
			double longMin = malongitude - coef;
			double longMax = malongitude + coef;
			Activite activite = null;
			
			Calendar calendrierDebut = Calendar.getInstance();
			commenceDans = (commenceDans ) * 60;
			calendrierDebut.add(Calendar.MINUTE, commenceDans);
			Date dateRechercheDebut = calendrierDebut.getTime();
			
			Calendar calendrierFin = Calendar.getInstance();
			int finiDans = (commenceDans) * 60 + 60;
			calendrierFin.add(Calendar.MINUTE, finiDans);
			Date dateRechercheFin = calendrierFin.getTime();

			// on remonte les activités dont le debut est comprise entre l'heure actuelle + commenceDans et l'heure actuelle + commenceDans+1 heure
		

			String requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
					+ " activite.longitude,    personne.prenom,    personne.sexe,    personne.nom,    personne.idpersonne,personne.datenaissance,    "
					+ "personne.note,personne.nbravis as totalavis,personne.photo,"
					+ "activite.nbrwaydeur as nbrparticipant,1 as role,"
					+ "activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite,activite.nbmaxwayd  FROM personne,"
					+ "activite  WHERE personne.idpersonne = activite.idpersonne  "
					+ "and datedebut between ? and ? "
					+ " and activite.latitude between ? and ?"
					+ " and activite.longitude between ? and ?";

			if (idtypeactivite_ != 0) {

				requete = requete + " and activite.idtypeactivite=?";

			}

			if (motcle != null) {

				requete = requete
						+ " and ( UPPER(libelle) like UPPER(?) or UPPER(titre) like UPPER(?))  )";

			}

			if (typeUser != 0) {

				requete = requete + " and activite.typeuser=?";

			}

			if (typeacces != 0) {
				requete = requete + " and activite.typeacces=?";

			}

			preparedStatement = connexion
					.prepareStatement(requete);

			preparedStatement.setTimestamp(1, new java.sql.Timestamp(
					dateRechercheDebut.getTime()));
			preparedStatement.setTimestamp(2, new java.sql.Timestamp(
					dateRechercheFin.getTime()));

			preparedStatement.setDouble(3, latMin);
			preparedStatement.setDouble(4, latMax);
			preparedStatement.setDouble(5, longMin);
			preparedStatement.setDouble(6, longMax);

			int index = 6;

			if (idtypeactivite_ != 0) {

				index++;
				preparedStatement.setInt(index, idtypeactivite_);

			}

			if (motcle != null) {

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

			if (typeacces!=0) {
				index++;
				preparedStatement.setInt(index, typeacces);

			}

			//

			requete = requete + " ORDER BY datedebut asc;";

			 rs = preparedStatement.executeQuery();

			while (rs.next()) {

				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				double distance = ServeurMethodes.getDistance(malatitude, latitude,
						malongitude, longitude);
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
				activite = new Activite(id, titre, libelle, idorganisateur,
						datedebut, datefin, idtypeactivite, latitude, longitude,
						adresse, nom, prenom, photo, note, role, archive,
						totalavis, datenaissance, sexe, nbrparticipant, true, true,
						nbmaxwayd);
				retour.add(activite);

			}

			rs.close();
			preparedStatement.close();
			// System.out.println("Activite total:" + total);
			return retour;

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return retour;
		}
		finally{
			try {
			if (connexion!=null)	connexion.close();
			if (rs!=null)rs.close();
			if (preparedStatement!=null)rs.close();
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}

}

	


