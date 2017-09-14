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
import website.metier.ProfilBean;

public class PersonneDAO {

	public static ArrayList<ProfilBean> getListProfil( )  {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ProfilBean profil = null;
		ArrayList<ProfilBean> retour=new ArrayList<ProfilBean>();

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
					+ "  mail, cleactivation,commentaire, photo,typeuser,premiereconnexion FROM personne";

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
				boolean actif=rs.getBoolean("actif");
				String photo = rs.getString("photo");
				int sexe = rs.getInt("sexe");
				double note = rs.getDouble("note");
				boolean admin=rs.getBoolean("admin");
				int typeuser=rs.getInt("typeuser");
				boolean premiereconnexion=rs.getBoolean("premiereconnexion");
				// System.out.println("Note" + note);

				profil = new ProfilBean(id, nom, prenom, datecreation,
						datenaissance, nbravis, sexe, nbractivite,
						nbrparticipation, nbrami, note, photo, affichesexe,
						afficheage, commentaire,actif,admin,typeuser,premiereconnexion);

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
	
	
	public static ProfilBean getFullProfil(int idpersonne)  {

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
					+ "  mail, cleactivation,commentaire, photo,typeuser,premiereconnexion FROM personne where idpersonne=?";

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
				boolean actif=rs.getBoolean("actif");
				String photo = rs.getString("photo");
				int sexe = rs.getInt("sexe");
				double note = rs.getDouble("note");
				boolean admin=rs.getBoolean("admin");
				int typeuser=rs.getInt("typeuser");
				// System.out.println("Note" + note);
				boolean premiereconnexion=rs.getBoolean("premiereconnexion");
				profil = new ProfilBean(id, nom, prenom, datecreation,
						datenaissance, nbravis, sexe, nbractivite,
						nbrparticipation, nbrami, note, photo, affichesexe,
						afficheage, commentaire,actif,admin,typeuser,premiereconnexion);

			
			
			
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
	
	public static ProfilBean getFullProfilByUid(String uid )  {

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
					+ "  mail, cleactivation,commentaire,typeuser,photo,premiereconnexion FROM personne where login=?";

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
				boolean actif=rs.getBoolean("actif");
				boolean admin=rs.getBoolean("admin");
				String photo = rs.getString("photo");
				int sexe = rs.getInt("sexe");
				int typeuser=rs.getInt("typeuser");
				double note = rs.getDouble("note");
				boolean premiereconnexion=rs.getBoolean("premiereconnexion");
				profil = new ProfilBean(id, nom, prenom, datecreation,
						datenaissance, nbravis, sexe, nbractivite,
						nbrparticipation, nbrami, note, photo, affichesexe,
						afficheage, commentaire,actif,admin,typeuser,premiereconnexion);
	
			
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
	
	public static MessageBean activerProfil(int idpersonne,boolean actif)  {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
	
		try {
			connexion = CxoPool.getConnection();

			String requete = "UPDATE  personne set actif=? "
					+ " WHERE idpersonne=?";
			preparedStatement = connexion
					.prepareStatement(requete);
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
	

}
