<%-- 
    Document   : bando_index2
    Created on : 28-lug-2017, 15.43.51
    Author     : rcosco
--%>

<%@page import="rc.so.action.Constant"%>
<%@page import="rc.so.entity.Docenti"%>
<%@page import="rc.so.entity.Domandecomplete"%>
<%@page import="rc.so.entity.Docbandi"%>
<%@page import="rc.so.entity.Docuserbandi"%>
<%@page import="java.util.ArrayList"%>
<%@page import="rc.so.action.Liste"%>
<%@page import="rc.so.action.ActionB"%>
<%@page import="rc.so.util.Utility"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    if (session.getAttribute("username") == null) {
        Utility.redirect(request, response, "home.jsp");
    } else {
%>
<html>
    <head>
        <meta charset="utf-8" />
        <title><%=Constant.NAMEAPP%></title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="assets/seta/fontg/fontsgoogle1.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        <!-- END GLOBAL MANDATORY STYLES -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <link href="assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/clockface/css/clockface.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        <!-- END THEME GLOBAL STYLES -->
        <!-- BEGIN THEME LAYOUT STYLES -->
        <link href="assets/layouts/layout/css/layout.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/layouts/layout/css/themes/grey.min.css" rel="stylesheet" type="text/css" id="style_color" />
        <link href="assets/layouts/layout/css/custom.min.css" rel="stylesheet" type="text/css" />
        <!-- END THEME LAYOUT STYLES -->
        <link rel="shortcut icon" href="favicon.ico" /> 
        <script type="text/javascript">
            function dismiss(name) {
                document.getElementById(name).className = "modal fade";
                document.getElementById(name).style.display = "none";
            }

            function submitfor() {
                document.forms["formregist"].submit();
            }

            function submitfor(nameform) {
                console.log(nameform);
                document.forms[nameform].submit();
            }
        </script>
        <script type="text/javascript">

            <%
                String bandorif = session.getAttribute("bandorif").toString();
                String username = session.getAttribute("username").toString();
                String allegato_A_B = request.getParameter("allegato_A_B");
                ArrayList<String[]> prov = ActionB.province();
            %>

            function controllaReg1() {
                var tipologia = '<%=allegato_A_B%>';
                var output = "0";
                if (tipologia == "A") {
                    var ch1 = document.getElementsByName("ch1")[0].checked;
                    var ch2 = document.getElementsByName("ch2")[0].checked;
                    var ch3 = document.getElementsByName("ch3")[0].checked;
                    var ch4 = document.getElementsByName("ch4")[0].checked;
                    var ch5 = document.getElementsByName("ch5")[0].checked;
                    var ch6 = document.getElementsByName("ch6")[0].checked;
                    var ch7 = document.getElementsByName("ch7")[0].checked;
                    var regione = document.getElementById("regione").value;
                    var iscrizione = document.getElementById("iscrizione").value;
                    var regione2 = document.getElementById("regione2").value;
                    var iscrizione2 = document.getElementById("iscrizione2").value;
                    var aule = document.getElementById("aule").value;
                    var ctrlAula1 = false;
                    var ctrlAula2 = false;
                    var ctrlAula3 = false;
                    var ctrlAula4 = false;
                    var ctrlAula5 = false;
                    var pec = document.getElementById("pec").value;
                    var numeroDocenti = document.getElementById("numeroDocenti").value;

                    var msg = "";
                    // CONTROLLO AULE
                    // AULA 1
                    var citta = document.getElementById("citta").value;
                    var provincia = document.getElementById("provincia").value;
                    var indirizzo = document.getElementById("indirizzo").value;
                    var estremi = document.getElementById("estremi").value;
                    var accreditamento = document.getElementById("accreditamento").value;
                    var responsabile = document.getElementById("responsabile").value;
                    var responsabileAmm = document.getElementById("responsabileAmm").value;
                    var recapiti = document.getElementById("recapiti").value;
                    // AULA 2
                    var citta2 = document.getElementById("citta2").value;
                    var provincia2 = document.getElementById("provincia2").value;
                    var indirizzo2 = document.getElementById("indirizzo2").value;
                    var estremi2 = document.getElementById("estremi2").value;
                    var accreditamento2 = document.getElementById("accreditamento2").value;
                    var responsabile2 = document.getElementById("responsabile2").value;
                    var responsabileAmm2 = document.getElementById("responsabileAmm2").value;
                    var recapiti2 = document.getElementById("recapiti2").value;
                    // AULA 3
                    var citta3 = document.getElementById("citta3").value;
                    var provincia3 = document.getElementById("provincia3").value;
                    var indirizzo3 = document.getElementById("indirizzo3").value;
                    var estremi3 = document.getElementById("estremi3").value;
                    var accreditamento3 = document.getElementById("accreditamento3").value;
                    var responsabile3 = document.getElementById("responsabile3").value;
                    var responsabileAmm3 = document.getElementById("responsabileAmm3").value;
                    var recapiti3 = document.getElementById("recapiti3").value;
                    // AULA 4
                    var citta4 = document.getElementById("citta4").value;
                    var provincia4 = document.getElementById("provincia4").value;
                    var indirizzo4 = document.getElementById("indirizzo4").value;
                    var estremi4 = document.getElementById("estremi4").value;
                    var accreditamento4 = document.getElementById("accreditamento4").value;
                    var responsabile4 = document.getElementById("responsabile4").value;
                    var responsabileAmm4 = document.getElementById("responsabileAmm4").value;
                    var recapiti4 = document.getElementById("recapiti4").value;
                    // AULA 5
                    var citta5 = document.getElementById("citta5").value;
                    var provincia5 = document.getElementById("provincia5").value;
                    var indirizzo5 = document.getElementById("indirizzo5").value;
                    var estremi5 = document.getElementById("estremi5").value;
                    var accreditamento5 = document.getElementById("accreditamento5").value;
                    var responsabile5 = document.getElementById("responsabile5").value;
                    var responsabileAmm5 = document.getElementById("responsabileAmm5").value;
                    var recapiti5 = document.getElementById("recapiti5").value;
                    // REQUISITO 1
                    var periodo = document.getElementById("periodo").value;
                    var committente = document.getElementById("committente").value;
                    var destinatari = document.getElementById("destinatari").value;
                    var attivita = document.getElementById("attivita").value;
                    //REQUISITO 2
                    var periodo2 = document.getElementById("periodo2").value;
                    var committente2 = document.getElementById("committente2").value;
                    var destinatari2 = document.getElementById("destinatari2").value;
                    var attivita2 = document.getElementById("attivita2").value;
                    //REQUISITO 3
                    var periodo3 = document.getElementById("periodo3").value;
                    var committente3 = document.getElementById("committente3").value;
                    var destinatari3 = document.getElementById("destinatari3").value;
                    var attivita3 = document.getElementById("attivita3").value;
                    //REQUISITO 4
                    var periodo4 = document.getElementById("periodo4").value;
                    var committente4 = document.getElementById("committente4").value;
                    var destinatari4 = document.getElementById("destinatari4").value;
                    var attivita4 = document.getElementById("attivita4").value;
                    // REQQUISITO 5
                    var periodo5 = document.getElementById("periodo5").value;
                    var committente5 = document.getElementById("committente5").value;
                    var destinatari5 = document.getElementById("destinatari5").value;
                    var attivita5 = document.getElementById("attivita5").value;
                    // PUNTO F)
                    var consorzioSelezioneA = document.getElementById("consorzioSelezioneA").checked;
                    var consorzioSelezioneB = document.getElementById("consorzioSelezioneB").checked;
                    var consorzio = document.getElementById("consorzio").value;
                    if (consorzioSelezioneA == false && consorzioSelezioneB == false) {
                        output = "1";
                        msg = "<span style='color:red;'>Selezionare una delle due possibili scelte di 'F) In caso di appartenenza a consorzio (non applicabile per soggetti pubblici):'</span>";
                    }
                    if (consorzioSelezioneB == true && (consorzio == null || consorzio == "")) {
                        output = "1";
                        msg = "<span style='color:red;'>Hai selezionato di aderire al Consorzio, indicare la denominazione.</span>";
                    }
                    if (consorzioSelezioneA == true && consorzio.trim() != "") {
                        output = "1";
                        msg = "<span style='color:red;'>Hai selezionato di NON aderire ad alcun consorzio, eliminare il valore inserito in denominazione.</span>";
                    }
                    if (periodo.trim() == "" || committente.trim() == "" || destinatari.trim() == "" || attivita.trim() == "") {
                        output = "1";
                        msg = "E' obbligatorio compilare i campi della sezione <b>'Requisito 1'</b>:<span style='color:red;'><br> - Descrizione delle attività realizzate <br> - Committente <br> - Destinatari <br> - Periodo (dal gg/mm/aa al gg/mm/aa )</span>";
                    }
                    if (periodo2.trim() != "" || committente2.trim() != "" || destinatari2.trim() != "" || attivita2.trim() != "") {
                        if (periodo2.trim() == "" || committente2.trim() == "" || destinatari2.trim() == "" || attivita2.trim() == "") {
                            output = "1";
                            msg = "E' obbligatorio compilare i campi della sezione <b>'Requisito 2'</b> qualora solo uno di essi sia valorizzato:<span style='color:red;'><br> - Descrizione delle attività realizzate <br> - Committente <br> - Destinatari <br> - Periodo (dal gg/mm/aa al gg/mm/aa )</span>";
                        }
                    }
                    if (periodo3.trim() != "" || committente3.trim() != "" || destinatari3.trim() != "" || attivita3.trim() != "") {
                        if (periodo3.trim() == "" || committente3.trim() == "" || destinatari3.trim() == "" || attivita3.trim() == "") {
                            output = "1";
                            msg = "E' obbligatorio compilare i campi della sezione <b>'Requisito 3'</b> qualora solo uno di essi sia valorizzato:<span style='color:red;'><br> - Descrizione delle attività realizzate <br> - Committente <br> - Destinatari <br> - Periodo (dal gg/mm/aa al gg/mm/aa )</span>";
                        }
                    }
                    if (periodo4.trim() != "" || committente4.trim() != "" || destinatari4.trim() != "" || attivita4.trim() != "") {
                        if (periodo4.trim() == "" || committente4.trim() == "" || destinatari4.trim() == "" || attivita4.trim() == "") {
                            output = "1";
                            msg = "E' obbligatorio compilare i campi della sezione <b>'Requisito 4'</b> qualora solo uno di essi sia valorizzato:<span style='color:red;'><br> - Descrizione delle attività realizzate <br> - Committente <br> - Destinatari <br> - Periodo (dal gg/mm/aa al gg/mm/aa )</span>";
                        }
                    }
                    if (periodo5.trim() != "" || committente5.trim() != "" || destinatari5.trim() != "" || attivita5.trim() != "") {
                        if (periodo5.trim() == "" || committente5.trim() == "" || destinatari5.trim() == "" || attivita5.trim() == "") {
                            output = "1";
                            msg = "E' obbligatorio compilare i campi della sezione <b>'Requisito 5'</b> qualora solo uno di essi sia valorizzato:<span style='color:red;'><br> - Descrizione delle attività realizzate <br> - Committente <br> - Destinatari <br> - Periodo (dal gg/mm/aa al gg/mm/aa )</span>";
                        }
                    }
                    if (ch1 == false && ch2 == false && ch3 == false && ch4 == false) {
                        output = "1";
                        msg = "<span style='color:red;'>E' obbligatorio selezionare almeno una delle possibili voci del vincolo 'A)'</span>";
                    }
                    if (ch4 == true && ch5 == false && ch6 == false && ch7 == false) {
                        output = "1";
                        msg = "<span style='color:red;'>E' obbligatorio selezionare almeno una delle possibili voci del vincolo 'Soggetto privato con i seguenti requisiti:'</span>";
                    }
                    if (ch7 == true && ch4 == false) {
                        output = "1";
                        msg = "<span style='color:red;'>Hai selezionato 'Previsione nell’oggetto sociale e/o nel codice ATECO dell’attività di formazione o consulenza per la creazione, gestione, accompagnamento all’attività d’impresa'.<br>E' obbligatorio selezionare 'Soggetto privato con i seguenti requisiti:'</span>";
                    }
                    if (ch4 == true && ch5 == true && (regione.trim() == "" || iscrizione.trim() == "")) {
                        output = "1";
                        msg = "<span style='color:red;'>E' obbligatorio copilare i campi che seguono 'presso la Regione' e 'n. di iscrizione'</span>";
                    }
                    if (regione.trim() != "" && ch4 == false) {
                        output = "1";
                        msg = "<span style='color:red;'>Hai compilato il campo 'presso la Regione', devi selezionare la voce: 'Soggetto privato con i seguenti requisiti:' </span>";
                    }
                    if (regione.trim() != "" && ch5 == false) {
                        output = "1";
                        msg = "<span style='color:red;'>Hai compilato il campo 'presso la Regione', devi selezionare la voce: 'Accreditato per la formazione' </span>";
                    }
                    if (iscrizione.trim() != "" && ch4 == false) {
                        output = "1";
                        msg = "<span style='color:red;'>Hai compilato il campo 'n. di iscrizione', devi selezionare la voce: 'Soggetto privato con i seguenti requisiti:' </span>";
                    }
                    if (iscrizione.trim() != "" && ch5 == false) {
                        output = "1";
                        msg = "<span style='color:red;'>Hai compilato il campo 'n. di iscrizione', devi selezionare la voce: 'Accreditato per la formazione' </span>";
                    }
                    if (regione2.trim() != "" && ch4 == false) {
                        output = "1";
                        msg = "<span style='color:red;'>Hai compilato il campo 'presso la Regione', devi selezionare la voce: 'Soggetto privato con i seguenti requisiti:' </span>";
                    }
                    if (regione2.trim() != "" && ch6 == false) {
                        output = "1";
                        msg = "<span style='color:red;'>Hai compilato il campo 'presso la Regione', devi selezionare la voce: 'Accreditato per i servizi' </span>";
                    }
                    if (iscrizione2.trim() != "" && ch4 == false) {
                        output = "1";
                        msg = "<span style='color:red;'>Hai compilato il campo 'n. di iscrizione', devi selezionare la voce: 'Soggetto privato con i seguenti requisiti:' </span>";
                    }
                    if (iscrizione2.trim() != "" && ch6 == false) {
                        output = "1";
                        msg = "<span style='color:red;'>Hai compilato il campo 'n. di iscrizione', devi selezionare la voce: 'Accreditato per i servizi' </span>";
                    }
                    if (ch4 == true && ch6 == true && (regione2.trim() == "" || iscrizione2.trim() == "")) {
                        output = "1";
                        msg = "<span style='color:red;'>E' obbligatorio copilare i campi che seguono 'presso la Regione' e 'n. di iscrizione'</span>";
                    }
                    if (pec.trim() == "") {
                        output = "1";
                        msg = "<span style='color:red;'>E' obbligatorio indicare una email PEC valida</span>";
                    }
                    var regexp = /^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+.([a-zA-Z])+([a-zA-Z])+/;
                    if (regexp.test(pec.trim()) == false) {
                        output = "1";
                        msg = "<span style='color:red;'>E' obbligatorio indicare una email PEC valida</span>";
                    }
                    if (numeroDocenti == 0) {
                        output = "1";
                        msg = "<span style='color:red;'>E' obbligatorio indicare il numero dei fascicoli dei docenti da allegare</span>";
                    }
                    if (aule == 0) {
                        ctrlAula1 = false;
                        ctrlAula2 = false;
                        ctrlAula3 = false;
                        ctrlAula4 = false;
                        ctrlAula5 = false;
                        output = "1";
                        msg = "<span style='color:red;'>E' obbligatorio indicare il numero di aule</span>";
                    }
                    if (aule == 1) {
                        ctrlAula1 = true;
                        ctrlAula2 = false;
                        ctrlAula3 = false;
                        ctrlAula4 = false;
                        ctrlAula5 = false;
                    }
                    if (aule == 2) {
                        ctrlAula1 = true;
                        ctrlAula2 = true;
                        ctrlAula3 = false;
                        ctrlAula4 = false;
                        ctrlAula5 = false;
                    }
                    if (aule == 3) {
                        ctrlAula1 = true;
                        ctrlAula2 = true;
                        ctrlAula3 = true;
                        ctrlAula4 = false;
                        ctrlAula5 = false;
                    }
                    if (aule == 4) {
                        ctrlAula1 = true;
                        ctrlAula2 = true;
                        ctrlAula3 = true;
                        ctrlAula4 = true;
                        ctrlAula5 = false;
                    }
                    if (aule == 5) {
                        ctrlAula1 = true;
                        ctrlAula2 = true;
                        ctrlAula3 = true;
                        ctrlAula4 = true;
                        ctrlAula5 = true;
                    }
                    if (ctrlAula1 == true) {
                        if (citta == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 1. E' obbligatorio indicare la 'Città'.</span>";
                        }
                        if (provincia == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 1. E' obbligatorio indicare la 'Provincia'.</span>";
                        }
                        if (indirizzo == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 1. E' obbligatorio indicare l''Indirizzo'.</span>";
                        }
                        if (estremi == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 1. E' obbligatorio indicare il 'Titolo di disponibilità Estremi -Mq aula'.</span>";
                        }
                        if (accreditamento == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 1. E' obbligatorio indicare l''Accreditamento regionale (SI/NO)'.</span>";
                        }
                        if (responsabile == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 1. E' obbligatorio indicare il 'Nominativo responsabile'.</span>";
                        }
                        if (responsabileAmm == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 1. E' obbligatorio indicare il 'Nominativo referente Amministrativo'.</span>";
                        }
                        if (recapiti == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 1. E' obbligatorio indicare i 'Recapiti'.</span>";
                        }
                    }
                    if (ctrlAula2 == true) {
                        if (citta2 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 2. E' obbligatorio indicare la 'Città'.</span>";
                        }
                        if (provincia2 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 2. E' obbligatorio indicare la 'Provincia'.</span>";
                        }
                        if (indirizzo2 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 2. E' obbligatorio indicare l''Indirizzo'.</span>";
                        }
                        if (estremi2 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 2. E' obbligatorio indicare il 'Titolo di disponibilità Estremi -Mq aula'.</span>";
                        }
                        if (accreditamento2 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 2. E' obbligatorio indicare l''Accreditamento regionale (SI/NO)'.</span>";
                        }
                        if (responsabile2 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 2. E' obbligatorio indicare il 'Nominativo responsabile'.</span>";
                        }
                        if (responsabileAmm2 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 2. E' obbligatorio indicare il 'Nominativo referente Amministrativo'.</span>";
                        }
                        if (recapiti2 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 2. E' obbligatorio indicare i 'Recapiti'.</span>";
                        }
                    }
                    if (ctrlAula3 == true) {
                        if (citta3 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 3. E' obbligatorio indicare la 'Città'.</span>";
                        }
                        if (provincia3 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 3. E' obbligatorio indicare la 'Provincia'.</span>";
                        }
                        if (indirizzo3 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 3. E' obbligatorio indicare l''Indirizzo'.</span>";
                        }
                        if (estremi3 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 3. E' obbligatorio indicare il 'Titolo di disponibilità Estremi -Mq aula'.</span>";
                        }
                        if (accreditamento3 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 3. E' obbligatorio indicare l''Accreditamento regionale (SI/NO)'.</span>";
                        }
                        if (responsabile3 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 3. E' obbligatorio indicare il 'Nominativo responsabile'.</span>";
                        }
                        if (responsabileAmm3 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 3. E' obbligatorio indicare il 'Nominativo referente Amministrativo'.</span>";
                        }
                        if (recapiti3 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 3. E' obbligatorio indicare i 'Recapiti'.</span>";
                        }
                    }
                    if (ctrlAula4 == true) {
                        if (citta4 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 4. E' obbligatorio indicare la 'Città'.</span>";
                        }
                        if (provincia4 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 4. E' obbligatorio indicare la 'Provincia'.</span>";
                        }
                        if (indirizzo4 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 4. E' obbligatorio indicare l''Indirizzo'.</span>";
                        }
                        if (estremi4 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 4. E' obbligatorio indicare il 'Titolo di disponibilità Estremi -Mq aula'.</span>";
                        }
                        if (accreditamento4 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 4. E' obbligatorio indicare l''Accreditamento regionale (SI/NO)'.</span>";
                        }
                        if (responsabile4 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 4. E' obbligatorio indicare il 'Nominativo responsabile'.</span>";
                        }
                        if (responsabileAmm4 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 4. E' obbligatorio indicare il 'Nominativo referente Amministrativo'.</span>";
                        }
                        if (recapiti4 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 4. E' obbligatorio indicare i 'Recapiti'.</span>";
                        }
                    }
                    if (ctrlAula5 == true) {
                        if (citta5 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 5. E' obbligatorio indicare la 'Città'.</span>";
                        }
                        if (provincia5 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 5. E' obbligatorio indicare la 'Provincia'.</span>";
                        }
                        if (indirizzo5 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 5. E' obbligatorio indicare l''Indirizzo'.</span>";
                        }
                        if (estremi5 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 5. E' obbligatorio indicare il 'Titolo di disponibilità Estremi -Mq aula'.</span>";
                        }
                        if (accreditamento5 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 5. E' obbligatorio indicare l''Accreditamento regionale (SI/NO)'.</span>";
                        }
                        if (responsabile5 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 5. E' obbligatorio indicare il 'Nominativo responsabile'.</span>";
                        }
                        if (responsabileAmm5 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 5. E' obbligatorio indicare il 'Nominativo referente Amministrativo'.</span>";
                        }
                        if (recapiti5 == "") {
                            output = "1";
                            msg = "<span style='color:red;'>Sezione Classe 5. E' obbligatorio indicare i 'Recapiti'.</span>";
                        }
                    }
                } else if (tipologia == "B") {
                    // DOCENTE 1
                    var docNome = document.getElementById("docNome").value;
                    var docCognome = document.getElementById("docCognome").value;
                    var inquadramento = document.getElementById("inquadramento").value;
                    var fascia = document.getElementById("fascia").value;
                    // DOCENTE 2
                    var docNome2 = document.getElementById("docNome2").value;
                    var docCognome2 = document.getElementById("docCognome2").value;
                    var inquadramento2 = document.getElementById("inquadramento2").value;
                    var fascia2 = document.getElementById("fascia2").value;
                    // DOCENTE 3
                    var docNome3 = document.getElementById("docNome3").value;
                    var docCognome3 = document.getElementById("docCognome3").value;
                    var inquadramento3 = document.getElementById("inquadramento3").value;
                    var fascia3 = document.getElementById("fascia3").value;
                    // DOCENTE 4
                    var docNome4 = document.getElementById("docNome4").value;
                    var docCognome4 = document.getElementById("docCognome4").value;
                    var inquadramento4 = document.getElementById("inquadramento4").value;
                    var fascia4 = document.getElementById("fascia4").value;
                    // DOCENTE 5
                    var docNome5 = document.getElementById("docNome5").value;
                    var docCognome5 = document.getElementById("docCognome5").value;
                    var inquadramento5 = document.getElementById("inquadramento5").value;
                    var fascia5 = document.getElementById("fascia5").value;
                    if (docNome.trim() == "" || docCognome.trim() == "" || inquadramento == "" || fascia == "") {
                        output = "1";
                        msg = "E' obbligatorio compilare i campi della sezione <b>'Docente 1'</b> in quanto almeno uno di essi deve essere censito:<span style='color:red;'><br> - Nome <br> - Cognome <br> - Fascia di appartenenza  <br> - Inquadramento</span>";
                    }
                    if (docNome2.trim() != "" || docCognome2.trim() != "" || inquadramento2 != "" || fascia2 != "") {
                        if (docNome2.trim() == "" || docCognome2.trim() == "" || inquadramento2 == "" || fascia2 == "") {
                            output = "1";
                            msg = "E' obbligatorio compilare i campi della sezione <b>'Docente 2'</b> in quanto uno di essi è valorizzato:<span style='color:red;'><br> - Nome <br> - Cognome <br> - Fascia di appartenenza  <br> - Inquadramento</span>";
                        }
                    }
                    if (docNome3.trim() != "" || docCognome3.trim() != "" || inquadramento3 != "" || fascia3 != "") {
                        if (docNome3.trim() == "" || docCognome3.trim() == "" || inquadramento3 == "" || fascia3 == "") {
                            output = "1";
                            msg = "E' obbligatorio compilare i campi della sezione <b>'Docente 3'</b> in quanto uno di essi è valorizzato:<span style='color:red;'><br> - Nome <br> - Cognome <br> - Fascia di appartenenza  <br> - Inquadramento</span>";
                        }
                    }
                    if (docNome4.trim() != "" || docCognome4.trim() != "" || inquadramento4 != "" || fascia4 != "") {
                        if (docNome4.trim() == "" || docCognome4.trim() == "" || inquadramento4 == "" || fascia4 == "") {
                            output = "1";
                            msg = "E' obbligatorio compilare i campi della sezione <b>'Docente 4'</b> in quanto uno di essi è valorizzato:<span style='color:red;'><br> - Nome <br> - Cognome <br> - Fascia di appartenenza  <br> - Inquadramento</span>";
                        }
                    }
                    if (docNome5.trim() != "" || docCognome5.trim() != "" || inquadramento5 != "" || fascia5 != "") {
                        if (docNome5.trim() == "" || docCognome5.trim() == "" || inquadramento5 == "" || fascia5 == "") {
                            output = "1";
                            msg = "E' obbligatorio compilare i campi della sezione <b>'Docente 5'</b> in quanto uno di essi è valorizzato:<span style='color:red;'><br> - Nome <br> - Cognome <br> - Fascia di appartenenza  <br> - Inquadramento</span>";
                        }
                    }
                } else if (tipologia == "C") {

                }
                if (output !== "0") {
                    document.getElementById("msgcompil").innerHTML = msg;
                    document.getElementById("confirm").className = document.getElementById("confirm").className + " in";
                    document.getElementById("confirm").style.display = "block";
                    return false;
                }
                if (tipologia === "A") {
                    document.getElementById("formModelloA").submit();
                } else {
                    document.getElementById("formModelloB").submit();
                }
            }

            function mostraAuleFx() {
                var x = '<%=allegato_A_B%>';
                if (x == "A") {
                    mostraaule('aule');
                } else {
                    mostraDocenti('1');
                }
            }

            function scaricaFile(servURL, params) {
                var method = "post"; // il metodo POST è usato di default
                var form = document.createElement("form");
                form.setAttribute("method", method);
                form.setAttribute("action", servURL);
                for (var key in params) {
                    var hiddenField = document.createElement("input");
                    hiddenField.setAttribute("type", "hidden");
                    hiddenField.setAttribute("name", key);
                    hiddenField.setAttribute("value", params[key]);
                    form.appendChild(hiddenField);
                }
                document.body.appendChild(form);
                form.submit();
            }
        </script>
        <script src="assets/global/plugins/jquery.min.js" type="text/javascript"></script>
    </head>

    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white page-sidebar-closed" onload="mostraAuleFx();">


        <%@ include file="menu/header.jsp"%>
        <!-- BEGIN HEADER -->
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->
            <!-- END MENU -->
            <%@ include file="menu/menuUs1.jsp"%>

            <!-- BEGIN CONTENT -->
            <div class="page-content-wrapper">
                <!-- BEGIN CONTENT BODY -->
                <div class="page-content">

                    <div id="confirm" class="modal fade" tabindex="-1" data-backdrop="confirm" data-keyboard="false">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title">Verifica Compilazione</h4>
                                </div>
                                <div class="modal-body">
                                    <p id="primariga">
                                        Verificare che tutti i campi siano compilati correttamente.
                                    </p>
                                    <p id="msgcompil">

                                    </p>
                                </div>
                                <div class="modal-footer" id="groupbtn2">
                                    <button type="button" data-dismiss="modal" class="btn red" onclick="return dismiss('confirm');">CHIUDI</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div id="confirm2" class="modal fade" tabindex="-1" data-backdrop="confirm2" data-keyboard="false">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title">Verifica Compilazione</h4>
                                </div>
                                <div class="modal-body">
                                    <p>
                                        Sei sicuro di voler procedere nella compilazione della domanda?
                                    </p>
                                    <p id="msgcompil2">

                                    </p>
                                </div>
                                <div class="modal-footer" id="groupbtn2">
                                    <button type="button" class="btn green" onclick="return submitfor();">SI</button>
                                    <button type="button" data-dismiss="modal" class="btn red" onclick="return dismiss('confirm2');">NO</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- BEGIN PAGE HEADER-->
                    <!-- BEGIN THEME PANEL -->
                    <!--    VUOTO RAF  -->
                    <!-- END THEME PANEL -->
                    <!-- BEGIN PAGE BAR -->
                    <!-- END PAGE BAR -->
                    <!-- BEGIN PAGE TITLE-->
                    <div class="row">
                        <div class="col-md-9">
                            <h3 class="page-title">Homepage</h3>
                        </div>
                        <div class="col-md-3" style="text-align: right;">
                            <img src="assets/seta/img/logo_blue_1.png" alt="logo" height="70px"/>
                        </div>
                    </div>
                    <%
                        String es = request.getParameter("esito");
                        String msg = "";
                        String inte = "";
                        if (es != null) {
                            if (es.equals("ok")) {
                                inte = "<i class='fa fa-exclamation-triangle font-green'></i> Operazione Completata";
                                msg = "Documenti docente caricati con successo.";
                            } else if (es.equals("ok1")) {
                                inte = "<i class='fa fa-exclamation-triangle font-green'></i> Operazione Completata";
                                msg = "Documenti docente eliminato con successo.";
                            } else if (es.equals("ok2")) {
                                inte = "<i class='fa fa-exclamation-triangle font-green'></i> Operazione Completata";
                                msg = "Sezione compilata con successo.";
                            } else if (es.equals("ok3")) {
                                inte = "<i class='fa fa-exclamation-triangle font-green'></i> Operazione Completata";
                                msg = "Eliminazione dati allegato B1 completata con successo.";
                            } else if(es.equals("okb1")) {
                                inte = "<i class='fa fa-exclamation-triangle font-green'></i> Operazione Completata";
                                msg = "Puoi ora tornare alla Home per caricare gli eventuali documenti rimanenti.";
                            } else if (es.equals("ko")) {
                                inte = "<i class='fa fa-exclamation-triangle font-red'></i> Errore inserimento";
                                msg = "Impossibile salvare i file, riprovare.";
                            } else if (es.equals("ko1")) {
                                inte = "<i class='fa fa-exclamation-triangle font-red'></i> Errore durante la fase di eliminazione";
                                msg = "Impossibile eliminare il fascicolo del docente, riprovare.";
                            } else if (es.equals("ko2")) {
                                inte = "<i class='fa fa-exclamation-triangle font-red'></i> Errore durante la fase di compilazione";
                                msg = "Impossibile completare tale operazione, riprovare.";
                            } else if (es.equals("ko3")) {
                                inte = "<i class='fa fa-exclamation-triangle font-red'></i> Errore durante la fase di cancellazione";
                                msg = "Impossibile completare tale operazione, riprovare.";
                            } else if (es.equals("ko4")) {
                                inte = "<i class='fa fa-exclamation-triangle font-red'></i> Impossibile caricare i files. Verificare che l'estensione sia corretta";
                                msg = "Impossibile completare tale operazione, riprovare.";
                            }
                    %>
                    <br>
                    <div class="modal fade" role="dialog" >

                        <button type="button" class="btn btn-info btn-lg" data-toggle="modal" id="myModal4but" data-target="#myModal4"></button>
                        <script>
                            $(document).ready(function () {
                                $('#myModal4but').click();
                            });
                        </script>
                    </div>
                    <div id="myModal4" class="modal fade" role="dialog">
                        <div class="modal-dialog modal-lg">
                            <!-- Modal content-->
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title"><%=inte%></h4>
                                </div>
                                <div class="modal-body">
                                    <%=msg%>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" data-dismiss="modal" class="btn blue" onclick="return dismiss('myModal4');">OK</button>
                                </div>
                            </div>

                        </div>
                    </div>

                    <%}%>
                    <div class="row">
                        <div class="col-md-12"> 
                            <% if (allegato_A_B.equals("A") && !ActionB.esisteAllegatoA(username)) {%>
                            <div class="portlet box yellow">
                                <div class="portlet-title">
                                    <div class="caption"><i class="fa fa-pencil-square-o"></i>Compilazione Dichiarazioni</div>
                                    <div class="tools">
                                        <a href="javascript:;" class="collapse"></a>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <form name="formregist" role="form" action="Operazioni?action=allegato_A" method="post"  class="form-horizontal" id="formModelloA">
                                        <input type="hidden" name="username" value="<%=username%>"/>
                                        <!--Operazioni?action=complinea1-->
                                        <div class="form-body">
                                            <div class="form-group">
                                                <div class="col-md-12">
                                                    ai sensi degli articoli 46 e 47 del DPR 28.12.2000 n. 445, consapevole delle sanzioni penali previste dall’articolo 76 del medesimo D.P.R., per le ipotesi di falsità in atti e dichiarazioni mendaci ivi indicate:<br>
                                                    <br>
                                                    <ul>
                                                        <li>
                                                            A) di essere (selezionare le caselle di interesse):
                                                            <ul>
                                                                <li><input type="checkbox" name="ch1">&nbsp;Ente/istituzione pubblica</li>
                                                                <li><input type="checkbox" name="ch2">&nbsp;Associazione datoriale</li>
                                                                <li><input type="checkbox" name="ch3">&nbsp;Ordine professionale</li>
                                                                <li>
                                                                    <input type="checkbox" name="ch4">&nbsp;Soggetto privato con i seguenti requisiti:<br>
                                                                    <ul>
                                                                        <li><input type="checkbox" name="ch5"> Accreditato per la formazione professionale presso la Regione <input class="uppercase"  id="regione" name="regione"  type="text" style="width: 200px"/> n. di iscrizione <input class="uppercase"  id="iscrizione" name="iscrizione" type="text" style="width: 200px"/>;</li><br>
                                                                        <li><input type="checkbox" name="ch6"> Accreditato per i servizi per il lavoro presso la Regione <input class="uppercase" id="regione2" name="regione2" type="text" style="width: 200px"/> n. di iscrizione <input class="uppercase"  id="iscrizione2" name="iscrizione2" type="text" style="width: 200px"/>;</li><br>
                                                                        <li><input type="checkbox" name="ch7">Previsione nell’oggetto sociale e/o nel codice ATECO dell’attività di formazione o consulenza per la creazione, gestione, accompagnamento all’attività d’impresa;</li>
                                                                    </ul>
                                                                </li>
                                                            </ul>
                                                        </li>
                                                        <li>B) di manifestare il proprio interesse ad operare nelle area territoriale identificata con la Regione Calabria;</li>
                                                        <li>
                                                            C) di disporre, secondo quanto riportato nella sottostante tabella/e di n.&nbsp;
                                                            <select id="aule" name="aule" onchange="return mostraaule(this.id);">
                                                                <option value="1">1</option>
                                                                <option value="2">2</option>
                                                                <option value="3">3</option>
                                                                <option value="4">4</option>
                                                                <option value="5">5</option>
                                                            </select>
                                                            <script type="text/javascript">
                                                                function mostraaule(ids) {
                                                                    var sel = document.getElementById(ids).value;

                                                                    for (var x = 1; x < 6; x++) {
                                                                        if (x <= sel) {
                                                                            $(".classe" + x).toggle(true);
                                                                        } else {
                                                                            $(".classe" + x).toggle(false);
                                                                            document.getElementById("citta" + x).value = '';
                                                                            document.getElementById("provincia" + x).value = '';
                                                                            document.getElementById("indirizzo" + x).value = '';
                                                                            document.getElementById("estremi" + x).value = '';
                                                                            document.getElementById("accreditamento" + x).value = '';
                                                                            document.getElementById("responsabile" + x).value = '';
                                                                            document.getElementById("responsabileAmm" + x).value = '';
                                                                            document.getElementById("recapiti" + x).value = '';
                                                                        }
                                                                    }
                                                                }
                                                            </script>
                                                            &nbsp;aula/e da destinare alle attività formativa oggetto di affidamento dell’Avviso in parola
                                                        </li>
                                                    </ul>
                                                    <br>
                                                </div>
                                                <div class="clearfix"></div>
                                                <hr>
                                                <table style="width: 100%;">
                                                    <thead>
                                                        <tr>
                                                            <th colspan="2" style="text-align: center; background-color: silver;"><h4><b>Compilazione aule</b> 
                                                                </h4>
                                                            </th>
                                                        </tr>
                                                    </thead>
                                                </table>
                                                <hr>
                                                <table style="width: 100%;">
                                                    <tr style="border-bottom: 3px;" class="classe1">
                                                        <th colspan="4" style="text-align: center;" ><span class="help-block bordered">
                                                                Aula 1 
                                                            </span> <hr>
                                                        </th>
                                                    </tr>
                                                    <tr class="classe1">
                                                        <td>
                                                            <div class="row">
                                                                <div class="col-md-12">
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Città
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="citta" placeholder="..." id="citta">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Provincia
                                                                                </span>
                                                                                <select id="provincia" name="provincia" class="form-control select2" data-placeholder="...">
                                                                                    <option value="">...</option>
                                                                                    <%for (int p = 0; p < prov.size(); p++) {%>
                                                                                    <option value="<%=prov.get(p)[0]%>"><%=prov.get(p)[1]%></option>
                                                                                    <%}%>
                                                                                </select>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Indirizzo
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="indirizzo" placeholder="..." id="indirizzo">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Titolo di disponibilità Estremi  -Mq aula 
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="estremi" placeholder="..." id="estremi">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Accreditamento regionale (SI/NO)
                                                                                </span>
                                                                                <select id="accreditamento" name="accreditamento" class="form-control select2" data-placeholder="...">
                                                                                    <option value="">...</option>
                                                                                    <option value="SI">SI</option>
                                                                                    <option value="NO">NO</option>
                                                                                </select>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Nominativo responsabile
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="responsabile" placeholder="..." id="responsabile">
                                                                            </div>
                                                                        </div>
                                                                    </div>

                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Nominativo referente Amministrativo
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="responsabileAmm" placeholder="..." id="responsabileAmm">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Recapiti
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="recapiti" placeholder="..." id="recapiti">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr style="border-bottom: 3px;" class="classe2">
                                                        <th colspan="4" style="text-align: center;" ><span class="help-block bordered">
                                                                Aula 2 
                                                            </span> <hr>
                                                        </th>
                                                    </tr>
                                                    <tr class="classe2">
                                                        <td>
                                                            <div class="row">
                                                                <div class="col-md-12">
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Città
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="citta2" placeholder="..." id="citta2">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Provincia
                                                                                </span>
                                                                                <select id="provincia2" name="provincia2" class="form-control select2" data-placeholder="...">
                                                                                    <option value="">...</option>
                                                                                    <%for (int p = 0; p < prov.size(); p++) {%>
                                                                                    <option value="<%=prov.get(p)[0]%>"><%=prov.get(p)[1]%></option>
                                                                                    <%}%>
                                                                                </select>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Indirizzo
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="indirizzo2" placeholder="..." id="indirizzo2">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Titolo di disponibilità Estremi  -Mq aula 
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="estremi2" placeholder="..." id="estremi2">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Accreditamento regionale (SI/NO)
                                                                                </span>
                                                                                <select id="accreditamento2" name="accreditamento2" class="form-control select2" data-placeholder="...">
                                                                                    <option value="">...</option>
                                                                                    <option value="SI">SI</option>
                                                                                    <option value="NO">NO</option>
                                                                                </select>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Nominativo responsabile
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="responsabile2" placeholder="..." id="responsabile2">
                                                                            </div>
                                                                        </div>
                                                                    </div>

                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Nominativo referente Amministrativo
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="responsabileAmm2" placeholder="..." id="responsabileAmm2">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Recapiti
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="recapiti2" placeholder="..." id="recapiti2">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr style="border-bottom: 3px;" class="classe3">
                                                        <th colspan="4" style="text-align: center;" ><span class="help-block bordered">
                                                                Aula 3 
                                                            </span> <hr>
                                                        </th>
                                                    </tr>
                                                    <tr class="classe3">
                                                        <td>
                                                            <div class="row">
                                                                <div class="col-md-12">
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Città
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="citta3" placeholder="..." id="citta3">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Provincia
                                                                                </span>
                                                                                <select id="provincia3" name="provincia3" class="form-control select2" data-placeholder="...">
                                                                                    <option value="">...</option>
                                                                                    <%for (int p = 0; p < prov.size(); p++) {%>
                                                                                    <option value="<%=prov.get(p)[0]%>"><%=prov.get(p)[1]%></option>
                                                                                    <%}%>
                                                                                </select>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Indirizzo
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="indirizzo3" placeholder="..." id="indirizzo3">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Titolo di disponibilità Estremi  -Mq aula 
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="estremi3" placeholder="..." id="estremi3">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Accreditamento regionale (SI/NO)
                                                                                </span>
                                                                                <select id="accreditamento3" name="accreditamento3" class="form-control select2 uppercase" data-placeholder="...">
                                                                                    <option value="">...</option>
                                                                                    <option value="SI">SI</option>
                                                                                    <option value="NO">NO</option>
                                                                                </select>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Nominativo responsabile
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="responsabile3" placeholder="..." id="responsabile3">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Nominativo referente Amministrativo
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="responsabileAmm3" placeholder="..." id="responsabileAmm3">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Recapiti
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="recapiti3" placeholder="..." id="recapiti3">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr style="border-bottom: 3px;" class="classe4">
                                                        <th colspan="4" style="text-align: center;" ><span class="help-block bordered">
                                                                Aula 4 
                                                            </span> <hr>
                                                        </th>
                                                    </tr>
                                                    <tr class="classe4">
                                                        <td>
                                                            <div class="row">
                                                                <div class="col-md-12">
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Città
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="citta4" placeholder="..." id="citta4">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Provincia
                                                                                </span>
                                                                                <select id="provincia4" name="provincia4" class="form-control select2" data-placeholder="...">
                                                                                    <option value="">...</option>
                                                                                    <%for (int p = 0; p < prov.size(); p++) {%>
                                                                                    <option value="<%=prov.get(p)[0]%>"><%=prov.get(p)[1]%></option>
                                                                                    <%}%>
                                                                                </select>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Indirizzo
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="indirizzo4" placeholder="..." id="indirizzo4">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Titolo di disponibilità Estremi  -Mq aula 
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="estremi4" placeholder="..." id="estremi4">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Accreditamento regionale (SI/NO)
                                                                                </span>
                                                                                <select id="accreditamento4" name="accreditamento4" class="form-control select2" data-placeholder="...">
                                                                                    <option value="">...</option>
                                                                                    <option value="SI">SI</option>
                                                                                    <option value="NO">NO</option>
                                                                                </select>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Nominativo responsabile
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="responsabile4" placeholder="..." id="responsabile4">
                                                                            </div>
                                                                        </div>
                                                                    </div>

                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Nominativo referente Amministrativo
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="responsabileAmm4" placeholder="..." id="responsabileAmm4">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Recapiti
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="recapiti4" placeholder="..." id="recapiti4">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr style="border-bottom: 3px;" class="classe5">
                                                        <th colspan="4" style="text-align: center;" ><span class="help-block bordered">
                                                                Aula 5
                                                            </span> <hr>
                                                        </th>
                                                    </tr>
                                                    <tr class="classe5">
                                                        <td>
                                                            <div class="row">
                                                                <div class="col-md-12">
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Città
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="citta5" placeholder="..." id="citta5">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Provincia
                                                                                </span>
                                                                                <select id="provincia5" name="provincia5" class="form-control select2" data-placeholder="...">
                                                                                    <option value="">...</option>
                                                                                    <%for (int p = 0; p < prov.size(); p++) {%>
                                                                                    <option value="<%=prov.get(p)[0]%>"><%=prov.get(p)[1]%></option>
                                                                                    <%}%>
                                                                                </select>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Indirizzo
                                                                                </span>
                                                                                <input type="text" class="form-control" name="indirizzo5" placeholder="..." id="indirizzo5">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Titolo di disponibilità Estremi  -Mq aula 
                                                                                </span>
                                                                                <input type="text" class="form-control" name="estremi5" placeholder="..." id="estremi5">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Accreditamento regionale (SI/NO)
                                                                                </span>
                                                                                <select id="accreditamento5" name="accreditamento5" class="form-control select2" data-placeholder="...">
                                                                                    <option value="">...</option>
                                                                                    <option value="SI">SI</option>
                                                                                    <option value="NO">NO</option>
                                                                                </select>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Nominativo responsabile
                                                                                </span>
                                                                                <input type="text" class="form-control" name="responsabile5" placeholder="..." id="responsabile5">
                                                                            </div>
                                                                        </div>
                                                                    </div>

                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Nominativo referente Amministrativo
                                                                                </span>
                                                                                <input type="text" class="form-control" name="responsabileAmm5" placeholder="..." id="responsabileAmm5">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Recapiti
                                                                                </span>
                                                                                <input type="text" class="form-control" name="recapiti5" placeholder="..." id="recapiti5">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </table>
                                                <div class="clearfix"></div>
                                                <hr>
                                                <table style="width: 100%;">
                                                    <thead>
                                                        <tr>
                                                            <th colspan="2" style="text-align: center; background-color: #1BBC9B"><h4><b>Compilazione requisiti d'esperienza</b> 
                                                                </h4>
                                                            </th>
                                                        </tr>
                                                    </thead>
                                                </table>
                                                <hr>
                                                <table style="width: 100%;">
                                                    <tr style="border-bottom: 3px;">
                                                        <th colspan="4" style="text-align: center;" ><span class="help-block bordered">
                                                                Requisito 1 
                                                            </span> <hr>
                                                        </th>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <div class="row">
                                                                <div class="col-md-12">
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Descrizione delle attività realizzate
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="attivita" placeholder="..." id="attivita">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Destinatari
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="destinatari" placeholder="..." id="destinatari">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Fonte di finanziamento (se pertinente)
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="finanziamento" placeholder="..." id="finanziamento">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Committente
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="committente" placeholder="..." id="committente">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Periodo (dal gg/mm/aa al  gg/mm/aa )
                                                                                </span>
                                                                                <input type="text" class="form-control periodo" name="periodo" placeholder="..." id="periodo" readonly>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr style="border-bottom: 3px;">
                                                        <th colspan="4" style="text-align: center;" ><span class="help-block bordered">
                                                                Requisito 2
                                                            </span> <hr>
                                                        </th>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <div class="row">
                                                                <div class="col-md-12">
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Descrizione delle attività realizzate
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="attivita2" placeholder="..." id="attivita2">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Destinatari
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="destinatari2" placeholder="..." id="destinatari2">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Fonte di finanziamento (se pertinente)
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="finanziamento2" placeholder="..." id="finanziamento2">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Committente
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="committente2" placeholder="..." id="committente2">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Periodo (dal gg/mm/aa al  gg/mm/aa )
                                                                                </span>
                                                                                <input type="text" class="form-control periodo" name="periodo2" placeholder="..." id="periodo2" readonly>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr style="border-bottom: 3px;" >
                                                        <th colspan="4" style="text-align: center;" ><span class="help-block bordered">
                                                                Requisito 3
                                                            </span> <hr>
                                                        </th>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <div class="row">
                                                                <div class="col-md-12">
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Descrizione delle attività realizzate
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="attivita3" placeholder="..." id="attivita3">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Destinatari
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="destinatari3" placeholder="..." id="destinatari3">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Fonte di finanziamento (se pertinente)
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="finanziamento3" placeholder="..." id="finanziamento3">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Committente
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="committente3" placeholder="..." id="committente3">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Periodo (dal gg/mm/aa al  gg/mm/aa )
                                                                                </span>
                                                                                <input type="text" class="form-control periodo" name="periodo3" placeholder="..." id="periodo3" readonly>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr style="border-bottom: 3px;" >
                                                        <th colspan="4" style="text-align: center;" ><span class="help-block bordered">
                                                                Requisito 4
                                                            </span> <hr>
                                                        </th>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <div class="row">
                                                                <div class="col-md-12">
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Descrizione delle attività realizzate
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="attivita4" placeholder="..." id="attivita4">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Destinatari
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="destinatari4" placeholder="..." id="destinatari4">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Fonte di finanziamento (se pertinente)
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="finanziamento4" placeholder="..." id="finanziamento4">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Committente
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="committente4" placeholder="..." id="committente4">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Periodo (dal gg/mm/aa al  gg/mm/aa )
                                                                                </span>
                                                                                <input type="text" class="form-control periodo" name="periodo4" placeholder="..." id="periodo4" readonly>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr style="border-bottom: 3px;" >
                                                        <th colspan="4" style="text-align: center;" ><span class="help-block bordered">
                                                                Requisito 5
                                                            </span> <hr>
                                                        </th>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <div class="row">
                                                                <div class="col-md-12">
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Descrizione delle attività realizzate
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="attivita5" placeholder="..." id="attivita5">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Destinatari
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="destinatari5" placeholder="..." id="destinatari5">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Fonte di finanziamento (se pertinente)
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="finanziamento5" placeholder="..." id="finanziamento5">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Committente
                                                                                </span>
                                                                                <input type="text" class="form-control uppercase" name="committente5" placeholder="..." id="committente5">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label col-md-1"></label>
                                                                            <div class="col-md-11">
                                                                                <span class="help-block">
                                                                                    Periodo (dal gg/mm/aa al  gg/mm/aa )
                                                                                </span>
                                                                                <input type="text" class="form-control periodo" name="periodo5" placeholder="..." id="periodo5" readonly>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </table>
                                                <div class="clearfix"></div>
                                                <hr>
                                                <table style="width: 100%;">
                                                    <thead>
                                                        <tr>
                                                            <th colspan="2" style="text-align: center; background-color: #1BBC9B"><h4><b>Compilazione requisiti d'esperienza</b> 
                                                                </h4>
                                                            </th>
                                                        </tr>
                                                    </thead>
                                                </table>

                                                <div class="col-md-12">
                                                    <br>
                                                    F) In caso di appartenenza a consorzio (non applicabile per soggetti pubblici): <br>
                                                    <br>
                                                    <ul>
                                                        <li><input type="radio" id="consorzioSelezioneA" name="consorzioSelezione" value="A">&nbsp;<label>di non aderire ad alcun consorzio;</label><br></li>
                                                        <li><input type="radio" id="consorzioSelezioneB" name="consorzioSelezione" value="B">&nbsp;<label>di aderire al Consorzio (indicare la denominazione e tipologia)&nbsp;<input class="uppercase" id="consorzio" name="consorzio" type="text" style="width: 200px"/>;</label><br></li>
                                                    </ul>
                                                    I) che la PEC ove potranno essere inviate le comunicazioni relative al presente procedimento, anche ai fini del controllo sui requisiti richiesti, è <input class="uppercase" id="pec" name="pec" type="text" style="width: 200px"/>;<br>
                                                    Saranno allegati Numero: 
                                                    <select id="numeroDocenti" name="numeroDocenti">
                                                        <option value="0">0</option>
                                                        <option value="1">1</option>
                                                        <option value="2">2</option>
                                                        <option value="3">3</option>
                                                        <option value="4">4</option>
                                                        <option value="5">5</option>
                                                    </select> Fascicolo/i Docente, articolati per ciascun docente con Curriculum Vitae, Modello B1, firmati dal docente e Documento di identità del docente. 
                                                </div>
                                                <div class="col-md-12">
                                                    <hr>
                                                </div>

                                                <div class="col-md-6">
                                                    <a class="btn red" href="bando_index.jsp"><i class="fa fa-arrow-left"></i> Indietro</a>

                                                </div>
                                            </div>
                                        </div>
                                    </form>                           
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12"> 
                                    <div class="portlet light">
                                        <div class="portlet-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <center><button type="button" class="btn btn-lg blue btn-block"   onclick="return controllaReg1();"><i class="fa fa-save"  ></i> Salva dati</button></center>
                                                </div>                            
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <%} else if (allegato_A_B.equals("B") && !ActionB.esisteAllegatoB(username)) {%>
                            <div class="portlet-body">
                                <form name="formregist" role="form" action="Operazioni?action=allegato_B" method="post"  class="form-horizontal" id="formModelloB">
                                    <input type="hidden" name="username" value="<%=username%>"/>
                                    <table style="width: 100%;">
                                        <thead>
                                            <tr>
                                                <th colspan="2" style="text-align: center; background-color: silver;"><h4><i class="fa fa-pencil-square-o"></i>Elenco corpo docente</h4>
                                                </th>
                                            </tr>
                                        </thead>
                                    </table>
                                    <hr>
                                    <table style="width: 100%;">
                                        <tr style="border-bottom: 3px;" class="classe1">
                                            <th colspan="4" style="text-align: center;" ><span class="help-block bordered">
                                                    Docente 1 
                                                </span> <hr>
                                            </th>
                                        </tr>
                                        <tr class="classe1">
                                            <td>
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label class="control-label col-md-1"></label>
                                                                <div class="col-md-11">
                                                                    <span class="help-block">
                                                                        Nome
                                                                    </span>
                                                                    <input type="text" class="form-control" name="docNome" placeholder="..." id="docNome">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label class="control-label col-md-1"></label>
                                                                <div class="col-md-11">
                                                                    <span class="help-block">
                                                                        Cognome
                                                                    </span>
                                                                    <input type="text" class="form-control" name="docCognome" placeholder="..." id="docCognome">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label class="control-label col-md-1"></label>
                                                                <div class="col-md-11">
                                                                    <span class="help-block">
                                                                        Fascia di appartenenza
                                                                    </span>
                                                                    <select class="form-control select2" name="fascia" id="fascia">
                                                                        <option value=""></option>
                                                                        <option value="A">A</option>
                                                                        <option value="B">B</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label class="control-label col-md-1"></label>
                                                                <div class="col-md-11">
                                                                    <span class="help-block">
                                                                        Inquadramento
                                                                    </span>
                                                                    <select class="form-control select2" name="inquadramento" id="inquadramento">
                                                                        <option value=""></option>
                                                                        <option value="piva">P.IVA - Collaboratore</option>
                                                                        <option value="dipendente">Dipendente</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <script type="text/javascript">
                                            function mostraDocenti() {
                                                var sel = <%=ActionB.getDocentiAllegatoA(username)%>;
                                                for (var x = 1; x < 6; x++) {
                                                    if (x <= sel) {
                                                        $(".classe" + x).toggle(true);
                                                    } else {
                                                        $(".classe" + x).toggle(false);

                                                    }
                                                }
                                            }
                                        </script>
                                        <tr style="border-bottom: 3px;" class="classe2">
                                            <th colspan="4" style="text-align: center;" ><span class="help-block bordered">
                                                    Docente 2 
                                                </span> <hr>
                                            </th>
                                        </tr>
                                        <tr class="classe2">
                                            <td>
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label class="control-label col-md-1"></label>
                                                                <div class="col-md-11">
                                                                    <span class="help-block">
                                                                        Nome
                                                                    </span>
                                                                    <input type="text" class="form-control" name="docNome2" placeholder="..." id="docNome2">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label class="control-label col-md-1"></label>
                                                                <div class="col-md-11">
                                                                    <span class="help-block">
                                                                        Cognome
                                                                    </span>
                                                                    <input type="text" class="form-control" name="docCognome2" placeholder="..." id="docCognome2">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label class="control-label col-md-1"></label>
                                                                <div class="col-md-11">
                                                                    <span class="help-block">
                                                                        Fascia di appartenenza
                                                                    </span>
                                                                    <select class="form-control select2" name="fascia2" id="fascia2">
                                                                        <option value=""></option>
                                                                        <option value="A">A</option>
                                                                        <option value="B">B</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label class="control-label col-md-1"></label>
                                                                <div class="col-md-11">
                                                                    <span class="help-block">
                                                                        Inquadramento
                                                                    </span>
                                                                    <select class="form-control select2" name="inquadramento2" id="inquadramento2">
                                                                        <option value=""></option>
                                                                        <option value="piva">P.IVA - Collaboratore</option>
                                                                        <option value="dipendente">Dipendente</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr style="border-bottom: 3px;" class="classe3">
                                            <th colspan="4" style="text-align: center;" ><span class="help-block bordered">
                                                    Docente 3 
                                                </span> <hr>
                                            </th>
                                        </tr>
                                        <tr class="classe3">
                                            <td>
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label class="control-label col-md-1"></label>
                                                                <div class="col-md-11">
                                                                    <span class="help-block">
                                                                        Nome
                                                                    </span>
                                                                    <input type="text" class="form-control" name="docNome3" placeholder="..." id="docNome3">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label class="control-label col-md-1"></label>
                                                                <div class="col-md-11">
                                                                    <span class="help-block">
                                                                        Cognome
                                                                    </span>
                                                                    <input type="text" class="form-control" name="docCognome3" placeholder="..." id="docCognome3">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label class="control-label col-md-1"></label>
                                                                <div class="col-md-11">
                                                                    <span class="help-block">
                                                                        Fascia di appartenenza
                                                                    </span>
                                                                    <select class="form-control select2" name="fascia3" id="fascia3">
                                                                        <option value=""></option>
                                                                        <option value="A">A</option>
                                                                        <option value="B">B</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label class="control-label col-md-1"></label>
                                                                <div class="col-md-11">
                                                                    <span class="help-block">
                                                                        Inquadramento
                                                                    </span>
                                                                    <select class="form-control select2" name="inquadramento3" id="inquadramento3">
                                                                        <option value=""></option>
                                                                        <option value="piva">P.IVA - Collaboratore</option>
                                                                        <option value="dipendente">Dipendente</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr style="border-bottom: 3px;" class="classe4">
                                            <th colspan="4" style="text-align: center;" ><span class="help-block bordered">
                                                    Docente 4 
                                                </span> <hr>
                                            </th>
                                        </tr>
                                        <tr class="classe4">
                                            <td>
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label class="control-label col-md-1"></label>
                                                                <div class="col-md-11">
                                                                    <span class="help-block">
                                                                        Nome
                                                                    </span>
                                                                    <input type="text" class="form-control" name="docNome4" placeholder="..." id="docNome4">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label class="control-label col-md-1"></label>
                                                                <div class="col-md-11">
                                                                    <span class="help-block">
                                                                        Cognome
                                                                    </span>
                                                                    <input type="text" class="form-control" name="docCognome4" placeholder="..." id="docCognome4">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label class="control-label col-md-1"></label>
                                                                <div class="col-md-11">
                                                                    <span class="help-block">
                                                                        Fascia di appartenenza
                                                                    </span>
                                                                    <select class="form-control select2" name="fascia4" id="fascia4">
                                                                        <option value=""></option>
                                                                        <option value="A">A</option>
                                                                        <option value="B">B</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label class="control-label col-md-1"></label>
                                                                <div class="col-md-11">
                                                                    <span class="help-block">
                                                                        Inquadramento
                                                                    </span>
                                                                    <select class="form-control select2" name="inquadramento4" id="inquadramento4">
                                                                        <option value=""></option>
                                                                        <option value="piva">P.IVA - Collaboratore</option>
                                                                        <option value="dipendente">Dipendente</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr style="border-bottom: 3px;" class="classe5">
                                            <th colspan="4" style="text-align: center;" ><span class="help-block bordered">
                                                    Docente 5
                                                </span> <hr>
                                            </th>
                                        </tr>
                                        <tr class="classe5">
                                            <td>
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label class="control-label col-md-1"></label>
                                                                <div class="col-md-11">
                                                                    <span class="help-block">
                                                                        Nome
                                                                    </span>
                                                                    <input type="text" class="form-control" name="docNome5" placeholder="..." id="docNome5">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label class="control-label col-md-1"></label>
                                                                <div class="col-md-11">
                                                                    <span class="help-block">
                                                                        Cognome
                                                                    </span>
                                                                    <input type="text" class="form-control" name="docCognome5" placeholder="..." id="docCognome5">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label class="control-label col-md-1"></label>
                                                                <div class="col-md-11">
                                                                    <span class="help-block">
                                                                        Fascia di appartenenza
                                                                    </span>
                                                                    <select class="form-control select2" name="fascia5" id="fascia5">
                                                                        <option value=""></option>
                                                                        <option value="A">A</option>
                                                                        <option value="B">B</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label class="control-label col-md-1"></label>
                                                                <div class="col-md-11">
                                                                    <span class="help-block">
                                                                        Inquadramento
                                                                    </span>
                                                                    <select class="form-control select2" name="inquadramento5" id="inquadramento5">
                                                                        <option value=""></option>
                                                                        <option value="piva">P.IVA - Collaboratore</option>
                                                                        <option value="dipendente">Dipendente</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </form>
                            </div>
                            <div class="row">
                                <div class="col-md-12"> 
                                    <div class="portlet light">
                                        <div class="portlet-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <center><button type="button" class="btn btn-lg blue btn-block"   onclick="return controllaReg1();"><i class="fa fa-save"  ></i> Salva dati</button></center>
                                                </div>                            
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <%} else if (allegato_A_B.equals("C") && ActionB.esisteAllegatoA(username) && ActionB.esisteAllegatoB(username)) {
                                //if (ActionB.isPresenzaDocumento(username, "ALB1")) {
                            %>
                            
                            <div class="portlet-body">
                                <table style="width: 100%;">
                                    <thead>
                                        <tr>
                                            <th colspan="2" style="text-align: center; background-color: silver;"><h4><i class="fa fa-pencil-square-o"></i>Caricamento documenti docente/i</h4>
                                            </th>
                                        </tr>
                                    </thead>
                                </table>
                                <hr>
                                <table style="width: 100%;" class='table-responsive table-striped'>
                                    <%                                        ArrayList<Docenti> al = ActionB.getDocenti(username);
                                        for (int i = 0; i < al.size(); i++) {
                                            boolean presenzaFieldB1 = ActionB.esisteAllegatoB1Field(username, i);
                                    %>
                                    <form name="formregist" role="form" action="Operazioni?action=UploadMultiplo" method="post"  class="form-horizontal" id="doc<%=i%>" enctype="multipart/form-data">
                                        <input type="hidden" name="id" value="<%=i%>"/>
                                        <tr style="border-bottom: 3px;" class="classe1">
                                            <th colspan="4" style="text-align: center;" ><span class="help-block bordered">
                                                    Docente:&nbsp;<%=al.get(i).getNome()%>&nbsp;<%=al.get(i).getCognome()%>
                                                </span> <hr>
                                            </th>
                                        </tr>
                                        <%
                                            if (ActionB.isPresenzaAllegatoB1(i + "", username)) {

                                        %>
                                        <tr class="classe<%=i%>">
                                            <td>
                                                <div class="col-md-12">
                                                    <div class="form-group">
                                                        <div class="col-md-12">
                                                            <label class="control-label">CV<b style="color: red;">*</b> </label>
                                                        </div>
                                                        <div class="col-md-12">
                                                            <button class="btn btn-outline green" type="button" onclick="return submitfor('f1<%=i%>');">
                                                                Visualizza <i class="fa fa-download"></i>
                                                            </button>
                                                        </div>

                                                    </div>
                                                </div>
                                            </td>
                                            <td>
                                                <div class="col-md-12">
                                                    <div class="form-group">
                                                        <div class="col-md-12">
                                                            <label class="control-label">Doc. Identità<b style="color: red;">*</b> </label>
                                                        </div>
                                                        <div class="col-md-12">
                                                            <button class="btn btn-outline green" type="button" onclick="return submitfor('f5<%=i%>');">
                                                                Visualizza <i class="fa fa-download"></i>
                                                            </button>
                                                        </div>

                                                    </div>
                                                </div>
                                            </td>
                                            <td>
                                                <div class="col-md-12">
                                                    <div class="form-group">
                                                        <div class="col-md-12">
                                                            <label class="control-label">Allegato B1<b style="color: red;">*</b> </label>
                                                        </div>
                                                        <div class="col-md-12">
                                                            <button class="btn btn-outline green" type="button" onclick="return submitfor('f4<%=i%>');">
                                                                Visualizza <i class="fa fa-download"></i>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <%

                                            if (ActionB.verificaDomandaCompleta(username)) {
                                        %>
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <%
                                        } else {
                                        %>
                                        <tr>
                                            <td></td>

                                            <td>
                                                <div class="col-md-12"> 
                                                    <div class="portlet light">
                                                        <div class="portlet-body">
                                                            <div class="row">
                                                                <div class="col-md-12">
                                                                    <center><button type="button" class="btn btn-lg red btn-block" onclick="return submitfor('eiliminaDoc<%=i%>');"><i class="fa fa-trash-o"  ></i> Elimina dati</button></center>
                                                                </div>                            
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                            <td ></td>
                                        </tr>
                                        <%
                                            }
                                        %>
                                        <form name="f0<%=i%>" role="form" action="Download?action=docbandoDocente" method="post" target="_blank"></form>
                                        <form name="f1<%=i%>" role="form" action="Download?action=docbandoDocente" method="post" target="_blank">
                                            <input type="hidden" name="tipodoc" value="cv"/>
                                            <input type="hidden" name="username" value="<%=username%>"/>
                                            <input type="hidden" name="indice" value="<%=i%>"/>
                                        </form>
                                        <form name="f4<%=i%>" role="form" action="Download?action=docbandoDocente" method="post" target="_blank">
                                            <input type="hidden" name="tipodoc" value="b1"/>
                                            <input type="hidden" name="username" value="<%=username%>"/>
                                            <input type="hidden" name="indice" value="<%=i%>"/>
                                        </form>
                                        <form name="f5<%=i%>" role="form" action="Download?action=docbandoDocente" method="post" target="_blank">
                                            <input type="hidden" name="tipodoc" value="ci"/>
                                            <input type="hidden" name="username" value="<%=username%>"/>
                                            <input type="hidden" name="indice" value="<%=i%>"/>
                                        </form>
                                        <form name="eiliminaDoc<%=i%>" role="form" action="Operazioni?action=delDocDocenti" method="post">
                                            <input type="hidden" name="id_doc" value="<%=i%>"/>
                                            <input type="hidden" name="username" value="<%=username%>"/>
                                        </form>
                                        <%
                                        } else {
                                        %>
                                        <tr class="classe<%=i%>">
                                            <td>
                                                <div class="form-group">
                                                    <div class="col-md-12">
                                                        <label class="control-label">CV<b style="color: red;">*</b> </label>
                                                    </div>
                                                    <div class="col-md-12">
                                                        <div class="fileinput fileinput-new" data-provides="fileinput">
                                                            <div class="input-group input-large">
                                                                <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                                                    <i class="fa fa-file fileinput-exists"></i>&nbsp;
                                                                    <span class="fileinput-filename"> </span>
                                                                </div>
                                                                <span class="input-group-addon btn default btn-file">
                                                                    <span class="fileinput-new"> Select file </span>
                                                                    <span class="fileinput-exists"> Change </span>
                                                                    <input required type="file" name="cv_<%=i%>"> </span>
                                                                <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Remove </a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>
                                                <div class="col-md-12">
                                                    <div class="form-group">
                                                        <div class="col-md-12">
                                                            <label class="control-label">Doc. Identità<b style="color: red;">*</b> </label>
                                                        </div>
                                                        <div class="col-md-12">
                                                            <div class="fileinput fileinput-new" data-provides="fileinput">
                                                                <div class="input-group input-large">
                                                                    <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                                                        <i class="fa fa-file fileinput-exists"></i>&nbsp;
                                                                        <span class="fileinput-filename"> </span>
                                                                    </div>
                                                                    <span class="input-group-addon btn default btn-file">
                                                                        <span class="fileinput-new"> Scegli </span>
                                                                        <span class="fileinput-exists"> Cambia </span>
                                                                        <input required type="file" name="di_<%=i%>"> </span>
                                                                    <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Remove </a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                            <%
                                                if (presenzaFieldB1) {

                                            %>
                                            <td>
                                                <div class="col-md-12">
                                                    <div class="form-group">
                                                        <div class="col-md-12">
                                                            <label class="control-label">Allegato B1<b style="color: red;">*</b> </label>
                                                        </div>
                                                        <div class="col-md-12">
                                                            <div class="fileinput fileinput-new" data-provides="fileinput">
                                                                <div class="input-group input-large">
                                                                    <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                                                        <i class="fa fa-file fileinput-exists"></i>&nbsp;
                                                                        <span class="fileinput-filename"> </span>
                                                                    </div>
                                                                    <span class="input-group-addon btn default btn-file">
                                                                        <span class="fileinput-new"> Select file </span>
                                                                        <span class="fileinput-exists"> Change </span>
                                                                        <input required type="file" name="b1_<%=i%>"> 
                                                                    </span>
                                                                    <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Remove </a>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-4">
                                                                <button class="btn btn-outline green" type="button" onclick="scaricaFile('Download', {'iddocente': '<%=i%>', 'username': '<%=username%>', 'nomeDocente': '<%=al.get(i).getNome()%> <%=al.get(i).getCognome()%>', 'action': 'docAllegatoB1'});">
                                                                    Visualizza <i class="fa fa-download"></i>
                                                                </button>
                                                                <button class="btn btn-outline red" type="button" onclick="scaricaFile('Operazioni', {'iddocente': '<%=i%>', 'username': '<%=username%>', 'action': 'delDocAllegatoB1'});">
                                                                    Elimina <i class="fa fa-trash-o"></i>
                                                                </button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                            <%
                                            } else {
                                            %>
                                            <td>
                                                <div class="col-md-12">
                                                    <div class="form-group">
                                                        <div class="col-md-12">
                                                            <label class="control-label">Allegato B1<b style="color: red;">*</b> </label>
                                                        </div>
                                                        <div class="col-md-12">
                                                            <button type="button" class="btn btn-outline red" onclick="location.href = 'allegatoB1.jsp?iddocente=<%=i%>'">
                                                                Compila <i class="fa fa-pencil-square-o"></i>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                            <%
                                                }
                                            %>
                                        </tr>
                                        <tr>
                                            <td ></td>
                                            <td>
                                                <%
                                                    if (ActionB.esisteAllegatoB1Field(username, i)) {
                                                %>
                                                <div class="col-md-12"> 
                                                    <div class="portlet light">
                                                        <div class="portlet-body">
                                                            <div class="row">
                                                                <div class="col-md-12">
                                                                    <center><button type="submit" class="btn btn-lg blue btn-block"><i class="fa fa-save"  ></i> Salva dati</button></center>
                                                                </div>                            
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <%}%>
                                            </td>
                                            <td ></td>
                                        </tr>
                                        <%
                                            }
                                        %>
                                    </form>
                                    <%}%>
                            </div>
                        </div>
                        </td>
                        </tr>
                        </table>
                    </div>
                   
                    <%} else {%>
                    <script>
                        location.href = "bando_index.jsp";
                    </script>
                    <%}%>

                </div>  
            </div>
        </div>

        <!-- END CONTENT -->
        <!-- BEGIN QUICK SIDEBAR -->
        <!-- END QUICK SIDEBAR -->
    </div>
    <!-- END CONTAINER -->
    <!-- BEGIN FOOTER -->
    <div class="page-footer">
        <div class="page-footer-inner"> <%=Constant.NAMEAPP%> v. 1.0.0</div>
        <div class="scroll-to-top">
            <i class="icon-arrow-up"></i>
        </div>
    </div>
    <script src="assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/jquery-easypiechart/jquery.easypiechart.min.js" type="text/javascript"></script>
    <!-- END CORE PLUGINS -->
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <script src="assets/seta/js/select2.full.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js" type="text/javascript"></script>
    <!-- END PAGE LEVEL PLUGINS -->
    <!-- BEGIN THEME GLOBAL SCRIPTS -->
    <script src="assets/global/scripts/app.min.js" type="text/javascript"></script>
    <script src="assets/pages/scripts/dashboard.min.js" type="text/javascript"></script>
    <!-- END THEME GLOBAL SCRIPTS -->
    <!-- BEGIN PAGE LEVEL SCRIPTS -->
    <script src="assets/pages/scripts/components-select2.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
    <script src="assets/pages/scripts/components-bootstrap-select.min.js" type="text/javascript"></script>
    <script src="assets/pages/scripts/components-date-time-pickers.min.js" type="text/javascript"></script>
    <script src="assets/seta/js/form-input-mask.min.js" type="text/javascript"></script>
    <!-- END PAGE LEVEL SCRIPTS -->
    <script src="assets/pages/scripts/components-bootstrap-select.min.js" type="text/javascript"></script>
    <!-- BEGIN THEME LAYOUT SCRIPTS -->
    <script src="assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
    <script src="assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="//cdn.jsdelivr.net/momentjs/latest/moment-with-locales.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
    <script>
                        moment.locale('it');
                        $('.periodo').daterangepicker({
                            autoUpdateInput: false,
                            locale: {
                                format: 'DD/MM/YYYY',
                                cancelLabel: 'Cancella',
                                applyLabel: "Seleziona"
                            }
                        });

                        $('.periodo').on('apply.daterangepicker', function (ev, picker) {
                            $(this).val(picker.startDate.format('DD/MM/YYYY') + ' - ' + picker.endDate.format('DD/MM/YYYY'));
                        });

                        $('.periodo').on('cancel.daterangepicker', function (ev, picker) {
                            $(this).val('');
                        });

    </script>

</body>
</html>
<%}%>