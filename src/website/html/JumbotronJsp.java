package website.html;

import org.apache.log4j.Logger;

import wayde.dao.ActiviteDAO;



public class JumbotronJsp {
	private static final Logger LOG = Logger.getLogger(JumbotronJsp.class);

	private String sousTitre ;
	private String titre;
	private String message;
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	public static String getJumbotron(JumbotronJsp jumbotron){
		
		if (jumbotron==null)return "";
		
		return  "<div class=\"container\">"
				+ " <div class='jumbotron jumbotronwayd'>"
				+ "<h3>"+ jumbotron.titre+ "</h3>"
				+ "<p>"+jumbotron.sousTitre+"</p></div>"
				;
				
		
	}

	
	
	public JumbotronJsp( String titre, String sousTitre,String message) {
		super();
		this.sousTitre = sousTitre;
		this.titre = titre;
		this.message = message;
	}

	public String getHtml() {

		String  retour = "<div class='container'>"
					+ " <div class='jumbotron'>"
					+ "<h1>"+ titre+ "</h1>"
					+ "<p>"+sousTitre+"</p></div>"
					+"<p>"+message+"</p>";
		
		
		

		return retour;
	}
}
