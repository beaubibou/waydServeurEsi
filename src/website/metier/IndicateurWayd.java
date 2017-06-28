package website.metier;

public class IndicateurWayd {

	int nbrTotalActivite;
	int nbrTotalParticipation;
	int nbrTotalInscrit;
	int nbrTotalMessage;
	int nbrTotalMessageByact;
	int nbrMessageDuJour;
	int nbrMessageByActDuJour;
	int nbrActiviteDuJour;
	int nbrParticipationDuJour;

	public IndicateurWayd(int nbrTotalActivite, int nbrTotalParticipation,
			int nbrTotalInscrit, int nbrTotalMessage, int nbrTotalMessageByact) {
		super();
		this.nbrTotalActivite = nbrTotalActivite;
		this.nbrTotalParticipation = nbrTotalParticipation;
		this.nbrTotalInscrit = nbrTotalInscrit;
		this.nbrTotalMessage = nbrTotalMessage;
		this.nbrTotalMessageByact = nbrTotalMessageByact;
		
	}

	public IndicateurWayd() {

	}


	

	public int getNbrMessageDuJour() {
		return nbrMessageDuJour;
	}

	public void setNbrMessageDuJour(int nbrMessageDuJour) {
		this.nbrMessageDuJour = nbrMessageDuJour;
	}

	public int getNbrActiviteDuJour() {
		return nbrActiviteDuJour;
	}

	public void setNbrActiviteDuJour(int nbrActiviteDuJour) {
		this.nbrActiviteDuJour = nbrActiviteDuJour;
	}

	public int getNbrMessageByActDuJour() {
		return nbrMessageByActDuJour;
	}

	public void setNbrMessageByActDuJour(int nbrMessageByActDuJour) {
		this.nbrMessageByActDuJour = nbrMessageByActDuJour;
	}

	public int getNbrParticipationDuJour() {
		return nbrParticipationDuJour;
	}

	public void setNbrParticipationDuJour(int nbrParticipationDuJour) {
		this.nbrParticipationDuJour = nbrParticipationDuJour;
	}

	public int getNbrTotalMessage() {
		return nbrTotalMessage;
	}

	public void setNbrTotalMessage(int nbrTotalMessage) {
		this.nbrTotalMessage = nbrTotalMessage;
	}

	public int getNbrTotalMessageByact() {
		return nbrTotalMessageByact;
	}

	public void setNbrTotalMessageByact(int nbrTotalMessageByact) {
		this.nbrTotalMessageByact = nbrTotalMessageByact;
	}

	public int getNbrTotalActivite() {
		return nbrTotalActivite;
	}

	public void setNbrTotalActivite(int nbrTotalActivite) {
		this.nbrTotalActivite = nbrTotalActivite;
	}

	public int getNbrTotalParticipation() {
		return nbrTotalParticipation;
	}

	public void setNbrTotalParticipation(int nbrTotalParticipation) {
		this.nbrTotalParticipation = nbrTotalParticipation;
	}

	public int getNbrTotalInscrit() {
		return nbrTotalInscrit;
	}

	public void setNbrTotalInscrit(int nbrTotalInscrit) {
		this.nbrTotalInscrit = nbrTotalInscrit;
	}

}
