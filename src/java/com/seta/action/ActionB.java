/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.action;

import com.seta.db.Db_Bando;
import com.seta.entity.AllegatoA;
import com.seta.entity.AllegatoB;
import com.seta.entity.AllegatoB1;
import com.seta.entity.AllegatoB1_field;
import com.seta.entity.Docbandi;
import com.seta.entity.Docenti;
import com.seta.entity.DocumentiDocente;
import com.seta.entity.Docuserbandi;
import com.seta.entity.Docuserconvenzioni;
import com.seta.entity.Domandecomplete;
import com.seta.entity.Faq;
import com.seta.entity.Registrazione;
import com.seta.entity.Reportistica;
import com.seta.entity.UserValoriReg;
import com.seta.util.Utility;
import java.util.ArrayList;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author raffaele
 */
public class ActionB {

    public static void trackingAction(String username, String azione) {
        Db_Bando dbb = new Db_Bando();
        dbb.insertTracking(username, azione);
        dbb.closeDB();
    }

    public static boolean verificaBando(String cod) {
        Db_Bando dbb = new Db_Bando();
        boolean bo = dbb.bandoAttivoRaf(cod);
        dbb.closeDB();
        return bo;
    }

    public static Domandecomplete isPresenteDomandaCompleta(String bando, String username) {
        Db_Bando dbb = new Db_Bando();
        Domandecomplete dc = dbb.isPresenteDomandaCompleta(bando, username);
        dbb.closeDB();
        return dc;
    }

    public static Domandecomplete isDomandaCompletaConsolidata(String bando, String username) {
        Db_Bando dbb = new Db_Bando();
        Domandecomplete dc = dbb.isDomandaCompletaConsolidata(bando, username);
        dbb.closeDB();
        return dc;
    }

    public static ArrayList<Registrazione> listaCampiReg(String bando, String az) {
        Db_Bando dbb = new Db_Bando();
        ArrayList<Registrazione> bo = new ArrayList<>();
        if (az == null) {
            bo = dbb.listaCampiReg(bando, "registrazione");
        }
        dbb.closeDB();
        return bo;
    }

    public static ArrayList<Faq> listaFaqBando(String cod) {
        Db_Bando dbb = new Db_Bando();
        ArrayList<Faq> bo = dbb.listaFaqBando(cod);
        dbb.closeDB();
        return bo;
    }

    public static ArrayList<String[]> province() {
        Db_Bando db = new Db_Bando();
        ArrayList<String[]> desc = db.getAllProvince();
        db.closeDB();
        return desc;
    }

    public static ArrayList<String[]> comuni() {
        Db_Bando db = new Db_Bando();
        ArrayList<String[]> desc = db.comuni();
        db.closeDB();
        return desc;
    }

    public static ArrayList<String[]> tipoDoc() {
        Db_Bando db = new Db_Bando();
        ArrayList<String[]> desc = db.getTipoDoc();
        db.closeDB();
        return desc;
    }

    public static String getDescrizioneBando(String cod) {
        Db_Bando dbb = new Db_Bando();
        String bo = dbb.getDescrizioneBando(cod);
        dbb.closeDB();
        return StringUtils.capitalize(bo);
    }

    public static String getDescrizioneAltroAllUser(String cod) {
        Db_Bando dbb = new Db_Bando();
        String bo = dbb.getDescrizioneAltroAll(cod);
        dbb.closeDB();
        return bo;
    }

    public static String descr_bandoaperto(String cod) {
        Db_Bando dbb = new Db_Bando();
        String bo = dbb.descr_bandoaperto(cod);
        dbb.closeDB();
        return bo;
    }

    public static String descr_bandochiuso(String cod) {
        Db_Bando dbb = new Db_Bando();
        String bo = dbb.descr_bandochiuso(cod);
        dbb.closeDB();
        return bo;
    }

    public static String getScadenzaBando(String cod) {
        Db_Bando dbb = new Db_Bando();
        String bo = dbb.getScadenzaBando(cod);
        dbb.closeDB();

        String pat1 = "yyyy-MM-dd";
        String pat2 = "dd/MM/yyyy";
        boolean ts = false;
        if (bo.length() > 10) {
            pat1 = "yyyy-MM-dd HH:mm:ss";
            pat2 = "dd/MM/yyyy  HH:mm:ss";
            ts = true;
        }

        return Utility.formatStringtoStringDate(bo, pat1, pat2, ts);

    }

    public static String generateUsername(String nome, String cognome) {

        nome = StringUtils.stripAccents(nome).replaceAll("[^a-zA-Z0-9]", "");
        cognome = StringUtils.stripAccents(cognome).replaceAll("[^a-zA-Z0-9]", "");

        String result;
        if (nome.length() > 1) {
            result = StringUtils.capitalize(nome.substring(0, 1)); // First char
        } else {
            result = RandomStringUtils.randomAlphabetic(1).toUpperCase();
        }
        if (cognome.length() > 5) {
            result += cognome.substring(0, 5);
        } else {
            result += cognome;
        }
        result += RandomStringUtils.randomNumeric(4);
        return result;
    }

    public static ArrayList<Docbandi> listaDocRichiesti(String cod) {
        Db_Bando dbb = new Db_Bando();
        ArrayList<Docbandi> liout = dbb.listaDocRichiestiBando(cod);
        dbb.closeDB();
        return liout;
    }

    public static ArrayList<Docbandi> listaDocRichiesti(String bandorif, String username) {
        Db_Bando db = new Db_Bando();
        ArrayList<Docbandi> liout = new ArrayList<>();
        ArrayList<Docbandi> listart = db.listaDocRichiestiBando(bandorif);
        db.closeDB();
        for (int i = 0; i < listart.size(); i++) {
            Docbandi dbb = listart.get(i);
            if (ActionB.getSino(username).equals("SI")) {
                if (listart.get(i).getCodicedoc().equals("DOCR") || listart.get(i).getCodicedoc().equals("DONL")) {
                    liout.add(dbb);
                }
            } else {
                if (listart.get(i).getCodicedoc().equals("DOCR") || listart.get(i).getCodicedoc().equals("DONLA") 
                 || listart.get(i).getCodicedoc().equals("DONLB") || listart.get(i).getCodicedoc().equals("ALB1")) {
                    liout.add(dbb);
                }
            }
        }
        return liout;
    }

    public static ArrayList<Docuserbandi> listaDocuserbando(String cod, String username) {
        Db_Bando dbb = new Db_Bando();
        ArrayList<Docuserbandi> liout = dbb.listaDocUserBando(cod, username);
        dbb.closeDB();
        return liout;
    }

    public static ArrayList<Docuserbandi> listaDocUserBandoAltri(String cod, String username) {
        Db_Bando dbb = new Db_Bando();
        ArrayList<Docuserbandi> liout = dbb.listaDocUserBandoAltri(cod, username);
        dbb.closeDB();
        return liout;
    }

    public static ArrayList<Docuserbandi> listaDocuserbando(String cod, String username, String coddoc) {
        Db_Bando dbb = new Db_Bando();
        ArrayList<Docuserbandi> liout = dbb.listaDocUserBando(cod, username, coddoc);
        dbb.closeDB();
        return liout;
    }

    public static Docuserbandi docUserBando(String cod, String username, String codicedoc) {
        Db_Bando dbb = new Db_Bando();
        Docuserbandi du = dbb.docUserBando(cod, username, codicedoc, "-", "-");
        dbb.closeDB();
        return du;
    }

    public static ArrayList<String[]> listaTipiAll(String bando) {
        Db_Bando dbb = new Db_Bando();
        ArrayList<String[]> li = dbb.getListaAllBando(bando);
        dbb.closeDB();
        return li;
    }

    public static ArrayList<String[]> listaTipiAllRUP(String bando) {
        Db_Bando dbb = new Db_Bando();
        ArrayList<String[]> li = dbb.getListaAllBandoRUP(bando);
        dbb.closeDB();
        return li;
    }

    public static ArrayList<String[]> occupazione() {
        Db_Bando dbb = new Db_Bando();
        ArrayList<String[]> li = dbb.occupazione();
        dbb.closeDB();
        return li;
    }

    public static ArrayList<Reportistica> getListReports(String bandorif) {
        Db_Bando db = new Db_Bando();
        ArrayList<Reportistica> li = db.listareports(bandorif);
        db.closeDB();
        return li;
    }

    public static boolean domandaAnnullata(String bandorif, String username) {
        Db_Bando db = new Db_Bando();
        boolean is = db.isDomandaAnnullata(bandorif, username);
        db.closeDB();
        return is;
    }

    public static String[] comune(String bandorif, String username) {
        Db_Bando db = new Db_Bando();
        ArrayList<UserValoriReg> lista = db.listaValoriUserbando(bandorif, username);
        ArrayList<String[]> comuni = db.comuni();
        db.closeDB();
        for (int i = 0; i < lista.size(); i++) {
            UserValoriReg uvr = lista.get(i);
            if (uvr.getCampo().equals("comuni")) {
                for (int k = 0; k < comuni.size(); k++) {
                    if (comuni.get(k)[0].equals(uvr.getValore())) {
                        return comuni.get(k);
                    }
                }
            }
        }
        return null;
    }

    public static String getValoreCampo(ArrayList<UserValoriReg> lista, String nomecampo) {
        for (int i = 0; i < lista.size(); i++) {
            UserValoriReg uvr = lista.get(i);
            if (uvr.getCampo().equals(nomecampo)) {
                return uvr.getValore();
            }
        }
        return "";
    }

    public static int getDimMaxFiles() {
        Db_Bando db = new Db_Bando();
        int x = Utility.parseIntR(db.getPath("dim"));
        db.closeDB();
        return x;
    }

    public static String getDimMaxFilesEtichetta() {
        Db_Bando db = new Db_Bando();
        String x = (db.getPath("dimetichetta"));
        db.closeDB();
        return x;
    }

    public static boolean esisteusrnamedomande(String username) {
        Db_Bando db = new Db_Bando();
        boolean x = db.esisteusrnamedomande(username);
        db.closeDB();
        return x;
    }

    public static boolean esisteAllegatoA(String username) {
        Db_Bando db = new Db_Bando();
        boolean x = db.esisteAllegatoA(username);
        db.closeDB();
        return x;
    }

    public static boolean esisteAllegatoB(String username) {
        Db_Bando db = new Db_Bando();
        boolean x = db.esisteAllegatoB(username);
        db.closeDB();
        return x;
    }
    
    public static boolean esisteAllegatoB1(String username) {
        Db_Bando db = new Db_Bando();
        boolean x = db.esisteAllegatoB1(username);
        db.closeDB();
        return x;
    }

    public static boolean remdatiAllegatoA(String username) {
        Db_Bando db = new Db_Bando();
        boolean x = db.remdatiAllegatoA(username);
        db.closeDB();
        return x;
    }

    public static boolean remdatiAllegatoB(String username) {
        Db_Bando db = new Db_Bando();
        boolean x = db.remdatiAllegatoB(username);
        db.closeDB();
        return x;
    }

    public static boolean invioattivo(String bando) {
        Db_Bando db = new Db_Bando();
        boolean x = db.invioattivo(bando);
        db.closeDB();
        return x;
    }

    public static String getPath(String id) {
        Db_Bando db = new Db_Bando();
        String path = db.getPath(id);
        db.closeDB();
        return path;
    }

    public static ArrayList<String[]> statieur() {
        Db_Bando db = new Db_Bando();
        ArrayList<String[]> desc = db.getAllStatiEur();
        db.closeDB();
        return desc;
    }

    public static ArrayList<String[]> Sesso() {
        Db_Bando db = new Db_Bando();
        ArrayList<String[]> desc = db.getSesso();
        db.closeDB();
        return desc;
    }

    public static ArrayList<String> SiNo() {
        ArrayList<String> desc = new ArrayList<>();
        desc.add("NO");
        desc.add("SI");
        return desc;
    }

    public static ArrayList<Domandecomplete> getDomandeComplete(String a, String b) {
        Db_Bando db = new Db_Bando();
        ArrayList<Domandecomplete> desc = db.listaconsegnatestato(a,b);
        db.closeDB();
        return desc;
    }

    public static String getSino(String username) {
        Db_Bando db = new Db_Bando();
        String var = db.getSiNo(username);
        db.closeDB();
        return var;
    }

    public static boolean insAllegatoA(String username, String enteistituzionepubblica, String associazione, String ordineprofessionale, String soggettoprivato, String formazione,
            String regione1, String iscrizione1, String servizi, String regione2, String iscrizione2, String ateco, String numaule, String citta1,
            String provincia1, String indirizzo1, String estremi1, String accreditamento1, String responsabile1, String amministrativo1, String recapiti1,
            String citta2, String provincia2, String indirizzo2, String estremi2, String accreditamento2, String responsabile2, String amministrativo2,
            String recapiti2, String citta3, String provincia3, String indirizzo3, String estremi3, String accreditamento3, String responsabile3,
            String amministrativo3, String recapiti3, String citta4, String provincia4, String indirizzo4, String estremi4, String accreditamento4,
            String responsabile4, String amministrativo4, String recapiti4, String citta5, String provincia5, String indirizzo5, String estremi5,
            String accreditamento5, String responsabile5, String amministrativo5, String recapiti5, String attivita, String destinatari, String finanziamento,
            String committente, String periodo, String attivita2, String destinatari2, String finanziamento2, String committente2, String periodo2, String attivita3,
            String destinatari3, String finanziamento3, String committente3, String periodo3, String attivita4, String destinatari4, String finanziamento4,
            String committente4, String periodo4, String attivita5, String destinatari5, String finanziamento5, String committente5, String periodo5,
            String noconsorzio, String consorzio, String nomeconsorzio, String pec, String numdocenti) {
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.insAllegatoA(username, enteistituzionepubblica, associazione, ordineprofessionale, soggettoprivato, formazione, regione1, iscrizione1, servizi,
                regione2, iscrizione2, ateco, numaule, citta1, provincia1, indirizzo1, estremi1, accreditamento1, responsabile1, amministrativo1, recapiti1, citta2, provincia2,
                indirizzo2, estremi2, accreditamento2, responsabile2, amministrativo2, recapiti2, citta3, provincia3, indirizzo3, estremi3, accreditamento3, responsabile3,
                amministrativo3, recapiti3, citta4, provincia4, indirizzo4, estremi4, accreditamento4, responsabile4, amministrativo4, recapiti4, citta5, provincia5, indirizzo5,
                estremi5, accreditamento5, responsabile5, amministrativo5, recapiti5, attivita, destinatari, finanziamento, committente, periodo, attivita2, destinatari2,
                finanziamento2, committente2, periodo2, attivita3, destinatari3, finanziamento3, committente3, periodo3, attivita4, destinatari4, finanziamento4, committente4,
                periodo4, attivita5, destinatari5, finanziamento5, committente5, periodo5, noconsorzio, consorzio, nomeconsorzio, pec, numdocenti);
        db.closeDB();
        return ctrl;
    }

    public static boolean insAllegatoB(String username, String nome, String cognome, String appartenenza, String inquadramento,
            String nome2, String cognome2, String appartenenza2, String inquadramento2, String nome3, String cognome3,
            String appartenenza3, String inquadramento3, String nome4, String cognome4, String appartenenza4,
            String inquadramento4, String nome5, String cognome5, String appartenenza5, String inquadramento5) {
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.insAllegatoB(username, nome, cognome, appartenenza, inquadramento, nome2, cognome2, appartenenza2,
                inquadramento2, nome3, cognome3, appartenenza3, inquadramento3, nome4, cognome4, appartenenza4, inquadramento4,
                nome5, cognome5, appartenenza5, inquadramento5);
        db.closeDB();
        return ctrl;
    }

    public static boolean removeAllDocUserBando(String codbando, String username) {
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.removeAllDocUserBando(codbando, username);
        db.closeDB();
        return ctrl;
    }

    public static boolean removeSingleDocUserBando(String codbando, String username, String codicedoc, String tipologia) {
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.removeSingleDocUserBando(codbando, username, codicedoc, tipologia);
        db.closeDB();
        return ctrl;
    }
    
    public static ArrayList<Docenti> getDocenti(String username) {
        Db_Bando db = new Db_Bando();
        ArrayList<Docenti> al = db.getDocenti(username);
        db.closeDB();
        return al;
    }
    
    public static boolean verificaDomandaCompleta(String username) {
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.verificaDomandaCompleta(username);
        db.closeDB();
        return ctrl;
    }
    
    public static boolean insAllegatoB1(String idallegato_b1, String username, String allegatocv,
                                        String allegatodr, String allegatob1) {
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.insAllegatoB1(idallegato_b1, username, allegatocv, allegatodr, allegatob1);
        db.closeDB();
        return ctrl;
    }
    
    public static boolean isPresenzaAllegatoB1(String idallegato_b1, String username) {
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.isPresenzaAllegatoB1(idallegato_b1, username);
        db.closeDB();
        return ctrl;
    }
    
    public static ArrayList<AllegatoB1> getAllegatoB1(String username, String id) {
        Db_Bando db = new Db_Bando();
        ArrayList<AllegatoB1> desc = db.getAllegatoB1(username, id);
        db.closeDB();
        return desc;
    }
    
    public static boolean delAllegatiDocenti(String username, String id) {
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.delAllegatiDocenti(username, id);
        db.closeDB();
        return ctrl;
    }
    
    public static boolean insertDocumentUserBando(Docuserbandi dub) {
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.insertDocUserBando(dub);
        db.closeDB();
        return ctrl;
    }
    
    public static ArrayList<DocumentiDocente> listaDocUserBandoDocenti(String username) {
        Db_Bando db = new Db_Bando();
        ArrayList<DocumentiDocente> desc = db.listaDocUserBandoDocenti(username);
        db.closeDB();
        return desc;
    }
    
    public static boolean delAllAllegatiDocenti(String username) {
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.delAllAllegatiDocenti(username);
        db.closeDB();
        return ctrl;
    }
    
    public static boolean esisteAllegatoB1Field(String username, int iddocente) {
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.esisteAllegatoB1Field(username, iddocente);
        db.closeDB();
        return ctrl;
    }
    
    public static boolean insAllegatoB1Field(String iddocente,String username,String periodo,String durata,String incarico,
                                      String finanziamento,String attivita,String committente) {
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.insAllegatoB1Field(iddocente, username, periodo, durata, incarico, finanziamento, attivita, committente);
        db.closeDB();
        return ctrl;
        
    }
    
    public static boolean delAllegatoB1Field(String id, String username){
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.delAllegatoB1Field(id, username);
        db.closeDB();
        return ctrl;
    }
    
    public static boolean delAllegatoB1Field(String username){
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.delAllegatoB1Field(username);
        db.closeDB();
        return ctrl;
    }
    
    public static AllegatoA getAllegatoA(String username) {
        Db_Bando db = new Db_Bando();
        AllegatoA a = db.getAllegatoA(username);
        db.closeDB();
        return a;
    }
    
    public static boolean esisteAllegatoB1Field(String username) {
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.esisteAllegatoB1Field(username);
        db.closeDB();
        return ctrl;
    }
    
    public static AllegatoB getAllegatoB(String username) {
        Db_Bando db = new Db_Bando();
        AllegatoB allegatoB = db.getAllegatoB(username);
        db.closeDB();
        return allegatoB;
    }
    
    public static boolean isDomandaPresente(String bandorif,String username) {
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.isDomandaPresente(bandorif, username);
        db.closeDB();
        return ctrl;
    }
    
    public static ArrayList<AllegatoB1_field> alb1(String user,String iddoc){
        Db_Bando db = new Db_Bando();
        ArrayList<AllegatoB1_field> al = db.alb1(user, iddoc);
        db.closeDB();
        return al;
    }
    
    public static boolean presenzaAllB1Field(String user,String iddoc){
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.presenzaAllB1Field(user, iddoc);
        db.closeDB();
        return ctrl;
    }
    
    public static int getDocentiAllegatoA(String username) {
        Db_Bando db = new Db_Bando();
        int x = db.getDocentiAllegatoA(username);
        db.closeDB();
        return x;
    }
    
    public static boolean isPresenteUsername(String username) {
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.isPresenteUsername(username);
        db.closeDB();
        return ctrl;
    }
    public static boolean isPresenzaDocumento(String username, String tipologia) {
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.isPresenzaDocumento(username,tipologia);
        db.closeDB();
        return ctrl;
    }
    
    public static boolean setStatoDomandaAccRif(String username, String stato, String protocollo, String motivazione){
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.setStatoDomandaAccRif(username, stato, protocollo, motivazione);
        db.closeDB();
        return ctrl;
    }
    
    public static boolean getStatoDomanda(String username) {
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.getStatoDomanda(username);
        db.closeDB();
        return ctrl;
    }
    
    public static boolean insertDocumentUserConvenzioni(Docuserconvenzioni dub) {
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.insertDocumentUserConvenzioni(dub);
        db.closeDB();
        return ctrl;
    }
    
    public static boolean verPresenzaConvenzioni(String username,String coddoc){
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.verPresenzaConvenzioni(username,coddoc);
        db.closeDB();
        return ctrl;
    }
    
    public static String getPathConvenzioni(String username,String coddoc) {
        Db_Bando db = new Db_Bando();
        String path = db.getPathConvenzioni(username, coddoc);
        db.closeDB();
        return path;
    }
    
    public static boolean remDocConvenzioni(String username, String coddoc) {
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.remDocConvenzioni(username, coddoc);
        db.closeDB();
        return ctrl;
    }
    
    public static boolean verificaPresenzaConvenzioni(String username, String coddoc) {
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.verificaPresenzaConvenzioni(username, coddoc);
        db.closeDB();
        return ctrl;
    }
    
    public static int countDocumentConvenzioni(String username) {
        Db_Bando db = new Db_Bando();
        int var = db.countDocumentConvenzioni(username);
        db.closeDB();
        return var;
    }
    
    public static boolean setStatoInvioDocumenti(String username) {
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.setStatoInvioDocumenti(username);
        db.closeDB();
        return ctrl;
    }
    
    public static boolean verificaInvioConvenzione(String username) {
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.verificaInvioConvenzione(username);
        db.closeDB();
        return ctrl;
    }
    
    public static ArrayList<Docuserconvenzioni> getDocumentiConvenzioni(String username) {
        Db_Bando db = new Db_Bando();
        ArrayList<Docuserconvenzioni> al = db.getDocumentiConvenzioni(username);
        db.closeDB();
        return al;
    }
    
    public static boolean settaInvioEmailROMA(String username){
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.settaInvioEmailROMA(username);
        db.closeDB();
        return ctrl;
    }
    
    public static String getInvioEmailROMA(String username){
        Db_Bando db = new Db_Bando();
        String ctrl = db.getInvioEmailROMA(username);
        db.closeDB();
        return ctrl;
    }
    
    // ROMA
    public static boolean insertConvenzioneROMA(String username,String path) {
        Db_Bando db = new Db_Bando();
        boolean ctrl = db.insertConvenzioneROMA(username, path);
        db.closeDB();
        return ctrl;
    }
    
    public static  String getConvenzioneROMA(String username) {
        Db_Bando db = new Db_Bando();
        String path = db.getConvenzioneROMA(username);
        db.closeDB();
        return path;
    }
    
    public static String[] getValoriPerEmail(String coddomanda) {
        Db_Bando db = new Db_Bando();
        String var[] = db.getValoriPerEmail(coddomanda);
        db.closeDB();
        return var;
    }
    
    public static String getRagioneSociale(String user){
        Db_Bando db = new Db_Bando();
        String ragsoc = db.getRagioneSociale(user);
        db.closeDB();
        return ragsoc;
    }
    
    
}
