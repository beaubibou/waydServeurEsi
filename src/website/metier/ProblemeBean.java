package website.metier;

import java.util.Date;

public class ProblemeBean {

	private int id;
	private String probleme;
	private String email;
	private Date dateCreation;
		
	
	public ProblemeBean(int id, String probleme, String email, Date dateCreation) {
		super();
		this.id = id;
		this.probleme = probleme;
		this.email = email;
		this.dateCreation = dateCreation;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProbleme() {
		return probleme;
	}
	public void setProbleme(String probleme) {
		this.probleme = probleme;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public String getDateCreationStr() {
		return Outils.getStringWsFromDate(dateCreation);
		}
	
	
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	
	
}
