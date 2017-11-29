package website.metier;

import org.apache.log4j.Logger;

public class TypeSignalement {
	
	private static final Logger LOG = Logger.getLogger(TypeSignalement.class);
	   
	public static final int TOUS=0,MOINSDE10=1,PLUSDE10=2,AUMOINSUNE=3;

	
	
public TypeSignalement(int id, String libelle) {
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
