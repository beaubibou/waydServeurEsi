package reponsevaleur;

import java.util.ArrayList;

import wayde.bean.Version;

public class VersionRV {

	private Erreur[] erreurs;
	private Version version;


	public void initErreurs(ArrayList<Erreur> listErreurs) {
		erreurs = (Erreur[]) listErreurs
				.toArray(new Erreur[listErreurs.size()]);
	}
	
	public VersionRV(){
		
	}

	public void initVersion(Version version) {
		this.version = version;
	}

	public Erreur[] getErreurs() {
		return erreurs;
	}

	public void setErreurs(Erreur[] erreurs) {
		this.erreurs = erreurs;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}


	

	
	

}
