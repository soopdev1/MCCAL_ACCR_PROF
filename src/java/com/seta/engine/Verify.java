
package com.seta.engine;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per verify complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="verify">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ver" type="{http://engine.seta.com/}verifica" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verify", propOrder = {
    "ver"
})
public class Verify {

    protected Verifica ver;

    /**
     * Recupera il valore della proprietà ver.
     * 
     * @return
     *     possible object is
     *     {@link Verifica }
     *     
     */
    public Verifica getVer() {
        return ver;
    }

    /**
     * Imposta il valore della proprietà ver.
     * 
     * @param value
     *     allowed object is
     *     {@link Verifica }
     *     
     */
    public void setVer(Verifica value) {
        this.ver = value;
    }

}
