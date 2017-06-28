package wayde.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import wayde.beandatabase.TypeActiviteDb;

public class TypeActiviteDAO {
	Connection connexion ;
	public TypeActiviteDAO(Connection connexion) {
		// TODO Auto-generated constructor stub
	this.connexion=connexion;
	}

	public  ArrayList<TypeActiviteDb> getListTypeActivite() throws SQLException {
		TypeActiviteDb typeactivitedb = null;
		ArrayList<TypeActiviteDb> retour=new ArrayList<TypeActiviteDb>();
		  
			Statement stmt = connexion.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM type_activite order by ordre asc;"
					 );
			while (rs.next()) {
				long id = rs.getLong("idtypeactivite");
				long idcategorie = rs.getLong("idcategorieactivite");
				String nom = rs.getString("nom");
				typeactivitedb = new TypeActiviteDb( id,  idcategorie,  nom);
				retour.add(typeactivitedb);
				
			}
			rs.close();
			stmt.close();
		
			return retour;

		
		
		
	}
}
