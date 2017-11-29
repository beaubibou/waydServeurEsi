package wayde.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import wayde.bean.Discussion;
import wayde.bean.Message;
import wayde.bean.MessageBoite;
import wayde.bean.Personne;

public class BoiteMessageDAO {
	private static final Logger LOG = Logger.getLogger(BoiteMessageDAO.class);

	
	Connection connexion;

	public BoiteMessageDAO(Connection connexion) {
		this.connexion = connexion;
	}

	public ArrayList<Discussion> getListDiscussionTotal(int idPersonne) {

		return null;

	}

	public ArrayList<Discussion> getListDiscussionMessage(int idPersonne) {

		return null;

	}

	public ArrayList<Discussion> getListDiscussionActivite(int idPersonne) {

		return null;

	}

	public int addMessage(int idemetteur, int iddestinataire, String message)
			throws Exception {

		// Insere le message dans la boite d'emission //

		Date datecreation = Calendar.getInstance().getTime();
		String idDiscussion;
		boolean lu=true;
		if (idemetteur<iddestinataire)
			idDiscussion=idemetteur+"-"+iddestinataire;
		else
			idDiscussion=iddestinataire+"-"+idemetteur;
		String requete = "INSERT INTO boiteenvoi( message, idemetteur, iddestinataire,datecreation,iddiscussion,lu)"
				+ "  VALUES (?, ?, ?, ?,?,?);";

		PreparedStatement preparedStatement = connexion.prepareStatement(
				requete, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, message);
		preparedStatement.setInt(2, idemetteur);
		preparedStatement.setInt(3, iddestinataire);
		preparedStatement.setTimestamp(4,
				new java.sql.Timestamp(datecreation.getTime()));
		preparedStatement.setString(5, idDiscussion);
		preparedStatement.setBoolean(6, lu);
		preparedStatement.execute();
		ResultSet rs = preparedStatement.getGeneratedKeys();
		int cle = 0;
		if (rs.next())
			cle = rs.getInt("id");

		preparedStatement.close();

		// Met dans la boite de reception

		boolean nonlu = false;
		requete = "INSERT INTO boitereception( message, idemetteur, datecreation,iddestinataire,iddiscussion,lu)"
				+ "  VALUES (?, ?, ?, ?,?,?);";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setString(1, message);
		preparedStatement.setInt(2, idemetteur);
		preparedStatement.setTimestamp(3,
				new java.sql.Timestamp(datecreation.getTime()));
		preparedStatement.setInt(4, iddestinataire);
		preparedStatement.setString(5, idDiscussion);
		preparedStatement.setBoolean(6, nonlu);
		preparedStatement.execute();
		preparedStatement.close();

		return cle;

	}

	public int addMessageActivite(int idemetteur, int idactivite,
			String message, ArrayList<Personne> listpersonne) throws Exception {

		// Insere le message dans la boite d'emission //

		Date datecreation = Calendar.getInstance().getTime();
		String requete = "INSERT INTO boiteenvoiactivite( message, idemetteur, iddestinataire,datecreation,lu)"
				+ "  VALUES (?, ?, ?, ?,true);";

		PreparedStatement preparedStatement = connexion.prepareStatement(
				requete, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, message);
		preparedStatement.setInt(2, idemetteur);
		preparedStatement.setInt(3, idactivite);
		preparedStatement.setTimestamp(4,
				new java.sql.Timestamp(datecreation.getTime()));
		preparedStatement.execute();
		ResultSet rs = preparedStatement.getGeneratedKeys();

		int cle = 0;
		if (rs.next())
			cle = rs.getInt("id");

		preparedStatement.close();

		// Met dans la boite de reception

		for (Personne participant : listpersonne) {
			boolean nonlu = false;

			if (participant.getId() != idemetteur) {

				requete = "INSERT INTO boitereceptionactivite( message, idemetteur, datecreation,idactivite,iddestinataire,lu)"
						+ "  VALUES (?, ?, ?, ?,?,?);";
				preparedStatement = connexion.prepareStatement(requete,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, message);
				preparedStatement.setInt(2, idemetteur);
				preparedStatement.setTimestamp(3, new java.sql.Timestamp(
						datecreation.getTime()));
				preparedStatement.setInt(4, idactivite);

				preparedStatement.setBoolean(6, nonlu);

				preparedStatement.execute();
			}
		}
		preparedStatement.close();

		return cle;

	}

	public int addMessagebis(MessageBoite message, int iddestinataire)
			throws SQLException {

		String iddiscussion;
		int idemetteur = message.getIdemetteur();

		// Tag le message avec un numero de discussion contentatnt les 2
		// protagagoniste

		if (idemetteur < iddestinataire)
			iddiscussion = "" + idemetteur + "-" + iddestinataire;
		else
			iddiscussion = "" + iddestinataire + "-" + idemetteur;

		// ***********************************************************************************
		Date datecreation = Calendar.getInstance().getTime();

		String requete = "INSERT INTO boitereception( message, idemetteur, datecreation,idactivite,iddestinataire,lu,from)"
				+ "  VALUES (?, ?, ?, ?,?,?,?);";
		PreparedStatement preparedStatement = connexion.prepareStatement(
				requete, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, message.getMessage());
		preparedStatement.setInt(2, message.getIdemetteur());
		preparedStatement.setTimestamp(3,
				new java.sql.Timestamp(datecreation.getTime()));
		preparedStatement.setInt(4, message.getIdactivite());
		preparedStatement.setInt(5, iddestinataire);
		preparedStatement.setBoolean(6, false);
		preparedStatement.setInt(7, message.getFrom());
		preparedStatement.execute();
		ResultSet rs = preparedStatement.getGeneratedKeys();

		int cle = 0;
		if (rs.next())
			cle = rs.getInt("idmessage");

		preparedStatement.close();

		requete = "INSERT INTO message( corps, idpersonne, datecreation,idactivite,iddestinataire,iddiscussion,lu,emis)  VALUES (?, ?, ?, ?,?,?,false,false);";
		preparedStatement = connexion.prepareStatement(requete,
				Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, message.getMessage());
		preparedStatement.setInt(2, message.getIdemetteur());
		preparedStatement.setTimestamp(3,
				new java.sql.Timestamp(datecreation.getTime()));
		preparedStatement.setInt(4, message.getIdactivite());
		preparedStatement.setInt(5, iddestinataire);
		preparedStatement.setString(6, iddiscussion);
		preparedStatement.execute();

		preparedStatement.close();

		return cle;

	}

	public void RemoveMessage(int idpersonne, int[] listmessage)
			throws SQLException {
		PreparedStatement preparedStatement = null;
		// System.out.println("Effacement de message");
		for (int f = 0; f < listmessage.length; f++)

		{
			String requete = "DELETE FROM recoit where ( idmessage=? and idpersonne=? );";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, listmessage[f]);
			preparedStatement.setInt(2, idpersonne);
			preparedStatement.execute();

		}
		preparedStatement.close();

	}

	public void LitMessageDiscussion(int iddestinaire, int idemetteur)
	// Met la
	// valeur
	// de lu
	// �
	// true
	// dans
	// la
	// table recoit. En focntion d'une discussion entre emetteur et
	// destinataire. Utilis� pour marquer tous les messages
	// comme lu dans une discussion.

			throws SQLException {
		PreparedStatement preparedStatement = null;

		String requete = "UPDATE  message  set lu=true  "
				+ " WHERE idpersonne=? and iddestinataire=? and emis=false";

		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idemetteur);
		preparedStatement.setInt(2, iddestinaire);
		preparedStatement.execute();
		preparedStatement.close();

	}

	public void LitMessageDiscussionByAct(int iddestinaire, int idactivite)
	// Met la
	// valeur
	// de lu
	// �
	// true
	// dans
	// la
	// table recoit. En focntion d'une discussion entre emetteur et
	// destinataire. Utilis� pour marquer tous les messages
	// comme lu dans une discussion.

			throws SQLException {
		PreparedStatement preparedStatement = null;
		String requete = "UPDATE  messagebyact  set lu=true  "
				+ " WHERE iddestinataire=? and idactivite=? and emis=false";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, iddestinaire);
		preparedStatement.setInt(2, idactivite);
		preparedStatement.execute();
		preparedStatement.close();

	}

	public void LitMessage(int idpersonne, int idmessage) throws SQLException {
		PreparedStatement preparedStatement = null;
		// System.out.println("Lecture d'un message");

		String requete = "UPDATE  message set lu=true  "
				+ " WHERE idmessage=? ";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idmessage);
		preparedStatement.execute();
		preparedStatement.close();

	}

	public void LitMessageByAct(int idpersonne, int idmessage)
			throws SQLException {
		PreparedStatement preparedStatement = null;
		// System.out.println("Lecture d'un message d'une activite");

		String requete = "UPDATE  messagebyact set lu=true  "
				+ " WHERE idmessage=? ";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idmessage);
		preparedStatement.execute();
		preparedStatement.close();

	}

	public void RemoveUnMessage(int idpersonne, int idmessage)
			throws SQLException {
		PreparedStatement preparedStatement = null;
		// System.out.println("Effacement un message d'une personne "+
		// idpersonne);
		String requete = "DELETE FROM recoit where ( idmessage=? and idpersonne=? );";
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idmessage);
		preparedStatement.setInt(2, idpersonne);
		preparedStatement.execute();
		preparedStatement.close();

	}

	public void effaceMessageRecu(int idpersonne, int idmessage)
			throws SQLException {
		String requete = "Delete from message WHERE iddestinataire=? and idmessage=?;";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setInt(2, idmessage);
		preparedStatement.execute();
		preparedStatement.close();

	}

	public void effaceMessageRecuByAct(int idpersonne, int idmessage)
			throws SQLException {
		String requete = "Delete from messagebyact WHERE iddestinataire=? and idmessage=?;";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setInt(2, idmessage);
		preparedStatement.execute();
		preparedStatement.close();

	}

	public void effaceMessageEmis(int idpersonne, int idmessage)
			throws SQLException {
		String requete = "delete from message  WHERE idpersonne=? and idmessage=?;";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setInt(2, idmessage);
		preparedStatement.execute();
		preparedStatement.close();

	}

	public void effaceMessageEmisByAct(int idpersonne, int idmessage)
			throws SQLException {
		String requete = "delete from messagebyact  WHERE idemetteur=? and idmessage=?;";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setInt(2, idmessage);
		preparedStatement.execute();
		preparedStatement.close();

	}

	public Message getMessage(int idmessage) throws SQLException {
		Message message = null;
		try {

			String requete = " SELECT  personne.prenom,personne.nom,message.datecreation,message.sujet,message.corps,"
					+ "message.idpersonne, recoit.lu,recoit.archive,recoit.supprime "
					+ "from personne,recoit,message "
					+ "where personne.idpersonne=message.idpersonne "
					+ "and recoit.idmessage=message.idmessage "
					+ "and recoit.idmessage=?";

			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);

			preparedStatement.setInt(1, idmessage);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String sujet = rs.getString("sujet");
				String corps = rs.getString("corps");
				Date datecreation = rs.getTimestamp("datecreation");
				String nomemetteur = rs.getString("nom");
				String prenomemetteur = rs.getString("prenom");
				int idmetteur = rs.getInt("idpersonne");
				boolean lu = rs.getBoolean("lu");
				boolean archive = rs.getBoolean("archive");
				boolean supprime = rs.getBoolean("supprime");
				message = new Message(idmessage, nomemetteur, prenomemetteur,
						idmetteur, sujet, corps, datecreation, lu, archive,
						supprime, 0);
				return message;

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

	// Renvoi le dernier message d'un chat sur une discussion
	public Message getLastMessageByAct(int idactivite) throws SQLException {

		String requete = " SELECT  corps,datecreation from messagebyact where idmessage="
				+ "( select (max(idmessage) )from messagebyact where idactivite=?)";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idactivite);
		ResultSet rs = preparedStatement.executeQuery();
		String corps;
		Date datecreation;
		if (rs.next()) {

			corps = rs.getString("corps");
			datecreation = rs.getTimestamp("datecreation");
			// System.out.println("corps"+corps);

			return new Message(corps, datecreation);
		}

		;
		rs.close();
		preparedStatement.close();

		return new Message("Bienvenue!! ", new Date());

	}

	public ArrayList<Message> getListMessageArchive(int idpersonne)
			throws SQLException {
		Message messagedb = null;
		ArrayList<Message> retour = new ArrayList<Message>();

		String requete = " SELECT  personne.prenom,personne.nom,message.datecreation,message.sujet,message.corps,"
				+ "message.idpersonne,message.idmessage,recoit.lu,recoit.archive,recoit.supprime "
				+ "from personne,recoit,message "
				+ "where personne.idpersonne=message.idpersonne "
				+ "and recoit.idmessage=message.idmessage "
				+ "and recoit.idpersonne=? and recoit.lu=false;";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setInt(1, idpersonne);
		ResultSet rs = preparedStatement.executeQuery();

		while (rs.next()) {
			String sujet = rs.getString("sujet");
			String corps = rs.getString("corps");
			Date datecreation = rs.getDate("datecreation");
			String nomemetteur = rs.getString("nom");
			String prenomemetteur = rs.getString("prenom");
			int idmetteur = rs.getInt("idpersonne");
			int idmessage = rs.getInt("idmessage");
			boolean lu = rs.getBoolean("lu");
			boolean archive = rs.getBoolean("archive");
			boolean supprime = rs.getBoolean("supprime");

			messagedb = new Message(idmessage, nomemetteur, prenomemetteur,
					idmetteur, sujet, corps, datecreation, lu, archive,
					supprime, 0);

			retour.add(messagedb);

		}
		rs.close();
		preparedStatement.close();

		return retour;

	}

	public ArrayList<Message> getListMessageNonLu(int idpersonne)
			throws SQLException {
		Message message = null;
		ArrayList<Message> retour = new ArrayList<Message>();

		String requete = " SELECT  personne.photo,personne.prenom,personne.nom,message.datecreation,message.sujet,message.corps,"
				+ "message.idpersonne,message.idmessage,recoit.lu,recoit.archive,recoit.supprime "
				+ "from personne,recoit,message "
				+ "where personne.idpersonne=message.idpersonne "
				+ "and recoit.idmessage=message.idmessage "
				+ "and recoit.idpersonne=? and recoit.lu=false order by datecreation desc;";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setInt(1, idpersonne);
		ResultSet rs = preparedStatement.executeQuery();

		while (rs.next()) {
			String sujet = rs.getString("sujet");
			String corps = rs.getString("corps");
			Date datecreation = rs.getTimestamp("datecreation");
			String nomemetteur = rs.getString("nom");
			String prenomemetteur = rs.getString("prenom");
			int idmetteur = rs.getInt("idpersonne");
			int idmessage = rs.getInt("idmessage");
			boolean lu = rs.getBoolean("lu");
			boolean archive = rs.getBoolean("archive");
			boolean supprime = rs.getBoolean("supprime");

			message = new Message(idmessage, nomemetteur, prenomemetteur,
					idmetteur, sujet, corps, datecreation, lu, archive,
					supprime, 0);

			retour.add(message);

		}
		rs.close();
		preparedStatement.close();
		return retour;

	}

	public ArrayList<Discussion> getListDiscussion(int idpersonne)
			throws SQLException {
		Discussion discussion = null;
		ArrayList<Discussion> retour = new ArrayList<Discussion>();

		// " personne p where m.idpersonne=p.idpersonne and iddestinataire=? group by m.idpersonne,p.nom,p.prenom,p.photo,m.corps,m.idmessage";
		// Une personne appartient � la discussion x-y ou y-x on gere les 2 cas

		String iddiscussion = "%-" + idpersonne;
		String iddiscussion1 = idpersonne + "-%";

		String requete = "SELECT "
				+

				// Calcul le nobmre de message non lu pour une discussion

				"( select count (idmessage) from message where iddestinataire=? and iddiscussion=m.iddiscussion and emis=false and lu=false) as nonlu,"

				// Ramene toutes les indicatioons pour le dernier message d'une
				// discussion

				+ "p2.nom as nom2,p2.prenom as prenom2,p2.photo as photo2,p2.idpersonne as idpersonne2,"
				+ "p1.nom as nom1,p1.prenom as prenom1,p1.photo as photo1,p1.idpersonne as idpersonne1, "
				+ "m.sujet, m.corps,m.datecreation, m.idmessage, m.idactivite, "
				+ "m.datecreation,m.lu, m.emis, m.iddiscussion "
				+ "from message m, personne p2, personne p1 where "
				+ "p2.idpersonne=m.idpersonne and p1.idpersonne=m.iddestinataire and m.idmessage in"

				// Cherche l'identifiant des derniers message d'une disucssion -
				// Renv

				+ " (select max(idmessage) from message where (message.iddiscussion like ? or message.iddiscussion like ?) and"
				+ " ((message.iddestinataire=? and message.emis=false) or (message.idpersonne=? and message.emis=true)) group by iddiscussion)  ";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setString(2, iddiscussion);
		preparedStatement.setString(3, iddiscussion1);
		preparedStatement.setInt(4, idpersonne);
		preparedStatement.setInt(5, idpersonne);
		ResultSet rs = preparedStatement.executeQuery();

		while (rs.next()) {
			// String nomemetteur = "";
			String prenomemetteur = "";
			int idemetteur = 0;
			String photo = "";
			String message = rs.getString("corps");
			Date datecreation = rs.getTimestamp("datecreation");
			int idpersonne1 = rs.getInt("idpersonne1");

			// A ce niveau on a le dernier message emis on ne sait qui est
			// l'emetteur soit nous m�me
			// soit l'autre. La discussion renvoi l'interlocuteur.
			// On le recherche donc ici

			if (idpersonne == idpersonne1) {// Si je suis la personne 1 alors je
											// renvoi la 2
				// nomemetteur = rs.getString("nom2");
				prenomemetteur = rs.getString("prenom2");
				idemetteur = rs.getInt("idpersonne2");
				photo = rs.getString("photo2");

			} else {

				// nomemetteur = rs.getString("nom1");
				prenomemetteur = rs.getString("prenom1");
				idemetteur = rs.getInt("idpersonne1");
				photo = rs.getString("photo1");

			}

			if (photo == null)
				photo = "";

			// int idmessage = rs.getInt("idmessage");
			int nbrnonlu = rs.getInt("nonlu");

			discussion = new Discussion(prenomemetteur, idemetteur, message,
					datecreation, photo, nbrnonlu, Discussion.STAND_ALONE, 0);
			// discussion.setDateDernierMessage(datecreation);
			retour.add(discussion);

		}
		rs.close();
		preparedStatement.close();
		return retour;

	}

	public ArrayList<Message> getListMessageBefore(int iddestinataire,
			int idemetteur, int idxmessage) throws SQLException {
		Message message = null;
		// System.out.println("recuepre message avant"+idxmessage);
		ArrayList<Message> retour = new ArrayList<Message>();

		String requete = "select p.prenom,p.nom,p.idpersonne,m.corps,m.idmessage, m.datecreation"
				+ ",m.lu from message m,personne p where ((m.iddestinataire=? and m.idpersonne=? and emis=false)"
				+ "	 or (m.idpersonne=? and m.iddestinataire=? and m.emis=true))"
				+ " and m.idpersonne=p.idpersonne and m.idmessage<? order by m.datecreation;";// desc
																								// limit
																								// 5;";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setInt(1, iddestinataire);
		preparedStatement.setInt(2, idemetteur);
		preparedStatement.setInt(3, iddestinataire);
		preparedStatement.setInt(4, idemetteur);
		preparedStatement.setInt(5, idxmessage);

		ResultSet rs = preparedStatement.executeQuery();

		while (rs.next()) {
			String sujet = "";
			String corps = rs.getString("corps");

			Date datecreation = rs.getTimestamp("datecreation");
			String nomemetteur = rs.getString("nom");
			String prenomemetteur = rs.getString("prenom");
			int idmetteur = rs.getInt("idpersonne");
			int idmessage = rs.getInt("idmessage");
			boolean lu = rs.getBoolean("lu");
			boolean archive = false;// rs.getBoolean("archive");
			boolean supprime = false;// rs.getBoolean("supprime");

			message = new Message(idmessage, nomemetteur, prenomemetteur,
					idmetteur, sujet, corps, datecreation, lu, archive,
					supprime, 0);

			retour.add(message);

		}
		rs.close();
		preparedStatement.close();

		return retour;

	}

	public ArrayList<Message> getDiscussion(int iddestinataire, int idemetteur)
			throws SQLException {
		Message message = null;

		ArrayList<Message> retour = new ArrayList<Message>();

		String requete = "select p.prenom,p.nom,p.idpersonne,m.corps,m.idmessage, m.datecreation"
				+ ",m.lu from message m,personne p where ((m.iddestinataire=? and m.idpersonne=? and emis=false)"
				+ "	 or (m.idpersonne=? and m.iddestinataire=? and m.emis=true))"
				+ " and m.idpersonne=p.idpersonne  order by m.datecreation desc ;";// desc
																					// limit
																					// 5;";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setInt(1, iddestinataire);
		preparedStatement.setInt(2, idemetteur);
		preparedStatement.setInt(3, iddestinataire);
		preparedStatement.setInt(4, idemetteur);

		ResultSet rs = preparedStatement.executeQuery();

		while (rs.next()) {
			String sujet = "";
			String corps = rs.getString("corps");

			Date datecreation = rs.getTimestamp("datecreation");
			String nomemetteur = rs.getString("nom");
			String prenomemetteur = rs.getString("prenom");
			int idmetteur = rs.getInt("idpersonne");
			int idmessage = rs.getInt("idmessage");
			boolean lu = rs.getBoolean("lu");
			boolean archive = false;// rs.getBoolean("archive");
			boolean supprime = false;// rs.getBoolean("supprime");

			message = new Message(idmessage, nomemetteur, prenomemetteur,
					idmetteur, sujet, corps, datecreation, lu, archive,
					supprime, 0);

			retour.add(0, message);

		}
		rs.close();
		preparedStatement.close();

		return retour;

	}

	public ArrayList<Message> getDiscussionByAct(int iddestinataire,
			int idactivite) throws SQLException {
		Message message = null;

		ArrayList<Message> retour = new ArrayList<Message>();

		String requete = "select p.prenom,p.nom,p.idpersonne,m.corps,m.idmessage, m.datecreation"
				+ ",m.lu from messagebyact m,personne p where ((m.iddestinataire=? and m.idactivite=? and emis=false)"
				+ "	 or (m.idemetteur=? and m.idactivite=? and m.emis=true))"
				+ " and m.idemetteur=p.idpersonne order by m.datecreation desc;";// " limit 5";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setInt(1, iddestinataire);
		preparedStatement.setInt(2, idactivite);
		preparedStatement.setInt(3, iddestinataire);
		preparedStatement.setInt(4, idactivite);

		ResultSet rs = preparedStatement.executeQuery();

		while (rs.next()) {
			String sujet = "";
			String corps = rs.getString("corps");

			Date datecreation = rs.getTimestamp("datecreation");
			String nomemetteur = rs.getString("nom");
			String prenomemetteur = rs.getString("prenom");
			int idmetteur = rs.getInt("idpersonne");
			int idmessage = rs.getInt("idmessage");
			boolean lu = rs.getBoolean("lu");
			boolean archive = false;// rs.getBoolean("archive");
			boolean supprime = false;// rs.getBoolean("supprime");

			message = new Message(idmessage, nomemetteur, prenomemetteur,
					idmetteur, sujet, corps, datecreation, lu, archive,
					supprime, idactivite);

			retour.add(0, message);

		}
		rs.close();
		preparedStatement.close();
		return retour;

	}

	public ArrayList<Message> getListMessageBeforeByAct(int iddestinataire,
			int idactivite, int idxmessage) throws SQLException {
		Message message = null;

		ArrayList<Message> retour = new ArrayList<Message>();

		String requete = "select p.prenom,p.nom,p.idpersonne,m.corps,m.idmessage, m.datecreation"
				+ ",m.lu from messagebyact m,personne p where ((m.iddestinataire=? and m.idactivite=? and emis=false)"
				+ "	 or (m.idemetteur=? and m.idactivite=? and m.emis=true))"
				+ " and m.idemetteur=p.idpersonne and m.idmessage<? order by m.datecreation ";// desc
																								// limit
																								// 5;";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setInt(1, iddestinataire);
		preparedStatement.setInt(2, idactivite);
		preparedStatement.setInt(3, iddestinataire);
		preparedStatement.setInt(4, idactivite);
		preparedStatement.setInt(5, idxmessage);

		ResultSet rs = preparedStatement.executeQuery();

		while (rs.next()) {
			String sujet = "";
			String corps = rs.getString("corps");
			Date datecreation = rs.getTimestamp("datecreation");
			String nomemetteur = rs.getString("nom");
			String prenomemetteur = rs.getString("prenom");
			int idmetteur = rs.getInt("idpersonne");
			int idmessage = rs.getInt("idmessage");
			boolean lu = rs.getBoolean("lu");
			boolean archive = false;// rs.getBoolean("archive");
			boolean supprime = false;// rs.getBoolean("supprime");

			message = new Message(idmessage, nomemetteur, prenomemetteur,
					idmetteur, sujet, corps, datecreation, lu, archive,
					supprime, idactivite);

			retour.add(message);

		}
		rs.close();
		preparedStatement.close();
		return retour;

	}

	public ArrayList<Message> getListMessageAfterByAct(int idpersonne,
			int idxmessage, int idactivite) throws SQLException {
		Message message = null;
		ArrayList<Message> retour = new ArrayList<Message>();

		String requete = " SELECT  personne.prenom,personne.nom,m.datecreation,m.corps,"
				+ "m.idemetteur,m.idmessage,m.lu "
				+ "from personne,messagebyact m "
				+ "where personne.idpersonne=m.idemetteur "
				+ "and m.iddestinataire=? and m.idmessage>? and m.emis=false and m.idactivite=? order by m.idmessage ASC";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setInt(2, idxmessage);
		preparedStatement.setInt(3, idactivite);
		ResultSet rs = preparedStatement.executeQuery();

		while (rs.next()) {
			String sujet = "";
			String corps = rs.getString("corps");
			Date datecreation = rs.getTimestamp("datecreation");
			String nomemetteur = rs.getString("nom");
			String prenomemetteur = rs.getString("prenom");
			int idmetteur = rs.getInt("idemetteur");
			int idmessage = rs.getInt("idmessage");
			boolean lu = rs.getBoolean("lu");
			boolean archive = false;
			boolean supprime = false;
			
			message = new Message(idmessage, nomemetteur, prenomemetteur,
					idmetteur, sujet, corps, datecreation, lu, archive,
					supprime, idactivite);

			retour.add(message);

		}
		rs.close();
		preparedStatement.close();

		return retour;

	}

	public ArrayList<Message> getListMessageAfter(int idpersonne, int idxmessage)
			throws SQLException {
		Message message = null;
		ArrayList<Message> retour = new ArrayList<Message>();

		String requete = " SELECT  personne.prenom,personne.nom,message.datecreation,message.sujet,"
				+ "message.corps,"
				+ "message.idpersonne,message.idmessage,message.lu "
				+ "from personne,message "
				+ "where personne.idpersonne=message.idpersonne "
				+ "and message.iddestinataire=? and message.idmessage>? and message.emis=false order by message.idmessage ASC";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setInt(2, idxmessage);
		ResultSet rs = preparedStatement.executeQuery();
		// System.out
		// .println("********************Recupere tous DAO les  message � partir de  ");

		while (rs.next()) {
			String sujet = rs.getString("sujet");
			String corps = rs.getString("corps");
			Date datecreation = rs.getTimestamp("datecreation");
			String nomemetteur = rs.getString("nom");
			String prenomemetteur = rs.getString("prenom");

			int idmetteur = rs.getInt("idpersonne");
			int idmessage = rs.getInt("idmessage");
			boolean lu = rs.getBoolean("lu");
			boolean archive = false;
			boolean supprime = false;
			// System.out.println("********************qsdfqsdfqdqsdfsdfqsdf  "
			// + idmessage);
			message = new Message(idmessage, nomemetteur, prenomemetteur,
					idmetteur, sujet, corps, datecreation, lu, archive,
					supprime, 0);

			retour.add(message);

		}
		rs.close();
		preparedStatement.close();

		return retour;

	}

	public void effaceDiscussion(int iddestinataire, int idemetteur)
			throws SQLException {
		// Efface les mesages que j'ai recu

		String requete = "delete  from message  where iddestinataire=? and emis=false and idpersonne=?";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, iddestinataire);
		preparedStatement.setInt(2, idemetteur);
		preparedStatement.execute();

		// Efface les mesages que j'ai emis pour l'emetteur
		requete = "delete  from message  where (idpersonne=? and emis=true and iddestinataire=?)";

		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, iddestinataire);
		preparedStatement.setInt(2, idemetteur);
		preparedStatement.execute();

		preparedStatement.close();

	}

}
