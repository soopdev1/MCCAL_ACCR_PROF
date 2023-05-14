<%-- 
    Document   : bando_index2
    Created on : 28-lug-2017, 15.43.51
    Author     : rcosco
--%>

<%@page import="rc.so.action.Constant"%>
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
        <script src="assets/seta/js/moment.js" type="text/javascript"></script>

        <!-- END THEME GLOBAL STYLES -->
        <!-- BEGIN THEME LAYOUT STYLES -->
        <link href="assets/layouts/layout/css/layout.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/layouts/layout/css/themes/grey.min.css" rel="stylesheet" type="text/css" id="style_color" />
        <link href="assets/layouts/layout/css/custom.min.css" rel="stylesheet" type="text/css" />
        <!-- END THEME LAYOUT STYLES -->
        <link rel="shortcut icon" href="favicon.ico" /> 

        <%
            String bandorif = session.getAttribute("bandorif").toString();
            String username = session.getAttribute("username").toString();
            //String sino = session.getAttribute("sino").toString();
            boolean isAnnullata = ActionB.domandaAnnullata(bandorif, username);
            String descbando = ActionB.getDescrizioneBando(bandorif);
            String scabando = ActionB.getScadenzaBando(bandorif);
            boolean isDomandaPresente = ActionB.isDomandaPresente(bandorif, username);
            boolean statoDomanda = ActionB.getStatoDomanda(username);
        %>
        <script type="text/javascript">
            function confirmsend() {
                document.getElementById("confirmsend").className = document.getElementById("confirmsend").className + " in";
                document.getElementById("confirmsend").style.display = "block";
                return false;
            }
            function confcanc() {
                document.getElementById("confirmcanc").className = document.getElementById("confirmcanc").className + " in";
                document.getElementById("confirmcanc").style.display = "block";
                return false;
            }

            function confirmrem(nameform) {
                document.getElementById("confirmremsi").onclick = function () {
                    submitfor(nameform);
                };
                document.getElementById("confirmrem").className = document.getElementById("confirmrem").className + " in";
                document.getElementById("confirmrem").style.display = "block";
                return false;
            }

            // PER BANDO H8
            function confirmremAllegatoA(nameform) {
                document.getElementById("confirmremsi").onclick = function () {
                    submitfor(nameform);
                };
                document.getElementById("confirmremAllegatoA").className = document.getElementById("confirmremAllegatoA").className + " in";
                document.getElementById("confirmremAllegatoA").style.display = "block";
                return false;
            }

            function confirmremAllegatoB(nameform) {
                document.getElementById("confirmremsi").onclick = function () {
                    submitfor(nameform);
                };
                document.getElementById("confirmremAllegatoB").className = document.getElementById("confirmremAllegatoB").className + " in";
                document.getElementById("confirmremAllegatoB").style.display = "block";
                return false;
            }

            function confirmremDef(nameform) {
                document.getElementById("confirmremsiDef").onclick = function () {
                    submitfor(nameform);
                };
                document.getElementById("confirmremDef").className = document.getElementById("confirmremDef").className + " in";
                document.getElementById("confirmremDef").style.display = "block";
                return false;
            }

            function submitfor(nameform) {
                document.forms[nameform].submit();
            }
            function dismiss(name) {
                document.getElementById(name).className = "modal fade";
                document.getElementById(name).style.display = "none";
            }

            function clitoastr(cl) {
                document.getElementById(cl).click();
            }
            function clierr() {
                document.getElementById('showtoast2').click();
            }

            function controlladataBando() {
                var ch1 = moment('<%=scabando%>', 'DD/MM/YYYY HH:mm:ss');
                var limitdate = moment();
                if (limitdate.isSameOrAfter(ch1)) {
                    document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                    document.getElementById('errorlarge').style.display = "block";
                    document.getElementById('errorlargetext').innerHTML = "Il Bando è scaduto in data " + "<%=scabando%>" + " non è possbile inviare la domanda.";
                    return false;
                }
                submitfor('fsend');
            }
            function closeanother(attuale) {
                var cusid_ele = document.getElementsByClassName('task-list');
                for (var i = 0; i < cusid_ele.length; ++i) {
                    var item = cusid_ele[i];
                    if (item.id !== attuale) {
                        item.className = "task-list panel-collapse collapse";
                    }
                }
            }

            function eseguiForm(servURL, params) {
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

            function confirmRemConvenzioni(nameform) {
                document.getElementById("confirmRemConvenzionisi").onclick = function () {
                    eseguiForm('Operazioni', {'tipodoc': 'CONV', 'username': '<%=username%>', 'action': 'eliminaconv'});
                };
                document.getElementById("confirmRemConvenzioni").className = document.getElementById("confirmRemConvenzioni").className + " in";
                document.getElementById("confirmRemConvenzioni").style.display = "block";
                return false;
            }

            function confirmRemConvenzioni1() {
                document.getElementById("confirmRemConvenzioni1si").onclick = function () {
                    eseguiForm('Operazioni', {'tipodoc': 'MOD1', 'username': '<%=username%>', 'action': 'eliminaconv'});
                };
                document.getElementById("confirmRemConvenzioni1").className = document.getElementById("confirmRemConvenzioni1").className + " in";
                document.getElementById("confirmRemConvenzioni1").style.display = "block";
                return false;
            }

            function confirmRemConvenzioni2() {
                document.getElementById("confirmRemConvenzioni2si").onclick = function () {
                    eseguiForm('Operazioni', {'tipodoc': 'MOD2', 'username': '<%=username%>', 'action': 'eliminaconv'});
                };
                document.getElementById("confirmRemConvenzioni2").className = document.getElementById("confirmRemConvenzioni2").className + " in";
                document.getElementById("confirmRemConvenzioni2").style.display = "block";
                return false;
            }

            function confirmSendConvenzione() {
                document.getElementById("inviaConvenzioniConferma").onclick = function () {
                    eseguiForm('Operazioni', {'username': '<%=username%>', 'action': 'sendConvenzioni'});
                };
                document.getElementById("inviaConvenzioni").className = document.getElementById("inviaConvenzioni").className + " in";
                document.getElementById("inviaConvenzioni").style.display = "block";
                return false;
            }

        </script>
        <script src="assets/global/plugins/jquery.min.js" type="text/javascript"></script>

    </head>
    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white page-sidebar-closed">
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
                    <div id="inviaConvenzioni" class="modal fade" tabindex="-1" data-backdrop="confirmrem" data-keyboard="false">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title">Conferma invio convenzioni</h4>
                                </div>
                                <div class="modal-body">
                                    <p>
                                        Sei sicuro di voler inviare i documenti all'Ente Microcredito?<br> 
                                        <u><b>L'operazione non potr&#224; pi&#249; essere annullata.</b></u>
                                    </p>
                                </div>
                                <div class="modal-footer" id="groupbtn2">
                                    <button type="button" class="btn green" id="inviaConvenzioniConferma"><i class="fa fa-check"></i> SI</button>
                                    <button type="button" data-dismiss="modal" class="btn red" onclick="return dismiss('inviaConvenzioni');"><i class="fa fa-times"></i> NO</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="confirmRemConvenzioni" class="modal fade" tabindex="-1" data-backdrop="confirmrem" data-keyboard="false">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title">Conferma eliminazione documento</h4>
                                </div>
                                <div class="modal-body">
                                    <p>
                                        Sei sicuro di voler eliminare il documento selezionato?<br> 
                                        <u><b>L'operazione non potr&#224; pi&#249; essere annullata e sar&#224; necessario caricare nuovamente il documento.</b></u>
                                    </p>
                                </div>
                                <div class="modal-footer" id="groupbtn2">
                                    <button type="button" class="btn green" id="confirmRemConvenzionisi"><i class="fa fa-check"></i> SI</button>
                                    <button type="button" data-dismiss="modal" class="btn red" onclick="return dismiss('confirmRemConvenzioni');"><i class="fa fa-times"></i> NO</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="confirmRemConvenzioni1" class="modal fade" tabindex="-1" data-backdrop="confirmrem" data-keyboard="false">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title">Conferma eliminazione documento</h4>
                                </div>
                                <div class="modal-body">
                                    <p>
                                        Sei sicuro di voler eliminare il documento selezionato?<br> 
                                        <u><b>L'operazione non potr&#224; pi&#249; essere annullata e sar&#224; necessario caricare nuovamente il documento.</b></u>
                                    </p>
                                </div>
                                <div class="modal-footer" id="groupbtn2">
                                    <button type="button" class="btn green" id="confirmRemConvenzioni1si"><i class="fa fa-check"></i> SI</button>
                                    <button type="button" data-dismiss="modal" class="btn red" onclick="return dismiss('confirmRemConvenzioni1');"><i class="fa fa-times"></i> NO</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="confirmRemConvenzioni2" class="modal fade" tabindex="-1" data-backdrop="confirmrem" data-keyboard="false">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title">Conferma eliminazione documento</h4>
                                </div>
                                <div class="modal-body">
                                    <p>
                                        Sei sicuro di voler eliminare il documento selezionato?<br> 
                                        <u><b>L'operazione non potr&#224; pi&#249; essere annullata e sar&#224; necessario caricare nuovamente il documento.</b></u>
                                    </p>
                                </div>
                                <div class="modal-footer" id="groupbtn2">
                                    <button type="button" class="btn green" id="confirmRemConvenzioni2si"><i class="fa fa-check"></i> SI</button>
                                    <button type="button" data-dismiss="modal" class="btn red" onclick="return dismiss('confirmRemConvenzioni2');"><i class="fa fa-times"></i> NO</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="confirmcanc" class="modal fade" tabindex="-1" data-backdrop="confirmcanc" data-keyboard="false">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title">Conferma cancellazione domanda</h4>
                                </div>
                                <div class="modal-body">
                                    <p>
                                        Sei sicuro di voler procedere con la cancellazione?<br>
                                        <u><b>L'operazione non potr&#224; pi&#249; essere annullata.</b></u>
                                    </p>
                                </div>
                                <div class="modal-footer" id="groupbtn2">
                                    <button type="button" class="btn green" onclick="return submitfor('fann');"><i class="fa fa-check"></i> SI</button>
                                    <button type="button" data-dismiss="modal" class="btn red" onclick="return dismiss('confirmcanc');"><i class="fa fa-times"></i> NO</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="confirmsend" class="modal fade" tabindex="-1" data-backdrop="confirmsend" data-keyboard="false">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title">Conferma invio domanda</h4>
                                </div>
                                <div class="modal-body">
                                    <p>
                                        Sei sicuro di voler procedere con l'invio?<br>
                                        <u><b>L'operazione non potr&#224; pi&#249; essere annullata.</b></u>
                                    </p>
                                </div>
                                <div class="modal-footer" id="groupbtn2">
                                    <button type="button" class="btn green" onclick="return controlladataBando();"><i class="fa fa-check"></i> SI</button>
                                    <button type="button" data-dismiss="modal" class="btn red" onclick="return dismiss('confirmsend');"><i class="fa fa-times"></i> NO</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="confirmrem" class="modal fade" tabindex="-1" data-backdrop="confirmrem" data-keyboard="false">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title">Conferma eliminazione documento</h4>
                                </div>
                                <div class="modal-body">
                                    <p>
                                        Sei sicuro di voler eliminare il documento selezionato?<br> 
                                        <u><b>L'operazione non potr&#224; pi&#249; essere annullata e sar&#224; necessario caricare nuovamente il documento.</b></u>
                                    </p>
                                </div>
                                <div class="modal-footer" id="groupbtn2">
                                    <button type="button" class="btn green"  id="confirmremsi"><i class="fa fa-check"></i> SI</button>
                                    <button type="button" data-dismiss="modal" class="btn red" onclick="return dismiss('confirmrem');"><i class="fa fa-times"></i> NO</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="confirmremAllegatoA" class="modal fade" tabindex="-1" data-backdrop="confirmremAllegatoA" data-keyboard="false">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title">Conferma eliminazione dati</h4>
                                </div>
                                <div class="modal-body">
                                    <p>
                                        Sei sicuro di voler eliminare il documento selezionato?<br> 
                                        <u><b>L'operazione non potr&#224; pi&#249; essere annullata e sar&#224; necessario reinserire i dati dell'allegato A, B e B1 in quanto, l'allegato B è strettamente connesso all'allegato A.</b></u>
                                    </p>
                                </div>
                                <div class="modal-footer" id="groupbtn2">
                                    <button type="button" class="btn green"  id="confirmremsi" onclick="return document.getElementById('f5DONLA').submit();"><i class="fa fa-check"></i> SI</button>
                                    <button type="button" data-dismiss="modal" class="btn red" onclick="return dismiss('confirmremAllegatoA');"><i class="fa fa-times"></i> NO</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="confirmremAllegatoB" class="modal fade" tabindex="-1" data-backdrop="confirmremAllegatoB" data-keyboard="false">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title">Conferma eliminazione dati</h4>
                                </div>
                                <div class="modal-body">
                                    <p>
                                        Sei sicuro di voler eliminare il documento selezionato?<br> 
                                        <u><b>L'operazione non potr&#224; pi&#249; essere annullata e sar&#224; necessario reinserire i dati dell'allegato B e B1 in quanto, l'allegato B1 è strettamente connesso all'allegato B.</b></u>
                                    </p>
                                </div>
                                <div class="modal-footer" id="groupbtn2">
                                    <button type="button" class="btn green"  id="confirmremsi" onclick="return document.getElementById('f6DONLB').submit();"><i class="fa fa-check"></i> SI</button>
                                    <button type="button" data-dismiss="modal" class="btn red" onclick="return dismiss('confirmremAllegatoB');"><i class="fa fa-times"></i> NO</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="confirmremDef" class="modal fade" tabindex="-1" data-backdrop="confirmremDef" data-keyboard="false">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title">Conferma eliminazione documento</h4>
                                </div>
                                <div class="modal-body">
                                    <p>
                                        Sei sicuro di voler eliminare il documento selezionato?<br> 
                                        <u><b>
                                                L'operazione non potr&#224; pi&#249; essere annullata e sar&#224; 
                                                necessario caricare nuovamente tutti i documenti finora caricati,
                                                anche quelli degli step successivi.
                                            </b>
                                        </u>
                                    </p>
                                </div>
                                <div class="modal-footer" id="groupbtn2">
                                    <button type="button" class="btn green" id="confirmremsiDef"><i class="fa fa-check"></i> SI</button>
                                    <button type="button" data-dismiss="modal" class="btn red" onclick="return dismiss('confirmremDef');"><i class="fa fa-times"></i> NO</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal fade" id="errorlarge" tabindex="-1" role="dialog" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                    <h4 class="modal-title font-red uppercase"><b>Messaggio di errore</b></h4>
                                </div>
                                <div class="modal-body" id="errorlargetext">ERRORE</div>
                                <div class="modal-footer">
                                    <button type="button" class="btn dark btn-outline" onclick="return dismiss('errorlarge');" data-dismiss="modal"><i class="fa fa-times"></i> Close</button>
                                </div>
                            </div>
                            <!-- /.modal-content -->
                        </div>
                        <!-- /.modal-dialog -->
                    </div>
                    <div id="myModal" class="modal fade" role="dialog">
                        <div class="modal-dialog modal-lg">
                            <!-- Modal content-->
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title">Step 2 - Selezionare una delle due opzioni disponibili</h4>
                                </div>
                                <div class="modal-body">
                                    <p><a href="bando_onlinenew.jsp" class="btn green"> Compilare dati di dettaglio del risparmio energetico percentuale atteso </a></p>
                                    <br>
                                    <p><button class="btn blue" data-toggle="modal" data-dismiss="modal" data-target="#myModal2">Passare direttamente all'inserimento dei soli dati contenuti nella relazione progettuale </button></p>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn red" data-dismiss="modal">Chiudi</button>
                                </div>
                            </div>

                        </div>
                    </div>
                    <%
                        String es = request.getParameter("esito");
                        String msg = "";
                        String inte = "";
                        if (es != null) {
                            if (es.equals("ok")) {
                                inte = "Operazione Completata";
                                msg = "Documento caricato con successo. Puoi procedere allo step successivo.";
                            } else if (es.equals("ok1")) {
                                inte = "Operazione Completata";
                                msg = "Documento compilato con successo. Puoi procedere allo step successivo.";
                            } else if (es.equals("okrem")) {
                                inte = "Operazione Completata";
                                msg = "Documento eliminato con successo.";
                            } else if (es.equals("okrem1")) {
                                inte = "Operazione Completata";
                                msg = "Dati eliminati con successo.";
                            } else if (es.equals("okinvio")) {
                                inte = "Operazione Completata";
                                msg = "Domanda inviata con successo.";
                            } else if (es.equals("korem")) {
                                inte = "Errore";
                                msg = "Impossibile eliminare il documento, contattare l'amministratore del sistema. <u>yisucal.supporto@microcredito.gov.it</u>";
                            } else if (es.equals("ko1")) {
                                inte = "Errore";
                                msg = "Impossibile salvare i dati della domanda, contattare l'amministratore del sistema. <u>yisucal.supporto@microcredito.gov.it</u>";
                            } else if (es.equals("ko2")) {
                                inte = "Errore";
                                msg = "Impossibile salvare i dati dell'allegato B, contattare l'amministratore del sistema. <u>yisucal.supporto@microcredito.gov.it</u>";
                            } else if (es.equals("koinvio")) {
                                inte = "Errore";
                                msg = "Impossibile inviare la domanda, contattare l'amministratore del sistema. <u>yisucal.supporto@microcredito.gov.it</u>";
                            } else if (es.equals("koinviopresente")) {
                                inte = "Errore";
                                msg = "Impossibile inviare la domanda in quanto risulta già inviata.";
                            } else if (es.equals("kocanc")) {
                                inte = "Errore";
                                msg = "Impossibile annullare la domanda, contattare l'amministratore del sistema. <u>yisucal.supporto@microcredito.gov.it</u>";
                            } else if (es.equals("kodop")) {
                                inte = "Errore";
                                msg = "Documento già presente. Impossibile caricare documento.";
                            } else if (es.equals("koest")) {
                                inte = "Errore";
                                msg = "Tipo documento non valido, è stato forzato il link alla pagina.";
                            } else if (es.equals("kocarest")) {
                                inte = "Errore";
                                msg = "I documenti convenzione, Modello 1 e Modello 2 devono essere firmati digitalmente e quindi devono avere il formato <b>.p7m</b>.";
                            } else if (es.equals("okconv")) {
                                inte = "Operazione Completata";
                                msg = "Convenzione inviata con successo.";
                            } else if (es.equals("koconv")) {
                                inte = "Errore";
                                msg = "Impossibile inviare i dati della convenzione, contattare l'amministratore del sistema. <u>yisucal.supporto@microcredito.gov.it</u>";
                            } else if (es.equals("koconv2")) {
                                inte = "Errore";
                                msg = "Impossibile inviare i dati della convenzione, convenzione già inviata";
                            } else {
                                es = "";
                            }
                        } else {
                            es = "";
                        }
                        if (!es.equals("")) {%>
                    <div class="modal fade" role="dialog">
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
                    <%}
                        Liste li = new Liste(bandorif, username);
                        int obbligatori = 0;
                        int presenti = 0;
                        ArrayList<Docbandi> lid1 = li.getLid1();
                        ArrayList<Docuserbandi> lid2 = li.getLidUser();
                        Domandecomplete doco = ActionB.isDomandaCompletaConsolidata(bandorif, username);
                        boolean mod = true;
                        if (doco != null) {
                            // mod = false;
                        }
                        int ind = 0;
                        for (int i = 0; i < lid1.size(); i++) {
                            boolean multiplopresente = false;
                            for (int j = 0; j < lid2.size(); j++) {
                                if (lid2.get(j).getCodicedoc().equals(lid1.get(i).getCodicedoc())) {
                                    if (lid1.get(i).getObbl().equals("1")) {
                                        if (lid1.get(i).getCollegati().equals("1")) {
                                            multiplopresente = true;
                                        } else {
                                            presenti++;
                                        }
                                    }
                                }
                            }
                            if (multiplopresente) {
                                presenti++;
                            }
                        }
                        for (int i = 0; i < lid1.size(); i++) {
                            if (lid1.get(i).getObbl().equals("1")) {
                                obbligatori++;
                            }
                            for (int j = 0; j < lid2.size(); j++) {
                                if (lid2.get(j).getCodicedoc().equals(lid1.get(i).getCodicedoc())) {
                                    ind = i;
                                }
                            }
                        }
                        double denom = (double) obbligatori;
                        double num = (double) presenti;
                        double perc = (num / denom) * 100;
                        int prc = (int) perc;
                        if (prc > 100) {
                            prc = 100;
                        }
                        boolean abilitainvio = prc == 100;
                        boolean esisteAllegatoA = ActionB.esisteAllegatoA(username);
                        boolean esisteAllegatoB = ActionB.esisteAllegatoB(username);
                        boolean esisteAllegatoB1 = ActionB.esisteAllegatoB1(username);
                        boolean domandaCompleta = ActionB.verificaDomandaCompleta(username);
                    %>

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
                    <%if (isAnnullata) {%>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert alert-danger" style="text-align: center;">
                                <strong>Attenzione! La sua domanda &#232; stata annullata, nessuna operazione disponibile.</strong>
                            </div>
                        </div>
                    </div>
                    <%} else {
                        if (statoDomanda) {
                            boolean ctrlInvioConvenzione = ActionB.verificaInvioConvenzione(username);
                    %>
                    <div class="row">
                        <div class="col-md-12"> 
                            <div class="col-md-9"> 
                                <div class="portlet box  blue">
                                    <div class="portlet-title">
                                        <div class="caption k"><i class="fa fa-pencil-square-o "></i>Scarica e sottoscrivi convenzione</div>
                                    </div>
                                    <%
                                        if (ActionB.verPresenzaConvenzioni(username, "CONV")) {
                                    %>
                                    <div class="portlet-body">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <a href="Download?action=viewFileConvenzioni&codicedoc=CONV"> <i class="fa fa-book font-dark fa-2x font-green-jungle"></i>&nbsp;&nbsp;&nbsp;Visualizza convenzione</a>
                                            </div>
                                            <%
                                                if (!ctrlInvioConvenzione) {
                                            %>
                                            <div class="col-md-6 text-right" >
                                                <a href="Operazioni?action=eliminaconv&tipodoc=CONV" onclick="return confirmRemConvenzioni('CONV');"> <i class="fa fa-trash font-dark fa-2x font-red" ></i>&nbsp;&nbsp;&nbsp;Elimina</a>
                                            </div>
                                            <%
                                                }
                                            %>
                                        </div>
                                    </div>
                                    <%
                                    } else {
                                    %>
                                    <div class="portlet-body">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <a href="Download?ctrl=1&action=downconv"><i class="fa fa-download font-dark fa-2x font-red"></i>&nbsp;&nbsp;&nbsp;Scarica convenzione</a>
                                            </div>
                                            <div class="col-md-6 text-right" >
                                                <a href="bando_updocacc.jsp?tipodoc=CONV"> <i class="fa fa-upload font-dark fa-2x font-green-jungle"></i>&nbsp;&nbsp;&nbsp;Carica</a>
                                            </div>
                                        </div>
                                    </div>
                                    <%
                                        }
                                        if (ActionB.verPresenzaConvenzioni(username, "MOD1")) {
                                    %>
                                    <div class="portlet-body">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <a href="Download?action=viewFileConvenzioni&codicedoc=MOD1"> <i class="fa fa-book font-dark fa-2x font-green-jungle"></i>&nbsp;&nbsp;&nbsp;Visualizza - Modello di percorso formativo</a>
                                            </div>
                                            <%
                                                if (!ctrlInvioConvenzione) {
                                            %>
                                            <div class="col-md-6 text-right" >
                                                <a onclick="return confirmRemConvenzioni1('MOD1');"> <i class="fa fa-trash font-dark fa-2x font-red"></i>&nbsp;&nbsp;&nbsp;Elimina</a>
                                            </div>
                                            <%
                                                }
                                            %>
                                        </div>
                                    </div>
                                    <%
                                    } else {
                                    %>
                                    <div class="portlet-body">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <a href="Download?ctrl=2&action=downconv"><i class="fa fa-download font-dark fa-2x font-red"></i>&nbsp;&nbsp;&nbsp;Allegato 1 - Modello di percorso formativo</a>
                                            </div>
                                            <div class="col-md-6 text-right" >
                                                <a href="bando_updocacc.jsp?tipodoc=MOD1"> <i class="fa fa-upload font-dark fa-2x font-green-jungle"></i>&nbsp;&nbsp;&nbsp;Carica</a>
                                            </div>
                                        </div>
                                    </div>
                                    <%
                                        }
                                        if (ActionB.verPresenzaConvenzioni(username, "MOD2")) {
                                    %>
                                    <div class="portlet-body">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <a href="Download?action=viewFileConvenzioni&codicedoc=MOD2"> <i class="fa fa-book font-dark fa-2x font-green-jungle"></i>&nbsp;&nbsp;&nbsp;Visualizza - Dichiarazione sostitutiva "Tracciabilità dei flussi finanziari"</a>
                                            </div>
                                            <%
                                                if (!ctrlInvioConvenzione) {
                                            %>
                                            <div class="col-md-6 text-right" >
                                                <a onclick="return confirmRemConvenzioni2('MOD2');"> <i class="fa fa-trash font-dark fa-2x font-red"></i>&nbsp;&nbsp;&nbsp;Elimina</a>
                                            </div>
                                            <%
                                                }
                                            %>
                                        </div>
                                    </div>
                                    <%
                                    } else {
                                    %>
                                    <div class="portlet-body">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <a href="Download?ctrl=3&action=downconv"><i class="fa fa-download font-dark fa-2x font-red"></i>&nbsp;&nbsp;&nbsp;Allegato 2 - Dichiarazione sostitutiva "Tracciabilità dei flussi finanziari"</a>
                                            </div>
                                            <div class="col-md-6 text-right" >
                                                <a href="bando_updocacc.jsp?tipodoc=MOD2"> <i class="fa fa-upload font-dark fa-2x font-green-jungle"></i>&nbsp;&nbsp;&nbsp;Carica</a>
                                            </div>
                                        </div>
                                    </div>

                                    <%
                                        }
                                    %>
                                    <%
                                        if (!ActionB.getConvenzioneROMA(username).trim().equals("")) {
                                    %>
                                    <div class="portlet-body">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <i class="fa fa-book font-dark fa-2x font-blue"></i>&nbsp;&nbsp;&nbsp;<a target="_blank" href="<%=request.getContextPath()%>/Download?action=docbandoconsConv&userdoc=<%=username%>">Visualizza - "Convenzione controfirmata da Ente Nazionale Microcredito"</a>
                                            </div>
                                        </div>
                                    </div>
                                    <%
                                        }
                                    %>
                                </div>
                            </div>
                            <%
                                if (ActionB.countDocumentConvenzioni(username) == 3) {
                                    if (!ctrlInvioConvenzione) {
                            %>
                            <div class="col-md-3">
                                <div class="portlet light">
                                    <a  class="mt-element-list popovers" style="text-decoration: none;" onclick="return document.getElementById('fsendb').click();"
                                        data-trigger="hover" data-container="body" data-placement="bottom"
                                        data-content="Invio allegato ad Ente Microcredito" 
                                        data-original-title="Invio definitivo della convenzione">
                                        <div class="mt-list-head list-news font-white bg-yellow" onclick="return confirmSendConvenzione();">
                                            <div class="list-head-title-container">
                                                <br>
                                                <h2 class="list-title uppercase" ><i class="fa fa-save" ></i> INVIA</h2>
                                                <br>
                                            </div>
                                        </div>
                                    </a>
                                </div>
                            </div>
                            <%
                                    }
                                }
                            %>
                        </div>
                    </div>
                    <%}%>

                    <div class="row">
                        <div class="col-md-12"> 
                            <div class="col-md-9"> 
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption font-dark"><i class="fa fa-pencil-square-o font-dark"></i> Stato avanzamento domanda di agevolazione</div>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="mt-element-list">
                                            <div class="mt-list-head list-todo">
                                                <div class="list-head-title-container">
                                                    <div class="list-head-count">
                                                        <div class="list-head-count-item font-green-jungle"><i class="fa fa-check font-green-jungle"></i> <%=presenti%></div>
                                                        <div class="list-head-count-item font-red"><i class="fa fa-times font-red"></i> <%=(obbligatori - presenti)%></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="mt-list-container list-todo">
                                                <div class="list-todo-line dark"></div>
                                                <ul>
                                                    <%/*
                                                        if (esisteAllegatoA){
                                                            for(int i=0;i<=5;i++) {
                                                                
                                                            }
                                                        }*/
                                                        for (int i = 0; i < lid1.size(); i++) {

                                                            Docbandi dba = lid1.get(i);

                                                            String tipol = "-";
                                                            String path = "";
                                                            String note = "";
                                                            boolean pres = false;
                                                            for (int j = 0; j < lid2.size(); j++) {
                                                                Docuserbandi docuser = lid2.get(j);
                                                                if (docuser.getCodicedoc().equals(dba.getCodicedoc())) {
                                                                    pres = true;
                                                                    tipol = docuser.getTipodoc();
                                                                    path = docuser.getPath();
                                                                    note = docuser.getNote();
                                                                    break;
                                                                }
                                                            }
                                                            String fontcolor = "font-red";
                                                            String badgecolor = "badge-danger";
                                                            String color = "red";
                                                            String done = "";
                                                            String pres_string = "<i class='fa fa-times'></i>";
                                                            if (pres) {
                                                                fontcolor = "font-green";
                                                                badgecolor = "badge-success";
                                                                color = "green";
                                                                done = "done";
                                                                pres_string = "<i class='fa fa-check'></i>";
                                                            } else {
                                                                if (!dba.getObbl().equals("1")) {
                                                                    fontcolor = "font-yellow-lemon";
                                                                    badgecolor = "badge-warning";
                                                                    color = "yellow-lemon";
                                                                }
                                                            }
                                                            String in = "";

                                                            if (ind == 0) {
                                                                if (lid2.size() > 0) {
                                                                    if (i == ind + 1) {
                                                                        in = "in";
                                                                    }
                                                                } else {
                                                                    if (i == ind) {
                                                                        in = "in";
                                                                    }
                                                                }
                                                            } else if (ind == (lid1.size() - 1)) {
                                                                if (i == ind) {
                                                                    in = "in";
                                                                }
                                                            } else if (i == (ind + 1)) {
                                                                in = "in";
                                                            }
                                                    %>
                                                    <li class="mt-list-item" data-close-others="true">
                                                        <div class="list-todo-icon bg-white <%=fontcolor%>">
                                                            <div class="badge <%=badgecolor%> bold"><%=(i + 1)%></div>
                                                        </div>
                                                        <div class="list-todo-item <%=color%>">
                                                            <a class="list-toggle-container font-white" data-toggle="collapse" href="#task-<%=i%>" aria-expanded="false" data-close-others="true" onclick="return closeanother('task-<%=i%>')">
                                                                <div class="list-toggle <%=done%> uppercase">
                                                                    <div class="row">
                                                                        <div class="col-md-12">
                                                                            <div class="col-md-11">
                                                                                <div class="list-toggle-title bold "><%=dba.getTitolo()%></div>
                                                                            </div>
                                                                            <div class="col-md-1">
                                                                                <div class="badge <%=badgecolor%> pull-right bold"><%=pres_string%></div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </a>
                                                            <div class="task-list panel-collapse collapse <%=in%>" id="task-<%=i%>" aria-expanded="false" style="height: 0px;" data-close-others="true">
                                                                <ul>
                                                                    <li class="task-list-item <%=done%>">
                                                                        <p><%=dba.getInfo()%></p>
                                                                    </li>
                                                                </ul>
                                                                <hr>
                                                                <div class="task-footer bordered">
                                                                    <div class="row">
                                                                        <%
                                                                            if (pres && mod) {
                                                                                if (!domandaCompleta && (dba.getCodicedoc().equals("DONLA")
                                                                                        || dba.getCodicedoc().equals("DONLB") || dba.getCodicedoc().equals("DOCR")
                                                                                        || dba.getCodicedoc().equals("DOCR") || dba.getCodicedoc().equals("DONL"))) {
                                                                        %>
                                                                        <div class="col-md-4">
                                                                            <button class="btn btn-outline green" type="submit" onclick="return submitfor('f3<%=dba.getCodicedoc()%>');">
                                                                                Verifica Documento  <i class="fa fa-download"></i>
                                                                            </button> 
                                                                        </div>
                                                                        <div class="col-md-4">
                                                                            <button class="btn btn-outline red" type="submit" onclick="return confirmrem('f4<%=dba.getCodicedoc()%>');">
                                                                                Elimina documento <i class="fa fa-trash-o"></i>
                                                                            </button>
                                                                        </div>
                                                                        <%  } else {
                                                                            if (domandaCompleta && dba.getCodicedoc().equals("ALB1") || !domandaCompleta && dba.getCodicedoc().equals("ALB1")) {
                                                                        %>
                                                                        <div class="col-md-4">
                                                                            <button class="btn btn-outline blue" type="submit" onclick="return submitfor('x1<%=dba.getCodicedoc()%>');">
                                                                                Visualizza fascicolo/i documenti <i class="fa fa-database"></i>
                                                                            </button>
                                                                        </div>
                                                                        <%
                                                                        } else {
                                                                        %>
                                                                        <div class="col-md-4">
                                                                            <button class="btn btn-outline green" type="submit" onclick="return submitfor('f3<%=dba.getCodicedoc()%>');">
                                                                                Visualizza Documento  <i class="fa fa-download"></i>
                                                                            </button> 
                                                                        </div>
                                                                        <%
                                                                                }
                                                                            }
                                                                        } else {
                                                                            if (mod) {
                                                                                if (dba.getCodicedoc().equals("DONLA")) {
                                                                                    if (esisteAllegatoA) {%>
                                                                        <div class="col-md-4">
                                                                            <button class="btn btn-outline blue" type="submit" onclick="return confirmremAllegatoA('f5<%=dba.getCodicedoc()%>');">
                                                                                Elimina Dati inseriti <i class="fa fa-trash-o"></i>
                                                                            </button>
                                                                        </div>
                                                                        <div class="col-md-4">
                                                                            <button class="btn btn-outline green" type="submit" onclick="return submitfor('f1<%=dba.getCodicedoc()%>');">
                                                                                Scarica Modello <i class="fa fa-download"></i>
                                                                            </button>
                                                                        </div>
                                                                        <div class="col-md-4">
                                                                            <button class="btn btn-outline red" type="submit" onclick="return submitfor('f2<%=dba.getCodicedoc()%>');">
                                                                                Carica <i class="fa fa-upload"></i>
                                                                            </button> 
                                                                        </div>
                                                                        <%} else {%>
                                                                        <div class="col-md-4">
                                                                            <button class="btn btn-outline red" onclick="location.href = 'bando_onlinecomp2.jsp?allegato_A_B=A'">
                                                                                Compila <i class="fa fa-pencil-square-o"></i>
                                                                            </button>   
                                                                        </div>
                                                                        <%}
                                                                            }
                                                                            if (dba.getCodicedoc().equals("DONLB")) {
                                                                                if (esisteAllegatoB) {
                                                                        %>
                                                                        <div class="col-md-4">
                                                                            <button class="btn btn-outline blue" type="submit" onclick="return confirmremAllegatoB('f6<%=dba.getCodicedoc()%>');">
                                                                                Elimina Dati inseriti <i class="fa fa-trash-o"></i>
                                                                            </button>
                                                                        </div>
                                                                        <div class="col-md-4">
                                                                            <button class="btn btn-outline green" type="submit" onclick="return submitfor('f1<%=dba.getCodicedoc()%>');">
                                                                                Scarica Modello <i class="fa fa-download"></i>
                                                                            </button>
                                                                        </div>
                                                                        <div class="col-md-4">
                                                                            <button class="btn btn-outline red" type="submit" onclick="return submitfor('f2<%=dba.getCodicedoc()%>');">
                                                                                Carica <i class="fa fa-upload"></i>
                                                                            </button> 
                                                                        </div>
                                                                        <%
                                                                        } else {
                                                                            if (esisteAllegatoA) {
                                                                        %>
                                                                        <div class="col-md-4">
                                                                            <button class="btn btn-outline red" onclick="location.href = 'bando_onlinecomp2.jsp?allegato_A_B=B'">
                                                                                Compila <i class="fa fa-pencil-square-o"></i>
                                                                            </button>   
                                                                        </div>
                                                                        <%}
                                                                                }
                                                                            }
                                                                            if (dba.getCodicedoc().equals("ALB1")) {
                                                                                if (esisteAllegatoB1) {
                                                                        %>

                                                                        <%
                                                                        } else if (esisteAllegatoB) {
                                                                        %>
                                                                        <div class="col-md-4">
                                                                            <button class="btn btn-outline red" onclick="location.href = 'bando_onlinecomp2.jsp?allegato_A_B=C'">
                                                                                Compila <i class="fa fa-pencil-square-o"></i>
                                                                            </button>   
                                                                        </div>
                                                                        <% }
                                                                            }

                                                                            if (!dba.getDownload().equals("-") && !dba.getCodicedoc().equals("DONLA") && !dba.getCodicedoc().equals("DONLB")) {%>
                                                                        <div class="col-md-4">
                                                                            <button class="btn btn-outline green" type="submit" onclick="return submitfor('f1<%=dba.getCodicedoc()%>');">
                                                                                Scarica Modello <i class="fa fa-download"></i>
                                                                            </button> 
                                                                        </div>
                                                                        <%}
                                                                            if (!dba.getCodicedoc().equals("DONLA") && !dba.getCodicedoc().equals("DONLB") && !dba.getCodicedoc().equals("ALB1")) {%>
                                                                        <div class="col-md-4">
                                                                            <button class="btn btn-outline red" type="submit" onclick="return submitfor('f2<%=dba.getCodicedoc()%>');">
                                                                                Carica <i class="fa fa-upload"></i>
                                                                            </button>
                                                                        </div>
                                                                        <%}
                                                                                }
                                                                            }
                                                                        %>
                                                                        <form name="f1<%=dba.getCodicedoc()%>" role="form" action="Download?action=modellodoc"
                                                                              method="post" target="_blank">
                                                                            <input type="hidden" name="tipodoc" value="<%=dba.getCodicedoc()%>"/>
                                                                        </form>
                                                                        <form name="f2<%=dba.getCodicedoc()%>" role="form" action="bando_updoc.jsp" method="post">
                                                                            <input type="hidden" name="tipodoc" value="<%=dba.getCodicedoc()%>"/>
                                                                        </form>
                                                                        <form name="f3<%=dba.getCodicedoc()%>" role="form" action="Download?action=docbando" method="post" target="_blank">
                                                                            <input type="hidden" name="tipodoc" value="<%=dba.getCodicedoc()%>"/>
                                                                            <input type="hidden" name="tipologia" value="<%=tipol%>"/>
                                                                        </form>
                                                                        <form name="f4<%=dba.getCodicedoc()%>" role="form" action="Operazioni?action=remdoc" method="post">
                                                                            <input type="hidden" name="tipodoc" value="<%=dba.getCodicedoc()%>"/>
                                                                            <input type="hidden" name="tipologia" value="<%=tipol%>"/>
                                                                        </form>
                                                                        <!--// ELEMINATA I DATI DI ALLEGATO A-->
                                                                        <form id="f5<%=dba.getCodicedoc()%>" name="f5<%=dba.getCodicedoc()%>" role="form" action="Operazioni?action=remdatiAllegatoA" method="post"></form>
                                                                        <!--// ELEMINATA I DATI DI ALLEGATO B-->
                                                                        <form id="f6<%=dba.getCodicedoc()%>" name="f6<%=dba.getCodicedoc()%>" role="form" action="Operazioni?action=remdatiAllegatoB" method="post"></form>
                                                                        <form name="x1<%=dba.getCodicedoc()%>" role="form" action="bando_onlinecomp2.jsp" method="post">
                                                                            <input type="hidden" name="allegato_A_B" value="C"/>
                                                                        </form>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </li>
                                                    <%}%>
                                                </ul>
                                            </div>
                                        </div>
                                        <hr>
                                        <div class="row ">
                                            <div class="col-md-12">
                                                <div class="col-md-4">
                                                    <div class="row">
                                                        <%if (prc == 100) {%>
                                                        <form role="form" action="Download?action=zipdomanda" method="post" target="_blank">
                                                            <input type="hidden" name="userdoc" value="<%=username%>"/>
                                                            <button  type="submit" class="btn blue btn-block btn-outline "> <i class="fa fa-download"></i> Scarica l'intera domanda (.zip)</button>
                                                        </form>
                                                        <%}%>
                                                    </div>
                                                </div>
                                                <div class="col-md-4"></div>
                                                <div class="col-md-4">
                                                    <div class="easy-pie-chart">
                                                        <div class="number visits" data-percent="<%=prc%>"><span><%=prc%></span>%</div>
                                                        Percentuale di completamento domanda
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <%

                                String txts = "Invia domanda";
                                String dis = "disabled";
                                String classinvio = "bg-grey-silver";
                                String datacontent1 = "IMPOSSIBILE INVIARE.";
                                if (abilitainvio && !isDomandaPresente) {
                                    if (prc < 100) {
                                        txts = "Invia domanda (INCOMPLETA)";
                                        classinvio = "bg-yellow";
                                        datacontent1 = "Occorre allegare tutti i documenti obbligatori richiesti dal bando";
                                    } else {
                                        dis = "";
                                        if (mod == false) {
                                            abilitainvio = false;
                                            classinvio = "bg-grey-silver";
                                            datacontent1 = "La domanda ed i suoi allegati sono stati già inviati.";
                                        } else {
                                            classinvio = "bg-green-jungle";
                                            datacontent1 = "La domanda ed i suoi allegati sono pronti per essere inviati.";
                                        }
                                    }
                                }

                                String txtann = "";
                                if (doco == null || doco.getConsolidato().equals("0")) {
                                    txtann = "Consente di eliminare in maniera definitva la domanda e/o le operazioni effettuate.";
                                } else {
                                    txtann = "Non è possibile annullare la domanda in quanto in fase di istruttoria.";
                                }

                            %>
                            <div class="col-md-3">    
                                <div class="row">
                                    <form role="form" name="fsend" id="fsend"
                                          action="Operazioni?action=inviadomanda"
                                          method="post" 
                                          <%if (abilitainvio && !isDomandaPresente) {%>
                                          onsubmit="return confirmsend();"
                                          <%} else {%>
                                          onsubmit="return false;"
                                          <%}%>
                                          >
                                        <div class="modal fade">
                                            <button type="submit" id="fsendb" />
                                        </div>
                                        <div class="col-md-12">
                                            <div class="portlet light">
                                                <a <%=dis%> class="mt-element-list popovers" style="text-decoration: none;" onclick="return document.getElementById('fsendb').click();"
                                                            data-trigger="hover" data-container="body" data-placement="bottom"
                                                            data-content="<%=datacontent1%>" 
                                                            data-original-title="Invio definitivo della domanda di partecipazione">
                                                    <div class="mt-list-head list-news font-white <%=classinvio%>">
                                                        <div class="list-head-title-container">
                                                            <br>
                                                            <h2 class="list-title uppercase"><i class="fa fa-save"></i> <%=txts%></h2>
                                                            <br>
                                                        </div>
                                                    </div>
                                                </a>
                                            </div>
                                        </div>
                                    </form>
                                    <div class="col-md-12">
                                        <hr>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="portlet light bordered">
                                            <div class="portlet-title">
                                                <div class="caption font-blue"><i class="fa fa-archive font-blue"></i><a href="<%=request.getContextPath() + "/Download?action=avviso"%>" target="_blank"> Scarica Avviso</a></div>
                                            </div>
                                            <div class="portlet-title">
                                                <div class="caption font-blue"><i class="fa fa-info-circle font-blue"></i>Informazioni Bando</div>
                                            </div>
                                            <div class="portlet-body">
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <p class="text-justify">
                                                            <strong>Il bando &#232; attivo.</strong> 
                                                        </p>
                                                        <p class="text-justify"> 
                                                            <strong >Nome bando: </strong> <%=descbando%>.
                                                        </p>
                                                        <p class="text-justify">
                                                            <strong>Per la scadenza del bando, consultare il sito dell'Ente Nazionale per il microcredito.</strong>
                                                        </p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%}%>
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



            <script type="text/javascript" src="assets/seta/js/jquery-1.10.1.min.js"></script>
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
    </body>
</html>
<%}%>
