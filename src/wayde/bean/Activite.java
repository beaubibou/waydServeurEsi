package wayde.bean;


import java.util.Calendar;
import java.util.Date;

public class Activite {
	

	int id;


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

	private String prenomorganisateur;

	private double note;
	
	private int typeactivite;
	
	private boolean dejainscrit;
	
	private boolean organisateur;

	private boolean archive;
	
	private int totalavis;
	
	private int sexe;
	
	private String age;
	
	private int nbrparticipant;

	private String tpsrestant;
	
	public  Date datedebut,datecreation;
	
	public  Date datefinactivite;

	public boolean actif;
	 
	public int role;
	
	public long finidans;

	private int nbmaxwaydeur;


	public int getTypeUser() {
		return typeUser;
	}

	public void setTypeUser(int typeUser) {
		this.typeUser = typeUser;
	}

	public int getTypeAcces() {
		return typeAcces;
	}

	public void setTypeAcces(int typeAcces) {
		this.typeAcces = typeAcces;
	}

	private int typeUser;


	private int typeAcces;
	
	
	
	public long getFinidans() {
		return finidans;
	}

	public void setFinidans(long finidans) {
		this.finidans = finidans;
	}

	public int getNbmaxwaydeur() {
		return nbmaxwaydeur;
	}

	public void setNbmaxwaydeur(int nbmaxwaydeur) {
		this.nbmaxwaydeur = nbmaxwaydeur;
	}

	public boolean isOganisateurActivite(int idpersonne){
	
		if (idpersonne==idorganisateur) return true;
	
		return false;
	}


	public Activite() {

	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getTpsrestant() {
		return tpsrestant;
	}

	public void setTpsrestant(String tpsrestant) {
		this.tpsrestant = tpsrestant;
	}

	public Activite(String titre, String libelle, int idorganisateur, Date datedebut,
	        int idtypeactivite, double latitude, double longitude,String adresse,boolean actif,int nbmaxwaydeur,Date finactivite)
	    {
	        super();
	        this.titre = titre;
	        this.libelle = libelle;
	        this.idorganisateur = idorganisateur;
	        this.datedebut = datedebut;
	        this.latitude = latitude;
	        this.longitude=longitude;
	        
	        this.adresse=adresse;
	        this.datecreation = new Date();
	        this.typeactivite=idtypeactivite;
	        this.actif=actif;
	        this.nbmaxwaydeur=nbmaxwaydeur;
	        this.nbrparticipant=1;
	        this.datefinactivite=finactivite;
	        this.finidans=getSeTermine(finactivite);
	        
	    }
	
	
	
	public Activite(int id,String titre, String libelle, int idorganisateur, Date datedebut,
	            Date datefin, int idtypeactivite, double latitude, double longitude,String adresse,
	            String nom,String prenom,String photo,double note,
	            int role,boolean archive,int totalavis,Date datenaissance,int sexe,
	            int nbrparticipant,boolean afficheage,boolean affichesexe,int nbmaxwaydeur,int typeUser,int typeAcces )
	        {
	            super();
	        	this.id=id;
	    		this.titre = titre;
	    		this.libelle =libelle;
	    		this.idorganisateur = idorganisateur;
	    		this.latitude = latitude;
	    		this.longitude = longitude;
	    		this.adresse = adresse;
	    		this.datefinStr = Parametres.getStringWsFromDate(datefin);
	    		this.datedebut=datedebut;
	    		this.datefinactivite=datefin;
	    		this.datedebutStr = Parametres.getStringWsFromDate(datedebut);
	    		this.nomorganisateur=nom;
	    		this.prenomorganisateur=prenom;
	    		this.photo=photo;
	    		this.note=note;
	    		this.organisateur=false;
	    		this.dejainscrit=false;
	    		this.archive=archive;
	    		this.totalavis=totalavis;
	    		this.sexe=sexe;
	    		this.nbrparticipant=nbrparticipant;
	    		this.typeactivite=idtypeactivite;
	    		this.tpsrestant=getTempsRestant(datefin);
	    		this.finidans=getSeTermine(datefin);
	    		this.age=getAgeStr(datenaissance,afficheage);
	    		if (affichesexe)this.sexe=3;
	    		this.role=role;
	    		this.typeUser=typeUser;
	    		this.typeAcces=typeAcces;
	    		this.nbmaxwaydeur=nbmaxwaydeur;
	    		if (this.titre.equals(""))this.titre=" ";
	    			
	    		
	        }
		
	
	private long getSeTermine(Date finActivite) {// calcul en miliseconde le temps avant la fin
		// TODO Auto-generated method stub
		return  finActivite.getTime()-new Date().getTime();
		
	}

	public  String getAgeStr(Date datenaissance,boolean afficheage)
	{
		if (afficheage)return "Masqué";
		if (datenaissance!=null){
			Calendar curr = Calendar.getInstance();
			Calendar birth = Calendar.getInstance();
			birth.setTime(datenaissance);
			int yeardiff = curr.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
			curr.add(Calendar.YEAR,-yeardiff);
			if(birth.after(curr))
			{
				yeardiff = yeardiff - 1;
			}
			if (yeardiff<0)return "Erreur";
			if (yeardiff==0)return "Pas d'age indiqué";
			return Integer.toString(yeardiff) +" ans";
		}

		return "Pas d'age indiqué";
	}

public String getTempsRestant(Date datefinactivite){
		
		if (datefinactivite==null)return "Terminée";
		if (new Date().after(datefinactivite))return "Activité passée";
		else
		{
		
			long diff =  datefinactivite.getTime()-new Date().getTime();
		//	long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
		//	long diffDays = diff / (24 * 60 * 60 * 1000);
		if (diff<0)return "Terminée";
			
			return diffHours+":" +String.format("%02d", diffMinutes);
	
			
		}
	
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

	public int getSexe() {
		return sexe;
	}

	public void setSexe(int sexe) {
		this.sexe = sexe;
	}

	public int getTotalavis() {
		return totalavis;
	}

	public void setTotalavis(int totalavis) {
		this.totalavis = totalavis;
	}

	public boolean isArchive() {
		return archive;
	}

	public void setArchive(boolean archive) {
		this.archive = archive;
	}

	public String getPhoto() {
		return photo;
	}

	
	public double getNote() {
		return note;
	}

		
	public void setNote(double note) {
		this.note = note;
	}

	public String getNomorganisateur() {
		return nomorganisateur;
	}

	public String getPrenomorganisateur() {
		return prenomorganisateur;
	}

	
	public int getId() {
		return id;
	}

	

	public String getTitre() {
		return titre;
	}

	public String getLibelle() {
		return libelle;
	}

	public int getIdorganisateur() {
		return idorganisateur;
	}

	public boolean isDejainscrit() {
		return dejainscrit;
	}

	public void setDejainscrit(boolean dejainscrit) {
		
		this.dejainscrit = dejainscrit;
	}

	public String getDatedebutStr() {
		return datedebutStr;
	}

	public String getDatefinStr() {
		return datefinStr;
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

	public void setIdorganisateur(int idorganisateur) {
		this.idorganisateur = idorganisateur;
	}

	public void setDatedebutStr(String datedebutStr) {
		this.datedebutStr = datedebutStr;
	}

	public void setDatefinStr(String datefinStr) {
		this.datefinStr = datefinStr;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void setNomorganisateur(String nomorganisateur) {
		this.nomorganisateur = nomorganisateur;
	}

	public void setPrenomorganisateur(String prenomorganisateur) {
		this.prenomorganisateur = prenomorganisateur;
	}

	public String getAdresse() {
		return adresse;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public boolean isOrganisateur() {
		return organisateur;
	}

	public void setOrganisateur(boolean organisateur) {
		this.organisateur = organisateur;
	}

	public boolean isComplete() {
	if (nbmaxwaydeur==nbrparticipant)return true;
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isTerminee() {
		// TODO Auto-generated method stub
		if (datefinactivite.before(new Date()))return true;
		
		return false;
	}

	public void defineOrganisateur(int idpersonne) {
		
		if (idorganisateur == idpersonne)
			setOrganisateur(true);
		else
			setOrganisateur(false);
			// TODO Auto-generated method stub
		
	}

	public void defineOrganisateur() {
		if (role == 0) {
			setDejainscrit(true);
		}
		if (role == 1) {
			setOrganisateur(true);
		}// TODO Auto-generated method stub
		
	}

	

	
		
	

	
}
