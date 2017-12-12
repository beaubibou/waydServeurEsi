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
import wayde.dao.ActiviteDAO;
import website.metier.StatistiqueBean;

public class StatDAO {
	private static final Logger LOG = Logger.getLogger(StatDAO.class);

	public static int getNbrMessageDuJour() {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int nbrMessage=0;
		try {
			connexion = CxoPool.getConnection();

			String requete = " SELECT count( idmessage) as nbrMessage  FROM public.message where to_char(datecreation,'DD-MM-YYYY')="
					+ " to_char(current_timestamp, 'DD-MM-YYYY')";
			preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				 nbrMessage = rs.getInt("nbrMessage");
				
			}

			return nbrMessage;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
			return nbrMessage;
			
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
	}
	public static int getNbrMessageByActDuJour() {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int nbrMessage=0;
		try {
			connexion = CxoPool.getConnection();

			String requete = " SELECT count( idmessage) as nbrMessage  FROM messagebyact where to_char(datecreation,'DD-MM-YYYY')="
					+ " to_char(current_timestamp, 'DD-MM-YYYY')";
			preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				 nbrMessage = rs.getInt("nbrMessage");
				
			}

			return nbrMessage;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
			return nbrMessage;
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
	}
		
	
	public static ArrayList<StatistiqueBean> getHistoriqueMessageByAct() {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<StatistiqueBean> retour = new ArrayList<StatistiqueBean>();

		try {
			connexion = CxoPool.getConnection();

			String requete = " SELECT count( idmessage) as nrbMessage ,to_char(datecreation, 'DD-MM-YYYY') as date  FROM public.messagebyact "
					+ "group by to_char(datecreation, 'DD-MM-YYYY')" ;
			preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("nrbMessage");
				String date = rs.getString("date");
				retour.add(new StatistiqueBean(date,id));
			}

			return retour;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
			return retour;
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
	}

	
	
	public static ArrayList<StatistiqueBean> getHistoriqueMessage() {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<StatistiqueBean> retour = new ArrayList<StatistiqueBean>();

		try {
			connexion = CxoPool.getConnection();
			String requete = "SELECT count( idmessage) as nrbMessage ,to_char(datecreation, 'DD-MM-YYYY') as date  FROM public.message "
					+ "group by to_char(datecreation, 'DD-MM-YYYY')";	preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("nrbMessage");
				String date = rs.getString("date");
				retour.add(new StatistiqueBean(date,id));
			}

			return retour;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
			return retour;
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
	}
	
	public static ArrayList<StatistiqueBean> getHistoriqueActivite() {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<StatistiqueBean> retour = new ArrayList<StatistiqueBean>();

		try {
			connexion = CxoPool.getConnection();
			String requete = "SELECT count( idactivite) as nrbActivite ,to_char(datecreation, 'DD-MM-YYYY') as date  FROM activite "
					+ "group by to_char(datecreation, 'DD-MM-YYYY')";	preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("nrbActivite");
				String date = rs.getString("date");
				retour.add(new StatistiqueBean(date,id));
			}

			return retour;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
			return retour;
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
	}
	
	public static ArrayList<StatistiqueBean> getHistoriqueParticipation() {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<StatistiqueBean> retour = new ArrayList<StatistiqueBean>();

		try {
			connexion = CxoPool.getConnection();
			String requete = "SELECT count( idactivite) as nrbParticipation ,to_char(datecreation, 'DD-MM-YYYY') as date  FROM participer "
					+ "group by to_char(datecreation, 'DD-MM-YYYY')";	preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("nrbParticipation");
				String date = rs.getString("date");
				retour.add(new StatistiqueBean(date,id));
			}

			return retour;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
			return retour;
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
	}
	
	
	public static int getNbrActiviteDuJour() {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int nbrActivite=0;
		try {
			connexion = CxoPool.getConnection();

			String requete = " SELECT count( idactivite) as nbrActivite  FROM activite where to_char(datecreation,'DD-MM-YYYY')="
					+ " to_char(current_timestamp, 'DD-MM-YYYY')";
			preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				nbrActivite = rs.getInt("nbrActivite");
				
			}

			return nbrActivite;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
			return nbrActivite;
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
	}
	
	
	public static int getNbrParticipationDuJour() {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int nbrParticipation=0;
		
		try {
			connexion = CxoPool.getConnection();
			String requete = " SELECT count( idactivite) as nbrParticipation  FROM participer where to_char(datecreation,'DD-MM-YYYY')="
					+ " to_char(current_timestamp, 'DD-MM-YYYY')";
			preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				nbrParticipation = rs.getInt("nbrParticipation");
				
			}
			return nbrParticipation;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
			return nbrParticipation;
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
	}
	
	

	
	
}
