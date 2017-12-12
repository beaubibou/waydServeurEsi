package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.NamingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.reflect.TypeLiteral;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import wayde.bean.CxoPool;
import website.metier.LogBean;
import website.metier.ProblemeBean;
import website.metier.TypeEtatLogs;
import website.metier.admin.EtatProbleme;
import website.metier.admin.FitreAdminLogs;

public class LogDAO {
	private static final Logger LOG = Logger.getLogger(LogDAO.class);
	private static final int MAX_LOG_SIZE = 100000;
	private static int nbrAeffacer=5000;

	public static ArrayList<LogBean> getListLog(FitreAdminLogs filtre,
			int page, int maxResult) {

		int offset = (maxResult) * page;

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<LogBean> retour = new ArrayList<LogBean>();

		int etatNiveauLog = filtre.getNiveau_log();
		DateTime debut = filtre.getDateCreationDebut();
		DateTime fin = filtre.getDateCreationFin();

		try {

			String requete = "";
			connexion = CxoPool.getConnection();

			switch (etatNiveauLog) {

			case TypeEtatLogs.TOUTES:
				requete = "SELECT  id,log_date,log_level, log_location,log_message  FROM log4j where"
						+ " log_date between ? and ? order by log_date desc  limit ?  offset ?";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setTimestamp(1, new java.sql.Timestamp(debut
						.toDate().getTime()));
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(fin
						.toDate().getTime()));
				preparedStatement.setInt(3, maxResult);
				preparedStatement.setInt(4, offset);
				break;

			case TypeEtatLogs.DEBUG:
				requete = "SELECT  id,log_date,log_level, log_location,log_message  FROM log4j where"
						+ " log_date between ? and ? and log_level='DEBUG' order by log_date desc  limit ?  offset ?";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setTimestamp(1, new java.sql.Timestamp(debut
						.toDate().getTime()));
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(fin
						.toDate().getTime()));
				preparedStatement.setInt(3, maxResult);
				preparedStatement.setInt(4, offset);
				break;

			case TypeEtatLogs.INFO:

				requete = "SELECT  id,log_date,log_level, log_location,log_message  FROM log4j where"
						+ " log_date between ? and ? and log_level='INFO' order by log_date desc  limit ?  offset ?";

				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setTimestamp(1, new java.sql.Timestamp(debut
						.toDate().getTime()));
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(fin
						.toDate().getTime()));
				preparedStatement.setInt(3, maxResult);
				preparedStatement.setInt(4, offset);
				break;

			case TypeEtatLogs.WARNING:
				requete = "SELECT  id,log_date,log_level, log_location,log_message  FROM log4j where"
						+ " log_date between ? and ? and log_level='WARN' order by log_date desc  limit ?  offset ?";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setTimestamp(1, new java.sql.Timestamp(debut
						.toDate().getTime()));
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(fin
						.toDate().getTime()));
				preparedStatement.setInt(3, maxResult);
				preparedStatement.setInt(4, offset);
				break;
			case TypeEtatLogs.ERROR:
		
				requete = "SELECT id,log_date,log_level, log_location,log_message  FROM log4j where"
						+ " log_date between ? and ? and log_level='ERROR' order by log_date desc  limit ?  offset ?";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setTimestamp(1, new java.sql.Timestamp(debut
						.toDate().getTime()));
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(fin
						.toDate().getTime()));
				preparedStatement.setInt(3, maxResult);
				preparedStatement.setInt(4, offset);
				break;

			}

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String log_location = rs.getString("log_location");
				String log_level = rs.getString("log_level");
				String log_message = rs.getString("log_message");
				Date date_log = rs.getTimestamp("log_date");
				
				retour.add(new LogBean(id,date_log,log_message,log_level,log_location));
			}
			
			
			return retour;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
			return retour;
		}
		finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
	}

	public static int getNbrLogs(){
		
		Connection connexion = null;

		PreparedStatement preparedStatement = null;
		
		int nbrLog = 0;
			try {
				connexion = CxoPool.getConnection();
				
				String requete = "SELECT count(id) as nbrLog from log4j";
				
				preparedStatement = connexion	.prepareStatement(requete);
			
				ResultSet rs = preparedStatement.executeQuery();
				
				
				if (rs.next()) {
					nbrLog=rs.getInt("nbrlog");
				}
				
			} catch (NamingException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			}
		
			CxoPool.close(connexion, preparedStatement);
			
			return nbrLog;



		
	}
	
	public static boolean supprimeNderniersLogd() {
		// TODO Auto-generated method stub

		
		if (getNbrLogs()<MAX_LOG_SIZE)
			return false;
			
		Connection connexion = null;

		PreparedStatement preparedStatement = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "delete from log4j where id in(SELECT id  FROM log4j order by id asc limit ? offset 0);";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, nbrAeffacer);
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();
			return true;

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			
		} finally {

			CxoPool.close(connexion, preparedStatement);

		}
		return false;

		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
	}
	
	

}
