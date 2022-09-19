/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.util;

/**
 *
 * @author srotella
 */
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
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

/**
 *
 * @author setasrl
 */
public class SendMailJet {

    public static boolean sendMail(String name, String[] to, String[] cc, String txt, String subject) throws MailjetException, MailjetSocketTimeoutException {
        return sendMail(name, to, cc, txt, subject, null);

    }

    public static boolean sendMail(String name, String[] to, String[] cc, String txt, String subject, File file) throws MailjetException, MailjetSocketTimeoutException {
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;

        String filename = "";
        String content_type = "";
        String b64 = "";

        client = new MailjetClient("843f15d68bd377c99d9c4f494c6f2f1f", "f842a4c3439997bf53616334c162581d", new ClientOptions("v3.1"));
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

        JSONObject mail = new JSONObject().put(Emailv31.Message.FROM, new JSONObject()
                .put("Email", "yisucal.supporto@microcredito.gov.it")
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
                InputStream i = new FileInputStream(file);
                b64 = new String(Base64.encodeBase64(IOUtils.toByteArray(i)));
                i.close();
            } catch (IOException ex) {
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

        response = client.post(request);

        System.out.println(response.getStatus());

        return response.getStatus()==200;
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
