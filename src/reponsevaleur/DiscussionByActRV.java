package reponsevaleur;

import java.util.ArrayList;

import wayde.bean.Message;

public class DiscussionByActRV {

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
	public DiscussionByActRV(){
		
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
