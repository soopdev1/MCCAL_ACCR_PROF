<%@page import="com.seta.entity.Domandecomplete"%>
<%@page import="com.seta.entity.Docuserbandi"%>
<%@page import="com.seta.entity.Docbandi"%>
<%@page import="com.seta.action.ActionB"%>
<%@page import="com.seta.util.Utility"%>
<!DOCTYPE html>
<%@page import="java.util.ArrayList"%>
<%@page import="com.seta.action.Label"%>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" >
        <meta charset="utf-8"/>
        <title>por04.lavorocalabria.it</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1, maximum-scale=1" name="viewport"/>
        <meta content="" name="description"/>
        <meta content="" name="author"/>
        <meta name="MobileOptimized" content="320">
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
        <link href="assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
        <!-- END GLOBAL MANDATORY STYLES -->

        <!-- SETA SELECT -->
        <link rel="stylesheet" type="text/css" href="assets/plugins/select2/select2_metro.css"/>

        <!-- SETA DATA -->
        <link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap-fileupload/bootstrap-fileupload.css"/>
        <link rel="stylesheet" type="text/css" href="assets/plugins/gritter/css/jquery.gritter.css"/>
        <link rel="stylesheet" type="text/css" href="assets/plugins/select2/select2_metro.css"/>
        <link rel="stylesheet" type="text/css" href="assets/plugins/clockface/css/clockface.css"/>
        <link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.css"/>
        <link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap-datepicker/css/datepicker.css"/>
        <link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap-timepicker/compiled/timepicker.css"/>
        <link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap-colorpicker/css/colorpicker.css"/>
        <link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css"/>
        <link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap-datetimepicker/css/datetimepicker.css"/>
        <link rel="stylesheet" type="text/css" href="assets/plugins/jquery-multi-select/css/multi-select.css"/>
        <link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap-switch/static/stylesheets/bootstrap-switch-metro.css"/>
        <link rel="stylesheet" type="text/css" href="assets/plugins/jquery-tags-input/jquery.tagsinput.css"/>
        <link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap-markdown/css/bootstrap-markdown.min.css">

        <!-- SETA TABLE -->

        <link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css"/>

        <!-- SETA FANCYBOX -->
        <link rel="stylesheet" type="text/css" href="assets/plugins/fancybox/source/jquery.fancybox.css"/>

        <!-- BEGIN THEME STYLES -->
        <link href="assets/css/style-Set.css" rel="stylesheet" type="text/css"/>
        <link href="assets/css/style.css" rel="stylesheet" type="text/css"/>
        <link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
        <link href="assets/css/plugins.css" rel="stylesheet" type="text/css"/>
        <link href="assets/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color"/>
        <link href="assets/css/custom.css" rel="stylesheet" type="text/css"/>
        <link href="assets/css/pages/tasks.css" rel="stylesheet" type="text/css"/>


        <!-- END THEME STYLES -->
        <link rel="shortcut icon" href="favicon.ico"/>
        <script type="text/javascript">
            function controlla() {
                var filedoc = document.f1.file.value;
                if (filedoc === "" || filedoc === "...") {
                    document.getElementById("valoretxt").innerHTML = "Impossibile procedere. Scegliere il file da caricare";
                    document.getElementById("static").className = document.getElementById("static").className + " in";
                    document.getElementById("static").style.display = "block";
                    return false;
                }
                var tipologia = document.f1.tipologia.value;
                if (tipologia === "" || tipologia === "...") {
                    document.getElementById("valoretxt").innerHTML = "Impossibile procedere. Scegliere la tipologia documentale da caricare.";
                    document.getElementById("static").className = document.getElementById("static").className + " in";
                    document.getElementById("static").style.display = "block";
                    return false;
                }
            }
            function controllacompl() {
                var filedocF = document.f2.fileF.value;
                var filedocR = document.f2.fileR.value;
                if (filedocF === "" || filedocR === "") {
                    document.getElementById("valoretxt").innerHTML = "Impossibile procedere. Necessario scegliere <span style='color:red;'>entrambi</span> i file da caricare.";
                    document.getElementById("static").className = document.getElementById("static").className + " in";
                    document.getElementById("static").style.display = "block";
                    return false;
                }
            }
            function dismiss(name) {
                document.getElementById(name).className = "modal fade";
                document.getElementById(name).style.display = "none";
            }
        </script>
    </head>
    <!-- END HEAD -->
    <!-- BEGIN BODY -->
    <body class="page-full-width page-footer-fixed" style="height: 500px; background-color: white;">
        <!-- SETA JAVA -->

        <div id="static" class="modal fade" tabindex="-1" data-backdrop="static" data-keyboard="false">
            <div class="modal-dialog">
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


        <!-- END HEADER -->
        <%
            String bandorif = session.getAttribute("bandorif").toString();
            String username = request.getParameter("userdoc");
            String descbando = ActionB.getDescrizioneBando(bandorif);
            String scabando = ActionB.getScadenzaBando(bandorif);
        %>

        <!-- END SIDEBAR -->
        <!-- BEGIN CONTENT -->
        <div class="page-content-wrapper">
            <div class="page-content">
                <div class="row">
                    <div class="col-md-10">
                        <div class="alert alert-info">
                            <strong>Bando attivo:</strong> <%=descbando%>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <div class="alert alert-danger">
                            <strong>Scadenza:</strong> <%=scabando%>
                        </div>
                    </div>
                        <div class="col-md-12">
                            <form action="<%=request.getContextPath() + "/Upload?action=simpleRUP"%>" method="POST" enctype="multipart/form-data" name="f1" onsubmit="return controlla();">
                                
                                <input type="hidden" name="userdoc" value="<%=username%>"/>
                                <div class="portlet box yellow">
                                    <div class="portlet-title">
                                        <div class="caption"><i class="fa fa-upload"></i>Upload allegati domanda</div>
                                        <div class="tools">
                                            <a href="javascript:;" class="collapse"></a>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="form-group"id="pll1">
                                                    <label>Tipologia Documentale <span style="color: red;">*</span></label>
                                                    <select name="tipologia" class="form-control select2me" data-placeholder="...">
                                                        <%
                                                            ArrayList<String[]> tipi = ActionB.listaTipiAllRUP(bandorif);
                                                            for (int p = 0; p < tipi.size(); p++) {%>
                                                        <option value="<%=tipi.get(p)[0]%>"><%=tipi.get(p)[1]%></option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group"id="pll1">
                                                    <label>Note</label>
                                                    <input type="text" name="note" class="form-control" placeholder="..."/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <span class="help-block">
                                                    File <b style="color: red;">*</b> 
                                                </span>
                                                <div class="fileupload fileupload-new" data-provides="fileupload">
                                                    <div class="input-group">
                                                        <span class="input-group-btn">
                                                            <span class="uneditable-input">
                                                                <i class="fa fa-file fileupload-exists"></i>
                                                                <span class="fileupload-preview">
                                                                </span>
                                                            </span>
                                                        </span>
                                                        <span class="btn default btn-file">
                                                            <span class="fileupload-new">
                                                                <i class="fa fa-paper-clip"></i> Scegli file
                                                            </span>
                                                            <span class="fileupload-exists">
                                                                <i class="fa fa-undo"></i> Cambia
                                                            </span>
                                                            <input type="file" class="default" name="file"/>
                                                        </span>
                                                        <a href="#" class="btn red fileupload-exists" data-dismiss="fileupload"><i class="fa fa-trash-o"></i> Elimina</a>
                                                        <button type="submit" class="btn green"><i class="fa fa-upload"></i> Upload</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>                        
                            </form>
                        </div>
                    </div>   
                                                    
                                                    
               
                <!-- END PAGE HEADER-->
                <!-- BEGIN PAGE CONTENT-->
                <!-- END PAGE CONTENT-->
                <%
                    String es = request.getParameter("esito");
                    if (es != null) {
                        String msg = "";
                        String cl = "";
                        if (es.equals("0")) {
                            cl = "alert-success";
                            msg = "Documento caricato con successo";
                        }
                        if (es.equals("1")) {
                            cl = "alert-danger";
                            msg = "Errore durante il caricamento del file. Riprovare.";
                        }
                        if (es.equals("2")) {
                            cl = "alert-danger";
                            msg = "File corrotto o non disponibile. Riprovare.";
                        }
                        if (es.equals("3")) {
                            cl = "alert-danger";
                            msg = "Impossibile careicare il documento. Contattare l'amministratore del sistema. <u>hdpllcalabria@gmail.com</u>";
                        }
                %>
                <br>
                <div class="row">
                    <div class="col-md-6">
                        <div class="alert <%=cl%>" style="text-align: center;">
                            <button type="button" class="close" data-dismiss="alert" aria-hidden="true"></button>
                            <strong><%=msg%></strong>
                        </div>
                    </div>
                </div>
                <%}%>
            </div>
         </div>
<!-- END FOOTER -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
   <script src="assets/plugins/respond.min.js"></script>
   <script src="assets/plugins/excanvas.min.js"></script> 
   <![endif]-->
<script src="assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="assets/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script src="assets/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
<script src="assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="assets/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
<script src="assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="assets/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="assets/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="assets/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>



<!-- SETA SELECT-->
<script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script>

<!-- SETA DATA -->

<script type="text/javascript" src="assets/plugins/fuelux/js/spinner.min.js"></script>
<script type="text/javascript" src="assets/plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-fileupload/bootstrap-fileupload.js"></script>
<script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-wysihtml5/wysihtml5-0.3.0.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.js"></script>
<script type="text/javascript" src="assets/jsset/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="assets/plugins/clockface/js/clockface.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-daterangepicker/moment.min.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-daterangepicker/daterangepicker.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-timepicker/js/bootstrap-timepicker.js"></script>
<script type="text/javascript" src="assets/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js"></script>
<script type="text/javascript" src="assets/plugins/jquery.input-ip-address-control-1.0.min.js"></script>
<script type="text/javascript" src="assets/plugins/jquery-multi-select/js/jquery.multi-select.js"></script>
<script type="text/javascript" src="assets/plugins/jquery-multi-select/js/jquery.quicksearch.js"></script>
<script src="assets/plugins/jquery.pwstrength.bootstrap/src/pwstrength.js" type="text/javascript"></script>
<script src="assets/plugins/bootstrap-switch/static/js/bootstrap-switch.min.js" type="text/javascript"></script>
<script src="assets/plugins/jquery-tags-input/jquery.tagsinput.min.js" type="text/javascript"></script>
<script src="assets/plugins/bootstrap-markdown/js/bootstrap-markdown.js" type="text/javascript"></script>
<script src="assets/plugins/bootstrap-markdown/lib/markdown.js" type="text/javascript"></script>
<script src="assets/plugins/bootstrap-maxlength/bootstrap-maxlength.min.js" type="text/javascript"></script>
<script src="assets/plugins/bootstrap-touchspin/bootstrap.touchspin.js" type="text/javascript"></script>


<!-- SETA TABLE -->
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>




<!-- END CORE PLUGINS -->
<script src="assets/jsset/app.js"></script>

<!-- SETA SELECT-->
<script src="assets/scripts/form-samples.js"></script>


<!-- grafici -->
<script src="assets/plugins/flot/jquery.flot.js" type="text/javascript"></script>
<script src="assets/plugins/flot/jquery.flot.resize.js" type="text/javascript"></script>

<script src="assets/plugins/jquery.sparkline.min.js" type="text/javascript"></script>

<!-- SETA DATA -->
<script src="assets/scripts/form-components.js"></script>

<!-- SETA TABLE -->
<script src="assets/jsset/jquery.dataTables.columnFilter.js"></script>
<script src="assets/jsset/ColReorder.min.js"></script>
<!-- SETA COLVIS -->
<script src="assets/jsset/ColVis.min.js"></script>

<!-- SETA AZIONI -->
<script src="assets/jsset/action.js"></script>

<!-- SETA FANCYBOX -->
<script type="text/javascript" src="assets/plugins/jquery-mixitup/jquery.mixitup.min.js"></script>
<script type="text/javascript" src="assets/plugins/fancybox/source/jquery.fancybox.pack.js"></script>
<script type="text/javascript" src="assets/scripts/portfolio.js"></script>
<script src="assets/scripts/index.js" type="text/javascript"></script>
<script src="assets/scripts/tasks.js" type="text/javascript"></script>
<script>
                                jQuery(document).ready(function () {
                                    App.init();
                                    FormSamples.init();
                                    FormComponents.init();
                                    Portfolio.init();
                                    Tasks.initDashboardWidget();
                                    Index.initCharts(); // init index page's custom scripts
                                    Index.initChat();
                                });
</script>

<script type="text/javascript">
    jQuery(document).ready(function () {
        //Hide the overview when click
        $('#someid').on('click', function () {
            $('#OverviewcollapseButton').removeClass("collapse").addClass("expand");
            $('#PaymentOverview').hide();
        });
    });
</script>

<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
    <ul class="dropdown-menu">
    </ul>
</a>
</body>
<!-- END BODY -->
</html>
<!-- Localized -->