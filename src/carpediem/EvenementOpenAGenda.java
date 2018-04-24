package carpediem;

import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;

import wayde.dao.ActiviteDAO;

public class EvenementOpenAGenda {

	String idagenda;
	String uidEvent;
	String titre;
	String libelle;
	String adresseTotal;
	double latitude = 0;
	double longitude = 0;
	String image;
	String lienurl;
	String ville;
	String description;
	String freetext;
	String nomLieu;
	ArrayList<DateEvenementOpenAgenda> listActivite = new ArrayList<>();

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFreetext() {
		return freetext;
	}

	public void setFreetext(String freetext) {
		this.freetext = freetext;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getIdagenda() {
		return idagenda;
	}

	public void setIdagenda(String idagenda) {
		this.idagenda = idagenda;
	}

	public String getUidEvent() {
		return uidEvent;
	}

	public void setUidEvent(String uidEvent) {
		this.uidEvent = uidEvent;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getAdresseTotal() {
		return adresseTotal;
	}

	public void setAdresseTotal(String adresseTotal) {
		this.adresseTotal = adresseTotal;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLienurl() {
		return lienurl;
	}

	public void setLienurl(String lienurl) {
		this.lienurl = lienurl;
	}

	public ArrayList<DateEvenementOpenAgenda> getListActivite() {
		return listActivite;
	}

	public void setListActivite(ArrayList<DateEvenementOpenAgenda> listActivite) {
		this.listActivite = listActivite;
	}

	public EvenementOpenAGenda(String idagenda, String uidEvent, String titre,
			String description, String freetext, String adresseTotal,
			double latitude, double longitude, String image, String lienurl,
			String ville, String nomLieu) {

		this.idagenda = idagenda;
		this.uidEvent = uidEvent;
		this.titre = titre;
		this.adresseTotal = adresseTotal;
		this.latitude = latitude;
		this.longitude = longitude;
		this.image = image;
		this.lienurl = lienurl;
		this.ville = ville;
		this.description = description;
		this.freetext = freetext;
		this.nomLieu = nomLieu;

	}

	public String getNomLieu() {
		return nomLieu;
	}

	public void setNomLieu(String nomLieu) {
		this.nomLieu = nomLieu;
	}

	public void ajouteEvenement(Date dateDebut, Date dateFin) {
		// if (valideActivite(dateDebut, dateFin))
		listActivite.add(new DateEvenementOpenAgenda(dateDebut, dateFin));
	}

	public static boolean valideActivite(Date dateDebut, Date dateFin) {

		DateTime maitenant = new DateTime().withHourOfDay(0)
				.withMinuteOfHour(0).withSecondOfMinute(0)
				.withMillisOfSecond(00);

		if (dateFin.before(maitenant.toDate()))
			return false;

		long Heure = 3600000;

		if (dateDebut.after(maitenant.plusDays(3).toDate()))
			return false;

		if (dateFin.getTime() - dateDebut.getTime() > 24 * Heure)
			return false;

		else

			return true;

	}

	public void ajouteDAO() {

		website.dao.ActiviteDAO.ajouteEvenementOpenAgenda(this);

	}
	
	public void ajouteDAO(Date debut,Date fin) {

		website.dao.ActiviteDAO.ajouteEvenementMapado(this,debut,fin);

	}

	public boolean isExistEvenement(Date dateDebut, Date dateFin) {

		for (DateEvenementOpenAgenda dateEvenementOpenAgenda : listActivite) {
			if (dateEvenementOpenAgenda.egale(dateDebut, dateFin))
				return true;

		}

		return false;
	}

	public void supprimeEvtExisant(Date dateDebut, Date dateFin) {

		if (isExistEvenement(dateDebut, dateFin)) {
			int index = getIndexEvenemt(dateDebut, dateFin);
			listActivite.remove(index);
			System.out.println("remonve indewx" + index);
		}
	}

	private int getIndexEvenemt(Date dateDebut, Date dateFin) {

		for (int f = 0; f < listActivite.size(); f++) {
			if (listActivite.get(f).egale(dateDebut, dateFin))
				return f;

		}

		return -1;

	}

}
