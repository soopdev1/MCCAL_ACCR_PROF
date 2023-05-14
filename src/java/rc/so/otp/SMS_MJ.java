package rc.so.otp;

import com.mailjet.client.ClientOptions;
import static com.mailjet.client.ClientOptions.builder;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.sms.SmsSend;

/**
 *
 * @author Administrator
 */
public class SMS_MJ {

    public static final String mailjet_smstoken = "eb142f5766544fe196a70e53e35883a4";

    public static boolean sendSMS2022(String cell, String msg) {
        try {
            ClientOptions options = builder().bearerAccessToken(mailjet_smstoken).build();
            MailjetClient client = new MailjetClient(options);
            MailjetRequest request = new MailjetRequest(SmsSend.resource)
                    .property(SmsSend.FROM, "YISU-CAL")
                    .property(SmsSend.TO, "+39" + cell)
                    .property(SmsSend.TEXT, msg);
            MailjetResponse response = client.post(request);
            if (response.getStatus() == 200) {
                return true;
            }
            System.out.println("sendSMS2022: " + response.getStatus());
            System.out.println("sendSMS2022: " + response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

//    public static void main(String[] args) {
//        sendSMS2022("3286137172", "testing message");
//    }
}
