/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.action;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.joda.time.DateTime;

public class Constant {
    
    public static final String bando = "BA0F6";
    
    public static final ResourceBundle rb = ResourceBundle.getBundle("conf.ban");
    //LOG
    public static final Logger log = createLog("BandoF6", rb.getString("path.log"), rb.getString("date.log"));
    
    
    //Classic
    public static final String slash = "/";
    
    public static final String homePage = "bando_index.jsp";
    public static final String homePageAz = "bando_azind.jsp";
    public static final String homePageRup = "bando_repag.jsp";
    //otp
    public static final String otp = "home_otp.jsp";
    //Reset
    public static final String reset = "home_acc1.jsp";
    public static final String resetIOK = "home_acc1.jsp?esito=ok";
    public static final String resetIKO = "home_acc1.jsp?esito=ko";
    public static final String errLogin = "home.jsp?esito=";
    
    //operazioni
    public static final String pdfext = ".pdf";
    public static final String url = "https://www.google.com/recaptcha/api/siteverify";
    public static final String secret = "6LdXcR8TAAAAAA-LTbETf8FGo-4PRvgPCXsdUant";
    public static final String USER_AGENT = "Mozilla/5.0";
    public static final String patterndatesql = "yyyy-MM-dd HH:mm:ss";
    
    
    public static final String patternSql = "yyyy-MM-dd";
    
    
    
    
    
    private static Logger createLog(String appname, String folderini, String patterndatefolder) {
        Logger LOGGER = Logger.getLogger(appname);
        try {
            DateTime dt = new DateTime();
            String filename = appname + "-" + dt.toString("HHmmssSSS") + ".log";
            File dirING = new File(folderini);
            dirING.mkdirs();
            if (patterndatefolder != null) {
                File dirINGNew = new File(dirING.getPath() + File.separator + dt.toString(patterndatefolder));
                dirINGNew.mkdirs();
                filename = dirINGNew.getPath() + File.separator + filename;
            } else {
                filename = dirING.getPath() + File.separator + filename;
            }
            Handler fileHandler = new FileHandler(filename);
            LOGGER.addHandler(fileHandler);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            LOGGER.setLevel(Level.ALL);
        } catch (IOException localIOException) {
        }

        return LOGGER;
    }
    
    public static final String timestampSQL = "yyyy-MM-dd HH:mm:ss";
    public static final String timestampITA = "dd/MM/yyyy HH:mm:ss";
    
}
