package website.metier;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageBean {

	private int id;
	private String nomEmetteur;
	private Date dateCreation;
	private String message;
	private boolean lu;
	private boolean emis;
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
	
	
	public boolean isLu() {
		return lu;
	}
	public void setLu(boolean lu) {
		this.lu = lu;
	}
	public boolean isEmis() {
		return emis;
	}
	public void setEmis(boolean emis) {
		this.emis = emis;
	}
	public MessageBean(int id, String nomEmetteur, Date dateCreation,
			String message,boolean lu,boolean emis) {
		super();
		this.id = id;
		this.nomEmetteur = nomEmetteur;
		this.dateCreation = dateCreation;
		this.message = message;
		this.lu=lu;
		this.emis=emis;
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
