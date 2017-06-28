package wayde.bean;
import java.util.Date;
public class Message {

	private int idmessage;
	private int idemetteur;
	private String nomemetteur;
	private String prenomemetteur;
	private String datecreationstr;
	private String corps;
	private String sujet;
	private boolean supprime;
	private boolean archive;
	private boolean lu;
	private int idactivite;
	private int idmessagesource;
	
	public Date datecreation;
	public Message() {

	}

	public Message(int idmessage, String nomemetteur, String prenomemetteur,
			int idemetteur, String sujet, String corps, Date datecreation,
			boolean lu, boolean archive, boolean supprime,int idactivite) {
		super();
		this.idmessage = idmessage;
		this.nomemetteur = nomemetteur;
		this.prenomemetteur = prenomemetteur;
		this.idemetteur = idemetteur;
		this.sujet =sujet;
		this.corps = corps;
		this.datecreationstr = Parametres.getStringWsFromDate(datecreation);
		this.lu = lu;
		this.archive = archive;
		this.supprime =supprime;
		this.idactivite = idactivite;
	
		
	}

	
	
	public Message(int idemetteur,  String corps,
			int idactivite, int idmessagesource) {
		this.corps = corps;
		this.idemetteur = idemetteur;
		this.idactivite = idactivite;
		this.idmessagesource = idmessagesource;

	}
	

	public Message(String corps, Date datecreation) {
		// TODO Auto-generated constructor stub
	this.corps=corps;
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

	public String getCorps() {
		return corps;
	}

	public void setCorps(String corps) {
		this.corps = corps;
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

}
