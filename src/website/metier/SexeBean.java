package website.metier;

import org.apache.log4j.Logger;

public class SexeBean {
	private static final Logger LOG = Logger.getLogger(SexeBean.class);
	   
int id;
String libelle;
public SexeBean(int id, String libelle) {
	super();
	this.id = id;
	this.libelle = libelle;
}
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
