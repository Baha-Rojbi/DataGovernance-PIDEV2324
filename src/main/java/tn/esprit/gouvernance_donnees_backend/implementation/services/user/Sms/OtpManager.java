package tn.esprit.gouvernance_donnees_backend.implementation.services.user.Sms;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.gouvernance_donnees_backend.entities.requestEntities.OTPDataRequest;

@Slf4j
@Configuration
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OtpManager {

    //static to be shared accross all instances 
    private static  Map<String, OTPDataRequest> otpStorage = new HashMap<>();
    private  long otpExpirationDurationMillis;

    public String generateOTP(int length) {
        StringBuilder otp = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10)); // Generates a random digit between 0 and 9
        }
        log.info("--------------------"+otp.toString());
        return otp.toString();
    }

    public OTPDataRequest storeOTP(String phoneNumber, String otp) {
        long expirationTime = System.currentTimeMillis() + otpExpirationDurationMillis;
        return otpStorage.put(phoneNumber, new OTPDataRequest(otp, expirationTime/(60 * 1000)));
    }

    public boolean verifyOTP(String phoneNumber,String otp) {
        OTPDataRequest otpData = otpStorage.get(phoneNumber);
        for (Map.Entry<String, OTPDataRequest> entry : otpStorage.entrySet()) {
            log.info("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
        log.info("-------------------------------------------"+otpData.getExpirationTime());
        log.info("-*******************************************"+ System.currentTimeMillis()/60000);

        if (otpData != null && otpData.getOtp().equals(otp)) {
            // Check if OTP has expired
            if (otpData.getExpirationTime() >= (System.currentTimeMillis()/60000)-1) {
                log.info("----------------");
                // OTP is valid and not expired
                otpStorage.remove(phoneNumber);
                return true;
            } else {
                // OTP has expired
                otpStorage.remove(phoneNumber);
                return false;
            }
        }
        return false;
    }


}
