package website.metier;

import org.apache.log4j.Logger;

public class TypeUser {
	private static final Logger LOG = Logger.getLogger(TypeUser.class);
	   
	public final static int TOUS = 0, PRO = 1, WAYDEUR = 3,ADMIN=10;
	
	int id;
	String libelle;
	public TypeUser(int id, String libelle) {
		super();
		this.id = id;
		this.libelle = libelle;
	}
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
