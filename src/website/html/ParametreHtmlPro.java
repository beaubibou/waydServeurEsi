package website.html;

public class ParametreHtmlPro {

	// v *******************COMPTE profil*********************
	public static int TAILLE_PSEUDO_MAX = 40;
	public	static int TAILLE_PSEUDO_MINI = 5;
	public static int TAILLE_SITE_WEB_MAX = 100;
	public static int TAILLE_TELEPHONNE_MAX = 14;
	public static int TAILLE_ADRESSE_MAX = 150;
	public static int TAILLE_SIRET_MAX = 40;
	public static int TAILLE_DESCRIPTION_PROFIL_MAX = 250;

	// v *******************ACTIVITE *********************

	public static int TAILLE_TITRE_ACTIVITE_MAX = 50;
	public static int TAILLE_DESCRIPTION_ACTIVITE_MAX = 250;
	
	// v *******************Contact *********************

		public static int TAILLE_MESSAGE_CONTACT_MAX = 250;
		public static int TAILLE_MAIL_CONTACT_MAX = 60;
		
	
		
		
	public static String getHintTitreActivite() {

		return "Titre de l'activit� " + TAILLE_TITRE_ACTIVITE_MAX + 
				" caract�res maximum";
	}

	public static String getHintDescriptionActivite() {

		return "Description de l'activit� " + TAILLE_DESCRIPTION_ACTIVITE_MAX
				+ " caract�res maximum";
	}

	public static String getHintNomSociete() {

		return "Nom de la soci�t� " + TAILLE_PSEUDO_MAX
				+ " caract�res maximum";
	}
	
	public static String getHintPseudoWaydeur() {

		return "Ton pseudo " + TAILLE_PSEUDO_MAX
				+ " caract�res maximum";
	}
	
	public static String getHintDescriptionProfil() {

		return "D�crivez votre activit� " + TAILLE_DESCRIPTION_PROFIL_MAX
				+ " caract�res maximum";
	}
	
	public static String getHintDescriptionProfilWaydeur() {

		return "D�crivez votre profil en quelques mots " + TAILLE_DESCRIPTION_PROFIL_MAX
				+ " caract�res maximum";
	}
	
	
	public static String getHintDescriptionMessageContact() {

		return "Laissez votre message " + TAILLE_MESSAGE_CONTACT_MAX
				+ " caract�res maximum";
	}
	
	
	
}
