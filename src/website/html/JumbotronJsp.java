package website.html;



public class JumbotronJsp {

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
				+ " <div class=\"jumbotron\">"
				+ "<h1>"+ jumbotron.titre+ "</h1>"
				+ "<p>"+jumbotron.sousTitre+"</p></div>"
				+"<p>"+jumbotron.message+"</p>";
		
	}


	public JumbotronJsp( String titre, String sousTitre,String message) {
		super();
		this.sousTitre = sousTitre;
		this.titre = titre;
		this.message = message;
	}

	public String getHtml() {

		String  retour = "<div class=\"container\">"
					+ " <div class=\"jumbotron\">"
					+ "<h1>"+ titre+ "</h1>"
					+ "<p>"+sousTitre+"</p></div>"
					+"<p>"+message+"</p>";
		
		
		

		return retour;
	}
}
