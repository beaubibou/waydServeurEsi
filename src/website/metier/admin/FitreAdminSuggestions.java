package website.metier.admin;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

public class FitreAdminSuggestions {

	private static final Logger LOG = Logger
			.getLogger(FitreAdminSuggestions.class);
	DateTime dateDebutCreation;
	DateTime dateFinCreation;
	int etatSuggestion = EtatSuggestion.TOUS;

	public FitreAdminSuggestions() {

		dateDebutCreation = new DateTime().withHourOfDay(0).withMinuteOfHour(0)
				.withSecondOfMinute(0).withMillisOfSecond(00);

		dateFinCreation = new DateTime().withHourOfDay(23).withMinuteOfHour(59)
				.withSecondOfMinute(59);

	}

	public DateTime getDateDebutCreation() {
		
		return dateDebutCreation;
	}

	public void setDateDebutCreation(DateTime dateDebutCreation) {
		this.dateDebutCreation = dateDebutCreation;
	}

	public DateTime getDateFinCreation() {
		
		return dateFinCreation.withHourOfDay(23).withMinuteOfHour(59)
				.withSecondOfMinute(59);
	}

	public void setDateFinCreation(DateTime dateFinCreation) {
					
		this.dateFinCreation = dateFinCreation;
	}

	public int getEtatSuggestion() {
		return etatSuggestion;
	}

	public void setEtatSuggestion(int etatSuggestion) {
		this.etatSuggestion = etatSuggestion;
	}

}
