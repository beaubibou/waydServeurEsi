package website.metier;

import org.apache.log4j.Logger;

public class TypeEtatLogPerf {
	private static final Logger LOG = Logger.getLogger(TypeEtatLogPerf.class);
	   
	public final static int ACTIVE = 0,DESACTIVE  = 1;

	public TypeEtatLogPerf(int id, String libelle) {
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
