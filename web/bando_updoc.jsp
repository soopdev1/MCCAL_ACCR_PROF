<%-- 
    Document   : bando_index2
    Created on : 28-lug-2017, 15.43.51
    Author     : rcosco
--%>

<%@page import="com.seta.entity.Domandecomplete"%>
<%@page import="com.seta.entity.Docbandi"%>
<%@page import="com.seta.entity.Docuserbandi"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.seta.action.Liste"%>
<%@page import="com.seta.action.ActionB"%>
<%@page import="com.seta.util.Utility"%>
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
        <title>YES I START UP CALABRIA - Donne e Disoccupati</title>
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


        <%
            int dim = ActionB.getDimMaxFiles();
            String dimetichetta = ActionB.getDimMaxFilesEtichetta();

        %>

        <script type="text/javascript">
            function controlla(coddoc) {

                var colleg = document.getElementById("colleg").value;

                var filedoc = document.f1.file.value;
                var ext = filedoc.substr(filedoc.lastIndexOf('.')).toLowerCase();
                var dimmax =<%=dim%>;
                var dimmaxetich =<%=dimetichetta%>;
                if (filedoc === "" || filedoc === "...") {
                    document.getElementById("valoretxt").innerHTML = "Impossibile procedere. Scegliere il file da caricare.";
                    document.getElementById("static").className = document.getElementById("static").className + " in";
                    document.getElementById("static").style.display = "block";
                    return false;
                } else if (ext !== ".pdf" && ext !== ".p7m") {
                    document.getElementById("valoretxt").innerHTML = "Impossibile procedere. Estensione File non consentita. E' possibile caricare solamente file in formato PDF/.p7m.";
                    document.getElementById("static").className = document.getElementById("static").className + " in";
                    document.getElementById("static").style.display = "block";
                    return false;
                    //} else if (document.f1.file.files[0].size > 4194304) {
                } else if (document.f1.file.files[0].size > dimmax) {
                    document.getElementById("valoretxt").innerHTML = "Impossibile procedere. Dimensione File non consentita. Il file supera la dimensione massima consentita (" + dimmaxetich + " MB).";
                    document.getElementById("static").className = document.getElementById("static").className + " in";
                    document.getElementById("static").style.display = "block";
                    return false;
                } else if (colleg === "1") {
                    var note = document.getElementById("note").value;
                    if (note === "") {
                        document.getElementById("valoretxt").innerHTML = "E' obbligatorio inserire la nota sulla tipologia di documento da caricare.";
                        document.getElementById("static").className = document.getElementById("static").className + " in";
                        document.getElementById("static").style.display = "block";
                        return false;
                    }
                }

            }

            function dismiss(name) {
                document.getElementById(name).className = "modal fade";
                document.getElementById(name).style.display = "none";
            }

            function confirmElimina(tipodoc, tipologia, note) {

                document.getElementById("tipodoc").value = tipodoc;
                document.getElementById("tipologia").value = tipologia;
                document.getElementById("notemodal").value = note;

                document.getElementById("msgcompil").innerHTML = "";
                document.getElementById("confirm2").className = document.getElementById("confirm").className + " in";
                document.getElementById("confirm2").style.display = "block";

            }


        </script>


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

                    <div id="static" class="modal fade" tabindex="-1" data-backdrop="static" data-keyboard="false">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title">Errore</h4>
                                </div>
                                <div class="modal-body">
                                    <p id="valoretxt">

                                    </p>
                                </div>
                                <div class="modal-footer" id="groupbtn2">
                                    <button type="button" data-dismiss="modal" class="btn red" onclick="return dismiss('static');">Chiudi</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div id="confirm" class="modal fade" tabindex="-1" data-backdrop="confirm" data-keyboard="false">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title">Verifica Compilazione</h4>
                                </div>
                                <div class="modal-body">
                                    <p>
                                        Verificare che tutti i campi siano compilati correttamente.
                                    </p>
                                    <p id="msgcompil">

                                    </p>
                                </div>
                                <div class="modal-footer" id="groupbtn2">
                                    <!--<button type="button" class="btn green" onclick="return submitfor();">SI</button>-->
                                    <button type="button" data-dismiss="modal" class="btn red" onclick="return dismiss('confirm');">Chiudi</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="confirm2" class="modal fade" tabindex="-1" data-backdrop="confirm" data-keyboard="false">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title">Conferma Eliminazione</h4>
                                </div>
                                <div class="modal-body">
                                    <p>
                                        Sei sicuro di voler procedere alla cancellazione del file?
                                    </p>
                                    <p id="msgcompil">

                                    </p>
                                </div>
                                <div class="modal-footer" id="groupbtn2">
                                    <form action="Operazioni?action=remdocAltri" method="post">
                                        <input type="hidden" name="tipologia" id="tipologia">
                                        <input type="hidden" name="tipodoc" id="tipodoc">
                                        <input type="hidden" name="notemodal" id="notemodal">                        
                                        <button type="submit" class="btn green"><i class="fa fa-check"></i> SI</button>                       
                                        <button type="button" data-dismiss="modal" class="btn red" onclick="return dismiss('confirm2');"><i class="fa fa-times"></i> NO</button>
                                    </form>
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
                        String coddoc = request.getParameter("tipodoc");

                        String bandorif = session.getAttribute("bandorif").toString();
                        String username = session.getAttribute("username").toString();
                        ArrayList<Docbandi> lid1 = ActionB.listaDocRichiesti(bandorif);
                        Docbandi d = null;
                        for (int i = 0; i < lid1.size(); i++) {
                            if (lid1.get(i).getCodicedoc().equals(coddoc)) {
                                d = lid1.get(i);
                            }
                        }

                        Domandecomplete doco = ActionB.isPresenteDomandaCompleta(bandorif, username);
                        boolean mod = true;
                        if (doco != null) {
                            mod = false;
                        }

                        if (d != null) {%>
                    <input type="hidden" id="colleg" value="<%=d.getCollegati()%>"/>
                    <%
                        if (d.getCollegati().equals("1")) {
                            ArrayList<Docuserbandi> li = ActionB.listaDocuserbando(bandorif, username, coddoc);
                            if (li.size() > 0) {%>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-12">
                                <div class="portlet box green">
                                    <div class="portlet-title">
                                        <div class="caption"><i class="fa fa-file"></i>Allegati già caricati</div>
                                        <div class="actions">
                                            <div class="btn-group">
                                                <a class="btn green tooltips" href="bando_updoc.jsp?tipodoc=<%=coddoc%>" data-placement="top" data-original-title="Aggiorna" >
                                                    <i class="fa fa-refresh"></i>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="portlet-body" id="dvData">
                                            <table class="table table-striped table-bordered table-hover" id="sample_1">
                                                <thead>
                                                    <tr>
                                                        <th><b>Tipologia documentale</b></th>
                                                        <th><b>Denominazione Allegato</b></th>
                                                        <th><b>Data Caricamento</b></th>
                                                        <th><b>Azioni</b></th>
                                                    </tr>
                                                </thead>
                                                <tbody style="text-align:center;">
                                                    <%
                                                        for (int i = 0; i < li.size(); i++) {%>
                                                    <tr>
                                                        <td><%=d.getTitolo()%></td> 
                                                        <td><%=li.get(i).getNote()%></td> 
                                                        <td><%=Utility.formatTimestamp(li.get(i).getDatacar())%></td> 
                                                        <td>
                                                            <div class="clearfix">
                                                                <div class="task-config">
                                                                    <a target="_blank" 
                                                                       href="<%="Download?action=allegatobando&tipodoc=" + li.get(i).getCodicedoc() + "&tipologia=" + li.get(i).getTipodoc() + "&path=" + li.get(i).getPath()%>" class="btn btn-default btn-xs green">
                                                                        <i class='fa fa-download'></i> Verifica Documento Caricato</a>
                                                                        <%if (mod) {%>                                                                
                                                                    <a class="btn btn-default btn-xs red" onclick="return confirmElimina('<%=li.get(i).getCodicedoc()%>', '<%=li.get(i).getTipodoc()%>', '<%=li.get(i).getNote()%>');">
                                                                        <i class='fa fa-trash-o'></i> Elimina</a>
                                                                        <%}%>
                                                                </div>
                                                            </div>
                                                        </td> 
                                                    </tr>
                                                    <%}%>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%}
                        }
                    %>
                    <div class="col-md-12">
                        <form action="Upload?action=simple" method="POST" enctype="multipart/form-data" name="f1" onsubmit="return controlla('<%=coddoc%>');">
                            <input type="hidden" name="tipodoc" value="<%=coddoc%>" />

                            <div class="portlet light bordered">
                                <div class="portlet-title">
                                    <div class="caption font-blue"><i class="fa fa-upload font-blue"></i>Carica documento: <%=d.getTitolo()%></div>
                                    <div class="tools"> 
                                        <button type="submit" class="btn blue"><i class="fa fa-upload"></i> Carica</button>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <%
                                                if (d.getCollegati().equals("1")) {%>
                                            <span class="help-block">
                                                Note <b style="color: red;">*</b> (da indicare per distinguere da eventuali altri allegati dello stesso tipo) 
                                            </span>
                                            <input class="form-control" id="note" name="note" placeholder="...." type="text" maxlength="100" onchange="return fieldNoEuro(this.id);"/>
                                            <%}%>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label class="control-label col-md-1">File <b style="color: red;">*</b> </label>
                                                <div class="col-md-11">
                                                    <div class="fileinput fileinput-new" data-provides="fileinput">
                                                        <div class="input-group input-large">
                                                            <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                                                <i class="fa fa-file fileinput-exists"></i>&nbsp;
                                                                <span class="fileinput-filename"> </span>
                                                            </div>
                                                            <span class="input-group-addon btn default btn-file">
                                                                <span class="fileinput-new"> Select file </span>
                                                                <span class="fileinput-exists"> Change </span>
                                                                <input type="file" name="file"> </span>
                                                            <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Remove </a>

                                                        </div>

                                                    </div>

                                                </div>
                                            </div>

                                            <br>
                                            <hr>


                                        </div>
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <a class="btn red" href="bando_index.jsp"> Indietro <i class="fa fa-arrow-left"></i> </a> 
                                            </div>
                                        </div>
                                    </div>



                                </div>
                            </div>
                    </div>                        
                    </form>


                    <%
                        String es = request.getParameter("esito");
                        if (es != null) {
                            String msg = "";
                            String cl = "";
                            if (es.equals("0")) {
                                cl = "alert-success";
                                msg = "Documento eliminato con successo.";
                            }
                            if (es.equals("ok")) {
                                cl = "alert-success";
                                msg = "Documento caricato con successo.";
                            }
                            if (es.equals("1")) {
                                cl = "alert-danger";
                                msg = "Errore durante il caricamento del file. File corrotto o non conforme (verificare dimensione del File). Riprovare.";
                            }
                            if (es.equals("2A")) {
                                cl = "alert-danger";
                                msg = "Errore. Il file caricato non contiene field leggibili, accertarsi di aver caricato il file corretto.";
                            }
                            if (es.equals("2B")) {
                                cl = "alert-danger";
                                msg = "Errore. Il file caricato non contiene il barcode valido, accertarsi di aver caricato il file corretto.";
                            }
                            if (es.equals("3A")) {
                                cl = "alert-danger";
                                msg = "Impossibile caricare il documento. Contattare l'amministratore del sistema. <u>hdpllcalabria@gmail.com</u>";
                            }
                            if (es.equals("3B")) {
                                cl = "alert-danger";
                                msg = "Impossibile caricare il documento. Allegato gi&#224; presente, &#232; necessario eliminare il documento prima di caricarne uno nuovo dello stesso tipo.";
                            }
                            if (es.equals("4")) {
                                cl = "alert-danger";
                                msg = "Impossibile caricare il documento. Contattare l'amministratore del sistema. <u>hdpllcalabria@gmail.com</u>";
                            }
                            if (es.equals("2")) {
                                cl = "alert-danger";
                                msg = "File corrotto/i o non disponibile/i. Riprovare nuovamente.";
                            }
                            if (es.equals("10")) {
                                cl = "alert-danger";
                                msg = "Errore durante il caricamento del file. Allegato già presente, modificare.";
                            }
                            if (es.equals("11")) {
                                cl = "alert-danger";
                                msg = "Errore durante il caricamento del file. La dimensione del File è maggiore di quella consentita (4 MB).";
                            }
                            if (es.equals("90")) {
                                cl = "alert-danger";
                                msg = "Errore durante il caricamento del file. Il file (.pdf) non contiene firme valide.";
                            }
                            if (es.equals("91")) {
                                cl = "alert-danger";
                                msg = "Errore durante il caricamento del file. Il file non è conforme ad un PDF/A.";
                            }
                            if (es.equals("KOO")) {
                                cl = "alert-danger";
                                msg = "Impossibile salvare il cronoprogramma. Riprovare.";
                            }
                    %>
                    <br>

                    <div class="col-md-12">
                        <br>
                        <div class="alert <%=cl%>" style="text-align: center;">
                            <strong><%=msg%></strong>
                        </div>
                    </div>

                    <%}%>

                </div>


                <%}%>


            </div>
            <!-- END CONTENT -->
            <!-- BEGIN QUICK SIDEBAR -->
            <!-- END QUICK SIDEBAR -->
        </div>
        <!-- END CONTAINER -->
        <!-- BEGIN FOOTER -->
        <div class="page-footer">
            <div class="page-footer-inner"> YES I START UP CALABRIA - Donne e Disoccupati v. 1.0.0</div>
            <div class="scroll-to-top">
                <i class="icon-arrow-up"></i>
            </div>
        </div>



        <script src="assets/global/plugins/jquery.min.js" type="text/javascript"></script>
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
