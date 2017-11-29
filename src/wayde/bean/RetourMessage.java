package wayde.bean;

import org.apache.log4j.Logger;

public class RetourMessage {
	
	private static final Logger LOG = Logger.getLogger(RetourMessage.class);

	private long datecreation;
	private int id,idemetteur;
	public final static int PLUS_SON_AMI=-1;
	public final static int NON_AUTORISE=-2;
	public static final int PLUS_INSCRIT = -3;
	

	public RetourMessage(long datecreation, int id,int  idemetteur) {
		super();
		this.datecreation = datecreation;
		this.id = id;
		this.idemetteur=idemetteur;
	}

	public RetourMessage(){
		
		
	}
	public long getDatecreation() {
		return datecreation;
	}


	public void setDatecreation(long datecreation) {
		this.datecreation = datecreation;
	}

	public int getIdemetteur() {
		return idemetteur;
	}

	public void setIdemetteur(int idemetteur) {
		this.idemetteur = idemetteur;
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
