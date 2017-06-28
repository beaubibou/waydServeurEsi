package wayde.bean;

import wayde.beandatabase.CategorieDb;

public class Categorie {
long id;
String nom;
	
public Categorie( CategorieDb categoriedb){
	
	
	this.id=categoriedb.getId();
	this.nom=categoriedb.getNom();
}

public Categorie( ){
	
	
	
}

public long getId() {
	return id;
}

public String getNom() {
	return nom;
}


}
