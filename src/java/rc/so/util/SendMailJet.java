/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.util;

/**
 *
 * @author srotella
 */
import com.mailjet.client.ClientOptions;
import static com.mailjet.client.ClientOptions.builder;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import rc.so.action.ActionB;
import rc.so.db.Db_Bando;

/**
 *
 * @author setasrl
 */
public class SendMailJet {

    public static boolean sendMail(String name, String[] to, String[] cc, String txt, String subject) {
        return sendMail(name, to, cc, txt, subject, null);

    }

    public static boolean sendMail(String name, String[] to, String[] cc, String txt, String subject, File file) {
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;

        String filename = "";
        String content_type = "";
        String b64 = "";

        Db_Bando db1 = new Db_Bando();
        String mailjet_api = db1.getPath("mailjet_api");
        String mailjet_secret = db1.getPath("mailjet_secret");
        String mailjet_name = db1.getPath("mailjet_name");
        db1.closeDB();

        ClientOptions options = builder()
                .apiKey(mailjet_api)
                .apiSecretKey(mailjet_secret)
                .build();

        client = new MailjetClient(options);

//        client.setDebug(1);
        JSONArray dest = new JSONArray();
        JSONArray ccn = new JSONArray();
        JSONArray ccj = new JSONArray();

        if (to != null) {
            for (String s : to) {
                dest.put(new JSONObject().put("Email", s)
                        .put("Name", ""));
            }
        } else {
            dest.put(new JSONObject().put("Email", "")
                    .put("Name", ""));
        }

        if (cc != null) {
            for (String s : cc) {
                ccj.put(new JSONObject().put("Email", s)
                        .put("Name", ""));
            }
        } else {
            ccj.put(new JSONObject().put("Email", "")
                    .put("Name", ""));
        }

//        try {
//            ccn.put(new JSONObject().put("Email", getPath("mail.bcc"))
//                    .put("Name", ""));
//        } catch (Exception ee1) {
//        }
        JSONObject mail = new JSONObject().put(Emailv31.Message.FROM, new JSONObject()
                .put("Email", mailjet_name)
                .put("Name", name))
                .put(Emailv31.Message.TO, dest)
                .put(Emailv31.Message.CC, ccj)
                .put(Emailv31.Message.BCC, ccn)
                .put(Emailv31.Message.SUBJECT, subject)
                .put(Emailv31.Message.HTMLPART, txt);

        if (file != null) {
            try {
                filename = file.getName();
                content_type = Files.probeContentType(file.toPath());
                try ( InputStream i = new FileInputStream(file)) {
                    b64 = new String(Base64.encodeBase64(IOUtils.toByteArray(i)));
                }
            } catch (Exception ex) {
                ActionB.trackingAction("Service", ex.getMessage());
                ex.printStackTrace();
            }
            mail.put(Emailv31.Message.ATTACHMENTS, new JSONArray()
                    .put(new JSONObject()
                            .put("ContentType", content_type)
                            .put("Filename", filename)
                            .put("Base64Content", b64)));
        }

        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(mail));

        try {
            response = client.post(request);
            boolean ok = response.getStatus() == 200;
            
            if (!ok) {
                System.err.println("ERRORE: sendMail - " + response.getStatus() + " -- " + response.getRawResponseContent() + " --- " + response.getData().toList());
            }
            
            return ok;
        } catch (Exception ex) {
            ActionB.trackingAction("Service", ex.getMessage());
            ex.printStackTrace();
        }
        return false;
//        System.out.println(response.getTotal());
    }

//    public static void main(String[] args) {
//        String to[] = {"srotella@setacom.it"};
//        String from[] = {"srotella@setacom.it"};
//        try {
//            sendMail("SIMONE", to, to, "testo email", "test oggetto", null);
//        } catch (MailjetException ex) {
//            Logger.getLogger(SendMailJet.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (MailjetSocketTimeoutException ex) {
//            Logger.getLogger(SendMailJet.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
