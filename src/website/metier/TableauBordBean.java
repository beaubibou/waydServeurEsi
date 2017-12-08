package website.metier;

public class TableauBordBean {

	private int idpersonne;
	private int nbrFini;
	private int nbrPlanifiee;
	private int nbrEnCours;

	public TableauBordBean(int idpersonne, int nbrFini, int nbrPlanifiee,
			int nbrEnCours) {
		// TODO Auto-generated constructor stub
	this.idpersonne=idpersonne;
	this.nbrFini=nbrFini;
	this.nbrPlanifiee=nbrPlanifiee;
	this.nbrEnCours=nbrEnCours;
	
	}

	public int getIdpersonne() {
		return idpersonne;
	}

	public void setIdpersonne(int idpersonne) {
		this.idpersonne = idpersonne;
	}

	public int getNbrFini() {
		return nbrFini;
	}

	public void setNbrFini(int nbrFini) {
		this.nbrFini = nbrFini;
	}

	public int getNbrPlanifiee() {
		return nbrPlanifiee;
	}

	public void setNbrPlanifiee(int nbrPlanifiee) {
		this.nbrPlanifiee = nbrPlanifiee;
	}

	public int getNbrEnCours() {
		return nbrEnCours;
	}

	public void setNbrEnCours(int nbrEnCours) {
		this.nbrEnCours = nbrEnCours;
	}

	@Override
	public String toString() {
		return "TableauBordBean [idpersonne=" + idpersonne + ", nbrFini="
				+ nbrFini + ", nbrPlanifiee=" + nbrPlanifiee + ", nbrEnCours="
				+ nbrEnCours + "]";
	}
	

}
