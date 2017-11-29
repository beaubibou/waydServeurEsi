package website.metier;

import org.apache.log4j.Logger;

public class TypeEtatMessage {
	
	private static final Logger LOG = Logger.getLogger(TypeEtatMessage.class);
	   
	public final static int LU = 0, NONLU = 1, TOUS=3;

	public TypeEtatMessage(int id, String libelle) {
		super();
		this.id = id;
		this.libelle = libelle;
	}

	int id;
	String libelle;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}
