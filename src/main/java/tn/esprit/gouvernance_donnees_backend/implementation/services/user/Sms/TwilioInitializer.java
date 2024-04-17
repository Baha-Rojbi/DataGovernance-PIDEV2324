package tn.esprit.gouvernance_donnees_backend.implementation.services.user.Sms;

import com.twilio.Twilio;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.gouvernance_donnees_backend.Configuration.twilioConfig.TwilioConfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class TwilioInitializer {

    @Autowired
    public TwilioInitializer(TwilioConfig twilioConfiguration) {
        Twilio.init(
                twilioConfiguration.getAccountSid(),
                twilioConfiguration.getAuthToken()
        );
        log.info("Twilio initialized ... with account sid {} ", twilioConfiguration.getAccountSid());
    }
}