package website.metier;

import org.apache.log4j.Logger;

public class TypeEtatLogs {
	private static final Logger LOG = Logger.getLogger(TypeEtatLogs.class);
	   
	public final static int TOUTES = 0, INFO = 1, WARNING = 2,DEBUG=3,ERROR=4;

	public TypeEtatLogs(int id, String libelle) {
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
