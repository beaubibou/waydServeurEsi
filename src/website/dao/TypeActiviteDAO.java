package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import wayde.bean.CxoPool;
import website.metier.TypeActiviteBean;

public class TypeActiviteDAO {
	private static final Logger LOG = Logger.getLogger(TypeActiviteDAO.class);

	public static ArrayList<TypeActiviteBean> getListTypeActivite() {

		long debut = System.currentTimeMillis();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<TypeActiviteBean> retour = new ArrayList<>();

		try {
			connexion = CxoPool.getConnection();

			String requete = "SELECT idtypeactivite,nom as libelle FROM type_activite order by ordre asc";
			preparedStatement = connexion.prepareStatement(requete);
		
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("idtypeactivite");
				String libelle = rs.getString("libelle");
				retour.add(new TypeActiviteBean(id,libelle));
			}

			LogDAO.LOG_DUREE("getListTypeActivite", debut);
			return retour;

		} catch (SQLException | NamingException e) {
		
			LOG.error( ExceptionUtils.getStackTrace(e));
			return retour;
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
	}

}
