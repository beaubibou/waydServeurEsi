package reponsevaleur;

import java.util.ArrayList;

import wayde.bean.Avis;

public class AvisRV {

	private Erreur[] erreurs;
	private Avis avis;

	public void initErreurs(ArrayList<Erreur> listErreurs) {

		erreurs = (Erreur[]) listErreurs
				.toArray(new Erreur[listErreurs.size()]);
	}
	public AvisRV(){
		
	}

	public void initAvis(Avis avis) {
		this.avis = avis;
	}

	public Erreur[] getErreurs() {
		return erreurs;
	}

	public void setErreurs(Erreur[] erreurs) {
		this.erreurs = erreurs;
	}

	public Avis getAvis() {
		return avis;
	}

	public void setAvis(Avis avis) {
		this.avis = avis;
	}


	
	

}
