/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.servlet;

import com.seta.action.ActionB;
import com.seta.entity.Domandecomplete;
import com.seta.util.Utility;
import static com.seta.util.Utility.redirect;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author fplacanica
 */
@WebServlet(name = "Query", urlPatterns = {"/Query"})
public class Query extends HttpServlet {
    
    private static String correggi(String ing) {
        if (ing != null) {
            ing = ing.replaceAll("\\\\", "");
            ing = ing.replaceAll("\n", "");
            ing = ing.replaceAll("\r", "");
            ing = ing.replaceAll("\t", "");
            ing = ing.replaceAll("'", "\'");
            ing = ing.replaceAll("“", "\'");
            ing = ing.replaceAll("‘", "\'");
            ing = ing.replaceAll("”", "\'");
            return ing.replaceAll("\"", "\'");
        } else {
            return "-";
        }
    }
    
    protected void query1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("text/plain; charset=ISO-8859-1");
            response.setCharacterEncoding("ISO-8859-1");
            boolean mobile = Utility.detectMobile2(request);
            String target = "class='fancybox-ferrari'";
            String data_da = request.getParameter("data_da");
            String data_a = request.getParameter("data_a");
            if(data_da==null){
                data_da="";
            }
            if(data_a==null){
                data_a="";
            }
            if (mobile) {
                target = "target='_blank'";
            }
            SimpleDateFormat sdf_in = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // creo l'oggetto
//            Date da = sdf_in.parse(data_da);
            String dt_a = "";
            String dt_da = "";
            if (!data_a.trim().equals("")) {
                Date a = sdf_in.parse(data_a);
                dt_a = sdf.format(a);
            }
            if (!data_da.trim().equals("")) {
                Date da = sdf_in.parse(data_da);
                dt_da = sdf.format(da);
            }
//            dt_da = sdf.format(da);
            ArrayList<Domandecomplete> lista = ActionB.getDomandeComplete(dt_da, dt_a);
            PrintWriter out = response.getWriter();
            String inizio = "{ \"aaData\": [";
            String fine = "] }";
            String valore = "";
            
            if (!lista.isEmpty()) {
                for (int i = 0; i < lista.size(); i++) {
                    String azioni = "";
                    if (lista.get(i).getStatoDomanda().equals("S")) {
                        azioni = "<li><a class='btn btn-sm white popovers' data-toggle='modal' onclick='return setAccettaDomanda(&#34" + lista.get(i).getUsername() + "&#34)' href='#accettamodal' data-trigger='hover' data-placement='top' data-content='Accetta domanda'><i class='fa fa-check-circle'></i> Accetta domanda</a></li>"
                               + "<li><a class='btn btn-sm white popovers' data-toggle='modal' onclick='return setRigettaDomanda(&#34" + lista.get(i).getUsername() + "&#34)' href='#scartamodal2' data-trigger='hover' data-placement='top' data-content='Rigetta domanda'><i class='fa fa-times-circle'></i>  Rigetta domanda</a></li>";
                    }
                    if (ActionB.countDocumentConvenzioni(lista.get(i).getUsername())==3){
                        azioni = "<li><a class='btn btn-sm white popovers fancyBoxRafreload' data-toggle='modal' href='bando_conv.jsp?userdoc=" + lista.get(i).getUsername() + "' data-trigger='hover' data-placement='top' data-content='Visualizza convenzioni'><i class='fa fa-eye'></i> Visualizza convenzioni</a></li>";
                    }
                    if (ActionB.getInvioEmailROMA(lista.get(i).getUsername()).equals("1")) {
                        if(ActionB.getConvenzioneROMA(lista.get(i).getUsername()).trim().equals("")) {
                            azioni = azioni + "<li><a class='btn btn-sm white popovers' data-toggle='modal' onclick='return setCaricaDocumento(&#34" + lista.get(i).getUsername() + "&#34)' href='#caricadocumento' data-trigger='hover' data-placement='top' data-content='Carica documento'><i class='fa fa-check-circle'></i> Carica documento</a></li>";
                        }
                    }
                    String az2 = "<div class='clearfix'><div class='task-config'>"
                            + "<div class='task-config-btn btn-group' style='text-align:left;'>"
                            + "<a class='btn btn-default btn-xs' href='#' data-toggle='dropdown' data-hover='dropdown' data-close-others='true'><i class='fa fa-cog'>"
                            + "</i>  <i class='fa fa-angle-down'></i></a><ul class='dropdown-menu pull-right'>"
                            + "<li><a href='bando_visdocall.jsp?userdoc=" + lista.get(i).getUsername() + "' "
                            + "class='btn btn-sm white popovers fancyBoxRaf' container='body' data-trigger='hover' data-container='body' data-placement='top' "
                            + "data-content='Visualizza Documenti'>"
                            + "<i class='fa fa-file'></i> Visualizza documenti</a></li>"
                            + azioni
                    +"</ul></div></div></div>";
                    //fancyBoxSimone
                    valore = valore + " [ \""
                            + correggi(lista.get(i).getId()) + "\", \""
                            + correggi(lista.get(i).getNome()).toUpperCase() + "\", \""
                            + correggi(lista.get(i).getCognome()).toUpperCase() + "\", \""
                            + correggi(lista.get(i).getCodicefiscale()).toUpperCase() + "\", \""
                            + correggi(lista.get(i).getRagsoc()).toUpperCase() + "\", \""
                            + correggi(lista.get(i).getPiva()).toUpperCase() + "\", \""
                            + correggi(lista.get(i).getCodfissog()).toUpperCase() + "\", \""
                            + Utility.formatTimestamp(lista.get(i).getDatainvio()) + "\", \""
                            + az2 + "\"],";
                }
                String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                out.print(x);
            } else {
                out.print(inizio + fine);
            }
            out.close();
        } catch (ParseException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("username") == null) {
            redirect(request, response, "home.jsp");
        } else {
            response.setContentType("text/plain; charset=ISO-8859-1");
            response.setCharacterEncoding("ISO-8859-1");
            String type = request.getParameter("tipo");
            if (type.equals("1")) {
                query1(request, response);
            }
            if (type.equals("getRagSoc")) {
                getRagSoc(request, response);
            }
            
        }
        
    }
    protected void getRagSoc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("user");
        String regSoc = ActionB.getRagioneSociale(user);
        response.getWriter().print(regSoc);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
