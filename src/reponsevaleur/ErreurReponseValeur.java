package reponsevaleur;

public class ErreurReponseValeur {

	public final static int CODE_PERSONNE_INEXISTANTE=0;
	public final static int CODE_JETON_INVALIDE=1;
	public final static int CODE_ERREUR_SYSTEME=2;
	public final static int CODE_QUOTA_ACTIVITE_DEPASSE=3;

	public static Erreur ERR_PERSONNE_INEXISTANTE=new Erreur(CODE_PERSONNE_INEXISTANTE,"Personne inexistante");

	public static Erreur ERR_JETON_INVALIDE=new Erreur(CODE_JETON_INVALIDE,"Jeton  invalide");
	
	public static Erreur ERREUR_SYSTEME(String message){
		
	return new Erreur(CODE_ERREUR_SYSTEME, message);
	}
	
	public static final Erreur ERR_QUOTA_ACTIVITE_DEPASSE = new Erreur(CODE_QUOTA_ACTIVITE_DEPASSE,"Quota activite dépassé");
	
	
	
}

