package website.metier;

import org.apache.log4j.Logger;

public class TypeActiveActivite {
	private static final Logger LOG = Logger.getLogger(TypeActiveActivite.class);
	   
	public final static int TOUTES = 0, ACTIVE = 1, INACTIVE = 2;

	public TypeActiveActivite(int id, String libelle) {
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
