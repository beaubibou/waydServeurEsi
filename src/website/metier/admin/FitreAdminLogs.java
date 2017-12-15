package website.metier.admin;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import website.metier.TypeEtatLogs;

public class FitreAdminLogs {
	private static final Logger LOG = Logger.getLogger(FitreAdminLogs.class);

	DateTime dateCreationDebut;
	DateTime dateCreationFin;
	int niveau_log;
	String methode;

	public String getMethode() {

		if (methode == null || methode.isEmpty())
			return "";
		return methode;
	}
	

	public void setMethode(String methode) {
		this.methode = methode;
	}

	public FitreAdminLogs() {

		dateCreationDebut = new DateTime().withHourOfDay(0).withMinuteOfHour(0)
				.withSecondOfMinute(0).withMillisOfSecond(00);

		dateCreationFin = new DateTime().withHourOfDay(23).withMinuteOfHour(59)
				.withSecondOfMinute(59);

		niveau_log = TypeEtatLogs.TOUTES;

		methode = null;

	}

	public DateTime getDateCreationDebut() {
		return dateCreationDebut;
	}

	public void setDateCreationDebut(DateTime dateCreationDebut) {
		this.dateCreationDebut = dateCreationDebut;
	}

	public DateTime getDateCreationFin() {

		return dateCreationFin.withHourOfDay(23).withMinuteOfHour(59)
				.withSecondOfMinute(59);
	}

	public void setDateCreationFin(DateTime dateCreationFin) {
		this.dateCreationFin = dateCreationFin;
	}

	public int getNiveau_log() {
		return niveau_log;
	}

	public void setNiveau_log(int niveau_log) {
		this.niveau_log = niveau_log;
	}


	public void setLogLevel(String log_level) {
		// TODO Auto-generated method stub
		switch (log_level) {
		case "INFO":
			niveau_log = TypeEtatLogs.INFO;
			break;
		case "WARN":
			niveau_log = TypeEtatLogs.WARNING;
			break;
			
		case "ERROR":
			niveau_log = TypeEtatLogs.ERROR;
			break;
			
		case "DEBUG":
			niveau_log = TypeEtatLogs.DEBUG;
			break;


		default:
			break;
		}
		
		
	}

}
