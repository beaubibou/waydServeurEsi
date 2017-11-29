package wayde.bean;

import java.util.Date;

import org.apache.log4j.Logger;



public class Personne {

	/**
	 * serialVersionUID - long, DOCUMENTEZ_MOI
	 */
	private static final Logger LOG = Logger.getLogger(Personne.class);

	private int id;
	private String login;
	private String mdp;
	private String nom;
	private String prenom;
	private String ville;
	private boolean actif;
	private boolean verrouille;
	private int nbrecheccnx;
	private String datecreationstr;
	private String datenaissancestr;
	private String message;
	String photo;
	String commentaire;
	int sexe;
	public String mail,pwd;
	private String gcm;
	private boolean affichesexe;
	private boolean afficheage;
	private boolean premiereconnexion;
	public Date  datenaissance;
	public String cleactivation;
	private int nbravis;
	private double note;
	private boolean notification;
	public double getLatitude() {
		return latitude;
	}



	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}



	public double getLongitude() {
		return longitude;
	}



	@Override
	public String toString() {
		return "Personne [id=" + id + ", login=" + login + ", mdp=" + mdp
				+ ", nom=" + nom + ", prenom=" + prenom + ", ville=" + ville
				+ ", actif=" + actif + ", verrouille=" + verrouille
				+ ", nbrecheccnx=" + nbrecheccnx + ", datecreationstr="
				+ datecreationstr + ", datenaissancestr=" + datenaissancestr
				+ ", message=" + message + ", photo=" + photo
				+ ", commentaire=" + commentaire + ", sexe=" + sexe + ", mail="
				+ mail + ", pwd=" + pwd + ", gcm=" + gcm + ", affichesexe="
				+ affichesexe + ", afficheage=" + afficheage
				+ ", premiereconnexion=" + premiereconnexion
				+ ", datenaissance=" + datenaissance + ", cleactivation="
				+ cleactivation + ", nbravis=" + nbravis + ", note=" + note
				+ ", rayon=" + rayon + ", admin=" + admin + ", latitude="
				+ latitude + ", longitude=" + longitude + "]";
	}



	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}



	private int rayon;
	private boolean admin;
	private double latitude;
	private double longitude;
	private int typeUser;
	private String siteWeb;
	private String siret;
	private String telephone;
	public Personne() {

	}

	

	public boolean isAdmin() {
		return admin;
	}



	public void setAdmin(boolean admin) {
		this.admin = admin;
	}



	public int getRayon() {
		return rayon;
	}



	public void setRayon(int rayon) {
		this.rayon = rayon;
	}



	public int getNbravis() {
		return nbravis;
	}



	public boolean isNotification() {
		return notification;
	}



	public void setNotification(boolean notification) {
		this.notification = notification;
	}



	public void setNbravis(int nbravis) {
		this.nbravis = nbravis;
	}



	public double getNote() {
		return note;
	}

	public void setNote(double note) {
		this.note = note;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	

	public boolean isPremiereconnexion() {
		return premiereconnexion;
	}

	public void setPremiereconnexion(boolean premiereconnexion) {
		this.premiereconnexion = premiereconnexion;
	}

	
	public Personne(int id, String login, String pwd, String nom,
			String prenom, String ville, boolean actif, boolean verrouille,
			int nbrecheccnx, Date datecreation, Date datenaissance,
			 String photo,  int sexe,String mail, String cleactivation,double note,
			 int totalavis,String commentaire,boolean afficheage,boolean affichesexe,
			boolean premiereconnexion,int rayon,
			boolean admin,double latitude,double longitude,
			boolean notification,int typeUser,String siteWeb,String siret,String telephone) {
		super();
		this.id = id;
		this.login =login;
		this.mdp =pwd;
		this.nom =nom;
		this.prenom = prenom;
		this.ville = ville;
		this.actif = actif;
		this.verrouille =verrouille;
		this.nbrecheccnx =nbrecheccnx;
		this.datecreationstr = Parametres.getStringWsFromDate(datecreation);
		this.datenaissancestr=Parametres.getStringWsFromDate(datenaissance);
		this.datenaissance=datenaissance;
		this.photo=photo;
		this.sexe=sexe;
		this.affichesexe=affichesexe;
		this.afficheage=afficheage;
		this.gcm="";
		this.commentaire=commentaire;
		this.premiereconnexion=premiereconnexion;
		this.cleactivation=cleactivation;
		this.admin=admin;
		if (nom.equals(""))this.nom=" ";
		this.rayon=rayon;
		this.latitude=latitude;
		this.longitude=longitude;
		this.notification=notification;
		this.typeUser=typeUser;
		this.siteWeb=siteWeb;
		this.siret=siret;
		this.telephone=telephone;
				
				
	}

	
	
	
	public String getSiteWeb() {
		return siteWeb;
	}



	public void setSiteWeb(String siteWeb) {
		this.siteWeb = siteWeb;
	}



	public String getSiret() {
		return siret;
	}



	public void setSiret(String siret) {
		this.siret = siret;
	}



	public String getTelephone() {
		return telephone;
	}



	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}



	public int getTypeUser() {
		return typeUser;
	}



	public void setTypeUser(int typeUser) {
		this.typeUser = typeUser;
	}



	public Personne(String nom, String prenom, String login, String mdp,
			Date datenaissance, int sexe, String mail,
			Boolean verrouille, Boolean actif) {
		// TODO Auto-generated constructor stub
		super();
		this.nom = nom;
		this.prenom =prenom;
		this.login =login;
		this.mdp =mdp;
		this.datenaissancestr=Parametres.getStringWsFromDate(datenaissance);
		this.sexe=sexe;
		this.actif =actif;
		this.datenaissance=datenaissance;
		
	
	}
	public boolean isAffichesexe() {
		return affichesexe;
	}

	public void setAffichesexe(boolean affichesexe) {
		this.affichesexe = affichesexe;
	}

	public boolean isAfficheage() {
		return afficheage;
	}

	public void setAfficheage(boolean afficheage) {
		this.afficheage = afficheage;
	}

	public Personne(String gcm, int idpersonne,boolean notification) {
		this.gcm=gcm;
		this.id=idpersonne;
		this.notification=notification;
		}

	public String getGcm() {
		return gcm;
	}

	public void setGcm(String gcm) {
		this.gcm = gcm;
	}

	public String getDatecreationstr() {
		return datecreationstr;
	}

	public void setDatecreationstr(String datecreationstr) {
		this.datecreationstr = datecreationstr;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public String getPhoto() {
		return photo;
	}

	public int getSexe() {
		return sexe;
	}

	public void setSexe(int sexe) {
		this.sexe = sexe;
	}

	

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getLogin() {
		return login;
	}


	public String getDatenaissancestr() {
		return datenaissancestr;
	}

	public void setDatenaissancestr(String datenaissancestr) {
		this.datenaissancestr = datenaissancestr;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	

	public int getNbrecheccnx() {
		return nbrecheccnx;
	}

	public void setNbrecheccnx(int nbrecheccnx) {
		this.nbrecheccnx = nbrecheccnx;
	}

	

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	public boolean isVerrouille() {
		return verrouille;
	}

	public void setVerrouille(boolean verrouille) {
		this.verrouille = verrouille;
	}

	public boolean changeMdp(String ancienMdp, String nouveauMdp) {

		if (ancienMdp.equals(mdp)) {
			setMdp(nouveauMdp);
			nbrecheccnx = 0;
			return true;
		} else {

			nbrecheccnx++;
			return false;
		}
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	
}
