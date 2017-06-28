package wayde.bean;


import java.util.Date;

public class Discussion {
	
	private int idemetteur;
	private String titre;
	private String datecreationstr;
	private String message;
	private String photo;
	private int nbnonlu;
	private int idactivite;
	private int type;
	private int idtypeactivite;
	public static int STAND_ALONE=1;
	public static int GROUP_TALK=2;
	public long dateMessage;


	public Discussion() {

	}

	
	public long getDateMessage() {
		return dateMessage;
	}


	public String getTitre() {
		return titre;
	}


	public void setTitre(String titre) {
		this.titre = titre;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public void setDateMessage(long dateMessage) {
		this.dateMessage = dateMessage;
	}


	public Discussion(  String titre,
			int idemetteur,  String corps, Date datecreation,
			String photo,int nbnonlu, int type, int idactivite) {
		super();
	
		this.titre =titre;
		this.idemetteur = idemetteur;
		this.message =corps;
		this.datecreationstr = Parametres.getStringWsFromDate(datecreation);
		this.photo=photo;
		this.nbnonlu=nbnonlu;
		this.type=type;
		this.idactivite=idactivite;

	}
	
	
	public int getIdactivite() {
		return idactivite;
	}

	public void setIdactivite(int idactivite) {
		this.idactivite = idactivite;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Discussion(Activite activite,Date dateDernierMessage,String message) {
	
		this.titre =activite.titre;;
		this.idemetteur = 0;
		this.message =message;
		this.datecreationstr = Parametres.getStringWsFromDate(dateDernierMessage);
		this.photo="";
		this.nbnonlu=0;
		this.type=Discussion.GROUP_TALK;
		this.idactivite=activite.getId();
		this.idtypeactivite=activite.getTypeactivite();
		this.dateMessage=dateDernierMessage.getTime();
		
	}
	
	
	public int getIdtypeactivite() {
		return idtypeactivite;
	}

	public void setIdtypeactivite(int idtypeactivite) {
		this.idtypeactivite = idtypeactivite;
	}

	public int getNbnonlu() {
		return nbnonlu;
	}

	public void setNbnonlu(int nbnonlu) {
		this.nbnonlu = nbnonlu;
	}

	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}



	


	public void setIdemetteur(int idemetteur) {
		this.idemetteur = idemetteur;
	}



	public void setDatecreationstr(String datecreationstr) {
		this.datecreationstr = datecreationstr;
	}


	public int getIdemetteur() {
		return idemetteur;
	}

		



	public String getDatecreationstr() {
		return datecreationstr;
	}

	




	
	
}
