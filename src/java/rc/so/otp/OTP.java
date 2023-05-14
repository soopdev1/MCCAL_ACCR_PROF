/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.otp;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static rc.so.otp.SMS_MJ.sendSMS2022;

/**
 *
 * @author rcosco
 */
public class OTP {

    public static String generaOTP(String codbando, String numero, String username) {
        int codmsg = 1;
        String otp = randomNumeric(8);

        Db_OTP db = new Db_OTP();
        String msg = db.getSMS(codbando, codmsg);
        boolean es1 = db.insOtp(codbando, username, otp, numero, codmsg);
        db.closeDB();

        String out;
        if (es1) {
            if (sendSMS2022(numero, msg + " " + otp)) {
                out = "SUCCESS";
            } else {
                out = "KO SMS";
            }
        } else {
            out = "KO Errore OTP";
        }
        return out;

    }

    public static boolean verificaOTP(
            String codbando,
            String otp,
            String username,
            int idsms
    ) {
        Db_OTP db = new Db_OTP();
        boolean pres = db.isOK(codbando, username, otp, idsms);
        if (pres) {
            db.cambiastato(codbando, username, idsms, "OK");
        }
        db.closeDB();
        return pres;
    }

}
