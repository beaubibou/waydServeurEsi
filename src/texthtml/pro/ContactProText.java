package texthtml.pro;

public class ContactProText {
	public static String TITRE_ONGLET="Contact";
	public static String TITRE_JUMBO="Contactez nous";
	public static String MESSAGE_JUMBO_LIGNE1="Une question, un problème ? Vous pouvez nous contacter via le formulaire ci-dessous ou à l'adresse contact.pro@wayd.fr";
	
	public static int TAILLE_MAX_MESSAGE=250;
	
	public static String LABEL_BUTTON_ENVOYER="Envoyer";
	

	public static String getNbrCarateresDescriptionMessage() {
		
		return  "/"+TAILLE_MAX_MESSAGE;
	}

	public static String initNbrCaracteres() {
		return "0 Caractére sur "
				+ TAILLE_MAX_MESSAGE;
	}
	
	public static String getHintDescriptionMessage(){
		return "Laissez votre message  " + TAILLE_MAX_MESSAGE	+ "  caractéres maximum.";
	}
}
