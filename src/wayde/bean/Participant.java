package wayde.bean;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import texthtml.pro.Erreur_HTML;

public class Participant {
	private static final Logger LOG = Logger.getLogger(Participant.class);

	int id;
	private String pseudo;
	private int nbravis;
	private int sexe;
	private double note;
	private String photostr;
	private String age;

	public Participant() {

	}

	public Participant(int id, String pseudo, int nbravis, int sexe,
			double note, String photostr, Date datenaissance,
			boolean afficheage, boolean affichesexe) {
		super();
		this.id = id;
		this.pseudo = pseudo;
		this.nbravis = nbravis;
		this.sexe = sexe;
		this.note = note;
		this.photostr = photostr;
		this.age = getAgeStr(datenaissance, afficheage);
		if (affichesexe)
			this.sexe = 3;
	}

	public String getAgeStr(Date datenaissance, boolean afficheage) {
		if (afficheage)
			return Erreur_HTML.NON_COMMUNIQUE;
		if (datenaissance != null) {
			Calendar curr = Calendar.getInstance();
			Calendar birth = Calendar.getInstance();
			birth.setTime(datenaissance);
			int yeardiff = curr.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
			curr.add(Calendar.YEAR, -yeardiff);
			if (birth.after(curr)) {
				yeardiff = yeardiff - 1;
			}
			if (yeardiff < 0)
				return "Erreur";
			if (yeardiff == 0)
				return Erreur_HTML.PAS_AGE_INDIQUE;
			return Integer.toString(yeardiff) + " ans";
		}
		return Erreur_HTML.PAS_AGE_INDIQUE;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public int getNbravis() {
		return nbravis;
	}

	public void setNbravis(int nbravis) {
		this.nbravis = nbravis;
	}

	public int getSexe() {
		return sexe;
	}

	public void setSexe(int sexe) {
		this.sexe = sexe;
	}

	public double getNote() {
		return note;
	}

	public void setNote(double note) {
		this.note = note;
	}

	public String getPhotostr() {
		return photostr;
	}

	public void setPhotostr(String photostr) {
		this.photostr = photostr;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

}
