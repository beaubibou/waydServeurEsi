package website.metier.admin;

import java.util.Date;

public class FitreAdminSuggestions {

	Date dateDebutCreation;
	Date dateFinCreation;
	int etatSuggestion=EtatSuggestion.TOUS;
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
	public int getEtatSuggestion() {
		return etatSuggestion;
	}
	public void setEtatSuggestion(int etatSuggestion) {
		this.etatSuggestion = etatSuggestion;
	}
	
	
}
