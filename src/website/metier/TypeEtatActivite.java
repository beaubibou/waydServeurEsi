package website.metier;

public class TypeEtatActivite {
	public final static int TOUTES = 0, ENCOURS = 1, TERMINEE = 2;

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
