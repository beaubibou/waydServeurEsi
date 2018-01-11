
package wayde.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import wayde.bean.CxoPool;
import wayde.beandatabase.AvisaDonnerDb;

public class AvisaDonnerDAO {
	private static final Logger LOG = Logger.getLogger(AvisaDonnerDAO.class);

	Connection connexion;
	
	public  AvisaDonnerDAO(Connection connexion){
		this.connexion=connexion;
	}
	public  ArrayList<AvisaDonnerDb> getListAvisaDonner(int idpersonne) throws Exception {
		
		AvisaDonnerDb avisadonnerdb = null;
		ArrayList<AvisaDonnerDb> retour = new ArrayList<AvisaDonnerDb>();

			String requete = " SELECT   personne.prenom,      personne.nom,    personne.photo,"
					+ "activite.idactivite,activite.datedebut,   activite.titre, noter.idpersonnenotee,noter.idnoter"
					+ "  FROM personne,activite,noter "
					+ "  WHERE personne.idpersonne = noter.idpersonnenotee  and activite.idactivite=noter.idactivite "
					+ "and activite.datefin<? and noter.idpersonnenotateur=? and noter.fait=false";

			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);
			
			preparedStatement.setTimestamp(1,
					new java.sql.Timestamp(new Date().getTime()));
			preparedStatement.setInt(2, idpersonne);
			ResultSet rs = preparedStatement.executeQuery();
		
			while (rs.next()) {
			
				int idactivite = rs.getInt("idactivite");
				int idnoter = rs.getInt("idnoter");
				int idpersonnenotee = rs.getInt("idpersonnenotee");
				String titre = rs.getString("titre");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String photo = rs.getString("photo");
				Date dateDebutactivite = rs.getTimestamp("datedebut");
				avisadonnerdb = new AvisaDonnerDb( idnoter,  idactivite,  idpersonnenotee,
						 nom,  prenom,  photo,  titre,dateDebutactivite);
				retour.add(avisadonnerdb);

			}
			CxoPool.close(preparedStatement, rs);
		
			return retour;

		

	

	}

}
