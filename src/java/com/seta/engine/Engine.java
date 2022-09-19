/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.engine;

/**
 *
 * @author srotella
 */
public class Engine {

    final static String user = "seta123";
    final static String password = "123456789!";

    public static boolean generaOTP(String codbando, String numero, String username) {
        OtpService_Service service = new OtpService_Service();
        OtpService ser = service.getOtpServicePort();
        Richiesta ric = new Richiesta();
        ric.setCodMsg(9);
        ric.setCodProgetto(codbando);
        ric.setNumcell(numero);
        ric.setUser(user);
        ric.setPswd(password);
        ric.setUserPortale(username);
        String ver = ser.insert(ric);
        if (ver.equalsIgnoreCase("Success")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean verificaOTP(String codbando, String otp, String username) {
        OtpService_Service service = new OtpService_Service();
        OtpService ser = service.getOtpServicePort();
        Verifica verifica = new Verifica();
        verifica.setCodProgetto(codbando);
        verifica.setOtp(otp);
        verifica.setPswd(password);
        verifica.setUser(user);
        verifica.setUserPortale(username);
        String ver = ser.verify(verifica);
        if (ver.equalsIgnoreCase("Success")) {
            return true;
        } else {
            return false;
        }
    }

}
