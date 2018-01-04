package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.NamingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.TypeLiteral;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.joda.time.DateTime;

import sun.rmi.runtime.Log;
import wayde.bean.CxoPool;
import website.metier.CountLogInfo;
import website.metier.LogBean;
import website.metier.ProblemeBean;
import website.metier.TypeEtatLogPerf;
import website.metier.TypeEtatLogs;
import website.metier.admin.EtatProbleme;
import website.metier.admin.FitreAdminLogs;

public class LogDAO {
	private static final Logger LOG = Logger.getLogger(LogDAO.class);
	public static int MAX_LOG_SIZE = 400000;
	public static int NBR_LOG_A_EFFACER = 10000;
	public static int TPS_WARNING_REQUETE = 20;
	public static int TPS_ECHATILLONNAGE = 30;
	public static final String MESSAGE_PAS_AUTHENTIFIE = "Accés réfusé";
	public static long TPS_CALCUL_PERFOMENCE = 60;

	public static int ETAT_PERF = TypeEtatLogPerf.ACTIVE;

	public static int getETAT_PERF() {
		return ETAT_PERF;
	}

	public static void setETAT_PERF(int eTAT_PERF) {
		ETAT_PERF = eTAT_PERF;
	}

	public static ArrayList<LogBean> getListLog(FitreAdminLogs filtre,
			int page, int maxResult) {

		long debutlog = System.currentTimeMillis();

		int offset = (maxResult) * page;

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<LogBean> retour = new ArrayList<LogBean>();

		int etatNiveauLog = filtre.getNiveau_log();
		DateTime debut = filtre.getDateCreationDebut();
		DateTime fin = filtre.getDateCreationFin();
		String methode = filtre.getMethode();

		try {

			String requete = "";
			connexion = CxoPool.getConnection();

			switch (etatNiveauLog) {

			case TypeEtatLogs.TOUTES:
				requete = "SELECT  duree,id,log_date,log_level, log_location,log_message  FROM log4j where"
						+ " log_date between ? and ? ";

				break;

			case TypeEtatLogs.DEBUG:
				requete = "SELECT  duree,id,log_date,log_level, log_location,log_message  FROM log4j where"
						+ " log_date between ? and ? and log_level='DEBUG' ";

				break;

			case TypeEtatLogs.INFO:

				requete = "SELECT  duree,id,log_date,log_level, log_location,log_message  FROM log4j where"
						+ " log_date between ? and ? and log_level='INFO' ";

				break;

			case TypeEtatLogs.WARNING:
				requete = "SELECT  duree,id,log_date,log_level, log_location,log_message  FROM log4j where"
						+ " log_date between ? and ? and log_level='WARN' ";

				break;
			case TypeEtatLogs.ERROR:

				requete = "SELECT duree,id,log_date,log_level, log_location,log_message  FROM log4j where"
						+ " log_date between ? and ? and log_level='ERROR' ";

				break;

			}

			if (!methode.equals("")) {

				requete = requete + " and LOWER(log_message) like ?";

			}
			requete = requete + " order by log_date desc  limit ?  offset ?";

			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setTimestamp(1, new java.sql.Timestamp(debut
					.toDate().getTime()));
			preparedStatement.setTimestamp(2, new java.sql.Timestamp(fin
					.toDate().getTime()));

			int index = 3;

			if (!methode.equals("")) {
				preparedStatement.setString(index, methode.toLowerCase());
				index++;
			}

			preparedStatement.setInt(index, maxResult);
			index++;
			preparedStatement.setInt(index, offset);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				long duree = rs.getLong("duree");
				String log_location = rs.getString("log_location");
				String log_level = rs.getString("log_level");
				String log_message = rs.getString("log_message");
				Date date_log = rs.getTimestamp("log_date");

				retour.add(new LogBean(id, date_log, log_message, log_level,
						log_location, duree));
			}

			LogDAO.LOG_DUREE("getListLog", debutlog);

			return retour;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			return retour;
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
	}

	public static ArrayList<LogBean> getListLogDetail(String date,
			String logLevel) {

		long debutlog = System.currentTimeMillis();

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<LogBean> retour = new ArrayList<LogBean>();

		try {

			String requete = "";
			connexion = CxoPool.getConnection();

			requete = "SELECT  duree,id,log_date,log_level, log_location,log_message"
					+ "  FROM log4j where to_char(log_date,'dd/mm/yyyy')=? and log_level=?";

			requete = requete + " order by log_date desc";

			preparedStatement = connexion.prepareStatement(requete);

			preparedStatement.setString(1, date);
			preparedStatement.setString(2, logLevel);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				long duree = rs.getLong("duree");
				String log_location = rs.getString("log_location");
				String log_level = rs.getString("log_level");
				String log_message = rs.getString("log_message");
				Date date_log = rs.getTimestamp("log_date");
				retour.add(new LogBean(id, date_log, log_message, log_level,
						log_location, duree));
			}
			LogDAO.LOG_DUREE("getListLogDetail", debutlog);
			return retour;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			return retour;
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
	}

	public static String getTailleBase() {

		long debut = System.currentTimeMillis();
		Connection connexion = null;

		PreparedStatement preparedStatement = null;

		String taille = "";
		try {
			connexion = CxoPool.getConnection();

			String requete = "select pg_size_pretty(pg_database_size('wayd')) as taille;";

			preparedStatement = connexion.prepareStatement(requete);

			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				taille = rs.getString("taille");
			}

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			LOG.error(ExceptionUtils.getStackTrace(e));
		}

		CxoPool.close(connexion, preparedStatement);

		LogDAO.LOG_DUREE("getTailleBase", debut);

		return taille;

	}

	public static int getNbrConnexionActivtePostGres() {

		long debut = System.currentTimeMillis();
		Connection connexion = null;
		ResultSet rs = null;

		PreparedStatement preparedStatement = null;

		int taille = 0;
		try {
			connexion = CxoPool.getConnection();

			String requete = "SELECT count(*)as nbr FROM pg_catalog.pg_stat_activity";

			preparedStatement = connexion.prepareStatement(requete);

			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				taille = rs.getInt("nbr");
			}

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
		} finally {
			CxoPool.close(connexion, preparedStatement, rs);
		}

		LogDAO.LOG_DUREE("getNbrConnexionActivtePostGres", debut);

		return taille;

	}

	public static int getNbrLogs() {

		long debut = System.currentTimeMillis();
		Connection connexion = null;

		PreparedStatement preparedStatement = null;

		int nbrLog = 0;
		try {
			connexion = CxoPool.getConnection();

			String requete = "SELECT count(id) as nbrLog from log4j";

			preparedStatement = connexion.prepareStatement(requete);

			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				nbrLog = rs.getInt("nbrlog");
			}

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			LOG.error(ExceptionUtils.getStackTrace(e));
			
		} finally {
			CxoPool.close(connexion, preparedStatement);
		}
		LogDAO.LOG_DUREE("getNbrLogs", debut);

		return nbrLog;

	}

	public static boolean supprimeNderniersLogd() {
		// TODO Auto-generated method stub

		long debut = System.currentTimeMillis();

		if (getNbrLogs() < MAX_LOG_SIZE)
			return false;

		Connection connexion = null;

		PreparedStatement preparedStatement = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "delete from log4j where id in(SELECT id  FROM log4j order by id asc limit ? offset 0);";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, NBR_LOG_A_EFFACER);
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();
			LogDAO.LOG_DUREE("suprrimeNdernierLog", debut);
			return true;

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);

		} finally {

			CxoPool.close(connexion, preparedStatement);

		}
		return false;

		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
	}

	public static void LOG_DUREE(String string, long debut) {
		// TODO Auto-generated method stub
		long duree = System.currentTimeMillis() - debut;

		if (ETAT_PERF == TypeEtatLogPerf.ACTIVE) {

			String message = string + " - " + duree + "ms";
			// MDC.put("duree", duree);
			LOG.info(message);
		}

		if (duree >= TPS_WARNING_REQUETE) {

			String message = string + " - " + duree + "ms";
			// MDC.put("duree", duree);
			LOG.warn(message);
		}

	}

	public static ArrayList<CountLogInfo> getStatLogs() {

		long debut = System.currentTimeMillis();
		Connection connexion = null;

		ArrayList<CountLogInfo> retour = new ArrayList<CountLogInfo>();
		PreparedStatement preparedStatement = null;

		try {
			connexion = CxoPool.getConnection();

			String requete = "SELECT to_char(log_date,'dd/mm/yyyy') as datestring,count(*) as nbr, log_level"
					+ "  FROM log4j group by datestring, log_level order by datestring desc";

			preparedStatement = connexion.prepareStatement(requete);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int nbr = rs.getInt("nbr");
				String dateStr = rs.getString("datestring");
				String log_level = rs.getString("log_level");

				retour.add(new CountLogInfo(dateStr, log_level, nbr));
			}

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			LOG.error(ExceptionUtils.getStackTrace(e));
		}
finally{
		CxoPool.close(connexion, preparedStatement);
}
		LogDAO.LOG_DUREE("getStatLogs", debut);

		return retour;

	}

	public static ArrayList<CountLogInfo> avgTpsRequeteJour() {

		long debut = System.currentTimeMillis();
		Connection connexion = null;

		ArrayList<CountLogInfo> retour = new ArrayList<CountLogInfo>();
		PreparedStatement preparedStatement = null;

		try {
			connexion = CxoPool.getConnection();

			String requete = "select avg(duree) as duree,to_char(log_date,'dd/mm/yyyy HH24:MI') as jour "
					+ "from log4j "
					+ "where log_message like '% - %' and duree>=0 group by jour order by jour desc";

			preparedStatement = connexion.prepareStatement(requete);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				String dateStr = rs.getString("jour");
				double duree = rs.getDouble("duree");
				retour.add(new CountLogInfo(dateStr, Double.toString(duree), 0));
			}

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			LOG.error(ExceptionUtils.getStackTrace(e));
		}
finally{
		CxoPool.close(connexion, preparedStatement);
}
		LogDAO.LOG_DUREE("avgTpsRequeteJour", debut);

		return retour;

	}

	public static void prepareStatPerf() {

		long debut = System.currentTimeMillis();
		Connection connexion = null;

		PreparedStatement preparedStatement = null;
		PreparedStatement updateStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();

			connexion.setAutoCommit(false);
			String requete = "SELECT log_message, id FROM log4j where log_message"
					+ " like '% - %' and log_level='INFO' and duree=-1";

			String updateRequete = "update log4j set duree=? where id=?";

			preparedStatement = connexion.prepareStatement(requete);
			updateStatement = connexion.prepareStatement(updateRequete);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				String log_message = rs.getString("log_message");
				int duree = getIntegers(log_message);
				int id = rs.getInt("id");
				updateStatement.setInt(1, duree);
				updateStatement.setInt(2, id);
				updateStatement.addBatch();

			}

			updateStatement.executeBatch();
			connexion.commit();
			LogDAO.LOG_DUREE("prepareStatPerf", debut);

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			LOG.error(ExceptionUtils.getStackTrace(e));
		}

		finally {
			CxoPool.close(connexion, preparedStatement, rs);
			CxoPool.close(connexion, updateStatement);

		}

	}

	public static int getIntegers(String str) {

		String retour = "0";

		for (int f = 0; f < str.length(); f++) {

			String charac = str.substring(f, f + 1);
			if (NumberUtils.isParsable(charac)) {
				retour = retour + charac;
				if (retour.length() > 5)
					return 0;
			}

		}

		return Integer.parseInt(retour);

	}

}
