package website.dao;

import org.apache.log4j.Logger;

public class MessageBean {
	private static final Logger LOG = Logger.getLogger(MessageBean.class);

	private String message;

	public MessageBean(String message) {
	this.message=message;
	
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
