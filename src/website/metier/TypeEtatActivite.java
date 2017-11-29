package website.metier;

import org.apache.log4j.Logger;

public class TypeEtatActivite {
	private static final Logger LOG = Logger.getLogger(TypeEtatActivite.class);
	   
	public final static int TOUTES = 0, ENCOURS = 1, TERMINEE = 2,PLANIFIEE=3;

	public TypeEtatActivite(int id, String libelle) {
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
