package wayde.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import wayde.bean.Avis;
import wayde.bean.CxoPool;

public class AvisDAO {

	private static final Logger LOG = Logger.getLogger(AvisDAO.class);

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

	public void addAvis(int idpersonnenotee, int idnotateur, int idactivite,
			String titre, String libelle, double note) throws SQLException {

		String requete = "UPDATE public.noter   SET datenotation=?, note=?, libelle=?, titre=?,  fait=? "
				+ "WHERE idpersonnenotee=? and idactivite=? and idpersonnenotateur=? and fait=false;";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setTimestamp(1,
				new java.sql.Timestamp(new Date().getTime()));

		if (libelle.equals(""))
			libelle = null;
		if (titre.equals(""))
			titre = null;

		preparedStatement.setDouble(2, note);
		preparedStatement.setString(3, libelle);
		preparedStatement.setString(4, titre);
		preparedStatement.setBoolean(5, true);
		preparedStatement.setInt(6, idpersonnenotee);
		preparedStatement.setInt(7, idactivite);
		preparedStatement.setInt(8, idnotateur);

		preparedStatement.execute();
		preparedStatement.close();

	}

	public Avis getAvisById(int idnoter) throws Exception {
		Avis avis = null;

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

		if (rs.next()) {
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

			avis = new Avis(idnoter, idactivite, idpersonnenotee,
					idpersonnenotateur, avistitre, avislibelle, datenotation,
					note, nomnotateur, prenomnotateur, photonotateur,
					titreactivite);

		}
		CxoPool.close(preparedStatement, rs);

		return avis;

	}

	public ArrayList<Avis> getListAvis(int idpersonnenotee) throws Exception {
		Avis avis = null;
		ArrayList<Avis> retour = new ArrayList<Avis>();

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
					idpersonnenotateur, avistitre, avislibelle, datenotation,
					note, nomnotateur, prenomnotateur, photonotateur,
					titreactivite);
			retour.add(avis);

		}
		CxoPool.close(preparedStatement, rs);

		return retour;

	}

	public Avis getDetailAvis(int idactivite, int idnotateur,
			int idpersonnenotee) throws SQLException {

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
		if (rs.next()) {
			String avistitre = rs.getString("titre");
			if (avistitre.equals("''"))
				avistitre = "l";
			String avislibelle = rs.getString("libelle");
			if (avislibelle.equals("' '"))
				avislibelle = "l";
			String titreactivite = rs.getString("titreactivite");
			int idpersonnenotateur = rs.getInt("idpersonnenotateur");
			Date datenotation = rs.getDate("datenotation");
			double note = rs.getDouble("note");
			int idnoter = rs.getInt("idnoter");
			String nomnotateur = rs.getString("nom");
			String prenomnotateur = rs.getString("prenom");
			String photonotateur = rs.getString("photo");

			avis = new Avis(idnoter, idactivite, idpersonnenotee,
					idpersonnenotateur, avistitre, avislibelle, datenotation,
					note, nomnotateur, prenomnotateur, photonotateur,
					titreactivite);

		}

		CxoPool.close(preparedStatement, rs);
		return avis;

	}

	public void updateDemande(int idparticipant, int idorganisateur,
			int idactivite, boolean ami) throws Exception {

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
		preparedStatement.close();
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
			preparedStatement.close();

		}

		requete = "delete from demandeami where nbrreponse>reponse";// <un a dit
																	// non
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.execute();
		preparedStatement.close();

		

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
			CxoPool.close(preparedStatement, rs);
			if (!isAmiFrom(idparticipant, idorganisateur)) {
				requete = "INSERT INTO ami(idpersonne, idami,d_creation)  VALUES (?, ?, ?);";

				preparedStatement = connexion.prepareStatement(requete,
						Statement.RETURN_GENERATED_KEYS);

				preparedStatement.setInt(1, idparticipant);
				preparedStatement.setInt(2, idorganisateur);
				preparedStatement.setTimestamp(3, new java.sql.Timestamp(
						new Date().getTime()));
				ajoutami = true;
				preparedStatement.execute();
				preparedStatement.close();
			}

			if (!isAmiFrom(idorganisateur, idparticipant)) {

				preparedStatement = connexion.prepareStatement(requete,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setInt(1, idorganisateur);
				preparedStatement.setInt(2, idparticipant);
				preparedStatement.setTimestamp(3, new java.sql.Timestamp(
						new Date().getTime()));
				preparedStatement.execute();
				preparedStatement.close();
				ajoutami = true;
			}
		} else {
			CxoPool.close(preparedStatement, rs);

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
		

		CxoPool.close(preparedStatement, rs);

		return ajoutami;
	}

	public boolean isAmiFrom(int idpersonne, int idami) throws SQLException {
		// Renvoi si id ami appartient aux ami de idpersonne
		boolean retour = false;
		String requete = " SELECT idami from ami  where idpersonne=? and idami=?  ";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setInt(2, idami);
		ResultSet rs = preparedStatement.executeQuery();
		
		if (rs.next()) {
			retour = true;
		}
		
		CxoPool.close(preparedStatement, rs);
		return retour;

	}

	public boolean isDoubleAvis(int idpersonne, int idnotateur, int idactivite)
			throws SQLException {

		// Renvoi vrai si les personne se sont notées mutuellemnt

		String requete = " SELECT idnoter from noter  where ( idpersonnenotee=? and idpersonnenotateur=? and idactivite=? and fait=true) "
				+ "or  (idpersonnenotateur =? and idpersonnenotee=? and idactivite=? and fait=true)";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setInt(2, idnotateur);
		preparedStatement.setInt(3, idactivite);

		preparedStatement.setInt(4, idpersonne);
		preparedStatement.setInt(5, idnotateur);
		preparedStatement.setInt(6, idactivite);

		ResultSet rs = preparedStatement.executeQuery();
		int retour = 0;
		while (rs.next()) {
			retour++;
		}

		CxoPool.close(preparedStatement, rs);

		if (retour == 2)
			return true;

		return false;

	}

}
