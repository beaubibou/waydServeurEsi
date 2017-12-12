package website.metier.admin;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

public class FitreAdminProbleme {
	private static final Logger LOG = Logger.getLogger(FitreAdminProbleme.class);
	   
	DateTime dateDebutCreation;
	DateTime dateFinCreation;
	int etatProbleme = EtatProbleme.TOUS;

	public FitreAdminProbleme() {

		
		dateDebutCreation = new DateTime() .withHourOfDay(0)
			    .withMinuteOfHour(0)
			    .withSecondOfMinute(0)
			    .withMillisOfSecond(00);
	
		
		dateFinCreation = new DateTime() .withHourOfDay(23)
			    .withMinuteOfHour(59)
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



	public int getEtatProbleme() {
		return etatProbleme;
	}

	public void setEtatProbleme(int etatProbleme) {
		this.etatProbleme = etatProbleme;
	}



}
