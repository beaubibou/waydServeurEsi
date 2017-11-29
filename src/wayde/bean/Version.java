package wayde.bean;

import org.apache.log4j.Logger;

public class Version {
	private static final Logger LOG = Logger.getLogger(Version.class);

	int version,majeur,mineur;

	public Version(int version, int majeur, int mineur) {
		super();
		this.version = version;
		this.majeur = majeur;
		this.mineur = mineur;
	}
	
	public Version(){
		
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getMajeur() {
		return majeur;
	}

	public void setMajeur(int majeur) {
		this.majeur = majeur;
	}

	public int getMineur() {
		return mineur;
	}

	public void setMineur(int mineur) {
		this.mineur = mineur;
	}
	
	
}
