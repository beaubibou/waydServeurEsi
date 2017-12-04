package texthtml.pro;

public class CompteProText {
	
	public static String HINT_SITEWEB="http://monsite.fr";
	public static String HINT_SIRET="Numéro Siret";
	public static String HINT_ADRESSE="Saissisez l'adresse";
	
	public static int TAILLE_PSEUDO_MAX = CommunText.TAILLE_PSEUDO_MAX;
	public static int TAILLE_SITE_WEB_MAX = CommunText.TAILLE_SITE_WEB_MAX;
	public static int TAILLE_TELEPHONNE_MAX = CommunText.TAILLE_TELEPHONNE_MAX;
	public static int TAILLE_SIRET_MAX = CommunText.TAILLE_SIRET_MAX;
	public static int TAILLE_ADRESSE_MAX = CommunText.TAILLE_ADRESSE_MAX;
	public static int TAILLE_DESCRIPTION_PROFIL_MAX = CommunText.TAILLE_DESCRIPTION_PROFIL_MAX;

	
	public static String TITRE_ONGLET = "Mon compte";

	public static String TITRE_JUMBO = "Votre compte";
	public static String MESSAGE_JUMBO_L1 = "Renseigner votre profil";
	
	public static String LABEL_NOM = "Nom *";
	public static String LABEL_SITE_WEB = "Site Web";
	public static String LABEL_NUMERO_SIRET = "Numéro SIRET *";
	public static String LABEL_ADRESSE = "Adresse *";
	public static String LABEL_DESCRIPTION_PROFIL = "Description";
	public static String LABEL_TELEPHONE = "Téléphone";
	
	public static  String ALERT_GPS_NO_POSITION=CommunText.ALERT_GPS_NO_POSITION;
			
	
	public static String getHintNomSociete() {

		return "Nom de la société " + TAILLE_PSEUDO_MAX
				+ " caractéres maximum";
	}

	public static String getHintDescriptionProfil() {

		return "Décrivez votre activité " + TAILLE_DESCRIPTION_PROFIL_MAX
				+ " caractéres maximum";
		
	}
	

	
}
