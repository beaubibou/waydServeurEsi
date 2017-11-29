package wayde.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import wayde.beandatabase.CategorieDb;



public class CategorieDAO {
	private static final Logger LOG = Logger.getLogger(CategorieDAO.class);

	Connection connexion;
	public CategorieDAO(Connection connexion) {
		// TODO Auto-generated constructor stub
	
	this.connexion=connexion;
	}

	

	public  ArrayList<CategorieDb> getListCategories() throws SQLException {
		CategorieDb categoriedb = null;
		ArrayList<CategorieDb> retour=new ArrayList<CategorieDb>();
				Statement stmt = connexion.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM categorie_activite;"
					 );
			while (rs.next()) {
				long id = rs.getLong("idcategorieactivite");
				String nom = rs.getString("nom");
				categoriedb = new CategorieDb(id,nom);
				retour.add(categoriedb);
				
			}
			rs.close();
			stmt.close();
		
			return retour;

		

	
		
		
	}

}
