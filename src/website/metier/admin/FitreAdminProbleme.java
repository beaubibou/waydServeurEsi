package website.metier.admin;

import java.util.Date;

public class FitreAdminProbleme {

	Date dateDebutCreation;
	Date dateFinCreation;
	int etatProbleme = EtatProbleme.TOUS;

	public FitreAdminProbleme() {
	
	dateDebutCreation = new Date();
	dateFinCreation = new Date();

	}

	public Date getDateDebutCreation() {
		return dateDebutCreation;
	}

	public void setDateDebutCreation(Date dateDebutCreation) {
		this.dateDebutCreation = dateDebutCreation;
	}

	public Date getDateFinCreation() {
		return dateFinCreation;
	}

	public void setDateFinCreation(Date dateFinCreation) {
		this.dateFinCreation = dateFinCreation;
	}

	public int getEtatProbleme() {
		return etatProbleme;
	}

	public void setEtatProbleme(int etatProbleme) {
		this.etatProbleme = etatProbleme;
	}

}
