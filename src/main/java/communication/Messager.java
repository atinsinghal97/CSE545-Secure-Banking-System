package communication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Component(value = "messager")
public class Messager {
	@Value("${app.sms.account.sid}")
    public String ACCOUNT_SID;
	
	@Value("${app.sms.auth.token}")
    public String AUTH_TOKEN;
	
	@Value("${app.sms.bank.number}")
    public String BANK_PHONE;

    public void sendSms(String toNumber, String body) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(new PhoneNumber(toNumber), // to
                        new PhoneNumber(BANK_PHONE), // from
                        body)
                .create();

        System.out.println(message.getSid());
    }
}
