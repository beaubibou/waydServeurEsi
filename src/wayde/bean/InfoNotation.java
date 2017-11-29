package wayde.bean;

import org.apache.log4j.Logger;

public class InfoNotation {
	private static final Logger LOG = Logger.getLogger(InfoNotation.class);

	int totalavis;
	double moyenne;
	
	public InfoNotation(int totalavis,double moyenne){
	this.totalavis=totalavis;	
	this.moyenne=moyenne;
	}

	public InfoNotation(){
	
		}
	public int getTotalavis() {
		return totalavis;
	}


	public void setTotalavis(int totalavis) {
		this.totalavis = totalavis;
	}


	public double getMoyenne() {
		return moyenne;
	}


	public void setMoyenne(double moyenne) {
		this.moyenne = moyenne;
	}

	
}
