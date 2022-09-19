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
public class UserBando {

    String username, codiceBando, stato, datareg, timestamp, password, cell, mail,tipo,sino;

    public UserBando() {
    }

    public UserBando(String username, String password, String codiceBando, String datareg, String cell, String mail,String tipo, String sino) {
        this.username = username;
        this.codiceBando = codiceBando;
        this.datareg = datareg;
        this.password = password;
        this.cell = cell;
        this.mail = mail;
        this.tipo = tipo;
        this.sino = sino;
    }

    public UserBando(String username, String codiceBando, String stato, String datareg, String timestamp, String password, String cell, String mail,String tipo, String sino) {
        this.username = username;
        this.codiceBando = codiceBando;
        this.stato = stato;
        this.datareg = datareg;
        this.timestamp = timestamp;
        this.password = password;
        this.cell = cell;
        this.mail = mail;
        this.tipo = tipo;
        this.sino = sino;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCodiceBando() {
        return codiceBando;
    }

    public void setCodiceBando(String codiceBando) {
        this.codiceBando = codiceBando;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
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

    public String getSino() {
        return sino;
    }

    public void setSino(String sino) {
        this.sino = sino;
    }
    
}
