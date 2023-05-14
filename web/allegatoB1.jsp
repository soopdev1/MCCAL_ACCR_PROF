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
    String user=session.getAttribute("username").toString();
    if (user == null) {
        Utility.redirect(request, response, "home.jsp");
    } else {
        if (request.getParameter("iddocente") == null) {
%>
<script>
    location.href = "bando_onlinecomp2.jsp?allegato_A_B=C";
</script>
<%
} else if (ActionB.esisteAllegatoB1Field(user)) {
    try {
        int x = Integer.parseInt(request.getParameter("iddocente"));
        ArrayList<Docenti> al = ActionB.getDocenti(user);
        if(x<=al.size()){
            if(ActionB.esisteAllegatoB1Field(user,x)){
%>
<script>
    location.href = "bando_onlinecomp2.jsp?allegato_A_B=C";
</script>
<%
            }
        } else {
%>
<script>
    location.href = "bando_onlinecomp2.jsp?allegato_A_B=C";
</script>
<%
        }
    } catch (Exception e) {
%>
<script>
    location.href = "bando_onlinecomp2.jsp?allegato_A_B=C";
</script>
<%
    }
}
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
                String iddocente = request.getParameter("iddocente");
            %>

            function controllaReg1() {
                var output = "0";
                var msg = "";
                // SEZIONE ATTIVITA' 1
                var periodo = document.getElementById("periodo").value;
                var durata = document.getElementById("durata").value;
                var tipo = document.getElementById("tipo").value;
                var fonte = document.getElementById("fonte").value;
                var elenco = document.getElementById("elenco").value;
                var sa = document.getElementById("sa").value;
                if (periodo.trim() == "" || durata.trim() == "" || tipo.trim() == "" || fonte.trim() == "" || elenco.trim() == "" || sa.trim() == "") {
                    output = "1";
                    msg = "La sezione <b>'Attività 1'</b> deve essere compilata in tutte le sue parti. I campi obbligatori sono:<br><br><span style='color:red;'> - Periodo<br> - Durata<br> - Tipo di incarico<br> - Fonte di finanziamento<br> - Elenco attività svolte<br> - Soggetto Attuatore/Committente</span>";
                }
                // SEZIONE ATTIVITA' 2
                var periodo2 = document.getElementById("periodo2").value;
                var durata2 = document.getElementById("durata2").value;
                var tipo2 = document.getElementById("tipo2").value;
                var fonte2 = document.getElementById("fonte2").value;
                var elenco2 = document.getElementById("elenco2").value;
                var sa2 = document.getElementById("sa2").value;
                if (periodo2.trim() != "" || durata2.trim() != "" || tipo2.trim() != "" || fonte2.trim() != "" || elenco2.trim() != "" || sa2.trim() != "") {
                    if (periodo2.trim() == "" || durata2.trim() == "" || tipo2.trim() == "" || fonte2.trim() == "" || elenco2.trim() == "" || sa2.trim() == "") {
                        output = "1";
                        msg = "Hai compilato uno dei campi della sezione <b>'Attività 2'</b> se solo uno di essi è compilato, i campi obbligatori diventano:<br><br><span style='color:red;'> - Periodo<br> - Durata<br> - Tipo di incarico<br> - Fonte di finanziamento<br> - Elenco attività svolte<br> - Soggetto Attuatore/Committente</span>";
                    }
                }
                // SEZIONE ATTIVITA' 3
                var periodo3 = document.getElementById("periodo3").value;
                var durata3 = document.getElementById("durata3").value;
                var tipo3 = document.getElementById("tipo3").value;
                var fonte3 = document.getElementById("fonte3").value;
                var elenco3 = document.getElementById("elenco3").value;
                var sa3 = document.getElementById("sa3").value;
                if (periodo3.trim() != "" || durata3.trim() != "" || tipo3.trim() != "" || fonte3.trim() != "" || elenco3.trim() != "" || sa3.trim() != "") {
                    if (periodo3.trim() == "" || durata3.trim() == "" || tipo3.trim() == "" || fonte3.trim() == "" || elenco3.trim() == "" || sa3.trim() == "") {
                        output = "1";
                        msg = "Hai compilato uno dei campi della sezione <b>'Attività 3'</b> se solo uno di essi è compilato, i campi obbligatori diventano:<br><br><span style='color:red;'> - Periodo<br> - Durata<br> - Tipo di incarico<br> - Fonte di finanziamento<br> - Elenco attività svolte<br> - Soggetto Attuatore/Committente</span>";
                    }
                }
                // SEZIONE ATTIVITA' 4
                var periodo4 = document.getElementById("periodo4").value;
                var durata4 = document.getElementById("durata4").value;
                var tipo4 = document.getElementById("tipo4").value;
                var fonte4 = document.getElementById("fonte4").value;
                var elenco4 = document.getElementById("elenco4").value;
                var sa4 = document.getElementById("sa4").value;
                if (periodo4.trim() != "" || durata4.trim() != "" || tipo4.trim() != "" || fonte4.trim() != "" || elenco4.trim() != "" || sa4.trim() != "") {
                    if (periodo4.trim() == "" || durata4.trim() == "" || tipo4.trim() == "" || fonte4.trim() == "" || elenco4.trim() == "" || sa4.trim() == "") {
                        output = "1";
                        msg = "Hai compilato uno dei campi della sezione <b>'Attività 4'</b> se solo uno di essi è compilato, i campi obbligatori diventano:<br><br><span style='color:red;'> - Periodo<br> - Durata<br> - Tipo di incarico<br> - Fonte di finanziamento<br> - Elenco attività svolte<br> - Soggetto Attuatore/Committente</span>";
                    }
                }
                // SEZIONE ATTIVITA' 5
                var periodo5 = document.getElementById("periodo5").value;
                var durata5 = document.getElementById("durata5").value;
                var tipo5 = document.getElementById("tipo5").value;
                var fonte5 = document.getElementById("fonte5").value;
                var elenco5 = document.getElementById("elenco5").value;
                var sa5 = document.getElementById("sa5").value;
                if (periodo5.trim() != "" || durata5.trim() != "" || tipo5.trim() != "" || fonte5.trim() != "" || elenco5.trim() != "" || sa5.trim() != "") {
                    if (periodo5.trim() == "" || durata5.trim() == "" || tipo5.trim() == "" || fonte5.trim() == "" || elenco5.trim() == "" || sa5.trim() == "") {
                        output = "1";
                        msg = "Hai compilato uno dei campi della sezione <b>'Attività 5'</b> se solo uno di essi è compilato, i campi obbligatori diventano:<br><br><span style='color:red;'> - Periodo<br> - Durata<br> - Tipo di incarico<br> - Fonte di finanziamento<br> - Elenco attività svolte<br> - Soggetto Attuatore/Committente</span>";
                    }
                }
                if (output !== "0") {
                    document.getElementById("msgcompil").innerHTML = msg;
                    document.getElementById("confirm").className = document.getElementById("confirm").className + " in";
                    document.getElementById("confirm").style.display = "block";
                    return false;
                }
                document.getElementById("formModello").submit();
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
                            } else if (es.equals("ko")) {
                                inte = "<i class='fa fa-exclamation-triangle font-red'></i> Errore inserimento";
                                msg = "Impossibile salvare i file, riprovare.";
                            } else if (es.equals("ko1")) {
                                inte = "<i class='fa fa-exclamation-triangle font-red'></i> Errore durante la fase di eliminazione";
                                msg = "Impossibile eliminare il fascicolo del docente, riprovare.";
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
                                <div class="modal-header">durata
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

                            <div class="portlet-body">
                                <form name="formModello" role="form" action="Operazioni?action=modb1" method="post"  class="form-horizontal" id="formModello">
                                    <input type="hidden" name="username" value="<%=username%>"/>
                                    <input type="hidden" name="iddocente" value="<%=iddocente%>"/>
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
                                                    Attività 1
                                                </span> <hr>
                                            </th>
                                        </tr>
                                        <tr class="classe1">
                                            <td>
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Periodo
                                                                    </span>
                                                                    <input type="text" class="form-control periodo" name="periodo" placeholder="..." id="periodo" readonly>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="col-md-6">
                                                                    <span class="help-block">Durata</span>
                                                                    <input type="text" class="form-control" name="durata" placeholder="..." id="durata">
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <span class="help-block">In gg o in mesi</span>
                                                                    <select class="form-control select2" name="ggmm" id="ggmm">
                                                                        <option value="gg">gg</option>
                                                                        <option value="mesi">mesi</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">

                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Tipo di incarico
                                                                    </span>
                                                                    <input type="text" class="form-control" name="tipo" placeholder="..." id="tipo">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Fonte di finanziamento
                                                                    </span>
                                                                    <input type="text" class="form-control" name="fonte" placeholder="..." id="fonte">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-9">
                                                            <div class="form-group">
                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Elenco attività svolte
                                                                    </span>
                                                                    <input type="text" class="form-control" name="elenco" placeholder="..." id="elenco">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Soggetto Attuatore/Committente
                                                                    </span>
                                                                    <input type="text" class="form-control" name="sa" placeholder="..." id="sa">
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr style="border-bottom: 3px;" class="classe2">
                                            <th colspan="4" style="text-align: center;" ><span class="help-block bordered">
                                                    Attività 2 
                                                </span> <hr>
                                            </th>
                                        </tr>
                                        <tr class="classe2">
                                            <td>
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Periodo
                                                                    </span>
                                                                    <input type="text" class="form-control periodo" name="periodo2" placeholder="..." id="periodo2" readonly>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="col-md-6">
                                                                    <span class="help-block">
                                                                        Durata
                                                                    </span>
                                                                    <input type="text" class="form-control" name="durata2" placeholder="..." id="durata2">
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <span class="help-block">In gg o in mesi</span>
                                                                    <select class="form-control select2" name="ggmm2" id="ggmm2">
                                                                        <option value="gg">gg</option>
                                                                        <option value="mesi">mesi</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">

                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Tipo di incarico
                                                                    </span>
                                                                    <input type="text" class="form-control" name="tipo2" placeholder="..." id="tipo2">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Fonte di finanziamento
                                                                    </span>
                                                                    <input type="text" class="form-control" name="fonte2" placeholder="..." id="fonte2">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-9">
                                                            <div class="form-group">
                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Elenco attività svolte
                                                                    </span>
                                                                    <input type="text" class="form-control" name="elenco2" placeholder="..." id="elenco2">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Soggetto Attuatore/Committente
                                                                    </span>
                                                                    <input type="text" class="form-control" name="sa2" placeholder="..." id="sa2">
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr style="border-bottom: 3px;" class="classe3">
                                            <th colspan="4" style="text-align: center;" ><span class="help-block bordered">
                                                    Attività 3 
                                                </span> <hr>
                                            </th>
                                        </tr>
                                        <tr class="classe3">
                                            <td>
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Periodo
                                                                    </span>
                                                                    <input type="text" class="form-control periodo" name="periodo3" placeholder="..." id="periodo3" readonly>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="col-md-6">
                                                                    <span class="help-block">
                                                                        Durata
                                                                    </span>
                                                                    <input type="text" class="form-control" name="durata3" placeholder="..." id="durata3">
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <span class="help-block">In gg o in mesi</span>
                                                                    <select class="form-control select2" name="ggmm3" id="ggmm3">
                                                                        <option value="gg">gg</option>
                                                                        <option value="mesi">mesi</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">

                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Tipo di incarico
                                                                    </span>
                                                                    <input type="text" class="form-control" name="tipo3" placeholder="..." id="tipo3">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Fonte di finanziamento
                                                                    </span>
                                                                    <input type="text" class="form-control" name="fonte3" placeholder="..." id="fonte3">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-9">
                                                            <div class="form-group">
                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Elenco attività svolte
                                                                    </span>
                                                                    <input type="text" class="form-control" name="elenco3" placeholder="..." id="elenco3">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Soggetto Attuatore/Committente
                                                                    </span>
                                                                    <input type="text" class="form-control" name="sa3" placeholder="..." id="sa3">
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr style="border-bottom: 3px;" class="classe4">
                                            <th colspan="4" style="text-align: center;" ><span class="help-block bordered">
                                                    Attività 4 
                                                </span> <hr>
                                            </th>
                                        </tr>
                                        <tr class="classe4">
                                            <td>
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Periodo
                                                                    </span>
                                                                    <input type="text" class="form-control periodo" name="periodo4" placeholder="..." id="periodo4" readonly>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="col-md-6">
                                                                    <span class="help-block">
                                                                        Durata
                                                                    </span>
                                                                    <input type="text" class="form-control" name="durata4" placeholder="..." id="durata4">
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <span class="help-block">In gg o in mesi</span>
                                                                    <select class="form-control select2" name="ggmm4" id="ggmm4">
                                                                        <option value="gg">gg</option>
                                                                        <option value="mesi">mesi</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">

                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Tipo di incarico
                                                                    </span>
                                                                    <input type="text" class="form-control" name="tipo4" placeholder="..." id="tipo4">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Fonte di finanziamento
                                                                    </span>
                                                                    <input type="text" class="form-control" name="fonte4" placeholder="..." id="fonte4">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-9">
                                                            <div class="form-group">
                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Elenco attività svolte
                                                                    </span>
                                                                    <input type="text" class="form-control" name="elenco4" placeholder="..." id="elenco4">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Soggetto Attuatore/Committente
                                                                    </span>
                                                                    <input type="text" class="form-control" name="sa4" placeholder="..." id="sa4">
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr style="border-bottom: 3px;" class="classe5">
                                            <th colspan="4" style="text-align: center;" ><span class="help-block bordered">
                                                    Attività 5
                                                </span> <hr>
                                            </th>
                                        </tr>
                                        <tr class="classe5">
                                            <td>
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Periodo
                                                                    </span>
                                                                    <input type="text" class="form-control periodo" name="periodo5" placeholder="..." id="periodo5" readonly>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="col-md-6">
                                                                    <span class="help-block">Durata</span>
                                                                    <input type="text" class="form-control" name="durata5" placeholder="..." id="durata5">
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <span class="help-block">In gg o in mesi</span>
                                                                    <select class="form-control select2" name="ggmm5" id="ggmm5">
                                                                        <option value="gg">gg</option>
                                                                        <option value="mesi">mesi</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">

                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Tipo di incarico
                                                                    </span>
                                                                    <input type="text" class="form-control" name="tipo5" placeholder="..." id="tipo5">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Fonte di finanziamento
                                                                    </span>
                                                                    <input type="text" class="form-control" name="fonte5" placeholder="..." id="fonte5">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-9">
                                                            <div class="form-group">
                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Elenco attività svolte
                                                                    </span>
                                                                    <input type="text" class="form-control" name="elenco5" placeholder="..." id="elenco5">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="col-md-12">
                                                                    <span class="help-block">
                                                                        Soggetto Attuatore/Committente
                                                                    </span>
                                                                    <input type="text" class="form-control" name="sa5" placeholder="..." id="sa5">
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
                <div class="page-footer-inner"><%=Constant.NAMEAPP%> v. 1.0.0</div>
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
                                                        $("#durata").inputmask({
                                                            "mask": "9",
                                                            "repeat": 10,
                                                            "greedy": false
                                                        }); // ~ mask "9" or mask "99" or ... mask "9999999999"
                                                        $("#durata2").inputmask({
                                                            "mask": "9",
                                                            "repeat": 10,
                                                            "greedy": false
                                                        }); // ~ mask "9" or mask "99" or ... mask "9999999999"
                                                        $("#durata3").inputmask({
                                                            "mask": "9",
                                                            "repeat": 10,
                                                            "greedy": false
                                                        }); // ~ mask "9" or mask "99" or ... mask "9999999999"
                                                        $("#durata4").inputmask({
                                                            "mask": "9",
                                                            "repeat": 10,
                                                            "greedy": false
                                                        }); // ~ mask "9" or mask "99" or ... mask "9999999999"
                                                        $("#durata5").inputmask({
                                                            "mask": "9",
                                                            "repeat": 10,
                                                            "greedy": false
                                                        }); // ~ mask "9" or mask "99" or ... mask "9999999999"
            </script>
    </body>
</html>
<%}%>