package com.seta.action;

import org.apache.commons.lang3.StringEscapeUtils;

public class Label {

    String societa, statodoc, statoinv, statocs, numdoc, datadoc, codcliente, ragsoc, piva, sez;//Ricerca

    String username, password, passdim; //Login

    String resetOk, resetErr;//Reset

    String sist, gp, modPa, modPro, anag, soc, dest, cons, doc, docAcq, lot, flude, re, resi, consu, gestu, useai, crea; //MENU

    String home; //Pagine

    String ricerca, reset, excel, result, az, colu; //Bottoni

    String da, a;

    String pro, sta, des, dat1, us; //INFO

    String oldp, newp, confp; //PASSWORD

    String lotto, file, ndoc, datapub, dataelab, utelab, idlotto, data_inizio, data_fine, stato_lotto, desc_err, doc_tot; //LOTTO

    String doc_pub, doc_no_pub, doc_doppi, doc_scart, info, nome_flusso, corretti, errati, scartati, data_caricamento;

    String destinatario, lingua, azione;

    String nometxt, nomepdf; //Info Lotto

    String domPec, alias, casella, imaps, imaport, smtps, smtport, pass, ogg, txt; //ANAGRAFICA SOC

    String anno, mese, gennaio, febbraio, marzo, aprile, maggio, giugno, luglio, agosto, settembre, ottobre, novembre, dicembre, numdoar, utme, utcu, pag; //CONSUNT

    String perr, inon, pii, pacc, pecos, pemaco, post;    //REPORT STATI INVIO

    String tipoinvio, pecmail; //REPORT DEST

    String nome, cognome, tipo, stato, email, ind;//USER

    String addest; //DEST

    String attenz, nores, controll; //AVVISI

    String infbase, prof, conferm, continua, indietro, save, vis; //CREAZ UTENTE

    String full, lock, modificaOK, dettagli, invia, invioOk, mailko, mailko1, abil, agg;

    String newDest, controlloDest, cont, destOk, destKo, destmodOk, destpre, destnopres, destIn, destCon, moddes, profiloKo; //NEW DEST

    public static String uneHtml(String val) {
        return StringEscapeUtils.unescapeHtml4(val);
    }

    public static String escHtml(String val) {
        return StringEscapeUtils.escapeHtml4(val);
    }

    public Label() {

    }

    public static String getLabelLanguage(String lan, String naz) {

        if (lan.equals("IT")) {
            if (naz.equals("IT")) {
                return "Italiano";
            }
            if (naz.equals("EN")) {
                return "Inglese";
            }
            if (naz.equals("FR")) {
                return "Francese";
            }
            if (naz.equals("DE")) {
                return "Tedesco";
            }
            if (naz.equals("PT")) {
                return "Portoghese";
            }
        }

        if (lan.equals("EN")) {
            if (naz.equals("IT")) {
                return "Italian";
            }
            if (naz.equals("EN")) {
                return "English";
            }
            if (naz.equals("FR")) {
                return "French";
            }
            if (naz.equals("DE")) {
                return "German";
            }
            if (naz.equals("PT")) {
                return "Portuguese";
            }
        }

        if (lan.equals("FR")) {
            if (naz.equals("IT")) {
                return "Italien";
            }
            if (naz.equals("EN")) {
                return "Anglais";
            }
            if (naz.equals("FR")) {
                return "Fran&#231;ais";
            }
            if (naz.equals("DE")) {
                return "Allemand";
            }
            if (naz.equals("PT")) {
                return "Portugais";
            }
        }

        if (lan.equals("PT")) {
            if (naz.equals("IT")) {
                return "Italiano";
            }
            if (naz.equals("EN")) {
                return "Ingl&#227s";
            }
            if (naz.equals("FR")) {
                return "Franc&#227s";
            }
            if (naz.equals("DE")) {
                return "Alem&#227o";
            }
            if (naz.equals("PT")) {
                return "Portugu&#234;s";
            }
        }

        return "";

    }

    public Label(String la) {
        String separator = " - ";
        if (la.equals("IT")) {
            //NEW DEST
            this.profiloKo = "Errore Durante la Modifica del Profilo. Riprovare.";
            this.agg = "Clicca Per Aggiornare i Dati.";
            //Ricerca
            this.societa = "Società";
            this.statodoc = "Stato Documento";
            this.statoinv = "Stato Invio";
            this.statocs = "Stato CS";
            this.numdoc = "Numero Documento";
            this.datadoc = "Data Documento";
            this.codcliente = "Codice Cliente";
            this.ragsoc = "Ragione Sociale";
            this.piva = "Partita IVA";
            this.sez = "Sezionale";

            //Login
            this.username = "Username";
            this.password = "Password";
            this.passdim = "Password Dimenticata ?";
            this.modificaOK = "Modifica Apportata Con Successo.";
            //Menu
            this.sist = "Sistema";
            this.gp = "Gestione Profilo";
            this.modPa = "Modifica Password";
            this.modPro = "Modifica Profilo";
            this.anag = "Anagrafica";
            this.soc = "Società";
            this.dest = "Destinatari";
            this.cons = "Consultazione";
            this.doc = "Documenti";
            this.docAcq = "Documenti Acquisiti";
            this.lot = "Lotti";
            this.flude = "Flusso Destinatari";
            this.re = "Reports";
            this.resi = "Stati Invio";
            this.consu = "Consuntivazione";
            this.gestu = "Gestione Utenze";
            this.useai = "Utenti Air Liquide";
            this.crea = "Crea Utente";

            //Bottoni
            this.ricerca = "Ricerca";
            this.reset = "Reset";
            this.excel = "Esporta in Excel";
            this.result = "Risultati Ricerca";
            this.colu = "Seleziona Colonne";

            this.da = "Da";
            this.a = "A";

            //Info
            this.pro = "Processo";
            this.sta = "Stato";
            this.des = "Descrizione";
            this.dat1 = "Data";
            this.us = "User";
            this.nometxt = "Nome File txt ";
            this.nomepdf = "Nome File pdf ";

            //PSW
            this.oldp = "Vecchia Password";
            this.newp = "Nuova Password";
            this.confp = "Conferma Password";

            //LOTTO
            this.info = "Info";
            this.idlotto = "Id Lotto";
            this.data_inizio = "Data Inzio Lavorazione";
            this.data_fine = "Data Fine Lavorazione";
            this.stato_lotto = "Stato Lotto";
            this.desc_err = "Descrizione Errore";
            this.doc_tot = "Doc Totali";
            this.doc_pub = "Doc Pubblicati";
            this.doc_no_pub = "Doc non pubblicati";
            this.doc_doppi = "Doc Doppi";
            this.doc_scart = "Doc Scartati";
            this.dataelab = "Data Elaborazione";
            this.lotto = "Nome Lotto";
            this.file = "Nome File";
            this.ndoc = "Data Documento";
            this.datapub = "Data Pubblicazione";
            this.utelab = "Utente Elaborazione";

            // FLUSSO DEST
            this.lingua = "Lingua";
            this.azione = "Azione";
            this.destinatario = "Destinatario";
            this.nome_flusso = "Nome Flusso";
            this.corretti = "Corretti";
            this.errati = "Errati";
            this.scartati = "Scartati";
            this.data_caricamento = "Data Caricamento";

            //ANASOC
            this.domPec = "Dominio PEC";
            this.alias = "Alias Mittente PEC";
            this.casella = "Casella PEC";
            this.imaps = "Imaps";
            this.imaport = "Imaps Port";
            this.smtps = "Smtps";
            this.smtport = "Smtps Port";
            this.pass = "Password";
            this.ogg = "Oggetto";
            this.txt = "Corpo";

            //CONSUNT
            this.anno = "Anno";
            this.mese = "Mese";
            this.gennaio = "Gennaio";
            this.febbraio = "Febbraio";
            this.marzo = "Marzo";
            this.aprile = "Aprile";
            this.maggio = "Maggio";
            this.giugno = "Giugno";
            this.luglio = "Luglio";
            this.agosto = "Agosto";
            this.settembre = "Settembre";
            this.ottobre = "Ottobre";
            this.novembre = "Novembre";
            this.dicembre = "Dicembre";
            this.numdoar = "Numero Documenti Archiviati";
            this.utme = "Utenti[Mese]";
            this.utcu = "Utenti[Cum]";
            this.pag = "Pagine";

            //REPORT STATI INVIO
            this.perr = "PEC - In errore";
            this.inon = "Invio Non Previsto";
            this.pii = "PEC - In invio";
            this.pacc = "PEC - Accettata";
            this.pecos = "PEC - Consegnata";
            this.pemaco = "PEC - Mancata consegna";
            this.post = "Postalizzato";

            //REPORT DEST
            this.tipoinvio = "Metodo Di Invio";
            this.pecmail = "Indirizzo PEC/mail";

            //USER
            this.stato = "Stato";
            this.email = "Email";
            this.nome = "Nome";
            this.cognome = "Cognome";
            this.tipo = "Tipo";
            this.ind = "Indirizzo";

            //DEST
            this.addest = "Aggiungi Nuovo Destinatario";
            this.az = "Azioni";

            //AVVISI
            this.attenz = "Attenzione!";
            this.nores = "La Ricerca Non Ha Prodotto Alcun Risultato";
            this.controll = "Controllare i Campi.";
            //CREZ UTENTE
            this.infbase = "Informazioni Base";
            this.prof = "Profilo";
            this.conferm = "Conferma Dati";
            this.continua = "Continua";
            this.indietro = "Indietro";
            this.save = "Salva";
            this.vis = "Società Visibili";

            //MENUALTO
            this.full = "Schermo Intero";
            this.lock = "Blocca Schermo";
            this.dettagli = "Dettagli";
            this.invia = "Invia Mail";
            this.invioOk = "Email Inviata Con Successo all'Indirizzo:";
            this.mailko = "Impossibile Inviare eMail. Controllare.";
            this.mailko1 = "Indirizzo Mail Errato. Controllare.";
            this.abil = "Abilita / Disabilita";
        } else { //ENG

            //NEW DEST
            this.newDest = "Add Recipient";
            this.controlloDest = "Check Recipient";
            this.cont = "Check";
            this.destOk = "Recipient Successfully Added.";
            this.destKo = "Unable to Add Recipient. Please Check Below.";
            this.destmodOk = "Modified Recipient Successfully.";
            this.destpre = "Present Recipient. Change The Data.";
            this.destnopres = "Recipient Not Present. Add Data.";
            this.destIn = "Recipient Information";
            this.destCon = "Configuration";
            this.moddes = "Edit Recipient";
            this.profiloKo = "Unable To Edit Profile. Please Check Below.";

            this.agg = "Click to Update Data.";

            //Ricerca
            this.societa = "Company";
            this.statodoc = "Document Status";
            this.statoinv = "Sending Status";
            this.statocs = "CS Status";
            this.numdoc = "Document Number";
            this.datadoc = "Document Date";
            this.codcliente = "Client Code";
            this.ragsoc = "Company Name";
            this.piva = "VAT";
            this.sez = "Sectional";

            //Login
            this.username = "Username";
            this.password = "Password";
            this.passdim = "Forgot Your Password?";
            this.modificaOK = "Modifica Apportata Con Successo.";

            //Menu
            this.sist = "System";
            this.gp = "Profile";
            this.modPa = "Change Password";
            this.modPro = "Change Profile";
            this.anag = "Master Data";
            this.soc = "Company";
            this.dest = "Recipients";
            this.cons = "Consultation";
            this.doc = "Documents";
            this.docAcq = "Scanned Documents";
            this.lot = "Batch";
            this.flude = "Recipients Flow";
            this.re = "Reports";
            this.resi = "Document Status";
            this.consu = "Summarizing";
            this.gestu = "User Management";
            this.useai = "User Air Liquide";
            this.crea = "Create User";

            //Bottoni
            this.ricerca = "Search";
            this.reset = "Reset";
            this.excel = "Export to Excel";
            this.result = "Results";
            this.colu = "Select Column";

            this.da = "From";
            this.a = "To";

            //Info
            this.pro = "Process";
            this.sta = "Status";
            this.des = "Description";
            this.dat1 = "Date";
            this.us = "User";
            this.nometxt = "Filename .txt";
            this.nomepdf = "Filename .pdf";

            //PSW
            this.oldp = "Old Password";
            this.newp = "New Password";
            this.confp = "Confirm Password";

            //LOTTO
            this.azione = "Action";
            this.lingua = "Language";
            this.destinatario = "Recipient";
            this.info = "Info";
            this.stato_lotto = "Batch Status";
            this.data_inizio = "Start Processing Date";
            this.data_fine = "End Processing Date";
            this.idlotto = "Batch Id";
            this.desc_err = "Description Error";
            this.doc_tot = "Doc Total";
            this.doc_pub = "Doc Published";
            this.doc_no_pub = "Doc Unpublished";
            this.doc_doppi = "Doc Double";
            this.doc_scart = "Doc Rejected";
            this.dataelab = "Processing Date";
            this.lotto = "Batch Name";
            this.file = "File Name";
            this.ndoc = "Document Date";
            this.datapub = "Publication Date";
            this.utelab = "Processing User";

            // FLUSSO DEST
            this.nome_flusso = "Flow Name";
            this.corretti = "Correct";
            this.errati = "Error";
            this.scartati = "Discarded";
            this.data_caricamento = "Loading Date";

            //FLUSSO DESTINATARi
            this.azione = "Action";
            this.lingua = "Language";
            this.destinatario = "Recipient";
            this.info = "Info";
            this.stato_lotto = "Batch Status";
            this.data_inizio = "Start Processing Date";
            this.data_fine = "End Processing Date";
            this.idlotto = "Batch Id";
            this.desc_err = "Description Error";
            this.doc_tot = "Doc Total";
            this.doc_pub = "Doc Published";
            this.doc_no_pub = "Doc Unpublished";
            this.doc_doppi = "Doc Double";
            this.doc_scart = "Doc Rejected";

            //ANASOC
            this.domPec = "Domain PEC";
            this.alias = "Alias PEC sender";
            this.casella = "Box PEC";
            this.imaps = "Imaps";
            this.imaport = "Imaps Port";
            this.smtps = "Smtps";
            this.smtport = "Smtps Port";
            this.pass = "Password";
            this.ogg = "Subject";
            this.txt = "Body";

            //CONSUNT
            this.anno = "Year";
            this.mese = "Month";
            this.gennaio = "January";
            this.febbraio = "February";
            this.marzo = "March";
            this.aprile = "April";
            this.maggio = "May";
            this.giugno = "June";
            this.luglio = "July";
            this.agosto = "August";
            this.settembre = "September";
            this.ottobre = "October";
            this.novembre = "November";
            this.dicembre = "December";
            this.numdoar = "Number of Documents";
            this.utme = "Users[Month]";
            this.utcu = "Users[Sum]";
            this.pag = "Pages";

            //REPORT STATI INVIO
            this.perr = "PEC - In errore";
            this.inon = "Invio Non Previsto";
            this.pii = "PEC - In invio";
            this.pacc = "PEC - Accettata";
            this.pecos = "PEC - Consegnata";
            this.pemaco = "PEC - Mancata consegna";
            this.post = "Postalizzato";

            //REPORT DEST
            this.tipoinvio = "Sending Method";
            this.pecmail = "PEC/mail Address";

            //USER
            this.stato = "Status";
            this.email = "Email";
            this.nome = "Name";
            this.cognome = "Surname";
            this.tipo = "Type";
            this.ind = "Address";

            //DEST
            this.addest = "Add New Recipient";
            this.az = "Actions";

            //AVVISI
            this.attenz = "Attention!";
            this.nores = "The Search Did Not Return Any Results.";
            this.controll = "Please Check Below.";

            //CREZ UTENTE
            this.infbase = "Basic Information";
            this.prof = "Profile";
            this.conferm = "Confirmation Data";
            this.continua = "Continue";
            this.indietro = "Back";
            this.save = "Save";
            this.vis = "Companies Available";

            //MENUALTO
            this.full = "Full Screen";
            this.lock = "Lock Screen";
            this.dettagli = "Details";
            this.invia = "Send Mail";
            this.invioOk = "Email Successfully Sent The Address:";
            this.mailko = "Unable To Send Email. Please Check Below.";
            this.mailko1 = "Invalid Email Address. Please Check Below.";
            this.abil = "Enable / Disable";
        }

        //pagine
        this.home = this.cons + separator + this.doc;

    }

    //ALTRE ETICHETTE
    public static String getTypeUser(String la, String us) {
        if (us.equalsIgnoreCase("adm") || us.equalsIgnoreCase("admin")) {
            return "Admin";
        } else if (us.equalsIgnoreCase("usr") || us.equalsIgnoreCase("user")) {
            return "User";
        } else {
            return us;
        }
    }

    public static String getMailSubject(String lan) {
        if (lan.equals("IT")) {
            return "Portale FEA";
        } else {
            return "FEA Portal: Login Credentials";
        }
    }

    public static String getMailText(String lan, String username, String ps,String addr) {
        if (lan.equals("IT")) {
            return "Gentile Utente,</br>La Sua password &egrave; stata modificata con successo.</br></br>"
                    + "Qui di seguito trova il riepilogo dei suoi dati di accesso.</br></br>"
                    + "Nome utente: "
                    + StringEscapeUtils.escapeHtml4(username)
                    + "</br>Password: "
                    + ps
                    + "</br></br>Indirizzo web di accesso: "+ addr+" </br></br></br>"
                    + "Per motivi di sicurezza la invitiamo a cambiare la password al primo accesso.</br>"
                    + "ATTENZIONE: questo messaggio &egrave; inviato automaticamente, non rispondere a questa e-mail.</br></br></br>"
                    + "Cogliamo l&rsquo;occasione per porgerLe i nostri pi&ugrave; cordiali saluti.";
        } else {
            return "Dear Customer, </br> Your password was changed successfully. </br> </br>"
                    + "Below is a summary of his access data. </br> </br>"
                    + "Username: "
                    + StringEscapeUtils.escapeHtml4(username)
                    + "</br> Password: "
                    + ps
                    + "</br> </br> Address of web access: </br> </br> </br>"
                    + "For security reasons, please change the password at first logon. </br>"
                    + "WARNING: This message is sent automatically, do not respond to this e-mail. </br> </br> </br>"
                    + "We take this opportunity to extend our best wishes.";
        }
    }

    public static String getMailCreateUserText(String lan, String username, String ps) {
        if (lan.equals("IT")) {
            return "Gentile Utente,</br>La sua registrazione al Portale &egrave; stata completata con successo.</br></br>"
                    + "Qui di seguito trova il riepilogo dei suoi dati di accesso.</br></br>"
                    + "Nome utente: "
                    + StringEscapeUtils.escapeHtml4(username)
                    + "</br>Password: "
                    + ps
                    + "</br></br>Indirizzo web di accesso: </br></br></br>"
                    + "Per motivi di sicurezza la invitiamo a cambiare la password al primo accesso.</br>"
                    + "ATTENZIONE: questo messaggio &egrave; inviato automaticamente, non rispondere a questa e-mail.</br></br></br>"
                    + "Cogliamo l&rsquo;occasione per porgerLe i nostri pi&ugrave; cordiali saluti.";
        } else {
            return "Dear Customer, </br> Your registration to the portal has been completed successfully. </br> </br>"
                    + "Below is a summary of his access data. </br> </br>"
                    + "Username: "
                    + StringEscapeUtils.escapeHtml4(username)
                    + "</br> Password: "
                    + ps
                    + "</br> </br> Address of web access: </br> </br> </br>"
                    + "For security reasons, please change the password at first logon. </br>"
                    + "WARNING: This message is sent automatically, do not respond to this e-mail. </br> </br> </br>"
                    + "We take this opportunity to extend our best wishes.";
        }
    }

    public static String getMessageCreateUser(String la, String esito) {
        if (la.equals("IT")) {
            if (esito.equals("ok")) {
                return "Utente Aggiunto Con Successo.";
            } else if (esito.equals("ko")) {
                return "Errore Durante La Creazione Dell'Utente. L'Utente Potrebbe Già Esistere o Il Server di Autenticazione Potrebbe Non Essere Disponibile. Riprovare Tra Qualche Istante.";
            }
        } else {
            if (esito.equals("ok")) {
                return "User ";
            } else if (esito.equals("ko")) {
                return "Error During User's Creation. Try Again.";
            }
        }
        return "Error";
    }

    public static String getMessageReset(String la, String esito) {
        if (la.equals("IT")) {
            if (esito.equals("passok")) {
                return "Password Modificata!";
            } else if (esito.equals("passko1")) {
                return "Errore Durante La Modifica Della Password.";
            } else if (esito.equals("passko2")) {
                return "La Password deve Contenere Almeno un Carattere Numerico!";
            } else if (esito.equals("passko3")) {
                return "La Password deve Contenere Almeno un Carattere Speciale!";
            } else if (esito.equals("passko4")) {
                return "La Password Deve Essere Almeno di 8 Caratteri!";
            } else if (esito.equals("passko5")) {
                return "La Password non Corrispondono!";
            } else if (esito.equals("passko6")) {
                return "La Nuova Password Deve Essere Diversa Dalla Precedente!";
            } else if (esito.equals("linguaok")) {
                return "Lingua Cambiata Correttamente.";
            } else if (esito.equals("linguako")) {
                return "Errore Durante Il Cambiamento Della Lingua.";
            } else {
                return "Errore Durante La Modifica Della Password.";
            }
        } else {
            //ENG
            if (esito.equals("passok")) {
                return "The Password Has Been Change!";
            } else if (esito.equals("passko1")) {
                return "Error During Change Password. Try Again.";
            } else if (esito.equals("passko2")) {
                return "Password Required At Least Numeric!";
            } else if (esito.equals("passko3")) {
                return "Password Required At Least Special Character!";
            } else if (esito.equals("passko4")) {
                return "Password Required At Least 8 Character!";
            } else if (esito.equals("passko5")) {
                return "Password Doesn't Macth!";
            } else if (esito.equals("passko6")) {
                return "The New Password Must Be Different From The Previous!";
            } else if (esito.equals("linguaok")) {
                return "Language Has Been Changed.";
            } else if (esito.equals("linguako")) {
                return "Error During Change Language. Try Again.";
            } else {
                return "Error When Changing Password.";
            }
        }
    }

    public static String getMessageLogin(String la, String esito) {
        if (la.equals("IT")) {
            if (esito.equals("KO1")) {
                return "Username e/o Password Errati. Riprovare";
            } else if (esito.equals("KO2")) {
                return "Username e/o Password Errati. Riprovare";
            } else if (esito.equals("KO3")) {
                return "Username e/o Password Errati. Riprovare";
            } else if (esito.equals("KO4")) {
                return "Username e/o Password Errati. Riprovare";
            } else {
                return "Username e/o Password Errati. Riprovare";
            }
        } else {
            //ENG
            if (esito.equals("KO1")) {
                return "I Dati Formiti Risultano Errati. Riprovare";
            } else if (esito.equals("KO2")) {
                return "I Dati Formiti Risultano Errati. Riprovare";
            } else if (esito.equals("KO3")) {
                return "Immettere Tutti i Campi.";
            } else if (esito.equals("KO4")) {
                return "Immettere Tutti i Campi.";
            } else {
                return esito;
            }
        }
    }

    public static String getMessageJS(String la, String esito) {
        if (la.equals("IT")) {
            if (esito.equals("oldpass")) {
                return "Immettere Vecchia Password.";
            } else if (esito.equals("newpass")) {
                return "Immettere Nuova Password.";
            } else if (esito.equals("confpass")) {
                return "Immettere Conferma Password.";
            } else if (esito.equals("user")) {
                return "Immettere Username.";
            } else if (esito.equals("mail")) {
                return "Immettere e-Mail.";
            } else if (esito.equals("mailerr")) {
                return "Indirizzo e-Mail ERRATO!";
            } else if (esito.equals("np1")) {
                return "Nuova Password";
            } else if (esito.equals("vp1")) {
                return "Vecchia Password";
            } else if (esito.equals("cp1")) {
                return "Conferma Password";
            } else if (esito.equals("vita")) {
                return "Italiano";
            } else if (esito.equals("veng")) {
                return "Passa alla Versione Inglese";
            } else {
                return "";
            }
        } else { //eng
            if (esito.equals("oldpass")) {
                return "Insert Old Password.";
            } else if (esito.equals("newpass")) {
                return "Insert New Password.";
            } else if (esito.equals("confpass")) {
                return "Insert Password Confirm.";
            } else if (esito.equals("user")) {
                return "Insert Username.";
            } else if (esito.equals("mail")) {
                return "Insert e-Mail.";
            } else if (esito.equals("mailerr")) {
                return "Error e-Mail!";
            } else if (esito.equals("np1")) {
                return "New Password";
            } else if (esito.equals("vp1")) {
                return "Old Password";
            } else if (esito.equals("cp1")) {
                return "Confirm Password";
            } else if (esito.equals("vita")) {
                return "Change to Italian Version";
            } else if (esito.equals("veng")) {
                return "English";
            } else {
                return "";
            }
        }
    }

    public static String getMessageRP(String la, String esito) {
        if (la.equals("IT")) {
            if (esito.equals("ok")) {
                return "La Password &#232; Stata Inviata.";
            } else if (esito.equals("ko")) {
                return "Username e/o email errati.";
            } else {
                return "";
            }
        } else {//ENG
            if (esito.equals("ok")) {
                return "The Password Has Been Sent.";
            } else if (esito.equals("ko")) {
                return "Incorrect Date. Try Again.";
            } else {
                return "";
            }
        }
    }

    //GET - SET
    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getLock() {
        return lock;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public String getControll() {
        return controll;
    }

    public void setControll(String controll) {
        this.controll = controll;
    }

    public String getInfbase() {
        return infbase;
    }

    public void setInfbase(String infbase) {
        this.infbase = infbase;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getConferm() {
        return conferm;
    }

    public void setConferm(String conferm) {
        this.conferm = conferm;
    }

    public String getContinua() {
        return continua;
    }

    public void setContinua(String continua) {
        this.continua = continua;
    }

    public String getIndietro() {
        return indietro;
    }

    public void setIndietro(String indietro) {
        this.indietro = indietro;
    }

    public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }

    public String getColu() {
        return colu;
    }

    public void setColu(String colu) {
        this.colu = colu;
    }

    public String getAttenz() {
        return attenz;
    }

    public void setAttenz(String attenz) {
        this.attenz = attenz;
    }

    public String getNores() {
        return nores;
    }

    public void setNores(String nores) {
        this.nores = nores;
    }

    public String getAz() {
        return az;
    }

    public void setAz(String az) {
        this.az = az;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAddest() {
        return addest;
    }

    public void setAddest(String addest) {
        this.addest = addest;
    }

    public String getInd() {
        return ind;
    }

    public void setInd(String ind) {
        this.ind = ind;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipoinvio() {
        return tipoinvio;
    }

    public void setTipoinvio(String tipoinvio) {
        this.tipoinvio = tipoinvio;
    }

    public String getPecmail() {
        return pecmail;
    }

    public void setPecmail(String pecmail) {
        this.pecmail = pecmail;
    }

    public String getPerr() {
        return perr;
    }

    public void setPerr(String perr) {
        this.perr = perr;
    }

    public String getInon() {
        return inon;
    }

    public void setInon(String inon) {
        this.inon = inon;
    }

    public String getPii() {
        return pii;
    }

    public void setPii(String pii) {
        this.pii = pii;
    }

    public String getPacc() {
        return pacc;
    }

    public void setPacc(String pacc) {
        this.pacc = pacc;
    }

    public String getPecos() {
        return pecos;
    }

    public void setPecos(String pecos) {
        this.pecos = pecos;
    }

    public String getPemaco() {
        return pemaco;
    }

    public void setPemaco(String pemaco) {
        this.pemaco = pemaco;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getNumdoar() {
        return numdoar;
    }

    public void setNumdoar(String numdoar) {
        this.numdoar = numdoar;
    }

    public String getUtme() {
        return utme;
    }

    public void setUtme(String utme) {
        this.utme = utme;
    }

    public String getUtcu() {
        return utcu;
    }

    public void setUtcu(String utcu) {
        this.utcu = utcu;
    }

    public String getPag() {
        return pag;
    }

    public void setPag(String pag) {
        this.pag = pag;
    }

    public String getGennaio() {
        return gennaio;
    }

    public void setGennaio(String gennaio) {
        this.gennaio = gennaio;
    }

    public String getFebbraio() {
        return febbraio;
    }

    public void setFebbraio(String febbraio) {
        this.febbraio = febbraio;
    }

    public String getMarzo() {
        return marzo;
    }

    public void setMarzo(String marzo) {
        this.marzo = marzo;
    }

    public String getAprile() {
        return aprile;
    }

    public void setAprile(String aprile) {
        this.aprile = aprile;
    }

    public String getMaggio() {
        return maggio;
    }

    public void setMaggio(String maggio) {
        this.maggio = maggio;
    }

    public String getGiugno() {
        return giugno;
    }

    public void setGiugno(String giugno) {
        this.giugno = giugno;
    }

    public String getLuglio() {
        return luglio;
    }

    public void setLuglio(String luglio) {
        this.luglio = luglio;
    }

    public String getAgosto() {
        return agosto;
    }

    public void setAgosto(String agosto) {
        this.agosto = agosto;
    }

    public String getSettembre() {
        return settembre;
    }

    public void setSettembre(String settembre) {
        this.settembre = settembre;
    }

    public String getOttobre() {
        return ottobre;
    }

    public void setOttobre(String ottobre) {
        this.ottobre = ottobre;
    }

    public String getNovembre() {
        return novembre;
    }

    public void setNovembre(String novembre) {
        this.novembre = novembre;
    }

    public String getDicembre() {
        return dicembre;
    }

    public void setDicembre(String dicembre) {
        this.dicembre = dicembre;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getMese() {
        return mese;
    }

    public void setMese(String mese) {
        this.mese = mese;
    }

    public String getOgg() {
        return ogg;
    }

    public void setOgg(String ogg) {
        this.ogg = ogg;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getDomPec() {
        return domPec;
    }

    public void setDomPec(String domPec) {
        this.domPec = domPec;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCasella() {
        return casella;
    }

    public void setCasella(String casella) {
        this.casella = casella;
    }

    public String getImaps() {
        return imaps;
    }

    public void setImaps(String imaps) {
        this.imaps = imaps;
    }

    public String getImaport() {
        return imaport;
    }

    public void setImaport(String imaport) {
        this.imaport = imaport;
    }

    public String getSmtps() {
        return smtps;
    }

    public void setSmtps(String smtps) {
        this.smtps = smtps;
    }

    public String getSmtport() {
        return smtport;
    }

    public void setSmtport(String smtport) {
        this.smtport = smtport;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNometxt() {
        return nometxt;
    }

    public void setNometxt(String nometxt) {
        this.nometxt = nometxt;
    }

    public String getNomepdf() {
        return nomepdf;
    }

    public void setNomepdf(String nomepdf) {
        this.nomepdf = nomepdf;
    }

    public String getLotto() {
        return lotto;
    }

    public void setLotto(String lotto) {
        this.lotto = lotto;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getNdoc() {
        return ndoc;
    }

    public void setNdoc(String ndoc) {
        this.ndoc = ndoc;
    }

    public String getDatapub() {
        return datapub;
    }

    public void setDatapub(String datapub) {
        this.datapub = datapub;
    }

    public String getDataelab() {
        return dataelab;
    }

    public void setDataelab(String dataelab) {
        this.dataelab = dataelab;
    }

    public String getUtelab() {
        return utelab;
    }

    public void setUtelab(String utelab) {
        this.utelab = utelab;
    }

    public String getIdlotto() {
        return idlotto;
    }

    public void setIdlotto(String idlotto) {
        this.idlotto = idlotto;
    }

    public String getData_inizio() {
        return data_inizio;
    }

    public void setData_inizio(String data_inizio) {
        this.data_inizio = data_inizio;
    }

    public String getData_fine() {
        return data_fine;
    }

    public void setData_fine(String data_fine) {
        this.data_fine = data_fine;
    }

    public String getStato_lotto() {
        return stato_lotto;
    }

    public void setStato_lotto(String stato_lotto) {
        this.stato_lotto = stato_lotto;
    }

    public String getDesc_err() {
        return desc_err;
    }

    public void setDesc_err(String desc_err) {
        this.desc_err = desc_err;
    }

    public String getDoc_tot() {
        return doc_tot;
    }

    public void setDoc_tot(String doc_tot) {
        this.doc_tot = doc_tot;
    }

    public String getDoc_pub() {
        return doc_pub;
    }

    public void setDoc_pub(String doc_pub) {
        this.doc_pub = doc_pub;
    }

    public String getDoc_no_pub() {
        return doc_no_pub;
    }

    public void setDoc_no_pub(String doc_no_pub) {
        this.doc_no_pub = doc_no_pub;
    }

    public String getDoc_doppi() {
        return doc_doppi;
    }

    public void setDoc_doppi(String doc_doppi) {
        this.doc_doppi = doc_doppi;
    }

    public String getDoc_scart() {
        return doc_scart;
    }

    public void setDoc_scart(String doc_scart) {
        this.doc_scart = doc_scart;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getNome_flusso() {
        return nome_flusso;
    }

    public void setNome_flusso(String nome_flusso) {
        this.nome_flusso = nome_flusso;
    }

    public String getCorretti() {
        return corretti;
    }

    public void setCorretti(String corretti) {
        this.corretti = corretti;
    }

    public String getErrati() {
        return errati;
    }

    public void setErrati(String errati) {
        this.errati = errati;
    }

    public String getScartati() {
        return scartati;
    }

    public void setScartati(String scartati) {
        this.scartati = scartati;
    }

    public String getData_caricamento() {
        return data_caricamento;
    }

    public void setData_caricamento(String data_caricamento) {
        this.data_caricamento = data_caricamento;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getLingua() {
        return lingua;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }

    public String getAzione() {
        return azione;
    }

    public void setAzione(String azione) {
        this.azione = azione;
    }

    public String getOldp() {
        return oldp;
    }

    public void setOldp(String oldp) {
        this.oldp = oldp;
    }

    public String getNewp() {
        return newp;
    }

    public void setNewp(String newp) {
        this.newp = newp;
    }

    public String getConfp() {
        return confp;
    }

    public void setConfp(String confp) {
        this.confp = confp;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    public String getSta() {
        return sta;
    }

    public void setSta(String sta) {
        this.sta = sta;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDat1() {
        return dat1;
    }

    public void setDat1(String dat1) {
        this.dat1 = dat1;
    }

    public String getUs() {
        return us;
    }

    public void setUs(String us) {
        this.us = us;
    }

    public String getExcel() {
        return excel;
    }

    public void setExcel(String excel) {
        this.excel = excel;
    }

    public String getDa() {
        return da;
    }

    public void setDa(String da) {
        this.da = da;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getRicerca() {
        return ricerca;
    }

    public void setRicerca(String ricerca) {
        this.ricerca = ricerca;
    }

    public String getReset() {
        return reset;
    }

    public void setReset(String reset) {
        this.reset = reset;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getSist() {
        return sist;
    }

    public void setSist(String sist) {
        this.sist = sist;
    }

    public String getGp() {
        return gp;
    }

    public void setGp(String gp) {
        this.gp = gp;
    }

    public String getModPa() {
        return modPa;
    }

    public void setModPa(String modPa) {
        this.modPa = modPa;
    }

    public String getModPro() {
        return modPro;
    }

    public void setModPro(String modPro) {
        this.modPro = modPro;
    }

    public String getAnag() {
        return anag;
    }

    public void setAnag(String anag) {
        this.anag = anag;
    }

    public String getSoc() {
        return soc;
    }

    public void setSoc(String soc) {
        this.soc = soc;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getCons() {
        return cons;
    }

    public void setCons(String cons) {
        this.cons = cons;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getDocAcq() {
        return docAcq;
    }

    public void setDocAcq(String docAcq) {
        this.docAcq = docAcq;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getFlude() {
        return flude;
    }

    public void setFlude(String flude) {
        this.flude = flude;
    }

    public String getRe() {
        return re;
    }

    public void setRe(String re) {
        this.re = re;
    }

    public String getResi() {
        return resi;
    }

    public void setResi(String resi) {
        this.resi = resi;
    }

    public String getConsu() {
        return consu;
    }

    public void setConsu(String consu) {
        this.consu = consu;
    }

    public String getGestu() {
        return gestu;
    }

    public void setGestu(String gestu) {
        this.gestu = gestu;
    }

    public String getUseai() {
        return useai;
    }

    public void setUseai(String useai) {
        this.useai = useai;
    }

    public String getCrea() {
        return crea;
    }

    public void setCrea(String crea) {
        this.crea = crea;
    }

    public String getResetErr() {
        return resetErr;
    }

    public void setResetErr(String resetErr) {
        this.resetErr = resetErr;
    }

    public String getResetOk() {
        return resetOk;
    }

    public void setResetOk(String resetOk) {
        this.resetOk = resetOk;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSocieta() {
        return societa;
    }

    public void setSocieta(String societa) {
        this.societa = societa;
    }

    public String getStatodoc() {
        return statodoc;
    }

    public void setStatodoc(String statodoc) {
        this.statodoc = statodoc;
    }

    public String getStatoinv() {
        return statoinv;
    }

    public void setStatoinv(String statoinv) {
        this.statoinv = statoinv;
    }

    public String getStatocs() {
        return statocs;
    }

    public void setStatocs(String statocs) {
        this.statocs = statocs;
    }

    public String getNumdoc() {
        return numdoc;
    }

    public void setNumdoc(String numdoc) {
        this.numdoc = numdoc;
    }

    public String getDatadoc() {
        return datadoc;
    }

    public void setDatadoc(String datadoc) {
        this.datadoc = datadoc;
    }

    public String getCodcliente() {
        return codcliente;
    }

    public void setCodcliente(String codcliente) {
        this.codcliente = codcliente;
    }

    public String getRagsoc() {
        return ragsoc;
    }

    public void setRagsoc(String ragsoc) {
        this.ragsoc = ragsoc;
    }

    public String getPiva() {
        return piva;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }

    public String getSez() {
        return sez;
    }

    public void setSez(String sez) {
        this.sez = sez;
    }

    public String getPassdim() {
        return passdim;
    }

    public void setPassdim(String passdim) {
        this.passdim = passdim;
    }

    public String getModificaOK() {
        return modificaOK;
    }

    public void setModificaOK(String modificaOK) {
        this.modificaOK = modificaOK;
    }

    public String getDettagli() {
        return dettagli;
    }

    public void setDettagli(String dettagli) {
        this.dettagli = dettagli;
    }

    public String getInvia() {
        return invia;
    }

    public void setInvia(String invia) {
        this.invia = invia;
    }

    public String getInvioOk() {
        return invioOk;
    }

    public void setInvioOk(String invioOk) {
        this.invioOk = invioOk;
    }

    public String getMailko() {
        return mailko;
    }

    public void setMailko(String mailko) {
        this.mailko = mailko;
    }

    public String getMailko1() {
        return mailko1;
    }

    public void setMailko1(String mailko1) {
        this.mailko1 = mailko1;
    }

    public String getAbil() {
        return abil;
    }

    public void setAbil(String abil) {
        this.abil = abil;
    }

    public String getAgg() {
        return agg;
    }

    public void setAgg(String agg) {
        this.agg = agg;
    }

    public String getNewDest() {
        return newDest;
    }

    public void setNewDest(String newDest) {
        this.newDest = newDest;
    }

    public String getControlloDest() {
        return controlloDest;
    }

    public void setControlloDest(String controlloDest) {
        this.controlloDest = controlloDest;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getDestOk() {
        return destOk;
    }

    public void setDestOk(String destOk) {
        this.destOk = destOk;
    }

    public String getDestKo() {
        return destKo;
    }

    public void setDestKo(String destKo) {
        this.destKo = destKo;
    }

    public String getDestmodOk() {
        return destmodOk;
    }

    public void setDestmodOk(String destmodOk) {
        this.destmodOk = destmodOk;
    }

    public String getDestpre() {
        return destpre;
    }

    public void setDestpre(String destpre) {
        this.destpre = destpre;
    }

    public String getDestnopres() {
        return destnopres;
    }

    public void setDestnopres(String destnopres) {
        this.destnopres = destnopres;
    }

    public String getDestIn() {
        return destIn;
    }

    public void setDestIn(String destIn) {
        this.destIn = destIn;
    }

    public String getDestCon() {
        return destCon;
    }

    public void setDestCon(String destCon) {
        this.destCon = destCon;
    }

    public String getModdes() {
        return moddes;
    }

    public void setModdes(String moddes) {
        this.moddes = moddes;
    }

    public String getProfiloKo() {
        return profiloKo;
    }

    public void setProfiloKo(String profiloKo) {
        this.profiloKo = profiloKo;
    }

}
