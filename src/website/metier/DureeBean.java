package website.metier;

import org.apache.log4j.Logger;

public class DureeBean {
	int value;// exprimee en minutes
	String libelle;
	private static final Logger LOG = Logger.getLogger(DureeBean.class);
	   
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

	public DureeBean(int value, String libelle) {
		super();
		this.value = value;
		this.libelle = libelle;
	}

}
