package wayde.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import wayd.ws.WBservices;
import wayde.bean.Activite;
import wayde.bean.Participation;
import wayde.bean.Personne;


public class ParticipationDAO {

	final int ATTENTE_REPONSE=0;
	final int ACCEPT_AMI=1;
	final int REFUSE_AMI=0;
	private static final Logger LOG = Logger.getLogger(ParticipationDAO.class);

	Connection connexion;

	public ParticipationDAO(Connection connexion) {
	this.connexion=connexion;
		// TODO Auto-generated constructor stub
	}

	public  void addParticipation (Participation participation)
			throws SQLException {

		Date datecreation = Calendar.getInstance().getTime();

		String requete = "INSERT INTO public.participer(role, idpersonne, idactivite, datecreation)  VALUES (?, ?, ?, ?);";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setInt(1, 0);
		preparedStatement.setInt(2, (int) participation.getIddemandeur());
		preparedStatement.setInt(3, (int) participation.getIdactivite());
		preparedStatement.setTimestamp(4,
				new java.sql.Timestamp(datecreation.getTime()));
		preparedStatement.execute();
		preparedStatement.close();
		
	}

	public void addNotation(Participation participation) throws SQLException{
		
		
		String requete = "INSERT INTO public.noter( idpersonnenotee, idactivite, idpersonnenotateur,fait) "
				+ " VALUES (?, ?, ?,?);";

		PreparedStatement preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, (int) participation.getIddemandeur());
		preparedStatement.setInt(2, (int) participation.getIdactivite());
		preparedStatement.setInt(3, (int) participation.getIdorganisateur());
		preparedStatement.setBoolean(4, false);
		preparedStatement.execute();
		preparedStatement.close();

		requete = "INSERT INTO public.noter( idpersonnenotee, idactivite, idpersonnenotateur,fait) "
				+ " VALUES (?, ?, ?,?);";

		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, (int) participation.getIdorganisateur());
		preparedStatement.setInt(2, (int) participation.getIdactivite());
		preparedStatement.setInt(3, (int) participation.getIddemandeur());
		preparedStatement.setBoolean(4, false);
		preparedStatement.execute();
		preparedStatement.close();
		
	}
	
	public void addDemandeAmi(Participation participation) throws SQLException{
		
		
		// PREPARE LE DEMANDE D'AMI
		String requete = "INSERT INTO demandeami(idorganisateur, idparticipant, reponse, nbrreponse,  idactivite) "
				+ " VALUES (?, ?,?,?,?);";
		
		PreparedStatement preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1,  participation.getIdorganisateur());
		preparedStatement.setInt(2,  participation.getIddemandeur());
		preparedStatement.setInt(3, ATTENTE_REPONSE);
		preparedStatement.setInt(4, ATTENTE_REPONSE);
		preparedStatement.setInt(5, (int) participation.getIdactivite());
		preparedStatement.execute();
		preparedStatement.close();
		
		
	}
	
	public  void RemoveParticipation(int idpersonne, int idactivite,int idorganisateur)
			throws SQLException {
		
	//	System.out.println("Effacement d'un participation");
		String requete = "DELETE FROM public.participer where ( idactivite=? and idpersonne=? and role=0);";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idactivite);
		preparedStatement.setInt(2, idpersonne);

		preparedStatement.execute();
		preparedStatement.close();

		requete = "DELETE FROM public.noter where ( idactivite=? and idpersonnenotee=?);";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idactivite);
		preparedStatement.setInt(2, idpersonne);

		preparedStatement.execute();
		preparedStatement.close();

		requete = "DELETE FROM public.noter where ( idactivite=? and idpersonnenotateur=?);";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idactivite);
		preparedStatement.setInt(2, idpersonne);
		
		preparedStatement.execute();
		preparedStatement.close();
		
		requete = "DELETE FROM demandeami where ( idactivite=? and idparticipant=? and idorganisateur=?);";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idactivite);
		preparedStatement.setInt(2, idpersonne);
		preparedStatement.setInt(3, idorganisateur);
		preparedStatement.execute();
		preparedStatement.close();

	

	}

	public  int getOrganisteur(int idactivite) throws Exception {
		Statement stmt = connexion.createStatement();
		//System.out.println("Cherche l'organisateur de" + idactivite);
		String requete = " SELECT idpersonne from activite where idactivite=?";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idactivite);
		ResultSet rs = preparedStatement.executeQuery();
		stmt.close();
	
		if (rs.next()) {
			return rs.getInt("idpersonne");
		}
		throw new Exception(
				"Methode: getOrganisateur - Pas d'organisateur pour l activite "
						+ idactivite);

	}

	

	public  ArrayList<Personne> getListPartipantActivite(int idactivite) throws SQLException  {

		ArrayList<Personne> retour = new ArrayList<Personne>();
		Statement stmt = connexion.createStatement();
		String requete = " SELECT personne.notification,personne.idpersonne,personne.gcm from participer,personne where idactivite=? 	and personne.idpersonne=participer.idpersonne  union "
				+ "SELECT personne.notification,personne.idpersonne,personne.gcm from activite,personne where idactivite=?  and personne.idpersonne=activite.idpersonne";
		PreparedStatement preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idactivite);
		preparedStatement.setInt(2, idactivite);
		ResultSet rs = preparedStatement.executeQuery();
		stmt.close();
		
		while (rs.next()) {
			int idpersonne=rs.getInt("idpersonne");
			String gcm=rs.getString("gcm");
			boolean notification=rs.getBoolean("notification");
			retour.add(new Personne(gcm, idpersonne,notification));
		}
		
	//	System.out.println("Cherche les participants à l'activité en "
	//			+ (System.currentTimeMillis() - debut) + " ms");
		
		return retour;

	}

	public  int  getNombreParticipant(int idactivite,int personne) throws Exception {

		Statement stmt = connexion.createStatement();
	//	System.out.println("Cherche touts participants à l'activité"+ idactivite);
		String requete = "select count (idpersonne) as nbrparticipant from participer where idpersonne=? and idactivite=?";
		PreparedStatement preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, personne);
		preparedStatement.setInt(2, idactivite);
		ResultSet rs = preparedStatement.executeQuery();
		stmt.close();
		
		if (rs.next()) {
			return rs.getInt("nbrparticipant")+1;
		}
		return 0;

	}

	
	
	public  ArrayList<Personne> getListPartipantActiviteExpect(int idactivite,int idexpect) throws SQLException  {

		ArrayList<Personne> retour = new ArrayList<Personne>();
		Statement stmt = connexion.createStatement();
	//	System.out.println("Cherche touts participants à l'activité"+ idactivite);
		String requete = " SELECT personne.notification,personne.idpersonne,personne.gcm from participer,personne where idactivite=?"
				+ "	and personne.idpersonne=participer.idpersonne and  personne.idpersonne!=? union "
				+ "SELECT personne.notification,personne.idpersonne,personne.gcm from activite,personne where idactivite=? "
				+ "and personne.idpersonne=activite.idpersonne and personne.idpersonne!=?";
		PreparedStatement preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idactivite);
		preparedStatement.setInt(2, idexpect);
		preparedStatement.setInt(3, idactivite);
		preparedStatement.setInt(4, idexpect);
		ResultSet rs = preparedStatement.executeQuery();
		stmt.close();
		
		while (rs.next()) {
			int idpersonne=rs.getInt("idpersonne");
			String gcm=rs.getString("gcm");
			boolean notification=rs.getBoolean("notification");
			retour.add(new Personne(gcm, idpersonne,notification));
		}
		return retour;

	}

	public boolean isInListParticipant(ArrayList<Personne> listparticipant,
			int idemetteur) {
		// TODO Auto-generated method stub
	for (Personne personne:listparticipant){
		
		if (personne.getId()==idemetteur)return true;
	}
		
	
		return false;
		
	}
	
	public int  getNbrParticipation(int idpersonne)
			throws SQLException {
		int nbr=0;
	
		String requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
				+ " activite.longitude,    personne.prenom, personne.datenaissance  ,   personne.sexe,    personne.nom,    personne.idpersonne, "
				+ "personne.note,0 as role,"
				+ "personne.nbravis as totalavis,"
				+ "activite.nbrwaydeur as nbrparticipant,    personne.photo, personne.photo,"
				+ "activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite, activite.nbmaxwayd  FROM personne,"
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
			nbr=nbr+1;
		}

		preparedStatement.close();

		// Cherche dans les activite

		return nbr;
	}
	
}
