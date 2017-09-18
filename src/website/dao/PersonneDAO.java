package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.NamingException;

import wayde.bean.CxoPool;
import wayde.bean.Parametres;
import website.metier.ProfilBean;

public class PersonneDAO {

	public static ArrayList<ProfilBean> getListProfil() {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ProfilBean profil = null;
		ArrayList<ProfilBean> retour = new ArrayList<ProfilBean>();

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
					+ "premiereconnexion,latitude,longitude,adresse,siteweb,telephone,latitudefixe,longitudefixe FROM personne";

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
				double latitude=rs.getDouble("latitude");
				double longitude=rs.getDouble("longitude");
				String adresse = rs.getString("adresse");
				
				String telephone=rs.getString("telephone");	
				String siteWeb=rs.getString("siteweb");
				double latitudeFixe=rs.getDouble("latitudefixe");
				double longitudeFixe=rs.getDouble("longitudefixe");
				profil = new ProfilBean(id, nom, prenom, datecreation,
						datenaissance, nbravis, sexe, nbractivite,
						nbrparticipation, nbrami, note, photo, affichesexe,
						afficheage, commentaire, actif, admin, typeuser,
						premiereconnexion,latitude,longitude,adresse,siteWeb,telephone,latitudeFixe,longitudeFixe);

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
					+ ",siteweb,telephone,latitudefixe,longitudefixe "
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
				double latitude=rs.getDouble("latitude");
				double longitude=rs.getDouble("longitude");
				String adresse = rs.getString("adresse");
				String telephone=rs.getString("telephone");	
				String siteWeb=rs.getString("siteweb");
				double latitudeFixe=rs.getDouble("latitudefixe");
				double longitudeFixe=rs.getDouble("longitudefixe");
				profil = new ProfilBean(id, nom, prenom, datecreation,
						datenaissance, nbravis, sexe, nbractivite,
						nbrparticipation, nbrami, note, photo, affichesexe,
						afficheage, commentaire, actif, admin, typeuser,
						premiereconnexion,latitude,longitude,adresse,siteWeb,telephone,latitudeFixe,longitudeFixe);

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
					+ ",siteweb,telephone,latitudefixe,longitudefixe FROM personne where login=?";

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
				double latitude=rs.getDouble("latitude");
				double longitude=rs.getDouble("longitude");
				String adresse = rs.getString("adresse");
				String telephone=rs.getString("telephone");	
				String siteWeb=rs.getString("siteweb");
				double latitudeFixe=rs.getDouble("latitudefixe");
				double longitudeFixe=rs.getDouble("longitudefixe");
				profil = new ProfilBean(id, nom, prenom, datecreation,
						datenaissance, nbravis, sexe, nbractivite,
						nbrparticipation, nbrami, note, photo, affichesexe,
						afficheage, commentaire, actif, admin, typeuser,
						premiereconnexion,latitude,longitude,adresse,siteWeb,telephone,latitudeFixe,longitudeFixe);

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
			double longitude, int typeuser, String commentaire,int idpersonne) {
		// TODO Auto-generated method stub

		Connection connexion = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			System.out.println("uopdate user");
			String requete = "UPDATE  personne set prenom=?, adresse=?,latitude=?,longitude=?,commentaire=?,premiereconnexion=false,typeuser=?,latitudefixe=?,longitudefixe=? "
					+ " WHERE idpersonne=?";
			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);
			preparedStatement.setString(1, nom);
			preparedStatement.setString(2, adresse);
			preparedStatement.setDouble(3, latitude);
			preparedStatement.setDouble(4, longitude);
			preparedStatement.setString(5, commentaire);
			preparedStatement.setInt(6, typeuser);
			preparedStatement.setDouble(7, latitude);
			preparedStatement.setDouble(8, longitude);
			preparedStatement.setInt(9, idpersonne);
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
	
	
	public boolean updateProfilProFull(String nom, String adresse, double latitude,
			double longitude,  String commentaire,int idpersonne,String siteWeb,String telephone) {
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

	public void updateProfilWaydeur(String nom, String adresse,
			double latitude, double longitude, int typeuser) {
		// TODO Auto-generated method stub

	}

}
