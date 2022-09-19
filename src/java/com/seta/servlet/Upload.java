/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.servlet;

import com.seta.action.ActionB;
import static com.seta.action.ActionB.trackingAction;
import com.seta.action.Constant;
import static com.seta.action.Constant.bando;
import com.seta.db.Db_Bando;
import com.seta.entity.Docbandi;
import com.seta.entity.Docuserbandi;
import com.seta.entity.Docuserconvenzioni;
import com.seta.util.SendMailJet;
import com.seta.util.Utility;
import static com.seta.util.Utility.redirect;
import java.io.File;
import java.io.IOException;
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

/**
 *
 * @author raffaele
 */
public class Upload extends HttpServlet {

    //upload rup
    protected void simpleRUP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Bando dbb = new Db_Bando();
        String tipodoc = "RALL";
        String bandorif = request.getSession().getAttribute("bandorif").toString();
        String usr = request.getSession().getAttribute("username").toString();
        String username = "";
        String tipologia = "";
        String note = "-";
        DateTime el = new DateTime();
        String dateReg = el.toString("yyyy-MM-dd HH:mm:ss");
        String dateFile = el.toString("yyyyMMddHHmmssSSS");
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        File nomefile = null;
        String msg = "0";
        if (isMultipart) {
            try {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if (item.isFormField()) {
                        if (item.getFieldName().equals("tipologia")) {
                            tipologia = item.getString();
                        }
                        if (item.getFieldName().equals("note")) {
                            note = item.getString();
                        }
                        if (item.getFieldName().equals("userdoc")) {
                            username = item.getString();
                        }
                    } else {
                        String fileName = item.getName();
                        if (fileName != null) {
                            if (!tipodoc.equals("")) {
                                if (fileName.lastIndexOf(".") > 0) {
                                    File pathdir = new File(dbb.getPath("pathdoc") + el.toString("yyyyMMdd"));
                                    pathdir.mkdirs();
                                    String estensione = fileName.substring(fileName.lastIndexOf("."));
                                    if (estensione.equalsIgnoreCase(".pdf") || estensione.equalsIgnoreCase(".p7m")) {
                                        String name = bandorif + tipodoc + username + "_R" + dateFile + estensione;
                                        nomefile = new File(pathdir + File.separator + name);
                                        try {
                                            item.write(nomefile);
                                        } catch (Exception ex) {
                                            Constant.log.severe(bando + ": " + ex.getMessage());
                                            msg = "1";
                                            trackingAction(username, "Error:uploadSimpleRup: " + ex.getMessage());
                                            nomefile = null;
                                        }
                                    } else {
                                        msg = "1";
                                        trackingAction(username, "Error:uploadSimple: Estensione non valida");
                                        nomefile = null;
                                    }
                                } else {
                                    msg = "1";
                                    trackingAction(username, "Error:uploadSimple: File senza estensione");
                                    nomefile = null;
                                }
                            }
                        }
                    }
                }
            } catch (FileUploadException ex) {
                Constant.log.severe(bando + ": " + ex.getMessage());
                trackingAction(username, "Error:uploadSimpleRup: " + ex.getMessage());
                msg = "1";
            }
        }
        if (nomefile != null) {
            Docuserbandi dub = new Docuserbandi(bandorif, username, tipodoc, "1", nomefile.getPath(), dateReg, tipologia, (note + " Caricato dal RUP: " + usr).trim());
            boolean es1 = dbb.insertDocUserBando(dub);
            if (!es1) {
                msg = "3";
            }
        } else {
            msg = "2";
        }
        dbb.closeDB();
        if (msg.equals("0")) {
            trackingAction(username, "Caricamento documento da parte del RUP: " + tipodoc + " - " + tipologia);
            redirect(request, response, "bando_updoc_rup.jsp?userdoc=" + username + "&esito=" + msg);
        } else {
            redirect(request, response, "bando_updoc_rup.jsp?userdoc=" + username + "&esito=" + msg);
        }
    }

    private String controllaEstensione(String tipodoc, String estensione) {
        if (estensione.equalsIgnoreCase(".pdf") || estensione.equalsIgnoreCase(".p7m")) {
            return "OK";
        }
        return "1";
    }

    private String controllaFile(String username, String tipodoc, String estensione, int dimmax, File pdf) {

        if (pdf.length() > dimmax) {
            trackingAction(username, "Error:uploadSimple: Dimensione File maggiore di quella consentita");
            return "11";
        }

        if (estensione.equalsIgnoreCase(".pdf") || estensione.equalsIgnoreCase(".p7m")) {
            return "OK";
        }
        return "1";
    }

    private String controllaFileOnlyP7m(String username, String tipodoc, String estensione, int dimmax, File pdf) {

        if (pdf.length() > dimmax) {
            trackingAction(username, "Error:uploadSimple: Dimensione File maggiore di quella consentita");
            return "11";
        }

        if (estensione.equalsIgnoreCase(".p7m")) {
            return "OK";
        }
        return "1";
    }

    //upload classico doc
    protected void simple(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Bando dbb = new Db_Bando();
        String bandorif = request.getSession().getAttribute("bandorif").toString();
        String username = request.getSession().getAttribute("username").toString();
        String tipodoc = "";
        String tipologia = "-";
        String note = "-";
        DateTime el = new DateTime();
        String dateReg = el.toString("yyyy-MM-dd HH:mm:ss");
        String dateFile = el.toString("yyyyMMddHHmmssSSS");
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        File nomefile = null;
//        File nomefiletemp = null;
        String msg = "0";
        int dimmax = Utility.parseIntR(dbb.getPath("dim"));
        if (isMultipart) {
            try {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if (item.isFormField()) {
                        if (item.getFieldName().equals("tipodoc")) {
                            tipodoc = item.getString();
                        }
                        if (item.getFieldName().equals("tipologia")) {
                            tipologia = item.getString();
                        }
                        if (item.getFieldName().equals("note")) {
                            note = item.getString();
                        }
                    } else {
                        String fileName = item.getName();
                        if (fileName != null) {
                            if (!tipodoc.equals("")) {
                                if (ActionB.isPresenzaDocumento(username, tipodoc)) {
                                    redirect(request, response, "bando_index.jsp?esito=kodop");
                                } else {
                                    if (fileName.lastIndexOf(".") > 0) {
                                        File pathdir = new File(dbb.getPath("pathdoc") + el.toString("yyyyMMdd"));
                                        pathdir.mkdirs();
                                        String estensione = fileName.substring(fileName.lastIndexOf("."));
                                        if (controllaEstensione(tipodoc, estensione).equals("OK")) {
                                            String name = bandorif + tipodoc + username + dateFile + estensione;
                                            nomefile = new File(pathdir + File.separator + name);
                                            //devo testare la dimensione del file
                                            try {
                                                item.write(nomefile);

                                                String controllifile = controllaFile(username, tipodoc, estensione, dimmax, nomefile);
                                                if (!controllifile.equals("OK")) {
                                                    nomefile = null;
                                                    msg = controllifile;
                                                }
//                                            chiudo gli eventuali field aperti
//                                            InputStream is = new FileInputStream(nomefiletemp);
//                                            PdfReader reader = new PdfReader(is);
//                                            nomefile = new File(pathdir + File.separator + name + new DateTime().toString("ddMMyyyyHHmmSSS") + ".up2.pdf");
//                                            OutputStream os = new FileOutputStream(nomefile);
//                                            PdfStamper stamper = new PdfStamper(reader, os);
//                                            stamper.setFormFlattening(true);
//                                            stamper.close();
//                                            os.close();
//                                            reader.close();
//                                            is.close();
//                                            if (nomefile.length() > 4194304) {
//                                            if (nomefile.length() > dimmax) {
//                                                msg = "11";
//                                                trackingAction(username, "Error:uploadSimple: Dimensione File maggiore di quella consentita");
//                                                nomefile = null;
//                                            }
                                            } catch (Exception ex) {
                                                Constant.log.severe(bando + ": " + ex.getMessage());
                                                msg = "1";
                                                trackingAction(username, "Error:uploadSimple: " + ex.getMessage());
                                                nomefile = null;
                                            }
                                        } else {
                                            msg = "1";
                                            trackingAction(username, "Error:uploadSimple: Estensione non valida");
                                            nomefile = null;
                                        }
                                    } else {
                                        msg = "1";
                                        trackingAction(username, "Error:uploadSimple: File senza estensione");
                                        nomefile = null;
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (FileUploadException ex) {
                Constant.log.severe(bando + ": " + ex.getMessage());
                trackingAction(username, "Error:uploadSimple: " + ex.getMessage());
                msg = "1";
            }
        }

        boolean check = true;
        boolean isaltro = false;
//        if (tipodoc.startsWith("PROG")) {
//            isaltro = true;
//        }
//
//        if (isaltro) {
//            if ((dbb.notaIsPresente(bandorif, username, note))) {
//                check = false;
//            }
//
//        }

        if (nomefile != null && check) {
            ArrayList<Docbandi> lid1 = ActionB.listaDocRichiesti(bandorif);
            Docbandi d = null;
            for (int i = 0; i < lid1.size(); i++) {
                if (lid1.get(i).getCodicedoc().equals(tipodoc)) {
                    d = lid1.get(i);
                }
            }
            if (d != null) {

                isaltro = d.getCollegati().equals("1");

                Docuserbandi dub = new Docuserbandi(bandorif, username, tipodoc, "1", nomefile.getPath(), dateReg, tipologia, note);
                boolean es1 = dbb.insertDocUserBando(dub);
                if (!es1) {
                    msg = "10";
                }
            } else {
                msg = "2";
            }

        }

        if (!check) {
            msg = "10";
        }

        //1 errore caricamento file
        //2 file non conforme 
        //3 documento già presente o errore inserimento
        dbb.closeDB();
        if (msg.equals("0")) {
            trackingAction(username, "Caricamento documento: " + tipodoc);
            if (isaltro) {
                redirect(request, response, "bando_updoc.jsp?esito=ok&tipodoc=" + tipodoc);
            } else {
                redirect(request, response, "bando_index.jsp?esito=ok");
            }
        } else {
            redirect(request, response, "bando_updoc.jsp?tipodoc=" + tipodoc + "&esito=" + msg);
        }

    }

    //upload classico doc
    protected void simpleConvenzioni(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        boolean ctrl = true;
        Db_Bando dbb = new Db_Bando();
        String bandorif = request.getSession().getAttribute("bandorif").toString();
        String data_da = "";
        String data_a = "";
        String ut = request.getSession().getAttribute("username").toString();
        String tipodoc = "";
        DateTime el = new DateTime();
        String dateFile = el.toString("yyyyMMddHHmmssSSS");
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        File nomefile = null;
        String msg = "0";
        int dimmax = Utility.parseIntR(dbb.getPath("dim"));
        if (isMultipart) {
            try {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if (item.isFormField()) {
                        if (item.getFieldName().equals("tipodoc")) {
                            tipodoc = item.getString();
                        }
                        if (item.getFieldName().equals("username")) {
                            username = item.getString();
                        }
                        if (item.getFieldName().equals("data_da")) {
                            data_da = item.getString();
                        }
                        if (item.getFieldName().equals("data_a")) {
                            data_a = item.getString();
                        }
                    } else {
                        String fileName = item.getName();
                        if (fileName != null) {
                            if (fileName.lastIndexOf(".") > 0) {
                                File pathdir = new File(dbb.getPath("pathdoc") + el.toString("yyyyMMdd"));
                                pathdir.mkdirs();
                                String estensione = fileName.substring(fileName.lastIndexOf("."));
                                if (controllaEstensione(tipodoc, estensione).equals("OK")) {
                                    String name = bandorif + tipodoc + username + dateFile + estensione;
                                    nomefile = new File(pathdir + File.separator + name);
                                    try {
                                        item.write(nomefile);
                                        String controllifile = controllaFile(ut, tipodoc, estensione, dimmax, nomefile);
                                        if (!controllifile.equals("OK")) {
                                            nomefile = null;
                                            msg = controllifile;
                                        }
                                    } catch (Exception ex) {
                                        Constant.log.severe(bando + ": " + ex.getMessage());
                                        msg = "1";
                                        trackingAction(ut, "Error:uploadSimple: " + ex.getMessage());
                                        nomefile = null;
                                    }
                                } else {
                                    msg = "1";
                                    trackingAction(ut, "Error:uploadSimple: Estensione non valida");
                                    nomefile = null;
                                }
                            } else {
                                msg = "1";
                                trackingAction(ut, "Error:uploadSimple: File senza estensione");
                                nomefile = null;
                            }
                        }
                    }

                }
            } catch (FileUploadException ex) {
                Constant.log.severe(bando + ": " + ex.getMessage());
                trackingAction(ut, "Error:uploadSimple: " + ex.getMessage());
                msg = "1";
            }
        }
        dbb.closeDB();
        if (msg.equals("0")) {
            if (ActionB.getConvenzioneROMA(username).trim().equals("")) {
                trackingAction(username, "Caricamento documento: " + tipodoc);
                if (ActionB.insertConvenzioneROMA(username, nomefile.getPath())) {
                    response.sendRedirect("bando_visdoc.jsp?esito=ok1&search=1&data_da=" + data_da + "&data_a=" + data_a);
                    String testo = ActionB.getPath("mailSARoma");
                    String var[] = ActionB.getValoriPerEmail(username);
                    String to[] = {var[3]};
                    try {
                        SendMailJet.sendMail("YES I START UP CALABRIA - Donne e Disoccupati",to , new String[]{}, testo, "YES I Start Up Professoni Calabria - Caricamento convenzione controfirmata da Ente Nazionale Microcredito");
                        trackingAction(ut, "Invio email al SA: " + to[0]);
                    } catch (Exception e) {
                        e.printStackTrace();
                        trackingAction(ut, "Errore invio email al SA: " + e.getMessage());
                    }
                } else {
                    response.sendRedirect("bando_visdoc.jsp?esito=ko1&search=1&data_da=" + data_da + "&data_a=" + data_a);
                } 
            } else {
                response.sendRedirect("bando_visdoc.jsp?esito=ko2&search=1&data_da=" + data_da + "&data_a=" + data_a);
            }

        } else {
            response.sendRedirect("bando_visdoc.jsp?esito=ko3&search=1&data_da=" + data_da + "&data_a=" + data_a);
        }

    }

    //upload classico doc
    protected void simpleConvenzioniSA(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Db_Bando dbb = new Db_Bando();
        String bandorif = request.getSession().getAttribute("bandorif").toString();
        String ut = request.getSession().getAttribute("username").toString();
        String tipologiaDoc = request.getParameter("tipodoc");
        DateTime el = new DateTime();
        String dateFile = el.toString("yyyyMMddHHmmssSSS");

        String tipodoc = "";
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        File nomefile = null;
        String msg = "0";
        int dimmax = Utility.parseIntR(dbb.getPath("dim"));
        if (isMultipart) {
            try {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if (item.isFormField()) {
                        if (item.getFieldName().equals("tipodoc")) {
                            tipodoc = item.getString();
                        }
                    } else {
                        String fileName = item.getName();
                        if (fileName != null) {
                            if (fileName.lastIndexOf(".") > 0) {
                                File pathdir = new File(dbb.getPath("pathdoc") + el.toString("yyyyMMdd"));
                                pathdir.mkdirs();
                                String estensione = fileName.substring(fileName.lastIndexOf("."));
                                if (estensione.equalsIgnoreCase(".p7m")) {
                                    String name = bandorif + tipodoc + ut + dateFile + estensione;
                                    nomefile = new File(pathdir + File.separator + name);
                                    try {
                                        item.write(nomefile);
                                        String controllifile = controllaFileOnlyP7m(ut, tipodoc, estensione, dimmax, nomefile);
                                        if (!controllifile.equals("OK")) {
                                            nomefile = null;
                                            msg = controllifile;
                                        }
                                    } catch (Exception ex) {
                                        Constant.log.severe(bando + ": " + ex.getMessage());
                                        msg = "1";
                                        trackingAction(ut, "Error:uploadSimple: " + ex.getMessage());
                                        nomefile = null;
                                    }
                                } else {
                                    msg = "1";
                                    trackingAction(ut, "Error:uploadSimple: Estensione non valida");
                                    nomefile = null;
                                }
                            } else {
                                msg = "1";
                                trackingAction(ut, "Error:uploadSimple: File senza estensione");
                                nomefile = null;
                            }
                        }
                    }
//                    System.out.println(item.isFormField());
                }
            } catch (FileUploadException ex) {
                Constant.log.severe(bando + ": " + ex.getMessage());
                trackingAction(ut, "Error:uploadSimple: " + ex.getMessage());
                msg = "1";
            }
        }
        dbb.closeDB();
        if (ActionB.verificaPresenzaConvenzioni(ut, tipodoc)) {
            response.sendRedirect("bando_index.jsp?esito=kodop");
        } else {
            if (msg.equals("0")) {
                Docuserconvenzioni docConv = new Docuserconvenzioni();
                docConv.setCodbando(bandorif);
                docConv.setUsername(ut);
                docConv.setCodicedoc(tipodoc);
                docConv.setPath(nomefile.getPath());
                if (ActionB.insertDocumentUserConvenzioni(docConv)) {
                    response.sendRedirect("bando_index.jsp?esito=ok");
                } else {
                    response.sendRedirect("bando_index.jsp?esito=ko");
                }
                trackingAction(ut, "Caricamento documento: " + tipodoc);
                System.out.println("B");
            } else {
                response.sendRedirect("bando_index.jsp?esito=kocarest");
            }
        }
    }

    //modifica Faq Fra
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            String action = request.getParameter("action");
            if (action.equals("simpleRUP")) {
                simpleRUP(request, response);
            }
            if (request.getSession().getAttribute("username") == null) {
                redirect(request, response, "home.jsp");
            } else {
                String bandorif = request.getSession().getAttribute("bandorif").toString();
                String username = request.getSession().getAttribute("username").toString();
                if (!ActionB.domandaAnnullata(bandorif, username)) {
                    if (action.equals("simple")) {
                        simple(request, response);
                    }
                    if (action.equals("simpleConvenzioni")) {
                        simpleConvenzioni(request, response);
                    }
                    if (action.equals("simpleConvenzioniSA")) {
                        simpleConvenzioniSA(request, response);
                    }
                } else {
                    redirect(request, response, "LoginOperations?type=out");
                }
            }
        } catch (ServletException | IOException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
            trackingAction("service", "Error:processRequestUpload: " + ex.getMessage());
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

}
