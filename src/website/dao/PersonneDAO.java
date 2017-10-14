package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import wayd.ws.WBservices;
import wayde.bean.CxoPool;
import website.metier.AvisBean;
import website.metier.ProfilBean;

public class PersonneDAO {
	private static final Logger LOG = Logger.getLogger(WBservices.class);

	public static ArrayList<ProfilBean> getListProfil() {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ProfilBean profil = null;
		ArrayList<ProfilBean> retour = new ArrayList<ProfilBean>();
//
		try {
			connexion = CxoPool.getConnection();
			Statement stmt = connexion.createStatement();
			// System.out.println("Cherche compte personen par Id" +
			// idpersonne);
			String requete = " SELECT personne.note,personne.nbravis,"
					+ "(SELECT COUNT(*) FROM activite where idpersonne=personne.idpersonne ) as nbractivite,"
					+ "(SELECT COUNT(*) FROM participer where idpersonne=personne.idpersonne ) as nbrparticipation,"
					+ "(SELECT COUNT(*) FROM ami where idpersonne=personne.idpersonne ) as nbrami,"
					+ "idpersonne, nom, prenom, login, pwd, ville, actif, verrouille,admin,"
					+ "nbrecheccnx, datecreation,  datenaissance, sexe,affichesexe, afficheage,"
					+ "  mail, cleactivation,commentaire, photo,typeuser,"
					+ "premiereconnexion,latitude,longitude,adresse,siteweb,telephone,latitudefixe,longitudefixe,siret FROM personne";

			preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();
			stmt.close();

			while (rs.next()) {
				int id = rs.getInt("idpersonne");
				int nbravis = rs.getInt("nbravis");
				int nbractivite = rs.getInt("nbractivite");
				int nbrparticipation = rs.getInt("nbrparticipation");
				int nbrami = rs.getInt("nbrami");
				String commentaire = rs.getString("commentaire");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				Date datecreation = rs.getTimestamp("datecreation");
				Date datenaissance = rs.getTimestamp("datenaissance");
				boolean afficheage = rs.getBoolean("afficheage");
				boolean affichesexe = rs.getBoolean("affichesexe");
				boolean actif = rs.getBoolean("actif");
				String photo = rs.getString("photo");
				int sexe = rs.getInt("sexe");
				double note = rs.getDouble("note");
				boolean admin = rs.getBoolean("admin");
				int typeuser = rs.getInt("typeuser");
				boolean premiereconnexion = rs.getBoolean("premiereconnexion");
				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				String adresse = rs.getString("adresse");

				String telephone = rs.getString("telephone");
				String siteWeb = rs.getString("siteweb");
				double latitudeFixe = rs.getDouble("latitudefixe");
				double longitudeFixe = rs.getDouble("longitudefixe");
				String siret = rs.getString("siret");
				profil = new ProfilBean(id, nom, prenom, datecreation,
						datenaissance, nbravis, sexe, nbractivite,
						nbrparticipation, nbrami, note, photo, affichesexe,
						afficheage, commentaire, actif, admin, typeuser,
						premiereconnexion, latitude, longitude, adresse,
						siteWeb, telephone, latitudeFixe, longitudeFixe,siret);

				retour.add(profil);

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

	public static ProfilBean getFullProfil(int idpersonne) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ProfilBean profil = null;

		try {
			connexion = CxoPool.getConnection();

			Statement stmt = connexion.createStatement();
			// System.out.println("Cherche compte personen par Id" +
			// idpersonne);
			String requete = " SELECT personne.note,personne.nbravis,"
					+ "(SELECT COUNT(*) FROM activite where idpersonne=personne.idpersonne ) as nbractivite,"
					+ "(SELECT COUNT(*) FROM participer where idpersonne=personne.idpersonne ) as nbrparticipation,"
					+ "(SELECT COUNT(*) FROM ami where idpersonne=personne.idpersonne ) as nbrami,"
					+ "idpersonne, nom, prenom, login, pwd, ville, actif, verrouille,admin,"
					+ "nbrecheccnx, datecreation,  datenaissance, sexe,affichesexe, afficheage,"
					+ "  mail, cleactivation,commentaire, photo,typeuser,premiereconnexion,latitude,longitude,adresse"
					+ ",siteweb,telephone,latitudefixe,longitudefixe,siret "
					+ " FROM personne where idpersonne=?";

			preparedStatement = connexion.prepareStatement(requete);

			preparedStatement.setInt(1, idpersonne);
			rs = preparedStatement.executeQuery();
			stmt.close();

			if (rs.next()) {
				int id = rs.getInt("idpersonne");
				int nbravis = rs.getInt("nbravis");
				int nbractivite = rs.getInt("nbractivite");
				int nbrparticipation = rs.getInt("nbrparticipation");
				int nbrami = rs.getInt("nbrami");
				String commentaire = rs.getString("commentaire");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				Date datecreation = rs.getTimestamp("datecreation");
				Date datenaissance = rs.getTimestamp("datenaissance");
				boolean afficheage = rs.getBoolean("afficheage");
				boolean affichesexe = rs.getBoolean("affichesexe");
				boolean actif = rs.getBoolean("actif");
				String photo = rs.getString("photo");
				int sexe = rs.getInt("sexe");
				double note = rs.getDouble("note");
				boolean admin = rs.getBoolean("admin");
				int typeuser = rs.getInt("typeuser");
				// System.out.println("Note" + note);
				boolean premiereconnexion = rs.getBoolean("premiereconnexion");
				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				String adresse = rs.getString("adresse");
				String telephone = rs.getString("telephone");
				String siteWeb = rs.getString("siteweb");
				double latitudeFixe = rs.getDouble("latitudefixe");
				double longitudeFixe = rs.getDouble("longitudefixe");
				String siret = rs.getString("siret");
				profil = new ProfilBean(id, nom, prenom, datecreation,
						datenaissance, nbravis, sexe, nbractivite,
						nbrparticipation, nbrami, note, photo, affichesexe,
						afficheage, commentaire, actif, admin, typeuser,
						premiereconnexion, latitude, longitude, adresse,
						siteWeb, telephone, latitudeFixe, longitudeFixe,siret);

			}
			return profil;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return profil;
		} finally {
			CxoPool.close(connexion, preparedStatement, rs);
		}

	}

	public static ProfilBean getFullProfilByUid(String uid) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ProfilBean profil = null;

		try {
			connexion = CxoPool.getConnection();

			Statement stmt = connexion.createStatement();
			// System.out.println("Cherche compte personen par Id" +
			// idpersonne);
			String requete = " SELECT personne.note,personne.nbravis,"
					+ "(SELECT COUNT(*) FROM activite where idpersonne=personne.idpersonne ) as nbractivite,"
					+ "(SELECT COUNT(*) FROM participer where idpersonne=personne.idpersonne ) as nbrparticipation,"
					+ "(SELECT COUNT(*) FROM ami where idpersonne=personne.idpersonne ) as nbrami,"
					+ "idpersonne, nom, prenom, login, pwd, ville, actif, verrouille,admin,"
					+ "nbrecheccnx, datecreation,  datenaissance, sexe,affichesexe, afficheage,"
					+ "  mail, cleactivation,commentaire,"
					+ "typeuser,photo,premiereconnexion,latitude,longitude,adresse"
					+ ",siteweb,telephone,latitudefixe,longitudefixe,siret FROM personne where login=?";

			preparedStatement = connexion.prepareStatement(requete);

			preparedStatement.setString(1, uid);
			rs = preparedStatement.executeQuery();
			stmt.close();

			if (rs.next()) {
				int id = rs.getInt("idpersonne");
				int nbravis = rs.getInt("nbravis");
				int nbractivite = rs.getInt("nbractivite");
				int nbrparticipation = rs.getInt("nbrparticipation");
				int nbrami = rs.getInt("nbrami");
				String commentaire = rs.getString("commentaire");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				Date datecreation = rs.getTimestamp("datecreation");
				Date datenaissance = rs.getTimestamp("datenaissance");
				boolean afficheage = rs.getBoolean("afficheage");
				boolean affichesexe = rs.getBoolean("affichesexe");
				boolean actif = rs.getBoolean("actif");
				boolean admin = rs.getBoolean("admin");
				String photo = rs.getString("photo");
				int sexe = rs.getInt("sexe");
				int typeuser = rs.getInt("typeuser");
				double note = rs.getDouble("note");
				boolean premiereconnexion = rs.getBoolean("premiereconnexion");
				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				String adresse = rs.getString("adresse");
				String telephone = rs.getString("telephone");
				String siteWeb = rs.getString("siteweb");
				double latitudeFixe = rs.getDouble("latitudefixe");
				double longitudeFixe = rs.getDouble("longitudefixe");
				String siret = rs.getString("siret");
				profil = new ProfilBean(id, nom, prenom, datecreation,
						datenaissance, nbravis, sexe, nbractivite,
						nbrparticipation, nbrami, note, photo, affichesexe,
						afficheage, commentaire, actif, admin, typeuser,
						premiereconnexion, latitude, longitude, adresse,
						siteWeb, telephone, latitudeFixe, longitudeFixe,siret);

			}

			return profil;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return null;
		} finally {
			CxoPool.close(connexion, preparedStatement, rs);
		}

	}

	public static MessageBean activerProfil(int idpersonne, boolean actif) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();

			String requete = "UPDATE  personne set actif=? "
					+ " WHERE idpersonne=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setBoolean(1, actif);
			preparedStatement.setInt(2, idpersonne);
			preparedStatement.execute();

			return new MessageBean("Ok");

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return new MessageBean("Erreur ");

		} finally {
			CxoPool.close(connexion, preparedStatement, rs);
		}

	}

	public boolean updateProfilPro(String nom, String adresse, double latitude,
			double longitude,  String commentaire, String siret,String telephonne,int idpersonne) {
		// TODO Auto-generated method stub

		Connection connexion = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			System.out.println("uopdate user");
			String requete = "UPDATE  personne set prenom=?, adresse=?,latitude=?,longitude=?,commentaire=?,"
					+ "premiereconnexion=false,typeuser=?,latitudefixe=?,longitudefixe=?, siret=?,telephone=?"
					+ " WHERE idpersonne=?";
			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);
			preparedStatement.setString(1, nom);
			preparedStatement.setString(2, adresse);
			preparedStatement.setDouble(3, latitude);
			preparedStatement.setDouble(4, longitude);
			preparedStatement.setString(5, commentaire);
			preparedStatement.setInt(6, ProfilBean.PRO);
			preparedStatement.setDouble(7, latitude);
			preparedStatement.setDouble(8, longitude);
			preparedStatement.setString(9, siret);
			preparedStatement.setString(10, telephonne);
			preparedStatement.setInt(11, idpersonne);
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

	public boolean updateProfilProFull(String nom, String adresse,
			double latitude, double longitude, String commentaire,
			int idpersonne, String siteWeb, String telephone) {
		// TODO Auto-generated method stub

		Connection connexion = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			System.out.println("uopdate user");
			String requete = "UPDATE  personne set prenom=?, adresse=?,latitude=?,longitude=?,commentaire=?,"
					+ "siteweb=?,telephone=?,latitudefixe=?,longitudefixe=? "
					+ " WHERE idpersonne=?";
			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);
			preparedStatement.setString(1, nom);
			preparedStatement.setString(2, adresse);
			preparedStatement.setDouble(3, latitude);
			preparedStatement.setDouble(4, longitude);
			preparedStatement.setString(5, commentaire);
			preparedStatement.setString(6, siteWeb);
			preparedStatement.setString(7, telephone);
			preparedStatement.setDouble(8, latitude);
			preparedStatement.setDouble(9, longitude);
			preparedStatement.setInt(10, idpersonne);
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();

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
		return true;

	}

	public void updateProfilAssociation(String nom, String adresse,
			double latitude, double longitude, int typeuser) {
		// TODO Auto-generated method stub

	}

	public boolean updateProfilWaydeur(String nom, int sexe,
			String commentaire, int idpersonne) {
		Connection connexion = null;
		LOG.info("updateProfilProFullWaydeur");
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			System.out.println("uopdate user");
			String requete = "UPDATE  personne set prenom=?, commentaire=?,sexe=?,typeuser=?,premiereconnexion=false"
					+ " WHERE idpersonne=?";
			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);
			preparedStatement.setString(1, nom);
			preparedStatement.setString(2, commentaire);
			preparedStatement.setInt(3, sexe);
			preparedStatement.setInt(4, ProfilBean.WAYDEUR);
			preparedStatement.setInt(5, idpersonne);
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			try {
				connexion.rollback();

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;

		} finally {

			try {
				connexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;

	}

	public boolean updatePhoto(String photo, int idpersonne) {
		Connection connexion = null;
		LOG.info("updatePhoto");
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			System.out.println("uopdate user");
			String requete = "UPDATE  personne set photo=?"
					+ " WHERE idpersonne=?";
			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);
			preparedStatement.setString(1, photo);
			preparedStatement.setInt(2, idpersonne);
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			try {
				connexion.rollback();

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;

		} finally {

			try {
				connexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;

	}

	public boolean updateProfilProFullWaydeur(String nom, String adresse,
			String commentaire, int sexe, boolean afficheSexe,
			boolean afficheAge, Date datenaissance, int idpersonne) {
		Connection connexion = null;
		LOG.info("updateProfilProFullWaydeur");
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			System.out.println("uopdate user");
			String requete = "UPDATE  personne set prenom=?, adresse=?,commentaire=?,datenaissance=? ,affichesexe=?,afficheage=?,sexe=?"
					+ " WHERE idpersonne=?";
			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);
			preparedStatement.setString(1, nom);
			preparedStatement.setString(2, adresse);
			preparedStatement.setString(3, commentaire);
			preparedStatement.setTimestamp(4, new java.sql.Timestamp(
					datenaissance.getTime()));
			preparedStatement.setBoolean(5, afficheSexe);
			preparedStatement.setBoolean(6, afficheAge);
			preparedStatement.setInt(7, sexe);
			preparedStatement.setInt(8, idpersonne);
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();

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
		return true;
	}
	public static ArrayList<AvisBean> getListAvis(int idpersonnenotee) {
		AvisBean avis = null;
		ArrayList<AvisBean> retour = new ArrayList<AvisBean>();
		Connection connexion=null;
		ResultSet rs=null;
		PreparedStatement preparedStatement=null;
		try {
			 connexion = CxoPool.getConnection();
			
			String requete = " SELECT   activite.titre as titreactivite,personne.prenom,      personne.nom,    personne.photo,"
					+ "noter.idactivite,  noter.idpersonnenotateur,noter.idpersonnenotee,noter.idnoter,noter.titre,"
					+ "noter.libelle,noter.note,noter.datenotation"
					+ "  FROM personne,noter,activite "
					+ "  WHERE personne.idpersonne = noter.idpersonnenotateur  "
					+ "and  noter.idpersonnenotee=? and noter.fait=true and noter.idactivite=activite.idactivite order by noter.datenotation desc";

			 preparedStatement = connexion
					.prepareStatement(requete);

			preparedStatement.setInt(1, idpersonnenotee);
			 rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int idactivite = rs.getInt("idactivite");
				int idnoter = rs.getInt("idnoter");
				String titreactivite = rs.getString("titreactivite");
				String avistitre = rs.getString("titre");
				String avislibelle = rs.getString("libelle");
				int idpersonnenotateur = rs.getInt("idpersonnenotateur");
				Date datenotation = rs.getTimestamp("datenotation");
				double note = rs.getDouble("note");
				String nomnotateur = rs.getString("nom");
				String prenomnotateur = rs.getString("prenom");
				String photonotateur = rs.getString("photo");
				avis = new AvisBean(idnoter, idactivite, idpersonnenotee,
						idpersonnenotateur, avistitre, avislibelle,
						datenotation, note, nomnotateur, prenomnotateur,
						photonotateur,titreactivite);
				retour.add(avis);

			}
			
			return retour;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		finally{
			try {
				connexion.close();
				rs.close();
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		return retour;
	}

	public static ArrayList<AvisBean> getListAvisAfter(int id, int lastIndex) {
		// TODO Auto-generated method stub
		return new ArrayList<AvisBean>();
	}

}
