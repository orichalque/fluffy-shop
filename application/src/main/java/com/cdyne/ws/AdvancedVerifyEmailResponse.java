
package com.cdyne.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schema suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AdvancedVerifyEmailResult" type="{http://ws.cdyne.com/}ReturnIndicator" minOccurs="0"/>
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
    "advancedVerifyEmailResult"
})
@XmlRootElement(name = "AdvancedVerifyEmailResponse")
public class AdvancedVerifyEmailResponse {

    @XmlElement(name = "AdvancedVerifyEmailResult")
    protected ReturnIndicator advancedVerifyEmailResult;

    /**
     * Obtient la valeur de la propriete advancedVerifyEmailResult.
     * 
     * @return
     *     possible object is
     *     {@link ReturnIndicator }
     *     
     */
    public ReturnIndicator getAdvancedVerifyEmailResult() {
        return advancedVerifyEmailResult;
    }

    /**
     * Definit la valeur de la propriete advancedVerifyEmailResult.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnIndicator }
     *     
     */
    public void setAdvancedVerifyEmailResult(ReturnIndicator value) {
        this.advancedVerifyEmailResult = value;
    }

}
