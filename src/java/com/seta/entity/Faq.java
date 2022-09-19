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
public class Faq {
    String id,codbando,nomedoc,tipodoc,descrizionedoc,pathpdf,stato,datacar,timestamp;

    public Faq() {
    }

    public Faq(String id, String codbando, String nomedoc, String tipodoc, String descrizionedoc, String pathpdf, String stato, String datacar, String timestamp) {
        this.id = id;
        this.codbando = codbando;
        this.nomedoc = nomedoc;
        this.tipodoc = tipodoc;
        this.descrizionedoc = descrizionedoc;
        this.pathpdf = pathpdf;
        this.stato = stato;
        this.datacar = datacar;
        this.timestamp = timestamp;
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

    public String getNomedoc() {
        return nomedoc;
    }

    public void setNomedoc(String nomedoc) {
        this.nomedoc = nomedoc;
    }

    public String getTipodoc() {
        return tipodoc;
    }

    public void setTipodoc(String tipodoc) {
        this.tipodoc = tipodoc;
    }

    public String getDescrizionedoc() {
        return descrizionedoc;
    }

    public void setDescrizionedoc(String descrizionedoc) {
        this.descrizionedoc = descrizionedoc;
    }

    public String getPathpdf() {
        return pathpdf;
    }

    public void setPathpdf(String pathpdf) {
        this.pathpdf = pathpdf;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
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