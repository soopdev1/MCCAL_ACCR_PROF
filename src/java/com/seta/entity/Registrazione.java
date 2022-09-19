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
public class Registrazione {

    String codbando, etichetta, campo, tipocampo, lunghezza, visibile, obbligatorio, timestamp,testomodello;
    int ordinamento;

    public Registrazione() {
    }

    public Registrazione(String codbando, String etichetta, String campo, String tipocampo, String lunghezza, String visibile, String obbligatorio,  int ordinamento,String timestamp,String testomodello) {
        this.codbando = codbando;
        this.etichetta = etichetta;
        this.campo = campo;
        this.tipocampo = tipocampo;
        this.lunghezza = lunghezza;
        this.visibile = visibile;
        this.obbligatorio = obbligatorio;
        this.timestamp = timestamp;
        this.ordinamento = ordinamento;
        this.testomodello = testomodello;
    }

    public String getTestomodello() {
        return testomodello;
    }

    public void setTestomodello(String testomodello) {
        this.testomodello = testomodello;
    }
    
    public String getCodbando() {
        return codbando;
    }

    public void setCodbando(String codbando) {
        this.codbando = codbando;
    }

    public String getEtichetta() {
        return etichetta;
    }

    public void setEtichetta(String etichetta) {
        this.etichetta = etichetta;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getTipocampo() {
        return tipocampo;
    }

    public void setTipocampo(String tipocampo) {
        this.tipocampo = tipocampo;
    }

    public String getLunghezza() {
        return lunghezza;
    }

    public void setLunghezza(String lunghezza) {
        this.lunghezza = lunghezza;
    }

    public String getVisibile() {
        return visibile;
    }

    public void setVisibile(String visibile) {
        this.visibile = visibile;
    }

    public String getObbligatorio() {
        return obbligatorio;
    }

    public void setObbligatorio(String obbligatorio) {
        this.obbligatorio = obbligatorio;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getOrdinamento() {
        return ordinamento;
    }

    public void setOrdinamento(int ordinamento) {
        this.ordinamento = ordinamento;
    }
    
    
    
}
