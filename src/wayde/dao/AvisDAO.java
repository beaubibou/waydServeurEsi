package wayde.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import wayde.bean.Avis;


public class AvisDAO {
	Connection connexion;

	public AvisDAO(Connection connexion) {
		this.connexion = connexion;

	}

	public void addAvis(Avis avis) throws SQLException {

		String requete = "UPDATE public.noter   SET datenotation=?, note=?, libelle=?, titre=?,  fait=? WHERE idnoter=?;";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setTimestamp(1,
				new java.sql.Timestamp(new Date().getTime()));
		preparedStatement.setDouble(2, avis.getNote());
		preparedStatement.setString(3, avis.getLibelle());
		preparedStatement.setString(4, avis.getTitre());
		preparedStatement.setBoolean(5, true);
		preparedStatement.setInt(6, avis.getIdnoter());
		preparedStatement.execute();
		preparedStatement.close();

	}

	public void addAvis(int idpersonnenotee, int idactivite, String titre,
			String libelle, double note) throws SQLException {

		String requete = "UPDATE public.noter   SET datenotation=?, note=?, libelle=?, titre=?,  fait=? "
				+ "WHERE idpersonnenotee=? and idactivite=?;";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setTimestamp(1,
				new java.sql.Timestamp(new Date().getTime()));
		if (libelle.equals(""))libelle=null;
		if (titre.equals(""))titre=null;
		
		preparedStatement.setDouble(2, note);
		preparedStatement.setString(3, libelle);
		preparedStatement.setString(4, titre);
		preparedStatement.setBoolean(5, true);
		preparedStatement.setInt(6, idpersonnenotee);
		preparedStatement.setInt(7, idactivite);
		preparedStatement.execute();
		preparedStatement.close();
	
	}

	public  Avis getAvisById(int idnoter) {
		Avis avis = null;
		try {

			String requete = " SELECT  activite.titre as titreactivite, personne.prenom,      personne.nom,    personne.photo,"
					+ "noter.idactivite,  noter.idpersonnenotateur,noter.idpersonnenotee,noter.idnoter,noter.titre,"
					+ "noter.libelle,noter.note,noter.datenotation"
					+ "  FROM personne,noter,activite "
					+ "  WHERE personne.idpersonne = noter.idpersonnenotateur  "
					+ "and  noter.idnoter=? and activite.idactivite=noter.idactivite";

			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);

			preparedStatement.setInt(1, idnoter);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int idactivite = rs.getInt("idactivite");
				String avistitre = rs.getString("titre");
				String titreactivite = rs.getString("titreactivite");
				String avislibelle = rs.getString("libelle");
				int idpersonnenotateur = rs.getInt("idpersonnenotateur");
				int idpersonnenotee = rs.getInt("idpersonnenotee");
				Date datenotation = rs.getDate("datenotation");
				double note = rs.getInt("note");
				String nomnotateur = rs.getString("nom");
				String prenomnotateur = rs.getString("prenom");
				String photonotateur = rs.getString("photo");
			
			//	System.out.println("avistire"+avistitre);
			
				
				avis = new Avis(idnoter, idactivite, idpersonnenotee,
						idpersonnenotateur, avistitre, avislibelle,
						datenotation, note, nomnotateur, prenomnotateur,
						photonotateur,titreactivite);
				return avis;

			}
			rs.close();
			preparedStatement.close();
		
			return null;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return null;

	}

	public  int getidPersonneNote(int idnoter) throws Exception {
		Statement stmt = connexion.createStatement();
		//System.out.println("Cherche la personne note" + idnoter);
		String requete = " SELECT idpersonnenotee from noter where idnoter=?";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setInt(1, idnoter);
		ResultSet rs = preparedStatement.executeQuery();
		stmt.close();
		
		if (rs.next()) {
			return rs.getInt("idpersonnenotee");
		}
		throw new Exception(
				"Methode AvisDAO.getidpersonneNote Pas de personne not�e pour "
						+ idnoter);

	}

	public  ArrayList<Avis> getListAvis(int idpersonnenotee) {
		Avis avis = null;
		ArrayList<Avis> retour = new ArrayList<Avis>();

		try {

			String requete = " SELECT   activite.titre as titreactivite,personne.prenom,      personne.nom,    personne.photo,"
					+ "noter.idactivite,  noter.idpersonnenotateur,noter.idpersonnenotee,noter.idnoter,noter.titre,"
					+ "noter.libelle,noter.note,noter.datenotation"
					+ "  FROM personne,noter,activite "
					+ "  WHERE personne.idpersonne = noter.idpersonnenotateur  "
					+ "and  noter.idpersonnenotee=? and noter.fait=true and noter.idactivite=activite.idactivite order by noter.datenotation desc";

			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);

			preparedStatement.setInt(1, idpersonnenotee);
			ResultSet rs = preparedStatement.executeQuery();

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
				avis = new Avis(idnoter, idactivite, idpersonnenotee,
						idpersonnenotateur, avistitre, avislibelle,
						datenotation, note, nomnotateur, prenomnotateur,
						photonotateur,titreactivite);
				retour.add(avis);

			}
			rs.close();
			preparedStatement.close();
			return retour;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return null;
	}

	public Avis getDetailAvis(int idactivite, int idnotateur,
			int idpersonnenotee) throws SQLException {

	//	System.out.println("Rechereche idactivite" + idactivite + " nottaeur "
			//	+ idnotateur + " idpersonne notee " + idpersonnenotee);
		Avis avis = null;

		String requete = " SELECT   activite.titre as titreactivite,personne.prenom,      personne.nom,    personne.photo,"
					+ "noter.idactivite,  noter.idpersonnenotateur,noter.idpersonnenotee,noter.idnoter,noter.titre,"
					+ "noter.libelle,noter.note,noter.datenotation"
					+ "  FROM personne,noter,activite "
					+ "  WHERE personne.idpersonne = noter.idpersonnenotateur  "
					+ "and  noter.idactivite=? and idpersonnenotateur=? and  noter.idpersonnenotee=? "
					+ "and activite.idactivite=noter.idactivite";

			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);

			preparedStatement.setInt(1, idactivite);
			preparedStatement.setInt(2, idnotateur);
			preparedStatement.setInt(3, idpersonnenotee);
			ResultSet rs = preparedStatement.executeQuery();
		//	System.out.println("klk");
			while (rs.next()) {
				String avistitre = rs.getString("titre");
				if (avistitre.equals("''"))avistitre="l";
				String avislibelle = rs.getString("libelle");
				if (avislibelle.equals("' '"))avislibelle="l";
				String titreactivite = rs.getString("titreactivite");
				int idpersonnenotateur = rs.getInt("idpersonnenotateur");
				Date datenotation = rs.getDate("datenotation");
				double note = rs.getDouble("note");
				int idnoter = rs.getInt("idnoter");
				String nomnotateur = rs.getString("nom");
				String prenomnotateur = rs.getString("prenom");
				String photonotateur = rs.getString("photo");
				avis = new Avis(idnoter, idactivite, idpersonnenotee,
						idpersonnenotateur, avistitre, avislibelle,
						datenotation, note, nomnotateur, prenomnotateur,
						photonotateur,titreactivite);
				rs.close();
				preparedStatement.close();
				
				
				return avis;

			}
			
			return null;
	

	}

	public void updateDemande(int idparticipant, int idorganisateur,
			int idactivite, boolean ami) throws SQLException {

		// LA demande d'almi est cr�e lors de la demande de participation
		// A chaque avis note le nbr est augment�
		// Si la variable ami est � true la reponse est augment� de 1

		String requete = "UPDATE demandeami   SET nbrreponse=nbrreponse+1"
				+ " WHERE ((idparticipant=? and idorganisateur=?) or (idparticipant=? and idorganisateur=?))and idactivite=? ";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setInt(1, idparticipant);
		preparedStatement.setInt(2, idorganisateur);
		preparedStatement.setInt(3, idorganisateur);
		preparedStatement.setInt(4, idparticipant);
		preparedStatement.setInt(5, idactivite);
		preparedStatement.execute();

		if (ami) {
			requete = "UPDATE demandeami   SET reponse=reponse+1"
					+ " WHERE ((idparticipant=? and idorganisateur=?) or (idparticipant=? and idorganisateur=?))and idactivite=? ";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idparticipant);
			preparedStatement.setInt(2, idorganisateur);
			preparedStatement.setInt(3, idorganisateur);
			preparedStatement.setInt(4, idparticipant);
			preparedStatement.setInt(5, idactivite);
			preparedStatement.execute();
		}

		requete = "delete from demandeami where nbrreponse>reponse";// <un a dit non
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.execute();
		preparedStatement.close();

		// TODO Auto-generated method stub

	}

	public boolean gestionAmi(int idparticipant, int idorganisateur,
			int idactivite) throws SQLException {
		
		boolean ajoutami = false;
	
		String requete = "select idparticipant from demandeami  where "
				+ "  (idparticipant=? and idorganisateur=? and idactivite=? and nbrreponse=2  )"
				+ " or (idparticipant=? and idorganisateur=? and idactivite=? and nbrreponse=2) ";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setInt(1, idparticipant);
		preparedStatement.setInt(2, idorganisateur);
		preparedStatement.setInt(3, idactivite);
		preparedStatement.setInt(4, idorganisateur);
		preparedStatement.setInt(5, idparticipant);
		preparedStatement.setInt(6, idactivite);
		ResultSet rs = preparedStatement.executeQuery();

		if (rs.next()) {// Si un enregistrement avec 2 avis positif � �t� trouv�
						// on ajoute les amis dans la table

			if (!isAmiFrom(idparticipant,idorganisateur)){
			requete = "INSERT INTO ami(idpersonne, idami,d_creation)  VALUES (?, ?, ?);";
		    
	//		System.out.println("Ajotue ami"+idparticipant+" "+idorganisateur);
			preparedStatement = connexion.prepareStatement(requete,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setInt(1, idparticipant);
			preparedStatement.setInt(2, idorganisateur);
			preparedStatement.setTimestamp(3,
					new java.sql.Timestamp(new Date().getTime()));
			ajoutami = true;
			preparedStatement.execute();
			}
			
			if (!isAmiFrom(idorganisateur, idparticipant)){
				//System.out.println("Ajotue ami"+idorganisateur+" "+idparticipant);
			requete =  " INSERT INTO ami(idpersonne, idami,d_creation)  VALUES (?, ?, ?);";
		
			preparedStatement.setInt(1, idorganisateur);
			preparedStatement.setInt(2, idparticipant);
			preparedStatement.setTimestamp(3,
					new java.sql.Timestamp(new Date().getTime()));
			preparedStatement.execute();
			ajoutami = true;
			}
		}

		// Efface la demande d'ami
		requete = "delete  from demandeami  where "
				+ "  (idparticipant=? and idorganisateur=? and idactivite=? and nbrreponse=2  )"
				+ " or (idparticipant=? and idorganisateur=? and idactivite=? and nbrreponse=2) ";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idparticipant);
		preparedStatement.setInt(2, idorganisateur);
		preparedStatement.setInt(3, idactivite);
		preparedStatement.setInt(4, idorganisateur);
		preparedStatement.setInt(5, idparticipant);
		preparedStatement.setInt(6, idactivite);
		preparedStatement.execute();
		// TODO Auto-generated method stub

		return ajoutami;
	}
	
	public boolean isAmiFrom(int idpersonne, int idami) throws SQLException {
		// Renvoi si id ami appartient aux ami de idpersonne

		String requete = " SELECT idami from ami  where idpersonne=? and idami=?  ";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
	
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setInt(2, idami);
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

}
