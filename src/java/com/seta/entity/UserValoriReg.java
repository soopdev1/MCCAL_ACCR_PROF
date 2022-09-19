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
public class UserValoriReg {

    String codbando, username, campo, valore, datareg, timestamp;

    public UserValoriReg() {

    }

    public UserValoriReg(String codbando, String username, String campo, String valore, String datareg) {
        this.codbando = codbando;
        this.username = username;
        this.campo = campo;
        this.valore = valore;
        this.datareg = datareg;
    }

    public UserValoriReg(String codbando, String username, String campo, String valore, String datareg, String timestamp) {
        this.codbando = codbando;
        this.username = username;
        this.campo = campo;
        this.valore = valore;
        this.datareg = datareg;
        this.timestamp = timestamp;
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

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }

    public String getDatareg() {
        return datareg;
    }

    public void setDatareg(String datareg) {
        this.datareg = datareg;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
