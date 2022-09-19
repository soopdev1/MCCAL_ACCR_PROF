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
public class Docbandi {
    
    String codbando,codicedoc,titolo,tipodoc,info,obbl,timestamp,datarif,download,faq,upload;
    
    int ordinamento;
    
    String collegati;
            
    public Docbandi() {
    }
    
    public Docbandi(
            String codbando, String codicedoc, 
            String titolo, String tipodoc, 
            String info, String obbl, 
            int ordinamento, String datarif, 
            String timestamp,String download,String faq,String upload,String collegati) {
        this.codbando = codbando;
        this.codicedoc = codicedoc;
        this.titolo = titolo;
        this.tipodoc = tipodoc;
        this.info = info;
        this.obbl = obbl;
        this.ordinamento = ordinamento;
        this.timestamp = timestamp;
        this.datarif = datarif;
        this.download = download;
        this.faq = faq;
        this.upload = upload;
        this.collegati = collegati;
    }

    public String getCollegati() {
        return collegati;
    }

    public void setCollegati(String collegati) {
        this.collegati = collegati;
    }
    
    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }
    
    public String getFaq() {
        return faq;
    }

    public void setFaq(String faq) {
        this.faq = faq;
    }
    
    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }
        
    public String getDatarif() {
        return datarif;
    }

    public void setDatarif(String datarif) {
        this.datarif = datarif;
    }
    
    public int getOrdinamento() {
        return ordinamento;
    }

    public void setOrdinamento(int ordinamento) {
        this.ordinamento = ordinamento;
    }
    
    public String getCodbando() {
        return codbando;
    }

    public void setCodbando(String codbando) {
        this.codbando = codbando;
    }

    public String getCodicedoc() {
        return codicedoc;
    }

    public void setCodicedoc(String codicedoc) {
        this.codicedoc = codicedoc;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getTipodoc() {
        return tipodoc;
    }

    public void setTipodoc(String tipodoc) {
        this.tipodoc = tipodoc;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getObbl() {
        return obbl;
    }

    public void setObbl(String obbl) {
        this.obbl = obbl;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    

}
