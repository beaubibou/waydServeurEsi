package website.metier;

import org.apache.log4j.Logger;

public class RayonBean {

	private static final Logger LOG = Logger.getLogger(RayonBean.class);
	   
	private int value;
	private String libelle;
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
	public RayonBean(int value, String libelle) {
		super();
		this.value = value;
		this.libelle = libelle;
	}
	
	
}
