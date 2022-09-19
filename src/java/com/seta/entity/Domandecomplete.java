/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.entity;

/**
 *
 * @author raffaele
 */
public class Domandecomplete {

    String id, codbando, username, datainvio, stato, timestamp;

    // dati utente;
    String nome, cognome, codicefiscale, pll, profiloprof, comune, consolidato;

    String ragsoc;
    String piva;
    String codfissog;
    String accreditato = "";
    String nato_a = "";
    String data = "";
    String carica = "";
    String societa = "";
    String sedecomune = "";
    String sedeprovincia = "";
    String sedeindirizzo = "";
    String sedecap = "";
    String cf = "";
    String pivacf = "";
    String cciaacomune = "";
    String cciaaprovincia = "";
    String rea = "";
    String matricolainps = "";
    String pec = "";
    String mail = "";
    String idoneo = "";
    String cellulare = "";
    String tipdoc = "";
    String docric = "";
    String coddomanda = "";
    String dataconsegna = "";
    String statoDomanda = "";

    public Domandecomplete(String id, String consolidato) {
        this.id = id;
        this.consolidato = consolidato;
    }

    public Domandecomplete(String id, String codbando, String username, String datainvio, String stato, String timestamp) {
        this.id = id;
        this.codbando = codbando;
        this.username = username;
        this.datainvio = datainvio;
        this.stato = stato;
        this.timestamp = timestamp;
    }

    public Domandecomplete(String id, String codbando, String username, String datainvio) {
        this.id = id;
        this.codbando = codbando;
        this.username = username;
        this.datainvio = datainvio;
    }

    public Domandecomplete(String id, String username, String nome, String cognome, String cf, String dataconsegna, String ragsoc, String piva, String codfissog, String statoDomanda) {
        this.id = id;
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.codicefiscale = cf;
        this.datainvio = dataconsegna;
        this.ragsoc = ragsoc;
        this.piva = piva;
        this.codfissog = codfissog;
        this.statoDomanda = statoDomanda;
    }

    public String getRagsoc() {
        return ragsoc;
    }

    public void setRagsoc(String ragsoc) {
        this.ragsoc = ragsoc;
    }

    public String getPiva() {
        return piva;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }

    public String getCodfissog() {
        return codfissog;
    }

    public void setCodfissog(String codfissog) {
        this.codfissog = codfissog;
    }

    public Domandecomplete() {
    }

    public String getConsolidato() {
        return consolidato;
    }

    public void setConsolidato(String consolidato) {
        this.consolidato = consolidato;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodicefiscale() {
        return codicefiscale;
    }

    public void setCodicefiscale(String codicefiscale) {
        this.codicefiscale = codicefiscale;
    }

    public String getPll() {
        return pll;
    }

    public void setPll(String pll) {
        this.pll = pll;
    }

    public String getProfiloprof() {
        return profiloprof;
    }

    public void setProfiloprof(String profiloprof) {
        this.profiloprof = profiloprof;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodbando() {
        return codbando;
    }

    public void setCodbando(String codbando) {
        this.codbando = codbando;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDatainvio() {
        return datainvio;
    }

    public void setDatainvio(String datainvio) {
        this.datainvio = datainvio;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAccreditato() {
        return accreditato;
    }

    public void setAccreditato(String accreditato) {
        this.accreditato = accreditato;
    }

    public String getNato_a() {
        return nato_a;
    }

    public void setNato_a(String nato_a) {
        this.nato_a = nato_a;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCarica() {
        return carica;
    }

    public void setCarica(String carica) {
        this.carica = carica;
    }

    public String getSocieta() {
        return societa;
    }

    public void setSocieta(String societa) {
        this.societa = societa;
    }

    public String getSedecomune() {
        return sedecomune;
    }

    public void setSedecomune(String sedecomune) {
        this.sedecomune = sedecomune;
    }

    public String getSedeprovincia() {
        return sedeprovincia;
    }

    public void setSedeprovincia(String sedeprovincia) {
        this.sedeprovincia = sedeprovincia;
    }

    public String getSedeindirizzo() {
        return sedeindirizzo;
    }

    public void setSedeindirizzo(String sedeindirizzo) {
        this.sedeindirizzo = sedeindirizzo;
    }

    public String getSedecap() {
        return sedecap;
    }

    public void setSedecap(String sedecap) {
        this.sedecap = sedecap;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getPivacf() {
        return pivacf;
    }

    public void setPivacf(String pivacf) {
        this.pivacf = pivacf;
    }

    public String getCciaacomune() {
        return cciaacomune;
    }

    public void setCciaacomune(String cciaacomune) {
        this.cciaacomune = cciaacomune;
    }

    public String getCciaaprovincia() {
        return cciaaprovincia;
    }

    public void setCciaaprovincia(String cciaaprovincia) {
        this.cciaaprovincia = cciaaprovincia;
    }

    public String getRea() {
        return rea;
    }

    public void setRea(String rea) {
        this.rea = rea;
    }

    public String getMatricolainps() {
        return matricolainps;
    }

    public void setMatricolainps(String matricolainps) {
        this.matricolainps = matricolainps;
    }

    public String getPec() {
        return pec;
    }

    public void setPec(String pec) {
        this.pec = pec;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getIdoneo() {
        return idoneo;
    }

    public void setIdoneo(String idoneo) {
        this.idoneo = idoneo;
    }

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public String getTipdoc() {
        return tipdoc;
    }

    public void setTipdoc(String tipdoc) {
        this.tipdoc = tipdoc;
    }

    public String getDocric() {
        return docric;
    }

    public void setDocric(String docric) {
        this.docric = docric;
    }

    public String getCoddomanda() {
        return coddomanda;
    }

    public void setCoddomanda(String coddomanda) {
        this.coddomanda = coddomanda;
    }

    public String getDataconsegna() {
        return dataconsegna;
    }

    public void setDataconsegna(String dataconsegna) {
        this.dataconsegna = dataconsegna;
    }

    public String getStatoDomanda() {
        return statoDomanda;
    }

    public void setStatoDomanda(String statoDomanda) {
        this.statoDomanda = statoDomanda;
    }

}
