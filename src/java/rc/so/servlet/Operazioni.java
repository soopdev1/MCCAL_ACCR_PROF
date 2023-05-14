/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.servlet;

import rc.so.action.ActionB;
import static rc.so.action.ActionB.trackingAction;
import rc.so.action.Constant;
import static rc.so.action.Constant.USER_AGENT;
import static rc.so.action.Constant.bando;
import static rc.so.action.Constant.patterndatesql;
import static rc.so.action.Constant.secret;
import static rc.so.action.Constant.url;
import rc.so.db.Db_Bando;
import rc.so.entity.Docuserbandi;
import rc.so.entity.Domandecomplete;
import rc.so.entity.UserBando;
import rc.so.entity.UserValoriReg;
import rc.so.util.SendMailJet;
import rc.so.util.Utility;
import static rc.so.util.Utility.convMd5;
import static rc.so.util.Utility.formatDate;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.redirect;
import rc.so.util.GoogleRecaptcha;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.joda.time.DateTime;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import static rc.so.action.Constant.NAMEAPP;
import static rc.so.otp.OTP.generaOTP;

/**
 *
 * @author raffaele
 */
public class Operazioni extends HttpServlet {

    private ArrayList<UserValoriReg> convertValori(ArrayList<String[]> valueUser, String dateReg, String bandorif, String username) {
        ArrayList<UserValoriReg> liout = new ArrayList<>();
        for (int i = 0; i < valueUser.size(); i++) {
            UserValoriReg uv = new UserValoriReg(bandorif, username, valueUser.get(i)[0], valueUser.get(i)[1], dateReg);
            liout.add(uv);
        }
        return liout;
    }

    private boolean verify(String gRecaptchaResponse) {
        if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
            return false;
        }
        try {
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            String postParams = "secret=" + secret + "&response=" + gRecaptchaResponse;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();
            return jsonObject.getBoolean("success");
        } catch (Exception e) {
            Constant.log.severe(bando + ": " + e.getMessage());
            ActionB.trackingAction("service", "Error:Operazioni.verifycaptcha: " + e.getMessage());
        }
        return false;
    }

    //registra nuovo utente
    protected void registraUtente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean ok = false;
        String gRecaptchaResponse = "";
        String bandorif = null;
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            try {
                ArrayList<String[]> valueUser = new ArrayList<>();
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if (item.isFormField()) {
                        if (item.getFieldName().trim().equals("g-recaptcha-response")) {
                            gRecaptchaResponse = item.getString().trim();
                        } else if (!item.getFieldName().trim().equals("privacy1")) {
                            String[] val = {item.getFieldName().trim(), Utility.replaeSpecialCharacter(item.getString().trim())};
                            valueUser.add(val);
                        }
                    }
                }
                boolean verify = GoogleRecaptcha.isValid(gRecaptchaResponse);
                if (verify) {
                    if (valueUser.size() > 0) {
                        String nome = null;
                        String cognome = null;
                        String numcell = null;
                        String email = null;
                        String cf = null;
                        for (int i = 0; i < valueUser.size(); i++) {
                            if (valueUser.get(i)[0].trim().equals("bandorif")) {
                                bandorif = valueUser.get(i)[1];
                            }
                            if (valueUser.get(i)[0].trim().equals("nome")) {
                                nome = valueUser.get(i)[1];
                            }
                            if (valueUser.get(i)[0].trim().equals("cognome")) {
                                cognome = valueUser.get(i)[1];
                            }
                            if (valueUser.get(i)[0].trim().equals("cell")) {
                                numcell = valueUser.get(i)[1];
                            }
                            if (valueUser.get(i)[0].trim().equals("email")) {
                                email = valueUser.get(i)[1];
                            }
                            if (valueUser.get(i)[0].trim().equals("cf")) {
                                cf = valueUser.get(i)[1];
                            }
                        }

                        Db_Bando dbb = new Db_Bando();
                        if (bandorif != null && email != null) {
                            if (ActionB.verificaBando(bandorif)) {
                                String username = ActionB.generateUsername(nome, cognome);
                                while (ActionB.isPresenteUsername(username)) {
                                    username = ActionB.generateUsername(nome, cognome);
                                }
                                if (EmailValidator.getInstance().isValid(email)) {
                                    String pass = Utility.randomP();
                                    String passmd5 = Utility.convMd5(pass);
                                    String dateReg = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
                                    UserBando ub = new UserBando(username, passmd5, bandorif, dateReg, numcell, email, "0", ActionB.getSino(username));
                                    boolean ins = dbb.insertUserReg(ub);
                                    if (ins) {
                                        ArrayList<UserValoriReg> liout = convertValori(valueUser, dateReg, bandorif, username);
                                        int insert = dbb.insertUserRegistration(liout);
                                        if (insert > 0) {
                                            //OTP
                                            boolean out = generaOTP(bandorif, numcell, username).equals("SUCCESS");
                                            if (out) {
                                                String dest[] = {email};
                                                String text = ActionB.getPath("mailreg").replaceAll("@username", username).replaceAll("@password", pass);
                                                if (!text.equals("-")) {
//                                                        boolean es = sendMailHtml(dest, "Registrazione", text, null);
                                                    try {
                                                        boolean es = SendMailJet.sendMail(NAMEAPP, dest, new String[]{}, text, NAMEAPP + " - Registrazione");
                                                        if (es) {
                                                            trackingAction("service", "Registrato nuovo Utente: " + username);
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                        trackingAction("service", "Errore registrazione: " + e.getMessage());
                                                    }
                                                    ok = true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        dbb.closeDB();
                    }
                } else {
                    trackingAction("service", "registraUtente - Error: ROBOT GOOGLE");
                    redirect(request, response, "home.jsp?esito=okr1");
                }
            } catch (FileUploadException ex) {
                trackingAction("service", "registraUtente - Error: " + ex.getMessage());
                Constant.log.severe(bando + ": " + ex.getMessage());
            } catch (ParseException ex) {
                Logger.getLogger(Operazioni.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (ok) {
            redirect(request, response, "home.jsp?esito=okr1");
        } else {
            redirect(request, response, "bando_reg.jsp?esito=KO1&bando=" + bandorif);
        }
    }

    //registra nuova azienda
    //compilazione online
    //cancellazione domanda
    protected void erase(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codconf = request.getParameter("c");
        String confmd5 = convMd5(codconf);
        Db_Bando dbb = new Db_Bando();

        String sc = dbb.getScadenzaBando(bando);
        DateTime datafine = formatDate(sc, patterndatesql);
        DateTime datenow = formatDate(dbb.curtime(), patterndatesql);

        if (!datafine.isAfter(datenow)) {
            dbb.closeDB();
            redirect(request, response, "pageko.jsp?err=3A");
        } else {

            String[] v = dbb.controllaDomandadaEliminare(confmd5);
            String msg = "0";
            if (v != null) {
                String idrichiesta = v[0];
                String codbando = v[1];
                String username = v[2];

//                Domandecomplete doco = dbb.isDomandaCompletaConsolidata(codbando, username);
//                if (doco != null && doco.getConsolidato().equals("0")) {
                boolean es1 = dbb.cambiaStatoUser(username, "3");                   //      cambio stato
                boolean es2 = dbb.removeAllDocUserBando(codbando, username);        //      elimino i doc caricati
                boolean es3 = dbb.removeAllValoriUserBando(codbando, username);     //      ELIMINO I DATI DELL'UTENTE
                boolean es4 = dbb.annullaDomandaCompleta(codbando, username);       //      ANNULLO LA DOMANDA COMPLETA
                boolean es8 = dbb.removeAllcampiDomanda(codbando, username); //     
                if (es1 && es2 && es3 && es4 && es8) {
                    boolean es7 = dbb.cambiaStatoRichiestaAnnulla(idrichiesta, "1");
                    if (!es7) {
                        msg = "2";
                    } else {
                        trackingAction(username, "Domanda da annullare per il bando " + codbando);
                    }
                } else {
                    msg = "1";
                }
//                } else {
//                    msg = "2A";
//                }
            } else {
                msg = "3";
            }
            dbb.closeDB();
            if (msg.equals("0")) {
                redirect(request, response, "page_ok.html");
            } else {
                redirect(request, response, "pageko.jsp?err=" + msg);
            }
        }
    }

    protected void remdocdef(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codbando = request.getSession().getAttribute("bandorif").toString();
        String username = request.getSession().getAttribute("username").toString();
        Db_Bando dbb = new Db_Bando();
        String msg = "0";
        boolean es2 = dbb.removeAllDocUserBando(codbando, username);        //      elimino i doc caricati
        boolean es3 = dbb.removeAllValoriUserBandoDOC1(codbando, username); //      ELIMINO I DATI DELL'UTENTE
        boolean es4 = dbb.annullaDomandaCompleta(codbando, username);       //      ANNULLO LA DOMANDA COMPLETA
        boolean es7 = dbb.removeAllcampiDomanda(codbando, username);  //      elimno la riga inserita con i campi della domanda
        if (es2 && es3 && es4 && es7) {

        } else {
            msg = "1";
        }
        dbb.closeDB();
        if (msg.equals("0")) {
            redirect(request, response, "bando_index.jsp?esito=okrem");
        } else {
            redirect(request, response, "bando_index.jsp?esito=korem");
        }
    }

    //eliminazione doc bando
    protected void remdoc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bandorif = request.getSession().getAttribute("bandorif").toString();
        String username = request.getSession().getAttribute("username").toString();
        String tipodoc = request.getParameter("tipodoc");
        Db_Bando dbb = new Db_Bando();
        String msg = "0";
        String tipologia = request.getParameter("tipologia");
        boolean es2 = dbb.removeSingleDocUserBando(bandorif, username, tipodoc, tipologia); // elimino i doc caricati
        if (!es2) {
            msg = "1";
        }
        dbb.closeDB();
        if (msg.equals("0")) {
            trackingAction(username, "Eliminazione documento Bando: " + bandorif + " Tipodoc: " + tipodoc);
            redirect(request, response, "bando_index.jsp?esito=okrem");
        } else {
            trackingAction(username, "Errore Eliminazione documento Bando: " + bandorif + " Tipodoc: " + tipodoc);
            redirect(request, response, "bando_index.jsp?esito=korem");
        }
    }

    protected void remdocAltri(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bandorif = request.getSession().getAttribute("bandorif").toString();
        String username = request.getSession().getAttribute("username").toString();
        String tipodoc = request.getParameter("tipodoc");
        String tipologia = request.getParameter("tipologia");
        String note = request.getParameter("notemodal");

        Db_Bando dbb = new Db_Bando();
        String msg = "0";
        boolean es2 = dbb.removeSingleDocUserBandoAltri(bandorif, username, tipodoc, tipologia, note); // elimino i doc caricati
        if (!es2) {
            msg = "1";
        }
        dbb.closeDB();
        if (msg.equals("0")) {
            trackingAction(username, "Eliminazione documento Bando: " + bandorif + " Tipodoc: " + tipodoc);
            redirect(request, response, "bando_updoc.jsp?esito=0&tipodoc=" + tipodoc);
        } else {
            redirect(request, response, "bando_index.jsp?esito=korem");
        }
    }

    //invio domanda
    protected void inviadomanda(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utility.printRequest(request);
        String bandorif = request.getSession().getAttribute("bandorif").toString();
        String username = request.getSession().getAttribute("username").toString();
        String indemail = request.getSession().getAttribute("indemail").toString();
        Db_Bando dbb = new Db_Bando();
        DateTime el = new DateTime();
        //controllo che la domanda non esista già
        boolean pres = false;
        if (dbb.isDomandaPresente(bandorif, username)) {
            pres = true;
        }
        boolean att = dbb.bandoAttivoRaf(bandorif);
        dbb.closeDB();
        if (att) {
            if (!pres) {
                String datainvio = el.toString("yyyy-MM-dd HH:mm:ss");
                String idd = generaId() + username;
                Domandecomplete doc = new Domandecomplete(idd, bandorif, username, datainvio);
                Db_Bando dbb1 = new Db_Bando();
                boolean es = dbb1.inviaDomanda(doc);
                String text = dbb1.getPath("maildominv").replaceAll("@codicedocumenti", idd);
                dbb1.closeDB();

                if (es) {
                    String[] dest = {indemail};
                    try {
                        SendMailJet.sendMail(NAMEAPP, dest, new String[]{}, text, NAMEAPP + " - Conferma invio domanda di partecipazione");
                        trackingAction(username, "Invio domanda partecipazione: " + bandorif + " Id domanda: " + idd);
                        redirect(request, response, "bando_index.jsp?esito=okinvio");
                    } catch (Exception e) {
                        trackingAction(username, "Errore invio domanda: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    redirect(request, response, "bando_index.jsp?esito=koinvio");

                }

            } else {
                redirect(request, response, "bando_index.jsp?esito=koinviopresente");
            }
        } else {
            trackingAction(request.getSession().getAttribute("username").toString(), "Invio domanda fuori tempo - Logout portale");
            request.getSession().setAttribute("bandorif", null);
            request.getSession().setAttribute("statouser", null);
            request.getSession().setAttribute("username", null);
            request.getSession().setAttribute("lang", null);
            request.getSession().setAttribute("numcell", null);
            request.getSession().setAttribute("indemail", null);
            request.getSession().setAttribute("tipo", null);
            request.getSession().invalidate();
            redirect(request, response, "pageko.jsp?err=FT");
        }
    }

    //richiesta annullamento domanda
    protected void annulladomanda(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String msg = "0";
        DateTime el = new DateTime();
        String bandorif = request.getSession().getAttribute("bandorif").toString();
        String username = request.getSession().getAttribute("username").toString();
        String mail = request.getSession().getAttribute("indemail").toString();
        String datacanc = el.toString(patterndatesql);
        String codconf = generaId(20) + username + generaId(20);
        String confmd5 = convMd5(codconf);
        Db_Bando dbb = new Db_Bando();
        String contpath = dbb.getPath("linkweb");
        String link = "<a class='link-title' href='" + contpath + "/Operazioni?action=26eb25b14d930f9d5f59b2c50798a9a4&c=" + codconf + "'>Conferma cancellazione domanda</a>";
        String text = dbb.getPath("mailcanc").replaceAll("@linkreg", link);
        boolean es1 = dbb.insertDomandaDaCanc(bandorif, username, datacanc, confmd5);
        dbb.closeDB();
        if (es1) {
            String dest[] = {mail};
            try {
                //boolean es = sendMailHtml(dest, "por04.lavorocalabria.it - Cancellazione Domanda di Partecipazione", text, null);
                boolean es = SendMailJet.sendMail(NAMEAPP, dest, new String[]{}, text, NAMEAPP + " - Cancellazione Domanda di Partecipazione");
                if (!es) {
                    msg = "1";
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            msg = "2";
        }
        if (msg.equals("0")) {
            trackingAction(username, "Richiesta annullamento domanda: " + bandorif);
            redirect(request, response, "LoginOperations?type=out");
        } else {
            redirect(request, response, "bando_index.jsp?esito=kocanc");
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            String action = request.getParameter("action");
            if (action.equals("botAreU")) {
                botAreU(request, response);
            } else if (action == null) {
                action = "";
            } else if (action.equals("reg")) {
                registraUtente(request, response);
            } else if (action.equals("26eb25b14d930f9d5f59b2c50798a9a4")) {
                erase(request, response);
            } else {
                if (request.getSession().getAttribute("username") == null) {
                    redirect(request, response, "home.jsp");
                } else {
                    String bandorif = (String) request.getSession().getAttribute("bandorif");
                    String username = (String) request.getSession().getAttribute("username");
                    if (bandorif != null || username != null) {
                        if (!ActionB.domandaAnnullata(bandorif, username)) {
                            if (action.equals("annulladomanda")) {
                                annulladomanda(request, response);
                            }
                            if (action.equals("remdoc")) {
                                remdoc(request, response);
                            }
                            if (action.equals("remdocAltri")) {
                                remdocAltri(request, response);
                            }
                            if (action.equals("remdocdef")) {
                                remdocdef(request, response);
                            }
                            if (action.equals("inviadomanda")) {
                                inviadomanda(request, response);
                            }
                            if (action.equals("allegato_A")) {
                                salva_allegato_a(request, response);
                            }
                            if (action.equals("allegato_B")) {
                                salva_allegato_b(request, response);
                            }
                            if (action.equals("remdatiAllegatoA")) {
                                remdatiAllegatoA(request, response);
                            }
                            if (action.equals("remdatiAllegatoB")) {
                                remdatiAllegatoB(request, response);
                            }
                            if (action.equals("UploadMultiplo")) {
                                uploadMultiplo(request, response);
                            }
                            if (action.equals("delDocDocenti")) {
                                delDocDocenti(request, response);
                            }
                            if (action.equals("modb1")) {
                                modelloB1(request, response);
                            }
                            if (action.equals("delDocAllegatoB1")) {
                                delDocAllegatoB1(request, response);
                            }
                            if (action.equals("accettaDomanda")) {
                                accettaDomanda(request, response);
                            }
                            if (action.equals("rigettaDomanda")) {
                                rigettaDomanda(request, response);
                            }
                            if (action.equals("eliminaconv")) {
                                eliminaconv(request, response);
                            }
                            if (action.equals("sendConvenzioni")) {
                                sendConvenzioni(request, response);
                            }
                            if (action.equals("sendconv")) {
                                sendconvmail(request, response);
                            }
                        } else {
                            redirect(request, response, "LoginOperations?type=out");
                        }
                    } else {
                        redirect(request, response, "LoginOperations?type=out");
                    }
                }
            }
        } catch (ServletException | IOException ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            trackingAction("service", "Error:processRequestOperazioni: " + sw.toString());
        }
    }

    protected void sendconvmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            String username = request.getParameter("username");
            String tipodoc = request.getParameter("tipodoc");
            String to[] = {request.getParameter("email")};
            String cc[] = {request.getParameter("emailcc")};
            String var[] = ActionB.getValoriPerEmail(username);
            String testo = ActionB.getPath("mailroma");
            testo = testo.replaceAll("@ragsoc@", var[4]);
            testo = testo.replaceAll("@prot@", var[2]);
            if (SendMailJet.sendMail(NAMEAPP, to, cc, testo, "YES I Start Up Professioni Calabria - Invio convenzione del Soggetto Attuatore " + var[4], new File(ActionB.getPathConvenzioni(username, tipodoc)))) {
                if (ActionB.settaInvioEmailROMA(username)) {
                    response.sendRedirect("bando_conv.jsp?esito=ok&userdoc=" + username);
                }
            } else {
                response.sendRedirect("bando_conv.jsp?esito=ko&userdoc=" + username);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void sendConvenzioni(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getSession().getAttribute("username").toString();
        if (!ActionB.verificaInvioConvenzione(username)) {
            if (ActionB.setStatoInvioDocumenti(username)) {
                try {
                    String email = ActionB.getPath("maildest");
                    String testo = ActionB.getPath("mailmicro");
                    String to[] = email.split(",");
                    String var[] = ActionB.getValoriPerEmail(username);
                    testo = testo.replaceAll("@indent@", var[1]);
                    testo = testo.replaceAll("@ragsoc@", var[4]);
                    SendMailJet.sendMail(NAMEAPP, to, new String[]{}, testo, "YES I Start Up Professioni Calabria - Caricamento documentazione da parte del Soggetto Attuatore " + var[4], null);
                } catch (Exception ex) {
                    Logger.getLogger(Operazioni.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
                response.sendRedirect("bando_index.jsp?esito=okconv");
            } else {
                response.sendRedirect("bando_index.jsp?esito=koconv");
            }
        } else {
            response.sendRedirect("bando_index.jsp?esito=koconv2");
        }

    }

    public void eliminaconv(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getSession().getAttribute("username").toString();
        String tipodoc = request.getParameter("tipodoc");
        if (ActionB.remDocConvenzioni(username, tipodoc)) {
            response.sendRedirect("bando_index.jsp?esito=okrem");
        } else {
            response.sendRedirect("bando_index.jsp?esito=korem");
        }
    }

    public void accettaDomanda(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("usernameDomanda");
        String accRif = request.getParameter("accRif");
        String data_da = request.getParameter("data_da");
        String data_a = request.getParameter("data_a");
        String protocollo = request.getParameter("protocollo");
        if (ActionB.setStatoDomandaAccRif(username, accRif, protocollo, "---")) {
            String testo = ActionB.getPath("accettazione");
            String var[] = ActionB.getValoriPerEmail(username);
            testo = testo.replaceAll("@indent@", var[1]);
            testo = testo.replaceAll("@prot@", protocollo);
            String to[] = {var[3]};
            try {
                SendMailJet.sendMail(NAMEAPP, to, new String[]{}, testo, "YES I Start Up - Professioni Calabria - Accettazione domanda accreditamento");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            redirect(request, response, "bando_visdoc.jsp?search=1&esito=okA&data_da=" + data_da + "&data_a=" + data_a);
        } else {
            redirect(request, response, "bando_visdoc.jsp?search=1&esito=koA&data_da=" + data_da + "&data_a=" + data_a);
        }
    }

    public void rigettaDomanda(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("usernameDomanda");
        System.out.println(username);
        String accRif = request.getParameter("accRif");
        String data_da = request.getParameter("data_da");
        String data_a = request.getParameter("data_a");
        String motivazione = request.getParameter("motivo");
        if (ActionB.setStatoDomandaAccRif(username, accRif, "", motivazione)) {
            String testo = ActionB.getPath("rigetta");
            String var[] = ActionB.getValoriPerEmail(username);
            testo = testo.replaceAll("@indent@", var[1]);
            testo = testo.replaceAll("@motivazione@", motivazione);
            String to[] = {var[3]};
            try {
                SendMailJet.sendMail(NAMEAPP, to, new String[]{}, testo, NAMEAPP + " - Rigetto presentazione domanda");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            redirect(request, response, "bando_visdoc.jsp?search=1&esito=okR&data_da=" + data_da + "&data_a=" + data_a);
        } else {
            redirect(request, response, "bando_visdoc.jsp?search=1&esito=ko&data_da=" + data_da + "&data_a=" + data_a);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public static String utf8(String var) {
        try {
            return new String(var.getBytes(Charsets.ISO_8859_1), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Operazioni.class.getName()).log(Level.SEVERE, null, ex);
        }
        return var;
    }

    public void salva_allegato_a(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");

        String ch1 = Utility.usatoPerCheck(request.getParameter("ch1"));
        String ch2 = Utility.usatoPerCheck(request.getParameter("ch2"));
        String ch3 = Utility.usatoPerCheck(request.getParameter("ch3"));
        String ch4 = Utility.usatoPerCheck(request.getParameter("ch4"));
        String ch5 = Utility.usatoPerCheck(request.getParameter("ch5"));
        String regione = utf8(request.getParameter("regione"));
        String iscrizione = utf8(request.getParameter("iscrizione"));
        String ch6 = Utility.usatoPerCheck(request.getParameter("ch6"));
        String regione2 = utf8(request.getParameter("regione2"));
        String iscrizione2 = utf8(request.getParameter("iscrizione2"));
        String ch7 = Utility.usatoPerCheck(request.getParameter("ch7"));
        String numAule = request.getParameter("aule");
        String citta = utf8(request.getParameter("citta"));
        String provincia = utf8(request.getParameter("provincia"));
        String indirizzo = utf8(request.getParameter("indirizzo"));
        String estremi = utf8(request.getParameter("estremi"));
        String accreditamento = utf8(request.getParameter("accreditamento"));
        String responsabile = utf8(request.getParameter("responsabile"));
        String responsabileAmm = utf8(request.getParameter("responsabileAmm"));
        String recapiti = utf8(request.getParameter("recapiti"));
        String citta2 = utf8(request.getParameter("citta2"));
        String provincia2 = utf8(request.getParameter("provincia2"));
        String indirizzo2 = utf8(request.getParameter("indirizzo2"));
        String estremi2 = utf8(request.getParameter("estremi2"));
        String accreditamento2 = utf8(request.getParameter("accreditamento2"));
        String responsabile2 = utf8(request.getParameter("responsabile2"));
        String responsabileAmm2 = utf8(request.getParameter("responsabileAmm2"));
        String recapiti2 = utf8(request.getParameter("recapiti2"));
        String citta3 = utf8(request.getParameter("citta3"));
        String provincia3 = utf8(request.getParameter("provincia3"));
        String indirizzo3 = utf8(request.getParameter("indirizzo3"));
        String estremi3 = utf8(request.getParameter("estremi3"));
        String accreditamento3 = utf8(request.getParameter("accreditamento3"));
        String responsabile3 = utf8(request.getParameter("responsabile3"));
        String responsabileAmm3 = utf8(request.getParameter("responsabileAmm3"));
        String recapiti3 = utf8(request.getParameter("recapiti3"));
        String citta4 = utf8(request.getParameter("citta4"));
        String provincia4 = utf8(request.getParameter("provincia4"));
        String indirizzo4 = utf8(request.getParameter("indirizzo4"));
        String estremi4 = utf8(request.getParameter("estremi4"));
        String accreditamento4 = utf8(request.getParameter("accreditamento4"));
        String responsabile4 = utf8(request.getParameter("responsabile4"));
        String responsabileAmm4 = utf8(request.getParameter("responsabileAmm4"));
        String recapiti4 = utf8(request.getParameter("recapiti4"));
        String citta5 = utf8(request.getParameter("citta5"));
        String provincia5 = utf8(request.getParameter("provincia5"));
        String indirizzo5 = utf8(request.getParameter("indirizzo5"));
        String estremi5 = utf8(request.getParameter("estremi5"));
        String accreditamento5 = utf8(request.getParameter("accreditamento5"));
        String responsabile5 = utf8(request.getParameter("responsabile5"));
        String responsabileAmm5 = utf8(request.getParameter("responsabileAmm5"));
        String recapiti5 = utf8(request.getParameter("recapiti5"));
        String attivita = utf8(request.getParameter("attivita"));
        String destinatari = utf8(request.getParameter("destinatari"));
        String finanziamento = utf8(request.getParameter("finanziamento"));
        String committente = utf8(request.getParameter("committente"));
        String periodo = utf8(request.getParameter("periodo"));
        String attivita2 = utf8(request.getParameter("attivita2"));
        String destinatari2 = utf8(request.getParameter("destinatari2"));
        String finanziamento2 = utf8(request.getParameter("finanziamento2"));
        String committente2 = utf8(request.getParameter("committente2"));
        String periodo2 = utf8(request.getParameter("periodo2"));
        String attivita3 = utf8(request.getParameter("attivita3"));
        String destinatari3 = utf8(request.getParameter("destinatari3"));
        String finanziamento3 = utf8(request.getParameter("finanziamento3"));
        String committente3 = utf8(request.getParameter("committente3"));
        String periodo3 = utf8(request.getParameter("periodo3"));
        String attivita4 = utf8(request.getParameter("attivita4"));
        String destinatari4 = utf8(request.getParameter("destinatari4"));
        String finanziamento4 = utf8(request.getParameter("finanziamento4"));
        String committente4 = utf8(request.getParameter("committente4"));
        String periodo4 = utf8(request.getParameter("periodo4"));
        String attivita5 = utf8(request.getParameter("attivita5"));
        String destinatari5 = utf8(request.getParameter("destinatari5"));
        String finanziamento5 = utf8(request.getParameter("finanziamento5"));
        String committente5 = utf8(request.getParameter("committente5"));
        String periodo5 = utf8(request.getParameter("periodo5"));
        String[] var = Utility.usatoPerRadio(request.getParameter("consorzioSelezione"));
        String noconsorzio = utf8(var[0]);
        String consorzio = utf8(var[1]);
        String nomeConsorzio = utf8(request.getParameter("consorzio"));
        String pec = utf8(request.getParameter("pec"));
        String numdocenti = utf8(request.getParameter("numeroDocenti"));
        if (ActionB.insAllegatoA(username, ch1, ch2, ch3, ch4, ch5, regione, iscrizione, ch6, regione2, iscrizione2, ch7,
                numAule, citta, provincia, indirizzo, estremi, accreditamento, responsabile, responsabileAmm, recapiti,
                citta2, provincia2, indirizzo2, estremi2, accreditamento2, responsabile2, responsabileAmm2, recapiti2,
                citta3, provincia3, indirizzo3, estremi3, accreditamento3, responsabile3, responsabileAmm3, recapiti3,
                citta4, provincia4, indirizzo4, estremi4, accreditamento4, responsabile4, responsabileAmm4, recapiti4,
                citta5, provincia5, indirizzo5, estremi5, accreditamento5, responsabile5, responsabileAmm5, recapiti5,
                attivita, destinatari, finanziamento, committente, periodo,
                attivita2, destinatari2, finanziamento2, committente2, periodo2,
                attivita3, destinatari3, finanziamento3, committente3, periodo3,
                attivita4, destinatari4, finanziamento4, committente4, periodo4,
                attivita5, destinatari5, finanziamento5, committente5, periodo5, noconsorzio, consorzio, nomeConsorzio, pec, numdocenti)) {
            redirect(request, response, "bando_index.jsp?esito=ok1");
        } else {
            redirect(request, response, "bando_index.jsp?esito=ko1");
        }
    }

    private void remdatiAllegatoA(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        if (ActionB.remdatiAllegatoA(username)) {
            ActionB.remdatiAllegatoB(username);
            ActionB.removeSingleDocUserBando("BA0F6", username, "DONLB", "-");
            ActionB.removeSingleDocUserBando("BA0F6", username, "ALB1", "-");
            ActionB.delAllAllegatiDocenti(username);
            ActionB.delAllegatoB1Field(username);
            redirect(request, response, "bando_index.jsp?esito=okrem1");
        } else {
            redirect(request, response, "bando_index.jsp?esito=korem");
        }
    }

    private void salva_allegato_b(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        String docNome = utf8(request.getParameter("docNome"));
        String docCognome = utf8(request.getParameter("docCognome"));
        String fascia = utf8(request.getParameter("fascia"));
        String inquedramento = utf8(request.getParameter("inquadramento"));
        String docNome2 = utf8(request.getParameter("docNome2"));
        String docCognome2 = utf8(request.getParameter("docCognome2"));
        String fascia2 = utf8(request.getParameter("fascia2"));
        String inquedramento2 = utf8(request.getParameter("inquadramento2"));
        String docNome3 = utf8(request.getParameter("docNome3"));
        String docCognome3 = utf8(request.getParameter("docCognome3"));
        String fascia3 = utf8(request.getParameter("fascia3"));
        String inquedramento3 = utf8(request.getParameter("inquadramento3"));
        String docNome4 = utf8(request.getParameter("docNome4"));
        String docCognome4 = utf8(request.getParameter("docCognome4"));
        String fascia4 = utf8(request.getParameter("fascia4"));
        String inquedramento4 = utf8(request.getParameter("inquadramento4"));
        String docNome5 = utf8(request.getParameter("docNome5"));
        String docCognome5 = utf8(request.getParameter("docCognome5"));
        String fascia5 = utf8(request.getParameter("fascia5"));
        String inquedramento5 = utf8(request.getParameter("inquadramento5"));
        if (ActionB.insAllegatoB(username, docNome, docCognome, fascia, inquedramento,
                docNome2, docCognome2, fascia2, inquedramento2,
                docNome3, docCognome3, fascia3, inquedramento3,
                docNome4, docCognome4, fascia4, inquedramento4,
                docNome5, docCognome5, fascia5, inquedramento5)) {
            redirect(request, response, "bando_index.jsp?esito=ok1");
        } else {
            redirect(request, response, "bando_index.jsp?esito=ko2");
        }
    }

    private void remdatiAllegatoB(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        if (ActionB.remdatiAllegatoB(username)) {
            ActionB.removeSingleDocUserBando("BA0F6", username, "DONLB", "-");
            ActionB.removeSingleDocUserBando("BA0F6", username, "ALB1", "-");
            ActionB.delAllAllegatiDocenti(username);
            ActionB.delAllegatoB1Field(username);
            redirect(request, response, "bando_index.jsp?esito=okrem1");
        } else {
            redirect(request, response, "bando_index.jsp?esito=korem");
        }
    }

    private String controllaEstensione(String estensione) {
        if (estensione.equalsIgnoreCase(".pdf") || estensione.equalsIgnoreCase(".p7m")) {
            return "OK";
        }
        return "1";
    }

    protected void uploadMultiplo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = "/mnt/mcprofessioni/bandoba0h8/doc/";
        String nomiFiles[] = new String[3];
        String username = (String) request.getSession().getAttribute("username");
        String id = "";
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String yyyyMMdd = sdf.format(date);
        if (!isMultipart) {
        } else {
            FileItemFactory factory = new DiskFileItemFactory();
            File directory = new File(path + yyyyMMdd);
            if (!directory.mkdir()) {
            }
            ServletFileUpload upload = new ServletFileUpload(factory);
            List items = null;
            try {
                items = upload.parseRequest(request);
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
            Iterator itr = items.iterator();
            int i = 0;
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                if (item.isFormField()) {
                    if (item.getFieldName().equals("id")) {
                        id = item.getString();
                    }
                } else {
                    try {
                        String estensione = item.getName().substring(item.getName().lastIndexOf("."));
                        if (estensione.equalsIgnoreCase(".pdf") || estensione.equalsIgnoreCase(".p7m")) {
                            String generatedString = RandomStringUtils.random(30, true, true);
                            File savedFile = new File(directory + File.separator + username + generatedString + estensione);
                            item.write(savedFile);
                            nomiFiles[i] = directory + File.separator + username + generatedString + estensione;
                            i++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (controllaEstensione(nomiFiles[0].substring(nomiFiles[0].lastIndexOf("."))).equals("OK") && controllaEstensione(nomiFiles[1].substring(nomiFiles[1].lastIndexOf("."))).equals("OK") && controllaEstensione(nomiFiles[2].substring(nomiFiles[2].lastIndexOf("."))).equals("OK")) {
                if (ActionB.insAllegatoB1(id, username, nomiFiles[0], nomiFiles[1], nomiFiles[2])) {
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (ActionB.esisteAllegatoB1(username)) {
                        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Docuserbandi dub = new Docuserbandi();
                        dub.setCodbando("BA0F6");
                        dub.setUsername(username);
                        dub.setCodicedoc("ALB1");
                        dub.setStato("1");
                        dub.setPath("-");
                        dub.setDatacar(sdf2.format(date));
                        dub.setTipodoc("-");
                        dub.setNote("-");
                        ActionB.insertDocumentUserBando(dub);
                    }
                    if (ActionB.esisteAllegatoB1(username)) {
                        redirect(request, response, "bando_onlinecomp2.jsp?allegato_A_B=C&esito=okb1");
                    } else {
                        redirect(request, response, "bando_onlinecomp2.jsp?allegato_A_B=C&esito=ok");
                        redirect(request, response, "bando_onlinecomp2.jsp?allegato_A_B=C&esito=ok");

                    }
                    redirect(request, response, "bando_onlinecomp2.jsp?allegato_A_B=C&esito=ok");
                } else {
                    redirect(request, response, "bando_onlinecomp2.jsp?allegato_A_B=C&esito=ko");
                }
            } else {
                redirect(request, response, "bando_onlinecomp2.jsp?allegato_A_B=C&esito=ko4");
            }
        }
    }

    protected void delDocDocenti(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id_doc = request.getParameter("id_doc");
        String username = request.getParameter("username");
        if (ActionB.delAllegatiDocenti(username, id_doc)) {
//            ActionB.delAllegatoB1Field(id_doc, username);
            redirect(request, response, "bando_onlinecomp2.jsp?allegato_A_B=C&esito=ok1");

        } else {
            redirect(request, response, "bando_onlinecomp2.jsp?allegato_A_B=C&esito=ko1");
        }
    }

    protected void modelloB1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id_doc = utf8(request.getParameter("iddocente"));
        String username = utf8(request.getParameter("username"));
        String periodo = utf8(request.getParameter("periodo"));
        String durata = utf8(request.getParameter("durata"));
        String ggmm = utf8(request.getParameter("ggmm"));
        String fonte = utf8(request.getParameter("fonte"));
        String tipo = utf8(request.getParameter("tipo"));
        String elenco = utf8(request.getParameter("elenco"));
        String sa = utf8(request.getParameter("sa"));
        Db_Bando db = new Db_Bando();
        boolean insAllb1_1 = db.insAllegatoB1Field(id_doc, username, periodo, durata + " - " + ggmm, tipo, fonte, elenco, sa);
        boolean insAllb1_2 = true;
        boolean insAllb1_3 = true;
        boolean insAllb1_4 = true;
        boolean insAllb1_5 = true;
        String periodo2 = utf8(request.getParameter("periodo2"));
        if (!periodo2.equals("")) {
            String ggmm2 = utf8(request.getParameter("ggmm2"));
            String durata2 = utf8(request.getParameter("durata2"));
            String fonte2 = utf8(request.getParameter("fonte2"));
            String tipo2 = utf8(request.getParameter("tipo2"));
            String elenco2 = utf8(request.getParameter("elenco2"));
            String sa2 = utf8(request.getParameter("sa2"));
            insAllb1_2 = db.insAllegatoB1Field(id_doc, username, periodo2, durata2 + " - " + ggmm2, tipo2, fonte2, elenco2, sa2);
        }
        String periodo3 = utf8(request.getParameter("periodo3"));
        if (!periodo3.equals("")) {
            String ggmm3 = utf8(request.getParameter("ggmm3"));
            String durata3 = utf8(request.getParameter("durata3"));
            String fonte3 = utf8(request.getParameter("fonte3"));
            String tipo3 = utf8(request.getParameter("tipo3"));
            String elenco3 = utf8(request.getParameter("elenco3"));
            String sa3 = utf8(request.getParameter("sa3"));
            insAllb1_3 = db.insAllegatoB1Field(id_doc, username, periodo3, durata3 + " - " + ggmm3, tipo3, fonte3, elenco3, sa3);
        }

        String periodo4 = utf8(request.getParameter("periodo4"));
        if (!periodo4.equals("")) {
            String ggmm4 = utf8(request.getParameter("ggmm4"));
            String durata4 = utf8(request.getParameter("durata4"));
            String fonte4 = utf8(request.getParameter("fonte4"));
            String tipo4 = utf8(request.getParameter("tipo4"));
            String elenco4 = utf8(request.getParameter("elenco4"));
            String sa4 = utf8(request.getParameter("sa4"));
            insAllb1_4 = db.insAllegatoB1Field(id_doc, username, periodo4, durata4 + " - " + ggmm4, tipo4, fonte4, elenco4, sa4);
        }
        String periodo5 = utf8(request.getParameter("periodo5"));
        if (!periodo5.equals("")) {
            String ggmm5 = utf8(request.getParameter("ggmm5"));
            String durata5 = utf8(request.getParameter("durata5"));
            String fonte5 = utf8(request.getParameter("fonte5"));
            String tipo5 = utf8(request.getParameter("tipo5"));
            String elenco5 = utf8(request.getParameter("elenco5"));
            String sa5 = utf8(request.getParameter("sa5"));
            insAllb1_5 = db.insAllegatoB1Field(id_doc, username, periodo5, durata5 + " - " + ggmm5, tipo5, fonte5, elenco5, sa5);
        }
        db.closeDB();
        if (insAllb1_1 && insAllb1_2 && insAllb1_3 && insAllb1_4 && insAllb1_5) {
            redirect(request, response, "bando_onlinecomp2.jsp?allegato_A_B=C&esito=ok2");
        } else {
            ActionB.delAllegatoB1Field(id_doc, username);
            redirect(request, response, "bando_onlinecomp2.jsp?allegato_A_B=C&esito=ko2");
        }
    }

    protected void delDocAllegatoB1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String iddocente = request.getParameter("iddocente");
        if (ActionB.delAllegatoB1Field(iddocente, username)) {
            redirect(request, response, "bando_onlinecomp2.jsp?allegato_A_B=C&esito=ok3");
        } else {
            redirect(request, response, "bando_onlinecomp2.jsp?allegato_A_B=C&esito=ko3");
        }
    }

    protected void botAreU(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        JSONObject result = new JSONObject();
        try {
            result.put("result", GoogleRecaptcha.isValid(request.getParameter("g-recaptcha-response")));
        } catch (org.json.simple.parser.ParseException ex) {
            result.put("result", false);
            ex.printStackTrace();
        }
        response.getWriter().write(result.toString());
        response.getWriter().flush();
        response.getWriter().close();
    }
}
