package texthtml.pro;

public class CreationActiviteText {

	public static int TAILLE_TITRE_ACTIVITE_MAX = CommunText.TAILLE_TITRE_ACTIVITE_MAX;
	public static int TAILLE_DESCRIPTION_ACTIVITE_MAX = CommunText.TAILLE_DESCRIPTION_ACTIVITE_MAX;
	public static int TAILLE_ADRESSE_MAX = CommunText.TAILLE_ADRESSE_MAX;

	public static String HINT_ADRESSE = CommunText.HINT_ADRESSE;
	public static String HINT_TITRE = CommunText.HINT_TITRE;

	public static String TITRE_JUMBO = "Proposez vos activités";
	public static String MESSAGE_JUMBO_LIGNE1 = "Proposez vos activités gratuites à la communauté. Une activité ne peut pas exéder 8 heures.";
	public static String MESSAGE_JUMBO_LIGNE2 = "Vous pouvez planifier jusqu à 5 activités simultanément.";

	public static String TITRE_PANEL = "Proposez une activité";
	public static String TITRE_ONGLET = "Proposez une activité";

	public static String LABEL_DATE_DEBUT = "Date début";
	public static String LABEL_DATE_FIN = "Date fin";
	public static String LABEL_TYPE_ACTIVITE = "Type activité";
	public static String LABEL_ADRESSE = "Adresse";
	public static String LABEL_DESCRIPTION = "Description";
	public static String LABEL_TITRE = "Titre";

	public static String ALERT_GPS_NO_POSITION = CommunText.ALERT_GPS_NO_POSITION;

	public static String getHintTitreActivite() {

		return "Titre de l'activité " + TAILLE_TITRE_ACTIVITE_MAX
				+ " caractéres maximum";
	}

	public static String getHintDescriptionActivite() {

		return "Description de l'activité " + TAILLE_DESCRIPTION_ACTIVITE_MAX
				+ " caractéres maximum";
	}

	public static String initNbrCaracteres() {

		return "0 Caractére sur "
				+ CreationActiviteText.TAILLE_DESCRIPTION_ACTIVITE_MAX;

	}
	public static String getNbrCarateresDescription(){
		return CommunText.getNbrCarateresDescription();
	}

}
