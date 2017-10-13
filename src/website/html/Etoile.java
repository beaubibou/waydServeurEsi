package website.html;

public class Etoile {

	
	public static String getNbrEtoiles(double note){
		
		note=Math.round(note);
				
		String retour = "<p>";
		for (double f=0; f < note; f++) {
			retour = retour
					+ "<span style=\"margin:2px;\" class=\"glyphicon glyphicon-thumbs-up\"></span>";

		}

		retour = retour + "</p>";
		
		return retour;
		
		
		
		
	}
}

