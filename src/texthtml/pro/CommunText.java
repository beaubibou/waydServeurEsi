package texthtml.pro;

public class CommunText {

	
	public static final String LABEL_BUTTON_PROPOSEZ = "Proposez";
	public static int TAILLE_TITRE_ACTIVITE_MAX = 50;
	public static int TAILLE_DESCRIPTION_ACTIVITE_MAX = 250;
	public static int TAILLE_ADRESSE_MAX = 250;
	public static String HINT_ADRESSE="Saissisez une adresse";
	public static String HINT_TITRE="Saissisez un titre";

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
}
