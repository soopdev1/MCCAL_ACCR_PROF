<%@page import="com.seta.action.Label"%>
<%@page import="com.seta.action.Constant"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->

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
        <link href="assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        <!-- END THEME GLOBAL STYLES -->
        <!-- BEGIN PAGE LEVEL STYLES -->
        <link href="assets/pages/css/login-2.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/layouts/layout/css/custom.min.css" rel="stylesheet" type="text/css" />
        <!-- END PAGE LEVEL STYLES -->
        <!-- BEGIN THEME LAYOUT STYLES -->
        <!-- END THEME LAYOUT STYLES -->
        <link rel="shortcut icon" href="favicon.ico" /> </head>
    <script type="text/javascript" >
        function sel() {
            document.getElementById("us1").focus();
        }

    </script>
    <!-- END HEAD -->
    <body class=" login" onload="sel();">
        <!-- BEGIN LOGO -->
        <div class="logo">
            <img src="assets/seta/img/logo_blue_1.png" alt="" height="150"/>
        </div>
        <!-- END LOGO -->
        <!-- BEGIN LOGIN -->
        <div class="content">
            <!-- BEGIN LOGIN FORM -->
            <form class="login-form" action="<%=request.getContextPath() + "/LoginOperations?type=log"%>" method="post">
                <hr>
                <!--
                <div class="form-title">
                    <span class="form-title">LOGIN</span>
                </div>
                -->
                    <div class="alert alert-danger display-hide">
                    <button class="close" data-close="alert"></button>
                    <span>Inserire Username e Password</span>
                    </div>
                    <%if (request.getParameter("esito") != null) {
                                if (request.getParameter("esito").equals("okr1")) {%>
                    <div class="alert alert-success text-justify">
                        Nuovo candidato accreditato con successo!
                        <br>Ti abbiamo inviato una mail con  le credenziali di accesso (controlla anche nello spam). <br>
                        Ti abbiamo anche  inviato un sms con la password temporanea da inserire al primo accesso.<br>
                        Tale password deve essere utilizzata entro 12 ore.
                    </div>
                    <%} else if (request.getParameter("esito").equals("okr2")) {
                    } else {%>
                    <div class="alert alert-danger text-justify">
                        <strong>Errore!</strong> <%=Label.getMessageLogin("IT", request.getParameter("esito"))%>
                    </div>
                    <%}}%>
                <div class="form-group">
                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                    <label class="control-label visible-ie8 visible-ie9">Username</label>
                    <input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off" placeholder="Username" name="username" id="us1"/> </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">Password</label>
                    <input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off" placeholder="Password" name="password" /> </div>
                <div class="form-actions">
                    <button type="submit" class="btn red btn-block uppercase">Login</button>
                </div>
            </form>
            <hr>
            <form id="link1" action="bando_reg.jsp" method="post">
                <input type="hidden" name="bando" value="<%=Constant.bando%>"/>
                <div class="create-account">
                    <p>
                        <button type="submit" class="btn btn-block green-soft">Accreditati al Bando</button>
                    </p>
                </div>
            </form>
            <!-- END LOGIN FORM -->
            <!-- BEGIN FORGOT PASSWORD FORM -->
        </div>
        <div class="copyright"> 
            <div>
                <strong> <img src="assets/seta/img/Adobe-PDF-Document-icon.png" alt=""/>                         
                    <a class="" href="Download?action=guida" target="_blank" /> Guida all'utilizzo della piattaforma
                </strong>
                <br/><br/>
                <strong> <img src="assets/seta/img/Adobe-PDF-Document-icon.png" alt=""/>                         
                    <a class="" href="Download?action=guidaConvenzioni" target="_blank" /> Guida per la gestione della documentazione di accreditamento
                </strong>
            </div>
            <br>
            <center>
                <table>
                    <tr>
                        <td><img src="assets/seta/img/uneur-small.png" alt="" height="54px" width="54px"/></td>
                        <td><img src="assets/seta/img/tes1t.png" alt="" height="45px" width="45px"/></td>
                        <td><img src="assets/seta/img/LogoRegCal.gif" alt="" height="45px" width="45px"/></td>
                        <td><img src="assets/seta/img/Immagine.png" alt="" height="45px" width="45px"/></td>
                    </tr>
                </table>
            </center> 

            
        </div>
        <!-- END LOGIN -->
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
        <!-- END CORE PLUGINS -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <script src="assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL SCRIPTS -->
        <script src="assets/global/scripts/app.min.js" type="text/javascript"></script>
        <!-- END THEME GLOBAL SCRIPTS -->
        <!-- BEGIN PAGE LEVEL SCRIPTS -->
        <script src="assets/pages/scripts/login.min.js" type="text/javascript"></script>
        <!-- END PAGE LEVEL SCRIPTS -->
        <!-- BEGIN THEME LAYOUT SCRIPTS -->
        <!-- END THEME LAYOUT SCRIPTS -->
    </body>

</html>