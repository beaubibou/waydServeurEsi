package website.metier;

import org.apache.log4j.Logger;

public class CountLogInfo {
	private static final Logger LOG = Logger.getLogger(CountLogInfo.class);
	private String date;
	private String log_level;
	private int nbr;
	public CountLogInfo(String date, String log_level, int nbr) {
		super();
		this.date = date;
		this.log_level = log_level;
		this.nbr = nbr;
	}
	
	public String getCouleur(){
		
		switch( log_level){
		
		case "INFO":
			return "#00ffff";
		case "WARN":
			return "orange";
		case "ERROR":
			return "#f90606";
			
		}
		return "white";
		
	}
	public String getLienHtlm(){
		
	String lien="StatLog?action=detailStat&datemessage="+date+ "&log_level="+log_level;
	
	return lien;
	}

	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getLog_level() {
		return log_level;
	}
	public void setLog_level(String log_level) {
		this.log_level = log_level;
	}
	public int getNbr() {
		return nbr;
	}
	public void setNbr(int nbr) {
		this.nbr = nbr;
	}
	

}
