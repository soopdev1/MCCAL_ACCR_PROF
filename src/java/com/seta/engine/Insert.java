
package com.seta.engine;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per insert complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="insert">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ric" type="{http://engine.seta.com/}richiesta" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "insert", propOrder = {
    "ric"
})
public class Insert {

    protected Richiesta ric;

    /**
     * Recupera il valore della proprietà ric.
     * 
     * @return
     *     possible object is
     *     {@link Richiesta }
     *     
     */
    public Richiesta getRic() {
        return ric;
    }

    /**
     * Imposta il valore della proprietà ric.
     * 
     * @param value
     *     allowed object is
     *     {@link Richiesta }
     *     
     */
    public void setRic(Richiesta value) {
        this.ric = value;
    }

}
