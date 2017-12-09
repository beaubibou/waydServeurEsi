package website.metier;

import java.util.Date;

import org.apache.log4j.Logger;

public class ProblemeBean {
	private static final Logger LOG = Logger.getLogger(ProblemeBean.class);
	   
	private int id;
	private String probleme;
	private String email;
	private Date dateCreation;
	private boolean lu;
	private String pseudo;
	
	public ProblemeBean(int id, String probleme, String email,String pseudo, Date dateCreation,boolean lu) {
		super();
		this.id = id;
		this.probleme = probleme;
		this.email = email;
		this.dateCreation = dateCreation;
		this.lu=lu;
		this.pseudo=pseudo;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
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
	
	public String getCheckHtml() {
		
		return "<input type='checkbox' id='moncheck'>";
	}
	
	public String getLuHtml(String lienLecture) {
			
			String retour="";
			if (!lu)
			{
			retour="<button id='"+lienLecture+"' name='lireMessage' type='button' >"
					+ "	<span class='glyphicon glyphicon-unchecked'></span>	</button>";
			}
			else
			{
				retour="<button id='noaction' name='noaction' type='button' >"
						+ "	<span class='glyphicon glyphicon-check'></span>	</button>";
			
			}
			
			return retour;
		}
	
}
	

