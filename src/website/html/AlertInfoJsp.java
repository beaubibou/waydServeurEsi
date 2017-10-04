package website.html;

import website.enumeration.AlertJsp;

public class AlertInfoJsp {

	String message;
	AlertJsp typeAlert;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AlertJsp getTypeAlert() {
		return typeAlert;
	}

	public void setTypeAlert(AlertJsp typeAlert) {
		this.typeAlert = typeAlert;
	}

	public AlertInfoJsp(String message, AlertJsp typeAlert) {
		super();
		this.message = message;
		this.typeAlert = typeAlert;
	}

	public String getHtml() {

		String retour = "";

		switch (typeAlert) {
		case Alert:
			retour = "<div class=\"container\">"
					+ " <div class=\"alert alert-danger alert-dismissible\">"
					+ "<a  class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>"
					+ message+"</div></div>";
			break;

		case Info:
			retour = "<div class=\"container\">"
					+ " <div class=\"alert alert-success alert-dismissible\">"
					+ "<a  class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>"
					+ message+"</div></div>";
	
			break;
		case Sucess:
			retour = "<div class=\"container\">"
					+ " <div class=\"alert alert-success alert-dismissible\">"
					+ "<a  class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>"
					+ message+"</div></div>";
	

			break;

		default:
			break;
		}
		

		return retour;
	}
}
