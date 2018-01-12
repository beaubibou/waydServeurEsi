package reponsevaleur;

import java.util.ArrayList;

import wayde.bean.TypeActivite;

public class ListTypeActiviteRV {

	private Erreur[] erreurs;
	private TypeActivite[] typeActivites;

	public ListTypeActiviteRV() {
		super();
	}

	public Erreur[] getErreurs() {
		return erreurs;
	}

	public void setErreurs(Erreur[] erreurs) {
		this.erreurs = erreurs;
	}
	
	
	public TypeActivite[] getTypeActivites() {
		return typeActivites;
	}

	public void setTypeActivites(TypeActivite[] typeActivites) {
		this.typeActivites = typeActivites;
	}

	public void initErreurs(ArrayList<Erreur> listErreurs) {

	erreurs=(Erreur[]) listErreurs.toArray(new Erreur[listErreurs
		                           					.size()]);
	}

	public void initActivite(ArrayList<TypeActivite> list) {
		
	typeActivites=	(TypeActivite[]) list.toArray(new TypeActivite[list.size()]);
	
	}
}
