package carpediem;

import java.util.Date;

public class DateEvenementOpenAgenda {

	Date dateDebut;
	Date dateFin;
	public DateEvenementOpenAgenda(Date dateDebut, Date dateFin) {
		super();
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}
	public boolean egale(Date dateDebut2, Date dateFin2) {
	
			
		if (dateDebut.compareTo(dateDebut2)==0 && dateFin.compareTo(dateFin2)==0){
		//System.out.println("******Exitste");	
		return true;
		}
			
		
		return false;
		
		
	}
	public Date getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	
	
}
