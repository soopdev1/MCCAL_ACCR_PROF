package com.seta.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.seta.action.ActionB;
import static com.seta.action.ActionB.trackingAction;
import com.seta.action.Constant;
import static com.seta.action.Constant.bando;
import static com.seta.action.Constant.rb;
import com.seta.entity.UserValoriReg;
import com.sun.mail.smtp.SMTPTransport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Utility {

    public static final SimpleDateFormat timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat timestampOut = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static String generaId() {
        String random = RandomStringUtils.randomAlphanumeric(5).trim();
        return new DateTime().toString("yyMMddHHmmssSSS") + random;
    }

    public static String generaId(int ra) {
        String random = RandomStringUtils.randomAlphanumeric(ra - 15).trim();
        return new DateTime().toString("yyMMddHHmmssSSS") + random;
    }

    public static String randomP() {
        final SecureRandom random = new SecureRandom();
        String r = new BigInteger(130, random).toString(32);
        r = r.substring(0, 6);
        r = r + "!1";
        return r;
    }

    public static String convMd5(String psw) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(psw.getBytes());
            byte byteData[] = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString().trim();
        } catch (NoSuchAlgorithmException e) {
            Constant.log.severe(bando+": "+e.getMessage());
            return "-";
        }
    }

    public static boolean checkSpecialCharacter(String pas) {
        char[] ch = "<>@!#$%^&*()_+[]{}?:;|'\"\\,./~`-=".toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (pas.contains(ch[i] + "")) {
                return true;
            }
        }
        return false;
    }

    public static String replaeSpecialCharacter(String pas) {
        String out = pas;
        if (out.contains("€")) {
            // out= out.replaceAll("€",""); 
            out = StringUtils.replace(out, "€", "");
        }
        if (out.contains("ç")) {
            out = StringUtils.replace(out, "ç", "c");
        }
        char[] ch = "\"".toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (pas.contains(ch[i] + "")) {
                //out=out.replaceAll(ch[i]+"","");
                out = StringUtils.replace(out, String.valueOf(ch[i]), "");
            }

        }
        return out;
    }

    public static boolean checkNumber(String pas) {
        char[] ch = "1234567890".toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (pas.contains(ch[i] + "")) {
                return true;
            }
        }
        return false;
    }

    public static String formatD(String d) {
        SimpleDateFormat formatData2 = new SimpleDateFormat("dd/MM/yyyy");
        Date data;
        try {
            data = formatData2.parse(d);
            SimpleDateFormat formatData3 = new SimpleDateFormat("yyyyMMdd");
            return formatData3.format(data);
        } catch (ParseException e) {
            Constant.log.severe(bando+": "+e.getMessage());
            return null;
        }
    }

    public static DateTime formatDate(String dat, String pattern) {
        try {
            if (dat.length() == 21) {
                dat = dat.substring(0, 19);
            }
            if (dat.length() == pattern.length()) {
                DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
                DateTime dt = formatter.parseDateTime(dat);
                return dt;
            }
        } catch (Exception e) {
            Constant.log.severe(bando+": "+e.getMessage());
        }
        return null;
    }

    public static Date getTimestamp(String d) {
        if (d != null) {
            try {
                String ver = d;
                if (d.lastIndexOf(".") > 0) {
                    ver = d.substring(0, d.lastIndexOf("."));
                }

                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(ver);
            } catch (ParseException e) {
                Constant.log.severe(bando+": "+e.getMessage());
            }
        }
        return null;
    }

    public static String formatTimestamp(String d) {
        if (d != null) {
            SimpleDateFormat formatData2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date data;
            try {
                String ver = d;
                if (d.lastIndexOf(".") > 0) {
                    ver = d.substring(0, d.lastIndexOf("."));
                }
                data = formatData2.parse(ver);
                SimpleDateFormat formatData3 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                return formatData3.format(data);
            } catch (ParseException e) {
                //Constant.log.severe(e.getMessage());
            }
        }
        return "-";
    }

    public static boolean copy(File srcFile, File destFile) {
        if (srcFile.exists()) {
            try {
                FileUtils.copyFile(srcFile, destFile, true);
                if (destFile.exists()) {
                    return true;
                }
            } catch (IOException ex) {
                Constant.log.severe(bando+": "+ex.getMessage());
                ActionB.trackingAction("service", "Error:Utility.copy: " + ex.getMessage());
            }
        }
        return false;
    }

    public static DateTime formatDate(String dat, String pattern, boolean timestamp) {
        try {
            if (timestamp) {
                dat = dat.substring(0, dat.length() - 2);
            }
            if (dat.length() == pattern.length()) {
                DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
                DateTime dt = formatter.parseDateTime(dat);
                return dt;
            }
        } catch (Exception e) {
            Constant.log.severe(bando+": "+e.getMessage());
        }
        return null;
    }

    public static String formatStringtoStringDate(String dat, String pattern1, String pattern2, boolean timestamp) {
        try {
            if (timestamp) {
                dat = dat.substring(0, dat.length() - 2);
            }
            if (dat.length() == pattern1.length()) {
                DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern1);
                DateTime dtout = fmt.parseDateTime(dat);
                return dtout.toString(pattern2, Locale.ITALY);
            }
        } catch (Exception e) {
            Constant.log.severe(bando+": "+e.getMessage());
        }
        return "DATA ERRATA";
    }

    public static boolean zipListFiles(List<File> files, File targetZipFile) {
        try {
            OutputStream out = new FileOutputStream(targetZipFile);
            ArchiveOutputStream os = new ArchiveStreamFactory().createArchiveOutputStream("zip", out);
            for (int i = 0; i < files.size(); i++) {
                File ing = files.get(i);
                os.putArchiveEntry(new ZipArchiveEntry(ing.getName()));
                IOUtils.copy(new FileInputStream(ing), os);
                os.closeArchiveEntry();
            }
            os.close();
            out.close();
            return targetZipFile.length() > 0;
        } catch (ArchiveException | IOException ex) {
            trackingAction("service", "Error:zipListFiles: " + ex.getMessage());
            Constant.log.severe(bando+": "+ex.getMessage());
            return false;
        }
    }

    public static boolean sendMailHtml(String[] destinatari, String oggetto, String testo, File fileDaAllegare) {
        try {
            String from = rb.getString("mail_frommail");
            String host = rb.getString("mail_hostmail");
            String user = rb.getString("mail_usermailOUT");
            String password = rb.getString("mail_pswmailOUT");
            Properties props = System.getProperties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.checkserveridentity", "false");
            props.put("mail.smtp.ssl.trust", "*");
            //props.put("mail.smtp.port", rb.getString("mail_port"));
            Session session = Session.getInstance(props, null);
            Multipart mp = new MimeMultipart();
            Message msg = new MimeMessage(session);
            InternetAddress froms = new InternetAddress(from);
            froms.setPersonal(rb.getString("mail_sendermail"));
            msg.setFrom(froms);
            InternetAddress[] addressTo = new InternetAddress[destinatari.length];
            for (int i = 0; i < destinatari.length; i++) {
                addressTo[i] = new InternetAddress(destinatari[i]);
            }
            msg.setRecipients(Message.RecipientType.TO, addressTo);
            msg.setSubject(oggetto);
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setContent(testo, "text/html");
            mp.addBodyPart(mbp1);
            if (fileDaAllegare != null) {
                MimeBodyPart mbp2 = new MimeBodyPart();
                FileDataSource fds = new FileDataSource(fileDaAllegare);
                mbp2.setDataHandler(new DataHandler(fds));
                mbp2.setFileName(fds.getName());
                mp.addBodyPart(mbp2);
            }
            msg.setHeader("X-Mailer", "SendMailSMTP");
            msg.setSentDate(new Date());
            msg.setContent(mp);
            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
            t.connect(host, Utility.parseIntR(rb.getString("mail_port")), user, password);
            t.sendMessage(msg, msg.getAllRecipients());
            t.close();
            return true;
        } catch (MessagingException | UnsupportedEncodingException ex) {
            Constant.log.severe(bando+": "+ex.getMessage());
            ActionB.trackingAction("service", ex.getMessage());
            return false;
        }
    }

    public static boolean checkPDF(File pdffile) {
        if (pdffile.exists()) {
            try {
                InputStream is = new FileInputStream(pdffile);
                PdfReader pdfReader = new PdfReader(is);
                int pag = pdfReader.getNumberOfPages();
                pdfReader.close();
                is.close();
                return pag > 0;
            } catch (IOException ex) {
                Constant.log.severe(bando+": "+ex.getMessage());
            }
        }
        return false;
    }

    public static boolean detectMobile2(HttpServletRequest request) {
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            if (headerName.toLowerCase().contains("user-agent")) {
                if (headerValue.toLowerCase().contains("android")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean printNotaPdf(File src, File dest, String text) {
        try {
            InputStream is = new FileInputStream(src);
            PdfReader reader = new PdfReader(is);
            OutputStream os = new FileOutputStream(dest);
            PdfStamper stamper = new PdfStamper(reader, os);
            stamper.setRotateContents(false);
            Font fontbold = FontFactory.getFont("Times-Roman", 11, Font.NORMAL);
            Phrase p = new Phrase();
            p.setFont(fontbold);
            p.add(text);
            int rotationPage = stamper.getImportedPage(reader, 1).getRotation();
            float wh = reader.getPageSize(1).getWidth();
            float he = reader.getPageSize(1).getHeight();
            float rotate = 0;
            boolean inverse = false;
            if (rotationPage == 90 || rotationPage == 270) {
                inverse = true;
            } else {
                rotate = 90;
            }
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                PdfContentByte canvas = stamper.getOverContent(i);
                if (inverse) {
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, p, wh - 350, 20, rotate);
                } else {
                    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, p, wh - 20, 20, rotate);
                }
            }
            stamper.close();
            os.close();
            reader.close();
            is.close();
            return true;
        } catch (IOException | DocumentException ex) {
            Constant.log.severe(bando+": "+ex.getMessage());
            return false;
        }
    }

    public static String dataRegUser(ArrayList<UserValoriReg> listavalori) {

        String data = "";
        for (int i = 0; i < listavalori.size(); i++) {
            if (listavalori.get(i).getCampo().trim().equals("data")) {
                data = listavalori.get(i).getValore().trim().toUpperCase();
            }
        }
        return data;

    }

    public static String nomecognomeUser(ArrayList<UserValoriReg> listavalori) {
        String nome = "";
        String cognome = "";
        for (int i = 0; i < listavalori.size(); i++) {
            if (listavalori.get(i).getCampo().trim().equals("nome")) {
                nome = listavalori.get(i).getValore().trim().toUpperCase();
            }
            if (listavalori.get(i).getCampo().trim().equals("cognome")) {
                cognome = listavalori.get(i).getValore().trim().toUpperCase();
            }
        }
        return cognome + " " + nome;
    }

    public static String cfUser(ArrayList<UserValoriReg> listavalori) {
        String cf = "";
        for (int i = 0; i < listavalori.size(); i++) {
            if (listavalori.get(i).getCampo().trim().equals("cf")) {
                cf = listavalori.get(i).getValore().trim().toUpperCase();
            }

        }
        return cf;
    }

    public static void printRequest(HttpServletRequest request) throws ServletException, IOException {
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            for (int i = 0; i < paramValues.length; i++) {
                Constant.log.log(Level.INFO, "NORMAL FIELD - {0} : {1}", new Object[]{paramName, paramValues[i]});
            }
        }
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            try {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if (item.isFormField()) {
                        String fieldName = item.getFieldName();
                        String fieldValue = item.getString();
                        Constant.log.log(Level.INFO, "MULTIPART FIELD - {0} : {1}", new Object[]{fieldName, fieldValue});
                    } else {
                        String fieldName = item.getFieldName();
                        String fieldValue = item.getName();
                        Constant.log.log(Level.INFO, "MULTIPART FILE - {0} : {1}", new Object[]{fieldName, fieldValue});
                    }
                }
            } catch (FileUploadException ex) {
                Constant.log.severe(bando+": "+ex.getMessage());
            }
        }

    }

    public static void redirect(HttpServletRequest request, HttpServletResponse response, String destination) {
        try {
            if (response.isCommitted()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher(destination);
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect(destination);
                return;
            }
        } catch (Exception ex) {
        }
    }

    public static String getNodeValuefromName(org.w3c.dom.Document doc, String name, int index) {
        if (doc.getElementsByTagName(name).item(index) != null) {
            return doc.getElementsByTagName(name).item(index).getTextContent().trim();
        }
        return "";
    }

    public static boolean verificaPDFA(File pdf) {
        boolean out = false;
        try {
            InputStream is = new FileInputStream(pdf);
            PdfDocument pdfDoc = new PdfDocument(new com.itextpdf.kernel.pdf.PdfReader(is));
            byte[] md = pdfDoc.getXmpMetadata();
            if (md != null) {
                String s2 = new String(md);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder1 = factory.newDocumentBuilder();
                String s1 = getNodeValuefromName(builder1.parse(new InputSource(new StringReader(s2))), "pdfaid:conformance", 0);
                if (s1.equalsIgnoreCase("A") || s1.equalsIgnoreCase("B")) {
                    out = true;
                } else if (s2.contains("pdfaid:conformance='A'") || s2.contains("pdfaid:conformance='B'")) {
                    out = true;
                }

            }
            pdfDoc.close();
            is.close();
        } catch (IOException | SAXException | ParserConfigurationException ex) {
            Constant.log.severe(bando+": "+ex.getMessage());
        }
        return out;
    }

    public static int parseIntR(String value) {
        value = value.replaceAll("-", "").trim();
        if (value.contains(".")) {
            StringTokenizer st = new StringTokenizer(value, ".");
            value = st.nextToken();
        }
        int d1 = 0;
        try {
            d1 = Integer.parseInt(value);
        } catch (Exception e) {
            Constant.log.severe(bando+": "+e.getMessage());
            d1 = 0;
        }
        return d1;
    }

    public static boolean checkPdfSigned(File pdf) { //ESTENSIONE .PDF
        try {
            com.itextpdf.text.pdf.PdfReader reader = new com.itextpdf.text.pdf.PdfReader(new FileInputStream(pdf));
            AcroFields acroFields = reader.getAcroFields();
            List<String> signatureNames = acroFields.getSignatureNames();
            return signatureNames.size() > 0;
        } catch (Exception ex) {
            Constant.log.severe(bando+": "+ex.getMessage());
        }
        return false;
    }

    public static double fd(String si_t_old) {
        double d1 = 0.0D;
        si_t_old = si_t_old.replace(",", "").trim();
        try {
            d1 = Double.parseDouble(si_t_old);
        } catch (Exception e) {
            d1 = 0.0D;
        }
        return d1;
    }

    public static int fi(String si_t_old) {
        int d1 = 0;
        si_t_old = si_t_old.replace(",", "").trim();
        try {
            d1 = Integer.parseInt(si_t_old);
        } catch (Exception e) {
            d1 = 0;
        }
        return d1;
    }

    public static String roundDoubleandFormat(double d, int scale) {
        return StringUtils.replace(String.format("%." + scale + "f", d), ",", ".");
    }

    public static String formatDoubleforMysql(String value) {
        if (value == null) {
            return "0.00";
        }
        if (value.equals("-") || value.equals("")) {
            return "0.00";
        }
        String add = "";
        if (value.contains("-")) {
            add = "-";
            value = value.replaceAll("-", "").trim();
        }

        if (!value.equals("0.00")) {
            if (value.contains(",")) {
                value = value.replaceAll("\\.", "");
                value = value.replaceAll(",", ".");
            } else {
                value = value.replaceAll("\\.", "");
                return value + ".00";
            }
        }
        return add + value;

    }

    public static String formatMysqltoDisplay(String ing) {
        if (ing == null) {
            return "";
        }
        if (ing.trim().equals("") || ing.trim().equals("-")) {
            return "";
        }
        if (ing.length() == 0) {
            return "";
        }
//        if(ing.equals("0")){
//            return "0"+Constant.decimal+"00";
//        }

        String start = ing.substring(0, 1);
        if (start.equals("-") || start.equals("+")) {
            ing = ing.replaceAll(start, "");
        } else {
            start = "";
        }

        String decimal = ",";
        String thousand = ".";
        String out = "";
        if (ing.contains(",")) {
            ing = ing.replaceAll(",", "");
        }
        if (ing.contains(".")) {
            String[] inter1 = splitStringEvery(ing.split("\\.")[0], 3);
            if (inter1.length > 1) {
                for (int i = 0; i < inter1.length; i++) {
                    out = out + inter1[i] + thousand;
                }
            } else {
                out = inter1[0];
            }
            if (out.lastIndexOf(thousand) + 1 == out.length()) {
                out = out.substring(0, out.lastIndexOf(thousand));
            }
            String dec = ing.split("\\.")[1];
            out = out + decimal + dec;
        } else {

            String[] inter1 = splitStringEvery(ing, 3);
            if (inter1.length > 1) {
                for (int i = 0; i < inter1.length; i++) {
                    out = out + inter1[i] + thousand;
                }
            } else {
                out = inter1[0];
            }
            if (out.lastIndexOf(thousand) + 1 == out.length()) {
                out = out.substring(0, out.lastIndexOf(thousand));
            }
        }
        return start + out;
    }

    public static String[] splitStringEvery(String s, int interval) {
        int arrayLength = (int) Math.ceil(((s.length() / (double) interval)));
        String[] result = new String[arrayLength];
        int j = s.length();
        int lastIndex = result.length - 1;
        for (int i = lastIndex; i >= 0 && j >= interval; i--) {
            result[i] = s.substring(j - interval, j);
            j -= interval;
        } //Add the last bit
        if (result[0] == null) {
            result[0] = s.substring(0, j);
        }
        return result;
    }

    public static String formatAL(String cod, ArrayList<String[]> array, int index) {
        for (int i = 0; i < array.size(); i++) {
            if (cod.equals(((String[]) array.get(i))[0])) {
                return ((String[]) array.get(i))[index];
            }
        }
        return "-";
    }

    public static String formatTipoProposta(String t) {
        if (t.equals("1")) {
            return "Progetto di fattibilità tecnica ed economica";
        } else {
            return "Progetto definitivo";
        }
    }
    
    public static String usatoPerCheck  (String variabile) {
        if(variabile==null){
            return "NO";
        } else {
            return "SI";
        }
    }
    
    public static String[] usatoPerRadio  (String consorzioSelezione) {
        String[]var = new String[2];
        if(consorzioSelezione == null){
            var[0]="NO";
            var[1]="NO";
            return var;
        } else if(consorzioSelezione.equals("A")) {
            var[0]="SI";
            var[1]="NO";
            return var;
        } else {
            var[0]="NO";
            var[1]="SI";
            return var;
        }
    }
    
    

}
