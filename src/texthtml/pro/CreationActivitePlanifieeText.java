package texthtml.pro;

public class CreationActivitePlanifieeText {

	public static int TAILLE_TITRE_ACTIVITE_MAX = CommunText.TAILLE_TITRE_ACTIVITE_MAX;
	public static int TAILLE_DESCRIPTION_ACTIVITE_MAX = CommunText.TAILLE_DESCRIPTION_ACTIVITE_MAX;
	public static int TAILLE_ADRESSE_MAX = CommunText.TAILLE_ADRESSE_MAX;
		
	public static String HINT_ADRESSE=CommunText.HINT_ADRESSE;
	public static String HINT_TITRE=CommunText.HINT_TITRE;
	
	
	public static String TITRE_JUMBO="Planifiez vos activités";
	public static String MESSAGE_JUMBO_LIGNE1="Planifiez et proposez une activité récurrente à la communauté.";
	public static String MESSAGE_JUMBO_LIGNE2="Vous pouvez planifier jusqu à 5 activités.";
	
	public static String TITRE_PANEL="Ajouter une activité";
	public static String TITRE_ONGLET="Planifiez des activités";
	
	public static String LABEL_ADRESSE = CommunText.LABEL_ADRESSE;
	public static String LABEL_DATE_DEBUT = CommunText.LABEL_DATE_DEBUT;
	public static String LABEL_DATE_FIN = CommunText.LABEL_DATE_FIN;
	public static String LABEL_TYPE_ACTIVITE = CommunText.LABEL_TYPE_ACTIVITE;
	public static String LABEL_DESCRIPTION_ACTIVITE = CommunText.LABEL_DESCRIPTION_ACTIVITE;
	public static String LABEL_TITRE = CommunText.LABEL_TITRE;

	public static String LABEL_HEURE_DEBUT="Heure début";
	public static String LABEL_DUREE="Durée";
	public static String LABEL_BUTTON_PROPOSEZ=CommunText.LABEL_BUTTON_PROPOSEZ;
	
	public static  String ALERT_GPS_NO_POSITION=CommunText.ALERT_GPS_NO_POSITION;
	public static String MESSAGE_CHECKBOX="Cette activité se répéte tous les jours";
	
	public static String getHintTitreActivite() {

		return CommunText.getHintTitreActivite();
	}

	public static String getHintDescriptionActivite() {

		return CommunText.getHintDescriptionActivite();
	}
	
	public static String initNbrCaracteres() {

		return "0 / "+CreationActiviteText.TAILLE_DESCRIPTION_ACTIVITE_MAX;

	
	}
	
	public static String getNbrCarateresDescription(){
		return CommunText.getNbrCarateresDescription();
	}
	
	 
}
