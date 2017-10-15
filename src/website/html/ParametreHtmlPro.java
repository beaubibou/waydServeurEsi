package website.html;

public class ParametreHtmlPro {

	// v *******************COMPTE *********************
	public static int TAILLE_PSEUDO_MAX = 12;
	public	static int TAILLE_PSEUDO_MINI = 5;
	public static int TAILLE_SITE_WEB = 100;
	public static int TAILLE_TELEPHONNE_MAX = 14;
	public static int TAILLE_ADRESSE_MAX = 150;
	public static int TAILLE_MAX_DESCRIPTION_PROFIL = 150;

	// v *******************ACTIVITE *********************

	public static int TAILLE_TITRE_ACTIVITE_MAX = 50;
	public static int TAILLE_DESCRIPTION_ACTIVITE_MAX = 250;

	public static String getHintTitreActivite() {

		return "Titre de l'activité " + TAILLE_TITRE_ACTIVITE_MAX + 
				" caractéres maximum";
	}

	public static String getHintDescriptionActivite() {

		return "Description de l'activité " + TAILLE_DESCRIPTION_ACTIVITE_MAX
				+ " caractères maximum";
	}

}
