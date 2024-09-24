/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.action;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfStamper;
import static rc.so.action.Constant.bando;
import rc.so.db.Db_Bando;
import rc.so.entity.AllegatoA;
import rc.so.entity.AllegatoB;
import rc.so.entity.AllegatoB1_field;
import rc.so.entity.Registrazione;
import rc.so.entity.UserValoriReg;
import static rc.so.util.Utility.checkPDF;
import com.sun.java.swing.plaf.windows.WindowsBorders;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.w3c.dom.Entity;

/**
 *
 * @author srotella
 */
public class Pdf_new {

    private static String nomecognomeUser(ArrayList<UserValoriReg> listavalori) {
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

    private static boolean printBarcode(File pdf, File pdfout, String txt) {
        try {
            InputStream io = new FileInputStream(pdf);
            com.itextpdf.text.pdf.PdfReader reader = new com.itextpdf.text.pdf.PdfReader(io);
            OutputStream os = new FileOutputStream(pdfout);
            PdfStamper stamper = new PdfStamper(reader, os);
            int page = reader.getNumberOfPages();
            for (int i = 1; i <= page; i++) {
                PdfContentByte cb = stamper.getOverContent(i);
                BarcodeQRCode qrcode = new BarcodeQRCode(txt.trim(), 1, 1, null);
                Image qrcodeImage = qrcode.getImage();
                qrcodeImage.setAbsolutePosition(reader.getPageSize(i).getWidth() - 50, reader.getPageSize(i).getHeight() - 50);
                qrcodeImage.scalePercent(125);
                cb.addImage(qrcodeImage);
            }
            stamper.close();
            reader.close();
            io.close();
            stamper.close();
            os.close();
            return true;
        } catch (FileNotFoundException ex) {
            ActionB.trackingAction("service", "Error:Pdf.printBarcode: " + ex.getMessage());
            Constant.log.severe(bando + ": " + ex.getMessage());
        } catch (IOException | DocumentException ex) {
            ActionB.trackingAction("service", "Error:Pdf.printBarcode: " + ex.getMessage());
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public static File allegatoA(String pathout, File pdfIn, String bandorif, String username) {
        try {
            Db_Bando dbb = new Db_Bando();
            ArrayList<Registrazione> listaCampiReg = dbb.listaCampiReg(bandorif, "registrazione");
            ArrayList<UserValoriReg> listavalori = dbb.listaValoriUserbando(bandorif, username);
            ArrayList<String[]> elencodati = new ArrayList<>();
            dbb.closeDB();
            File pdfOut = new File(pathout + username + new DateTime().toString("ddMMyyyyHHmmSSS") + ".A.pdf");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(pdfIn.getPath()), new PdfWriter(baos));
            Document doc = new Document(pdfDoc);
            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, false);
            Map<String, PdfFormField> fields = form.getFormFields();
            String luogo = "";
            for (int i = 0; i < listaCampiReg.size(); i++) {
                for (int j = 0; j < listavalori.size(); j++) {
                    if (listaCampiReg.get(i).getCampo().equals(listavalori.get(j).getCampo())) {
                        String valore = listavalori.get(j).getValore().trim();
                        if (valore.equals("")) {
                            valore = " ";
                        }
                        if (listavalori.get(j).getCampo().equals("residente")) {
                            luogo = valore;
                        }
                        if (listavalori.get(j).getCampo().equals("comuni")) {
                            valore = ActionB.comune(bandorif, username)[1];
                        }
                        String txt = listaCampiReg.get(i).getCampo().trim();
                        String temp[] = {txt, valore.toUpperCase()};
                        elencodati.add(temp);
                        break;
                    }
                }
            }

            for (int i = 0; i < elencodati.size(); i++) {
                String[] t = elencodati.get(i);
                String nome = t[0];
                String valore = t[1];
                if (fields.containsKey(nome)) {
                    fields.get(nome).setValue(valore);
                    form.partialFormFlattening(nome);
                }
            }

            String nomecognomeUser = nomecognomeUser(listavalori);
            fields.get("cognomenome").setValue(nomecognomeUser.toUpperCase());
            form.partialFormFlattening("cognomenome");
            fields.get("luogodata").setValue(luogo.toUpperCase() + " " + new DateTime().toString("dd/MM/yyyy"));
            form.partialFormFlattening("luogodata");
            form.flattenFields();
            doc.close();
            pdfDoc.close();
            FileUtils.writeByteArrayToFile(pdfOut, baos.toByteArray());
            if (checkPDF(pdfOut)) {
                File pdfqr = new File(pdfOut.getPath() + ".qr.pdf");
                boolean x = printBarcode(pdfOut, pdfqr, bandorif);

                if (x) {
                    return pdfqr;
                }
            }
        } catch (IOException ex) {
            ActionB.trackingAction("service", "Error:Pdf_new.allegatoA: " + ex.getMessage());
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return null;
    }

    public static File domanda(String pathout, File pdfIn, String bandorif, String username) {
        try {
            Db_Bando dbb = new Db_Bando();
            ArrayList<Registrazione> listaCampiReg = dbb.listaCampiReg(bandorif, "registrazione");
            ArrayList<UserValoriReg> listavalori = dbb.listaValoriUserbando(bandorif, username);
            ArrayList<String[]> elencodati = new ArrayList<>();
            dbb.closeDB();
            File pdfOut = new File(pathout + username + new DateTime().toString("ddMMyyyyHHmmSSS") + ".A.pdf");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (PdfDocument pdfDoc = new PdfDocument(new PdfReader(pdfIn.getPath()), new PdfWriter(baos))) {
                Document doc = new Document(pdfDoc);
                PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, false);
                Map<String, PdfFormField> fields = form.getFormFields();
                String luogo = "";
                String sesso = "";
                for (int i = 0; i < listaCampiReg.size(); i++) {
                    for (int j = 0; j < listavalori.size(); j++) {
//                    System.out.println(listavalori.get(j).getCampo());
                        if (listaCampiReg.get(i).getCampo().equals(listavalori.get(j).getCampo())) {
                            String valore = listavalori.get(j).getValore().trim();
                            if (valore.equals("")) {
                                valore = " ";
                            }
                            if (listavalori.get(j).getCampo().equals("residente")) {
                                luogo = valore;
                            }
                            if (listavalori.get(j).getCampo().equals("sesso")) {
                                sesso = valore;
                            }
                            if (listavalori.get(j).getCampo().equals("sedecomune")) {
                                luogo = valore;
                            }
                            if (listavalori.get(j).getCampo().equals("comuni")) {
                                valore = ActionB.comune(bandorif, username)[1];
                            }

                            String txt = listaCampiReg.get(i).getCampo().trim();
                            String temp[] = {txt, valore.toUpperCase()};
                            elencodati.add(temp);
                            break;
                        }
                    }
                }
                for (int i = 0; i < elencodati.size(); i++) {
                    String[] t = elencodati.get(i);
                    String nome = t[0];
                    String valore = t[1];
                    if (fields.containsKey(nome)) {
                        fields.get(nome).setValue(valore);
                        form.partialFormFlattening(nome);
                    }
//                if(nome.equals("societa")){
//                    form.getField("societa2").setValue(valore);
//                }
//                System.out.println(valore);
                }
                form.getField("dataoggi").setValue(luogo + " " + new DateTime().toString("dd/MM/yyyy"));
                form.partialFormFlattening("dataoggi");
                form.flattenFields();
                doc.close();
            }
            FileUtils.writeByteArrayToFile(pdfOut, baos.toByteArray());
            if (checkPDF(pdfOut)) {
                File pdfqr = new File(pdfOut.getPath() + ".qr.pdf");
                boolean x = printBarcode(pdfOut, pdfqr, bandorif);

                if (x) {
                    return pdfqr;
                }
            }
        } catch (IOException ex) {
            ActionB.trackingAction("service", "Error:Pdf_new.allegatoA: " + ex.getMessage());
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return null;
    }

    public static File allegatoC(String pathout, File pdfIn, String bandorif, String username) {
        try {
            Db_Bando dbb = new Db_Bando();
            ArrayList<Registrazione> listaCampiReg = dbb.listaCampiReg(bandorif, "registrazione");
            ArrayList<UserValoriReg> listavalori = dbb.listaValoriUserbando(bandorif, username);
            ArrayList<String[]> elencodati = new ArrayList<>();
            dbb.closeDB();
            File pdfOut = new File(pathout + username + new DateTime().toString("ddMMyyyyHHmmSSS") + ".C.pdf");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(pdfIn.getPath()), new PdfWriter(baos));
            Document doc = new Document(pdfDoc);
            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, false);
            Map<String, PdfFormField> fields = form.getFormFields();
            String luogo = "";
            for (int i = 0; i < listaCampiReg.size(); i++) {
                for (int j = 0; j < listavalori.size(); j++) {
                    if (listaCampiReg.get(i).getCampo().equals(listavalori.get(j).getCampo())) {
                        String valore = listavalori.get(j).getValore().trim();
                        if (valore.equals("")) {
                            valore = " ";
                        }
                        if (listavalori.get(j).getCampo().equals("residente")) {
                            luogo = valore;
                        }
                        if (listavalori.get(j).getCampo().equals("comuni")) {
                            valore = ActionB.comune(bandorif, username)[1];
                        }
                        String txt = listaCampiReg.get(i).getCampo().trim();
                        String temp[] = {txt, valore};
                        elencodati.add(temp);
                        break;
                    }
                }
            }

            for (int i = 0; i < elencodati.size(); i++) {
                String[] t = elencodati.get(i);
                String nome = t[0];
                String valore = t[1];
                if (fields.containsKey(nome)) {
                    fields.get(nome).setValue(valore);
                    form.partialFormFlattening(nome);
                }
            }

            String nomecognomeUser = nomecognomeUser(listavalori);
            fields.get("cognomenome").setValue(nomecognomeUser);
            form.partialFormFlattening("cognomenome");
            fields.get("luogodata").setValue(luogo + " " + new DateTime().toString("dd/MM/yyyy"));
            form.partialFormFlattening("luogodata");
//
//            String tipoproposta = ActionB.tipoproposta(bandorif, username);
//
//            if (tipoproposta.equals("1")) {
//                fields.get("prog11").setValue("On");
//                form.partialFormFlattening("prog11");
//                form.partialFormFlattening("prog12");
//
//            } else if (tipoproposta.equals("2")) {
//                fields.get("prog12").setValue("On");
//                form.partialFormFlattening("prog11");
//                form.partialFormFlattening("prog12");
//            } else {
//                form.partialFormFlattening("prog11");
//                form.partialFormFlattening("prog12");
//            }
            form.getField("dataoggi").setValue(new DateTime().toString("dd/MM/yyyy"));
            form.flattenFields();
            doc.close();
            pdfDoc.close();
            FileUtils.writeByteArrayToFile(pdfOut, baos.toByteArray());
            if (checkPDF(pdfOut)) {
                File pdfqr = new File(pdfOut.getPath() + ".qr.pdf");
                boolean x = printBarcode(pdfOut, pdfqr, bandorif);

                if (x) {
                    return pdfqr;
                }
//                if (printBarcode(pdfOut, pdfqr, bandorif)) {
//                    Db_Bando dbb1 = new Db_Bando();
//                    String prot = dbb1.generaProt(username);
//                    dbb1.closeDB();
//                    File pdfOut_f = new File(pdfOut.getPath() + ".merg_fin.pdf");
//                    String laterale = prot + " - " + ActionB.getValoreCampo(listavalori, "cf") + " - "
//                            + ActionB.getValoreCampo(listavalori, "nome") + " " + ActionB.getValoreCampo(listavalori, "cognome")
//                            + " - " + ActionB.getValoreCampo(listavalori, "data");
//                    boolean x = printNotaPdf(pdfqr, pdfOut_f, laterale.toUpperCase());
//                    if (x) {
//                        return pdfOut_f;
//                    }
//                }
            }
        } catch (IOException ex) {
            ActionB.trackingAction("service", "Error:Pdf_new.allegatoC: " + ex.getMessage());
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return null;
    }

    public static boolean mergePDFs(ArrayList<File> listpdf, File pdfout) {
        if (listpdf.size() > 0) {
            try {
                com.itextpdf.text.Document document = new com.itextpdf.text.Document();
                OutputStream out = new FileOutputStream(pdfout);
                List<InputStream> pdfs = new ArrayList<>();
                for (int i = 0; i < listpdf.size(); i++) {
                    pdfs.add(new FileInputStream(listpdf.get(i)));
                }
                List<com.itextpdf.text.pdf.PdfReader> readers = new ArrayList<>();
                Iterator<InputStream> iteratorPDFs = pdfs.iterator();
                while (iteratorPDFs.hasNext()) {
                    InputStream pdf = iteratorPDFs.next();
                    com.itextpdf.text.pdf.PdfReader pdfReader = new com.itextpdf.text.pdf.PdfReader(pdf);
                    readers.add(pdfReader);
                }
                com.itextpdf.text.pdf.PdfWriter writer = com.itextpdf.text.pdf.PdfWriter.getInstance(document, out);
                document.open();
                PdfContentByte cb = writer.getDirectContent(); // Holds the PDF
                PdfImportedPage page;
                int pageOfCurrentReaderPDF = 0;
                Iterator<com.itextpdf.text.pdf.PdfReader> iteratorPDFReader = readers.iterator();
                while (iteratorPDFReader.hasNext()) {
                    com.itextpdf.text.pdf.PdfReader pdfReader = iteratorPDFReader.next();
                    while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
                        document.newPage();
                        pageOfCurrentReaderPDF++;
                        page = writer.getImportedPage(pdfReader,
                                pageOfCurrentReaderPDF);
                        cb.addTemplate(page, 0, 0);
                    }
                    pageOfCurrentReaderPDF = 0;
                }
                out.flush();
                document.close();
                out.close();
                return checkPDF(pdfout);
            } catch (DocumentException | IOException e) {
                Constant.log.severe(bando + ": " + e.getMessage());
            }
        }
        return false;
    }

    public static String compileAllegatoA(String username, String codbando, AllegatoA a) {
        Db_Bando dbb = new Db_Bando();
        ArrayList<UserValoriReg> listavalori = dbb.listaValoriUserbando(codbando, username);
        dbb.closeDB();
        // PRODUZIONE
        String template = "/mnt/mcprofessioni/bandoba0h8/Allegato_A_Domanda_partecipazione_CALABRIA - 10_09_2018_V1.0_SR_FIELD.pdf";
        // SVILUPPO
//        String template = "C:\\bandoba0h8\\Allegato_A_Domanda_partecipazione_CALABRIA - 10_09_2018_V1.0_SR_FIELD.pdf";

        String generatedString = RandomStringUtils.random(30, true, true);
        // PRODUZIONE
        String out = "/mnt/mcprofessioni/bandoba0h8/temp/" + username + generatedString + ".pdf";
        // SVILUPPO
//        String out = "C:\\mnt\\bando\\bandoba0h8\\temp\\" + username + generatedString + ".pdf";
        try {
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(template), new PdfWriter(out));
            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
            String var_nome = "";
            String nome = listavalori.stream().filter(va  -> va.getCampo().equals("nome")).findAny().map(va  -> va.getValore()).get();
            String cognome = listavalori.stream().filter(va  -> va.getCampo().equals("cognome")).findAny().map(va  -> va.getValore()).get();
            String nato = listavalori.stream().filter(va  -> va.getCampo().equals("nato")).findAny().map(va  -> va.getValore()).get();
            String data = listavalori.stream().filter(va  -> va.getCampo().equals("data")).findAny().map(va  -> va.getValore()).get();
            String caricasoc = listavalori.stream().filter(va  -> va.getCampo().equals("caricasoc")).findAny().map(va  -> va.getValore()).get();
            String societa = listavalori.stream().filter(va  -> va.getCampo().equals("societa")).findAny().map(va  -> va.getValore()).get();
            String sedecomune = listavalori.stream().filter(va  -> va.getCampo().equals("sedecomune")).findAny().map(va  -> va.getValore()).get();
            String sedeprov = listavalori.stream().filter(va  -> va.getCampo().equals("sedeprov")).findAny().map(va  -> va.getValore()).get();
            String sedeindirizzo = listavalori.stream().filter(va  -> va.getCampo().equals("sedeindirizzo")).findAny().map(va  -> va.getValore()).get();
            String sedecap = listavalori.stream().filter(va  -> va.getCampo().equals("sedecap")).findAny().map(va  -> va.getValore()).get();
            String comunecciaa = listavalori.stream().filter(va  -> va.getCampo().equals("comunecciaa")).findAny().map(va  -> va.getValore()).get();
            String proccciaa = listavalori.stream().filter(va  -> va.getCampo().equals("proccciaa")).findAny().map(va  -> va.getValore()).get();
            String pec = listavalori.stream().filter(va  -> va.getCampo().equals("pec")).findAny().map(va  -> va.getValore()).get();
            String matricolainps = listavalori.stream().filter(va  -> va.getCampo().equals("matricolainps")).findAny().map(va  -> va.getValore()).get();
            String piva = listavalori.stream().filter(va  -> va.getCampo().equals("piva")).findAny().map(va  -> va.getValore()).get();
            String rea = listavalori.stream().filter(va  -> va.getCampo().equals("rea")).findAny().map(va  -> va.getValore()).get();
            String cf = listavalori.stream().filter(va  -> va.getCampo().equals("cf")).findAny().map(va  -> va.getValore()).get();

            for (int i = 0; i < listavalori.size(); i++) {
                if (listavalori.get(i).getCampo().equals("nome")) {
                    var_nome = var_nome + listavalori.get(i).getValore();
                }
                form.getField("nome_cognome").setValue(cognome + " " + nome);
                form.getField("comune_nascita").setValue(nato);
                form.getField("data_nascita").setValue(data);
                form.getField("in_qualita_di").setValue(caricasoc);
                form.getField("azienda").setValue(societa);
                form.getField("sede_legale").setValue(sedecomune);
                form.getField("prov_sede_legale").setValue(sedeprov);
                form.getField("indirizzo_sede_legale").setValue(sedeindirizzo);
                form.getField("cap_sede_legale").setValue(sedecap);
                form.getField("cf").setValue(cf);
                form.getField("comune_cciaa").setValue(comunecciaa);
                form.getField("piva").setValue(piva);
                form.getField("prov_cciaa").setValue(proccciaa);
                form.getField("rea").setValue(rea);
                form.getField("mtr_inps").setValue(matricolainps);
                form.getField("azienda2").setValue(societa);
                form.getField("pec").setValue(pec);
                form.getField("azienda3").setValue(societa);
                if (a.getEnteistituzionepubblica().equals("SI")) {
                    form.getField("ch1").setValue("On");
                }
                if (a.getAssociazione().equals("SI")) {
                    form.getField("ch2").setValue("On");
                }
                if (a.getOrdineprofessionale().equals("SI")) {
                    form.getField("ch3").setValue("On");
                }
                if (a.getSoggettoprivato().equals("SI")) {
                    form.getField("ch4").setValue("On");
                    if (a.getFormazione().equals("SI")) {
                        form.getField("ch5").setValue("On");
                        form.getField("regione").setValue(a.getRegione1());
                        form.getField("iscrizione").setValue(a.getIscrizione1());
                    }
                    if (a.getServizi().equals("SI")) {
                        form.getField("ch6").setValue("On");
                        form.getField("regione2").setValue(a.getRegione2());
                        form.getField("iscrizione2").setValue(a.getIscrizione2());
                    }
                    if (a.getAteco().equals("SI")) {
                        form.getField("ch7").setValue("On");
                    }
                }
                form.getField("num_aule").setValue(a.getNumaule());

                form.getField("citta").setValue(a.getCitta1());
                form.getField("prov").setValue(a.getProvincia1());
                form.getField("indirizzo").setValue(a.getIndirizzo1());
                form.getField("estremi").setValue(a.getEstremi1());
                form.getField("accreditato").setValue(a.getAccreditamento1());
                form.getField("responsabile").setValue(a.getResponsabile1());
                form.getField("amministrativo").setValue(a.getAmministrativo1());
                form.getField("recapiti").setValue(a.getRecapiti1());

                form.getField("citta2").setValue(a.getCitta2());
                form.getField("prov2").setValue(a.getProvincia2());
                form.getField("indirizzo2").setValue(a.getIndirizzo2());
                form.getField("estremi2").setValue(a.getEstremi2());
                form.getField("accreditato2").setValue(a.getAccreditamento2());
                form.getField("responsabile2").setValue(a.getResponsabile2());
                form.getField("amministrativo2").setValue(a.getAmministrativo2());
                form.getField("recapiti2").setValue(a.getRecapiti2());

                form.getField("citta3").setValue(a.getCitta3());
                form.getField("prov3").setValue(a.getProvincia3());
                form.getField("indirizzo3").setValue(a.getIndirizzo3());
                form.getField("estremi3").setValue(a.getEstremi3());
                form.getField("accreditato3").setValue(a.getAccreditamento3());
                form.getField("responsabile3").setValue(a.getResponsabile3());
                form.getField("amministrativo3").setValue(a.getAmministrativo3());
                form.getField("recapiti3").setValue(a.getRecapiti3());

                form.getField("citta4").setValue(a.getCitta4());
                form.getField("prov4").setValue(a.getProvincia4());
                form.getField("indirizzo4").setValue(a.getIndirizzo4());
                form.getField("estremi4").setValue(a.getEstremi4());
                form.getField("accreditato4").setValue(a.getAccreditamento4());
                form.getField("responsabile4").setValue(a.getResponsabile4());
                form.getField("amministrativo4").setValue(a.getAmministrativo4());
                form.getField("recapiti4").setValue(a.getRecapiti4());

                form.getField("citta5").setValue(a.getCitta5());
                form.getField("prov5").setValue(a.getProvincia5());
                form.getField("indirizzo5").setValue(a.getIndirizzo5());
                form.getField("estremi5").setValue(a.getEstremi5());
                form.getField("accreditato5").setValue(a.getAccreditamento5());
                form.getField("responsabile5").setValue(a.getResponsabile5());
                form.getField("amministrativo5").setValue(a.getAmministrativo5());
                form.getField("recapiti5").setValue(a.getRecapiti5());

                form.getField("descAttivita").setValue(a.getAttivita());
                form.getField("destinatari").setValue(a.getDestinatari());
                form.getField("fonte").setValue(a.getFinanziamento());
                form.getField("committente").setValue(a.getCommittente());
                form.getField("periodo").setValue(a.getPeriodo());

                form.getField("descAttivita2").setValue(a.getAttivita2());
                form.getField("destinatari2").setValue(a.getDestinatari2());
                form.getField("fonte2").setValue(a.getFinanziamento2());
                form.getField("committente2").setValue(a.getCommittente2());
                form.getField("periodo2").setValue(a.getPeriodo2());

                form.getField("descAttivita3").setValue(a.getAttivita3());
                form.getField("destinatari3").setValue(a.getDestinatari3());
                form.getField("fonte3").setValue(a.getFinanziamento3());
                form.getField("committente3").setValue(a.getCommittente3());
                form.getField("periodo3").setValue(a.getPeriodo3());

                form.getField("descAttivita4").setValue(a.getAttivita4());
                form.getField("destinatari4").setValue(a.getDestinatari4());
                form.getField("fonte4").setValue(a.getFinanziamento4());
                form.getField("committente4").setValue(a.getCommittente4());
                form.getField("periodo4").setValue(a.getPeriodo4());

                form.getField("descAttivita5").setValue(a.getAttivita5());
                form.getField("destinatari5").setValue(a.getDestinatari5());
                form.getField("fonte5").setValue(a.getFinanziamento5());
                form.getField("committente5").setValue(a.getCommittente5());
                form.getField("periodo5").setValue(a.getPeriodo5());

                if (a.getNoconsorzio().equals("SI")) {
                    form.getField("noconsorzio").setValue("Sì");
                } else {
                    form.getField("consorzio").setValue("Sì");
                    form.getField("nomeconsorzio").setValue(a.getNomeconsorzio());
                }
                form.getField("pec2").setValue(a.getPec());
                form.getField("numdocenti").setValue(a.getNumdocenti());
            }
            form.getField("dataoggi").setValue(sedecomune + " " + new DateTime().toString("dd/MM/yyyy"));
            form.flattenFields();//chiude il documento
            pdfDoc.close();

            return out;
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            //e.close();
        }
        return null;
    }

    public static String compileAllegatoB(String username, String codbando, AllegatoA a) {
        Db_Bando dbb = new Db_Bando();
        ArrayList<UserValoriReg> listavalori = dbb.listaValoriUserbando(codbando, username);
        dbb.closeDB();
        String template = "/mnt/mcprofessioni/bandoba0h8/Allegato_B_Calabria - 10_09_2018_V1.0_SR_BANDO_V2.pdf";
//        String template = "C:\\bandoba0h8\\Allegato_B_Calabria - 10_09_2018_V1.0_SR_BANDO_V2.pdf";
        String generatedString = RandomStringUtils.random(30, true, true);
//        String out = "C:\\mnt\\bando\\bandoba0h8\\temp\\" + username + generatedString + ".pdf";
        String out = "/mnt/mcprofessioni/bandoba0h8/temp/" + username + generatedString + ".pdf";
        try {
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(template), new PdfWriter(out));
            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
            String var_nome = "";
            String nome = listavalori.stream().filter(va  -> va.getCampo().equals("nome")).findAny().map(va  -> va.getValore()).get();
            String cognome = listavalori.stream().filter(va  -> va.getCampo().equals("cognome")).findAny().map(va  -> va.getValore()).get();
            String nato = listavalori.stream().filter(va  -> va.getCampo().equals("nato")).findAny().map(va  -> va.getValore()).get();
            String data = listavalori.stream().filter(va  -> va.getCampo().equals("data")).findAny().map(va  -> va.getValore()).get();
            String caricasoc = listavalori.stream().filter(va  -> va.getCampo().equals("caricasoc")).findAny().map(va  -> va.getValore()).get();
            String societa = listavalori.stream().filter(va  -> va.getCampo().equals("societa")).findAny().map(va  -> va.getValore()).get();
            String sedecomune = listavalori.stream().filter(va  -> va.getCampo().equals("sedecomune")).findAny().map(va  -> va.getValore()).get();
            String sedeprov = listavalori.stream().filter(va  -> va.getCampo().equals("sedeprov")).findAny().map(va  -> va.getValore()).get();
            String sedeindirizzo = listavalori.stream().filter(va  -> va.getCampo().equals("sedeindirizzo")).findAny().map(va  -> va.getValore()).get();
            String sedecap = listavalori.stream().filter(va  -> va.getCampo().equals("sedecap")).findAny().map(va  -> va.getValore()).get();
            String comunecciaa = listavalori.stream().filter(va  -> va.getCampo().equals("comunecciaa")).findAny().map(va  -> va.getValore()).get();
            String proccciaa = listavalori.stream().filter(va  -> va.getCampo().equals("proccciaa")).findAny().map(va  -> va.getValore()).get();
            String pec = listavalori.stream().filter(va  -> va.getCampo().equals("pec")).findAny().map(va  -> va.getValore()).get();
            String matricolainps = listavalori.stream().filter(va  -> va.getCampo().equals("matricolainps")).findAny().map(va  -> va.getValore()).get();
            String piva = listavalori.stream().filter(va  -> va.getCampo().equals("piva")).findAny().map(va  -> va.getValore()).get();
            String rea = listavalori.stream().filter(va  -> va.getCampo().equals("rea")).findAny().map(va  -> va.getValore()).get();
            String cf = listavalori.stream().filter(va  -> va.getCampo().equals("cf")).findAny().map(va  -> va.getValore()).get();
            //String comunecarica = listavalori.stream().filter(va -> va.getCampo().equals("domiciliocarica")).findAny().map(va -> va.getValore()).get();

            for (int i = 0; i < listavalori.size(); i++) {
                if (listavalori.get(i).getCampo().equals("nome")) {
                    var_nome = var_nome + listavalori.get(i).getValore();
                }
                form.getField("nome_cognome").setValue(cognome + " " + nome);
                form.getField("nato_a").setValue(nato);
                form.getField("data_nascita").setValue(data);
                form.getField("carica_soc").setValue(caricasoc);
                form.getField("azienda").setValue(societa);
                form.getField("comune_sede_legale").setValue(sedecomune);
                form.getField("prov_sede_legale").setValue(sedeprov);
                form.getField("indirizzo_sede_legale").setValue(sedeindirizzo);
                form.getField("cap_sede_legale").setValue(sedecap);
                form.getField("cf").setValue(cf);
                form.getField("comune_cciaa").setValue(comunecciaa);
                form.getField("piva").setValue(piva);
                form.getField("prov_cciaa").setValue(proccciaa);
                form.getField("rea").setValue(rea);
                form.getField("mtr_inps").setValue(matricolainps);
                form.getField("azienda2").setValue(societa);
                AllegatoB ab = ActionB.getAllegatoB(username);

                form.getField("doc_nome_cognome").setValue(ab.getNome() + " " + ab.getCognome());
                if (ab.getAppartenenza().equals("A")) {
                    form.getField("A1").setValue("X");
                    form.getField("B1").setValue("");
                } else {
                    form.getField("A1").setValue("");
                    form.getField("B1").setValue("X");
                }
                if (ab.getInquadramento().equals("piva")) {
                    form.getField("piva1").setValue("X");
                    form.getField("dipendente1").setValue("");
                } else {
                    form.getField("piva1").setValue("");
                    form.getField("dipendente1").setValue("X");
                }
                if (!ab.getNome2().equals("") && !ab.getCognome2().equals("")) {
                    form.getField("doc_nome_cognome2").setValue(ab.getNome2() + " " + ab.getCognome2());
                    if (ab.getAppartenenza2().equals("A")) {
                        form.getField("A2").setValue("X");
                        form.getField("B2").setValue("");
                    } else {
                        form.getField("A2").setValue("");
                        form.getField("B2").setValue("X");
                    }
                    if (ab.getInquadramento2().equals("piva")) {
                        form.getField("piva2").setValue("X");
                        form.getField("dipendente2").setValue("");
                    } else {
                        form.getField("piva2").setValue("");
                        form.getField("dipendente2").setValue("X");
                    }
                }
                if (!ab.getNome3().equals("") && !ab.getCognome3().equals("")) {
                    form.getField("doc_nome_cognome3").setValue(ab.getNome3() + " " + ab.getCognome3());

                    if (ab.getAppartenenza3().equals("A")) {
                        form.getField("A3").setValue("X");
                        form.getField("B3").setValue("");
                    } else {
                        form.getField("A3").setValue("");
                        form.getField("B3").setValue("X");
                    }

                    if (ab.getInquadramento3().equals("piva")) {
                        form.getField("piva3").setValue("X");
                        form.getField("dipendente3").setValue("");
                    } else {
                        form.getField("piva3").setValue("");
                        form.getField("dipendente3").setValue("X");
                    }
                }
                if (!ab.getNome4().equals("") && !ab.getCognome4().equals("")) {
                    form.getField("doc_nome_cognome4").setValue(ab.getNome4() + " " + ab.getCognome4());

                    if (ab.getAppartenenza4().equals("A")) {
                        form.getField("A4").setValue("X");
                        form.getField("B4").setValue("");
                    } else {
                        form.getField("A4").setValue("");
                        form.getField("B4").setValue("X");
                    }

                    if (ab.getInquadramento4().equals("piva")) {
                        form.getField("piva4").setValue("X");
                        form.getField("dipendente4").setValue("");
                    } else {
                        form.getField("piva4").setValue("");
                        form.getField("dipendente4").setValue("X");
                    }
                }
                if (!ab.getNome5().equals("") && !ab.getCognome5().equals("")) {
                    form.getField("doc_nome_cognome5").setValue(ab.getNome5() + " " + ab.getCognome5());

                    if (ab.getAppartenenza5().equals("A")) {
                        form.getField("A5").setValue("X");
                        form.getField("B5").setValue("");
                    } else {
                        form.getField("A5").setValue("");
                        form.getField("B5").setValue("X");
                    }

                    if (ab.getInquadramento5().equals("piva")) {
                        form.getField("piva5").setValue("X");
                        form.getField("dipendente5").setValue("");
                    } else {
                        form.getField("piva5").setValue("");
                        form.getField("dipendente5").setValue("X");
                    }
                }
                form.getField("dataoggi").setValue(sedecomune + " " + new DateTime().toString("dd/MM/yyyy"));
                form.flattenFields();//chiude il documento
                pdfDoc.close();
                return out;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String compileAllegatoB1(String username, ArrayList<AllegatoB1_field> alb1, String nomeDocente) {
        Db_Bando dbb = new Db_Bando();
        dbb.closeDB();
        // PRODUZIONE
        String template = "/mnt/mcprofessioni/bandoba0h8/Allegato_B1_V2.0_SR.pdf";
        // SVILUPPO
//        String template = "C:\\bandoba0h8\\Allegato_B1_V2.0_SR.pdf";
        String generatedString = RandomStringUtils.random(30, true, true);
        // PRODUZIONE
        String out = "/mnt/mcprofessioni/bandoba0h8/temp/" + username + generatedString + ".pdf";
        // SVILUPPO
//        String out = "C:\\mnt\\bando\\bandoba0h8\\temp\\" + username + generatedString + ".pdf";
        try {
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(template), new PdfWriter(out));
            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
//            System.out.println(nomeDocente);
            form.getField("doc_nome_cognome").setValue(nomeDocente);
            for (int i = 0; i < alb1.size(); i++) {
                if (i == 0) {
                    form.getField("periodo").setValue(alb1.get(i).getPeriodo());
                    form.getField("durata").setValue(alb1.get(i).getDurata());
                    form.getField("incarico").setValue(alb1.get(i).getIncarico());
                    form.getField("fonte").setValue(alb1.get(i).getFinanziamento());
                    form.getField("attivita").setValue(alb1.get(i).getAttivita());
                    form.getField("sa").setValue(alb1.get(i).getCommittente());
                }
                if (i == 1) {
                    form.getField("periodo2").setValue(alb1.get(i).getPeriodo());
                    form.getField("durata2").setValue(alb1.get(i).getDurata());
                    form.getField("incarico2").setValue(alb1.get(i).getIncarico());
                    form.getField("fonte2").setValue(alb1.get(i).getFinanziamento());
                    form.getField("attivita2").setValue(alb1.get(i).getAttivita());
                    form.getField("sa2").setValue(alb1.get(i).getCommittente());
                }
                if (i == 2) {
                    form.getField("periodo3").setValue(alb1.get(i).getPeriodo());
                    form.getField("durata3").setValue(alb1.get(i).getDurata());
                    form.getField("incarico3").setValue(alb1.get(i).getIncarico());
                    form.getField("fonte3").setValue(alb1.get(i).getFinanziamento());
                    form.getField("attivita3").setValue(alb1.get(i).getAttivita());
                    form.getField("sa3").setValue(alb1.get(i).getCommittente());
                }
                if (i == 3) {
                    form.getField("periodo4").setValue(alb1.get(i).getPeriodo());
                    form.getField("durata4").setValue(alb1.get(i).getDurata());
                    form.getField("incarico4").setValue(alb1.get(i).getIncarico());
                    form.getField("fonte4").setValue(alb1.get(i).getFinanziamento());
                    form.getField("attivita4").setValue(alb1.get(i).getAttivita());
                    form.getField("sa4").setValue(alb1.get(i).getCommittente());
                }
                if (i == 4) {
                    form.getField("periodo5").setValue(alb1.get(i).getPeriodo());
                    form.getField("durata5").setValue(alb1.get(i).getDurata());
                    form.getField("incarico5").setValue(alb1.get(i).getIncarico());
                    form.getField("fonte5").setValue(alb1.get(i).getFinanziamento());
                    form.getField("attivita5").setValue(alb1.get(i).getAttivita());
                    form.getField("sa5").setValue(alb1.get(i).getCommittente());
                }

            }
            form.getField("dataoggi").setValue(new DateTime().toString("dd/MM/yyyy"));
            form.flattenFields();//chiude il documento
            System.out.println(out);
            pdfDoc.close();
            return out;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        AllegatoA a = ActionB.getAllegatoA("Mbiond4218");
//        compileAllegatoA("Mbiond4218", "BA0F6", a);
        compileAllegatoB("Mbiond4218", "BA0F6", a);
    }

}
