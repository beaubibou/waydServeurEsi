package website.metier;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.axis.encoding.Base64;
import org.apache.log4j.Logger;

import texthtml.pro.Erreur_HTML;
import wayde.bean.Parametres;
import website.dao.CacheValueDAO;
import website.enumeration.TypePhoto;
import website.html.MessageAlertDialog;

public class LogBean {
	private static final Logger LOG = Logger.getLogger(LogBean.class);
	   
	public final static int PRO = 1;
	public final static int ASSOCIATION = 2;
	public final static int WAYDEUR = 3;
	private int id;
	private  Date date_log;
	private String message;
	private String niveau_log;
	private String location;
	public int getId() {
		return id;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LogBean(int id, Date date_log, String message, String niveau_log,String location) {
		super();
		this.id = id;
		this.date_log = date_log;
		this.message = message;
		this.niveau_log = niveau_log;
		this.location=location;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate_log() {
		return date_log;
	}
	public void setDate_log(Date date_log) {
		this.date_log = date_log;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getNiveau_log() {
		return niveau_log;
	}
	public void setNiveau_log(String niveau_log) {
		this.niveau_log = niveau_log;
	} 
	

}
