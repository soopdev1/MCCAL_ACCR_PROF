<%@page import="rc.so.action.Constant"%>
<%@page import="rc.so.action.ActionB"%>
<%@page import="java.util.ArrayList"%>
<%@page import="rc.so.entity.Registrazione"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->

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

        <!--<script src="https://www.google.com/recaptcha/api.js"></script>-->
        <script src="https://www.google.com/recaptcha/api.js?render=6Lfr-eMUAAAAACNdFH7tsZvaVg9gGOrCl0Wj_FW3"></script>
    <script id="registration" src="assets/seta/js/Registration.js" data-context="<%=request.getContextPath()%>" data-gKey="6Lfr-eMUAAAAACNdFH7tsZvaVg9gGOrCl0Wj_FW3" type="text/javascript"></script>
        <script src="assets/seta/js/jscontrolli.js"></script>
        <script src="assets/seta/js/validate.min.js"></script>
        <script type="text/javascript">
            <%
                String bando = request.getParameter("bando");
                if (bando == null || bando.equalsIgnoreCase("null")) {
                    bando = Constant.bando;
                }
                ArrayList<Registrazione> lijs = ActionB.listaCampiReg(bando, null);
            %>

            function checkEmail(email) {
                if (email !== "") {
                    var v1 = validate.single(email, {presence: true, email: true});
                    if (undefined !== v1) {
                        return false;
                    }
                }
                return true;
            }
            function fieldOnlyNumber(fieldid) {
                var stringToReplace = document.getElementById(fieldid).value;
                stringToReplace = stringToReplace.replace(/\D/g, '');
                document.getElementById(fieldid).value = stringToReplace;
            }
            function controllaReg() {
                var output = "0";
                var msg = "";
            <%if (lijs.size() > 0) {
                    for (int js = 0; js < lijs.size(); js++) {
                        Registrazione rg = lijs.get(js);
                        String desc = rg.getEtichetta();
                        if (desc.length() > 20) {
                            desc = desc.substring(0, 20).replaceAll("[-+.^:,]", "") + "...";
                        }
            %>
                var idfield = "<%=rg.getCampo()%>";
                var obfield = "<%=rg.getObbligatorio()%>";
                var desc = "<%=desc%>";
                var typefield = "<%=rg.getTipocampo()%>";
                var maxlengthfield = parseInt("<%=rg.getLunghezza()%>");
                var valfield = document.getElementById(idfield).value.trim();
                if (obfield === "SI") {
                    if (idfield !== 'tso' || idfield !== 'settore' || idfield !== 'numdipsedeoper') {
                        if (valfield === "" || valfield === "...") {
                            msg += "Il campo <span style='color:red;'>" + desc + "</span> &#232; obbligatorio.<br>";
                            output = "1";
                        }
                    }
                }
                if (typefield === "0002") { //numerico
                    if (valfield.length > 0) {
                        if (!isNumeric(valfield)) {
                            msg += "Il campo <span style='color:red;'>" + desc + "</span> accetta solo valori numerici.<br>";
                            output = "2"; //CAMPO NUMERICO ERRATO
                        }
                    }
                }

                if (typefield === "0002") { //numerico
                    if (idfield === 'cell') {
                        if (valfield.length < 7 || !valfield.startsWith("3")) {
                            msg += "Il campo <span style='color:red;'>" + desc + "</span> deve contenere un numero di cellulare.<br>";
                            output = "2"; //CAMPO NUMERICO ERRATO                        
                        }
                    }
                }
                if (typefield === "0003") { //CODICE FISCALE
                    if (valfield.length > 0) {
                        if (!checkCF(valfield)) {
                            msg += "Il campo <span style='color:red;'>" + desc + "</span> &#232; errato in quanto non conforme ad un Codice Fiscale.<br>";
                            output = "3"; //CAMPO CF ERRATO
                        }
                    }
                }

                if (typefield === "0004") { //MAIL
                    if (valfield.length > 0) {
                        if (!checkMail(valfield)) {
                            msg += "Il campo <span style='color:red;'>" + desc + "</span> &#232; errato in quanto non conforme ad un indirizzo Email/PEC.<br>";
                            output = "4"; //CAMPO MAIL ERRATO
                        }
                    }
                }
                if (typefield === "0006") { //DATA
                    if (valfield.length > 0) {
                        if (!checkDate(valfield)) {
                            msg += "Il campo <span style='color:red;'>" + desc + "</span> &#232; errato in quanto non conforme ad una Data.<br>";
                            output = "5"; //CAMPO DATA ERRATO
                        }
                    }
                }
                if (typefield === "0008") { //PARTITA IVA
                    if (valfield.length > 0) {
                        if (!checkIva(valfield)) {
                            msg += "Il campo <span style='color:red;'>" + desc + "</span> &#232; errato in quanto non conforme ad una Partita IVA.<br>";
                            output = "5"; //CAMPO DATA ERRATO
                        }
                    }
                }
                if (maxlengthfield > 0) {
                    if (valfield.length > maxlengthfield) {
                        msg += "Il campo <span style='color:red;'>" + desc + "</span> &#232; errato in quanto supera la lungezza massima consentita (" + maxlengthfield + " Caratteri).<br>";
                        output = "6"; //CAMPO TROPPO LUNGO
                    }
                }

                // if (idfield === "cf") { //controllo cf presente METTERE ct
                if (idfield === "cfdsadsadsadsasaddasdadssaddsadsadadasads") {

                }
            <%}
                }%>
                var privacy1 = document.getElementById("privacy1").checked;
                if (!privacy1) {
                    msg += "Il campo <span style='color:red;'>Privacy</span> &#232; obbligatorio.<br>";
                    output = "1";
                }
                /*
                 var response = grecaptcha.getResponse();
                 if (response.length === 0) {
                 msg += "Il campo <span style='color:red;'>Codice di Controllo</span> &#232; obbligatorio.<br>";
                 output = "1";
                 }
                 */
                var cf = document.getElementById('cf').value;
                if (cf.length > 0) {
                    var x = 0;
                    if (checkCF(cf)) {
                        x = 1;
                    }
                    if (checkIva(cf)) {
                        x = 1;
                    }
                    if (x === 0) {
                        msg += "Il campo <span style='color:red;'>Codice Fiscale Ente</span> &#232; errato in quanto non conforme ad una Partita IVA o ad un Codice Fiscale.<br>";
                        output = "5"; //CAMPO DATA ERRATO
                    }
                }
                grecaptcha.execute(g_key).then(function (token) {
                    $('#g-recaptcha-response').val(token);
                });
                if (output !== "0") {
                    document.getElementById("valoretxt").innerHTML = msg;
                    document.getElementById("static").className = document.getElementById("static").className + " in";
                    document.getElementById("static").style.display = "block";
                    document.getElementById("btnmodal1").focus();
                    return false;
                }
                document.getElementById("confirm").className = document.getElementById("confirm").className + " in";
                document.getElementById("confirm").style.display = "block";
                return false;
            }
            function submitfor() {
                
                document.forms["formregist"].submit();
            }
        </script>
    </head>
    <!-- END HEAD -->
    <body class="page-full-width page-header-fixed page-content-white"> <!onload="return soggetti();">->
    <!-- BEGIN HEADER -->
    <div class="modal fade" id="static" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">

                    <h4 class="modal-title font-red uppercase"><b>Errore</b></h4>
                </div>
                <div class="modal-body" id="valoretxt"></div>
                <div class="modal-footer">
                    <button type="button" data-dismiss="modal" class="btn btn-outline red" id="btnmodal1" onclick="return dismiss('static');">Chiudi</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <div id="confirm" class="modal fade" tabindex="-1" data-backdrop="confirm" data-keyboard="false">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Conferma Accreditamento</h4>
                </div>
                <div class="modal-body">
                    <p>
                        Sei sicuro di voler procedere con l'accreditamento? L'operazione non potr&#224; pi&#249; essere annullata.
                    </p>
                </div>
                <div class="modal-footer" id="groupbtn2">
                    <button type="button" class="btn green" onclick="return submitfor();">SI</button>
                    <button type="button" data-dismiss="modal" class="btn red" onclick="return dismiss('confirm');">NO</button>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="menu/menu1.jsp" %>

    <!-- END HEADER -->
    <!-- BEGIN HEADER & CONTENT DIVIDER -->
    <div class="clearfix"> </div>
    <!-- END HEADER & CONTENT DIVIDER -->
    <!-- BEGIN CONTAINER -->
    <div class="page-container">
        <!-- BEGIN MENU -->

        <!-- BEGIN CONTENT -->
        <div class="page-content-wrapper">
            <!-- BEGIN CONTENT BODY -->
            <div class="page-content">
                <!-- BEGIN PAGE HEADER-->
                <!-- BEGIN THEME PANEL -->
                <!--    VUOTO RAF  -->
                <!-- END THEME PANEL -->
                <!-- BEGIN PAGE BAR -->
                <!-- END PAGE BAR -->
                <!-- BEGIN PAGE TITLE-->
                <div class="container min-hight">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-3"><h3 class="page-title">Accreditamento</h3> </div>
                            <div class="col-md-6"><center>
                                    <table>
                                        <tr>
                                            <td><img src="assets/seta/img/uneur-small.png" alt="" height="74px" width="74px"/></td>
                                            <td><img src="assets/seta/img/tes1t.png" alt="" height="74px" width="74px"/></td>
                                            <td><img src="assets/seta/img/LogoRegCal.gif" alt="" height="75px" width="75px"/></td>
                                            <td><img src="assets/seta/img/Immagine2.png" alt="" height="75px" width="75px"/></td>
                                        </tr>
                                    </table>
                                </center> 
                            </div>
                            <div class="col-md-3" style="text-align: right;"><img src="assets/seta/img/logo_blue_1.png" alt="logo" height="74px" class="img-responsivelogo"/></div> 
                        </div>                         
                    </div>


                    <!-- END PAGE TITLE -->
                    <!-- END PAGE HEADER -->
                    <!-- SELECT BUY/SELL -->
                    <%
                        //String bando = request.getParameter("bando");
                        //String descbando = ActionB.getDescrizioneBando(bando);
                        String descbandoB = ActionB.getDescrizioneBando("temp");
                        boolean attivo = ActionB.verificaBando(bando);
                        String es = request.getParameter("esito");
                        if (es != null) {
                            String msg = "";
                            String cl = "";
                            if (es.equals("0")) {
                                cl = "alert-success";
                                msg = "successo.";
                            }
                            if (es.equals("KO1")) {
                                cl = "alert-danger";
                                msg = "Si &#232; verificato un errore durante l'inserimento dei dati, riprovare.";
                            }
                            if (es.equals("KO2")) {
                                cl = "alert-danger";
                                msg = "Si &#232; verificato un errore durante l'inserimento dei dati, riprovare.";
                            }
                    %>
                    <br>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert <%=cl%>" style="text-align: center;">
                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true"></button>
                                <strong><%=msg%></strong>
                            </div>
                        </div>
                    </div>
                    <%}%>

                    <br>    

                    <div class="row lead">
                        <div class="col-md-12 text-center">
                            <%=descbandoB%>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <!-- BEGIN LEFT SIDEBAR -->   
                        <div class="row lead text-center">
                            <%if (attivo) { %>
                            <div class="col-md-12">
                                <em><i class="fa fa-check-circle font-green"></i></em>
                                <span class="bold">L'accreditamento a questo bando è attivo</span>
                            </div>
                            <%} else {%>
                            <div class="col-md-12">
                                <em><i class="fa fa-exclamation-circle font-red"></i></em>
                                <span class="bold">L'accreditamento a questo bando non è disponibile.</span>
                            </div>
                            <%}%>
                        </div>
                        <!-- END LEFT SIDEBAR -->
                        <!-- BEGIN RIGHT SIDEBAR -->            
                        <!-- END RIGHT SIDEBAR -->            
                    </div>
                    <%if (attivo) {%>
                    <hr>
                    <div class="row">
                        <div class="col-md-12">
                            <!-- <h4>Se hai dubbi nella compilazione clicca <a href="#faqq">qui</a> per consultare le FAQ.</h4> -->
                        </div>
                    </div>
                    <%ArrayList<Registrazione> li = ActionB.listaCampiReg(bando, null);
                        if (li.size() > 0) {
                    %>
                    <div class="row breadcrumbs margin-bottom-40">
                        <div class="col-md-12">
                            <h3 class="bold text-center">Dati </h3>
                        </div>
                    </div>
                    <form name="formregist" role="form" enctype="multipart/form-data" action="<%= "Operazioni?action=reg"%>" method="post" onsubmit="return controllaReg();">
                        <input type="hidden" name="g-recaptcha-response" id="g-recaptcha-response">
                        <input type="hidden" name="bandorif" value="<%=bando%>"/>
                        <div class="row">
                            <%
                                for (int i = 0; i < li.size(); i++) {
                                    Registrazione re = li.get(i);
                                    boolean obbl = re.getObbligatorio().equals("SI");
                            %>
                            <div class="col-md-6" id="colid_<%=re.getCampo()%>">
                                <div class="form-body">
                                    <div class="form-group">

                                        <%if (re.getTipocampo().equals("0018")) {%>
                                        <div id="s1l"> 
                                            <label for="<%=re.getCampo()%>"><%=re.getEtichetta()%> <%if (obbl) {%><span style="color: red;">*</span><%}%></label>
                                        </div>
                                        <%} else {%>

                                        <label for="<%=re.getCampo()%>"><%=re.getEtichetta()%> <%if (obbl) {%><span style="color: red;">*</span><%}%></label>
                                        <%}%>
                                        <%if (re.getEtichetta().equals("Numero Cellulare")) {%>
                                        <i class="fa fa-info-circle popovers" data-trigger="hover" data-container="body" data-placement="bottom"
                                           data-content="Verrà inviato un codice OTP da inserire al primo accesso" 
                                           data-original-title="Numero Cellulare"></i>
                                        <%}%>
                                        <%if (re.getEtichetta().equals("Indirizzo Email")) {%>
                                        <i class="fa fa-info-circle popovers" data-trigger="hover" data-container="body" data-placement="bottom"
                                           data-content="Verranno inviate le credenziali di accesso" 
                                           data-original-title="Indirizzo Email"></i>
                                        <%}%>
                                        <%if (re.getEtichetta().equals("Numero documento riconoscimento")) {%>
                                        <i class="fa fa-info-circle popovers" data-trigger="hover" data-container="body" data-placement="bottom"
                                           data-content="Deve essere indicato il numero del documento di riconoscimento che verrà poi caricato 
                                           in seguito in fase di invio della domanda" 
                                           data-original-title="Numero documento riconoscimento."></i>
                                        <%}%>
                                        <%if (re.getTipocampo().equals("0001") || re.getTipocampo().equals("0003") || re.getTipocampo().equals("0015") || re.getTipocampo().equals("0008")) {%>
                                        <input class="form-control" id="<%=re.getCampo()%>" name="<%=re.getCampo()%>" placeholder="...." type="text"  onchange="return fieldNoEuro(this.id);"/>
                                        <%}%>
                                        <%if (re.getTipocampo().equals("0002")) {%>
                                        <input class="form-control" id="<%=re.getCampo()%>" name="<%=re.getCampo()%>" placeholder="...." type="text"  onchange="return fieldOnlyNumber(this.id);"/>
                                        <%}%>
                                        <%if (re.getTipocampo().equals("0004")) {%>
                                        <input class="form-control" id="<%=re.getCampo()%>" name="<%=re.getCampo()%>"  placeholder="...." type="text" onchange="return fieldNoEuroMail(this.id);"/>
                                        <%}%>
                                        <%if (re.getTipocampo().equals("0005")) {%>
                                        <select id="<%=re.getCampo()%>" name="<%=re.getCampo()%>" class="form-control select2" data-placeholder="...">
                                            <option value="">...</option>
                                            <% ArrayList<String[]> prov = ActionB.province();
                                                for (int p = 0; p < prov.size(); p++) {%>
                                            <option value="<%=prov.get(p)[0]%>"><%=prov.get(p)[1]%></option>
                                            <%}%>
                                        </select>
                                        <%}%>        
                                        <%if (re.getTipocampo().equals("0006")) {%>
                                        <input class="form-control form-control-inline date-picker" size="16" type="text" id="<%=re.getCampo()%>" name="<%=re.getCampo()%>"/>
                                        <%}%>

                                        <%if (re.getTipocampo().equals("0013")) {%>
                                        <select id="<%=re.getCampo()%>" name="<%=re.getCampo()%>" class="form-control select2" data-placeholder="...">
                                            <option value="">...</option>
                                            <% ArrayList<String[]> tipodoc = ActionB.tipoDoc();
                                                for (int p = 0; p < tipodoc.size(); p++) {%>
                                            <option value="<%=tipodoc.get(p)[0]%>"><%=tipodoc.get(p)[1]%></option>
                                            <%}%>
                                        </select>
                                        <%}%>
                                        <%if (re.getTipocampo().equals("0009")) {%>
                                        <select id="<%=re.getCampo()%>" name="<%=re.getCampo()%>" class="form-control select2" data-placeholder="...">
                                            <option value="">...</option>
                                            <% ArrayList<String[]> statieur = ActionB.statieur();
                                                for (int p = 0; p < statieur.size(); p++) {%>
                                            <option value="<%=statieur.get(p)[0]%>"><%=statieur.get(p)[1]%></option>
                                            <%}%>
                                        </select>
                                        <%}%>
                                        <%if (re.getTipocampo().equals("0020")) {%>
                                        <select id="<%=re.getCampo()%>" name="<%=re.getCampo()%>" class="form-control select2" data-placeholder="...">
                                            <% ArrayList<String> Soggetti = ActionB.SiNo();
                                                for (int p = 0; p < Soggetti.size(); p++) {%>
                                            <option value="<%=Soggetti.get(p)%>"><%=Soggetti.get(p)%></option>
                                            <%}%>
                                        </select>
                                        <%}%>
                                    </div>
                                </div>
                            </div>
                            <%}%>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-body">
                                    <div class="form-group text-justify">
                                        <label>Privacy <span style="color: red;">*</span></label>
                                        <div class="md-checkbox">
                                            <input type="checkbox" name="privacy1" id="privacy1" class="md-checkbox" /> 
                                            <label for="privacy1">
                                                <span></span>
                                                <span class="check"></span>
                                                <span class="box"></span> ai sensi dell'art. 13, del D.Lgs n. 196/2003, recante "Codice in materia di protezione dei dati personali", l'Amministrazione utilizzerà i dati acquisiti, esclusivamente per le finalità relative all'Avviso Pubblico per il quale gli stessi vengono comunicati, secondo le modalità previste dalle leggi e dai regolamenti vigenti.
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-md-12">
                                <center><button type="submit" class="btn btn-lg red btn-block"><i class="fa fa-save"></i> Procedi</button></center>
                            </div>                            
                        </div> 

                    </form>
                    <!-- END BEGIN BLOG -->
                    <%}%>
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
            <div class="page-footer-inner"> </div>
            <div class="scroll-to-top">
                <i class="icon-arrow-up"></i>
            </div>
        </div>
    </div>
    <!-- END FOOTER -->
    <!--[if lt IE 9]>
<script src="../assets/global/plugins/respond.min.js"></script>
<script src="../assets/global/plugins/excanvas.min.js"></script> 
<![endif]-->
    <!-- BEGIN CORE PLUGINS -->
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
    <!-- END CORE PLUGINS -->
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <script src="assets/seta/js/select2.full.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js" type="text/javascript"></script>
    <!-- END PAGE LEVEL PLUGINS -->
    <!-- BEGIN THEME GLOBAL SCRIPTS -->
    <script src="assets/global/scripts/app.min.js" type="text/javascript"></script>

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
    <!-- END THEME LAYOUT SCRIPTS -->
    


    <!-- BEGIN THEME GLOBAL SCRIPTS -->

</body>

</html>
