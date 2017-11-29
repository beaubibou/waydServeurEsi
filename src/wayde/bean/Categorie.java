package wayde.bean;

import org.apache.log4j.Logger;

import wayde.beandatabase.CategorieDb;

public class Categorie {
	private static final Logger LOG = Logger.getLogger(Categorie.class);
	long id;
	String nom;

	public Categorie(CategorieDb categoriedb) {

		this.id = categoriedb.getId();
		this.nom = categoriedb.getNom();
	}

	public Categorie() {

	}

	public long getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

}
