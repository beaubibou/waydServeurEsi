package wayde.bean;

import org.apache.log4j.Logger;

public class IndicateurWayd {
	private static final Logger LOG = Logger.getLogger(IndicateurWayd.class);

	int nbrTotalActivite;
	int nbrTotalParticipation;
	int nbrTotalInscrit;
	int nbrTotalMessage;
	int nbrTotalMessageByact;

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
