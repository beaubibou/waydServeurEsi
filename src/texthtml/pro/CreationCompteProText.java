package texthtml.pro;

public class CreationCompteProText {

	public static int TAILLE_PSEUDO_MAX = 40;
	public static int TAILLE_SITE_WEB_MAX = 100;
	public static int TAILLE_TELEPHONNE_MAX = 14;
	public static int TAILLE_SIRET_MAX = 40;
	public static int TAILLE_ADRESSE_MAX = 150;
	public static int TAILLE_DESCRIPTION_PROFIL_MAX = 250;

	public static String HINT_ADRESSE = CommunText.HINT_ADRESSE;
	public static String HINT_EMAIL = "Adresse email";
	public static String HINT_MOT_DE_PASSE = "Mot de passe";
	public static String HINT_MOT_DE_PASSE_BIS = "Confirmez le mot de passe";
	

	
	public static String TITRE_JUMBO = "Proposez vos activités";
	public static String MESSAGE_JUMBO_LIGNE1 = "Proposez vos activités gratuites à la communauté. Une activité ne peut pas exéder 8 heures.";
	public static String MESSAGE_JUMBO_LIGNE2 = "Vous pouvez planifier jusqu à 5 activités simultanément.";

	public static String TITRE_PANEL = "Création du compte";
	public static String TITRE_ONGLET = "Ajoutez une activité";

	public static String LABEL_NOM = "Nom *";
	public static String LABEL_SITE_WEB = "Site Web";
	public static String LABEL_NUMERO_SIRET = "Numéro SIRET *";
	public static String LABEL_ADRESSE = "Adresse *";
	public static String LABEL_DESCRIPTION_PROFIL = "Description";
	public static String LABEL_TELEPHONE = "Téléphone";

	public static String ALERT_GPS_NO_POSITION = CommunText.ALERT_GPS_NO_POSITION;

	public static String getHintNomSociete() {

		return "Nom de la société " + TAILLE_PSEUDO_MAX
				+ " caractéres maximum";
	}

	public static String getHintDescriptionProfil() {

		return "Décrivez votre activité " + TAILLE_DESCRIPTION_PROFIL_MAX
				+ " caractéres maximum";
		
	}
	
	
	public static String getNbrCarateresDescription(){
		return CommunText.getNbrCarateresDescription();
	}

}
