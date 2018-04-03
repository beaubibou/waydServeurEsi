package servlet;

import java.io.IOException;

import org.joda.time.DateTime;

import carpediem.ImportCarpe;

public class ImportRunnable implements Runnable {
	int nbrJour = 2;
	String ville;
	String jeton;
	
		
	public ImportRunnable(int nbrJour, String ville, String jeton) {
		super();
		this.nbrJour = nbrJour;
		this.ville = ville;
		this.jeton = jeton;
	}



	@Override
	public void run() {
		DateTime date = new DateTime();

		ImportCarpe importCarpe;
		try {
			
			importCarpe = new ImportCarpe();
		//	for (int nbrJours = 0; nbrJours < nbrJour; nbrJours++) {

			//	DateTime date1 = date.plusDays(nbrJours);
			//	String dateEventStr = ImportCarpeDiem.getFormatDate(date1);
				importCarpe
						.importActivitesByPageNew( ville, jeton,nbrJour);

		//	}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
