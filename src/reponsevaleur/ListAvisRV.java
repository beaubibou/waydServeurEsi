package reponsevaleur;

import java.util.ArrayList;

import wayde.bean.Avis;

public class ListAvisRV {

	private Erreur[] erreurs;
	private Avis[] avis;

	public ListAvisRV() {
		super();
	}

	public Erreur[] getErreurs() {
		return erreurs;
	}

	public void setErreurs(Erreur[] erreurs) {
		this.erreurs = erreurs;
	}

	

	public void initErreurs(ArrayList<Erreur> listErreurs) {

	erreurs=	(Erreur[]) listErreurs.toArray(new Erreur[listErreurs
		                           					.size()]);
	}

	public void initAvis(ArrayList<Avis> list) {
	avis=	(Avis[]) list.toArray(new Avis[list.size()]);
	}
}
