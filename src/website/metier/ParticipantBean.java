package website.metier;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class ParticipantBean {
	private static final Logger LOG = Logger.getLogger(ParticipantBean.class);
	   
	int id;
	private String pseudo;
	private int nbravis;
	private int sexe;
	private double note;
	private String photostr;
	private String age;
	private int typeuser;
	private boolean admin;

	public ParticipantBean() {

	}

	public ParticipantBean(int id, String pseudo, int nbravis, int sexe,
			double note, String photostr, Date datenaissance,
			boolean afficheage, boolean affichesexe,int typeuser,boolean admin) {
		super();
		this.id = id;
		this.pseudo = pseudo;
		this.nbravis = nbravis;
		this.sexe = sexe;
		this.note = note;
		this.photostr = photostr;
		this.typeuser=typeuser;
		this.admin=admin;
		this.age = getAgeStr(datenaissance, afficheage);
		if (affichesexe)
			this.sexe = 3;
	}

	public String getTypeUserHTML() {

		if (isAdmin())
			return "<span style='color: green;'	class='glyphicon glyphicon-king'></span>";

		if (typeuser == TypeUser.PRO)
			return "<span style='color: blue;'	class='glyphicon glyphicon-usd'></span>";

		if (typeuser == TypeUser.WAYDEUR)
			return "<span style='color: black;'	class='glyphicon glyphicon-user'></span>";

		return "";

	}

	private boolean isAdmin() {
		// TODO Auto-generated method stub
		return admin;
	}

	public String getAgeStr(Date datenaissance, boolean afficheage) {
		if (afficheage)
			return "Non communiqu�";
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
				return "Pas d'age indiqu�";
			return Integer.toString(yeardiff) + " ans";
		}
		return "Pas d'age indiqu�";
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
