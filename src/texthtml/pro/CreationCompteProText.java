package texthtml.pro;

public class CreationCompteProText {

	public static int TAILLE_PSEUDO_MAX = CommunText.TAILLE_PSEUDO_MAX;
	public static int TAILLE_SITE_WEB_MAX = CommunText.TAILLE_SITE_WEB_MAX;
	public static int TAILLE_TELEPHONNE_MAX = CommunText.TAILLE_TELEPHONNE_MAX;
	public static int TAILLE_SIRET_MAX = CommunText.TAILLE_SIRET_MAX;
	public static int TAILLE_ADRESSE_MAX = CommunText.TAILLE_ADRESSE_MAX;
	public static int TAILLE_DESCRIPTION_PROFIL_MAX = CommunText.TAILLE_DESCRIPTION_PROFIL_MAX;

	public static String HINT_ADRESSE = CommunText.HINT_ADRESSE;
	public static String HINT_EMAIL = "Renseignez votre adresse email";
	public static String HINT_MOT_DE_PASSE = "Mot de passe";
	public static String HINT_MOT_DE_PASSE_BIS = "Confirmez le mot de passe";
		
	public static String TITRE_JUMBO = "Proposez vos activités";
	public static String MESSAGE_JUMBO_LIGNE1 = "Proposez vos activités gratuites à la communauté. Une activité ne peut pas exéder 8 heures.";
	public static String MESSAGE_JUMBO_LIGNE2 = "Vous pouvez planifier jusqu à 5 activités simultanément.";

	public static String TITRE_PANEL = "Création du compte";
	public static String TITRE_ONGLET = "Ajoutez une activité";

	public static String LABEL_NOM = CommunText.LABEL_NOM;
	public static String LABEL_SITE_WEB = "Site Web";
	public static String LABEL_NUMERO_SIRET = "Numéro SIRET *";
	public static String LABEL_ADRESSE = "Adresse *";
	public static String LABEL_DESCRIPTION_PROFIL = "Description";
	public static String LABEL_TELEPHONE = CommunText.LABEL_TELEPHONE;

	public static String ALERT_GPS_NO_POSITION = CommunText.ALERT_GPS_NO_POSITION;

	public static String getHintNomSociete() {

		return CommunText.getHintNomSociete();
	}

	public static String getHintDescriptionProfil() {

		return CommunText.getHintDescriptionProfil();
		
	}
	
	public static String getNbrCarateresDescription(){
		return CommunText.getNbrCarateresDescription();
	}

}
