package reponsevaleur;

import java.util.ArrayList;

import wayde.bean.Activite;
import wayde.bean.Notification;
import wayde.bean.Participant;

public class ListParticipantRV {

	private Erreur[] erreurs;
	private Participant[] participants;

	public ListParticipantRV() {
		super();
	}

	public Erreur[] getErreurs() {
		return erreurs;
	}

	public void setErreurs(Erreur[] erreurs) {
		this.erreurs = erreurs;
	}

	
	
	public Participant[] getParticipants() {
		return participants;
	}

	public void setParticipants(Participant[] participants) {
		this.participants = participants;
	}

	public void initErreurs(ArrayList<Erreur> listErreurs) {

	erreurs=	(Erreur[]) listErreurs.toArray(new Erreur[listErreurs
		                           					.size()]);
	}

	public void initParticipants(ArrayList<Participant> list) {
		
	participants=	(Participant[]) list.toArray(new Participant[list.size()]);
	
	}
}
