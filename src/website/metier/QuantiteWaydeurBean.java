package website.metier;

import org.apache.log4j.Logger;

public class QuantiteWaydeurBean {
	private static final Logger LOG = Logger.getLogger(QuantiteWaydeurBean.class);
	   
	int value;
 String libelle;
public int getValue() {
	return value;
}
public void setValue(int value) {
	this.value = value;
}
public String getLibelle() {
	return libelle;
}
public void setLibelle(String libelle) {
	this.libelle = libelle;
}
public QuantiteWaydeurBean(int value, String libelle) {
	super();
	this.value = value;
	this.libelle = libelle;
}
 
}
