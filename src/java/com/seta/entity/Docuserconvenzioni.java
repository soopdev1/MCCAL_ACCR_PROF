package com.seta.entity;

/**
 *
 * @author raffaele
 */
public class Docuserconvenzioni {

    String codbando, username, codicedoc, stato, path, datacar, timestamp,tipodoc,note;

    public Docuserconvenzioni(String codbando, String username, String codicedoc, String stato, String path, String datacar,String tipodoc,String note) {
        this.codbando = codbando;
        this.username = username;
        this.codicedoc = codicedoc;
        this.stato = stato;
        this.path = path;
        this.datacar = datacar;
        this.tipodoc = tipodoc;
        this.note = note;
    }

    public Docuserconvenzioni(String codbando, String username, String codicedoc, String stato, String path, String datacar, String timestamp,String tipodoc,String note) {
        this.codbando = codbando;
        this.username = username;
        this.codicedoc = codicedoc;
        this.stato = stato;
        this.path = path;
        this.datacar = datacar;
        this.timestamp = timestamp;
        this.tipodoc = tipodoc;
        this.note = note;
    }

    public Docuserconvenzioni() {
    }

    public String getTipodoc() {
        return tipodoc;
    }

    public void setTipodoc(String tipodoc) {
        this.tipodoc = tipodoc;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getCodicedoc() {
        return codicedoc;
    }

    public void setCodicedoc(String codicedoc) {
        this.codicedoc = codicedoc;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDatacar() {
        return datacar;
    }

    public void setDatacar(String datacar) {
        this.datacar = datacar;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    
    
}
