package website.metier;

import org.apache.log4j.Logger;

public class TypeGratuitActivite {
	private static final Logger LOG = Logger.getLogger(TypeGratuitActivite.class);
	   
	
	public static int GRATUIT = 1;
	public static int PAYANT = 2;
	public static int GRATUITE_INCONNU = 0;
	public static int TOUS = 3;
	
	public TypeGratuitActivite(int id, String libelle) {
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
