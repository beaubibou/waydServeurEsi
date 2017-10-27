package wayde.dao;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;




public class Outils {

 	public static String getCleActivation() {
		// TODO Auto-generated method stub
		return  ""+(int) (Math.random() * 9000 + 110);
	}
	
 	


	
	
	public  void EnvoyerMail(String mailof,String cleactivation){
		Properties props = new Properties();
		props.put("mail.smtp.host", "auth.smtp.1and1.fr");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		Session session = Session.getInstance(props,
			new javax.mail.Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication("inscription@wayd.eu","W@yd2016");
			}
			});
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("inscription@wayd.eu"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mailof));
			
			message.setSubject("Inscription Wayde");
			message.setText("Code activation:" +
					cleactivation);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public  void EnvoyerMdp(String mailof,String mdp){
		
		
		Properties props = new Properties();
	//	props.put("mail.smtp.host", "smtp.gmail.com");
	//	props.put("mail.smtp.socketFactory.port", "465");
	//	props.put("mail.smtp.socketFactory.class",
	//			"javax.net.ssl.SSLSocketFactory");
	//	props.put("mail.smtp.auth", "true");
	//	props.put("mail.smtp.port", "465");
		
		props.put("mail.smtp.host", "auth.smtp.1and1.fr");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		Session session = Session.getInstance(props,
			new javax.mail.Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication("inscription@wayd.eu","W@yd2016");
			}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("inscription@wayd.eu"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mailof));
			message.setSubject("Nouveau MDP WAYDE");
			message.setText("Votre mot de passe:" +
					mdp);
			Transport.send(message);
		//	System.out.println("Mail envoye à"+mailof);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}
	
}

