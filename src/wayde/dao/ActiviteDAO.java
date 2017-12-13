package wayde.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.naming.NamingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import fcm.ServeurMethodes;
import wayde.bean.Activite;
import wayde.bean.CxoPool;
import wayde.bean.IndicateurWayd;
import wayde.bean.Personne;
import wayde.bean.ProprietePref;
import wayde.bean.TableauBord;


public class ActiviteDAO {
	private static final Logger LOG = Logger.getLogger(ActiviteDAO.class);

	Connection connexion;

	public ActiviteDAO(Connection connexion) {
		this.connexion = connexion;
	}
		

	public Activite getActivite(int idactivite_) {

			Activite activite = null;
			PreparedStatement preparedStatement=null;
			ResultSet rs =null;

		try {
		
			String	requete=" SELECT activite.datedebut,       activite.adresse,    activite.latitude,"
							+ " activite.longitude,    personne.prenom,    personne.sexe,    personne.nom,    personne.idpersonne,"
							+ "personne.affichesexe,personne.afficheage,personne.datenaissance,personne.note,"
							+ "personne.nbravis as totalavis,    personne.photo,1 as role,"
							+ "activite.idactivite,    activite.libelle,    activite.titre,"
							+ "activite.nbrwaydeur,activite.nbmaxwayd,   activite.datefin, activite.idtypeactivite"
							+ ",activite.typeuser,activite.typeacces   FROM personne,"
							+ "activite  WHERE personne.idpersonne = activite.idpersonne  and activite.idactivite=?";
							
			preparedStatement = connexion
					.prepareStatement(requete);
			preparedStatement.setInt(1, idactivite_);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("idactivite");
				int idtypeactivite = rs.getInt("idtypeactivite");
				String libelle = rs.getString("libelle");
				String titre = rs.getString("titre");
				int idorganisateur = rs.getInt("idpersonne");
				int sexe = rs.getInt("sexe");
				Date datedebut = rs.getTimestamp("datedebut");
				Date datefin = rs.getTimestamp("datefin");
				String adresse = rs.getString("adresse");
				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				double note = rs.getDouble("note");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				Date datenaissance = rs.getTimestamp("datenaissance");
				boolean afficheage = rs.getBoolean("afficheage");
				boolean affichesexe = rs.getBoolean("affichesexe");
				int nbmaxwayd = rs.getInt("nbmaxwayd");
				int nbrparticipant = rs.getInt("nbrwaydeur");
				if (prenom == null)
					prenom = "";
				String photo = rs.getString("photo");
				int role = rs.getInt("role");
				Date maintenant = new Date();
				boolean archive = false;

				if (datefin != null)
					if (datefin.before(maintenant))
						archive = true;

				if (datefin == null)
					archive = true;

				int totalavis = rs.getInt("totalavis");
				
				int typeUser = rs.getInt("typeuser");
				int typeAcces = rs.getInt("typeacces");
			
				//if (libelle.length()==0)
				//libelle=" ";
				
				activite = new Activite(id, titre, libelle, idorganisateur,
						datedebut, datefin, idtypeactivite, latitude,
						longitude, adresse, nom, prenom, photo, note, role,
						archive, totalavis, datenaissance, sexe,
						nbrparticipant, afficheage, affichesexe, nbmaxwayd,typeUser,typeAcces
						);

			}
			CxoPool.close(preparedStatement, rs);
			return activite;

		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			CxoPool.close(preparedStatement, rs);
			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));

		}

		return null;

	}

	public ArrayList<Personne> getListPersonneInterresse(Activite activite)
			throws SQLException {
		
		ArrayList<Personne> listpersonne = new ArrayList<Personne>();

		 if (!activite.isEnCours())
			 return listpersonne;
		
		Calendar c = Calendar.getInstance();
		c.setTime(activite.datedebut);
		int jour = c.get(Calendar.DAY_OF_WEEK);
		int heuredebut = c.get(Calendar.HOUR_OF_DAY);
		c.setTime(activite.datefinactivite);
		int idtypeactivite = activite.getTypeactivite();

		double actlatitude = activite.getLatitude();
		double actlongitude = activite.getLongitude();

		String requete = "SELECT personne.notification,personne.gcm,personne.latitude,personne.longitude,personne.rayon,prefere.idpersonne,"
				+ " prefere.idtypeactivite FROM public.plage,prefere,personne "
				+ "where jour=? and heuredebut<=? and ?<=heurefin and plage.idtypeactivite=? "
				+ " and prefere.idpersonne=plage.idpersonne	 and prefere.idtypeactivite=plage.idtypeactivite"
				+ "  and prefere.active=true  and prefere.always=false  and personne.idpersonne=prefere.idpersonne "
				+ "and personne.idpersonne!=? union"
				+ "  select personne.notification,personne.gcm,personne.latitude,personne.longitude,personne.rayon,prefere.idpersonne, prefere.idtypeactivite  "
				+ "FROM prefere,personne "
				+ "where "
				+ " prefere.idtypeactivite=? and  prefere.active=true  and prefere.always=true"
				+ " and personne.idpersonne=prefere.idpersonne and personne.idpersonne!=?;";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
	
		preparedStatement.setInt(1, jour);
		preparedStatement.setInt(2, heuredebut);
		preparedStatement.setInt(3, heuredebut);
		preparedStatement.setInt(4, idtypeactivite);
		preparedStatement.setInt(5, activite.getIdorganisateur());
		preparedStatement.setInt(6, idtypeactivite);
		preparedStatement.setInt(7, activite.getIdorganisateur());
		ResultSet rs = preparedStatement.executeQuery();
		// A FAIRE ************
		// RECUPER TOUTS LES DESTINATAIRE POUT ENVOYER MESSAGE
		String gcm;
		int idpersonne;
	
		while (rs.next()) {
			gcm = rs.getString("gcm");
			idpersonne = rs.getInt("idpersonne");
			double personnelatitude = rs.getDouble("latitude");
			double personnelongitude = rs.getDouble("longitude");
			int rayon = rs.getInt("rayon");
			boolean notification=rs.getBoolean("notification");
			double distance = ServeurMethodes.getDistance(actlatitude,
					personnelatitude, actlongitude, personnelongitude);
	
			if (idpersonne != activite.getIdorganisateur() && distance <= rayon)
				listpersonne.add(new Personne(gcm, idpersonne,notification));
		}

		rs.close();
		preparedStatement.close();
		return listpersonne;

	}

	public void addActivite(Activite activite) throws SQLException {

		
		String requete = "INSERT INTO activite("
				+ "   titre, libelle,datedebut, "
				+ " datefin, adresse, latitude, longitude, actif,"
				+ "idtypeactivite,idpersonne,datecreation,nbmaxwayd,nbrwaydeur,typeuser)"
				+ "	VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement preparedStatement = connexion.prepareStatement(
				requete, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, activite.getTitre());
		preparedStatement.setString(2, activite.getLibelle());
		preparedStatement.setTimestamp(3, new java.sql.Timestamp(
				activite.datedebut.getTime()));
		preparedStatement.setTimestamp(4, new java.sql.Timestamp(
				activite.datefinactivite.getTime()));
		preparedStatement.setString(5, activite.getAdresse());
		preparedStatement.setDouble(6, activite.getLatitude());
		preparedStatement.setDouble(7, activite.getLongitude());
		preparedStatement.setBoolean(8, activite.actif);
		preparedStatement.setInt(9, activite.getTypeactivite());
		preparedStatement.setInt(10, activite.getIdorganisateur());
		preparedStatement.setTimestamp(11,
				new java.sql.Timestamp(new Date().getTime()));
		preparedStatement.setInt(12, activite.getNbmaxwaydeur());
		preparedStatement.setInt(13, activite.getNbrparticipant());
		preparedStatement.setInt(14, activite.getTypeUser());
		preparedStatement.execute();
		ResultSet rs = preparedStatement.getGeneratedKeys();
		int cle = 0;
		if (rs.next())
			cle = rs.getInt("idactivite");
		preparedStatement.close();
		rs.close();
		activite.setId(cle);

	}

	
	

	public void addRefus(int idPersonne,int idActivite) throws SQLException {

		if (!isADejaRefuse(idPersonne, idActivite)){
		
		String requete = "INSERT INTO refusparticipation(idpersonne, idactivite, datecreation)"
				+ " VALUES (?, ?, ?)";

		PreparedStatement preparedStatement = connexion.prepareStatement(
				requete, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, idPersonne);
		preparedStatement.setInt(2, idActivite);
		preparedStatement.setTimestamp(3,new java.sql.Timestamp(new Date().getTime()));
		preparedStatement.execute();
		preparedStatement.close();
		}

	}

	public ArrayList<Activite> getListActiviteAvenir(double malatitude,
			double malongitude, int rayonmetre, int idtypeactivite_,
			String motcle, int commencedans) throws SQLException {
	
		double coef = rayonmetre * 0.007 / 700;
		double latMin = malatitude - coef;
		double latMax = malatitude + coef;
		double longMin = malongitude - coef;
		double longMax = malongitude + coef;
		Activite activite = null;
		ArrayList<Activite> retour = new ArrayList<Activite>();
		Calendar calendrier = Calendar.getInstance();
		calendrier.add(Calendar.MINUTE, commencedans);

		String requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
				+ " activite.longitude,    personne.prenom,    personne.sexe,    personne.nom,    personne.idpersonne,personne.datenaissance,    "
				+ "personne.note,personne.nbravis as totalavis,personne.photo,"
				+ "activite.nbrwaydeur as nbrparticipant,1 as role,"
				+ "activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite,activite.nbmaxwayd,activite.typeuser,activite.typeacces   FROM personne,"
				+ "activite  WHERE (personne.idpersonne = activite.idpersonne  and activite.idtypeactivite=?  "
				+ "and ? between datedebut and datefin"
				+ " and activite.latitude between ? and ?"
				+ " and activite.longitude between ? and ?"
				+ " and (UPPER(libelle) like UPPER(?) or UPPER(titre) like UPPER(?))  )  ORDER BY datedebut asc";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		//System.out.println(new Date());
		preparedStatement.setInt(1, idtypeactivite_);
		preparedStatement.setTimestamp(2, new java.sql.Timestamp(calendrier
				.getTime().getTime()));

		String test = "%" + motcle + "%";
		preparedStatement.setDouble(3, latMin);
		preparedStatement.setDouble(4, latMax);
		preparedStatement.setDouble(5, longMin);
		preparedStatement.setDouble(6, longMax);
		preparedStatement.setString(7, test);
		preparedStatement.setString(8, test);
		//
		ResultSet rs = preparedStatement.executeQuery();
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
			//Date datefinactivite = rs.getTimestamp("d_finactivite");

			if (prenom == null)
				prenom = "";
			String photo = rs.getString("photo");
			int role = rs.getInt("role");
			Date datenaissance = rs.getTimestamp("datenaissance");
			boolean archive = false;
			int totalavis = rs.getInt("totalavis");
			
			int typeUser = rs.getInt("typeuser");
			int typeAcces = rs.getInt("typeacces");
		


			activite = new Activite(id, titre, libelle, idorganisateur,
					datedebut, datefin, idtypeactivite, latitude, longitude,
					adresse, nom, prenom, photo, note, role, archive,
					totalavis, datenaissance, sexe, nbrparticipant, true, true,
					nbmaxwayd,typeUser,typeAcces);
			retour.add(activite);

		}

		rs.close();
		preparedStatement.close();
		return retour;

	}
		
	
		
	
	
	public ArrayList<Activite> getListActiviteAvenirNocritere(
			double malatitude, double malongitude, int rayonmetre,
			String motcle, int commencedans) throws SQLException {
		
		double coef = rayonmetre * 0.007 / 700;
		double latMin = malatitude - coef;
		double latMax = malatitude + coef;
		double longMin = malongitude - coef;
		double longMax = malongitude + coef;

		Activite activite = null;
		ArrayList<Activite> retour = new ArrayList<Activite>();
		Calendar calendrier = Calendar.getInstance();
		calendrier.add(Calendar.MINUTE, commencedans);

		String requete = " SELECT activite.datedebut,      activite.adresse,    activite.latitude,"
				+ " activite.longitude,    personne.prenom,    personne.sexe,    personne.nom,personne.datenaissance  ,    personne.idpersonne,    "
				+ " personne.note,personne.nbravis as totalavis  ,personne.photo,"
				+ "activite.nbrwaydeur as nbrparticipant,1 as role,"
				+ "activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,activite.idtypeactivite ,activite.nbmaxwayd,activite.typeuser,activite.typeacces  FROM personne,"
				+ "activite  WHERE (personne.idpersonne = activite.idpersonne  "
				+ "and ? between datedebut and datefin"
				+ " and activite.latitude between ? and ?"
				+ " and activite.longitude between ? and ?"
				+ " and (UPPER(libelle) like UPPER(?) or UPPER(titre) like UPPER(?))  )  ORDER BY datedebut asc";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
	
		preparedStatement.setTimestamp(1, new java.sql.Timestamp(calendrier
				.getTime().getTime()));

		String test = "%" + motcle + "%";
		preparedStatement.setDouble(2, latMin);
		preparedStatement.setDouble(3, latMax);
		preparedStatement.setDouble(4, longMin);
		preparedStatement.setDouble(5, longMax);
		preparedStatement.setString(6, test);
		preparedStatement.setString(7, test);
		//

		ResultSet rs = preparedStatement.executeQuery();

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
			if (prenom == null)
				prenom = "";
			String photo = rs.getString("photo");
			int role = rs.getInt("role");
			boolean archive = false;
			Date datenaissance = rs.getTimestamp("datenaissance");
			//Date datefinactivite = rs.getTimestamp("d_finactivite");

			int totalavis = rs.getInt("totalavis");
			
			int typeUser = rs.getInt("typeuser");
			int typeAcces = rs.getInt("typeacces");
		
			
			activite = new Activite(id, titre, libelle, idorganisateur,
					datedebut, datefin, idtypeactivite, latitude, longitude,
					adresse, nom, prenom, photo, note, role, archive,
					totalavis, datenaissance, sexe, nbrparticipant, true, true,
					nbmaxwayd,typeUser,typeAcces);

			retour.add(activite);

		}
		rs.close();
		preparedStatement.close();
		return retour;

	}

	public ArrayList<Activite> getListActivitePref(int idpersonne)
			throws Exception {

		Activite activite = null;
		ArrayList<Activite> retour = new ArrayList<Activite>();

		// ************************************Recupere les pref de
		// l'USER**********************
		PersonneDAO personnedao = new PersonneDAO(connexion);

		ProprietePref proprietepreference = personnedao
				.getProprietePref(idpersonne);
		double coef = proprietepreference.getRayon() * 0.007 / 700;
		double latMin = proprietepreference.getLatitude() - coef;
		double latMax = proprietepreference.getLatitude() + coef;
		double longMin = proprietepreference.getLongitude() - coef;
		double longMax = proprietepreference.getLongitude() + coef;
		try {


			
			
			// Modifcation des requets pour n'avoir que les suggestions en cours
			String requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude, activite.longitude,activite.idactivite,"
					+ " activite.libelle,    activite.titre,    activite.datefin,   activite.idtypeactivite,personne.photo,0 as role,"
					+ "personne.datenaissance,activite.typeuser,activite.typeacces,  "
					+ " personne.note,personne.nbravis as totalavis,"
					+ " activite.nbrwaydeur as nbrparticipant,activite.nbmaxwayd,"
					+ " personne.prenom,personne.sexe,personne.nom,personne.idpersonne "
					+ " FROM activite,personne "

					+ " where exists ("
					+ "	select 1 from prefere	 where prefere.idpersonne=? and	 prefere.idtypeactivite=activite.idtypeactivite	 "
					+ " and prefere.always=true and prefere.active=true )"
					+ " and personne.idpersonne = activite.idpersonne  and ? between datedebut and datefin and activite.idpersonne!=? "
					+ " and activite.latitude between ? and ?"
					+ " and activite.longitude between ? and ? ORDER BY datedebut ASC;";

			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);
			
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setTimestamp(2,
					new java.sql.Timestamp(new Date().getTime()));
			preparedStatement.setInt(3, idpersonne);
			preparedStatement.setDouble(4, latMin);
			preparedStatement.setDouble(5, latMax);
			preparedStatement.setDouble(6, longMin);
			preparedStatement.setDouble(7, longMax);
				

			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
			
				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				double distance = ServeurMethodes.getDistance(
						proprietepreference.getLatitude(), latitude,
						proprietepreference.getLongitude(), longitude);

				// Passe � l'enregistrement suivant si la distance est plus
				// grande que le rayon

				if (distance >= proprietepreference.getRayon())
					continue;

				int id = rs.getInt("idactivite");
				int sexe = rs.getInt("sexe");

				String libelle = rs.getString("libelle");
				String titre = rs.getString("titre");
				int idorganisateur = rs.getInt("idpersonne");
				int idtypeactivite = rs.getInt("idtypeactivite");
				int nbrparticipant = rs.getInt("nbrparticipant");

				Date datedebut = rs.getTimestamp("datedebut");
				Date datefin = rs.getTimestamp("datefin");
				String adresse = rs.getString("adresse");
				double note = rs.getDouble("note");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				if (prenom == null)
					prenom = "";
				String photo = rs.getString("photo");
				int role = rs.getInt("role");
				boolean archive = false;
				Date datenaissance = rs.getTimestamp("datenaissance");
				int totalavis = rs.getInt("totalavis");
				int nbmaxwayd = rs.getInt("nbmaxwayd");
				//Date datefinactivite = rs.getTimestamp("d_finactivite");
				int typeUser = rs.getInt("typeuser");
				int typeAcces = rs.getInt("typeacces");
			
				activite = new Activite(id, titre, libelle, idorganisateur,
						datedebut, datefin, idtypeactivite, latitude,
						longitude, adresse, nom, prenom, photo, note, role,
						archive, totalavis, datenaissance, sexe,
						nbrparticipant, true, true, nbmaxwayd,typeUser,typeAcces);

				retour.add(activite);

			}
			rs.close();
			preparedStatement.close();

		
			return retour;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));

		}

		return null;

	}
	

	
	
	
	public ArrayList<Activite> getMesActiviteEncours(int idpersonne)
			throws SQLException {

		Activite activite = null;
		ArrayList<Activite> retour = new ArrayList<Activite>();

		String requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
				+ " activite.longitude,    personne.prenom, personne.datenaissance  ,   personne.sexe,    personne.nom,    personne.idpersonne, "
				+ "personne.note,0 as role,"
				+ "personne.nbravis as totalavis,"
				+ "activite.nbrwaydeur as nbrparticipant,    personne.photo, personne.photo,"
				+ "activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite, activite.nbmaxwayd,activite.typeuser,activite.typeacces  FROM personne,"
				+ "activite,participer  WHERE (personne.idpersonne=activite.idpersonne and "
				+ "activite.idactivite = participer.idactivite "
				+ " and participer.idpersonne=? and activite.datefin>? ) ORDER BY datedebut DESC";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setTimestamp(2,
				new java.sql.Timestamp(new Date().getTime()));

		ResultSet rs = preparedStatement.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("idactivite");
			String libelle = rs.getString("libelle");
			String titre = rs.getString("titre");
			int idorganisateur = rs.getInt("idpersonne");
			int nbrparticipant = rs.getInt("nbrparticipant");
			int idtypeactivite = rs.getInt("idtypeactivite");
			Date datedebut = rs.getTimestamp("datedebut");
			Date datefin = rs.getTimestamp("datefin");
			String adresse = rs.getString("adresse");
			double latitude = rs.getDouble("latitude");
			double longitude = rs.getDouble("longitude");
			double note = rs.getDouble("note");
			String nom = rs.getString("nom");
			Date datenaissance = rs.getTimestamp("datenaissance");
			String prenom = rs.getString("prenom");
			if (prenom == null)
				prenom = "";
			String photo = rs.getString("photo");
			boolean archive = false;
			int totalavis = rs.getInt("totalavis");
			int role = rs.getInt("role");
			int sexe = rs.getInt("sexe");
			int nbmaxwayd = rs.getInt("nbmaxwayd");
			//Date datefinactivite = rs.getTimestamp("d_finactivite");
			int typeUser = rs.getInt("typeuser");
			int typeAcces = rs.getInt("typeacces");
		
			activite = new Activite(id, titre, libelle, idorganisateur,
					datedebut, datefin, idtypeactivite, latitude, longitude,
					adresse, nom, prenom, photo, note, role, archive,
					totalavis, datenaissance, sexe, nbrparticipant, true, true,
					nbmaxwayd,typeUser,typeAcces);
			retour.add(activite);

		}

		preparedStatement.close();

		// Cherche dans les activite

		requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
				+ " activite.longitude,    personne.prenom,  personne.datenaissance,  personne.sexe,    personne.nom,    personne.idpersonne,"
				+ " personne.note,personne.nbravis as totalavis ,"
				+ "activite.nbrwaydeur as nbrparticipant,   personne.photo,"
				+ "activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite,activite.nbmaxwayd,activite.typeuser,activite.typeacces   FROM personne,"
				+ "activite  WHERE personne.idpersonne = activite.idpersonne  and activite.idpersonne=?"
				+ "and  activite.datefin>?";

		preparedStatement = connexion.prepareStatement(requete);

		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setTimestamp(2,
				new java.sql.Timestamp(new Date().getTime()));
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
			if (prenom == null)
				prenom = "";
			String photo = rs.getString("photo");
			Date datenaissance = rs.getTimestamp("datenaissance");
			boolean archive = false;
			int sexe = rs.getInt("sexe");
			int totalavis = rs.getInt("totalavis");
			int nbmaxwayd = rs.getInt("nbmaxwayd");
			//Date datefinactivite = rs.getTimestamp("d_finactivite");
			int typeUser = rs.getInt("typeuser");
			int typeAcces = rs.getInt("typeacces");
		
			activite = new Activite(id, titre, libelle, idorganisateur,
					datedebut, datefin, idtypeactivite, latitude, longitude,
					adresse, nom, prenom, photo, note, 1, archive, totalavis,
					datenaissance, sexe, nbrparticipant, true, true, nbmaxwayd
					,typeUser,typeAcces);

			retour.add(activite);

		}

		rs.close();
		preparedStatement.close();
		return retour;

	}

	public void RemoveActivite(int idactivite)
			throws SQLException {
		
			
		String requete = "DELETE FROM demandeami where ( idactivite=? );";
		PreparedStatement preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idactivite);
		preparedStatement.execute();
		preparedStatement.close();

		 requete = "DELETE FROM public.participer where ( idactivite=? );";
		 preparedStatement = connexion
				.prepareStatement(requete);
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

		

		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

	}

	public void RemoveOnlyActivite(int idactivite)
			throws SQLException {
		
			
		String requete  = "DELETE FROM activite where ( idactivite=? );";
		PreparedStatement preparedStatement = connexion.prepareStatement(requete);
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idactivite);
		preparedStatement.execute();
		preparedStatement.close();

		

		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

	}

	public ArrayList<Activite> getMesActiviteArchive(int idpersonne)
			throws SQLException {
		Activite activite = null;
		ArrayList<Activite> retour = new ArrayList<Activite>();

		String requete = "SELECT activite.datedebut,activite.adresse,activite.latitude,"
				+ "activite.longitude,personne.prenom,personne.sexe,personne.nom,  personne.datenaissance,personne.idpersonne, "
				+ "personne.note,0 as role,"
				+ "personne.nbravis as totalavis,"
				+ "activite.nbrwaydeur as nbrparticipant,    personne.photo,"
				+ "personne.photo,activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite, activite.nbmaxwayd,activite.typeuser,activite.typeacces  FROM personne,"
				+ "activite,participer  WHERE (personne.idpersonne=activite.idpersonne and "
				+ "activite.idactivite = participer.idactivite "
				+ " and participer.idpersonne=? and activite.datefin<? ) ORDER BY datedebut DESC";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setTimestamp(2,
				new java.sql.Timestamp(new Date().getTime()));

		ResultSet rs = preparedStatement.executeQuery();
	
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
			if (prenom == null)
				prenom = "";
			String photo = rs.getString("photo");
			boolean archive = true;
			int totalavis = rs.getInt("totalavis");
			int role = rs.getInt("role");
			int sexe = rs.getInt("sexe");
			int nbmaxwayd = rs.getInt("nbmaxwayd");
	
			int typeUser = rs.getInt("typeuser");
			int typeAcces = rs.getInt("typeacces");
		
			activite = new Activite(id, titre, libelle, idorganisateur,
					datedebut, datefin, idtypeactivite, latitude, longitude,
					adresse, nom, prenom, photo, note, role, archive,
					totalavis, datenaissance, sexe, nbrparticipant, true, true,
					nbmaxwayd,typeUser,typeAcces);
			retour.add(activite);

		}

		preparedStatement.close();

		// Cherche dans les activite

		requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
				+ " activite.longitude,    personne.prenom,personne.datenaissance,    personne.sexe,    personne.nom,    personne.idpersonne,   "
				+ "personne.note,personne.nbravis as totalavis,"
				+ "activite.nbrwaydeur as nbrparticipant"
				+ ",personne.photo,activite.idactivite,    activite.libelle,    activite.titre,   activite.datefin,    activite.idtypeactivite,activite.nbmaxwayd,activite.typeuser,activite.typeacces  FROM personne,"
				+ "activite  WHERE personne.idpersonne = activite.idpersonne  and activite.idpersonne=?"
				+ "and  activite.datefin<?";

		preparedStatement = connexion.prepareStatement(requete);

		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setTimestamp(2,
				new java.sql.Timestamp(new Date().getTime()));
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
			if (prenom == null)
				prenom = "";
			String photo = rs.getString("photo");
			boolean archive = true;
			int sexe = rs.getInt("sexe");
			int totalavis = rs.getInt("totalavis");
			int nbmaxwayd = rs.getInt("nbmaxwayd");
		//	Date datefinactivite = rs.getTimestamp("d_finactivite");
			int typeUser = rs.getInt("typeuser");
			int typeAcces = rs.getInt("typeacces");
			activite = new Activite(id, titre, libelle, idorganisateur,
					datedebut, datefin, idtypeactivite, latitude, longitude,
					adresse, nom, prenom, photo, note, 1, archive, totalavis,
					datenaissance, sexe, nbrparticipant, true, true, nbmaxwayd,
					typeUser,typeAcces);
			retour.add(activite);

		}

		rs.close();
		preparedStatement.close();

		return retour;

	}

	public int getNbrMessageNonLu(int idpersonne) throws SQLException {
		int nbrmessagenonlu = 0;

	
		String requete = "select  count(idmessage) as nbrmessagenonlu from message where (  iddestinataire=? and lu=false and emis=false);";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		ResultSet rs = preparedStatement.executeQuery();
		if (rs.next()) {
			nbrmessagenonlu = rs.getInt("nbrmessagenonlu");
		}

		
		requete = "select  count(idmessage) as nbrmessagenonlu from messagebyact m,activite a where (  m.iddestinataire=? and m.lu=false"
				+ " and m.emis=false and a.idactivite=m.idactivite and a.datefin>?);";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setTimestamp(2,
				new java.sql.Timestamp(new Date().getTime()));
		rs = preparedStatement.executeQuery();
		if (rs.next()) {
			nbrmessagenonlu = nbrmessagenonlu + rs.getInt("nbrmessagenonlu");
		}
		CxoPool.close(preparedStatement, rs);
		return nbrmessagenonlu;

	}

	public int getNbrSuggestion(int idpersonne) throws Exception {

		int nbrsuggestion = 0;
		PersonneDAO personnedao = new PersonneDAO(connexion);
		ProprietePref proprietepreference = personnedao
				.getProprietePref(idpersonne);

		double coef = proprietepreference.getRayon() * 0.007 / 700;
		double latMin = proprietepreference.getLatitude() - coef;
		double latMax = proprietepreference.getLatitude() + coef;
		double longMin = proprietepreference.getLongitude() - coef;
		double longMax = proprietepreference.getLongitude() + coef;

		
		String requete = " SELECT  activite.latitude, activite.longitude"
				+ " FROM activite,personne  where exists ("
				+ "	select 1 from prefere	 where prefere.idpersonne=? and	 prefere.idtypeactivite=activite.idtypeactivite	 and prefere.always=true and prefere.active=true        )"
				+ " and personne.idpersonne = activite.idpersonne  and ? between datedebut and datefin and activite.idpersonne!=?"
				+ " and activite.latitude between ? and ?"
				+ " and activite.longitude between ? and ?  ORDER BY datedebut DESC;";

		PreparedStatement preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		
		preparedStatement.setTimestamp(2,
				new java.sql.Timestamp(new Date().getTime()));
		preparedStatement.setInt(3, idpersonne);
		preparedStatement.setDouble(4, latMin);
		preparedStatement.setDouble(5, latMax);
		preparedStatement.setDouble(6, longMin);
		preparedStatement.setDouble(7, longMax);
		
	
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			double latitude = rs.getDouble("latitude");
			double longitude = rs.getDouble("longitude");
			double distance = ServeurMethodes.getDistance(
					proprietepreference.getLatitude(), latitude,
					proprietepreference.getLongitude(), longitude);

			if (distance <= proprietepreference.getRayon())
				nbrsuggestion++;
		}

		return nbrsuggestion;

	}

	// Defini si la personne participe � une activite
	public boolean isInscrit(Activite activite, int idpersonne) {
		String requete = "SELECT  idpersonne FROM public.participer "
				+ "where( idpersonne=? and idactivite=?);";
		PreparedStatement preparedStatement;
		try {
			
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setInt(2, activite.getId());
			ResultSet rs = preparedStatement.executeQuery();
				
			if (rs.next()) {
				activite.setDejainscrit(true);
				preparedStatement.close();
				return true;
			} else {
				preparedStatement.close();
				return false;

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
		}

		return false;

		// TODO Auto-generated method stub

	}

	public boolean isOrganisateur(int idactivite, int idpersonne)
			throws SQLException {
		String requete = "SELECT idactivite   FROM activite "
				+ "where( idpersonne=? and idactivite=?);";
		PreparedStatement preparedStatement;

		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setInt(2, idactivite);
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

	public int getNbrMessageNonLuByAct(int idpersonne, int idactivite)
			throws SQLException {
		int nbrmessagenonlu = 0;

		String requete = "select  count(idmessage) as nbrmessagenonlu from messagebyact m,activite a where "
				+ "(  m.iddestinataire=? and m.lu=false"
				+ " and m.emis=false and m.idactivite=? and a.idactivite=m.idactivite and a.datefin>?);";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setInt(2, idactivite);
		preparedStatement.setTimestamp(3,
				new java.sql.Timestamp(new Date().getTime()));
		ResultSet rs = preparedStatement.executeQuery();

		if (rs.next()) {
			nbrmessagenonlu = rs.getInt("nbrmessagenonlu");
		}

		return nbrmessagenonlu;

	}

	public int getNbrActiviteTotal(int idpersonne) throws SQLException {
		int nbractivite = 0;
		String requete = "Select count(idactivite) as nbractivite  FROM activite where (  idpersonne=? and  activite.datefin>?) ;";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setTimestamp(2,
				new java.sql.Timestamp(new Date().getTime()));
		ResultSet rs = preparedStatement.executeQuery();

		if (rs.next()) {
			nbractivite = rs.getInt("nbractivite");
		}
		int nbrparticipation = 0;
	
		requete = "Select count(participer.idpersonne) as nbrparticipation  FROM activite,participer where  participer.idactivite=activite.idactivite"
				+ " and  participer.idpersonne=? and activite.datefin>? ;";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setTimestamp(2,
				new java.sql.Timestamp(new Date().getTime()));
		rs = preparedStatement.executeQuery();
		if (rs.next()) {
			nbrparticipation = rs.getInt("nbrparticipation");
		}
		
		return nbractivite + nbrparticipation;

	}

	public int getNbrActiviteProposeEnCours(int idpersonne) throws SQLException {
		int nbractivite = 0;
	
		String requete = "Select count(idactivite) as nbractivite  FROM activite where (  idpersonne=? and  activite.datefin>?) ;";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setTimestamp(2,
				new java.sql.Timestamp(new Date().getTime()));
		ResultSet rs = preparedStatement.executeQuery();

		if (rs.next()) {
			nbractivite = rs.getInt("nbractivite");
		}
	
		return nbractivite ;

	}

	
	
	public int getNbrNotificationNonLu(int idpersonne) throws SQLException {
		int nbrnotification = 0;

		String requete = "select  count(iddestinataire) as nbrnotification "
				+ "from notification where (  iddestinataire=? and lu=false);";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		ResultSet rs = preparedStatement.executeQuery();
		if (rs.next()) {
			nbrnotification = rs.getInt("nbrnotification");
		}
		return nbrnotification;

	}

	public TableauBord getTableauBord(int idpersonne) throws Exception {

		int nbractivite = 0, nbrparticipation = 0, nbrmessagenonlu = 0;

		// ************************************Recupere les pref de
		// l'USER**********************

		PersonneDAO personnedao = new PersonneDAO(connexion);
		ProprietePref proprietepreference = personnedao
				.getProprietePref(idpersonne);

		double coef = proprietepreference.getRayon() * 0.007 / 700;
		double latMin = proprietepreference.getLatitude() - coef;
		double latMax = proprietepreference.getLatitude() + coef;
		double longMin = proprietepreference.getLongitude() - coef;
		double longMax = proprietepreference.getLongitude() + coef;
	
		String requete = "Select count(idactivite) as nbractivite  FROM activite where (  idpersonne=? and  activite.datefin>?) ;";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setTimestamp(2,
				new java.sql.Timestamp(new Date().getTime()));
		ResultSet rs = preparedStatement.executeQuery();

		if (rs.next()) {
			nbractivite = rs.getInt("nbractivite");
		}

		// Calcul nombre de participation en cours
		
		requete = "Select count(participer.idpersonne) as nbrparticipation  FROM activite,participer where  participer.idactivite=activite.idactivite"
				+ " and  participer.idpersonne=? and activite.datefin>? ;";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setTimestamp(2,
				new java.sql.Timestamp(new Date().getTime()));

		rs = preparedStatement.executeQuery();
		if (rs.next()) {
			nbrparticipation = rs.getInt("nbrparticipation");
		}

		//Calcul de message non lu en stand alone

		requete = "select  count(idmessage) as nbrmessagenonlu from message where (  iddestinataire=? and lu=false and emis=false);";

		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		rs = preparedStatement.executeQuery();
		if (rs.next()) {
			nbrmessagenonlu = rs.getInt("nbrmessagenonlu");
		}
		//"Calcul de message non lu en stand talkgroup

		requete = "select  count(idmessage) as nbrmessagenonlu from messagebyact m,activite a where (  m.iddestinataire=? and m.lu=false"
				+ " and m.emis=false and a.idactivite=m.idactivite and a.datefin>?);";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setTimestamp(2,
				new java.sql.Timestamp(new Date().getTime()));
		rs = preparedStatement.executeQuery();
		if (rs.next()) {
			nbrmessagenonlu = nbrmessagenonlu + rs.getInt("nbrmessagenonlu");
		}

		requete = " SELECT  activite.latitude, activite.longitude"
				+ " FROM activite,personne  where exists ("
				+ "	select 1 from prefere	 where prefere.idpersonne=? and	 prefere.idtypeactivite=activite.idtypeactivite	 and prefere.always=true and prefere.active=true        )"
				+ " and personne.idpersonne = activite.idpersonne  and ? between datedebut and datefin and activite.idpersonne!=?"
				+ " and activite.latitude between ? and ?"
				+ " and activite.longitude between ? and ?  ORDER BY datedebut DESC;";

		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		
		preparedStatement.setTimestamp(2,
				new java.sql.Timestamp(new Date().getTime()));
		preparedStatement.setInt(3, idpersonne);
		preparedStatement.setDouble(4, latMin);
		preparedStatement.setDouble(5, latMax);
		preparedStatement.setDouble(6, longMin);
		preparedStatement.setDouble(7, longMax);
		
		int nbrsuggestion = 0;
		rs = preparedStatement.executeQuery();
		while (rs.next()) {
			double latitude = rs.getDouble("latitude");
			double longitude = rs.getDouble("longitude");
			double distance = ServeurMethodes.getDistance(
					proprietepreference.getLatitude(), latitude,
					proprietepreference.getLongitude(), longitude);

			if (distance <= proprietepreference.getRayon())
				nbrsuggestion++;
		}
		//Compte le nbr de suggestion" + nbrsuggestion

		requete = "select  count(iddestinataire) as nbrnotification from notification where (  iddestinataire=? and lu=false);";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		rs = preparedStatement.executeQuery();
		int nbrnotification = 0;
		if (rs.next()) {
			nbrnotification = rs.getInt("nbrnotification");
		}

		preparedStatement.close();
		rs.close();
		requete = "select  count(idpersonne) as nbrami from ami where  idpersonne=?;";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		rs = preparedStatement.executeQuery();
		int nbrami = 0;
		if (rs.next()) {
			nbrami = rs.getInt("nbrami");
		}

		preparedStatement.close();
		rs.close();
		
		// Compte le nbr de notification"
		return new TableauBord(nbrmessagenonlu, nbrparticipation + nbractivite,
				nbrsuggestion, nbrnotification, nbrami);
	}

	public int getNbrNotification(int idpersonne) throws SQLException {
		// TODO Auto-generated method stub

		int nbrnotification = 0;
		String requete = "select  count(iddestinataire) as nbrnotification from notification"
				+ " where (  iddestinataire=? and lu=false);";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		ResultSet rs = preparedStatement.executeQuery();

		if (rs.next()) {
			nbrnotification = rs.getInt("nbrnotification");
		}

		//System.out.println(nbrnotification);
		CxoPool.close(preparedStatement, rs);
		return nbrnotification;
	}

	public int getNbrAmi(int idpersonne) throws SQLException {
		// TODO Auto-generated method stub

		String requete = "select  count(idpersonne) as nbrami from ami where  idpersonne=?;";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		ResultSet rs = preparedStatement.executeQuery();
		int nbrami = 0;
		if (rs.next()) {
			nbrami = rs.getInt("nbrami");
		}

		CxoPool.close(preparedStatement, rs);
		return nbrami;

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
	
	public IndicateurWayd getIndicateurs() throws SQLException {

		int nbrTotalactivite = 0, nbrTotalparticipation = 0, nbrTotalInscrit = 0, nbrTotalMessage = 0, nbrTotalMessageByAct = 0;

		// ************Calcul le nbr total d'activit�

		String requete = "Select count(idactivite) as nbractivite  FROM activite";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		ResultSet rs = preparedStatement.executeQuery();

		if (rs.next()) {
			nbrTotalactivite = rs.getInt("nbractivite");
		}

		//***********Calcul le nbr de participation
	
		requete = "Select count(idpersonne) as nbrinscrit  FROM personne;";
		preparedStatement = connexion.prepareStatement(requete);
		rs = preparedStatement.executeQuery();
		if (rs.next()) {
			nbrTotalInscrit = rs.getInt("nbrinscrit");
		}

		//**************Calcul le nbr d'inscrit
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

		return new IndicateurWayd(nbrTotalactivite, nbrTotalparticipation, nbrTotalInscrit, nbrTotalMessage, nbrTotalMessageByAct) {
		};
	}

	public void updateActivite(int idpersonne, String libelle, String titre,
			int idactivite,int nbrmax) throws SQLException {
	
		String requete = "UPDATE  activite set titre=?,libelle=?,nbmaxwayd=? WHERE idpersonne=? and idactivite=?";
				
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setString(1, titre);
		preparedStatement.setString(2, libelle);
		preparedStatement.setInt(3, nbrmax);
		preparedStatement.setInt(4, idpersonne);
		preparedStatement.setInt(5, idactivite);
		preparedStatement.execute();
		preparedStatement.close();
		
		// TODO Auto-generated method stub
		
	}

	public boolean isADejaRefuse(int idpersonne, int idactivite) throws SQLException {
		// Renvoi si id ami appartient aux ami de idpersonne

		String requete = " SELECT idpersonne from refusparticipation  where idpersonne=? and idactivite=?  ";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
	
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setInt(2, idactivite);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			rs.close();
			preparedStatement.close();
			return true;
		}
		rs.close();
		preparedStatement.close();
		return false;

	}
	
	public static boolean terminerActivite(int idActivite) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = CxoPool.getConnection();
			String requete = "UPDATE public.activite   SET       datefin=current_timestamp,datefin=current_timestamp WHERE idactivite=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idActivite);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			return true;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
			return false;

		} finally {

			CxoPool.close(connexion,preparedStatement);
		}

	}

	public ArrayList<Activite> getListActivites(Double malatitude,
			Double malongitude, int rayonmetre, int idtypeactivite_, String motcle,
			long debutActivite, long finActivite, int typeUser,
			int accessActivite,int commenceDans) throws SQLException {
		
		double coef = rayonmetre * 0.007 / 700;
		double latMin = malatitude - coef;
		double latMax = malatitude + coef;
		double longMin = malongitude - coef;
		double longMax = malongitude + coef;
		// System.out.println(latMin + "," + latMax + "coef:" + coef);
		Activite activite = null;
		ArrayList<Activite> retour = new ArrayList<Activite>();
		Calendar calendrier = Calendar.getInstance();
		calendrier.add(Calendar.MINUTE, commenceDans);

		
		String requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
				+ " activite.longitude,    personne.prenom,    personne.sexe,    personne.nom,    personne.idpersonne,personne.datenaissance,    "
				+ "personne.note,personne.nbravis as totalavis,personne.photo,"
				+ "activite.nbrwaydeur as nbrparticipant,1 as role,"
				+ "activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite,activite.nbmaxwayd,activite.typeacces,activite.typeuser   FROM personne,"
				+ "activite  WHERE personne.idpersonne = activite.idpersonne  and activite.idtypeactivite=?  "
				+ "and ? between datedebut and datefin"
				+ " and activite.latitude between ? and ?"
				+ " and activite.longitude between ? and ?"
				+ " and (UPPER(libelle) like UPPER(?) or UPPER(titre) like UPPER(?))  )  ORDER BY datedebut asc";
		
			if (idtypeactivite_!=0){
				
				
				
			}
			
			if (motcle!=null)
				if (!motcle.equals("")){
					
					
					
					
				}
			
			if (typeUser!=0){
				
				
				
			}
		
			
			if (accessActivite!=0){
				
				
				
			}
			
			if (commenceDans!=0){
				
				
				
			}
		
		
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idtypeactivite_);
		preparedStatement.setTimestamp(2, new java.sql.Timestamp(calendrier
				.getTime().getTime()));

		String test = "%" + motcle + "%";
		preparedStatement.setDouble(3, latMin);
		preparedStatement.setDouble(4, latMax);
		preparedStatement.setDouble(5, longMin);
		preparedStatement.setDouble(6, longMax);
		preparedStatement.setString(7, test);
		preparedStatement.setString(8, test);
		//

		ResultSet rs = preparedStatement.executeQuery();

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
			//Date datefinactivite = rs.getTimestamp("d_finactivite");

			if (prenom == null)
				prenom = "";
			String photo = rs.getString("photo");
			int role = rs.getInt("role");
			Date datenaissance = rs.getTimestamp("datenaissance");
			boolean archive = false;
			int totalavis = rs.getInt("totalavis");
			int typeAcces = rs.getInt("typeacces");
		
			activite = new Activite(id, titre, libelle, idorganisateur,
					datedebut, datefin, idtypeactivite, latitude, longitude,
					adresse, nom, prenom, photo, note, role, archive,
					totalavis, datenaissance, sexe, nbrparticipant, true, true,
					nbmaxwayd,typeUser,typeAcces);
			retour.add(activite);

		}

		rs.close();
		preparedStatement.close();
		return retour;

		
		
		
	}

	public void addActivitePro(String titre, String libelle,
			int idorganisateur, int idtypeactivite, String latitudestr,
			String longitudestr, String adresse, Long debut, Long fin) {
		
		// TODO Auto-generated method stub
		
	}


	

}
