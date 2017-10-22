package website.metier;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.axis.encoding.Base64;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;

import servlet.DetailActivite;
import website.dao.CacheValueDAO;
import website.enumeration.TypePhoto;
import fcm.ServeurMethodes;

public class ActiviteBean {
	private static final Logger LOG = Logger.getLogger(ActiviteBean.class);
	int id;

	public static int GRATUIT = 1;
	public static int PAYANT = 2;

	String titre;

	String libelle;

	int idorganisateur;

	String datedebutStr;

	String datefinStr;

	String adresse;

	double latitude;

	double longitude;

	String photo;

	private String nomorganisateur;

	private String pseudo;

	private double note;

	private int typeactivite;

	private int nbrparticipant;

	public Date datedebut, datefin, datecreation;

	public boolean actif;

	public int role;

	public long finidans;

	private int nbmaxwaydeur;

	private int totalavis;

	private int sexe;

	private String urlPhoto;

	private int typeUser;

	private int typeAccess;

	private String libelleActivite;

	private double latRef, longRef; // Coordonnée du pont de recherche

	public int getTypeUser() {
		return typeUser;
	}

	public String getNomorganisateur() {
		return nomorganisateur;
	}

	public void setNomorganisateur(String nomorganisateur) {
		this.nomorganisateur = nomorganisateur;
	}

	public long getFinidans() {
		return finidans;
	}

	public boolean isOrganisteur(int idpersonne) {

		if (idpersonne == idorganisateur)
			return true;
		return false;

	}

	public void setFinidans(long finidans) {
		this.finidans = finidans;
	}

	public String getLibelleActivite() {
		return libelleActivite;
	}

	public void setLibelleActivite(String libelleActivite) {
		this.libelleActivite = libelleActivite;
	}

	public void setTypeUser(int typeUser) {
		this.typeUser = typeUser;
	}

	public int getTypeAccess() {
		return typeAccess;
	}

	public void setTypeAccess(int typeAccess) {
		this.typeAccess = typeAccess;
	}

	public String getUrlPhoto() {

		if (photo == null)
			photo = "";
		byte[] bytes = Base64.decode(photo);
		urlPhoto = "data:image/jpeg;base64," + Base64.encode(bytes);

		return urlPhoto;
	}

	public String isActiveStr() {
		if (isActive())
			return ("En cours");
		else
			return ("Terminée");

	}

	public boolean isActive() {
		if (datefin.after(new Date()))
			return true;
		else
			return false;

	}

	public void setUrlPhoto(String urlPhoto) {
		this.urlPhoto = urlPhoto;
	}

	private ArrayList<ParticipantBean> listParticipant = new ArrayList<ParticipantBean>();

	public String getDatefinStr() {
		return datefinStr;
	}

	public void setDatefinStr(String datefinStr) {
		this.datefinStr = datefinStr;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public int getNbmaxwaydeur() {
		return nbmaxwaydeur;
	}

	public void setNbmaxwaydeur(int nbmaxwaydeur) {
		this.nbmaxwaydeur = nbmaxwaydeur;
	}

	public ActiviteBean() {

	}

	public String getPhoto() {
		
			if (photo==null)
		return CacheValueDAO.getPhoto(TypePhoto.Inconnu);
		
		if (photo.equals("")){
				return CacheValueDAO.getPhoto(TypePhoto.Inconnu);
		}
		return photo;
		
		
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getSexe() {
		return sexe;
	}

	public void setSexe(int sexe) {
		this.sexe = sexe;
	}

	public ActiviteBean(String titre, String libelle, int idorganisateur,
			Date datedebut, Date datefin, int idtypeactivite, double latitude,
			double longitude, String adresse, boolean actif, int nbmaxwaydeur) {
		super();
		this.titre = titre;
		this.libelle = libelle;
		this.datedebut = datedebut;
		this.latitude = latitude;
		this.longitude = longitude;
		this.datefin = datefin;// Fin de l'alerte
		this.datecreation = new Date();
		this.typeactivite = idtypeactivite;
		this.nbmaxwaydeur = nbmaxwaydeur;
		this.nbrparticipant = 1;
		this.idorganisateur = idorganisateur;

	}

	public Date getDatedebut() {
		return datedebut;
	}

	public void setDatedebut(Date datedebut) {
		this.datedebut = datedebut;
	}

	public Date getDatefin() {
		return datefin;
	}

	public void setDatefin(Date datefin) {
		this.datefin = datefin;
	}

	public ActiviteBean(int id, String titre, String libelle,
			int idorganisateur, Date datedebut, Date datefin,
			int idtypeactivite, double latitude, double longitude,
			String adresse, String nom, String pseudo, String photo,
			double note, int role, boolean archive, int totalavis,
			Date datenaissance, int sexe, int nbrparticipant,
			boolean afficheage, boolean affichesexe, int nbmaxwaydeur) {

		super();
		this.id = id;
		this.titre = titre;
		this.libelle = libelle;
		this.idorganisateur = idorganisateur;
		this.latitude = latitude;
		this.longitude = longitude;
		this.datefinStr = Outils.getStringWsFromDate(datefin);
		this.datedebut = datedebut;
		this.datefin = datefin;
		this.datedebutStr = Outils.getStringWsFromDate(datedebut);
		this.nomorganisateur = nom;
		this.pseudo = pseudo;
		this.photo = photo;
		this.note = note;
		this.totalavis = totalavis;
		this.sexe = sexe;
		this.nbrparticipant = nbrparticipant;
		this.typeactivite = idtypeactivite;
		this.nbmaxwaydeur = nbmaxwaydeur;
		this.idorganisateur = idorganisateur;
		this.adresse = adresse;
		

	}

	public int getIdorganisateur() {
		return idorganisateur;
	}

	public void setIdorganisateur(int idorganisateur) {
		this.idorganisateur = idorganisateur;
	}

	public ActiviteBean(int id, String titre, String libelle,
			int idorganisateur, Date datedebut, Date datefin,
			int idtypeactivite, double latitude, double longitude, String nom,
			String pseudo, String photo, double note, int totalavis,
			Date datenaissance, int sexe, int nbrparticipant, int nbmaxwayd,
			int typeUser, int typeAcces, String libelleActivite, String adresse) {
		this.id = id;
		this.titre = titre;
		this.libelle = libelle;
		this.idorganisateur = idorganisateur;
		this.latitude = latitude;
		this.longitude = longitude;
		this.datefinStr = Outils.getStringWsFromDate(datefin);
		this.datedebut = datedebut;
		this.datefin = datefin;
		this.datedebutStr = Outils.getStringWsFromDate(datedebut);
		this.nomorganisateur = nom;
		this.pseudo = pseudo;
		this.photo = photo;
		this.note = note;
		this.totalavis = totalavis;
		this.sexe = sexe;
		this.nbrparticipant = nbrparticipant;
		this.typeactivite = idtypeactivite;
		this.nbmaxwaydeur = nbmaxwayd;
		this.typeAccess = typeAcces;
		this.typeUser = typeUser;
		this.libelleActivite = libelleActivite;
		this.adresse = adresse;

		// TODO Auto-generated constructor stub

	}

	private long getSeTermine(Date finActivite) {// calcul en miliseconde le
													// temps avant la fin
		// TODO Auto-generated method stub
		return finActivite.getTime() - new Date().getTime();

	}

	public String getAgeStr(Date datenaissance, boolean afficheage) {
		if (afficheage)
			return "Masqué";
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
				return "Pas d'age indiqué";
			return Integer.toString(yeardiff) + " ans";
		}

		return "Pas d'age indiqué";
	}

	public String getTempsRestant(Date datefinactivite) {

		if (datefinactivite == null)
			return "Terminée";
		if (new Date().after(datefinactivite))
			return "Activité passée";
		else {

			long diff = datefinactivite.getTime() - new Date().getTime();
			// long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			// long diffDays = diff / (24 * 60 * 60 * 1000);
			if (diff < 0)
				return "Terminée";

			return diffHours + ":" + String.format("%02d", diffMinutes);

		}

	}

	public String getTempsRestant() {

		if (datefin == null)
			return "Terminée";
		if (new Date().after(datefin))
			return "Activité passée";
		else {

			long diff = datefin.getTime() - new Date().getTime();
			// long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			// long diffDays = diff / (24 * 60 * 60 * 1000);
			if (diff < 0)
				return "Terminée";

			return "Se termine dans " + diffHours + ":"
					+ String.format("%02d", diffMinutes);

		}

	}

	public String getEtat() {

		if (isActive())
			return "En cours";

		return "Terminée";
	}

	public int getTypeactivite() {
		return typeactivite;
	}

	public void setTypeactivite(int typeactivite) {
		this.typeactivite = typeactivite;
	}

	public int getNbrparticipant() {
		return nbrparticipant;
	}

	public void setNbrparticipant(int nbrparticipant) {
		this.nbrparticipant = nbrparticipant;
	}

	public int getId() {
		return id;
	}

	public String getTitre() {
		return titre;
	}

	public String getLibelle() {

		return StringEscapeUtils.unescapeJava(libelle);

	}

	public void setId(int id) {
		this.id = id;

	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public boolean isComplete() {
		if (nbmaxwaydeur == nbrparticipant)
			return true;
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isTerminee() {
		// TODO Auto-generated method stub
		if (datefin.before(new Date()))
			return true;

		return false;
	}

	public void setListParticipant(ArrayList<ParticipantBean> listParticipant) {
		// TODO Auto-generated method stub

		this.listParticipant = listParticipant;

	}

	public String getDatedebutStr() {
		return datedebutStr;
	}

	public void setDatedebutStr(String datedebutStr) {
		this.datedebutStr = datedebutStr;
	}

	public ArrayList<ParticipantBean> getListParticipant() {
		return listParticipant;
	}

	public String getBilanParticipation() {

		return "Nombre d'inscrit:" + listParticipant.size() + "/"
				+ nbmaxwaydeur;
	}

	public boolean isInscrit(int iddemandeur) {
		// TODO Auto-generated method stub
		for (ParticipantBean participantBean : listParticipant)
			if (participantBean.getId() == iddemandeur)
				return true;

		return false;
	}

	public String getAdresse() {

		return adresse;
	}

	public String getHoraire() {

		return "De " + datedebutStr + " à " + datefinStr;
	}

	public String getHoraireLeA() {

		SimpleDateFormat jour = new SimpleDateFormat("dd-MM-yyyy");
		String datestrdebut = jour.format(datedebut);
		SimpleDateFormat formatHeure = new SimpleDateFormat("HH:mm");
		String heuredebutstr = formatHeure.format(datedebut);
		String heurefinstr = formatHeure.format(datefin);

		return "Le " + datestrdebut + " </br> de " + heuredebutstr + " à "
				+ heurefinstr;
	}

	public void setPositionRecherche(double latRef, double longRef) {

		this.latRef = latRef;
		this.longRef = longRef;

	}

	public String calculDistance() {

		double distance = ServeurMethodes.getDistance(latitude, latRef,
				longitude, longRef);
		// return "mlooi";

		if (distance < 1000)
			return "" + Math.round(distance) + " métres";

		else{
			
			distance=distance/1000;
		String pattern="##.##";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
				
			return "" + myFormatter.format(distance) + " Km";
		}
		

	}

	public String getHoraireLeAHorizontal() {

		SimpleDateFormat jour = new SimpleDateFormat("dd-MM-yyyy");
		String datestrdebut = jour.format(datedebut);
		SimpleDateFormat formatHeure = new SimpleDateFormat("HH:mm");
		String heuredebutstr = formatHeure.format(datedebut);
		String heurefinstr = formatHeure.format(datefin);

		return "Le " + datestrdebut + " de " + heuredebutstr + " à "
				+ heurefinstr;
	}
}
