package texthtml.pro;

public class CommunText {

	
	public static final String LABEL_BUTTON_PROPOSEZ = "Proposez";
	public static final String INFO_SUPPRIME_SELECTION ="Supprimez les selections";
	public static int TAILLE_TITRE_ACTIVITE_MAX = 50;
	public static int TAILLE_DESCRIPTION_ACTIVITE_MAX = 250;
	public static int TAILLE_ADRESSE_MAX = 250;
	public static String HINT_ADRESSE="Saissisez une adresse";
	public static String HINT_TITRE="Saissisez un titre";

	
	public static int TAILLE_PSEUDO_MAX = 40;
	public static int TAILLE_SITE_WEB_MAX = 100;
	public static int TAILLE_TELEPHONNE_MAX = 14;
	public static int TAILLE_SIRET_MAX = 14;
	public static int TAILLE_DESCRIPTION_PROFIL_MAX = 250;
	
	
	public static String LABEL_TELEPHONE = "Téléphone *";
	public static String LABEL_NOM = "Pseudo *";
	public static String LABEL_SITE_WEB = "Site Web";
	public static String LABEL_NUMERO_SIRET = "Numéro SIRET *";
	public static String LABEL_ADRESSE = "Adresse *";
	public static String LABEL_DESCRIPTION_PROFIL = "Description";
	
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
				" caractéres maximum.";
	}

	public static String getNbrCarateresDescription() {
		// TODO Auto-generated method stub
		return  " Caractere(s) sur "+TAILLE_DESCRIPTION_ACTIVITE_MAX;
	}


	public static String PSEUDO_LIMITE_A_CARATERE() {
		// TODO Auto-generated method stub
		return "Pseudo limité à "+ TAILLE_PSEUDO_MAX+ " caractéres.";
	}

	public static String DESCRIPTION_PROFIL_LIMITE_A_CARATERE() {
		// TODO Auto-generated method stub
		return "La description est limitée à "+ TAILLE_DESCRIPTION_PROFIL_MAX+ " caractéres.";
	}
	
	public static String getHintNomSociete() {

		return "Pseudo de la structure " + TAILLE_PSEUDO_MAX
				+ " caractéres maximum";
	}
}
