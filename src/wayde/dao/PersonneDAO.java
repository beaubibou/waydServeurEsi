package wayde.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import wayde.bean.Activite;
import wayde.bean.Droit;
import wayde.bean.Parametres;
import wayde.bean.Personne;
import wayde.bean.Profil;
import wayde.bean.ProfilNotation;
import wayde.bean.ProprietePref;
import wayde.bean.Tutoriel;
import wayde.bean.ProfilDiscussion;

public class PersonneDAO {
	Connection connexion;

	public PersonneDAO() {

	}

	public PersonneDAO(Connection connexion) {
		this.connexion = connexion;
	}

	public Personne getPersonneId(int idpersonne) throws SQLException {
		Statement stmt = connexion.createStatement();
		// System.out.println("Cherche compte personen par Id" + idpersonne);
		String requete = " SELECT personne.note,"
				+ "personne.nbravis as totalavis,"
				+ "idpersonne, nom, prenom, login, pwd, ville, actif, verrouille,commentaire,"
				+ "nbrecheccnx, datecreation,  datenaissance, sexe,admin,rayon,latitude,longitude,"
				+ "  mail, cleactivation, photo,affichesexe,afficheage,premiereconnexion,nbravis,note FROM personne where idpersonne=?";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setInt(1, idpersonne);
		ResultSet rs = preparedStatement.executeQuery();
		stmt.close();

		return getPersonneDbByRs(rs);

	}

	public ProfilNotation getProfilNotation(int notateur, int idpersonne,
			int idactivite) throws SQLException {

		boolean isAmi = new AmiDAO(connexion).isAmiFrom(notateur, idpersonne);
		Profil profil = getFullProfil(idpersonne);
		Activite activite = new ActiviteDAO(connexion).getActivite(idactivite);

		return new ProfilNotation(profil, isAmi, activite.getTitre());

	}

	public Profil getFullProfil(int idpersonne) throws SQLException {

		Statement stmt = connexion.createStatement();
		// System.out.println("Cherche compte personen par Id" + idpersonne);
		String requete = " SELECT personne.note,personne.nbravis,"
				+ "(SELECT COUNT(*) FROM activite where idpersonne=personne.idpersonne ) as nbractivite,"
				+ "(SELECT COUNT(*) FROM participer where idpersonne=personne.idpersonne ) as nbrparticipation,"
				+ "(SELECT COUNT(*) FROM ami where idpersonne=personne.idpersonne ) as nbrami,"
				+ "idpersonne, nom, prenom, login, pwd, ville, actif, verrouille,"
				+ "nbrecheccnx, datecreation,  datenaissance, sexe,affichesexe, afficheage,"
				+ "  mail, cleactivation,commentaire, photo FROM personne where idpersonne=?";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setInt(1, idpersonne);
		ResultSet rs = preparedStatement.executeQuery();
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
			String ville = rs.getString("ville");
			Date datecreation = rs.getTimestamp("datecreation");
			Date datenaissance = rs.getTimestamp("datenaissance");
			boolean afficheage = rs.getBoolean("afficheage");
			boolean affichesexe = rs.getBoolean("affichesexe");

			String photo = rs.getString("photo");

			if (photo == null)
				photo = "";
			int sexe = rs.getInt("sexe");
			double note = rs.getDouble("note");

			// System.out.println("Note" + note);

			Profil profil = new Profil(id, nom, prenom, ville, datecreation,
					datenaissance, nbravis, sexe, nbractivite,
					nbrparticipation, nbrami, note, photo, affichesexe,
					afficheage, commentaire);
			rs.close();
			return profil;
		}
		return null;
	}

	public Personne getUnProfil(int idpersonne) throws SQLException {
		Statement stmt = connexion.createStatement();
		// System.out.println("Cherche compte personen par Id" + idpersonne);
		String requete = " SELECT  note, nbravis as totalavis,"
				+ "idpersonne, nom, prenom, login, pwd, ville, actif, verrouille,"
				+ "nbrecheccnx, datecreation,  datenaissance,latitude,longitude, sexe,affichesexe,afficheage"
				+ " , mail, cleactivation, photo,commentaire,premiereconnexion,nbravis,note FROM personne where idpersonne=?";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		ResultSet rs = preparedStatement.executeQuery();
		stmt.close();
		return getUnProfilDbByRs(rs);
	}

	public Droit getDroit(int idpersonne, String jeton) throws SQLException {

		Statement stmt = connexion.createStatement();
		String requete = " SELECT  idpersonne,verrouille,admin,actif from personne where idpersonne=? and jeton=?";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setString(2, jeton);
		ResultSet rs = preparedStatement.executeQuery();
		stmt.close();
		return getDroitDbByRs(rs);
	}

	public String getGCMId(int idpersonne) throws Exception {
		Statement stmt = connexion.createStatement();
		// System.out.println("Cherche GCMID" + idpersonne);
		String requete = " SELECT gcm from personne where idpersonne=?";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		ResultSet rs = preparedStatement.executeQuery();
		stmt.close();
		if (rs.next()) {
			return rs.getString("gcm");
		}
		throw new Exception("Pas de GMC pour " + idpersonne);
	}

	public String test_getToken(int idpersonne) throws Exception {
		Statement stmt = connexion.createStatement();
		System.out.println("Cherche token" + idpersonne);
		String requete = " SELECT jeton from personne where idpersonne=?";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		ResultSet rs = preparedStatement.executeQuery();
		stmt.close();
		if (rs.next()) {
			return rs.getString("jeton");
		}
		throw new Exception("Pas de jeton pour " + idpersonne);
	}

	public ProprietePref getProprietePref(int idpersonne) throws Exception {
		Statement stmt = connexion.createStatement();
		String requete = " SELECT latitude,longitude,rayon,adressepref from personne where idpersonne=?";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		ResultSet rs = preparedStatement.executeQuery();
		stmt.close();

		if (rs.next()) {
			double longitude = rs.getDouble("longitude");
			double latitude = rs.getDouble("latitude");
			int rayon = rs.getInt("rayon");
			String adresse = rs.getString("adressepref");
			if (adresse == null)
				adresse = "Pas d'adresse";
			return new ProprietePref(rayon, latitude, longitude, adresse);
		}
		throw new Exception("Pas de préférence pour  " + idpersonne);

	}

	public Personne getPersonneJeton(String idtoken) throws SQLException {

		Statement stmt = connexion.createStatement();
		// System.out.println("Cherche compte personen par token:");

		String requete = " SELECT personne.note,personne.nbravis as totalavis,"
				+ "idpersonne, nom, prenom, login, pwd, ville, actif, verrouille,commentaire,"
				+ "nbrecheccnx, datecreation, datenaissance, sexe,longitude,latitude,"
				+ "  mail, cleactivation, photo,affichesexe,afficheage,premiereconnexion,rayon,admin FROM personne where jeton=?";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setString(1, idtoken);
		ResultSet rs = preparedStatement.executeQuery();
		stmt.close();
		return getPersonneDbByRs(rs);
	}

	public Personne getPersonneDbByRs(ResultSet rs) throws SQLException {
		Personne personne = null;
		if (rs.next()) {
			int id = rs.getInt("idpersonne");
			String login = rs.getString("login");
			String pwd = rs.getString("pwd");
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			String ville = rs.getString("ville");
			String commentaire = rs.getString("commentaire");
			boolean actif = rs.getBoolean("actif");
			boolean verrouille = rs.getBoolean("verrouille");
			boolean premiereconnexion = rs.getBoolean("premiereconnexion");
			boolean afficheage = rs.getBoolean("afficheage");
			boolean affichesexe = rs.getBoolean("affichesexe");
			boolean admin = rs.getBoolean("admin");
			int nbrecheccnx = rs.getInt("nbrecheccnx");
			Date datecreation = rs.getTimestamp("datecreation");
			Date datenaissance = rs.getTimestamp("datenaissance");
			String photo = rs.getString("photo");
			int rayonrecherche = rs.getInt("rayon");
			if (photo == null)
				photo = "";
			String cleactivation = rs.getString("cleactivation");
			String mail = rs.getString("mail");
			int sexe = rs.getInt("sexe");
			double note = rs.getDouble("note");
			int totalavis = rs.getInt("totalavis");
			double latitude = rs.getDouble("latitude");
			double longitude = rs.getDouble("longitude");

			// System.out.println("Note" + note);
			personne = new Personne(id, login, pwd, nom, prenom, ville, actif,
					verrouille, nbrecheccnx, datecreation, datenaissance,
					photo, sexe, mail, cleactivation, note, totalavis,
					commentaire, afficheage, affichesexe, premiereconnexion,
					rayonrecherche, admin, latitude, longitude);
			// System.out.println("Trouvé");
			rs.close();
			return personne;

		}
		return null;
	}

	public Personne getUnProfilDbByRs(ResultSet rs) throws SQLException {
		Personne personne = null;
		if (rs.next()) {
			int id = rs.getInt("idpersonne");
			String login = rs.getString("login");
			String pwd = rs.getString("pwd");
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			String ville = rs.getString("ville");
			String commentaire = rs.getString("commentaire");
			boolean actif = rs.getBoolean("actif");
			boolean verrouille = rs.getBoolean("verrouille");
			boolean afficheage = rs.getBoolean("afficheage");
			boolean affichesexe = rs.getBoolean("affichesexe");
			boolean premiereconnexion = rs.getBoolean("premiereconnexion");
			boolean admin = rs.getBoolean("admin");
			Date datecreation = rs.getTimestamp("datecreation");
			Date datenaissance = rs.getTimestamp("datenaissance");
			String photo = rs.getString("photo");

			int rayonrecherche = rs.getInt("rayon");

			if (photo == null)
				photo = "";
			// String cleactivation = rs.getString("cleactivation");
			String mail = rs.getString("mail");
			int sexe = rs.getInt("sexe");
			double note = rs.getDouble("note");
			double latitude = rs.getDouble("latitude");
			double longitude = rs.getDouble("longitude");

			int totalavis = rs.getInt("nbravis");
		//	System.out.println("Note" + note);
			personne = new Personne(id, login, pwd, nom, prenom, ville, actif,
					verrouille, 0, datecreation, datenaissance, photo, sexe,
					mail, "cleactivation", note, totalavis, commentaire,
					afficheage, affichesexe, premiereconnexion, rayonrecherche,
					admin, latitude, longitude);
			
			rs.close();
			return personne;

		}
		return null;
	}

	public Droit getDroitDbByRs(ResultSet rs) throws SQLException {
		Droit droit = null;
		if (rs.next()) {

			int idpersonne = rs.getInt("idpersonne");
			boolean actif = rs.getBoolean("actif");
			boolean verrouille = rs.getBoolean("verrouille");
			boolean admin = rs.getBoolean("admin");

			droit = new Droit(idpersonne, admin, verrouille, actif);
			
			rs.close();

			return droit;

		}
		// A CHANGER pour les test
		return null;
		
	//	return new Droit(1, false, false, true);
	}

	public void updatePersonne(Personne personne) throws SQLException {
	
		String requete = "UPDATE public.personne   SET nom=?, prenom=?, login=?, pwd=?, ville=?, actif=?,  "
				+ "   verrouille=?, nbrecheccnx=?,  photo=?, "
				+ "       datenaissance=?, sexe=?,   mail=?"
				+ " WHERE idpersonne=?;";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setString(1, personne.getNom());
		preparedStatement.setString(2, personne.getPrenom());
		preparedStatement.setString(3, personne.getLogin().toLowerCase());
		preparedStatement.setString(4, personne.pwd);
		preparedStatement.setString(5, personne.getVille());
		preparedStatement.setBoolean(6, personne.isActif());
		preparedStatement.setBoolean(7, personne.isVerrouille());
		preparedStatement.setInt(8, personne.getNbrecheccnx());
		preparedStatement.setString(9, personne.getPhoto());
		preparedStatement.setTimestamp(10, new java.sql.Timestamp(
				personne.datenaissance.getTime()));

		preparedStatement.setInt(11, personne.getSexe());
		preparedStatement.setString(12, personne.mail);
		preparedStatement.setInt(13, personne.getId());
		preparedStatement.execute();
		preparedStatement.close();

	}

	public void updateChampCalculePersonne(int idpersonne) throws SQLException {
		String requete = " SELECT (SELECT AVG(note) FROM noter where idpersonnenotee=? and fait=true) as note,"
				+ "(SELECT COUNT(*) FROM noter where idpersonnenotee=? and fait=true) as nbravis";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setInt(2, idpersonne);
		ResultSet rs = preparedStatement.executeQuery();
		double note = 0;
		int nbravis = 0;
		if (rs.next()) {
			note = rs.getDouble("note");
			nbravis = rs.getInt("nbravis");
		}
		requete = "UPDATE public.personne   SET note=?, nbravis=?"
				+ " WHERE idpersonne=?;";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setDouble(1, note);
		preparedStatement.setInt(2, nbravis);
		preparedStatement.setInt(3, idpersonne);
		preparedStatement.execute();
		preparedStatement.close();

	}

	public void addPersonne(Personne personne) throws Exception, SQLException {

		Date datecreation = Calendar.getInstance().getTime();
		String requete = "INSERT INTO public.personne(nom, prenom, login, pwd,mail,sexe,verrouille,actif,datecreation,datenaissance,cleactivation)"
				+ "  VALUES (?,?,?,?,?, ?, ?, ?,?,?,?);";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setString(1, personne.getNom());
		preparedStatement.setString(2, personne.getPrenom());
		preparedStatement.setString(3, (personne.getLogin()).toLowerCase());
		preparedStatement.setString(4, personne.pwd);
		preparedStatement.setString(5, personne.mail);
		preparedStatement.setInt(6, personne.getSexe());
		preparedStatement.setBoolean(7, false);
		preparedStatement.setBoolean(8, false);
		preparedStatement.setTimestamp(9,
				new java.sql.Timestamp(datecreation.getTime()));
		preparedStatement.setTimestamp(10, new java.sql.Timestamp(
				personne.datenaissance.getTime()));
		preparedStatement.setString(11, personne.cleactivation);
		preparedStatement.execute();
		preparedStatement.close();

	}

	public int addCompteGenerique(String iduser, String idtoken,
			String photostr, String nom,String gcmToken) throws Exception, SQLException {
		Date datecreation = Calendar.getInstance().getTime();
		String requete = "INSERT INTO personne(nom, prenom, login, pwd,mail,sexe,verrouille,actif,datecreation,"
				+ "datenaissance,cleactivation,latitude,longitude,rayon,adressepref,jeton,photo,commentaire,affichesexe,afficheage,premiereconnexion,gcm)"
				+ "  VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		String commentaire =null;
		PreparedStatement preparedStatement = connexion.prepareStatement(
				requete, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, nom);
		preparedStatement.setString(2, "waydeur");
		preparedStatement.setString(3, iduser);
		preparedStatement.setString(4, null);
		preparedStatement.setString(5, null);
		preparedStatement.setInt(6, 1);
		preparedStatement.setBoolean(7, false);
		preparedStatement.setBoolean(8, true);
		preparedStatement.setTimestamp(9,
				new java.sql.Timestamp(datecreation.getTime()));
		preparedStatement.setTimestamp(10,
				new java.sql.Timestamp(datecreation.getTime()));
		preparedStatement.setString(11, null);
		preparedStatement.setDouble(12, 48.856614);
		preparedStatement.setDouble(13, 2.3522219);
		preparedStatement.setInt(14, 10000);
		preparedStatement.setString(15, "Paris, France");
		preparedStatement.setString(16, idtoken);
	
		if (photostr.equals(""))
		preparedStatement.setString(17, null);
		else
		preparedStatement.setString(17, photostr);
			
		preparedStatement.setString(18, commentaire);
		preparedStatement.setBoolean(19, true);// affiche sexe
		preparedStatement.setBoolean(20, true);// affiche age
		preparedStatement.setBoolean(21, true);// affiche age
		preparedStatement.setString(22, gcmToken);// affiche age
		
		preparedStatement.execute();
	//	System.out.println("Cree compte generique ");
		ResultSet rs = preparedStatement.getGeneratedKeys();
		int cle = 0;
		if (rs.next())
			cle = rs.getInt("idpersonne");
		preparedStatement.close();
		// System.out.println("cle recuepree de la personne" + cle);
		return cle;
	}

	public int TestaddCompteGenerique() throws Exception, SQLException {
		Date datecreation = Calendar.getInstance().getTime();
		String requete = "INSERT INTO personne(nom, prenom, login, pwd,mail,sexe,verrouille,actif,datecreation,"
				+ "datenaissance,cleactivation,latitude,longitude,rayon,adressepref,jeton,photo,commentaire,affichesexe,afficheage,premiereconnexion)"
				+ "  VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		String commentaire = " ";

		String nom = "Waydeur" + System.currentTimeMillis();
		String pseudo = "login" + System.currentTimeMillis();
		String iduser = "iduser" + System.currentTimeMillis();
		String idtoken = "token" + System.currentTimeMillis();
		Random ran = new Random();
		double te = (ran.nextInt(8000) + 42000);
		// double te = (ran.nextInt(200) + 43700);
		double latitude = te / 1000;

		te = (ran.nextInt(6500));
		// te = (ran.nextInt(200)+3800);
		double longitude = te / 1000;

		String photo = "/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoHBwYIDAoMDAsKCwsNDhIQDQ4RDgsLEBYQERMUFRUVDA8XGBYUGBIUFRT/2wBDAQMEBA"
				+ "UEBQkFBQkUDQsNFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBT/wAARCAB4AHgDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAA"
				+ "AAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVF"
				+ "VWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA"
				+ "AwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2"
				+ "Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09"
				+ "fb3+Pn6/9oADAMBAAIRAxEAPwDuryw1TTtZtbVXtYZ5hkmE8L9eK7vTkvreNUl1FGdeMqcZNeYa3omraXqJ1i71KNzu2lSQAoPpXS2GoW0sAlM26QgZOetfiOf5/CWVUfq1KnUptWlyp"
				+ "pc3la1rH7BlGXTqYyq685qa1V2nePnudJqutXFlDJJcusqoMgg9a+fvF/x91G11KcW1o80EL8sGHIr1vULiK6ikhJ4YevWvPb74TWs9w8zAbJTkr61+U5JPK6VapWzOm5X2Vz9CqYGvOio4Op"
				+ "GnO+rcb6dkujPQvh98QR448P21xNmJMcq/au403VNMtG/1Pnv/ALPFeZaBpNnodstpbsIF24wDUuo3U+i2T3EciyBF3YzzXBSw8quYqWBVrv3FL8N+xWLw+HhTnKc7RS1/U6j4i69psUS"
				+ "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";

		PreparedStatement preparedStatement = connexion.prepareStatement(
				requete, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, nom);
		preparedStatement.setString(2, pseudo);
		preparedStatement.setString(3, iduser);
		preparedStatement.setString(4, null);
		preparedStatement.setString(5, null);
		preparedStatement.setInt(6, 1);
		preparedStatement.setBoolean(7, false);
		preparedStatement.setBoolean(8, true);
		preparedStatement.setTimestamp(9,
				new java.sql.Timestamp(datecreation.getTime()));
		preparedStatement.setTimestamp(10,
				new java.sql.Timestamp(datecreation.getTime()));
		preparedStatement.setString(11, null);
		preparedStatement.setDouble(12, latitude);
		preparedStatement.setDouble(13, longitude);
		preparedStatement.setInt(14, 10000);
		preparedStatement.setString(15, "Somewehre");
		preparedStatement.setString(16, idtoken);
		preparedStatement.setString(17, photo);
		preparedStatement.setString(18, commentaire);
		preparedStatement.setBoolean(19, false);// affiche sexe
		preparedStatement.setBoolean(20, false);// affiche age
		preparedStatement.setBoolean(21, false);// affiche age
		preparedStatement.execute();
		//System.out.println("Cree compte generique " + nom + " " + pseudo + " "
	//			+ latitude + " " + longitude);
		ResultSet rs = preparedStatement.getGeneratedKeys();
		int cle = 0;
		if (rs.next())
			cle = rs.getInt("idpersonne");
		preparedStatement.close();
		// System.out.println("cle recuepree de la personne" + cle);
		return cle;
	}

	public void updateJeton(String iduser, String idtoken, String photostr,
			String nom,String gcmToken) throws SQLException {

		if (photostr.equals("")) {// Cas ou la photo vient d'un compte avec mot de passe

			String requete = "UPDATE  personne set jeton=?,nom=?,gcm=? "
					+ " WHERE login=?";
			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);
			preparedStatement.setString(1, idtoken);
			preparedStatement.setString(2, nom);
			preparedStatement.setString(3, gcmToken);
			preparedStatement.setString(4, iduser);
			preparedStatement.execute();
			preparedStatement.close();
		}

		else

		{//Cas opu la photo vient de FB ou google
			
			String photo=isPhotoExist(iduser);
			
			if (photo==null){
			String requete = "UPDATE  personne set jeton=?,nom=?,photo=?,gcm=? "
					+ " WHERE login=?";
			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);
			preparedStatement.setString(1, idtoken);
			preparedStatement.setString(2, nom);
			preparedStatement.setString(3, photostr);
			preparedStatement.setString(4, gcmToken);
			preparedStatement.setString(5, iduser);
			preparedStatement.execute();
			preparedStatement.close();
			}
			
			else
			{	
				String requete = "UPDATE  personne set jeton=?,nom=?,gcm=? "
						+ " WHERE login=?";
				PreparedStatement preparedStatement = connexion
						.prepareStatement(requete);
				preparedStatement.setString(1, idtoken);
				preparedStatement.setString(2, nom);
				preparedStatement.setString(3, gcmToken);
				preparedStatement.setString(4, iduser);
				preparedStatement.execute();
				preparedStatement.close();
				
				
			}

		}

	

	}

	public void updateProfilWayd(int idpersonne, String photostr, String nom,
			String prenom, String datenaissancestr, int sexe,
			String commentaire, boolean afficheage, boolean affichesexe)
			throws SQLException, ParseException {
		String requete = "UPDATE  personne set photo=?, nom=?,prenom=?,datenaissance=?,sexe=?,commentaire=?, afficheage=?,affichesexe=? "
				+ " WHERE idpersonne=?";
		Date dateanniversaire = Parametres.getDateFromString(datenaissancestr);
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setString(1, photostr);
		preparedStatement.setString(2, nom);
		preparedStatement.setString(3, prenom);
		preparedStatement.setTimestamp(4, new java.sql.Timestamp(
				dateanniversaire.getTime()));
		preparedStatement.setInt(5, sexe);
		if (commentaire.equals(""))
		preparedStatement.setString(6, null);
		else
			preparedStatement.setString(6, commentaire);
		preparedStatement.setBoolean(7, afficheage);
		preparedStatement.setBoolean(8, affichesexe);
		preparedStatement.setInt(9, idpersonne);
		preparedStatement.execute();
		preparedStatement.close();

	}

	public void updatePseudo(String pseudo, Long datenaissance, int sexe,
			String token, int idpersonne) throws SQLException, ParseException {
		System.out.println(" debur update");
		String requete = "UPDATE  personne set prenom=?,premiereconnexion=false,sexe=?,datenaissance=?  "
				+ " WHERE idpersonne=?";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setString(1, pseudo);
		preparedStatement.setInt(2, sexe);
		preparedStatement.setTimestamp(3, new Timestamp(datenaissance));
		preparedStatement.setInt(4, idpersonne);
		preparedStatement.execute();
		preparedStatement.close();
		System.out.println(" fin update");

	}

	public void updateGCM(int idpersonne, String gcm) throws SQLException {
		String requete = "UPDATE  personne set gcm=? WHERE gcm=?;"
				+ "UPDATE  personne set gcm=?" + " WHERE idpersonne=?";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setString(1, null);
		preparedStatement.setString(2, gcm);
		preparedStatement.setString(3, gcm);
		preparedStatement.setInt(4, idpersonne);
		preparedStatement.execute();
		preparedStatement.close();

	}

	public void updatePosition(int idpersonne, Double latitude, Double longitude)
			throws SQLException {
		String requete = "UPDATE  personne set latitude=?, longitude=? "
				+ " WHERE idpersonne=?";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setDouble(1, latitude);
		preparedStatement.setDouble(2, longitude);
		preparedStatement.setInt(3, idpersonne);
		preparedStatement.execute();
		preparedStatement.close();

	}

	public void updateRayon(int idpersonne, int rayon) throws SQLException {
		String requete = "UPDATE  personne set rayon=? "
				+ " WHERE idpersonne=?";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, rayon);
		preparedStatement.setInt(2, idpersonne);
		preparedStatement.execute();
		preparedStatement.close();
	}

	public boolean isLoginExist(String login) throws SQLException {
		String requete = "SELECT login  FROM personne where login=?;";
		PreparedStatement preparedStatement;
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setString(1, login);
		ResultSet rs = preparedStatement.executeQuery();
		if (rs.next()) {
			preparedStatement.close();
			return true;
		} else {
			preparedStatement.close();

			return false;

		}

		// TODO Auto-generated method stub
	}
	
	public String isPhotoExist(String login) throws SQLException {
		String requete = "SELECT photo  FROM personne where login=?;";
		String photo=null;
		PreparedStatement preparedStatement;
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setString(1, login);
		ResultSet rs = preparedStatement.executeQuery();
		if (rs.next()) {
			
			photo=rs.getString("photo");
			
		} 
		
			preparedStatement.close();
		
			return photo;

		// TODO Auto-generated method stub
	}
	

	public boolean isPseudoExist(String pseudo) throws SQLException {

		String requete = "SELECT prenom  FROM personne " + "where LOWER(prenom)=?;";

		PreparedStatement preparedStatement;

		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setString(1, pseudo.toLowerCase());
		ResultSet rs = preparedStatement.executeQuery();

		if (rs.next()) {
			preparedStatement.close();
			return true;
		} else {
			preparedStatement.close();

			return false;

		}

		// TODO Auto-generated method stub
	}

	



	

	public Personne test_GetPersonneAle() throws SQLException {
		// .String requete =
		// "SELECT count(idpersonne) as nbrpersonne from personne";
		// PreparedStatement preparedStatement = connexion
		// .prepareStatement(requete);
		//
		// // preparedStatement.setInt(1, idpersonne);
		// ResultSet rs = preparedStatement.executeQuery();
		// int nbrPersonne = 0;
		// while (rs.next()) {
		// nbrPersonne = rs.getInt("nbrpersonne");
		// }
		//
		// requete = "select min (idpersonne) as maxid from personne";
		// preparedStatement = connexion.prepareStatement(requete);
		// int minid = 0;
		// rs = preparedStatement.executeQuery();
		//
		// while (rs.next()) {
		// minid = rs.getInt("maxid");
		//
		// }
		//
		// rs.close();
		// preparedStatement.close();

		Random ran = new Random();
		int idAlea = ran.nextInt(1400000) + 6260;

		return getPersonneId(idAlea - 1);
	}

	public ArrayList<ProfilDiscussion> getListProfilDiscussion(int idpersonne[])
			throws SQLException {

		ArrayList<ProfilDiscussion> retour = new ArrayList<ProfilDiscussion>();
		StringBuilder requete = new StringBuilder();
		requete.append("select idpersonne,photo from personne where idpersonne in (");
		for (int id : idpersonne) {
			requete.append(id + ",");

		}

		requete.delete(requete.length() - 1, requete.length());
		requete.append(")");
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete.toString());

		ResultSet rs = preparedStatement.executeQuery();

		while (rs.next()) {
			String photo = rs.getString("photo");
			int id = rs.getInt("idpersonne");
			retour.add(new ProfilDiscussion(id, photo));

		}
		rs.close();
		preparedStatement.close();
		return retour;
	}

	public ProfilDiscussion getProfilDiscussion(int idpersonne)
			throws SQLException {
		// TODO Auto-generated method stub
		ProfilDiscussion retour = null;
		String requete = "select idpersonne,photo from personne where idpersonne =?";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		ResultSet rs = preparedStatement.executeQuery();

		while (rs.next()) {
			String photo = rs.getString("photo");
			int id = rs.getInt("idpersonne");
			retour = new ProfilDiscussion(id, photo);

		}
		rs.close();
		preparedStatement.close();

		return retour;
	}

}
