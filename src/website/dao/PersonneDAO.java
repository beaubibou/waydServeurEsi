package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.naming.NamingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import wayd.ws.WBservices;
import wayde.bean.CxoPool;
import website.metier.AvisBean;
import website.metier.Outils;
import website.metier.ProfilBean;
import website.metier.TableauBordBean;
import website.metier.TypeEtatProfil;
import website.metier.TypeEtatValide;
import website.metier.TypeSignalement;
import website.metier.TypeUser;
import website.metier.UserAjax;
import website.metier.admin.FitreAdminProfils;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord.UpdateRequest;

public class PersonneDAO {
	private static final Logger LOG = Logger.getLogger(PersonneDAO.class);

	public static boolean supprimePersonne(int idPersonne) {
		
		long debut = System.currentTimeMillis();
		String uid = PersonneDAO.getUID(idPersonne);

		if (uid == null)
			return false;

		supprimePersonneFireBase(uid, idPersonne);

		LogDAO.LOG_DUREE("supprimePersonne", debut);
		return true;

	}
	
	public static boolean supprimePersonneBase(int idPersonne) {
	
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			
			
			String requete = " delete from activite where idpersonne=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();
			
			requete = " delete from personne where idpersonne=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();
			
			
			
			connexion.commit();

		} catch (NamingException | SQLException e) {
				LOG.error( ExceptionUtils.getStackTrace(e));
		}

		finally {

			CxoPool.close(connexion, preparedStatement, rs);

		}
		return false;



	}
	
	
	
	
public static int isLoginExist(String login)  {
		
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;

	try {
		connexion = CxoPool.getConnection();
		String requete = " SELECT idpersonne from personne where login=? ";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setString(1, login);
		rs = preparedStatement.executeQuery();
	
		if (rs.next())
			return rs.getInt("idpersonne");

	} catch (NamingException | SQLException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
	}

	finally {

		CxoPool.close(connexion, preparedStatement, rs);

	}
	return 0;

	
	}

public static int isIdActiviteOPENExist(String idactivite)  {
	
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;

	try {
		
		connexion = CxoPool.getConnection();
		String requete = " SELECT idactivite from activite where idactiviteopen=? ";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setString(1, idactivite);
		rs = preparedStatement.executeQuery();
	
		if (rs.next())
			return rs.getInt("idactivite");

	} catch (NamingException | SQLException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
	}

	finally {

		CxoPool.close(connexion, preparedStatement, rs);

	}
	return 0;

	
	}

public static int isIdActiviteFBExist(String idactivite)  {
	
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;

	try {
		
		connexion = CxoPool.getConnection();
		String requete = " SELECT idactivite from activite where idactivitefacebook=? ";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setString(1, idactivite);
		rs = preparedStatement.executeQuery();
	
		if (rs.next())
			return rs.getInt("idactivite");

	} catch (NamingException | SQLException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
	}

	finally {

		CxoPool.close(connexion, preparedStatement, rs);

	}
	return 0;

	
	}

	public ArrayList<UserAjax> getListUserAjaxMap(double malatitude,
			double malongitude, double NELat, double NELon, double SWLat,
			double SWlon) {

		long debut = System.currentTimeMillis();

		ArrayList<UserAjax> retour = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Connection connexion=null;

		try {
			connexion = CxoPool.getConnection();
			double latMin = SWLat;
			double latMax = NELat;
			double longMin = SWlon;
			double longMax = NELon;
			UserAjax userAjax = null;

			String requete = " SELECT idpersonne,prenom,latitude,longitude,photo from personne where latitude between ? and ?"
					+ " and longitude between ? and ? FETCH FIRST 40 ROWS ONLY;";

			preparedStatement = connexion.prepareStatement(requete);
		
			preparedStatement.setDouble(1, latMin);
			preparedStatement.setDouble(2, latMax);
			preparedStatement.setDouble(3, longMin);
			preparedStatement.setDouble(4, longMax);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				String pseudo = rs.getString("prenom");
				String photo = rs.getString("photo");
				int idpersonne = rs.getInt("idpersonne");
				
				userAjax = new UserAjax(idpersonne,pseudo,latitude,longitude,photo);
				retour.add(userAjax);

			}

		} catch (NamingException | SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}

		LogDAO.LOG_DUREE("getListActiviteAjaxMap", debut);
		return retour;
	}


	private static boolean supprimePersonneDAO(int idPersonne) {
		long debut = System.currentTimeMillis();
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
			
			requete = "DELETE FROM participer where  participer.idactivite in (select activite.idactivite from activite where activite.idpersonne=?) ";
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
			
			LogDAO.LOG_DUREE("supprimePersonneDAO", debut);
		
			return true;

		} catch (NamingException | SQLException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
		} finally {

			CxoPool.close(connexion, preparedStatement);
		}
		return false;


	}

	public static boolean supprimePersonneFireBase(String uid, int idPersonne) {

		long debut = System.currentTimeMillis();
		try {

			if (FirebaseApp.getApps().isEmpty())
				FirebaseApp.initializeApp(WBservices.OPTION_FIRE_BASE);

			FirebaseAuth.getInstance().deleteUserAsync(uid).get();
			supprimePersonneDAO(idPersonne);
		
			LogDAO.LOG_DUREE("supprimePersonneFireBase", debut);
			
			return true;

		} catch (InterruptedException | ExecutionException e) {

			LOG.error( ExceptionUtils.getStackTrace(e));
			return false;
		}
	}

	public static boolean desactivePersonneFireBase(String uid, int idPersonne,
			boolean actif) {

		long debut = System.currentTimeMillis();
		try {

			if (FirebaseApp.getApps().isEmpty())
				FirebaseApp.initializeApp(WBservices.OPTION_FIRE_BASE);

			UpdateRequest request = new UpdateRequest(uid);

			request.setDisabled(!actif);
			 FirebaseAuth.getInstance().updateUserAsync(request).get();

			 LogDAO.LOG_DUREE("desactivePersonneFireBase", debut);
				
			return true;

		} catch (InterruptedException | ExecutionException e) {

			LOG.error( ExceptionUtils.getStackTrace(e));
			return false;
		}
	}

	public static boolean isPseudoExist(String pseudo) {
	
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();
			String requete = " SELECT idpersonne from personne where prenom=? ";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setString(1, pseudo);
			rs = preparedStatement.executeQuery();

			 LogDAO.LOG_DUREE("isPseudoExist", debut);
				
			
			if (rs.next())
				return true;

		} catch (NamingException | SQLException e) {
				LOG.error( ExceptionUtils.getStackTrace(e));
		}

		finally {

			CxoPool.close(connexion, preparedStatement, rs);

		}
		return false;
	}

	public static boolean isSiretExist(String siret) {
		

		long debut = System.currentTimeMillis();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();
			String requete = " SELECT siret from personne where siret=? ";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setString(1, siret);
			rs = preparedStatement.executeQuery();
			LogDAO.LOG_DUREE("isSiretExist", debut);
				
			if (rs.next())
				return true;

		} catch (NamingException | SQLException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
		}

		finally {

			CxoPool.close(connexion, preparedStatement, rs);

		}
		return false;
	}

	public static boolean isTelephoneExist(String telephone) {
		

		long debut = System.currentTimeMillis();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();
			String requete = " SELECT idpersonne from personne where telephone=? ";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setString(1, telephone);
			rs = preparedStatement.executeQuery();
			LogDAO.LOG_DUREE("isTelephoneExist", debut);
			
			if (rs.next())
				return true;

		} catch (NamingException | SQLException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
		}

		finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
		return false;
	}

	public static boolean isSiretExistPersonne(String siret, int idPersonne) {
		

		// Renvoi si le numero est déja utilisé par une personne différente de
		// la personne en parametre

		long debut = System.currentTimeMillis();
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
		
			LogDAO.LOG_DUREE("isSiretExistPersonne", debut);
			
			if (rs.next())
				return true;

		} catch (NamingException | SQLException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
		}

		finally {

			CxoPool.close(connexion, preparedStatement, rs);

		}
		return false;
	}

	public static boolean isTelephoneExistPersonne(String telephone,
			int idPersonne) {
		

		// Renvoi si le numero est déja utilisé par une personne différente de
		// la personne en parametre
		long debut = System.currentTimeMillis();
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
			LogDAO.LOG_DUREE("isTelephoneExistPersonne", debut);
			
			if (rs.next())
				return true;

		} catch (NamingException | SQLException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
		}

		finally {

			CxoPool.close(connexion, preparedStatement, rs);

		}
		return false;
	}

	public static boolean isProfilActif(int idpersonne) {
		

		long debut = System.currentTimeMillis();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {

			connexion = CxoPool.getConnection();
			String requete = " SELECT actif from personne where idpersonne=? ";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			rs = preparedStatement.executeQuery();
			LogDAO.LOG_DUREE("isProfilActif", debut);
			
			if (rs.next())
				if (rs.getBoolean("actif") )
					return true;

		} catch (NamingException | SQLException e) {
				LOG.error( ExceptionUtils.getStackTrace(e));
		}

		finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
		return false;
	}

	public static ArrayList<ProfilBean> getListProfil(FitreAdminProfils filtre,
			int page, int maxResult) {

		long debut = System.currentTimeMillis();
		int offset = (maxResult) * page;
		int typeUser = filtre.getTypeUser();
		String pseudo = filtre.getPseudo();
		String email = filtre.getEmail();
		int etatProfil = filtre.getEtatProfil();
		int typeSignalement = filtre.getTypeSignalement();
		int etatValide=filtre.getEtatValide();
	
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ProfilBean profil = null;
		ArrayList<ProfilBean> retour = new ArrayList<>();
		//
		int index = 1;
		try {
			connexion = CxoPool.getConnection();

			String requete = " SELECT personne.note,personne.nbravis,"
					+ "(SELECT COUNT(*) FROM activite where idpersonne=personne.idpersonne ) as nbractivite,"
					+ "(SELECT COUNT(*) FROM participer where idpersonne=personne.idpersonne ) as nbrparticipation,"
					+ "(SELECT COUNT(*) FROM ami where idpersonne=personne.idpersonne ) as nbrami,"
					+ "idpersonne, nom, prenom, login, pwd, ville, actif, verrouille,admin,valide,"
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
					requete = requete + " and LOWER(prenom) like ?";
				
			}

			if (email != null) {
				if (!email.isEmpty())
					requete = requete + " and LOWER(mail) like ?";
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
			
			if (etatValide!=TypeEtatValide.TOUS){
				switch (etatValide) {
				case TypeEtatValide.EN_ATTENTE:
					requete = requete + " and valide=false";
					break;
				case TypeEtatValide.VALIDE:
					requete = requete + " and valide=true";

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
					preparedStatement.setString(index, pseudo.toLowerCase());
					index++;
				}
			}

			if (email != null) {
				if (!email.isEmpty()) {
					preparedStatement.setString(index, email.toLowerCase());
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
				boolean valide = rs.getBoolean("valide");
				profil = new ProfilBean(id, nom, prenom, datecreation,
						datenaissance, nbravis, sexe, nbractivite,
						nbrparticipation, nbrami, note, photo, affichesexe,
						afficheage, commentaire, actif, admin, typeuser,
						premiereconnexion, latitude, longitude, adresse,
						siteWeb, telephone, latitudeFixe, longitudeFixe, siret,
						sexeStr, mail,valide);
				profil.setNbrSignalement(nbrSignalement);

				retour.add(profil);

			}

			LogDAO.LOG_DUREE("getListProfil", debut);
			
			return retour;

		} catch (SQLException | NamingException e) {
				LOG.error( ExceptionUtils.getStackTrace(e));
			return retour;
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}

	}

	public static ProfilBean getFullProfil(int idpersonne) {

		long debut = System.currentTimeMillis();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ProfilBean profil = null;

		try {
			connexion = CxoPool.getConnection();

		
			String requete = " SELECT personne.note,personne.nbravis,"
					+ "(SELECT COUNT(*) FROM activite where idpersonne=personne.idpersonne ) as nbractivite,"
					+ "(SELECT COUNT(*) FROM participer where idpersonne=personne.idpersonne ) as nbrparticipation,"
					+ "(SELECT COUNT(*) FROM ami where idpersonne=personne.idpersonne ) as nbrami,"
					+ "idpersonne, nom, prenom, login, pwd, ville, actif, verrouille,admin,valide,"
					+ "nbrecheccnx, datecreation,  datenaissance, sexe,affichesexe, afficheage,"
					+ "  mail, cleactivation,commentaire, photo,typeuser,premiereconnexion,latitude,longitude,adresse"
					+ ",siteweb,telephone,latitudefixe,longitudefixe,siret,sexe.libelle as sexeStr "
					+ " FROM personne,sexe where idpersonne=? and sexe.id=personne.sexe";

			preparedStatement = connexion.prepareStatement(requete);

			preparedStatement.setInt(1, idpersonne);
			rs = preparedStatement.executeQuery();
		
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
				boolean valide = rs.getBoolean("valide");
				
				
				profil = new ProfilBean(id, nom, prenom, datecreation,
						datenaissance, nbravis, sexe, nbractivite,
						nbrparticipation, nbrami, note, photo, affichesexe,
						afficheage, commentaire, actif, admin, typeuser,
						premiereconnexion, latitude, longitude, adresse,
						siteWeb, telephone, latitudeFixe, longitudeFixe, siret,
						sexestr, mail,valide);

			}
			
			LogDAO.LOG_DUREE("getFullProfil", debut);
			
			CxoPool.close(preparedStatement, rs);
			
			return profil;

		} catch (SQLException | NamingException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
			return profil;
		} finally {
			CxoPool.close(connexion, preparedStatement, rs);
		}

	}

	public static String getUID(int idpersonne) {

		long debut = System.currentTimeMillis();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
	
		try {
			connexion = CxoPool.getConnection();

		
			String requete = " SELECT login FROM personne where idpersonne=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			rs = preparedStatement.executeQuery();
		
			String uid = null;
			if (rs.next()) {
				uid = rs.getString("login");

			}
			LogDAO.LOG_DUREE("getUID", debut);
			CxoPool.close(preparedStatement, rs);
			return uid;

		} catch (SQLException | NamingException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
			return null;
		} finally {
			CxoPool.close(connexion, preparedStatement, rs);
		}

	}

	public static ProfilBean getFullProfilByUid(String uid) {

		long debut = System.currentTimeMillis();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ProfilBean profil = null;

		try {
			connexion = CxoPool.getConnection();

		
			String requete = " SELECT personne.note,personne.nbravis,"
					+ "(SELECT COUNT(*) FROM activite where idpersonne=personne.idpersonne ) as nbractivite,"
					+ "(SELECT COUNT(*) FROM participer where idpersonne=personne.idpersonne ) as nbrparticipation,"
					+ "(SELECT COUNT(*) FROM ami where idpersonne=personne.idpersonne ) as nbrami,"
					+ "idpersonne, nom, prenom, login, pwd, ville, actif, verrouille,admin,valide,"
					+ "nbrecheccnx, datecreation,  datenaissance, sexe,affichesexe, afficheage,"
					+ "  mail, cleactivation,commentaire,"
					+ "typeuser,photo,premiereconnexion,latitude,longitude,adresse,sexe.libelle as sexeStr "
					+ ",siteweb,telephone,latitudefixe,longitudefixe,siret FROM personne,sexe  where login=? and sexe.id=personne.sexe";

			preparedStatement = connexion.prepareStatement(requete);

			preparedStatement.setString(1, uid);
			rs = preparedStatement.executeQuery();
		

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
				boolean valide = rs.getBoolean("valide");
				
				profil = new ProfilBean(id, nom, prenom, datecreation,
						datenaissance, nbravis, sexe, nbractivite,
						nbrparticipation, nbrami, note, photo, affichesexe,
						afficheage, commentaire, actif, admin, typeuser,
						premiereconnexion, latitude, longitude, adresse,
						siteWeb, telephone, latitudeFixe, longitudeFixe, siret,
						sexeStr, mail,valide);

			}

			LogDAO.LOG_DUREE("getFullProfilByUid", debut);
		
			return profil;

		} catch (SQLException | NamingException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
			return null;
		} finally {
			CxoPool.close(connexion, preparedStatement, rs);
		}

	}

	public static boolean activerProfilEtActivite(int idPersonne, boolean actif) {
	
		
		long debut = System.currentTimeMillis();
		String uid = PersonneDAO.getUID(idPersonne);
		PreparedStatement preparedStatement=null;

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
			preparedStatement = connexion
					.prepareStatement(requete);
			preparedStatement.setBoolean(1, actif);
			preparedStatement.setInt(2, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();
			
			requete = "UPDATE  personne set actif=? " + " WHERE idpersonne=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setBoolean(1, actif);
			preparedStatement.setInt(2, idPersonne);
			preparedStatement.execute();
			
			
			connexion.commit();
			LogDAO.LOG_DUREE("activerProfilEtActivite", debut);
			

		} catch (NamingException | SQLException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return false;

		} finally {

			CxoPool.close(connexion, preparedStatement);
		}
		return true;

	}
	
	public static boolean valideCompte(int idPersonne, boolean actif) {
	
		
		long debut = System.currentTimeMillis();
		PreparedStatement preparedStatement=null;
		Connection connexion = null;

		// ACTIVE OU DESACTIVE PROFIL ET ACTIVITE

		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "UPDATE personne SET   valide=? WHERE idpersonne=?";
			preparedStatement = connexion
					.prepareStatement(requete);
			preparedStatement.setBoolean(1, actif);
			preparedStatement.setInt(2, idPersonne);
			preparedStatement.execute();
			preparedStatement.close();
				
			connexion.commit();
			
			LogDAO.LOG_DUREE("valideCompte", debut);

		} catch (NamingException | SQLException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return false;

		} finally {

			CxoPool.close(connexion, preparedStatement);
		}
		return true;

	}

	public boolean updateProfilPro(String nom, String adresse, double latitude,
			double longitude, String commentaire, String siret,
			String telephonne, int idpersonne) {
	
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		PreparedStatement preparedStatement=null;
		try {

			// on met le sexe � autre
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "UPDATE  personne set prenom=?, adresse=?,latitude=?,longitude=?,commentaire=?,"
					+ "premiereconnexion=false,typeuser=?,latitudefixe=?,longitudefixe=?, siret=?,telephone=?,sexe=2 "
					+ " WHERE idpersonne=?";
		  preparedStatement = connexion
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
			LogDAO.LOG_DUREE("updateProfilPro", debut);
			
			return true;

		} catch (NamingException | SQLException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);

		} finally {


			CxoPool.close(connexion, preparedStatement);
		}
		return false;

	}

	public static boolean prepareTestFireBaseUser() {
	
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		PreparedStatement preparedStatement=null;
		try {

			// on met le sexe � autre
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "UPDATE  personne set jeton=null "
					+ " WHERE mail like 'test.firebase%'";
		  preparedStatement = connexion
					.prepareStatement(requete);
		
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();
			LogDAO.LOG_DUREE("updateProfilPro", debut);
			
			return true;

		} catch (NamingException | SQLException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);

		} finally {


			CxoPool.close(connexion, preparedStatement);
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
				FirebaseApp.initializeApp(WBservices.OPTION_FIRE_BASE);

			String uid = PersonneDAO.getUID(idpersonne);
			if (uid == null)
				return false;
			UpdateRequest request = new UpdateRequest(uid)
					.setDisplayName(pseudo);
		
			FirebaseAuth.getInstance().updateUserAsync(request).get();

		
			updateProfilProDAO(pseudo, adresse, latitude, longitude,
					commentaire, idpersonne, siteWeb, telephone, siret);

			
			
			return true;

		} catch (InterruptedException | ExecutionException e) {

		
			LOG.error( ExceptionUtils.getStackTrace(e));
			return false;
		}

	}

	public boolean updateProfilProDAO(String pseudo, String adresse,
			double latitude, double longitude, String commentaire,
			int idpersonne, String siteWeb, String telephone, String siret) {
	
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		PreparedStatement preparedStatement =null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "UPDATE  personne set prenom=?, adresse=?,latitude=?,longitude=?,commentaire=?,"
					+ "siteweb=?,telephone=?,latitudefixe=?,longitudefixe=?,siret=? "
					+ " WHERE idpersonne=?";
			preparedStatement = connexion
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
			LogDAO.LOG_DUREE("updateProfilProDAO", debut);
		
			connexion.commit();
			
			return true;
		} catch (NamingException | SQLException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);

		} finally {

			CxoPool.close(connexion, preparedStatement);
		}
		return false;

	}

	
	public boolean updateProfilWaydeur(String pseudo, int sexe,
			String commentaire, int idpersonne) {

		long debut = System.currentTimeMillis();
		pseudo = pseudo.trim();
		commentaire = commentaire.trim();
		Connection connexion = null;
		PreparedStatement preparedStatement=null;

		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "UPDATE  personne set prenom=?, commentaire=?,sexe=?,typeuser=?,premiereconnexion=false"
					+ " WHERE idpersonne=?";
			preparedStatement = connexion
					.prepareStatement(requete);
			preparedStatement.setString(1, Outils.getStringStatement(pseudo));
			preparedStatement.setString(2,
					Outils.getStringStatement(commentaire));
			preparedStatement.setInt(3, sexe);
			preparedStatement.setInt(4, ProfilBean.WAYDEUR);
			preparedStatement.setInt(5, idpersonne);
			preparedStatement.execute();
			preparedStatement.close();
		
			LogDAO.LOG_DUREE("updateProfilWaydeur", debut);
			
			connexion.commit();

		} catch (NamingException | SQLException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return false;

		} finally {

			CxoPool.close(connexion, preparedStatement);
		}
		return true;

	}

	public static boolean updatePhoto(String photo, int idpersonne) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		PreparedStatement preparedStatement=null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "UPDATE  personne set photo=?"
					+ " WHERE idpersonne=?";
		    preparedStatement = connexion
					.prepareStatement(requete);
			preparedStatement.setString(1, photo);
			preparedStatement.setInt(2, idpersonne);
			preparedStatement.execute();
			preparedStatement.close();
			LogDAO.LOG_DUREE("updatePhoto", debut);
			
			connexion.commit();

		} catch (NamingException | SQLException e) {
				LOG.error( ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return false;

		} finally {

			CxoPool.close(connexion, preparedStatement);
		}
		return true;

	}

	public boolean updateProfilProFullWaydeur(String nom, String adresse,
			String commentaire, int sexe, boolean afficheSexe,
			boolean afficheAge, Date datenaissance, int idpersonne) {
	
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		PreparedStatement preparedStatement=null;
	
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "UPDATE  personne set prenom=?, adresse=?,commentaire=?,datenaissance=? ,affichesexe=?,afficheage=?,sexe=?"
					+ " WHERE idpersonne=?";
			preparedStatement = connexion
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
			LogDAO.LOG_DUREE("updateProfilProFullWaydeur", debut);
			
			connexion.commit();

		} catch (NamingException | SQLException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);

		} finally {

			CxoPool.close(connexion, preparedStatement);
		}
		return true;
	}

	public static TableauBordBean getTableauDeBord(int idpersonne) {
	
		long debut = System.currentTimeMillis();
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
			CxoPool.close(preparedStatement, rs);	
			
			
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

			CxoPool.close(preparedStatement, rs);	
			
		
			
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
			
		
			LogDAO.LOG_DUREE("getTableauDeBord", debut);
			
			return new TableauBordBean(idpersonne, nbrFini, nbrPlanifiee, nbrEnCours);

		} catch (SQLException | NamingException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
			
		} finally {
			
				CxoPool.close(connexion, preparedStatement, rs);
	
		}
		return new TableauBordBean(idpersonne, 0, 0, 0);
	}

	public static ArrayList<AvisBean> getListAvis(int idpersonnenotee) {
		
		long debut = System.currentTimeMillis();
		
		AvisBean avis = null;
		ArrayList<AvisBean> retour = new ArrayList<>();
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
			LogDAO.LOG_DUREE("getListAvis", debut);
			return retour;

		} catch (SQLException | NamingException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));

		} finally {
			CxoPool.close(connexion, preparedStatement);
		}
		return retour;
	}

	

}
