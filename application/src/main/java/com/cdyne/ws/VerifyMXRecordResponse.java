
package com.cdyne.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VerifyMXRecordResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "verifyMXRecordResult"
})
@XmlRootElement(name = "VerifyMXRecordResponse")
public class VerifyMXRecordResponse {

    @XmlElement(name = "VerifyMXRecordResult")
    protected int verifyMXRecordResult;

    /**
     * Obtient la valeur de la propri�t� verifyMXRecordResult.
     * 
     */
    public int getVerifyMXRecordResult() {
        return verifyMXRecordResult;
    }

    /**
     * D�finit la valeur de la propri�t� verifyMXRecordResult.
     * 
     */
    public void setVerifyMXRecordResult(int value) {
        this.verifyMXRecordResult = value;
    }

}
