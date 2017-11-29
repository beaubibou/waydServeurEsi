package website.metier;

import java.util.Date;

import org.apache.log4j.Logger;

public class SuggestionBean {
	private static final Logger LOG = Logger.getLogger(SuggestionBean.class);
	   
	private int id, idPersonne;
	private String pseudo;
	private String suggestion;
	private Date dateCreation;
	private boolean lu;
	
	
	public SuggestionBean(int id, int idPersonne, String suggestion,
			Date dateCreation,String pseudo,boolean lu) {
		super();
		this.id = id;
		this.idPersonne = idPersonne;
		this.suggestion = suggestion;
		this.dateCreation = dateCreation;
		this.pseudo=pseudo;
		this.lu=lu;
	}
	public int getId() {
		return id;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdPersonne() {
		return idPersonne;
	}
	public void setIdPersonne(int idPersonne) {
		this.idPersonne = idPersonne;
	}
	public String getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	
	
	public String getDateCreationStr() {
		return Outils.getStringWsFromDate(dateCreation);
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
