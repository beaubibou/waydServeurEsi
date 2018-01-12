package reponsevaleur;

import java.util.ArrayList;

import wayde.bean.Activite;
import wayde.bean.Avis;
import wayde.bean.Message;

public class DiscussionRV {

	private Erreur[] erreurs;
	private Message[] messages;

	public void initErreurs(ArrayList<Erreur> listErreurs) {

		erreurs = (Erreur[]) listErreurs
				.toArray(new Erreur[listErreurs.size()]);
	}
	
	public void initMessages(ArrayList<Message> listMessage) {

		messages = (Message[]) listMessage
				.toArray(new Message[listMessage.size()]);
	}
	public DiscussionRV(){
		
	}

	
	public Erreur[] getErreurs() {
		return erreurs;
	}

	public void setErreurs(Erreur[] erreurs) {
		this.erreurs = erreurs;
	}

	public Message[] getMessages() {
		return messages;
	}

	public void setMessages(Message[] messages) {
		this.messages = messages;
	}

	

	
	

}
