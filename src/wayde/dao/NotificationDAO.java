package wayde.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import wayde.bean.Notification;
import wayde.bean.Personne;
import wayde.beandatabase.AvisaDonnerDb;

public class NotificationDAO {
	Connection connexion;

	public NotificationDAO() {
		
	}

	public NotificationDAO(Connection connexion) {
		this.connexion = connexion;
	}

	public void addNotification(int iddestinataire, int idtype, int idactivite,
			int idpersonne) throws SQLException {

		String requete = "INSERT into notification(iddestinataire, idtype, d_creation, idactivite, idpersonne,lu)"
				+ " VALUES (?, ?, ?, ?,? ,false)";
		PreparedStatement preparedStatement = connexion.prepareStatement(
				requete, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, iddestinataire);
		preparedStatement.setInt(2, idtype);
		Date datecreation = Calendar.getInstance().getTime();
		preparedStatement.setTimestamp(3,
				new java.sql.Timestamp(datecreation.getTime()));
		preparedStatement.setInt(4, idactivite);
		preparedStatement.setInt(5, idpersonne);
		preparedStatement.execute();

	}
	
	
	public void addNotification(ArrayList<Personne> listpersonne, int idtype, int idactivite,
			int idpersonne) throws SQLException {

		PreparedStatement preparedStatement ;
		String requete;
		
		for (Personne personne:listpersonne)
		{
		if (personne.getId()!=idpersonne)	{// Ne met pas de notification à l'emtteur
		requete = "INSERT into notification(iddestinataire, idtype, d_creation, idactivite, idpersonne,lu)"
				+ " VALUES (?, ?, ?, ?,? ,false)";
			 preparedStatement = connexion.prepareStatement(
				requete, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, personne.getId());
		preparedStatement.setInt(2, idtype);
		Date datecreation = Calendar.getInstance().getTime();
		preparedStatement.setTimestamp(3,
				new java.sql.Timestamp(datecreation.getTime()));
		preparedStatement.setInt(4, idactivite);
		preparedStatement.setInt(5, idpersonne);
		preparedStatement.execute();
		
		
		
		}
		}
	}
	
	
	
	
	public void addNotifiRappelAnoter(int idDestinataire, int idactivite,
			int idpersonneAnoter,Date datecreation) throws SQLException {
	
		String requete = " INSERT into notification(iddestinataire, idtype, d_creation, idactivite, idpersonne,lu)"
				+ " VALUES (?, ?, ?, ?,? ,false)";
		
		PreparedStatement preparedStatement = connexion.prepareStatement(
				requete, Statement.RETURN_GENERATED_KEYS);

		preparedStatement.setInt(1, idDestinataire);
		preparedStatement.setInt(2, Notification.DonneAvis);
		preparedStatement.setTimestamp(3,
				new java.sql.Timestamp(new Date().getTime()));
		preparedStatement.setInt(4, idactivite);
		preparedStatement.setInt(5, idpersonneAnoter);
		preparedStatement.execute();

	}

	public void addNotificationFromAvis(
			 int idpersonne) throws SQLException {

		// ****************Recupere les avis à donner pour les activite à noter
		AvisaDonnerDAO avisadonnerdao = new AvisaDonnerDAO(connexion);
		ArrayList<AvisaDonnerDb> listavisadonner=avisadonnerdao.getListAvisaDonner(idpersonne);
			
		for (AvisaDonnerDb avisadonner : listavisadonner) {
			try {
				if (!isDemandeAvisExist(idpersonne, avisadonner.getIdpersonnenotee(), avisadonner.getIdactivite()))
				addNotifiRappelAnoter(idpersonne, avisadonner.getIdactivite(),
						avisadonner.getIdpersonnenotee(),avisadonner.getDateDebutActivite());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	// *******************Renvoi faux si la demande d'avis de notation n'existe pas dans la talbe des notification
	public boolean isDemandeAvisExist(int idDestinataire,int idPersonneAnoter,int idActivite) throws SQLException {

		String requete = "SELECT idactivite  FROM notification " +
		"where idpersonne=? and iddestinataire=? and idactivite=? and idtype=1;";
		PreparedStatement preparedStatement;
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idPersonneAnoter);
		preparedStatement.setInt(2, idDestinataire);
		preparedStatement.setInt(3, idActivite);
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
	public void removeNotificationAnoter(int idpersonne, int idpersonnenotee,
			int idactivite) {// Efface un message dans la table notificiation
		// utilisé aprés a voir déposé un avis

		String requete = "Delete from notification where iddestinataire=? and idtype=?"
				+ " and idactivite=? and idpersonne=?;";
			PreparedStatement preparedStatement;
		try {
			preparedStatement = connexion.prepareStatement(requete,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setInt(2, Notification.DonneAvis);
			preparedStatement.setInt(3, idactivite);
			preparedStatement.setInt(4, idpersonnenotee);
			preparedStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<Notification> getListNotification(int idpersonne)
			throws SQLException {

		ArrayList<Notification> retour = new ArrayList<Notification>();

		String requete = "select a.titre,p.prenom,n.iddestinataire,n.idtype,n.d_creation,n.idactivite,"
				+ "n.idpersonne,n.lu,n.idnotification from notification n left join personne p on n.idpersonne=p.idpersonne "
				+ "left join activite a on a.idactivite=n.idactivite  where "
				+ "  n.iddestinataire=? order by n.d_creation desc;";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		ResultSet rs = preparedStatement.executeQuery();

		while (rs.next()) {

			String titreactivite = rs.getString("titre");
			if (titreactivite == null)
				titreactivite = "";
			int idactivite = rs.getInt("idactivite");
			String pseudo = rs.getString("prenom");
			int idpersonneconcernee = rs.getInt("idpersonne");
			Date datecreation = rs.getTimestamp("d_creation");
			boolean lu = rs.getBoolean("lu");
			int idtype = rs.getInt("idtype");
			int idnotification = rs.getInt("idnotification");

			Notification notification = new Notification(idactivite, idtype,
					idpersonneconcernee, idpersonne, pseudo,
					titreactivite, datecreation, lu,idnotification);
			retour.add(notification);

		}
		rs.close();
		preparedStatement.close();

		return retour;

	}

	public ArrayList<Notification> getListNotificationAfter(int idpersonne,int idnotification)
			throws SQLException {

		ArrayList<Notification> retour = new ArrayList<Notification>();

		String requete = "select a.titre,p.nom,n.iddestinataire,n.idtype,n.d_creation,n.idactivite,"
				+ "n.idpersonne,n.lu,n.idnotification from notification n left join personne p on n.idpersonne=p.idpersonne "
				+ "left join activite a on a.idactivite=n.idactivite  where "
				+ "  n.iddestinataire=? and n.idnotification>? order by n.d_creation desc;";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setInt(2, idnotification);
		
		ResultSet rs = preparedStatement.executeQuery();

		while (rs.next()) {
//System.out.println("kkkkkkkkkkkkk");
			String titreactivite = rs.getString("titre");
			if (titreactivite == null)
				titreactivite = "";
			int idactivite = rs.getInt("idactivite");
			String nompersconcernee = rs.getString("nom");
			int idpersonneconcernee = rs.getInt("idpersonne");
			Date datecreation = rs.getTimestamp("d_creation");
			boolean lu = rs.getBoolean("lu");
			int idtype = rs.getInt("idtype");
			int idnotifications = rs.getInt("idnotification");

			Notification notification = new Notification(idactivite, idtype,
					idpersonneconcernee, idpersonne, nompersconcernee,
					titreactivite, datecreation, lu,idnotifications);
			retour.add(notification);

		}
		rs.close();
		preparedStatement.close();

		return retour;

	}

	public void litNotification(int idpersonne) throws SQLException {
		PreparedStatement preparedStatement = null;
		
		String requete = "UPDATE  notification set lu=true  "
				+ " WHERE iddestinataire=?";

		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.execute();
		preparedStatement.close();


	}

	public void addNotificationAjoutAmi(int iddestinataire, int idactivite,
			int idpersonne) throws SQLException {

		
		String requete = " INSERT into notification(iddestinataire, idtype, d_creation, idactivite, idpersonne,lu)"
				+ " VALUES (?, ?, ?, ?,? ,false)";
		PreparedStatement preparedStatement = connexion.prepareStatement(
				requete, Statement.RETURN_GENERATED_KEYS);

		preparedStatement.setInt(1, iddestinataire);
		preparedStatement.setInt(2, Notification.NouvelAmi);
		Date datecreation = Calendar.getInstance().getTime();
		preparedStatement.setTimestamp(3,
				new java.sql.Timestamp(datecreation.getTime()));
		preparedStatement.setInt(4, idactivite);
		preparedStatement.setInt(5, idpersonne);
		preparedStatement.execute();

		requete = " INSERT into notification(iddestinataire, idtype, d_creation, idactivite, idpersonne,lu)"
				+ " VALUES (?, ?, ?, ?,? ,false)";
		preparedStatement = connexion.prepareStatement(requete,
				Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setInt(2, Notification.NouvelAmi);
		datecreation = Calendar.getInstance().getTime();
		preparedStatement.setTimestamp(3,
				new java.sql.Timestamp(datecreation.getTime()));
		preparedStatement.setInt(4, idactivite);
		preparedStatement.setInt(5, iddestinataire);
		preparedStatement.execute();

	}

	public void effaceNotification(int iddestinataire, int idnotification) throws SQLException {
			PreparedStatement preparedStatement = null;
	//	System.out.println("Effacement d'une activite");
		String requete = "DELETE FROM notification where ( idnotification=? and iddestinataire=? );";
		preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idnotification);
		preparedStatement.setInt(2, iddestinataire);
		preparedStatement.execute();
		preparedStatement.close();

	
	
	}

	public void addNotificationSupAmi(int idpersonne, int idami) throws SQLException {
	//	System.out.println("*****Ajout notification supprime ami");

		String requete = " INSERT into notification(iddestinataire, idtype, d_creation, idactivite, idpersonne,lu)"
				+ " VALUES (?, ?, ?, ?,? ,false)";
		PreparedStatement preparedStatement = connexion.prepareStatement(
				requete, Statement.RETURN_GENERATED_KEYS);

		preparedStatement.setInt(1, idami);
		preparedStatement.setInt(2, Notification.SUPPRIME_AMI);
		Date datecreation = Calendar.getInstance().getTime();
		preparedStatement.setTimestamp(3,
				new java.sql.Timestamp(datecreation.getTime()));
		preparedStatement.setInt(4, 0);
		preparedStatement.setInt(5, idpersonne);
		preparedStatement.execute();

		
		
	}

	public void LitNotification(int idpersonne, int idnotification) throws SQLException {
			PreparedStatement preparedStatement = null;
		//	System.out.println("Lecture d'une notification d'une activite");
			String requete = "UPDATE  notification set lu=true  "
					+ " WHERE idnotification=? and iddestinataire=? ";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idnotification);
			preparedStatement.setInt(2, idpersonne);
			preparedStatement.execute();
			preparedStatement.close();


	}

}
