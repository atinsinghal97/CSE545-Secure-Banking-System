package communication;

//import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component(value = "mailer")
public class Mailer {
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	public Boolean sendEmail(SimpleMailMessage message) {	
		mailSender.send(message);
		return true;
	}
	
	public Boolean sendEmail(String email, String subject, String body) {	
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "utf-8");
		String htmlMsg = body;
		try {
			message.setText(htmlMsg, true);
		    message.setTo(email); 
		    message.setSubject(subject); 
		    mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		} catch (MailException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
