package com.seta.entity;

/**
 *
 * @author raffaele
 */
public class Reportistica {

    String codice, bando, descrizione, tipo, categorie, assex, valore, ordine, timestamp,color,icona;

    public Reportistica(String codice, String bando, String descrizione, String tipo, String categorie,
            String assex, String valore, String ordine, String timestamp, String color, String icona) {
        this.codice = codice;
        this.bando = bando;
        this.descrizione = descrizione;
        this.tipo = tipo;
        this.categorie = categorie;
        this.assex = assex;
        this.valore = valore;
        this.ordine = ordine;
        this.timestamp = timestamp;
        this.color = color;
        this.icona = icona;
    }

    public Reportistica() {
    }

    public String getIcona() {
        return icona;
    }

    public void setIcona(String icona) {
        this.icona = icona;
    }
    
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getBando() {
        return bando;
    }

    public void setBando(String bando) {
        this.bando = bando;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getAssex() {
        return assex;
    }

    public void setAssex(String assex) {
        this.assex = assex;
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }

    public String getOrdine() {
        return ordine;
    }

    public void setOrdine(String ordine) {
        this.ordine = ordine;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
