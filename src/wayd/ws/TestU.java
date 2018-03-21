package wayd.ws;

import java.sql.Connection;
import java.sql.PreparedStatement;

import wayde.bean.CxoPool;
import wayde.bean.Droit;
import wayde.bean.MessageServeur;
import wayde.bean.Personne;
import wayde.dao.NotificationDAO;
import wayde.dao.PersonneDAO;
import website.dao.LogDAO;

public class TestU {
	
	public int test_getNbrActiviteEncours() {

		Connection connexion = null;
		int nbractivite = 0;
//		try {
//			connexion = CxoPool.getConnection();
//			String requete = "Select count(idactivite) as nbractivite  FROM activite where  activite.datefin>? ;";
//			PreparedStatement preparedStatement = connexion
//					.prepareStatement(requete);
//
//			preparedStatement.setTimestamp(1,
//					new java.sql.Timestamp(new Date().getTime()));
//			ResultSet rs = preparedStatement.executeQuery();
//
//			if (rs.next()) {
//				nbractivite = rs.getInt("nbractivite");
//			}
//		} catch (SQLException | NamingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			LOG.error(ExceptionUtils.getStackTrace(e));
//			return 0;
//		} finally {
//			CxoPool.closeConnection(connexion);
//		}

		return nbractivite;
	}

	public void tesgcm(String gmcToken) {
		
//		try {
//			PushNotifictionHelper.sendPushNotificationTo(gmcToken);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			LOG.error(ExceptionUtils.getStackTrace(e));
//		}

	}

	public int test_getPremierId() {

		Connection connexion = null;
		int premierId = 0;
//		try {
//			connexion = CxoPool.getConnection();
//			String requete = "select min (idpersonne) as minid from personne";
//			PreparedStatement preparedStatement = connexion
//					.prepareStatement(requete);
//			ResultSet rs = preparedStatement.executeQuery();
//			if (rs.next()) {
//				premierId = rs.getInt("minid");
//			}
//		} catch (SQLException | NamingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			LOG.error(ExceptionUtils.getStackTrace(e));
//			return 0;
//		} finally {
//			CxoPool.closeConnection(connexion);
//		}

		return premierId;
	}
	
	public void test_termineActivite(String mdp, int idactivite) {

//		long debut = System.currentTimeMillis();
//
//		if (mdp.compareTo("mestivierphilippe") != 0)
//			return;
//
//		ActiviteDAO.terminerActivite(idactivite);
//
//		LogDAO.LOG_DUREE("test_termineActivite", debut);

	}

	public int test_getDernierId() {

		Connection connexion = null;
		int dernierId = 0;
//		try {
//			connexion = CxoPool.getConnection();
//			String requete = "select max (idpersonne) as maxid from personne";
//			PreparedStatement preparedStatement = connexion
//					.prepareStatement(requete);
//			ResultSet rs = preparedStatement.executeQuery();
//			if (rs.next()) {
//				dernierId = rs.getInt("maxid");
//			}
//		} catch (SQLException | NamingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			LOG.error(ExceptionUtils.getStackTrace(e));
//			return 0;
//		} finally {
//			CxoPool.closeConnection(connexion);
//		}

		return dernierId;
	}

	public Personne test_GetPersonneAlea() {
		long debut = System.currentTimeMillis();

//		Connection connexion = null;
//		try {
//
//			connexion = CxoPool.getConnection();
//			PersonneDAO personnedao = new PersonneDAO(connexion);
//			Personne personne = personnedao.test_GetPersonneAle();
//
//			LogDAO.LOG_DUREE("test_GetPersonneAlea", debut);
//
//			return personne;
//
//		} catch (SQLException | NamingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			LOG.error(ExceptionUtils.getStackTrace(e));
//			return null;
//		} finally {
//			CxoPool.closeConnection(connexion);
//		}
		return null;

	}

	public String test_GetToken(int idpersonne) {
//		long debut = System.currentTimeMillis();
//		Connection connexion = null;
//
//		try {
//			connexion = CxoPool.getConnection();
//			PersonneDAO personnedao = new PersonneDAO(connexion);
//			LogDAO.LOG_DUREE("test_GetToken", debut);
//
//			return personnedao.test_getToken(idpersonne);
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			LOG.error(ExceptionUtils.getStackTrace(e));
//			return null;
//		} finally {
//			CxoPool.closeConnection(connexion);
//		}

		return null;
	}

	public void test_DonneAvis(int idpersonne, String jeton) {
//		Connection connexion = null;
//		try {
//			connexion = CxoPool.getConnection();
//
//			AvisaDonnerDAO avisadonner = new AvisaDonnerDAO(connexion);
//
//			ArrayList<AvisaDonnerDb> listavisAdonner = avisadonner
//					.getListAvisaDonner(idpersonne);
//			if (listavisAdonner.size() != 0) {
//
//				AvisaDonnerDb avis = listavisAdonner.get(new Random(System
//						.currentTimeMillis()).nextInt(listavisAdonner.size()));
//				int idpersonnenotee = avis.getIdpersonnenotee();
//				int idactivite = avis.getIdactivite();
//				addAvis(idpersonne, idpersonnenotee, idactivite, "Titre"
//						+ System.currentTimeMillis(),
//						"Libelle" + System.currentTimeMillis(), "3.2", true,
//						jeton);
//			}
//
//		} catch (SQLException | NamingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			LOG.error(ExceptionUtils.getStackTrace(e));
//
//		} finally {
//			CxoPool.closeConnection(connexion);
//		}

	}

	public int test_addCompte(long jeton) {
//		Connection connexion = null;
//
//		try {
//			// for (int f=0;f<500;f++)
//			connexion = CxoPool.getConnection();
//			PersonneDAO personnedao = new PersonneDAO(connexion);
//			return personnedao.TestaddCompteGenerique(jeton);
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			LOG.error(ExceptionUtils.getStackTrace(e));
//
//		} finally {
//			CxoPool.closeConnection(connexion);
//		}

		return 0;
	}

	public void test_init(String mdp) {
//		Connection connexion = null;
//
//		if (mdp.compareTo("mestivierphilippe") != 0)
//			return;
//
//		try {
//			// for (int f=0;f<500;f++)
//			long debut = System.currentTimeMillis();
//			connexion = CxoPool.getConnection();
//			connexion.setAutoCommit(false);
//			String requete = "delete from noter";
//			PreparedStatement preparedStatement = connexion
//					.prepareStatement(requete);
//			preparedStatement.execute();
//			preparedStatement.close();
//
//			requete = "delete from amelioration";
//			preparedStatement = connexion.prepareStatement(requete);
//			preparedStatement.execute();
//			preparedStatement.close();
//
//			requete = "delete from prefere";
//			preparedStatement = connexion.prepareStatement(requete);
//			preparedStatement.execute();
//			preparedStatement.close();
//
//			requete = "delete from ami";
//			preparedStatement = connexion.prepareStatement(requete);
//			preparedStatement.execute();
//			preparedStatement.close();
//
//			requete = "delete from notification";
//			preparedStatement = connexion.prepareStatement(requete);
//			preparedStatement.execute();
//			preparedStatement.close();
//
//			requete = "delete from demandeami";
//			preparedStatement = connexion.prepareStatement(requete);
//			preparedStatement.execute();
//			preparedStatement.close();
//
//			requete = "delete from participer";
//			preparedStatement = connexion.prepareStatement(requete);
//			preparedStatement.execute();
//			preparedStatement.close();
//
//			requete = "delete from messagebyact ";
//			preparedStatement = connexion.prepareStatement(requete);
//
//			preparedStatement.execute();
//			preparedStatement.close();
//
//			requete = "delete from message";
//			preparedStatement = connexion.prepareStatement(requete);
//
//			preparedStatement.execute();
//			preparedStatement.close();
//
//			requete = "delete from activite ";
//			preparedStatement = connexion.prepareStatement(requete);
//
//			preparedStatement.execute();
//			preparedStatement.close();
//
//			requete = "delete from personne  ";
//			preparedStatement = connexion.prepareStatement(requete);
//
//			preparedStatement.execute();
//			preparedStatement.close();
//
//			connexion.commit();
//
//			String loginfo = "test_init - "
//					+ (System.currentTimeMillis() - debut) + "ms";
//
//		} catch (SQLException | NamingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			LOG.error(ExceptionUtils.getStackTrace(e));
//			CxoPool.rollBack(connexion);
//
//		} finally {
//			CxoPool.closeConnection(connexion);
//		}

	}

	public void envoyerMail() {

		// new Outils().EnvoyerMdp("pmestivier@club.fr", "ppp");
		// kljlkj
	}
	public Personne getPersonnebyToken(String idtoken) {
		Connection connexion = null;
		long debut = System.currentTimeMillis();

		try {
			connexion = CxoPool.getConnection();

			PersonneDAO personnedao = new PersonneDAO(connexion);

			Personne personne = personnedao.getPersonneJeton(idtoken);// Recherche

			// un

			if (personne == null) {
				return null;
			}

			if (personne != null) {
				personnedao.updateChampCalculePersonne(personne.getId());// calcule
																			// les
																			// champs
				personne.setMessage("Ok");

				Droit droit = new PersonneDAO(connexion).getDroit(
						personne.getId());

				if (droit == null) {
					personne.setMessage(TextWebService.PERSONNE_INEXISTANTE);
					personne.setId(0);// echec connexion
					return personne;
				}
				

				if (!droit.isJetonOk(idtoken)) {
					personne.setMessage(TextWebService.JETON_NON_VALIDE);
					personne.setId(0);// echec connexion
					return personne;
				}
				
				MessageServeur autorisation = droit.isDefautAccess();

				if (!autorisation.isReponse()) {
					personne.setId(0);// echec connexion
					personne.setMessage(TextWebService.COMPTE_INACTIF);
					return personne;
				}

				NotificationDAO notificationdao = new NotificationDAO(connexion);
				notificationdao.addNotificationFromAvis(personne.getId());

				// ******** Met les notifications existantes � pas lu **//
				String requete = "UPDATE  notification set lu=false "
						+ " WHERE iddestinataire=? and idtype=1";
				PreparedStatement preparedStatement = connexion
						.prepareStatement(requete);
				preparedStatement.setInt(1, personne.getId());
				preparedStatement.execute();
				preparedStatement.close();

				LogDAO.LOG_DUREE("getPersonnebyToken", debut);

				return personne;

			}

		} catch ( Exception e) {
			// TODO Auto-generated catch block
			
		//	LOG.error(ExceptionUtils.getStackTrace(e));
		}

		finally {

			CxoPool.closeConnection(connexion);
		}

		return null;

	}

}
