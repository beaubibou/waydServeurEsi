package wayde.bean;

import java.util.Date;

import org.apache.log4j.Logger;

public class Notification {
	private static final Logger LOG = Logger.getLogger(Notification.class);

	int idactivite;
	int idtype;
	int idpersonne;
	int iddestinataire;
	String message;
	boolean lu;
	String  d_creationstr;
	int idnotification;
	public static final int DonneAvis=1;
	public static final int RecoitAvis=2;
	public static final int NouvelAmi = 3;
	public static final int SUPPRIME_AMI = 4;
	public static final int Supprime_Participation = 5;
	public static final int Supprime_Activite = 6;
	public static final int MESSAGE_TEXT = 7;
	public Notification(){
		
		
	}
	
	public Notification(int idactivite, int idtypemessage, int idpersonne,
			int iddestinataire, String nompersconcernee, String titreactivite,
			Date d_creation,boolean lu,int idnotification) {
		
		super();
		this.idactivite = idactivite;
		this.idtype = idtypemessage;
		this.idpersonne = idpersonne;
		this.iddestinataire = iddestinataire;
		this.lu=lu;
		d_creationstr = Parametres.getStringWsFromDate(d_creation);
		this.idnotification=idnotification;
		switch (idtypemessage){
		
		case DonneAvis:
			this.message="Penser à noter "+nompersconcernee+ " pour sa participation  "
			+ " à "+titreactivite+".";
			break;
			
		case RecoitAvis:
			this.message="Vous avez recu un avis de "+nompersconcernee+ " pour votre participation à "
			+ titreactivite+".";
		
			break;
			
		case NouvelAmi:
			this.message="Vous �tes � pr�sent l'ami de "+nompersconcernee;
			break;
			
		case SUPPRIME_AMI:
			this.message= nompersconcernee+ " ne souhaite plus �tre votre ami";
			break;
		
		case Supprime_Participation:
			this.message= nompersconcernee+ " ne participe plus � l'activit�";
			break;	
		case Supprime_Activite:
			this.message= nompersconcernee+ " � annul� son activit�";
			break;	
			
		case MESSAGE_TEXT:
			this.message= "L'organisateur � annul� votre participation";
			break;	
				
		}
		
	}

	

	public int getIdnotification() {
		return idnotification;
	}

	public void setIdnotification(int idnotification) {
		this.idnotification = idnotification;
	}

	public String getD_creationstr() {
		return d_creationstr;
	}

	public void setD_creationstr(String d_creationstr) {
		this.d_creationstr = d_creationstr;
	}

	public boolean isLu() {
		return lu;
	}

	public void setLu(boolean lu) {
		this.lu = lu;
	}

	public int getIdactivite() {
		return idactivite;
	}

	public void setIdactivite(int idactivite) {
		this.idactivite = idactivite;
	}

	public int getIdtype() {
		return idtype;
	}

	public void setIdtype(int idtype) {
		this.idtype = idtype;
	}

	public int getIdpersonne() {
		return idpersonne;
	}

	public void setIdpersonne(int idpersonne) {
		this.idpersonne = idpersonne;
	}

	public int getIddestinataire() {
		return iddestinataire;
	}

	public void setIddestinataire(int iddestinataire) {
		this.iddestinataire = iddestinataire;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}



	
	
	
	
}
