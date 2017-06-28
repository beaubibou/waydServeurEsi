package website.metier;

import java.util.Date;

public class SuggestionBean {

	private int id, idPersonne;
	private String pseudo;
	private String suggestion;
	private Date dateCreation;
	
	
	
	public SuggestionBean(int id, int idPersonne, String suggestion,
			Date dateCreation,String pseudo) {
		super();
		this.id = id;
		this.idPersonne = idPersonne;
		this.suggestion = suggestion;
		this.dateCreation = dateCreation;
		this.pseudo=pseudo;
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
	
	
}
