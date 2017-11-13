package website.metier;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageBean {

	int id;
	String nomEmetteur;
	Date dateCreation;
	String message;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomEmetteur() {
		return nomEmetteur;
	}
	public void setNomEmetteur(String nomEmetteur) {
		this.nomEmetteur = nomEmetteur;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCheckHtml() {

	
			return "<input type='checkbox' id='moncheck'>";
		
	}
	
	
	public MessageBean(int id, String nomEmetteur, Date dateCreation,
			String message) {
		super();
		this.id = id;
		this.nomEmetteur = nomEmetteur;
		this.dateCreation = dateCreation;
		this.message = message;
	}
	public String getDateCreationHtml() {

//		SimpleDateFormat jour = new SimpleDateFormat("dd-MM-yyyy");
//		String datestrdebut = jour.format(datedebut);
//		SimpleDateFormat formatHeure = new SimpleDateFormat("HH:mm");
//		String heuredebutstr = formatHeure.format(datedebut);
//		String heurefinstr = formatHeure.format(datefin);

		return "horaire test";
	}

}
