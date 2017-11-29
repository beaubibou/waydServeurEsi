package website.metier;

import org.apache.log4j.Logger;

public class QuandBean {

	private static final Logger LOG = Logger.getLogger(QuandBean.class);
 	int value;
	String libelle;

	public QuandBean(int value, String libelle) {
		super();
		this.value = value;
		this.libelle = libelle;
	}

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

}
