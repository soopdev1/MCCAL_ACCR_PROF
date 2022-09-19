/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.servlet;

import static com.seta.action.ActionB.trackingAction;
import com.seta.action.Constant;
import static com.seta.action.Constant.bando;
import com.seta.db.Db_Bando;
import com.seta.entity.UserBando;
import com.seta.util.Utility;
import static com.seta.util.Utility.redirect;
import com.seta.engine.Engine;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class LoginOperations extends HttpServlet {

    //LOGOUT
    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("username") == null) {
            redirect(request, response, "home.jsp");
        } else {
            trackingAction(request.getSession().getAttribute("username").toString(), "Logout portale");
            request.getSession().setAttribute("bandorif", null);
            request.getSession().setAttribute("statouser", null);
            request.getSession().setAttribute("username", null);
            request.getSession().setAttribute("lang", null);
            request.getSession().setAttribute("numcell", null);
            request.getSession().setAttribute("indemail", null);
            request.getSession().setAttribute("tipo", null);
            request.getSession().invalidate();
            redirect(request, response, "home.jsp");
        }
    }

    //redirect alla pagina desiderata
    private String getSiteRedirect(String stato, String tipo) {

        if (tipo.equals("0")) { //    UTENTE
            if (stato.equals("0")) {
                return Constant.otp;
            } else if (stato.equals("2")) { //resetPass
                return Constant.reset;
            } else if (stato.equals("1")) {
                return Constant.homePage;
            }
        }
        if (tipo.equals("1")) { //    RUP
            if (stato.equals("2")) { // resetPass
                return Constant.reset;
            } else if (stato.equals("1")) {
                return Constant.homePageRup;
            }
        }
        if (tipo.equals("2")) {
            if (stato.equals("0")) {
                return Constant.otp;
            } else if (stato.equals("2")) { //resetPass
                return Constant.reset;
            } else if (stato.equals("1")) {
                return Constant.homePageAz;
            }
        }
        return Constant.errLogin + "KO5";
    }

    //RESET
    //Changepsw
    //controllo OTP
    protected void otpbando(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("username") == null) {
            redirect(request, response, "home.jsp");
        } else {
            String user = request.getSession().getAttribute("username").toString();
            String bando = request.getSession().getAttribute("bandorif").toString();
            String tipo = request.getSession().getAttribute("tipo").toString();
            String otp = request.getParameter("otp");
            if (otp == null || otp.trim().equals("")) {
                redirect(request, response, Constant.otp + "?esito=KO1");
            } else {
                boolean esito = Engine.verificaOTP(bando, otp, user);
                if (esito) {
                    Db_Bando dbb = new Db_Bando();
                    boolean es = dbb.cambiaValoreUser(user, "stato", "1");
                    dbb.closeDB();
                    trackingAction(request.getSession().getAttribute("username").toString(), "Verifica OTP");
                    if (es) {
                        redirect(request, response, getSiteRedirect("1", tipo));
                    } else {
                        redirect(request, response, Constant.otp + "?esito=KO2");
                    }
                } else {
                    redirect(request, response, Constant.otp + "?esito=KO3");
                }
            }
        }
    }

    //LOGIN
    protected void loginBando(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        if (user != null && pass != null) {
            if (!user.trim().equals("") && !pass.trim().equals("")) {
                String password = Utility.convMd5(pass);
                Db_Bando dbb = new Db_Bando();
                UserBando ub = dbb.getUser(user, password);
                dbb.closeDB();
                if (ub != null) {
                    request.getSession().setAttribute("bandorif", ub.getCodiceBando());
                    request.getSession().setAttribute("statouser", ub.getStato());
                    request.getSession().setAttribute("username", ub.getUsername());
                    request.getSession().setAttribute("lang", "IT");
                    request.getSession().setAttribute("numcell", ub.getCell());
                    request.getSession().setAttribute("indemail", ub.getMail());
                    request.getSession().setAttribute("tipo", ub.getTipo());
                    request.getSession().setAttribute("sino", ub.getSino());
                    trackingAction(ub.getUsername(), "Login Portale");
                    redirect(request, response, getSiteRedirect(ub.getStato(), ub.getTipo()));
                } else {
                    redirect(request, response, Constant.errLogin + "KO1");
                }
            } else {
                redirect(request, response, Constant.errLogin + "KO2");
            }
        } else {
            redirect(request, response, Constant.errLogin + "KO3");
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            String param = request.getParameter("type");
            if (param == null) {
                param = "";
            }
            if (param.equals("out")) {
                logout(request, response);
            }
            if (param.equals("log")) {
                loginBando(request, response);
            }
            if (param.equals("otp")) {
                otpbando(request, response);
            }
        } catch (ServletException | IOException ex) {
            Constant.log.severe(bando+": "+ex.getMessage());
            trackingAction("service", "Error:LoginOperation: " + ex.getMessage());
        }
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
