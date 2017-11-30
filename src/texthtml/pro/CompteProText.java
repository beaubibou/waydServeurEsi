package texthtml.pro;

public class CompteProText {
	
	public static String HINT_SITEWEB="http://monsite.fr";
	public static String HINT_SIRET="Numéro Siret";
	public static String HINT_ADRESSE="Saissisez l'adresse";
	public static int TAILLE_PSEUDO_MAX = 40;
	public static int TAILLE_SITE_WEB_MAX = 100;
	public static int TAILLE_TELEPHONNE_MAX = 14;
	public static int TAILLE_SIRET_MAX = 40;
	public static int TAILLE_ADRESSE_MAX = 150;
	public static int TAILLE_DESCRIPTION_PROFIL_MAX = 250;
	
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
