package wayde.bean;
import java.util.Date;
public class MessageBoite {

	private int idmessage;
	private int idemetteur;
	private int iddestinataire;
	private String nomemetteur;
	private String prenomemetteur;
	private String datecreationstr;
	private String message;
	private String sujet;
	private boolean supprime;
	private boolean archive;
	private boolean lu;
	private int idactivite;
	private int idmessagesource;
	
	private int from;// Indique si il vient d'une discussion ou d'une activite
	public static final int FROM_ACTIVTE=1;
	public static final int FROM_DISCUSSION=2;
	
	public Date datecreation;
	public MessageBoite() {

	}

	public int getIddestinataire() {
		return iddestinataire;
	}

	public void setIddestinataire(int iddestinataire) {
		this.iddestinataire = iddestinataire;
	}

	public MessageBoite(int idmessage, String nomemetteur, String prenomemetteur,
			int idemetteur, String sujet, String message, Date datecreation,
			boolean lu, boolean archive, boolean supprime,int idactivite,int from) {
		super();
		this.idmessage = idmessage;
		this.nomemetteur = nomemetteur;
		this.prenomemetteur = prenomemetteur;
		this.idemetteur = idemetteur;
		this.sujet =sujet;
		this.message = message;
		this.datecreationstr = Parametres.getStringWsFromDate(datecreation);
		this.lu = lu;
		this.archive = archive;
		this.supprime =supprime;
		this.idactivite = idactivite;
		this.from=from;
	
		
	}

	
	
	public MessageBoite(int idemetteur,  String corps,
			int idactivite, int idmessagesource) {
		this.message = corps;
		this.idemetteur = idemetteur;
		this.idactivite = idactivite;
		this.idmessagesource = idmessagesource;

	}
	

	public MessageBoite(String corps, Date datecreation) {
		// TODO Auto-generated constructor stub
	this.message=corps;
	this.datecreation=datecreation;
	}

	

	public void setIdactivite(int idactivite) {
		this.idactivite = idactivite;
	}

	public void setIdmessagesource(int idmessagesource) {
		this.idmessagesource = idmessagesource;
	}

	public int getIdactivite() {
		return idactivite;
	}

	public int getIdmessagesource() {
		return idmessagesource;
	}

	public void setIdmessage(int idmessage) {
		this.idmessage = idmessage;
	}

	public void setIdemetteur(int idemetteur) {
		this.idemetteur = idemetteur;
	}

	public void setNomemetteur(String nomemetteur) {
		this.nomemetteur = nomemetteur;
	}

	public void setPrenomemetteur(String prenomemetteur) {
		this.prenomemetteur = prenomemetteur;
	}

	public void setDatecreationstr(String datecreationstr) {
		this.datecreationstr = datecreationstr;
	}

	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	public void setSupprime(boolean supprime) {
		this.supprime = supprime;
	}

	public int getIdemetteur() {
		return idemetteur;
	}

	public String getMessage() {
		return message;
	}

	public void setCorps(String corps) {
		this.message = corps;
	}

	public boolean isLu() {
		return lu;
	}

	public void setLu(boolean lu) {
		this.lu = lu;
	}

	public boolean isArchive() {
		return archive;
	}

	public void setArchive(boolean archive) {
		this.archive = archive;
	}

	public int getIdmessage() {
		return idmessage;
	}

	public String getNomemetteur() {
		return nomemetteur;
	}

	public String getPrenomemetteur() {
		return prenomemetteur;
	}

	public boolean isSupprime() {
		return supprime;
	}

	public String getDatecreationstr() {
		return datecreationstr;
	}

	public String getSujet() {
		return sujet;
	}

	public int getFrom() {
		// TODO Auto-generated method stub
		return from;
	}

}
