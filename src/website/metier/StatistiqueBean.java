package website.metier;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

public class StatistiqueBean implements Comparable<StatistiqueBean>{
	private static final Logger LOG = Logger.getLogger(StatistiqueBean.class);
	   
	private Date date;
	private String dateStr;
	private int valeur;
	private  String lFormatTemplate = "dd-MM-yyyy";
	
	
	public StatistiqueBean(String dateStr, int valeur) {
		super();
		
		SimpleDateFormat lDateFormat = new SimpleDateFormat(lFormatTemplate);
		this.dateStr = dateStr;
		this.valeur = valeur;
		try {
			date= lDateFormat.parse(dateStr);
		} catch (ParseException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
		}
			
	
	}



	public Date getDate() {
		return date;
	}



	public void setDate(Date date) {
		this.date = date;
	}



	public String getDateStr() {
		return dateStr;
	}



	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}



	public int getValeur() {
		return valeur;
	}



	public void setValeur(int valeur) {
		this.valeur = valeur;
	}



	@Override
	public int compareTo(StatistiqueBean o) {
		
	
	
		return this.date.compareTo(o.getDate());
		
	}
	
	
}
