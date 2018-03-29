package texthtml.pro;

public class CommunText {

	
	public static final String LABEL_BUTTON_PROPOSEZ = "Proposez";
	public static final String INFO_SUPPRIME_SELECTION ="Supprimez les selections";
	public static final  int TAILLE_TITRE_ACTIVITE_MAX = 200;
	
	public static final int TAILLE_DESCRIPTION_ACTIVITE_MAX = 2000;
	public static final int TAILLE_ADRESSE_MAX = 250;
	public static final String HINT_ADRESSE="Saissisez une adresse";
	public static final String HINT_TITRE="Saisisez un titre";
	public static final String HINT_TELEPHONE="XXXXXXXXXX";
	public static final String HINT_MOT_DE_PASSE = "Saisissez le mot de passe";
	public static final String HINT_MOT_DE_PASSE_BIS = "Ressaisir le mot de passe";
	public static final String HINT_SIRET = "Numéro de  Siret (14 chiffres) ";
	public static final String HINT_EMAIL = "Adresse email";
	public static final int NBR_ACTIVITE_MAX = 5;

	public static final int TAILLE_PSEUDO_MAX = 40;
	public static final int TAILLE_SITE_WEB_MAX = 100;
	public static final int TAILLE_TELEPHONNE_MAX = 10;
	public static final int TAILLE_SIRET_MAX = 14;
	public static final int TAILLE_DESCRIPTION_PROFIL_MAX = 250;
	
	
	public static final String LABEL_TELEPHONE = "Téléphone *";
	public static final String LABEL_NOM = "Pseudo *";
	public static final String LABEL_SITE_WEB = "Site Web";
	public static final String LABEL_NUMERO_SIRET = "Numéro SIRET *";
	public static final String LABEL_ADRESSE = "Adresse *";
	public static final String LABEL_DESCRIPTION_PROFIL = "Description";
	
	public static final String LABEL_DATE_DEBUT = "Date début";
	public static final String LABEL_DATE_FIN = "Date fin";
	public static final String LABEL_TYPE_ACTIVITE = "Type activité";
	public static final String LABEL_DESCRIPTION_ACTIVITE = "Description";
	public static final String LABEL_TITRE = "Titre";
	
	public static String getHintDescriptionProfil() {

		return "Décrivez en quelques mots l'activité de votre structure en " + TAILLE_DESCRIPTION_PROFIL_MAX
				+ " caractéres maximum";
		
	}
	
	
	
	public static  String ALERT_GPS_NO_POSITION="La position GPS de votre adresse n'a pas été trouvée. "
			+ " Veuillez ressaisir votre adresse.";
	
		
	public static String getHintDescriptionActivite() {

		return "Description de l'activité " + TAILLE_DESCRIPTION_ACTIVITE_MAX
				+ " caractéres maximum.";
	}
			
	public static String getHintTitreActivite() {

		return "Titre de l'activité " + TAILLE_TITRE_ACTIVITE_MAX + 
				" caractères maximum.";
	}

	public static String getNbrCarateresDescription() {
		
		return  " / "+TAILLE_DESCRIPTION_ACTIVITE_MAX;
	}


	public static String PSEUDO_LIMITE_A_CARATERE() {
		
		return "Pseudo limité à "+ TAILLE_PSEUDO_MAX+ " caractères.";
	}

	public static String DESCRIPTION_PROFIL_LIMITE_A_CARATERE() {
		
		return "La description est limitée à "+ TAILLE_DESCRIPTION_PROFIL_MAX+ " caractères.";
	}
	
	public static String getHintNomSociete() {

		return "Pseudo de la structure " + TAILLE_PSEUDO_MAX
				+ " caractères maximum";
	}
}
