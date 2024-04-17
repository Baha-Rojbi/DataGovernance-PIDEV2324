package tn.esprit.gouvernance_donnees_backend.implementation.services.user.Sms;

import org.springframework.stereotype.Service;

import com.twilio.type.PhoneNumber;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.gouvernance_donnees_backend.Configuration.twilioConfig.TwilioConfig;
import tn.esprit.gouvernance_donnees_backend.entities.requestEntities.SmsRequest;

@Service
@Slf4j
@AllArgsConstructor
public class TwilioSmsSenderService  {

    private final TwilioConfig twilioConfiguration;

 
    @SuppressWarnings("null")
    public void sendSms(SmsRequest smsRequest) {
        if (smsRequest!=null) {
            PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfiguration.getPhoneNumber());
            String message = smsRequest.getMessage();
            MessageCreator creator = Message.creator(to, from, message);
            creator.create();
            log.info("Send sms {}", smsRequest);
        } else {
            throw new IllegalArgumentException(
                    "Phone number [" + smsRequest.getPhoneNumber() + "] is not a valid number"
            );
        }

    }

}
