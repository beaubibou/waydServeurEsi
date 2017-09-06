package wayde.bean;

public class Version {

	int versionn,majeur,mineur;

	public Version(int versionn, int majeur, int mineur) {
		super();
		this.versionn = versionn;
		this.majeur = majeur;
		this.mineur = mineur;
	}
	
	public Version(){
		
	}

	public int getVersionn() {
		return versionn;
	}

	public void setVersionn(int versionn) {
		this.versionn = versionn;
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
