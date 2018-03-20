package reponsevaleur;

import java.util.ArrayList;

import wayde.bean.Ami;

public class ListAmiRV {

	private Erreur[] erreurs;
	private Ami[] amis;

	public ListAmiRV() {
		super();
	}

	public Erreur[] getErreurs() {
		return erreurs;
	}

	public void setErreurs(Erreur[] erreurs) {
		this.erreurs = erreurs;
	}

	public Ami[] getAmi() {
		return amis;
	}

	public void setAmi(Ami[] ami) {
		this.amis = ami;
	}

	public void initErreurs(ArrayList<Erreur> listErreurs) {

	erreurs=	(Erreur[]) listErreurs.toArray(new Erreur[listErreurs
		                           					.size()]);
	}

	public void initAmis(ArrayList<Ami> list) {
	amis=	(Ami[]) list.toArray(new Ami[list.size()]);
	}
}
