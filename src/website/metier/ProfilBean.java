package website.metier;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.axis.encoding.Base64;
import org.apache.log4j.Logger;

import texthtml.pro.Erreur_HTML;
import wayde.bean.Parametres;
import website.dao.CacheValueDAO;
import website.enumeration.TypePhoto;
import website.html.MessageAlertDialog;

public class ProfilBean {
	private static final Logger LOG = Logger.getLogger(ProfilBean.class);
	   
	int id;
	public final static int PRO = 1;
	public final static int ASSOCIATION = 2;
	public final static int WAYDEUR = 3;

	public final static int CARPEDIEM = 4;

	public double getLatitudeFixe() {
		return latitudeFixe;
	}

	public void setLatitudeFixe(double latitudeFixe) {
		this.latitudeFixe = latitudeFixe;
	}

	public double getLongitudeFixe() {
		return longitudeFixe;
	}

	public void setLongitudeFixe(double longitudeFixe) {
		this.longitudeFixe = longitudeFixe;
	}


	private String nom;
	private String pseudo;
	private String ville;
	private String datecreationStr;
	// private String datenaissancestr;
	private int nbravis;
	private int sexe;
	private int nbractivite;
	private int nbrparticipation;
	private int nbrami;
	private double note;
	private String photostr;
	private String age;
	private String commentaire;
	private boolean actif, admin;
	private int typeuser;
	private boolean premiereconnexion;
	private ArrayList<AmiBean> listAmi = new ArrayList<>();
	private ArrayList<ActiviteBean> listActivite = new ArrayList<>();
	private ArrayList<SignalementProfilBean> listSignalement = new ArrayList<>();
	private double latitude;
	private double longitude;
	private double latitudeFixe;
	private double longitudeFixe;
	private String adresse;
	private String siteWeb;
	private String telephone;
	private boolean afficheAge;
	private boolean afficeSexe;
	private String siret;
	private FiltreRecherche filtreRecherche;
	private int nbrSignalement;
	private boolean valide;

	public int getNbrSignalement() {
		return nbrSignalement;
	}

public String getTypeUserHTML(){
		
	if (isAdmin())
		return "<span style='color: green;'	class='glyphicon glyphicon-king'></span>";

	
	if (typeuser==TypeUser.PRO)
			return "<span style='color: blue;'	class='glyphicon glyphicon-usd'></span>";

		if (typeuser==TypeUser.WAYDEUR)
			return "<span style='color: black;'	class='glyphicon glyphicon-user'></span>";

		
		
	return "";
		
	}
	
	
	public void setNbrSignalement(int nbrSignalement) {
		this.nbrSignalement = nbrSignalement;
	}

	private MessageAlertDialog messageAlertDialog;
	
	public FiltreRecherche getFiltreRecherche() {
		return filtreRecherche;
	}

	public MessageAlertDialog getMessageAlertDialog() {
		return messageAlertDialog;
	}

	public void setMessageAlertDialog(MessageAlertDialog messageAlertDialog) {
		this.messageAlertDialog = messageAlertDialog;
	}

	public void setFiltreRecherche(FiltreRecherche filtreRecherche) {
		this.filtreRecherche = filtreRecherche;
	}

	private Date dateNaissance;
	private String sexeStr;

	private String email;

	public String getSiteWeb() {
		return siteWeb;
	}

	public void setSiteWeb(String siteWeb) {
		this.siteWeb = siteWeb;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getTelephoneStr() {
		if (telephone == null)
			return "";
		return telephone;
	}

	public String getSiteWebStr() {
		if (siteWeb == null)
			return "";
		return siteWeb;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public ArrayList<SignalementProfilBean> getListSignalement() {
		return listSignalement;
	}

	public void setListSignalement(ArrayList<SignalementProfilBean> listSignalement) {
		this.listSignalement = listSignalement;
	}

	public boolean isPremiereconnexion() {
		return premiereconnexion;
	}

	public void setPremiereconnexion(boolean premiereconnexion) {
		this.premiereconnexion = premiereconnexion;
	}

	public String getUrlPhoto() {

		if (photostr == null)
			photostr = "";
		byte[] bytes = Base64.decode(photostr);
		String urlPhoto = "data:image/jpeg;base64," + Base64.encode(bytes);
		return urlPhoto;
	}

	public ArrayList<AmiBean> getListAmi() {
		return listAmi;
	}

	public ArrayList<ActiviteBean> getListActivite() {
		return listActivite;
	}

	public void setListAmi(ArrayList<AmiBean> listAmi) {
		this.listAmi = listAmi;
	}

	public int getTypeuser() {
		return typeuser;
	}

	public void setTypeuser(int typeuser) {
		this.typeuser = typeuser;
	}

	public String getDatecreationStr() {
		return datecreationStr;
	}

	public void setDatecreationStr(String datecreationStr) {
		this.datecreationStr = datecreationStr;
	}

	public ProfilBean() {

	}

	public String getCommentaire() {
		return commentaire;
	}

	public String getCommentaireStr() {
		if (commentaire == null)
			return "";
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public boolean isActif() {
		return actif;
	}

	public String isActifStr() {
		if (actif)
			return "Actif";
		else
			return "Inactif";
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	public ProfilBean(int id, String nom, String pseudo, Date datecreation,
			Date datenaissance, int nbravis, int sexe, int nbractivite,
			int nbrparticipation, int nbrami, double note, String photostr,
			boolean affichesexe, boolean afficheage, String commentaire,
			boolean actif, boolean admin, int typeuser,
			boolean premiereconnexion, double latitude, double longitude,
			String adresse, String siteWeb, String telephone,
			double latitudeFixe, double longitudeFixe, String siret,String sexeStr,String email,boolean valide) {
		super();
			
		this.id = id;
		this.nom = nom;
		this.pseudo = pseudo;
		this.nbravis = nbravis;
		this.actif = actif;
		this.sexe = sexe;
		this.nbractivite = nbractivite;
		this.nbrparticipation = nbrparticipation;
		this.nbrami = nbrami;
		this.note = note;
		this.photostr = photostr;
		this.age = getAgeStr(datenaissance, afficheage);
		this.dateNaissance = datenaissance;
		this.commentaire = commentaire;
		this.admin = admin;
		this.typeuser = typeuser;
		this.datecreationStr = Parametres.getStringWsFromDate(datecreation);
		this.premiereconnexion = premiereconnexion;
		this.latitude = latitude;
		this.longitude = longitude;
		this.adresse = adresse;
		this.siteWeb = siteWeb;
		this.telephone = telephone;
		this.latitudeFixe = latitudeFixe;
		this.longitudeFixe = longitudeFixe;
		this.afficeSexe = affichesexe;
		this.afficheAge = afficheage;
		this.siret = siret;
		this.sexeStr=sexeStr;
		this.email=email;
		this.valide=valide;
		filtreRecherche = new FiltreRecherche();
		filtreRecherche.setLatitude(latitudeFixe);
		filtreRecherche.setLongitude(longitudeFixe);
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSexeStr() {
		return sexeStr;
	}

	public void setSexeStr(String sexeStr) {
		this.sexeStr = sexeStr;
	}

	public String getSiret() {
		return siret;
	}

	public void setSiret(String siret) {
		this.siret = siret;
	}

	public boolean isAfficheAge() {
		return afficheAge;
	}

	public void setAfficheAge(boolean afficheAge) {
		this.afficheAge = afficheAge;
	}

	public boolean isAfficeSexe() {
		return afficeSexe;
	}

	public void setAfficeSexe(boolean afficeSexe) {
		this.afficeSexe = afficeSexe;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getAgeStr(Date datenaissance, boolean afficheage) {
		if (afficheage)
			return Erreur_HTML.AGE_MASQUE;
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

	public int getNbractivite() {
		return nbractivite;
	}

	public void setNbractivite(int nbractivite) {
		this.nbractivite = nbractivite;
	}

	public int getNbrparticipation() {
		return nbrparticipation;
	}

	public void setNbrparticipation(int nbrparticipation) {
		this.nbrparticipation = nbrparticipation;
	}

	public int getNbrami() {
		return nbrami;
	}

	public void setNbrami(int nbrami) {
		this.nbrami = nbrami;
	}

	public int getSexe() {
		return sexe;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSexe(int sexe) {
		this.sexe = sexe;
	}

	public String getNom() {
		return nom;
	}

	public String getPhotostr() {
		
		if (photostr == null)
			return Outils.getUrlPhoto(CacheValueDAO.getPhoto(TypePhoto.Inconnu));

		if (photostr.equals("")) {
			return Outils.getUrlPhoto(CacheValueDAO.getPhoto(TypePhoto.Inconnu));
		}
		
		if (photostr.contains("https:"))
			return photostr;
		
		
		if (photostr.contains("http:"))
			return photostr;
		
		return	Outils.getUrlPhoto(photostr);
		
		
	
	}

	public void setPhotostr(String photostr) {
		this.photostr = photostr;
	}

	// public void setDatecreationstr(String datecreationstr) {
	// this.datecreationstr = datecreationstr;
	// }

	// public void setDatenaissancestr(String datenaissancestr) {
	// this.datenaissancestr = datenaissancestr;
	// }

	public void setNbravis(int nbravis) {
		this.nbravis = nbravis;
	}

	public void setNote(double note) {
		this.note = note;
	}

	// public String getDatecreationstr() {
	// return datecreationstr;
	// }

	// public String getDatenaissancestr() {
	// return datenaissancestr;
	// }

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public int getNbravis() {
		return nbravis;
	}

	public double getNote() {
		return note;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public void setListActivite(ArrayList<ActiviteBean> listActivite) {
		this.listActivite = listActivite;
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

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getActifHtml(){
		
		if (!isActif())
			return "<span style='color: red;'	class='glyphicon glyphicon-stop'></span>";

		if (isActif())
			return "<span style='color: green;'	class='glyphicon glyphicon-play'></span>";

			return "";
		
	}
	
	public String getLienActive(){
	
		if (!isActif())
	
			return "/wayd/ListProfil?idPersonne=" +id+"&action=active";
		
		if (isActif())
			return "/wayd/ListProfil?idPersonne=" +id+"&action=desactive";
		
		return "";
		
	}
	
	
	public String getLienValidationCompte(){
		
		return "/wayd/ListProfil?idPersonne=" +id+"&action=validecompte";
		
	}
	@Override
	public String toString() {
		return "ProfilBean [id=" + id + ", nom=" + nom + ", pseudo=" + pseudo
				+ ", ville=" + ville + ", datecreationStr=" + datecreationStr
				+ ", nbravis=" + nbravis + ", sexe=" + sexe + ", nbractivite="
				+ nbractivite + ", nbrparticipation=" + nbrparticipation
				+ ", nbrami=" + nbrami + ", note=" + note + ", photostr="
				+ photostr + ", age=" + age + ", commentaire=" + commentaire
				+ ", actif=" + actif + ", admin=" + admin + ", typeuser="
				+ typeuser + ", premiereconnexion=" + premiereconnexion
				+ ", listAmi=" + listAmi + ", listActivite=" + listActivite
				+ ", listSignalement=" + listSignalement + ", latitude="
				+ latitude + ", longitude=" + longitude + ", adresse="
				+ adresse + "]";
	}

	public boolean isValide() {
		return valide;
	}

	public void setValide(boolean valide) {
		this.valide = valide;
	}

	public boolean isPro() {
		
		if (typeuser == ProfilBean.PRO)
			return true;
		return false;
	}

	public boolean isWaydeur() {
		
		if (typeuser == ProfilBean.WAYDEUR)
			return true;
		return false;
	}

}
