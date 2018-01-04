package website.metier;

import org.apache.log4j.Logger;

public class TypeEtatValide {

	private static final Logger LOG = Logger.getLogger(TypeEtatValide.class);

	public final static int VALIDE = 0, EN_ATTENTE = 1;

	public static final int TOUS = 2;

	public TypeEtatValide(int id, String libelle) {
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
