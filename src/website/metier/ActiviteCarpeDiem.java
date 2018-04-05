package website.metier;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActiviteCarpeDiem {

	String startDate;
	String endDate;
	String description;
	String urlCarpeDiem;
	String name;
	String address;
	String nomLieu;
	String ville;
	double lat, lng;
	String idEventFaceBook;
	int id;
	String lienFaceBook;
	private String fulldescription;
	private String urlPhotoFB;
	private String idactiviteFB;

	public String getIdactiviteFB() {
		return idactiviteFB;
	}

	public void setIdactiviteFB(String idactiviteFB) {
		this.idactiviteFB = idactiviteFB;
	}

	public String getUrlPhotoFB() {
		return urlPhotoFB;
	}

	public void setUrlPhotoFB(String urlPhotoFB) {
		this.urlPhotoFB = urlPhotoFB;
	}

	public String getIdEventFaceBook() {
		return idEventFaceBook;
	}

	public void setIdEventFaceBook(String idEventFaceBook) {
		this.idEventFaceBook = idEventFaceBook;
	}

	public String getLienFaceBook() {
		return "https://www.facebook.com/"+idEventFaceBook;
	}

	public void setLienFaceBook(String lienFaceBook) {
		this.lienFaceBook = lienFaceBook;
	}

	public String getFulldescription() {
		return fulldescription;
	}

	public void setFulldescription(String fulldescription) {
		this.fulldescription = fulldescription;
	}

	public ActiviteCarpeDiem(String startDate, String endDate, String image,
			String description, String url, String name, String address,
			String nomLieu, String ville, double lat, double lng, int id) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
		this.urlCarpeDiem = url;
		this.name = name;
		this.address = address;
		this.nomLieu = nomLieu;
		this.ville = ville;
		this.lat = lat;
		this.lng = lng;
		this.id = id;
	}

	public ActiviteCarpeDiem() {
		this.startDate = null;
		this.endDate = null;
		this.description = null;
		this.urlCarpeDiem = null;
		this.name = null;
		this.address = null;
		this.nomLieu = null;
		this.ville = null;
		this.lat = 0;
		this.lng = 0;
		this.id = 0;
	}

	public void reset() {
		this.startDate = null;
		this.endDate = null;

		this.description = null;
		this.urlCarpeDiem = null;
		this.name = null;
		this.address = null;
		this.nomLieu = null;
		this.ville = null;
		this.lat = 0;
		this.lng = 0;
		this.id = 0;

	}

	public ActiviteCarpeDiem(ActiviteCarpeDiem activite) {
		this.startDate = activite.startDate;
		this.endDate = activite.endDate;
		this.description = activite.description;
		this.urlCarpeDiem = activite.urlCarpeDiem;
		this.name = activite.name;
		this.address = activite.address;
		this.nomLieu = activite.nomLieu;
		this.ville = activite.ville;
		this.lat = activite.lat;
		this.lng = activite.lng;
		this.id = activite.id;

	}

	public ActiviteCarpeDiem(String datedebut, String datefin, String image,
			String fulldescription, String titre, String adresseTotal,
			String nomLieu, String ville, Double lat, Double lng,
			String idactiviteFB, String ulrPhotoFB, String idEvent) {

		this.startDate = datedebut;
		this.endDate = datefin;
		this.fulldescription = fulldescription;
		this.name = titre;
		this.address = adresseTotal;
		this.nomLieu = nomLieu;
		this.ville = ville;
		this.lat = lat;
		this.lng = lng;
		this.urlPhotoFB = ulrPhotoFB;
		this.idactiviteFB = idactiviteFB;
		this.idEventFaceBook=idEvent;

	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getStartDate() {
		return startDate;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {

		if (fulldescription.length() < 40)
			return fulldescription;

		return fulldescription.substring(0, 38);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrlCarpeDiem() {
		return urlCarpeDiem;
	}

	public void setUrlCarpeDiem(String url) {
		this.urlCarpeDiem = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {

		if (this.name == null) {
			this.name = name;

		} else {
			this.nomLieu = name;

		}

	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNomLieu() {
		return nomLieu;
	}

	public void setNomLieu(String nomLieu) {
		this.nomLieu = nomLieu;
	}

	public boolean isComplete() {

		if (address != null && nomLieu != null)
			return true;

		return false;
	}

	@Override
	public String toString() {
		return "ActiviteCarpeDiem [startDate=" + startDate + ", endDate="
				+ endDate + ", description=" + description + ", urlCarpeDiem="
				+ urlCarpeDiem + ", name=" + name + ", address=" + address
				+ ", nomLieu=" + nomLieu + ", ville=" + ville + ", lat=" + lat
				+ ", lng=" + lng + ", idEventFaceBook=" + idEventFaceBook
				+ ", id=" + id + ", lienFaceBook=" + lienFaceBook
				+ ", fulldescription=" + fulldescription + ", urlPhotoFB="
				+ urlPhotoFB + ", idactiviteFB=" + idactiviteFB + "]";
	}

	public Date getDateDebut() throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		return formatter.parse(startDate);

	}

	public Date getDateFin() throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		return formatter.parse(endDate);

	}

}
