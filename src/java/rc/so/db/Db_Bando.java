
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.db;

import rc.so.action.Constant;
import static rc.so.action.Constant.bando;
import static rc.so.action.Constant.patterndatesql;
import static rc.so.action.Constant.rb;
import rc.so.entity.AllegatoA;
import rc.so.entity.AllegatoB;
import rc.so.entity.AllegatoB1;
import rc.so.entity.AllegatoB1_field;
import rc.so.entity.Docbandi;
import rc.so.entity.Docenti;
import rc.so.entity.DocumentiDocente;
import rc.so.entity.Docuserbandi;
import rc.so.entity.Docuserconvenzioni;
import rc.so.entity.Domandecomplete;
import rc.so.entity.Faq;
import rc.so.entity.Registrazione;
import rc.so.entity.Reportistica;
import rc.so.entity.UserBando;
import rc.so.entity.UserValoriReg;
import rc.so.util.Utility;
import static rc.so.util.Utility.formatDate;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author raffaele
 */
public class Db_Bando {

    private Connection c = null;
    public Db_Bando() {

        String user = rb.getString("db.user");
        String password = rb.getString("db.pass");
        String host =  rb.getString("db.host") + ":3306/bandoh8";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Properties p = new Properties();
            p.put("user", user);
            p.put("password", password);
            p.put("characterEncoding", "UTF-8");
            p.put("passwordCharacterEncoding", "UTF-8");
            p.put("useSSL", "false");
            p.put("connectTimeout", "1000");
            p.put("useUnicode", "true");
            p.put("serverTimezone", "UTC");
            p.put("zeroDateTimeBehavior", "convertToNull");
            this.c = DriverManager.getConnection("jdbc:mysql://" + host, p);
        } catch (Exception ex) {
            ex.printStackTrace();
            if (this.c != null) {
                try {
                    this.c.close();
                } catch (SQLException ex1) {
                }
            }
            this.c = null;
        }
    }

    public void closeDB() {
        try {
            if (c != null) {
                this.c.close();
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
    }

    public Db_Bando(Connection conn) {
        try {
            this.c = conn;
        } catch (Exception ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
    }

    public Connection getConnectionDB() {
        return c;
    }

    public Connection getConnection() {
        return c;
    }

  

    public String curdate() {
        try {
            String sql = "SELECT now()";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return "";
    }

    public boolean bandoAttivoRaf(String cod) {
        try {
            String sql = "SELECT * FROM elencobandi WHERE codbando = ? AND ATTIVO = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cod);
            ps.setString(2, "SI");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String dataapertura = rs.getString("dataapertura");
                String datafinereg = rs.getString("datafineregistrazione");
                DateTime datafine = formatDate(datafinereg, patterndatesql);
                DateTime dataap = formatDate(dataapertura, patterndatesql);
                DateTime datenow = formatDate(curtime(), patterndatesql);
                if (datafine.isAfter(datenow) && dataap.isBefore(datenow)) {
                    return true;
                } else {
                    return datafine.equals(datenow);
                }
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public String curtime() {
        try {
            String sql = "SELECT now()";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return "";
    }

    public ArrayList<Registrazione> listaCampiReg(String cod, String table) {
        ArrayList<Registrazione> li = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table + " WHERE codbando = ? and visibile = ? order by ordinamento";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cod);
            ps.setString(2, "SI");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Registrazione reg = new Registrazione(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9), rs.getString(10));
                li.add(reg);
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return li;
    }

    public ArrayList<String[]> getAllProvince() {
        ArrayList<String[]> val = new ArrayList<>();
        try {
            String sql = "SELECT * FROM province order by descrizione";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] v1 = {rs.getString(1), rs.getString(2) + " (" + rs.getString(3) + ")"};
                val.add(v1);
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return val;
    }

    public ArrayList<String[]> occupazione() {
        ArrayList<String[]> val = new ArrayList<>();
        try {
            String sql = "SELECT * FROM occupazione";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] v1 = {rs.getString(1), rs.getString(2)};
                val.add(v1);
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return val;
    }

    public ArrayList<String[]> getTipoDoc() {
        ArrayList<String[]> val = new ArrayList<>();
        try {
            String sql = "SELECT * FROM doc_validi ORDER BY descrizione";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] v1 = {rs.getString(1), rs.getString(2)};
                val.add(v1);
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return val;
    }

    public String getDescrizioneBando(String cod) {
        try {
            String sql = "SELECT descrizione FROM elencobandi WHERE codbando = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cod);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return "Descrizione non disponibile.";
    }

    public String getDescrizioneAltroAll(String cod) {
        try {
            String sql = "SELECT descrizione FROM tipiall WHERE id = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cod);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return "Descrizione non disponibile.";
    }

    public String descr_bandoaperto(String cod) {
        try {
            String sql = "SELECT datachiusura FROM elencobandi WHERE codbando = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cod);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String dat = rs.getString(1).split("\\.")[0];
                DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                DateTime dtout = fmt.parseDateTime(dat);
                return "L'accreditamento a questo bando è attivo fino alle ore " + dtout.toString("HH:mm:ss") + " del " + dtout.toString("dd/MM/yyyy") + ".";
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return "";
    }

    public String descr_bandochiuso(String cod) {
        try {
            String sql = "SELECT dataapertura,datachiusura FROM elencobandi WHERE codbando = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cod);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String dat_aper = rs.getString(1).split("\\.")[0];
                String dat_chiu = rs.getString(2).split("\\.")[0];
                DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                DateTime dt1 = fmt.parseDateTime(dat_aper);
                DateTime dt2 = fmt.parseDateTime(dat_chiu);
                return "L'accreditamento è disponibile dalle ore " + dt1.toString("HH:mm") + " del "
                        + dt1.toString("dd/MM/yyyy") + " alle ore " + dt2.toString("HH:mm:ss") + " del " + dt2.toString("dd/MM/yyyy") + ".";
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return "";
    }

    public String getScadenzaBando(String cod) {
        try {
            String sql = "SELECT datachiusura FROM elencobandi WHERE codbando = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cod);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return "Descrizione non disponibile.";
    }

    public String getInizioBando(String cod) {
        try {
            String sql = "SELECT dataapertura FROM elencobandi WHERE codbando = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cod);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return "Descrizione non disponibile.";
    }

    public ArrayList<Faq> listaFaqBando(String cod) {
        ArrayList<Faq> val = new ArrayList<>();
        try {
            String sql = "SELECT * FROM faq WHERE codbando = ? AND stato = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cod);
            ps.setString(2, "1");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Faq fa = new Faq(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
                val.add(fa);
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return val;
    }

    public Faq faq(String id) {
        Faq f = new Faq();
        try {
            String sql = "SELECT * FROM faq WHERE id = ? ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                f = new Faq(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));

            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return f;
    }

    public boolean isPresenteUsername(String username) {
        try {
            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return true;
    }

    public boolean notaIsPresente(String bandorif, String username, String nota) {
        try {
            String sql = "SELECT note FROM docuserbandi WHERE codbando = ? AND username = ? AND codicedoc = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, bandorif);
            ps.setString(2, username);
            ps.setString(3, "ALTR");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (nota.trim().equals(rs.getString(1).trim())) {
                    return true;
                }
            }

            return false;

        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public boolean isDomandaPresente(String bandorif, String username) {
        try {
            String sql = "SELECT id FROM domandecomplete WHERE codbando = ? AND username = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, bandorif);
            ps.setString(2, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public boolean insertUserReg(UserBando ub) {
        try {
            String ins = "INSERT INTO users (username,codiceBando,datareg,password,tipo,cell,mail) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = this.c.prepareStatement(ins);
            ps.setString(1, ub.getUsername());
            ps.setString(2, ub.getCodiceBando());
            ps.setString(3, ub.getDatareg());
            ps.setString(4, ub.getPassword());
            ps.setString(5, ub.getTipo());
            ps.setString(6, ub.getCell());
            ps.setString(7, ub.getMail());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public int insertUserRegistration(ArrayList<UserValoriReg> liout) {
        int x = 0;
        for (int i = 0; i < liout.size(); i++) {
            try {
                UserValoriReg uvr = liout.get(i);
                String ins = "INSERT INTO usersvalori (codbando,username,campo,valore,datareg) VALUES (?,?,?,?,?)";
                PreparedStatement ps = this.c.prepareStatement(ins);
                ps.setString(1, uvr.getCodbando());
                ps.setString(2, uvr.getUsername());
                ps.setString(3, uvr.getCampo());
                ps.setString(4, uvr.getValore());
                ps.setString(5, uvr.getDatareg());
                ps.execute();
                x++;
            } catch (SQLException ex) {
                Constant.log.severe(bando + ": " + ex.getMessage());
                return 0;
            }
        }
        return x;
    }

    public boolean esisteAllegatoA(String username) {
        try {
            String sql = "Select username from allegato_a where username='" + username + "' ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public boolean esisteAllegatoB(String username) {
        try {
            String sql = "Select username from allegato_b where username='" + username + "' ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public boolean esisteAllegatoB1(String username) {
        try {
            ArrayList<Docenti> al = getDocenti(username);
            String sql = "Select count(*) from allegato_b1 where username='" + username + "' ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) == 0) {
                    return false;
                } else if (rs.getInt(1) == al.size()) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public boolean esisteAllegatoB1Field(String username, int iddocente) {
        try {
            String sql = "Select * from allegato_b1_field where username=? and iddocente=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, username);
            ps.setInt(2, iddocente);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public boolean esisteAllegatoB1Field(String username) {
        try {
            String sql = "Select * from allegato_b1_field where username=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public boolean remdatiAllegatoA(String username) {
        String query = "delete from allegato_a where username = ?";
        try {
            PreparedStatement ps = this.c.prepareStatement(query);
            ps.setString(1, username);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean remdatiAllegatoB(String username) {
        String query = "delete from allegato_b where username = ?";
        try {
            PreparedStatement ps = this.c.prepareStatement(query);
            ps.setString(1, username);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean esisteusrnamedomande(String username) {
        try {
            String sql = "Select username from bandoh8 where username='" + username + "' ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public boolean deletebandoh8(String username) {
        try {
            String sql = "DELETE FROM bandoh8 where username='" + username + "' ";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public UserBando getUser(String user, String psw) {
        try {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, psw);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String bando = rs.getString("codiceBando");
                String tipo = rs.getString("tipo");
                if (tipo.equals("1")) {
                    return new UserBando(user, bando, rs.getString("stato"), rs.getString("datareg"), rs.getString("timestamp"),
                            psw, rs.getString("cell"), rs.getString("mail"), rs.getString("tipo"), getSiNo(user));
                } else {
                    boolean att = bandoAttivoRaf(bando);
                    if (att) {
                        return new UserBando(user, bando, rs.getString("stato"), rs.getString("datareg"), rs.getString("timestamp"),
                                psw, rs.getString("cell"), rs.getString("mail"), rs.getString("tipo"), getSiNo(user));
                    }
                }
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return null;
    }

    public String getSiNo(String username) {
        String sino = "";
        String query = "select valore from usersvalori where campo='accreditato' and username=?";
        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sino = rs.getString("valore");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sino;
    }

    public boolean esisteUserMail(String user, String mail) {
        try {
            String sql = "SELECT * FROM users WHERE username = ? AND mail = ? AND stato = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, mail);
            ps.setString(3, "1");
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public boolean cambiaValoreUser(String username, String field, String value) {
        try {
            String upd = "UPDATE users SET " + field + " = ? WHERE username = ?";
            PreparedStatement ps = this.c.prepareStatement(upd);
            ps.setString(1, value);
            ps.setString(2, username);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public ArrayList<Docbandi> listaDocRichiestiBando(String cod) {
        ArrayList<Docbandi> val = new ArrayList<>();
        try {
            String sql = "SELECT * FROM docbandi WHERE codbando = ?  order by ordinamento,codicedoc";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cod);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Docbandi db = new Docbandi(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8),
                        rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13));
                val.add(db);

            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return val;
    }

    public ArrayList<Docuserbandi> listaDocUserBando(String cod, String username) {
        ArrayList<Docuserbandi> val = new ArrayList<>();
        try {
            String sql = "SELECT * FROM docuserbandi WHERE codbando = ? AND username = ? AND stato = ?   order by datacar";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cod);
            ps.setString(2, username);
            ps.setString(3, "1");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                Docuserbandi db = new Docuserbandi(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
                val.add(db);

            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return val;
    }

    public ArrayList<Docuserbandi> listaDocUserBandoAltri(String cod, String username) {
        ArrayList<Docuserbandi> val = new ArrayList<>();
        try {
            String sql = "SELECT * FROM docuserbandi WHERE codbando = ? AND username = ? AND stato = ? AND codicedoc = 'ALTR'  order by datacar";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cod);
            ps.setString(2, username);
            ps.setString(3, "1");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Docuserbandi db = new Docuserbandi(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
                val.add(db);

            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return val;
    }

    public ArrayList<Docuserbandi> listaDocUserBando(String cod, String username, String coddoc) {
        ArrayList<Docuserbandi> val = new ArrayList<>();
        try {
            String sql = "SELECT * FROM docuserbandi WHERE codbando = ? AND username = ? AND stato = ? AND codicedoc = ? order by datacar";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cod);
            ps.setString(2, username);
            ps.setString(3, "1");
            ps.setString(4, coddoc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Docuserbandi db = new Docuserbandi(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
                val.add(db);
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return val;
    }

    public Docuserbandi docUserBando(String cod, String username, String codicedoc, String tipologia, String note) {
        try {
            String sql = "SELECT * FROM docuserbandi WHERE codbando = ? AND username = ? AND codicedoc = ? AND stato = ? AND tipodoc = ? AND note = ? order by datacar";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cod);
            ps.setString(2, username);
            ps.setString(3, codicedoc);
            ps.setString(4, "1");
            ps.setString(5, tipologia);
            ps.setString(6, note);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Docuserbandi db = new Docuserbandi(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
                return db;
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return null;
    }

    public boolean insertDocumentUserBando(Docuserbandi dub) {
        try {
            String ins = "INSERT INTO docuserbandi (codbando,username,codicedoc,path,datareg) VALUES (?,?,?,?,?)";
            PreparedStatement ps = this.c.prepareStatement(ins);
            ps.setString(1, dub.getCodbando());
            ps.setString(2, dub.getUsername());
            ps.setString(3, dub.getCodicedoc());
            ps.setString(4, dub.getPath());
            ps.setString(5, dub.getDatacar());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public ArrayList<UserValoriReg> listaValoriUserbando(String cod, String username) {
        ArrayList<UserValoriReg> li = new ArrayList<>();
        try {
            String sql = "SELECT * FROM usersvalori WHERE codbando = ? AND username = ? order by campo";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cod);
            ps.setString(2, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserValoriReg reg = new UserValoriReg(
                        rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6));
                li.add(reg);
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return li;
    }

    public boolean insertDocUserBando(Docuserbandi dub) {
//        Docuserbandi old = docUserBando(dub.getCodbando(), dub.getUsername(), dub.getCodicedoc(), dub.getTipodoc(), dub.getNote());
//        if (old == null || old.getTipodoc().equals("AAL1")) {
        try {
            String ins = "INSERT INTO docuserbandi (codbando,username,codicedoc,stato,path,datacar,tipodoc,note) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = this.c.prepareStatement(ins);
            ps.setString(1, dub.getCodbando());
            ps.setString(2, dub.getUsername());
            ps.setString(3, dub.getCodicedoc());
            ps.setString(4, dub.getStato());
            ps.setString(5, dub.getPath());
            ps.setString(6, dub.getDatacar());
            ps.setString(7, dub.getTipodoc());
            ps.setString(8, Utility.replaeSpecialCharacter(dub.getNote()));
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
//        }
        return false;
    }

    public String getPath(String id) {
        try {
            String sql = "SELECT url FROM path WHERE id = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return "-";
    }

    public String getPathDocUserBando(String bandorif, String username, String codicedoc) {
        try {
            String sql = "SELECT path FROM docuserbandi WHERE codbando = ? AND username = ? AND codicedoc = ? AND tipodoc = ? AND stato = ? ORDER BY timestamp DESC LIMIT 1";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, bandorif);
            ps.setString(2, username);
            ps.setString(3, codicedoc);
            ps.setString(4, "-");
            ps.setString(5, "1");

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String out = rs.getString(1);
                return out;
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return "-";
    }

    public String getPathDocUserBando(String bandorif, String username, String codicedoc, String tipologia, String note) {
        try {
            String sql = "SELECT path FROM docuserbandi WHERE codbando = ? AND username = ? AND codicedoc = ? AND tipodoc = ? AND stato = ? AND note = ? ORDER BY timestamp DESC LIMIT 1";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, bandorif);
            ps.setString(2, username);
            ps.setString(3, codicedoc);
            ps.setString(4, tipologia);
            ps.setString(5, "1");
            ps.setString(6, note);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return "-";
    }

    public String getPathDocModello(String bandorif, String codicedoc) {
        try {
            String sql = "SELECT download FROM docbandi WHERE codbando =  ? AND codicedoc = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, bandorif);
            ps.setString(2, codicedoc);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return "-";
    }

    public boolean insertDomandaDaCanc(String bandorif, String username, String datacanc, String codiceconferma) {
        try {
            String ins = "INSERT INTO annulladomande (codbando,username,datacanc,codiceconferma) VALUES (?,?,?,?)";
            PreparedStatement ps = this.c.prepareStatement(ins);
            ps.setString(1, bandorif);
            ps.setString(2, username);
            ps.setString(3, datacanc);
            ps.setString(4, codiceconferma);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public String[] controllaDomandadaEliminare(String codconf) {
        try {
            String sql = "SELECT * FROM annulladomande WHERE codiceconferma =  ? AND stato = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, codconf);
            ps.setString(2, "0");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String[] v = {rs.getString(1), rs.getString(2), rs.getString(3)};
                return v;
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return null;
    }

    public boolean cambiaStatoUser(String username, String stato) {
        try {

            String upd = "UPDATE users SET stato = ? WHERE username = ?";
            PreparedStatement ps = this.c.prepareStatement(upd);
            ps.setString(1, stato);
            ps.setString(2, username);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public boolean removeAllDocUserBando(String codbando, String username) {
        try {
            String canc = "DELETE FROM docuserbandi WHERE codbando = ? AND username = ?";
            PreparedStatement ps = this.c.prepareStatement(canc);
            ps.setString(1, codbando);
            ps.setString(2, username);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public boolean removeSingleDocUserBando(String codbando, String username, String codicedoc, String tipologia) {
        try {
            String canc = "DELETE FROM docuserbandi WHERE codbando = ? AND username = ? AND codicedoc = ? AND tipodoc = ?";
            PreparedStatement ps = this.c.prepareStatement(canc);
            ps.setString(1, codbando);
            ps.setString(2, username);
            ps.setString(3, codicedoc);
            ps.setString(4, tipologia);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public boolean removeSingleDocUserBandoAltri(String codbando, String username, String codicedoc, String note) {
        try {
            String canc = "DELETE FROM docuserbandi WHERE codbando = ? AND username = ? AND codicedoc = ? AND note = ?";
            PreparedStatement ps = this.c.prepareStatement(canc);
            ps.setString(1, codbando);
            ps.setString(2, username);
            ps.setString(3, codicedoc);
            ps.setString(4, note);
            ps.executeUpdate();

            return true;
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public boolean removeSingleDocUserBandoAltri(String codbando, String username, String codicedoc, String tipologia, String note) {
        try {
            String canc = "DELETE FROM docuserbandi WHERE codbando = ? AND username = ? AND codicedoc = ? AND tipodoc = ? AND note = ?";
            PreparedStatement ps = this.c.prepareStatement(canc);
            ps.setString(1, codbando);
            ps.setString(2, username);
            ps.setString(3, codicedoc);
            ps.setString(4, tipologia);
            ps.setString(5, note);
            ps.executeUpdate();

            return true;
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public boolean removeAllValoriUserBando(String codbando, String username) {
        try {
            String canc = "DELETE FROM usersvalori WHERE codbando = ? AND username = ?";
            PreparedStatement ps = this.c.prepareStatement(canc);
            ps.setString(1, codbando);
            ps.setString(2, username);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public boolean removeAllValoriUserBandoDOC1(String codbando, String username) {
        try {
            String canc = "DELETE FROM usersvalori WHERE codbando = ? AND username = ? AND campo IN (SELECT campo FROM domandaonline WHERE codbando = ? AND visibile = ?)";
            PreparedStatement ps = this.c.prepareStatement(canc);
            ps.setString(1, codbando);
            ps.setString(2, username);
            ps.setString(3, codbando);
            ps.setString(4, "SI");
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public boolean cambiaStatoRichiestaAnnulla(String id, String stato) {
        try {
            String upd = "UPDATE annulladomande SET stato = ? WHERE id = ?";
            PreparedStatement ps = this.c.prepareStatement(upd);
            ps.setString(1, stato);
            ps.setString(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public ArrayList<String[]> listCodFiscBando(String codbando) {
        ArrayList<String[]> liout = new ArrayList<>();
        ArrayList<String[]> liout2 = new ArrayList<>();
        try {
            String sql = "SELECT username,campo,valore FROM usersvalori WHERE codbando = ? and (campo = ? or campo = ?)";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, codbando);
            ps.setString(2, "cf");
            ps.setString(3, "linea");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] v1 = {rs.getString(1), rs.getString(2), rs.getString(3)};
                liout.add(v1);
            }

            for (int i = 0; i < liout.size(); i++) {
                String usr = liout.get(i)[0];
                String out[] = new String[2];
                for (int x = 0; x < liout.size(); x++) {
                    if (usr.equals(liout.get(x)[0])) {
                        if (liout.get(x)[1].equals("cf")) {
                            out[0] = liout.get(x)[2];
                        } else if (liout.get(x)[1].equals("linea")) {
                            out[1] = liout.get(x)[2];
                        }
                    }
                }
                liout2.add(out);
            }

        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return liout2;
    }

    public boolean inviaDomanda(Domandecomplete doc) {
        try {
            String ins = "INSERT INTO domandecomplete (id,codbando,username,datainvio) VALUES (?,?,?,?)";
            PreparedStatement ps = this.c.prepareStatement(ins);
            ps.setString(1, doc.getId());
            ps.setString(2, doc.getCodbando());
            ps.setString(3, doc.getUsername());
            ps.setString(4, doc.getDatainvio());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public Domandecomplete isPresenteDomandaCompleta(String bandorif, String username) {
        try {
            String sql = "SELECT * FROM domandecomplete WHERE codbando = ? and username = ? AND stato = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, bandorif);
            ps.setString(2, username);
            ps.setString(3, "1");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Domandecomplete dc = new Domandecomplete(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6));
                return dc;
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return null;
    }

    public Domandecomplete isDomandaCompletaConsolidata(String bandorif, String username) {
        try {
            String sql = "SELECT * FROM domandecomplete WHERE codbando = ? and username = ? AND stato = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, bandorif);
            ps.setString(2, username);
            ps.setString(3, "1");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Domandecomplete dc = new Domandecomplete(rs.getString(1), rs.getString(7));
                return dc;
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return null;
    }

    public boolean annullaDomandaCompleta(String bandorif, String username) {
        try {
            String upd = "UPDATE domandecomplete set stato = ? WHERE codbando = ? and username = ?";
            PreparedStatement ps = this.c.prepareStatement(upd);
            ps.setString(1, "0");
            ps.setString(2, bandorif);
            ps.setString(3, username);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public boolean insertTracking(String idUser, String azione) {
        try {
            String ins = "INSERT INTO tracking (idUser,azione) VALUES (?,?)";
            PreparedStatement ps = this.c.prepareStatement(ins);
            ps.setString(1, idUser);
            ps.setString(2, azione);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public ArrayList<String[]> getListaAllBando(String bando) {
        ArrayList<String[]> val = new ArrayList<>();
        try {
            String sql = "SELECT * FROM tipiall WHERE bandorif = ? order by descrizione";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, bando);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] v1 = {rs.getString(1), rs.getString(2), rs.getString(3)};
                val.add(v1);
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return val;
    }

    public ArrayList<String[]> getListaAllBandoRUP(String bando) {
        ArrayList<String[]> val = new ArrayList<>();
        try {
            String sql = "SELECT * FROM tipiall_rup WHERE bandorif = ? order by descrizione";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, bando);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] v1 = {rs.getString(1), rs.getString(2), rs.getString(3)};
                val.add(v1);
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return val;
    }

    public ArrayList<Domandecomplete> listaconsegnatestato(String lineaintervento) {
        ArrayList<Domandecomplete> li = new ArrayList<>();
        try {
            String sql = "SELECT * FROM bandoh8 where lineaintervento ='" + lineaintervento + "' and coddomanda<>'-'";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                li.add(new Domandecomplete(rs.getString("coddomanda"), rs.getString("username"), rs.getString("nome"),
                        rs.getString("cognome"), rs.getString("cf"), rs.getString("dataconsegna"), rs.getString("comune"), rs.getString("protocollo"), rs.getString("dataprotocollo"), rs.getString("stato_domanda")));
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return li;
    }

    public ArrayList<Reportistica> listareports(String bandorif) {
        ArrayList<Reportistica> lc = new ArrayList<>();
        try {
            String sql = "SELECT * FROM reportistica WHERE bando = ? ORDER BY ordine";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, bandorif);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reportistica re = new Reportistica(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
                        Utility.formatTimestamp(rs.getString(9)), rs.getString(10), rs.getString(11));
                lc.add(re);
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return lc;
    }

    public boolean removeAllcampiDomanda(String codbando, String username) {
        try {
            String canc = "DELETE FROM bandoh8 WHERE codbando = ? AND username = ?";
            PreparedStatement ps = this.c.prepareStatement(canc);
            ps.setString(1, codbando);
            ps.setString(2, username);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public boolean removeAllDomandaValue(String codbando, String username) {
        try {
            String canc = "UPDATE bandod3 SET stato= 'A' WHERE codbando = ? AND username = ?";
            PreparedStatement ps = this.c.prepareStatement(canc);
            ps.setString(1, codbando);
            ps.setString(2, username);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public boolean isDomandaAnnullata(String bandorif, String username) {
        try {
            String sel = "SELECT id FROM annulladomande WHERE codbando = ? and username = ? AND stato = ?";
            PreparedStatement ps = this.c.prepareStatement(sel);
            ps.setString(1, bandorif);
            ps.setString(2, username);
            ps.setString(3, "1");

            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public String getPathFaq(String id) {
        try {
            String sql = "SELECT pathpdf FROM faq WHERE id=?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return "--";
    }

    public boolean modificaFaq(Faq faq) {
        try {
            String upd = "UPDATE faq SET nomedoc = ?, descrizionedoc= ?, pathpdf= ? WHERE id = ?";
            PreparedStatement ps = this.c.prepareStatement(upd);
            ps.setString(1, faq.getNomedoc());
            ps.setString(2, faq.getDescrizionedoc());
            ps.setString(3, faq.getPathpdf());
            ps.setString(4, faq.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public boolean modificaFaqNoFile(Faq faq) {
        try {
            String upd = "UPDATE faq SET nomedoc = ?, descrizionedoc= ? WHERE id = ?";
            PreparedStatement ps = this.c.prepareStatement(upd);
            ps.setString(1, faq.getNomedoc());
            ps.setString(2, faq.getDescrizionedoc());
            ps.setString(3, faq.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public String generaProt(String username) {
        try {
            String ins = "INSERT INTO protocollidomande (username) VALUES (?)";
            PreparedStatement ps = this.c.prepareStatement(ins);
            ps.setString(1, username);
            ps.execute();
            String select = "SELECT LPAD(id, 10, '0') FROM protocollidomande WHERE username = ? ORDER BY timestamp DESC LIMIT 1";
            PreparedStatement ps1 = this.c.prepareStatement(select);
            ps1.setString(1, username);
            ResultSet rs = ps1.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return "0";
    }

    public ArrayList<String[]> comuni() {
        ArrayList<String[]> val = new ArrayList<>();
        try {
            String sql = "SELECT * FROM comuni order by nome,provincia";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] o = {StringUtils.leftPad(rs.getString(1), 5, "0"), rs.getString(2), rs.getString(4), rs.getString(5)};
                val.add(o);
            }

        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return val;
    }

    public boolean invioattivo(String bando) {
        try {
            String sql = "SELECT codbando FROM elencobandi WHERE codbando = ? AND now()<datainizioclick OR now()>datafineclick AND attivo = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, bando);
            ps.setString(2, "SI");
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public ArrayList<String[]> getAllStatiEur() {
        ArrayList<String[]> val = new ArrayList<>();
        try {
            String sql = "SELECT * FROM nazeur ORDER BY ordine";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] v1 = {rs.getString(2), rs.getString(2)};
                val.add(v1);
            }
        } catch (SQLException ex) {
            Constant.log.severe(ex.getMessage());
        }
        return val;
    }

    public ArrayList<String[]> getSesso() {
        ArrayList<String[]> val = new ArrayList<>();
        try {
            String sql = "SELECT * FROM sesso order by sesso desc";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] v1 = {rs.getString(1), rs.getString(1)};
                val.add(v1);
            }
        } catch (SQLException ex) {
            Constant.log.severe(ex.getMessage());
        }
        return val;
    }
    
    

    public ArrayList<Domandecomplete> listaconsegnatestato(String data_da, String data_a) {
        ArrayList<Domandecomplete> li = new ArrayList<>();

        try {
            String sql = "SELECT * FROM bandoh8 where coddomanda<>'-'";
            if (!data_da.trim().equals("") && data_a.equals("")) {
                sql = sql + " and dataconsegna like '" + data_da + "%'";
            } else if (!data_da.trim().equals("") && !data_a.equals("")) {
                sql = sql + " and dataconsegna>'" + data_da + " 00:00:00' and dataconsegna <'" + data_a + " 23:59:59'";
            }
            PreparedStatement ps = this.c.prepareStatement(sql + " limit 1000");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                li.add(new Domandecomplete(rs.getString("coddomanda"), rs.getString("username"), rs.getString("nome"),
                        rs.getString("cognome"), rs.getString("cf"), rs.getString("dataconsegna"),
                        rs.getString("societa"), rs.getString("pivacf"), rs.getString("pec"), rs.getString("stato_domanda")));
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return li;
    }

    public boolean insAllegatoA(String username, String enteistituzionepubblica, String associazione, String ordineprofessionale, String soggettoprivato, String formazione,
            String regione1, String iscrizione1, String servizi, String regione2, String iscrizione2, String ateco, String numaule, String citta1,
            String provincia1, String indirizzo1, String estremi1, String accreditamento1, String responsabile1, String amministrativo1, String recapiti1,
            String citta2, String provincia2, String indirizzo2, String estremi2, String accreditamento2, String responsabile2, String amministrativo2,
            String recapiti2, String citta3, String provincia3, String indirizzo3, String estremi3, String accreditamento3, String responsabile3,
            String amministrativo3, String recapiti3, String citta4, String provincia4, String indirizzo4, String estremi4, String accreditamento4,
            String responsabile4, String amministrativo4, String recapiti4, String citta5, String provincia5, String indirizzo5, String estremi5,
            String accreditamento5, String responsabile5, String amministrativo5, String recapiti5, String attivita, String destinatari, String finanziamento,
            String committente, String periodo, String attivita2, String destinatari2, String finanziamento2, String committente2, String periodo2, String attivita3,
            String destinatari3, String finanziamento3, String committente3, String periodo3, String attivita4, String destinatari4, String finanziamento4,
            String committente4, String periodo4, String attivita5, String destinatari5, String finanziamento5, String committente5, String periodo5,
            String noconsorzio, String consorzio, String nomeconsorzio, String pec, String numdocenti) {
        String query = "insert into allegato_a (username,enteistituzionepubblica,associazione,ordineprofessionale,soggettoprivato,formazione,regione1,iscrizione1,servizi,"
                + "regione2,iscrizione2,ateco,numaule,citta1,provincia1,indirizzo1,estremi1,accreditamento1,responsabile1,amministrativo1,recapiti1,"
                + "citta2,provincia2,indirizzo2,estremi2,accreditamento2,responsabile2,amministrativo2,recapiti2,citta3,provincia3,indirizzo3,estremi3,"
                + "accreditamento3,responsabile3,amministrativo3,recapiti3,citta4,provincia4,indirizzo4,estremi4,accreditamento4,responsabile4,"
                + "amministrativo4,recapiti4,citta5,provincia5,indirizzo5,estremi5,accreditamento5,responsabile5,amministrativo5,recapiti5,attivita,"
                + "destinatari,finanziamento,committente,periodo,attivita2,destinatari2,finanziamento2,committente2,periodo2,attivita3,destinatari3,"
                + "finanziamento3,committente3,periodo3,attivita4,destinatari4,finanziamento4,committente4,periodo4,attivita5,destinatari5,"
                + "finanziamento5,committente5,periodo5,noconsorzio,consorzio,nomeconsorzio,pec,numdocenti) value (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
                + ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, enteistituzionepubblica);
            ps.setString(3, associazione);
            ps.setString(4, ordineprofessionale);
            ps.setString(5, soggettoprivato);
            ps.setString(6, formazione);
            ps.setString(7, regione1);
            ps.setString(8, iscrizione1);
            ps.setString(9, servizi);
            ps.setString(10, regione2);
            ps.setString(11, iscrizione2);
            ps.setString(12, ateco);
            ps.setString(13, numaule);
            ps.setString(14, citta1);
            ps.setString(15, provincia1);
            ps.setString(16, indirizzo1);
            ps.setString(17, estremi1);
            ps.setString(18, accreditamento1);
            ps.setString(19, responsabile1);
            ps.setString(20, amministrativo1);
            ps.setString(21, recapiti1);
            ps.setString(22, citta2);
            ps.setString(23, provincia2);
            ps.setString(24, indirizzo2);
            ps.setString(25, estremi2);
            ps.setString(26, accreditamento2);
            ps.setString(27, responsabile2);
            ps.setString(28, amministrativo2);
            ps.setString(29, recapiti2);
            ps.setString(30, citta3);
            ps.setString(31, provincia3);
            ps.setString(32, indirizzo3);
            ps.setString(33, estremi3);
            ps.setString(34, accreditamento3);
            ps.setString(35, responsabile3);
            ps.setString(36, amministrativo3);
            ps.setString(37, recapiti3);
            ps.setString(38, citta4);
            ps.setString(39, provincia4);
            ps.setString(40, indirizzo4);
            ps.setString(41, estremi4);
            ps.setString(42, accreditamento4);
            ps.setString(43, responsabile4);
            ps.setString(44, amministrativo4);
            ps.setString(45, recapiti4);
            ps.setString(46, citta5);
            ps.setString(47, provincia5);
            ps.setString(48, indirizzo5);
            ps.setString(49, estremi5);
            ps.setString(50, accreditamento5);
            ps.setString(51, responsabile5);
            ps.setString(52, amministrativo5);
            ps.setString(53, recapiti5);
            ps.setString(54, attivita);
            ps.setString(55, destinatari);
            ps.setString(56, finanziamento);
            ps.setString(57, committente);
            ps.setString(58, periodo);
            ps.setString(59, attivita2);
            ps.setString(60, destinatari2);
            ps.setString(61, finanziamento2);
            ps.setString(62, committente2);
            ps.setString(63, periodo2);
            ps.setString(64, attivita3);
            ps.setString(65, destinatari3);
            ps.setString(66, finanziamento3);
            ps.setString(67, committente3);
            ps.setString(68, periodo3);
            ps.setString(69, attivita4);
            ps.setString(70, destinatari4);
            ps.setString(71, finanziamento4);
            ps.setString(72, committente4);
            ps.setString(73, periodo4);
            ps.setString(74, attivita5);
            ps.setString(75, destinatari5);
            ps.setString(76, finanziamento5);
            ps.setString(77, committente5);
            ps.setString(78, periodo5);
            ps.setString(79, noconsorzio);
            ps.setString(80, consorzio);
            ps.setString(81, nomeconsorzio);
            ps.setString(82, pec);
            ps.setString(83, numdocenti);
            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insAllegatoB(String username, String nome, String cognome, String appartenenza, String inquadramento,
            String nome2, String cognome2, String appartenenza2, String inquadramento2, String nome3, String cognome3,
            String appartenenza3, String inquadramento3, String nome4, String cognome4, String appartenenza4,
            String inquadramento4, String nome5, String cognome5, String appartenenza5, String inquadramento5) {
        String query = "insert into allegato_b (username,nome,cognome,appartenenza,inquadramento,nome2,"
                + "cognome2,appartenenza2,inquadramento2,nome3,cognome3,appartenenza3,"
                + "inquadramento3,nome4,cognome4,appartenenza4,inquadramento4,nome5,"
                + "cognome5,appartenenza5,inquadramento5) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, nome);
            ps.setString(3, cognome);
            ps.setString(4, appartenenza);
            ps.setString(5, inquadramento);
            ps.setString(6, nome2);
            ps.setString(7, cognome2);
            ps.setString(8, appartenenza2);
            ps.setString(9, inquadramento2);
            ps.setString(10, nome3);
            ps.setString(11, cognome3);
            ps.setString(12, appartenenza3);
            ps.setString(13, inquadramento3);
            ps.setString(14, nome4);
            ps.setString(15, cognome4);
            ps.setString(16, appartenenza4);
            ps.setString(17, inquadramento4);
            ps.setString(18, nome5);
            ps.setString(19, cognome5);
            ps.setString(20, appartenenza5);
            ps.setString(21, inquadramento5);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Docenti> getDocenti(String username) {
        String query = "select * from allegato_b where username=?";
        ArrayList<Docenti> al = new ArrayList<>();
        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Docenti docenti = new Docenti();
                docenti.setNome(rs.getString("nome"));
                docenti.setCognome(rs.getString("cognome"));
                if (!rs.getString("nome").trim().equals("")) {
                    al.add(docenti);
                }
                Docenti docenti2 = new Docenti();
                docenti2.setNome(rs.getString("nome2"));
                docenti2.setCognome(rs.getString("cognome2"));
                if (!rs.getString("nome2").trim().equals("")) {
                    al.add(docenti2);
                }
                Docenti docenti3 = new Docenti();
                docenti3.setNome(rs.getString("nome3"));
                docenti3.setCognome(rs.getString("cognome3"));
                if (!rs.getString("nome3").trim().equals("")) {
                    al.add(docenti3);
                }
                Docenti docenti4 = new Docenti();
                docenti4.setNome(rs.getString("nome4"));
                docenti4.setCognome(rs.getString("cognome4"));
                if (!rs.getString("nome4").trim().equals("")) {
                    al.add(docenti4);
                }
                Docenti docenti5 = new Docenti();
                docenti5.setNome(rs.getString("nome5"));
                docenti5.setCognome(rs.getString("cognome5"));
                if (!rs.getString("nome5").trim().equals("")) {
                    al.add(docenti5);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return al;
    }

    public boolean verificaDomandaCompleta(String username) {
        String query = "select username from domandecomplete where username=?";
        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insAllegatoB1(String idallegato_b1, String username, String allegatocv,
            String allegatodr, String allegatob1) {
        String query = "insert into allegato_b1 (idallegato_b1,username,allegatocv,allegatodr,allegatob1) values (?,?,?,?,?)";
        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, idallegato_b1);
            ps.setString(2, username);
            ps.setString(3, allegatocv);
            ps.setString(4, allegatodr);
            ps.setString(5, allegatob1);
            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isPresenzaAllegatoB1(String idallegato_b1, String username) {
        String query = "select * from allegato_b1 where idallegato_b1=? and username=?";
        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, idallegato_b1);
            ps.setString(2, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<AllegatoB1> getAllegatoB1(String username, String id) {
        ArrayList<AllegatoB1> al = new ArrayList<>();
        String query = "select * from allegato_b1 where username=? and idallegato_b1=?";
        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                AllegatoB1 b1 = new AllegatoB1();
                b1.setId(rs.getString("idallegato_b1"));
                b1.setAllegatob1(rs.getString("allegatob1"));
                b1.setAllegatocv(rs.getString("allegatocv"));
                b1.setAllegatodr(rs.getString("allegatodr"));
                al.add(b1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return al;
    }

    public boolean delAllegatiDocenti(String username, String id) {
        String query = "delete from allegato_b1 where username=? and idallegato_b1=?";
        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, id);
            int x = ps.executeUpdate();
            return x > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delAllAllegatiDocenti(String username) {
        String query = "delete from allegato_b1 where username=?";
        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, username);
            int x = ps.executeUpdate();
            return x > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<DocumentiDocente> listaDocUserBandoDocenti(String username) {
        ArrayList<DocumentiDocente> val = new ArrayList<>();
        try {
            String sql = "SELECT * FROM allegato_b1 WHERE username = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DocumentiDocente dd = new DocumentiDocente();
                dd.setCv(rs.getString("allegatocv"));
                dd.setDr(rs.getString("allegatodr"));
                dd.setB1(rs.getString("allegatob1"));
                dd.setData(rs.getString("data"));
                val.add(dd);
            }
        } catch (SQLException ex) {
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return val;
    }

    public boolean insAllegatoB1Field(String iddocente, String username, String periodo, String durata, String incarico,
            String finanziamento, String attivita, String committente) {
        String query = "insert into allegato_b1_field (iddocente,username,periodo,durata,incarico,finanziamento,attivita,committente) values (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = this.c.prepareStatement(query);
            ps.setString(1, iddocente);
            ps.setString(2, username);
            ps.setString(3, periodo);
            ps.setString(4, durata);
            ps.setString(5, incarico);
            ps.setString(6, finanziamento);
            ps.setString(7, attivita);
            ps.setString(8, committente);
            int i = ps.executeUpdate();
            return i > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delAllegatoB1Field(String id, String username) {
        String query = "delete from allegato_b1_field where iddocente=? and username=?";
        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, id);
            ps.setString(2, username);
            int i = ps.executeUpdate();
            return i > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delAllegatoB1Field(String username) {
        String query = "delete from allegato_b1_field where username=?";
        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, username);
            int i = ps.executeUpdate();
            return i > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public AllegatoA getAllegatoA(String username) {
        AllegatoA a = new AllegatoA();
        String query = "select * from allegato_a where username=?";
        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                a.setEnteistituzionepubblica(rs.getString("enteistituzionepubblica"));
                a.setAssociazione(rs.getString("associazione"));
                a.setOrdineprofessionale(rs.getString("ordineprofessionale"));
                a.setSoggettoprivato(rs.getString("soggettoprivato"));
                a.setFormazione(rs.getString("formazione"));
                a.setRegione1(rs.getString("regione1"));
                a.setIscrizione1(rs.getString("iscrizione1"));
                a.setServizi(rs.getString("servizi"));
                a.setRegione2(rs.getString("regione2"));
                a.setIscrizione2(rs.getString("iscrizione2"));
                a.setAteco(rs.getString("ateco"));
                a.setNumaule(rs.getString("numaule"));
                a.setCitta1(rs.getString("citta1"));
                a.setProvincia1(rs.getString("provincia1"));
                a.setIndirizzo1(rs.getString("indirizzo1"));
                a.setEstremi1(rs.getString("estremi1"));
                a.setAccreditamento1(rs.getString("accreditamento1"));
                a.setResponsabile1(rs.getString("responsabile1"));
                a.setAmministrativo1(rs.getString("amministrativo1"));
                a.setRecapiti1(rs.getString("recapiti1"));
                a.setCitta2(rs.getString("citta2"));
                a.setProvincia2(rs.getString("provincia2"));
                a.setIndirizzo2(rs.getString("indirizzo2"));
                a.setEstremi2(rs.getString("estremi2"));
                a.setAccreditamento2(rs.getString("accreditamento2"));
                a.setResponsabile2(rs.getString("responsabile2"));
                a.setAmministrativo2(rs.getString("amministrativo2"));
                a.setRecapiti2(rs.getString("recapiti2"));
                a.setCitta3(rs.getString("citta3"));
                a.setProvincia3(rs.getString("provincia3"));
                a.setIndirizzo3(rs.getString("indirizzo3"));
                a.setEstremi3(rs.getString("estremi3"));
                a.setAccreditamento3(rs.getString("accreditamento3"));
                a.setResponsabile3(rs.getString("responsabile3"));
                a.setAmministrativo3(rs.getString("amministrativo3"));
                a.setRecapiti3(rs.getString("recapiti3"));
                a.setCitta4(rs.getString("citta4"));
                a.setProvincia4(rs.getString("provincia4"));
                a.setIndirizzo4(rs.getString("indirizzo4"));
                a.setEstremi4(rs.getString("estremi4"));
                a.setAccreditamento4(rs.getString("accreditamento4"));
                a.setResponsabile4(rs.getString("responsabile4"));
                a.setAmministrativo4(rs.getString("amministrativo4"));
                a.setRecapiti4(rs.getString("recapiti4"));
                a.setCitta5(rs.getString("citta5"));
                a.setProvincia5(rs.getString("provincia5"));
                a.setIndirizzo5(rs.getString("indirizzo5"));
                a.setEstremi5(rs.getString("estremi5"));
                a.setAccreditamento5(rs.getString("accreditamento5"));
                a.setResponsabile5(rs.getString("responsabile5"));
                a.setAmministrativo5(rs.getString("amministrativo5"));
                a.setRecapiti5(rs.getString("recapiti5"));
                a.setAttivita(rs.getString("attivita"));
                a.setDestinatari(rs.getString("destinatari"));
                a.setFinanziamento(rs.getString("finanziamento"));
                a.setCommittente(rs.getString("committente"));
                a.setPeriodo(rs.getString("periodo"));
                a.setAttivita2(rs.getString("attivita2"));
                a.setDestinatari2(rs.getString("destinatari2"));
                a.setFinanziamento2(rs.getString("finanziamento2"));
                a.setCommittente2(rs.getString("committente2"));
                a.setPeriodo2(rs.getString("periodo2"));
                a.setAttivita3(rs.getString("attivita3"));
                a.setDestinatari3(rs.getString("destinatari3"));
                a.setFinanziamento3(rs.getString("finanziamento3"));
                a.setCommittente3(rs.getString("committente3"));
                a.setPeriodo3(rs.getString("periodo3"));
                a.setAttivita4(rs.getString("attivita4"));
                a.setDestinatari4(rs.getString("destinatari4"));
                a.setFinanziamento4(rs.getString("finanziamento4"));
                a.setCommittente4(rs.getString("committente4"));
                a.setPeriodo4(rs.getString("periodo4"));
                a.setAttivita5(rs.getString("attivita5"));
                a.setDestinatari5(rs.getString("destinatari5"));
                a.setFinanziamento5(rs.getString("finanziamento5"));
                a.setCommittente5(rs.getString("committente5"));
                a.setPeriodo5(rs.getString("periodo5"));
                a.setNoconsorzio(rs.getString("noconsorzio"));
                a.setConsorzio(rs.getString("consorzio"));
                a.setNomeconsorzio(rs.getString("nomeconsorzio"));
                a.setPec(rs.getString("pec"));
                a.setNumdocenti(rs.getString("numdocenti"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    public AllegatoB getAllegatoB(String username) {
        AllegatoB ab = new AllegatoB();
        String query = "select * from allegato_b where username=?";
        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ab.setNome(rs.getString("nome"));
                ab.setCognome(rs.getString("cognome"));
                ab.setAppartenenza(rs.getString("appartenenza"));
                ab.setInquadramento(rs.getString("inquadramento"));
                ab.setNome2(rs.getString("nome2"));
                ab.setCognome2(rs.getString("cognome2"));
                ab.setAppartenenza2(rs.getString("appartenenza2"));
                ab.setInquadramento2(rs.getString("inquadramento2"));
                ab.setNome3(rs.getString("nome3"));
                ab.setCognome3(rs.getString("cognome3"));
                ab.setAppartenenza3(rs.getString("appartenenza3"));
                ab.setInquadramento3(rs.getString("inquadramento3"));
                ab.setNome4(rs.getString("nome4"));
                ab.setCognome4(rs.getString("cognome4"));
                ab.setAppartenenza4(rs.getString("appartenenza4"));
                ab.setInquadramento4(rs.getString("inquadramento4"));
                ab.setNome5(rs.getString("nome5"));
                ab.setCognome5(rs.getString("cognome5"));
                ab.setAppartenenza5(rs.getString("appartenenza5"));
                ab.setInquadramento5(rs.getString("inquadramento5"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ab;
    }

    public ArrayList<AllegatoB1_field> alb1(String user, String iddoc) {
        String query = "select * from allegato_b1_field where username=? and iddocente=?";
        ArrayList<AllegatoB1_field> al = new ArrayList<>();
        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, iddoc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AllegatoB1_field alb1 = new AllegatoB1_field();
                alb1.setAttivita(rs.getString("attivita"));
                alb1.setCommittente(rs.getString("committente"));
                alb1.setDurata(rs.getString("durata"));
                alb1.setFinanziamento(rs.getString("finanziamento"));
                alb1.setIddocente(rs.getString("iddocente"));
                alb1.setIncarico(rs.getString("incarico"));
                alb1.setPeriodo(rs.getString("periodo"));
                alb1.setUsername(rs.getString("username"));
                al.add(alb1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return al;
    }

    public boolean presenzaAllB1Field(String user, String iddoc) {
        String query = "select * from allegato_b1_field where username=? and iddocente=?";
        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, iddoc);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getDocentiAllegatoA(String username) {
        String query = "select numdocenti from allegato_a where username=?";
        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("numdocenti");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ArrayList<String> getUsername() {
        String query = "select username from usersvalori where username in (select username from domandecomplete) and username not in (select username from bandoh8) group by username";
        ArrayList<String> al = new ArrayList<>();
        try {
            PreparedStatement ps = this.c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                al.add(rs.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return al;
    }

    public ArrayList<String[]> getDescDocumenti() {
        ArrayList<String[]> val = new ArrayList<>();
        try {
            String query = "SELECT id,descrizione FROM doc_validi";
            PreparedStatement ps = this.c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] v1 = {rs.getString(1), rs.getString(2)};
                val.add(v1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return val;
    }

    public static String formatAL(String cod, ArrayList<String[]> array, int index) {
        for (int i = 0; i < array.size(); i++) {
            if (cod.equals(((String[]) array.get(i))[0])) {
                return ((String[]) array.get(i))[index];
            }
        }
        return "-";
    }

    public String expexcel_ricerca(String data_da, String data_a) {
        String generatedString = RandomStringUtils.randomAlphabetic(7);
        ArrayList<String[]> documenti = getDescDocumenti();
        try {
            String sql = "SELECT * FROM bandoh8 where coddomanda<>'-'";
            if (!data_da.trim().equals("") && data_a.equals("")) {
                sql = sql + " and dataconsegna like '" + data_da + "%'";
            } else if (!data_da.trim().equals("") && !data_a.equals("")) {
                sql = sql + " and dataconsegna>'" + data_da + " 00:00:00' and dataconsegna<'" + data_a + " 23:59:59'";
            }
            PreparedStatement ps = this.c.prepareStatement(sql + " limit 1000");
            ResultSet rs = ps.executeQuery();
            XSSFWorkbook workbook = new XSSFWorkbook();
            File xl = new File("/mnt/mcprofessioni/bandoba0h8/temp/" + generatedString + "bandoh8.xlsx");
            if (rs.next()) {
                XSSFSheet sheet = workbook.createSheet("DOMANDE CONSEGNATE");
                XSSFFont font = workbook.createFont();
                font.setFontName(HSSFFont.FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
                XSSFCellStyle style1 = (XSSFCellStyle) workbook.createCellStyle();
                style1.setFillBackgroundColor(new XSSFColor());
                style1.setFillForegroundColor(new XSSFColor(new java.awt.Color(192, 192, 192)));
                style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
                style1.setBorderTop(XSSFCellStyle.BORDER_THIN);
                style1.setBorderBottom(XSSFCellStyle.BORDER_THIN);
                style1.setBorderLeft(XSSFCellStyle.BORDER_THIN);
                style1.setBorderRight(XSSFCellStyle.BORDER_THIN);
                style1.setFont(font);
                XSSFFont font2 = workbook.createFont();
                font2.setFontName(HSSFFont.FONT_ARIAL);
                font2.setFontHeightInPoints((short) 12);
                XSSFCellStyle style2 = (XSSFCellStyle) workbook.createCellStyle();
                style2.setBorderTop(XSSFCellStyle.BORDER_THIN);
                style2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
                style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
                style2.setBorderRight(XSSFCellStyle.BORDER_THIN);
                style2.setFont(font2);
                int cntriga = 1;
                Row rowP = sheet.createRow((short) cntriga);
                Cell cl = rowP.createCell(1);
                cl.setCellStyle(style1);
                cl.setCellValue("Codice domanda");
                cl = rowP.createCell(2);
                cl.setCellStyle(style1);
                cl.setCellValue("Username");
                cl = rowP.createCell(3);
                cl.setCellStyle(style1);
                cl.setCellValue("SA già accreditato");
                cl = rowP.createCell(4);
                cl.setCellStyle(style1);
                cl.setCellValue("Cognome");
                cl = rowP.createCell(5);
                cl.setCellStyle(style1);
                cl.setCellValue("Nome");
                cl = rowP.createCell(6);
                cl.setCellStyle(style1);
                cl.setCellValue("Nato a");
                cl = rowP.createCell(7);
                cl.setCellStyle(style1);
                cl.setCellValue("Data nascita");
                cl = rowP.createCell(8);
                cl.setCellStyle(style1);
                cl.setCellValue("Carica societaria");
                cl = rowP.createCell(9);
                cl.setCellStyle(style1);
                cl.setCellValue("Società");
                cl = rowP.createCell(10);
                cl.setCellStyle(style1);
                cl.setCellValue("Comune sede");
                cl = rowP.createCell(11);
                cl.setCellStyle(style1);
                cl.setCellValue("Provincia sede");
                cl = rowP.createCell(12);
                cl.setCellStyle(style1);
                cl.setCellValue("Indirizzo sede");
                cl = rowP.createCell(13);
                cl.setCellStyle(style1);
                cl.setCellValue("CAP sede");
                cl = rowP.createCell(14);
                cl.setCellStyle(style1);
                cl.setCellValue("Codice fiscale");
                cl = rowP.createCell(15);
                cl.setCellStyle(style1);
                cl.setCellValue("Partita iva/Codice fiscale SA");
                cl = rowP.createCell(16);
                cl.setCellStyle(style1);
                cl.setCellValue("Comune CCIAA");
                cl = rowP.createCell(17);
                cl.setCellStyle(style1);
                cl.setCellValue("Provincia CCIAA");
                cl = rowP.createCell(18);
                cl.setCellStyle(style1);
                cl.setCellValue("Numero REA");
                cl = rowP.createCell(19);
                cl.setCellStyle(style1);
                cl.setCellValue("Matricola Inps");
                cl = rowP.createCell(20);
                cl.setCellStyle(style1);
                cl.setCellValue("PEC");
                cl = rowP.createCell(21);
                cl.setCellStyle(style1);
                cl.setCellValue("Email");
                cl = rowP.createCell(22);
                cl.setCellStyle(style1);
                cl.setCellValue("Idoneità");
                cl = rowP.createCell(23);
                cl.setCellStyle(style1);
                cl.setCellValue("Cellulare");
                cl = rowP.createCell(24);
                cl.setCellStyle(style1);
                cl.setCellValue("Tipo documento");
                cl = rowP.createCell(25);
                cl.setCellStyle(style1);
                cl.setCellValue("Numero documento");
                cl = rowP.createCell(26);
                cl.setCellStyle(style1);
                cl.setCellValue("Data consegna");
                rs.beforeFirst();
                while (rs.next()) {
                    cntriga++;
                    rowP = sheet.createRow((short) cntriga);
                    cl = rowP.createCell(1);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("coddomanda"));
                    cl = rowP.createCell(2);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("username"));
                    cl = rowP.createCell(3);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("accreditato"));
                    cl = rowP.createCell(4);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("cognome").toUpperCase());
                    cl = rowP.createCell(5);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("nome").toUpperCase());
                    cl = rowP.createCell(6);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("nato_a").toUpperCase());
                    cl = rowP.createCell(7);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("data").toUpperCase());
                    cl = rowP.createCell(8);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("carica").toUpperCase());
                    cl = rowP.createCell(9);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("societa").toUpperCase());
                    cl = rowP.createCell(10);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("sedecomune").toUpperCase());
                    cl = rowP.createCell(11);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("sedeprovincia").toUpperCase());
                    cl = rowP.createCell(12);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("sedeindirizzo").toUpperCase());
                    cl = rowP.createCell(13);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("sedecap").toUpperCase());
                    cl = rowP.createCell(14);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("cf").toUpperCase());
                    cl = rowP.createCell(15);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("pivacf").toUpperCase());
                    cl = rowP.createCell(16);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("cciaacomune").toUpperCase());
                    cl = rowP.createCell(17);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("cciaaprovincia").toUpperCase());
                    cl = rowP.createCell(18);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("rea").toUpperCase());
                    cl = rowP.createCell(19);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("matricolainps").toUpperCase());
                    cl = rowP.createCell(20);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("pec").toUpperCase());
                    cl = rowP.createCell(21);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("mail").toUpperCase());
                    cl = rowP.createCell(22);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("idoneo").toUpperCase());
                    cl = rowP.createCell(23);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("cellulare").toUpperCase());

                    cl = rowP.createCell(24);
                    cl.setCellStyle(style2);
                    cl.setCellValue(formatAL(rs.getString("tipdoc").toUpperCase(), documenti, 1));
                    cl = rowP.createCell(25);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("docric"));
                    cl = rowP.createCell(26);
                    cl.setCellStyle(style2);
                    cl.setCellValue(rs.getString("dataconsegna").toUpperCase());
                }
                for (int c = 1; c < 60; c++) {
                    sheet.autoSizeColumn(c);
                }
            }
            FileOutputStream out = new FileOutputStream(xl);
            workbook.write(out);
            out.close();
            //String base64 = new String(Base64.encodeBase64(FileUtils.readFileToByteArray(xl)));
            return "/mnt/mcprofessioni/bandoba0h8/temp/" + generatedString + "bandoh8.xlsx";

        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Domandecomplete domandeComplete(String cod, String username) {
        Domandecomplete dc = new Domandecomplete();
        try {
            String sql = "SELECT * FROM usersvalori WHERE codbando = ? AND username = ?";
            PreparedStatement ps = this.c.prepareStatement(sql);
            ps.setString(1, cod);
            ps.setString(2, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String campo = rs.getString("campo");
                dc.setCodbando("BA0F6");
                dc.setUsername(username);
                if (campo.equals("accreditato")) {
                    dc.setAccreditato(rs.getString("valore"));
                }
                if (campo.equals("caricasoc")) {
                    dc.setCarica(rs.getString("valore"));
                }
                if (campo.equals("cell")) {
                    dc.setCellulare(rs.getString("valore"));
                }
                if (campo.equals("cf")) {
                    dc.setCf(rs.getString("valore"));
                }
                if (campo.equals("cognome")) {
                    dc.setCognome(rs.getString("valore"));
                }
                if (campo.equals("comunecciaa")) {
                    dc.setCciaacomune(rs.getString("valore"));
                }
                if (campo.equals("data")) {
                    dc.setData(rs.getString("valore"));
                }
                if (campo.equals("docric1")) {
                    dc.setDocric(rs.getString("valore"));
                }
                if (campo.equals("email")) {
                    dc.setMail(rs.getString("valore"));
                }
                if (campo.equals("idoneo")) {
                    dc.setIdoneo(rs.getString("valore"));
                }
                if (campo.equals("matricolainps")) {
                    dc.setMatricolainps(rs.getString("valore"));
                }
                if (campo.equals("nato")) {
                    dc.setNato_a(rs.getString("valore"));
                }
                if (campo.equals("nome")) {
                    dc.setNome(rs.getString("valore"));
                }
                if (campo.equals("pec")) {
                    dc.setPec(rs.getString("valore"));
                }
                if (campo.equals("piva")) {
                    dc.setPivacf(rs.getString("valore"));
                }
                if (campo.equals("proccciaa")) {
                    dc.setCciaaprovincia(rs.getString("valore"));
                }
                if (campo.equals("rea")) {
                    dc.setRea(rs.getString("valore"));
                }
                if (campo.equals("sedecap")) {
                    dc.setSedecap(rs.getString("valore"));
                }
                if (campo.equals("sedecomune")) {
                    dc.setSedecomune(rs.getString("valore"));
                }
                if (campo.equals("sedeindirizzo")) {
                    dc.setSedeindirizzo(rs.getString("valore"));
                }
                if (campo.equals("sedeprov")) {
                    dc.setSedeprovincia(rs.getString("valore"));
                }
                if (campo.equals("societa")) {
                    dc.setSocieta(rs.getString("valore"));
                }
                if (campo.equals("tipdoc1")) {
                    dc.setTipdoc(rs.getString("valore"));
                }
                String valoriDomandaCompleta[] = getCodiceDomanda(username);
                dc.setCoddomanda(valoriDomandaCompleta[0]);
                dc.setDataconsegna(valoriDomandaCompleta[1]);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return dc;
    }

    public String[] getCodiceDomanda(String username) {
        String query = "select id,timestamp from domandecomplete where username=?";
        String valori[] = new String[2];
        try {
            PreparedStatement ps = this.c.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                valori[0] = rs.getString("id");
                valori[1] = rs.getString("timestamp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valori;
    }

    public boolean isPresenzaDocumento(String username, String tipologia) {
        String query = "select username from docuserbandi where username='" + username + "' and codicedoc='" + tipologia + "'";
        try {
            PreparedStatement ps = this.c.prepareStatement(query);
            ResultSet rs = ps.executeQuery(query);
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setStatoDomandaAccRif(String username, String stato, String protocollo, String motivazione) {
        String query = "update bandoh8 set stato_domanda='" + stato + "', protocollo=?, rigetto=? where username='" + username + "' and stato_domanda='S'";
        
        try {
            PreparedStatement ps = this.c.prepareStatement(query);
            ps.setString(1, protocollo);
            ps.setString(2, motivazione);
            int x = ps.executeUpdate();
            return x > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean getStatoDomanda(String username) {
        String query = "select username from bandoh8 where stato_domanda='A' and username='" + username + "' ";
        try {
            PreparedStatement ps = this.c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertDocumentUserConvenzioni(Docuserconvenzioni dub) {
        try {
            String ins = "INSERT INTO docuserconvenzioni (codbando,username,codicedoc,path) VALUES (?,?,?,?)";
            PreparedStatement ps = this.c.prepareStatement(ins);
            ps.setString(1, dub.getCodbando());
            ps.setString(2, dub.getUsername());
            ps.setString(3, dub.getCodicedoc());
            ps.setString(4, dub.getPath());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public boolean verPresenzaConvenzioni(String username, String coddoc) {
        try {
            String query = "select username from docuserconvenzioni where username = '" + username + "' and codicedoc = '" + coddoc + "'";
            PreparedStatement ps = this.c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getPathConvenzioni(String username, String coddoc) {
        String var = "";
        try {
            String query = "select path from docuserconvenzioni where username = '" + username + "' and codicedoc = '" + coddoc + "'";
            PreparedStatement ps = this.c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                var = rs.getString("path");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return var;
    }

    public boolean remDocConvenzioni(String username, String coddoc) {
        try {
            String query = "delete from docuserconvenzioni where username = '" + username + "' and codicedoc = '" + coddoc + "'";
            PreparedStatement ps = this.c.prepareStatement(query);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean verificaPresenzaConvenzioni(String username, String coddoc) {
        try {
            String query = "select username from docuserconvenzioni where username = '" + username + "' and codicedoc = '" + coddoc + "'";
            PreparedStatement ps = this.c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int countDocumentConvenzioni(String username) {
        int var = 0;
        try {
            String query = "select count(*) from docuserconvenzioni where username='" + username + "'";
            PreparedStatement ps = this.c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                var = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return var;
    }

    public boolean setStatoInvioDocumenti(String username) {
        try {
            String query = "update docuserconvenzioni set stato='1' where username='" + username + "'";
            PreparedStatement ps = this.c.prepareStatement(query);
            int x = ps.executeUpdate();
            return x > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean verificaInvioConvenzione(String username) {
        try {
            String query = "select username from docuserconvenzioni where username = '" + username + "' and stato='1'";
            PreparedStatement ps = this.c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Docuserconvenzioni> getDocumentiConvenzioni(String username) {
        ArrayList<Docuserconvenzioni> al = new ArrayList<>();
        try {
            String query = "select * from docuserconvenzioni where username = '" + username + "' and stato='1'";
            PreparedStatement ps = this.c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Docuserconvenzioni d = new Docuserconvenzioni();
                d.setCodicedoc(rs.getString("codicedoc"));
                d.setPath(rs.getString("path"));
                d.setNote("-");
                d.setDatacar(rs.getString("timestamp"));
                al.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return al;
    }

    public boolean settaInvioEmailROMA(String username) {
        String query = "update docuserconvenzioni set sendmail='1' where username='" + username + "'";
        try {
            PreparedStatement ps = this.c.prepareStatement(query);
            int i = ps.executeUpdate();
            return i > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getInvioEmailROMA(String username) {
        String query = "select username,sendmail from docuserconvenzioni where username='" + username + "' and codicedoc='CONV'";
        try {
            PreparedStatement ps = this.c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("sendmail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    // DOCUMENTAZIONE PROVENIENTE DA ROMA
    public boolean insertConvenzioneROMA(String username, String path) {
        try {
            String ins = "INSERT INTO convenzioniroma (codbando,username,codicedoc,path) VALUES (?,?,?,?)";
            PreparedStatement ps = this.c.prepareStatement(ins);
            ps.setString(1, "BA0F6");
            ps.setString(2, username);
            ps.setString(3, "CONVROMA");
            ps.setString(4, path);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            Constant.log.severe(bando + ": " + ex.getMessage());
        }
        return false;
    }

    public String getConvenzioneROMA(String username) {
        String pathRoma = "";
        try {
            String query = "select path from convenzioniroma where username = '" + username + "' order by timestamp desc limit 1";
            PreparedStatement ps = this.c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pathRoma = rs.getString("path");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pathRoma;
    }
    
    public String getEmailUtente(String username){
        String email = "";
        String query = "select mail from users where username=?";
        try {
            PreparedStatement ps = this.c.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                email = rs.getString("mail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return email;
    }

    public String[] getValoriPerEmail(String username) {
        String var[] = new String[5];
        String query = "select * from bandoh8 where username=?";
        try {
            PreparedStatement ps = this.c.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                var[0]= rs.getString("username");
                var[1]= rs.getString("coddomanda");
                var[2]= rs.getString("protocollo");
                var[3]= rs.getString("mail");
                var[4]= rs.getString("societa");
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return var;
    }
    
    public String getRagioneSociale(String user){
        String var = "";
        String query = "select valore from usersvalori where username='"+user+"' and campo='societa'";
        try {
            PreparedStatement ps = this.c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                var = rs.getString("valore");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return var;
    }
    
    public static boolean getPresenzaInvioEmail() {
        return false;
    }

//    public static void main(String[] args) {
//
//        Db_Bando db = new Db_Bando();
//        db.expexcel_ricerca();
//        db.closeDB();
//     
//    }
}
