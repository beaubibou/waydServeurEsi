package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.auth.UserRecord.UpdateRequest;

import wayd.ws.WBservices;
import wayde.bean.CxoPool;
import website.metier.AvisBean;
import website.metier.Outils;
import website.metier.ProfilBean;
import website.metier.TableauBordBean;
import website.metier.TypeEtatProfil;
import website.metier.TypeSignalement;
import website.metier.TypeUser;
import website.metier.admin.FitreAdminProfils;

public class PersonneDAO {
	private static final Logger LOG = Logger.getLogger(PersonneDAO.class);

	public static boolean supprimePersonne(int idPersonne) {
		// TODO Auto-generated method stub
		String uid = PersonneDAO.getUID(idPersonne);

		if (uid == null)
			return false;

		supprimePersonneFireBase(uid, idPersonne);

		return true;

	}

	private static boolean supprimePersonneDAO(int idPersonne) {
		// TODO Auto-generated method stub

		Connection connexion = null;

		PreparedStatement preparedStatement = null;
		try {
			connexion = CxoPool.getConnection();
			String requete = "";
			connexion.setAutoCommit(false);
			
			requete = "DELETE FROM interet where  idpersonne=? ;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM amelioration where  idpersonne=? ;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM ami where  idpersonne=?;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM ami where  idami=?;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM demandeami where  idorganisateur=?;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM demandeami where  idparticipant=?;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM message where  idpersonne=?;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM message where  iddestinataire=?;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM messagebyact where  idemetteur=?;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM messagebyact where  iddestinataire=?;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM nbrvu where  idpersonne=?;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM noter where  idpersonnenotee=?;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM noter where  idpersonnenotateur=?;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM notification where  idpersonne=?;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM notification where  iddestinataire=?;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM participer where  idpersonne=?;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM plage where  idpersonne=?;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM prefere where  idpersonne=?;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM refusparticipation where  idpersonne=?;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();

			// efface signaler_activité

			requete = "delete from signaler_activite where idactivite in (select idactivite from activite where idpersonne=?)";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM signaler_profil where  idpersonne=?;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM refusparticipation where  idpersonne=?;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM activite where  idpersonne=?;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM personne where  idpersonne=?;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();

			connexion.commit();

			return true;

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block

			try {
				if (connexion != null)
					connexion.rollback();
				if (preparedStatement != null)
					preparedStatement.close();

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
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

		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

	}

	public static boolean supprimePersonneFireBase(String uid, int idPersonne) {

		try {

			if (FirebaseApp.getApps().isEmpty())
				FirebaseApp.initializeApp(WBservices.optionFireBase);

			FirebaseAuth.getInstance().deleteUserAsync(uid).get();
			supprimePersonneDAO(idPersonne);

			return true;

		} catch (InterruptedException | ExecutionException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public static boolean desactivePersonneFireBase(String uid, int idPersonne,
			boolean actif) {

		try {

			if (FirebaseApp.getApps().isEmpty())
				FirebaseApp.initializeApp(WBservices.optionFireBase);

			UpdateRequest request = new UpdateRequest(uid);

			request.setDisabled(!actif);
			UserRecord userRecord = FirebaseAuth.getInstance()
					.updateUserAsync(request).get();

			return true;

		} catch (InterruptedException | ExecutionException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public static boolean isPseudoExist(String pseudo) {
		// TODO Auto-generated method stub

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();
			String requete = " SELECT idpersonne from personne where prenom=? ";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setString(1, pseudo);
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
				if (connexion != null)
					connexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return false;
	}

	public static boolean isSiretExist(String siret) {
		// TODO Auto-generated method stub

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();
			String requete = " SELECT siret from personne where siret=? ";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setString(1, siret);
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
				if (connexion != null)
					connexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return false;
	}

	public static boolean isTelephoneExist(String telephone) {
		// TODO Auto-generated method stub

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();
			String requete = " SELECT idpersonne from personne where telephone=? ";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setString(1, telephone);
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
				if (connexion != null)
					connexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return false;
	}

	public static boolean isSiretExistPersonne(String siret, int idPersonne) {
		// TODO Auto-generated method stub

		// Renvoi si le numero est déja utilisé par une personne différente de
		// la personne en parametre

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();
			String requete = " SELECT siret from personne where siret=? and idpersonne!=? ";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setString(1, siret);
			preparedStatement.setInt(2, idPersonne);

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
				if (connexion != null)
					connexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return false;
	}

	public static boolean isTelephoneExistPersonne(String telephone,
			int idPersonne) {
		// TODO Auto-generated method stub

		// Renvoi si le numero est déja utilisé par une personne différente de
		// la personne en parametre
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();
			String requete = " SELECT idpersonne from personne where telephone=? and idpersonne!=? ";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setString(1, telephone);
			preparedStatement.setInt(2, idPersonne);

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
				if (connexion != null)
					connexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return false;
	}

	public static boolean isProfilActif(int idpersonne) {
		// TODO Auto-generated method stub

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {

			connexion = CxoPool.getConnection();
			String requete = " SELECT actif from personne where idpersonne=? ";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			rs = preparedStatement.executeQuery();

			if (rs.next())
				if (rs.getBoolean("actif") == true)
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
				if (connexion != null)
					connexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return false;
	}

	public static ArrayList<ProfilBean> getListProfil(FitreAdminProfils filtre,
			int page, int maxResult) {

		int offset = (maxResult) * page;
		int typeUser = filtre.getTypeUser();
		String pseudo = filtre.getPseudo();
		String email = filtre.getEmail();
		int etatProfil = filtre.getEtatProfil();
		int typeSignalement = filtre.getTypeSignalement();
		pseudo = pseudo.replace("*", "%");
		email = email.replace("*", "%");

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ProfilBean profil = null;
		ArrayList<ProfilBean> retour = new ArrayList<ProfilBean>();
		//
		int index = 1;
		try {
			connexion = CxoPool.getConnection();

			String requete = " SELECT personne.note,personne.nbravis,"
					+ "(SELECT COUNT(*) FROM activite where idpersonne=personne.idpersonne ) as nbractivite,"
					+ "(SELECT COUNT(*) FROM participer where idpersonne=personne.idpersonne ) as nbrparticipation,"
					+ "(SELECT COUNT(*) FROM ami where idpersonne=personne.idpersonne ) as nbrami,"
					+ "idpersonne, nom, prenom, login, pwd, ville, actif, verrouille,admin,"
					+ "nbrecheccnx, datecreation,  datenaissance, sexe,affichesexe, afficheage,"
					+ "  mail, cleactivation,commentaire, photo,typeuser,"
					+ "premiereconnexion,latitude,longitude,adresse,siteweb,telephone,"
					+ "latitudefixe,longitudefixe,siret,"
					+ " COALESCE(tablesignalement.nbrsignalement, 0::bigint) AS nbrsignalement, sexe.libelle  as sexeStr"
					+ "  FROM personne "
					+ " left join (select count(*) as nbrsignalement,signaler_profil.idsignalement from signaler_profil group by signaler_profil.idsignalement) tablesignalement "
					+ " ON personne.idpersonne = tablesignalement.idsignalement left join sexe on personne.sexe=sexe.id  where 1=1";

			if (typeUser == TypeUser.ADMIN) {
				requete = requete + " and admin=true ";
			} else {

				if (typeUser == TypeUser.PRO || typeUser == TypeUser.WAYDEUR) {
					requete = requete + " and typeuser=? ";
				}
			}

			if (pseudo != null) {
				if (!pseudo.isEmpty())
					requete = requete + " and prenom like ?";
			}

			if (email != null) {
				if (!email.isEmpty())
					requete = requete + " and mail like ?";
			}

			if (etatProfil != TypeEtatProfil.TOUTES) {
				switch (etatProfil) {
				case TypeEtatProfil.ACTIF:
					requete = requete + " and actif=true";
					break;
				case TypeEtatProfil.INACTIF:
					requete = requete + " and actif=false";

				}
			}

			switch (typeSignalement) {

			case TypeSignalement.AUMOINSUNE:
				requete = requete + " and nbrsignalement>0 ";

				break;

			case TypeSignalement.MOINSDE10:
				requete = requete
						+ " and nbrsignalement<10 or nbrsignalement is null ";

				break;
			case TypeSignalement.PLUSDE10:
				requete = requete + " and nbrsignalement>=10 ";
				break;

			case TypeSignalement.TOUS:

				break;
			}

			requete = requete + " order by datecreation desc limit ?  offset ?";

			// *********************************************************************
			preparedStatement = connexion.prepareStatement(requete);

			if (typeUser != TypeUser.ADMIN)
				if (typeUser == TypeUser.PRO || typeUser == TypeUser.WAYDEUR) {
					preparedStatement.setInt(index, typeUser);
					index++;
				}

			if (pseudo != null) {
				if (!pseudo.isEmpty()) {
					preparedStatement.setString(index, pseudo);
					index++;
				}
			}

			if (email != null) {
				if (!email.isEmpty()) {
					preparedStatement.setString(index, email);
					index++;
				}
			}

			preparedStatement.setInt(index, maxResult);
			index++;
			preparedStatement.setInt(index, offset);

			rs = preparedStatement.executeQuery();

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
				int nbrSignalement = rs.getInt("nbrsignalement");
				String siret = rs.getString("siret");
				String sexeStr = rs.getString("sexeStr");
				String mail = rs.getString("mail");
				profil = new ProfilBean(id, nom, prenom, datecreation,
						datenaissance, nbravis, sexe, nbractivite,
						nbrparticipation, nbrami, note, photo, affichesexe,
						afficheage, commentaire, actif, admin, typeuser,
						premiereconnexion, latitude, longitude, adresse,
						siteWeb, telephone, latitudeFixe, longitudeFixe, siret,
						sexeStr, mail);
				profil.setNbrSignalement(nbrSignalement);

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
					+ ",siteweb,telephone,latitudefixe,longitudefixe,siret,sexe.libelle as sexeStr "
					+ " FROM personne,sexe where idpersonne=? and sexe.id=personne.sexe";

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
				String sexestr = rs.getString("sexeStr");
				String mail = rs.getString("mail");
				profil = new ProfilBean(id, nom, prenom, datecreation,
						datenaissance, nbravis, sexe, nbractivite,
						nbrparticipation, nbrami, note, photo, affichesexe,
						afficheage, commentaire, actif, admin, typeuser,
						premiereconnexion, latitude, longitude, adresse,
						siteWeb, telephone, latitudeFixe, longitudeFixe, siret,
						sexestr, mail);

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

	public static String getUID(int idpersonne) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ProfilBean profil = null;

		try {
			connexion = CxoPool.getConnection();

			Statement stmt = connexion.createStatement();
			String requete = " SELECT login FROM personne where idpersonne=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			rs = preparedStatement.executeQuery();
			stmt.close();

			String uid = null;
			if (rs.next()) {
				uid = rs.getString("login");

			}
			return uid;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return null;
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
					+ "typeuser,photo,premiereconnexion,latitude,longitude,adresse,sexe.libelle as sexeStr "
					+ ",siteweb,telephone,latitudefixe,longitudefixe,siret FROM personne,sexe  where login=? and sexe.id=personne.sexe";

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
				String sexeStr = rs.getString("sexeStr");
				double latitudeFixe = rs.getDouble("latitudefixe");
				double longitudeFixe = rs.getDouble("longitudefixe");
				String siret = rs.getString("siret");
				String mail = rs.getString("mail");

				profil = new ProfilBean(id, nom, prenom, datecreation,
						datenaissance, nbravis, sexe, nbractivite,
						nbrparticipation, nbrami, note, photo, affichesexe,
						afficheage, commentaire, actif, admin, typeuser,
						premiereconnexion, latitude, longitude, adresse,
						siteWeb, telephone, latitudeFixe, longitudeFixe, siret,
						sexeStr, mail);

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

	public static boolean activerProfilEtActivite(int idPersonne, boolean actif) {
		// TODO Auto-generated method stub

		String uid = PersonneDAO.getUID(idPersonne);

		if (uid == null)
			return false;

		if (!desactivePersonneFireBase(uid, idPersonne, actif))
			return false;

		Connection connexion = null;

		// ACTIVE OU DESACTIVE PROFIL ET ACTIVITE

		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "UPDATE activite  SET   actif=? WHERE idpersonne=?";
			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);
			preparedStatement.setBoolean(1, actif);
			preparedStatement.setInt(2, idPersonne);
			preparedStatement.execute();

			requete = "UPDATE  personne set actif=? " + " WHERE idpersonne=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setBoolean(1, actif);
			preparedStatement.setInt(2, idPersonne);
			preparedStatement.execute();

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

	public boolean updateProfilPro(String nom, String adresse, double latitude,
			double longitude, String commentaire, String siret,
			String telephonne, int idpersonne) {
		// TODO Auto-generated method stub

		Connection connexion = null;
		try {

			// on met le sexe � autre
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "UPDATE  personne set prenom=?, adresse=?,latitude=?,longitude=?,commentaire=?,"
					+ "premiereconnexion=false,typeuser=?,latitudefixe=?,longitudefixe=?, siret=?,telephone=?,sexe=2 "
					+ " WHERE idpersonne=?";
			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);
			preparedStatement.setString(1, nom);
			preparedStatement.setString(2, adresse);
			preparedStatement.setDouble(3, latitude);
			preparedStatement.setDouble(4, longitude);
			preparedStatement.setString(5,
					Outils.getStringStatement(commentaire));
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

	public boolean updateProfilProFull(String pseudo, String adresse,
			double latitude, double longitude, String commentaire,
			int idpersonne, String siteWeb, String telephone, String siret) {

		if (pseudo != null)
			pseudo = pseudo.trim();

		if (adresse != null)
			adresse = adresse.trim();

		if (siteWeb != null)
			siteWeb = siteWeb.trim();

		if (siret != null)
			siret = siret.trim();

		if (siteWeb != null)
			siteWeb = siteWeb.trim();

		try {

			if (FirebaseApp.getApps().isEmpty())
				FirebaseApp.initializeApp(WBservices.optionFireBase);

			String uid = PersonneDAO.getUID(idpersonne);
			if (uid == null)
				return false;
			LOG.info("uid à metter à jour" + uid);
			UpdateRequest request = new UpdateRequest(uid)
					.setDisplayName(pseudo);
			LOG.info("Tenttaid" + uid);
			FirebaseAuth.getInstance().updateUserAsync(request).get();

			LOG.info("Fin ten" + uid);
			updateProfilProDAO(pseudo, adresse, latitude, longitude,
					commentaire, idpersonne, siteWeb, telephone, siret);

			return true;

		} catch (InterruptedException | ExecutionException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public boolean updateProfilProDAO(String pseudo, String adresse,
			double latitude, double longitude, String commentaire,
			int idpersonne, String siteWeb, String telephone, String siret) {
		// TODO Auto-generated method stub

		Connection connexion = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "UPDATE  personne set prenom=?, adresse=?,latitude=?,longitude=?,commentaire=?,"
					+ "siteweb=?,telephone=?,latitudefixe=?,longitudefixe=?,siret=? "
					+ " WHERE idpersonne=?";
			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);
			preparedStatement.setString(1, Outils.getStringStatement(pseudo));
			preparedStatement.setString(2, Outils.getStringStatement(adresse));
			preparedStatement.setDouble(3, latitude);
			preparedStatement.setDouble(4, longitude);
			preparedStatement.setString(5,
					Outils.getStringStatement(commentaire));
			preparedStatement.setString(6, Outils.getStringStatement(siteWeb));
			preparedStatement
					.setString(7, Outils.getStringStatement(telephone));
			preparedStatement.setDouble(8, latitude);
			preparedStatement.setDouble(9, longitude);
			preparedStatement.setString(10, Outils.getStringStatement(siret));
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

	public void updateProfilAssociation(String nom, String adresse,
			double latitude, double longitude, int typeuser) {
		// TODO Auto-generated method stub

	}

	public boolean updateProfilWaydeur(String pseudo, int sexe,
			String commentaire, int idpersonne) {

		pseudo = pseudo.trim();
		commentaire = commentaire.trim();
		Connection connexion = null;

		LOG.info("updateProfilProFullWaydeur sexe " + sexe);
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "UPDATE  personne set prenom=?, commentaire=?,sexe=?,typeuser=?,premiereconnexion=false"
					+ " WHERE idpersonne=?";
			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);
			preparedStatement.setString(1, Outils.getStringStatement(pseudo));
			preparedStatement.setString(2,
					Outils.getStringStatement(commentaire));
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

	public static boolean updatePhoto(String photo, int idpersonne) {
		Connection connexion = null;
		LOG.info("updatePhoto");
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
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

	public static TableauBordBean getTableauDeBord(int idpersonne) {
		TableauBordBean tdb = null;
		Connection connexion = null;
		ResultSet rs = null;
		PreparedStatement preparedStatement = null;
		try {
			connexion = CxoPool.getConnection();

			String requete = "SELECT count (idactivite) as  nbrFini FROM activite where idpersonne=? and datefin<?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setTimestamp(2,
					new java.sql.Timestamp(new Date().getTime()));
			rs = preparedStatement.executeQuery();
			int nbrFini = 0;
			while (rs.next()) {
				nbrFini = rs.getInt("nbrFini");
			}
			
			requete = "SELECT count (idactivite) as  nbrEnCours FROM activite where idpersonne=? and datefin>? and datedebut<?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setTimestamp(2,
					new java.sql.Timestamp(new Date().getTime()));
			preparedStatement.setTimestamp(3,
					new java.sql.Timestamp(new Date().getTime()));
			rs = preparedStatement.executeQuery();
					
			
			int nbrEnCours = 0;
			while (rs.next()) {
				nbrEnCours = rs.getInt("nbrEnCours");
			}

		
			
			requete = "SELECT count (idactivite) as  nbrPlanifiee FROM activite where idpersonne=? and  datedebut>?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setTimestamp(2,
					new java.sql.Timestamp(new Date().getTime()));

			rs = preparedStatement.executeQuery();
			int nbrPlanifiee = 0;
			while (rs.next()) {
				nbrPlanifiee = rs.getInt("nbrPlanifiee");
			}
			
			CxoPool.close(connexion, preparedStatement, rs);
		
			return new TableauBordBean(idpersonne, nbrFini, nbrPlanifiee, nbrEnCours);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			
				CxoPool.close(connexion, preparedStatement, rs);
	
		}
		return new TableauBordBean(idpersonne, 0, 0, 0);
	}

	public static ArrayList<AvisBean> getListAvis(int idpersonnenotee) {
		AvisBean avis = null;
		ArrayList<AvisBean> retour = new ArrayList<AvisBean>();
		Connection connexion = null;
		ResultSet rs = null;
		PreparedStatement preparedStatement = null;
		try {
			connexion = CxoPool.getConnection();

			String requete = " SELECT   activite.titre as titreactivite,personne.prenom,      personne.nom,    personne.photo,"
					+ "noter.idactivite,  noter.idpersonnenotateur,noter.idpersonnenotee,noter.idnoter,noter.titre,"
					+ "noter.libelle,noter.note,noter.datenotation"
					+ "  FROM personne,noter,activite "
					+ "  WHERE personne.idpersonne = noter.idpersonnenotateur  "
					+ "and  noter.idpersonnenotee=? and noter.fait=true and noter.idactivite=activite.idactivite order by noter.datenotation desc";

			preparedStatement = connexion.prepareStatement(requete);

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
						photonotateur, titreactivite);
				retour.add(avis);

			}

			return retour;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
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
