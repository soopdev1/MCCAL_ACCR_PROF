
package com.seta.engine;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per richiesta complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="richiesta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codMsg" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codProgetto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numcell" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pswd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="user" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userPortale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "richiesta", propOrder = {
    "codMsg",
    "codProgetto",
    "numcell",
    "pswd",
    "user",
    "userPortale"
})
public class Richiesta {

    protected int codMsg;
    protected String codProgetto;
    protected String numcell;
    protected String pswd;
    protected String user;
    protected String userPortale;

    /**
     * Recupera il valore della proprietà codMsg.
     * 
     */
    public int getCodMsg() {
        return codMsg;
    }

    /**
     * Imposta il valore della proprietà codMsg.
     * 
     */
    public void setCodMsg(int value) {
        this.codMsg = value;
    }

    /**
     * Recupera il valore della proprietà codProgetto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodProgetto() {
        return codProgetto;
    }

    /**
     * Imposta il valore della proprietà codProgetto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodProgetto(String value) {
        this.codProgetto = value;
    }

    /**
     * Recupera il valore della proprietà numcell.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumcell() {
        return numcell;
    }

    /**
     * Imposta il valore della proprietà numcell.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumcell(String value) {
        this.numcell = value;
    }

    /**
     * Recupera il valore della proprietà pswd.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPswd() {
        return pswd;
    }

    /**
     * Imposta il valore della proprietà pswd.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPswd(String value) {
        this.pswd = value;
    }

    /**
     * Recupera il valore della proprietà user.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUser() {
        return user;
    }

    /**
     * Imposta il valore della proprietà user.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUser(String value) {
        this.user = value;
    }

    /**
     * Recupera il valore della proprietà userPortale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserPortale() {
        return userPortale;
    }

    /**
     * Imposta il valore della proprietà userPortale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserPortale(String value) {
        this.userPortale = value;
    }

}
